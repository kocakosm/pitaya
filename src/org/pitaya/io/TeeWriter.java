/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2013 Osman KOCAK <kocakosm@gmail.com>                   *
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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A decorating {@link Writer} implementation that writes all data written to it
 * to its underlying writers. Named after the Unix 'tee' command.
 *
 * @author Osman KOCAK
 */
public final class TeeWriter extends Writer
{
	private final List<Writer> writers;

	/**
	 * Creates a new {@code TeeWriter}.
	 * 
	 * @param writers the writers to write to.
	 *
	 * @throws NullPointerException if {@code writers} is {@code null} or
	 *	if it contains a {@code null} reference.
	 */
	public TeeWriter(Writer... writers)
	{
		this(Arrays.asList(writers));
	}

	/**
	 * Creates a new {@code TeeWriter}.
	 * 
	 * @param writers the writers to write to.
	 *
	 * @throws NullPointerException if {@code writers} is {@code null} or
	 *	if it returns a {@code null} reference.
	 */
	public TeeWriter(Iterable<Writer> writers)
	{
		this.writers = new ArrayList<Writer>();
		for (Writer writer : writers) {
			Parameters.checkNotNull(writer);
			this.writers.add(writer);
		}
	}

	@Override
	public TeeWriter append(char c) throws IOException
	{
		for (Writer writer : writers) {
			writer.append(c);
		}
		return this;
	}

	@Override
	public TeeWriter append(CharSequence sequence) throws IOException
	{
		for (Writer writer : writers) {
			writer.append(sequence);
		}
		return this;
	}

	@Override
	public TeeWriter append(CharSequence sequence, int start, int end) 
		throws IOException
	{
		return append(sequence.subSequence(start, end));
	}

	@Override
	public void write(int c) throws IOException
	{
		for (Writer writer : writers) {
			writer.write(c);
		}
	}

	@Override
	public void write(char[] buf) throws IOException
	{
		for (Writer writer : writers) {
			writer.write(buf);
		}
	}

	@Override
	public void write(char[] buf, int off, int len) throws IOException
	{
		for (Writer writer : writers) {
			writer.write(buf, off, len);
		}
	}

	@Override
	public void write(String str) throws IOException
	{
		for (Writer writer : writers) {
			writer.write(str);
		}
	}

	@Override
	public void write(String str, int off, int len) throws IOException
	{
		write(str.substring(off, off + len));
	}

	@Override
	public void flush() throws IOException
	{
		for (Writer writer : writers) {
			writer.flush();
		}
	}

	@Override
	public void close() throws IOException
	{
		for (Writer writer : writers) {
			writer.close();
		}
	}
}
