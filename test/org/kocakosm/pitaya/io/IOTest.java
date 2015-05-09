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
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

/**
 * {@link IO}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class IOTest
{
	@Test
	public void testClose() throws Exception
	{
		OutputStream out = null;
		IO.close(out);

		out = mock(OutputStream.class);
		IO.close(out);
		verify(out).close();

		out = mock(OutputStream.class);
		doThrow(new IOException()).when(out).close();
		IO.close(out);
		verify(out).close();
	}

	@Test
	public void testFlush() throws Exception
	{
		OutputStream out = null;
		IO.flush(out);

		out = mock(OutputStream.class);
		IO.flush(out);
		verify(out).flush();

		out = mock(OutputStream.class);
		doThrow(new IOException()).when(out).flush();
		IO.flush(out);
		verify(out).flush();
	}

	@Test
	public void testConstructor() throws Exception
	{
		Class<IO> c = IO.class;
		assertEquals(1, c.getDeclaredConstructors().length);
		Constructor<IO> constructor = c.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}
}
