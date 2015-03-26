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

package org.kocakosm.pitaya.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A buffer that stores a sequence of bytes. Not thread-safe.
 *
 * @author Osman KOCAK
 */
public final class ByteBuffer implements Iterable<Byte>, Serializable
{
	private static final long serialVersionUID = 5183155512871175888L;
	private static final int DEFAULT_CAPACITY = 512;

	private byte[] buf;
	private int count;
	private int capacity;

	/**
	 * Creates a new {@code ByteBuffer} with an initial capacity of 512
	 * bytes.
	 */
	public ByteBuffer()
	{
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates a new {@code ByteBuffer} having the given initial capacity.
	 *
	 * @param capacity the initial capacity.
	 *
	 * @throws IllegalArgumentException if {@code capacity < 0}.
	 */
	public ByteBuffer(int capacity)
	{
		Parameters.checkCondition(capacity >= 0);
		this.buf = new byte[capacity];
		this.capacity = capacity;
	}

	/**
	 * Creates a new {@code ByteBuffer} containing the given data.
	 *
	 * @param bytes the buffer's initial content.
	 *
	 * @throws NullPointerException if {@code bytes} is {@code null}.
	 */
	public ByteBuffer(byte... bytes)
	{
		this.count = bytes.length;
		this.capacity = Math.max((count * 3) / 2 + 1, DEFAULT_CAPACITY);
		this.buf = new byte[capacity];
		System.arraycopy(bytes, 0, buf, 0, count);
	}

	/**
	 * Writes the given byte to this buffer.
	 *
	 * @param b the byte to write.
	 *
	 * @return this object.
	 */
	public ByteBuffer append(byte b)
	{
		int newCount = count + 1;
		ensureCapacity(newCount);
		buf[count] = b;
		count = newCount;
		return this;
	}

	/**
	 * Writes the given bytes to this buffer.
	 *
	 * @param bytes the bytes to write.
	 *
	 * @return this object.
	 *
	 * @throws NullPointerException if {@code bytes} is {@code null}.
	 */
	public ByteBuffer append(byte... bytes)
	{
		append(bytes, 0, bytes.length);
		return this;
	}

	/**
	 * Writes the given data to this buffer.
	 *
	 * @param bytes the data to write.
	 * @param off the offset.
	 * @param len the number of bytes to write.
	 *
	 * @return this object.
	 *
	 * @throws NullPointerException if {@code bytes} is {@code null}.
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than the length of
	 *	the {@code bytes} array.
	 */
	public ByteBuffer append(byte[] bytes, int off, int len)
	{
		int newCount = count + len;
		ensureCapacity(newCount);
		System.arraycopy(bytes, off, buf, count, len);
		count = newCount;
		return this;
	}

	/**
	 * Clears this buffer.
	 *
	 * @return this object.
	 */
	public ByteBuffer clear()
	{
		count = 0;
		buf = new byte[capacity];
		return this;
	}

	/**
	 * Returns the number of bytes contained in this buffer.
	 *
	 * @return the number of bytes contained in this buffer.
	 */
	public int size()
	{
		return count;
	}

	/**
	 * Returns the content of this buffer.
	 *
	 * @return the content of this buffer.
	 */
	public byte[] toByteArray()
	{
		byte[] data = new byte[count];
		System.arraycopy(buf, 0, data, 0, count);
		return data;
	}

	/**
	 * Returns the bytes contained in this buffer, starting at {@code off}.
	 *
	 * @param off the starting offset.
	 *
	 * @return the requested data bytes.
	 *
	 * @throws IndexOutOfBoundsException if {@code off} is negative or
	 *	if {@code off} is greater than {@code size()}.
	 */
	public byte[] toByteArray(int off)
	{
		return toByteArray(off, count - off);
	}

	/**
	 * Returns {@code len} bytes from this buffer, starting at {@code off}.
	 *
	 * @param off the starting offset.
	 * @param len the number of bytes to return.
	 *
	 * @return the requested data bytes.
	 *
	 * @throws IndexOutOfBoundsException if {@code off} or {@code len} is
	 *	negative or if {@code off + len} is greater than {@code size()}.
	 */
	public byte[] toByteArray(int off, int len)
	{
		if (off < 0 || len < 0 || off + len > count) {
			throw new IndexOutOfBoundsException();
		}
		byte[] data = new byte[len];
		System.arraycopy(buf, off, data, 0, len);
		return data;
	}

	@Override
	public Iterator<Byte> iterator()
	{
		return new ByteBufferIterator();
	}

	@Override
	public String toString()
	{
		return BaseEncoding.BASE_16.encode(buf, 0, count);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof ByteBuffer)) {
			return false;
		}
		final ByteBuffer buffer = (ByteBuffer) o;
		if (count != buffer.count) {
			return false;
		}
		for (int i = 0; i < count; i++) {
			if (buf[i] != buffer.buf[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		if (count == 0) {
			return hash;
		}
		hash = 17 * hash + count;
		hash = 17 * hash + buf[0];
		hash = 17 * hash + buf[count];
		return hash;
	}

	private void ensureCapacity(int capacity)
	{
		int oldSize = buf.length;
		if (capacity > oldSize) {
			int newSize = Math.max((oldSize * 3) / 2 + 1, capacity);
			byte[] newBuf = new byte[newSize];
			System.arraycopy(buf, 0, newBuf, 0, count);
			buf = newBuf;
		}
	}

	private final class ByteBufferIterator implements Iterator<Byte>
	{
		private int index;

		@Override
		public boolean hasNext()
		{
			return index < count;
		}

		@Override
		public Byte next()
		{
			if (index >= count) {
				throw new NoSuchElementException();
			}
			return buf[index++];
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}
