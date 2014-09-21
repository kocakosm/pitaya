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

package org.kocakosm.pitaya.charset;

/**
 * Utility class to encode and decode UTF-8 {@code String}s.
 *
 * @author Osman KOCAK
 */
public final class UTF8
{
	/**
	 * Returns the UTF-8 encoding of the given {@code String}.
	 *
	 * @param str the {@code String} to encode.
	 *
	 * @return the UTF-8 encoding of the given {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static byte[] encode(String str)
	{
		return str.getBytes(Charsets.UTF_8);
	}

	/**
	 * Returns the UTF-8 encoding of the specified character sequence.
	 *
	 * @param str the input {@code String}.
	 * @param off the start index, inclusive.
	 * @param len the number of characters to encode.
	 *
	 * @return the UTF-8 encoding of the specified characters.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code str}'s
	 *	length.
	 */
	public static byte[] encode(String str, int off, int len)
	{
		return encode(str.substring(off, off + len));
	}

	/**
	 * Decodes the given UTF-8 encoded characters.
	 *
	 * @param input the UTF-8 encoded characters to decode.
	 *
	 * @return the decoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	public static String decode(byte[] input)
	{
		return decode(input, 0, input.length);
	}

	/**
	 * Decodes {@code len} UTF-8 encoded bytes from the given input buffer,
	 * starting at {@code off}.
	 *
	 * @param input the input buffer.
	 * @param off the starting offset.
	 * @param len the number of bytes to decode.
	 *
	 * @return the decoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code input}'s
	 *	length.
	 */
	public static String decode(byte[] input, int off, int len)
	{
		return new String(input, off, len, Charsets.UTF_8);
	}

	private UTF8()
	{
		/* ... */
	}
}
