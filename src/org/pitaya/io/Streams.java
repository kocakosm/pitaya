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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utiliy methods to deal with {@link InputStream}s and {@link OutputStream}s.
 *
 * @author Osman KOCAK
 */
public final class Streams
{
	/**
	 * Forwards the content of the given {@link InputStream} into the given
	 * {@link OutputStream}.
	 *
	 * @param in the stream to read.
	 * @param out the stream to write on.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public static void pipe(InputStream in, OutputStream out)
		throws IOException
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
	 * Silently closes the given {@link InputStream}.
	 *
	 * @param in the stream to close, may be {@code null}.
	 */
	public static void close(InputStream in)
	{
		if (in != null) {
			try {
				in.close();
			} catch (IOException ex) {
				/* Ignored... */
			}
		}
	}

	/**
	 * Silently closes the given {@link OutputStream}.
	 *
	 * @param out the stream to close, may be {@code null}.
	 */
	public static void close(OutputStream out)
	{
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (IOException ex) {
				/* Ignored... */
			}
		}
	}

	/**
	 * Reads the content of the given {@link InputStream}.
	 *
	 * @param in the stream to read.
	 *
	 * @return the stream's content.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IOException if the data cannot be read.
	 */
	public static byte[] read(InputStream in) throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		pipe(in, out);
		return out.toByteArray();
	}

	private Streams()
	{
		/* ... */
	}
}
