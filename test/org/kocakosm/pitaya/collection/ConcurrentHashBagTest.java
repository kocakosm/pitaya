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

package org.kocakosm.pitaya.collection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * {@link ConcurrentHashBag}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ConcurrentHashBagTest
{
	@Test
	public void testCollectionContructor()
	{
		Collection<String> collection = Arrays.asList("Hello", "World");
		Bag<String> bag = new ConcurrentHashBag<String>(collection);
		assertEquals(2, bag.size());
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
	}

	@Test
	public void testIterableContructor()
	{
		Iterable<String> iterable = Arrays.asList("Hello", "World");
		Bag<String> bag = new ConcurrentHashBag<String>(iterable);
		assertEquals(2, bag.size());
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
	}

	@Test
	public void testIteratorContructor()
	{
		Collection<String> collection = Arrays.asList("Hello", "World");
		Bag<String> bag = new ConcurrentHashBag<String>(collection.iterator());
		assertEquals(2, bag.size());
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
	}

	@Test
	public void testArrayContructor()
	{
		Bag<String> bag = new ConcurrentHashBag<String>("Hello", "World");
		assertEquals(2, bag.size());
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
	}

	@Test
	public void testAdd()
	{
		Bag<String> bag = new ConcurrentHashBag<String>();
		bag.add("Hello");
		assertTrue(bag.contains("Hello"));
	}

	@Test
	public void testAddAll()
	{
		Bag<String> bag = new ConcurrentHashBag<String>();
		assertTrue(bag.addAll(Arrays.asList("Hello", "World")));
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
	}

	@Test
	public void testAddAllAbsent()
	{
		ConcurrentBag<Long> bag = new ConcurrentHashBag<Long>(1L, 2L);
		assertEquals(bag.addAllAbsent(Arrays.asList(1L, 2L, 3L, 4L)), 2);
		assertEquals(bag.count(1L), 1);
		assertEquals(bag.count(2L), 1);
		assertEquals(bag.count(3L), 1);
		assertEquals(bag.count(4L), 1);
	}

	@Test
	public void testAddIfAbsent()
	{
		ConcurrentBag<Long> bag = new ConcurrentHashBag<Long>(1L, 2L);
		assertFalse(bag.addIfAbsent(1L));
		assertFalse(bag.addIfAbsent(2L));
		assertTrue(bag.addIfAbsent(3L));
		assertTrue(bag.addIfAbsent(4L));
		assertEquals(bag.count(1L), 1);
		assertEquals(bag.count(2L), 1);
		assertEquals(bag.count(3L), 1);
		assertEquals(bag.count(4L), 1);
	}

	@Test
	public void testClear()
	{
		Bag<String> bag = new ConcurrentHashBag<String>("Hello", "World");
		assertFalse(bag.isEmpty());
		bag.clear();
		assertTrue(bag.isEmpty());
	}

	@Test
	public void testContains()
	{
		Bag<String> bag = new ConcurrentHashBag<String>("Hello", "World");
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
		assertFalse(bag.contains("Bye"));
	}

	@Test
	public void testContainsAll()
	{
		Bag<String> bag = new ConcurrentHashBag<String>("Hello", "World");
		assertTrue(bag.containsAll(bag));
		assertTrue(bag.containsAll(Arrays.asList("Hello")));
	}

	@Test
	public void testCount()
	{
		Bag<String> bag = new ConcurrentHashBag<String>();
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
		assertTrue(new ConcurrentHashBag<Integer>().isEmpty());
		assertFalse(new HashBag<String>("Hello").isEmpty());
	}

	@Test
	public void testIterator()
	{
		assertFalse(new ConcurrentHashBag<Integer>().iterator().hasNext());
		Bag<Long> bag = new ConcurrentHashBag<Long>(1L, 2L, 1L, 2L, 3L);
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
		Bag<Long> bag = new ConcurrentHashBag<Long>(1L, 2L, 1L);
		assertFalse(bag.remove(5L));
		assertTrue(bag.remove(1L));
		assertTrue(bag.contains(1L));
		assertTrue(bag.remove(1L));
		assertFalse(bag.contains(1L));
		assertTrue(bag.remove(2L));
		assertFalse(bag.contains(2L));
	}

	@Test
	public void testRemoveAll()
	{
		assertFalse(new ConcurrentHashBag<Long>(1L, 5L, 1L).removeAll(Arrays.asList(2L)));
		Bag<Long> bag = new ConcurrentHashBag<Long>(1L, 2L, 1L, 3L);
		assertTrue(bag.removeAll(Arrays.asList(1L, 2L, 5L)));
		assertFalse(bag.contains(1L));
		assertFalse(bag.contains(2L));
		assertTrue(bag.contains(3L));
	}

	@Test
	public void testRetainAll()
	{
		assertFalse(new ConcurrentHashBag<Long>(2L, 2L).retainAll(Arrays.asList(2L)));
		Bag<Long> bag = new ConcurrentHashBag<Long>(1L, 2L, 1L, 3L);
		assertTrue(bag.retainAll(Arrays.asList(2L)));
		assertTrue(bag.contains(2L));
		assertFalse(bag.contains(1L));
		assertFalse(bag.contains(3L));
	}

	@Test
	public void testSize()
	{
		Bag<String> bag = new ConcurrentHashBag<String>();
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
		assertEquals(0, new ConcurrentHashBag<String>().toArray().length);
		String[] in = new String[]{"Hello", "World"};
		String[] out = new ConcurrentHashBag<String>(in).toArray(new String[0]);
		Arrays.sort(out);
		assertArrayEquals(in, out);
	}

	@Test
	public void testEqualsAndHashCode()
	{
		Bag<Integer> bag1 = new ConcurrentHashBag<Integer>(1, 2, 3, 1, 2, 3);
		Bag<Integer> bag2 = new ConcurrentHashBag<Integer>(3, 1, 2, 3, 1, 2);
		Bag<Integer> bag3 = new ConcurrentHashBag<Integer>(2, 3, 1, 2, 3, 1);

		assertTrue(bag1.equals(bag1));
		assertTrue(bag1.equals(bag2));
		assertTrue(bag2.equals(bag1));
		assertTrue(bag2.equals(bag3));
		assertTrue(bag3.equals(bag2));
		assertTrue(bag1.equals(bag3));
		assertTrue(bag3.equals(bag1));
		assertTrue(bag1.hashCode() == bag2.hashCode());
		assertTrue(bag2.hashCode() == bag3.hashCode());

		bag2.add(3);
		assertFalse(bag1.equals(bag2));
		assertFalse(bag2.equals(bag1));

		bag2 = null;
		assertFalse(bag1.equals(bag2));
	}
}
