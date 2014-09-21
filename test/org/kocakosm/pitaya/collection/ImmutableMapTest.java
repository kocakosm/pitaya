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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * {@link ImmutableMap}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ImmutableMapTest
{
	@Test
	public void testBuilder()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		in.put(1L, 10L);
		Map<Long, Long> map = new ImmutableMap.Builder<Long, Long>()
			.put(2L, 20L).put(in).build();
		in.put(2L, 20L);
		assertEquals(in, map);
	}

	@Test
	public void testCopyOf()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		in.put(1L, 10L);
		in.put(2L, 20L);
		assertEquals(in, ImmutableMap.copyOf(in));
	}

	@Test
	public void testContainsKey()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		in.put(1L, 10L);
		in.put(2L, 20L);
		Map<Long, Long> map = ImmutableMap.copyOf(in);
		assertFalse(map.containsKey(0L));
		assertTrue(map.containsKey(1L));
		assertTrue(map.containsKey(2L));
		assertFalse(map.containsKey(3L));
	}

	@Test
	public void testContainsValue()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		in.put(1L, 10L);
		in.put(2L, 20L);
		Map<Long, Long> map = ImmutableMap.copyOf(in);
		assertFalse(map.containsValue(0L));
		assertTrue(map.containsValue(10L));
		assertTrue(map.containsValue(20L));
		assertFalse(map.containsValue(3L));
	}

	@Test
	public void testIsEmpty()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		assertTrue(ImmutableMap.copyOf(in).isEmpty());
		in.put(1L, 10L);
		assertFalse(ImmutableMap.copyOf(in).isEmpty());
	}

	@Test
	public void testSize()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		assertEquals(0, ImmutableMap.copyOf(in).size());
		in.put(1L, 10L);
		assertEquals(1, ImmutableMap.copyOf(in).size());
		in.put(2L, 20L);
		assertEquals(2, ImmutableMap.copyOf(in).size());
	}

	@Test
	public void testGet()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		in.put(1L, 10L);
		in.put(2L, 20L);
		Map<Long, Long> map = ImmutableMap.copyOf(in);
		assertNull(map.get(0L));
		assertEquals(Long.valueOf(10L), map.get(1L));
		assertEquals(Long.valueOf(20L), map.get(2L));
		assertNull(map.get(3L));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testClear()
	{
		ImmutableMap.copyOf(new HashMap<Long, Long>()).clear();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove()
	{
		ImmutableMap.copyOf(new HashMap<Long, Long>()).remove(1L);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testPut()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		ImmutableMap.copyOf(in).put(1L, 10L);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testPutAll()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		ImmutableMap.copyOf(in).putAll(in);
	}

	@Test
	public void testKeySet()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		in.put(1L, 10L);
		in.put(2L, 20L);
		assertEquals(in.keySet(), ImmutableMap.copyOf(in).keySet());
	}

	@Test
	public void testEntrySet()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		in.put(1L, 10L);
		in.put(2L, 20L);
		assertEquals(in.entrySet(), ImmutableMap.copyOf(in).entrySet());
	}

	@Test
	public void testValues()
	{
		Map<Long, Long> in = new HashMap<Long, Long>();
		in.put(1L, 10L);
		in.put(2L, 20L);
		assertEquals(asSet(in.values()), asSet(ImmutableMap.copyOf(in).values()));
	}

	private <T> Set<T> asSet(Collection<T> values)
	{
		return new HashSet<T>();
	}
}
