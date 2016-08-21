/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2016 Osman KOCAK <kocakosm@gmail.com>                   *
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
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

/**
 * {@link CountingWriter}'s unit test.
 *
 * @author Osman KOCAK
 */
public final class CountingWriterTest
{
	@Test
	public void testAppend() throws IOException
	{
		Writer data = new StringWriter();
		CountingWriter out = new CountingWriter(data);
		assertEquals(out, out.append('H'));
		assertEquals(out, out.append("Hello", 1, 5));
		assertEquals(out, out.append(" World !"));
		assertEquals("Hello World !", data.toString());
	}

	@Test
	public void testWriteChars() throws IOException
	{
		Writer data = new StringWriter();
		CountingWriter out = new CountingWriter(data);
		out.write('H');
		out.write("Hello".toCharArray(), 1, 4);
		out.write(" World !");
		assertEquals("Hello World !", data.toString());
	}

	@Test
	public void testWriteString() throws IOException
	{
		Writer data = new StringWriter();
		CountingWriter out = new CountingWriter(data);
		out.write("H");
		out.write("Hello", 1, 4);
		assertEquals("Hello", data.toString());
	}

	@Test
	public void testCount() throws IOException
	{
		CountingWriter out = new CountingWriter(new StringWriter());
		out.write('H');
		assertEquals(1, out.getCount());
		out.write("ello");
		assertEquals(5, out.getCount());
		out.write("  World !!  ", 1, 9);
		assertEquals(14, out.getCount());
	}

	@Test
	public void testResetCount() throws IOException
	{
		CountingWriter out = new CountingWriter(new StringWriter());
		out.write("Hello");
		assertEquals(5, out.getCount());
		assertEquals(5, out.resetCount());
		assertEquals(0, out.getCount());
		out.write("Hello");
		assertEquals(5, out.resetCount());
	}

	@Test
	public void testFlush() throws IOException
	{
		Writer data = mock(Writer.class);
		new CountingWriter(data).flush();
		verify(data).flush();
	}

	@Test
	public void testClose() throws IOException
	{
		Writer data = mock(Writer.class);
		new CountingWriter(data).close();
		verify(data).close();
	}
}
