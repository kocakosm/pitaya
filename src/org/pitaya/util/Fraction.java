/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2013 Osman KOCAK <kocakosm@gmail.com>                   *
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

import static java.math.MathContext.DECIMAL128;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * {@code Fraction} is a {@link Number} implementation that represents fractions
 * accurately. Instances of this class are immutable.
 *
 * @author Osman KOCAK
 */
public final class Fraction extends Number implements Comparable<Fraction>
{
	private static final long serialVersionUID = 2433311932359200182L;

	/** {@code Fraction} representation of 0. */
	public static final Fraction ZERO = valueOf(0);

	/** {@code Fraction} representation of 1. */
	public static final Fraction ONE = valueOf(1);

	/** {@code Fraction} representation of 1/2. */
	public static final Fraction ONE_HALF = new Fraction(1, 2);

	/** {@code Fraction} representation of 1/3. */
	public static final Fraction ONE_THIRD = new Fraction(1, 3);

	/** {@code Fraction} representation of 2/3. */
	public static final Fraction TWO_THIRDS = new Fraction(2, 3);

	/** {@code Fraction} representation of 1/4. */
	public static final Fraction ONE_QUARTER = new Fraction(1, 4);

	/** {@code Fraction} representation of 2/4. */
	public static final Fraction TWO_QUARTERS = new Fraction(2, 4);

	/** {@code Fraction} representation of 3/4. */
	public static final Fraction THREE_QUARTERS = new Fraction(3, 4);

	/**
	 * Returns the {@code Fraction} representation of the given value.
	 *
	 * @param val an {@code int} value.
	 *
	 * @return the {@code Fraction} representation of the given value.
	 */
	public static Fraction valueOf(int val)
	{
		return new Fraction(BigInteger.valueOf(val), BigInteger.ONE);
	}

	/**
	 * Returns the {@code Fraction} representation of the given value.
	 *
	 * @param val an {@link Integer} value.
	 *
	 * @return the {@code Fraction} representation of the given value.
	 *
	 * @throws NullPointerException if {@code val} is {@code null}.
	 */
	public static Fraction valueOf(Integer val)
	{
		return new Fraction(BigInteger.valueOf(val), BigInteger.ONE);
	}

	/**
	 * Returns the {@code Fraction} representation of the given value.
	 *
	 * @param val a {@link BigInteger} value.
	 *
	 * @return the {@code Fraction} representation of the given value.
	 *
	 * @throws NullPointerException if {@code val} is {@code null}.
	 */
	public static Fraction valueOf(BigInteger val)
	{
		return new Fraction(val, BigInteger.ONE);
	}

	/**
	 * Creates a {@code Fraction} from its {@code String} representation.
	 *
	 * @param str the {@code Fraction}'s {@code String} representation.
	 *
	 * @return the created {@code Fraction}.
	 *
	 * @throws IllegalArgumentException if {@code str} doesn't represent a
	 *	{@code Fraction} or if its denominator equals zero.
	 */
	public static Fraction valueOf(String str)
	{
		String fraction = str.replaceAll("\\s", "");
		int index = fraction.indexOf('/');
		if (index < 0) {
			return valueOf(new BigInteger(fraction));
		}
		BigInteger n = new BigInteger(fraction.substring(0, index));
		BigInteger d = new BigInteger(fraction.substring(index + 1));
		return new Fraction(n, d);
	}

	private final BigInteger n;
	private final BigInteger d;
	private final BigDecimal v;

	/**
	 * Creates a new {@code Fraction}.
	 *
	 * @param numerator the numerator.
	 * @param denominator the denominator.
	 *
	 * @throws IllegalArgumentException if {@code denominator} equals zero.
	 */
	public Fraction(int numerator, int denominator)
	{
		this(BigInteger.valueOf(numerator),
			BigInteger.valueOf(denominator));
	}

	/**
	 * Creates a new {@code Fraction}.
	 *
	 * @param numerator the numerator.
	 * @param denominator the denominator.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if {@code denominator} equals zero.
	 */
	public Fraction(BigInteger numerator, BigInteger denominator)
	{
		Parameters.checkCondition(!BigInteger.ZERO.equals(denominator));
		n = numerator;
		d = denominator;
		v = new BigDecimal(n).divide(new BigDecimal(d), DECIMAL128);
	}

	/**
	 * Adds the given value to this one and returns the result in reduced
	 * form.
	 *
	 * @param f the fraction to add to this one.
	 *
	 * @return {@code this + f} in reduced form.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 */
	public Fraction plus(Fraction f)
	{
		return add(f);
	}

	/**
	 * Adds the given value to this one and returns the result in reduced
	 * form.
	 *
	 * @param f the fraction to add to this one.
	 *
	 * @return {@code this + f} in reduced form.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 */
	public Fraction add(Fraction f)
	{
		return new Fraction(n.multiply(f.d).add(f.n.multiply(d)),
			d.multiply(f.d)).reduce();
	}

	/**
	 * Subtracts the given value from this one and returns the result in
	 * reduced form.
	 *
	 * @param f the fraction to subtract from this one.
	 *
	 * @return {@code this - f} in reduced form.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 */
	public Fraction minus(Fraction f)
	{
		return subtract(f);
	}

	/**
	 * Subtracts the given value from this one and returns the result in
	 * reduced form.
	 *
	 * @param f the fraction to subtract from this one.
	 *
	 * @return {@code this - f} in reduced form.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 */
	public Fraction subtract(Fraction f)
	{
		return new Fraction(n.multiply(f.d).subtract(f.n.multiply(d)),
			d.multiply(f.d)).reduce();
	}

	/**
	 * Multiplies this value by the given one and returns the result in
	 * reduced form.
	 *
	 * @param f the fraction to multiply this one with.
	 *
	 * @return {@code this * f} in reduced form.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 */
	public Fraction times(Fraction f)
	{
		return multiply(f);
	}

	/**
	 * Multiplies this value by the given one and returns the result in
	 * reduced form.
	 *
	 * @param f the fraction to multiply this one with.
	 *
	 * @return {@code this * f} in reduced form.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 */
	public Fraction multiply(Fraction f)
	{
		return new Fraction(n.multiply(f.n), d.multiply(f.d)).reduce();
	}

	/**
	 * Divides this value by the given one and returns the result in reduced
	 * form.
	 *
	 * @param f the fraction to divide this one with.
	 *
	 * @return {@code this / f} in reduced form.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws ArithmeticException if {@code f} equals zero.
	 */
	public Fraction over(Fraction f)
	{
		return divide(f);
	}

	/**
	 * Divides this value by the given one and returns the result in reduced
	 * form.
	 *
	 * @param f the fraction to divide this one with.
	 *
	 * @return {@code this / f} in reduced form.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws ArithmeticException if {@code f} equals zero.
	 */
	public Fraction divide(Fraction f)
	{
		if (ZERO.equals(f)) {
			throw new ArithmeticException("Division by zero");
		}
		return new Fraction(n.multiply(f.d), d.multiply(f.n)).reduce();
	}

	/**
	 * Returns the {@code Fraction} obtained by raising this to the given
	 * power, in reduced form.
	 *
	 * @param exponent the power to raise this fraction to.
	 *
	 * @return {@code this ^ exponent} in reduced form.
	 *
	 * @throws ArithmeticException if {@code exponent} is negative.
	 */
	public Fraction pow(int exponent)
	{
		return new Fraction(n.pow(exponent), d.pow(exponent)).reduce();
	}

	/**
	 * Returns a {@code Fraction} which is the absolute value of this one.
	 *
	 * @return the absolute value of this {@code Fraction}.
	 */
	public Fraction abs()
	{
		return new Fraction(n.abs(), d.abs());
	}

	/**
	 * Returns the inverse of this {@code Fraction}. The result is not
	 * reduced before being returned.
	 *
	 * @return {@code 1 / this}.
	 */
	public Fraction invert()
	{
		return new Fraction(d, n);
	}

	/**
	 * Returns the opposite of this {@code Fraction}. The result is not
	 * reduced before being returned.
	 *
	 * @return {@code -this}.
	 */
	public Fraction negate()
	{
		if (d.signum() < 0) {
			return new Fraction(n, d.negate());
		}
		return new Fraction(n.negate(), d);
	}

	/**
	 * Returns the reduced form of this {@code Fraction}. Also, note that
	 * the sign information will be held by the numerator.
	 *
	 * @return the reduced form of this {@code Fraction}.
	 */
	public Fraction reduce()
	{
		BigInteger gcd = n.gcd(d);
		if (d.signum() < 0) {
			BigInteger numerator = n.divide(gcd).negate();
			BigInteger denominator = d.divide(gcd).negate();
			return new Fraction(numerator, denominator);
		}
		return new Fraction(n.divide(gcd), d.divide(gcd));
	}

	/**
	 * Returns the signum function of this {@code Fraction}.
	 *
	 * @return -1, 0 or 1 as the value of this {@code Fraction} is negative,
	 *	zero or positive
	 */
	public int signum()
	{
		return n.signum() * d.signum();
	}

	/**
	 * Returns this {@code Fraction}'s numerator.
	 *
	 * @return this {@code Fraction}'s numerator.
	 */
	public BigInteger numerator()
	{
		return n;
	}

	/**
	 * Returns this {@code Fraction}'s denominator.
	 *
	 * @return this {@code Fraction}'s denominator.
	 */
	public BigInteger denominator()
	{
		return d;
	}

	/**
	 * Returns the value of this {@code Fraction} as a {@code BigDecimal},
	 * rounded in accordance to IEEE 754R Decimal128 format.
	 *
	 * @return the value of this {@code Fraction} as a {@code BigDecimal}.
	 */
	public BigDecimal bigDecimalValue()
	{
		return v;
	}

	@Override
	public int intValue()
	{
		return v.intValue();
	}

	@Override
	public long longValue()
	{
		return v.longValue();
	}

	@Override
	public float floatValue()
	{
		return v.floatValue();
	}

	@Override
	public double doubleValue()
	{
		return v.doubleValue();
	}

	@Override
	public int compareTo(Fraction f)
	{
		return n.multiply(f.d).compareTo(f.n.multiply(d));
	}

	@Override
	public String toString()
	{
		return n + " / " + d;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof Fraction)) {
			return false;
		}
		Fraction f1 = reduce();
		Fraction f2 = ((Fraction) o).reduce();
		return f1.n.equals(f2.n) && f1.d.equals(f2.d);
	}

	@Override
	public int hashCode()
	{
		Fraction f = reduce();
		int hash = 7;
		hash = 71 * hash + f.n.hashCode();
		hash = 71 * hash + f.d.hashCode();
		return hash;
	}
}
