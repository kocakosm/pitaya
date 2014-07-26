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

package org.pitaya.io;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * {@link RandomReader}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class RandomReaderTest
{
	@Test
	public void testRead()
	{
		FakeRandom prng = new FakeRandom((byte) 0x02, (byte) 0x00, (byte) 0x01);
		RandomReader reader = new RandomReader(prng, 'a', 'b', 'c');
		assertEquals('c', reader.read());
		assertEquals('a', reader.read());
		assertEquals('b', reader.read());
	}

	@Test
	public void testReadArray()
	{
		FakeRandom prng = new FakeRandom((byte) 0x02, (byte) 0x00, (byte) 0x01);
		RandomReader reader = new RandomReader(prng, 'a', 'b', 'c');
		char[] rnd = new char[3];

		assertEquals(rnd.length, reader.read(rnd));
		assertArrayEquals("cab".toCharArray(), rnd);

		assertEquals(2, reader.read(rnd, 0, 2));
		assertArrayEquals("ca".toCharArray(), Arrays.copyOf(rnd, 2));
	}

	@Test
	public void testClose()
	{
		FakeRandom prng = new FakeRandom((byte) 0x00);
		RandomReader reader = new RandomReader(prng, 'a');
		assertEquals('a', reader.read());
		reader.close();
		assertEquals('a', reader.read());
	}
}
