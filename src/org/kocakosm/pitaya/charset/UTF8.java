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

package org.kocakosm.pitaya.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Static utility methods to encode and decode UTF-8 characters.
 *
 * @author Osman KOCAK
 */
public final class UTF8
{
	/**
	 * Returns whether the given {@code CharSequence} can be encoded into
	 * UTF-8.
	 *
	 * @param chars the {@code CharSequence} to test.
	 *
	 * @return whether {@code chars} can be encoded into UTF-8.
	 *
	 * @throws NullPointerException if {@code chars} is {@code null}.
	 */
	public static boolean canEncode(CharSequence chars)
	{
		return canEncode(chars, 0, chars.length());
	}

	/**
	 * Returns whether the given characters can be encoded into UTF-8.
	 *
	 * @param chars the characters to test.
	 *
	 * @return whether {@code chars} can be encoded into UTF-8.
	 *
	 * @throws NullPointerException if {@code chars} is {@code null}.
	 */
	public static boolean canEncode(char... chars)
	{
		return canEncode(chars, 0, chars.length);
	}

	/**
	 * Returns whether the specified range in the given {@code CharSequence}
	 * can be encoded into UTF-8.
	 *
	 * @param chars the input {@code CharSequence}.
	 * @param off the start index, inclusive.
	 * @param len the number of characters to test.
	 *
	 * @return whether the specified range in {@code chars} can be encoded
	 *	into UTF-8.
	 *
	 * @throws NullPointerException if {@code chars} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code chars}'
	 *	length.
	 */
	public static boolean canEncode(CharSequence chars, int off, int len)
	{
		return canEncode(CharBuffer.wrap(chars, off, off + len));
	}

	/**
	 * Returns whether the specified range in the given array of
	 * {@code char}s can be encoded into UTF-8.
	 *
	 * @param chars the input array of {@code char}s.
	 * @param off the start index, inclusive.
	 * @param len the number of characters to test.
	 *
	 * @return whether the specified range in {@code chars} can be encoded
	 *	into UTF-8.
	 *
	 * @throws NullPointerException if {@code chars} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code chars}'
	 *	length.
	 */
	public static boolean canEncode(char[] chars, int off, int len)
	{
		return canEncode(CharBuffer.wrap(chars, off, len));
	}

	private static boolean canEncode(CharBuffer buffer)
	{
		return Charsets.UTF_8.newEncoder().canEncode(buffer);
	}

	/**
	 * Returns whether the given byte array represents valid UTF-8 encoded
	 * characters.
	 *
	 * @param input the bytes to test.
	 *
	 * @return whether {@code input} repesents UTF-8 encoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	public static boolean canDecode(byte... input)
	{
		return canDecode(input, 0, input.length);
	}

	/**
	 * Returns whether the specified range in the given byte array
	 * represents valid UTF-8 encoded characters.
	 *
	 * @param input the input buffer.
	 * @param off the start index, inclusive.
	 * @param len the number of bytes to test.
	 *
	 * @return whether the specified range in {@code input} represents UTF-8
	 *	encoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code input}'s
	 *	length.
	 */
	public static boolean canDecode(byte[] input, int off, int len)
	{
		try {
			decode(input, off, len);
		} catch (UncheckedCharacterCodingException ex) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the UTF-8 encoding of the given {@code CharSequence}.
	 *
	 * @param chars the {@code CharSequence} to encode.
	 *
	 * @return the UTF-8 encoding of the given {@code CharSequence}.
	 *
	 * @throws NullPointerException if {@code chars} is {@code null}.
	 * @throws UncheckedCharacterCodingException if {@code chars} can't be
	 * 	encoded into UTF-8.
	 */
	public static byte[] encode(CharSequence chars)
	{
		return encode(chars, 0, chars.length());
	}

	/**
	 * Returns the UTF-8 encoding of the given characters.
	 *
	 * @param chars the characters to encode.
	 *
	 * @return the UTF-8 encoding of the given characters.
	 *
	 * @throws NullPointerException if {@code chars} is {@code null}.
	 * @throws UncheckedCharacterCodingException if {@code chars} can't be
	 * 	encoded into UTF-8.
	 */
	public static byte[] encode(char... chars)
	{
		return encode(chars, 0, chars.length);
	}

	/**
	 * Returns the UTF-8 encoding of the specified range in the given
	 * {@code CharSequence}.
	 *
	 * @param chars the input {@code CharSequence}.
	 * @param off the start index, inclusive.
	 * @param len the number of characters to encode.
	 *
	 * @return the UTF-8 encoding of the specified characters.
	 *
	 * @throws NullPointerException if {@code chars} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code chars}'
	 *	length.
	 * @throws UncheckedCharacterCodingException if {@code chars} can't be
	 * 	encoded into UTF-8.
	 */
	public static byte[] encode(CharSequence chars, int off, int len)
	{
		return encode(CharBuffer.wrap(chars, off, off + len));
	}

	/**
	 * Returns the UTF-8 encoding of the specified range in the given array
	 * of {@code char}s.
	 *
	 * @param chars the input array of {@code char}s.
	 * @param off the start index, inclusive.
	 * @param len the number of characters to encode.
	 *
	 * @return the UTF-8 encoding of the specified characters.
	 *
	 * @throws NullPointerException if {@code chars} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code chars}'
	 *	length.
	 * @throws UncheckedCharacterCodingException if {@code chars} can't be
	 * 	encoded into UTF-8.
	 */
	public static byte[] encode(char[] chars, int off, int len)
	{
		return encode(CharBuffer.wrap(chars, off, len));
	}

	private static byte[] encode(CharBuffer buffer)
	{
		CharsetEncoder encoder = Charsets.UTF_8.newEncoder();
		try {
			ByteBuffer out = encoder.encode(buffer);
			byte[] bytes = new byte[out.limit()];
			out.get(bytes);
			return bytes;
		} catch (CharacterCodingException ex) {
			throw new UncheckedCharacterCodingException(ex);
		}
	}

	/**
	 * Decodes the given UTF-8 encoded characters.
	 *
	 * @param input the UTF-8 encoded characters to decode.
	 *
	 * @return the decoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws UncheckedCharacterCodingException if {@code input} doesn't
	 * 	represent valid UTF-8 encoded characters.
	 */
	public static String decode(byte... input)
	{
		return decode(input, 0, input.length);
	}

	/**
	 * Decodes {@code len} UTF-8 encoded bytes from the given input buffer,
	 * starting at {@code off}.
	 *
	 * @param input the input buffer.
	 * @param off the starting offset.
	 * @param len the number of bytes to decode.
	 *
	 * @return the decoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code input}'s
	 *	length.
	 * @throws UncheckedCharacterCodingException if {@code input} doesn't
	 * 	represent valid UTF-8 encoded characters.
	 */
	public static String decode(byte[] input, int off, int len)
	{
		CharsetDecoder decoder = Charsets.UTF_8.newDecoder();
		ByteBuffer buf = ByteBuffer.wrap(input, off, len);
		try {
			CharBuffer out = decoder.decode(buf);
			char[] chars = new char[out.limit()];
			out.get(chars);
			return new String(chars);
		} catch (CharacterCodingException ex) {
			throw new UncheckedCharacterCodingException(ex);
		}
	}

	private UTF8()
	{
		/* ... */
	}
}
