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

import static org.junit.Assert.*;

import org.kocakosm.pitaya.charset.ASCII;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

/**
 * {@link Passwords}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class PasswordsTest
{
	@Test
	public void testGenerate()
	{
		assertTrue(ASCII.isAlphaNumeric(Passwords.generate()));
		assertEquals(10, Passwords.generate().length());
	}

	@Test
	public void testValidPassword()
	{
		String password = "password";
		byte[] hash = Passwords.hash(password);
		assertTrue(Passwords.verify(password, hash));
	}

	@Test
	public void testInvalidPassword()
	{
		byte[] hash = Passwords.hash("password");
		assertFalse(Passwords.verify("Password", hash));
		assertFalse(Passwords.verify("Password", new byte[0]));
	}

	@Test
	public void testConstructor() throws Exception
	{
		Class<Passwords> c = Passwords.class;
		assertEquals(1, c.getDeclaredConstructors().length);
		Constructor<Passwords> constructor = c.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}
}
