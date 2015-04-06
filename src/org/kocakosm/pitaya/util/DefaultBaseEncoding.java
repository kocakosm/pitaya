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
 * Default {@link BaseEncoding}'s implementation. Instances of this class are
 * immutable.
 *
 * @author Osman KOCAK
 */
final class DefaultBaseEncoding implements BaseEncoding
{
	private static final char PADDING_CHAR = '=';

	private final int n;
	private final String separator;
	private final Alphabet alphabet;
	private final boolean omitPadding;
	private final boolean ignoreUnknownChars;

	DefaultBaseEncoding(Alphabet alphabet)
	{
		this(alphabet, !alphabet.requiresPadding(), false, "", -1);
	}

	private DefaultBaseEncoding(Alphabet alphabet, boolean omitPadding,
		boolean ignoreUnknownChars, String separator, int n)
	{
		this.alphabet = alphabet;
		this.omitPadding = omitPadding;
		this.ignoreUnknownChars = ignoreUnknownChars;
		this.separator = separator;
		this.n = n;
	}

	@Override
	public BaseEncoding withSeparator(String separator, int n)
	{
		Parameters.checkCondition(n > 0);
		for (char c : separator.toCharArray()) {
			if (c == PADDING_CHAR || alphabet.decode(c) != -1) {
				throw new IllegalArgumentException(
					"Invalid separator character: '" + c + "'");
			}
		}
		return new DefaultBaseEncoding(alphabet, omitPadding,
			ignoreUnknownChars, separator, n);
	}

	@Override
	public BaseEncoding withoutPadding()
	{
		return new DefaultBaseEncoding(alphabet, true,
			ignoreUnknownChars, separator, n);
	}

	@Override
	public BaseEncoding ignoreUnknownCharacters()
	{
		return new DefaultBaseEncoding(alphabet, omitPadding, true,
			separator, n);
	}

	@Override
	public String encode(byte... in)
	{
		return encode(in, 0, in.length);
	}

	@Override
	public String encode(byte[] in, int off, int len)
	{
		checkBounds(in, off, len);
		StringBuilder sb = new StringBuilder(maxEncodedLength(len));
		int accu = 0;
		int count = 0;
		int b = alphabet.bitsPerChar();
		for (int i = off; i < off + len; i++) {
			accu = (accu << 8) | (in[i] & 0xFF);
			count += 8;
			while (count >= b) {
				count -= b;
				sb.append(alphabet.encode(accu >>> count));
			}
		}
		if (count > 0) {
			accu = (accu & (0xFF >>> (8 - count))) << (b - count);
			sb.append(alphabet.encode(accu));
			if (!omitPadding) {
				int c = alphabet.charsPerBlock();
				int pad = c - (sb.length() % c);
				for (int i = 0; i < pad; i++) {
					sb.append(PADDING_CHAR);
				}
			}
		}
		insertSeparators(sb);
		return sb.toString();
	}

	private void checkBounds(byte[] in, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > in.length) {
			throw new IndexOutOfBoundsException();
		}
	}

	private int maxEncodedLength(int l)
	{
		return alphabet.charsPerBlock()
			* (l / alphabet.bytesPerBlock() + 1)
			+ alphabet.maxPaddingLength()
			+ (n > 0 ? (l / n) * separator.length() : 0);
	}

	private void insertSeparators(StringBuilder sb)
	{
		if (n > 0) {
			int len = sb.length();
			for (int i = n; i < len; i += n + separator.length()) {
				sb.insert(i, separator);
			}
		}
	}

	@Override
	public byte[] decode(String in)
	{
		String encoded = n > 0 ? in.replace(separator, "") : in;
		ByteBuffer buf = new ByteBuffer(maxDecodedLength(encoded.length()));
		int accu = 0;
		int count = 0;
		int decoded = 0;
		for (int i = 0; i < encoded.length(); i++) {
			char c = encoded.charAt(i);
			if (isPaddingChar(c) && !omitPadding) {
				break;
			}
			int v = decode(c);
			if (v != -1) {
				decoded++;
				accu = (accu << alphabet.bitsPerChar()) | v;
				count += alphabet.bitsPerChar();
				while (count >= 8) {
					count -= 8;
					buf.append((byte) (accu >>> count));
				}
			}
		}
		if (!omitPadding || !alphabet.requiresPadding()) {
			decoded += getPaddingLength(encoded);
			int blockSize = alphabet.charsPerBlock();
			Parameters.checkCondition(decoded % blockSize == 0);
		}
		return buf.toByteArray();
	}

	private int maxDecodedLength(int l)
	{
		return alphabet.bytesPerBlock()
			* (l / alphabet.charsPerBlock() + 1);
	}

	private boolean isPaddingChar(char c)
	{
		return c == PADDING_CHAR && alphabet.requiresPadding();
	}

	private int decode(char c)
	{
		int decoded = alphabet.decode(c);
		if (decoded == -1 && !ignoreUnknownChars) {
			throw new IllegalArgumentException(
				"Unknown character: '" + c + "'");
		}
		return decoded;
	}

	private int getPaddingLength(String in)
	{
		int i = in.indexOf(PADDING_CHAR);
		if (i < 0) {
			return 0;
		}
		int count = 1;
		while (++i < in.length()) {
			char c = in.charAt(i);
			if (c != PADDING_CHAR && (alphabet.decode(c) != -1 || !ignoreUnknownChars)) {
				throw new IllegalArgumentException(
					"Invalid padding character: '" + c + "'");
			}
			count++;
		}
		return count;
	}

	@Override
	public byte[] decode(String in, int off, int len)
	{
		return decode(in.substring(off, off + len));
	}
}
