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

package org.pitaya.hmac;

import org.pitaya.digest.Digest;
import org.pitaya.digest.Digests;

import java.util.Arrays;

/**
 * Somme commonly used HMAC engines. Careful: some of these HMAC engines are not
 * suitable for security related applications.
 *
 * @author Osman KOCAK
 */
public final class HMACs
{
	/**
	 * Returns a new MD2 {@link HMAC} instance.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new MD2 {@link HMAC} instance.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static HMAC md2(byte[] key)
	{
		return new HMACImpl(key, Digests.md2(), 16);
	}

	/**
	 * Returns a new MD4 {@link HMAC} instance.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new MD4 {@link HMAC} instance.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static HMAC md4(byte[] key)
	{
		return new HMACImpl(key, Digests.md4(), 64);
	}

	/**
	 * Returns a new MD5 {@link HMAC} instance.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new MD5 {@link HMAC} instance.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static HMAC md5(byte[] key)
	{
		return new HMACImpl(key, Digests.md5(), 64);
	}

	/**
	 * Returns a new SHA1 {@link HMAC} instance.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new SHA1 {@link HMAC} instance.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static HMAC sha1(byte[] key)
	{
		return new HMACImpl(key, Digests.sha1(), 64);
	}

	/**
	 * Returns a new SHA-256 {@link HMAC} instance.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new SHA-256 {@link HMAC} instance.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static HMAC sha256(byte[] key)
	{
		return new HMACImpl(key, Digests.sha256(), 64);
	}

	/**
	 * Returns a new SHA-512 {@link HMAC} instance.
	 *
	 * @param key the HMAC's secret key.
	 *
	 * @return a new SHA-512 {@link HMAC} instance.
	 *
	 * @throws NullPointerException if {@code key} is {@code null}.
	 */
	public static HMAC sha512(byte[] key)
	{
		return new HMACImpl(key, Digests.sha512(), 128);
	}

	private static final class HMACImpl implements HMAC
	{
		private final byte[] key;
		private final Digest digest;

		HMACImpl(byte[] key, Digest digest, int blockSize)
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
				digest.update((byte)((b & 0xFF) ^ 0x36));
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
		public byte[] hmac(byte... input)
		{
			update(input);
			return hmac();
		}

		@Override
		public byte[] hmac()
		{
			byte[] h = digest.digest();
			for (byte b : key) {
				digest.update((byte)((b & 0xFF) ^ 0x5c));
			}
			byte[] hmac = digest.digest(h);
			reset();
			return hmac;
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
