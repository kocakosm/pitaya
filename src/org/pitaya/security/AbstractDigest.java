/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2013 Osman KOCAK <kocakosm@gmail.com>                   *
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
		this.length = length * 8;
	}

	@Override
	public int length()
	{
		return length;
	}

	@Override
	public void update(byte... input)
	{
		update(input, 0, input.length);
	}

	@Override
	public byte[] digest(byte... input)
	{
		update(input, 0, input.length);
		return digest();
	}

	@Override
	public byte[] digest(byte[] input, int off, int len)
	{
		update(input, off, len);
		return digest();
	}

	@Override
	public String toString()
	{
		return name;
	}
}
