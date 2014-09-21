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

package org.kocakosm.pitaya.collection;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * Empty {@link Bag}'s tests.
 *
 * @author Osman KOCAK
 */
public final class EmptyBagTest
{
	@Test(expected = UnsupportedOperationException.class)
	public void testAdd()
	{
		Bags.emptyBag().add("Hello");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddAll()
	{
		Bags.emptyBag().addAll(Arrays.asList("Hello", "World"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear()
	{
		Bags.emptyBag().clear();
	}

	@Test
	public void testContains()
	{
		assertFalse(Bags.emptyBag().contains("Hello"));
		assertFalse(Bags.emptyBag().contains(null));
	}

	@Test
	public void testContainsAll()
	{
		assertFalse(Bags.emptyBag().containsAll(Arrays.asList("Hello")));
	}

	@Test
	public void testCount()
	{
		assertEquals(0, Bags.emptyBag().count("Hello"));
	}

	@Test
	public void testIsEmpty()
	{
		assertTrue(Bags.emptyBag().isEmpty());
	}

	@Test
	public void testIterator()
	{
		assertFalse(Bags.emptyBag().iterator().hasNext());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove()
	{
		Bags.emptyBag().remove("Hello");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAll()
	{
		Bags.emptyBag().removeAll(Arrays.asList("Hello"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRetainAll()
	{
		Bags.emptyBag().retainAll(Arrays.asList(2L));
	}

	@Test
	public void testSize()
	{
		assertEquals(0, Bags.emptyBag().size());
	}

	@Test
	public void testToArray()
	{
		assertEquals(0, Bags.emptyBag().toArray().length);
		String[] a = new String[]{"Hello", "World"};
		Bags.emptyBag().toArray(a);
		assertNull(a[0]);
		assertEquals("World", a[1]);
	}

	@Test
	public void testEqualsAndHashCode()
	{
		Bag<Integer> bag1 = Bags.emptyBag();
		Bag<Integer> bag2 = new HashBag<Integer>();

		assertTrue(bag1.equals(bag1));
		assertTrue(bag1.equals(bag2));
		assertTrue(bag1.hashCode() == bag2.hashCode());

		bag2.add(0);
		assertFalse(bag1.equals(bag2));

		bag2 = null;
		assertFalse(bag1.equals(bag2));
	}
}
