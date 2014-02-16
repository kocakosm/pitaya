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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Chronometer}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class ChronometerTest
{
	@Test(expected=IllegalStateException.class)
	public void testStopAlreadyIdle()
	{
		Chronometer sw = new Chronometer();
		assertFalse(sw.isRunning());
		sw.stop();
	}

	@Test(expected=IllegalStateException.class)
	public void testStartAlreadyRunning()
	{
		Chronometer sw = new Chronometer().start();
		assertTrue(sw.isRunning());
		sw.start();
	}

	@Test
	public void testElapsedTime() throws Exception
	{
		Chronometer chronometer = new Chronometer();
		assertEquals(0, chronometer.elapsedTime());
		chronometer.start();
		Thread.sleep(50);
		chronometer.stop();
		assertTrue(Math.abs(chronometer.elapsedTime() - 50) < 5);
		Thread.sleep(50);
		chronometer.start();
		Thread.sleep(50);
		assertTrue(Math.abs(chronometer.elapsedTime() - 100) < 10);
	}

	@Test
	public void testReset() throws Exception
	{
		Chronometer chronometer = new Chronometer().start();
		Thread.sleep(50);
		chronometer.stop().reset();
		assertEquals(0, chronometer.elapsedTime());
		chronometer.start();
		Thread.sleep(50);
		chronometer.reset();
		Thread.sleep(50);
		chronometer.stop();
		assertTrue(Math.abs(chronometer.elapsedTime() - 50) < 5);
	}

	@Test
	public void testToString() throws Exception
	{
		Chronometer chronometer = new Chronometer();
		assertEquals("0 millisecond", chronometer.toString());
		chronometer.start();
		Thread.sleep(100);
		chronometer.stop();
		long elapsed = chronometer.elapsedTime();
		assertEquals(elapsed + " milliseconds", chronometer.toString());
	}
}
