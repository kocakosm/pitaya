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

import java.io.OutputStream;

/**
 * {@code OutputStream} that simply discards all written bytes (similar to
 * /dev/null on Unix systems).
 *
 * @author Osman KOCAK
 */
public final class NullOutputStream extends OutputStream
{
	@Override
	public void close()
	{
		/* ... */
	}

	@Override
	public void flush()
	{
		/* ... */
	}

	@Override
	public void write(byte[] b)
	{
		Parameters.checkNotNull(b);
	}

	@Override
	public void write(byte[] b, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > b.length) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void write(int i)
	{
		/* ... */
	}
}
