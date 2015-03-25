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

package org.kocakosm.pitaya.io;

import org.junit.Test;

/**
 * {@link NullWriter}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class NullWriterTest
{
	@Test
	public void testNoExceptionIsThrown()
	{
		NullWriter writer = new NullWriter();
		writer.write(5);
		writer.write("I Am the Walrus");
		writer.write("I Am the Walrus".toCharArray());
		writer.write("I Am the Walrus", 0, 15);
		writer.write("I Am the Walrus".toCharArray(), 0, 15);
		writer.append('5');
		writer.append(new StringBuilder("I Am the Walrus"));
		writer.append(new StringBuilder("I Am the Walrus"), 0, 15);
		writer.flush();
		writer.close();
	}
}
