/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2016 Osman KOCAK <kocakosm@gmail.com>                   *
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
import org.kocakosm.pitaya.util.XArrays;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * {@link TextFiles}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class TextFilesTest
{
	private static final char[] ALPHABET = "0123456789".toCharArray();

	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();

	@Test
	public void testHead() throws Exception
	{
		File test = tmp.newFolder();
		String[] lines = randomStrings(15);
		File src = createFile(test, "data", lines);
		List<String> head = TextFiles.head(src);
		assertArrayEquals(Arrays.copyOf(lines, 10), head.toArray());
	}

	@Test
	public void testHeadFirstLine() throws Exception
	{
		File test = tmp.newFolder();
		String[] lines = randomStrings(5);
		File src = createFile(test, "data", lines);
		List<String> head = TextFiles.head(src, 1);
		assertArrayEquals(Arrays.copyOf(lines, 1), head.toArray());
	}

	@Test
	public void testTail() throws Exception
	{
		File test = tmp.newFolder();
		String[] lines = randomStrings(15);
		File src = createFile(test, "data", lines);
		List<String> head = TextFiles.tail(src);
		assertArrayEquals(XArrays.copyOf(lines, 5, 10), head.toArray());
	}

	@Test
	public void testTailLastLine() throws Exception
	{
		File test = tmp.newFolder();
		String[] lines = randomStrings(5);
		File src = createFile(test, "data", lines);
		List<String> head = TextFiles.tail(src, 1);
		assertArrayEquals(XArrays.copyOf(lines, 4, 1), head.toArray());
	}

	@Test
	public void testNewReader() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", "Hello");
		assertEquals("Hello", read(TextFiles.newReader(txt)));
	}

	@Test
	public void testNewWriter() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", "Hello");
		Writer writer = TextFiles.newWriter(txt);
		writer.write("World");
		writer.flush();
		writer.close();
		assertEquals("World", read(txt));
	}

	@Test
	public void testNewWriterWithAppendOption() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", "Hello");
		Writer writer = TextFiles.newWriter(txt, WriteOption.APPEND);
		writer.write(" ");
		writer.write("World");
		writer.flush();
		writer.close();
		assertEquals("Hello World", read(txt));
	}

	@Test
	public void testNewWriterWithOverwriteOption() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", "Kello");
		Writer writer = TextFiles.newWriter(txt, WriteOption.OVERWRITE);
		writer.write("H");
		writer.flush();
		writer.close();
		assertEquals("Hello", read(txt));
	}

	@Test
	public void testNewWriterWithCreateOption() throws Exception
	{
		File test = tmp.newFolder();
		File f = new File(test, "hello.txt");
		Writer writer = TextFiles.newWriter(f, WriteOption.CREATE);
		writer.write("Hello");
		writer.flush();
		writer.close();
		assertEquals("Hello", read(f));
	}

	@Test(expected = IOException.class)
	public void testNewWriterWithCreateOptionOnExistingFile()
		throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", "Hello");
		TextFiles.newWriter(txt, WriteOption.CREATE);
	}

	@Test
	public void testNewWriterWithUpdateOption() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", "Test");
		Writer out = TextFiles.newWriter(txt, WriteOption.UPDATE);
		out.write("Hello");
		out.flush();
		out.close();
		assertEquals("Hello", read(txt));
	}

	@Test(expected = FileNotFoundException.class)
	public void testNewWriterWithUpdateOptionOnMissingFile() throws Exception
	{
		File test = tmp.newFolder();
		File f = new File(test, "hello.txt");
		TextFiles.newWriter(f, WriteOption.UPDATE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewWriterWithUpdateAndCreateOptions() throws Exception
	{
		File test = tmp.newFolder();
		File f = new File(test, "hello.txt");
		TextFiles.newWriter(f, WriteOption.UPDATE, WriteOption.CREATE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewWriterWithOverwriteAndAppendOptions() throws Exception
	{
		File test = tmp.newFolder();
		File f = new File(test, "hello.txt");
		TextFiles.newWriter(f, WriteOption.OVERWRITE, WriteOption.APPEND);
	}

	@Test
	public void testRead() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", "Hello");
		assertEquals("Hello", TextFiles.read(txt));
	}

	@Test
	public void testReadLines() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", "Hello", "World");
		assertEquals(Arrays.asList("Hello", "World"), TextFiles.readLines(txt));
	}

	@Test
	public void testConstructor() throws Exception
	{
		Class<TextFiles> c = TextFiles.class;
		assertEquals(1, c.getDeclaredConstructors().length);
		Constructor<TextFiles> constructor = c.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	private String[] randomStrings(int count)
	{
		String[] data = new String[count];
		for (int i = 0; i < count; i++) {
			data[i] = Strings.random(count, ALPHABET);
		}
		return data;
	}

	private File createFile(File dir, String name, String... lines)
		throws Exception
	{
		File f = new File(dir, name);
		BufferedWriter writer = null;
		try {
			OutputStream out = new FileOutputStream(f);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(lines[0]);
			for (int i = 1; i < lines.length; i++) {
				writer.newLine();
				writer.write(lines[i]);
			}
		} finally {
			IO.flush(writer);
			IO.close(writer);
		}
		return f;
	}

	private String read(File f) throws Exception
	{
		InputStream in = null;
		try {
			in = new FileInputStream(f);
			return CharStreams.read(in);
		} finally {
			IO.close(in);
		}
	}

	private String read(Reader in) throws Exception
	{
		try {
			return CharStreams.read(in);
		} finally {
			IO.close(in);
		}
	}
}
