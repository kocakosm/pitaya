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

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * {@link XObjects} unit tests.
 *
 * @author Osman KOCAK
 */
public final class XObjectsTest
{
	@Test
	public void testFirstNonNull()
	{
		assertNull(XObjects.firstNonNull(null, null));
		assertEquals("Hey", XObjects.firstNonNull(null, "Hey", null));
	}

	@Test
	public void testEquals()
	{
		assertTrue(XObjects.equals(null, null));
		assertTrue(XObjects.equals("Hey", "Hey"));
		assertFalse(XObjects.equals(null, "Hey"));
		assertFalse(XObjects.equals("Hey", null));

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
		byte[] e3 = new byte[] {1, 2, 3};
		boolean[] f1 = new boolean[] {false, true, false};
		boolean[] f2 = new boolean[] {false, true, false};
		float[] g1 = new float[] {1, 2, 3};
		float[] g2 = new float[] {1, 2, 3};
		double[] h1 = new double[] {1, 2, 3};
		double[] h2 = new double[] {1, 2, 3};
		String[] i1 = new String[] {"Hey", "Jude"};
		String[] i2 = new String[] {"Hey", "Jude"};
		String[] i3 = new String[] {"Hey", "Jude"};
		String[] i4 = new String[] {"Hey", "Jude", "!"};
		byte[][] j1 = new byte[][] {e1, e2};
		byte[][] j2 = new byte[][] {e3, e3};
		String[][] k1 = new String[][] {i1, i2};
		String[][] k2 = new String[][] {i1, i3};

		assertTrue(XObjects.equals(a1, a2));
		assertFalse(XObjects.equals(a2, b1));
		assertTrue(XObjects.equals(b1, b2));
		assertFalse(XObjects.equals(b2, c1));
		assertTrue(XObjects.equals(c1, c2));
		assertFalse(XObjects.equals(c2, d1));
		assertTrue(XObjects.equals(d1, d2));
		assertFalse(XObjects.equals(d2, e1));
		assertTrue(XObjects.equals(e1, e2));
		assertFalse(XObjects.equals(e2, f1));
		assertTrue(XObjects.equals(f1, f2));
		assertFalse(XObjects.equals(f2, g1));
		assertTrue(XObjects.equals(g1, g2));
		assertFalse(XObjects.equals(g2, h1));
		assertTrue(XObjects.equals(h1, h2));
		assertFalse(XObjects.equals(h2, i1));
		assertTrue(XObjects.equals(i1, i2));
		assertFalse(XObjects.equals(i1, i4));
		assertTrue(XObjects.equals(j1, j2));
		assertTrue(XObjects.equals(k1, k2));
	}

	@Test
	public void testHashcode()
	{
		assertEquals(0, XObjects.hashCode((Object) null));
		int h = Arrays.hashCode(new String[] {"Hey", "Hey"});
		assertEquals(h, XObjects.hashCode("Hey", "Hey"));
	}

	@Test
	public void testToString()
	{
		assertEquals("null", XObjects.toString(null));
		assertEquals("Hey", XObjects.toString("Hey"));
		long[] a = new long[] {1, 2, 3};
		assertEquals(Arrays.toString(a), XObjects.toString(a));
		int[] b = new int[] {1, 2, 3};
		assertEquals(Arrays.toString(b), XObjects.toString(b));
		short[] c = new short[] {1, 2, 3};
		assertEquals(Arrays.toString(c), XObjects.toString(c));
		char[] d = new char[] {1, 2, 3};
		assertEquals(Arrays.toString(d), XObjects.toString(d));
		byte[] e = new byte[] {1, 2, 3};
		assertEquals(Arrays.toString(e), XObjects.toString(e));
		boolean[] f = new boolean[] {false, true, false};
		assertEquals(Arrays.toString(f), XObjects.toString(f));
		float[] g = new float[] {1, 2, 3};
		assertEquals(Arrays.toString(g), XObjects.toString(g));
		double[] h = new double[] {1, 2, 3};
		assertEquals(Arrays.toString(h), XObjects.toString(h));
		String[] i = new String[] {"Hey", "Jude"};
		assertEquals(Arrays.toString(i), XObjects.toString(i));
	}

	@Test
	public void testToStringWithDefaultValue()
	{
		assertEquals(null, XObjects.toString(null, null));
		assertEquals("", XObjects.toString(null, ""));
		assertEquals("Hello", XObjects.toString("Hello", ""));
	}

	@Test
	public void testToStringBuilder()
	{
		assertEquals("Test (1, 2)",
			XObjects.toStringBuilder("Test")
				.append(1L).append(2).toString());
		assertEquals("Test (1, A)",
			XObjects.toStringBuilder("Test")
				.append((short) 1).append('A').toString());
		assertEquals("Test (1, true)",
			XObjects.toStringBuilder("Test")
				.append((byte) 1).append(true).toString());
		assertEquals("Test (1.0, 2.0)",
			XObjects.toStringBuilder("Test")
				.append((float) 1.0).append(2.0).toString());
		assertEquals("XObjectsTest (Hello, World)",
			XObjects.toStringBuilder(this)
				.append("Hello").append("World").toString());
		assertEquals("Test (one=1, two=2)",
			XObjects.toStringBuilder("Test")
				.append("one", 1L).append("two", 2).toString());
		assertEquals("Test (one=1, A=A)",
			XObjects.toStringBuilder("Test")
				.append("one", (short) 1).append("A", 'A').toString());
		assertEquals("Test (one=1, T=true)",
			XObjects.toStringBuilder("Test")
				.append("one", (byte) 1).append("T", true).toString());
		assertEquals("Test (one=1.0, two=2.0)",
			XObjects.toStringBuilder("Test")
				.append("one", (float) 1.0).append("two", 2.0).toString());
		assertEquals("XObjectsTest (h=Hello, w=World)",
			XObjects.toStringBuilder(this)
				.append("h", "Hello").append("w", "World").toString());
		assertEquals("Test ([1, 2, 3])",
			XObjects.toStringBuilder("Test")
				.append(new int[] {1, 2, 3}).toString());
	}
}
