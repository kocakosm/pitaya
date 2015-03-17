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

package org.kocakosm.pitaya.io;

import org.kocakosm.pitaya.charset.Charsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Character streams utilities.
 *
 * @author Osman KOCAK
 */
public final class CharStreams
{
	/**
	 * Concatenates the given {@code Reader}s into a single one. The given
	 * streams will be read sequentially. Closing the concatenated stream
	 * will close all the source streams. Note that the returned
	 * {@code Reader} is not thread-safe and does not support
	 * {@link Reader#mark(int)} and {@link Reader#reset()}.
	 *
	 * @param streams the streams to concatenate.
	 *
	 * @return the concatenated {@code Reader}.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 * @throws IllegalArgumentException if {@code streams} is empty.
	 */
	public static Reader concat(Reader... streams)
	{
		return new ConcatReader(streams);
	}

	/**
	 * Concatenates the given {@code Reader}s into a single one. The given
	 * streams will be read sequentially. Closing the concatenated stream
	 * will close all the source streams. Note that the returned
	 * {@code Reader} is not thread-safe and does not support
	 * {@link Reader#mark(int)} and {@link Reader#reset()}.
	 *
	 * @param streams the streams to concatenate.
	 *
	 * @return the concatenated {@code Reader}.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 * @throws IllegalArgumentException if {@code streams} is empty.
	 */
	public static Reader concat(List<? extends Reader> streams)
	{
		return new ConcatReader(streams);
	}

	/**
	 * Copies the content of the given {@code Reader} into the given
	 * {@code Writer}.
	 *
	 * @param in the {@code Reader} to read from.
	 * @param out the {@code Reader} to write on.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public static void copy(Reader in, Writer out) throws IOException
	{
		char[] buf = new char[4096];
		int len = in.read(buf);
		while (len >= 0) {
			out.write(buf, 0, len);
			len = in.read(buf);
		}
		out.flush();
	}

	/**
	 * Copies the content of the given {@code InputStream} into the given
	 * {@code Writer} using the system's default charset.
	 *
	 * @param in the {@code InputStream} to read from.
	 * @param out the {@code Reader} to write on.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public static void copy(InputStream in, Writer out) throws IOException
	{
		copy(new InputStreamReader(in, Charsets.DEFAULT), out);
	}

	/**
	 * Copies the content of the given {@code InputStream} into the given
	 * {@code Writer} using the specified charset.
	 *
	 * @param in the {@code InputStream} to read from.
	 * @param out the {@code Reader} to write on.
	 * @param charset the charset to use.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public static void copy(InputStream in, Writer out, Charset charset)
		throws IOException
	{
		copy(new InputStreamReader(in, charset), out);
	}

	/**
	 * Copies the content of the given {@code Reader} into the given
	 * {@code OutputStream} using the system's default charset.
	 *
	 * @param in the {@code Reader} to read from.
	 * @param out the {@code OutputStream} to write on.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public static void copy(Reader in, OutputStream out) throws IOException
	{
		copy(in, new OutputStreamWriter(out, Charsets.DEFAULT));
	}

	/**
	 * Copies the content of the given {@code Reader} into the given
	 * {@code OutputStream} using the specified charset.
	 *
	 * @param in the {@code Reader} to read from.
	 * @param out the {@code OutputStream} to write on.
	 * @param charset the charset to use.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public static void copy(Reader in, OutputStream out, Charset charset)
		throws IOException
	{
		copy(in, new OutputStreamWriter(out, charset));
	}

	/**
	 * Returns whether the given streams have the same content.
	 *
	 * @param in1 the first stream.
	 * @param in2 the second stream.
	 *
	 * @return {@code true} if the streams have the same content.
	 *
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public static boolean equals(Reader in1, Reader in2) throws IOException
	{
		if (in1 == in2) {
			return true;
		}
		if (in1 == null || in2 == null) {
			return false;
		}
		in1 = buffer(in1);
		in2 = buffer(in2);
		int c1 = in1.read();
		int c2 = in2.read();
		while (c1 != -1 && c2 != -1 && c1 == c2) {
			c1 = in1.read();
			c2 = in2.read();
		}
		return in1.read() == -1 && in2.read() == -1;
	}

	private static BufferedReader buffer(Reader in)
	{
		if (in instanceof BufferedReader) {
			return (BufferedReader) in;
		}
		return new BufferedReader(in);
	}

	/**
	 * Returns an {@code Reader} that will only supply characters from the
	 * given {@code Reader} up to the specified limit. The returned
	 * {@code Reader} is thread-safe.
	 *
	 * @param in the source stream.
	 * @param limit the maximum number of characters to be read from {@code in}.
	 *
	 * @return the length-limited {@code Reader}.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IllegalArgumentException if {@code limit} is negative.
	 */
	public static Reader limit(Reader in, long limit)
	{
		return new LimitReader(in, limit);
	}

	/**
	 * Reads the whole content of the given {@code Reader} into a
	 * {@code String}.
	 *
	 * @param in the stream to read.
	 *
	 * @return the stream's content as a {@code String}.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IOException if the stream cannot be read.
	 */
	public static String read(Reader in) throws IOException
	{
		Writer out = new StringWriter();
		copy(in, out);
		return out.toString();
	}

	/**
	 * Reads the whole content of the given {@code InputStream} into a
	 * {@code String} using the system's default charset.
	 *
	 * @param in the stream to read.
	 *
	 * @return the stream's content as a {@code String}.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IOException if the stream cannot be read.
	 */
	public static String read(InputStream in) throws IOException
	{
		return read(new InputStreamReader(in, Charsets.DEFAULT));
	}

	/**
	 * Reads the whole content of the given {@code InputStream} into a
	 * {@code String} using the specified charset.
	 *
	 * @param in the stream to read.
	 * @param charset the stream's charset.
	 *
	 * @return the stream's content as a {@code String}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if the stream cannot be read.
	 */
	public static String read(InputStream in, Charset charset)
		throws IOException
	{
		return read(new InputStreamReader(in, charset));
	}

	/**
	 * Reads all the lines from the given source {@code Reader}. Note that
	 * the returned {@code List} is immutable.
	 *
	 * @param in the stream to read.
	 *
	 * @return the stream's lines.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IOException if the stream cannot be read.
	 */
	public static List<String> readLines(Reader in) throws IOException
	{
		BufferedReader reader = buffer(in);
		List<String> lines = new ArrayList<String>();
		String line = reader.readLine();
		while (line != null) {
			lines.add(line);
			line = reader.readLine();
		}
		return Collections.unmodifiableList(lines);
	}

	/**
	 * Reads all the lines from the given source {@code InputStream} using
	 * the system's default charset. Note that the returned {@code List} is
	 * immutable.
	 *
	 * @param in the stream to read.
	 *
	 * @return the stream's lines.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IOException if the stream cannot be read.
	 */
	public static List<String> readLines(InputStream in) throws IOException
	{
		return readLines(in, Charsets.DEFAULT);
	}

	/**
	 * Reads all the lines from the given source {@code InputStream} using
	 * the specified charset. Note that the returned {@code List} is immutable.
	 *
	 * @param in the stream to read.
	 * @param charset the charset to use.
	 *
	 * @return the stream's lines.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if the stream cannot be read.
	 */
	public static List<String> readLines(InputStream in, Charset charset)
		throws IOException
	{
		return readLines(new InputStreamReader(in, charset));
	}

	/**
	 * Returns a {@code Writer} that writes all characters written to it
	 * to the given underlying streams. Named after the Unix 'tee' command.
	 * The returned instance is not thread-safe.
	 *
	 * @param streams the streams to write to.
	 *
	 * @return the 'tee' {@code Writer}.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static Writer tee(Writer... streams)
	{
		return new TeeWriter(streams);
	}

	/**
	 * Returns a {@code Writer} that writes all characters written to it
	 * to the given underlying streams. Named after the Unix 'tee' command.
	 * The returned instance is not thread-safe.
	 *
	 * @param streams the streams to write to.
	 *
	 * @return the 'tee' {@code Writer}.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static Writer tee(Iterable<? extends Writer> streams)
	{
		return new TeeWriter(streams);
	}

	private CharStreams()
	{
		/* ... */
	}
}
