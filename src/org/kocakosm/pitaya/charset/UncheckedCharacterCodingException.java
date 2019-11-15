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

package org.kocakosm.pitaya.charset;

import java.nio.charset.CharacterCodingException;

/**
 * Wraps a {@code CharacterCodingException} with an unchecked exception.
 *
 * @since 0.5
 *
 * @author Osman KOCAK
 */
public final class UncheckedCharacterCodingException extends RuntimeException
{
	private static final long serialVersionUID = 4625630151936291612L;

	/**
	 * Creates a new {@code UncheckedCharacterCodingException}.
	 *
	 * @param cause the {@code CharacterCodingException}.
	 *
	 * @throws NullPointerException if {@code cause} is {@code null}.
	 */
	public UncheckedCharacterCodingException(CharacterCodingException cause)
	{
		super(checkNotNull(cause));
	}

	@Override
	public CharacterCodingException getCause()
	{
		return (CharacterCodingException) super.getCause();
	}

	private static <T> T checkNotNull(T ref)
	{
		if (ref == null) {
			throw new NullPointerException();
		}
		return ref;
	}
}
