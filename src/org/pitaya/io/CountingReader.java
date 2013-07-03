/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2013 Osman KOCAK <kocakosm@gmail.com>                   *
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
import java.io.Reader;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A decorating {@link Reader} that counts the number of characters that have
 * been read from its underlying {@link Reader}. Characters read multiple times
 * are counted as many times they have been read. Also, skipped characters are 
 * not counted.
 *
 * @author Osman KOCAK
 */
public final class CountingReader extends Reader
{
	private final Reader reader;
	private final AtomicLong counter;

	/**
	 * Creates a new {@code CountingReader}.
	 *
	 * @param reader the underlying reader.
	 *
	 * @throws NullPointerException if {@code reader} is {@code null}.
	 */
	public CountingReader(Reader reader)
	{
		Parameters.checkNotNull(reader);
		this.reader = reader;
		this.counter = new AtomicLong();
	}
	
	/**
	 * Returns the number of characters that have been read so far.
	 *
	 * @return the number of characters that have been read so far.
	 */
	public long getCount()
	{
		return counter.get();
	}

	/**
	 * Sets the counter to 0 and returns its value before resetting it.
	 *
	 * @return the number of characters that have been read so far.
	 */
	public long resetCount()
	{
		return counter.getAndSet(0);
	}
	
	@Override
	public boolean ready() throws IOException
	{
		return reader.ready();
	}

	@Override
	public int read() throws IOException
	{
		int b = reader.read();
		count(b != -1 ? 1 : -1);
		return b;
	}

	@Override
	public int read(char[] buf) throws IOException
	{
		int n = reader.read(buf);
		count(n);
		return n;
	}

	@Override
	public int read(char[] buf, int off, int len) throws IOException
	{
		int n = reader.read(buf, off, len);
		count(n);
		return n;
	}

	@Override
	public boolean markSupported()
	{
		return reader.markSupported();
	}

	@Override
	public void mark(int readLimit) throws IOException
	{
		reader.mark(readLimit);
	}

	@Override
	public void reset() throws IOException
	{
		reader.reset();
	}

	@Override
	public long skip(long n) throws IOException
	{
		return reader.skip(n);
	}

	@Override
	public void close() throws IOException
	{
		reader.close();
	}
	
	private void count(int n)
	{
		if (n != -1) {
			counter.addAndGet(n);
		}
	}
}
