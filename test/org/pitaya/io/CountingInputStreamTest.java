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

package org.pitaya.io;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.pitaya.util.ByteBuffer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * {@link CountingInputStream}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class CountingInputStreamTest
{
	private static final byte[] DATA = {
		(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44,
		(byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88, (byte) 0x99,
		(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE,
		(byte) 0xFF
	};

	@Test
	public void testAvailable() throws IOException
	{
		InputStream data = new ByteArrayInputStream(DATA);
		CountingInputStream in = new CountingInputStream(data);
		assertTrue(in.available() > 0);
		Streams.read(in);
		assertTrue(in.available() == 0);
	}

	@Test
	public void testRead() throws IOException
	{
		InputStream data = new ByteArrayInputStream(DATA);
		CountingInputStream in = new CountingInputStream(data);
		ByteBuffer buf = new ByteBuffer();
		buf.append((byte) in.read());
		byte[] b = new byte[5];
		in.read(b);
		buf.append(b);
		b = new byte[20];
		int len = in.read(b, 0, 15);
		buf.append(b, 0, len);
		assertEquals(16, in.getCount());
		assertArrayEquals(DATA, buf.toByteArray());
	}

	@Test
	public void testCount() throws IOException
	{
		InputStream data = new ByteArrayInputStream(DATA);
		CountingInputStream in = new CountingInputStream(data);
		assertEquals(0, in.getCount());
		in.read();
		assertEquals(1, in.getCount());
		in.read(new byte[9]);
		assertEquals(10, in.getCount());
		in.read(new byte[20], 0, 10);
		assertEquals(16, in.getCount());
	}

	@Test
	public void testResetCount() throws IOException
	{
		InputStream data = new ByteArrayInputStream(DATA);
		CountingInputStream in = new CountingInputStream(data);
		in.read(new byte[8]);
		assertEquals(8, in.getCount());
		assertEquals(8, in.resetCount());
		assertEquals(0, in.getCount());
		in.read(new byte[8]);
		assertEquals(8, in.getCount());
	}

	@Test
	public void testMarkSupported() throws IOException
	{
		InputStream data = new ByteArrayInputStream(DATA);
		CountingInputStream in = new CountingInputStream(data);
		assertEquals(data.markSupported(), in.markSupported());
	}

	@Test
	public void testMarkAndReset() throws IOException
	{
		InputStream data = new ByteArrayInputStream(DATA);
		CountingInputStream in = new CountingInputStream(data);
		in.mark(100);
		in.read(new byte[20]);
		assertEquals(16, in.getCount());
		in.reset();
		in.read(new byte[20]);
		assertEquals(32, in.getCount());
	}

	@Test
	public void testSkip() throws IOException
	{
		InputStream data = new ByteArrayInputStream(DATA);
		CountingInputStream in = new CountingInputStream(data);
		in.read();
		assertEquals(1, in.getCount());
		in.skip(20);
		in.read(new byte[8]);
		assertEquals(1, in.getCount());
	}

	@Test
	public void testClose() throws IOException
	{
		InputStream data = mock(InputStream.class);
		InputStream in = new CountingInputStream(data);
		in.close();
		verify(data).close();
	}
}
