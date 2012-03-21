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

package org.pitaya.hmac;

/**
 * HMAC engine.
 *
 * @author Osman KOCAK
 */
public interface HMAC
{
	/**
	 * Returns the HMAC's length (in bits).
	 *
	 * @return the HMAC's length (in bits).
	 */
	int length();

	/** Resets the engine. */
	void reset();

	/**
	 * Updates the HMAC using the given byte.
	 *
	 * @param input the byte with which to update the HMAC.
	 */
	void update(byte input);

	/**
	 * Updates the HMAC using the specified array of bytes.
	 *
	 * @param input the array of bytes with which to update the HMAC.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	void update(byte... input);

	/**
	 * Updates the HMAC using the specified number of bytes from the given
	 * array of bytes, starting at the specified offset.
	 *
	 * @param input the array of bytes.
	 * @param off the offset to start from in the array of bytes.
	 * @param len the number of bytes to use, starting at offset.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} is negative or if
	 *	{@code off + len} is greater than {@code input}'s length.
	 */
	void update(byte[] input, int off, int len);

	/**
	 * Performs a final update on the HMAC using the specified array of
	 * bytes, then completes the HMAC computation. That is, this method
	 * first calls {@link #update(byte...)}, passing the input array to the
	 * update method, then calls {@link #hmac()}. Note that the engine is
	 * reset after this call is made.
	 *
	 * @param input the input to be updated before the HMAC is completed.
	 *
	 * @return the resulting HMAC.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	byte[] hmac(byte... input);

	/**
	 * Completes the hash computation. Note that the engine is reset after
	 * this call is made.
	 *
	 * @return the resulting HMAC.
	 */
	byte[] hmac();
}
