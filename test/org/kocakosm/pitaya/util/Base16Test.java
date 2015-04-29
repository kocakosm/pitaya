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
 * Base16 unit tests.
 *
 * @author Osman KOCAK
 */
public final class Base16Test
{
	private static final Random RND = new Random();

	@Test
	public void testRFC4648TestVectors()
	{
		BaseEncoding e = BaseEncoding.BASE_16;
		assertEquals("", e.encode(ascii("")));
		assertEquals("66", e.encode(ascii("f")));
		assertEquals("666F", e.encode(ascii("fo")));
		assertEquals("666F6F", e.encode(ascii("foo")));
		assertEquals("666F6F62", e.encode(ascii("foob")));
		assertEquals("666F6F6261", e.encode(ascii("fooba")));
		assertEquals("666F6F626172", e.encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), e.decode("666F6F626172"));
		assertArrayEquals(ascii("fooba"), e.decode("666F6F6261"));
		assertArrayEquals(ascii("foob"), e.decode("666F6F62"));
		assertArrayEquals(ascii("foo"), e.decode("666F6F"));
		assertArrayEquals(ascii("fo"), e.decode("666F"));
		assertArrayEquals(ascii("f"), e.decode("66"));
		assertArrayEquals(ascii(""), e.decode(""));
	}

	@Test
	public void testEncodeAndDecodeRandomData()
	{
		BaseEncoding e = BaseEncoding.BASE_16;
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[RND.nextInt(2049)];
			RND.nextBytes(bytes);
			assertArrayEquals(bytes, e.decode(e.encode(bytes)));
		}
	}

	@Test
	public void testIgnoreUnknownCharacters()
	{
		BaseEncoding e = BaseEncoding.BASE_16.ignoreUnknownCharacters();
		assertArrayEquals(ascii(""), e.decode(" \t j \rL"));
		assertArrayEquals(ascii("hello"), e.decode("\t68 656C\r6CY6F"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithInvalidCharacter()
	{
		BaseEncoding.BASE_16.decode(" \t j \rL");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithInvalidLength()
	{
		BaseEncoding.BASE_16.decode("E1F0C");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeWithPaddingCharacter()
	{
		BaseEncoding.BASE_16.decode("E1F0C=");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidSeparator()
	{
		BaseEncoding.BASE_16.withSeparator("f", 5);
	}

	@Test
	public void testEncodeWithSeparator()
	{
		BaseEncoding e = BaseEncoding.BASE_16.withSeparator("\n", 3);
		assertEquals("666\nF6F\n626\n172", e.encode(ascii("foobar")));

		e = BaseEncoding.BASE_16.withSeparator("  ", 1);
		assertEquals("6  6  6  F  6  F", e.encode(ascii("foo")));
	}

	@Test
	public void testDecodeWithSeparator()
	{
		BaseEncoding e = BaseEncoding.BASE_16.withSeparator("\n", 3);
		assertArrayEquals(ascii("foobar"), e.decode("666\nF6F\n626\n172"));

		e = BaseEncoding.BASE_16.withSeparator("  ", 1);
		assertArrayEquals(ascii("foo"), e.decode("6  6  6  F  6  F"));
	}

	@Test
	public void testDecodeRange()
	{
		BaseEncoding e = BaseEncoding.BASE_16;
		assertArrayEquals(ascii("foo"), e.decode("666F6F666F6F", 0, 6));
	}

	private byte[] ascii(String str)
	{
		return ASCII.encode(str);
	}
}
