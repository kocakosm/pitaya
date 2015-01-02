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

package org.kocakosm.pitaya.time;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Utility class to measure elapsed time between specific instants. Note that
 * while this class provides millisecond precision, it does not necessarily
 * provide millisecond accuracy (depends, among many things, on the accuracy of
 * the underlying system clock). Instances of this class are not thread-safe.
 *
 * @author Osman KOCAK
 */
public final class Chronometer
{
	private long referenceTime;
	private long elapsedTime;
	private boolean running;

	/** Creates a new (unstarted) {@code Chronometer}. */
	public Chronometer()
	{
		this.referenceTime = now();
		this.elapsedTime = 0L;
		this.running = false;
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
		return running;
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
		if (running) {
			throw new IllegalStateException("Already running...");
		}
		referenceTime = now();
		running = true;
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
		if (!running) {
			throw new IllegalStateException("Already idle...");
		}
		elapsedTime += now() - referenceTime;
		running = false;
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
		elapsedTime = 0L;
		referenceTime = now();
		return this;
	}

	/**
	 * Returns the current elapsed time, expressed in milliseconds.
	 *
	 * @return the current elapsed time.
	 */
	public long elapsedTime()
	{
		if (running) {
			return (elapsedTime + now() - referenceTime) / 1000000L;
		}
		return elapsedTime / 1000000L;
	}

	@Override
	public String toString()
	{
		return Duration.of(elapsedTime(), MILLISECONDS).toString();
	}

	private long now()
	{
		return System.nanoTime();
	}
}
