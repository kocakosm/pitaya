/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012 Osman KOCAK <kocakosm@gmail.com>                        *
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * {@link UId}'s tests.
 *
 * @author Osman KOCAK
 */
public final class UIdTest
{
	@Test
	public void testSerialisation()
	{
		UId id1 = UId.create();
		UId id2 = UId.valueOf(id1.toString());
		UId id3 = UId.valueOf(id2.toByteArray());

		assertEquals(id1, id2);
		assertEquals(id2, id3);
		assertEquals(id1.hashCode(), id2.hashCode());
		assertEquals(id2.hashCode(), id3.hashCode());
	}
}
