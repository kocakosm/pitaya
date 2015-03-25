/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2015 Osman KOCAK <kocakosm@gmail.com>                   *
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

package org.kocakosm.pitaya.collection;

import org.kocakosm.pitaya.util.Parameters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * {@link Bag} implementation based on {@link HashMap}. This implementation
 * accepts {@code null} elements. Instances of this class are not thread-safe.
 *
 * @param <E> the type of the elements in the bag.
 *
 * @author Osman KOCAK
 */
public final class HashBag<E> extends AbstractBag<E> implements Serializable
{
	private static final long serialVersionUID = 3651678489121314654L;

	private final Map<E, List<E>> entries;

	/** Creates a new empty {@code HashBag}. */
	public HashBag()
	{
		this(10);
	}

	/**
	 * Creates a new empty {@code HashBag} having the given initial capacity.
	 *
	 * @param initialCapacity the bag's initial capacity.
	 *
	 * @throws IllegalArgumentException if {@code initialCapacity < 0}.
	 */
	public HashBag(int initialCapacity)
	{
		Parameters.checkCondition(initialCapacity >= 0);
		this.entries = new HashMap<E, List<E>>(initialCapacity);
	}

	/**
	 * Creates a new {@code HashBag} using the elements contained in the
	 * given {@code Collection}.
	 *
	 * @param c the collection to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public HashBag(Collection<? extends E> c)
	{
		this(c.size());
		addAll(c);
	}

	/**
	 * Creates a new {@code HashBag} using the elements contained in the
	 * given {@code Iterable}.
	 *
	 * @param i the iterable to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public HashBag(Iterable<? extends E> i)
	{
		this();
		for (E e : i) {
			add(e);
		}
	}

	/**
	 * Creates a new {@code HashBag} using the elements contained in the
	 * given {@code Iterator}.
	 *
	 * @param i the iterator to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public HashBag(Iterator<? extends E> i)
	{
		this();
		while (i.hasNext()) {
			add(i.next());
		}
	}

	/**
	 * Creates a new {@code HashBag} using the elements contained in the
	 * given array.
	 *
	 * @param elements the elements to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code elements} is {@code null}.
	 */
	public HashBag(E... elements)
	{
		this(Arrays.asList(elements));
	}

	@Override
	public boolean add(E e)
	{
		List<E> entry = entries.get(e);
		if (entry == null) {
			entry = new ArrayList<E>();
			entries.put(e, entry);
		}
		return entry.add(e);
	}

	@Override
	public void clear()
	{
		entries.clear();
	}

	@Override
	public boolean contains(Object o)
	{
		List<E> entry = entries.get(o);
		return entry == null ? false : !entry.isEmpty();
	}

	@Override
	public int count(E e)
	{
		List<E> entry = entries.get(e);
		return entry == null ? 0 : entry.size();
	}

	@Override
	public Iterator<E> iterator()
	{
		return Iterables.concat(entries.values()).iterator();
	}

	@Override
	public boolean remove(Object o)
	{
		List<E> entry = entries.get(o);
		return entry == null ? false : entry.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		boolean removed = false;
		for (Object o : c) {
			removed |= entries.remove(o) != null;
		}
		return removed;
	}

	@Override
	public int size()
	{
		int size = 0;
		for (List<E> value : entries.values()) {
			size += value.size();
		}
		return size;
	}
}
