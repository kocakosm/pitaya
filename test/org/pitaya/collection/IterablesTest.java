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

import static org.pitaya.collection.Iterables.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * {@link Iterables}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class IterablesTest
{
	@Test
	public void testEmptyIterableHasNext()
	{
		assertFalse(emptyIterable().iterator().hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void testEmptyIterableNext()
	{
		emptyIterable().iterator().next();
	}

	@Test(expected = IllegalStateException.class)
	public void testEmptyIterableRemove()
	{
		emptyIterable().iterator().remove();
	}

	@Test
	public void testConcat()
	{
		Iterable<String> i1 = Arrays.asList("Hello");
		Iterable<String> i2 = Arrays.asList(" ");
		Iterable<String> i3 = Arrays.asList("World");
		Iterable<String> i4 = Arrays.asList("!");

		List<String> result = new ArrayList<String>();
		for (String s : concat(i1, i2, i3, i4)) {
			result.add(s);
		}

		assertEquals(Arrays.asList("Hello", " ", "World", "!"), result);
	}

	@Test
	public void testConcatEmptyIterables()
	{
		assertFalse(concat(emptyIterable(), emptyIterable()).iterator().hasNext());
		assertFalse(concat(new ArrayList<Iterable<Long>>()).iterator().hasNext());
		assertFalse(concat().iterator().hasNext());
	}
}
