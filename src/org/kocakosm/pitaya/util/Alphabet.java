/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2016 Osman KOCAK <kocakosm@gmail.com>                   *
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
 * RFC 4648 alphabet. Instances of this class are immutable.
 *
 * @author Osman KOCAK
 */
enum Alphabet
{
	BASE_16("0123456789ABCDEF", false),
	BASE_32("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", false),
	BASE_32_HEX("0123456789ABCDEFGHIJKLMNOPQRSTUV", false),
	BASE_64("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", true),
	BASE_64_URL("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", true);

	private final char[] chars;
	private final int bitsPerChar;
	private final int charsPerBlock;
	private final int bytesPerBlock;
	private final int maxPaddingLength;
	private final boolean caseSensitive;

	private Alphabet(String alphabet, boolean caseSensitive)
	{
		this.chars = alphabet.toCharArray();
		this.caseSensitive = caseSensitive;
		this.bitsPerChar = round(Math.log(chars.length) / Math.log(2));
		int gcd = gcd(8, bitsPerChar);
		this.charsPerBlock = 8 / gcd;
		this.bytesPerBlock = bitsPerChar / gcd;
		this.maxPaddingLength = charsPerBlock - floor(8.0 / bitsPerChar);
	}

	private int round(double x)
	{
		return (int) Math.round(x);
	}

	private int floor(double x)
	{
		return (int) Math.floor(x);
	}

	private int gcd(int a, int b)
	{
		return b == 0 ? a : gcd(b, a % b);
	}

	int decode(char c)
	{
		char encoded = caseSensitive ? c : ASCII.toUpperCase(c);
		for (int i = 0; i < chars.length; i++) {
			if (encoded == chars[i]) {
				return i;
			}
		}
		return -1;
	}

	char encode(int value)
	{
		return chars[value & (chars.length - 1)];
	}

	int bitsPerChar()
	{
		return bitsPerChar;
	}

	int charsPerBlock()
	{
		return charsPerBlock;
	}

	int bytesPerBlock()
	{
		return bytesPerBlock;
	}

	int maxPaddingLength()
	{
		return maxPaddingLength;
	}

	boolean requiresPadding()
	{
		return maxPaddingLength > 0;
	}
}
