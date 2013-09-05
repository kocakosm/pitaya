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

/**
 * Bitwise operations.
 *
 * @author Osman KOCAK
 */
public final class Bits
{
	/**
	 * Returns the value obtained by rotating the two's complement binary
	 * representation of the specified {@code int} value left by the
	 * specified number of bits. Note that left rotation with a negative
	 * distance is equivalent to right rotation. Note also that rotation by
	 * any multiple of 32 is a no-op.
	 *
	 * @param x the number to rotate.
	 * @param n the rotation distance.
	 *
	 * @return the value obtained by rotating the two's complement binary
	 *	representation of the specified {@code int} value left by the
	 *	specified number of bits.
	 */
	public static int rotateLeft(int x, int n)
	{
		return (x << n) | (x >>> (32 - n));
	}

	/**
	 * Returns the value obtained by rotating the two's complement binary
	 * representation of the specified {@code long} value left by the
	 * specified number of bits. Note that left rotation with a negative
	 * distance is equivalent to right rotation. Note also that rotation by
	 * any multiple of 64 is a no-op.
	 *
	 * @param x the number to rotate.
	 * @param n the rotation distance.
	 *
	 * @return the value obtained by rotating the two's complement binary
	 *	representation of the specified {@code long} value left by the
	 *	specified number of bits.
	 */
	public static long rotateLeft(long x, int n)
	{
		return (x << n) | (x >>> (64 - n));
	}

	/**
	 * Returns the value obtained by rotating the two's complement binary
	 * representation of the specified {@code int} value right by the
	 * specified number of bits. Note that right rotation with a negative
	 * distance is equivalent to left rotation. Note also that rotation by
	 * any multiple of 64 is a no-op.
	 *
	 * @param x the number to rotate.
	 * @param n the rotation distance.
	 *
	 * @return the value obtained by rotating the two's complement binary
	 *	representation of the specified {@code int} value right by the
	 *	specified number of bits.
	 */
	public static int rotateRight(int x, int n)
	{
		return (x >>> n) | (x << (32 - n));
	}

	/**
	 * Returns the value obtained by rotating the two's complement binary
	 * representation of the specified {@code long} value right by the
	 * specified number of bits. Note that right rotation with a negative
	 * distance is equivalent to left rotation. Note also that rotation by
	 * any multiple of 64 is a no-op.
	 *
	 * @param x the number to rotate.
	 * @param n the rotation distance.
	 *
	 * @return the value obtained by rotating the two's complement binary
	 *	representation of the specified {@code long} value right by the
	 *	specified number of bits.
	 */
	public static long rotateRight(long x, int n)
	{
		return (x >>> n) | (x << (64 - n));
	}

	private Bits()
	{
		/* ... */
	}
}
