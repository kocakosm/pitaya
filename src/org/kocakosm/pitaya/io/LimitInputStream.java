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
import java.io.InputStream;

/**
 * A decorating {@code InputStream} that will only supply bytes up to a certain
 * length. Thread safe.
 *
 * @author Osman KOCAK
 */
final class LimitInputStream extends InputStream
{
	private final InputStream in;
	private final long limit;
	private final Object lock;

	private long position;
	private long mark;
	private long markLimit;

	/**
	 * Creates a new {@code LimitInputStream}.
	 *
	 * @param in the underlying stream.
	 * @param limit the maximum number of bytes to provide from {@code in}.
	 *
	 * @throws NullPointerException if {@code in} is {@code null}.
	 * @throws IllegalArgumentException if {@code limit} is negative.
	 */
	LimitInputStream(InputStream in, long limit)
	{
		Parameters.checkNotNull(in);
		Parameters.checkCondition(limit >= 0);
		this.in = in;
		this.limit = limit;
		this.lock = new Object();
		this.position = 0L;
		this.mark = -1L;
	}

	@Override
	public int available() throws IOException
	{
		synchronized (lock) {
			if (position >= limit) {
				return 0;
			}
			return in.available();
		}
	}

	@Override
	public void close() throws IOException
	{
		synchronized (lock) {
			in.close();
		}

	}

	@Override
	public void mark(int readLimit)
	{
		synchronized (lock) {
			in.mark(readLimit);
			mark = position;
			markLimit = position + readLimit;
		}
	}

	@Override
	public boolean markSupported()
	{
		synchronized (lock) {
			return in.markSupported();
		}
	}

	@Override
	public int read() throws IOException
	{
		synchronized (lock) {
			if (position >= limit) {
				return -1;
			}
			int b = in.read();
			shiftPosition(b != -1 ? 1 : -1);
			return b;
		}
	}

	@Override
	public int read(byte[] b) throws IOException
	{
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		synchronized (lock) {
			if (position >= limit) {
				return -1;
			}
			int max = (int) Math.min(len, limit - position);
			int n = in.read(b, off, max);
			shiftPosition(n);
			return n;
		}
	}

	@Override
	public void reset() throws IOException
	{
		synchronized (lock) {
			if (mark == -1 || (mark > -1 && position > markLimit)) {
				throw new IOException("Missing mark");
			}
			in.reset();
			position = mark;
		}
	}

	@Override
	public long skip(long n) throws IOException
	{
		synchronized (lock) {
			long skipped = in.skip(Math.min(n, limit - position));
			shiftPosition(skipped);
			return skipped;
		}
	}

	private void shiftPosition(long n)
	{
		if (n != -1) {
			position += n;
		}
	}
}
