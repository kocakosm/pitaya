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

package org.kocakosm.pitaya.time;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Stopwatch}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class StopwatchTest
{
	@Test(expected = IllegalStateException.class)
	public void testStopAlreadyIdle()
	{
		Stopwatch stopwatch = new Stopwatch();
		assertFalse(stopwatch.isRunning());
		stopwatch.stop();
	}

	@Test(expected = IllegalStateException.class)
	public void testStartAlreadyRunning()
	{
		Stopwatch stopwatch = new Stopwatch().start();
		assertTrue(stopwatch.isRunning());
		stopwatch.start();
	}

	@Test
	public void testElapsedTime() throws Exception
	{
		Stopwatch stopwatch = new Stopwatch();
		assertEquals(0, stopwatch.elapsedTime());
		stopwatch.start();
		Thread.sleep(50);
		stopwatch.stop();
		assertTrue(Math.abs(stopwatch.elapsedTime() - 50) < 10);
		Thread.sleep(50);
		stopwatch.start();
		Thread.sleep(50);
		stopwatch.stop();
		assertTrue(Math.abs(stopwatch.elapsedTime() - 100) < 20);
	}

	@Test
	public void testReset() throws Exception
	{
		Stopwatch stopwatch = new Stopwatch().start();
		Thread.sleep(50);
		stopwatch.stop().reset();
		assertEquals(0, stopwatch.elapsedTime());
		stopwatch.start();
		Thread.sleep(50);
		stopwatch.reset();
		Thread.sleep(50);
		stopwatch.stop();
		assertTrue(Math.abs(stopwatch.elapsedTime() - 50) < 10);
	}

	@Test
	public void testToString() throws Exception
	{
		Stopwatch stopwatch = new Stopwatch();
		assertEquals("0 millisecond", stopwatch.toString());
		stopwatch.start();
		Thread.sleep(50);
		long elapsed = stopwatch.stop().elapsedTime();
		assertEquals(elapsed + " milliseconds", stopwatch.toString());
	}
}
