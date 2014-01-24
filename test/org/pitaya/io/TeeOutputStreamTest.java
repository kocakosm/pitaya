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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

/**
 * {@link TeeOutputStream}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class TeeOutputStreamTest
{
	private static final byte[] DATA = {
		(byte) 0x00, (byte) 0x11, (byte) 0x22, (byte) 0x33, (byte) 0x44,
		(byte) 0x55, (byte) 0x66, (byte) 0x77, (byte) 0x88, (byte) 0x99,
		(byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE,
	};

	@Test
	public void testWrite() throws IOException
	{
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();
		OutputStream tee = new TeeOutputStream(out1, out2);
		for (byte b : DATA) {
			tee.write(b);
		}
		tee.flush();
		assertArrayEquals(DATA, out1.toByteArray());
		assertArrayEquals(DATA, out2.toByteArray());
	}

	@Test
	public void testWriteArray() throws IOException
	{
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();
		OutputStream tee = new TeeOutputStream(out1, out2);
		tee.write(DATA);
		tee.flush();
		assertArrayEquals(DATA, out1.toByteArray());
		assertArrayEquals(DATA, out2.toByteArray());

		out1 = new ByteArrayOutputStream();
		out2 = new ByteArrayOutputStream();
		tee = new TeeOutputStream(out1, out2);
		tee.write(DATA, 0, 8);
		tee.write(DATA, 8, 7);
		tee.flush();
		assertArrayEquals(DATA, out1.toByteArray());
		assertArrayEquals(DATA, out2.toByteArray());
	}

	@Test
	public void testFlush() throws IOException
	{
		OutputStream out1 = mock(OutputStream.class);
		OutputStream out2 = mock(OutputStream.class);
		OutputStream out = new TeeOutputStream(out1, out2);
		out.flush();
		verify(out1).flush();
		verify(out2).flush();
	}

	@Test
	public void testClose() throws IOException
	{
		OutputStream out1 = mock(OutputStream.class);
		OutputStream out2 = mock(OutputStream.class);
		OutputStream out = new TeeOutputStream(out1, out2);
		out.close();
		verify(out1).close();
		verify(out2).close();
	}
}
