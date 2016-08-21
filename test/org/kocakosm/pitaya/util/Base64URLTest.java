/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2016 Osman KOCAK <kocakosm@gmail.com>                   *
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
 * Base64URL unit tests.
 *
 * @author Osman KOCAK
 */
public final class Base64URLTest
{
	private static final Random RND = new Random();

	@Test
	public void testRFC4648TestVectorsWithPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_64_URL;
		assertEquals("", e.encode(ascii("")));
		assertEquals("Zg==", e.encode(ascii("f")));
		assertEquals("Zm8=", e.encode(ascii("fo")));
		assertEquals("Zm9v", e.encode(ascii("foo")));
		assertEquals("Zm9vYg==", e.encode(ascii("foob")));
		assertEquals("Zm9vYmE=", e.encode(ascii("fooba")));
		assertEquals("Zm9vYmFy", e.encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), e.decode("Zm9vYmFy"));
		assertArrayEquals(ascii("fooba"), e.decode("Zm9vYmE="));
		assertArrayEquals(ascii("foob"), e.decode("Zm9vYg=="));
		assertArrayEquals(ascii("foo"), e.decode("Zm9v"));
		assertArrayEquals(ascii("fo"), e.decode("Zm8="));
		assertArrayEquals(ascii("f"), e.decode("Zg=="));
		assertArrayEquals(ascii(""), e.decode(""));
	}

	@Test
	public void testRFC4648TestVectorsWithoutPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_64_URL.withoutPadding();
		assertEquals("", e.encode(ascii("")));
		assertEquals("Zg", e.encode(ascii("f")));
		assertEquals("Zm8", e.encode(ascii("fo")));
		assertEquals("Zm9v", e.encode(ascii("foo")));
		assertEquals("Zm9vYg", e.encode(ascii("foob")));
		assertEquals("Zm9vYmE", e.encode(ascii("fooba")));
		assertEquals("Zm9vYmFy", e.encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), e.decode("Zm9vYmFy"));
		assertArrayEquals(ascii("fooba"), e.decode("Zm9vYmE"));
		assertArrayEquals(ascii("foob"), e.decode("Zm9vYg"));
		assertArrayEquals(ascii("foo"), e.decode("Zm9v"));
		assertArrayEquals(ascii("fo"), e.decode("Zm8"));
		assertArrayEquals(ascii("f"), e.decode("Zg"));
		assertArrayEquals(ascii(""), e.decode(""));
	}

	@Test
	public void testEncodeAndDecodeRandomDataWithPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_64_URL;
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[RND.nextInt(2049)];
			RND.nextBytes(bytes);
			assertArrayEquals(bytes, e.decode(e.encode(bytes)));
		}
	}

	@Test
	public void testEncodeAndDecodeRandomDataWithoutPadding()
	{
		BaseEncoding e = BaseEncoding.BASE_64_URL.withoutPadding();
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[RND.nextInt(2049)];
			RND.nextBytes(bytes);
			assertArrayEquals(bytes, e.decode(e.encode(bytes)));
		}
	}

	@Test
	public void testIgnoreUnknownCharacters()
	{
		BaseEncoding e = BaseEncoding.BASE_64_URL.ignoreUnknownCharacters();
		assertArrayEquals(ascii(""), e.decode("/\t  +\r\n"));
		assertArrayEquals(ascii("hello"), e.decode("/a\nGV+s \rbG8="));
		assertArrayEquals(ascii("f"), e.decode("Z\ng=\t="));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithInvalidCharacter()
	{
		BaseEncoding.BASE_64_URL.decode("Zm9v+mFy");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithInvalidLength()
	{
		BaseEncoding.BASE_64_URL.decode("Zg");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithInvalidPadding()
	{
		BaseEncoding.BASE_64_URL.decode("Zg==g");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSeparator()
	{
		BaseEncoding.BASE_64_URL.withSeparator("=", 5);
	}

	@Test
	public void testEncodeWithSeparator()
	{
		BaseEncoding e = BaseEncoding.BASE_64_URL.withSeparator("\n", 3);
		assertEquals("Zm9\nvYm\nFy", e.encode(ascii("foobar")));

		e = BaseEncoding.BASE_64_URL.withSeparator("  ", 1);
		assertEquals("Z  m  9  v", e.encode(ascii("foo")));
	}

	@Test
	public void testDecodeWithSeparator()
	{
		BaseEncoding e = BaseEncoding.BASE_64_URL.withSeparator("\n", 3);
		assertArrayEquals(ascii("foobar"), e.decode("Zm9\nvYm\nFy"));

		e = BaseEncoding.BASE_64_URL.withSeparator("  ", 1);
		assertArrayEquals(ascii("foo"), e.decode("Z  m  9  v"));
	}

	@Test
	public void testDecodeRange()
	{
		BaseEncoding e = BaseEncoding.BASE_64_URL;
		assertArrayEquals(ascii("foo"), e.decode("Zm9v", 0, 4));
	}

	private byte[] ascii(String str)
	{
		return ASCII.encode(str);
	}
}
