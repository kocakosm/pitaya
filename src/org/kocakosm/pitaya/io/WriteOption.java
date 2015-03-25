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

/**
 * File write options.
 *
 * @author Osman KOCAK
 */
public enum WriteOption
{
	/**
	 * Create a new file, failing if the file already exists. Can't be used
	 * together with {@link #UPDATE}, {@link #OVERWRITE} and {@link #APPEND}.
	 */
	CREATE,

	/**
	 * Update an existing file, failing if the file doesn't exist. Can't be
	 * used together with {@link #CREATE}.
	 */
	UPDATE,

	/**
	 * Overwrite the file (without first truncating it). Can't be used
	 * together with {@link #CREATE} and {@link #APPEND}.
	 */
	OVERWRITE,

	/**
	 * Append data at the end of the file. Can't be used together with
	 * {@link #CREATE} and {@link #OVERWRITE}.
	 */
	APPEND;
}
