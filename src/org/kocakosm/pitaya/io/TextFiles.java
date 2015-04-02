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

package org.kocakosm.pitaya.io;

import org.kocakosm.pitaya.charset.Charsets;
import org.kocakosm.pitaya.util.Parameters;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Text files utilities.
 *
 * @see XFiles
 *
 * @author Osman KOCAK
 */
public final class TextFiles
{
	/**
	 * Returns the first (up to 10) lines of the given {@code File} using
	 * the system's default charset. Named after the Unix command of the
	 * same name.
	 *
	 * @param f the {@code File} to read.
	 *
	 * @return the first lines of the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> head(File f) throws IOException
	{
		return head(f, Charsets.DEFAULT);
	}

	/**
	 * Returns the first (up to 10) lines of the given {@code File} using
	 * the specified charset. Named after the Unix command of the same name.
	 *
	 * @param f the {@code File} to read.
	 * @param charset the charset to use.
	 *
	 * @return the first lines of the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> head(File f, Charset charset) throws IOException
	{
		return head(f, 10, charset);
	}

	/**
	 * Returns the first (up to {@code n}) lines of the given {@code File}
	 * using the system's default charset. Named after the Unix command of
	 * the same name.
	 *
	 * @param f the {@code File} to read.
	 * @param n the maximum number of lines to read.
	 *
	 * @return the first lines of the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IllegalArgumentException if {@code n} is negative.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> head(File f, int n) throws IOException
	{
		return head(f, n, Charsets.DEFAULT);
	}

	/**
	 * Returns the first (up to {@code n}) lines of the given {@code File}
	 * using the specified charset. Named after the Unix command of the same
	 * name.
	 *
	 * @param f the {@code File} to read.
	 * @param n the maximum number of lines to read.
	 * @param charset the charset to use.
	 *
	 * @return the first lines of the given {@code File}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if {@code n} is negative.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> head(File f, int n, Charset charset)
		throws IOException
	{
		Parameters.checkCondition(n >= 0);
		List<String> lines = new ArrayList<String>();
		BufferedReader reader = newReader(f, charset);
		try {
			String line = reader.readLine();
			while (line != null && lines.size() < n) {
				lines.add(line);
				line = reader.readLine();
			}
		} finally {
			IO.close(reader);
		}
		return Collections.unmodifiableList(lines);
	}

	/**
	 * Returns the last (up to 10) lines of the given {@code File} using the
	 * system's default charset. Named after the Unix command of the same
	 * name.
	 *
	 * @param f the {@code File} to read.
	 *
	 * @return the last lines of the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> tail(File f) throws IOException
	{
		return tail(f, Charsets.DEFAULT);
	}

	/**
	 * Returns the last (up to 10) lines of the given {@code File} using the
	 * specified charset. Named after the Unix command of the same name.
	 *
	 * @param f the {@code File} to read.
	 * @param charset the charset to use.
	 *
	 * @return the last lines of the given {@code File}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> tail(File f, Charset charset) throws IOException
	{
		return tail(f, 10, charset);
	}

	/**
	 * Returns the last (up to n) lines of the given {@code File} using the
	 * system's default charset. Named after the Unix command of the same
	 * name.
	 *
	 * @param f the {@code File} to read.
	 * @param n the maximum number of lines to read.
	 *
	 * @return the last lines of the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IllegalArgumentException if {@code n} is negative.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> tail(File f, int n) throws IOException
	{
		return tail(f, n, Charsets.DEFAULT);
	}

	/**
	 * Returns the last (up to n) lines of the given {@code File} using the
	 * specified charset. Named after the Unix command of the same name.
	 *
	 * @param f the {@code File} to read.
	 * @param n the maximum number of lines to read.
	 * @param charset the charset to use.
	 *
	 * @return the last lines of the given {@code File}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if {@code n} is negative.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> tail(File f, int n, Charset charset)
		throws IOException
	{
		Parameters.checkCondition(n >= 0);
		if (n == 0) {
			return Collections.emptyList();
		}
		List<String> lines = new LinkedList<String>();
		BufferedReader reader = newReader(f, charset);
		try {
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				if (lines.size() > n) {
					lines.remove(0);
				}
				line = reader.readLine();
			}
		} finally {
			IO.close(reader);
		}
		return Collections.unmodifiableList(lines);
	}

	/**
	 * Returns a new {@code BufferedReader} to read the given {@code File}
	 * using the system's default charset.
	 *
	 * @param f the file to read from.
	 *
	 * @return a {@code BufferedReader} to read the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws FileNotFoundException if {@code f} doesn't exist, or if it is
	 *	a directory rather than a regular file, or if it can't be opened
	 *	for reading.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static BufferedReader newReader(File f) throws FileNotFoundException
	{
		return newReader(f, Charsets.DEFAULT);
	}

	/**
	 * Returns a new {@code BufferedReader} to read the given {@code File}
	 * using the specified charset.
	 *
	 * @param f the file to read from.
	 * @param charset the charset to use.
	 *
	 * @return a {@code BufferedReader} to read the given {@code File}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws FileNotFoundException if {@code f} doesn't exist, or if it is
	 *	a directory rather than a regular file, or if it can't be opened
	 *	for reading.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static BufferedReader newReader(File f, Charset charset)
		throws FileNotFoundException
	{
		InputStream in = new FileInputStream(f);
		return new BufferedReader(new InputStreamReader(in, charset));
	}

	/**
	 * Returns a new {@code BufferedWriter} to write to the given
	 * {@code File} using the system's default charset.
	 *
	 * @param f the file to write to.
	 * @param options the write options.
	 *
	 * @return a {@code BufferedWriter} to write to the given {@code File}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if incompatible options are given.
	 * @throws FileNotFoundException if {@code f} exists but is a directory
	 *	rather than a regular file, or if it does not exist but cannot
	 *	be created, or if it cannot be opened for any other reason.
	 * @throws IOException if the {@link WriteOption#CREATE} option is given
	 *	and the specified file already exists.
	 * @throws SecurityException if a security manager exists and denies
	 *	write access to {@code f}.
	 */
	public static BufferedWriter newWriter(File f, WriteOption... options)
		throws IOException
	{
		return newWriter(f, Charsets.DEFAULT, options);
	}

	/**
	 * Returns a new {@code BufferedWriter} to write to the given
	 * {@code File} using the specified charset.
	 *
	 * @param f the file to write to.
	 * @param charset the charset to use.
	 * @param options the write options.
	 *
	 * @return a {@code BufferedWriter} to write to the given {@code File}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if incompatible options are given.
	 * @throws FileNotFoundException if {@code f} exists but is a directory
	 *	rather than a regular file, or if it does not exist but cannot
	 *	be created, or if it cannot be opened for any other reason.
	 * @throws IOException if the {@link WriteOption#CREATE} option is given
	 *	and the specified file already exists.
	 * @throws SecurityException if a security manager exists and denies
	 *	write access to {@code f}.
	 */
	public static BufferedWriter newWriter(File f, Charset charset,
		WriteOption... options) throws IOException
	{
		OutputStream out = XFiles.newOutputStream(f, options);
		return new BufferedWriter(new OutputStreamWriter(out, charset));
	}

	/**
	 * Reads the whole content of the given {@code File} as a {@code String}
	 * using the system's default charset.
	 *
	 * @param f the file to read.
	 *
	 * @return the file's content as a {@code String}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static String read(File f) throws IOException
	{
		return read(f, Charsets.DEFAULT);
	}

	/**
	 * Reads the whole content of the given {@code File} as a {@code String}
	 * using the specified charset.
	 *
	 * @param f the file to read.
	 * @param charset the charset to use.
	 *
	 * @return the file's content as a {@code String}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static String read(File f, Charset charset) throws IOException
	{
		BufferedReader in = newReader(f, charset);
		try {
			return CharStreams.read(in);
		} finally {
			IO.close(in);
		}
	}

	/**
	 * Reads all the lines from the given {@code File} using the system's
	 * default charset. Note that the returned {@code List} is immutable.
	 *
	 * @param f the file to read.
	 *
	 * @return the file's lines.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> readLines(File f) throws IOException
	{
		return readLines(f, Charsets.DEFAULT);
	}

	/**
	 * Reads all the lines from the given {@code File} using the specified
	 * charset. Note that the returned {@code List} is immutable.
	 *
	 * @param f the file to read.
	 * @param charset the charset to use.
	 *
	 * @return the file's lines.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static List<String> readLines(File f, Charset charset)
		throws IOException
	{
		BufferedReader in = newReader(f, charset);
		try {
			return CharStreams.readLines(in);
		} finally {
			IO.close(in);
		}
	}

	private TextFiles()
	{
		/* ... */
	}
}
