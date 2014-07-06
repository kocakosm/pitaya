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

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

import org.junit.Test;

/**
 * {@link TeeWriter}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class TeeWriterTest
{
	private static final char[] DATA = "Hello world !".toCharArray();

	@Test
	public void testAppendChar() throws IOException
	{
		CharArrayWriter w1 = new CharArrayWriter();
		CharArrayWriter w2 = new CharArrayWriter();
		Writer tee = new TeeWriter(w1, w2);
		for (char c : DATA) {
			tee.append(c);
		}
		tee.flush();
		assertArrayEquals(DATA, w1.toCharArray());
		assertArrayEquals(DATA, w2.toCharArray());
	}

	@Test
	public void testAppendCharSequence() throws IOException
	{
		CharArrayWriter w1 = new CharArrayWriter();
		CharArrayWriter w2 = new CharArrayWriter();
		Writer tee = new TeeWriter(w1, w2);
		String data = new String(DATA);
		tee.append(data);
		tee.flush();
		assertArrayEquals(DATA, w1.toCharArray());
		assertArrayEquals(DATA, w2.toCharArray());

		w1 = new CharArrayWriter();
		w2 = new CharArrayWriter();
		tee = new TeeWriter(w1, w2);
		tee.append(data, 0, 6);
		tee.append(data, 6, 13);
		tee.flush();
		assertArrayEquals(DATA, w1.toCharArray());
		assertArrayEquals(DATA, w2.toCharArray());
	}

	@Test
	public void testWriteChar() throws IOException
	{
		CharArrayWriter w1 = new CharArrayWriter();
		CharArrayWriter w2 = new CharArrayWriter();
		Writer tee = new TeeWriter(w1, w2);
		for (char c : DATA) {
			tee.write(c);
		}
		tee.flush();
		assertArrayEquals(DATA, w1.toCharArray());
		assertArrayEquals(DATA, w2.toCharArray());
	}

	@Test
	public void testWriteCharArray() throws IOException
	{
		CharArrayWriter w1 = new CharArrayWriter();
		CharArrayWriter w2 = new CharArrayWriter();
		Writer tee = new TeeWriter(w1, w2);
		tee.write(DATA);
		tee.flush();
		assertArrayEquals(DATA, w1.toCharArray());
		assertArrayEquals(DATA, w2.toCharArray());

		w1 = new CharArrayWriter();
		w2 = new CharArrayWriter();
		tee = new TeeWriter(w1, w2);
		tee.write(DATA, 0, 6);
		tee.write(DATA, 6, 7);
		tee.flush();
		assertArrayEquals(DATA, w1.toCharArray());
		assertArrayEquals(DATA, w2.toCharArray());
	}

	@Test
	public void testWriteString() throws IOException
	{
		CharArrayWriter w1 = new CharArrayWriter();
		CharArrayWriter w2 = new CharArrayWriter();
		Writer tee = new TeeWriter(w1, w2);
		String data = new String(DATA);
		tee.write(data);
		tee.flush();
		assertArrayEquals(DATA, w1.toCharArray());
		assertArrayEquals(DATA, w2.toCharArray());

		w1 = new CharArrayWriter();
		w2 = new CharArrayWriter();
		tee = new TeeWriter(w1, w2);
		tee.write(data, 0, 6);
		tee.write(data, 6, 7);
		tee.flush();
		assertArrayEquals(DATA, w1.toCharArray());
		assertArrayEquals(DATA, w2.toCharArray());
	}

	@Test
	public void testFlush() throws IOException
	{
		Writer w1 = mock(Writer.class);
		Writer w2 = mock(Writer.class);
		Writer tee = new TeeWriter(w1, w2);
		tee.flush();
		verify(w1).flush();
		verify(w2).flush();
	}

	@Test
	public void testClose() throws IOException
	{
		Writer w1 = mock(Writer.class);
		Writer w2 = mock(Writer.class);
		Writer tee = new TeeWriter(w1, w2);
		tee.close();
		verify(w1).close();
		verify(w2).close();
	}
}
