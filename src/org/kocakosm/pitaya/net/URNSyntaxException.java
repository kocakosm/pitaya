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

package org.kocakosm.pitaya.net;

/**
 * Thrown to indicate that a {@code String} could not be parsed to a {@code URN}
 * reference.
 *
 * @see URN
 *
 * @author Osman KOCAK
 */
public final class URNSyntaxException extends Exception
{
	private static final long serialVersionUID = 106807221764107977L;

	/**
	 * Creates a new {@code URNSyntaxException}.
	 *
	 * @param message the error message.
	 */
	public URNSyntaxException(String message)
	{
		super(message);
	}

	/**
	 * Creates a new {@code URNSyntaxException}.
	 *
	 * @param cause the error that caused this one.
	 */
	public URNSyntaxException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Creates a new {@code URNSyntaxException}.
	 *
	 * @param message the error message.
	 * @param cause the error that caused this one.
	 */
	public URNSyntaxException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
