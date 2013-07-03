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

import java.util.Arrays;

/**
 * Various utility methods for {@code Object}s.
 *
 * @author Osman KOCAK
 */
public final class Objects
{
	/**
	 * Returns a default value if the given reference is {@code null}, or
	 * the reference itself if it is not {@code null}.
	 * 
	 * @param <T> the type of the given reference.
	 * @param ref the reference to test for {@code null}ity.
	 * @param defaultValue the default value.
	 *
	 * @return {@code ref} if non-{@code null}, the default value otherwise.
	 */
	public static <T> T defaultIfNull(T ref, T defaultValue)
	{
		return ref == null ? defaultValue : ref;
	}

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
	 * they are equal, {@code false} in all other cases.
	 *
	 * @param a the first to object.
	 * @param b the second to object.
	 *
	 * @return whether the given possibly {@code null} objects are equal.
	 */
	public static boolean equals(Object a, Object b)
	{
		return a == null ? b == null : a.equals(b);
	}

	/**
	 * Returns a hash code for the given {@code Object}s.
	 * 
	 * @param objects the {@code Object}s to compute a hash code for.
	 *
	 * @return a hash code for the given {@code Object}s.
	 *
	 * @throws NullPointerException if {@code objects} is {@code null}.
	 */
	public static int hashCode(Object... objects)
	{
		return Arrays.hashCode(objects);
	}

	/**
	 * Returns the result of the given object's {@code toString()} method if
	 * it is non-{@code null}, and {@code ""} if it is {@code null}.
	 * 
	 * @param o the object to translate into a {@link String}.
	 *
	 * @return a {@link String} representation of the given {@link Object}.
	 */
	public static String toString(Object o)
	{
		return o == null ? "" : o.toString();
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
		return toStringBuilder(prettyName(self));
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

	/** Helper class for {@code toString()}'s implementations. */
	public static final class ToStringBuilder
	{
		private final StringBuilder sb;
		private boolean first = true;

		private ToStringBuilder(String name)
		{
			this.sb = new StringBuilder(128).append(name);
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
		public ToStringBuilder append(String name, Object value)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(value);
			return this;
		}

		/**
		 * Appends the given name/values pair.
		 *
		 * @param name the name.
		 * @param values the values.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, Object... values)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(Arrays.toString(values));
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
		 * Appends the given name/values pair.
		 *
		 * @param name the name.
		 * @param values the values.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, boolean... values)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(Arrays.toString(values));
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
		 * Appends the given name/values pair.
		 *
		 * @param name the name.
		 * @param values the values.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, byte... values)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(Arrays.toString(values));
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
		 * Appends the given name/values pair.
		 *
		 * @param name the name.
		 * @param values the values.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, int... values)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(Arrays.toString(values));
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
		 * Appends the given name/values pair.
		 *
		 * @param name the name.
		 * @param values the values.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, long... values)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(Arrays.toString(values));
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
		 * Appends the given name/values pair.
		 *
		 * @param name the name.
		 * @param values the values.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, float... values)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(Arrays.toString(values));
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
		 * Appends the given name/values pair.
		 *
		 * @param name the name.
		 * @param values the values.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, double... values)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(Arrays.toString(values));
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
		 * Appends the given name/values pair.
		 *
		 * @param name the name.
		 * @param values the values.
		 *
		 * @return this builder.
		 *
		 * @throws NullPointerException if {@code name} is {@code null}.
		 */
		public ToStringBuilder append(String name, char... values)
		{
			Parameters.checkNotNull(name);
			appendSeparator().append(name)
				.append('=').append(Arrays.toString(values));
			return this;
		}

		/**
		 * Appends the given unnamed value.
		 *
		 * @param value the value.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(Object value)
		{
			appendSeparator().append(value);
			return this;
		}

		/**
		 * Appends the given unnamed values.
		 *
		 * @param values the values.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(Object... values)
		{
			appendSeparator().append(Arrays.toString(values));
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
		 * Appends the given unnamed values.
		 *
		 * @param values the values.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(boolean... values)
		{
			appendSeparator().append(Arrays.toString(values));
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
		 * Appends the given unnamed values.
		 *
		 * @param values the values.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(byte... values)
		{
			appendSeparator().append(Arrays.toString(values));
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
		 * Appends the given unnamed values.
		 *
		 * @param values the values.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(int... values)
		{
			appendSeparator().append(Arrays.toString(values));
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
		 * Appends the given unnamed values.
		 *
		 * @param values the values.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(long... values)
		{
			appendSeparator().append(Arrays.toString(values));
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
		 * Appends the given unnamed values.
		 *
		 * @param values the values.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(float... values)
		{
			appendSeparator().append(Arrays.toString(values));
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
		 * Appends the given unnamed values.
		 *
		 * @param values the values.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(double... values)
		{
			appendSeparator().append(Arrays.toString(values));
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

		/**
		 * Appends the given unnamed values.
		 *
		 * @param values the values.
		 *
		 * @return this builder.
		 */
		public ToStringBuilder append(char... values)
		{
			appendSeparator().append(Arrays.toString(values));
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

	private static String prettyName(Object o)
	{
		String name = o.getClass().getName();
		int start = name.lastIndexOf('$');
		if (start == -1) {
			start = name.lastIndexOf('.');
		}
		return name.substring(start + 1);
	}

	private Objects()
	{
		/* ... */
	}
}
