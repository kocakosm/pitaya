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

import static org.kocakosm.pitaya.io.WriteOption.*;

import org.kocakosm.pitaya.collection.ImmutableSet;
import org.kocakosm.pitaya.util.Parameters;
import org.kocakosm.pitaya.util.XArrays;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Set;

/**
 * {@link File}s utilities.
 *
 * @author Osman KOCAK
 */
public final class XFiles
{
	/**
	 * Copies the contents of {@code src} to {@code dst}. If {@code dst}
	 * doesn't exist, it will be created. If it already exists, it can be
	 * either a directory or a regular file if {@code src} is also a regular
	 * file. Named after the Unix command of the same name.
	 *
	 * @param src the source {@code File}.
	 * @param dst the target {@code File}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if {@code src} is a directory and
	 *	{@code dst} is a regular file.
	 * @throws IOException if {@code src} doesn't exist or if {@code src} is
	 *	neither a regular file nor a directory or if an I/O error occurs
	 *	during the process.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to the {@code src} or write access to {@code dst}.
	 */
	public static void cp(File src, File dst) throws IOException
	{
		checkExists(src);
		Parameters.checkCondition((!dst.exists() || dst.isDirectory())
			|| (src.isFile() && dst.isFile()));
		if (src.isDirectory()) {
			copyDirectory(src, dst);
		} else if (src.isFile()) {
			copyFile(src, dst);
		} else {
			throw new IOException(
				src + " is neither a directory nor a regular file");
		}
	}

	private static void checkExists(File f) throws FileNotFoundException
	{
		if (!f.exists()) {
			throw new FileNotFoundException(f + " doesn't exist");
		}
	}

	private static void copyDirectory(File src, File dst) throws IOException
	{
		if (dst.isDirectory()) {
			cp(src, new File(dst, src.getName()));
		} else {
			mkdir(dst);
			for (File f : src.listFiles()) {
				cp(f, new File(dst, f.getName()));
			}
		}
	}

	private static void copyFile(File src, File dst) throws IOException
	{
		if (dst.isDirectory()) {
			cp(src, new File(dst, src.getName()));
		} else {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = newInputStream(src);
				out = newOutputStream(dst);
				ByteStreams.copy(in, out);
			} finally {
				IO.close(in);
				IO.close(out);
			}
		}
	}

	/**
	 * Copies the contents of the given input {@code File} to the given
	 * {@code OutputStream}. Named after the Unix command of the same name.
	 *
	 * @param src the file to copy from.
	 * @param dst the stream to write to.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if {@code src} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be opened
	 *	for reading, or if an I/O error occurs during the process.
	 * @throws SecurityException if a security manager exists denies read
	 *	access to {@code src}.
	 */
	public static void cp(File src, OutputStream dst) throws IOException
	{
		InputStream in = newInputStream(src);
		try {
			ByteStreams.copy(in, dst);
		} finally {
			IO.close(in);
		}
	}

	/**
	 * Copies the contents of the given {@code InputStream} to the given
	 * output {@code File}. Named after the Unix command of the same name.
	 *
	 * @param src the stream to read from.
	 * @param dst the file to write to.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if {@code dst} exists but is a directory rather
	 *	than a regular file, or if it does not exist but cannot be
	 *	created, or if an I/O error occurs during the process.
	 * @throws SecurityException if a security manager exists denies write
	 *	access to {@code dst}.
	 */
	public static void cp(InputStream src, File dst) throws IOException
	{
		OutputStream out = newOutputStream(dst);
		try {
			ByteStreams.copy(src, out);
		} finally {
			IO.close(out);
		}
	}

	/**
	 * Creates the directories specified by the given paths. This method
	 * will also create any necessary parent directories for a particular
	 * path. Named after the Unix command of the same name.
	 *
	 * @param paths the directories to create.
	 *
	 * @throws NullPointerException if {@code paths} is {@code null}, or if
	 *	it contains a {@code null} reference.
	 * @throws IOException if one of the specified path already exists and
	 *	represents a regular file, or if the requested directory can't
	 *	be created.
	 * @throws SecurityException if a security manager exists and denies
	 *	read/write access to one of the specified paths.
	 */
	public static void mkdir(File... paths) throws IOException
	{
		for (File path : paths) {
			createDirectory(path);
		}
	}

	private static void createDirectory(File f) throws IOException
	{
		if (f.exists()) {
			if (!f.isDirectory()) {
				throw new IOException(
					f + " exists and is not a directory");
			}
		} else if (!f.mkdirs()) {
			throw new IOException("Failed to create " + f);
		}
	}

	/**
	 * Moves a file from one path to another. Named after the Unix command
	 * of the same name.
	 *
	 * @param src the original file.
	 * @param dst the destination path.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if {@code src} is a directory and
	 *	{@code dst} is a regular file.
	 * @throws IOException if {@code src} can't be moved to {@code dst}.
	 * @throws SecurityException if a security manager exists and denies
	 *	read/write access to {@code src}/{@code dst}.
	 */
	public static void mv(File src, File dst) throws IOException
	{
		Parameters.checkNotNull(dst);
		if (!src.equals(dst)) {
			cp(src, dst);
			try {
				rm(src);
			} catch (IOException e) {
				rm(dst);
				throw new IOException("Can't move " + src, e);
			}
		}
	}

	/**
	 * Deletes the given {@code File}s. Directories will be recursively
	 * deleted. Named after the Unix command of the same name.
	 *
	 * @param files the {@code File}s to delete.
	 *
	 * @throws NullPointerException if {@code files} is {@code null} or if
	 *	it contains a {@code null} reference.
	 * @throws IOException if one of the specified {@code File}s can't be
	 *	deleted.
	 * @throws SecurityException if a security manager exists and denies
	 *	read/write access to one of the specified files or its children.
	 */
	public static void rm(File... files) throws IOException
	{
		for (File f : files) {
			if (f.exists()) {
				if (f.isDirectory()) {
					rm(f.listFiles());
				}
				if (!f.delete()) {
					throw new IOException("Can't delete " + f);
				}
			}
		}
	}

	/**
	 * Creates empty files at the specified paths or updates the last
	 * modification time of the files at the specified paths. Named after
	 * the Unix command of the same name.
	 *
	 * @param files the files to touch.
	 *
	 * @throws IOException if an I/O error occurs during the process.
	 * @throws SecurityException if a security manager exists and denies
	 *	read/write access to one of the specified files.
	 */
	public static void touch(File... files) throws IOException
	{
		long now = System.currentTimeMillis();
		for (File f : files) {
			if (!f.createNewFile() && !f.setLastModified(now)) {
				throw new IOException("Failed to touch " + f);
			}
		}
	}

	/**
	 * Reads the first (up to {@code 512}) bytes of the given {@code File}.
	 * Named after the Unix command of the same name.
	 *
	 * @param f the file to read.
	 *
	 * @return the first bytes of the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static byte[] head(File f) throws IOException
	{
		return head(f, 512);
	}

	/**
	 * Reads the first (up to {@code n}) bytes of the given {@code File}.
	 * Named after the Unix command of the same name.
	 *
	 * @param f the file to read.
	 * @param n the maximum number of bytes to read.
	 *
	 * @return the first bytes of the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IllegalArgumentException if {@code n} is negative.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static byte[] head(File f, int n) throws IOException
	{
		Parameters.checkCondition(n >= 0);
		InputStream in = newInputStream(f);
		byte[] buf = new byte[n];
		try {
			return XArrays.copyOf(buf, 0, in.read(buf));
		} finally {
			IO.close(in);
		}
	}

	/**
	 * Reads the last (up to {@code 512}) bytes of the given {@code File}.
	 * Named after the Unix command of the same name.
	 *
	 * @param f the file to read.
	 *
	 * @return the last bytes of the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static byte[] tail(File f) throws IOException
	{
		return tail(f, 512);
	}

	/**
	 * Reads the last (up to {@code n}) bytes of the given {@code File}.
	 * Named after the Unix command of the same name.
	 *
	 * @param f the file to read.
	 * @param n the maximum number of bytes to read.
	 *
	 * @return the last bytes of the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static byte[] tail(File f, int n) throws IOException
	{
		Parameters.checkCondition(n >= 0);
		RandomAccessFile file = new RandomAccessFile(f, "r");
		file.seek(file.length() - n);
		byte[] data = new byte[n];
		file.read(data);
		return data;
	}

	/**
	 * Returns whether the given {@code File}s have the same content. Two
	 * regular files are considered equal if they contain the same bytes.
	 * Two directories are considered equal if they both contain the same
	 * items where an item is either a directory or a regular file (items
	 * must have the same name and content in both directories).
	 *
	 * @param f1 the first {@code File}.
	 * @param f2 the second {@code File}.
	 *
	 * @return whether the given {@code File}s have the same content.
	 *
	 * @throws IOException if the content of the {@code File}s can't be read.
	 */
	public static boolean equals(File f1, File f2) throws IOException
	{
		if (f1 == null ^ f2 == null) {
			return false;
		}
		if (f1 != null) {
			if (f1.equals(f2)) {
				return true;
			}
			if (f1.isFile() != f2.isFile()
				|| f1.isDirectory() != f2.isDirectory())
			{
				return false;
			}
			if (f1.isFile()) {
				return Arrays.equals(read(f1), read(f2));
			}
			File[] files = f1.listFiles();
			if (files.length != f2.listFiles().length) {
				return false;
			}
			for (File f : files) {
				if (!equals(f, new File(f2, f.getName()))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the file name without its path or extension. This method is
	 * named after the 'basename' Unix command.
	 *
	 * @param f the file to get the base name.
	 *
	 * @return {@code f}'s extension-free name.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 */
	public static String getBaseName(File f)
	{
		String fileName = f.getName();
		int index = fileName.lastIndexOf('.');
		return index == -1 ? fileName : fileName.substring(0, index);
	}

	/**
	 * Returns the extension of the given file, or the empty {@code String}
	 * if the file has no extension. The returned extension does not include
	 * the leading dot.
	 *
	 * @param f the file to get the extension.
	 *
	 * @return {@code f}'s extension.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 */
	public static String getExtension(File f)
	{
		String fileName = f.getName();
		int index = fileName.lastIndexOf('.');
		return index == -1 ? "" : fileName.substring(index + 1);
	}

	/**
	 * Returns an {@code InputStream} to read from the given {@code File}.
	 *
	 * @param f the file to read from.
	 *
	 * @return an {@code InputStream} to read from the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws FileNotFoundException if {@code f} doesn't exist, or if it is
	 *	a directory rather than a regular file, or if it can't be opened
	 *	for reading.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static BufferedInputStream newInputStream(File f)
		throws FileNotFoundException
	{
		return new BufferedInputStream(new FileInputStream(f));
	}

	/**
	 * Returns an {@code OutputStream} to write to the given {@code File}.
	 *
	 * @param f the file to write to.
	 * @param options the write options.
	 *
	 * @return an {@code OutputStream} to write to the given {@code File}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IllegalArgumentException if incompatible options are given.
	 * @throws FileNotFoundException if {@code f} exists but is a directory
	 *	rather than a regular file, or if it does not exist but cannot
	 *	be created, or if it cannot be opened for any other reason.
	 * @throws IOException if the {@link WriteOption#CREATE_NEW} option is
	 *	given and the specified file already exists.
	 * @throws SecurityException if a security manager exists and denies
	 *	write access to {@code f}.
	 */
	public static BufferedOutputStream newOutputStream(File f, WriteOption... options)
		throws IOException
	{
		Set<WriteOption> opts = ImmutableSet.of(options);
		checkWriteOptions(opts);
		checkFileExistence(f, opts);
		return new BufferedOutputStream(newOutputStream(f, opts));
	}

	private static void checkWriteOptions(Set<WriteOption> options)
	{
		if ((options.contains(APPEND) && options.contains(OVERWRITE))
			|| (options.contains(CREATE) && options.size() > 1))
		{
			throw new IllegalArgumentException(
				"Incompatible write options: " + options);
		}
	}

	private static void checkFileExistence(File f, Set<WriteOption> options)
		throws IOException
	{
		if (options.contains(CREATE) && f.exists()) {
			throw new IOException(
				CREATE + ": " + f + " already exists");
		}
		if (options.contains(UPDATE) && !f.exists()) {
			throw new FileNotFoundException(
				UPDATE + ": " + f + " dosn't exist");
		}
	}

	private static OutputStream newOutputStream(File f, Set<WriteOption> options)
		throws FileNotFoundException
	{
		if (options.contains(APPEND)) {
			return new FileOutputStream(f, true);
		}
		if (options.contains(OVERWRITE)) {
			RandomAccessFile file = new RandomAccessFile(f, "rw");
			FileChannel channel = file.getChannel();
			return Channels.newOutputStream(channel);
		}
		return new FileOutputStream(f);
	}

	/**
	 * Reads the content of the given {@code File}.
	 *
	 * @param f the file to read.
	 *
	 * @return the file's content as a {@code byte[]}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if {@code f} does not exist, or if it is a
	 *	directory rather than a regular file, or if it can't be read.
	 * @throws SecurityException if a security manager exists and denies
	 *	read access to {@code f}.
	 */
	public static byte[] read(File f) throws IOException
	{
		InputStream in = newInputStream(f);
		try {
			return ByteStreams.read(in);
		} finally {
			IO.close(in);
		}
	}

	private XFiles()
	{
		/* ... */
	}
}
