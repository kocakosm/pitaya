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

import static org.kocakosm.pitaya.util.XArrays.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.junit.Test;

/**
 * {@link XArrays}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class XArraysTest
{
	private static final Random PRNG = new Random();

	@Test
	public void testLongArrayIsEmptyOrNull()
	{
		assertTrue(isNullOrEmpty(new long[0]));
		assertTrue(isNullOrEmpty((long[]) null));
		assertFalse(isNullOrEmpty(new long[] {1, 2, 3}));
	}

	@Test
	public void testIntArrayIsEmptyOrNull()
	{
		assertTrue(isNullOrEmpty(new int[0]));
		assertTrue(isNullOrEmpty((int[]) null));
		assertFalse(isNullOrEmpty(new int[] {1, 2, 3}));
	}

	@Test
	public void testShortArrayIsEmptyOrNull()
	{
		assertTrue(isNullOrEmpty(new short[0]));
		assertTrue(isNullOrEmpty((short[]) null));
		assertFalse(isNullOrEmpty(new short[] {1, 2, 3}));
	}

	@Test
	public void testCharArrayIsEmptyOrNull()
	{
		assertTrue(isNullOrEmpty(new char[0]));
		assertTrue(isNullOrEmpty((char[]) null));
		assertFalse(isNullOrEmpty(new char[] {1, 2, 3}));
	}

	@Test
	public void testByteArrayIsEmptyOrNull()
	{
		assertTrue(isNullOrEmpty(new byte[0]));
		assertTrue(isNullOrEmpty((byte[]) null));
		assertFalse(isNullOrEmpty(new byte[] {1, 2, 3}));
	}

	@Test
	public void testBooleanArrayIsEmptyOrNull()
	{
		assertTrue(isNullOrEmpty(new boolean[0]));
		assertTrue(isNullOrEmpty((boolean[]) null));
		assertFalse(isNullOrEmpty(new boolean[] {true, false}));
	}

	@Test
	public void testFloatArrayIsEmptyOrNull()
	{
		assertTrue(isNullOrEmpty(new float[0]));
		assertTrue(isNullOrEmpty((float[]) null));
		assertFalse(isNullOrEmpty(new float[] {1, 2, 3}));
	}

	@Test
	public void testDoubleArrayIsEmptyOrNull()
	{
		assertTrue(isNullOrEmpty(new double[0]));
		assertTrue(isNullOrEmpty((double[]) null));
		assertFalse(isNullOrEmpty(new double[] {1, 2, 3}));
	}

	@Test
	public void testObjectArrayIsEmptyOrNull()
	{
		assertTrue(isNullOrEmpty(new Object[0]));
		assertTrue(isNullOrEmpty((Object[]) null));
		assertFalse(isNullOrEmpty(new String[] {"1", "2"}));
	}

	@Test
	public void testConcatLongArrays()
	{
		long[] a = new long[] {1, 2, 3};
		long[] b = new long[] {4, 5, 6};
		long[] concat = new long[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, concat(a, b));
	}

	@Test
	public void testConcatIntArrays()
	{
		int[] a = new int[] {1, 2, 3};
		int[] b = new int[] {4, 5, 6};
		int[] concat = new int[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, concat(a, b));
	}

	@Test
	public void testConcatShortArrays()
	{
		short[] a = new short[] {1, 2, 3};
		short[] b = new short[] {4, 5, 6};
		short[] concat = new short[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, concat(a, b));
	}

	@Test
	public void testConcatCharArrays()
	{
		char[] a = new char[] {1, 2, 3};
		char[] b = new char[] {4, 5, 6};
		char[] concat = new char[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, concat(a, b));
	}

	@Test
	public void testConcatByteArrays()
	{
		byte[] a = new byte[] {1, 2, 3};
		byte[] b = new byte[] {4, 5, 6};
		byte[] concat = new byte[] {1, 2, 3, 4, 5, 6};
		assertArrayEquals(concat, concat(a, b));
	}

	@Test
	public void testConcatBooleanArrays()
	{
		boolean[] a = new boolean[] {true, false};
		boolean[] b = new boolean[] {true, true, false};
		boolean[] concat = new boolean[] {true, false, true, true, false};
		assertTrue(Arrays.equals(concat, concat(a, b)));
	}

	@Test
	public void testConcatFloatArrays()
	{
		float[] a = new float[] {1, 2, 3};
		float[] b = new float[] {4, 5, 6};
		float[] concat = new float[] {1, 2, 3, 4, 5, 6};
		assertTrue(Arrays.equals(concat, concat(a, b)));
	}

	@Test
	public void testConcatDoubleArrays()
	{
		double[] a = new double[] {1, 2, 3};
		double[] b = new double[] {4, 5, 6};
		double[] concat = new double[] {1, 2, 3, 4, 5, 6};
		assertTrue(Arrays.equals(concat, concat(a, b)));
	}

	@Test
	public void testConcatObjectArrays()
	{
		Object[] a = new Object[] {"a", "b", "c"};
		Object[] b = new Object[] {"d", "e", "f"};
		Object[] concat = new Object[] {"a", "b", "c", "d", "e", "f"};
		assertArrayEquals(concat, concat(a, b));
	}

	@Test
	public void testRangeCopyOfLongArray()
	{
		long[] a = new long[] {1, 2, 3, 4, 5};
		assertArrayEquals(new long[] {2, 3, 4}, copyOf(a, 1, 3));
		assertArrayEquals(new long[] {5, 0, 0}, copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfIntArray()
	{
		int[] a = new int[] {1, 2, 3, 4, 5};
		assertArrayEquals(new int[] {2, 3, 4}, copyOf(a, 1, 3));
		assertArrayEquals(new int[] {5, 0, 0}, copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfShortArray()
	{
		short[] a = new short[] {1, 2, 3, 4, 5};
		assertArrayEquals(new short[] {2, 3, 4}, copyOf(a, 1, 3));
		assertArrayEquals(new short[] {5, 0, 0}, copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfCharArray()
	{
		char[] a = new char[] {1, 2, 3, 4, 5};
		assertArrayEquals(new char[] {2, 3, 4}, copyOf(a, 1, 3));
		assertArrayEquals(new char[] {5, 0, 0}, copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfByteArray()
	{
		byte[] a = new byte[] {1, 2, 3, 4, 5};
		assertArrayEquals(new byte[] {2, 3, 4}, copyOf(a, 1, 3));
		assertArrayEquals(new byte[] {5, 0, 0}, copyOf(a, 4, 3));
	}

	@Test
	public void testRangeCopyOfBooleanArray()
	{
		boolean[] a = new boolean[] {false, true, true, false};
		assertTrue(Arrays.equals(new boolean[] {true, true, false}, copyOf(a, 1, 3)));
		assertTrue(Arrays.equals(new boolean[] {false, false, false}, copyOf(a, 3, 3)));
	}

	@Test
	public void testRangeCopyOfFloatArray()
	{
		float[] a = new float[] {1, 2, 3, 4, 5};
		assertTrue(Arrays.equals(new float[] {2, 3, 4}, copyOf(a, 1, 3)));
		assertTrue(Arrays.equals(new float[] {5, 0, 0}, copyOf(a, 4, 3)));
	}

	@Test
	public void testRangeCopyOfDoubleArray()
	{
		double[] a = new double[] {1, 2, 3, 4, 5};
		assertTrue(Arrays.equals(new double[] {2, 3, 4}, copyOf(a, 1, 3)));
		assertTrue(Arrays.equals(new double[] {5, 0, 0}, copyOf(a, 4, 3)));
	}

	@Test
	public void testRangeCopyOfObjectArray()
	{
		Object[] a = new Object[] {"a", "b", "c", "d", "e"};
		assertArrayEquals(new Object[] {"b", "c", "d"}, copyOf(a, 1, 3));
		assertArrayEquals(new Object[] {"e", null, null}, copyOf(a, 4, 3));
	}

	@Test
	public void testCopyOfLongArray()
	{
		long[] a = new long[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, copyOf(a));
	}

	@Test
	public void testCopyOfIntArray()
	{
		int[] a = new int[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, copyOf(a));
	}
	@Test
	public void testCopyOfShortArray()
	{
		short[] a = new short[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, copyOf(a));
	}

	@Test
	public void testCopyOfCharArray()
	{
		char[] a = new char[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, copyOf(a));
	}

	@Test
	public void testCopyOfByteArray()
	{
		byte[] a = new byte[] {1, 2, 3, 4, 5};
		assertArrayEquals(a, copyOf(a));
	}

	@Test
	public void testCopyOfBooleanArray()
	{
		boolean[] a = new boolean[] {false, true, true, false};
		assertTrue(Arrays.equals(a, copyOf(a)));
	}

	@Test
	public void testCopyOfFloatArray()
	{
		float[] a = new float[] {1, 2, 3, 4, 5};
		assertTrue(Arrays.equals(a, copyOf(a)));
	}

	@Test
	public void testCopyOfDoubleArray()
	{
		double[] a = new double[] {1, 2, 3, 4, 5};
		assertTrue(Arrays.equals(a, copyOf(a)));
	}

	@Test
	public void testCopyOfObjectArray()
	{
		Object[] a = new Object[] {"a", "b", "c", "d", "e"};
		assertArrayEquals(a, copyOf(a));
	}

	@Test
	public void testReverseLongArray()
	{
		assertArrayEquals(new long[0], reverse(new long[0]));

		long[] a = new long[] {-2, -1, 0, 1, 2};
		assertArrayEquals(new long[] {2, 1, 0, -1, -2}, reverse(a));
		assertArrayEquals(new long[] {-2, -1, 0, 1, 2}, a);
	}

	@Test
	public void testReverseIntArray()
	{
		assertArrayEquals(new int[0], reverse(new int[0]));

		int[] a = new int[] {-2, -1, 0, 1, 2};
		assertArrayEquals(new int[] {2, 1, 0, -1, -2}, reverse(a));
		assertArrayEquals(new int[] {-2, -1, 0, 1, 2}, a);
	}

	@Test
	public void testReverseShortArray()
	{
		assertArrayEquals(new short[0], reverse(new short[0]));

		short[] a = new short[] {-2, -1, 0, 1, 2};
		assertArrayEquals(new short[] {2, 1, 0, -1, -2}, reverse(a));
		assertArrayEquals(new short[] {-2, -1, 0, 1, 2}, a);
	}

	@Test
	public void testReverseCharArray()
	{
		assertArrayEquals(new char[0], reverse(new char[0]));

		char[] a = new char[] {0, 1, 2, 3, 4};
		assertArrayEquals(new char[] {4, 3, 2, 1, 0}, reverse(a));
		assertArrayEquals(new char[] {0, 1, 2, 3, 4}, a);
	}

	@Test
	public void testReverseByteArray()
	{
		assertArrayEquals(new byte[0], reverse(new byte[0]));

		byte[] a = new byte[] {-2, -1, 0, 1, 2};
		assertArrayEquals(new byte[] {2, 1, 0, -1, -2}, reverse(a));
		assertArrayEquals(new byte[] {-2, -1, 0, 1, 2}, a);
	}

	@Test
	public void testReverseBooleanArray()
	{
		assertTrue(Arrays.equals(new boolean[0], reverse(new boolean[0])));

		boolean[] a = new boolean[] {true, true, false, true, false};
		assertTrue(Arrays.equals(new boolean[] {false, true, false, true, true}, reverse(a)));
		assertTrue(Arrays.equals(new boolean[] {true, true, false, true, false}, a));
	}

	@Test
	public void testReverseFloatArray()
	{
		assertTrue(Arrays.equals(new float[0], reverse(new float[0])));

		float[] a = new float[] {-2, -1, 0, 1, 2};
		assertTrue(Arrays.equals(new float[] {2, 1, 0, -1, -2}, reverse(a)));
		assertTrue(Arrays.equals(new float[] {-2, -1, 0, 1, 2}, a));
	}

	@Test
	public void testReverseDoubleArray()
	{
		assertTrue(Arrays.equals(new double[0], reverse(new double[0])));

		double[] a = new double[] {-2, -1, 0, 1, 2};
		assertTrue(Arrays.equals(new double[] {2, 1, 0, -1, -2}, reverse(a)));
		assertTrue(Arrays.equals(new double[] {-2, -1, 0, 1, 2}, a));
	}

	@Test
	public void testReverseObjectArray()
	{
		assertArrayEquals(new Object[0], reverse(new Object[0]));

		Object[] a = new Object[] {"a", "b", "c", "d"};
		assertArrayEquals(new Object[] {"d", "c", "b", "a"}, reverse(a));
		assertArrayEquals(new Object[] {"a", "b", "c", "d"}, a);
	}

	@Test
	public void testRotateLongArray()
	{
		assertArrayEquals(new long[0], rotate(new long[0], 15));

		long[] a = new long[] {-2, -1, 0, 1, 2};
		assertArrayEquals(a, rotate(a, 0));
		assertArrayEquals(a, rotate(a, -a.length));
		assertArrayEquals(a, rotate(a, a.length * 5));
		assertArrayEquals(new long[] {2, -2, -1, 0, 1}, rotate(a, 1));
		assertArrayEquals(new long[] {1, 2, -2, -1, 0}, rotate(a, 2));
		assertArrayEquals(new long[] {0, 1, 2, -2, -1}, rotate(a, 3));
		assertArrayEquals(new long[] {-1, 0, 1, 2, -2}, rotate(a, 4));
		assertArrayEquals(new long[] {2, -2, -1, 0, 1}, rotate(a, 6));
		assertArrayEquals(new long[] {-1, 0, 1, 2, -2}, rotate(a, -1));
	}

	@Test
	public void testRotateIntArray()
	{
		assertArrayEquals(new int[0], rotate(new int[0], 15));

		int[] a = new int[] {-2, -1, 0, 1, 2};
		assertArrayEquals(a, rotate(a, 0));
		assertArrayEquals(a, rotate(a, -a.length));
		assertArrayEquals(a, rotate(a, a.length * 5));
		assertArrayEquals(new int[] {2, -2, -1, 0, 1}, rotate(a, 1));
		assertArrayEquals(new int[] {1, 2, -2, -1, 0}, rotate(a, 2));
		assertArrayEquals(new int[] {0, 1, 2, -2, -1}, rotate(a, 3));
		assertArrayEquals(new int[] {-1, 0, 1, 2, -2}, rotate(a, 4));
		assertArrayEquals(new int[] {2, -2, -1, 0, 1}, rotate(a, 6));
		assertArrayEquals(new int[] {-1, 0, 1, 2, -2}, rotate(a, -1));
	}

	@Test
	public void testRotateShortArray()
	{
		assertArrayEquals(new short[0], rotate(new short[0], 15));

		short[] a = new short[] {-2, -1, 0, 1, 2};
		assertArrayEquals(a, rotate(a, 0));
		assertArrayEquals(a, rotate(a, -a.length));
		assertArrayEquals(a, rotate(a, a.length * 5));
		assertArrayEquals(new short[] {2, -2, -1, 0, 1}, rotate(a, 1));
		assertArrayEquals(new short[] {1, 2, -2, -1, 0}, rotate(a, 2));
		assertArrayEquals(new short[] {0, 1, 2, -2, -1}, rotate(a, 3));
		assertArrayEquals(new short[] {-1, 0, 1, 2, -2}, rotate(a, 4));
		assertArrayEquals(new short[] {2, -2, -1, 0, 1}, rotate(a, 6));
		assertArrayEquals(new short[] {-1, 0, 1, 2, -2}, rotate(a, -1));
	}

	@Test
	public void testRotateCharArray()
	{
		assertArrayEquals(new char[0], rotate(new char[0], 15));

		char[] a = new char[] {'a', 'b', 'c', 'd'};
		assertArrayEquals(a, rotate(a, 0));
		assertArrayEquals(a, rotate(a, -a.length));
		assertArrayEquals(a, rotate(a, a.length * 5));
		assertArrayEquals(new char[] {'d', 'a', 'b', 'c'}, rotate(a, 1));
		assertArrayEquals(new char[] {'c', 'd', 'a', 'b'}, rotate(a, 2));
		assertArrayEquals(new char[] {'b', 'c', 'd', 'a'}, rotate(a, 3));
		assertArrayEquals(new char[] {'d', 'a', 'b', 'c'}, rotate(a, 5));
		assertArrayEquals(new char[] {'b', 'c', 'd', 'a'}, rotate(a, -1));
	}

	@Test
	public void testRotateByteArray()
	{
		assertArrayEquals(new byte[0], rotate(new byte[0], 15));

		byte[] a = new byte[] {-2, -1, 0, 1, 2};
		assertArrayEquals(a, rotate(a, 0));
		assertArrayEquals(a, rotate(a, -a.length));
		assertArrayEquals(a, rotate(a, a.length * 5));
		assertArrayEquals(new byte[] {2, -2, -1, 0, 1}, rotate(a, 1));
		assertArrayEquals(new byte[] {1, 2, -2, -1, 0}, rotate(a, 2));
		assertArrayEquals(new byte[] {0, 1, 2, -2, -1}, rotate(a, 3));
		assertArrayEquals(new byte[] {-1, 0, 1, 2, -2}, rotate(a, 4));
		assertArrayEquals(new byte[] {2, -2, -1, 0, 1}, rotate(a, 6));
		assertArrayEquals(new byte[] {-1, 0, 1, 2, -2}, rotate(a, -1));
	}

	@Test
	public void testRotateBooleanArray()
	{
		assertTrue(Arrays.equals(new boolean[0], rotate(new boolean[0], 15)));

		boolean[] a = new boolean[] {true, true, false, true};
		assertTrue(Arrays.equals(a, rotate(a, 0)));
		assertTrue(Arrays.equals(a, rotate(a, -a.length)));
		assertTrue(Arrays.equals(a, rotate(a, a.length * 5)));
		assertTrue(Arrays.equals(new boolean[] {true, true, true, false}, rotate(a, 1)));
		assertTrue(Arrays.equals(new boolean[] {false, true, true, true}, rotate(a, 2)));
		assertTrue(Arrays.equals(new boolean[] {true, false, true, true}, rotate(a, 3)));
		assertTrue(Arrays.equals(new boolean[] {true, true, true, false}, rotate(a, 5)));
		assertTrue(Arrays.equals(new boolean[] {true, false, true, true}, rotate(a, -1)));
	}

	@Test
	public void testRotateFloatArray()
	{
		assertTrue(Arrays.equals(new float[0], rotate(new float[0], 15)));

		float[] a = new float[] {-2, -1, 0, 1, 2};
		assertTrue(Arrays.equals(a, rotate(a, 0)));
		assertTrue(Arrays.equals(a, rotate(a, -a.length)));
		assertTrue(Arrays.equals(a, rotate(a, a.length * 5)));
		assertTrue(Arrays.equals(new float[] {2, -2, -1, 0, 1}, rotate(a, 1)));
		assertTrue(Arrays.equals(new float[] {1, 2, -2, -1, 0}, rotate(a, 2)));
		assertTrue(Arrays.equals(new float[] {0, 1, 2, -2, -1}, rotate(a, 3)));
		assertTrue(Arrays.equals(new float[] {-1, 0, 1, 2, -2}, rotate(a, 4)));
		assertTrue(Arrays.equals(new float[] {2, -2, -1, 0, 1}, rotate(a, 6)));
		assertTrue(Arrays.equals(new float[] {-1, 0, 1, 2, -2}, rotate(a, -1)));
	}

	@Test
	public void testRotateDoubleArray()
	{
		assertTrue(Arrays.equals(new double[0], rotate(new double[0], 15)));

		double[] a = new double[] {-2, -1, 0, 1, 2};
		assertTrue(Arrays.equals(a, rotate(a, 0)));
		assertTrue(Arrays.equals(a, rotate(a, -a.length)));
		assertTrue(Arrays.equals(a, rotate(a, a.length * 5)));
		assertTrue(Arrays.equals(new double[] {2, -2, -1, 0, 1}, rotate(a, 1)));
		assertTrue(Arrays.equals(new double[] {1, 2, -2, -1, 0}, rotate(a, 2)));
		assertTrue(Arrays.equals(new double[] {0, 1, 2, -2, -1}, rotate(a, 3)));
		assertTrue(Arrays.equals(new double[] {-1, 0, 1, 2, -2}, rotate(a, 4)));
		assertTrue(Arrays.equals(new double[] {2, -2, -1, 0, 1}, rotate(a, 6)));
		assertTrue(Arrays.equals(new double[] {-1, 0, 1, 2, -2}, rotate(a, -1)));
	}

	@Test
	public void testRotateObjectArray()
	{
		assertArrayEquals(new Object[0], rotate(new Object[0], 15));

		Object[] a = new Object[]{"a", "b", "c", "d"};
		assertArrayEquals(a, rotate(a, 0));
		assertArrayEquals(a, rotate(a, -a.length));
		assertArrayEquals(a, rotate(a, a.length * 5));
		assertArrayEquals(new Object[]{"d", "a", "b", "c"}, rotate(a, 1));
		assertArrayEquals(new Object[]{"c", "d", "a", "b"}, rotate(a, 2));
		assertArrayEquals(new Object[]{"b", "c", "d", "a"}, rotate(a, 3));
		assertArrayEquals(new Object[]{"d", "a", "b", "c"}, rotate(a, 5));
		assertArrayEquals(new Object[]{"b", "c", "d", "a"}, rotate(a, -1));
	}

	@Test
	public void testShuffleLongArray()
	{
		long[] a = new long[100];
		for (int i = 0; i < a.length; i++) {
			a[i] = PRNG.nextLong();
		}
		long[] shuffled = shuffle(a);
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
		int[] shuffled = shuffle(a);
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
		short[] shuffled = shuffle(a);
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
		char[] shuffled = shuffle(a);
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
		byte[] shuffled = shuffle(a);
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
		boolean[] shuffled = shuffle(a);
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
		float[] shuffled = shuffle(a);
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
		double[] shuffled = shuffle(a);
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
		Object[] shuffled = shuffle(a);
		assertFalse(Arrays.equals(a, shuffled));
		Arrays.sort(a);
		Arrays.sort(shuffled);
		assertArrayEquals(a, shuffled);
	}

	@Test
	public void testSortLongArray()
	{
		long[] a = new long[] {3, 1, 5, 4, 2};
		long[] sorted = new long[] {1, 2, 3, 4, 5};
		assertNotSame(a, sort(a));
		assertArrayEquals(sorted, sort(a));
	}

	@Test
	public void testSortIntArray()
	{
		int[] a = new int[] {3, 1, 5, 4, 2};
		int[] sorted = new int[] {1, 2, 3, 4, 5};
		assertNotSame(a, sort(a));
		assertArrayEquals(sorted, sort(a));
	}
	@Test
	public void testSortShortArray()
	{
		short[] a = new short[] {3, 1, 5, 4, 2};
		short[] sorted = new short[] {1, 2, 3, 4, 5};
		assertNotSame(a, sort(a));
		assertArrayEquals(sorted, sort(a));
	}

	@Test
	public void testSortCharArray()
	{
		char[] a = new char[] {3, 1, 5, 4, 2};
		char[] sorted = new char[] {1, 2, 3, 4, 5};
		assertNotSame(a, sort(a));
		assertArrayEquals(sorted, sort(a));
	}

	@Test
	public void testSortByteArray()
	{
		byte[] a = new byte[] {3, 1, 5, 4, 2};
		byte[] sorted = new byte[] {1, 2, 3, 4, 5};
		assertNotSame(a, sort(a));
		assertArrayEquals(sorted, sort(a));
	}

	@Test
	public void testSortFloatArray()
	{
		float[] a = new float[] {3, 1, 5, 4, 2};
		float[] sorted = new float[] {1, 2, 3, 4, 5};
		assertNotSame(a, sort(a));
		assertTrue(Arrays.equals(sorted, sort(a)));
	}

	@Test
	public void testSortDoubleArray()
	{
		double[] a = new double[] {3, 1, 5, 4, 2};
		double[] sorted = new double[] {1, 2, 3, 4, 5};
		assertNotSame(a, sort(a));
		assertTrue(Arrays.equals(sorted, sort(a)));
	}

	@Test
	public void testSortObjectArray()
	{
		String[] a = new String[] {"d", "a", "e", "c", "b"};
		String[] sorted = new String[] {"a", "b", "c", "d", "e"};
		assertNotSame(a, sort(a));
		assertArrayEquals(sorted, sort(a));
	}

	@Test
	public void testSortObjectArrayWithComparator()
	{
		Comparator<String> c = new Comparator<String>()
		{
			@Override
			public int compare(String s1, String s2)
			{
				int c = s1.compareTo(s2);
				return c < 0 ? 1 : c > 0 ? -1 : 0;
			}
		};
		String[] a = new String[] {"d", "a", "e", "c", "b"};
		String[] sorted = new String[] {"e", "d", "c", "b", "a"};
		assertNotSame(a, sort(a, c));
		assertArrayEquals(sorted, sort(a, c));
	}

	@Test
	public void testToPrimitiveLong()
	{
		assertNull(XArrays.toPrimitive((Long[]) null));

		long[] p = new long[] {1L, 2L, 3L};
		Long[] w = new Long[] {1L, 2L, 3L};
		assertArrayEquals(p, XArrays.toPrimitive(w));
	}

	@Test
	public void testToPrimitiveInt()
	{
		assertNull(XArrays.toPrimitive((Integer[]) null));

		int[] p = new int[] {1, 2, 3};
		Integer[] w = new Integer[] {1, 2, 3};
		assertArrayEquals(p, XArrays.toPrimitive(w));
	}

	@Test
	public void testToPrimitiveShort()
	{
		assertNull(XArrays.toPrimitive((Short[]) null));

		short[] p = new short[] {1, 2, 3};
		Short[] w = new Short[] {1, 2, 3};
		assertArrayEquals(p, XArrays.toPrimitive(w));
	}

	@Test
	public void testToPrimitiveChar()
	{
		assertNull(XArrays.toPrimitive((Character[]) null));

		char[] p = new char[] {'a', 'b', 'c'};
		Character[] w = new Character[] {'a', 'b', 'c'};
		assertArrayEquals(p, XArrays.toPrimitive(w));
	}

	@Test
	public void testToPrimitiveByte()
	{
		assertNull(XArrays.toPrimitive((Byte[]) null));

		byte[] p = new byte[] {1, 2, 3};
		Byte[] w = new Byte[] {1, 2, 3};
		assertArrayEquals(p, XArrays.toPrimitive(w));
	}

	@Test
	public void testToPrimitiveBoolean()
	{
		assertNull(XArrays.toPrimitive((Boolean[]) null));

		boolean[] p = new boolean[] {true, false, true};
		Boolean[] w = new Boolean[] {true, false, true};
		assertTrue(Arrays.equals(p, XArrays.toPrimitive(w)));
	}

	@Test
	public void testToPrimitiveFloat()
	{
		assertNull(XArrays.toPrimitive((Float[]) null));

		float[] p = new float[] {1f, 2f, 3f};
		Float[] w = new Float[] {1f, 2f, 3f};
		assertTrue(Arrays.equals(p, XArrays.toPrimitive(w)));
	}

	@Test
	public void testToPrimitiveDouble()
	{
		assertNull(XArrays.toPrimitive((Double[]) null));

		double[] p = new double[] {1d, 2d, 3d};
		Double[] w = new Double[] {1d, 2d, 3d};
		assertTrue(Arrays.equals(p, XArrays.toPrimitive(w)));
	}

	@Test
	public void testToWrapperLong()
	{
		assertNull(XArrays.toWrapper((long[]) null));

		long[] p = new long[] {1L, 2L, 3L};
		Long[] w = new Long[] {1L, 2L, 3L};
		assertArrayEquals(w, XArrays.toWrapper(p));
	}

	@Test
	public void testToWrapperInt()
	{
		assertNull(XArrays.toWrapper((int[]) null));

		int[] p = new int[] {1, 2, 3};
		Integer[] w = new Integer[] {1, 2, 3};
		assertArrayEquals(w, XArrays.toWrapper(p));
	}

	@Test
	public void testToWrapperShort()
	{
		assertNull(XArrays.toWrapper((short[]) null));

		short[] p = new short[] {1, 2, 3};
		Short[] w = new Short[] {1, 2, 3};
		assertArrayEquals(w, XArrays.toWrapper(p));
	}

	@Test
	public void testToWrapperChar()
	{
		assertNull(XArrays.toWrapper((char[]) null));

		char[] p = new char[] {'a', 'b', 'c'};
		Character[] w = new Character[] {'a', 'b', 'c'};
		assertArrayEquals(w, XArrays.toWrapper(p));
	}

	@Test
	public void testToWrapperByte()
	{
		assertNull(XArrays.toWrapper((byte[]) null));

		byte[] p = new byte[] {1, 2, 3};
		Byte[] w = new Byte[] {1, 2, 3};
		assertArrayEquals(w, XArrays.toWrapper(p));
	}

	@Test
	public void testToWrapperBoolean()
	{
		assertNull(XArrays.toWrapper((boolean[]) null));

		boolean[] p = new boolean[] {true, false, true};
		Boolean[] w = new Boolean[] {true, false, true};
		assertTrue(Arrays.equals(w, XArrays.toWrapper(p)));
	}

	@Test
	public void testToWrapperFloat()
	{
		assertNull(XArrays.toWrapper((float[]) null));

		float[] p = new float[] {1f, 2f, 3f};
		Float[] w = new Float[] {1f, 2f, 3f};
		assertTrue(Arrays.equals(w, XArrays.toWrapper(p)));
	}

	@Test
	public void testToWrapperDouble()
	{
		assertNull(XArrays.toWrapper((double[]) null));

		double[] p = new double[] {1d, 2d, 3d};
		Double[] w = new Double[] {1d, 2d, 3d};
		assertTrue(Arrays.equals(w, XArrays.toWrapper(p)));
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
		String[] a = new String[] {"a", "b", "c", "d"};
		assertEquals(Arrays.toString(a), XArrays.toString(a));
	}
}
