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

package org.kocakosm.pitaya.util;

import java.util.Arrays;

/**
 * Various extra utility methods for {@code Object}s.
 *
 * @author Osman KOCAK
 */
public final class XObjects
{
	/**
	 * Returns the first of the given references that is not {@code null}.
	 * If all are {@code null}, {@code null} is returned.
	 *
	 * @param <T> the type of the given references.
	 * @param refs the references to test for {@code null}ity.
	 *
	 * @return the first non-{@code null} reference.
	 */
	public static <T> T firstNonNull(T... refs)
	{
		for (T ref : refs) {
			if (ref != null) {
				return ref;
			}
		}
		return null;
	}

	/**
	 * Returns {@code true} if the given objects are both {@code null} or if
	 * they are equal, {@code false} in all other cases. If {@code a} and
	 * {@code b} are arrays, they are compared using the appropriate
	 * {@link Arrays}' {@code equals} method.
	 *
	 * @param a the first object.
	 * @param b the second object.
	 *
	 * @return whether the given possibly {@code null} objects are equal.
	 */
	public static boolean equals(Object a, Object b)
	{
		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		if (a.equals(b)) {
			return true;
		}
		if (a.getClass().isArray() && b.getClass().isArray()) {
			return arrayEquals(a, b);
		}
		return false;
	}

	private static boolean arrayEquals(Object a, Object b)
	{
		if (a instanceof long[] && b instanceof long[]) {
			return Arrays.equals((long[]) a, (long[]) b);
		}
		if (a instanceof int[] && b instanceof int[]) {
			return Arrays.equals((int[]) a, (int[]) b);
		}
		if (a instanceof short[] && b instanceof short[]) {
			return Arrays.equals((short[]) a, (short[]) b);
		}
		if (a instanceof char[] && b instanceof char[]) {
			return Arrays.equals((char[]) a, (char[]) b);
		}
		if (a instanceof byte[] && b instanceof byte[]) {
			return Arrays.equals((byte[]) a, (byte[]) b);
		}
		if (a instanceof boolean[] && b instanceof boolean[]) {
			return Arrays.equals((boolean[]) a, (boolean[]) b);
		}
		if (a instanceof float[] && b instanceof float[]) {
			return Arrays.equals((float[]) a, (float[]) b);
		}
		if (a instanceof double[] && b instanceof double[]) {
			return Arrays.equals((double[]) a, (double[]) b);
		}
		if (a instanceof Object[] && b instanceof Object[]) {
			return Arrays.deepEquals((Object[]) a, (Object[]) b);
		}
		return false;
	}

	/**
	 * Returns a hash code for the given {@code Object}s.
	 *
	 * @param objects the {@code Object}s to compute a hash code for.
	 *
	 * @return a hash code for the given {@code Object}s.
	 */
	public static int hashCode(Object... objects)
	{
		return objects == null
			? 0 : objects.length == 1
			? hashCode(objects[0]) : Arrays.hashCode(objects);
	}

	private static int hashCode(Object object)
	{
		return object == null ? 0 : object.hashCode();
	}

	/**
	 * Returns a {@code String} representation of the given object obtained
	 * by calling its {@link Object#toString()} method if it's a "regular"
	 * object or by calling the appropriate {@link Arrays}' {@code toString}
	 * method if it's an array, returns {@code "null"} if it's {@code null}.
	 *
	 * @param o the object to translate into a {@link String}.
	 *
	 * @return a {@link String} representation of the given {@link Object}.
	 */
	public static String toString(Object o)
	{
		return toString(o, "null");
	}

	/**
	 * Returns a {@code String} representation of the given object obtained
	 * by calling its {@link Object#toString()} method if it's a "regular"
	 * object or by calling the appropriate {@link Arrays}' {@code toString}
	 * method if it's an array, returns {@code ifNull} if it's {@code null}.
	 *
	 * @param o the object to translate into a {@link String}.
	 * @param ifNull the value to return if {@code o} is {@code null}.
	 *
	 * @return a {@link String} representation of the given {@link Object}.
	 */
	public static String toString(Object o, String ifNull)
	{
		return o == null
			? ifNull : o.getClass().isArray()
			? arrayToString(o) : o.toString();
	}

	private static String arrayToString(Object array)
	{
		if (array instanceof long[]) {
			return Arrays.toString((long[]) array);
		}
		if (array instanceof int[]) {
			return Arrays.toString((int[]) array);
		}
		if (array instanceof short[]) {
			return Arrays.toString((short[]) array);
		}
		if (array instanceof char[]) {
			return Arrays.toString((char[]) array);
		}
		if (array instanceof byte[]) {
			return Arrays.toString((byte[]) array);
		}
		if (array instanceof boolean[]) {
			return Arrays.toString((boolean[]) array);
		}
		if (array instanceof float[]) {
			return Arrays.toString((float[]) array);
		}
		if (array instanceof double[]) {
			return Arrays.toString((double[]) array);
		}
		if (array instanceof Object[]) {
			return Arrays.deepToString((Object[]) array);
		}
		return array.toString();
	}

	/**
	 * Returns a {@link ToStringBuilder} for the given object.
	 *
	 * @param self the object to generate its string representation.
	 *
	 * @return the corresponding {@link ToStringBuilder}.
	 *
	 * @throws NullPointerException if {@code self} is {@code null}.
	 */
	public static ToStringBuilder toStringBuilder(Object self)
	{
		return toStringBuilder(Classes.getShortName(self.getClass()));
	}

	/**
	 * Returns a {@link ToStringBuilder} with the given name.
	 *
	 * @param name the object to generate its string representation.
	 *
	 * @return the corresponding {@link ToStringBuilder}.
	 *
	 * @throws NullPointerException if {@code name} is {@code null}.
	 */
	public static ToStringBuilder toStringBuilder(String name)
	{
		Parameters.checkNotNull(name);
		return new ToStringBuilder(name);
	}

	/**
	 * Helper class for {@link Object#toString()}'s implementations. Not
	 * thread-safe.
	 */
	public static final class ToStringBuilder
	{
		private final StringBuilder sb;
		private boolean first = true;

		private ToStringBuilder(String name)
		{
			this.sb = new StringBuilder(128).append(name);
		}

		/**
		 * Appends the given name/value pair. Note that if {@code value}
		 * is an array, its values will be displayed between brackets,
		 * each value being separated by a comma.
		 *
		 * @param name the name.
		 * @param value the value.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, Object value)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(XObjects.toString(value));
			return this;
		}

		/**
		 * Appends the given name/value pair.
		 *
		 * @param name the name.
		 * @param value the value.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, boolean value)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(value);
			return this;
		}

		/**
		 * Appends the given name/value pair.
		 *
		 * @param name the name.
		 * @param value the value.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, byte value)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(value);
			return this;
		}

		/**
		 * Appends the given name/value pair.
		 *
		 * @param name the name.
		 * @param value the value.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, int value)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(value);
			return this;
		}

		/**
		 * Appends the given name/value pair.
		 *
		 * @param name the name.
		 * @param value the value.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, long value)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(value);
			return this;
		}

		/**
		 * Appends the given name/value pair.
		 *
		 * @param name the name.
		 * @param value the value.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, float value)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(value);
			return this;
		}

		/**
		 * Appends the given name/value pair.
		 *
		 * @param name the name.
		 * @param value the value.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, double value)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(value);
			return this;
		}

		/**
		 * Appends the given name/value pair.
		 *
		 * @param name the name.
		 * @param value the value.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, char value)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(value);
			return this;
		}

		/**
		 * Appends the given unnamed value. Note that if {@code value}
		 * is an array, its values will be displayed between brackets,
		 * each value being separated by a comma.
		 *
		 * @param value the value.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(Object value)
		{
			appendSeparator().append(XObjects.toString(value));
			return this;
		}

		/**
		 * Appends the given unnamed value.
		 *
		 * @param value the value.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(boolean value)
		{
			appendSeparator().append(value);
			return this;
		}

		/**
		 * Appends the given unnamed value.
		 *
		 * @param value the value.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(byte value)
		{
			appendSeparator().append(value);
			return this;
		}

		/**
		 * Appends the given unnamed value.
		 *
		 * @param value the value.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(int value)
		{
			appendSeparator().append(value);
			return this;
		}

		/**
		 * Appends the given unnamed value.
		 *
		 * @param value the value.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(long value)
		{
			appendSeparator().append(value);
			return this;
		}

		/**
		 * Appends the given unnamed value.
		 *
		 * @param value the value.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(float value)
		{
			appendSeparator().append(value);
			return this;
		}

		/**
		 * Appends the given unnamed value.
		 *
		 * @param value the value.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(double value)
		{
			appendSeparator().append(value);
			return this;
		}

		/**
		 * Appends the given unnamed value.
		 *
		 * @param value the value.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(char value)
		{
			appendSeparator().append(value);
			return this;
		}

		@Override
		public String toString()
		{
			sb.append(")");
			return sb.toString();
		}

		private StringBuilder appendSeparator()
		{
			if (first) {
				sb.append(" (");
				first = false;
			} else {
				sb.append(", ");
			}
			return sb;
		}
	}

	private XObjects()
	{
		/* ... */
	}
}
