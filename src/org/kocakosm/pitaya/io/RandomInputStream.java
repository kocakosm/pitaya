/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2016 Osman KOCAK <kocakosm@gmail.com>                   *
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

import java.io.InputStream;
import java.util.Random;

/**
 * {@code InputStream} that reads data from a (pseudo) random number generator.
 * This stream has no end of file. Closing a {@code RandomInputStream} has no
 * effect. Never throws {@code IOException}s. Thread safe.
 *
 * @author Osman KOCAK
 */
public final class RandomInputStream extends InputStream
{
	private static final Random PRNG = new Random();

	private final Random prng;

	/** Creates a new {@code RandomInputStream}. */
	public RandomInputStream()
	{
		this(PRNG);
	}

	/**
	 * Creates a new {@code RandomInputStream}.
	 *
	 * @param prng the source of randomness to use.
	 *
	 * @throws NullPointerException if {@code prng} is {@code null}.
	 */
	public RandomInputStream(Random prng)
	{
		Parameters.checkNotNull(prng);
		this.prng = prng;
	}

	@Override
	public int read()
	{
		return prng.nextInt(256);
	}

	@Override
	public int read(byte[] b)
	{
		prng.nextBytes(b);
		return b.length;
	}

	@Override
	public int read(byte[] b, int off, int len)
	{
		if (off < 0 || len < 0 || off + len > b.length) {
			throw new IndexOutOfBoundsException();
		}
		byte[] tmp = new byte[len];
		prng.nextBytes(tmp);
		System.arraycopy(tmp, 0, b, off, len);
		return len;
	}
}
