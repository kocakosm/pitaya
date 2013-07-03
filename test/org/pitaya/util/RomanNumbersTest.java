/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2013 Osman KOCAK <kocakosm@gmail.com>                   *
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * {@link RomanNumbers} unit tests.
 *
 * @author Osman KOCAK
 */
public final class RomanNumbersTest
{
	@Test
	public void testToString()
	{
		assertEquals("I", RomanNumbers.toString(1));
		assertEquals("V", RomanNumbers.toString(5));
		assertEquals("X", RomanNumbers.toString(10));
		assertEquals("L", RomanNumbers.toString(50));
		assertEquals("C", RomanNumbers.toString(100));
		assertEquals("D", RomanNumbers.toString(500));
		assertEquals("M", RomanNumbers.toString(1000));
		assertEquals("XVI", RomanNumbers.toString(16));
		assertEquals("XIV", RomanNumbers.toString(14));
		assertEquals("DIX", RomanNumbers.toString(509));
		assertEquals("XCIX", RomanNumbers.toString(99));
		assertEquals("MDXV", RomanNumbers.toString(1515));
		assertEquals("MMII", RomanNumbers.toString(2002));
		assertEquals("MCMLXXV", RomanNumbers.toString(1975));
		assertEquals("MMMMCMXCIX", RomanNumbers.toString(4999));
		assertEquals("DCCCLXXXVIII", RomanNumbers.toString(888));
		assertEquals("MMMMDCCCLXXXVIII", RomanNumbers.toString(4888));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testToStringError1()
	{
		RomanNumbers.toString(0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testToStringError2()
	{
		RomanNumbers.toString(5000);
	}

	@Test
	public void testValueOf()
	{
		assertEquals(1, RomanNumbers.valueOf("I"));
		assertEquals(1, RomanNumbers.valueOf('I'));
		assertEquals(5, RomanNumbers.valueOf("V"));
		assertEquals(5, RomanNumbers.valueOf('V'));
		assertEquals(10, RomanNumbers.valueOf("X"));
		assertEquals(10, RomanNumbers.valueOf('X'));
		assertEquals(50, RomanNumbers.valueOf("L"));
		assertEquals(50, RomanNumbers.valueOf('L'));
		assertEquals(100, RomanNumbers.valueOf("C"));
		assertEquals(100, RomanNumbers.valueOf('C'));
		assertEquals(500, RomanNumbers.valueOf("D"));
		assertEquals(500, RomanNumbers.valueOf('D'));
		assertEquals(1000, RomanNumbers.valueOf("M"));
		assertEquals(1000, RomanNumbers.valueOf('M'));
		assertEquals(16, RomanNumbers.valueOf("XVI"));
		assertEquals(14, RomanNumbers.valueOf("XIV"));
		assertEquals(509, RomanNumbers.valueOf("DIX"));
		assertEquals(99, RomanNumbers.valueOf("XCIX"));
		assertEquals(1515, RomanNumbers.valueOf("MDXV"));
		assertEquals(2002, RomanNumbers.valueOf("MMII"));
		assertEquals(1975, RomanNumbers.valueOf("MCMLXXV"));
		assertEquals(4999, RomanNumbers.valueOf("MMMMCMXCIX"));
		assertEquals(888, RomanNumbers.valueOf("DCCCLXXXVIII"));
		assertEquals(4888, RomanNumbers.valueOf("MMMMDCCCLXXXVIII"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValueOfError1()
	{
		RomanNumbers.valueOf("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValueOfError2()
	{
		RomanNumbers.valueOf("IM");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValueOfError3()
	{
		RomanNumbers.valueOf("DD");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValueOfError4()
	{
		RomanNumbers.valueOf("LL");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValueOfError5()
	{
		RomanNumbers.valueOf("VV");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValueOfError6()
	{
		RomanNumbers.valueOf("XD");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValueOfError7()
	{
		RomanNumbers.valueOf("XM");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testValueOfError8()
	{
		RomanNumbers.valueOf("IC");
	}
}
