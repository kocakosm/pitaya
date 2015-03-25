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

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

/**
 * {@link UnmodifiableIterator}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class UnmodifiableIteratorTest
{
	@Test
	public void testHasNext()
	{
		assertFalse(new UnmodifiableIterator(iterator()).hasNext());

		Iterator<Long> i = new UnmodifiableIterator<Long>(iterator(1L));
		assertTrue(i.hasNext());
		i.next();
		assertFalse(i.hasNext());
	}

	@Test
	public void testNext()
	{
		Iterator<Long> i = new UnmodifiableIterator<Long>(iterator(1L));
		assertEquals(Long.valueOf(1L), i.next());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove()
	{
		new UnmodifiableIterator<Long>(iterator(1L)).remove();
	}

	private <T> Iterator<T> iterator(T... values)
	{
		return Arrays.asList(values).iterator();
	}
}
