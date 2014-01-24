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
	 * Returns the ASCII encoding of the given character sequence.
	 *
	 * @param sequence the character sequence to encode.
	 *
	 * @return the ASCII encoding of the given character sequence.
	 *
	 * @throws NullPointerException if {@code sequence} is {@code null}.
	 */
	public static byte[] encode(CharSequence sequence)
	{
		return sequence.toString().getBytes(Charsets.US_ASCII);
	}

	/**
	 * Returns the ASCII encoding of the specified character sequence.
	 *
	 * @param sequence the input character sequence.
	 * @param off the start index, inclusive.
	 * @param len the number of characters to encode.
	 *
	 * @return the ASCII encoding of the specified characters.
	 *
	 * @throws NullPointerException if {@code sequence} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than the sequence
	 *	length.
	 */
	public static byte[] encode(CharSequence sequence, int off, int len)
	{
		return encode(sequence.subSequence(off, off + len));
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
	 * Returns whether the given {@link CharSequence} is an alphabetic
	 * sequence, that is, a sequence which only contains letters.
	 *
	 * @param sequence the {@link CharSequence} to test.
	 *
	 * @return whether the given sequence is an alphabetic sequence.
	 *
	 * @throws NullPointerException if {@code sequence} is {@code null}.
	 */
	public static boolean isAlphabetic(CharSequence sequence)
	{
		for (char c : sequence.toString().toCharArray()) {
			if (!isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given {@link CharSequence} is a numeric sequence,
	 * that is, a sequence which only contains digits.
	 *
	 * @param sequence the {@link CharSequence} to test.
	 *
	 * @return whether the given sequence is a numeric sequence.
	 *
	 * @throws NullPointerException if {@code sequence} is {@code null}.
	 */
	public static boolean isNumeric(CharSequence sequence)
	{
		for (char c : sequence.toString().toCharArray()) {
			if (!isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns whether the given {@link CharSequence} is an alphanumeric
	 * sequence, that is, a sequence which only contains letters and digits.
	 *
	 * @param sequence the {@link CharSequence} to test.
	 *
	 * @return whether the given sequence is an alphanumeric sequence.
	 *
	 * @throws NullPointerException if {@code sequence} is {@code null}.
	 */
	public static boolean isAlphaNumeric(CharSequence sequence)
	{
		for (char c : sequence.toString().toCharArray()) {
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
	 * Returns a {@link String} in which all uppercase ASCII characters have
	 * been replaced by their lowercase equivalent (all other characters are
	 * unchanged).
	 *
	 * @param s the {@link String} to convert into lowercase.
	 *
	 * @return the converted {@link String}.
	 *
	 * @throws NullPointerException if {@code s} is {@code null}.
	 */
	public static String toLowerCase(String s)
	{
		StringBuilder sb = new StringBuilder(s.length());
		for (char c : s.toCharArray()) {
			sb.append(toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * Returns a {@link String} in which all lowercase ASCII characters have
	 * been replaced by their uppercase equivalent (all other characters are
	 * unchanged).
	 *
	 * @param s the {@link String} to convert into uppercase.
	 *
	 * @return the converted {@link String}.
	 *
	 * @throws NullPointerException if {@code s} is {@code null}.
	 */
	public static String toUpperCase(String s)
	{
		StringBuilder sb = new StringBuilder(s.length());
		for (char c : s.toCharArray()) {
			sb.append(toUpperCase(c));
		}
		return sb.toString();
	}

	/**
	 * Returns a {@link String} built from the given one by upper-casing its
	 * first character while lower-casing the others.
	 * 
	 * @param s the {@link String} to capitalize.
	 *
	 * @return the capitalized {@link String}.
	 *
	 * @throws NullPointerException if {@code s} is {@code null}.
	 */
	public static String capitalize(String s)
	{
		StringBuilder sb = new StringBuilder(s.length());
		for (char c : s.toCharArray()) {
			sb.append(sb.length() > 0 ? toLowerCase(c) : toUpperCase(c));
		}
		return sb.toString();
	}

	private ASCII()
	{
		/* ... */
	}
}
