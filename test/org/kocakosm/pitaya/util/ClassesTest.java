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

package org.kocakosm.pitaya.util;

import static org.kocakosm.pitaya.util.Classes.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * {@link Classes}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class ClassesTest
{
	@Test
	public void testGetQualifiedName()
	{
		assertEquals("int", getQualifiedName(int.class));
		assertEquals("byte[][]", getQualifiedName(byte[][].class));
		assertEquals("java.lang.Byte", getQualifiedName(Byte.class));
		assertEquals("java.lang.Byte[]", getQualifiedName(Byte[].class));
	}

	@Test
	public void testGetShortName()
	{
		assertEquals("ClassesTest", getShortName(getClass()));
		assertEquals("TestClassB", getShortName(TestClassA.TestClassB.class));

	}

	@Test
	public void testGetPackageName()
	{
		assertEquals("java.lang", getPackageName(String.class));
		assertEquals("org.junit", getPackageName(Test.class));

	}

	@Test
	public void testGetSuperTypes()
	{
		assertEquals(asSet(), getSuperTypes(A.class));
		assertEquals(asSet(A.class), getSuperTypes(B.class));
		assertEquals(asSet(A.class, B.class), getSuperTypes(C.class));
		assertEquals(asSet(D.class, B.class, A.class), getSuperTypes(E.class));
		assertEquals(asSet(Object.class), getSuperTypes(F.class));
		assertEquals(asSet(Object.class, F.class), getSuperTypes(G.class));
		assertEquals(asSet(Object.class, G.class, F.class), getSuperTypes(H.class));
		assertEquals(asSet(Object.class, H.class, G.class, F.class, D.class, A.class), getSuperTypes(I.class));
	}

	@Test
	public void testGetCommonSuperTypes()
	{
		assertEquals(asSet(A.class), getCommonSuperTypes(B.class));
		assertEquals(asSet(), getCommonSuperTypes(A.class, I.class));
		assertEquals(asSet(A.class), getCommonSuperTypes(C.class, E.class, I.class));
		assertEquals(asSet(A.class, D.class), getCommonSuperTypes(E.class, I.class));
	}

	private <T> Set<T> asSet(T... objects)
	{
		return new HashSet<T>(Arrays.asList(objects));
	}

	private interface A {}
	private interface B extends A {}
	private interface C extends A, B {}
	private interface D {}
	private interface E extends D, B {}
	private class F {}
	private class G extends F {}
	private class H extends G {}
	private class I extends H implements D, A {}
	private class TestClassA  { private class TestClassB {} }
}
