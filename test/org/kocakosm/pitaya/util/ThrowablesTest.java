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

package org.kocakosm.pitaya.util;

import static org.junit.Assert.*;

import java.io.IOError;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * {@link Throwables}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class ThrowablesTest
{
	@Test
	public void testGetRootCause()
	{
		Throwable root = new Throwable();
		assertEquals(root, Throwables.getRootCause(root));

		Throwable t = new Throwable(new Throwable(root));
		assertEquals(root, Throwables.getRootCause(t));
	}

	@Test
	public void testGetCauseChain()
	{
		Throwable root = new Throwable();
		assertEquals(Arrays.asList(root), Throwables.getCauseChain(root));

		Throwable t = new Throwable(new Throwable(root));
		assertEquals(Arrays.asList(t, t.getCause(), root), Throwables.getCauseChain(t));
	}

	@Test
	public void testGetStackTrace()
	{
		Throwable t = new Throwable("ERROR");
		String trace = Throwables.getStackTrace(t);
		assertTrue(trace.startsWith("java.lang.Throwable: ERROR"));
		assertTrue(trace.length() > "java.lang.Throwable: ERROR".length());
	}

	@Test
	public void testGetStackFrames()
	{
		Throwable t = new Throwable(new NullPointerException("NPE"));
		StackTraceElement[] elements = t.getStackTrace();
		List<String> frames = Throwables.getStackFrames(t);
		assertEquals(elements.length, frames.size());
		for (int i = 0; i < elements.length; i++) {
			assertEquals(elements[i].toString(), frames.get(i));
		}
	}

	@Test(expected = IOError.class)
	public void testPropagateError()
	{
		Throwables.propagate(new IOError(new Throwable()));
	}

	@Test(expected = NullPointerException.class)
	public void testPropagateRuntimeException()
	{
		Throwables.propagate(new NullPointerException());
	}

	@Test(expected = RuntimeException.class)
	public void testPropagateCheckedException()
	{
		Throwables.propagate(new IOException());
	}
}
