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

package org.kocakosm.pitaya.util;

/**
 * RFC 4648 encoding scheme. Provided implementations of this interface are all
 * immutable.
 *
 * @see <a href="https://www.ietf.org/rfc/rfc4648.txt">RFC 4648</a>
 *
 * @author Osman KOCAK
 */
public interface BaseEncoding
{
	/** Base64 encoding scheme. */
	BaseEncoding BASE_64 = new Base64();

	/** Base64 encoding scheme with URL and filename safe alphabet. */
	BaseEncoding BASE_64_URL = new Base64(
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'0', '1', '2', '3', '4','5', '6', '7', '8', '9', '-', '_'
	);

	/** Base32 encoding scheme. */
	BaseEncoding BASE_32 = new Base32();

	/** Base32 encoding scheme with extended Hex alphabet. */
	BaseEncoding BASE_32_HEX = new Base32(
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
		'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
		'Q', 'R', 'S', 'T', 'U', 'V'
	);

	/** Base16 encoding scheme. */
	BaseEncoding BASE_16 = new Base16();

	/**
	 * Returns a {@code BaseEncoding} that will behave as this one except
	 * that it won't add padding when encoding data and will accept unpadded
	 * encoded {@code String}s.
	 *
	 * @return a {@code BaseEncoding} with the desired configuration.
	 */
	BaseEncoding withoutPadding();

	/**
	 * Encodes the given data bytes.
	 *
	 * @param in the data bytes to encode.
	 *
	 * @return the encoded {@code String}.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 */
	String encode(byte... in);

	/**
	 * Encodes the given data bytes.
	 *
	 * @param in the input buffer.
	 * @param off the input offset.
	 * @param len the number of bytes to encode.
	 *
	 * @return the encoded {@code String}.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code in}'s
	 *	length.
	 */
	String encode(byte[] in, int off, int len);

	/**
	 * Decodes the given encoded {@code String}. Decoding is stopped at the
	 * first padding character found (if applicable). Whitespace characters,
	 * namely {@code '\t', ' ', '\n'} and {@code '\r'}, are ignored.
	 *
	 * @param in the input {@code String}.
	 *
	 * @return the decoded data.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IllegalArgumentException if {@code in} is not a valid
	 *	base-encoded {@code String}.
	 */
	byte[] decode(String in);

	/**
	 * Decodes the specified range from the given encoded {@code String}.
	 * Decoding is stopped at the first padding character found (if
	 * applicable). Whitespace characters, namely {@code '\t', ' ', '\n'}
	 * and {@code '\r'}, are ignored.
	 *
	 * @param in the input {@code String}.
	 * @param off the input offset.
	 * @param len the number of characters to decode.
	 *
	 * @return the decoded data.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code in}'s
	 *	length.
	 * @throws IllegalArgumentException if the specified character range in
	 *	{@code in} is not a valid base-encoded {@code String}.
	 */
	byte[] decode(String in, int off, int len);
}
