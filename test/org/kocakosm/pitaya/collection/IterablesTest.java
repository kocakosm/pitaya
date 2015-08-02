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

import static org.kocakosm.pitaya.collection.Iterables.*;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

/**
 * {@link Iterables}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class IterablesTest
{
	@Test
	public void testEmptyIterableHasNext()
	{
		assertFalse(emptyIterable().iterator().hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void testEmptyIterableNext()
	{
		emptyIterable().iterator().next();
	}

	@Test(expected = IllegalStateException.class)
	public void testEmptyIterableRemove()
	{
		emptyIterable().iterator().remove();
	}

	@Test
	public void testConcat()
	{
		Iterable<String> i1 = Arrays.asList("Hello");
		Iterable<String> i2 = Arrays.asList(" ");
		Iterable<String> i3 = Arrays.asList("World");
		Iterable<String> i4 = Arrays.asList("!");

		List<String> result = new ArrayList<String>();
		for (String s : concat(i1, i2, i3, i4)) {
			result.add(s);
		}

		assertEquals(Arrays.asList("Hello", " ", "World", "!"), result);
	}

	@Test
	public void testConcatEmptyIterables()
	{
		assertFalse(concat(emptyIterable(), emptyIterable()).iterator().hasNext());
		assertFalse(concat(new ArrayList<Iterable<Long>>()).iterator().hasNext());
		assertFalse(concat().iterator().hasNext());
	}

	@Test
	public void testCycle()
	{
		List<Long> result = new ArrayList<Long>();
		for (Long n : cycle(1L, 2L, 3L)) {
			result.add(n);
			if (result.size() >= 9) {
				break;
			}
		}
		assertEquals(Arrays.asList(1L, 2L, 3L, 1L, 2L, 3L, 1L, 2L, 3L), result);

		Iterator<Long> iterator = cycle(Arrays.asList(1L, 2L, 3L)).iterator();
		result = new ArrayList<Long>();
		while (iterator.hasNext()) {
			result.add(iterator.next());
			iterator.remove();
		}
		assertEquals(Arrays.asList(1L, 2L, 3L), result);
	}

	@Test
	public void testEqual()
	{
		List<Long> i1 = Arrays.asList(1L, 2L, 3L);
		List<Long> i2 = Arrays.asList(1L, 2L, 3L);
		List<Long> i3 = Arrays.asList(1L, 2L, 3L, 4L);
		assertTrue(Iterables.equal(i1, i1));
		assertTrue(Iterables.equal(i1, i2));
		assertFalse(Iterables.equal(i1, i3));
		assertFalse(Iterables.equal(i3, i2));
		assertTrue(Iterables.equal(null, null));
		assertFalse(Iterables.equal(i1, null));
		assertFalse(Iterables.equal(null, i1));
	}

	@Test
	public void testLimit()
	{
		List<Long> result = new ArrayList<Long>();
		for (Long n : limit(Arrays.asList(1L, 2L, 3L, 4L), 3)) {
			result.add(n);
		}
		assertEquals(Arrays.asList(1L, 2L, 3L), result);
	}

	@Test
	public void testSkip()
	{
		List<Long> result = new ArrayList<Long>();
		for (Long n : skip(Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L), 3)) {
			result.add(n);
		}
		assertEquals(Arrays.asList(3L, 4L, 5L), result);
		assertFalse(skip(Arrays.asList(1L, 2L, 3L), 10).iterator().hasNext());
	}

	@Test
	public void testToList()
	{
		assertEquals(Arrays.asList(1L, 2L, 3L), toList(Arrays.asList(1L, 2L, 3L)));
		assertEquals(Collections.emptyList(), toList(Collections.emptyList()));
	}

	@Test
	public void testToSet()
	{
		Iterable<Long> iterable = Arrays.asList(1L, 2L, 3L);
		assertEquals(set(1L, 2L, 3L), toSet(iterable));
		assertEquals(iterable, new ArrayList<Long>(toSet(iterable)));
		assertEquals(Collections.emptySet(), toSet(Collections.emptyList()));
	}

	@Test
	public void testToBag()
	{
		Iterable<Long> iterable = Arrays.asList(1L, 2L, 3L);
		assertEquals(bag(1L, 2L, 3L), toBag(iterable));
		assertEquals(iterable, new ArrayList<Long>(toBag(iterable)));
		assertEquals(Bags.emptyBag(), toBag(Collections.emptyList()));
	}

	@Test
	public void testToString()
	{
		assertEquals("[]", Iterables.toString(Collections.emptyList()));
		assertEquals("[1, 2, 3]", Iterables.toString(Arrays.asList(1L, 2L, 3L)));
	}

	@Test
	public void testConstructor() throws Exception
	{
		Class<Iterables> c = Iterables.class;
		assertEquals(1, c.getDeclaredConstructors().length);
		Constructor<Iterables> constructor = c.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	private <E> Set<E> set(E... elements)
	{
		return new HashSet<E>(Arrays.asList(elements));
	}

	private <E> Bag<E> bag(E... elements)
	{
		return new HashBag<E>(elements);
	}
}
