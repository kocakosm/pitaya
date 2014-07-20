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

import java.io.IOException;
import java.io.InputStream;

/**
 * MAC (Message Authentication Code) engine. A MAC provides a way to check the
 * integrity of information transmitted over or stored in an unreliable medium,
 * based on a secret key. Typically, message authentication codes are used
 * between two parties that share a secret key in order to validate information
 * transmitted between these parties.
 * Implementations of this interface are not meant to be thread-safe.
 *
 * @see HMAC
 *
 * @author Osman KOCAK
 */
public interface MAC
{
	/**
	 * Returns the MAC's length (in bytes).
	 *
	 * @return the MAC's length (in bytes).
	 */
	int length();

	/**
	 * Resets the engine.
	 *
	 * @return this object.
	 */
	MAC reset();

	/**
	 * Updates the MAC using the given byte.
	 *
	 * @param input the byte with which to update the MAC.
	 *
	 * @return this object.
	 */
	MAC update(byte input);

	/**
	 * Updates the MAC using the specified array of bytes.
	 *
	 * @param input the array of bytes with which to update the MAC.
	 *
	 * @return this object.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	MAC update(byte... input);

	/**
	 * Updates the MAC using the specified number of bytes from the given
	 * array of bytes, starting at the specified offset.
	 *
	 * @param input the array of bytes.
	 * @param off the offset to start from in the array of bytes.
	 * @param len the number of bytes to use, starting at offset.
	 *
	 * @return this object.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} is negative or if
	 *	{@code off + len} is greater than {@code input}'s length.
	 */
	MAC update(byte[] input, int off, int len);

	/**
	 * Updates the MAC using the content of the specified stream.
	 *
	 * @param input the stream to process.
	 *
	 * @return this object.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IOException if {@code input} can't be read or if it has been
	 *	closed, or if some other I/O error occurs.
	 */
	MAC update(InputStream input) throws IOException;

	/**
	 * Completes the MAC computation. Note that the engine is reset after
	 * this call is made.
	 *
	 * @return the resulting MAC.
	 */
	byte[] mac();

	/**
	 * Performs a final update on the MAC using the specified array of
	 * bytes, then completes the MAC computation. That is, this method first
	 * calls {@link #update(byte...)}, passing the input array to the update
	 * method, then calls {@link #mac()}. Note that the engine is reset
	 * after this call is made.
	 *
	 * @param input the input to be updated before the MAC is completed.
	 *
	 * @return the resulting MAC.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	byte[] mac(byte... input);

	/**
	 * Performs a final update on the MAC using the specified data bytes,
	 * then completes the MAC computation. That is, this method first calls
	 * {@link #update(byte[], int, int)}, passing the input array to the
	 * update method, then calls {@link #mac()}. Note that the engine is
	 * reset after this call is made.
	 *
	 * @param input the input array of bytes.
	 * @param off the offset to start from in the array of bytes.
	 * @param len the number of bytes to use, starting at {@code off}.
	 *
	 * @return the resulting MAC.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} is negative or if
	 *	{@code off + len} is greater than {@code input}'s length.
	 */
	byte[] mac(byte[] input, int off, int len);

	/**
	 * Performs a final update on the MAC using the specified stream, then
	 * completes the MAC computation. That is, this method first calls
	 * {@link #update(InputStream)}, passing the input stream to the update
	 * method, then calls {@link #mac()}. Note that the engine is reset
	 * after this call is made.
	 *
	 * @param input the stream to process.
	 *
	 * @return the resulting MAC.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IOException if {@code input} can't be read or if it has been
	 *	closed, or if some other I/O error occurs.
	 */
	byte[] mac(InputStream input) throws IOException;
}
