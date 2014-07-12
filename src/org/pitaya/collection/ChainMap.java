/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2014 Osman KOCAK <kocakosm@gmail.com>                   *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation, either version 3 of the License, or (at your *
 * option) any later version.                                                 *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public     *
 * License for more details.                                                  *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *----------------------------------------------------------------------------*/

package org.pitaya.collection;

import org.pitaya.util.Objects;
import org.pitaya.util.Parameters;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A {@code ChainMap} is a {@link Map} implementation that groups multiple
 * {@code Map}s together. Lookups sequentially walk trough all the underlying
 * {@code Map}s until the value for the looked up key is found. Writes and
 * updates only modify the first {@code Map}. Deletions are applied on all
 * underlying {@code Map}s. Note that only references to the input {@code Map}s
 * are stored, so, if one of one of these {@code Map}s gets updated, those
 * changes will be reflected in the {@code ChainMap}. Not thread safe.
 *
 * @param <K> the type of the {@code Map}'s keys.
 * @param <V> the type of the {@code Map}'s values.
 *
 * @author Osman KOCAK
 */
public final class ChainMap<K, V> extends AbstractMap<K, V> implements Map<K, V>
{
	private final List<Map<K, V>> maps;

	/**
	 * Creates a new {@code ChainMap} from the given {@code Map}s.
	 *
	 * @param maps the input {@code Map}s.
	 *
	 * @throws NullPointerException if {@code maps} is {@code null}.
	 * @throws IllegalArgumentException if {@code maps} is empty.
	 */
	public ChainMap(Map<K, V>... maps)
	{
		Parameters.checkCondition(maps.length > 0);
		this.maps = ImmutableList.copyOf(maps);
	}

	/**
	 * Creates a new {@code ChainMap} from the given {@code Map}s.
	 *
	 * @param maps the input {@code Map}s.
	 *
	 * @throws NullPointerException if {@code maps} is {@code null}.
	 * @throws IllegalArgumentException if {@code maps} is empty.
	 */
	public ChainMap(List<Map<K, V>> maps)
	{
		Parameters.checkCondition(!maps.isEmpty());
		this.maps = ImmutableList.copyOf(maps);
	}

	@Override
	public int size()
	{
		return keySet().size();
	}

	@Override
	public boolean isEmpty()
	{
		for (Map<K, V> map : maps) {
			if (!map.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean containsKey(Object key)
	{
		for (Map<K, V> map : maps) {
			if (map.containsKey(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value)
	{
		for (Map<K, V> map : maps) {
			if (map.containsValue(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public V get(Object key)
	{
		for (Map<K, V> map : maps) {
			V value = map.get(key);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	@Override
	public V put(K key, V value)
	{
		return maps.get(0).put(key, value);
	}

	@Override
	public V remove(Object key)
	{
		V value = null;
		for (Map<K, V> map : maps) {
			V v = map.remove(key);
			value = Objects.firstNonNull(value, v);
		}
		return value;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m)
	{
		maps.get(0).putAll(m);
	}

	@Override
	public void clear()
	{
		for (Map<K, V> map : maps) {
			map.clear();
		}
	}

	@Override
	public Set<K> keySet()
	{
		ImmutableSet.Builder<K> keys = new ImmutableSet.Builder<K>();
		for (Map<K, V> map : maps) {
			keys.add(map.keySet());
		}
		return keys.build();
	}

	@Override
	public Collection<V> values()
	{
		ImmutableList.Builder<V> values = new ImmutableList.Builder<V>();
		for (K key : keySet()) {
			values.add(get(key));
		}
		return values.build();
	}

	@Override
	public Set<Entry<K, V>> entrySet()
	{
		ImmutableSet.Builder<Entry<K, V>> entries =
			new ImmutableSet.Builder<Entry<K, V>>();
		for (K key : keySet()) {
			entries.add(new SimpleEntry<K, V>(key, get(key)));
		}
		return entries.build();
	}
}
