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

import static org.pitaya.util.Booleans.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Booleans}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class BooleansTest
{
	@Test
	public void testAnd()
	{
		assertTrue(and(TRUE, TRUE, TRUE));
		assertFalse(and(TRUE, FALSE, TRUE));
	}

	@Test
	public void testOr()
	{
		assertTrue(or(FALSE, TRUE, FALSE));
		assertFalse(or(FALSE, FALSE, FALSE));
	}

	@Test
	public void testNot()
	{
		assertFalse(not(TRUE));
		assertTrue(not(FALSE));
	}

	@Test
	public void testXor()
	{
		assertFalse(xor(FALSE, FALSE, FALSE));
		assertFalse(xor(TRUE, TRUE, TRUE));
		assertFalse(xor(TRUE, FALSE, TRUE));
		assertTrue(xor(FALSE, TRUE, FALSE));
	}

	@Test
	public void testIsFalse()
	{
		assertFalse(isFalse(null));
		assertTrue(isFalse(FALSE));
		assertFalse(isFalse(TRUE));
	}

	@Test
	public void testIsNotFalse()
	{
		assertTrue(isNotFalse(null));
		assertFalse(isNotFalse(FALSE));
		assertTrue(isNotFalse(TRUE));
	}

	@Test
	public void testIsTrue()
	{
		assertFalse(isTrue(null));
		assertFalse(isTrue(FALSE));
		assertTrue(isTrue(TRUE));
	}

	@Test
	public void testIsNotTrue()
	{
		assertTrue(isNotTrue(null));
		assertTrue(isNotTrue(FALSE));
		assertFalse(isNotTrue(TRUE));
	}

	@Test
	public void testValueOfString()
	{
		assertTrue(valueOf("   T r Ue  "));
		assertTrue(valueOf("   1  "));
		assertTrue(valueOf("   O n  "));
		assertTrue(valueOf("  Y eS  "));
		assertFalse(valueOf(""));
	}

	@Test
	public void testValueOfInt()
	{
		assertFalse(valueOf(0));
		assertTrue(valueOf(-1));
		assertTrue(valueOf(2));
	}

	@Test
	public void testValueOfInteger()
	{
		assertFalse(valueOf(Integer.valueOf(0)));
		assertTrue(valueOf(Integer.valueOf(-1)));
		assertTrue(valueOf(Integer.valueOf(2)));
	}

	@Test
	public void testValueOfBoolean()
	{
		assertTrue(valueOf(true));
		assertFalse(valueOf(false));
	}

	@Test
	public void testToInteger()
	{
		assertEquals(Integer.valueOf(1), toInteger(true));
		assertEquals(Integer.valueOf(0), toInteger(false));

		assertEquals(Integer.valueOf(1), toInteger(TRUE));
		assertEquals(Integer.valueOf(0), toInteger(FALSE));
	}

	@Test
	public void testTostring()
	{
		assertEquals("true", Booleans.toString(true));
		assertEquals("false", Booleans.toString(false));
	}
}
