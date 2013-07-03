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

package org.pitaya.io;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * {@link ConcatInputStream}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ConcatInputStreamTest
{
	private static final byte[] DATA = {
		(byte) 0x00, (byte) 0xFF
	};

	private static final byte[] DATA2 = {
		(byte) 0x00, (byte) 0xFF,
		(byte) 0x00, (byte) 0xFF,
		(byte) 0x00, (byte) 0xFF
	};

	@Test
	public void testRead() throws IOException
	{
		InputStream d1 = new ByteArrayInputStream(DATA);
		InputStream d2 = new ByteArrayInputStream(DATA);
		InputStream d3 = new ByteArrayInputStream(DATA);
		InputStream in = new ConcatInputStream(d1, d2, d3);
		for (byte b : DATA2) {
			assertEquals(b & 0xFF, in.read());
		}
		assertEquals(-1, in.read());
		in.close();
	}

	@Test
	public void testReadArray() throws IOException
	{
		InputStream d1 = new ByteArrayInputStream(DATA);
		InputStream d2 = new ByteArrayInputStream(DATA);
		InputStream d3 = new ByteArrayInputStream(DATA);
		InputStream in = new ConcatInputStream(d1, d2, d3);
		assertArrayEquals(DATA2, Streams.read(in));
		in.close();
	}
}
