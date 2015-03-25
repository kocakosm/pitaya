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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utilities for {@link Throwable}s.
 *
 * @author Osman KOCAK
 */
public final class Throwables
{
	/**
	 * Returns the root cause of the given {@code Throwable}. If the given
	 * {@code Throwable} is not the result of a previous one, it is
	 * considered as the root.
	 *
	 * @param t the {@code Throwable} to get the root cause.
	 *
	 * @return the root cause of the given {@code Throwable}.
	 *
	 * @throws NullPointerException if {@code t} is {@code null}.
	 */
	public static Throwable getRootCause(Throwable t)
	{
		Throwable cause = t.getCause();
		return cause == null ? t : getRootCause(cause);
	}

	/**
	 * Returns the chain of {@code Throwable}s that caused the given one.
	 * The given {@code Throwable} is the first element in the returned
	 * {@code List}, the last one is the root cause.
	 *
	 * @param t the {@code Throwable} to get the chain.
	 *
	 * @return the chain of {@code Throwable} that caused {@code t}.
	 *
	 * @throws NullPointerException if {@code t} is {@code null}.
	 */
	public static List<Throwable> getCauseChain(Throwable t)
	{
		List<Throwable> chain = new ArrayList<Throwable>();
		chain.add(Parameters.checkNotNull(t));
		Throwable cause = t.getCause();
		while (cause != null) {
			chain.add(cause);
			cause = cause.getCause();
		}
		return Collections.unmodifiableList(chain);
	}

	/**
	 * Returns the stack trace of the given {@code Throwable} as a
	 * {@code String}.
	 *
	 * @param t the {@code Throwable} to get the stack trace.
	 *
	 * @return the stack trace of the given {@code Throwable}.
	 *
	 * @throws NullPointerException if {@code t} is {@code null}.
	 */
	public static String getStackTrace(Throwable t)
	{
		StringWriter out = new StringWriter();
		PrintWriter writer = new PrintWriter(out);
		t.printStackTrace(writer);
		writer.flush();
		return out.toString();
	}

	/**
	 * Returns the stack frames of the given {@code Throwable}. The stack
	 * frames are simply obtained from calling {@code toString} on each
	 * {@link StackTraceElement} of {@code t}.
	 *
	 * @param t the {@code Throwable} to get the stack frames.
	 *
	 * @return the stack frames of the given {@code Throwable}.
	 *
	 * @throws NullPointerException if {@code t} is {@code null}.
	 *
	 * @see Throwable#getStackTrace()
	 */
	public static List<String> getStackFrames(Throwable t)
	{
		StackTraceElement[] elements = t.getStackTrace();
		List<String> frames = new ArrayList<String>(elements.length);
		for (StackTraceElement element : elements) {
			frames.add(element.toString());
		}
		return Collections.unmodifiableList(frames);
	}

	/**
	 * Re-throws the given {@code Throwable} if it is already an instance of
	 * {@code RuntimeException} or {@link Error}, and, if not, wraps it in a
	 * {@code RuntimeException} before throwing it.
	 *
	 * @param t the {@code Throwable} to propagate.
	 *
	 * @return nothing (this method always throws a {@code Throwable}).
	 *
	 * @throws NullPointerException if {@code t} is {@code null}.
	 * @throws RuntimeException if {@code t} is already an instance of
	 *	{@code RuntimeException} or if {@code t} is neither an instance
	 *	of {@code RuntimeException} nor an instance of {@code Error}.
	 * @throws Error if {@code t} is an instance of {@code Error}.
	 */
	public static RuntimeException propagate(Throwable t)
	{
		Parameters.checkNotNull(t);
		if (t instanceof RuntimeException) {
			throw (RuntimeException) t;
		} else if (t instanceof Error) {
			throw (Error) t;
		}
		throw new RuntimeException(t);
	}

	private Throwables()
	{
		/* ... */
	}
}
