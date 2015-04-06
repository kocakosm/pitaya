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

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Test;

/**
 * {@link Resource}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ResourceTest
{
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final String DATA = "Hello\nWorld";
	private static final byte[] DATA_BYTES = DATA.getBytes(UTF_8);

	@Test
	public void testFind()
	{
		Resource.find("org/kocakosm/pitaya/io/resource1.txt");
	}

	@Test(expected = Resource.NotFoundException.class)
	public void testResourceNotFound()
	{
		Resource.find("resource.txt");
	}

	@Test
	public void testFindWithContextClass()
	{
		Resource.find("resource1.txt", getClass());
	}

	@Test(expected = Resource.NotFoundException.class)
	public void testResourceNotFoundWithContextClass()
	{
		Resource.find("resource.txt", getClass());
	}

	@Test
	public void testGetURL() throws Exception
	{
		Resource r = Resource.find("resource1.txt", getClass());
		String url = r.getURL().toString();
		assertTrue(url.startsWith("file:/"));
		assertTrue(url.endsWith("/org/kocakosm/pitaya/io/resource1.txt"));
	}

	@Test
	public void testCopyToOutputStream() throws Exception
	{
		Resource r = Resource.find("resource1.txt", getClass());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		r.copyTo(out);
		assertArrayEquals(DATA_BYTES, out.toByteArray());
	}

	@Test
	public void testCopyToWriter() throws Exception
	{
		Resource r = Resource.find("resource1.txt", getClass());
		StringWriter out = new StringWriter();
		r.copyTo(out, UTF_8);
		assertEquals(DATA, out.toString());
	}

	@Test
	public void testRead() throws Exception
	{
		Resource r = Resource.find("resource1.txt", getClass());
		assertArrayEquals(DATA_BYTES, r.read());
	}

	@Test
	public void testReadAsString() throws Exception
	{
		Resource r = Resource.find("resource1.txt", getClass());
		assertEquals(DATA, r.read(UTF_8));
	}

	@Test
	public void testReadLines() throws Exception
	{
		Resource r = Resource.find("resource1.txt", getClass());
		assertEquals(Arrays.asList("Hello", "World"), r.readLines(UTF_8));
	}

	@Test
	public void testToString()
	{
		Resource r = Resource.find("resource1.txt", getClass());
		assertEquals(r.getURL().toString(), r.toString());
	}

	@Test
	public void testEqualsAndHashCode()
	{
		Resource r0 = null;
		Resource r1 = Resource.find("resource1.txt", getClass());
		Resource r2 = Resource.find("resource2.txt", getClass());
		Resource r3 = Resource.find("resource1.txt", getClass());

		assertFalse(r1.equals(r0));
		assertTrue(r1.equals(r1));
		assertEquals(r1.hashCode(), r1.hashCode());
		assertFalse(r1.equals(r2));
		assertFalse(r2.equals(r1));
		assertTrue(r1.equals(r3));
		assertTrue(r3.equals(r1));
		assertEquals(r1.hashCode(), r3.hashCode());
	}
}
