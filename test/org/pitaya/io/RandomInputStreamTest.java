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

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

/**
 * {@link RandomInputStream}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class RandomInputStreamTest
{
	private static final byte[] DATA = {
		(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
		(byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08, (byte) 0x09
	};

	@Test
	public void testRead()
	{
		FakeRandom prng = new FakeRandom(DATA);
		RandomInputStream in = new RandomInputStream(prng);
		for (int i = 0; i < DATA.length; i++) {
			assertEquals(DATA[i], in.read());
		}
	}

	@Test
	public void testReadArray() throws IOException
	{
		FakeRandom prng = new FakeRandom(DATA);
		RandomInputStream in = new RandomInputStream(prng);
		byte[] rnd = new byte[DATA.length];

		assertEquals(rnd.length, in.read(rnd));
		assertArrayEquals(DATA, rnd);

		assertEquals(5, in.read(rnd, 0, 5));
		assertArrayEquals(Arrays.copyOf(DATA, 5), Arrays.copyOf(rnd, 5));
	}
}
