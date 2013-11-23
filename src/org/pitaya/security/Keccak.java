/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2013 Osman KOCAK <kocakosm@gmail.com>                   *
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

import org.pitaya.util.Bits;
import org.pitaya.util.LittleEndian;
import org.pitaya.util.Parameters;

import java.util.Arrays;

/**
 * The Keccak digest algorithm. Instances of this class are not thread safe.
 * Note: this implementation is focused on readability instead of performance,
 * so it should not perform as well as other implementations.
 *
 * @author Osman KOCAK
 */
final class Keccak extends AbstractDigest
{
	private static final long[] RC = new long[] {
		0x0000000000000001L, 0x0000000000008082L, 0x800000000000808aL,
		0x8000000080008000L, 0x000000000000808bL, 0x0000000080000001L,
		0x8000000080008081L, 0x8000000000008009L, 0x000000000000008aL,
		0x0000000000000088L, 0x0000000080008009L, 0x000000008000000aL,
		0x000000008000808bL, 0x800000000000008bL, 0x8000000000008089L,
		0x8000000000008003L, 0x8000000000008002L, 0x8000000000000080L,
		0x000000000000800aL, 0x800000008000000aL, 0x8000000080008081L,
		0x8000000000008080L, 0x0000000080000001L, 0x8000000080008008L
	};
	private static final int[] R = new int[] {
		0, 1, 62, 28, 27, 36, 44, 6, 55, 20, 3, 10, 43,
		25, 39, 41, 45, 15, 21, 8, 18, 2, 61, 56, 14
	};

	private final long[] A;
	private final int blockLen;
	private final byte[] buffer;
	private int bufferLen;

	/**
	 * Creates a new ready to use {@code Keccak}.
	 *
	 * @param length the digest length (in bytes).
	 *
	 * @throws IllegalArgumentException if {@code length} is not one of 28,
	 *	32, 48 or 64.
	 */
	Keccak(int length)
	{
		super("Keccak-" + length * 8, length);
		Parameters.checkCondition(length == 28 || length == 32 || length == 48 || length == 64);
		this.A = new long[25];
		this.blockLen = 200 - 2 * length;
		this.buffer = new byte[blockLen];
		this.bufferLen = 0;
	}

	@Override
	public Digest reset()
	{
		for (int i = 0; i < 25; i++) {
			A[i] = 0L;
		}
		bufferLen = 0;
		return this;
	}

	@Override
	public Digest update(byte input)
	{
		buffer[bufferLen] = input;
		if (++bufferLen == blockLen) {
			processBuffer();
		}
		return this;
	}

	@Override
	public Digest update(byte[] input, int off, int len)
	{
		while (len > 0) {
			int cpLen = Math.min(blockLen - bufferLen, len);
			System.arraycopy(input, off, buffer, bufferLen, cpLen);
			bufferLen += cpLen;
			off += cpLen;
			len -= cpLen;
			if (bufferLen == blockLen) {
				processBuffer();
			}
		}
		return this;
	}

	@Override
	public byte[] digest()
	{
		addPadding();
		processBuffer();
		byte[] tmp = new byte[length() * 8];
		for (int i = 0; i < length(); i += 8) {
			LittleEndian.encode(A[i >>> 3], tmp, i);
		}
		reset();
		return Arrays.copyOf(tmp, length());
	}

	private void addPadding()
	{
		if ((bufferLen + 1) == buffer.length) {
			buffer[bufferLen] = (byte) 0x81;
		} else {
			buffer[bufferLen] = (byte) 0x01;
			for (int i = bufferLen + 1; i < (buffer.length - 1); i++) {
				buffer[i] = 0;
			}
			buffer[buffer.length - 1] = (byte) 0x80;
		}
	}

	private void processBuffer()
	{
		for (int i = 0; i < buffer.length; i += 8) {
			A[i >>> 3] ^= LittleEndian.decodeLong(buffer, i);
		}
		keccakf();
		bufferLen = 0;
	}

	private void keccakf()
	{
		long[] B = new long[25];
		long[] C = new long[5];
		long[] D = new long[5];
		for (int n = 0; n < 24; n++) {
			for (int x = 0; x < 5; x++) {
				C[x] = A[index(x, 0)] ^ A[index(x, 1)] ^ A[index(x, 2)] ^ A[index(x, 3)] ^ A[index(x, 4)];
			}
			for (int x = 0; x < 5; x++) {
				D[x] = C[index(x - 1)] ^ Bits.rotateLeft(C[index(x + 1)], 1);
			}
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					A[index(x, y)] ^= D[x];
				}
			}
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					B[index(y, x * 2 + 3 * y)] = Bits.rotateLeft(A[index(x, y)], R[index(x, y)]);
				}
			}
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					A[index(x, y)] = B[index(x, y)] ^ (~B[index(x + 1, y)] & B[index(x + 2, y)]);
				}
			}
			A[0] ^= RC[n];
		}
	}

	private int index(int x)
	{
		while (x < 0) {
			x += 5;
		}
		return (x % 5);
	}

	private int index(int x, int y)
	{
		return index(x) + 5 * index(y);
	}
}
