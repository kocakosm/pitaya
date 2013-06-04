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

package org.pitaya.util;

import static org.pitaya.util.Base32.*;
import static org.junit.Assert.*;

import org.pitaya.charset.ASCII;

import java.util.Random;

import org.junit.Test;

/**
 * Base32 unit tests.
 *
 * @author Osman KOCAK
 */
public final class Base32Test
{
	@Test
	public void testRFC4648TestVectors()
	{
		assertEquals("", encode(ascii("")));
		assertEquals("MY======", encode(ascii("f")));
		assertEquals("MZXQ====", encode(ascii("fo")));
		assertEquals("MZXW6===", encode(ascii("foo")));
		assertEquals("MZXW6YQ=", encode(ascii("foob")));
		assertEquals("MZXW6YTB", encode(ascii("fooba")));
		assertEquals("MZXW6YTBOI======", encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), decode("MZXW6YTBOI======"));
		assertArrayEquals(ascii("fooba"), decode("MZXW6YTB"));
		assertArrayEquals(ascii("foob"), decode("MZXW6YQ="));
		assertArrayEquals(ascii("foo"), decode("MZXW6==="));
		assertArrayEquals(ascii("fo"), decode("MZXQ===="));
		assertArrayEquals(ascii("f"), decode("MY======"));
		assertArrayEquals(ascii(""), decode(""));
	}

	@Test
	public void testRandomData()
	{
		Random rnd = new Random();
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[rnd.nextInt(2049)];
			assertArrayEquals(bytes, decode(encode(bytes)));
		}
	}

	@Test
	public void testDecodeWithWhitespaces()
	{
		assertArrayEquals(ascii(""), decode(" \t  \r\n"));
		assertArrayEquals(ascii("hello"), decode("\nNB\t  SWY3DP\r"));
	}

	private byte[] ascii(String str)
	{
		return ASCII.encode(str);
	}
}
