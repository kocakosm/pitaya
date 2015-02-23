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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

/**
 * {@link ImmutableList}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ImmutableListTest
{
	@Test
	public void testBuilder()
	{
		List<String> list = new ImmutableList.Builder<String>()
			.add(Arrays.asList("C"))
			.add("A", "B").sort()
			.add(Arrays.asList("D").iterator())
			.reverse().rotate(1).build();
		assertEquals(Arrays.asList("A", "D", "C", "B"), list);
	}

	@Test
	public void testCopyOfCollection()
	{
		Collection<String> collection = Arrays.asList("Hello");
		assertEquals(collection, ImmutableList.copyOf(collection));
	}

	@Test
	public void testCopyOfIterable()
	{
		Iterable<String> iterable = Arrays.asList("Hello");
		assertEquals(iterable, ImmutableList.copyOf(iterable));
	}

	@Test
	public void testCopyOfIterator()
	{
		Iterable<String> iterable = Arrays.asList("Hello");
		assertEquals(iterable, ImmutableList.copyOf(iterable.iterator()));
	}

	@Test
	public void testCopyOfArray()
	{
		String[] array = new String[]{"Hello"};
		assertEquals(Arrays.asList(array), ImmutableList.copyOf(array));
	}

	@Test
	public void testOf()
	{
		assertEquals(Arrays.asList("Hello"), ImmutableList.of("Hello"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAdd()
	{
		ImmutableList.of().add("Hello");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testInsert()
	{
		ImmutableList.of().add(1, "Hello");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddAll()
	{
		ImmutableList.of().addAll(Arrays.asList("Hello"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testInsertAll()
	{
		ImmutableList.of().addAll(1, Arrays.asList("Hello"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSet()
	{
		ImmutableList.of().set(1, "Hello");
	}

	@Test
	public void testGet()
	{
		assertEquals("Hello", ImmutableList.of("Hello").get(0));
	}

	@Test
	public void testIndexOf()
	{
		assertEquals(-1, ImmutableList.of("Hello", "World").indexOf(""));
		assertEquals(1, ImmutableList.of("Hello", "World").indexOf("World"));
	}

	@Test
	public void testLastIndexOf()
	{
		assertEquals(-1, ImmutableList.of("World", "World").lastIndexOf(""));
		assertEquals(1, ImmutableList.of("World", "World").lastIndexOf("World"));
	}

	@Test
	public void testSubList()
	{
		List<String> in = Arrays.asList("Hello", "World", "!");
		assertEquals(in.subList(0, 1), ImmutableList.copyOf(in).subList(0, 1));
		assertEquals(in.subList(1, 1), ImmutableList.copyOf(in).subList(1, 1));
		assertEquals(in.subList(1, 2), ImmutableList.copyOf(in).subList(1, 2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear()
	{
		ImmutableList.of().clear();
	}

	@Test
	public void testContains()
	{
		List<String> list = ImmutableList.of("Hello", "World");
		assertTrue(list.contains("Hello"));
		assertTrue(list.contains("World"));
		assertFalse(list.contains("Bye"));
		assertFalse(list.contains(null));

		list = ImmutableList.of("271", null);
		assertTrue(list.contains("271"));
		assertFalse(list.contains("Hello"));
		assertTrue(list.contains(null));
	}

	@Test
	public void testContainsAll()
	{
		List<String> list = ImmutableList.of("Hello", "World");
		assertTrue(list.containsAll(list));
		assertTrue(list.containsAll(Arrays.asList("Hello")));
	}

	@Test
	public void testIsEmpty()
	{
		assertTrue(ImmutableList.of().isEmpty());
		assertFalse(ImmutableList.of("Hello").isEmpty());
	}

	@Test
	public void testIterator()
	{
		assertFalse(ImmutableList.of().iterator().hasNext());
		List<Long> list = ImmutableList.of(1L, 2L, 1L, 2L, 3L);
		List<Long> result = new ArrayList<Long>();
		Iterator<Long> iterator = list.iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		assertTrue(result.containsAll(list));
	}

	@Test
	public void testListIterator()
	{
		List<Long> list = ImmutableList.of(1L, 2L, 1L, 2L, 3L);

		ListIterator<Long> iterator = list.listIterator();
		assertFalse(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(0, iterator.nextIndex());
		assertEquals(Long.valueOf(1L), iterator.next());
		assertTrue(iterator.hasPrevious());
		assertEquals(0, iterator.previousIndex());

		iterator = list.listIterator(2);
		assertTrue(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(2, iterator.nextIndex());
		assertEquals(1, iterator.previousIndex());
		assertEquals(Long.valueOf(2L), iterator.previous());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testListIteratorAdd()
	{
		ImmutableList.of(1L, 2L, 3L).listIterator().add(1L);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testListIteratorRemove()
	{
		ImmutableList.of(1L, 2L, 3L).listIterator().remove();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testListIteratorSet()
	{
		ImmutableList.of(1L, 2L, 3L).listIterator().set(1L);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveObject()
	{
		ImmutableList.of().remove("Hi");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveIndex()
	{
		ImmutableList.of().remove(0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAll()
	{
		ImmutableList.of().removeAll(Arrays.asList("Low"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRetainAll()
	{
		ImmutableList.of().retainAll(Arrays.asList("Low"));
	}

	@Test
	public void testSize()
	{
		assertEquals(0, ImmutableList.of().size());
		assertEquals(1, ImmutableList.of("Hello").size());
		assertEquals(2, ImmutableList.of("Hello", "World").size());
	}

	@Test
	public void testToArray()
	{
		assertEquals(0, ImmutableList.of().toArray().length);
		String[] in = new String[]{"Hello", "World"};
		String[] out = ImmutableList.copyOf(in).toArray(new String[0]);
		Arrays.sort(out);
		assertArrayEquals(in, out);
	}
}
