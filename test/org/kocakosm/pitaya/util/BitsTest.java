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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * {@link Bits} unit tests.
 *
 * @author Osman KOCAK
 */
public final class BitsTest
{
	@Test
	public void testRotateLeftInt()
	{
		int x = randomInt();
		assertEquals(x, Bits.rotateLeft(x, 32));
		assertEquals(1024, Bits.rotateLeft(1, 10));
		assertEquals(Bits.rotateRight(x, 10), Bits.rotateLeft(x, -10));
	}

	@Test
	public void testRotateLeftLong()
	{
		long x = randomLong();
		assertEquals(x, Bits.rotateLeft(x, 64));
		assertEquals(1024L, Bits.rotateLeft(1L, 10));
		assertEquals(Bits.rotateRight(x, 10), Bits.rotateLeft(x, -10));
	}

	@Test
	public void testRotateRightInt()
	{
		int x = randomInt();
		assertEquals(x, Bits.rotateRight(x, 32));
		assertEquals(1, Bits.rotateRight(1024, 10));
		assertEquals(Bits.rotateLeft(x, 10), Bits.rotateRight(x, -10));
	}

	@Test
	public void testRotateRightLong()
	{
		long x = randomLong();
		assertEquals(x, Bits.rotateRight(x, 64));
		assertEquals(1L, Bits.rotateRight(1024L, 10));
		assertEquals(Bits.rotateLeft(x, 10), Bits.rotateRight(x, -10));
	}

	private int randomInt()
	{
		return (int) (Math.random() * 1000);
	}

	private long randomLong()
	{
		return (long) (Math.random() * 1000);
	}
}
