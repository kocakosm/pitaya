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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
	 * Abbreviates the given {@link String} using "..." so that the returned
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
	 * Abbreviates the given {@link String} using the specified ellipsis so
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
	 * Returns an empty {@link String} if the given one is {@code null}, or
	 * returns the (unmodified) parameter otherwise.
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
	 * Returns a pseudo-random {@link String} of the specified size using
	 * characters from the given array.
	 *
	 * @param length the length of the {@link String} to produce.
	 * @param chars the characters to use to build the {@link String}.
	 *
	 * @return a pseudo-random {@link String}.
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
	 * Returns a random {@link String} using the given characters and the
	 * specified source of randomness. All characters have equal likelihood
	 * to appear in the resulting {@link String} assuming that the source of
	 * randomness is fair.
	 *
	 * @param length the length of the {@link String} to produce.
	 * @param rnd the source of randomness to use.
	 * @param chars the characters to use to build the {@link String}.
	 *
	 * @return a random {@link String}.
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

	/**
	 * Reverses the given {@link String}.
	 *
	 * @param str the {@link String} to reverse.
	 *
	 * @return the reversed {@link String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String reverse(String str)
	{
		return new StringBuilder(str).reverse().toString();
	}

	/**
	 * Randomly permutes the characters from the given {@link String}. This
	 * implementation uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param str the {@link String} to be shuffled.
	 *
	 * @return the shuffled {@link String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String shuffle(String str)
	{
		return shuffle(str, PRNG);
	}

	/**
	 * Randomly permutes the characters from the given {@link String} using
	 * the specified source of randomness. All permutations occur with equal
	 * likelihood assuming that the source of randomness is fair. This
	 * implementation uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param str the {@link String} to be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled {@link String}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static String shuffle(String str, Random rnd)
	{
		return new String(XArrays.shuffle(str.toCharArray(), rnd));
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
			while (true) {
				start = str.indexOf(sub, start);
				if (start == -1) {
					break;
				}
				start += sub.length();
				n++;
			}
		}
		return n;
	}

	/**
	 * Returns a new {@code Joiner} that will use the specified separator,
	 * no prefix, no suffix and {@code "null"} as default replacement for
	 * {@code null} values. Note that the returned {@code Joiner} instance
	 * is immutable.
	 *
	 * @param separator the separator.
	 *
	 * @return the created {@code Joiner}.
	 */
	public static Joiner joinWith(char separator)
	{
		return joinWith(Character.toString(separator));
	}

	/**
	 * Returns a new {@code Joiner} that will use the specified separator,
	 * no prefix, no suffix and {@code "null"} as default replacement for
	 * {@code null} values. Note that the returned {@code Joiner} instance
	 * is immutable.
	 *
	 * @param separator the separator.
	 *
	 * @return the created {@code Joiner}.
	 *
	 * @throws NullPointerException if {@code separator} is {@code null}.
	 */
	public static Joiner joinWith(String separator)
	{
		return new DefaultJoiner(separator, "", "", false, false,
			"null", "", null);
	}

	/**
	 * Joins multiple {@code Strings} into a single one with a predefined
	 * separator and optional prefix and suffix.
	 */
	public static interface Joiner
	{
		/**
		 * Returns a new {@code Joiner} ignoring {@code null} values.
		 *
		 * @return a new {@code Joiner} ignoring {@code null} values.
		 */
		Joiner ignoreNulls();

		/**
		 * Returns a new {@code Joiner} ignoring empty {@code String}s.
		 *
		 * @return a new {@code Joiner} ignoring empty {@code String}s.
		 */
		Joiner ignoreEmptyStrings();

		/**
		 * Returns a new {@code Joiner} that will use the specified
		 * {@code String} as a replacement for {@code null} values.
		 *
		 * @param forNull the {@code String} to use for {@code null}s.
		 *
		 * @return a new {@code Joiner} that will use {@code forNull}
		 *	in replacement of {@code null} values.
		 *
		 * @throws NullPointerException if {@code forNull} is {@code null}.
		 */
		Joiner replaceNullWith(String forNull);

		/**
		 * Returns a new {@code Joiner} that will use the specified
		 * value as a replacement for empty {@code String}s.
		 *
		 * @param forEmpty the value to use for empty {@code String}s.
		 *
		 * @return a new {@code Joiner} that will use {@code forEmpty}
		 *	in replacement of empty {@code String}s.
		 *
		 * @throws NullPointerException if {@code forEmpty} is {@code null}.
		 */
		Joiner replaceEmptyStringWith(String forEmpty);

		/**
		 * Returns a new {@code Joiner} that will use the specified
		 * prefix when joining {@code String}s.
		 *
		 * @param prefix the prefix.
		 *
		 * @return a new {@code Joiner} that will use the given prefix.
		 *
		 * @throws NullPointerException if {@code prefix} is {@code null}.
		 */
		Joiner withPrefix(String prefix);

		/**
		 * Returns a new {@code Joiner} that will use the specified
		 * suffix when joining {@code String}s.
		 *
		 * @param suffix the suffix.
		 *
		 * @return a new {@code Joiner} that will use the given suffix.
		 *
		 * @throws NullPointerException if {@code suffix} is {@code null}.
		 */
		Joiner withSuffix(String suffix);

		/**
		 * Returns a new {@code MapJoiner} that will use the specified
		 * separator for key-value pairs.
		 *
		 * @param keyValueSeparator the key-value separator.
		 *
		 * @return a new {@code MapJoiner} that will use the specified
		 *	key-value separator.
		 *
		 * @throws NullPointerException if {@code keyValueSeparator} is
		 *	{@code null}.
		 */
		MapJoiner withKeyValueSeparator(String keyValueSeparator);

		/**
		 * Returns a {@code String} built from the given parts and the
		 * previously sepecified separator, prefix, suffix and default
		 * {@code null} values replacement.
		 *
		 * @param parts the parts to join.
		 *
		 * @return the created {@code String}.
		 *
		 * @throws NullPointerException if {@code parts} is {@code null}.
		 */
		String join(Object... parts);

		/**
		 * Returns a {@code String} built from the given parts and the
		 * previously sepecified separator, prefix, suffix and default
		 * {@code null} values replacement.
		 *
		 * @param parts the parts to join.
		 *
		 * @return the created {@code String}.
		 *
		 * @throws NullPointerException if {@code parts} is {@code null}.
		 */
		String join(Iterable<?> parts);
	}

	/**
	 * Joins {@code Map} content into a single {@code String} using
	 * predefined separators for entries and key-value pairs.
	 */
	public static interface MapJoiner extends Joiner
	{
		/**
		 * Returns a {@code String} representation of the specified
		 * {@code Map} using the previously configured separator and
		 * key-value separator.
		 *
		 * @param map the map to join.
		 *
		 * @return {@code map}'s {@code String} representation.
		 *
		 * @throws NullPointerException if {@code map} is {@code null}.
		 */
		String join(Map<?, ?> map);

		@Override
		MapJoiner ignoreNulls();

		@Override
		MapJoiner ignoreEmptyStrings();

		@Override
		MapJoiner replaceNullWith(String forNull);

		@Override
		MapJoiner replaceEmptyStringWith(String forEmpty);

		@Override
		MapJoiner withPrefix(String prefix);

		@Override
		MapJoiner withSuffix(String suffix);
	}

	private static final class DefaultJoiner implements MapJoiner
	{
		private final String separator;
		private final String prefix;
		private final String suffix;
		private final boolean ignoreNull;
		private final boolean ignoreEmpty;
		private final String forNull;
		private final String forEmpty;
		private final String keyValueSeparator;

		DefaultJoiner(String separator, String prefix, String suffix,
			boolean ignoreNull, boolean ignoreEmpty, String forNull,
			String forEmpty, String keyValueSeparator)
		{
			Parameters.checkNotNull(separator);
			Parameters.checkNotNull(prefix);
			Parameters.checkNotNull(suffix);
			Parameters.checkNotNull(forNull);
			Parameters.checkNotNull(forEmpty);
			this.ignoreNull = ignoreNull;
			this.ignoreEmpty = ignoreEmpty;
			this.separator = separator;
			this.prefix = prefix;
			this.suffix = suffix;
			this.forNull = forNull;
			this.forEmpty = forEmpty;
			this.keyValueSeparator = keyValueSeparator;
		}

		@Override
		public MapJoiner ignoreNulls()
		{
			return new DefaultJoiner(separator, prefix, suffix,
				true, ignoreEmpty, forNull, forEmpty,
				keyValueSeparator);
		}

		@Override
		public MapJoiner ignoreEmptyStrings()
		{
			return new DefaultJoiner(separator, prefix, suffix,
				ignoreNull, true, forNull, forEmpty,
				keyValueSeparator);
		}

		@Override
		public MapJoiner replaceNullWith(String forNull)
		{
			return new DefaultJoiner(separator, prefix, suffix,
				false, ignoreEmpty, forNull, forEmpty,
				keyValueSeparator);
		}

		@Override
		public MapJoiner replaceEmptyStringWith(String forEmpty)
		{
			return new DefaultJoiner(separator, prefix, suffix,
				ignoreNull, false, forNull, forEmpty,
				keyValueSeparator);
		}

		@Override
		public MapJoiner withPrefix(String prefix)
		{
			return new DefaultJoiner(separator, prefix, suffix,
				ignoreNull, ignoreEmpty, forNull, forEmpty,
				keyValueSeparator);
		}

		@Override
		public MapJoiner withSuffix(String suffix)
		{
			return new DefaultJoiner(separator, prefix, suffix,
				ignoreNull, ignoreEmpty, forNull, forEmpty,
				keyValueSeparator);
		}

		@Override
		public MapJoiner withKeyValueSeparator(String keyValueSeparator)
		{
			Parameters.checkNotNull(keyValueSeparator);
			return new DefaultJoiner(separator, prefix, suffix,
				ignoreNull, ignoreEmpty, forNull, forEmpty,
				keyValueSeparator);
		}

		@Override
		public String join(Object... parts)
		{
			return join(Arrays.asList(parts));
		}

		@Override
		public String join(Iterable<?> parts)
		{
			boolean first = true;
			StringBuilder sb = new StringBuilder(prefix);
			for (Object part : parts) {
				String s = format(part);
				if (s != null) {
					if (first) {
						first = false;
					} else {
						sb.append(separator);
					}
					sb.append(s);
				}
			}
			return sb.append(suffix).toString();
		}

		private String format(Object o)
		{
			if (ignoreNull && o == null) {
				return null;
			}
			String s = Objects.toString(o, forNull);
			if (ignoreEmpty && s.isEmpty()) {
				return null;
			}
			return s.isEmpty() ? forEmpty : s;
		}

		@Override
		public String join(Map<?, ?> map)
		{
			List<String> parts = new ArrayList<String>(map.size());
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				parts.add(join(entry));
			}
			return join(parts);
		}

		private String join(Map.Entry<?, ?> entry)
		{
			String k = Objects.toString(entry.getKey(), forNull);
			String v = Objects.toString(entry.getValue(), forNull);
			return (k.isEmpty() ? forEmpty : k) + keyValueSeparator
				+ (v.isEmpty() ? forEmpty : v);
		}
	}

	private Strings()
	{
		/* ... */
	}
}
