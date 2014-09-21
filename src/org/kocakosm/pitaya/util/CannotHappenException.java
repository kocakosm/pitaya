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

package org.kocakosm.pitaya.util;

/**
 * This exception is meant to be thrown in situations that cannot happen in
 * practice. Use with parcimony.
 *
 * @author Osman KOCAK
 */
public final class CannotHappenException extends RuntimeException
{
	private static final long serialVersionUID = 3651678489121314654L;

	/** Creates a new {@code CannotHappenException}. */
	public CannotHappenException()
	{
		/* ... */
	}

	/**
	 * Creates a new {@code CannotHappenException}.
	 *
	 * @param message the error message.
	 */
	public CannotHappenException(String message)
	{
		super(message);
	}

	/**
	 * Creates a new {@code CannotHappenException}.
	 *
	 * @param cause the error that caused this one.
	 */
	public CannotHappenException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Creates a new {@code CannotHappenException}.
	 *
	 * @param message the error message.
	 * @param cause the error that caused this one.
	 */
	public CannotHappenException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
