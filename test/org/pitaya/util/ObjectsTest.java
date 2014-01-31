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

import static org.junit.Assert.*;

import java.util.Arrays;

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

		long[] a = new long[] {1, 2, 3};
		int[] b = new int[] {1, 2, 3};
		short[] c = new short[] {1, 2, 3};
		char[] d = new char[] {1, 2, 3};
		byte[] e = new byte[] {1, 2, 3};
		boolean[] f = new boolean[] {false, true, false};
		float[] g = new float[] {1, 2, 3};
		double[] h = new double[] {1, 2, 3};
		String[] i = new String[] {"Hey", "Jude"};

		assertTrue(Objects.equals(a, a));
		assertFalse(Objects.equals(a, b));
		assertTrue(Objects.equals(b, b));
		assertFalse(Objects.equals(b, c));
		assertTrue(Objects.equals(c, c));
		assertFalse(Objects.equals(c, d));
		assertTrue(Objects.equals(d, d));
		assertFalse(Objects.equals(d, e));
		assertTrue(Objects.equals(e, e));
		assertFalse(Objects.equals(e, f));
		assertTrue(Objects.equals(f, f));
		assertFalse(Objects.equals(f, g));
		assertTrue(Objects.equals(g, g));
		assertFalse(Objects.equals(g, h));
		assertTrue(Objects.equals(h, h));
		assertFalse(Objects.equals(h, i));
		assertTrue(Objects.equals(i, i));
	}

	@Test
	public void testHashcode()
	{
		assertEquals(0, Objects.hashCode((Object) null));
		int h = Arrays.hashCode(new String[] {"Hey", "Hey"});
		assertEquals(h, Objects.hashCode("Hey", "Hey"));
	}

	@Test
	public void testToString()
	{
		assertEquals("", Objects.toString(null));
		assertEquals("Hey", Objects.toString("Hey"));
		long[] a = new long[] {1, 2, 3};
		assertEquals(Arrays.toString(a), Objects.toString(a));
		int[] b = new int[] {1, 2, 3};
		assertEquals(Arrays.toString(b), Objects.toString(b));
		short[] c = new short[] {1, 2, 3};
		assertEquals(Arrays.toString(c), Objects.toString(c));
		char[] d = new char[] {1, 2, 3};
		assertEquals(Arrays.toString(d), Objects.toString(d));
		byte[] e = new byte[] {1, 2, 3};
		assertEquals(Arrays.toString(e), Objects.toString(e));
		boolean[] f = new boolean[] {false, true, false};
		assertEquals(Arrays.toString(f), Objects.toString(f));
		float[] g = new float[] {1, 2, 3};
		assertEquals(Arrays.toString(g), Objects.toString(g));
		double[] h = new double[] {1, 2, 3};
		assertEquals(Arrays.toString(h), Objects.toString(h));
		String[] i = new String[] {"Hey", "Jude"};
		assertEquals(Arrays.toString(i), Objects.toString(i));
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
