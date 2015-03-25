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

/**
 * Base16 encoding scheme (RFC 4648).
 *
 * @author Osman KOCAK
 */
final class Base16 extends AbstractBaseEncoding
{
	private static final char[] HEX_DIGITS = new char[] {
		'0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	};

	@Override
	public BaseEncoding withoutPadding()
	{
		return this;
	}

	@Override
	public String encode(byte[] in, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > in.length) {
			throw new IndexOutOfBoundsException();
		}
		StringBuilder sb = new StringBuilder(len * 2);
		for (int i = off; i < off + len; i++) {
			byte b = in[i];
			sb.append(HEX_DIGITS[(b >> 4) & 0xF]);
			sb.append(HEX_DIGITS[b & 0xF]);
		}
		return sb.toString();
	}

	@Override
	public byte[] decode(String in)
	{
		String hex = formatInput(in);
		int len = hex.length();
		Parameters.checkCondition(len % 2 == 0);
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			int val = intValue(hex.charAt(i)) << 4;
			val += intValue(hex.charAt(i + 1));
			data[i / 2] = (byte) (val & 0xFF);
		}
		return data;
	}

	private String formatInput(String in)
	{
		return ASCII.toUpperCase(in.replaceAll("[\\s\n\t\r]", ""));
	}

	private int intValue(char hex)
	{
		for (int i = 0; i < HEX_DIGITS.length; i++) {
			if (hex == HEX_DIGITS[i]) {
				return i;
			}
		}
		throw new IllegalArgumentException();
	}
}
