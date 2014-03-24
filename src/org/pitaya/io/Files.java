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

package org.pitaya.io;

import org.pitaya.util.Parameters;
import org.pitaya.util.XArrays;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * {@link File}s utilities.
 *
 * @author Osman KOCAK
 */
public final class Files
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
		if (!src.exists()) {
			throw new FileNotFoundException(src + " doesn't exist");
		}
		Parameters.checkCondition(!dst.exists() || dst.isDirectory()
			|| (src.isFile() && dst.isFile()));
		if (src.isDirectory()) {
			if (dst.isDirectory()) {
				cp(src, new File(dst, src.getName()));
			} else {
				mkdir(dst);
				for (File f : src.listFiles()) {
					cp(f, new File(dst, f.getName()));
				}
			}
		} else if (src.isFile()) {
			if (dst.isDirectory()) {
				cp(src, new File(dst, src.getName()));
			} else {
				InputStream in = null;
				OutputStream out = null;
				try {
					in = getReader(src);
					out = getWriter(dst);
					ByteStreams.copy(in, out);
				} finally {
					IO.close(in);
					IO.close(out);
				}
			}
		} else {
			throw new IOException(src
				+ " is neither a directory nor a regular file");
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
		InputStream in = null;
		try {
			in = getReader(src);
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
		OutputStream out = null;
		try {
			out = getWriter(dst);
			ByteStreams.copy(src, out);
		} finally {
			IO.close(out);
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
		InputStream in = null;
		try {
			in = getReader(f);
			byte[] buf = new byte[n];
			return XArrays.copyOf(buf, 0, in.read(buf));
		} finally {
			IO.close(in);
		}
	}

	/**
	 * Creates the directory specified by the given path. This method will
	 * also create any necessary parent directories. If the specified
	 * directory already exists, this method does nothing. Named after the
	 * Unix command of the same name.
	 *
	 * @param f the directory to create.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws IOException if the object at the specified path already 
	 *	exists and is a regular file, or if the requested directory can
	 *	not be created.
	 * @throws SecurityException if a security manager exists and denies 
	 *	read/write access to {@code f}.
	 */
	public static void mkdir(File f) throws IOException
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
			if (!rm(src)) {
				rm(dst);
				throw new IOException("Can't move " + src);
			}
		}
	}

	/**
	 * Deletes the given {@code File}. If {@code f} represents a directory,
	 * this method will recursively delete any nested directories or files
	 * as well. Named after the Unix command of the same name.
	 *
	 * @param f the {@code File} to delete.
	 *
	 * @return {@code true} if {@code f} has been deleted, {@code false}
	 *	otherwise.
	 * 
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws SecurityException if a security manager exists and denies 
	 *	read/write access to {@code f} or its children.
	 */
	public static boolean rm(File f)
	{
		if (f.exists()) {
			if (f.isDirectory()) {
				for (File child : f.listFiles()) {
					rm(child);
				}
			}
			return f.delete();
		}
		return false;
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
		byte[] data = read(f);
		if (data.length <= n) {
			return data;
		}
		return XArrays.copyOf(data, data.length - n, n);
	}

	/**
	 * Creates an empty file at the specified path or updates the last
	 * modification time of the file at the specified path. Named after the
	 * Unix command of the same name.
	 *
	 * @param f the file to touch.
	 *
	 * @throws IOException if an I/O error occurs during the process.
	 * @throws SecurityException if a security manager exists and denies 
	 *	read/write access to {@code f}.
	 */
	public static void touch(File f) throws IOException
	{
		if (!f.createNewFile() && !f.setLastModified(now())) {
			throw new IOException("Failed to touch " + f);
		}
	}

	private static long now()
	{
		return System.currentTimeMillis();
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
	 * Implementation note: the returned {@code InputStream} is an instance 
	 * of {@link BufferedInputStream}.
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
	public static InputStream getReader(File f) throws FileNotFoundException
	{
		return new BufferedInputStream(new FileInputStream(f));
	}

	/**
	 * Returns an {@code OutputStream} to write to the given {@code File}.
	 * Implementation note: the returned {@code OutputStream} is an instance
	 * of {@link BufferedOutputStream}.
	 *
	 * @param f the file to write to.
	 *
	 * @return an {@code OutputStream} to write to the given {@code File}.
	 *
	 * @throws NullPointerException if {@code f} is {@code null}.
	 * @throws FileNotFoundException if {@code f} exists but is a directory
	 *	rather than a regular file, or if it does not exist but cannot
	 *	be created, or if it cannot be opened for any other reason.
	 * @throws SecurityException if a security manager exists and denies 
	 *	write access to {@code f}.
	 */
	public static OutputStream getWriter(File f) throws FileNotFoundException
	{
		return new BufferedOutputStream(new FileOutputStream(f));
	}

	/**
	 * Returns an {@code OutputStream} to write to the given {@code File}
	 * with the specified mode.
	 * Implementation note: the returned {@code OutputStream} is an instance
	 * of {@link BufferedOutputStream}.
	 *
	 * @param f the file to write to.
	 * @param mode the write mode to the file.
	 *
	 * @return an {@code OutputStream} to write to the given {@code File}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws FileNotFoundException if {@code f} exists but is a directory
	 *	rather than a regular file, or if it does not exist but cannot
	 *	be created, or if it cannot be opened for any other reason.
	 * @throws SecurityException if a security manager exists and denies 
	 *	write access to {@code f}.
	 */
	public static OutputStream getWriter(File f, FileWriteMode mode)
		throws FileNotFoundException
	{
		Parameters.checkNotNull(mode);
		boolean append = mode == FileWriteMode.APPEND;
		return new BufferedOutputStream(new FileOutputStream(f, append));
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
		InputStream in = null;
		try {
			in = getReader(f);
			return ByteStreams.read(in);
		} finally {
			IO.close(in);
		}
	}

	/**
	 * Appends the given data to the end of the specified {@code File}.
	 *
	 * @param data the data to append.
	 * @param f the file to write to.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if {@code f} exists but is a directory rather 
	 *	than a regular file, or if it does not exist but cannot be 
	 *	created, or if an I/O error occurs during the process.
	 * @throws SecurityException if a security manager exists and denies 
	 *	write access to {@code f}.
	 */
	public static void append(byte[] data, File f) throws IOException
	{
		OutputStream out = null;
		try {
			out = getWriter(f, FileWriteMode.APPEND);
			out.write(data);
		} finally {
			IO.flush(out);
			IO.close(out);
		}
	}

	/**
	 * Writes the given data to the specified {@code File}.
	 *
	 * @param data the data to write.
	 * @param f the file to write to.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if {@code f} exists but is a directory rather 
	 *	than a regular file, or if it does not exist but cannot be 
	 *	created, or if an I/O error occurs during the process.
	 * @throws SecurityException if a security manager exists and denies 
	 *	write access to {@code f}.
	 */
	public static void write(byte[] data, File f) throws IOException
	{
		OutputStream out = null;
		try {
			out = getWriter(f);
			out.write(data);
		} finally {
			IO.flush(out);
			IO.close(out);
		}
	}

	private Files()
	{
		/* ... */
	}
}
