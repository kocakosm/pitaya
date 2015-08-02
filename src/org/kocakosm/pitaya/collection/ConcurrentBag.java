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

import java.util.Collection;

/**
 * A {@link Bag} providing thread safety and atomicity guarantees.
 *
 * @param <E> the type of the elements in the bag.
 *
 * @author Osman KOCAK
 */
public interface ConcurrentBag<E> extends Bag<E>
{
	/**
	 * Adds all of the elements in the specified collection that are not
	 * already contained in this bag.
	 *
	 * @param c the collection containing elements to be added to this bag.
	 *
	 * @return the number of elements added.
	 *
	 * @throws NullPointerException if the given collection is {@code null}
	 *	or if it contains a {@code null} reference and this bag does not
	 *	accept {@code null} elements.
	 */
	int addAllAbsent(Collection<? extends E> c);

	/**
	 * Adds the given element to this bag if not already present.
	 *
	 * @param e the element to be added to this bag, if absent.
	 *
	 * @return {@code true} if the element has been added.
	 *
	 * @throws NullPointerException if {@code e} is {@code null} and this
	 *	bag does not accept {@code null} elements.
	 */
	boolean addIfAbsent(E e);
}
