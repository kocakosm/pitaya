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

package org.pitaya.collection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * {@link HashBag}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class HashBagTest
{
	@Test
	public void testCollectionContructor()
	{
		Collection<String> collection = Arrays.asList("Hello", "World");
		Bag<String> bag = new HashBag<String>(collection);
		assertEquals(2, bag.size());
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
	}

	@Test
	public void testIterableContructor()
	{
		Iterable<String> iterable = Arrays.asList("Hello", "World");
		Bag<String> bag = new HashBag<String>(iterable);
		assertEquals(2, bag.size());
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
	}

	@Test
	public void testArrayContructor()
	{
		Bag<String> bag = new HashBag<String>("Hello", "World");
		assertEquals(2, bag.size());
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
	}

	@Test
	public void testAdd()
	{
		Bag<String> bag = new HashBag<String>();
		bag.add("Hello");
		assertTrue(bag.contains("Hello"));
	}

	@Test
	public void testAddAll()
	{
		Bag<String> bag = new HashBag<String>();
		bag.addAll(Arrays.asList("Hello", "World"));
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
	}

	@Test
	public void testClear()
	{
		Bag<String> bag = new HashBag<String>("Hello", "World");
		assertFalse(bag.isEmpty());
		bag.clear();
		assertTrue(bag.isEmpty());
	}

	@Test
	public void testContainsAll()
	{
		Bag<String> bag = new HashBag<String>("Hello", "World");
		assertTrue(bag.containsAll(bag));
		assertTrue(bag.containsAll(Arrays.asList("Hello")));
	}

	@Test
	public void testCount()
	{
		Bag<String> bag = new HashBag<String>();
		assertEquals(0, bag.count("Hello"));
		bag.add("World");
		assertEquals(0, bag.count("Hello"));
		bag.add("Hello");
		assertEquals(1, bag.count("Hello"));
		bag.add("World");
		assertEquals(1, bag.count("Hello"));
		bag.add("Hello");
		assertEquals(2, bag.count("Hello"));
	}

	@Test
	public void testIsEmpty()
	{
		assertTrue(new HashBag<Integer>().isEmpty());
	}

	@Test
	public void testIterator()
	{
		assertFalse(new HashBag<Integer>().iterator().hasNext());
		Bag<Long> bag = new HashBag<Long>(Arrays.asList(1L, 2L, 1L, 2L, 3L));
		List<Long> result = new ArrayList<Long>();
		Iterator<Long> iterator = bag.iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		assertTrue(result.containsAll(bag));
	}

	@Test
	public void testRemove()
	{
		Bag<Long> bag = new HashBag<Long>(Arrays.asList(1L, 2L, 1L));
		bag.remove(1L);
		assertTrue(bag.contains(1L));
		bag.remove(1L);
		assertFalse(bag.contains(1L));
		bag.remove(2L);
		assertFalse(bag.contains(2L));
	}

	@Test
	public void testRemoveAll()
	{
		Bag<Long> bag = new HashBag<Long>(Arrays.asList(1L, 2L, 1L, 3L));
		bag.removeAll(Arrays.asList(1L, 2L, 5L));
		assertFalse(bag.contains(1L));
		assertFalse(bag.contains(2L));
		assertTrue(bag.contains(3L));
	}

	@Test
	public void testRetainAll()
	{
		Bag<Long> bag = new HashBag<Long>(Arrays.asList(1L, 2L, 1L, 3L));
		bag.retainAll(Arrays.asList(2L));
		assertTrue(bag.contains(2L));
		assertFalse(bag.contains(1L));
		assertFalse(bag.contains(3L));
	}

	@Test
	public void testSize()
	{
		Bag<String> bag = new HashBag<String>();
		assertEquals(0, bag.size());
		bag.add("Hello");
		assertEquals(1, bag.size());
		bag.add("World");
		assertEquals(2, bag.size());
		bag.add("Bye");
		assertEquals(3, bag.size());
		bag.add("World");
		assertEquals(4, bag.size());
	}

	@Test
	public void testToArray()
	{
		assertEquals(0, new HashBag<String>().toArray().length);
		String[] in = new String[] {"Hello", "World"};
		String[] out = new HashBag<String>(in).toArray(new String[0]);
		Arrays.sort(out);
		assertArrayEquals(in, out);
	}

	@Test
	public void testEqualsAndHashCode()
	{
		Bag<Integer> bag1 = new HashBag<Integer>(1, 2, 3, 1, 2, 3);
		Bag<Integer> bag2 = new HashBag<Integer>(3, 1, 2, 3, 1, 2);
		Bag<Integer> bag3 = new HashBag<Integer>(2, 3, 1, 2, 3, 1);

		assertTrue(bag1.equals(bag1));
		assertTrue(bag1.equals(bag2));
		assertTrue(bag2.equals(bag1));
		assertTrue(bag2.equals(bag3));
		assertTrue(bag3.equals(bag2));
		assertTrue(bag1.equals(bag3));
		assertTrue(bag3.equals(bag1));
		assertTrue(bag1.hashCode() == bag2.hashCode());
		assertTrue(bag2.hashCode() == bag3.hashCode());
	}
}
