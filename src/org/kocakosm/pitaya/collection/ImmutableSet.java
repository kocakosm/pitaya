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

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Immutable {@link Set} implementation. Accepts {@code null} values.
 *
 * @param <E> the type of the {@code Set}'s elements.
 *
 * @author Osman KOCAK
 */
public final class ImmutableSet<E> extends AbstractSet<E> implements Serializable
{
	private static final long serialVersionUID = 6744093868687684286L;

	/**
	 * {@code ImmutableSet} builder. Not thread-safe.
	 *
	 * @param <E> the type of the {@code Set}'s elements.
	 */
	public static final class Builder<E>
	{
		private final Set<E> inner = new HashSet<E>();

		/**
		 * Adds the given values to the {@code Set} being built.
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
		 * to the {@code Set} being built.
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
		 * to the {@code Set} being built.
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
		 * Finalizes the creation of the {@code ImmutableSet}.
		 *
		 * @return an instance of {@code ImmutableSet}.
		 */
		public Set<E> build()
		{
			return new ImmutableSet<E>(inner);
		}
	}

	/**
	 * Creates a new {@code ImmutableSet} from the given {@code Collection}.
	 *
	 * @param <E> the type of the {@code Set}'s elements.
	 * @param c the source {@code Collection}.
	 *
	 * @return the created {@code ImmutableSet}.
	 *
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public static <E> Set<E> copyOf(Collection<? extends E> c)
	{
		return new ImmutableSet<E>(new HashSet<E>(c));
	}

	/**
	 * Creates a new {@code ImmutableSet} from the given {@code Iterable}.
	 *
	 * @param <E> the type of the {@code Set}'s elements.
	 * @param i the source {@code Iterable}.
	 *
	 * @return the created {@code ImmutableSet}.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public static <E> Set<E> copyOf(Iterable<? extends E> i)
	{
		return new ImmutableSet<E>(Iterables.toSet(i));
	}

	/**
	 * Creates a new {@code ImmutableSet} from the given {@code Iterator}.
	 *
	 * @param <E> the type of the {@code Set}'s elements.
	 * @param i the source {@code Iterator}.
	 *
	 * @return the created {@code ImmutableSet}.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public static <E> Set<E> copyOf(Iterator<? extends E> i)
	{
		return new ImmutableSet<E>(Iterators.toSet(i));
	}

	/**
	 * Creates a new {@code ImmutableSet} containing the given elements.
	 *
	 * @param <E> the type of the {@code Set}'s elements.
	 * @param values the {@code Set}'s elements.
	 *
	 * @return the created {@code ImmutableSet}.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 */
	public static <E> Set<E> copyOf(E[] values)
	{
		return new ImmutableSet<E>(new HashSet<E>(Arrays.asList(values)));
	}

	/**
	 * Creates a new {@code ImmutableSet} containing the given elements.
	 *
	 * @param <E> the type of the {@code Set}'s elements.
	 * @param values the {@code Set}'s elements.
	 *
	 * @return the created {@code ImmutableSet}.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 */
	public static <E> Set<E> of(E... values)
	{
		return new ImmutableSet<E>(new HashSet<E>(Arrays.asList(values)));
	}

	private final Set<E> inner;

	private ImmutableSet(Set<E> inner)
	{
		this.inner = inner;
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
	public boolean retainAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear()
	{
		throw new UnsupportedOperationException();
	}
}
