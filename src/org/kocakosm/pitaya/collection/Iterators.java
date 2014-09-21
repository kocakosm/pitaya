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

package org.kocakosm.pitaya.collection;

import org.kocakosm.pitaya.util.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Contains static utility methods that operate on or return {@link Iterator}s.
 *
 * @author Osman KOCAK
 */
public final class Iterators
{
	/** The empty {@link Iterator}. */
	public static final Iterator EMPTY_ITERATOR = new EmptyIterator();

	/**
	 * Returns the empty {@link Iterator} for a particular type (type-safe).
	 * Note that unlike this method, the like-named field does not provide
	 * type safety.
	 *
	 * @param <E> the type of the {@link Iterator}'s elements.
	 *
	 * @return the empty {@link Iterator}.
	 */
	public static <E> Iterator<E> emptyIterator()
	{
		return (Iterator<E>) EMPTY_ITERATOR;
	}

	/**
	 * Concatenates the given {@link Iterator}s into a single one. The
	 * source {@link Iterator}s aren't polled until necessary. The returned
	 * {@link Iterator} supports {@link Iterator#remove()} if the
	 * corresponding input {@link Iterator} supports it.
	 *
	 * @param <E> the type of the returned {@link Iterator}'s elements.
	 * @param iterators the {@link Iterator}s to concatenate.
	 *
	 * @return the concatenated {@link Iterator}.
	 *
	 * @throws NullPointerException if {@code iterators} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <E> Iterator<E> concat(Iterator<? extends E>... iterators)
	{
		return concat(Arrays.asList(iterators).iterator());
	}

	/**
	 * Concatenates the given {@link Iterator}s into a single one. The
	 * source {@link Iterator}s aren't polled until necessary. The returned
	 * {@link Iterator} supports {@link Iterator#remove()} if the
	 * corresponding input {@link Iterator} supports it.
	 *
	 * @param <E> the type of the returned {@link Iterator}'s elements.
	 * @param iterators the {@link Iterator}s to concatenate.
	 *
	 * @return the concatenated {@link Iterator}.
	 *
	 * @throws NullPointerException if {@code iterators} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <E> Iterator<E> concat(Iterator<? extends Iterator<? extends E>> iterators)
	{
		return new ConcatIterator<E>(iterators);
	}

	/**
	 * Returns an {@link Iterator} that cycles indefinitely over the
	 * elements returned by the given source {@link Iterator}. The source
	 * {@link Iterator} isn't polled until necessary. The returned
	 * {@link Iterator} supports {@link Iterator#remove()}. Use with
	 * caution.
	 *
	 * @param <E> the type of the returned {@link Iterator}'s elements.
	 * @param iterator the source {@link Iterator}.
	 *
	 * @return the cyclic {@link Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 */
	public static <E> Iterator<E> cycle(Iterator<? extends E> iterator)
	{
		return new LazyCyclicIteror<E>(iterator);
	}

	/**
	 * Returns an {@link Iterator} that cycles indefinitely over the given
	 * elements. The returned {@link Iterator} supports
	 * {@link Iterator#remove()}. Use with caution.
	 *
	 * @param <E> the type of the returned {@link Iterator}'s elements.
	 * @param elements the elements to cycle over.
	 *
	 * @return the cyclic {@link Iterator}.
	 *
	 * @throws NullPointerException if {@code elements} is {@code null}.
	 */
	public static <E> Iterator<E> cycle(E... elements)
	{
		return new CyclicIteror<E>(Arrays.asList(elements));
	}

	/**
	 * Creates an {@link Iterator} returning only the first {@code limit}
	 * elements of the given {@link Iterator}. The source {@link Iterator}
	 * isn't polled until necessary. The returned {@link Iterator} supports
	 * {@link Iterator#remove()} if the source {@link Iterator} supports it.
	 *
	 * @param <E> the type of the returned {@link Iterator}'s elements.
	 * @param iterator the {@link Iterator} to limit.
	 * @param limit the number of elements in the returned {@link Iterator}.
	 *
	 * @return the limited {@link Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 * @throws IllegalArgumentException if ({@code limit} is negative.
	 */
	public static <E> Iterator<E> limit(Iterator<? extends E> iterator,
		int limit)
	{
		return new LimitIteror<E>(iterator, limit);
	}

	/**
	 * Skips {@code n} elements from the given {@link Iterator}.
	 *
	 * @param iterator the {@link Iterator}.
	 * @param n the number of items to skip.
	 *
	 * @return the actual number of elements that has been skipped.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 * @throws IllegalArgumentException if ({@code n} is negative.
	 */
	public static int skip(Iterator<?> iterator, int n)
	{
		Parameters.checkCondition(n >= 0);
		int skipped = 0;
		while (skipped < n && iterator.hasNext()) {
			iterator.next();
			skipped++;
		}
		return skipped;
	}

	/**
	 * Returns a {@link List} containing all the given {@link Iterator}'s
	 * elements. This method consumes entirely the {@link Iterator} (after
	 * this method call, its {@link Iterator#hasNext()} method will return
	 * {@code false}).
	 *
	 * @param <E> the type of the returned {@link List}'s elements.
	 * @param iterator the {@link Iterator} to consume.
	 *
	 * @return the {@link List} created from the given {@link Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 */
	public static <E> List<E> toList(Iterator<? extends E> iterator)
	{
		List<E> list = new ArrayList<E>();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}

	/**
	 * Returns a {@link Set} containing all the given {@link Iterator}'s
	 * elements. This method consumes entirely the {@link Iterator} (after
	 * this method call, its {@link Iterator#hasNext()} method will return
	 * {@code false}).
	 *
	 * @param <E> the type of the returned {@link Set}'s elements.
	 * @param iterator the {@link Iterator} to consume.
	 *
	 * @return the {@link Set} created from the given {@link Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 */
	public static <E> Set<E> toSet(Iterator<? extends E> iterator)
	{
		Set<E> set = new HashSet<E>();
		while (iterator.hasNext()) {
			set.add(iterator.next());
		}
		return set;
	}

	/**
	 * Returns a {@link Bag} containing all the given {@link Iterator}'s
	 * elements. This method consumes entirely the {@link Iterator} (after
	 * this method call, its {@link Iterator#hasNext()} method will return
	 * {@code false}).
	 *
	 * @param <E> the type of the returned {@link Bag}'s elements.
	 * @param iterator the {@link Iterator} to consume.
	 *
	 * @return the {@link Bag} created from the given {@link Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 */
	public static <E> Bag<E> toBag(Iterator<? extends E> iterator)
	{
		Bag<E> bag = new HashBag<E>();
		while (iterator.hasNext()) {
			bag.add(iterator.next());
		}
		return bag;
	}

	/**
	 * Returns a {@link String} representation of the given
	 * {@link Iterator}, with the format [e1, e2, ..., en]. This method
	 * consumes entirely the {@link Iterator} (after this method call, its
	 * {@link Iterator#hasNext()} method will return {@code false}).
	 *
	 * @param iterator the {@link Iterator}.
	 *
	 * @return the {@link String} created from the given {@link Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 */
	public static String toString(Iterator<?> iterator)
	{
		return toList(iterator).toString();
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

		ConcatIterator(Iterator<? extends Iterator<? extends E>> iterators)
		{
			this.iterators = new ArrayList<Iterator<? extends E>>();
			while (iterators.hasNext()) {
				Iterator<? extends E> iterator = iterators.next();
				if (iterator.hasNext()) {
					this.iterators.add(iterator);
				}
			}
		}

		@Override
		public boolean hasNext()
		{
			return (!finished() && current().hasNext())
				|| (nextIterator() && hasNext());
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

	private static final class LazyCyclicIteror<E> implements Iterator<E>
	{
		private final Iterator<? extends E> iterator;
		private final List<E> elements;
		private int index = -1;

		LazyCyclicIteror(Iterator<? extends E> iterator)
		{
			Parameters.checkNotNull(iterator);
			this.elements = new ArrayList<E>();
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext()
		{
			return iterator.hasNext() || !elements.isEmpty();
		}

		@Override
		public E next()
		{
			if (iterator.hasNext()) {
				E element = iterator.next();
				elements.add(element);
				index++;
				return element;
			} else if (!elements.isEmpty()) {
				if (++index >= elements.size()) {
					index = 0;
				}
				return elements.get(index);
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove()
		{
			if (index < 0) {
				throw new IllegalStateException();
			}
			elements.remove(index--);
		}
	}

	private static final class CyclicIteror<E> implements Iterator<E>
	{
		private final List<E> elements;
		private int index = -1;

		CyclicIteror(List<? extends E> elements)
		{
			this.elements = new ArrayList<E>(elements);
		}

		@Override
		public boolean hasNext()
		{
			return !elements.isEmpty();
		}

		@Override
		public E next()
		{
			if (hasNext()) {
				return elements.get(nextIndex());
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove()
		{
			if (index < 0) {
				throw new IllegalStateException();
			}
			elements.remove(index--);
		}

		private int nextIndex()
		{
			if (++index >= elements.size()) {
				index = 0;
			}
			return index;
		}
	}

	private static final class LimitIteror<E> implements Iterator<E>
	{
		private final Iterator<? extends E> iterator;
		private final int limit;
		private int index;

		LimitIteror(Iterator<? extends E> iterator, int limit)
		{
			Parameters.checkNotNull(iterator);
			Parameters.checkCondition(limit >= 0);
			this.iterator = iterator;
			this.limit = limit;
		}

		@Override
		public boolean hasNext()
		{
			return iterator.hasNext() && index < limit;
		}

		@Override
		public E next()
		{
			if (hasNext()) {
				index++;
				return iterator.next();
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove()
		{
			iterator.remove();
		}
	}

	private Iterators()
	{
		/* ... */
	}
}
