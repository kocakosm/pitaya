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

package org.kocakosm.pitaya.charset;

import static org.kocakosm.pitaya.charset.ASCII.*;
import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.junit.Test;

/**
 * {@link ASCII}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ASCIITest
{
	@Test
	public void testCanEncode()
	{
		assertTrue(canEncode("The Raven"));
		assertFalse(canEncode("Spleen et Idéal"));
		assertTrue(canEncode("Spleen et Idéal", 0, 6));
		assertFalse(canEncode("Spleen et Idéal", 10, 5));
	}

	@Test
	public void testCanDecode()
	{
		Charset utf8 = Charset.forName("UTF-8");
		assertTrue(canDecode("The Raven".getBytes(utf8)));
		assertFalse(canDecode("Spleen et Idéal".getBytes(utf8)));
		assertTrue(canDecode("Spleen et Idéal".getBytes(utf8), 0, 6));
		assertFalse(canDecode("Spleen et Idéal".getBytes(utf8), 10, 5));
	}

	@Test
	public void testEncode()
	{
		Charset ascii = Charset.forName("US-ASCII");
		assertArrayEquals(encode(""), new byte[0]);
		String str = "Hello world!";
		assertArrayEquals(str.getBytes(ascii), encode(str));
		assertArrayEquals("llo w".getBytes(ascii), encode(str, 2, 5));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEncodeInvalidString()
	{
		encode("Spleen et Idéal");
	}

	@Test
	public void testDecode()
	{
		Charset ascii = Charset.forName("US-ASCII");
		assertEquals(decode(new byte[0]), "");
		String str = "Hello world!";
		assertEquals(decode(str.getBytes(ascii)), str);
		assertEquals(decode(str.getBytes(ascii), 2, 5), "llo w");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDecodeInvalidBytes()
	{
		decode("Spleen et Idéal".getBytes(Charset.forName("UTF-8")));
	}

	@Test
	public void testIsDigit()
	{
		assertTrue(isDigit('0'));
		assertTrue(isDigit('1'));
		assertTrue(isDigit('2'));
		assertTrue(isDigit('3'));
		assertTrue(isDigit('4'));
		assertTrue(isDigit('5'));
		assertTrue(isDigit('6'));
		assertTrue(isDigit('7'));
		assertTrue(isDigit('8'));
		assertTrue(isDigit('9'));
		assertFalse(isDigit('a'));
		assertFalse(isDigit('#'));
		assertFalse(isDigit('='));
		assertFalse(isDigit('&'));
		assertFalse(isDigit('$'));
	}

	@Test
	public void testIsLetter()
	{
		assertTrue(isLetter('a'));
		assertTrue(isLetter('z'));
		assertTrue(isLetter('A'));
		assertTrue(isLetter('Z'));
		assertFalse(isLetter('ç'));
		assertFalse(isLetter('é'));
		assertFalse(isLetter('1'));
		assertFalse(isLetter(')'));
	}

	@Test
	public void testIsAlphabetic()
	{
		assertTrue(isAlphabetic(""));
		assertTrue(isAlphabetic("Hello"));
		assertFalse(isAlphabetic("Hello world"));
		assertFalse(isAlphabetic("123, Fake Street"));
	}

	@Test
	public void testIsNumeric()
	{
		assertTrue(isNumeric(""));
		assertTrue(isNumeric("0123456789"));
		assertFalse(isNumeric("Hello world"));
		assertFalse(isNumeric("123, Fake Street"));
	}

	@Test
	public void testIsAlphaNumeric()
	{
		assertTrue(isAlphaNumeric(""));
		assertTrue(isAlphaNumeric("0123456789"));
		assertTrue(isAlphaNumeric("AbCdEfGhIjKlMnOpQrStUvWxYz"));
		assertTrue(isAlphaNumeric("0A1b2C3d4E5f5G6h8I9j"));
		assertFalse(isAlphaNumeric("123, Fake Street"));
	}

	@Test
	public void testIsLowerCase()
	{
		assertTrue(isLowerCase('a'));
		assertTrue(isLowerCase('m'));
		assertTrue(isLowerCase('z'));
		assertFalse(isLowerCase('A'));
		assertFalse(isLowerCase('M'));
		assertFalse(isLowerCase('Z'));
		assertFalse(isLowerCase('1'));
		assertFalse(isLowerCase('#'));
		assertFalse(isLowerCase('é'));
	}

	@Test
	public void testIsUpperCase()
	{
		assertTrue(isUpperCase('A'));
		assertTrue(isUpperCase('M'));
		assertTrue(isUpperCase('Z'));
		assertFalse(isUpperCase('a'));
		assertFalse(isUpperCase('m'));
		assertFalse(isUpperCase('z'));
		assertFalse(isUpperCase('1'));
		assertFalse(isUpperCase('#'));
		assertFalse(isUpperCase('É'));
	}

	@Test
	public void testToLowerCase()
	{
		assertEquals("", toLowerCase(""));
		assertEquals("hello world!", toLowerCase("Hello World!"));
	}

	@Test
	public void testToUpperCase()
	{
		assertEquals("", toUpperCase(""));
		assertEquals("HELLO WORLD!", toUpperCase("Hello World!"));
	}

	@Test
	public void testCapitalize()
	{
		assertEquals("", capitalize(""));
		assertEquals("A", capitalize("a"));
		assertEquals("Z", capitalize("Z"));
		assertEquals("5", capitalize("5"));
		assertEquals("5AbC", capitalize("5AbC"));
		assertEquals("   AbC", capitalize("   AbC"));
		assertEquals("Hello", capitalize("hello"));
	}
}
