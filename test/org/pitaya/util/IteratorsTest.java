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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * {@link Iterators}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class IteratorsTest
{
	@Test
	public void testEmptyIteratorHasNext()
	{
		assertFalse(Iterators.emptyIterator().hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void testEmptyIteratorNext()
	{
		Iterators.emptyIterator().next();
	}

	@Test(expected = IllegalStateException.class)
	public void testEmptyIteratorRemove()
	{
		Iterators.emptyIterator().remove();
	}

	@Test
	public void testConcat()
	{
		Iterator<String> i1 = Arrays.asList("Hello").iterator();
		Iterator<String> i2 = Arrays.asList(" ").iterator();
		Iterator<String> i3 = Arrays.asList("World").iterator();
		Iterator<String> i4 = Arrays.asList("!").iterator();

		List<String> result = new ArrayList<String>();
		Iterator<String> concat = Iterators.concat(i1, i2, i3, i4);
		while (concat.hasNext()) {
			result.add(concat.next());
		}

		assertEquals(Arrays.asList("Hello", " ", "World", "!"), result);
	}
}
