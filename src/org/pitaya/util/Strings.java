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

/**
 * {@code String} utilities.
 *
 * @author Osman KOCAK
 */
public final class Strings
{
	/**
	 * Abbreviates the given {@link String} using "..." so that the returned
	 * {@code String}'s length equals to {@code length}.
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
		Parameters.checkCondition(length > 3);
		if (str.length() <= length) {
			return str;
		}
		return str.substring(0, length - 3) + "...";
	}

	/**
	 * Returns whether the given {@link CharSequence} only contains space,
	 * '\n', '\r' or '\t' characters.
	 *
	 * @param sequence the {@code CharSequence} to test.
	 *
	 * @return whether the given {@link CharSequence} is blank.
	 *
	 * @throws NullPointerException if {@code sequence} is {@code null}.
	 */
	public static boolean isBlank(CharSequence sequence)
	{
		for (int i = 0; i < sequence.length(); i++) {
			char c = sequence.charAt(i);
			if (c != ' ' && c != '\r' && c != '\n' && c != '\t') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given {@link CharSequence} only contains space
	 * characters.
	 *
	 * @param sequence the {@code CharSequence} to test.
	 *
	 * @return whether the given {@link CharSequence} containes only spaces.
	 *
	 * @throws NullPointerException if {@code sequence} is {@code null}.
	 */
	public static boolean isWhiteSpace(CharSequence sequence)
	{
		for (int i = 0; i < sequence.length(); i++) {
			if (sequence.charAt(i) != ' ') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given {@link CharSequence} is empty.
	 *
	 * @param sequence the {@link CharSequence} to test.
	 *
	 * @return whether the given {@link CharSequence} is empty.
	 *
	 * @throws NullPointerException if {@code sequence} is {@code null}.
	 */
	public static boolean isEmpty(CharSequence sequence)
	{
		return sequence.length() == 0;
	}

	/**
	 * Returns whether the given {@link CharSequence} is {@code null} or
	 * empty.
	 *
	 * @param sequence the {@link CharSequence} to test.
	 *
	 * @return {@code true} if {@code sequence} is {@code null} or empty,
	 *	{@code false} otherwise.
	 */
	public static boolean isNullOrEmpty(CharSequence sequence)
	{
		return sequence == null || isEmpty(sequence);
	}

	/**
	 * Returns {@code null} if the given {@link String} is empty, returns
	 * the (unmodified) parameter otherwise.
	 *
	 * @param str the {@link String} to test.
	 *
	 * @return {@code null} if the given {@link String} is empty.
	 */
	public static String emptyToNull(String str)
	{
		return str == null ? null : str.isEmpty() ? null : str;
	}

	/**
	 * Returns an empty {@link String} if the given one is {@code null},
	 * returns (unmodified) parameter otherwise.
	 *
	 * @param str the {@link String} to test.
	 *
	 * @return an empty {@link String} if the given one is {@code null}.
	 */
	public static String nullToEmpty(String str)
	{
		return str == null ? "" : str;
	}

	/**
	 * Concatenates the given {@link String}s.
	 *
	 * @param strings the {@link String}s to concatenate.
	 *
	 * @return the concatenated {@link String}.
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
	 * Returns the new {@link String} obtained by stripping the first
	 * {@code n} characters from the given one.
	 *
	 * @param str the {@link String} to be stripped.
	 * @param n the number of characters to strip.
	 *
	 * @return the stripped {@link String}.
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
	 * Returns the new {@link String} obtained by stripping the last
	 * {@code n} characters from the given one.
	 *
	 * @param str the {@link String} to be stripped.
	 * @param n the number of characters to strip.
	 *
	 * @return the stripped {@link String}.
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
	 * Returns the new {@link String} obtained by stripping the first and
	 * last {@code n} characters from the given one.
	 *
	 * @param str the {@link String} to be stripped.
	 * @param n the number of characters to strip.
	 *
	 * @return the stripped {@link String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IllegalArgumentException if {@code n} is negative.
	 */
	public static String strip(String str, int n)
	{
		return stripRight(stripLeft(str, n), n);
	}

	/**
	 * Returns a copy of the given {@link String} with leading whitespaces
	 * omitted.
	 *
	 * @param str the {@link String} to trim.
	 *
	 * @return the trimmed {@link String}.
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
	 * Returns a copy of the given {@link String} with trailing whitespaces
	 * omitted.
	 *
	 * @param str the {@link String} to trim.
	 *
	 * @return the trimmed {@link String}.
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
	 * Returns a copy of the given {@link String} with leading and trailing
	 * whitespaces omitted.
	 *
	 * @param str the {@link String} to trim.
	 *
	 * @return the trimmed {@link String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String trim(String str)
	{
		return str.trim();
	}

	/**
	 * Reverses the given {@link String}.
	 *
	 * @param str the {@link String} to reverse.
	 *
	 * @return the reversed {@link String}.
	 */
	public static String reverse(String str)
	{
		return new StringBuilder(str).reverse().toString();
	}

	/**
	 * Returns a {@link String} of length at least {@code len} created by
	 * prepending as many copies of {@code padChar} as necessary to reach
	 * that length.
	 *
	 * @param str the {@link String} to pad.
	 * @param len the result's minimum length.
	 * @param padChar the padding character.
	 *
	 * @return the padded {@link String}.
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
	 * Returns a {@link String} of length at least {@code len} created by
	 * appending as many copies of {@code padChar} as necessary to reach
	 * that length.
	 *
	 * @param str the {@link String} to pad.
	 * @param len the result's minimum length.
	 * @param padChar the padding character.
	 *
	 * @return the padded {@link String}.
	 *
	 * @throws IllegalArgumentException if {@code len} is negative.
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String padRight(String str, int len, char padChar)
	{
		Parameters.checkCondition(len >= 0);
		Parameters.checkNotNull(str);
		StringBuilder sb = new StringBuilder(len);
		sb.append(str);
		while (sb.length() < len) {
			sb.append(padChar);
		}
		return sb.toString();
	}

	/**
	 * Returns the {@link String} obtained by repeating {@code count} times
	 * the given {@link String}.
	 *
	 * @param str the {@link String} to repeat.
	 * @param count the number of times the {@code str} has to be repeared.
	 *
	 * @return the created {@link String}.
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

	private Strings()
	{
		/* ... */
	}
}
