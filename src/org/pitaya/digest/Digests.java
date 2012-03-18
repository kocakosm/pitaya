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

package org.pitaya.digest;

/**
 * Somme commonly used digest algorithms. Careful: some of these algorithms are
 * not suitable for security related applications.
 *
 * @author Osman KOCAK
 */
public final class Digests
{
	public static Digest md2()
	{
		throw new UnsupportedOperationException();
	}

	public static Digest md4()
	{
		return new MD4();
	}

	public static Digest md5()
	{
		throw new UnsupportedOperationException();
	}

	public static Digest sha1()
	{
		throw new UnsupportedOperationException();
	}

	public static Digest sha256()
	{
		throw new UnsupportedOperationException();
	}

	public static Digest sha512()
	{
		throw new UnsupportedOperationException();
	}

	private Digests()
	{
		/* ... */
	}
}
