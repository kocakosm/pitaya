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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

/**
 * {@link Comparators}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class ComparatorsTest
{
	@Test
	public void testCompose()
	{
		Comparator<String> nullsLast = new NullsLastStringComparator();
		Comparator<String> alphabetical = new AlphabeticalStringComparator();

		List<Comparator<? super String>> comparators =
			new ArrayList<Comparator<? super String>>();
		comparators.addAll(Arrays.asList(alphabetical, nullsLast));
		Comparator<String> c1 = Comparators.compose(comparators);
		Comparator<String> c2 = Comparators.compose(nullsLast, alphabetical);

		String[] a = new String[] {null, "d", "c", null, "a", "b", null};
		String[] b = new String[] {null, "b", "d", null, "a", null, "c"};
		Arrays.sort(a, c1);
		Arrays.sort(b, c2);

		assertArrayEquals(a, b);
		assertArrayEquals(new String[] {"a", "b", "c", "d", null, null, null}, a);
	}

	@Test
	public void testInvert()
	{
		Comparator<Long> ascending = new AscendingLongComparator();

		Long[] a = new Long[] {5L, 1L, 3L, 2L, 4L, 1L};
		Arrays.sort(a, ascending);
		assertArrayEquals(new Long[] {1L, 1L, 2L, 3L, 4L, 5L}, a);

		Comparator<Long> descending = Comparators.invert(ascending);
		Arrays.sort(a, descending);
		assertArrayEquals(new Long[] {5L, 4L, 3L, 2L, 1L, 1L}, a);
	}

	@Test
	public void testNaturalOrder()
	{
		Comparator<Long> natural = Comparators.naturalOrder();

		Long[] a = new Long[] {5L, 1L, 3L, 2L, 4L, 1L};
		Arrays.sort(a, natural);

		assertArrayEquals(new Long[] {1L, 1L, 2L, 3L, 4L, 5L}, a);
	}

	private static final class AscendingLongComparator implements Comparator<Long>
	{
		@Override
		public int compare(Long o1, Long o2)
		{
			return o1.compareTo(o2);
		}
	}

	private static final class NullsLastStringComparator implements Comparator<String>
	{
		@Override
		public int compare(String o1, String o2)
		{
			if (o1 == null && o2 == null) {
				return 0;
			}
			if (o1 == null) {
				return 1;
			}
			if (o2 == null) {
				return -1;
			}
			return 0;
		}
	}

	private static final class AlphabeticalStringComparator implements Comparator<String>
	{
		@Override
		public int compare(String o1, String o2)
		{
			if (o1 == null || o2 == null) {
				return 0;
			}
			return o1.compareTo(o2);
		}
	}
}
