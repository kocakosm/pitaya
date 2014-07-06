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

package org.pitaya.util;

/**
 * Basic Base16 encoding and decoding (RFC 4648).
 *
 * @author Osman KOCAK
 */
public final class Base16
{
	private static final char[] HEX_DIGITS = new char[] {
		'0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	};

	/**
	 * Returns the hexadecimal {@code String} representation of the given
	 * data bytes.
	 *
	 * @param bytes the data to encode.
	 *
	 * @return the encoded {@code String}.
	 *
	 * @throws NullPointerException if {@code bytes} is {@code null}.
	 */
	public static String encode(byte... bytes)
	{
		return encode(bytes, 0, bytes.length);
	}

	/**
	 * Returns the hexadecimal {@code String} representation of {@code len}
	 * data bytes from the given input buffer, starting at {@code off}.
	 *
	 * @param buf the input buffer.
	 * @param off the starting offset.
	 * @param len the number of bytes to encode.
	 *
	 * @return the encoded {@code String}.
	 *
	 * @throws NullPointerException if {@code buf} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code buf}'s
	 *	length.
	 */
	public static String encode(byte[] buf, int off, int len)
	{
		if (off < 0 || len < 0) {
			throw new IndexOutOfBoundsException();
		}
		StringBuilder sb = new StringBuilder(len * 2);
		for (int i = off; i < off + len; i++) {
			byte b = buf[i];
			sb.append(HEX_DIGITS[(b >> 4) & 0xF]);
			sb.append(HEX_DIGITS[b & 0xF]);
		}
		return sb.toString();
	}

	/**
	 * Decodes the given hexadecimal encoded {@code String}. Whitespace
	 * characters, namely {@code '\t', ' ', '\n'} and {@code '\r'}, are
	 * ignored.
	 *
	 * @param hex the {@code String} to decode.
	 *
	 * @return the decoded data.
	 *
	 * @throws NullPointerException if {@code hex} is {@code null}.
	 * @throws IllegalArgumentException if {@code hex} is not a valid Base16
	 *	{@code String}.
	 */
	public static byte[] decode(String hex)
	{
		String encoded = hex.replaceAll("[\\s\n\t\r]", "");
		int len = encoded.length();
		Parameters.checkCondition(len % 2 == 0);
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			int val = intValue(encoded.charAt(i)) << 4;
			val += intValue(encoded.charAt(i + 1));
			data[i / 2] = (byte) (val & 0xFF);
		}
		return data;
	}

	private static int intValue(char hex)
	{
		char c = toUpperCase(hex);
		for (int i = 0; i < HEX_DIGITS.length; i++) {
			if (HEX_DIGITS[i] == c) {
				return i;
			}
		}
		throw new IllegalArgumentException();
	}

	private static char toUpperCase(char c)
	{
		return c >= 'a' ? (char) (c - 32) : c;
	}

	private Base16()
	{
		/* ... */
	}
}
