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
 * Base32 unit tests.
 *
 * @author Osman KOCAK
 */
public final class Base32Test
{
	@Test
	public void testRFC4648TestVectors()
	{
		assertEquals("", Base32.encode(ASCII.encode("")));
		assertEquals("MY======", Base32.encode(ASCII.encode("f")));
		assertEquals("MZXQ====", Base32.encode(ASCII.encode("fo")));
		assertEquals("MZXW6===", Base32.encode(ASCII.encode("foo")));
		assertEquals("MZXW6YQ=", Base32.encode(ASCII.encode("foob")));
		assertEquals("MZXW6YTB", Base32.encode(ASCII.encode("fooba")));
		assertEquals("MZXW6YTBOI======", Base32.encode(ASCII.encode("foobar")));
		assertArrayEquals(ASCII.encode("foobar"), Base32.decode("MZXW6YTBOI======"));
		assertArrayEquals(ASCII.encode("fooba"), Base32.decode("MZXW6YTB"));
		assertArrayEquals(ASCII.encode("foob"), Base32.decode("MZXW6YQ="));
		assertArrayEquals(ASCII.encode("foo"), Base32.decode("MZXW6==="));
		assertArrayEquals(ASCII.encode("fo"), Base32.decode("MZXQ===="));
		assertArrayEquals(ASCII.encode("f"), Base32.decode("MY======"));
		assertArrayEquals(ASCII.encode(""), Base32.decode(""));
	}

	@Test
	public void testRandomData()
	{
		Random rnd = new Random();
		for (int i = 0; i < 100; i++) {
			byte[] bytes = new byte[rnd.nextInt(2049)];
			assertArrayEquals(bytes, Base32.decode(Base32.encode(bytes)));
		}
	}
}
