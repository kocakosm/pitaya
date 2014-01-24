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

import java.util.AbstractCollection;

/**
 * Abstract skeleton implementation of the {@link Bag} interface. If you want to
 * provide your own {@link Bag} implementation, it is more than highly adviced
 * that you extend this abstract base class and do not override its 
 * {@link AbstractBag#equals(java.lang.Object)} and {@link AbstractBag#hashCode()}
 * methods.
 *
 * @param <E> the type of the elements in the bag.
 *
 * @author Osman KOCAK
 */
public abstract class AbstractBag<E> extends AbstractCollection<E> implements Bag<E>
{
	@Override
	public int count(E e)
	{
		int count = 0;
		for (E entry : this) {
			if (e == null ? entry == null : e.equals(entry)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof Bag)) {
			return false;
		}
		Bag bag = (Bag) o;
		if (size() == bag.size()) {
			for (E entry : this) {
				if (count(entry) != bag.count(entry)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 1;
		for (E entry : this) {
			hash = 31 * hash + count(entry);
		}
		return hash;
	}
}
