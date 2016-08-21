/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2016 Osman KOCAK <kocakosm@gmail.com>                   *
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
import org.kocakosm.pitaya.util.XObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Static utility methods that operate on or return {@link Iterator}s.
 *
 * @author Osman KOCAK
 */
public final class Iterators
{
	/** The empty {@code Iterator}. */
	public static final Iterator EMPTY_ITERATOR = new EmptyIterator();

	/**
	 * Returns the empty {@code Iterator} for a particular type (type-safe).
	 * Note that unlike this method, the like-named static field does not
	 * provide type safety.
	 *
	 * @param <E> the type of the {@code Iterator}'s elements.
	 *
	 * @return the empty {@code Iterator}.
	 */
	public static <E> Iterator<E> emptyIterator()
	{
		return (Iterator<E>) EMPTY_ITERATOR;
	}

	/**
	 * Concatenates all the given {@code Iterator}s into a single one. The
	 * source {@code Iterator}s aren't polled until necessary. The returned
	 * {@code Iterator} supports {@link Iterator#remove()} when the
	 * corresponding input {@code Iterator} supports it.
	 *
	 * @param <E> the type of the returned {@code Iterator}'s elements.
	 * @param iterators the {@code Iterator}s to concatenate.
	 *
	 * @return the concatenated {@code Iterator}.
	 *
	 * @throws NullPointerException if {@code iterators} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <E> Iterator<E> concat(Iterator<? extends E>... iterators)
	{
		return concat(Arrays.asList(iterators).iterator());
	}

	/**
	 * Concatenates all the given {@code Iterator}s into a single one. The
	 * source {@code Iterator}s aren't polled until necessary. The returned
	 * {@code Iterator} supports {@link Iterator#remove()} when the
	 * corresponding input {@code Iterator} supports it.
	 *
	 * @param <E> the type of the returned {@code Iterator}'s elements.
	 * @param iterators the {@code Iterator}s to concatenate.
	 *
	 * @return the concatenated {@code Iterator}.
	 *
	 * @throws NullPointerException if {@code iterators} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <E> Iterator<E> concat(Iterator<? extends Iterator<? extends E>> iterators)
	{
		return new ConcatIterator<E>(iterators);
	}

	/**
	 * Returns an {@code Iterator} that cycles indefinitely over the content
	 * of the given source {@code Iterator}. The source {@code Iterator} is
	 * not polled until necessary. The returned {@code Iterator} supports
	 * {@link Iterator#remove()}.
	 *
	 * @param <E> the type of the returned {@code Iterator}'s elements.
	 * @param iterator the source {@code Iterator}.
	 *
	 * @return the cyclic {@code Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 */
	public static <E> Iterator<E> cycle(Iterator<? extends E> iterator)
	{
		return new LazyCyclicIterator<E>(iterator);
	}

	/**
	 * Returns an {@code Iterator} that cycles indefinitely over the given
	 * elements. The returned {@code Iterator} supports {@link Iterator#remove()}.
	 *
	 * @param <E> the type of the returned {@code Iterator}'s elements.
	 * @param elements the elements to cycle over.
	 *
	 * @return the cyclic {@code Iterator}.
	 *
	 * @throws NullPointerException if {@code elements} is {@code null}.
	 */
	public static <E> Iterator<E> cycle(E... elements)
	{
		return new CyclicIterator<E>(Arrays.asList(elements));
	}

	/**
	 * Returns whether the given {@code Iterator}s contain equal elements in
	 * the same order. Note that this method consumes entirely the input
	 * {@code Iterator}s.
	 *
	 * @param i1 the first {@code Iterator}.
	 * @param i2 the second {@code Iterator}.
	 *
	 * @return whether the given {@code Iterator} have the same content.
	 */
	public static boolean equal(Iterator<?> i1, Iterator<?> i2)
	{
		if (i1 == i2) {
			return true;
		}
		if (i1 == null || i2 == null) {
			return false;
		}
		while (i1.hasNext() && i2.hasNext()) {
			if (!XObjects.equal(i1.next(), i2.next())) {
				return false;
			}
		}
		return !(i1.hasNext() || i2.hasNext());
	}

	/**
	 * Creates an {@code Iterator} returning only the first {@code limit}
	 * elements of the given {@code Iterator}. The source {@code Iterator}
	 * is not polled until necessary. The returned {@code Iterator} supports
	 * {@link Iterator#remove()} if the source {@code Iterator} supports it.
	 *
	 * @param <E> the type of the returned {@code Iterator}'s elements.
	 * @param iterator the {@code Iterator} to limit.
	 * @param limit the number of elements in the returned {@code Iterator}.
	 *
	 * @return the limited {@code Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 * @throws IllegalArgumentException if ({@code limit} is negative.
	 */
	public static <E> Iterator<E> limit(Iterator<? extends E> iterator,
		int limit)
	{
		return new LimitIterator<E>(iterator, limit);
	}

	/**
	 * Skips {@code n} elements from the given {@code Iterator}.
	 *
	 * @param iterator the {@code Iterator}.
	 * @param n the number of elements to skip.
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
	 * Returns a {@code List} containing all the given {@code Iterator}'s
	 * elements. This method consumes entirely the input {@code Iterator}.
	 *
	 * @param <E> the type of the returned {@code List}'s elements.
	 * @param iterator the {@code Iterator} to consume.
	 *
	 * @return the {@code List} created from the given {@code Iterator}.
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
	 * Returns a {@code Set} containing all the given {@code Iterator}'s
	 * elements. The returned {@code Set} has the same iteration order as
	 * the given {@code Iterator}. This method consumes entirely the input
	 * {@code Iterator}.
	 *
	 * @param <E> the type of the returned {@code Set}'s elements.
	 * @param iterator the {@code Iterator} to consume.
	 *
	 * @return the {@code Set} created from the given {@code Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 */
	public static <E> Set<E> toSet(Iterator<? extends E> iterator)
	{
		Set<E> set = new LinkedHashSet<E>();
		while (iterator.hasNext()) {
			set.add(iterator.next());
		}
		return set;
	}

	/**
	 * Returns a {@code Bag} containing all the given {@code Iterator}'s
	 * elements. The returned {@code Bag} has the same iteration order as
	 * the given {@code Iterator}. This method consumes entirely the input
	 * {@code Iterator}.
	 *
	 * @param <E> the type of the returned {@code Bag}'s elements.
	 * @param iterator the {@code Iterator} to consume.
	 *
	 * @return the {@code Bag} created from the given {@code Iterator}.
	 *
	 * @throws NullPointerException if {@code iterator} is {@code null}.
	 */
	public static <E> Bag<E> toBag(Iterator<? extends E> iterator)
	{
		return new ArrayBag<E>(iterator);
	}

	/**
	 * Returns a {@code String} representation of the given {@code Iterator},
	 * using the "[e1, e2, ..., en]" format. This method consumes entirely
	 * the input {@code Iterator}.
	 *
	 * @param iterator the {@code Iterator}.
	 *
	 * @return the {@code String} created from the given {@code Iterator}.
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

	private static final class LazyCyclicIterator<E> implements Iterator<E>
	{
		private final Iterator<? extends E> iterator;
		private final List<E> elements;
		private int index = -1;

		LazyCyclicIterator(Iterator<? extends E> iterator)
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
			}
			if (!elements.isEmpty()) {
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

	private static final class CyclicIterator<E> implements Iterator<E>
	{
		private final List<E> elements;
		private int index = -1;

		CyclicIterator(List<? extends E> elements)
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

	private static final class LimitIterator<E> implements Iterator<E>
	{
		private final Iterator<? extends E> iterator;
		private final int limit;
		private int index;

		LimitIterator(Iterator<? extends E> iterator, int limit)
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
