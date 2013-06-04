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

import java.util.HashMap;
import java.util.Map;

/**
 * Basic Base64 encoding and decoding (RFC 4648).
 *
 * @author Osman KOCAK
 */
public final class Base64
{
	private static final char[] BASE64_CHARS;
	private static final Character PADDING_CHAR;
	private static final Map<Character, Integer> BASE64_VALUES;
	static {
		BASE64_CHARS = (
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
			"abcdefghijklmnopqrstuvwxyz" +
			"0123456789+/"
		).toCharArray();
		PADDING_CHAR = Character.valueOf('=');
		BASE64_VALUES = new HashMap<Character, Integer>();
		for (int i = 0; i < 64; i++) {
			BASE64_VALUES.put(BASE64_CHARS[i], Integer.valueOf(i));
		}
	}

	/**
	 * Encodes the given data bytes into a Base64 {@code String}.
	 *
	 * @param bytes the bytes to encode.
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
	 * Encodes the given data bytes into a Base64 {@code String}.
	 *
	 * @param buf the input buffer.
	 * @param off the input buffer's offset.
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
		StringBuilder sb = new StringBuilder(4 * ((len + 2) / 3));
		int accu = 0;
		int count = 0;
		for (int i = off; i < off + len; i++) {
			accu = (accu << 8) | (buf[i] & 0xFF);
			count += 8;
			while (count >= 6) {
				count -= 6;
				sb.append(BASE64_CHARS[(accu >>> count) & 63]);
			}
		}
		if (count > 0) {
			accu = (accu & (0xFF >>> (8 - count))) << (6 - count);
			sb.append(BASE64_CHARS[accu]);
			int pad = 4 - (sb.length() % 4);
			for (int i = 0; i < pad; i++) {
				sb.append(PADDING_CHAR);
			}
		}
		return sb.toString();
	}

	/**
	 * Decodes a Base64-encoded {@code String}. Decoding is stopped at the
	 * first {@code '='} character found.  Whitespace characters, namely
	 * {@code '\t', ' ', '\n'} and {@code '\r'}, are ignored.
	 *
	 * @param sequence the Base64 characters to decode.
	 *
	 * @return the decoded data.
	 *
	 * @throws NullPointerException if {@code sequence} is {@code null}.
	 * @throws IllegalArgumentException if {@code sequence} is not a valid
	 *	Base64 sequence.
	 */
	public static byte[] decode(CharSequence sequence)
	{
		String encoded = sequence.toString().replaceAll("[\\s\n\t\r]", "");
		int len = encoded.length();
		Parameters.checkCondition(len % 4 == 0);
		ByteBuffer buf = new ByteBuffer((len * 3) / 4);
		int accu = 0;
		int count = 0;
		for (int i = 0; i < len; i++) {
			Character c = Character.valueOf(encoded.charAt(i));
			if (PADDING_CHAR.equals(c)) {
				break;
			}
			Parameters.checkCondition(BASE64_VALUES.containsKey(c));
			accu = (accu << 6) | BASE64_VALUES.get(c).intValue();
			count += 6;
			while (count >= 8) {
				count -= 8;
				buf.append((byte)(accu >>> count));
			}
		}
		return buf.toByteArray();
	}

	private Base64()
	{
		/* ... */
	}
}
