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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Byte streams utilities.
 *
 * @author Osman KOCAK
 */
public final class ByteStreams
{
	/**
	 * Concatenates the given {@code InputStream}s into a single one. The
	 * given streams will be read sequentially. Closing the concatenated
	 * stream will close all the source streams. Note that the returned
	 * {@code InputStream} is not thread-safe and does not support
	 * {@link InputStream#mark(int)} and {@link InputStream#reset()}.
	 *
	 * @param streams the streams to concatenate.
	 *
	 * @return the concatenated {@code InputStream}.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 * @throws IllegalArgumentException if {@code streams} is empty.
	 */
	public static InputStream concat(InputStream... streams)
	{
		return new ConcatInputStream(streams);
	}

	/**
	 * Concatenates the given {@code InputStream}s into a single one. The
	 * given streams will be read sequentially. Closing the concatenated
	 * stream will close all the source streams. Note that the returned
	 * {@code InputStream} is not thread-safe and does not support
	 * {@link InputStream#mark(int)} and {@link InputStream#reset()}.
	 *
	 * @param streams the streams to concatenate.
	 *
	 * @return the concatenated {@code InputStream}.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 * @throws IllegalArgumentException if {@code streams} is empty.
	 */
	public static InputStream concat(List<? extends InputStream> streams)
	{
		return new ConcatInputStream(streams);
	}

	/**
	 * Copies the content of the given {@code InputStream} into the given
	 * {@code OutputStream}.
	 *
	 * @param in the stream to read from.
	 * @param out the stream to write on.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public static void copy(InputStream in, OutputStream out) throws IOException
	{
		byte[] buf = new byte[4096];
		int len = in.read(buf);
		while (len >= 0) {
			out.write(buf, 0, len);
			len = in.read(buf);
		}
		out.flush();
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
	public static boolean equals(InputStream in1, InputStream in2)
		throws IOException
	{
		if (in1 == in2) {
			return true;
		}
		if (in1 == null || in2 == null) {
			return false;
		}
		in1 = buffer(in1);
		in2 = buffer(in2);
		int b1 = in1.read();
		int b2 = in2.read();
		while (b1 != -1 && b2 != -1 && b1 == b2) {
			b1 = in1.read();
			b2 = in2.read();
		}
		return in1.read() == -1 && in2.read() == -1;
	}

	private static InputStream buffer(InputStream in)
	{
		if (in instanceof BufferedInputStream) {
			return in;
		}
		return new BufferedInputStream(in);
	}

	/**
	 * Returns an {@code InputStream} that will only supply bytes from the
	 * given {@code InputStream} up to the specified limit. The returned
	 * {@code InputStream} is thread-safe.
	 *
	 * @param in the source stream.
	 * @param limit the maximum number of bytes to be read from {@code in}.
	 *
	 * @return the length-limited {@code InputStream}.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IllegalArgumentException if {@code limit} is negative.
	 */
	public static InputStream limit(InputStream in, long limit)
	{
		return new LimitInputStream(in, limit);
	}

	/**
	 * Reads the content of the given {@code InputStream}.
	 *
	 * @param in the stream to read.
	 *
	 * @return the stream's content.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IOException if {@code in} can't be read.
	 */
	public static byte[] read(InputStream in) throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(in, out);
		return out.toByteArray();
	}

	/**
	 * Returns an {@code OutputStream} that writes all bytes written to it
	 * to the given underlying streams. Named after the Unix 'tee' command.
	 * The returned instance is not thread-safe.
	 *
	 * @param streams the streams to write to.
	 *
	 * @return the 'tee' {@code OutputStream}.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static OutputStream tee(OutputStream... streams)
	{
		return new TeeOutputStream(streams);
	}

	/**
	 * Returns an {@code OutputStream} that writes all bytes written to it
	 * to the given underlying streams. Named after the Unix 'tee' command.
	 * The returned instance is not thread-safe.
	 *
	 * @param streams the streams to write to.
	 *
	 * @return the 'tee' {@code OutputStream}.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public static OutputStream tee(Iterable<? extends OutputStream> streams)
	{
		return new TeeOutputStream(streams);
	}

	private ByteStreams()
	{
		/* ... */
	}
}
