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

package org.kocakosm.pitaya.math;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Numbers}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class NumbersTest
{
	@Test
	public void testMaxLong()
	{
		assertEquals(0L, Numbers.max(0L));
		assertEquals(3L, Numbers.max(0L, -1L, 3L, 2L, 1L));
		assertEquals(3L, Numbers.max(2L, 1L, 0L, -1L, 3L));
		assertEquals(3L, Numbers.max(3L, 2L, 1L, 0L, -1L));
	}

	@Test
	public void testMaxInt()
	{
		assertEquals(0, Numbers.max(0));
		assertEquals(3, Numbers.max(0, -1, 3, 2, 1));
		assertEquals(3, Numbers.max(2, 1, 0, -1, 3));
		assertEquals(3, Numbers.max(3, 2, 1, 0, -1));
	}

	@Test
	public void testMaxDouble()
	{
		assertEquals(0.0, Numbers.max(0.0), 0.0);
		assertEquals(3.0, Numbers.max(0.0, -1.0, 3.0, 2.0, 1.0), 0.0);
		assertEquals(3.0, Numbers.max(2.0, 1.0, 0.0, -1.0, 3.0), 0.0);
		assertEquals(3.0, Numbers.max(3.0, 2.0, 1.0, 0.0, -1.0), 0.0);
	}

	@Test
	public void testMaxFloat()
	{
		assertEquals(0.0, Numbers.max(0.0f), 0.0);
		assertEquals(3.0, Numbers.max(0f, -1f, 3f, 2f, 1f), 0.0);
		assertEquals(3.0, Numbers.max(2f, 1f, 0f, -1f, 3f), 0.0);
		assertEquals(3.0, Numbers.max(3f, 2f, 1f, 0f, -1f), 0.0);
	}

	@Test
	public void testMinLong()
	{
		assertEquals(0L, Numbers.min(0L));
		assertEquals(-1L, Numbers.min(0L, -1L, 3L, 2L, 1L));
		assertEquals(-1L, Numbers.min(2L, 1L, 0L, -1L, 3L));
		assertEquals(-1L, Numbers.min(3L, 2L, 1L, 0L, -1L));
	}

	@Test
	public void testMinInt()
	{
		assertEquals(0, Numbers.min(0));
		assertEquals(-1, Numbers.min(0, -1, 3, 2, 1));
		assertEquals(-1, Numbers.min(2, 1, 0, -1, 3));
		assertEquals(-1, Numbers.min(3, 2, 1, 0, -1));
	}

	@Test
	public void testMinDouble()
	{
		assertEquals(0.0, Numbers.min(0.0), 0.0);
		assertEquals(-1.0, Numbers.min(0.0, -1.0, 3.0, 2.0, 1.0), 0.0);
		assertEquals(-1.0, Numbers.min(2.0, 1.0, 0.0, -1.0, 3.0), 0.0);
		assertEquals(-1.0, Numbers.min(3.0, 2.0, 1.0, 0.0, -1.0), 0.0);
	}

	@Test
	public void testMinFloat()
	{
		assertEquals(0.0, Numbers.min(0.0f), 0.0);
		assertEquals(-1.0, Numbers.min(0f, -1f, 3f, 2f, 1f), 0.0);
		assertEquals(-1.0, Numbers.min(2f, 1f, 0f, -1f, 3f), 0.0);
		assertEquals(-1.0, Numbers.min(3f, 2f, 1f, 0f, -1f), 0.0);
	}

	@Test
	public void testMeanLong()
	{
		assertEquals(8457L, Numbers.mean(8457L), 0.0);
		assertEquals(0.5, Numbers.mean(0L, -1L, 2L, 1L), 0.0);
	}

	@Test
	public void testMeanInt()
	{
		assertEquals(8457, Numbers.mean(8457), 0.0);
		assertEquals(0.5, Numbers.mean(0, -1, 2, 1), 0.0);
	}

	@Test
	public void testMeanDouble()
	{
		assertEquals(Math.PI, Numbers.mean(Math.PI), 0.0);
		assertEquals(0.5, Numbers.mean(0.0, -1.0, 2.0, 1.0), 0.0);
	}

	@Test
	public void testMeanFloat()
	{
		assertEquals(51.2454f, Numbers.mean(51.2454f), 0.0);
		assertEquals(0.5, Numbers.mean(0f, -1f, 2f, 1f), 0.0);
	}

	@Test
	public void testIsFiniteDouble()
	{
		assertTrue(Numbers.isFinite(54.124));
		assertFalse(Numbers.isFinite(Double.NaN));
		assertFalse(Numbers.isFinite(Double.NEGATIVE_INFINITY));
		assertFalse(Numbers.isFinite(Double.POSITIVE_INFINITY));
	}

	@Test
	public void testIsFiniteFloat()
	{
		assertTrue(Numbers.isFinite(54.124f));
		assertFalse(Numbers.isFinite(Float.NaN));
		assertFalse(Numbers.isFinite(Float.NEGATIVE_INFINITY));
		assertFalse(Numbers.isFinite(Float.POSITIVE_INFINITY));
	}

	@Test
	public void testSafeAbsLong()
	{
		assertEquals(0L, Numbers.safeAbs(0L));
		assertEquals(1L, Numbers.safeAbs(1L));
		assertEquals(1L, Numbers.safeAbs(-1L));

		assertEquals(Long.MAX_VALUE, Numbers.safeAbs(Long.MAX_VALUE));
		assertEquals(Long.MAX_VALUE, Numbers.safeAbs(Long.MIN_VALUE + 1L));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeAbsLongOverflow()
	{
		Numbers.safeNegate(Long.MIN_VALUE);
	}

	@Test
	public void testSafeAbsInt()
	{
		assertEquals(0, Numbers.safeAbs(0));
		assertEquals(1, Numbers.safeAbs(1));
		assertEquals(1, Numbers.safeAbs(-1));

		assertEquals(Integer.MAX_VALUE, Numbers.safeAbs(Integer.MAX_VALUE));
		assertEquals(Integer.MAX_VALUE, Numbers.safeAbs(Integer.MIN_VALUE + 1));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeAbsIntOverflow()
	{
		Numbers.safeNegate(Integer.MIN_VALUE);
	}

	@Test
	public void testSafeNegateLong()
	{
		assertEquals(0L, Numbers.safeNegate(0L));
		assertEquals(-1L, Numbers.safeNegate(1L));
		assertEquals(1L, Numbers.safeNegate(-1L));

		assertEquals(Long.MAX_VALUE, Numbers.safeNegate(Long.MIN_VALUE + 1L));
		assertEquals(Long.MIN_VALUE + 1L, Numbers.safeNegate(Long.MAX_VALUE));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeNegateLongOverflow()
	{
		Numbers.safeNegate(Long.MIN_VALUE);
	}

	@Test
	public void testSafeNegateInt()
	{
		assertEquals(0, Numbers.safeNegate(0));
		assertEquals(-1, Numbers.safeNegate(1));
		assertEquals(1, Numbers.safeNegate(-1));

		assertEquals(Integer.MAX_VALUE, Numbers.safeNegate(Integer.MIN_VALUE + 1L));
		assertEquals(Integer.MIN_VALUE + 1L, Numbers.safeNegate(Integer.MAX_VALUE));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeNegateIntOverflow()
	{
		Numbers.safeNegate(Integer.MIN_VALUE);
	}

	@Test
	public void testSafeAddLong()
	{
		assertEquals(0L, Numbers.safeAdd(0L, 0L));
		assertEquals(3L, Numbers.safeAdd(1L, 2L));
		assertEquals(1L, Numbers.safeAdd(-1L, 2L));
		assertEquals(-1L, Numbers.safeAdd(1L, -2L));
		assertEquals(-4L, Numbers.safeAdd(-1L, -3L));

		assertEquals(-1L, Numbers.safeAdd(Long.MIN_VALUE, Long.MAX_VALUE));
		assertEquals(-1L, Numbers.safeAdd(Long.MAX_VALUE, Long.MIN_VALUE));
		assertEquals(Long.MIN_VALUE + 1L, Numbers.safeAdd(Long.MIN_VALUE, 1L));
		assertEquals(Long.MAX_VALUE - 1L, Numbers.safeAdd(Long.MAX_VALUE, -1L));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeAddLongUnderFlow()
	{
		Numbers.safeAdd(Long.MIN_VALUE, -1L);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeAddLongOverFlow()
	{
		Numbers.safeAdd(Long.MAX_VALUE, 1L);
	}

	@Test
	public void testSafeAddInt()
	{
		assertEquals(0, Numbers.safeAdd(0, 0));
		assertEquals(3, Numbers.safeAdd(1, 2));
		assertEquals(1, Numbers.safeAdd(-1, 2));
		assertEquals(-1, Numbers.safeAdd(1, -2));
		assertEquals(-4, Numbers.safeAdd(-1, -3));

		assertEquals(-1, Numbers.safeAdd(Integer.MIN_VALUE, Integer.MAX_VALUE));
		assertEquals(-1, Numbers.safeAdd(Integer.MAX_VALUE, Integer.MIN_VALUE));
		assertEquals(Integer.MIN_VALUE + 1, Numbers.safeAdd(Integer.MIN_VALUE, 1));
		assertEquals(Integer.MAX_VALUE - 1, Numbers.safeAdd(Integer.MAX_VALUE, -1));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeAddIntUnderFlow()
	{
		Numbers.safeAdd(Integer.MIN_VALUE, -1);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeAddIntOverFlow()
	{
		Numbers.safeAdd(Integer.MAX_VALUE, 1);
	}

	@Test
	public void testSafeSubtractLong()
	{
		assertEquals(0L, Numbers.safeSubtract(0L, 0L));
		assertEquals(-1L, Numbers.safeSubtract(1L, 2L));
		assertEquals(-3L, Numbers.safeSubtract(-1L, 2L));
		assertEquals(3L, Numbers.safeSubtract(1L, -2L));
		assertEquals(2L, Numbers.safeSubtract(-1L, -3L));

		assertEquals(-1L, Numbers.safeSubtract(Long.MIN_VALUE, -Long.MAX_VALUE));
		assertEquals(0L, Numbers.safeSubtract(Long.MAX_VALUE, -(Long.MIN_VALUE + 1L)));
		assertEquals(Long.MIN_VALUE + 1L, Numbers.safeSubtract(Long.MIN_VALUE, -1L));
		assertEquals(Long.MAX_VALUE - 1L, Numbers.safeSubtract(Long.MAX_VALUE, 1L));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeSubtractLongUnderFlow()
	{
		Numbers.safeSubtract(Long.MIN_VALUE, 1L);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeSubtractLongOverFlow()
	{
		Numbers.safeSubtract(Long.MAX_VALUE, -1L);
	}

	@Test
	public void testSafeSubtractInt()
	{
		assertEquals(0, Numbers.safeSubtract(0, 0));
		assertEquals(-1, Numbers.safeSubtract(1, 2));
		assertEquals(-3, Numbers.safeSubtract(-1, 2));
		assertEquals(3, Numbers.safeSubtract(1, -2));
		assertEquals(2, Numbers.safeSubtract(-1, -3));

		assertEquals(-1, Numbers.safeSubtract(Integer.MIN_VALUE, -Integer.MAX_VALUE));
		assertEquals(0, Numbers.safeSubtract(Integer.MAX_VALUE, -(Integer.MIN_VALUE + 1)));
		assertEquals(Integer.MIN_VALUE + 1, Numbers.safeSubtract(Integer.MIN_VALUE, -1));
		assertEquals(Integer.MAX_VALUE - 1, Numbers.safeSubtract(Integer.MAX_VALUE, 1));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeSubtractIntUnderFlow()
	{
		Numbers.safeSubtract(Integer.MIN_VALUE, 1);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeSubtractIntOverFlow()
	{
		Numbers.safeSubtract(Integer.MAX_VALUE, -1);
	}

	@Test
	public void testSafeMultiplyLong()
	{
		assertEquals(0L, Numbers.safeMultiply(0L, 0L));

		assertEquals(6L, Numbers.safeMultiply(2L, 3L));
		assertEquals(-6L, Numbers.safeMultiply(2L, -3L));
		assertEquals(-6L, Numbers.safeMultiply(-2L, 3L));
		assertEquals(6L, Numbers.safeMultiply(-2L, -3L));

		assertEquals(Long.MIN_VALUE, Numbers.safeMultiply(Long.MIN_VALUE, 1L));
		assertEquals(Long.MAX_VALUE, Numbers.safeMultiply(Long.MAX_VALUE, 1L));
		assertEquals(-Long.MAX_VALUE, Numbers.safeMultiply(Long.MAX_VALUE, -1L));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeMultiplyLongOverflow()
	{
		Numbers.safeMultiply(Long.MIN_VALUE, -1L);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeMultiplyLongUnderflow()
	{
		Numbers.safeMultiply(Long.MAX_VALUE, -2L);
	}

	@Test
	public void testSafeMultiplyInt()
	{
		assertEquals(0, Numbers.safeMultiply(0, 0));

		assertEquals(6, Numbers.safeMultiply(2, 3));
		assertEquals(-6, Numbers.safeMultiply(2, -3));
		assertEquals(-6, Numbers.safeMultiply(-2, 3));
		assertEquals(6, Numbers.safeMultiply(-2, -3));

		assertEquals(Integer.MIN_VALUE, Numbers.safeMultiply(Integer.MIN_VALUE, 1));
		assertEquals(Integer.MAX_VALUE, Numbers.safeMultiply(Integer.MAX_VALUE, 1));
		assertEquals(-Integer.MAX_VALUE, Numbers.safeMultiply(Integer.MAX_VALUE, -1));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeMultiplyIntOverflow()
	{
		Numbers.safeMultiply(Integer.MIN_VALUE, -1);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeMultiplyIntUnderflow()
	{
		Numbers.safeMultiply(Integer.MAX_VALUE, -2);
	}

	@Test
	public void testSafeDivideLong()
	{
		assertEquals(2L, Numbers.safeDivide(6L, 3L));
		assertEquals(-2L, Numbers.safeDivide(6L, -3L));
		assertEquals(-2L, Numbers.safeDivide(-6L, 3L));
		assertEquals(2L, Numbers.safeDivide(-6L, -3L));

		assertEquals(Long.MIN_VALUE, Numbers.safeDivide(Long.MIN_VALUE, 1L));
		assertEquals(Long.MAX_VALUE, Numbers.safeDivide(Long.MAX_VALUE, 1L));
		assertEquals(-Long.MAX_VALUE, Numbers.safeDivide(Long.MAX_VALUE, -1L));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeDivideLongOverflow()
	{
		Numbers.safeDivide(Long.MIN_VALUE, -1L);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeDivideLongByZero()
	{
		Numbers.safeDivide(2541L, 0L);
	}

	@Test
	public void testSafeDivideInt()
	{
		assertEquals(2, Numbers.safeDivide(6, 3));
		assertEquals(-2, Numbers.safeDivide(6, -3));
		assertEquals(-2, Numbers.safeDivide(-6, 3));
		assertEquals(2, Numbers.safeDivide(-6, -3));

		assertEquals(Integer.MIN_VALUE, Numbers.safeDivide(Integer.MIN_VALUE, 1));
		assertEquals(Integer.MAX_VALUE, Numbers.safeDivide(Integer.MAX_VALUE, 1));
		assertEquals(-Integer.MAX_VALUE, Numbers.safeDivide(Integer.MAX_VALUE, -1));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeDivideIntOverflow()
	{
		Numbers.safeDivide(Integer.MIN_VALUE, -1);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeDivideIntByZero()
	{
		Numbers.safeDivide(2541, 0);
	}

	@Test
	public void testSafePowLong()
	{
		assertEquals(1L, Numbers.safePow(1L, 1));
		assertEquals(1L, Numbers.safePow(Long.MAX_VALUE, 0));
		assertEquals(Math.pow(17, 11), Numbers.safePow(17L, 11), 0.1);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafePowLongOverflow()
	{
		Numbers.safePow(24L, 16);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSafePowLongWithNegativeExponent()
	{
		Numbers.safePow(7L, -4);
	}

	@Test
	public void testSafePowInt()
	{
		assertEquals(1, Numbers.safePow(1, 1));
		assertEquals(1, Numbers.safePow(Integer.MAX_VALUE, 0));
		assertEquals(Math.pow(9, 8), Numbers.safePow(9, 8), 0.1);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafePowIntOverflow()
	{
		Numbers.safePow(16, 14);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSafePowIntWithNegativeExponent()
	{
		Numbers.safePow(7, -4);
	}

	@Test
	public void testGcdLong()
	{
		assertEquals(5L, Numbers.gcd(5L, 0L));
		assertEquals(7L, Numbers.gcd(0L, -7L));
		assertEquals(13L, Numbers.gcd(13L, 13L));
		assertEquals(1L, Numbers.gcd(-37L, 600L));
		assertEquals(20L, Numbers.gcd(20L, -100L));
		assertEquals(18913L, Numbers.gcd(-624129L, -2061517L));
	}

	@Test(expected = ArithmeticException.class)
	public void testGcdLongOverflow()
	{
		Numbers.gcd(5L, Long.MIN_VALUE);
	}

	@Test
	public void testGcdInt()
	{
		assertEquals(5, Numbers.gcd(5, 0));
		assertEquals(7, Numbers.gcd(0, -7));
		assertEquals(13, Numbers.gcd(13, 13));
		assertEquals(1, Numbers.gcd(-37, 600));
		assertEquals(20, Numbers.gcd(20, -100));
		assertEquals(18913, Numbers.gcd(-624129, -2061517));
	}

	@Test(expected = ArithmeticException.class)
	public void testGcdIntOverflow()
	{
		Numbers.gcd(5, Integer.MIN_VALUE);
	}

	@Test
	public void testLcmLong()
	{
		assertEquals(5L, Numbers.lcm(5L, 0L));
		assertEquals(7L, Numbers.lcm(0L, -7L));
		assertEquals(13L, Numbers.lcm(13L, 13L));
		assertEquals(22200L, Numbers.lcm(-37L, 600L));
		assertEquals(100L, Numbers.lcm(20L, -100L));
		assertEquals(68030061L, Numbers.lcm(-624129L, -2061517L));
	}

	@Test(expected = ArithmeticException.class)
	public void testLcmLongOverflow()
	{
		Numbers.lcm(5L, Long.MIN_VALUE);
	}

	@Test
	public void testLcmInt()
	{
		assertEquals(5, Numbers.lcm(5, 0));
		assertEquals(7, Numbers.lcm(0, -7));
		assertEquals(13, Numbers.lcm(13, 13));
		assertEquals(22200, Numbers.lcm(-37, 600));
		assertEquals(100, Numbers.lcm(20, -100));
		assertEquals(68030061, Numbers.lcm(-624129, -2061517));
	}

	@Test(expected = ArithmeticException.class)
	public void testLcmIntOverflow()
	{
		Numbers.lcm(5, Integer.MIN_VALUE);
	}

	@Test
	public void testSafeLongToInt()
	{
		assertEquals(0, Numbers.safeToInt(0L));
		assertEquals(-5, Numbers.safeToInt(-5L));
		assertEquals(Integer.MAX_VALUE, Numbers.safeToInt(Integer.MAX_VALUE));
		assertEquals(Integer.MIN_VALUE, Numbers.safeToInt(Integer.MIN_VALUE));
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeLongToIntOverflow()
	{
		Numbers.safeToInt(Integer.MAX_VALUE + 1L);
	}

	@Test(expected = ArithmeticException.class)
	public void testSafeLongToIntUnderflow()
	{
		Numbers.safeToInt(Integer.MIN_VALUE - 1L);
	}
}
