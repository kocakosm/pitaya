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

package org.kocakosm.pitaya.net;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;

/**
 * {@link URLs}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class URLsTest
{
	@Test
	public void testCreate() throws Exception
	{
		String url = "http://localhost:8080/test?hello=world";
		assertEquals(toURL(url), URLs.create(url));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateError()
	{
		URLs.create("/test");
	}

	@Test
	public void testRelativize() throws Exception
	{
		URL base = toURL("http://localhost/test");
		URL full = toURL("http://localhost/test/hello/world");
		assertEquals("hello/world", URLs.relativize(base, full));
	}

	@Test
	public void testResolve() throws Exception
	{
		URL base = toURL("http://localhost/");
		String path = "/hello/world";
		URL resolved = toURL("http://localhost/hello/world");
		assertEquals(resolved, URLs.resolve(base, path));
	}

	private URL toURL(String url) throws Exception
	{
		return new URL(url);
	}
}
