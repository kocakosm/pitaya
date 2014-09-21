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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * {@link ImmutableBag}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ImmutableBagTest
{
	@Test
	public void testBuilder()
	{
		Bag<String> bag = new ImmutableBag.Builder<String>()
			.add(Arrays.asList("A"))
			.add(Arrays.asList("B").iterator())
			.add("C", "D").build();
		assertEquals(new HashBag<String>("A", "B", "C", "D"), bag);
	}

	@Test
	public void testCopyOfIterable()
	{
		Iterable<String> iterable = new HashBag<String>("Hello");
		Bag<String> bag = ImmutableBag.copyOf(iterable);
		assertEquals(iterable, bag);
	}

	@Test
	public void testCopyOfIterator()
	{
		Iterable<String> iterable = new HashBag<String>("Hello");
		Bag<String> bag = ImmutableBag.copyOf(iterable.iterator());
		assertEquals(iterable, bag);
	}

	@Test
	public void testCopyOfArray()
	{
		String[] array = new String[]{"Hello"};
		Bag<String> bag = ImmutableBag.copyOf(array);
		assertEquals(new HashBag<String>(array), bag);
	}

	@Test
	public void testOf()
	{
		Bag<String> bag = ImmutableBag.of("Hello");
		assertEquals(new HashBag<String>("Hello"), bag);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAdd()
	{
		ImmutableBag.of().add("Hello");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddAll()
	{
		ImmutableBag.of().addAll(Arrays.asList("Hello"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear()
	{
		ImmutableBag.of().clear();
	}

	@Test
	public void testContains()
	{
		Bag<String> bag = ImmutableBag.of("Hello", "World");
		assertTrue(bag.contains("Hello"));
		assertTrue(bag.contains("World"));
		assertFalse(bag.contains("Bye"));
		assertFalse(bag.contains(null));

		bag = ImmutableBag.of("271", null);
		assertTrue(bag.contains("271"));
		assertFalse(bag.contains("Hello"));
		assertTrue(bag.contains(null));
	}

	@Test
	public void testContainsAll()
	{
		Bag<String> bag = ImmutableBag.of("Hello", "World");
		assertTrue(bag.containsAll(bag));
		assertTrue(bag.containsAll(Arrays.asList("Hello")));
	}

	@Test
	public void testCount()
	{
		Bag<String> bag = ImmutableBag.of("Hello", "World");
		assertEquals(1, bag.count("Hello"));
		assertEquals(1, bag.count("World"));
		assertEquals(0, bag.count("Bye!!"));
	}

	@Test
	public void testIsEmpty()
	{
		assertTrue(ImmutableBag.of().isEmpty());
		assertFalse(ImmutableBag.of("Hello").isEmpty());
	}

	@Test
	public void testIterator()
	{
		assertFalse(ImmutableBag.of().iterator().hasNext());
		Bag<Long> bag = ImmutableBag.of(1L, 2L, 1L, 2L, 3L);
		List<Long> result = new ArrayList<Long>();
		Iterator<Long> iterator = bag.iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		assertTrue(result.containsAll(bag));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove()
	{
		ImmutableBag.of().remove("Hi");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAll()
	{
		ImmutableBag.of().removeAll(Arrays.asList("Low"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRetainAll()
	{
		ImmutableBag.of().retainAll(Arrays.asList("Low"));
	}

	@Test
	public void testSize()
	{
		assertEquals(0, ImmutableBag.of().size());
		assertEquals(1, ImmutableBag.of("Hello").size());
		assertEquals(2, ImmutableBag.of("Hello", "World").size());
	}

	@Test
	public void testToArray()
	{
		assertEquals(0, ImmutableBag.of().toArray().length);
		String[] in = new String[]{"Hello", "World"};
		String[] out = ImmutableBag.copyOf(in).toArray(new String[0]);
		Arrays.sort(out);
		assertArrayEquals(in, out);
	}
}
