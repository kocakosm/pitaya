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

package org.pitaya.mac;

import org.pitaya.digest.Digest;
import org.pitaya.digest.Digests;

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
 *
 * @author Osman KOCAK
 */
public final class HMACs
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
		return new HMAC(key, Digests.md2(), 16);
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
		return new HMAC(key, Digests.md4(), 64);
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
		return new HMAC(key, Digests.md5(), 64);
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
		return new HMAC(key, Digests.sha1(), 64);
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
		return new HMAC(key, Digests.sha256(), 64);
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
		return new HMAC(key, Digests.sha512(), 128);
	}

	private static final class HMAC implements MAC
	{
		private final byte[] key;
		private final Digest digest;

		HMAC(byte[] key, Digest digest, int blockSize)
		{
			if (key.length > blockSize) {
				this.key = digest.digest(key);
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
		public void reset()
		{
			digest.reset();
			for (byte b : key) {
				digest.update((byte) ((b & 0xFF) ^ 0x36));
			}
		}

		@Override
		public void update(byte input)
		{
			digest.update(input);
		}

		@Override
		public void update(byte... input)
		{
			digest.update(input);
		}

		@Override
		public void update(byte[] input, int off, int len)
		{
			digest.update(input, off, len);
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
		public String toString()
		{
			return "HMAC-" + digest;
		}
	}

	private HMACs()
	{
		/* ... */
	}
}
