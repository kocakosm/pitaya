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

import org.kocakosm.pitaya.charset.Charsets;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * {@link CharStreams}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class CharStreamsTest
{
	@Test
	public void testConcatArray() throws Exception
	{
		Reader in1 = new StringReader("Magical ");
		Reader in2 = new StringReader("Mystery ");
		Reader in3 = new StringReader("Tour");
		assertEquals("Magical Mystery Tour", read(CharStreams.concat(in1, in2, in3)));
	}

	@Test
	public void testConcatList() throws Exception
	{
		Reader in1 = new StringReader("Magical ");
		Reader in2 = new StringReader("Mystery ");
		Reader in3 = new StringReader("Tour");
		List<Reader> streams = Arrays.asList(in1, in2, in3);
		assertEquals("Magical Mystery Tour", read(CharStreams.concat(streams)));
	}

	@Test
	public void testCopyReaderToWriter() throws Exception
	{
		String data = "Strawberry Fields Forever";
		Reader in = new StringReader(data);
		Writer out = new StringWriter();
		CharStreams.copy(in, out);
		assertEquals(data, out.toString());
	}

	@Test
	public void testCopyInputStreamToWriter() throws Exception
	{
		String data = "Hello, Goodbye";
		InputStream in = new ByteArrayInputStream(data.getBytes());
		Writer out = new StringWriter();
		CharStreams.copy(in, out);
		assertEquals(data, out.toString());
	}

	@Test
	public void testCopyInputStreamToWriterWithCharset() throws Exception
	{
		String data = "Hello, Goodbye";
		Charset charset = Charsets.US_ASCII;
		InputStream in = new ByteArrayInputStream(data.getBytes(charset));
		Writer out = new StringWriter();
		CharStreams.copy(in, out, charset);
		assertEquals(data, out.toString());
	}

	@Test
	public void testCopyReaderToOutputStream() throws Exception
	{
		String data = "Penny Lane";
		Reader in = new StringReader(data);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		CharStreams.copy(in, out);
		assertEquals(data, new String(out.toByteArray()));
	}

	@Test
	public void testCopyReaderToOutputStreamWithCharset() throws Exception
	{
		String data = "Penny Lane";
		Reader in = new StringReader(data);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Charset charset = Charsets.US_ASCII;
		CharStreams.copy(in, out, charset);
		assertEquals(data, new String(out.toByteArray(), charset));
	}

	@Test
	public void testEqual() throws Exception
	{
		Reader in1 = new StringReader("Apple Records");
		Reader in2 = new StringReader("Apple Records");
		assertFalse(CharStreams.equal(in1, null));
		assertFalse(CharStreams.equal(null, in2));
		assertTrue(CharStreams.equal(null, null));
		assertTrue(CharStreams.equal(in1, in2));
		assertTrue(CharStreams.equal(in1, in2));

		in1 = new StringReader("Apple");
		in2 = new StringReader("Apple Records");
		assertFalse(CharStreams.equal(in1, in2));

		in1 = new StringReader("Apple Records");
		in2 = new StringReader("Apple");
		assertFalse(CharStreams.equal(in1, in2));
	}

	@Test
	public void testLimit() throws Exception
	{
		String data = "All You Need Is Love";
		Reader in = new StringReader(data, data);
		Reader limited = CharStreams.limit(in, data.length());
		assertEquals(data, read(limited));
	}

	@Test
	public void testReadReader() throws Exception
	{
		String data = "Blue Jay Way";
		Reader in = new StringReader(data);
		assertEquals(data, CharStreams.read(in));
	}

	@Test
	public void testReadInputStream() throws Exception
	{
		String data = "Blue Jay Way";
		InputStream in = new ByteArrayInputStream(data.getBytes());
		assertEquals(data, CharStreams.read(in));
	}

	@Test
	public void testReadInputStreamWithCharset() throws Exception
	{
		String data = "Blue Jay Way";
		Charset charset = Charsets.US_ASCII;
		InputStream in = new ByteArrayInputStream(data.getBytes(charset));
		assertEquals(data, CharStreams.read(in, charset));
	}

	@Test
	public void testReadLines() throws Exception
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
		writer.write("John");	writer.newLine();
		writer.write("Paul");	writer.newLine();
		writer.write("George");	writer.newLine();
		writer.write("Ringo");	writer.flush();
		byte[] data = out.toByteArray();
		InputStream in = new ByteArrayInputStream(data);
		List<String> beatles = Arrays.asList("John", "Paul", "George", "Ringo");
		assertEquals(beatles, CharStreams.readLines(in));
	}

	@Test
	public void testTeeArray() throws Exception
	{
		String data = "All You Need Is Love";
		Writer out1 = new StringWriter();
		Writer out2 = new StringWriter();
		Writer out3 = new StringWriter();
		CharStreams.tee(out1, out2, out3).write(data);
		assertEquals(data, out1.toString());
		assertEquals(data, out2.toString());
		assertEquals(data, out2.toString());
	}

	@Test
	public void testTeeIterable() throws Exception
	{
		String data = "All You Need Is Love";
		Writer out1 = new StringWriter();
		Writer out2 = new StringWriter();
		Writer out3 = new StringWriter();
		List<Writer> streams = Arrays.asList(out1, out2, out3);
		CharStreams.tee(streams).write(data);
		assertEquals(data, out1.toString());
		assertEquals(data, out2.toString());
		assertEquals(data, out2.toString());
	}

	@Test
	public void testConstructor() throws Exception
	{
		Class<CharStreams> c = CharStreams.class;
		assertEquals(1, c.getDeclaredConstructors().length);
		Constructor<CharStreams> constructor = c.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	private String read(Reader in) throws Exception
	{
		Writer out = new StringWriter();
		char[] buf = new char[4096];
		int len = in.read(buf);
		while (len >= 0) {
			out.write(buf, 0, len);
			len = in.read(buf);
		}
		out.flush();
		return out.toString();
	}
}
