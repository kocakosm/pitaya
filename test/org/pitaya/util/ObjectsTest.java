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

		long[] a1 = new long[] {1, 2, 3};
		long[] a2 = new long[] {1, 2, 3};
		int[] b1 = new int[] {1, 2, 3};
		int[] b2 = new int[] {1, 2, 3};
		short[] c1 = new short[] {1, 2, 3};
		short[] c2 = new short[] {1, 2, 3};
		char[] d1 = new char[] {1, 2, 3};
		char[] d2 = new char[] {1, 2, 3};
		byte[] e1 = new byte[] {1, 2, 3};
		byte[] e2 = new byte[] {1, 2, 3};
		boolean[] f1 = new boolean[] {false, true, false};
		boolean[] f2 = new boolean[] {false, true, false};
		float[] g1 = new float[] {1, 2, 3};
		float[] g2 = new float[] {1, 2, 3};
		double[] h1 = new double[] {1, 2, 3};
		double[] h2 = new double[] {1, 2, 3};
		String[] i1 = new String[] {"Hey", "Jude"};
		String[] i2 = new String[] {"Hey", "Jude"};

		assertTrue(Objects.equals(a1, a2));
		assertFalse(Objects.equals(a2, b1));
		assertTrue(Objects.equals(b1, b2));
		assertFalse(Objects.equals(b2, c1));
		assertTrue(Objects.equals(c1, c2));
		assertFalse(Objects.equals(c2, d1));
		assertTrue(Objects.equals(d1, d2));
		assertFalse(Objects.equals(d2, e1));
		assertTrue(Objects.equals(e1, e2));
		assertFalse(Objects.equals(e2, f1));
		assertTrue(Objects.equals(f1, f2));
		assertFalse(Objects.equals(f2, g1));
		assertTrue(Objects.equals(g1, g2));
		assertFalse(Objects.equals(g2, h1));
		assertTrue(Objects.equals(h1, h2));
		assertFalse(Objects.equals(h2, i1));
		assertTrue(Objects.equals(i1, i2));
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
		assertEquals("null", Objects.toString(null));
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
	public void testToStringWithDefaultValue()
	{
		assertEquals(null, Objects.toString(null, null));
		assertEquals("", Objects.toString(null, ""));
		assertEquals("Hello", Objects.toString("Hello", ""));
	}

	@Test
	public void testToStringBuilder()
	{
		assertEquals("Test (1, 2)",
			Objects.toStringBuilder("Test")
				.append(1L).append(2).toString());
		assertEquals("Test (1, A)",
			Objects.toStringBuilder("Test")
				.append((short) 1).append('A').toString());
		assertEquals("Test (1, true)",
			Objects.toStringBuilder("Test")
				.append((byte) 1).append(true).toString());
		assertEquals("Test (1.0, 2.0)",
			Objects.toStringBuilder("Test")
				.append((float) 1.0).append(2.0).toString());
		assertEquals("ObjectsTest (Hello, World)",
			Objects.toStringBuilder(this)
				.append("Hello").append("World").toString());
		assertEquals("Test (one=1, two=2)",
			Objects.toStringBuilder("Test")
				.append("one", 1L).append("two", 2).toString());
		assertEquals("Test (one=1, A=A)",
			Objects.toStringBuilder("Test")
				.append("one", (short) 1).append("A", 'A').toString());
		assertEquals("Test (one=1, T=true)",
			Objects.toStringBuilder("Test")
				.append("one", (byte) 1).append("T", true).toString());
		assertEquals("Test (one=1.0, two=2.0)",
			Objects.toStringBuilder("Test")
				.append("one", (float) 1.0).append("two", 2.0).toString());
		assertEquals("ObjectsTest (h=Hello, w=World)",
			Objects.toStringBuilder(this)
				.append("h", "Hello").append("w", "World").toString());
		assertEquals("Test ([1, 2, 3])",
			Objects.toStringBuilder("Test")
				.append(new int[] {1, 2, 3}).toString());
	}
}
