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

package org.pitaya.util;

import org.pitaya.charset.ASCII;
import org.pitaya.charset.UTF8;
import org.pitaya.digest.Digest;
import org.pitaya.digest.Digests;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Password utility functions.
 *
 * @author Osman KOCAK
 */
public final class Passwords
{
	private static final Random PRNG = new SecureRandom();

	/**
	 * Generates a pseudo-random password (10 alphanumeric characters).
	 *
	 * @return a pseudo-random password.
	 */
	public static String generate()
	{
		String s = new BigInteger(52, PRNG).toString(36) + "0000000000";
		StringBuilder password = new StringBuilder(10);
		for (int i = 0; i < 10; i++) {
			if (PRNG.nextBoolean()) {
				password.append(ASCII.toUpperCase(s.charAt(i)));
			} else {
				password.append(s.charAt(i));
			}
		}
		return password.toString();
	}

	/**
	 * Hashes the given password (the salt is appended to the hash).
	 *
	 * @param password the password to hash.
	 *
	 * @return the hashed password.
	 *
	 * @throws NullPointerException if {@code password} is {@code null}.
	 */
	public static byte[] hash(String password)
	{
		return hash(password, salt());
	}

	/**
	 * Verifies that the given password matches the hashed one.
	 *
	 * @param password the password to verify.
	 * @param hash the hashed password.
	 *
	 * @return whether the given password matches the hashed one.
	 *
	 * @throws NullPointerException if {@code password} or {@code hash} is
	 *	{@code null}.
	 */
	public static boolean verify(String password, byte[] hash)
	{
		byte[] salt = new byte[16];
		System.arraycopy(hash, 32, salt, 0, salt.length);
		return Arrays.equals(hash, hash(password, salt));
	}

	private static byte[] hash(String password, byte[] salt)
	{
		byte[] passwd = UTF8.encode(password);
		Digest digest = Digests.sha256();
		digest.update(salt);
		digest.update(passwd);
		byte[] hash = digest.digest();
		for (int i = 0; i < 10; i++) {
			digest.update(salt);
			digest.update(passwd);
			digest.update(hash);
			hash = digest.digest();
		}
		byte[] buf = new byte[hash.length + salt.length];
		System.arraycopy(hash, 0, buf, 0, hash.length);
		System.arraycopy(salt, 0, buf, hash.length, salt.length);
		return buf;
	}

	private static byte[] salt()
	{
		byte[] salt = new byte[16];
		PRNG.nextBytes(salt);
		return salt;
	}

	private Passwords()
	{
		/* ... */
	}
}
