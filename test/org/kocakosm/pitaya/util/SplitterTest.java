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
 * {@link Splitter}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class SplitterTest
{
	@Test
	public void testSplitWithDefaultSplitter()
	{
		Splitter splitter = Splitter.on(",");
		assertEquals(asList(""), splitter.split(""));
		assertEquals(asList("1", "2", "3"), splitter.split("1,2,3"));
		assertEquals(asList("1", " 2", "", "3 "), splitter.split("1, 2,,3 "));
	}

	@Test
	public void testSplitIgnoringEmptyStrings()
	{
		Splitter splitter = Splitter.on(",").ignoreEmptyStrings();
		assertEquals(asList(), splitter.split(""));
		assertEquals(asList(), splitter.split(",,"));
		assertEquals(asList("1", "2", "3"), splitter.split("1,2,,3"));
	}

	@Test
	public void testSplitWithEmptyStringReplacement()
	{
		Splitter splitter = Splitter.on(",").replaceEmptyStringWith("X");
		assertEquals(asList("1", "X", "3"), splitter.split("1,,3"));
	}

	@Test
	public void testSplitWithLimit()
	{
		Splitter splitter = Splitter.on(",").limit(2);
		assertEquals(asList("1", "2,3"), splitter.split("1,2,3"));
	}

	@Test
	public void testSplitAndTrimResults()
	{
		Splitter splitter = Splitter.on(",").trimResults();
		assertEquals(asList("1", "2", "", "3"), splitter.split(" 1 ,2 , , 3"));
	}

	@Test
	public void testSplitIgnoringPrefix()
	{
		Splitter splitter = Splitter.on(", ").ignorePrefix("[");
		assertEquals(asList("1", "2", "3"), splitter.split("[1, 2, 3"));
	}

	@Test
	public void testSplitIgnoringSuffix()
	{
		Splitter splitter = Splitter.on(", ").ignoreSuffix("]");
		assertEquals(asList("1", "2", "3"), splitter.split("1, 2, 3]"));
	}
}
