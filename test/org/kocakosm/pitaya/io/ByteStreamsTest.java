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

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * {@link ByteStreams}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class ByteStreamsTest
{
	private static final byte[] DATA = {
		(byte) 0x00, (byte) 0xFF, (byte) 0x00,
		(byte) 0xFF, (byte) 0x00, (byte) 0xFF
	};

	@Test
	public void testConcatArray() throws Exception
	{
		InputStream in1 = new ByteArrayInputStream(DATA, 0, 2);
		InputStream in2 = new ByteArrayInputStream(DATA, 2, 2);
		InputStream in3 = new ByteArrayInputStream(DATA, 4, 2);
		assertArrayEquals(DATA, read(ByteStreams.concat(in1, in2, in3)));
	}

	@Test
	public void testConcatList() throws Exception
	{
		InputStream in1 = new ByteArrayInputStream(DATA, 0, 2);
		InputStream in2 = new ByteArrayInputStream(DATA, 2, 2);
		InputStream in3 = new ByteArrayInputStream(DATA, 4, 2);
		List<InputStream> streams = Arrays.asList(in1, in2, in3);
		assertArrayEquals(DATA, read(ByteStreams.concat(streams)));
	}

	@Test
	public void testCopy() throws Exception
	{
		InputStream in = new ByteArrayInputStream(DATA);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteStreams.copy(in, out);
		assertArrayEquals(DATA, out.toByteArray());
	}

	@Test
	public void testLimit() throws Exception
	{
		InputStream in = new ByteArrayInputStream(DATA);
		InputStream limited = ByteStreams.limit(in, 2);
		assertArrayEquals(new byte[]{(byte) 0x00, (byte) 0xFF}, read(limited));
	}

	@Test
	public void testRead() throws Exception
	{
		InputStream in = new ByteArrayInputStream(DATA);
		assertArrayEquals(DATA, ByteStreams.read(in));
	}

	@Test
	public void testTeeArray() throws Exception
	{
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();
		ByteArrayOutputStream out3 = new ByteArrayOutputStream();
		ByteStreams.tee(out1, out2, out3).write(DATA);
		assertArrayEquals(DATA, out1.toByteArray());
		assertArrayEquals(DATA, out2.toByteArray());
		assertArrayEquals(DATA, out2.toByteArray());
	}

	@Test
	public void testTeeIterable() throws Exception
	{
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();
		ByteArrayOutputStream out3 = new ByteArrayOutputStream();
		List<ByteArrayOutputStream> streams = Arrays.asList(out1, out2, out3);
		ByteStreams.tee(streams).write(DATA);
		assertArrayEquals(DATA, out1.toByteArray());
		assertArrayEquals(DATA, out2.toByteArray());
		assertArrayEquals(DATA, out2.toByteArray());
	}

	private byte[] read(InputStream in) throws Exception
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int len = in.read(buf);
		while (len >= 0) {
			out.write(buf, 0, len);
			len = in.read(buf);
		}
		out.flush();
		return out.toByteArray();
	}
}
