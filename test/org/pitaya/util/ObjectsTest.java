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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Objects} unit tests.
 *
 * @author Osman KOCAK
 */
public final class ObjectsTest
{
	@Test
	public void testDefaultIfNull()
	{
		assertNull(Objects.defaultIfNull(null, null));
		assertEquals("default", Objects.defaultIfNull(null, "default"));
		assertEquals("Hey", Objects.defaultIfNull("Hey", "default"));
	}

	@Test
	public void testFirstNonNull()
	{
		assertNull(Objects.firstNonNull(null, null));
		assertEquals("Hey", Objects.firstNonNull(null, "Hey", null));
	}

	@Test
	public void testEquals()
	{
		assertTrue(Objects.equals(null, null));
		assertTrue(Objects.equals("Hey", "Hey"));
		assertFalse(Objects.equals(null, "Hey"));
		assertFalse(Objects.equals("Hey", null));
	}

	@Test
	public void testToString()
	{
		assertEquals("", Objects.toString(null));
		assertEquals("Hey", Objects.toString("Hey"));
	}

	@Test
	public void testToStringBuilder()
	{
		assertEquals("TestClass (values=[0, 1], Hello, World)",
			new TestClass().toString());
	}

	private static final class TestClass
	{
		private final int[] values = {0, 1};
		private final String str1 = "Hello";
		private final String str2 = "World";

		@Override
		public String toString()
		{
			return Objects.toStringBuilder(this)
				.append("values", values)
				.append(str1).append(str2)
				.toString();
		}
	}
}
