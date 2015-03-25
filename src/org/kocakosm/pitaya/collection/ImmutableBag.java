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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Immutable {@link Bag} implementation. Accepts {@code null} values.
 *
 * @param <E> the type of the {@code Bag}'s elements.
 *
 * @author Osman KOCAK
 */
public final class ImmutableBag<E> extends AbstractBag<E> implements Serializable
{
	private static final long serialVersionUID = 194306588498543706L;

	/**
	 * {@code ImmutableBag} builder. Not thread-safe.
	 *
	 * @param <E> the type of the {@code Bag}'s elements.
	 */
	public static final class Builder<E>
	{
		private final Bag<E> inner = new HashBag<E>();

		/**
		 * Adds the given values to the {@code Bag} being built.
		 *
		 * @param values the values to add.
		 *
		 * @return this object.
		 *
		 * @throws NullPointerException if {@code values} is {@code null}.
		 */
		public Builder<E> add(E... values)
		{
			inner.addAll(Arrays.asList(values));
			return this;
		}

		/**
		 * Adds all the values contained in the given {@code Iterable}
		 * to the {@code Bag} being built.
		 *
		 * @param values the values to add.
		 *
		 * @return this object.
		 *
		 * @throws NullPointerException if {@code values} is {@code null}.
		 */
		public Builder<E> add(Iterable<? extends E> values)
		{
			for (E value : values) {
				inner.add(value);
			}
			return this;
		}

		/**
		 * Adds all the values contained in the given {@code Iterator}
		 * to the {@code Bag} being built.
		 *
		 * @param values the values to add.
		 *
		 * @return this object.
		 *
		 * @throws NullPointerException if {@code values} is {@code null}.
		 */
		public Builder<E> add(Iterator<? extends E> values)
		{
			while (values.hasNext()) {
				inner.add(values.next());
			}
			return this;
		}

		/**
		 * Finalizes the creation of the {@code ImmutableBag}.
		 *
		 * @return an instance of {@code ImmutableBag}.
		 */
		public Bag<E> build()
		{
			return new ImmutableBag<E>(inner);
		}
	}

	/**
	 * Creates a new {@code ImmutableBag} from the given {@code Collection}.
	 *
	 * @param <E> the type of the {@code Bag}'s elements.
	 * @param c the source {@code Collection}.
	 *
	 * @return the created {@code ImmutableBag}.
	 *
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public static <E> Bag<E> copyOf(Collection<? extends E> c)
	{
		return new ImmutableBag<E>(new HashBag<E>(c));
	}

	/**
	 * Creates a new {@code ImmutableBag} from the given {@code Iterable}.
	 *
	 * @param <E> the type of the {@code Bag}'s elements.
	 * @param i the source {@code Iterable}.
	 *
	 * @return the created {@code ImmutableBag}.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public static <E> Bag<E> copyOf(Iterable<? extends E> i)
	{
		return new ImmutableBag<E>(new HashBag<E>(i));
	}

	/**
	 * Creates a new {@code ImmutableBag} from the given {@code Iterator}.
	 *
	 * @param <E> the type of the {@code Bag}'s elements.
	 * @param i the source {@code Iterator}.
	 *
	 * @return the created {@code ImmutableBag}.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public static <E> Bag<E> copyOf(Iterator<? extends E> i)
	{
		return new ImmutableBag<E>(new HashBag<E>(i));
	}

	/**
	 * Creates a new {@code ImmutableBag} containing the given elements.
	 *
	 * @param <E> the type of the {@code Bag}'s elements.
	 * @param values the {@code Bag}'s elements.
	 *
	 * @return the created {@code ImmutableBag}.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 */
	public static <E> Bag<E> copyOf(E[] values)
	{
		return new ImmutableBag<E>(new HashBag<E>(values));
	}

	/**
	 * Creates a new {@code ImmutableBag} containing the given elements.
	 *
	 * @param <E> the type of the {@code Bag}'s elements.
	 * @param values the {@code Bag}'s elements.
	 *
	 * @return the created {@code ImmutableBag}.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 */
	public static <E> Bag<E> of(E... values)
	{
		return new ImmutableBag<E>(new HashBag<E>(values));
	}

	private final Bag<E> inner;

	private ImmutableBag(Bag<E> inner)
	{
		this.inner = inner;
	}

	@Override
	public int count(E e)
	{
		return inner.count(e);
	}

	@Override
	public int size()
	{
		return inner.size();
	}

	@Override
	public boolean isEmpty()
	{
		return inner.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		return inner.contains(o);
	}

	@Override
	public Iterator<E> iterator()
	{
		return new UnmodifiableIterator<E>(inner.iterator());
	}

	@Override
	public Object[] toArray()
	{
		return inner.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return inner.toArray(a);
	}

	@Override
	public boolean add(E e)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return inner.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear()
	{
		throw new UnsupportedOperationException();
	}
}
