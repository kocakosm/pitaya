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

import org.kocakosm.pitaya.util.Strings;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link Scanf}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ScanfTest
{
	private OutputStream stdIn;

	@Before
	public void start() throws IOException
	{
		PipedInputStream in = new PipedInputStream();
		stdIn = new PipedOutputStream(in);
		System.setIn(in);
	}

	@Test
	public void testReadString() throws IOException
	{
		String s = Strings.random(20, "abcdef0123456789".toCharArray());
		write(s);
		assertEquals(s, Scanf.readString());
	}

	@Test
	public void testReadBooleanTrue() throws IOException
	{
		write("t ru e ");
		assertEquals(true, Scanf.readBoolean());
	}

	@Test
	public void testReadBooleanYes() throws IOException
	{
		write("yes");
		assertEquals(true, Scanf.readBoolean());
	}

	@Test
	public void testReadBooleanOn() throws IOException
	{
		write("On");
		assertEquals(true, Scanf.readBoolean());
	}

	@Test
	public void testReadBooleanFalse() throws IOException
	{
		write("False");
		assertEquals(false, Scanf.readBoolean());
	}

	@Test
	public void testReadBooleanRandom() throws IOException
	{
		write(Strings.random(5, "abcdef0123456789".toCharArray()));
		assertEquals(false, Scanf.readBoolean());
	}

	@Test
	public void testReadInt() throws IOException
	{
		int n = (int) (Math.random() * 1000);
		write(Integer.toString(n));
		assertEquals(n, Scanf.readInt());
	}

	@Test
	public void testReadIntHex() throws IOException
	{
		int n = (int) (Math.random() * 1000);
		write(Integer.toHexString(n));
		assertEquals(n, Scanf.readInt(16));
	}

	@Test
	public void testReadLong() throws IOException
	{
		long n = (long) (Math.random() * 1000);
		write(Long.toString(n));
		assertEquals(n, Scanf.readLong());
	}

	@Test
	public void testReadLongHex() throws IOException
	{
		long n = (long) (Math.random() * 1000);
		write(Long.toHexString(n));
		assertEquals(n, Scanf.readLong(16));
	}

	@Test
	public void testReadBigInteger() throws IOException
	{
		BigInteger n = BigInteger.valueOf((int) (Math.random() * 1000));
		write(n.toString());
		assertEquals(n, Scanf.readBigInteger());
	}

	@Test
	public void testReadBigIntegerHex() throws IOException
	{
		BigInteger n = BigInteger.valueOf((int) (Math.random() * 1000));
		write(n.toString(16));
		assertEquals(n, Scanf.readBigInteger(16));
	}

	@Test
	public void testReadDouble() throws IOException
	{
		Double n = Math.random() * 1000;
		write(Double.toString(n));
		assertEquals(n, Double.valueOf(Scanf.readDouble()));
	}

	@Test
	public void testReadBigDecimal() throws IOException
	{
		BigDecimal n = BigDecimal.valueOf(Math.random() * 1000);
		write(n.toString());
		assertEquals(n, Scanf.readBigDecimal());
	}

	@Test
	public void testConstructor() throws Exception
	{
		Class<Scanf> c = Scanf.class;
		assertEquals(1, c.getDeclaredConstructors().length);
		Constructor<Scanf> constructor = c.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	private void write(String str) throws IOException
	{
		Writer out = new OutputStreamWriter(stdIn);
		BufferedWriter writer = new BufferedWriter(out);
		writer.write(str);
		writer.newLine();
		writer.flush();
		writer.close();
	}
}
