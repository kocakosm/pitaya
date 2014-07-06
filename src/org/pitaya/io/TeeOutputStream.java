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

package org.pitaya.io;

import org.pitaya.util.Parameters;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A decorating {@code OutputStream} that writes all bytes written to it to the
 * given underlying streams. Named after the Unix 'tee' command. Not thread-safe.
 *
 * @author Osman KOCAK
 */
final class TeeOutputStream extends OutputStream
{
	private final List<OutputStream> streams;

	/**
	 * Creates a new {@code TeeOutputStream}.
	 *
	 * @param streams the streams to write to.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	TeeOutputStream(OutputStream... streams)
	{
		this(Arrays.asList(streams));
	}

	/**
	 * Creates a new {@code TeeOutputStream}.
	 *
	 * @param streams the streams to write to.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it returns a {@code null} reference.
	 */
	TeeOutputStream(Iterable<? extends OutputStream> streams)
	{
		this.streams = new ArrayList<OutputStream>();
		for (OutputStream stream : streams) {
			Parameters.checkNotNull(stream);
			this.streams.add(stream);
		}
	}

	@Override
	public void close() throws IOException
	{
		for (OutputStream out : streams) {
			out.close();
		}
	}

	@Override
	public void flush() throws IOException
	{
		for (OutputStream out : streams) {
			out.flush();
		}
	}

	@Override
	public void write(int i) throws IOException
	{
		for (OutputStream out : streams) {
			out.write(i);
		}
	}

	@Override
	public void write(byte[] b) throws IOException
	{
		for (OutputStream out : streams) {
			out.write(b);
		}
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		for (OutputStream out : streams) {
			out.write(b, off, len);
		}
	}
}
