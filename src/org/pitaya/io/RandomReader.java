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
import java.util.Random;

/**
 * {@code Reader} that (pseudo) randomly returns characters from a specified
 * source. This stream has no end of file. Closing a {@code RandomReader} has no
 * effect. Never throws {@code IOException}s.
 *
 * @author Osman KOCAK
 */
final class RandomReader extends Reader
{
	private final Random prng;
	private final char[] chars;

	RandomReader(Random prng, char... chars)
	{
		Parameters.checkNotNull(prng);
		this.prng = prng;
		this.chars = Arrays.copyOf(chars, chars.length);
	}

	@Override
	public int read()
	{
		return chars[prng.nextInt(chars.length)];
	}

	@Override
	public int read(char[] cbuf)
	{
		for (int i = 0; i < cbuf.length; i++) {
			cbuf[i] = (char) read();
		}
		return cbuf.length;
	}

	@Override
	public int read(char[] cbuf, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > cbuf.length) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < len; i++) {
			cbuf[off + i] = (char) read();
		}
		return len;
	}

	@Override
	public void close()
	{
		/* ... */
	}
}
