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

package org.kocakosm.pitaya.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Splits {@code String}s using a predefined separator. Instances of this class
 * are immutable.
 *
 * @see Joiner
 *
 * @author Osman KOCAK
 */
public final class Splitter
{
	/**
	 * Returns a new {@code Splitter} that will use the specified separator
	 * to split {@code String}s.
	 *
	 * @param separator the separator.
	 *
	 * @return the created {@code Splitter}.
	 *
	 * @throws NullPointerException if {@code separator} is {@code null}.
	 */
	public static Splitter on(String separator)
	{
		return on(Pattern.compile(Pattern.quote(separator)));
	}

	/**
	 * Returns a new {@code Splitter} that will use the specified pattern
	 * to split {@code String}s.
	 *
	 * @param pattern the separator pattern.
	 *
	 * @return the created {@code Splitter}.
	 *
	 * @throws NullPointerException if {@code pattern} is {@code null}.
	 */
	public static Splitter on(Pattern pattern)
	{
		return new Splitter(-1, false, pattern, null, null, "", false);
	}

	private final int limit;
	private final boolean trim;
	private final Pattern pattern;
	private final String prefix;
	private final String suffix;
	private final String forEmpty;
	private final boolean ignoreEmptyStrings;

	private Splitter(int limit, boolean trim, Pattern pattern, String prefix,
		String suffix, String forEmpty, boolean ignoreEmptyStrings)
	{
		Parameters.checkNotNull(pattern);
		this.limit = limit;
		this.trim = trim;
		this.pattern = pattern;
		this.suffix = suffix;
		this.prefix = prefix;
		this.forEmpty = forEmpty;
		this.ignoreEmptyStrings = ignoreEmptyStrings;
	}

	/**
	 * Returns a new {@code Splitter} that will behave as this one except
	 * that it will limit the number of times the splitting pattern is
	 * applied. If {@code limit} is strictly positive, then the pattern will
	 * be applied at most {@code limit - 1} times. If {@code limit} is
	 * non-positive then the splitting pattern will be applied as many times
	 * as possible.
	 *
	 * @param limit the splitting limit.
	 *
	 * @return a new {@code Splitter} with the desired configuration.
	 */
	public Splitter limit(int limit)
	{
		return new Splitter(limit == 0 ? -1 : limit, trim, pattern,
			prefix, suffix, forEmpty, ignoreEmptyStrings);
	}

	/**
	 * Returns a new {@code Splitter} that will behave as this one except
	 * that it will trim splitting results.
	 *
	 * @return a new {@code Splitter} with the desired configuration.
	 */
	public Splitter trimResults()
	{
		return new Splitter(limit, true, pattern, prefix, suffix,
			forEmpty, ignoreEmptyStrings);
	}

	/**
	 * Returns a new {@code Splitter} that will behave as this one except
	 * that it will ignore the given prefix.
	 *
	 * @param prefix the prefix to ignore.
	 *
	 * @return a new {@code Splitter} with the desired configuration.
	 *
	 * @throws NullPointerException if {@code prefix} is {@code null}.
	 */
	public Splitter ignorePrefix(String prefix)
	{
		Parameters.checkNotNull(prefix);
		return new Splitter(limit, trim, pattern, prefix, suffix,
			forEmpty, ignoreEmptyStrings);
	}

	/**
	 * Returns a new {@code Splitter} that will behave as this one except
	 * that it will ignore the given suffix.
	 *
	 * @param suffix the suffix to ignore.
	 *
	 * @return a new {@code Splitter} with the desired configuration.
	 *
	 * @throws NullPointerException if {@code suffix} is {@code null}.
	 */
	public Splitter ignoreSuffix(String suffix)
	{
		Parameters.checkNotNull(suffix);
		return new Splitter(limit, trim, pattern, prefix, suffix,
			forEmpty, ignoreEmptyStrings);
	}

	/**
	 * Returns a new {@code Splitter} that will behave as this one except
	 * that it will ignore empty results.
	 *
	 * @return a new {@code Splitter} with the desired configuration.
	 */
	public Splitter ignoreEmptyStrings()
	{
		return new Splitter(limit, trim, pattern, prefix, suffix,
			forEmpty, true);
	}

	/**
	 * Returns a new {@code Splitter} that will behave as this one except
	 * that it will replace empty results by the specified {@code String}.
	 *
	 * @param forEmpty the value to use for empty {@code String}s.
	 *
	 * @return a new {@code Splitter} with the desired configuration.
	 */
	public Splitter replaceEmptyStringWith(String forEmpty)
	{
		return new Splitter(limit, trim, pattern, prefix, suffix,
			forEmpty, false);
	}

	/**
	 * Splits the given {@code String} into parts according to its
	 * configuration.
	 *
	 * @param s the {@code String} to split.
	 *
	 * @return the parts, as an unmodifiable {@code List}.
	 *
	 * @throws NullPointerException if {@code s} is {@code null}.
	 */
	public List<String> split(String s)
	{
		String[] parts = pattern.split(removePrefixAndSuffix(s), limit);
		List<String> results = new ArrayList<String>(parts.length);
		for (String part : parts) {
			String str = trim ? part.trim() : part;
			if (!str.isEmpty()) {
				results.add(str);
			} else if (!ignoreEmptyStrings) {
				results.add(forEmpty);
			}
		}
		return Collections.unmodifiableList(results);
	}

	private String removePrefixAndSuffix(String input)
	{
		String s = prefix != null && input.startsWith(prefix)
			? input.substring(prefix.length()) : input;
		return suffix != null && s.endsWith(suffix)
			? s.substring(0, s.length() - suffix.length()) : s;
	}
}
