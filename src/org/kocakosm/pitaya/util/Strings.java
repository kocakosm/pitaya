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

package org.kocakosm.pitaya.util;

import java.util.Random;

/**
 * {@code String} utilities.
 *
 * @author Osman KOCAK
 */
public final class Strings
{
	private static final Random PRNG = new Random();

	/**
	 * Returns whether the given {@code String} only contains space, '\n',
	 * '\r' or '\t' characters.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return whether the given {@code String} is blank.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static boolean isBlank(String str)
	{
		for (char c : str.toCharArray()) {
			if (c != ' ' && c != '\r' && c != '\n' && c != '\t') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given {@code String} is {@code null} or only
	 * contains space, '\n', '\r' or '\t' characters.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return whether the given {@code String} is {@code null} or blank.
	 */
	public static boolean isNullOrBlank(String str)
	{
		return str == null || isBlank(str);
	}

	/**
	 * Returns whether the given {@code String} only contains space
	 * characters.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return whether the given {@code String} contains only spaces.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static boolean isWhiteSpace(String str)
	{
		for (char c : str.toCharArray()) {
			if (c != ' ') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given {@code String} is {@code null} or only
	 * contains space characters.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return whether the given {@code String} is {@code null} or contains
	 *	only spaces.
	 */
	public static boolean isNullOrWhiteSpace(String str)
	{
		return str == null || isWhiteSpace(str);
	}

	/**
	 * Returns whether the given {@code String} is {@code null} or empty.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return {@code true} if {@code str} is {@code null} or empty,
	 *	{@code false} otherwise.
	 */
	public static boolean isNullOrEmpty(String str)
	{
		return str == null || str.isEmpty();
	}

	/**
	 * Abbreviates the given {@code String} using "..." so that the returned
	 * {@code String}'s length is equal to {@code length}.
	 *
	 * @param str the {@code String} to abbreviate.
	 * @param length the desired length for the abbreviated {@code String}.
	 *
	 * @return the abbreviated {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IllegalArgumentException if {@code length} is too small.
	 */
	public static String abbreviate(String str, int length)
	{
		return abbreviate(str, length, "...");
	}

	/**
	 * Abbreviates the given {@code String} using the specified ellipsis so
	 * that the returned {@code String}'s length is equal to {@code length}.
	 *
	 * @param str the {@code String} to abbreviate.
	 * @param length the desired length for the abbreviated {@code String}.
	 * @param ellipsis the ellipsis to use to mark abbreviation.
	 *
	 * @return the abbreviated {@code String}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if {@code length} is too small.
	 */
	public static String abbreviate(String str, int length, String ellipsis)
	{
		Parameters.checkCondition(length > ellipsis.length());
		if (str.length() <= length) {
			return str;
		}
		return str.substring(0, length - ellipsis.length()) + ellipsis;
	}

	/**
	 * Returns a {@code String} built from the given one by upper-casing its
	 * first character (other characters are copied unchanged).
	 *
	 * @param str the {@code String} to capitalize.
	 *
	 * @return the capitalized {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String capitalize(String str)
	{
		if (str.isEmpty()) {
			return str;
		}
		char[] chars = str.toCharArray();
		chars[0] = Character.toTitleCase(chars[0]);
		return new String(chars);
	}

	/**
	 * Concatenates the given {@code String}s.
	 *
	 * @param strings the {@code String}s to concatenate.
	 *
	 * @return the concatenated {@code String}.
	 *
	 * @throws NullPointerException if {@code strings} is {@code null}.
	 */
	public static String concat(String... strings)
	{
		StringBuilder sb = new StringBuilder(strings.length * 10);
		for (String string : strings) {
			sb.append(string);
		}
		return sb.toString();
	}

	/**
	 * Computes and returns the Damerau-Levenshtein distance between the
	 * given {@code String}s. This distance is obtained  by counting the
	 * minimum number of operations needed to transform one {@code String}
	 * into the other, where an operation is defined as an insertion,
	 * deletion, or substitution of a single character, or a transposition
	 * of two adjacent characters.
	 *
	 * @param str1 the first {@code String}.
	 * @param str2 the second {@code String}.
	 *
	 * @return the distance between {@code str1} and {@code str2}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static int distance(String str1, String str2)
	{
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int[][] d = new int[s1.length + 1][s2.length + 1];
		for (int i = 0; i <= s1.length; i++) {
			d[i][0] = i;
		}
		for (int i = 0; i <= s2.length; i++) {
			d[0][i] = i;
		}
		for (int i = 1; i <= s1.length; i++) {
			for (int j = 1; j <= s2.length; j++) {
				int c = s1[i - 1] == s2[j - 1] ? 0 : 1;
				d[i][j] = min(
					d[i - 1][j] + 1,
					d[i][j - 1] + 1,
					d[i - 1][j - 1] + c);
				if (i > 1 && j > 1
					&& s1[i - 1] == s2[j - 2]
					&& s1[i - 2] == s2[j - 1])
				{
					d[i][j] = min(
						d[i][j], d[i - 2][j - 2] + c);
				}
			}
		}
		return d[s1.length][s2.length];
	}

	private static int min(int... values)
	{
		int min = values[0];
		for (int i = 1; i < values.length; i++) {
			min = Math.min(min, values[i]);
		}
		return min;
	}

	/**
	 * Returns {@code null} if the given {@code String} is empty, returns
	 * the (unmodified) parameter otherwise.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return {@code null} if the given {@code String} is empty.
	 */
	public static String emptyToNull(String str)
	{
		return str == null ? null : str.isEmpty() ? null : str;
	}

	/**
	 * Returns an empty {@code String} if the given one is {@code null}, or
	 * returns the (unmodified) parameter otherwise.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return an empty {@code String} if the given one is {@code null}.
	 */
	public static String nullToEmpty(String str)
	{
		return str == null ? "" : str;
	}

	/**
	 * Returns a pseudo-random {@code String} of the specified size using
	 * characters from the given array.
	 *
	 * @param length the length of the {@code String} to produce.
	 * @param chars the characters to use to build the {@code String}.
	 *
	 * @return a pseudo-random {@code String}.
	 *
	 * @throws NullPointerException if {@code chars} is {@code null}.
	 * @throws IllegalArgumentException if {@code length} is negative or if
	 *	{@code chars} is empty.
	 */
	public static String random(int length, char... chars)
	{
		return random(length, PRNG, chars);
	}

	/**
	 * Returns a random {@code String} using the given characters and the
	 * specified source of randomness. All characters have equal likelihood
	 * to appear in the resulting {@code String} assuming that the source of
	 * randomness is fair.
	 *
	 * @param length the length of the {@code String} to produce.
	 * @param rnd the source of randomness to use.
	 * @param chars the characters to use to build the {@code String}.
	 *
	 * @return a random {@code String}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if {@code length} is negative or if
	 *	{@code chars} is empty.
	 */
	public static String random(int length, Random rnd, char... chars)
	{
		Parameters.checkCondition(length >= 0);
		Parameters.checkCondition(chars.length > 0);
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(chars[rnd.nextInt(chars.length)]);
		}
		return sb.toString();
	}

	/**
	 * Returns the {@code String} obtained by repeating {@code count} times
	 * the given {@code String}.
	 *
	 * @param str the {@code String} to repeat.
	 * @param count the number of times the {@code str} has to be repeared.
	 *
	 * @return the created {@code String}.
	 *
	 * @throws IllegalArgumentException if {@code count} is negative.
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String repeat(String str, int count)
	{
		Parameters.checkCondition(count >= 0);
		StringBuilder sb = new StringBuilder(str.length() * count);
		for (int i = 0; i < count; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * Reverses the given {@code String}.
	 *
	 * @param str the {@code String} to reverse.
	 *
	 * @return the reversed {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String reverse(String str)
	{
		return new StringBuilder(str).reverse().toString();
	}

	/**
	 * Randomly permutes the characters from the given {@code String}. This
	 * implementation uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param str the {@code String} to be shuffled.
	 *
	 * @return the shuffled {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String shuffle(String str)
	{
		return shuffle(str, PRNG);
	}

	/**
	 * Randomly permutes the characters from the given {@code String} using
	 * the specified source of randomness. All permutations occur with equal
	 * likelihood assuming that the source of randomness is fair. This
	 * implementation uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param str the {@code String} to be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled {@code String}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static String shuffle(String str, Random rnd)
	{
		return new String(XArrays.shuffle(str.toCharArray(), rnd));
	}

	/**
	 * Returns the new {@code String} obtained by stripping the first and
	 * last {@code n} characters from the given one.
	 *
	 * @param str the {@code String} to be stripped.
	 * @param n the number of characters to strip.
	 *
	 * @return the stripped {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IllegalArgumentException if {@code n} is negative.
	 */
	public static String strip(String str, int n)
	{
		return stripRight(stripLeft(str, n), n);
	}

	/**
	 * Returns the new {@code String} obtained by stripping the first
	 * {@code n} characters from the given one.
	 *
	 * @param str the {@code String} to be stripped.
	 * @param n the number of characters to strip.
	 *
	 * @return the stripped {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IllegalArgumentException if {@code n} is negative.
	 */
	public static String stripLeft(String str, int n)
	{
		Parameters.checkCondition(n >= 0);
		int start = Math.min(str.length(), n);
		return str.substring(start);
	}

	/**
	 * Returns the new {@code String} obtained by stripping the last
	 * {@code n} characters from the given one.
	 *
	 * @param str the {@code String} to be stripped.
	 * @param n the number of characters to strip.
	 *
	 * @return the stripped {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IllegalArgumentException if {@code n} is negative.
	 */
	public static String stripRight(String str, int n)
	{
		Parameters.checkCondition(n >= 0);
		int end = Math.max(0, str.length() - n);
		return str.substring(0, end);
	}

	/**
	 * Returns a copy of the given {@code String} with leading and trailing
	 * whitespaces omitted.
	 *
	 * @param str the {@code String} to trim.
	 *
	 * @return the trimmed {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String trim(String str)
	{
		return str.trim();
	}

	/**
	 * Returns a copy of the given {@code String} with leading whitespaces
	 * omitted.
	 *
	 * @param str the {@code String} to trim.
	 *
	 * @return the trimmed {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String trimLeft(String str)
	{
		int start = 0;
		for (char c : str.toCharArray()) {
			if (c != ' ') {
				break;
			}
			start++;
		}
		return str.substring(start);
	}

	/**
	 * Returns a copy of the given {@code String} with trailing whitespaces
	 * omitted.
	 *
	 * @param str the {@code String} to trim.
	 *
	 * @return the trimmed {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String trimRight(String str)
	{
		int end = str.length();
		for (int i = str.length() - 1; i >= 0; i--) {
			if (str.charAt(i) != ' ') {
				break;
			}
			end--;
		}
		return str.substring(0, end);
	}

	/**
	 * Returns a {@code String} of length at least {@code len} created by
	 * prepending as many copies of {@code padChar} as necessary to reach
	 * that length.
	 *
	 * @param str the {@code String} to pad.
	 * @param len the result's minimum length.
	 * @param padChar the padding character.
	 *
	 * @return the padded {@code String}.
	 *
	 * @throws IllegalArgumentException if {@code len} is negative.
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String padLeft(String str, int len, char padChar)
	{
		Parameters.checkCondition(len >= 0);
		StringBuilder sb = new StringBuilder(len);
		while (sb.length() < len - str.length()) {
			sb.append(padChar);
		}
		sb.append(str);
		return sb.toString();
	}

	/**
	 * Returns a {@code String} of length at least {@code len} created by
	 * appending as many copies of {@code padChar} as necessary to reach
	 * that length.
	 *
	 * @param str the {@code String} to pad.
	 * @param len the result's minimum length.
	 * @param padChar the padding character.
	 *
	 * @return the padded {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IllegalArgumentException if {@code len} is negative.
	 */
	public static String padRight(String str, int len, char padChar)
	{
		Parameters.checkNotNull(str);
		Parameters.checkCondition(len >= 0);
		StringBuilder sb = new StringBuilder(len);
		sb.append(str);
		while (sb.length() < len) {
			sb.append(padChar);
		}
		return sb.toString();
	}

	/**
	 * Quotes the given {@code String} with double quotes. If the given
	 * {@code String} has multiple quotes, only one of them is kept.
	 *
	 * @param str the {@code String} to quote.
	 *
	 * @return the quoted {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String quote(String str)
	{
		return new StringBuilder(str.length() + 2)
			.append("\"").append(unquote(str)).append("\"")
			.toString();
	}

	/**
	 * Unquotes the given {@code String}, that is, this method removes any
	 * leading or trailing double quotes. If the given {@code String} is not
	 * quoted, returns it unmodified.
	 *
	 * @param str the {@code String} to unquote.
	 *
	 * @return the unquoted {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String unquote(String str)
	{
		String unquoted = str;
		while (unquoted.startsWith("\"")) {
			unquoted = unquoted.substring(1);
		}
		while (unquoted.endsWith("\"")) {
			unquoted = unquoted.substring(0, unquoted.length() - 1);
		}
		return unquoted;
	}

	/**
	 * Counts the occurrences of the substring {@code sub} in {@code str}.
	 *
	 * @param str {@code String} to search in.
	 * @param sub {@code String} to search for.
	 *
	 * @return the number of occurrences of {@code sub} in {@code str}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static int countOccurrences(String str, String sub)
	{
		Parameters.checkNotNull(str);
		int n = 0;
		if (!sub.isEmpty()) {
			int start = 0;
			while ((start = str.indexOf(sub, start)) != -1) {
				start += sub.length();
				n++;
			}
		}
		return n;
	}

	private Strings()
	{
		/* ... */
	}
}
