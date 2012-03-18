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

import org.pitaya.charset.UTF8;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
		BigInteger n = new BigInteger(52, PRNG);
		String ns = "0000000000" + n.toString(36).toUpperCase();
		return ns.substring(ns.length() - 10);
	}

	/**
	 * Generates a (16-bytes long) random password salt.
	 *
	 * @return a random password salt.
	 */
	public static byte[] salt()
	{
		byte[] salt = new byte[16];
		PRNG.nextBytes(salt);
		return salt;
	}

	/**
	 * Hashes the given password with the given salt.
	 *
	 * @param password the password to hash.
	 * @param salt the password hashing salt.
	 *
	 * @return the hashed password.
	 *
	 * @throws NullPointerException if one of the given parameters is
	 *	{@code null}.
	 */
	public static byte[] hash(String password, byte[] salt)
	{
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException ex) {
			/* Cannot happen... */
			throw new IllegalStateException(ex);
		}
		md.update(salt);
		md.update(UTF8.encode(password));
		byte[] hash = md.digest();
		for (int i = 0; i < 10; i++) {
			md.update(salt);
			md.update(hash);
			hash = md.digest();
		}
		return hash;
	}

	/**
	 * Verifies that the given password and salt matches the hashed one.
	 *
	 * @param hash the hashed password.
	 * @param password the password to verify.
	 * @param salt the salt that has been used when hashing the password.
	 *
	 * @return the hashed password.
	 *
	 * @throws NullPointerException if one of the given parameters is
	 *	{@code null}.
	 */
	public static boolean verify(byte[] hash, String password, byte[] salt)
	{
		return Arrays.equals(hash, hash(password, salt));
	}

	private Passwords()
	{
		/* ... */
	}
}
