/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2016 Osman KOCAK <kocakosm@gmail.com>                   *
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

import org.kocakosm.pitaya.util.Parameters;

import java.math.BigDecimal;

/**
 * Extra math utilities.
 *
 * @author Osman KOCAK
 */
public final class Numbers
{
	/**
	 * Returns the maximum of the given values.
	 *
	 * @param values a non-empty array of {@code long} values.
	 *
	 * @return the maximum of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static long max(long... values)
	{
		Parameters.checkCondition(values.length > 0);
		long max = values[0];
		for (int i = 1; i < values.length; i++) {
			max = Math.max(max, values[i]);
		}
		return max;
	}

	/**
	 * Returns the maximum of the given values.
	 *
	 * @param values a non-empty array of {@code int} values.
	 *
	 * @return the maximum of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static int max(int... values)
	{
		Parameters.checkCondition(values.length > 0);
		int max = values[0];
		for (int i = 1; i < values.length; i++) {
			max = Math.max(max, values[i]);
		}
		return max;
	}

	/**
	 * Returns the maximum of the given values, using the same comparison
	 * rules as {@link Math#max(float, float)}.
	 *
	 * @param values a non-empty array of {@code float} values.
	 *
	 * @return the maximum of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static float max(float... values)
	{
		Parameters.checkCondition(values.length > 0);
		float max = values[0];
		for (int i = 1; i < values.length; i++) {
			max = Math.max(max, values[i]);
		}
		return max;
	}

	/**
	 * Returns the maximum of the given values, using the same comparison
	 * rules as {@link Math#max(double, double)}.
	 *
	 * @param values a non-empty array of {@code double} values.
	 *
	 * @return the maximum of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static double max(double... values)
	{
		Parameters.checkCondition(values.length > 0);
		double max = values[0];
		for (int i = 1; i < values.length; i++) {
			max = Math.max(max, values[i]);
		}
		return max;
	}

	/**
	 * Returns the minimum of the given values.
	 *
	 * @param values a non-empty array of {@code long} values.
	 *
	 * @return the minimum of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static long min(long... values)
	{
		Parameters.checkCondition(values.length > 0);
		long min = values[0];
		for (int i = 1; i < values.length; i++) {
			min = Math.min(min, values[i]);
		}
		return min;
	}

	/**
	 * Returns the minimum of the given values.
	 *
	 * @param values a non-empty array of {@code int} values.
	 *
	 * @return the minimum of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static int min(int... values)
	{
		Parameters.checkCondition(values.length > 0);
		int min = values[0];
		for (int i = 1; i < values.length; i++) {
			min = Math.min(min, values[i]);
		}
		return min;
	}

	/**
	 * Returns the minimum of the given values, using the same comparison
	 * rules as {@link Math#min(float, float)}.
	 *
	 * @param values a non-empty array of {@code float} values.
	 *
	 * @return the minimum of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static float min(float... values)
	{
		Parameters.checkCondition(values.length > 0);
		float min = values[0];
		for (int i = 1; i < values.length; i++) {
			min = Math.min(min, values[i]);
		}
		return min;
	}

	/**
	 * Returns the minimum of the given values, using the same comparison
	 * rules as {@link Math#min(double, double)}.
	 *
	 * @param values a non-empty array of {@code double} values.
	 *
	 * @return the minimum of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static double min(double... values)
	{
		Parameters.checkCondition(values.length > 0);
		double min = values[0];
		for (int i = 1; i < values.length; i++) {
			min = Math.min(min, values[i]);
		}
		return min;
	}

	/**
	 * Returns the arithmetic mean of the given values. This method is not
	 * subject to overflow.
	 *
	 * @param values the values.
	 *
	 * @return the arithmetic mean of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static double mean(int... values)
	{
		Parameters.checkCondition(values.length > 0);
		long sum = 0L;
		for (int value : values) {
			sum += value;
		}
		return (double) sum / values.length;
	}

	/**
	 * Returns the arithmetic mean of the given values. This method is not
	 * subject to overflow.
	 *
	 * @param values the values.
	 *
	 * @return the arithmetic mean of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 */
	public static double mean(long... values)
	{
		Parameters.checkCondition(values.length > 0);
		BigDecimal sum = BigDecimal.ZERO;
		for (long value : values) {
			sum = sum.add(BigDecimal.valueOf(value));
		}
		return sum.divide(BigDecimal.valueOf(values.length)).doubleValue();
	}

	/**
	 * Returns the arithmetic mean of the given values. This method is not
	 * subject to overflow.
	 *
	 * @param values the values.
	 *
	 * @return the arithmetic mean of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 * @throws NumberFormatException if {@code values} contains a NaN or an
	 *	infinite value.
	 */
	public static double mean(double... values)
	{
		Parameters.checkCondition(values.length > 0);
		BigDecimal sum = BigDecimal.ZERO;
		for (double value : values) {
			sum = sum.add(BigDecimal.valueOf(value));
		}
		return sum.divide(BigDecimal.valueOf(values.length)).doubleValue();
	}

	/**
	 * Returns the arithmetic mean of the given values. This method is not
	 * subject to overflow.
	 *
	 * @param values the values.
	 *
	 * @return the arithmetic mean of the given values.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 * @throws IllegalArgumentException if {@code values} is empty.
	 * @throws NumberFormatException if {@code values} contains a NaN or an
	 *	infinite value.
	 */
	public static double mean(float... values)
	{
		Parameters.checkCondition(values.length > 0);
		BigDecimal sum = BigDecimal.ZERO;
		for (float value : values) {
			sum = sum.add(BigDecimal.valueOf(value));
		}
		return sum.divide(BigDecimal.valueOf(values.length)).doubleValue();
	}

	/**
	 * Returns whether the given value is a "real" value. Namely, it returns
	 * {@code false} if the given value is infinite or NaN, and {@code true}
	 * in all other cases.
	 *
	 * @param f the value to test.
	 *
	 * @return whether the given value is a "real" value.
	 */
	public static boolean isFinite(float f)
	{
		return !Float.isInfinite(f) && !Float.isNaN(f);
	}

	/**
	 * Returns whether the given value is a "real" value. Namely, it returns
	 * {@code false} if the given value is infinite or NaN, and {@code true}
	 * in all other cases.
	 *
	 * @param d the value to test.
	 *
	 * @return whether the given value is a "real" value.
	 */
	public static boolean isFinite(double d)
	{
		return !Double.isInfinite(d) && !Double.isNaN(d);
	}

	/**
	 * Returns the absolute value of the given value, provided the result
	 * fits into a {@code long}.
	 *
	 * @param a the value.
	 *
	 * @return the absolute value of {@code a}.
	 *
	 * @throws ArithmeticException if the absolute value of {@code a} can't
	 *	be represented by a {@code long}.
	 */
	public static long safeAbs(long a)
	{
		if (a == Long.MIN_VALUE) {
			throw new ArithmeticException(
				"Long overflow: abs(" + a + ")");
		}
		return Math.abs(a);
	}

	/**
	 * Returns the absolute value of the given value, provided the result
	 * fits into an {@code int}.
	 *
	 * @param a the value.
	 *
	 * @return the absolute value of {@code a}.
	 *
	 * @throws ArithmeticException if the absolute value of {@code a} can't
	 *	be represented by an {@code int}.
	 */
	public static int safeAbs(int a)
	{
		if (a == Integer.MIN_VALUE) {
			throw new ArithmeticException(
				"Int overflow: abs(" + a + ")");
		}
		return Math.abs(a);
	}

	/**
	 * Returns the opposite of the given value, provided the result fits
	 * into a {@code long}.
	 *
	 * @param a the value to negate.
	 *
	 * @return {@code -a}.
	 *
	 * @throws ArithmeticException if {@code -a} can't be represented by a
	 *	{@code long}.
	 */
	public static long safeNegate(long a)
	{
		if (a == Long.MIN_VALUE) {
			throw new ArithmeticException("Long overflow: -" + a);
		}
		return -a;
	}

	/**
	 * Returns the opposite of the given value, provided the result fits
	 * into an {@code int}.
	 *
	 * @param a the value to negate.
	 *
	 * @return {@code -a}.
	 *
	 * @throws ArithmeticException if {@code -a} can't be represented by an
	 *	{@code int}.
	 */
	public static int safeNegate(int a)
	{
		if (a == Integer.MIN_VALUE) {
			throw new ArithmeticException("Int overflow: -" + a);
		}
		return -a;
	}

	/**
	 * Returns the sum of {@code a} and {@code b}, provided the result fits
	 * into a {@code long}.
	 *
	 * @param a the first operand.
	 * @param b the second operand.
	 *
	 * @return {@code a + b}.
	 *
	 * @throws ArithmeticException if {@code a + b} can't be represented by
	 *	a {@code long}.
	 */
	public static long safeAdd(long a, long b)
	{
		long sum = a + b;
		if (a < 0L != sum < 0L && a < 0L == b < 0L) {
			throw new ArithmeticException(
				"Long overflow: " + a + " + " + b);
		}
		return sum;
	}

	/**
	 * Returns the sum of {@code a} and {@code b}, provided the result fits
	 * into an {@code int}.
	 *
	 * @param a the first operand.
	 * @param b the second operand.
	 *
	 * @return {@code a + b}.
	 *
	 * @throws ArithmeticException if {@code a + b} can't be represented by
	 *	an {@code int}.
	 */
	public static int safeAdd(int a, int b)
	{
		int sum = a + b;
		if (a < 0 != sum < 0 && a < 0 == b < 0) {
			throw new ArithmeticException(
				"Int overflow: " + a + " + " + b);
		}
		return sum;
	}

	/**
	 * Returns the difference of {@code a} and {@code b}, provided the
	 * result fits into a {@code long}.
	 *
	 * @param a the first operand.
	 * @param b the second operand.
	 *
	 * @return {@code a - b}.
	 *
	 * @throws ArithmeticException if {@code a - b} can't be represented by
	 *	a {@code long}.
	 */
	public static long safeSubtract(long a, long b)
	{
		long diff = a - b;
		if (a < 0L != diff < 0L && a < 0L != b < 0L) {
			throw new ArithmeticException(
				"Long overflow: " + a + " - " + b);
		}
		return diff;
	}

	/**
	 * Returns the difference of {@code a} and {@code b}, provided the
	 * result fits into an {@code int}.
	 *
	 * @param a the first operand.
	 * @param b the second operand.
	 *
	 * @return {@code a - b}.
	 *
	 * @throws ArithmeticException if {@code a - b} can't be represented by
	 *	an {@code int}.
	 */
	public static int safeSubtract(int a, int b)
	{
		int diff = a - b;
		if (a < 0 != diff < 0 && a < 0 != b < 0) {
			throw new ArithmeticException(
				"Int overflow: " + a + " - " + b);
		}
		return diff;
	}

	/**
	 * Returns the product of {@code a} and {@code b}, provided the result
	 * fits into a {@code long}.
	 *
	 * @param a the first operand.
	 * @param b the second operand.
	 *
	 * @return {@code a * b}.
	 *
	 * @throws ArithmeticException if {@code a * b} can't be represented by
	 *	a {@code long}.
	 */
	public static long safeMultiply(long a, long b)
	{
		if (a == 0L || b == 0L) {
			return 0L;
		}
		long max = a < 0L == b < 0L ? Long.MAX_VALUE : Long.MIN_VALUE;
		if ((b > 0L && b > max / a) || (b < 0L && b < max / a)) {
			throw new ArithmeticException(
				"Long overflow: " + a + " * " + b);
		}
		return a * b;
	}

	/**
	 * Returns the product of {@code a} and {@code b}, provided the result
	 * fits into an {@code int}.
	 *
	 * @param a the first operand.
	 * @param b the second operand.
	 *
	 * @return {@code a * b}.
	 *
	 * @throws ArithmeticException if {@code a * b} can't be represented by
	 *	an {@code int}.
	 */
	public static int safeMultiply(int a, int b)
	{
		if (a == 0 || b == 0) {
			return 0;
		}
		long max = a < 0 == b < 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
		if ((b > 0 && b > max / a) || (b < 0 && b < max / a)) {
			throw new ArithmeticException(
				"Int overflow: " + a + " * " + b);
		}
		return a * b;
	}

	/**
	 * Returns the result of the integer division of the first number by the
	 * second, provided the result fits into a {@code long}.
	 *
	 * @param a the dividend.
	 * @param b the divisor.
	 *
	 * @return {@code a / b}.
	 *
	 * @throws ArithmeticException if {@code b} is equal to {@code 0} or if
	 *	{@code a / b} can't be represented by a {@code long}.
	 */
	public static long safeDivide(long a, long b)
	{
		if (a == Long.MIN_VALUE && b == -1L) {
			throw new ArithmeticException(
				"Long overflow: " + a + " / " + b);
		}
		return a / b;
	}

	/**
	 * Returns the result of the integer division of the first number by the
	 * second, provided the result fits into an {@code int}.
	 *
	 * @param a the dividend.
	 * @param b the divisor.
	 *
	 * @return {@code a / b}.
	 *
	 * @throws ArithmeticException if {@code b} is equal to {@code 0} or if
	 *	{@code a / b} can't be represented by an {@code int}.
	 */
	public static int safeDivide(int a, int b)
	{
		if (a == Integer.MIN_VALUE && b == -1L) {
			throw new ArithmeticException(
				"Int overflow: " + a + " / " + b);
		}
		return a / b;
	}

	/**
	 * Returns the value of the first argument raised to the power of the
	 * second, provided the result fits into a {@code long}. This method
	 * implements the exponentiation by squaring method. Please refer to
	 * <a href="http://en.wikipedia.org/wiki/Exponentiation_by_squaring">
	 * Wikipedia</a> for further information.
	 *
	 * @param a the base.
	 * @param b the exponent.
	 *
	 * @return {@code a ^ b}.
	 *
	 * @throws IllegalArgumentException if {@code b < 0}.
	 * @throws ArithmeticException if {@code a ^ b} can't be represented by
	 *	a {@code long}.
	 */
	public static long safePow(long a, int b)
	{
		Parameters.checkCondition(b >= 0);
		if (b == 0) {
			return 1L;
		}
		long base = a;
		int exponent = b;
		long result = 1L;
		try {
			while (exponent > 1) {
				if ((exponent & 1) != 0) {
					result = safeMultiply(result, base);
					exponent -= 1;
				}
				base = safeMultiply(base, base);
				exponent >>= 1;
			}
			return safeMultiply(result, base);
		} catch (ArithmeticException e) {
			throw new ArithmeticException(
				"Long overflow: " + a + " ^ " + b);
		}
	}

	/**
	 * Returns the value of the first argument raised to the power of the
	 * second, provided the result fits into an {@code int}. This method
	 * implements the exponentiation by squaring method. Please refer to
	 * <a href="http://en.wikipedia.org/wiki/Exponentiation_by_squaring">
	 * Wikipedia</a> for further information.
	 *
	 * @param a the base.
	 * @param b the exponent.
	 *
	 * @return {@code a ^ b}.
	 *
	 * @throws IllegalArgumentException if {@code b < 0}.
	 * @throws ArithmeticException if {@code a ^ b} can't be represented by
	 *	an {@code int}.
	 */
	public static int safePow(int a, int b)
	{
		Parameters.checkCondition(b >= 0);
		if (b == 0) {
			return 1;
		}
		int base = a;
		int exponent = b;
		int result = 1;
		try {
			while (exponent > 1) {
				if ((exponent & 1) != 0) {
					result = safeMultiply(result, base);
					exponent -= 1;
				}
				base = safeMultiply(base, base);
				exponent >>= 1;
			}
			return safeMultiply(result, base);
		} catch (ArithmeticException e) {
			throw new ArithmeticException(
				"Int overflow: " + a + " ^ " + b);
		}
	}

	/**
	 * Returns the greatest common divisor of the absolute value of the
	 * given two numbers. This method first computes the absolute values of
	 * the given numbers, thus it doesn't accept {@code Long.MIN_VALUE}.
	 * Also, note that if one of the given values is {@code 0}, this method
	 * will return the absolute value of the second argument.
	 * This method implements the binary GCD algorithm (also known as
	 * Stein's algorithm). For further information on this algorithm, please
	 * refer to <a href="http://en.wikipedia.org/wiki/Binary_GCD_algorithm">
	 * Wikipedia</a>.
	 *
	 * @param a the first number.
	 * @param b the second number.
	 *
	 * @return the greatest common divisor of {@code a} and {@code b}.
	 *
	 * @throws ArithmeticException if {@code a} or {@code b} is equal to
	 *	{@code Long.MIN_VALUE}.
	 */
	public static long gcd(long a, long b)
	{
		if (a < 0L || b < 0L) {
			try {
				return gcd(safeAbs(a), safeAbs(b));
			} catch (ArithmeticException e) {
				throw new ArithmeticException(
					"Long overflow: gcd(" + a + ", " + b + ")");
			}
		}
		if (a == 0L) {
			return b;
		}
		if (b == 0L) {
			return a;
		}
		int shift = 0;
		while (((a | b) & 1L) == 0) {
			a >>= 1;
			b >>= 1;
			shift++;
		}
		while ((a & 1L) == 0L) {
			a >>= 1;
		}
		do {
			while ((b & 1L) == 0L) {
				b >>= 1;
			}
			if (a > b) {
				long tmp = b;
				b = a;
				a = tmp;
			}
			b -= a;
		} while (b != 0L);
		return a << shift;
	}

	/**
	 * Returns the greatest common divisor of the absolute value of the
	 * given two numbers. This method first computes the absolute values of
	 * the given numbers, thus it doesn't accept {@code Integer.MIN_VALUE}.
	 * Also, note that if one of the given values is {@code 0}, this method
	 * will return the absolute value of the second argument.
	 * This method implements the binary GCD algorithm (also known as
	 * Stein's algorithm). For further information on this algorithm, please
	 * refer to <a href="http://en.wikipedia.org/wiki/Binary_GCD_algorithm">
	 * Wikipedia</a>.
	 *
	 * @param a the first number.
	 * @param b the second number.
	 *
	 * @return the greatest common divisor of {@code a} and {@code b}.
	 *
	 * @throws ArithmeticException if {@code a} or {@code b} is equal to
	 *	{@code Integer.MIN_VALUE}.
	 */
	public static int gcd(int a, int b)
	{
		try {
			if (a < 0 || b < 0) {
				return gcd(safeAbs(a), safeAbs(b));
			}
			return (int) gcd((long) a, (long) b);
		} catch (ArithmeticException e) {
			throw new ArithmeticException(
				"Int overflow: gcd(" + a + ", " + b + ")");
		}
	}

	/**
	 * Returns the least common multiple of the absolute value of the given
	 * two numbers. This method first computes the absolute values of the
	 * given numbers, thus it doesn't accept {@code Long.MIN_VALUE}. Also,
	 * note that if one of the given values is {@code 0}, this method will
	 * return the absolute value of the second argument. This method uses
	 * the formula {@code lcm(a,b) = (a / gcd(a,b)) * b}. Please refer to
	 * <a href="http://en.wikipedia.org/wiki/Least_common_multiple">
	 * Wikipedia</a> for further information.
	 *
	 * @param a the first number.
	 * @param b the second number.
	 *
	 * @return the least common multiple of {@code a} and {@code b}.
	 *
	 * @throws ArithmeticException if {@code a} or {@code b} is equal to
	 *	{@code Long.MIN_VALUE}.
	 */
	public static long lcm(long a, long b)
	{
		try {
			if (a < 0L || b < 0L) {
				return lcm(safeAbs(a), safeAbs(b));
			}
			if (a == 0L) {
				return b;
			}
			if (b == 0L) {
				return a;
			}
			return safeMultiply(a / gcd(a, b), b);
		} catch (ArithmeticException e) {
			throw new ArithmeticException(
				"Long overflow: lcm(" + a + ", " + b + ")");
		}
	}

	/**
	 * Returns the least common multiple of the absolute value of the given
	 * two numbers. This method first computes the absolute values of the
	 * given numbers, thus it doesn't accept {@code Integer.MIN_VALUE}.
	 * Also, note that if one of the given values is {@code 0}, this method
	 * will return the absolute value of the second argument. This method
	 * uses the formula {@code lcm(a,b) = (a / gcd(a,b)) * b}. Please refer
	 * to <a href="http://en.wikipedia.org/wiki/Least_common_multiple">
	 * Wikipedia</a> for further information.
	 *
	 * @param a the first number.
	 * @param b the second number.
	 *
	 * @return the least common multiple of {@code a} and {@code b}.
	 *
	 * @throws ArithmeticException if {@code a} or {@code b} is equal to
	 *	{@code Integer.MIN_VALUE}.
	 */
	public static int lcm(int a, int b)
	{
		try {
			if (a < 0 || b < 0) {
				return lcm(safeAbs(a), safeAbs(b));
			}
			if (a == 0) {
				return b;
			}
			if (b == 0) {
				return a;
			}
			return safeMultiply(a / gcd(a, b), b);
		} catch (ArithmeticException e) {
			throw new ArithmeticException(
				"Int overflow: lcm(" + a + ", " + b + ")");
		}
	}

	/**
	 * Casts the given {@code long} value to {@code int}, if possible.
	 *
	 * @param a the value to cast to {@code int}.
	 *
	 * @return the casted value.
	 *
	 * @throws ArithmeticException if {@code a} cannot be cast into an
	 *	{@code int}.
	 */
	public static int safeToInt(long a)
	{
		if (a >= Integer.MIN_VALUE && a <= Integer.MAX_VALUE) {
			return (int) a;
		}
		throw new ArithmeticException(a + " can't be cast to int");
	}

	private Numbers()
	{
		/* ... */
	}
}
