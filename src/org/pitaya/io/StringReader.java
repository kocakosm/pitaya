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

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link Reader} whose source are {@link String}s. Instances of this class
 * behave exactly as {@link java.io.StringReader} instances except that they
 * never throw {@link java.io.IOException}s. Not thread-safe.
 *
 * @author Osman KOCAK
 */
public final class StringReader extends Reader
{
	private final StringBuilder in;

	private int index;
	private int mark;

	/**
	 * Creates a new {@code StringReader}.
	 *
	 * @param strings the input {@link String}s.
	 *
	 * @throws NullPointerException if {@code strings} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public StringReader(String... strings)
	{
		this(Arrays.asList(strings));
	}

	/**
	 * Creates a new {@code StringReader}.
	 *
	 * @param strings the input {@link String}s.
	 *
	 * @throws NullPointerException if {@code strings} is {@code null} or if
	 *	it contains a {@code null} reference.
	 */
	public StringReader(List<String> strings)
	{
		this.in = new StringBuilder(strings.size() * 10);
		for (String str : strings) {
			Parameters.checkNotNull(str);
			this.in.append(str);
		}
		this.in.trimToSize();
	}

	@Override
	public boolean ready()
	{
		return true;
	}

	@Override
	public int read()
	{
		return finished() ? -1 : in.charAt(index++);
	}

	@Override
	public int read(char[] buf)
	{
		return read(buf, 0, buf.length);
	}

	@Override
	public int read(char[] buf, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > buf.length) {
			throw new IndexOutOfBoundsException();
		}
		if (finished()) {
			return -1;
		}
		int n = 0;
		for (int i = off; i < off + len && index < in.length(); i++) {
			buf[i] = in.charAt(index++);
			n++;
		}
		return n;
	}

	@Override
	public boolean markSupported()
	{
		return true;
	}

	@Override
	public void mark(int readLimit)
	{
		Parameters.checkCondition(readLimit >= 0);
		mark = index;
	}

	@Override
	public void reset()
	{
		index = mark;
	}

	@Override
	public long skip(long n)
	{
		Parameters.checkCondition(n >= 0);
		if (finished()) {
			return 0;
		}
		long skipped = n;
		if (n > in.length() - index) {
			skipped = in.length() - index;
		}
		index += skipped;
		return skipped;
	}

	@Override
	public void close()
	{
		/* ... */
	}

	private boolean finished()
	{
		return index >= in.length();
	}
}
