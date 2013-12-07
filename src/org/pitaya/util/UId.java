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

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * A unique identifier (a 128-bit random number). Instances of this class are 
 * immutable.
 *
 * @author Osman KOCAK
 */
public final class UId
{
	private static final Random PRNG = new SecureRandom();
	private static final int LENGTH = 16;

	/**
	 * Creates a new random unique identifier.
	 *
	 * @return a new random {@code UId}.
	 */
	public static UId create()
	{
		byte[] bytes = new byte[LENGTH];
		PRNG.nextBytes(bytes);
		return new UId(bytes);
	}

	/**
	 * Creates a new {@code UId} from its hexadecimal representation.
	 *
	 * @param hex the {@code UId}'s hexadecimal representation.
	 *
	 * @return the created {@code UId}.
	 *
	 * @throws IllegalArgumentException if the given {@link String} does not
	 *	represent a valid {@code UId} hexadecimal encoding.
	 */
	public static UId valueOf(String hex)
	{
		return new UId(Base16.decode(hex));
	}

	/**
	 * Creates a new {@code UId} from its encoding.
	 *
	 * @param bytes the {@code UId}'s encoding.
	 *
	 * @return the decoded {@code UId}.
	 *
	 * @throws IllegalArgumentException if the given bytes are not a valid
	 *	{@code UId} encoding.
	 */
	public static UId valueOf(byte... bytes)
	{
		return new UId(Arrays.copyOf(bytes, bytes.length));
	}

	private final byte[] bytes;

	private UId(byte... bytes)
	{
		Parameters.checkCondition(bytes.length == LENGTH);
		this.bytes = bytes;
	}

	/**
	 * Returns this identifier's encoding.
	 *
	 * @return this identifier's encoding.
	 */
	public byte[] toByteArray()
	{
		return Arrays.copyOf(bytes, bytes.length);
	}

	@Override
	public String toString()
	{
		return Base16.encode(bytes);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof UId)) {
			return false;
		}
		final UId id = (UId)o;
		return Arrays.equals(bytes, id.bytes);
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(bytes);
	}
}
