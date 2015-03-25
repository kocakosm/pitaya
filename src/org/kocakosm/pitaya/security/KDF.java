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

package org.kocakosm.pitaya.security;

/**
 * Key derivation function.
 *
 * @see KDFs
 *
 * @author Osman KOCAK
 */
public interface KDF
{
	/**
	 * Derives a key from the given secret and salt.
	 *
	 * @param secret the secret.
	 * @param salt the salt.
	 *
	 * @return the derived key.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	byte[] deriveKey(byte[] secret, byte[] salt);
}
