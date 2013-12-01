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
import java.util.NoSuchElementException;

/**
 * Contains static utility methods that operate on or return {@link Iterator}s.
 *
 * @author Osman KOCAK
 */
public final class Iterators
{
	/** The empty iterator. */
	public static final Iterator EMPTY_ITERATOR = new EmptyIterator();

	/**
	 * Returns the empty iterator for a particular type (type-safe). Note
	 * that unlike this method, the like-named field does not provide type
	 * safety.
	 *
	 * @param <E> the type of the iterator's elements.
	 *
	 * @return the empty iterator.
	 */
	public static <E> Iterator<E> emptyIterator()
	{
		return (Iterator<E>) EMPTY_ITERATOR;
	}

	/**
	 * Concatenates the given {@link Iterator}s into a single one. The 
	 * source iterators aren't polled until necessary. The returned iterator
	 * supports {@link Iterator#remove()} if the corresponding input 
	 * iterator supports it.
	 * 
	 * @param <E> the type of the iterators' elements.
	 * @param iterators the iterators to concatenate.
	 * 
	 * @return the concatenated iterator.
	 * 
	 * @throws NullPointerException if {@code iterators} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <E> Iterator<E> concat(Iterator<? extends E>... iterators)
	{
		return concat(Arrays.asList(iterators));
	}

	/**
	 * Concatenates the given {@link Iterator}s into a single one. The 
	 * source iterators aren't polled until necessary. The returned iterator
	 * supports {@link Iterator#remove()} if the corresponding input 
	 * iterator supports it.
	 * 
	 * @param <E> the type of the iterators' elements.
	 * @param iterators the iterators to concatenate.
	 * 
	 * @return the concatenated iterator.
	 * 
	 * @throws NullPointerException if {@code iterators} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <E> Iterator<E> concat(Iterable<? extends Iterator<? extends E>> iterators)
	{
		return new ConcatIterator<E>(iterators);
	}

	private static final class EmptyIterator<E> implements Iterator<E>
	{
		@Override
		public boolean hasNext()
		{
			return false;
		}

		@Override
		public E next()
		{
			throw new NoSuchElementException();
		}

		@Override
		public void remove()
		{
			throw new IllegalStateException();
		}
	}

	private static final class ConcatIterator<E> implements Iterator<E>
	{
		private final List<Iterator<? extends E>> iterators;
		private int index;

		ConcatIterator(Iterable<? extends Iterator<? extends E>> iterators)
		{
			this.iterators = new ArrayList<Iterator<? extends E>>();
			for (Iterator<? extends E> iterator : iterators) {
				if (iterator.hasNext()) {
					this.iterators.add(iterator);
				}
			}
		}

		@Override
		public boolean hasNext()
		{
			return current().hasNext() || nextIterator();
		}

		@Override
		public E next()
		{
			if (hasNext()) {
				return current().next();
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove()
		{
			current().remove();
		}

		private Iterator<? extends E> current()
		{
			return iterators.get(index);
		}

		private boolean finished()
		{
			return index >= iterators.size();
		}

		private boolean nextIterator()
		{
			index++;
			return !finished();
		}
	}

	private Iterators()
	{
		/* ... */
	}
}
