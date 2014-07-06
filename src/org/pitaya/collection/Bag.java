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

package org.pitaya.collection;

import java.util.Collection;
import java.util.Set;

/**
 * A collection that supports order-independent equality, like {@link Set}, but
 * may have duplicate elements.
 *
 * @param <E> the type of the elements in the bag.
 *
 * @see Collection
 * @see AbstractBag
 * @see HashBag
 * @see ConcurrentHashBag
 * @see Bags
 *
 * @author Osman KOCAK
 */
public interface Bag<E> extends Collection<E>
{
	/**
	 * Returns the count of the given element in this bag.
	 *
	 * @param e the object to count.
	 *
	 * @return the number of occurrences of the element in this bag.
	 */
	int count(E e);
}
