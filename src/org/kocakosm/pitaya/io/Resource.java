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

import org.kocakosm.pitaya.util.Parameters;
import org.kocakosm.pitaya.util.XObjects;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * A classpath resource.
 *
 * @author Osman KOCAK
 */
public final class Resource
{
	/**
	 * Finds and returns the resource having the given name using the
	 * {@linkplain Thread#getContextClassLoader() context class loader}.
	 * Note that if the context class loader is {@code null}, the class
	 * loader that loaded this class will be used instead.
	 *
	 * @param name the resource's name.
	 *
	 * @return the requested resource.
	 *
	 * @throws NullPointerException if {@code name} is {@code null}.
	 * @throws NotFoundException if the resource can't be found.
	 */
	public static Resource find(String name)
	{
		Parameters.checkNotNull(name);
		ClassLoader loader = XObjects.firstNonNull(
			Thread.currentThread().getContextClassLoader(),
			Resource.class.getClassLoader());
		URL url = loader.getResource(name);
		if (url != null) {
			return new Resource(url);
		}
		throw new NotFoundException(name);
	}

	/**
	 * Finds and returns the resource having the given name (relative to
	 * the specified class).
	 *
	 * @param name the resource's name.
	 * @param contextClass the context class.
	 *
	 * @return the requested resource.
	 *
	 * @throws NullPointerException if {@code name} is {@code null}.
	 * @throws NotFoundException if the resource can't be found.
	 */
	public static Resource find(String name, Class<?> contextClass)
	{
		Parameters.checkNotNull(name);
		URL url = contextClass.getResource(name);
		if (url != null) {
			return new Resource(url);
		}
		throw new NotFoundException(name);
	}

	private final URL url;

	private Resource(URL url)
	{
		this.url = url;
	}

	/**
	 * Returns this resource's {@code URL}.
	 *
	 * @return this resource's {@code URL}.
	 */
	public URL getURL()
	{
		return url;
	}

	/**
	 * Copies the content of this resource to the specified stream.
	 *
	 * @param out the stream to copy to.
	 *
	 * @throws NullPointerException if {@code out} is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public void copyTo(OutputStream out) throws IOException
	{
		InputStream in = url.openStream();
		try {
			ByteStreams.copy(in, out);
		} finally {
			IO.close(in);
		}
	}

	/**
	 * Copies the content of this resource to the specified stream.
	 *
	 * @param out the stream to copy to.
	 * @param charset the charset to use.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public void copyTo(Writer out, Charset charset) throws IOException
	{
		InputStream in = url.openStream();
		try {
			CharStreams.copy(in, out, charset);
		} finally {
			IO.close(in);
		}
	}

	/**
	 * Reads and returns this resource's content as a {@code byte[]}.
	 *
	 * @return this resource's content.
	 *
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public byte[] read() throws IOException
	{
		InputStream in = url.openStream();
		try {
			return ByteStreams.read(in);
		} finally {
			IO.close(in);
		}
	}

	/**
	 * Reads and returns this resource's content as a {@code String}.
	 *
	 * @param charset the charset to use.
	 *
	 * @return this resource's content.
	 *
	 * @throws NullPointerException if {@code charset} is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public String read(Charset charset) throws IOException
	{
		InputStream in = url.openStream();
		try {
			return CharStreams.read(in, charset);
		} finally {
			IO.close(in);
		}
	}

	/**
	 * Reads and returns all this resource's lines. Note that the returned
	 * {@code List} is immutable.
	 *
	 * @param charset the charset to use.
	 *
	 * @return this resource's lines.
	 *
	 * @throws NullPointerException if {@code charset} is {@code null}.
	 * @throws IOException if an I/O error occurs during the process.
	 */
	public List<String> readLines(Charset charset) throws IOException
	{
		InputStream in = url.openStream();
		try {
			return CharStreams.readLines(in, charset);
		} finally {
			IO.close(in);
		}
	}

	@Override
	public String toString()
	{
		return url.toString();
	}

	@Override
	public int hashCode()
	{
		return url.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof Resource)) {
			return false;
		}
		final Resource r = (Resource) o;
		return url.equals(r.url);
	}

	/** Thrown to indicate that a resource can't be found. */
	public static final class NotFoundException extends RuntimeException
	{
		private static final long serialVersionUID = 75062119139042997L;

		/**
		 * Creates a new {@code ResourceNotFoundException}.
		 *
		 * @param message the error message.
		 */
		NotFoundException(String message)
		{
			super(message);
		}
	}
}
