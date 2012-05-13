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

package org.pitaya.io;

import org.pitaya.util.Booleans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;

/**
 * Very simple utility class to easily read from the standard input. For a more
 * powerful API, use {@link java.util.Scanner}.
 *
 * @author Osman KOCAK
 */
public final class Scanf
{
	/**
	 * Reads the next line from the standard input using the system's
	 * default charset.
	 *
	 * @return the next line read from the standard input.
	 *
	 * @throws IOException if the standard input cannot be read.
	 */
	public static String readString() throws IOException
	{
		return readString(Charset.defaultCharset());
	}

	/**
	 * Reads the next line from the standard input using the given charset.
	 *
	 * @param charset the charset to use.
	 *
	 * @return the next line read from the standard input.
	 *
	 * @throws NullPointerException if {@code charset} is {@code null}.
	 * @throws IOException if the standard input cannot be read.
	 */
	public static String readString(Charset charset) throws IOException
	{
		Reader in = new InputStreamReader(System.in, charset);
		BufferedReader reader = new BufferedReader(in);
		try {
			return reader.readLine();
		} finally {
			reader.close();
		}
	}

	/**
	 * Reads the next line from the standard input and parses it into a
	 * {@code boolean} value. The strings "true", "on", "yes" (ignoring
	 * spaces and case) return {@code true}, all other strings will return
	 * {@code false}.
	 *
	 * @return the parsed {@code boolean} value.
	 *
	 * @throws IOException if the standard input cannot be read.
	 */
	public static boolean readBoolean() throws IOException
	{
		return Booleans.valueOf(readString());
	}

	/**
	 * Reads the next line from the standard input and parses it into an
	 * {@code int} value.
	 *
	 * @return the parsed {@code int} value.
	 *
	 * @throws IOException if the standard input cannot be read.
	 * @throws NumberFormatException if the line cannot be parsed.
	 */
	public static int readInt() throws IOException
	{
		return Integer.valueOf(readString());
	}

	/**
	 * Reads the next line from the standard input and parses it into an
	 * {@code int} value with the specified radix.
	 * 
	 * @param radix the radix to be used in interpreting the read line.
	 *
	 * @return the parsed {@code int} value.
	 *
	 * @throws IOException if the standard input cannot be read.
	 * @throws NumberFormatException if the line cannot be parsed.
	 */
	public static int readInt(int radix) throws IOException
	{
		return Integer.valueOf(readString(), radix);
	}

	/**
	 * Reads the next line from the standard input and parses it into a
	 * {@code long} value.
	 *
	 * @return the parsed {@code long} value.
	 *
	 * @throws IOException if the standard input cannot be read.
	 * @throws NumberFormatException if the line cannot be parsed.
	 */
	public static long readLong() throws IOException
	{
		return Long.valueOf(readString());
	}

	/**
	 * Reads the next line from the standard input and parses it into a
	 * {@code long} value with the specified radix.
	 *
	 * @param radix the radix to be used in interpreting the read line.
	 *
	 * @return the parsed {@code long} value.
	 *
	 * @throws IOException if the standard input cannot be read.
	 * @throws NumberFormatException if the line cannot be parsed.
	 */
	public static long readLong(int radix) throws IOException
	{
		return Long.valueOf(readString(), radix);
	}

	/**
	 * Reads the next line from the standard input and parses it into a
	 * {@link BigInteger} value.
	 *
	 * @return the parsed {@link BigInteger} value.
	 *
	 * @throws IOException if the standard input cannot be read.
	 * @throws NumberFormatException if the line cannot be parsed.
	 */
	public static BigInteger readBigInteger() throws IOException
	{
		return new BigInteger(readString());
	}

	/**
	 * Reads the next line from the standard input and parses it into a
	 * {@link BigInteger} value with the specified radix.
	 *
	 * @param radix the radix to be used in interpreting the read line.
	 *
	 * @return the parsed {@link BigInteger} value.
	 *
	 * @throws IOException if the standard input cannot be read.
	 * @throws NumberFormatException if the line cannot be parsed.
	 */
	public static BigInteger readBigInteger(int radix) throws IOException
	{
		return new BigInteger(readString(), radix);
	}

	/**
	 * Reads the next line from the standard input and parses it into a
	 * {@code double} value.
	 *
	 * @return the parsed {@code double} value.
	 *
	 * @throws IOException if the standard input cannot be read.
	 * @throws NumberFormatException if the line cannot be parsed.
	 */
	public static double readDouble() throws IOException
	{
		return Double.valueOf(readString());
	}

	/**
	 * Reads the next line from the standard input and parses it into a
	 * {@link BigDecimal} value.
	 *
	 * @return the parsed {@link BigDecimal} value.
	 *
	 * @throws IOException if the standard input cannot be read.
	 * @throws NumberFormatException if the line cannot be parsed.
	 */
	public static BigDecimal readBigDecimal() throws IOException
	{
		return new BigDecimal(readString());
	}

	private Scanf()
	{
		/* ... */
	}
}
