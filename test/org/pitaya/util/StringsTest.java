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

package org.pitaya.util;

import static org.pitaya.util.Strings.*;
import static org.junit.Assert.*;

import java.util.Arrays;

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
	public void testIsNullOrBlank()
	{
		assertTrue(isNullOrBlank(null));
		assertTrue(isNullOrBlank(""));
		assertTrue(isNullOrBlank(" "));
		assertTrue(isNullOrBlank("\t"));
		assertTrue(isNullOrBlank("\r"));
		assertTrue(isNullOrBlank("\n"));
		assertTrue(isNullOrBlank(" \n\r  \t"));
		assertFalse(isNullOrBlank("\tHello world!\r\n"));
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
	public void testIsNullOrWhitespace()
	{
		assertTrue(isNullOrWhiteSpace(null));
		assertTrue(isNullOrWhiteSpace(""));
		assertTrue(isNullOrWhiteSpace(" "));
		assertFalse(isNullOrWhiteSpace("\t"));
		assertFalse(isNullOrWhiteSpace("Hello world!"));
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
	public void testRandom()
	{
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		assertEquals("", random(0, chars));
		assertEquals(10, random(10, chars).length());
		assertEquals("aaaaaaaa", random(8, 'a'));
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
	public void testShuffle()
	{
		assertEquals("", shuffle(""));
		assertEquals("aaaaaaaa", shuffle("aaaaaaaa"));
		assertFalse("Hello world!!".equals(shuffle("Hello world!!")));
		char[] source = "Hello world!!".toCharArray();
		Arrays.sort(source);
		char[] shuffled = shuffle("Hello world!!").toCharArray();
		Arrays.sort(shuffled);
		assertArrayEquals(source, shuffled);
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

	@Test
	public void testQuote()
	{
		assertEquals("\"\"", quote(""));
		assertEquals("\"\"", quote("\"\""));
		assertEquals("\"Hello\"", quote("Hello\""));
		assertEquals("\"Hello\"", quote("\"Hello"));
		assertEquals("\"Hello\"", quote("\"Hello\""));
		assertEquals("\"Hello\"", quote("\"\"Hello\"\""));
		assertEquals("\"Hello\"", quote("\"\"Hello\"\"\""));
	}

	@Test
	public void testUnquote()
	{
		assertEquals("", unquote(""));
		assertEquals("", unquote("\""));
		assertEquals("Hello", unquote("Hello\""));
		assertEquals("Hello", unquote("\"Hello"));
		assertEquals("Hello", unquote("\"Hello\""));
		assertEquals("Hello", unquote("\"\"Hello\"\""));
		assertEquals("Hello", unquote("\"\"Hello\"\"\""));
	}

	@Test
	public void testCountOccurences()
	{
		assertEquals(0, countOccurrences("", "hello"));
		assertEquals(0, countOccurrences("hello", ""));
		assertEquals(0, countOccurrences("acegikmoqsuwy", "ab"));
		assertEquals(3, countOccurrences("abcegiabmosuab", "ab"));
		assertEquals(7, countOccurrences("ababababababab", "ab"));
		assertEquals(1, countOccurrences("hello", "hello"));
	}
}
