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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Static utility methods that operate on or return {@link Iterable}s.
 *
 * @author Osman KOCAK
 */
public final class Iterables
{
	/** The empty {@code Iterable}. */
	public static final Iterable EMPTY_ITERABLE = new EmptyIterable();

	/**
	 * Returns the empty {@code Iterable} for a particular type (type-safe).
	 * Note that unlike this method, the like-named static field does not
	 * provide type safety.
	 *
	 * @param <T> the type of the {@code Iterable}'s elements.
	 *
	 * @return the empty {@code Iterable}.
	 */
	public static <T> Iterable<T> emptyIterable()
	{
		return (Iterable<T>) EMPTY_ITERABLE;
	}

	/**
	 * Concatenates the given {@code Iterable}s into a single one. Note that
	 * the returned {@code Iterable} is only a view, that is, any subsequent
	 * update on any of the input {@code Iterable}s will affect the returned
	 * view. The input {@code Iterable}s will not be polled until necessary.
	 * The returned view's {@code Iterator} supports {@link Iterator#remove()}
	 * when the corresponding input {@code Iterator} supports it.
	 *
	 * @param <T> the type of the returned {@code Iterable}s' elements.
	 * @param iterables the {@code Iterable}s to concatenate.
	 *
	 * @return the concatenated {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterables} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <T> Iterable<T> concat(Iterable<? extends T>... iterables)
	{
		return concat(Arrays.asList(iterables));
	}

	/**
	 * Concatenates the given {@code Iterable}s into a single one. Note that
	 * the returned {@code Iterable} is only a view, that is, any subsequent
	 * update on any of the input {@code Iterable}s will affect the returned
	 * view. The input {@code Iterable}s will not be polled until necessary.
	 * The returned view's {@code Iterator} supports {@link Iterator#remove()}
	 * when the corresponding input {@code Iterator} supports it.
	 *
	 * @param <T> the type of the returned {@code Iterable}s' elements.
	 * @param iterables the {@code Iterable}s to concatenate.
	 *
	 * @return the concatenated {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterables} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> iterables)
	{
		return new ConcatIterable<T>(iterables);
	}

	/**
	 * Returns a cyclic {@code Iterable} that cycles indefinitely over the
	 * given {@code Iterable}'s elements. The returned {@code Iterable}'s
	 * {@code Iterator} supports {@code Iterator#remove()}.
	 *
	 * @param <T> the type of the returned {@code Iterable}'s elements.
	 * @param iterable the {@code Iterable} containing the elements to
	 *	cycle over.
	 *
	 * @return the cyclic {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> Iterable<T> cycle(Iterable<? extends T> iterable)
	{
		return new CyclicIterable<T>(toList(iterable));
	}

	/**
	 * Returns an {@code Iterable} that cycles indefinitely over the given
	 * elements. The returned {@code Iterable}'s {@code Iterator} supports
	 * {@link Iterator#remove()}.
	 *
	 * @param <T> the type of the returned {@code Iterable}'s elements.
	 * @param elements the elements to cycle over.
	 *
	 * @return the cyclic {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> Iterable<T> cycle(T... elements)
	{
		return new CyclicIterable<T>(new ArrayList<T>(Arrays.asList(elements)));
	}

	/**
	 * Returns whether the given {@code Iterable}s contain equal elements in
	 * the same order.
	 *
	 * @param i1 the first {@code Iterable}.
	 * @param i2 the second {@code Iterable}.
	 *
	 * @return whether the given {@code Iterables} have the same content.
	 */
	public static boolean equal(Iterable<?> i1, Iterable<?> i2)
	{
		if (i1 == i2) {
			return true;
		}
		if (i1 == null || i2 == null) {
			return false;
		}
		return Iterators.equal(i1.iterator(), i2.iterator());
	}

	/**
	 * Returns an {@code Iterable} view of the given {@code Iterable} that
	 * will only return its first {@code limit} items. The returned view's
	 * {@code Iterator}s support {@link Iterator#remove()} if the original
	 * {@code Iterator} does.
	 *
	 * @param <T> the type of the returned {@code Iterable}'s elements.
	 * @param iterable the {@code Iterable} to limit.
	 * @param limit the number of elements in the returned {@code Iterable}.
	 *
	 * @return the limited {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 * @throws IllegalArgumentException if ({@code limit} is negative.
	 */
	public static <T> Iterable<T> limit(Iterable<? extends T> iterable, int limit)
	{
		return new LimitIterable<T>(iterable, limit);
	}

	/**
	 * Returns an {@code Iterable} view of the given {@code Iterable} that
	 * skips its first {@code n} elements. The returned {@code Iterable}'s
	 * {@code Iterator} supports {@link Iterator#remove()} if the original
	 * {@code Iterator} does.
	 *
	 * @param <T> the type of the {@code Iterable}'s elements.
	 * @param iterable the input {@code Iterable}.
	 * @param n the number of items to skip.
	 *
	 * @return the skipping {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 * @throws IllegalArgumentException if ({@code n} is negative.
	 */
	public static <T> Iterable<T> skip(Iterable<T> iterable, int n)
	{
		return new SkipIterable<T>(iterable, n);
	}

	/**
	 * Returns a {@code List} containing all the given {@code Iterable}'s
	 * elements.
	 *
	 * @param <T> the type of the returned {@code List}'s elements.
	 * @param iterable the source {@code Iterable}.
	 *
	 * @return the {@code List} created from the given {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> List<T> toList(Iterable<? extends T> iterable)
	{
		List<T> list = new ArrayList<T>();
		for (T e : iterable) {
			list.add(e);
		}
		return list;
	}

	/**
	 * Returns a {@code Set} containing all the given {@code Iterable}'s
	 * elements.
	 *
	 * @param <T> the type of the returned {@code Set}'s elements.
	 * @param iterable the source {@code Iterable}.
	 *
	 * @return the {@code Set} created from the given {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> Set<T> toSet(Iterable<? extends T> iterable)
	{
		Set<T> set = new HashSet<T>();
		for (T e : iterable) {
			set.add(e);
		}
		return set;
	}

	/**
	 * Returns a {@code Bag} containing all the given {@code Iterable}'s
	 * elements.
	 *
	 * @param <T> the type of the returned {@code Bag}'s elements.
	 * @param iterable the source {@code Iterable}.
	 *
	 * @return the {@code Bag} created from the given {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static <T> Bag<T> toBag(Iterable<? extends T> iterable)
	{
		return new HashBag<T>(iterable);
	}

	/**
	 * Returns a {@code String} representation of the given {@code Iterable}
	 * using the "[e1, e2, ..., en]" format.
	 *
	 * @param iterable the {@code Iterable}.
	 *
	 * @return the {@code String} created from the given {@code Iterable}.
	 *
	 * @throws NullPointerException if {@code iterable} is {@code null}.
	 */
	public static String toString(Iterable<?> iterable)
	{
		return toList(iterable).toString();
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
		private final List<Iterable<? extends T>> iterables;

		ConcatIterable(Iterable<? extends Iterable<? extends T>> iterables)
		{
			this.iterables = new ArrayList<Iterable<? extends T>>();
			for (Iterable<? extends T> iterable : iterables) {
				Parameters.checkNotNull(iterable);
				this.iterables.add(iterable);
			}
		}

		@Override
		public Iterator<T> iterator()
		{
			List<Iterator<? extends T>> iterators = new ArrayList<Iterator<? extends T>>();
			for (Iterable<? extends T> iterable : iterables) {
				iterators.add(iterable.iterator());
			}
			return Iterators.concat(iterators.iterator());
		}
	}

	private static final class CyclicIterable<T> implements Iterable<T>
	{
		private final Iterable<? extends T> iterable;

		CyclicIterable(Iterable<? extends T> iterable)
		{
			Parameters.checkNotNull(iterable);
			this.iterable = iterable;
		}

		@Override
		public Iterator<T> iterator()
		{
			return Iterators.cycle(iterable.iterator());
		}
	}

	private static final class LimitIterable<T> implements Iterable<T>
	{
		private final int limit;
		private final Iterable<? extends T> iterable;

		LimitIterable(Iterable<? extends T> iterable, int limit)
		{
			Parameters.checkNotNull(iterable);
			Parameters.checkCondition(limit >= 0);
			this.iterable = iterable;
			this.limit = limit;
		}

		@Override
		public Iterator<T> iterator()
		{
			return Iterators.limit(iterable.iterator(), limit);
		}
	}

	private static final class SkipIterable<T> implements Iterable<T>
	{
		private final int n;
		private final Iterable<T> iterable;

		SkipIterable(Iterable<T> iterable, int n)
		{
			Parameters.checkNotNull(iterable);
			Parameters.checkCondition(n >= 0);
			this.iterable = iterable;
			this.n = n;
		}

		@Override
		public Iterator<T> iterator()
		{
			Iterator<T> iterator = iterable.iterator();
			Iterators.skip(iterator, n);
			return iterator;
		}
	}

	private Iterables()
	{
		/* ... */
	}
}
