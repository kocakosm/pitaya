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

import org.kocakosm.pitaya.charset.ASCII;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Base32 encoding scheme (RFC 4648).
 *
 * @author Osman KOCAK
 */
final class Base32 extends AbstractBaseEncoding
{
	private static final char PADDING_CHAR = '=';

	private final boolean padding;
	private final char[] alphabet;
	private final Map<Character, Integer> values;

	Base32()
	{
		this("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray());
	}

	Base32(char... alphabet)
	{
		Parameters.checkCondition(alphabet.length == 32);
		this.padding = true;
		this.alphabet = Arrays.copyOf(alphabet, alphabet.length);
		this.values = new HashMap<Character, Integer>(32);
		for (int i = 0; i < 32; i++) {
			values.put(this.alphabet[i], i);
		}
		Parameters.checkCondition(!values.containsKey(PADDING_CHAR));
	}

	private Base32(boolean padding, Map<Character, Integer> values,
		char... alphabet)
	{
		this.padding = padding;
		this.values = values;
		this.alphabet = alphabet;
	}

	@Override
	public BaseEncoding withoutPadding()
	{
		return new Base32(false, values, alphabet);
	}

	@Override
	public String encode(byte[] in, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > in.length) {
			throw new IndexOutOfBoundsException();
		}
		StringBuilder sb = new StringBuilder(8 * ((len + 6) / 5));
		int accu = 0;
		int count = 0;
		for (int i = off; i < off + len; i++) {
			accu = (accu << 8) | (in[i] & 0xFF);
			count += 8;
			while (count >= 5) {
				count -= 5;
				sb.append(alphabet[(accu >>> count) & 31]);
			}
		}
		if (count > 0) {
			accu = (accu & (0xFF >>> (8 - count))) << (5 - count);
			sb.append(alphabet[accu]);
			if (padding) {
				int pad = 8 - (sb.length() % 8);
				for (int i = 0; i < pad; i++) {
					sb.append(PADDING_CHAR);
				}
			}
		}
		return sb.toString();
	}

	@Override
	public byte[] decode(String in)
	{
		String base32 = formatInput(in);
		int len = base32.length();
		Parameters.checkCondition(padding ? len % 8 == 0 : true);
		ByteBuffer buf = new ByteBuffer((len * 5) / 8);
		int accu = 0;
		int count = 0;
		for (char c : base32.toCharArray()) {
			if (c == PADDING_CHAR) {
				break;
			}
			Parameters.checkCondition(values.containsKey(c));
			accu = (accu << 5) | values.get(c);
			count += 5;
			while (count >= 8) {
				count -= 8;
				buf.append((byte) (accu >>> count));
			}
		}
		return buf.toByteArray();
	}

	private String formatInput(String in)
	{
		return ASCII.toUpperCase(in.replaceAll("[\\s\n\t\r]", ""));
	}
}
