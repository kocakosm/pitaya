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

package org.pitaya.util;

import java.util.Date;

/**
 * {@link Date}s utilities.
 *
 * @author Osman KOCAK
 */
public final class Dates
{
	/**
	 * Returns a copy of the given {@link Date}.
	 * 
	 * @param date the {@code Date} to copy.
	 * 
	 * @return a copy of the given {@code Date} or {@code null} if it was 
	 *	{@code null}.
	 */
	public static Date copy(Date date)
	{
		return date == null ? null : new Date(date.getTime());
	}

	private Dates()
	{
		/* ... */
	}
}
