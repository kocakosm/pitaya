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

package org.kocakosm.pitaya.security;

import org.kocakosm.pitaya.util.Bits;
import org.kocakosm.pitaya.util.ByteBuffer;
import org.kocakosm.pitaya.util.LittleEndian;
import org.kocakosm.pitaya.util.Parameters;
import org.kocakosm.pitaya.util.XObjects;

import java.util.Arrays;

/**
 * SCrypt Key Derivation Function as specified by the Internet Engineering Task
 * Force (http://tools.ietf.org/html/draft-josefsson-scrypt-kdf-01). Instances
 * of this class are immutable.
 *
 * @author Osman KOCAK
 */
final class SCrypt implements KDF
{
	private final int r;
	private final int n;
	private final int p;
	private final int dkLen;

	/**
	 * Creates a new {@code SCrypt} instance.
	 *
	 * @param r the block size parameter.
	 * @param n the CPU/Memory cost parameter.
	 * @param p the parallelization parameter.
	 * @param dkLen the desired length for derived keys, in bytes.
	 *
	 * @throws IllegalArgumentException if {@code r, dkLen} or {@code p} is
	 *	negative, or if {@code n} is not greater than 1 or if it is not
	 *	a power of 2 or if it is not less than 2 ^ (128 * r / 8), or if
	 *	{@code p} is greater than ((2 ^ 32 - 1) * 32) / (128 * r).
	 */
	SCrypt(int r, int n, int p, int dkLen)
	{
		Parameters.checkCondition(r > 0 && p > 0 && dkLen > 0);
		Parameters.checkCondition(n > 1 && (n & (n - 1)) == 0);
		Parameters.checkCondition(r == 1 ? n < (1 << 16) : true);
		Parameters.checkCondition(p * r < (1 << 30));
		this.r = r;
		this.n = n;
		this.p = p;
		this.dkLen = dkLen;
	}

	@Override
	public byte[] deriveKey(byte[] secret, byte[] salt)
	{
		KDF pbkdf2 = KDFs.pbkdf2(Algorithm.HMAC_SHA256, 1, p * 128 * r);
		byte[] b = pbkdf2.deriveKey(secret, salt);
		ByteBuffer buffer = new ByteBuffer(p * 128 * r);
		for (int i = 0; i < p; i++) {
			buffer.append(roMix(slice(b, i * 128 * r, 128 * r)));
		}
		pbkdf2 = KDFs.pbkdf2(Algorithm.HMAC_SHA256, 1, dkLen);
		return pbkdf2.deriveKey(secret, buffer.toByteArray());
	}

	@Override
	public String toString()
	{
		return XObjects.toStringBuilder("SCrypt").append("r", r)
			.append("n", n).append("p", p).append("dkLen", dkLen)
			.toString();
	}

	private byte[] roMix(byte[] x)
	{
		int len = x.length;
		ByteBuffer v = new ByteBuffer(n * len);
		for (int i = 0; i < n; i++) {
			v.append(x);
			x = blockMix(x);
		}
		int offset = (2 * r - 1) * 64;
		for (int i = 0; i < n; i++) {
			int j = LittleEndian.decodeInt(x, offset) & (n - 1);
			x = blockMix(xor(x, v.toByteArray(j * len, len)));
		}
		return x;
	}

	private byte[] blockMix(byte[] in)
	{
		byte[] x = slice(in, (2 * r - 1) * 64, 64);
		ByteBuffer buffer = new ByteBuffer(128 * r);
		for (int i = 0; i < 2 * r; i++) {
			x = salsa20(xor(x, slice(in, i * 64, 64)), 8);
			buffer.append(x);
		}
		byte[] y = buffer.toByteArray();
		byte[] b = new byte[in.length];
		for (int i = 0; i < r; i++) {
			System.arraycopy(y, (i * 2) * 64, b, i * 64, 64);
		}
		for (int i = 0; i < r; i++) {
			System.arraycopy(y, (i * 2 + 1) * 64, b, (i + r) * 64, 64);
		}
		return b;
	}

	private byte[] salsa20(byte[] buf, int rounds)
	{
		int[] in = new int[16];
		for (int i = 0; i < 16; i++) {
			in[i] = LittleEndian.decodeInt(buf, i * 4);
		}
		int[] x = Arrays.copyOf(in, in.length);
		for (int i = rounds; i > 0; i -= 2) {
			x[ 4] ^= Bits.rotateLeft(x[ 0] + x[12], 7);
			x[ 8] ^= Bits.rotateLeft(x[ 4] + x[ 0], 9);
			x[12] ^= Bits.rotateLeft(x[ 8] + x[ 4], 13);
			x[ 0] ^= Bits.rotateLeft(x[12] + x[ 8], 18);
			x[ 9] ^= Bits.rotateLeft(x[ 5] + x[ 1], 7);
			x[13] ^= Bits.rotateLeft(x[ 9] + x[ 5], 9);
			x[ 1] ^= Bits.rotateLeft(x[13] + x[ 9], 13);
			x[ 5] ^= Bits.rotateLeft(x[ 1] + x[13], 18);
			x[14] ^= Bits.rotateLeft(x[10] + x[ 6], 7);
			x[ 2] ^= Bits.rotateLeft(x[14] + x[10], 9);
			x[ 6] ^= Bits.rotateLeft(x[ 2] + x[14], 13);
			x[10] ^= Bits.rotateLeft(x[ 6] + x[ 2], 18);
			x[ 3] ^= Bits.rotateLeft(x[15] + x[11], 7);
			x[ 7] ^= Bits.rotateLeft(x[ 3] + x[15], 9);
			x[11] ^= Bits.rotateLeft(x[ 7] + x[ 3], 13);
			x[15] ^= Bits.rotateLeft(x[11] + x[ 7], 18);
			x[ 1] ^= Bits.rotateLeft(x[ 0] + x[ 3], 7);
			x[ 2] ^= Bits.rotateLeft(x[ 1] + x[ 0], 9);
			x[ 3] ^= Bits.rotateLeft(x[ 2] + x[ 1], 13);
			x[ 0] ^= Bits.rotateLeft(x[ 3] + x[ 2], 18);
			x[ 6] ^= Bits.rotateLeft(x[ 5] + x[ 4], 7);
			x[ 7] ^= Bits.rotateLeft(x[ 6] + x[ 5], 9);
			x[ 4] ^= Bits.rotateLeft(x[ 7] + x[ 6], 13);
			x[ 5] ^= Bits.rotateLeft(x[ 4] + x[ 7], 18);
			x[11] ^= Bits.rotateLeft(x[10] + x[ 9], 7);
			x[ 8] ^= Bits.rotateLeft(x[11] + x[10], 9);
			x[ 9] ^= Bits.rotateLeft(x[ 8] + x[11], 13);
			x[10] ^= Bits.rotateLeft(x[ 9] + x[ 8], 18);
			x[12] ^= Bits.rotateLeft(x[15] + x[14], 7);
			x[13] ^= Bits.rotateLeft(x[12] + x[15], 9);
			x[14] ^= Bits.rotateLeft(x[13] + x[12], 13);
			x[15] ^= Bits.rotateLeft(x[14] + x[13], 18);
		}
		byte[] out = new byte[64];
		for (int i = 0; i < 16; i++) {
			LittleEndian.encode(x[i] + in[i], out, i * 4);
		}
		return out;
	}

	private byte[] slice(byte[] src, int off, int len)
	{
		byte[] slice = new byte[len];
		System.arraycopy(src, off, slice, 0, len);
		return slice;
	}

	private byte[] xor(byte[] x, byte[] y)
	{
		int len = x.length;
		byte[] z = new byte[len];
		for (int k = 0; k < len; k++) {
			z[k] = (byte) (x[k] ^ y[k]);
		}
		return z;
	}
}
