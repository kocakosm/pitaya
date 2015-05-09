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

import static org.kocakosm.pitaya.collection.Iterators.*;
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
 * {@link Iterators}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class IteratorsTest
{
	@Test
	public void testEmptyIteratorHasNext()
	{
		assertFalse(emptyIterator().hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void testEmptyIteratorNext()
	{
		emptyIterator().next();
	}

	@Test(expected = IllegalStateException.class)
	public void testEmptyIteratorRemove()
	{
		emptyIterator().remove();
	}

	@Test
	public void testConcat()
	{
		Iterator<String> i1 = iterator("Hello");
		Iterator<String> i2 = iterator(" ");
		Iterator<String> i3 = iterator("World");
		Iterator<String> i4 = iterator("!");

		List<String> result = new ArrayList<String>();
		Iterator<String> concat = concat(i1, i2, i3, i4);
		while (concat.hasNext()) {
			result.add(concat.next());
		}

		assertEquals(Arrays.asList("Hello", " ", "World", "!"), result);
	}

	@Test
	public void testConcatEmptyIterators()
	{
		assertFalse(concat(emptyIterator(), emptyIterator()).hasNext());
		assertFalse(concat(iterator()).hasNext());
		assertFalse(concat().hasNext());
	}

	@Test
	public void testCycle()
	{
		Iterator<Long> iterator = cycle(1L, 2L, 3L);
		List<Long> result = new ArrayList<Long>();
		while (iterator.hasNext() && result.size() < 9) {
			result.add(iterator.next());
		}
		assertEquals(Arrays.asList(1L, 2L, 3L, 1L, 2L, 3L, 1L, 2L, 3L), result);

		iterator = cycle(iterator(1L, 2L, 3L));
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
		Iterator<Long> i1 = Arrays.asList(1L, 2L, 3L).iterator();
		Iterator<Long> i2 = Arrays.asList(1L, 2L, 3L).iterator();
		Iterator<Long> i3 = Arrays.asList(1L, 2L, 3L, 4L).iterator();
		assertTrue(Iterators.equal(i1, i1));
		assertTrue(Iterators.equal(i1, i2));
		assertFalse(Iterators.equal(i1, i3));
		assertFalse(Iterators.equal(i3, i2));
		assertTrue(Iterators.equal(null, null));
		assertFalse(Iterators.equal(i1, null));
		assertFalse(Iterators.equal(null, i1));
	}

	@Test
	public void testLimit()
	{
		Iterator<Long> iterator = limit(iterator(1L, 2L, 3L, 4L), 3);
		List<Long> result = new ArrayList<Long>();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		assertEquals(Arrays.asList(1L, 2L, 3L), result);
	}

	@Test
	public void testSkip()
	{
		Iterator<Long> iterator = iterator(0L, 1L, 2L, 3L, 4L, 5L);
		assertEquals(2, skip(iterator, 2));
		List<Long> result = new ArrayList<Long>();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		assertEquals(Arrays.asList(2L, 3L, 4L, 5L), result);

		iterator = iterator(0L, 1L, 2L, 3L, 4L, 5L);
		assertEquals(0, skip(iterator, 0));
		assertEquals(6, skip(iterator, 10));
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testToList()
	{
		Iterator<Long> iterator = iterator(1L, 2L, 3L);
		assertEquals(Arrays.asList(1L, 2L, 3L), toList(iterator));
		assertEquals(Collections.emptyList(), toList(iterator()));
	}

	@Test
	public void testToSet()
	{
		Iterator<Long> iterator = iterator(1L, 2L, 3L, 3L, 2L, 1L);
		assertEquals(set(1L, 2L, 3L), toSet(iterator));
		assertEquals(Collections.emptySet(), toSet(iterator()));
	}

	@Test
	public void testToBag()
	{
		Iterator<Long> iterator = iterator(1L, 2L, 3L);
		assertEquals(bag(3L, 2L, 1L), toBag(iterator));
		assertEquals(Bags.emptyBag(), toBag(iterator()));
	}

	@Test
	public void testToString()
	{
		assertEquals("[]", Iterators.toString(iterator()));
		assertEquals("[1, 2, 3]", Iterators.toString(iterator(1L, 2L, 3L)));
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

	private <E> Iterator<E> iterator(E... elements)
	{
		return Arrays.asList(elements).iterator();
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
