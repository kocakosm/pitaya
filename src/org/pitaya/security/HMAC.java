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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Somme commonly used HMAC (Hash-based Message Authentication Code) engines. A
 * HMAC is a specific construction for calculating a {@link MAC} involving a
 * cryptographic hash function in combination with a secret key. As with any
 * MAC, it may be used to simultaneously verify both the data integrity and the
 * authenticity of a message. HMAC is specified in RFC 2104.
 * Careful: the cryptographic strength of a HMAC depends upon the cryptographic
 * strength of the underlying hash function, the size of its hash output length
 * in bits, and on the size and quality of the cryptographic key, so, some of
 * these HMAC engines may not be suitable for security related applications.
 * Also, note that instances returned by this class are not thread-safe.
 *
 * @author Osman KOCAK
 */
public final class HMAC
{
	/**
	 * Returns a new MD2 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new MD2 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC md2(byte... key)
	{
		return new Engine(key, Digests.md2(), 16);
	}

	/**
	 * Returns a new MD4 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new MD4 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC md4(byte... key)
	{
		return new Engine(key, Digests.md4(), 64);
	}

	/**
	 * Returns a new MD5 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new MD5 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC md5(byte... key)
	{
		return new Engine(key, Digests.md5(), 64);
	}

	/**
	 * Returns a new SHA1 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new SHA1 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC sha1(byte... key)
	{
		return new Engine(key, Digests.sha1(), 64);
	}

	/**
	 * Returns a new SHA-256 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new SHA-256 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC sha256(byte... key)
	{
		return new Engine(key, Digests.sha256(), 64);
	}

	/**
	 * Returns a new SHA-512 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new SHA-512 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC sha512(byte... key)
	{
		return new Engine(key, Digests.sha512(), 128);
	}

	/**
	 * Returns a new Keccak-224 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new Keccak-224 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC keccak224(byte... key)
	{
		return new Engine(key, Digests.keccak224(), 144);
	}

	/**
	 * Returns a new Keccak-256 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new Keccak-256 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC keccak256(byte... key)
	{
		return new Engine(key, Digests.keccak256(), 136);
	}

	/**
	 * Returns a new Keccak-384 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new Keccak-384 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC keccak384(byte... key)
	{
		return new Engine(key, Digests.keccak384(), 104);
	}

	/**
	 * Returns a new Keccak-512 HMAC engine.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new Keccak-512 HMAC engine.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static MAC keccak512(byte... key)
	{
		return new Engine(key, Digests.keccak512(), 72);
	}

	private static final class Engine implements MAC
	{
		private final byte[] key;
		private final Digest digest;

		Engine(byte[] key, Digest digest, int blockSize)
		{
			if (key.length > blockSize) {
				this.key = Arrays.copyOf(digest.digest(key), blockSize);
			} else {
				this.key = Arrays.copyOf(key, blockSize);
			}
			this.digest = digest;
			reset();
		}

		@Override
		public int length()
		{
			return digest.length();
		}

		@Override
		public MAC reset()
		{
			digest.reset();
			for (byte b : key) {
				digest.update((byte) ((b & 0xFF) ^ 0x36));
			}
			return this;
		}

		@Override
		public MAC update(byte input)
		{
			digest.update(input);
			return this;
		}

		@Override
		public MAC update(byte... input)
		{
			digest.update(input);
			return this;
		}

		@Override
		public MAC update(byte[] input, int off, int len)
		{
			digest.update(input, off, len);
			return this;
		}

		@Override
		public MAC update(InputStream input) throws IOException
		{
			digest.update(input);
			return this;
		}

		@Override
		public byte[] mac()
		{
			byte[] hash = digest.digest();
			for (byte b : key) {
				digest.update((byte) ((b & 0xFF) ^ 0x5c));
			}
			byte[] hmac = digest.digest(hash);
			reset();
			return hmac;
		}

		@Override
		public byte[] mac(byte... input)
		{
			update(input);
			return mac();
		}

		@Override
		public byte[] mac(byte[] input, int off, int len)
		{
			update(input, off, len);
			return mac();
		}

		@Override
		public byte[] mac(InputStream input) throws IOException
		{
			update(input);
			return mac();
		}

		@Override
		public String toString()
		{
			return "HMAC-" + digest;
		}
	}

	private HMAC()
	{
		/* ... */
	}
}
