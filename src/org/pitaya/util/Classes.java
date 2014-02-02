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

package org.pitaya.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Utilies for {@code Class}es.
 *
 * @author Osman KOCAK
 */
public final class Classes
{
	/**
	 * Returns the qualified name of the given class (package name followed
	 * by a dot followed by the class name). For arrays classes, this method
	 * returns the component type class name followed by "[]".
	 *
	 * @param c the class.
	 *
	 * @return the qualified name of the given class.
	 * 
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public static String getQualifiedName(Class<?> c)
	{
		if (c.isArray()) {
			StringBuilder qname = new StringBuilder();
			while (c.isArray()) {
				c = c.getComponentType();
				qname.append("[]");
			}
			qname.insert(0, c.getName());
			return qname.toString();
		}
		return c.getName();
	}

	/**
	 * Returns only the name of the given class, that is, without its 
	 * package name and without its eventual "outer" name.
	 *
	 * @param c the class.
	 *
	 * @return the short name of the given class.
	 * 
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public static String getShortName(Class<?> c)
	{
		String qname = getQualifiedName(c);
		int start = qname.lastIndexOf('$');
		if (start == -1) {
			start = qname.lastIndexOf('.');
		}
		return qname.substring(start + 1);
	}

	/**
	 * Returns the name of the package of the given class or the empty 
	 * {@code String} if the class is defined in the default package.
	 *
	 * @param c the class.
	 *
	 * @return the package name of the given class.
	 * 
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public static String getPackageName(Class<?> c)
	{
		String name = c.getName();
		int i = name.lastIndexOf('.');
		return (i != -1 ? name.substring(0, i) : "");
	}

	/**
	 * Returns all the super types of the given class, that is, all the 
	 * classes in which any instance of the given class can be cast into.
	 * 
	 * @param c the class.
	 * 
	 * @return all the given class's super types.
	 * 
	 * @throws NullPointerException if {@code c} is {@code null}.
	 */
	public static Set<Class<?>> getSuperTypes(Class<?> c)
	{
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (Class<?> clazz : c.getInterfaces()) {
			classes.add(clazz);
			classes.addAll(getSuperTypes(clazz));
		}
		Class<?> sup = c.getSuperclass();
		if (sup != null) {
			classes.add(sup);
			classes.addAll(getSuperTypes(sup));
		}
		return Collections.unmodifiableSet(classes);
	}

	/**
	 * Returns the {@code Set} of common ancestors of the given classes, 
	 * that is, all the classes in which any instance of the given classes 
	 * can be both cast into.
	 *
	 * @param c1 the first class.
	 * @param c2 the other class.
	 *
	 * @return the common ancestors of the given classes.
	 * 
	 * @throws NullPointerException if {@code c1} or {@code c2} is {@code null}.
	 */
	public static Set<Class<?>> findCommonAncestors(Class<?> c1, Class<?> c2)
	{
		Set<Class<?>> c1Ancestors = getSuperTypes(c1);
		Set<Class<?>> c2Ancestors = getSuperTypes(c2);
		Set<Class<?>> intersection = new HashSet<Class<?>>(c1Ancestors);
		intersection.retainAll(c2Ancestors);
		return Collections.unmodifiableSet(intersection);
	}

	private Classes()
	{
		/* ... */
	}
}
