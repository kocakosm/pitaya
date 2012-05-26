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
import java.util.concurrent.atomic.AtomicLong;

/**
 * A decorating {@link InputStream} that counts the number of bytes that have
 * been read from the underlying stream. Bytes read multiple times are counted
 * as many times they have been read. Also, skipped bytes are not counted.
 *
 * @author Osman KOCAK
 */
public class CountingInputStream extends InputStream
{
	private final InputStream in;
	private final AtomicLong counter;

	/**
	 * Creates a new {@code CountingInputStream}.
	 *
	 * @param in the underlying stream.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 */
	public CountingInputStream(InputStream in)
	{
		Parameters.checkNotNull(in);
		this.in = in;
		this.counter = new AtomicLong();
	}

	/**
	 * Returns the number of bytes that have been read from the underlying
	 * stream so far.
	 *
	 * @return the number of bytes that have been read so far.
	 */
	public long getCount()
	{
		return counter.get();
	}

	/**
	 * Sets the counter to 0 and returns its value before resetting it.
	 *
	 * @return the number of bytes that have been read so far.
	 */
	public long resetCount()
	{
		return counter.getAndSet(0);
	}

	@Override
	public int available() throws IOException
	{
		return in.available();
	}

	@Override
	public void close() throws IOException
	{
		in.close();
	}

	@Override
	public void mark(int readLimit)
	{
		in.mark(readLimit);
	}

	@Override
	public boolean markSupported()
	{
		return in.markSupported();
	}

	@Override
	public int read() throws IOException
	{
		int b = in.read();
		count(b != -1 ? 1 : 0);
		return b;
	}

	@Override
	public int read(byte[] b) throws IOException
	{
		int n = in.read(b);
		count(n);
		return n;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		int n = in.read(b, off, len);
		count(n);
		return n;
	}

	@Override
	public void reset() throws IOException
	{
		in.reset();
	}

	@Override
	public long skip(long n) throws IOException
	{
		return in.skip(n);
	}

	private void count(int n)
	{
		if (n != -1) {
			counter.addAndGet(n);
		}
	}
}
