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
import java.util.Random;

import org.junit.Test;

/**
 * {@link XArrays}' unit tests.
 *
 * @author Osman KOCAK
 */
public class XArraysTest
{
	private static final Random PRNG = new Random();

	@Test
	public void testLongArrayIsEmptyOrNull()
	{
		assertTrue(XArrays.isNullOrEmpty(new long[0]));
		assertTrue(XArrays.isNullOrEmpty((long[]) null));
		assertFalse(XArrays.isNullOrEmpty(new long[] {1, 2, 3}));
	}

	@Test
	public void testIntArrayIsEmptyOrNull()
	{
		assertTrue(XArrays.isNullOrEmpty(new int[0]));
		assertTrue(XArrays.isNullOrEmpty((int[]) null));
		assertFalse(XArrays.isNullOrEmpty(new int[] {1, 2, 3}));
	}

	@Test
	public void testShortArrayIsEmptyOrNull()
	{
		assertTrue(XArrays.isNullOrEmpty(new short[0]));
		assertTrue(XArrays.isNullOrEmpty((short[]) null));
		assertFalse(XArrays.isNullOrEmpty(new short[] {1, 2, 3}));
	}

	@Test
	public void testCharArrayIsEmptyOrNull()
	{
		assertTrue(XArrays.isNullOrEmpty(new char[0]));
		assertTrue(XArrays.isNullOrEmpty((char[]) null));
		assertFalse(XArrays.isNullOrEmpty(new char[] {1, 2, 3}));
	}

	@Test
	public void testByteArrayIsEmptyOrNull()
	{
		assertTrue(XArrays.isNullOrEmpty(new byte[0]));
		assertTrue(XArrays.isNullOrEmpty((byte[]) null));
		assertFalse(XArrays.isNullOrEmpty(new byte[] {1, 2, 3}));
	}

	@Test
	public void testBooleanArrayIsEmptyOrNull()
	{
		assertTrue(XArrays.isNullOrEmpty(new boolean[0]));
		assertTrue(XArrays.isNullOrEmpty((boolean[]) null));
		assertFalse(XArrays.isNullOrEmpty(new boolean[] {true, false}));
	}

	@Test
	public void testFloatArrayIsEmptyOrNull()
	{
		assertTrue(XArrays.isNullOrEmpty(new float[0]));
		assertTrue(XArrays.isNullOrEmpty((float[]) null));
		assertFalse(XArrays.isNullOrEmpty(new float[] {1, 2, 3}));
	}

	@Test
	public void testDoubleArrayIsEmptyOrNull()
	{
		assertTrue(XArrays.isNullOrEmpty(new double[0]));
		assertTrue(XArrays.isNullOrEmpty((double[]) null));
		assertFalse(XArrays.isNullOrEmpty(new double[] {1, 2, 3}));
	}

	@Test
	public void testObjectArrayIsEmptyOrNull()
	{
		assertTrue(XArrays.isNullOrEmpty(new Object[0]));
		assertTrue(XArrays.isNullOrEmpty((Object[]) null));
		assertFalse(XArrays.isNullOrEmpty(new Object[] {"1", "2"}));
	}

	@Test
	public void testConcatLongArrays()
	{
		long[] a = new long[] {1, 2, 3};
		long[] b = new long[] {4, 5, 6};
		long[] concat = new long[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, XArrays.concat(a, b));
	}

	@Test
	public void testConcatIntArrays()
	{
		int[] a = new int[] {1, 2, 3};
		int[] b = new int[] {4, 5, 6};
		int[] concat = new int[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, XArrays.concat(a, b));
	}

	@Test
	public void testConcatShortArrays()
	{
		short[] a = new short[] {1, 2, 3};
		short[] b = new short[] {4, 5, 6};
		short[] concat = new short[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, XArrays.concat(a, b));
	}

	@Test
	public void testConcatCharArrays()
	{
		char[] a = new char[] {1, 2, 3};
		char[] b = new char[] {4, 5, 6};
		char[] concat = new char[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, XArrays.concat(a, b));
	}

	@Test
	public void testConcatByteArrays()
	{
		byte[] a = new byte[] {1, 2, 3};
		byte[] b = new byte[] {4, 5, 6};
		byte[] concat = new byte[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, XArrays.concat(a, b));
	}

	@Test
	public void testConcatBooleanArrays()
	{
		boolean[] a = new boolean[] {true, false};
		boolean[] b = new boolean[] {true, true, false};
		boolean[] concat = new boolean[] {true, false, true, true, false};
		assertTrue(Arrays.equals(concat, XArrays.concat(a, b)));
	}

	@Test
	public void testConcatFloatArrays()
	{
		float[] a = new float[] {1, 2, 3};
		float[] b = new float[] {4, 5, 6};
		float[] concat = new float[] {1, 2, 3, 4, 5, 6};
		assertTrue(Arrays.equals(concat, XArrays.concat(a, b)));
	}

	@Test
	public void testConcatDoubleArrays()
	{
		double[] a = new double[] {1, 2, 3};
		double[] b = new double[] {4, 5, 6};
		double[] concat = new double[] {1, 2, 3, 4, 5, 6};
		assertTrue(Arrays.equals(concat, XArrays.concat(a, b)));
	}

	@Test
	public void testConcatObjectArrays()
	{
		Object[] a = new Object[] {"a", "b", "c"};
		Object[] b = new Object[] {"d", "e", "f"};
		Object[] concat = new Object[] {"a", "b", "c", "d", "e", "f"};
		assertArrayEquals(concat, XArrays.concat(a, b));
	}

	@Test
	public void testRangeCopyOfLongArray()
	{
		long[] a = new long[] {1, 2, 3, 4, 5};
		assertArrayEquals(new long[] {2, 3, 4}, XArrays.copyOf(a, 1, 3));
		assertArrayEquals(new long[] {5, 0, 0}, XArrays.copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfIntArray()
	{
		int[] a = new int[] {1, 2, 3, 4, 5};
		assertArrayEquals(new int[] {2, 3, 4}, XArrays.copyOf(a, 1, 3));
		assertArrayEquals(new int[] {5, 0, 0}, XArrays.copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfShortArray()
	{
		short[] a = new short[] {1, 2, 3, 4, 5};
		assertArrayEquals(new short[] {2, 3, 4}, XArrays.copyOf(a, 1, 3));
		assertArrayEquals(new short[] {5, 0, 0}, XArrays.copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfCharArray()
	{
		char[] a = new char[] {1, 2, 3, 4, 5};
		assertArrayEquals(new char[] {2, 3, 4}, XArrays.copyOf(a, 1, 3));
		assertArrayEquals(new char[] {5, 0, 0}, XArrays.copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfByteArray()
	{
		byte[] a = new byte[] {1, 2, 3, 4, 5};
		assertArrayEquals(new byte[] {2, 3, 4}, XArrays.copyOf(a, 1, 3));
		assertArrayEquals(new byte[] {5, 0, 0}, XArrays.copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfBooleanArray()
	{
		boolean[] a = new boolean[] {false, true, true, false};
		assertTrue(Arrays.equals(new boolean[] {true, true, false}, XArrays.copyOf(a, 1, 3)));
		assertTrue(Arrays.equals(new boolean[] {false, false, false}, XArrays.copyOf(a, 3, 3)));
	}

	@Test
	public void testRangeCopyOfFloatArray()
	{
		float[] a = new float[] {1, 2, 3, 4, 5};
		assertTrue(Arrays.equals(new float[] {2, 3, 4}, XArrays.copyOf(a, 1, 3)));
		assertTrue(Arrays.equals(new float[] {5, 0, 0}, XArrays.copyOf(a, 4, 3)));
	}

	@Test
	public void testRangeCopyOfDoubleArray()
	{
		double[] a = new double[] {1, 2, 3, 4, 5};
		assertTrue(Arrays.equals(new double[] {2, 3, 4}, XArrays.copyOf(a, 1, 3)));
		assertTrue(Arrays.equals(new double[] {5, 0, 0}, XArrays.copyOf(a, 4, 3)));
	}

	@Test
	public void testRangeCopyOfObjectArray()
	{
		Object[] a = new Object[] {"a", "b", "c", "d", "e"};
		assertArrayEquals(new Object[] {"b", "c", "d"}, XArrays.copyOf(a, 1, 3));
		assertArrayEquals(new Object[] {"e", null, null}, XArrays.copyOf(a, 4, 3));
	}

	@Test
	public void testCopyOfLongArray()
	{
		long[] a = new long[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, XArrays.copyOf(a));
	}

	@Test
	public void testCopyOfIntArray()
	{
		int[] a = new int[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, XArrays.copyOf(a));
	}
	@Test
	public void testCopyOfShortArray()
	{
		short[] a = new short[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, XArrays.copyOf(a));
	}

	@Test
	public void testCopyOfCharArray()
	{
		char[] a = new char[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, XArrays.copyOf(a));
	}

	@Test
	public void testCopyOfByteArray()
	{
		byte[] a = new byte[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, XArrays.copyOf(a));
	}

	@Test
	public void testCopyOfBooleanArray()
	{
		boolean[] a = new boolean[] {false, true, true, false};
		assertTrue(Arrays.equals(a, XArrays.copyOf(a)));
	}

	@Test
	public void testCopyOfFloatArray()
	{
		float[] a = new float[] {1, 2, 3, 4, 5};
		assertTrue(Arrays.equals(a, XArrays.copyOf(a)));
	}

	@Test
	public void testCopyOfDoubleArray()
	{
		double[] a = new double[] {1, 2, 3, 4, 5};
		assertTrue(Arrays.equals(a, XArrays.copyOf(a)));
	}

	@Test
	public void testCopyOfObjectArray()
	{
		Object[] a = new Object[] {"a", "b", "c", "d", "e"};
		assertArrayEquals(a, XArrays.copyOf(a));
	}

	@Test
	public void testSortLongArray()
	{
		long[] a = new long[] {3, 1, 5, 4, 2};
		long[] sorted = new long[] {1, 2, 3, 4, 5};
		assertNotSame(a, XArrays.sort(a));
		assertArrayEquals(sorted, XArrays.sort(a));
	}

	@Test
	public void testSortIntArray()
	{
		int[] a = new int[] {3, 1, 5, 4, 2};
		int[] sorted = new int[] {1, 2, 3, 4, 5};
		assertNotSame(a, XArrays.sort(a));
		assertArrayEquals(sorted, XArrays.sort(a));
	}
	@Test
	public void testSortShortArray()
	{
		short[] a = new short[] {3, 1, 5, 4, 2};
		short[] sorted = new short[] {1, 2, 3, 4, 5};
		assertNotSame(a, XArrays.sort(a));
		assertArrayEquals(sorted, XArrays.sort(a));
	}

	@Test
	public void testSortCharArray()
	{
		char[] a = new char[] {3, 1, 5, 4, 2};
		char[] sorted = new char[] {1, 2, 3, 4, 5};
		assertNotSame(a, XArrays.sort(a));
		assertArrayEquals(sorted, XArrays.sort(a));
	}

	@Test
	public void testSortByteArray()
	{
		byte[] a = new byte[] {3, 1, 5, 4, 2};
		byte[] sorted = new byte[] {1, 2, 3, 4, 5};
		assertNotSame(a, XArrays.sort(a));
		assertArrayEquals(sorted, XArrays.sort(a));
	}

	@Test
	public void testSortFloatArray()
	{
		float[] a = new float[] {3, 1, 5, 4, 2};
		float[] sorted = new float[] {1, 2, 3, 4, 5};
		assertNotSame(a, XArrays.sort(a));
		assertTrue(Arrays.equals(sorted, XArrays.sort(a)));
	}

	@Test
	public void testSortDoubleArray()
	{
		double[] a = new double[] {3, 1, 5, 4, 2};
		double[] sorted = new double[] {1, 2, 3, 4, 5};
		assertNotSame(a, XArrays.sort(a));
		assertTrue(Arrays.equals(sorted, XArrays.sort(a)));
	}

	@Test
	public void testSortObjectArray()
	{
		Object[] a = new Object[] {"d", "a", "e", "c", "b"};
		Object[] sorted = new Object[] {"a", "b", "c", "d", "e"};
		assertNotSame(a, XArrays.sort(a));
		assertArrayEquals(sorted, XArrays.sort(a));
	}

	@Test
	public void testShuffleLongArray()
	{
		long[] a = new long[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = PRNG.nextLong();
		}
		long[] shuffled = XArrays.shuffle(a);
		assertFalse(Arrays.equals(a, shuffled));
		Arrays.sort(a);
		Arrays.sort(shuffled);
		assertArrayEquals(a, shuffled);
	}

	@Test
	public void testShuffleIntArray()
	{
		int[] a = new int[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = PRNG.nextInt();
		}
		int[] shuffled = XArrays.shuffle(a);
		assertFalse(Arrays.equals(a, shuffled));
		Arrays.sort(a);
		Arrays.sort(shuffled);
		assertArrayEquals(a, shuffled);
	}

	@Test
	public void testShuffleShortArray()
	{
		short[] a = new short[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = (short) PRNG.nextInt();
		}
		short[] shuffled = XArrays.shuffle(a);
		assertFalse(Arrays.equals(a, shuffled));
		Arrays.sort(a);
		Arrays.sort(shuffled);
		assertArrayEquals(a, shuffled);
	}

	@Test
	public void testShuffleCharArray()
	{
		char[] a = new char[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) PRNG.nextInt();
		}
		char[] shuffled = XArrays.shuffle(a);
		assertFalse(Arrays.equals(a, shuffled));
		Arrays.sort(a);
		Arrays.sort(shuffled);
		assertArrayEquals(a, shuffled);
	}

	@Test
	public void testShuffleByteArray()
	{
		byte[] a = new byte[100];
		PRNG.nextBytes(a);
		byte[] shuffled = XArrays.shuffle(a);
		assertFalse(Arrays.equals(a, shuffled));
		Arrays.sort(a);
		Arrays.sort(shuffled);
		assertArrayEquals(a, shuffled);
	}

	@Test
	public void testShuffleBooleanArray()
	{
		boolean[] a = new boolean[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = PRNG.nextBoolean();
		}
		boolean[] shuffled = XArrays.shuffle(a);
		assertEquals(a.length, shuffled.length);
		assertFalse(Arrays.equals(a, shuffled));
	}

	@Test
	public void testShuffleFloatArray()
	{
		float[] a = new float[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = PRNG.nextFloat();
		}
		float[] shuffled = XArrays.shuffle(a);
		assertFalse(Arrays.equals(a, shuffled));
		Arrays.sort(a);
		Arrays.sort(shuffled);
		assertTrue(Arrays.equals(a, shuffled));
	}

	@Test
	public void testShuffleDoubleArray()
	{
		double[] a = new double[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = PRNG.nextDouble();
		}
		double[] shuffled = XArrays.shuffle(a);
		assertFalse(Arrays.equals(a, shuffled));
		Arrays.sort(a);
		Arrays.sort(shuffled);
		assertTrue(Arrays.equals(a, shuffled));
	}

	@Test
	public void testShuffleObjectArray()
	{
		Object[] a = new Object[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = PRNG.nextLong();
		}
		Object[] shuffled = XArrays.shuffle(a);
		assertFalse(Arrays.equals(a, shuffled));
		Arrays.sort(a);
		Arrays.sort(shuffled);
		assertArrayEquals(a, shuffled);
	}

	@Test
	public void testHashLongArray()
	{
		long[] a = new long[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.hashCode(a), XArrays.hashCode(a));
	}

	@Test
	public void testHashIntArray()
	{
		int[] a = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.hashCode(a), XArrays.hashCode(a));
	}

	@Test
	public void testHashShortArray()
	{
		short[] a = new short[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.hashCode(a), XArrays.hashCode(a));
	}

	@Test
	public void testHashCharArray()
	{
		char[] a = new char[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.hashCode(a), XArrays.hashCode(a));
	}

	@Test
	public void testHashByteArray()
	{
		byte[] a = new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.hashCode(a), XArrays.hashCode(a));
	}

	@Test
	public void testHashBooleanArray()
	{
		boolean[] a = new boolean[] {false, true, true, false};
		assertEquals(Arrays.hashCode(a), XArrays.hashCode(a));
	}

	@Test
	public void testHashFloatArray()
	{
		float[] a = new float[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.hashCode(a), XArrays.hashCode(a));
	}

	@Test
	public void testHashDoubleArray()
	{
		double[] a = new double[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.hashCode(a), XArrays.hashCode(a));
	}

	@Test
	public void testHashObjectArray()
	{
		Object[] a = new Object[] {"a", "b", "c", "d"};
		assertEquals(Arrays.hashCode(a), XArrays.hashCode(a));
	}

	@Test
	public void testToStringLongArray()
	{
		long[] a = new long[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}

	@Test
	public void testToStringIntArray()
	{
		int[] a = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}

	@Test
	public void testToStringShortArray()
	{
		short[] a = new short[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}

	@Test
	public void testToStringCharArray()
	{
		char[] a = new char[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}

	@Test
	public void testToStringByteArray()
	{
		byte[] a = new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}

	@Test
	public void testToStringBooleanArray()
	{
		boolean[] a = new boolean[] {false, true, true, false};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}

	@Test
	public void testToStringFloatArray()
	{
		float[] a = new float[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}

	@Test
	public void testToStringDoubleArray()
	{
		double[] a = new double[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}

	@Test
	public void testToStringObjectArray()
	{
		Object[] a = new Object[] {"a", "b", "c", "d"};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}
}
