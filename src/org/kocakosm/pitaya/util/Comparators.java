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

package org.kocakosm.pitaya.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Utilities for {@link Comparator}s.
 *
 * @author Osman KOCAK
 */
public final class Comparators
{
	/**
	 * Returns a {@code Comparator} that will call each {@code Comparator}
	 * in the given array until one of them returns a non-zero result (will
	 * return {@code 0} if they all return {@code 0}).
	 *
	 * @param <T> the parameters type of the returned {@code Comparator}.
	 * @param comparators the {@code Comparator}s to compose.
	 *
	 * @return the composed {@code Comparator}.
	 *
	 * @throws NullPointerException if {@code comparators} is {@code null}
	 *	of if it contains a {@code null} reference.
	 * @throws IllegalArgumentException if {@code comparators} is empty.
	 */
	public static <T> Comparator<T> compose(Comparator<? super T>... comparators)
	{
		return compose(Arrays.asList(comparators));
	}

	/**
	 * Returns a {@code Comparator} that will call each {@code Comparator}
	 * in the given {@code Iterable} until one of them returns a non-zero
	 * result (will return {@code 0} if they all return {@code 0}).
	 *
	 * @param <T> the parameters type of the returned {@code Comparator}.
	 * @param comparators the {@code Comparator}s to compose.
	 *
	 * @return the composed {@code Comparator}.
	 *
	 * @throws NullPointerException if {@code comparators} is {@code null}
	 *	of if it contains a {@code null} reference.
	 * @throws IllegalArgumentException if {@code comparators} is empty.
	 */
	public static <T> Comparator<T> compose(List<Comparator<? super T>> comparators)
	{
		return new CompositeComparator<T>(comparators);
	}

	/**
	 * Returns a {@code Comparator} that represents the reverse ordering of
	 * the given one. Namely, the returned {@code Comparator} will return
	 * a negative value if the original returns a positive value and,
	 * conversely, will return a positive value if it returns a negative
	 * value.
	 *
	 * @param <T> the parameters type of the {@code Comparator}s.
	 * @param comparator the comparator to invert.
	 *
	 * @return the inverted {@code Comparator}.
	 *
	 * @throws NullPointerException if {@code comparator} is {@code null}.
	 */
	public static <T> Comparator<T> reverse(final Comparator<T> comparator)
	{
		Parameters.checkNotNull(comparator);
		return new Comparator<T>()
		{
			@Override
			public int compare(T o1, T o2)
			{
				return -comparator.compare(o1, o2);
			}
		};
	}

	/**
	 * Returns a {@code Comparator} that compares {@code Comparable} objects
	 * in natural order. The returned comparator doesn't accept {@code null}
	 * values.
	 *
	 * @param <T> the {@code Comparable} type.
	 *
	 * @return a {@code Comparator} that compares {@code Comparable} objects.
	 */
	public static <T extends Comparable<? super T>> Comparator<T> naturalOrder()
	{
		return new Comparator<T>()
		{
			@Override
			public int compare(T o1, T o2)
			{
				return o1.compareTo(Parameters.checkNotNull(o2));
			}
		};
	}

	/**
	 * Returns a new {@code Comparator} that considers {@code null} values
	 * as less than all other values and compares non-{@code null} values
	 * with the given {@code Comparator}.
	 *
	 * @param <T> the parameters type of the {@code Comparator}.
	 * @param comparator the non-{@code null} values {@code Comparator}
	 *
	 * @return the created {@code Comparator}.
	 *
	 * @throws NullPointerException if {@code comparator} is {@code null}.
	 */
	public static <T> Comparator<T> withNullsFirst(final Comparator<T> comparator)
	{
		Parameters.checkNotNull(comparator);
		return new Comparator<T>()
		{
			@Override
			public int compare(T o1, T o2)
			{
				return o1 == o2 ? 0
					: o1 == null ? -1
					: o2 == null ? 1
					: comparator.compare(o1, o2);
			}
		};
	}

	/**
	 * Returns a new {@code Comparator} that considers {@code null} values
	 * as greater than all other values and compares non-{@code null} values
	 * with the given {@code Comparator}.
	 *
	 * @param <T> the parameters type of the {@code Comparator}.
	 * @param comparator the non-{@code null} values {@code Comparator}.
	 *
	 * @return the created {@code Comparator}.
	 *
	 * @throws NullPointerException if {@code comparator} is {@code null}.
	 */
	public static <T> Comparator<T> withNullsLast(final Comparator<T> comparator)
	{
		Parameters.checkNotNull(comparator);
		return new Comparator<T>()
		{
			@Override
			public int compare(T o1, T o2)
			{
				return o1 == o2 ? 0
					: o1 == null ? 1
					: o2 == null ? -1
					: comparator.compare(o1, o2);
			}
		};
	}

	private static final class CompositeComparator<T> implements Comparator<T>
	{
		private final List<Comparator<? super T>> comparators;

		CompositeComparator(List<Comparator<? super T>> comparators)
		{
			this.comparators = new ArrayList<Comparator<? super T>>();
			for (Comparator<? super T> comparator : comparators) {
				Parameters.checkNotNull(comparator);
				this.comparators.add(comparator);
			}
			Parameters.checkCondition(!this.comparators.isEmpty());
		}

		@Override
		public int compare(T o1, T o2)
		{
			for (Comparator<? super T> comparator : comparators) {
				int c = comparator.compare(o1, o2);
				if (c != 0) {
					return c;
				}
			}
			return 0;
		}
	}

	private Comparators()
	{
		/* ... */
	}
}
