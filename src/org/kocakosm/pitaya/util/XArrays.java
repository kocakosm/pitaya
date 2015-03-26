/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2015 Osman KOCAK <kocakosm@gmail.com>                   *
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Various extra utilities for arrays.
 *
 * @see Arrays
 *
 * @author Osman KOCAK
 */
public final class XArrays
{
	private static final Random PRNG = new Random();

	/**
	 * Returns whether the given array is {@code null} or {@code empty},
	 * that is, returns {@code true} if {@code a} is {@code null} or empty
	 * and {@code false} otherwise.
	 *
	 * @param a the array to test.
	 *
	 * @return whether the given array is {@code null} or {@code empty}.
	 */
	public static boolean isNullOrEmpty(long[] a)
	{
		return a == null || a.length == 0;
	}

	/**
	 * Returns whether the given array is {@code null} or {@code empty},
	 * that is, returns {@code true} if {@code a} is {@code null} or empty
	 * and {@code false} otherwise.
	 *
	 * @param a the array to test.
	 *
	 * @return whether the given array is {@code null} or {@code empty}.
	 */
	public static boolean isNullOrEmpty(int[] a)
	{
		return a == null || a.length == 0;
	}

	/**
	 * Returns whether the given array is {@code null} or {@code empty},
	 * that is, returns {@code true} if {@code a} is {@code null} or empty
	 * and {@code false} otherwise.
	 *
	 * @param a the array to test.
	 *
	 * @return whether the given array is {@code null} or {@code empty}.
	 */
	public static boolean isNullOrEmpty(short[] a)
	{
		return a == null || a.length == 0;
	}

	/**
	 * Returns whether the given array is {@code null} or {@code empty},
	 * that is, returns {@code true} if {@code a} is {@code null} or empty
	 * and {@code false} otherwise.
	 *
	 * @param a the array to test.
	 *
	 * @return whether the given array is {@code null} or {@code empty}.
	 */
	public static boolean isNullOrEmpty(char[] a)
	{
		return a == null || a.length == 0;
	}

	/**
	 * Returns whether the given array is {@code null} or {@code empty},
	 * that is, returns {@code true} if {@code a} is {@code null} or empty
	 * and {@code false} otherwise.
	 *
	 * @param a the array to test.
	 *
	 * @return whether the given array is {@code null} or {@code empty}.
	 */
	public static boolean isNullOrEmpty(byte[] a)
	{
		return a == null || a.length == 0;
	}

	/**
	 * Returns whether the given array is {@code null} or {@code empty},
	 * that is, returns {@code true} if {@code a} is {@code null} or empty
	 * and {@code false} otherwise.
	 *
	 * @param a the array to test.
	 *
	 * @return whether the given array is {@code null} or {@code empty}.
	 */
	public static boolean isNullOrEmpty(boolean[] a)
	{
		return a == null || a.length == 0;
	}

	/**
	 * Returns whether the given array is {@code null} or {@code empty},
	 * that is, returns {@code true} if {@code a} is {@code null} or empty
	 * and {@code false} otherwise.
	 *
	 * @param a the array to test.
	 *
	 * @return whether the given array is {@code null} or {@code empty}.
	 */
	public static boolean isNullOrEmpty(float[] a)
	{
		return a == null || a.length == 0;
	}

	/**
	 * Returns whether the given array is {@code null} or {@code empty},
	 * that is, returns {@code true} if {@code a} is {@code null} or empty
	 * and {@code false} otherwise.
	 *
	 * @param a the array to test.
	 *
	 * @return whether the given array is {@code null} or {@code empty}.
	 */
	public static boolean isNullOrEmpty(double[] a)
	{
		return a == null || a.length == 0;
	}

	/**
	 * Returns whether the given array is {@code null} or {@code empty},
	 * that is, returns {@code true} if {@code a} is {@code null} or empty
	 * and {@code false} otherwise.
	 *
	 * @param a the array to test.
	 *
	 * @return whether the given array is {@code null} or {@code empty}.
	 */
	public static boolean isNullOrEmpty(Object[] a)
	{
		return a == null || a.length == 0;
	}

	/**
	 * Concatenates the given arrays into a single one.
	 *
	 * @param arrays the arrays to concatenate.
	 *
	 * @return the concatenated array.
	 *
	 * @throws NullPointerException if {@code arrays} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public static long[] concat(long[]... arrays)
	{
		int len = 0;
		for (long[] array : arrays) {
			len += array.length;
		}
		long[] concat = new long[len];
		int i = 0;
		for (long[] array : arrays) {
			System.arraycopy(array, 0, concat, i, array.length);
			i = array.length;
		}
		return concat;
	}

	/**
	 * Concatenates the given arrays into a single one.
	 *
	 * @param arrays the arrays to concatenate.
	 *
	 * @return the concatenated array.
	 *
	 * @throws NullPointerException if {@code arrays} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public static int[] concat(int[]... arrays)
	{
		int len = 0;
		for (int[] array : arrays) {
			len += array.length;
		}
		int[] concat = new int[len];
		int i = 0;
		for (int[] array : arrays) {
			System.arraycopy(array, 0, concat, i, array.length);
			i = array.length;
		}
		return concat;
	}

	/**
	 * Concatenates the given arrays into a single one.
	 *
	 * @param arrays the arrays to concatenate.
	 *
	 * @return the concatenated array.
	 *
	 * @throws NullPointerException if {@code arrays} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public static short[] concat(short[]... arrays)
	{
		int len = 0;
		for (short[] array : arrays) {
			len += array.length;
		}
		short[] concat = new short[len];
		int i = 0;
		for (short[] array : arrays) {
			System.arraycopy(array, 0, concat, i, array.length);
			i = array.length;
		}
		return concat;
	}

	/**
	 * Concatenates the given arrays into a single one.
	 *
	 * @param arrays the arrays to concatenate.
	 *
	 * @return the concatenated array.
	 *
	 * @throws NullPointerException if {@code arrays} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public static char[] concat(char[]... arrays)
	{
		int len = 0;
		for (char[] array : arrays) {
			len += array.length;
		}
		char[] concat = new char[len];
		int i = 0;
		for (char[] array : arrays) {
			System.arraycopy(array, 0, concat, i, array.length);
			i = array.length;
		}
		return concat;
	}

	/**
	 * Concatenates the given arrays into a single one.
	 *
	 * @param arrays the arrays to concatenate.
	 *
	 * @return the concatenated array.
	 *
	 * @throws NullPointerException if {@code arrays} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public static byte[] concat(byte[]... arrays)
	{
		int len = 0;
		for (byte[] array : arrays) {
			len += array.length;
		}
		byte[] concat = new byte[len];
		int i = 0;
		for (byte[] array : arrays) {
			System.arraycopy(array, 0, concat, i, array.length);
			i = array.length;
		}
		return concat;
	}

	/**
	 * Concatenates the given arrays into a single one.
	 *
	 * @param arrays the arrays to concatenate.
	 *
	 * @return the concatenated array.
	 *
	 * @throws NullPointerException if {@code arrays} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public static boolean[] concat(boolean[]... arrays)
	{
		int len = 0;
		for (boolean[] array : arrays) {
			len += array.length;
		}
		boolean[] concat = new boolean[len];
		int i = 0;
		for (boolean[] array : arrays) {
			System.arraycopy(array, 0, concat, i, array.length);
			i = array.length;
		}
		return concat;
	}

	/**
	 * Concatenates the given arrays into a single one.
	 *
	 * @param arrays the arrays to concatenate.
	 *
	 * @return the concatenated array.
	 *
	 * @throws NullPointerException if {@code arrays} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public static float[] concat(float[]... arrays)
	{
		int len = 0;
		for (float[] array : arrays) {
			len += array.length;
		}
		float[] concat = new float[len];
		int i = 0;
		for (float[] array : arrays) {
			System.arraycopy(array, 0, concat, i, array.length);
			i = array.length;
		}
		return concat;
	}

	/**
	 * Concatenates the given arrays into a single one.
	 *
	 * @param arrays the arrays to concatenate.
	 *
	 * @return the concatenated array.
	 *
	 * @throws NullPointerException if {@code arrays} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public static double[] concat(double[]... arrays)
	{
		int len = 0;
		for (double[] array : arrays) {
			len += array.length;
		}
		double[] concat = new double[len];
		int i = 0;
		for (double[] array : arrays) {
			System.arraycopy(array, 0, concat, i, array.length);
			i = array.length;
		}
		return concat;
	}

	/**
	 * Concatenates the given arrays into a single one.
	 *
	 * @param <T> the type of the elements in the arrays to concatenate.
	 * @param arrays the arrays to concatenate.
	 *
	 * @return the concatenated array.
	 *
	 * @throws IllegalArgumentException if {@code arrays} is empty.
	 * @throws NullPointerException if {@code arrays} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public static <T> T[] concat(T[]... arrays)
	{
		Parameters.checkCondition(arrays.length > 0);
		int len = 0;
		for (T[] array : arrays) {
			len += array.length;
		}
		T[] concat = newArray(arrays[0].getClass().getComponentType(), len);
		int i = 0;
		for (T[] array : arrays) {
			System.arraycopy(array, 0, concat, i, array.length);
			i = array.length;
		}
		return concat;
	}

	/**
	 * Copies the specified range of elements from the given array into a
	 * new array. The returned array will be padded with {@code 0}, if
	 * necessary, so that it has an exact length of {@code len}.
	 *
	 * @param a the source array.
	 * @param off the starting position in the source array.
	 * @param len the number of elements to copy (returned array's length).
	 *
	 * @return a new array containing the specified range of the source array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException if {@code off} is negative.
	 * @throws NegativeArraySizeException if {@code len} is negative.
	 */
	public static long[] copyOf(long[] a, int off, int len)
	{
		long[] copy = new long[len];
		if (len > a.length - off) {
			System.arraycopy(a, off, copy, 0, a.length - off);
		} else {
			System.arraycopy(a, off, copy, 0, len);
		}
		return copy;
	}

	/**
	 * Copies the specified range of elements from the given array into a
	 * new array. The returned array will be padded with {@code 0}, if
	 * necessary, so that it has an exact length of {@code len}.
	 *
	 * @param a the source array.
	 * @param off the starting position in the source array.
	 * @param len the number of elements to copy (returned array's length).
	 *
	 * @return a new array containing the specified range of the source array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException if {@code off} is negative.
	 * @throws NegativeArraySizeException if {@code len} is negative.
	 */
	public static int[] copyOf(int[] a, int off, int len)
	{
		int[] copy = new int[len];
		if (len > a.length - off) {
			System.arraycopy(a, off, copy, 0, a.length - off);
		} else {
			System.arraycopy(a, off, copy, 0, len);
		}
		return copy;
	}

	/**
	 * Copies the specified range of elements from the given array into a
	 * new array. The returned array will be padded with {@code 0}, if
	 * necessary, so that it has an exact length of {@code len}.
	 *
	 * @param a the source array.
	 * @param off the starting position in the source array.
	 * @param len the number of elements to copy (returned array's length).
	 *
	 * @return a new array containing the specified range of the source array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException if {@code off} is negative.
	 * @throws NegativeArraySizeException if {@code len} is negative.
	 */
	public static short[] copyOf(short[] a, int off, int len)
	{
		short[] copy = new short[len];
		if (len > a.length - off) {
			System.arraycopy(a, off, copy, 0, a.length - off);
		} else {
			System.arraycopy(a, off, copy, 0, len);
		}
		return copy;
	}

	/**
	 * Copies the specified range of elements from the given array into a
	 * new array. The returned array will be padded with {@code 0}, if
	 * necessary, so that it has an exact length of {@code len}.
	 *
	 * @param a the source array.
	 * @param off the starting position in the source array.
	 * @param len the number of elements to copy (returned array's length).
	 *
	 * @return a new array containing the specified range of the source array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException if {@code off} is negative.
	 * @throws NegativeArraySizeException if {@code len} is negative.
	 */
	public static char[] copyOf(char[] a, int off, int len)
	{
		char[] copy = new char[len];
		if (len > a.length - off) {
			System.arraycopy(a, off, copy, 0, a.length - off);
		} else {
			System.arraycopy(a, off, copy, 0, len);
		}
		return copy;
	}

	/**
	 * Copies the specified range of elements from the given array into a
	 * new array. The returned array will be padded with {@code 0}, if
	 * necessary, so that it has an exact length of {@code len}.
	 *
	 * @param a the source array.
	 * @param off the starting position in the source array.
	 * @param len the number of elements to copy (returned array's length).
	 *
	 * @return a new array containing the specified range of the source array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException if {@code off} is negative.
	 * @throws NegativeArraySizeException if {@code len} is negative.
	 */
	public static byte[] copyOf(byte[] a, int off, int len)
	{
		byte[] copy = new byte[len];
		if (len > a.length - off) {
			System.arraycopy(a, off, copy, 0, a.length - off);
		} else {
			System.arraycopy(a, off, copy, 0, len);
		}
		return copy;
	}

	/**
	 * Copies the specified range of elements from the given array into a
	 * new array. The returned array will be padded with {@code false}, if
	 * necessary, so that it has an exact length of {@code len}.
	 *
	 * @param a the source array.
	 * @param off the starting position in the source array.
	 * @param len the number of elements to copy (returned array's length).
	 *
	 * @return a new array containing the specified range of the source array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException if {@code off} is negative.
	 * @throws NegativeArraySizeException if {@code len} is negative.
	 */
	public static boolean[] copyOf(boolean[] a, int off, int len)
	{
		boolean[] copy = new boolean[len];
		if (len > a.length - off) {
			System.arraycopy(a, off, copy, 0, a.length - off);
		} else {
			System.arraycopy(a, off, copy, 0, len);
		}
		return copy;
	}

	/**
	 * Copies the specified range of elements from the given array into a
	 * new array. The returned array will be padded with {@code 0.0}, if
	 * necessary, so that it has an exact length of {@code len}.
	 *
	 * @param a the source array.
	 * @param off the starting position in the source array.
	 * @param len the number of elements to copy (returned array's length).
	 *
	 * @return a new array containing the specified range of the source array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException if {@code off} is negative.
	 * @throws NegativeArraySizeException if {@code len} is negative.
	 */
	public static float[] copyOf(float[] a, int off, int len)
	{
		float[] copy = new float[len];
		if (len > a.length - off) {
			System.arraycopy(a, off, copy, 0, a.length - off);
		} else {
			System.arraycopy(a, off, copy, 0, len);
		}
		return copy;
	}

	/**
	 * Copies the specified range of elements from the given array into a
	 * new array. The returned array will be padded with {@code 0.0}, if
	 * necessary, so that it has an exact length of {@code len}.
	 *
	 * @param a the source array.
	 * @param off the starting position in the source array.
	 * @param len the number of elements to copy (returned array's length).
	 *
	 * @return a new array containing the specified range of the source array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException if {@code off} is negative.
	 * @throws NegativeArraySizeException if {@code len} is negative.
	 */
	public static double[] copyOf(double[] a, int off, int len)
	{
		double[] copy = new double[len];
		if (len > a.length - off) {
			System.arraycopy(a, off, copy, 0, a.length - off);
		} else {
			System.arraycopy(a, off, copy, 0, len);
		}
		return copy;
	}

	/**
	 * Copies the specified range of elements from the given array into a
	 * new array. The returned array will be padded with {@code null}, if
	 * necessary, so that it has an exact length of {@code len}.
	 *
	 * @param <T> the type of the elements in the array to copy.
	 * @param a the source array.
	 * @param off the starting position in the source array.
	 * @param len the number of elements to copy (returned array's length).
	 *
	 * @return a new array containing the specified range of the source array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException if {@code off} is negative.
	 * @throws NegativeArraySizeException if {@code len} is negative.
	 */
	public static <T> T[] copyOf(T[] a, int off, int len)
	{
		T[] copy = newArray(a.getClass().getComponentType(), len);
		if (len > a.length - off) {
			System.arraycopy(a, off, copy, 0, a.length - off);
		} else {
			System.arraycopy(a, off, copy, 0, len);
		}
		return copy;
	}

	/**
	 * Returns a copy of the given array. The original array and the
	 * returned copy will have identical length and content.
	 *
	 * @param original the array to be copied.
	 *
	 * @return a copy of the original array.
	 *
	 * @throws NullPointerException if {@code original} is {@code null}.
	 */
	public static long[] copyOf(long... original)
	{
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Returns a copy of the given array. The original array and the
	 * returned copy will have identical length and content.
	 *
	 * @param original the array to be copied.
	 *
	 * @return a copy of the original array.
	 *
	 * @throws NullPointerException if {@code original} is {@code null}.
	 */
	public static int[] copyOf(int... original)
	{
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Returns a copy of the given array. The original array and the
	 * returned copy will have identical length and content.
	 *
	 * @param original the array to be copied.
	 *
	 * @return a copy of the original array.
	 *
	 * @throws NullPointerException if {@code original} is {@code null}.
	 */
	public static short[] copyOf(short... original)
	{
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Returns a copy of the given array. The original array and the
	 * returned copy will have identical length and content.
	 *
	 * @param original the array to be copied.
	 *
	 * @return a copy of the original array.
	 *
	 * @throws NullPointerException if {@code original} is {@code null}.
	 */
	public static char[] copyOf(char... original)
	{
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Returns a copy of the given array. The original array and the
	 * returned copy will have identical length and content.
	 *
	 * @param original the array to be copied.
	 *
	 * @return a copy of the original array.
	 *
	 * @throws NullPointerException if {@code original} is {@code null}.
	 */
	public static byte[] copyOf(byte... original)
	{
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Returns a copy of the given array. The original array and the
	 * returned copy will have identical length and content.
	 *
	 * @param original the array to be copied.
	 *
	 * @return a copy of the original array.
	 *
	 * @throws NullPointerException if {@code original} is {@code null}.
	 */
	public static boolean[] copyOf(boolean... original)
	{
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Returns a copy of the given array. The original array and the
	 * returned copy will have identical length and content.
	 *
	 * @param original the array to be copied.
	 *
	 * @return a copy of the original array.
	 *
	 * @throws NullPointerException if {@code original} is {@code null}.
	 */
	public static float[] copyOf(float... original)
	{
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Returns a copy of the given array. The original array and the
	 * returned copy will have identical length and content.
	 *
	 * @param original the array to be copied.
	 *
	 * @return a copy of the original array.
	 *
	 * @throws NullPointerException if {@code original} is {@code null}.
	 */
	public static double[] copyOf(double... original)
	{
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Returns a copy of the given array. The original array and the
	 * returned copy will have identical length and content.
	 *
	 * @param <T> the type of the elements in the array to copy.
	 * @param original the array to be copied.
	 *
	 * @return a copy of the original array.
	 *
	 * @throws NullPointerException if {@code original} is {@code null}.
	 */
	public static <T> T[] copyOf(T[] original)
	{
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but in reverse order.
	 *
	 * @param a the array to reverse.
	 *
	 * @return the reversed array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static long[] reverse(long... a)
	{
		int len = a.length;
		long[] copy = new long[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[len - i - 1];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but in reverse order.
	 *
	 * @param a the array to reverse.
	 *
	 * @return the reversed array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static int[] reverse(int... a)
	{
		int len = a.length;
		int[] copy = new int[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[len - i - 1];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but in reverse order.
	 *
	 * @param a the array to reverse.
	 *
	 * @return the reversed array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static short[] reverse(short... a)
	{
		int len = a.length;
		short[] copy = new short[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[len - i - 1];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but in reverse order.
	 *
	 * @param a the array to reverse.
	 *
	 * @return the reversed array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static char[] reverse(char... a)
	{
		int len = a.length;
		char[] copy = new char[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[len - i - 1];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but in reverse order.
	 *
	 * @param a the array to reverse.
	 *
	 * @return the reversed array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static byte[] reverse(byte... a)
	{
		int len = a.length;
		byte[] copy = new byte[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[len - i - 1];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but in reverse order.
	 *
	 * @param a the array to reverse.
	 *
	 * @return the reversed array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static boolean[] reverse(boolean... a)
	{
		int len = a.length;
		boolean[] copy = new boolean[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[len - i - 1];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but in reverse order.
	 *
	 * @param a the array to reverse.
	 *
	 * @return the reversed array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static float[] reverse(float... a)
	{
		int len = a.length;
		float[] copy = new float[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[len - i - 1];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but in reverse order.
	 *
	 * @param a the array to reverse.
	 *
	 * @return the reversed array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static double[] reverse(double... a)
	{
		int len = a.length;
		double[] copy = new double[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[len - i - 1];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but in reverse order.
	 *
	 * @param <T> the type of the elements in the array to reverse.
	 * @param a the array to reverse.
	 *
	 * @return the reversed array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static <T> T[] reverse(T[] a)
	{
		int len = a.length;
		T[] copy = newArray(a.getClass().getComponentType(), len);
		for (int i = 0; i < len; i++) {
			copy[i] = a[len - i - 1];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but rotated by the given distance. The element at index {@code i} in
	 * the returned array corresponds to the element in the original array
	 * at index {@code (i - distance)} mod {@code a.length}, for all values
	 * of {@code i} between {@code 0} and {@code a.length - 1}, inclusive.
	 * Note that rotation by {@code 0} or by a multiple of {@code a.length}
	 * is a no-op.
	 *
	 * @param a the array to rotate.
	 * @param distance the distance to rotate.
	 *
	 * @return the rotated array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static long[] rotate(long[] a, int distance)
	{
		int len = a.length;
		long[] copy = new long[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[index(i - distance, len)];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but rotated by the given distance. The element at index {@code i} in
	 * the returned array corresponds to the element in the original array
	 * at index {@code (i - distance)} mod {@code a.length}, for all values
	 * of {@code i} between {@code 0} and {@code a.length - 1}, inclusive.
	 * Note that rotation by {@code 0} or by a multiple of {@code a.length}
	 * is a no-op.
	 *
	 * @param a the array to rotate.
	 * @param distance the distance to rotate.
	 *
	 * @return the rotated array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static int[] rotate(int[] a, int distance)
	{
		int len = a.length;
		int[] copy = new int[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[index(i - distance, len)];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but rotated by the given distance. The element at index {@code i} in
	 * the returned array corresponds to the element in the original array
	 * at index {@code (i - distance)} mod {@code a.length}, for all values
	 * of {@code i} between {@code 0} and {@code a.length - 1}, inclusive.
	 * Note that rotation by {@code 0} or by a multiple of {@code a.length}
	 * is a no-op.
	 *
	 * @param a the array to rotate.
	 * @param distance the distance to rotate.
	 *
	 * @return the rotated array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static short[] rotate(short[] a, int distance)
	{
		int len = a.length;
		short[] copy = new short[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[index(i - distance, len)];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but rotated by the given distance. The element at index {@code i} in
	 * the returned array corresponds to the element in the original array
	 * at index {@code (i - distance)} mod {@code a.length}, for all values
	 * of {@code i} between {@code 0} and {@code a.length - 1}, inclusive.
	 * Note that rotation by {@code 0} or by a multiple of {@code a.length}
	 * is a no-op.
	 *
	 * @param a the array to rotate.
	 * @param distance the distance to rotate.
	 *
	 * @return the rotated array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static char[] rotate(char[] a, int distance)
	{
		int len = a.length;
		char[] copy = new char[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[index(i - distance, len)];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but rotated by the given distance. The element at index {@code i} in
	 * the returned array corresponds to the element in the original array
	 * at index {@code (i - distance)} mod {@code a.length}, for all values
	 * of {@code i} between {@code 0} and {@code a.length - 1}, inclusive.
	 * Note that rotation by {@code 0} or by a multiple of {@code a.length}
	 * is a no-op.
	 *
	 * @param a the array to rotate.
	 * @param distance the distance to rotate.
	 *
	 * @return the rotated array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static byte[] rotate(byte[] a, int distance)
	{
		int len = a.length;
		byte[] copy = new byte[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[index(i - distance, len)];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but rotated by the given distance. The element at index {@code i} in
	 * the returned array corresponds to the element in the original array
	 * at index {@code (i - distance)} mod {@code a.length}, for all values
	 * of {@code i} between {@code 0} and {@code a.length - 1}, inclusive.
	 * Note that rotation by {@code 0} or by a multiple of {@code a.length}
	 * is a no-op.
	 *
	 * @param a the array to rotate.
	 * @param distance the distance to rotate.
	 *
	 * @return the rotated array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static boolean[] rotate(boolean[] a, int distance)
	{
		int len = a.length;
		boolean[] copy = new boolean[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[index(i - distance, len)];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but rotated by the given distance. The element at index {@code i} in
	 * the returned array corresponds to the element in the original array
	 * at index {@code (i - distance)} mod {@code a.length}, for all values
	 * of {@code i} between {@code 0} and {@code a.length - 1}, inclusive.
	 * Note that rotation by {@code 0} or by a multiple of {@code a.length}
	 * is a no-op.
	 *
	 * @param a the array to rotate.
	 * @param distance the distance to rotate.
	 *
	 * @return the rotated array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static float[] rotate(float[] a, int distance)
	{
		int len = a.length;
		float[] copy = new float[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[index(i - distance, len)];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but rotated by the given distance. The element at index {@code i} in
	 * the returned array corresponds to the element in the original array
	 * at index {@code (i - distance)} mod {@code a.length}, for all values
	 * of {@code i} between {@code 0} and {@code a.length - 1}, inclusive.
	 * Note that rotation by {@code 0} or by a multiple of {@code a.length}
	 * is a no-op.
	 *
	 * @param a the array to rotate.
	 * @param distance the distance to rotate.
	 *
	 * @return the rotated array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static double[] rotate(double[] a, int distance)
	{
		int len = a.length;
		double[] copy = new double[len];
		for (int i = 0; i < len; i++) {
			copy[i] = a[index(i - distance, len)];
		}
		return copy;
	}

	/**
	 * Returns a new array containing the same elements as the given one,
	 * but rotated by the given distance. The element at index {@code i} in
	 * the returned array corresponds to the element in the original array
	 * at index {@code (i - distance)} mod {@code a.length}, for all values
	 * of {@code i} between {@code 0} and {@code a.length - 1}, inclusive.
	 * Note that rotation by {@code 0} or by a multiple of {@code a.length}
	 * is a no-op.
	 *
	 * @param <T> the type of the elements in the array to rotate.
	 * @param a the array to rotate.
	 * @param distance the distance to rotate.
	 *
	 * @return the rotated array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static <T> T[] rotate(T[] a, int distance)
	{
		int len = a.length;
		T[] copy = newArray(a.getClass().getComponentType(), len);
		for (int i = 0; i < len; i++) {
			copy[i] = a[index(i - distance, len)];
		}
		return copy;
	}

	private static int index(int x, int mod)
	{
		return x < 0 ? index(x + mod, mod) : x % mod;
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted. This method uses the optimized version of the Fisher-Yates
	 * shuffle algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs
	 * in linear time.
	 *
	 * @param a the array whose content will be shuffled.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static long[] shuffle(long... a)
	{
		return shuffle(a, PRNG);
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted using the specified source of randomness. All permutations
	 * occur with equal likelihood assuming that the source of randomness is
	 * fair. This method uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param a the array whose content will be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static long[] shuffle(long[] a, Random rnd)
	{
		long[] copy = copyOf(a);
		for (int i = copy.length; i > 1; i--) {
			swap(copy, i - 1, rnd.nextInt(i));
		}
		return copy;
	}

	private static void swap(long[] a, int i, int j)
	{
		long c = a[i];
		a[i] = a[j];
		a[j] = c;
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted. This method uses the optimized version of the Fisher-Yates
	 * shuffle algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs
	 * in linear time.
	 *
	 * @param a the array whose content will be shuffled.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static int[] shuffle(int... a)
	{
		return shuffle(a, PRNG);
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted using the specified source of randomness. All permutations
	 * occur with equal likelihood assuming that the source of randomness is
	 * fair. This method uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param a the array whose content will be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static int[] shuffle(int[] a, Random rnd)
	{
		int[] copy = copyOf(a);
		for (int i = copy.length; i > 1; i--) {
			swap(copy, i - 1, rnd.nextInt(i));
		}
		return copy;
	}

	private static void swap(int[] a, int i, int j)
	{
		int c = a[i];
		a[i] = a[j];
		a[j] = c;
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted. This method uses the optimized version of the Fisher-Yates
	 * shuffle algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs
	 * in linear time.
	 *
	 * @param a the array whose content will be shuffled.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static short[] shuffle(short... a)
	{
		return shuffle(a, PRNG);
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted using the specified source of randomness. All permutations
	 * occur with equal likelihood assuming that the source of randomness is
	 * fair. This method uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param a the array whose content will be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static short[] shuffle(short[] a, Random rnd)
	{
		short[] copy = copyOf(a);
		for (int i = copy.length; i > 1; i--) {
			swap(copy, i - 1, rnd.nextInt(i));
		}
		return copy;
	}

	private static void swap(short[] a, int i, int j)
	{
		short c = a[i];
		a[i] = a[j];
		a[j] = c;
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted. This method uses the optimized version of the Fisher-Yates
	 * shuffle algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs
	 * in linear time.
	 *
	 * @param a the array whose content will be shuffled.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static char[] shuffle(char... a)
	{
		return shuffle(a, PRNG);
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted using the specified source of randomness. All permutations
	 * occur with equal likelihood assuming that the source of randomness is
	 * fair. This method uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param a the array whose content will be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static char[] shuffle(char[] a, Random rnd)
	{
		char[] copy = copyOf(a);
		for (int i = copy.length; i > 1; i--) {
			swap(copy, i - 1, rnd.nextInt(i));
		}
		return copy;
	}

	private static void swap(char[] a, int i, int j)
	{
		char c = a[i];
		a[i] = a[j];
		a[j] = c;
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted. This method uses the optimized version of the Fisher-Yates
	 * shuffle algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs
	 * in linear time.
	 *
	 * @param a the array whose content will be shuffled.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static byte[] shuffle(byte... a)
	{
		return shuffle(a, PRNG);
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted using the specified source of randomness. All permutations
	 * occur with equal likelihood assuming that the source of randomness is
	 * fair. This method uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param a the array whose content will be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static byte[] shuffle(byte[] a, Random rnd)
	{
		byte[] copy = copyOf(a);
		for (int i = copy.length; i > 1; i--) {
			swap(copy, i - 1, rnd.nextInt(i));
		}
		return copy;
	}

	private static void swap(byte[] a, int i, int j)
	{
		byte c = a[i];
		a[i] = a[j];
		a[j] = c;
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted. This method uses the optimized version of the Fisher-Yates
	 * shuffle algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs
	 * in linear time.
	 *
	 * @param a the array whose content will be shuffled.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static boolean[] shuffle(boolean... a)
	{
		return shuffle(a, PRNG);
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted using the specified source of randomness. All permutations
	 * occur with equal likelihood assuming that the source of randomness is
	 * fair. This method uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param a the array whose content will be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static boolean[] shuffle(boolean[] a, Random rnd)
	{
		boolean[] copy = copyOf(a);
		for (int i = copy.length; i > 1; i--) {
			swap(copy, i - 1, rnd.nextInt(i));
		}
		return copy;
	}

	private static void swap(boolean[] a, int i, int j)
	{
		boolean c = a[i];
		a[i] = a[j];
		a[j] = c;
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted. This method uses the optimized version of the Fisher-Yates
	 * shuffle algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs
	 * in linear time.
	 *
	 * @param a the array whose content will be shuffled.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static float[] shuffle(float... a)
	{
		return shuffle(a, PRNG);
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted using the specified source of randomness. All permutations
	 * occur with equal likelihood assuming that the source of randomness is
	 * fair. This method uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param a the array whose content will be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static float[] shuffle(float[] a, Random rnd)
	{
		float[] copy = copyOf(a);
		for (int i = copy.length; i > 1; i--) {
			swap(copy, i - 1, rnd.nextInt(i));
		}
		return copy;
	}

	private static void swap(float[] a, int i, int j)
	{
		float c = a[i];
		a[i] = a[j];
		a[j] = c;
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted. This method uses the optimized version of the Fisher-Yates
	 * shuffle algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs
	 * in linear time.
	 *
	 * @param a the array whose content will be shuffled.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static double[] shuffle(double... a)
	{
		return shuffle(a, PRNG);
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted using the specified source of randomness. All permutations
	 * occur with equal likelihood assuming that the source of randomness is
	 * fair. This method uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param a the array whose content will be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static double[] shuffle(double[] a, Random rnd)
	{
		double[] copy = copyOf(a);
		for (int i = copy.length; i > 1; i--) {
			swap(copy, i - 1, rnd.nextInt(i));
		}
		return copy;
	}

	private static void swap(double[] a, int i, int j)
	{
		double c = a[i];
		a[i] = a[j];
		a[j] = c;
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted. This method uses the optimized version of the Fisher-Yates
	 * shuffle algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs
	 * in linear time.
	 *
	 * @param <T> the type of the elements in the array to shuffle.
	 * @param a the array whose content will be shuffled.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 */
	public static <T> T[] shuffle(T[] a)
	{
		return shuffle(a, PRNG);
	}

	/**
	 * Returns a copy of the given array where its element have been randomly
	 * permuted using the specified source of randomness. All permutations
	 * occur with equal likelihood assuming that the source of randomness is
	 * fair. This method uses the optimized version of the Fisher-Yates shuffle
	 * algorithm (Fisher, Yates, Durstenfeld, Knuth) and thus runs in linear
	 * time.
	 *
	 * @param <T> the type of the elements in the array to shuffle.
	 * @param a the array whose content will be shuffled.
	 * @param rnd the source of randomness to use.
	 *
	 * @return the shuffled array.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static <T> T[] shuffle(T[] a, Random rnd)
	{
		T[] copy = copyOf(a);
		for (int i = copy.length; i > 1; i--) {
			swap(copy, i - 1, rnd.nextInt(i));
		}
		return copy;
	}

	private static <T> void swap(T[] a, int i, int j)
	{
		T c = a[i];
		a[i] = a[j];
		a[j] = c;
	}

	/**
	 * Returns a sorted copy of the given array into ascending numerical
	 * order.
	 *
	 * @param a the array to be sorted.
	 *
	 * @return a sorted copy of the given array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 *
	 * @see Arrays#sort(long[])
	 */
	public static long[] sort(long... a)
	{
		long[] copy = copyOf(a);
		Arrays.sort(copy);
		return copy;
	}

	/**
	 * Returns a sorted copy of the given array into ascending numerical
	 * order.
	 *
	 * @param a the array to be sorted.
	 *
	 * @return a sorted copy of the given array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 *
	 * @see Arrays#sort(int[])
	 */
	public static int[] sort(int... a)
	{
		int[] copy = copyOf(a);
		Arrays.sort(copy);
		return copy;
	}

	/**
	 * Returns a sorted copy of the given array into ascending numerical
	 * order.
	 *
	 * @param a the array to be sorted.
	 *
	 * @return a sorted copy of the given array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 *
	 * @see Arrays#sort(short[])
	 */
	public static short[] sort(short... a)
	{
		short[] copy = copyOf(a);
		Arrays.sort(copy);
		return copy;
	}

	/**
	 * Returns a sorted copy of the given array into ascending numerical
	 * order.
	 *
	 * @param a the array to be sorted.
	 *
	 * @return a sorted copy of the given array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 *
	 * @see Arrays#sort(char[])
	 */
	public static char[] sort(char... a)
	{
		char[] copy = copyOf(a);
		Arrays.sort(copy);
		return copy;
	}

	/**
	 * Returns a sorted copy of the given array into ascending numerical
	 * order.
	 *
	 * @param a the array to be sorted.
	 *
	 * @return a sorted copy of the given array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 *
	 * @see Arrays#sort(byte[])
	 */
	public static byte[] sort(byte... a)
	{
		byte[] copy = copyOf(a);
		Arrays.sort(copy);
		return copy;
	}

	/**
	 * Returns a sorted copy of the given array into ascending numerical
	 * order.
	 *
	 * @param a the array to be sorted.
	 *
	 * @return a sorted copy of the given array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 *
	 * @see Arrays#sort(float[])
	 */
	public static float[] sort(float... a)
	{
		float[] copy = copyOf(a);
		Arrays.sort(copy);
		return copy;
	}

	/**
	 * Returns a sorted copy of the given array into ascending numerical
	 * order.
	 *
	 * @param a the array to be sorted.
	 *
	 * @return a sorted copy of the given array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 *
	 * @see Arrays#sort(double[])
	 */
	public static double[] sort(double... a)
	{
		double[] copy = copyOf(a);
		Arrays.sort(copy);
		return copy;
	}

	/**
	 * Returns a sorted copy of the given array of objects into ascending
	 * order, according to the natural ordering of its elements (all
	 * elements in the array must implement the {@link Comparable}
	 * interface).
	 *
	 * @param <T> the type of the elements in the array to sort.
	 * @param a the array to be sorted.
	 *
	 * @return a sorted copy of the given array.
	 *
	 * @throws NullPointerException if {@code a} is {@code null}.
	 * @throws ClassCastException if the array contains elements that are
	 *	not mutually comparable (for example, Strings and Integers).
	 * @throws IllegalArgumentException if the natural ordering of the array
	 *	elements is found to violate the {@link Comparable} contract.
	 *
	 * @see Arrays#sort(java.lang.Object[])
	 */
	public static <T> T[] sort(T[] a)
	{
		T[] copy = copyOf(a);
		Arrays.sort(copy);
		return copy;
	}

	/**
	 * Returns a sorted copy of the given array of objects into ascending
	 * order, according to the order induced by the given {@code Comparator}.
	 *
	 * @param <T> the type of the elements in the array to sort.
	 * @param a the array to be sorted.
	 * @param c the comparator to determine the order of the array.
	 *
	 * @return a sorted copy of the given array.
	 *
	 * @throws NullPointerException if {@code a} or {@code c} is {@code null}.
	 * @throws ClassCastException if the array contains elements that are
	 *	not mutually comparable (for example, Strings and Integers).
	 *
	 * @see Arrays#sort(T[], java.util.Comparator)
	 */
	public static <T> T[] sort(T[] a, Comparator<? super T> c)
	{
		T[] copy = Arrays.copyOf(a, a.length);
		Arrays.sort(copy, c);
		return copy;
	}

	/**
	 * Converts an array of {@code Long}s to an array of {@code long}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code Long} array to convert.
	 *
	 * @return the corresponding {@code long} array.
	 *
	 * @throws NullPointerException if {@code a} contains a {@code null} value.
	 */
	public static long[] toPrimitive(Long... a)
	{
		if (a != null) {
			long[] p = new long[a.length];
			for (int i = 0; i < a.length; i++) {
				p[i] = a[i];
			}
			return p;
		}
		return null;
	}

	/**
	 * Converts an array of {@code Integer}s to an array of {@code int}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code Integer} array to convert.
	 *
	 * @return the corresponding {@code int} array.
	 *
	 * @throws NullPointerException if {@code a} contains a {@code null} value.
	 */
	public static int[] toPrimitive(Integer... a)
	{
		if (a != null) {
			int[] p = new int[a.length];
			for (int i = 0; i < a.length; i++) {
				p[i] = a[i];
			}
			return p;
		}
		return null;
	}

	/**
	 * Converts an array of {@code Short}s to an array of {@code short}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code Short} array to convert.
	 *
	 * @return the corresponding {@code short} array.
	 *
	 * @throws NullPointerException if {@code a} contains a {@code null} value.
	 */
	public static short[] toPrimitive(Short... a)
	{
		if (a != null) {
			short[] p = new short[a.length];
			for (int i = 0; i < a.length; i++) {
				p[i] = a[i];
			}
			return p;
		}
		return null;
	}

	/**
	 * Converts an array of {@code Character}s to an array of {@code char}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code Character} array to convert.
	 *
	 * @return the corresponding {@code char} array.
	 *
	 * @throws NullPointerException if {@code a} contains a {@code null} value.
	 */
	public static char[] toPrimitive(Character... a)
	{
		if (a != null) {
			char[] p = new char[a.length];
			for (int i = 0; i < a.length; i++) {
				p[i] = a[i];
			}
			return p;
		}
		return null;
	}

	/**
	 * Converts an array of {@code Byte}s to an array of {@code byte}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code Byte} array to convert.
	 *
	 * @return the corresponding {@code byte} array.
	 *
	 * @throws NullPointerException if {@code a} contains a {@code null} value.
	 */
	public static byte[] toPrimitive(Byte... a)
	{
		if (a != null) {
			byte[] p = new byte[a.length];
			for (int i = 0; i < a.length; i++) {
				p[i] = a[i];
			}
			return p;
		}
		return null;
	}

	/**
	 * Converts an array of {@code Boolean}s to an array of {@code boolean}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code Boolean} array to convert.
	 *
	 * @return the corresponding {@code boolean} array.
	 *
	 * @throws NullPointerException if {@code a} contains a {@code null} value.
	 */
	public static boolean[] toPrimitive(Boolean... a)
	{
		if (a != null) {
			boolean[] p = new boolean[a.length];
			for (int i = 0; i < a.length; i++) {
				p[i] = a[i];
			}
			return p;
		}
		return null;
	}

	/**
	 * Converts an array of {@code Float}s to an array of {@code float}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code Float} array to convert.
	 *
	 * @return the corresponding {@code float} array.
	 *
	 * @throws NullPointerException if {@code a} contains a {@code null} value.
	 */
	public static float[] toPrimitive(Float... a)
	{
		if (a != null) {
			float[] p = new float[a.length];
			for (int i = 0; i < a.length; i++) {
				p[i] = a[i];
			}
			return p;
		}
		return null;
	}

	/**
	 * Converts an array of {@code Double}s to an array of {@code double}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code Double} array to convert.
	 *
	 * @return the corresponding {@code double} array.
	 *
	 * @throws NullPointerException if {@code a} contains a {@code null} value.
	 */
	public static double[] toPrimitive(Double... a)
	{
		if (a != null) {
			double[] p = new double[a.length];
			for (int i = 0; i < a.length; i++) {
				p[i] = a[i];
			}
			return p;
		}
		return null;
	}

	/**
	 * Converts an array of {@code long}s to an array of {@code Long}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code long} array to convert.
	 *
	 * @return the corresponding {@code Long} array.
	 */
	public static Long[] toObject(long... a)
	{
		if (a != null) {
			Long[] w = new Long[a.length];
			for (int i = 0; i < a.length; i++) {
				w[i] = a[i];
			}
			return w;
		}
		return null;
	}

	/**
	 * Converts an array of {@code int}s to an array of {@code Integer}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code int} array to convert.
	 *
	 * @return the corresponding {@code Integer} array.
	 */
	public static Integer[] toObject(int... a)
	{
		if (a != null) {
			Integer[] w = new Integer[a.length];
			for (int i = 0; i < a.length; i++) {
				w[i] = a[i];
			}
			return w;
		}
		return null;
	}

	/**
	 * Converts an array of {@code short}s to an array of {@code Short}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code short} array to convert.
	 *
	 * @return the corresponding {@code Short} array.
	 */
	public static Short[] toObject(short... a)
	{
		if (a != null) {
			Short[] w = new Short[a.length];
			for (int i = 0; i < a.length; i++) {
				w[i] = a[i];
			}
			return w;
		}
		return null;
	}

	/**
	 * Converts an array of {@code char}s to an array of {@code Character}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code char} array to convert.
	 *
	 * @return the corresponding {@code Character} array.
	 */
	public static Character[] toObject(char... a)
	{
		if (a != null) {
			Character[] w = new Character[a.length];
			for (int i = 0; i < a.length; i++) {
				w[i] = a[i];
			}
			return w;
		}
		return null;
	}

	/**
	 * Converts an array of {@code byte}s to an array of {@code Byte}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code byte} array to convert.
	 *
	 * @return the corresponding {@code Byte} array.
	 */
	public static Byte[] toObject(byte... a)
	{
		if (a != null) {
			Byte[] w = new Byte[a.length];
			for (int i = 0; i < a.length; i++) {
				w[i] = a[i];
			}
			return w;
		}
		return null;
	}

	/**
	 * Converts an array of {@code boolean}s to an array of {@code Boolean}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code boolean} array to convert.
	 *
	 * @return the corresponding {@code Boolean} array.
	 */
	public static Boolean[] toObject(boolean... a)
	{
		if (a != null) {
			Boolean[] w = new Boolean[a.length];
			for (int i = 0; i < a.length; i++) {
				w[i] = a[i];
			}
			return w;
		}
		return null;
	}

	/**
	 * Converts an array of {@code float}s to an array of {@code Float}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code Float} array to convert.
	 *
	 * @return the corresponding {@code Short} array.
	 */
	public static Float[] toObject(float... a)
	{
		if (a != null) {
			Float[] w = new Float[a.length];
			for (int i = 0; i < a.length; i++) {
				w[i] = a[i];
			}
			return w;
		}
		return null;
	}

	/**
	 * Converts an array of {@code double}s to an array of {@code Double}s.
	 * Returns {@code null} if the given array is {@code null}. Does not
	 * modify the input array.
	 *
	 * @param a the {@code double} array to convert.
	 *
	 * @return the corresponding {@code Double} array.
	 */
	public static Double[] toObject(double... a)
	{
		if (a != null) {
			Double[] w = new Double[a.length];
			for (int i = 0; i < a.length; i++) {
				w[i] = a[i];
			}
			return w;
		}
		return null;
	}

	/**
	 * Returns a {@code String} representation of the contents of the given
	 * array. Returns {@code "null"} if {@code a} is {@code null}.
	 *
	 * @param a the array whose {@code String} representation to return.
	 *
	 * @return a {@code String} representation of {@code a}.
	 *
	 * @see Arrays#toString(long[])
	 */
	public static String toString(long... a)
	{
		return Arrays.toString(a);
	}

	/**
	 * Returns a {@code String} representation of the contents of the given
	 * array. Returns {@code "null"} if {@code a} is {@code null}.
	 *
	 * @param a the array whose {@code String} representation to return.
	 *
	 * @return a {@code String} representation of {@code a}.
	 *
	 * @see Arrays#toString(int[])
	 */
	public static String toString(int... a)
	{
		return Arrays.toString(a);
	}

	/**
	 * Returns a {@code String} representation of the contents of the given
	 * array. Returns {@code "null"} if {@code a} is {@code null}.
	 *
	 * @param a the array whose {@code String} representation to return.
	 *
	 * @return a {@code String} representation of {@code a}.
	 *
	 * @see Arrays#toString(short[])
	 */
	public static String toString(short... a)
	{
		return Arrays.toString(a);
	}

	/**
	 * Returns a {@code String} representation of the contents of the given
	 * array. Returns {@code "null"} if {@code a} is {@code null}.
	 *
	 * @param a the array whose {@code String} representation to return.
	 *
	 * @return a {@code String} representation of {@code a}.
	 *
	 * @see Arrays#toString(char[])
	 */
	public static String toString(char... a)
	{
		return Arrays.toString(a);
	}

	/**
	 * Returns a {@code String} representation of the contents of the given
	 * array. Returns {@code "null"} if {@code a} is {@code null}.
	 *
	 * @param a the array whose {@code String} representation to return.
	 *
	 * @return a {@code String} representation of {@code a}.
	 *
	 * @see Arrays#toString(byte[])
	 */
	public static String toString(byte... a)
	{
		return Arrays.toString(a);
	}

	/**
	 * Returns a {@code String} representation of the contents of the given
	 * array. Returns {@code "null"} if {@code a} is {@code null}.
	 *
	 * @param a the array whose {@code String} representation to return.
	 *
	 * @return a {@code String} representation of {@code a}.
	 *
	 * @see Arrays#toString(boolean[])
	 */
	public static String toString(boolean... a)
	{
		return Arrays.toString(a);
	}

	/**
	 * Returns a {@code String} representation of the contents of the given
	 * array. Returns {@code "null"} if {@code a} is {@code null}.
	 *
	 * @param a the array whose {@code String} representation to return.
	 *
	 * @return a {@code String} representation of {@code a}.
	 *
	 * @see Arrays#toString(float[])
	 */
	public static String toString(float... a)
	{
		return Arrays.toString(a);
	}

	/**
	 * Returns a {@code String} representation of the contents of the given
	 * array. Returns {@code "null"} if {@code a} is {@code null}.
	 *
	 * @param a the array whose {@code String} representation to return.
	 *
	 * @return a {@code String} representation of {@code a}.
	 *
	 * @see Arrays#toString(double[])
	 */
	public static String toString(double... a)
	{
		return Arrays.toString(a);
	}

	/**
	 * Returns a {@code String} representation of the contents of the given
	 * array. Returns {@code "null"} if {@code a} is {@code null}.
	 *
	 * @param a the array whose {@code String} representation to return.
	 *
	 * @return a {@code String} representation of {@code a}.
	 *
	 * @see Arrays#toString(java.lang.Object[])
	 * @see Arrays#deepToString(java.lang.Object[])
	 */
	public static String toString(Object[] a)
	{
		return Arrays.toString(a);
	}

	private static <T> T[] newArray(Class<?> componentType, int len)
	{
		return (T[]) Array.newInstance(componentType, len);
	}

	private XArrays()
	{
		/* ... */
	}
}
