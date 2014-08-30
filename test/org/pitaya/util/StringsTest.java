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

import org.pitaya.collection.ImmutableMap;

import java.util.Arrays;
import java.util.Map;

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
	public void testAbbreviateWithCustomEllipsis()
	{
		assertEquals("", abbreviate("", 4, ".."));
		assertEquals("ab", abbreviate("ab", 4, ".."));
		assertEquals("abcd", abbreviate("abcd", 4, ".."));
		assertEquals("ab##", abbreviate("abcde", 4, "##"));
		assertEquals("abcd", abbreviate("abcde", 4, ""));
		assertEquals("Hello --", abbreviate("Hello world!", 8, "--"));
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

	@Test
	public void testJoinWithDefaultJoiner()
	{
		Joiner joiner = joinWith(',');
		assertEquals("", joiner.join());
		assertEquals("1,2,3", joiner.join(1, 2, 3));
		assertEquals("1,2,3", joiner.join(Arrays.asList(1, 2, 3)));
		assertEquals("null,null", joiner.join("null", null));
	}

	@Test
	public void testJoinWithPrefixAndSuffix()
	{
		Joiner joiner = joinWith(", ").withPrefix("[").withSuffix("]");
		assertEquals("[]", joiner.join());
		assertEquals("[1, 2, 3]", joiner.join(1, 2, 3));
		assertEquals("[1, 2, 3]", joiner.join(Arrays.asList(1, 2, 3)));
	}

	@Test
	public void testJoinIgnoringNullValues()
	{
		Joiner joiner = joinWith(", ").ignoreNulls();
		assertEquals("1, 2, 3", joiner.join(1, null, 2, 3, null));
		assertEquals("1, 2, 3", joiner.join(Arrays.asList(1, null, 2, 3, null)));
	}

	@Test
	public void testJoinWithNullReplacement()
	{
		Joiner joiner = joinWith(", ").replaceNullWith("");
		assertEquals("one, , two", joiner.join("one", null, "two"));
		assertEquals("one, , two", joiner.join(Arrays.asList("one", null, "two")));
	}

	@Test
	public void testJoinIgnoringEmptyStrings()
	{
		Joiner joiner = joinWith(", ").ignoreEmptyStrings();
		assertEquals("1, 2, 3", joiner.join(1, "", 2, 3, ""));
		assertEquals("1, 2, 3", joiner.join(Arrays.asList("1", "", "2", "3")));
	}

	@Test
	public void testJoinWithEmptyStringReplacement()
	{
		Joiner joiner = joinWith(", ").replaceEmptyStringWith("X");
		assertEquals("1, X, 2", joiner.join("1", "", "2"));
		assertEquals("1, X, 2", joiner.join(Arrays.asList("1", "", "2")));
	}

	@Test
	public void testJoinMapWithDefaultJoiner()
	{
		MapJoiner joiner = joinWith(", ").withKeyValueSeparator("=");
		assertEquals("", joiner.join(map().build()));
		assertEquals("null=null", joiner.join(map().put(null, null).build()));
		Map<?, ?> m = map().put("one", 1).put("two", 2).build();
		assertTrue("one=1, two=2".equals(joiner.join(m))
			|| "two=2, one=1".equals(joiner.join(m)));
	}

	@Test
	public void testJoinMapWithPrefixAndSuffix()
	{
		MapJoiner joiner = joinWith(", ").withKeyValueSeparator("=")
			.withPrefix("{").withSuffix("}");
		assertEquals("{}", joiner.join(map().build()));
		Map<?, ?> m = map().put("one", 1).put("two", 2).build();
		assertTrue("{one=1, two=2}".equals(joiner.join(m))
			|| "{two=2, one=1}".equals(joiner.join(m)));
	}

	@Test
	public void testJoinMapWithNullReplacement()
	{
		MapJoiner joiner = joinWith(", ").withKeyValueSeparator("=")
			.replaceNullWith("X");
		assertEquals("", joiner.join(map().build()));
		assertEquals("X=3", joiner.join(map().put(null, 3).build()));
		assertEquals("4=X", joiner.join(map().put(4, null).build()));
	}

	@Test
	public void testJoinMapWithEmptyStringReplacement()
	{
		MapJoiner joiner = joinWith(", ").withKeyValueSeparator("=")
			.replaceEmptyStringWith("X");
		assertEquals("", joiner.join(map().build()));
		assertEquals("X=3", joiner.join(map().put("", 3).build()));
		assertEquals("4=X", joiner.join(map().put(4, "").build()));
	}

	private <K, V> ImmutableMap.Builder<K, V> map()
	{
		return new ImmutableMap.Builder<K, V>();
	}
}
