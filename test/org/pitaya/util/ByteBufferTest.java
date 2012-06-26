/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012 Osman KOCAK <kocakosm@gmail.com>                        *
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

package org.pitaya.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * {@link ByteBuffer}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ByteBufferTest
{
	@Test(expected=IllegalArgumentException.class)
	public void testInstanciationWithNegativeSize()
	{
		new ByteBuffer(-1);
	}

	@Test(expected=NullPointerException.class)
	public void testInstanciationWithNullArray()
	{
		new ByteBuffer(null);
	}

	@Test
	public void testBytesAreAppended()
	{
		byte[] bytes = new byte[] {
			(byte)0xC8, (byte)0xFF, (byte)0x5E, (byte)0x56
		};
		ByteBuffer buf = new ByteBuffer();
		buf.append((byte) 0x00);
		buf.append((byte) 0x09, (byte)0xD4);
		buf.append(bytes, 1, 2);
		byte[] expected = new byte[] {
			(byte)0x00, (byte)0x09, (byte)0xD4, (byte)0xFF,
			(byte)0x5E
		};
		assertArrayEquals(expected, buf.toByteArray());
		expected = new byte[] {
			(byte)0x00, (byte)0x09
		};
		assertArrayEquals(expected, buf.toByteArray(0, 2));
		expected = new byte[] {
			(byte)0xD4, (byte)0xFF, (byte)0x5E
		};
		assertArrayEquals(expected, buf.toByteArray(2));
	}

	@Test
	public void testBufferGrowsAsBytesAreAppended()
	{
		ByteBuffer buf = new ByteBuffer(0);
		buf.append((byte)0xC8, (byte)0xFF);
		assertEquals(2, buf.size());
		buf.append((byte)0x5E, (byte)0x56);
		assertEquals(4, buf.size());
		byte[] expected = new byte[] {
			(byte)0xC8, (byte)0xFF, (byte)0x5E, (byte)0x56
		};
		assertArrayEquals(expected, buf.toByteArray());
	}

	@Test
	public void testContainsNoDataAfterReset()
	{
		ByteBuffer buf = new ByteBuffer((byte)0xFF, (byte)0x45);
		buf.append((byte)0xD1, (byte)0xE7);
		buf.reset();
		assertEquals(0, buf.size());
		assertArrayEquals(new byte[0], buf.toByteArray());
	}

	@Test
	public void testIterator()
	{
		ByteBuffer buf = new ByteBuffer((byte)0xFF, (byte)0x45);
		buf.append((byte)0xD1, (byte)0xE7);
		List<Byte> expected = Arrays.asList(
			(byte)0xFF, (byte)0x45, (byte)0xD1, (byte)0xE7
		);
		List<Byte> actual = new ArrayList<Byte>(buf.size());
		for (Byte b : buf) {
			actual.add(b);
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testToString()
	{
		ByteBuffer buf = new ByteBuffer((byte)0xFF, (byte)0x45);
		buf.append((byte)0xD1, (byte)0xE7);
		assertEquals("FF45D1E7", buf.toString());
	}

	@Test
	public void testEqualsAndHashCode()
	{
		ByteBuffer buf1 = new ByteBuffer((byte)0xFF, (byte)0x45);
		buf1.append((byte)0xD1, (byte)0xE7);
		ByteBuffer buf2 = new ByteBuffer();
		buf2.append((byte)0xFF, (byte)0x45);
		buf2.append((byte)0xD1, (byte)0xE7);
		assertEquals(buf1, buf1);
		assertEquals(buf1, buf2);
		assertEquals(buf1.hashCode(), buf2.hashCode());
		buf2.append((byte)0x00);
		assertFalse(buf1.equals(buf2));
		buf2 = null;
		assertFalse(buf1.equals(buf2));
	}
}
