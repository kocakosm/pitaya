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

package org.kocakosm.pitaya.util;

import static org.junit.Assert.*;

import org.kocakosm.pitaya.charset.ASCII;

import java.util.Random;

import org.junit.Test;

/**
 * Base32 unit tests.
 *
 * @author Osman KOCAK
 */
public final class Base32Test
{
	private static final Random RND = new Random();

	@Test
	public void testRFC4648TestVectorsWithPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_32;
		assertEquals("", e.encode(ascii("")));
		assertEquals("MY======", e.encode(ascii("f")));
		assertEquals("MZXQ====", e.encode(ascii("fo")));
		assertEquals("MZXW6===", e.encode(ascii("foo")));
		assertEquals("MZXW6YQ=", e.encode(ascii("foob")));
		assertEquals("MZXW6YTB", e.encode(ascii("fooba")));
		assertEquals("MZXW6YTBOI======", e.encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), e.decode("MZXW6YTBOI======"));
		assertArrayEquals(ascii("fooba"), e.decode("MZXW6YTB"));
		assertArrayEquals(ascii("foob"), e.decode("MZXW6YQ="));
		assertArrayEquals(ascii("foo"), e.decode("MZXW6==="));
		assertArrayEquals(ascii("fo"), e.decode("MZXQ===="));
		assertArrayEquals(ascii("f"), e.decode("MY======"));
		assertArrayEquals(ascii(""), e.decode(""));
	}

	@Test
	public void testRFC4648TestVectorsWithoutPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_32.withoutPadding();
		assertEquals("", e.encode(ascii("")));
		assertEquals("MY", e.encode(ascii("f")));
		assertEquals("MZXQ", e.encode(ascii("fo")));
		assertEquals("MZXW6", e.encode(ascii("foo")));
		assertEquals("MZXW6YQ", e.encode(ascii("foob")));
		assertEquals("MZXW6YTB", e.encode(ascii("fooba")));
		assertEquals("MZXW6YTBOI", e.encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), e.decode("MZXW6YTBOI"));
		assertArrayEquals(ascii("fooba"), e.decode("MZXW6YTB"));
		assertArrayEquals(ascii("foob"), e.decode("MZXW6YQ"));
		assertArrayEquals(ascii("foo"), e.decode("MZXW6"));
		assertArrayEquals(ascii("fo"), e.decode("MZXQ"));
		assertArrayEquals(ascii("f"), e.decode("MY"));
		assertArrayEquals(ascii(""), e.decode(""));
	}

	@Test
	public void testEncodeAndDecodeRandomDataWithPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_32;
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[RND.nextInt(2049)];
			RND.nextBytes(bytes);
			assertArrayEquals(bytes, e.decode(e.encode(bytes)));
		}
	}

	@Test
	public void testEncodeAndDecodeRandomDataWithoutPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_32.withoutPadding();
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[RND.nextInt(2049)];
			RND.nextBytes(bytes);
			assertArrayEquals(bytes, e.decode(e.encode(bytes)));
		}
	}

	@Test
	public void testIgnoreUnknownCharacters()
	{
		BaseEncoding e = BaseEncoding.BASE_32.ignoreUnknownCharacters();
		assertArrayEquals(ascii(""), e.decode(" \t 8 \r0"));
		assertArrayEquals(ascii("hello"), e.decode("\nNB0 SWY3\rDP "));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithInvalidCharacter()
	{
		BaseEncoding.BASE_32.decode("MZXW8===");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithInvalidLength()
	{
		BaseEncoding.BASE_32.decode("mzxq");
	}

	@Test
	public void testEncodeWithSeparator()
	{
		BaseEncoding e = BaseEncoding.BASE_32.withSeparator("\n", 3);
		assertEquals("MZX\nW6=\n==", e.encode(ascii("foo")));
	}

	@Test
	public void testDecodeWithSeparator()
	{
		BaseEncoding e = BaseEncoding.BASE_32.withSeparator("\n", 3);
		assertArrayEquals(ascii("foo"), e.decode("MZX\nW6=\n=="));
	}

	private byte[] ascii(String str)
	{
		return ASCII.encode(str);
	}
}
