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

import static org.pitaya.util.Base64.*;
import static org.junit.Assert.*;

import org.pitaya.charset.ASCII;

import java.util.Random;

import org.junit.Test;

/**
 * Base64 unit tests.
 *
 * @author Osman KOCAK
 */
public final class Base64Test
{
	@Test
	public void testRFC4648TestVectors()
	{
		assertEquals("", encode(ascii("")));
		assertEquals("Zg==", encode(ascii("f")));
		assertEquals("Zm8=", encode(ascii("fo")));
		assertEquals("Zm9v", encode(ascii("foo")));
		assertEquals("Zm9vYg==", encode(ascii("foob")));
		assertEquals("Zm9vYmE=", encode(ascii("fooba")));
		assertEquals("Zm9vYmFy", encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), decode("Zm9vYmFy"));
		assertArrayEquals(ascii("fooba"), decode("Zm9vYmE="));
		assertArrayEquals(ascii("foob"), decode("Zm9vYg=="));
		assertArrayEquals(ascii("foo"), decode("Zm9v"));
		assertArrayEquals(ascii("fo"), decode("Zm8="));
		assertArrayEquals(ascii("f"), decode("Zg=="));
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
		assertArrayEquals(ascii("hello"), decode("  a\nGV\ts \rbG8= "));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidLength()
	{
		decode("Zg");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCharacter()
	{
		decode("Zm9v_mFy");
	}

	private byte[] ascii(String str)
	{
		return ASCII.encode(str);
	}
}
