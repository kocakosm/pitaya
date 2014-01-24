/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2014 Osman KOCAK <kocakosm@gmail.com>                   *
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

package org.pitaya.charset;

import static org.pitaya.charset.UTF8.*;
import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.junit.Test;

/**
 * {@link UTF8}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class UTF8Test
{
	@Test
	public void testEncode()
	{
		Charset latin1 = Charset.forName("UTF-8");
		assertArrayEquals(encode(""), new byte[0]);
		String str = "Hello world!";
		assertArrayEquals(encode(str), str.getBytes(latin1));
		assertArrayEquals(encode(str, 2, 5), "llo w".getBytes(latin1));
	}

	@Test
	public void testDecode()
	{
		Charset latin1 = Charset.forName("UTF-8");
		assertEquals(decode(new byte[0]), "");
		String str = "Hello world!";
		assertEquals(decode(str.getBytes(latin1)), str);
		assertEquals(decode(str.getBytes(latin1), 2, 5), "llo w");
	}
}
