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

package org.pitaya.charset;

import java.nio.charset.Charset;

/**
 * Charsets guaranteed to be supported by all Java platform implementations.
 *
 * @author Osman KOCAK
 */
public final class Charsets
{
	/** 7-bit ASCII (ISO646-US). */
	public static final Charset US_ASCII = Charset.forName("US-ASCII");

	/** ISO Latin Alphabet 1 (ISO-LATIN-1). */
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

	/** 8-bit UCS Transformation Format. */
	public static final Charset UTF_8 = Charset.forName("UTF-8");

	/**
	 * 16-bit UCS Transformation Format, byte order identified by an
	 * optional byte-order mark.
	 */
	public static final Charset UTF_16 = Charset.forName("UTF-16");

	/** 16-bit UCS Transformation Format, big-endian byte order. */
	public static final Charset UTF_16BE = Charset.forName("UTF-16BE");

	/** 16-bit UCS Transformation Format, little-endian byte order. */
	public static final Charset UTF_16LE = Charset.forName("UTF-16LE");

	private Charsets()
	{
		/* ... */
	}
}
