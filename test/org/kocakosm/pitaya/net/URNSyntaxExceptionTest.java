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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link URNSyntaxException}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class URNSyntaxExceptionTest
{
	@Test
	public void testMessage()
	{
		String message = "Error!";
		Exception e = new URNSyntaxException(message);
		assertEquals(message, e.getMessage());
	}

	@Test
	public void testCause()
	{
		Exception cause = new Exception("Error!");
		Exception e = new URNSyntaxException(cause);
		assertSame(cause, e.getCause());
	}

	@Test
	public void testMessageAndCause()
	{
		String message = "Error!";
		Exception cause = new Exception();
		Exception e = new URNSyntaxException(message, cause);
		assertEquals(message, e.getMessage());
		assertSame(cause, e.getCause());
	}
}
