/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2013 Osman KOCAK <kocakosm@gmail.com>                   *
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

import static org.pitaya.util.Parameters.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Parameters}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class ParametersTest
{
	@Test
	public void testCheckNotNullSuccess()
	{
		Object o = new Object();
		assertTrue(o == checkNotNull(o));
	}

	@Test(expected=NullPointerException.class)
	public void testCheckNotNullFailure()
	{
		checkNotNull(null);
	}

	@Test
	public void testCheckNotNullWithMessageSuccess()
	{
		Object o = new Object();
		assertTrue(o == checkNotNull(o, "Hello %s %s", "world", "!!"));
	}

	@Test
	public void testCheckNotNullWithMessageFailure()
	{
		String msg = null;
		try {
			checkNotNull(null, "Hello %s %s", "world", "!!");
		} catch (NullPointerException e) {
			msg = e.getMessage();
		}
		assertEquals("Hello world !!", msg);
	}

	@Test
	public void testCheckConditionSuccess()
	{
		checkCondition(true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCheckConditionFailure()
	{
		checkCondition(false);
	}

	@Test
	public void testCheckConditionWithMessageSuccess()
	{
		checkCondition(true, "Hello %s %s", "world", "!!");
	}

	@Test
	public void testCheckConditionWithMessageFailure()
	{
		String msg = null;
		try {
			checkCondition(false, "Hello %s %s", "world", "!!");
		} catch (IllegalArgumentException e) {
			msg = e.getMessage();
		}
		assertEquals("Hello world !!", msg);
	}

	@Test
	public void testCheckTypeSuccess()
	{
		String str = "";
		assertTrue(str == checkType(str, CharSequence.class));
	}

	@Test(expected=ClassCastException.class)
	public void testCheckTypeFailure()
	{
		checkType(new Object(), CharSequence.class);
	}

	@Test
	public void testCheckTypeWithMessageSuccess()
	{
		String str = "";
		assertTrue(str == checkType(str, CharSequence.class, ""));
	}

	@Test
	public void testCheckTypeWithMessageFailure()
	{
		String msg = null;
		try {
			checkType(new Object(), CharSequence.class,
				"Hello %s %s", "world", "!!");
		} catch (ClassCastException e) {
			msg = e.getMessage();
		}
		assertEquals("Hello world !!", msg);
	}
}
