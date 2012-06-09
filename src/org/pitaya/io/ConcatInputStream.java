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

import org.pitaya.util.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * An {@code InputStream} which reads sequentially from multiple sources. Mark
 * and reset are not supported. Not thread safe.
 *
 * @author Osman KOCAK
 */
public final class ConcatInputStream extends InputStream
{
	private final InputStream[] streams;
	private int index;

	/**
	 * Creates a new {@code ConcatInputStream}.
	 * 
	 * @param streams the streams to concatenate.
	 *
	 * @throws NullPointerException if {@code streams} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public ConcatInputStream(InputStream... streams)
	{
		this.streams = Arrays.copyOf(streams, streams.length);
		for (InputStream stream : this.streams) {
			Parameters.checkNotNull(stream);
		}
	}

	@Override
	public int available() throws IOException
	{
		return end() ? 0 : streams[index].available();
	}

	@Override
	public void close()
	{
		for (InputStream stream : streams) {
			Streams.close(stream);
		}
	}

	@Override
	public int read() throws IOException
	{
		int b = end() ? -1 : streams[index].read();
		return b != -1 ? b : next() ? read() : -1;

	}

	@Override
	public int read(byte[] b) throws IOException
	{
		int n = end() ? -1 : streams[index].read(b);
		return n != -1 ? n : next() ? read(b) : -1;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		int n = end() ? -1 : streams[index].read(b, off, len);
		return n != -1 ? n : next() ? read(b, off, len) : -1;
	}

	@Override
	public long skip(long n) throws IOException
	{
		return end() ? 0 : streams[index].read(new byte[(int) n]);
	}

	private boolean end()
	{
		return index >= streams.length;
	}

	private boolean next()
	{
		index++;
		return !end();
	}
}
