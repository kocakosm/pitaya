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

package org.kocakosm.pitaya.charset;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;

import org.junit.Test;

/**
 * {@link Charsets}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class CharsetsTest
{
	@Test
	public void testDefault()
	{
		assertEquals(Charset.defaultCharset(), Charsets.DEFAULT);
	}

	@Test
	public void testASCII()
	{
		assertEquals("US-ASCII", Charsets.US_ASCII.toString());
	}

	@Test
	public void testLatin1()
	{
		assertEquals("ISO-8859-1", Charsets.ISO_8859_1.toString());
	}

	@Test
	public void testUTF8()
	{
		assertEquals("UTF-8", Charsets.UTF_8.toString());
	}

	@Test
	public void testUTF16()
	{
		assertEquals("UTF-16", Charsets.UTF_16.toString());
	}

	@Test
	public void testUTF16BE()
	{
		assertEquals("UTF-16BE", Charsets.UTF_16BE.toString());
	}

	@Test
	public void testUTF16LE()
	{
		assertEquals("UTF-16LE", Charsets.UTF_16LE.toString());
	}
}
