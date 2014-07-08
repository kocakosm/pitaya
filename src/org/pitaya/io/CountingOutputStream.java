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
import java.util.concurrent.atomic.AtomicLong;

/**
 * A decorating {@link OutputStream} that counts the number of bytes that have
 * been written to the underlying stream.
 *
 * @author Osman KOCAK
 */
public final class CountingOutputStream extends OutputStream
{
	private final OutputStream out;
	private final AtomicLong counter;

	/**
	 * Creates a new {@code CountingOutputStream}.
	 *
	 * @param out the underlying stream.
	 *
	 * @throws NullPointerException if {@code out} is {@code null}.
	 */
	public CountingOutputStream(OutputStream out)
	{
		Parameters.checkNotNull(out);
		this.out = out;
		this.counter = new AtomicLong();
	}

	/**
	 * Returns the number of bytes that have been written to the underlying
	 * stream so far.
	 *
	 * @return the number of bytes that have been written so far.
	 */
	public long getCount()
	{
		return counter.get();
	}

	/**
	 * Sets the counter to 0 and returns its value before resetting it.
	 *
	 * @return the number of bytes that have been written so far.
	 */
	public long resetCount()
	{
		return counter.getAndSet(0);
	}

	@Override
	public void close() throws IOException
	{
		out.close();
	}

	@Override
	public void flush() throws IOException
	{
		out.flush();
	}

	@Override
	public void write(int i) throws IOException
	{
		out.write(i);
		counter.incrementAndGet();
	}

	@Override
	public void write(byte[] b) throws IOException
	{
		out.write(b);
		counter.addAndGet(b.length);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		out.write(b, off, len);
		counter.addAndGet(Math.min(b.length - off, len));
	}
}
