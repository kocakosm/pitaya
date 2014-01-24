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

package org.pitaya.security;

/**
 * A digest engine. Implementations of this interface are not meant to be
 * thread-safe.
 *
 * @see Digests
 *
 * @author Osman KOCAK
 */
public interface Digest
{
	/**
	 * Returns the digest's length (in bytes).
	 *
	 * @return the digest's length (in bytes).
	 */
	int length();

	/** 
	 * Resets the engine.
	 * 
	 * @return this object.
	 */
	Digest reset();

	/**
	 * Updates the digest using the given byte.
	 *
	 * @param input the byte with which to update the digest.
	 * 
	 * @return this object.
	 */
	Digest update(byte input);

	/**
	 * Updates the digest using the specified array of bytes.
	 *
	 * @param input the array of bytes with which to update the digest.
	 * 
	 * @return this object.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	Digest update(byte... input);

	/**
	 * Updates the digest using the specified number of bytes from the given
	 * array of bytes, starting at the specified offset.
	 *
	 * @param input the array of bytes.
	 * @param off the offset to start from in the array of bytes.
	 * @param len the number of bytes to use, starting at {@code off}.
	 * 
	 * @return this object.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} is negative or if
	 *	{@code off + len} is greater than {@code input}'s length.
	 */
	Digest update(byte[] input, int off, int len);

	/**
	 * Completes the hash computation. Note that the engine is reset after
	 * this call is made.
	 *
	 * @return the resulting digest.
	 */
	byte[] digest();

	/**
	 * Performs a final update on the digest using the specified array of
	 * bytes, then completes the digest computation. That is, this method
	 * first calls {@link #update(byte...)}, passing the input array to the
	 * update method, then calls {@link #digest()}. Note that the engine is
	 * reset after this call is made.
	 *
	 * @param input the input to be updated before the digest is completed.
	 *
	 * @return the resulting digest.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	byte[] digest(byte... input);

	/**
	 * Performs a final update on the digest using the specified data bytes,
	 * then completes the digest computation. That is, this method first
	 * calls {@link #update(byte[], int, int)}, passing the input array to
	 * the update method, then calls {@link #digest()}. Note that the engine
	 * is reset after this call is made.
	 *
	 * @param input the input array of bytes.
	 * @param off the offset to start from in the array of bytes.
	 * @param len the number of bytes to use, starting at {@code off}.
	 *
	 * @return the resulting digest.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} is negative or if
	 *	{@code off + len} is greater than {@code input}'s length.
	 */
	byte[] digest(byte[] input, int off, int len);
}
