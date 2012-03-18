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
		assertEquals("", Base64.encode(ASCII.encode("")));
		assertEquals("Zg==", Base64.encode(ASCII.encode("f")));
		assertEquals("Zm8=", Base64.encode(ASCII.encode("fo")));
		assertEquals("Zm9v", Base64.encode(ASCII.encode("foo")));
		assertEquals("Zm9vYg==", Base64.encode(ASCII.encode("foob")));
		assertEquals("Zm9vYmE=", Base64.encode(ASCII.encode("fooba")));
		assertEquals("Zm9vYmFy", Base64.encode(ASCII.encode("foobar")));
		assertArrayEquals(ASCII.encode("foobar"), Base64.decode("Zm9vYmFy"));
		assertArrayEquals(ASCII.encode("fooba"), Base64.decode("Zm9vYmE="));
		assertArrayEquals(ASCII.encode("foob"), Base64.decode("Zm9vYg=="));
		assertArrayEquals(ASCII.encode("foo"), Base64.decode("Zm9v"));
		assertArrayEquals(ASCII.encode("fo"), Base64.decode("Zm8="));
		assertArrayEquals(ASCII.encode("f"), Base64.decode("Zg=="));
		assertArrayEquals(ASCII.encode(""), Base64.decode(""));
	}

	@Test
	public void testRandomData()
	{
		Random rnd = new Random();
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[rnd.nextInt(2049)];
			assertArrayEquals(bytes, Base64.decode(Base64.encode(bytes)));
		}
	}
}
