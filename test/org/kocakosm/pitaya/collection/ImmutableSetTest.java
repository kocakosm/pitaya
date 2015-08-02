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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * {@link ImmutableSet}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ImmutableSetTest
{
	@Test
	public void testBuilder()
	{
		Set<String> set = new ImmutableSet.Builder<String>()
			.add(Arrays.asList("A"))
			.add(Arrays.asList("B").iterator())
			.add("C", "D").build();
		assertEquals(asSet("A", "B", "C", "D"), set);
	}

	@Test
	public void testCopyOfCollection()
	{
		Collection<String> collection = asSet("Hello");
		assertEquals(collection, ImmutableSet.copyOf(collection));
	}

	@Test
	public void testCopyOfIterable()
	{
		Iterable<String> iterable = asSet("Hello");
		assertEquals(iterable, ImmutableSet.copyOf(iterable));
	}

	@Test
	public void testCopyOfIterator()
	{
		Iterable<String> iterable = asSet("Hello");
		assertEquals(iterable, ImmutableSet.copyOf(iterable.iterator()));
	}

	@Test
	public void testCopyOfArray()
	{
		String[] array = new String[]{"Hello"};
		assertEquals(asSet(array), ImmutableSet.copyOf(array));
	}

	@Test
	public void testOf()
	{
		assertEquals(asSet("Hello"), ImmutableSet.of("Hello"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAdd()
	{
		ImmutableSet.of().add("Hello");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddAll()
	{
		ImmutableSet.of().addAll(Arrays.asList("Hello"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear()
	{
		ImmutableSet.of().clear();
	}

	@Test
	public void testContains()
	{
		Set<String> set = ImmutableSet.of("Hello", "World");
		assertTrue(set.contains("Hello"));
		assertTrue(set.contains("World"));
		assertFalse(set.contains("Bye"));
		assertFalse(set.contains(null));

		set = ImmutableSet.of("271", null);
		assertTrue(set.contains("271"));
		assertFalse(set.contains("Hello"));
		assertTrue(set.contains(null));
	}

	@Test
	public void testContainsAll()
	{
		Set<String> set = ImmutableSet.of("Hello", "World");
		assertTrue(set.containsAll(set));
		assertTrue(set.containsAll(Arrays.asList("Hello")));
	}

	@Test
	public void testIsEmpty()
	{
		assertTrue(ImmutableSet.of().isEmpty());
		assertFalse(ImmutableSet.of("Hello").isEmpty());
	}

	@Test
	public void testIterator()
	{
		assertFalse(ImmutableSet.of().iterator().hasNext());
		List<Long> in = Arrays.asList(1L, 2L, 3L);
		Set<Long> set = ImmutableSet.copyOf(in);
		assertEquals(in, Iterables.toList(set));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove()
	{
		ImmutableSet.of().remove("Hi");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAll()
	{
		ImmutableSet.of().removeAll(Arrays.asList("Low"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRetainAll()
	{
		ImmutableSet.of().retainAll(Arrays.asList("Low"));
	}

	@Test
	public void testSize()
	{
		assertEquals(0, ImmutableSet.of().size());
		assertEquals(1, ImmutableSet.of("Hello").size());
		assertEquals(2, ImmutableSet.of("Hello", "World").size());
	}

	@Test
	public void testToArray()
	{
		assertEquals(0, ImmutableSet.of().toArray().length);
		String[] in = new String[]{"Hello", "World"};
		String[] out = ImmutableSet.copyOf(in).toArray(new String[0]);
		assertArrayEquals(in, out);
	}

	private <T> Set<T> asSet(T... values)
	{
		return new HashSet<T>(Arrays.asList(values));
	}
}
