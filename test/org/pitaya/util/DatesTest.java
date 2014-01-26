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

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * {@link Dates}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class DatesTest
{
	@Test
	public void testCopy()
	{
		assertEquals(null, Dates.copy(null));

		Date now = new Date();
		assertEquals(now, Dates.copy(now));
		assertNotSame(now, Dates.copy(now));
	}
}
