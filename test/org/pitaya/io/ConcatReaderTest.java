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

import java.io.IOException;
import java.io.Reader;

import org.junit.Test;

/**
 * {@link ConcatReader}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ConcatReaderTest
{
	@Test
	public void testReady() throws IOException
	{
		Reader data = new StringReader("Abbey road");
		Reader reader = new ConcatReader(data);
		assertEquals(data.ready(), reader.ready());
		reader.read(new char[8]);
		assertEquals(data.ready(), reader.ready());
	}

	@Test
	public void testRead() throws IOException
	{
		Reader r1 = new StringReader("Hello");
		Reader r2 = new StringReader(" ");
		Reader r3 = new StringReader("World");
		Reader reader = new ConcatReader(r1, r2, r3);
		for (char c : "Hello World".toCharArray()) {
			assertEquals(c & 0xFF, reader.read());
		}
		assertEquals(-1, reader.read());
	}

	@Test
	public void testReadArray() throws IOException
	{
		Reader r1 = new StringReader("Hello");
		Reader r2 = new StringReader(" ");
		Reader r3 = new StringReader("World");
		Reader reader = new ConcatReader(r1, r2, r3);

		char[] buf = new char[5];
		assertEquals(5, reader.read(buf));
		assertArrayEquals("Hello".toCharArray(), buf);

		buf = new char[1];
		assertEquals(1, reader.read(buf, 0, 1));
		assertArrayEquals(" ".toCharArray(), buf);

		buf = new char[10];
		assertEquals(5, reader.read(buf, 0, 8));
		for (int i = 0; i < 5; i++) {
			assertEquals("World".charAt(i), buf[i]);
		}
	}

	@Test
	public void testMarkSupported() throws IOException
	{
		Reader reader = new ConcatReader(mock(Reader.class));
		assertFalse(reader.markSupported());
	}

	@Test(expected = IOException.class)
	public void testMark() throws IOException
	{
		new ConcatReader(mock(Reader.class)).mark(0);
	}

	@Test(expected = IOException.class)
	public void testReset() throws IOException
	{
		new ConcatReader(mock(Reader.class)).reset();
	}

	@Test
	public void testSkip() throws IOException
	{
		Reader r1 = new StringReader("Yellow");
		Reader r2 = new StringReader("Submarine");
		Reader reader = new ConcatReader(r1, r2);
		assertEquals("Yellow".length(), reader.skip("Yellow".length()));
		for (char c : "Submarine".toCharArray()) {
			assertEquals(c & 0xFF, reader.read());
		}
	}

	@Test
	public void testClose() throws IOException
	{
		Reader r1 = mock(Reader.class);
		Reader r2 = mock(Reader.class);
		Reader reader = new ConcatReader(r1, r2);
		reader.close();
		verify(r1).close();
		verify(r2).close();
	}
}
