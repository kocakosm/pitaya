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

import java.io.Writer;

/**
 * {@code Writer} that simply discards all characters written to it (similar to
 * /dev/null on Unix systems). Instances of this class are immutable.
 *
 * @author Osman KOCAK
 */
public final class NullWriter extends Writer
{
	@Override
	public NullWriter append(char c)
	{
		return this;
	}

	@Override
	public NullWriter append(CharSequence sequence)
	{
		Parameters.checkNotNull(sequence);
		return this;
	}

	@Override
	public NullWriter append(CharSequence sequence, int start, int end)
	{
		return append(sequence.subSequence(start, end));
	}

	@Override
	public void write(int c)
	{
		/* ... */
	}

	@Override
	public void write(char[] buf)
	{
		Parameters.checkNotNull(buf);
	}

	@Override
	public void write(char[] buf, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > buf.length) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void write(String str)
	{
		Parameters.checkNotNull(str);
	}

	@Override
	public void write(String str, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > str.length()) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void flush()
	{
		/* ... */
	}

	@Override
	public void close()
	{
		/* ... */
	}
}
