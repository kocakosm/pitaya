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

import org.pitaya.util.Parameters;

/**
 * Utility class to easily get instances of {@link Digest} and {@link MAC}.
 *
 * @author Osman KOCAK
 */
final class Factory
{
	/**
	 * Creates and returns a {@link Digest} instance corresponding to the 
	 * given algorithm.
	 * 
	 * @param algorithm the digest algorithm.
	 * 
	 * @return the created {@link Digest} instance.
	 * 
	 * @throws NullPointerException if {@code algorithm} is {@code null}.
	 * @throws IllegalArgumentException if the given algorithm is unknown.
	 */
	static Digest getDigest(Algorithm<Digest> algorithm)
	{
		Parameters.checkNotNull(algorithm);
		Digest digest;
		if (algorithm == Algorithm.MD2) {
			digest = Digests.md2();
		} else if (algorithm == Algorithm.MD4) {
			digest = Digests.md4();
		} else if (algorithm == Algorithm.MD5) {
			digest = Digests.md5();
		} else if (algorithm == Algorithm.SHA1) {
			digest = Digests.sha1();
		} else if (algorithm == Algorithm.SHA256) {
			digest = Digests.sha256();
		} else if (algorithm == Algorithm.SHA512) {
			digest = Digests.sha512();
		} else if (algorithm == Algorithm.KECCAK224) {
			digest = Digests.keccak224();
		} else if (algorithm == Algorithm.KECCAK256) {
			digest = Digests.keccak256();
		} else if (algorithm == Algorithm.KECCAK384) {
			digest = Digests.keccak384();
		} else if (algorithm == Algorithm.KECCAK512) {
			digest = Digests.keccak512();
		} else {
			throw new IllegalArgumentException("Unknown algorithm");
		}
		return digest;
	}

	/**
	 * Creates and returns a {@link MAC} instance corresponding to the given
	 * algorithm, initialized with the provided secret key.
	 * 
	 * @param algorithm the MAC algorithm.
	 * @param key the secret key.
	 * 
	 * @return the created {@link MAC} instance.
	 * 
	 * @throws NullPointerException if {@code algorithm} is {@code null}.
	 * @throws IllegalArgumentException if the given algorithm is unknown.
	 */
	static MAC getMAC(Algorithm<MAC> algorithm, byte[] key) 
	{
		Parameters.checkNotNull(algorithm);
		MAC mac;
		if (algorithm == Algorithm.HMAC_MD2) {
			mac = HMAC.md2(key);
		} else if (algorithm == Algorithm.HMAC_MD4) {
			mac = HMAC.md4(key);
		} else if (algorithm == Algorithm.HMAC_MD5) {
			mac = HMAC.md5(key);
		} else if (algorithm == Algorithm.HMAC_SHA1) {
			mac = HMAC.sha1(key);
		} else if (algorithm == Algorithm.HMAC_SHA256) {
			mac = HMAC.sha256(key);
		} else if (algorithm == Algorithm.HMAC_SHA512) {
			mac = HMAC.sha512(key);
		} else if (algorithm == Algorithm.HMAC_KECCAK224) {
			mac = HMAC.keccak224(key);
		} else if (algorithm == Algorithm.HMAC_KECCAK256) {
			mac = HMAC.keccak256(key);
		} else if (algorithm == Algorithm.HMAC_KECCAK384) {
			mac = HMAC.keccak384(key);
		} else if (algorithm == Algorithm.HMAC_KECCAK512) {
			mac = HMAC.keccak512(key);
		} else {
			throw new IllegalArgumentException("Unknown algorithm");
		}
		return mac;
	}

	private Factory()
	{
		/* ... */
	}
}
