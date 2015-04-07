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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A thread-safe variant of {@link HashBag} based on {@link ConcurrentHashMap}.
 * This class does not accept {@code null} elements.
 *
 * @param <E> the type of the elements in the bag.
 *
 * @author Osman KOCAK
 */
public final class ConcurrentHashBag<E> extends AbstractBag<E> implements Serializable
{
	private static final long serialVersionUID = 7336345430492192446L;

	private final ConcurrentMap<E, CopyOnWriteArrayList<E>> entries;

	/** Creates a new empty {@code ConcurrentHashBag}. */
	public ConcurrentHashBag()
	{
		this(10);
	}

	/**
	 * Creates a new empty {@code ConcurrentHashBag} having the given
	 * initial capacity.
	 *
	 * @param initialCapacity the bag's initial capacity.
	 *
	 * @throws IllegalArgumentException if {@code initialCapacity < 0}.
	 */
	public ConcurrentHashBag(int initialCapacity)
	{
		Parameters.checkCondition(initialCapacity >= 0);
		this.entries = new ConcurrentHashMap<E, CopyOnWriteArrayList<E>>(initialCapacity);
	}

	/**
	 * Creates a new {@code ConcurrentHashBag} using the elements contained
	 * in the given {@code Collection}.
	 *
	 * @param c the collection to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code c} is {@code null} or if it
	 *	contains a {@code null} reference.
	 */
	public ConcurrentHashBag(Collection<? extends E> c)
	{
		this(c.size());
		addAll(c);
	}

	/**
	 * Creates a new {@code ConcurrentHashBag} using the elements contained
	 * in the given {@code Iterable}.
	 *
	 * @param i the iterable to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code i} is {@code null} or if it
	 *	contains a {@code null} reference.
	 */
	public ConcurrentHashBag(Iterable<? extends E> i)
	{
		this();
		for (E e : i) {
			add(e);
		}
	}

	/**
	 * Creates a new {@code ConcurrentHashBag} using the elements contained
	 * in the given {@code Iterator}.
	 *
	 * @param i the iterator to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code i} is {@code null} or if it
	 *	contains a {@code null} reference.
	 */
	public ConcurrentHashBag(Iterator<? extends E> i)
	{
		this();
		while (i.hasNext()) {
			add(i.next());
		}
	}

	/**
	 * Creates a new {@code ConcurrentHashBag} using the elements contained
	 * in the given array.
	 *
	 * @param elements the elements to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code elements} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public ConcurrentHashBag(E... elements)
	{
		this(Arrays.asList(elements));
	}

	@Override
	public boolean add(E e)
	{
		return getEntry(e).add(e);
	}

	/**
	 * Adds all of the elements in the specified collection that are not
	 * already contained in this bag.
	 *
	 * @param c the collection containing elements to be added to this bag.
	 *
	 * @return the number of elements added.
	 *
	 * @throws NullPointerException if the given collection is {@code null}
	 *	or if it contains a {@code null} reference.
	 */
	public int addAllAbsent(Collection<? extends E> c)
	{
		int count = 0;
		for (E e : c) {
			if (addIfAbsent(e)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Adds the given element to this bag if not already present.
	 *
	 * @param e the element to be added to this bag, if absent.
	 *
	 * @return {@code true} if the element was added.
	 *
	 * @throws NullPointerException if {@code e} is {@code null}.
	 */
	public boolean addIfAbsent(E e)
	{
		return getEntry(e).addIfAbsent(e);
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
	public boolean retainAll(Collection<?> c)
	{
		boolean updated = false;
		for (List<E> entry : entries.values()) {
			updated |= entry.retainAll(c);
		}
		return updated;
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

	private CopyOnWriteArrayList<E> getEntry(E e)
	{
		entries.putIfAbsent(e, new CopyOnWriteArrayList<E>());
		return entries.get(e);
	}
}
