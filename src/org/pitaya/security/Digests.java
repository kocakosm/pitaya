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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Somme commonly used digest algorithms. Careful: some of these algorithms may
 * not be suitable for security related applications.
 *
 * @author Osman KOCAK
 */
public final class Digests
{
	/**
	 * Returns a new MD2 {@link Digest} instance.
	 *
	 * @return a new MD2 {@link Digest} instance.
	 */
	public static Digest md2()
	{
		return new MD2();
	}

	/**
	 * Returns a new MD4 {@link Digest} instance.
	 *
	 * @return a new MD4 {@link Digest} instance.
	 */
	public static Digest md4()
	{
		return new MD4();
	}

	/**
	 * Returns a new MD5 {@link Digest} instance.
	 *
	 * @return a new MD5 {@link Digest} instance.
	 */
	public static Digest md5()
	{
		return new DigestImpl("MD5");
	}

	/**
	 * Returns a new SHA1 {@link Digest} instance.
	 *
	 * @return a new SHA1 {@link Digest} instance.
	 */
	public static Digest sha1()
	{
		return new DigestImpl("SHA1");
	}

	/**
	 * Returns a new SHA-256 {@link Digest} instance.
	 *
	 * @return a new SHA-256 {@link Digest} instance.
	 */
	public static Digest sha256()
	{
		return new DigestImpl("SHA-256");
	}

	/**
	 * Returns a new SHA-512 {@link Digest} instance.
	 *
	 * @return a new SHA-512 {@link Digest} instance.
	 */
	public static Digest sha512()
	{
		return new DigestImpl("SHA-512");
	}

	private static final class DigestImpl implements Digest
	{
		private final String algorithm;
		private final MessageDigest md;

		DigestImpl(String algorithm)
		{
			this.algorithm = algorithm;
			try {
				this.md = MessageDigest.getInstance(algorithm);
			} catch (NoSuchAlgorithmException ex) {
				/* Cannot happen... */
				throw new IllegalStateException();
			}
		}

		@Override
		public int length()
		{
			return md.getDigestLength();
		}

		@Override
		public void reset()
		{
			md.reset();
		}

		@Override
		public void update(byte input)
		{
			md.update(input);
		}

		@Override
		public void update(byte... input)
		{
			md.update(input);
		}

		@Override
		public void update(byte[] input, int off, int len)
		{
			md.update(input, off, len);
		}

		@Override
		public byte[] digest()
		{
			return md.digest();
		}

		@Override
		public byte[] digest(byte... input)
		{
			return md.digest(input);
		}

		@Override
		public byte[] digest(byte[] input, int off, int len)
		{
			md.update(input, off, len);
			return md.digest();
		}

		@Override
		public String toString()
		{
			return algorithm;
		}
	}

	private Digests()
	{
		/* ... */
	}
}
