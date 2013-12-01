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

package org.pitaya.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Contains static utility methods that operate on or return {@link Iterable}s.
 *
 * @author Osman KOCAK
 */
public final class Iterables
{
	/** The empty iterable. */
	public static final Iterable EMPTY_ITERABLE = new EmptyIterable();

	/**
	 * Returns the empty iterable for a particular type (type-safe). Note
	 * that unlike this method, the like-named field does not provide type
	 * safety.
	 *
	 * @param <T> the type of the iterable's elements.
	 *
	 * @return the empty iterable.
	 */
	public static <T> Iterable<T> emptyIterable()
	{
		return (Iterable<T>) EMPTY_ITERABLE;
	}

	/**
	 * Concatenates the given {@link Iterable}s into a single one. The
	 * source iterables' iterators are not polled until necessary. The
	 * returned iterable's iterator supports {@link Iterator#remove()} if
	 * the corresponding input iterable's iterator supports it.
	 *
	 * @param <T> the type of the iterables' elements.
	 * @param iterables the iterables to concatenate.
	 *
	 * @return the concatenated iterable.
	 * 
	 * @throws NullPointerException if {@code iterables} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <T> Iterable<T> concat(Iterable<? extends T>... iterables)
	{
		return concat(Arrays.asList(iterables));
	}

	/**
	 * Concatenates the given {@link Iterable}s into a single one. The
	 * source iterables' iterators are not polled until necessary. The
	 * returned iterable's iterator supports {@link Iterator#remove()} if
	 * the corresponding input iterable's iterator supports it.
	 *
	 * @param <T> the type of the iterables' elements.
	 * @param iterables the iterables to concatenate.
	 *
	 * @return the concatenated iterable.
	 * 
	 * @throws NullPointerException if {@code iterables} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> iterables)
	{
		return new ConcatIterable<T>(iterables);
	}

	private static final class EmptyIterable<T> implements Iterable<T>
	{
		@Override
		public Iterator<T> iterator()
		{
			return Iterators.emptyIterator();
		}
	}

	private static final class ConcatIterable<T> implements Iterable<T>
	{
		private final Iterator<T> iterator;

		ConcatIterable(Iterable<? extends Iterable<? extends T>> iterables)
		{
			List<Iterator<? extends T>> iterators = new ArrayList<Iterator<? extends T>>();
			for (Iterable<? extends T> iterable : iterables) {
				iterators.add(iterable.iterator());
			}
			this.iterator = Iterators.concat(iterators);
		}

		@Override
		public Iterator<T> iterator()
		{
			return iterator;
		}
	}

	private Iterables()
	{
		/* ... */
	}
}
