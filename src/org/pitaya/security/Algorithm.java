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

package org.pitaya.security;

/**
 * Algorithm identifier.
 *
 * @param <T> the category of the algorithm.
 * 
 * @author Osman KOCAK
 */
public final class Algorithm<T>
{
	/** The MD2 digest algorithm. */
	public static final Algorithm<Digest> MD2 = new Algorithm<Digest>();

	/** The MD4 digest algorithm. */
	public static final Algorithm<Digest> MD4 = new Algorithm<Digest>();

	/** The MD5 digest algorithm. */
	public static final Algorithm<Digest> MD5 = new Algorithm<Digest>();

	/** The SHA1 digest algorithm. */
	public static final Algorithm<Digest> SHA1 = new Algorithm<Digest>();

	/** The SHA-256 digest algorithm. */
	public static final Algorithm<Digest> SHA256 = new Algorithm<Digest>();

	/** The SHA-512 digest algorithm. */
	public static final Algorithm<Digest> SHA512 = new Algorithm<Digest>();

	/** The HMAC-MD2 MAC algorithm. */
	public static final Algorithm<MAC> HMAC_MD2 = new Algorithm<MAC>();

	/** The HMAC-MD4 MAC algorithm. */
	public static final Algorithm<MAC> HMAC_MD4 = new Algorithm<MAC>();

	/** The HMAC-MD5 MAC algorithm. */
	public static final Algorithm<MAC> HMAC_MD5 = new Algorithm<MAC>();

	/** The HMAC-SHA1 MAC algorithm. */
	public static final Algorithm<MAC> HMAC_SHA1 = new Algorithm<MAC>();

	/** The HMAC-SHA-256 MAC algorithm. */
	public static final Algorithm<MAC> HMAC_SHA256 = new Algorithm<MAC>();

	/** The HMAC-SHA-512 MAC algorithm. */
	public static final Algorithm<MAC> HMAC_SHA512 = new Algorithm<MAC>();

	private Algorithm()
	{
		/* ... */
	}
}
