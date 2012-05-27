/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012 Osman KOCAK <kocakosm@gmail.com>                        *
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

/**
 * {@link LimitInputStream}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class LimitInputStreamTest
{
	private static final byte[] DATA = {
		(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44,
		(byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88, (byte) 0x99,
		(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE,
		(byte) 0xFF
	};

	@Test
	public void testRead() throws IOException
	{
		InputStream data, in;

		data = new ByteArrayInputStream(DATA);
		in = new LimitInputStream(data, 0);
		assertEquals(-1, in.read());

		data = new ByteArrayInputStream(DATA);
		in = new LimitInputStream(data, DATA.length);
		for (byte b : DATA) {
			assertEquals(b & 0xFF, in.read());
		}
		assertEquals(-1, in.read());

		data = new ByteArrayInputStream(DATA);
		in = new LimitInputStream(data, DATA.length + 10);
		for (byte b : DATA) {
			assertEquals(b & 0xFF, in.read());
		}
		assertEquals(-1, in.read());

		data = new ByteArrayInputStream(DATA);
		in = new LimitInputStream(data, DATA.length - 10);
		for (int i = 0; i < DATA.length - 10; i++) {
			assertEquals(DATA[i] & 0xFF, in.read());
		}
		assertEquals(-1, in.read());
	}

	@Test
	public void testReadArray() throws IOException
	{
		InputStream data, in;

		data = new ByteArrayInputStream(DATA);
		in = new LimitInputStream(data, 0);
		assertArrayEquals(new byte[0], Streams.read(in));

		data = new ByteArrayInputStream(DATA);
		in = new LimitInputStream(data, DATA.length);
		assertArrayEquals(DATA, Streams.read(in));

		data = new ByteArrayInputStream(DATA);
		in = new LimitInputStream(data, DATA.length + 10);
		assertArrayEquals(DATA, Streams.read(in));

		data = new ByteArrayInputStream(DATA);
		in = new LimitInputStream(data, 10);
		assertArrayEquals(Arrays.copyOf(DATA, 10), Streams.read(in));
	}
}
