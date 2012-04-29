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

import static java.util.concurrent.TimeUnit.*;

/**
 * Utility class to measure elapsed time between specific instants. Precision is
 * the millisecond (modulo the overhead of measurement). This class heavily
 * relies on {@link System#currentTimeMillis()}. Instances of this class are not
 * thread-safe.
 *
 * @author Osman KOCAK
 */
public final class Chronometer
{
	private long referenceTime;
	private long elapsedTime;
	private boolean isRunning;

	/** Creates a new (unstarted) {@code Chronometer}. */
	public Chronometer()
	{
		this.referenceTime = now();
		this.elapsedTime = 0;
		this.isRunning = false;
	}

	/**
	 * Returns {@code true} if {@link #start()} has been called on this
	 * object, and {@link #stop()} hasn't been called since the last call
	 * to {@code start()}.
	 *
	 * @return whether this chronometer is running.
	 */
	public boolean isRunning()
	{
		return isRunning;
	}

	/**
	 * Starts this chronometer.
	 *
	 * @return this object.
	 *
	 * @throws IllegalStateException if this chronometer is already running.
	 */
	public Chronometer start()
	{
		if (isRunning) {
			throw new IllegalStateException("Already running...");
		}
		referenceTime = now();
		isRunning = true;
		return this;
	}

	/**
	 * Stops this chronometer.
	 *
	 * @return this object.
	 *
	 * @throws IllegalStateException if this chronometer is already stopped.
	 */
	public Chronometer stop()
	{
		if (!isRunning) {
			throw new IllegalStateException("Already idle...");
		}
		elapsedTime += now() - referenceTime;
		isRunning = false;
		return this;
	}

	/**
	 * Resets this chronometer (sets the elapsed time for this chronometer
	 * to zero). Calling this method does NOT modify this chronometer's
	 * state (remains running if it was running, and, idle if it was idle).
	 *
	 * @return this object.
	 */
	public Chronometer reset()
	{
		elapsedTime = 0;
		referenceTime = now();
		return this;
	}

	/**
	 * Returns the current elapsed time, expressed in milliseconds
	 *
	 * @return the current elapsed time.
	 */
	public long elapsedTime()
	{
		if (isRunning) {
			return elapsedTime + now() - referenceTime;
		}
		return elapsedTime;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		long time = elapsedTime();
		long days = MILLISECONDS.toDays(time);
		time -= DAYS.toMillis(days);
		append(sb, days, "day");
		long hours = MILLISECONDS.toHours(time);
		time -= HOURS.toMillis(hours);
		append(sb, hours, "hour");
		long minutes = MILLISECONDS.toMinutes(time);
		time -= MINUTES.toMillis(minutes);
		append(sb, minutes, "minute");
		long seconds = MILLISECONDS.toSeconds(time);
		time -= SECONDS.toMillis(seconds);
		append(sb, seconds, "second");
		append(sb, time, "millisecond");
		return sb.toString();
	}

	private void append(StringBuilder sb, long value, String unit)
	{
		if (value > 0) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(value).append(' ').append(unit);
			if (value > 1) {
				sb.append('s');
			}
		}
	}

	private long now()
	{
		return System.currentTimeMillis();
	}
}
