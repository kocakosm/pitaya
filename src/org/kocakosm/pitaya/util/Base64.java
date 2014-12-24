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

import java.util.HashMap;
import java.util.Map;

/**
 * Base64 encoding scheme (RFC 4648).
 *
 * @author Osman KOCAK
 */
final class Base64 extends AbstractBaseEncoding
{
	private static final char PADDING_CHAR;
	private static final char[] BASE64_CHARS;
	private static final Map<Character, Integer> BASE64_VALUES;
	static {
		PADDING_CHAR = '=';
		BASE64_CHARS = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz"
			+ "0123456789+/").toCharArray();
		BASE64_VALUES = new HashMap<Character, Integer>(64);
		for (int i = 0; i < 64; i++) {
			BASE64_VALUES.put(BASE64_CHARS[i], i);
		}
	}

	@Override
	public String encode(byte[] in, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > in.length) {
			throw new IndexOutOfBoundsException();
		}
		StringBuilder sb = new StringBuilder(4 * ((len + 2) / 3));
		int accu = 0;
		int count = 0;
		for (int i = off; i < off + len; i++) {
			accu = (accu << 8) | (in[i] & 0xFF);
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

	@Override
	public byte[] decode(String in)
	{
		String base64 = in.replaceAll("[\\s\n\t\r]", "");
		int len = base64.length();
		Parameters.checkCondition(len % 4 == 0);
		ByteBuffer buf = new ByteBuffer((len * 3) / 4);
		int accu = 0;
		int count = 0;
		for (char c : base64.toCharArray()) {
			if (c == PADDING_CHAR) {
				break;
			}
			Parameters.checkCondition(BASE64_VALUES.containsKey(c));
			accu = (accu << 6) | BASE64_VALUES.get(c);
			count += 6;
			while (count >= 8) {
				count -= 8;
				buf.append((byte) (accu >>> count));
			}
		}
		return buf.toByteArray();
	}
}
