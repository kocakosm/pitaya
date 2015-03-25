/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2015 Osman KOCAK <kocakosm@gmail.com>                   *
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
import java.io.Reader;

import org.junit.Test;

/**
 * {@link LimitReader}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class LimitReaderTest
{
	private static final String DATA = "Hello World !!!!";

	@Test
	public void testReady() throws IOException
	{
		Reader data = new StringReader(DATA);
		Reader reader = new LimitReader(data, 10);
		assertEquals(data.ready(), reader.ready());
		reader.read(new char[8]);
		assertEquals(data.ready(), reader.ready());
	}

	@Test
	public void testRead() throws IOException
	{
		Reader data = new StringReader(DATA);
		Reader reader = new LimitReader(data, 5);
		for (char c : "Hello".toCharArray()) {
			assertEquals(c, reader.read());
		}
		assertEquals(-1, reader.read());
	}

	@Test
	public void testReadArray() throws IOException
	{
		Reader data = new StringReader(DATA);
		Reader reader = new LimitReader(data, 10);
		char[] buf = new char[5];
		assertEquals(5, reader.read(buf));
		assertArrayEquals("Hello".toCharArray(), buf);

		data = new StringReader(DATA);
		reader = new LimitReader(data, 11);
		buf = new char[15];
		assertEquals(11, reader.read(buf));
		for (int i = 0; i < "Hello World".length(); i++) {
			assertEquals("Hello World".charAt(i), buf[i]);
		}
	}

	@Test
	public void testMarkSupported() throws IOException
	{
		Reader data = new StringReader(DATA);
		Reader reader = new LimitReader(data, 10);
		assertEquals(data.markSupported(), reader.markSupported());
	}

	@Test
	public void testMarkAndReset() throws IOException
	{
		Reader data = new StringReader(DATA);
		Reader reader = new LimitReader(data, 10);
		char[] buf = new char[5];
		reader.mark(100);
		reader.read(buf);
		assertArrayEquals("Hello".toCharArray(), buf);
		reader.reset();
		reader.read(buf);
		assertArrayEquals("Hello".toCharArray(), buf);
	}

	@Test
	public void testSkip() throws IOException
	{
		Reader data = new StringReader(DATA);
		Reader reader = new LimitReader(data, 10);
		assertEquals(10, reader.skip(100));
		assertEquals(0, reader.skip(1));
	}

	@Test
	public void testClose() throws IOException
	{
		Reader data = mock(Reader.class);
		Reader reader = new LimitReader(data, 10);
		reader.close();
		verify(data).close();
	}
}
