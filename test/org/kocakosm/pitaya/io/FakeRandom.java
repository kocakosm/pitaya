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

package org.kocakosm.pitaya.io;

import org.kocakosm.pitaya.util.ByteBuffer;

import java.util.Arrays;
import java.util.Random;

/**
 * Fake {@code Random} implementation.
 *
 * @author Osman KOCAK
 */
final class FakeRandom extends Random
{
	private static final long serialVersionUID = 1L;

	private int index = 0;
	private final byte[] src;

	FakeRandom(byte... src)
	{
		this.src = Arrays.copyOf(src, src.length);
	}

	@Override
	public int nextInt()
	{
		return src[index++ % src.length];
	}

	@Override
	public int nextInt(int n)
	{
		return Math.abs(nextInt() % n);
	}

	@Override
	public void nextBytes(byte[] bytes)
	{
		ByteBuffer buf = new ByteBuffer(bytes.length);
		while (buf.size() < bytes.length) {
			buf.append(src);
		}
		System.arraycopy(buf.toByteArray(), 0, bytes, 0, bytes.length);
	}
}
