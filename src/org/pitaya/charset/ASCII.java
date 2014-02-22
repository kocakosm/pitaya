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

package org.pitaya.charset;

/**
 * Utility class that only contains methods that operate on or returns ASCII
 * {@code char}s and {@code String}s.
 *
 * @author Osman KOCAK
 */
public final class ASCII
{
	/**
	 * Returns the ASCII encoding of the given {@code String}.
	 *
	 * @param str the {@code String} to encode.
	 *
	 * @return the ASCII encoding of the given {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static byte[] encode(String str)
	{
		return str.getBytes(Charsets.US_ASCII);
	}

	/**
	 * Returns the ASCII encoding of the specified character sequence.
	 *
	 * @param str the input {@code String}.
	 * @param off the start index, inclusive.
	 * @param len the number of characters to encode.
	 *
	 * @return the ASCII encoding of the specified characters.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code str}'s
	 *	length.
	 */
	public static byte[] encode(String str, int off, int len)
	{
		return encode(str.substring(off, off + len));
	}

	/**
	 * Decodes the given ASCII encoded characters.
	 *
	 * @param input the ASCII encoded characters to decode.
	 *
	 * @return the decoded characters.
	 *
	 * @throws NullPointerException if {@code input} is {@code null}.
	 */
	public static String decode(byte[] input)
	{
		return decode(input, 0, input.length);
	}

	/**
	 * Decodes {@code len} ASCII encoded bytes from the given input buffer,
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
	 */
	public static String decode(byte[] input, int off, int len)
	{
		return new String(input, off, len, Charsets.US_ASCII);
	}

	/**
	 * Returns whether the given character represents a digit.
	 *
	 * @param c the character to test.
	 *
	 * @return whether the given character represents a digit.
	 */
	public static boolean isDigit(char c)
	{
		return c >= '0' && c <= '9';
	}

	/**
	 * Returns whether the given character is a letter.
	 *
	 * @param c the character to test.
	 *
	 * @return whether the given character is a letter.
	 */
	public static boolean isLetter(char c)
	{
		return isLowerCase(c) || isUpperCase(c);
	}

	/**
	 * Returns whether the given {@code String} is an alphabetic sequence, 
	 * that is, a sequence which only contains letters.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return whether the given {@code String} is an alphabetic sequence.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static boolean isAlphabetic(String str)
	{
		for (char c : str.toCharArray()) {
			if (!isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given {@code String} is a numeric sequence, that 
	 * is, a sequence which only contains digits.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return whether the given {@code String} is a numeric sequence.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static boolean isNumeric(String str)
	{
		for (char c : str.toCharArray()) {
			if (!isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given {@code String} is an alphanumeric sequence,
	 * that is, a sequence which only contains letters and digits.
	 *
	 * @param str the {@code String} to test.
	 *
	 * @return whether the given {@code String} is an alphanumeric sequence.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static boolean isAlphaNumeric(String str)
	{
		for (char c : str.toCharArray()) {
			if (!isDigit(c) && !isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given character is one of the lowercase ASCII
	 * characters between 'a' and 'z' (inclusive). All others return false.
	 *
	 * @param c the character to test.
	 *
	 * @return whether the given character is a lowercase character.
	 */
	public static boolean isLowerCase(char c)
	{
		return c >= 'a' && c <= 'z';
	}

	/**
	 * Returns whether the given character is one of the uppercase ASCII
	 * characters between 'A' and 'Z' (inclusive). All others return false.
	 *
	 * @param c the character to test.
	 *
	 * @return whether the given character is a uppercase character.
	 */
	public static boolean isUpperCase(char c)
	{
		return c >= 'A' && c <= 'Z';
	}

	/**
	 * Returns the lowercase equivalent of the given character. If the given
	 * character doesn't represent an uppercase ASCII character, this method
	 * simply returns the given argument.
	 *
	 * @param c the character to transform lowercase.
	 *
	 * @return the lowercase equivalent of the given character if it is an
	 *	uppercase one, or the argument itself otherwise.
	 */
	public static char toLowerCase(char c)
	{
		return isUpperCase(c) ? (char) (c + 32) : c;
	}

	/**
	 * Returns the uppercase equivalent of the given character. If the given
	 * character doesn't represent a lowercase ASCII character, this method
	 * simply returns the given argument.
	 *
	 * @param c the character to transform uppercase.
	 *
	 * @return the uppercase equivalent of the given character if it is a
	 *	lowercase one, or the argument itself otherwise.
	 */
	public static char toUpperCase(char c)
	{
		return isLowerCase(c) ? (char) (c - 32) : c;
	}

	/**
	 * Returns a {@code String} in which all uppercase ASCII characters have
	 * been replaced by their lowercase equivalent (all other characters are
	 * unchanged).
	 *
	 * @param str the {@code String} to convert into lowercase.
	 *
	 * @return the converted {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String toLowerCase(String str)
	{
		StringBuilder sb = new StringBuilder(str.length());
		for (char c : str.toCharArray()) {
			sb.append(toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * Returns a {@code String} in which all lowercase ASCII characters have
	 * been replaced by their uppercase equivalent (all other characters are
	 * unchanged).
	 *
	 * @param str the {@code String} to convert into uppercase.
	 *
	 * @return the converted {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String toUpperCase(String str)
	{
		StringBuilder sb = new StringBuilder(str.length());
		for (char c : str.toCharArray()) {
			sb.append(toUpperCase(c));
		}
		return sb.toString();
	}

	/**
	 * Returns a {@code String} built from the given one by upper-casing its
	 * first character (other characters are copied unchanged).
	 * 
	 * @param str the {@code String} to capitalize.
	 *
	 * @return the capitalized {@code String}.
	 *
	 * @throws NullPointerException if {@code str} is {@code null}.
	 */
	public static String capitalize(String str)
	{
		StringBuilder sb = new StringBuilder(str.length());
		for (char c : str.toCharArray()) {
			sb.append(sb.length() > 0 ? c : toUpperCase(c));
		}
		return sb.toString();
	}

	private ASCII()
	{
		/* ... */
	}
}
