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

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * Common I/O utilities.
 *
 * @author Osman KOCAK
 */
public final class IO
{
	/**
	 * Silently closes the given {@code Closeable}.
	 *
	 * @param stream the stream to close, may be {@code null}.
	 */
	public static void close(Closeable stream)
	{
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException ex) {
				/* Ignored... */
			}
		}
	}

	/**
	 * Silently flushes the given {@code Flushable}.
	 *
	 * @param stream the stream to flush, may be {@code null}.
	 */
	public static void flush(Flushable stream)
	{
		if (stream != null) {
			try {
				stream.flush();
			} catch (IOException ex) {
				/* Ignored... */
			}
		}
	}

	private IO()
	{
		/* ... */
	}
}
