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
 * RFC 4648 encoding scheme. Instances of this class are immutable.
 *
 * @see <a href="https://www.ietf.org/rfc/rfc4648.txt">RFC 4648</a>
 *
 * @author Osman KOCAK
 */
public final class BaseEncoding
{
	/** Base64 encoding scheme. */
	public static final BaseEncoding BASE_64 = new BaseEncoding(Alphabet.BASE_64);

	/** Base64 encoding scheme with URL and filename safe alphabet. */
	public static final BaseEncoding BASE_64_URL = new BaseEncoding(Alphabet.BASE_64_URL);

	/** Base32 encoding scheme. */
	public static final BaseEncoding BASE_32 = new BaseEncoding(Alphabet.BASE_32);

	/** Base32 encoding scheme with extended Hex alphabet. */
	public static final BaseEncoding BASE_32_HEX = new BaseEncoding(Alphabet.BASE_32_HEX);

	/** Base16 encoding scheme. */
	public static final BaseEncoding BASE_16 = new BaseEncoding(Alphabet.BASE_16);

	private static final char PADDING_CHAR = '=';

	private final int n;
	private final String separator;
	private final Alphabet alphabet;
	private final boolean omitPadding;
	private final boolean ignoreUnknownChars;

	private BaseEncoding(Alphabet alphabet)
	{
		this(alphabet, "", -1, !alphabet.requiresPadding(), false);
	}

	private BaseEncoding(Alphabet alphabet, String separator, int n,
		boolean omitPadding, boolean ignoreUnknownChars)
	{
		this.alphabet = alphabet;
		this.omitPadding = omitPadding;
		this.ignoreUnknownChars = ignoreUnknownChars;
		this.separator = separator;
		this.n = n;
	}

	/**
	 * Returns a {@code BaseEncoding} that behaves as this one except that
	 * it adds the specified separator after every {@code n} characters when
	 * encoding data and ignores any occurence of the specified separator
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
	public BaseEncoding withSeparator(String separator, int n)
	{
		Parameters.checkCondition(n > 0);
		for (char c : separator.toCharArray()) {
			if (isPaddingChar(c) || isInAlphabet(c)) {
				throw new IllegalArgumentException(
					"Invalid separator character: '" + c + "'");
			}
		}
		return new BaseEncoding(alphabet, separator, n, omitPadding,
			ignoreUnknownChars);
	}

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
	public BaseEncoding withoutPadding()
	{
		return new BaseEncoding(alphabet, separator, n, true,
			ignoreUnknownChars);
	}

	/**
	 * Returns a {@code BaseEncoding} that behaves as this one except that
	 * it ignores any unknown character when decoding encoded data.
	 *
	 * See <a href="http://tools.ietf.org/html/rfc4648#section-3.3">RFC 4648
	 * section 3.3</a> for more details.
	 *
	 * @return a {@code BaseEncoding} with the desired configuration.
	 */
	public BaseEncoding ignoreUnknownCharacters()
	{
		return new BaseEncoding(alphabet, separator, n, omitPadding, true);
	}

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
	public String encode(byte... in)
	{
		return encode(in, 0, in.length);
	}

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
				len += separator.length();
			}
		}
	}

	/**
	 * Decodes the given encoded {@code String} according to this
	 * {@code BaseEncoding}'s configuration. Decoding is not case-sensitive
	 * when possible (namely for Base16, Base32 and Base32Hex schemes).
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

	private boolean isInAlphabet(char c)
	{
		return alphabet.decode(c) != -1;
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
			if (c == PADDING_CHAR) {
				count++;
			} else if (isInAlphabet(c) || !ignoreUnknownChars) {
				throw new IllegalArgumentException(
					"Invalid padding character: '" + c + "'");
			}
		}
		return count;
	}

	/**
	 * Decodes the specified range from the given encoded {@code String}
	 * according to this {@code BaseEncoding}'s configuration. Decoding is
	 * not case-sensitive when possible (namely for Base16, Base32 and
	 * Base32Hex schemes).
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
	public byte[] decode(String in, int off, int len)
	{
		return decode(in.substring(off, off + len));
	}
}
