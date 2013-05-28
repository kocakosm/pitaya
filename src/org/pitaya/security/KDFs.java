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
 * Somme commonly used key derivation function algorithms. Instances returned
 * by this class are all thread-safe. Careful: some of these algorithms may no
 * more be suitable for security related applications.
 *
 * @author Osman KOCAK
 */
public final class KDFs
{
	/**
	 * Creates and returns a new {@link KDF} instance implementing the 
	 * PBKDF1 algorithm (RFC 2898).
	 * 
	 * @param digest the digest algorithm to use.
	 * @param iterationCount the desired number of iterations.
	 * @param dkLen the desired length for derived keys.
	 * 
	 * @return the created {@link KDF} instance.
	 * 
	 * @throws NullPointerException if {@code digest} is {@code null}.
	 * @throws IllegalArgumentException if {@code iterationCount} or 
	 *	{@code dkLen} is negative, or if {@code dkLen} is greater than 
	 *	the digest algorithm's output length, or if the digest algorithm 
	 *	is unknown.
	 */
	public static KDF pbkdf1(Algorithm<Digest> digest, int iterationCount,
		int dkLen)
	{
		return new PBKDF1(digest, iterationCount, dkLen);
	}

	/**
	 * Creates and returns a new {@link KDF} instance implementing the 
	 * PBKDF2 algorithm (RFC 2898).
	 * 
	 * @param mac the MAC algorithm to use.
	 * @param iterationCount the desired number of iterations.
	 * @param dkLen the desired length for derived keys.
	 * 
	 * @return the created {@link KDF} instance.
	 * 
	 * @throws NullPointerException if {@code mac} is {@code null}.
	 * @throws IllegalArgumentException if {@code iterationCount} or 
	 *	{@code dkLen} is negative, or if the MAC algorithm is unknown.
	 */
	public static KDF pbkdf2(Algorithm<MAC> mac, int iterationCount, 
		int dkLen)
	{
		return new PBKDF2(mac, iterationCount, dkLen);
	}

	/**
	 * Creates and returns a new {@link KDF} instance implementing the 
	 * HKDF algorithm (RFC 5869).
	 * 
	 * @param mac the MAC algorithm to use.
	 * @param info optional context and application specific information, 
	 *	may be {@code null} or empty.
	 * @param dkLen the desired length for derived keys.
	 * 
	 * @return the created {@link KDF} instance.
	 * 
	 * @throws NullPointerException if {@code algorithm} is {@code null}.
	 * @throws IllegalArgumentException if {@code dkLen} is negative, or if
	 *	the MAC algorithm is unknown, or if {@code dkLen} is greater 
	 *	than 255 * MAC algorithm's output length.
	 */
	public static KDF hkdf(Algorithm<MAC> mac, byte[] info, int dkLen)
	{
		return new HKDF(mac, info, dkLen);
	}

	private KDFs()
	{
		/* ... */
	}
}
