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

package org.kocakosm.pitaya.charset;

import static org.kocakosm.pitaya.charset.UTF8.*;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
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
	public void testCanEncode()
	{
		assertTrue(canEncode("The Raven"));
		assertTrue(canEncode("Spleen et Idéal"));
		assertTrue(canEncode("您好"));
		assertTrue(canEncode("Spleen et Idéal", 0, 6));
		assertTrue(canEncode("Spleen et Idéal", 10, 5));

		assertTrue(canEncode("The Raven".toCharArray()));
		assertTrue(canEncode("Spleen et Idéal".toCharArray()));
		assertTrue(canEncode("您好".toCharArray()));
		assertTrue(canEncode("Spleen et Idéal".toCharArray(), 0, 6));
		assertTrue(canEncode("Spleen et Idéal".toCharArray(), 10, 5));
	}

	@Test
	public void testCanDecode()
	{
		Charset utf8 = Charset.forName("UTF-8");
		assertTrue(canDecode("The Raven".getBytes(utf8)));
		assertTrue(canDecode("Spleen et Idéal".getBytes(utf8)));
		assertTrue(canDecode("您好".getBytes(utf8)));
		assertTrue(canDecode("Spleen et Idéal".getBytes(utf8), 0, 6));
		assertTrue(canDecode("Spleen et Idéal".getBytes(utf8), 10, 5));
		assertFalse(canDecode((byte) 0xEB, (byte) 0x3A, (byte) 0xC4));
	}

	@Test
	public void testEncode()
	{
		Charset utf8 = Charset.forName("UTF-8");
		String str = "您好 Hallå Hello";

		assertArrayEquals(encode(""), new byte[0]);
		assertArrayEquals(encode(str), str.getBytes(utf8));
		assertArrayEquals(encode(str, 3, 5), "Hallå".getBytes(utf8));
		assertArrayEquals(encode(str, 0, 8), "您好 Hallå".getBytes(utf8));

		assertArrayEquals(encode(new char[0]), new byte[0]);
		assertArrayEquals(encode(str.toCharArray()), str.getBytes(utf8));
		assertArrayEquals(encode(str.toCharArray(), 3, 5), "Hallå".getBytes(utf8));
		assertArrayEquals(encode(str.toCharArray(), 0, 8), "您好 Hallå".getBytes(utf8));
	}

	@Test
	public void testDecode()
	{
		Charset utf8 = Charset.forName("UTF-8");
		assertEquals(decode(new byte[0]), "");
		String str = "您好 Hallå Hello";
		assertEquals(decode(str.getBytes(utf8)), str);
		assertEquals(decode(str.getBytes(utf8), 0, 13), "您好 Hallå");
	}

	@Test(expected = UncheckedCharacterCodingException.class)
	public void testDecodeInvalidBytes()
	{
		decode((byte) 0xEB, (byte) 0x3A, (byte) 0xC4);
	}

	@Test
	public void testConstructor() throws Exception
	{
		Class<UTF8> c = UTF8.class;
		assertEquals(1, c.getDeclaredConstructors().length);
		Constructor<UTF8> constructor = c.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}
}
