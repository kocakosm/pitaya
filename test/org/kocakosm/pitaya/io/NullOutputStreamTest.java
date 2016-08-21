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

package org.kocakosm.pitaya.io;

import org.junit.Test;

/**
 * {@link NullOutputStream}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class NullOutputStreamTest
{
	private static final byte[] DATA = {
		(byte) 0x00, (byte) 0xFF, (byte) 0x00, (byte) 0xFF
	};

	@Test
	public void testNoExceptionIsThrown()
	{
		NullOutputStream out = new NullOutputStream();
		out.write(DATA);
		out.write(5);
		out.write(DATA, 0, 2);
		out.flush();
		out.close();
	}
}
