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

import org.pitaya.util.Parameters;

import java.util.Arrays;

/**
 * PBKDF1 Key Derivation Function (RFC 2898). Thread-safe.
 *
 * @author Osman KOCAK
 */
final class PBKDF1 implements KDF
{
	private final int dkLen;
	private final int iterationCount;
	private final Algorithm<Digest> algorithm;
	
	/**
	 * Creates a new {@code PBKDF1} instance.
	 * 
	 * @param algorithm the digest algorithm to use.
	 * @param iterationCount the desired number of iterations.
	 * @param dkLen the desired length for derived keys.
	 * 
	 * @throws NullPointerException if {@code algorithm} is {@code null}.
	 * @throws IllegalArgumentException if {@code iterationCount} or 
	 *	{@code dkLen} is negative, or if {@code dkLen} is greater than 
	 *	the digest algorithm's output length, or if the digest algorithm 
	 *	is unknown.
	 */
	PBKDF1(Algorithm<Digest> algorithm, int iterationCount, int dkLen)
	{
		Parameters.checkCondition(dkLen > 0);
		Parameters.checkCondition(iterationCount > 0);
		Digest digest = Factory.getDigest(algorithm);
		Parameters.checkCondition(dkLen <= digest.length());
		this.algorithm = algorithm;
		this.iterationCount = iterationCount;
		this.dkLen = dkLen;
	}

	@Override
	public byte[] deriveKey(byte[] secret, byte[] salt)
	{
		Digest digest = Factory.getDigest(algorithm);
		digest.update(secret);
		byte[] hash = digest.digest(salt);
		for (int i = 1; i < iterationCount; i++) {
			hash = digest.digest(hash);
		}
		return Arrays.copyOf(hash, dkLen);
	}
}
