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

import org.pitaya.charset.ASCII;

/**
 * Roman numbers conversion utilities.
 * 
 * @author Osman KOCAK
 */
public final class RomanNumbers
{
	private static final String[] BASE_NUMBERS = {
		"M", "CM", "D", "CD", "C", "XC", "L",
		"XL", "X", "IX", "V", "IV", "I"
	};

	private static final int[] BASE_VALUES = {
		1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
	};

	/**
	 * Returns the {@code int} value of the given roman digit.
	 * 
	 * @param c the roman digit to interpret.
	 *
	 * @return the {@code int} value of the given roman digit.
	 *
	 * @throws IllegalArgumentException if the given {@code char} does not
	 *	represent a roman digit.
	 */
	public static int valueOf(char c)
	{
		switch (ASCII.toUpperCase(c)) {
			case 'I': return 1;
			case 'V': return 5;
			case 'X': return 10;
			case 'L': return 50;
			case 'C': return 100;
			case 'D': return 500;
			case 'M': return 1000;
		}
		throw new IllegalArgumentException("Not a roman digit: " + c);
	}

	/**
	 * Returns the {@code int} value of the given roman number. This method
	 * only accepts representations of values between 1 and 4999 inlusive.
	 *
	 * @param str the roman number to interpret.
	 *
	 * @return the {@code int} value of the given roman number.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IllegalArgumentException if the given {@code String} does not
	 *	represent a valid roman number.
	 */
	public static int valueOf(String str)
	{
		String r = ASCII.toUpperCase(str.replaceAll("\\s", ""));
		Parameters.checkCondition(r.length() > 0);
		int index = 0;
		int value = 0;
		for (int i = 0; i < BASE_NUMBERS.length; i++) {
			while (r.startsWith(BASE_NUMBERS[i], index)) {
				value += BASE_VALUES[i];
				Parameters.checkCondition(value < 5000);
				index += BASE_NUMBERS[i].length();
			}
		}
		Parameters.checkCondition(index == r.length());
		Parameters.checkCondition(r.equals(toString(value)));
		return value;
	}

	/**
	 * Returns the roman number representation of the given {@code int}
	 * value. This method only accepts values between 1 and 4999 inclusive.
	 * 
	 * @param value the value to convert into a roman number.
	 *
	 * @return the roman representation of the given value.
	 *
	 * @throws IllegalArgumentException if {@code value} is out of range.
	 */
	public static String toString(int value)
	{
		Parameters.checkCondition(value > 0 && value < 5000);
		StringBuilder sb = new StringBuilder();
		int remainder = value;
		for (int i = 0; i < BASE_VALUES.length; i++) {
			while (remainder >= BASE_VALUES[i]) {
				sb.append(BASE_NUMBERS[i]);
				remainder -= BASE_VALUES[i];
			}
		}
		return sb.toString();
	}

	private RomanNumbers()
	{
		/* ... */
	}
}
