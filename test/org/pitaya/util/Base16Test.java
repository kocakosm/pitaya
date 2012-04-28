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

import static org.pitaya.util.Base16.decode;
import static org.pitaya.util.Base16.encode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.pitaya.charset.ASCII;

import java.util.Random;

import org.junit.Test;

/**
 * Base16 unit tests.
 *
 * @author Osman KOCAK
 */
public final class Base16Test
{
	@Test
	public void testRFC4648TestVectors()
	{
		assertEquals("", encode(ascii("")));
		assertEquals("66", encode(ascii("f")));
		assertEquals("666F", encode(ascii("fo")));
		assertEquals("666F6F", encode(ascii("foo")));
		assertEquals("666F6F62", encode(ascii("foob")));
		assertEquals("666F6F6261", encode(ascii("fooba")));
		assertEquals("666F6F626172", encode(ascii("foobar")));

		assertArrayEquals(ascii("foobar"), decode("666F6F626172"));
		assertArrayEquals(ascii("fooba"), decode("666F6F6261"));
		assertArrayEquals(ascii("foob"), decode("666F6F62"));
		assertArrayEquals(ascii("foo"), decode("666F6F"));
		assertArrayEquals(ascii("fo"), decode("666F"));
		assertArrayEquals(ascii("f"), decode("66"));
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

	private byte[] ascii(String str)
	{
		return ASCII.encode(str);
	}
}
