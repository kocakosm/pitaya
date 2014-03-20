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

package org.pitaya.io;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Test;

/**
 * {@link ObjectCodec}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ObjectCodecTest
{
	@Test
	public void testSerializationRoundTrip()
	{
		SerializableClass original = new SerializableClass(42, "Hello");
		byte[] serialized = ObjectCodec.encode(original);
		assertEquals(original, ObjectCodec.decode(serialized));
	}

	@Test(expected = ClassCastException.class)
	public void testDeserializationWithInvalidClass()
	{
		SerializableClass original = new SerializableClass(42, "Hello");
		byte[] serialized = ObjectCodec.encode(original);
		ObjectCodec.decode(serialized, String.class);
	}

	private static final class SerializableClass implements Serializable
	{
		private static final long serialVersionUID = 1L;

		private final int a;
		private final String b;

		SerializableClass(int a, String b)
		{
			this.a = a;
			this.b = b;
		}

		@Override
		public int hashCode()
		{
			return b.hashCode();
		}

		@Override
		public boolean equals(Object o)
		{
			if (!(o instanceof SerializableClass)) {
				return false;
			}
			final SerializableClass s = (SerializableClass) o;
			return a == s.a && b.equals(s.b);
		}
	}
}
