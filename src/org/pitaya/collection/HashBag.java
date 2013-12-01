/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2013 Osman KOCAK <kocakosm@gmail.com>                   *
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

import org.pitaya.util.Iterables;
import org.pitaya.util.Iterators;
import org.pitaya.util.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * {@link Bag} implementation based on {@link HashMap}. This implementation 
 * accepts {@code null} elements.
 *
 * @param <E> the type of the elements in the bag.
 *
 * @author Osman KOCAK
 */
public final class HashBag<E> extends AbstractBag<E>
{
	private final Map<E, List<E>> entries;

	/** Creates a new empty {@code HashBag}. */
	public HashBag()
	{
		this(5);
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
	 * given {@link Collection}.
	 * 
	 * @param c the collection to use to populate the created bag.
	 * 
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public HashBag(Collection<? extends E> c)
	{
		this(c.size());
		addAll((Iterable<? extends E>) c);
	}

	/**
	 * Creates a new {@code HashBag} using the elements contained in the 
	 * given {@link Iterable}.
	 * 
	 * @param i the iterable to use to populate the created bag.
	 * 
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public HashBag(Iterable<? extends E> i)
	{
		this();
		addAll(i);
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
		return getEntry(e).add(e);
	}

	@Override
	public Iterator<E> iterator()
	{
		if (isEmpty()) {
			return Iterators.emptyIterator();
		}
		return Iterables.concat(entries.values()).iterator();
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

	private void addAll(Iterable<? extends E> c)
	{
		for (E e : c) {
			getEntry(e).add(e);
		}
	}

	private List<E> getEntry(E e)
	{
		List<E> entry = entries.get(e);
		if (entry == null) {
			entry = new ArrayList<E>();
			entries.put(e, entry);
		}
		return entry;
	}
}
