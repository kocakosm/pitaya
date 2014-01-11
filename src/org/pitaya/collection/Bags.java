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

import org.pitaya.util.Parameters;

import java.util.Collection;
import java.util.Iterator;

/**
 * Contains static utility methods that operate on or return {@link Bag}s.
 *
 * @author Osman KOCAK
 */
public final class Bags
{
	/** The empty bag. */
	public static final Bag EMPTY_BAG = new EmptyBag();

	/**
	 * Returns the empty {@link Bag} for a particular type (type-safe). Note
	 * that unlike this method, the like-named field does not provide type
	 * safety.
	 *
	 * @param <E> the type of the bag's elements.
	 *
	 * @return the empty {@link Bag}.
	 */
	public static <E> Bag<E> emptyBag()
	{
		return (Bag<E>) EMPTY_BAG;
	}

	private static final class EmptyBag<E> extends AbstractBag<E>
	{
		@Override
		public int count(E e)
		{
			return 0;
		}

		@Override
		public int size()
		{
			return 0;
		}

		@Override
		public boolean isEmpty()
		{
			return true;
		}

		@Override
		public boolean contains(Object o)
		{
			return false;
		}

		@Override
		public Iterator<E> iterator()
		{
			return Iterators.emptyIterator();
		}

		@Override
		public Object[] toArray()
		{
			return new Object[0];
		}

		@Override
		public <T> T[] toArray(T[] a)
		{
			if (a.length > 0) {
				a[0] = null;
			}
			return a;
		}

		@Override
		public boolean add(E e)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsAll(Collection<?> c)
		{
			Parameters.checkNotNull(c);
			return false;
		}

		@Override
		public boolean addAll(Collection<? extends E> c)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(Collection<?> c)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(Collection<?> c)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear()
		{
			throw new UnsupportedOperationException();
		}
	}

	private Bags()
	{
		/* ... */
	}
}
