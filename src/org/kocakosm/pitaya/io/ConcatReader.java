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

import org.kocakosm.pitaya.util.Parameters;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A {@code Reader} which reads sequentially from multiple sources. Does not
 * support {@code mark()} and {@code reset()} operations. Not thread safe.
 *
 * @author Osman KOCAK
 */
final class ConcatReader extends Reader
{
	private int index;
	private final List<Reader> readers;

	/**
	 * Creates a new {@code ConcatReader}.
	 *
	 * @param readers the readers to concatenate.
	 *
	 * @throws NullPointerException if {@code readers} is {@code null} or
	 *	if it contains a {@code null} reference.
	 * @throws IllegalArgumentException if {@code readers} is empty.
	 */
	ConcatReader(Reader... readers)
	{
		this(Arrays.asList(readers));
	}

	/**
	 * Creates a new {@code ConcatReader}.
	 *
	 * @param readers the readers to concatenate.
	 *
	 * @throws NullPointerException if {@code readers} is {@code null} or
	 *	if it contains a {@code null} reference.
	 * @throws IllegalArgumentException if {@code readers} is empty.
	 */
	ConcatReader(Iterable<? extends Reader> readers)
	{
		this.readers = new ArrayList<Reader>();
		for (Reader reader : readers) {
			this.readers.add(Parameters.checkNotNull(reader));
		}
		Parameters.checkCondition(!this.readers.isEmpty());
	}

	@Override
	public boolean ready() throws IOException
	{
		return current().ready();
	}

	@Override
	public int read() throws IOException
	{
		int b = finished() ? -1 : current().read();
		return b != -1 ? b : next() ? read() : -1;
	}

	@Override
	public int read(char[] buf) throws IOException
	{
		int n = finished() ? -1 : current().read(buf);
		return n != -1 ? n : next() ? read(buf) : -1;
	}

	@Override
	public int read(char[] buf, int off, int len) throws IOException
	{
		int n = finished() ? -1 : current().read(buf, off, len);
		return n != -1 ? n : next() ? read(buf, off, len) : -1;
	}

	@Override
	public boolean markSupported()
	{
		return false;
	}

	@Override
	public void mark(int readLimit) throws IOException
	{
		Parameters.checkCondition(readLimit >= 0);
		throw new IOException("Mark not supported");
	}

	@Override
	public void reset() throws IOException
	{
		throw new IOException("Mark not supported");
	}

	@Override
	public long skip(long n) throws IOException
	{
		Parameters.checkCondition(n >= 0);
		return finished() ? 0 : current().read(new char[(int) n]);
	}

	@Override
	public void close()
	{
		for (Reader reader : readers) {
			IO.close(reader);
		}
	}

	private Reader current()
	{
		return readers.get(index);
	}

	private boolean finished()
	{
		return index >= readers.size();
	}

	private boolean next()
	{
		index++;
		return !finished();
	}
}
