/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012 Osman KOCAK <kocakosm@gmail.com>                        *
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

import org.junit.Test;

/**
 * {@link StringReader} test.
 *
 * @author Osman KOCAK
 */
public final class StringReaderTest
{
	@Test
	public void testReady()
	{
		StringReader reader = new StringReader("");
		assertTrue(reader.ready());
	}

	@Test
	public void testRead()
	{
		StringReader reader = new StringReader("Hello", " ", "world !");
		for (char c : "Hello world !".toCharArray()) {
			assertEquals(c, reader.read());
		}
		assertEquals(-1, reader.read());
	}

	@Test
	public void testReadArray()
	{
		StringReader reader = new StringReader("Hello", " ", "world !");
		char[] buf = new char[5];
		assertEquals(5, reader.read(buf));
		assertArrayEquals("Hello".toCharArray(), buf);

		reader = new StringReader("Hello");
		buf = new char[15];
		assertEquals(5, reader.read(buf));
		for (int i = 0; i < "Hello".length(); i++) {
			assertEquals("Hello".charAt(i), buf[i]);
		}

		reader = new StringReader("Hello", " ", "world !");
		buf = new char[5];
		assertEquals(5, reader.read(buf));
		assertArrayEquals("Hello".toCharArray(), buf);
		
		buf = new char[1];
		assertEquals(1, reader.read(buf, 0, 1));
		assertArrayEquals(" ".toCharArray(), buf);
		
		buf = new char[10];
		assertEquals(7, reader.read(buf, 1, 9));
		for (int i = 0; i < 7; i++) {
			assertEquals("world !".charAt(i), buf[i + 1]);
		}
	}

	@Test
	public void testSkip()
	{
		StringReader reader = new StringReader("Hello", " ", "world !");
		assertEquals(6, reader.skip(6));
		for (char c : "world !".toCharArray()) {
			assertEquals(c, reader.read());
		}
		assertEquals(0, reader.skip(1));
		assertEquals(-1, reader.read());

		reader = new StringReader("Hello");
		assertEquals(5, reader.skip(6));
		assertEquals(-1, reader.read());
		assertEquals(0, reader.skip(1));
	}

	@Test
	public void testMarkAndReset()
	{
		StringReader reader = new StringReader("Hello", " ", "world !");
		assertTrue(reader.markSupported());
		reader.mark(5);
		char[] buf = new char[5];
		reader.read(buf);
		assertArrayEquals("Hello".toCharArray(), buf);
		reader.reset();
		reader.read(buf);
		assertArrayEquals("Hello".toCharArray(), buf);
	}
}
