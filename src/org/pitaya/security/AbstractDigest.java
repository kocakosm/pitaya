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

package org.pitaya.security;

import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract skeleton implementation of the {@link Digest} interface.
 *
 * @author Osman KOCAK
 */
abstract class AbstractDigest implements Digest
{
	private final String name;
	private final int length;

	/**
	 * Creates a new {@code AbstractDigest}.
	 *
	 * @param name the digest algorithm's name.
	 * @param length the digest's length in bytes.
	 */
	AbstractDigest(String name, int length)
	{
		this.name = name;
		this.length = length;
	}

	@Override
	public int length()
	{
		return length;
	}

	@Override
	public Digest update(byte... input)
	{
		return update(input, 0, input.length);
	}

	@Override
	public Digest update(InputStream input) throws IOException
	{
		byte[] buf = new byte[2048];
		int len = input.read(buf);
		while (len >= 0) {
			update(buf, 0, len);
			len = input.read(buf);
		}
		return this;
	}

	@Override
	public byte[] digest(byte... input)
	{
		return update(input, 0, input.length).digest();
	}

	@Override
	public byte[] digest(byte[] input, int off, int len)
	{
		return update(input, off, len).digest();
	}

	@Override
	public byte[] digest(InputStream input) throws IOException
	{
		return update(input).digest();
	}

	@Override
	public String toString()
	{
		return name;
	}
}
