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

package org.kocakosm.pitaya.util;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * {@link Joiner}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class JoinerTest
{
	@Test
	public void testJoinWithDefaultJoiner()
	{
		Joiner joiner = Joiner.on(", ");
		assertEquals("", joiner.join());
		assertEquals("1, 2, 3", joiner.join(1, 2, 3));
		assertEquals("1, 2, 3", joiner.join(asList(1, 2, 3)));
		assertEquals("null, null", joiner.join("null", null));
	}

	@Test
	public void testJoinWithPrefixAndSuffix()
	{
		Joiner joiner = Joiner.on(", ").withPrefix("[").withSuffix("]");
		assertEquals("[]", joiner.join());
		assertEquals("[1, 2, 3]", joiner.join(1, 2, 3));
		assertEquals("[1, 2, 3]", joiner.join(asList(1, 2, 3)));
	}

	@Test
	public void testJoinTrimmingInputs()
	{
		Joiner joiner = Joiner.on(", ").trimInputs();
		assertEquals("1, 2, 3", joiner.join(" 1", " 2 ", "3 "));
		assertEquals("1, 2, 3", joiner.join(asList(" 1", " 2 ", "3 ")));
	}

	@Test
	public void testJoinIgnoringNullValues()
	{
		Joiner joiner = Joiner.on(", ").ignoreNulls();
		assertEquals("1, 2, 3", joiner.join(1, null, 2, 3, null));
		assertEquals("1, 2, 3", joiner.join(asList(1, null, 2, 3, null)));
	}

	@Test
	public void testJoinWithNullReplacement()
	{
		Joiner joiner = Joiner.on(", ").replaceNullWith("");
		assertEquals("one, , two", joiner.join("one", null, "two"));
		assertEquals("one, , two", joiner.join(asList("one", null, "two")));
	}

	@Test
	public void testJoinIgnoringEmptyStrings()
	{
		Joiner joiner = Joiner.on(", ").ignoreEmptyStrings();
		assertEquals("1, 2, 3", joiner.join(1, "", 2, 3, ""));
		assertEquals("1, 2, 3", joiner.join(asList("1", "", "2", "3")));
	}

	@Test
	public void testJoinWithEmptyStringReplacement()
	{
		Joiner joiner = Joiner.on(", ").replaceEmptyStringWith("X");
		assertEquals("1, X, 2", joiner.join("1", "", "2"));
		assertEquals("1, X, 2", joiner.join(asList("1", "", "2")));
	}
}
