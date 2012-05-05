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

import static org.pitaya.util.Strings.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@code Strings}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class StringsTest
{
	@Test
	public void testIsBlank()
	{
		assertTrue(isBlank(""));
		assertTrue(isBlank(" "));
		assertTrue(isBlank("\t"));
		assertTrue(isBlank("\r"));
		assertTrue(isBlank("\n"));
		assertTrue(isBlank(" \n\r  \t"));
		assertFalse(isBlank("\tHello world!\r\n"));
	}

	@Test
	public void testIsWhitespace()
	{
		assertTrue(isWhiteSpace(""));
		assertTrue(isWhiteSpace(" "));
		assertFalse(isWhiteSpace("\t"));
		assertFalse(isWhiteSpace("Hello world!"));
	}

	@Test
	public void testIsEmpty()
	{
		assertTrue(isEmpty(""));
		assertFalse(isEmpty(" "));
		assertFalse(isEmpty("Hello world!"));
	}

	@Test
	public void testIsNullOrEmpty()
	{
		assertTrue(isNullOrEmpty(null));
		assertTrue(isNullOrEmpty(""));
		assertFalse(isNullOrEmpty("!!"));
	}

	@Test
	public void testAbbreviate()
	{
		assertEquals("", abbreviate("", 4));
		assertEquals("ab", abbreviate("ab", 4));
		assertEquals("abcd", abbreviate("abcd", 4));
		assertEquals("a...", abbreviate("abcde", 4));
		assertEquals("Hello...", abbreviate("Hello world!", 8));
	}

	@Test
	public void testConcat()
	{
		assertEquals("", concat());
		assertEquals("Hello world !", concat("Hello ", "world ", "!"));
	}

	@Test
	public void testEmptyToNull()
	{
		assertNull(emptyToNull(""));
		String str = "Hello world";
		assertEquals(str, emptyToNull(str));
	}

	@Test
	public void testNullToEmpty()
	{
		assertEquals("", nullToEmpty(null));
		String str = "Hello world";
		assertEquals(str, nullToEmpty(str));
	}

	@Test
	public void testRepeat()
	{
		assertEquals("", repeat("Hello", 0));
		assertEquals("Hello", repeat("Hello", 1));
		assertEquals("HelloHelloHello", repeat("Hello", 3));
	}

	@Test
	public void testReverse()
	{
		assertEquals("enirambus wolleY", reverse("Yellow submarine"));
		String palindrome = "rats live on no evil star";
		assertEquals(palindrome, reverse(palindrome));
	}

	@Test
	public void testStrip()
	{
		assertEquals(" ", strip("Hello world", 5));
		assertEquals("Hello world", strip("Hello world", 0));
		assertEquals("", strip("Hello world", 30));
	}

	@Test
	public void testStripLeft()
	{
		assertEquals("world", stripLeft("Hello world", 6));
		assertEquals("Hello world", stripLeft("Hello world", 0));
		assertEquals("", stripLeft("Hello world", 30));
	}

	@Test
	public void testStripRight()
	{
		assertEquals("Hello", stripRight("Hello world", 6));
		assertEquals("Hello world", stripRight("Hello world", 0));
		assertEquals("", stripRight("Hello world", 30));
	}

	@Test
	public void testTrim()
	{
		assertEquals("Hello", trim("Hello"));
		assertEquals("Hello", trim("    Hello      "));
	}

	@Test
	public void testTrimLeft()
	{
		assertEquals("Hello", trimLeft("Hello"));
		assertEquals("Hello ", trimLeft("    Hello "));
	}

	@Test
	public void testTrimRight()
	{
		assertEquals("Hello", trimRight("Hello"));
		assertEquals(" Hello", trimRight(" Hello      "));
	}

	@Test
	public void testPadLeft()
	{
		assertEquals("Hello", padLeft("Hello", 5, '.'));
		assertEquals(".....Hello", padLeft("Hello", 10, '.'));
	}

	@Test
	public void testPadRight()
	{
		assertEquals("Hello", padRight("Hello", 5, '.'));
		assertEquals("Hello.....", padRight("Hello", 10, '.'));
	}
}
