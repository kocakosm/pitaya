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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

/**
 * Immutable random access {@link List} implementation. Accepts {@code null}
 * values.
 *
 * @param <E> the type of the {@code List}'s elements.
 *
 * @author Osman KOCAK
 */
public final class ImmutableList<E> extends AbstractList<E> implements RandomAccess, Serializable
{
	private static final long serialVersionUID = 5355899738233743694L;

	/**
	 * {@code ImmutableList} builder. Not thread-safe.
	 *
	 * @param <E> the type of the {@code List}'s elements.
	 */
	public static final class Builder<E>
	{
		private final List<E> inner = new ArrayList<E>();

		/**
		 * Adds the given values to the {@code List} being built.
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
		 * to the {@code List} being built.
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
		 * to the {@code List} being built.
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
		 * Sorts the elements currently in the {@code List} being built
		 * into ascending order, according to the natural ordering of
		 * its elements (all elements in this builder must implement the
		 * {@link Comparable} interface).
		 *
		 * @return this object.
		 *
		 * @throws ClassCastException if this builder contains elements
		 *	that are not mutually comparable.
		 * @throws IllegalArgumentException if the natural ordering of
		 *	the elements in this builder is found to violate the
		 *	{@link Comparable} contract.
		 */
		public Builder<E> sort()
		{
			E[] values = (E[]) inner.toArray();
			Arrays.sort(values);
			return new Builder<E>().add(values);
		}

		/**
		 * Sorts the elements currently in the {@code List} being built
		 * into ascending order, according to the order induced by the
		 * given {@code Comparator}.
		 *
		 * @param c the {@code Comparator} to use.
		 *
		 * @return this object.
		 *
		 * @throws ClassCastException if this builder contains elements
		 *	that are not mutually comparable using the specified
		 *	{@code Comparator}.
		 * @throws IllegalArgumentException if the comparator is found
		 *	to violate the {@code Comparator} contract.
		 */
		public Builder<E> sort(Comparator<? super E> c)
		{
			Collections.sort(inner, c);
			return this;
		}

		/**
		 * Reverses the order of the elements currently in the
		 * {@code List} being built.
		 *
		 * @return this object.
		 */
		public Builder<E> reverse()
		{
			Collections.reverse(inner);
			return this;
		}

		/**
		 * Rotates the elements currently in the {@code List} being
		 * built by the specified distance.
		 *
		 * @param n the distance to rotate.
		 *
		 * @return this object.
		 */
		public Builder<E> rotate(int n)
		{
			Collections.rotate(inner, n);
			return this;
		}

		/**
		 * Randomly permutes the elements currently in the {@code List}
		 * being built using a default source of randomness. All
		 * permutations occur with approximately equal likelihood.
		 *
		 * @return this object.
		 */
		public Builder<E> shuffle()
		{
			Collections.shuffle(inner);
			return this;
		}

		/**
		 * Randomly permutes the elements currently in the {@code List}
		 * being built using the specified source of randomness. All
		 * permutations occur with equal likelihood assuming that the
		 * source of randomness is fair.
		 *
		 * @param random the source of randomness to use.
		 *
		 * @return this object.
		 *
		 * @throws NullPointerException if {@code random} is {@code null}.
		 */
		public Builder<E> shuffle(Random random)
		{
			Collections.shuffle(inner, random);
			return this;
		}

		/**
		 * Finalizes the creation of the {@code ImmutableList}.
		 *
		 * @return an instance of {@code ImmutableList}.
		 */
		public List<E> build()
		{
			return new ImmutableList<E>(inner);
		}
	}

	/**
	 * Creates a new {@code ImmutableList} from the given {@code Collection}.
	 *
	 * @param <E> the type of the {@code List}'s elements.
	 * @param c the source {@code Collection}.
	 *
	 * @return the created {@code ImmutableList}.
	 *
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public static <E> List<E> copyOf(Collection<? extends E> c)
	{
		return new ImmutableList<E>(new ArrayList<E>(c));
	}

	/**
	 * Creates a new {@code ImmutableList} from the given {@code Iterable}.
	 *
	 * @param <E> the type of the {@code List}'s elements.
	 * @param i the source {@code Iterable}.
	 *
	 * @return the created {@code ImmutableList}.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public static <E> List<E> copyOf(Iterable<? extends E> i)
	{
		return new ImmutableList<E>(Iterables.toList(i));
	}

	/**
	 * Creates a new {@code ImmutableList} from the given {@code Iterator}.
	 *
	 * @param <E> the type of the {@code List}'s elements.
	 * @param i the source {@code Iterator}.
	 *
	 * @return the created {@code ImmutableList}.
	 *
	 * @throws NullPointerException if {@code i} is {@code null}.
	 */
	public static <E> List<E> copyOf(Iterator<? extends E> i)
	{
		return new ImmutableList<E>(Iterators.toList(i));
	}

	/**
	 * Creates a new {@code ImmutableList} containing the given elements.
	 *
	 * @param <E> the type of the {@code List}'s elements.
	 * @param values the {@code List}'s elements.
	 *
	 * @return the created {@code ImmutableList}.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 */
	public static <E> List<E> copyOf(E[] values)
	{
		return new ImmutableList<E>(Arrays.asList(values));
	}

	/**
	 * Creates a new {@code ImmutableList} containing the given elements.
	 *
	 * @param <E> the type of the {@code List}'s elements.
	 * @param values the {@code List}'s elements.
	 *
	 * @return the created {@code ImmutableList}.
	 *
	 * @throws NullPointerException if {@code values} is {@code null}.
	 */
	public static <E> List<E> of(E... values)
	{
		return new ImmutableList<E>(Arrays.asList(values));
	}

	private final List<E> inner;

	private ImmutableList(List<E> inner)
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
		return new UnmodifiableListIterator<E>(inner.listIterator());
	}

	@Override
	public Object[] toArray()
	{
		return inner.toArray();
	}

	@Override
	public <E> E[] toArray(E[] a)
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
	public boolean addAll(int index, Collection<? extends E> c)
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

	@Override
	public E get(int index)
	{
		return inner.get(index);
	}

	@Override
	public E set(int index, E element)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Object o)
	{
		return inner.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o)
	{
		return inner.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator()
	{
		return new UnmodifiableListIterator<E>(inner.listIterator());
	}

	@Override
	public ListIterator<E> listIterator(int index)
	{
		return new UnmodifiableListIterator<E>(inner.listIterator(index));
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex)
	{
		return new ImmutableList<E>(inner.subList(fromIndex, toIndex));
	}

	private static final class UnmodifiableListIterator<E> implements ListIterator<E>
	{
		private final ListIterator<E> inner;

		UnmodifiableListIterator(ListIterator<E> iterator)
		{
			this.inner = iterator;
		}

		@Override
		public boolean hasNext()
		{
			return inner.hasNext();
		}

		@Override
		public E next()
		{
			return inner.next();
		}

		@Override
		public boolean hasPrevious()
		{
			return inner.hasPrevious();
		}

		@Override
		public E previous()
		{
			return inner.previous();
		}

		@Override
		public int nextIndex()
		{
			return inner.nextIndex();
		}

		@Override
		public int previousIndex()
		{
			return inner.previousIndex();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(E e)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(E e)
		{
			throw new UnsupportedOperationException();
		}
	}
}
