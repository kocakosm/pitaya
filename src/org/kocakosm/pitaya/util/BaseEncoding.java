/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2015 Osman KOCAK <kocakosm@gmail.com>                   *
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
	BaseEncoding BASE_64 = new DefaultBaseEncoding(Alphabet.BASE_64);

	/** Base64 encoding scheme with URL and filename safe alphabet. */
	BaseEncoding BASE_64_URL = new DefaultBaseEncoding(Alphabet.BASE_64_URL);

	/** Base32 encoding scheme. */
	BaseEncoding BASE_32 = new DefaultBaseEncoding(Alphabet.BASE_32);

	/** Base32 encoding scheme with extended Hex alphabet. */
	BaseEncoding BASE_32_HEX = new DefaultBaseEncoding(Alphabet.BASE_32_HEX);

	/** Base16 encoding scheme. */
	BaseEncoding BASE_16 = new DefaultBaseEncoding(Alphabet.BASE_16);

	/**
	 * Returns a {@code BaseEncoding} that behaves as this one except that
	 * it adds the specified separator after every {@code n} characters when
	 * encodind data and ignores any occurence of the specified separator
	 * when decoding encoded data.
	 *
	 * See <a href="http://tools.ietf.org/html/rfc4648#section-3.1">RFC 4648
	 * section 3.1</a> for more details.
	 *
	 * @param separator the separator to use.
	 * @param n the number of characters between two successive separators.
	 *
	 * @return a {@code BaseEncoding} with the desired configuration.
	 *
	 * @throws NullPointerException if {@code separator} is {@code null}.
	 * @throws IllegalArgumentException if {@code separator} contains any
	 *	alphabet or padding characters or if {@code n <= 0}.
	 */
	BaseEncoding withSeparator(String separator, int n);

	/**
	 * Returns a {@code BaseEncoding} that behaves as this one except that
	 * it doesn't append padding when encoding data and treats any padding
	 * character as an unknown character when decoding encoded data. Also,
	 * note that since Base16 encoding scheme does not need padding, it is
	 * not affected by this method call.
	 *
	 * See <a href="http://tools.ietf.org/html/rfc4648#section-3.2">RFC 4648
	 * section 3.2</a> for more details.
	 *
	 * @return a {@code BaseEncoding} with the desired configuration.
	 */
	BaseEncoding withoutPadding();

	/**
	 * Returns a {@code BaseEncoding} that behaves as this one except that
	 * it ignores any unknown character when decoding encoded data.
	 *
	 * See <a href="http://tools.ietf.org/html/rfc4648#section-3.3">RFC 4648
	 * section 3.3</a> for more details.
	 *
	 * @return a {@code BaseEncoding} with the desired configuration.
	 */
	BaseEncoding ignoreUnknownCharacters();

	/**
	 * Encodes the given data bytes according to this {@code BaseEncoding}'s
	 * configuration.
	 *
	 * @param in the data bytes to encode.
	 *
	 * @return the encoded {@code String}.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 */
	String encode(byte... in);

	/**
	 * Encodes the given data bytes according to this {@code BaseEncoding}'s
	 * configuration.
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
	 * Decodes the given encoded {@code String} according to this
	 * {@code BaseEncoding}'s configuration.
	 *
	 * @param in the input {@code String}.
	 *
	 * @return the decoded data.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IllegalArgumentException if {@code in} is not a valid
	 *	base-encoded {@code String} according to this
	 *	{@code BaseEncoding}'s configuration.
	 */
	byte[] decode(String in);

	/**
	 * Decodes the specified range from the given encoded {@code String}
	 * according to this {@code BaseEncoding}'s configuration.
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
	 *	{@code in} is not a valid base-encoded {@code String} according
	 *	to this {@code BaseEncoding}'s configuration.
	 */
	byte[] decode(String in, int off, int len);
}
