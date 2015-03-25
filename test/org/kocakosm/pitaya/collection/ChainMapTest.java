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

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

/**
 * {@link ChainMap}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ChainMapTest
{
	@Test(expected = IllegalArgumentException.class)
	public void testListConstructorWithEmptyList()
	{
		new ChainMap<Long, Long>(ImmutableList.<Map<Long, Long>>of());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testArrayConstructorWithEmptyArray()
	{
		new ChainMap<Long, Long>();
	}

	@Test
	public void testSize()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		m1.put(1L, 1L);
		m1.put(2L, 4L);
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		m2.put(2L, 4L);
		m2.put(3L, 9L);
		List<Map<Long, Long>> maps = ImmutableList.of(m1, m2);

		assertEquals(3, new ChainMap<Long, Long>(maps).size());
	}

	@Test
	public void testIsEmpty()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		assertTrue(chain.isEmpty());
		m1.put(1L, 1L);
		assertFalse(chain.isEmpty());
	}

	@Test
	public void testContainsKey()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		m1.put(1L, 1L);
		m1.put(2L, 4L);
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		m2.put(2L, 4L);
		m2.put(3L, 9L);
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		assertTrue(chain.containsKey(1L));
		assertTrue(chain.containsKey(2L));
		assertTrue(chain.containsKey(3L));
		assertFalse(chain.containsKey(0L));
	}

	@Test
	public void testContainsValue()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		m1.put(1L, 1L);
		m1.put(2L, 4L);
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		m2.put(2L, 4L);
		m2.put(3L, 9L);
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		assertTrue(chain.containsValue(1L));
		assertTrue(chain.containsValue(4L));
		assertTrue(chain.containsValue(9L));
		assertFalse(chain.containsValue(0L));
	}

	@Test
	public void testGet()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		m1.put(1L, 1L);
		m1.put(2L, 4L);
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		m2.put(2L, 3L);
		m2.put(3L, 9L);
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		assertEquals(Long.valueOf(1L), chain.get(1L));
		assertEquals(Long.valueOf(4L), chain.get(2L));
		assertEquals(Long.valueOf(9L), chain.get(3L));
		assertNull(chain.get(0L));
	}

	@Test
	public void testPut()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		assertNull(chain.put(1L, 1L));
		assertFalse(m2.containsKey(1L));
		assertEquals(Long.valueOf(1L), m1.get(1L));
		assertEquals(Long.valueOf(1L), chain.put(1L, 2L));
	}

	@Test
	public void testRemove()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		m1.put(1L, 1L);
		m1.put(2L, 4L);
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		m2.put(2L, 3L);
		m2.put(3L, 9L);
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		assertEquals(Long.valueOf(1L), chain.remove(1L));
		assertFalse(m1.containsKey(1L));
		assertEquals(Long.valueOf(4L), chain.remove(2L));
		assertFalse(m1.containsKey(2L));
		assertFalse(m2.containsKey(2L));
		assertEquals(Long.valueOf(9L), chain.remove(3L));
		assertFalse(m2.containsKey(3L));
	}

	@Test
	public void testPutAll()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		Map<Long, Long> m3 = new HashMap<Long, Long>();
		m3.put(1L, 1L);
		m3.put(2L, 4L);

		chain.putAll(m3);
		assertFalse(m1.containsKey(0L));
		assertTrue(m1.containsKey(1L));
		assertTrue(m1.containsKey(2L));
		assertFalse(m2.containsKey(0L));
		assertFalse(m2.containsKey(1L));
		assertFalse(m2.containsKey(2L));
	}

	@Test
	public void testClear()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		m1.put(1L, 1L);
		m1.put(2L, 4L);
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		m2.put(2L, 3L);
		m2.put(3L, 9L);
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		chain.clear();
		assertTrue(m1.isEmpty());
		assertTrue(m2.isEmpty());
	}

	@Test
	public void testKeySet()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		m1.put(1L, 1L);
		m1.put(2L, 4L);
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		m2.put(2L, 3L);
		m2.put(3L, 9L);
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		assertEquals(ImmutableSet.of(1L, 2L, 3L), chain.keySet());
	}

	@Test
	public void testValues()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		m1.put(1L, 1L);
		m1.put(2L, 4L);
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		m2.put(2L, 3L);
		m2.put(3L, 9L);
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		assertEquals(ImmutableList.of(1L, 4L, 9L), chain.values());
	}

	@Test
	public void testEntrySet()
	{
		Map<Long, Long> m1 = new HashMap<Long, Long>();
		m1.put(1L, 1L);
		m1.put(2L, 4L);
		Map<Long, Long> m2 = new HashMap<Long, Long>();
		m2.put(2L, 3L);
		m2.put(3L, 9L);
		Map<Long, Long> chain = new ChainMap<Long, Long>(m1, m2);

		Entry<Long, Long> e1 = new SimpleEntry<Long, Long>(1L, 1L);
		Entry<Long, Long> e2 = new SimpleEntry<Long, Long>(2L, 4L);
		Entry<Long, Long> e3 = new SimpleEntry<Long, Long>(3L, 9L);
		assertEquals(ImmutableSet.of(e1, e2, e3), chain.entrySet());
	}

	@Test
	public void testEqualsAndHashCode()
	{
		Map<Long, Long> m1 = new ImmutableMap.Builder<Long, Long>().put(1L, 1L).build();
		Map<Long, Long> m2 = new ImmutableMap.Builder<Long, Long>().put(1L, 1L).build();
		Map<Long, Long> m3 = new ImmutableMap.Builder<Long, Long>().put(2L, 4L).build();

		Map<Long, Long> c1 = new ChainMap<Long, Long>(m1);
		Map<Long, Long> c2 = new ChainMap<Long, Long>(m2);
		Map<Long, Long> c3 = new ChainMap<Long, Long>(m1, m2);
		Map<Long, Long> c4 = new ChainMap<Long, Long>(m3);

		assertTrue(c1.equals(c1));
		assertTrue(c1.equals(c2));
		assertTrue(c2.equals(c1));
		assertTrue(c2.equals(c3));
		assertTrue(c3.equals(c2));
		assertTrue(c1.equals(c3));
		assertTrue(c3.equals(c1));
		assertFalse(c3.equals(c4));
		assertFalse(c4.equals(c3));
		assertFalse(c1.equals(c4));
		assertFalse(c4.equals(c1));
		assertTrue(c1.equals(m1));
		assertTrue(c2.equals(m1));
		assertTrue(c3.equals(m1));
		assertFalse(c1.equals((ChainMap) null));
		assertTrue(c1.hashCode() == c2.hashCode());
		assertTrue(c2.hashCode() == c3.hashCode());
	}
}
