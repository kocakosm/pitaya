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

package org.kocakosm.pitaya.security;

import org.kocakosm.pitaya.charset.UTF8;
import org.kocakosm.pitaya.util.ByteBuffer;
import org.kocakosm.pitaya.util.Strings;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Passwords related utility functions.
 *
 * @author Osman KOCAK
 */
public final class Passwords
{
	private static final int R = 8;
	private static final int P = 1;
	private static final int N = 1 << 14;
	private static final int R_MIN = 8;
	private static final int P_MIN = 1;
	private static final int N_MIN = 1 << 14;
	private static final int SALT_LENGTH = 16;
	private static final int HASH_LENGTH = 32;
	private static final Random PRNG = new SecureRandom();
	private static final char[] ALPHABET = new char[] {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
		'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};

	/**
	 * Generates a pseudo-random password (10 alphanumeric characters).
	 *
	 * @return a pseudo-random password.
	 */
	public static String generate()
	{
		return Strings.random(10, PRNG, ALPHABET);
	}

	/**
	 * Hashes the given password (using {@linkplain KDFs#scrypt SCrypt}).
	 * Hashing parameters are appended to the returned result.
	 *
	 * @param password the password to hash.
	 *
	 * @return the hashed password.
	 *
	 * @throws NullPointerException if {@code password} is {@code null}.
	 */
	public static byte[] hash(String password)
	{
		return hash(password, salt(), R, N, P);
	}

	/**
	 * Verifies that the given password matches the hashed one.
	 *
	 * @param password the password to verify.
	 * @param hash the hashed password.
	 *
	 * @return whether the given password matches the hashed one.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static boolean verify(String password, byte[] hash)
	{
		byte[] h = Arrays.copyOf(hash, HASH_LENGTH + SALT_LENGTH + 3);
		int n = 1 << (h[HASH_LENGTH + SALT_LENGTH] & 0xFF);
		int r = h[HASH_LENGTH + SALT_LENGTH + 1] & 0xFF;
		int p = h[HASH_LENGTH + SALT_LENGTH + 2] & 0xFF;
		if (n > N || n < N_MIN || r > R || r < R_MIN || p > P || p < P_MIN) {
			n = N;
			r = R;
			p = P;
		}
		byte[] salt = new byte[SALT_LENGTH];
		System.arraycopy(h, HASH_LENGTH, salt, 0, SALT_LENGTH);
		byte[] expected = hash(password, salt, r, n, p);
		int result = 0;
		for (int i = 0; i < h.length; i++) {
			result |= h[i] ^ expected[i];
		}
		return result == 0;
	}

	private static byte[] hash(String password, byte[] salt, int r, int n, int p)
	{
		KDF scrypt = KDFs.scrypt(r, n, p, HASH_LENGTH);
		ByteBuffer buf = new ByteBuffer(HASH_LENGTH + SALT_LENGTH + 3);
		buf.append(scrypt.deriveKey(UTF8.encode(password), salt));
		buf.append(salt);
		buf.append((byte) Math.round(Math.log(n) / Math.log(2)));
		buf.append((byte) r, (byte) p);
		return buf.toByteArray();
	}

	private static byte[] salt()
	{
		byte[] salt = new byte[SALT_LENGTH];
		PRNG.nextBytes(salt);
		return salt;
	}

	private Passwords()
	{
		/* ... */
	}
}
