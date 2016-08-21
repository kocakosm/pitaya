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

import java.io.Reader;
import java.util.Arrays;
import java.util.Random;

/**
 * {@code Reader} that (pseudo) randomly returns characters from a specified
 * alphabet. This stream has no end of file. Closing a {@code RandomReader} has
 * no effect. Never throws {@code IOException}s. Thread safe.
 *
 * @author Osman KOCAK
 */
public final class RandomReader extends Reader
{
	private static final Random PRNG = new Random();

	private final Random prng;
	private final char[] alphabet;

	/**
	 * Creates a new {@code RandomReader}.
	 *
	 * @param alphabet the source alphabet to use.
	 *
	 * @throws NullPointerException if {@code alphabet} is {@code null}.
	 * @throws IllegalArgumentException if {@code alphabet} is empty.
	 */
	public RandomReader(char... alphabet)
	{
		this(PRNG, alphabet);
	}

	/**
	 * Creates a new {@code RandomReader}.
	 *
	 * @param prng the source of randomness to use.
	 * @param alphabet the source alphabet to use.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if {@code alphabet} is empty.
	 */
	public RandomReader(Random prng, char... alphabet)
	{
		Parameters.checkNotNull(prng);
		Parameters.checkCondition(alphabet.length > 0);
		this.prng = prng;
		this.alphabet = Arrays.copyOf(alphabet, alphabet.length);
	}

	@Override
	public int read()
	{
		return alphabet[prng.nextInt(alphabet.length)];
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
