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

package org.kocakosm.pitaya.io;

import static org.junit.Assert.*;

import org.kocakosm.pitaya.charset.ASCII;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * {@link Files}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class FilesTest
{
	private static final Random PRNG = new Random();

	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();

	@Test
	public void testCopyFile() throws Exception
	{
		File test = tmp.newFolder();
		File src = createFile(test, "hello.txt", ascii("Hello"));
		File dst = new File(test, "copy-of-hello.txt");
		Files.cp(src, dst);
		assertEquals(src, dst);
	}

	@Test
	public void testCopyFileToFile() throws Exception
	{
		File test = tmp.newFolder();
		File src = createFile(test, "hello.txt", ascii("Hello"));
		File dst = createFile(test, "world.txt", ascii("World"));
		Files.cp(src, dst);
		assertEquals(src, dst);
	}

	@Test
	public void testCopyFileToDirectory() throws Exception
	{
		File test = tmp.newFolder();
		File src = createFile(test, "hello.txt", ascii("Hello"));
		File dst = new File(test, "out");
		dst.mkdirs();
		Files.cp(src, dst);
		assertEquals(src, new File(dst, src.getName()));
	}

	@Test
	public void testCopyDirectory() throws Exception
	{
		File test = tmp.newFolder();
		File src = new File(test, "in");
		src.mkdirs();
		createFile(src, "hello.txt", ascii("Hello"));
		createFile(src, "world.txt", ascii("World"));
		File dst = new File(test, "out");
		Files.cp(src, dst);
		assertEquals(src, dst);
	}

	@Test
	public void testCopyDirectoryToDirectory() throws Exception
	{
		File test = tmp.newFolder();
		File src = new File(test, "in");
		src.mkdirs();
		createFile(src, "hello.txt", ascii("Hello"));
		createFile(src, "world.txt", ascii("World"));
		File dst = new File(test, "out");
		dst.mkdirs();
		Files.cp(src, dst);
		assertEquals(src, new File(dst, "in"));
	}

	@Test
	public void testCopyFileToStream() throws Exception
	{
		File test = tmp.newFolder();
		byte[] data = ascii("Hello");
		File src = createFile(test, "hello.txt", data);
		ByteArrayOutputStream dst = new ByteArrayOutputStream();
		Files.cp(src, dst);
		assertArrayEquals(data, dst.toByteArray());
	}

	@Test
	public void testCopyStreamToFile() throws Exception
	{
		File test = tmp.newFolder();
		byte[] data = ascii("Hello");
		File dst = new File(test, "hello.txt");
		Files.cp(new ByteArrayInputStream(data), dst);
		assertArrayEquals(data, read(dst));
	}

	@Test
	public void testEquals() throws Exception
	{
		byte[] data = ascii("Hello");
		File test1 = tmp.newFolder();
		File f1 = createFile(test1, "f1.txt", data);
		File f2 = createFile(test1, "f2.txt", data);
		File test2 = tmp.newFolder();
		createFile(test2, "f1.txt", data);
		createFile(test2, "f2.txt", data);

		assertTrue(Files.equals(f1, f1));
		assertFalse(Files.equals(f1, new File("f1.txt")));
		assertFalse(Files.equals(new File("f1.txt"), f1));
		assertFalse(Files.equals(f1, null));
		assertFalse(Files.equals(null, f1));
		assertTrue(Files.equals(null, null));
		assertTrue(Files.equals(f1, f2));
		assertFalse(Files.equals(f1, test1));
		assertFalse(Files.equals(test1, f1));
		assertTrue(Files.equals(test1, test2));
		createFile(test1, "f3.txt", data);
		assertFalse(Files.equals(test1, test2));
	}

	@Test
	public void testHead() throws Exception
	{
		File test = tmp.newFolder();
		byte[] data = randomData(PRNG.nextInt(512) + 512);
		File src = createFile(test, "data", data);
		byte[] head = Files.head(src);
		assertArrayEquals(Arrays.copyOf(data, 512), head);
	}

	@Test
	public void testCreateDirectory() throws Exception
	{
		File test = tmp.newFolder();
		File in1 = new File(test, "yellow");
		File in2 = new File(in1, "submarine");
		Files.mkdir(in2);
		assertTrue(in2.exists());
		assertTrue(in2.isDirectory());
		Assert.assertEquals(in2.getParentFile(), in1);
	}

	@Test(expected = IOException.class)
	public void testCreateDirectoryOnFile() throws Exception
	{
		File test = tmp.newFolder();
		Files.mkdir(createFile(test, "hello.txt", ascii("Hello")));
	}

	@Test
	public void testMoveFile() throws Exception
	{
		File test = tmp.newFolder();
		File src = createFile(test, "hello.txt", ascii("Hello"));
		File dst = new File(test, "hi.txt");
		Files.mv(src, dst);
		assertFalse(src.exists());
		assertTrue(dst.exists());
		src = createFile(test, "hello.txt", ascii("Hello"));
		assertEquals(src, dst);
	}

	@Test
	public void testMoveFileToFile() throws Exception
	{
		File test = tmp.newFolder();
		File src = createFile(test, "hello.txt", ascii("Hello"));
		File dst = createFile(test, "world.txt", ascii("World"));
		Files.mv(src, dst);
		assertFalse(src.exists());
		assertTrue(dst.exists());
		src = createFile(test, "hello.txt", ascii("Hello"));
		assertEquals(src, dst);
	}

	@Test
	public void testMoveFileToDirectory() throws Exception
	{
		File test = tmp.newFolder();
		File src = createFile(test, "hello.txt", ascii("Hello"));
		File dst = new File(test, "out");
		dst.mkdirs();
		Files.mv(src, dst);
		assertFalse(src.exists());
		assertTrue(new File(dst, "hello.txt").exists());
		src = createFile(test, "hello.txt", ascii("Hello"));
		assertEquals(src, new File(dst, src.getName()));
	}

	@Test
	public void testMoveDirectory() throws Exception
	{
		File test = tmp.newFolder();
		File src = new File(test, "in");
		src.mkdirs();
		createFile(src, "hello.txt", ascii("Hello"));
		createFile(src, "world.txt", ascii("World"));
		File dst = new File(test, "out");

		Files.mv(src, dst);
		assertFalse(src.exists());
		assertTrue(dst.exists());

		src.mkdirs();
		createFile(src, "hello.txt", ascii("Hello"));
		createFile(src, "world.txt", ascii("World"));
		assertEquals(src, dst);
	}

	@Test
	public void testMoveDirectoryToDirectory() throws Exception
	{
		File test = tmp.newFolder();
		File src = new File(test, "in");
		src.mkdirs();
		createFile(src, "hello.txt", ascii("Hello"));
		File dst = new File(test, "out");
		dst.mkdirs();

		Files.mv(src, dst);
		assertFalse(src.exists());
		assertTrue(new File(dst, "in").exists());

		src = new File(test, "in");
		src.mkdirs();
		createFile(src, "hello.txt", ascii("Hello"));
		assertEquals(src, new File(dst, "in"));
	}

	@Test
	public void testDeleteFile() throws Exception
	{
		File test = tmp.newFolder();
		File src = createFile(test, "42.txt", ascii("42"));
		assertTrue(src.exists());
		Files.rm(src);
		assertFalse(src.exists());
	}

	@Test
	public void testDeleteDirectory() throws Exception
	{
		File test = tmp.newFolder();
		File src = new File(test, "in");
		src.mkdirs();
		createFile(src, "hello.txt", ascii("Hello"));
		createFile(src, "world.txt", ascii("World"));
		assertTrue(src.exists());
		assertTrue(src.listFiles().length > 0);
		Files.rm(src);
		assertFalse(src.exists());
	}

	@Test
	public void testTail() throws Exception
	{
		File test = tmp.newFolder();
		byte[] data = randomData(PRNG.nextInt(512) + 512);
		int len = data.length;
		File src = createFile(test, "data", data);
		byte[] tail = Files.tail(src);
		assertArrayEquals(Arrays.copyOfRange(data, len - 512, len), tail);
	}

	@Test
	public void testTouchNewFile() throws Exception
	{
		File test = tmp.newFolder();
		File f = new File(test, "test");
		assertFalse(f.exists());
		Files.touch(f);
		assertTrue(f.exists());
		assertTrue(f.isFile());
	}

	@Test
	public void testTouchExistingFile() throws Exception
	{
		File test = tmp.newFolder();
		File f = createFile(test, "hello.txt", ascii("Hello"));
		long creation = f.lastModified();
		assertTrue(f.exists());
		assertTrue(f.isFile());
		Thread.sleep(1001L);
		Files.touch(f);
		assertTrue(f.exists());
		assertTrue(f.isFile());
		assertNotEquals(creation, f.lastModified());
	}

	@Test
	public void testTouchDirectory() throws Exception
	{
		File test = tmp.newFolder();
		File src = new File(test, "in");
		src.mkdirs();
		long creation = src.lastModified();
		Thread.sleep(1001L);
		assertTrue(src.exists());
		assertTrue(src.isDirectory());
		Files.touch(src);
		assertTrue(src.exists());
		assertTrue(src.isDirectory());
		assertNotEquals(creation, src.lastModified());
	}

	@Test
	public void testGetBaseName() throws Exception
	{
		File test = tmp.newFolder();
		File hello = createFile(test, "hello.txt", ascii("Hello"));
		File world = createFile(test, "world.txt", ascii("World"));
		File hi = createFile(test, "hello.xml", ascii("<hello></hello>"));
		File blob = createFile(test, "blob", randomData(16));
		Assert.assertEquals("hello", Files.getBaseName(hello));
		Assert.assertEquals("world", Files.getBaseName(world));
		Assert.assertEquals("hello", Files.getBaseName(hi));
		Assert.assertEquals("blob", Files.getBaseName(blob));
	}

	@Test
	public void testGetExtension() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", ascii("Hello"));
		File xml = createFile(test, "hello.xml", ascii("<hello></hello>"));
		File blob = createFile(test, "blob", randomData(16));
		Assert.assertEquals("txt", Files.getExtension(txt));
		Assert.assertEquals("xml", Files.getExtension(xml));
		Assert.assertEquals("", Files.getExtension(blob));
	}

	@Test
	public void testNewInputStream() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", ascii("Hello"));
		assertArrayEquals(ascii("Hello"), read(Files.newInputStream(txt)));
	}

	@Test
	public void testNewOutputStream() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", ascii("Hello"));
		OutputStream out = Files.newOutputStream(txt);
		out.write(ascii("World"));
		out.flush();
		out.close();
		assertArrayEquals(ascii("World"), read(txt));
	}

	@Test
	public void testNewOutputStreamWithAppendOption() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", ascii("Hello"));
		OutputStream out = Files.newOutputStream(txt, WriteOption.APPEND);
		out.write(ascii(" "));
		out.write(ascii("World"));
		out.flush();
		out.close();
		assertArrayEquals(ascii("Hello World"), read(txt));
	}

	@Test
	public void testNewOutputStreamWithOverwriteOption() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", ascii("Kello"));
		OutputStream out = Files.newOutputStream(txt, WriteOption.OVERWRITE);
		out.write(ascii("H"));
		out.flush();
		out.close();
		assertArrayEquals(ascii("Hello"), read(txt));
	}

	@Test
	public void testNewOutputStreamWithCreateOption() throws Exception
	{
		File test = tmp.newFolder();
		File f = new File(test, "hello.txt");
		OutputStream out = Files.newOutputStream(f, WriteOption.CREATE);
		out.write(ascii("Hello"));
		out.flush();
		out.close();
		assertArrayEquals(ascii("Hello"), read(f));
	}

	@Test(expected = IOException.class)
	public void testNewOutputStreamWithCreateOptionOnExistingFile()
		throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", ascii("Hello"));
		Files.newOutputStream(txt, WriteOption.CREATE);
	}

	@Test
	public void testNewOutputStreamWithUpdateOption() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", ascii("Test"));
		OutputStream out = Files.newOutputStream(txt, WriteOption.UPDATE);
		out.write(ascii("Hello"));
		out.flush();
		out.close();
		assertArrayEquals(ascii("Hello"), read(txt));
	}

	@Test(expected = FileNotFoundException.class)
	public void testNewOutputStreamWithUpdateOptionOnMissingFile()
		throws Exception
	{
		File test = tmp.newFolder();
		File f = new File(test, "hello.txt");
		Files.newOutputStream(f, WriteOption.UPDATE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewOutputStreamWithUpdateAndCreateOptions()
		throws Exception
	{
		File test = tmp.newFolder();
		File f = new File(test, "hello.txt");
		Files.newOutputStream(f, WriteOption.UPDATE, WriteOption.CREATE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewOutputStreamWithOverwriteAndAppendOptions()
		throws Exception
	{
		File test = tmp.newFolder();
		File f = new File(test, "hello.txt");
		Files.newOutputStream(f, WriteOption.OVERWRITE, WriteOption.APPEND);
	}

	@Test
	public void testRead() throws Exception
	{
		File test = tmp.newFolder();
		File txt = createFile(test, "hello.txt", ascii("Hello"));
		assertArrayEquals(ascii("Hello"), Files.read(txt));
	}

	private byte[] randomData(int len)
	{
		byte[] data = new byte[len];
		PRNG.nextBytes(data);
		return data;
	}

	private File createFile(File dir, String name, byte[] data)
		throws Exception
	{
		File f = new File(dir, name);
		write(data, f);
		return f;
	}

	private void assertEquals(File f1, File f2) throws Exception
	{
		assertFalse(f1.isFile() ^ f2.isFile());
		assertFalse(f1.isDirectory() ^ f2.isDirectory());
		if (f1.isFile()) {
			assertArrayEquals(read(f1), read(f2));
		} else {
			for (File f : f1.listFiles()) {
				assertEquals(f, new File(f2, f.getName()));
			}
			for (File f : f2.listFiles()) {
				assertEquals(f, new File(f1, f.getName()));
			}
		}
	}

	private byte[] read(File f) throws Exception
	{
		InputStream in = null;
		try {
			in = new FileInputStream(f);
			return ByteStreams.read(in);
		} finally {
			IO.close(in);
		}
	}

	private byte[] read(InputStream in) throws Exception
	{
		try {
			return ByteStreams.read(in);
		} finally {
			IO.close(in);
		}
	}

	private void write(byte[] data, File f) throws Exception
	{
		OutputStream out = null;
		try {
			out = new FileOutputStream(f);
			out.write(data);
		} finally {
			IO.flush(out);
			IO.close(out);
		}
	}

	private byte[] ascii(String data)
	{
		return ASCII.encode(data);
	}
}
