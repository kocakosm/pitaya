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
import java.util.Iterator;
import java.util.List;

/**
 * {@link Bag} implementation based on {@link ArrayList}. This implementation
 * provides a predictable iteration order which is the order in which elements
 * were inserted into the bag (insertion-order). Also, this implementation
 * accepts {@code null} elements. Instances of this class are not thread-safe.
 *
 * @param <E> the type of the elements in the bag.
 *
 * @author Osman KOCAK
 */
public final class ArrayBag<E> extends AbstractBag<E> implements Serializable
{
	private static final long serialVersionUID = 2071675609121734096L;

	private final List<E> entries;

	/** Creates a new empty {@code ArrayBag}. */
	public ArrayBag()
	{
		this(10);
	}

	/**
	 * Creates a new empty {@code ArrayBag} having the given initial capacity.
	 *
	 * @param initialCapacity the bag's initial capacity.
	 *
	 * @throws IllegalArgumentException if {@code initialCapacity < 0}.
	 */
	public ArrayBag(int initialCapacity)
	{
		Parameters.checkCondition(initialCapacity >= 0);
		this.entries = new ArrayList<E>(initialCapacity);
	}

	/**
	 * Creates a new {@code ArrayBag} using the elements contained in the
	 * given {@code Collection}.
	 *
	 * @param c the collection to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public ArrayBag(Collection<? extends E> c)
	{
		this(c.size());
		addAll(c);
	}

	/**
	 * Creates a new {@code ArrayBag} using the elements contained in the
	 * given {@code Iterable}.
	 *
	 * @param i the iterable to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public ArrayBag(Iterable<? extends E> i)
	{
		this();
		for (E e : i) {
			add(e);
		}
	}

	/**
	 * Creates a new {@code ArrayBag} using the elements contained in the
	 * given {@code Iterator}.
	 *
	 * @param i the iterator to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public ArrayBag(Iterator<? extends E> i)
	{
		this();
		while (i.hasNext()) {
			add(i.next());
		}
	}

	/**
	 * Creates a new {@code ArrayBag} using the elements contained in the
	 * given array.
	 *
	 * @param elements the elements to use to populate the created bag.
	 *
	 * @throws NullPointerException if {@code elements} is {@code null}.
	 */
	public ArrayBag(E... elements)
	{
		this(Arrays.asList(elements));
	}

	@Override
	public Iterator<E> iterator()
	{
		return entries.iterator();
	}

	@Override
	public int size()
	{
		return entries.size();
	}

	@Override
	public void clear()
	{
		entries.clear();
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return entries.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return entries.removeAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		return entries.addAll(c);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return entries.containsAll(c);
	}

	@Override
	public boolean remove(Object o)
	{
		return entries.remove(o);
	}

	@Override
	public boolean add(E e)
	{
		return entries.add(e);
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return entries.toArray(a);
	}

	@Override
	public Object[] toArray()
	{
		return entries.toArray();
	}

	@Override
	public boolean contains(Object o)
	{
		return entries.contains(o);
	}

	@Override
	public boolean isEmpty()
	{
		return entries.isEmpty();
	}
}
