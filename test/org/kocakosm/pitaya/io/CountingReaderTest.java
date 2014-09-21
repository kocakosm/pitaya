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
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.Reader;

import org.junit.Test;

/**
 * {@link CountingReader}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class CountingReaderTest
{
	private static final String DATA = "Hello World !!!!";

	@Test
	public void testReady() throws IOException
	{
		Reader data = new StringReader(DATA);
		CountingReader reader = new CountingReader(data);
		assertEquals(data.ready(), reader.ready());
		reader.read(new char[8]);
		assertEquals(data.ready(), reader.ready());
	}

	@Test
	public void testCount() throws IOException
	{
		Reader data = new StringReader(DATA);
		CountingReader reader = new CountingReader(data);
		assertEquals(0, reader.getCount());
		reader.read();
		assertEquals(1, reader.getCount());
		reader.read(new char[9]);
		assertEquals(10, reader.getCount());
		reader.read(new char[20], 0, 10);
		assertEquals(16, reader.getCount());
	}

	@Test
	public void testResetCount() throws IOException
	{
		Reader data = new StringReader(DATA);
		CountingReader reader = new CountingReader(data);
		reader.read(new char[8]);
		assertEquals(8, reader.getCount());
		assertEquals(8, reader.resetCount());
		assertEquals(0, reader.getCount());
		reader.read(new char[8]);
		assertEquals(8, reader.getCount());
	}

	@Test
	public void testMarkSupported() throws IOException
	{
		Reader data = new StringReader(DATA);
		CountingReader reader = new CountingReader(data);
		assertEquals(data.markSupported(), reader.markSupported());
	}

	@Test
	public void testMarkAndReset() throws IOException
	{
		Reader data = new StringReader(DATA);
		CountingReader reader = new CountingReader(data);
		reader.mark(100);
		reader.read(new char[20]);
		assertEquals(16, reader.getCount());
		reader.reset();
		reader.read(new char[20]);
		assertEquals(32, reader.getCount());
	}

	@Test
	public void testSkip() throws IOException
	{
		Reader data = new StringReader(DATA);
		CountingReader reader = new CountingReader(data);
		reader.read();
		assertEquals(1, reader.getCount());
		reader.skip(20);
		reader.read(new char[8]);
		assertEquals(1, reader.getCount());
	}

	@Test
	public void testClose() throws IOException
	{
		Reader data = mock(Reader.class);
		Reader reader = new CountingReader(data);
		reader.close();
		verify(data).close();
	}
}
