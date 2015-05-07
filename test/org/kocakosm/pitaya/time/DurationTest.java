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

import static org.kocakosm.pitaya.time.Duration.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * {@link Duration}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class DurationTest
{
	@Test
	public void testValueOf()
	{
		assertEquals(0, valueOf("0 ms").toMilliseconds());
		assertEquals(0, valueOf("-0 ms").toMilliseconds());
		assertEquals(100, valueOf("100 ms").toMilliseconds());
		assertEquals(100, valueOf("100 milliseconds").toMilliseconds());
		assertEquals(999, valueOf("1000 millis - 1 ms").toMilliseconds());
		assertEquals(1000, valueOf("1 s").toMilliseconds());
		assertEquals(1000, valueOf("1 second").toMilliseconds());
		assertEquals(1001, valueOf("1 s,1 ms").toMilliseconds());
		assertEquals(1001, valueOf("1 s + 1 ms").toMilliseconds());
		assertEquals(10000, valueOf(" -   - 10 sec").toMilliseconds());
		assertEquals(-10000, valueOf(" - - - 10 sec").toMilliseconds());
		assertEquals(60000, valueOf("1 min").toMilliseconds());
		assertEquals(60000, valueOf("1 minute").toMilliseconds());
		assertEquals(3600000, valueOf("1 h").toMilliseconds());
		assertEquals(3600000, valueOf("1 hour").toMilliseconds());
		assertEquals(86400000, valueOf("1 d").toMilliseconds());
		assertEquals(86400000, valueOf("1 day").toMilliseconds());
		assertEquals(86400000, valueOf("1\nday").toMilliseconds());
		assertEquals(93784005, valueOf("1,d \n, 2 h, \t3 MIN 4 S AnD 5 mS &").toMilliseconds());
	}

	@Test
	public void testOfAmoutAndUnit()
	{
		assertEquals(ZERO, of(0, TimeUnit.MILLISECONDS));
		assertEquals(ONE_MILLISECOND, of(1, TimeUnit.MILLISECONDS));
		assertEquals(ONE_SECOND, of(1, TimeUnit.SECONDS));
		assertEquals(ONE_MINUTE, of(1, TimeUnit.MINUTES));
		assertEquals(ONE_HOUR, of(1, TimeUnit.HOURS));
		assertEquals(ONE_DAY, of(1, TimeUnit.DAYS));
		assertEquals(ONE_WEEK, of(7, TimeUnit.DAYS));
		assertEquals(Long.MAX_VALUE, of(Long.MAX_VALUE, TimeUnit.MILLISECONDS).toMilliseconds());
	}

	@Test(expected = ArithmeticException.class)
	public void testOfAmoutAndUnitOverflow()
	{
		of(Long.MAX_VALUE, TimeUnit.MINUTES);
	}

	@Test
	public void testOfDurations()
	{
		assertEquals(ZERO, of());
		assertEquals(1000, of(ONE_SECOND).toMilliseconds());
		assertEquals(1001, of(ONE_SECOND, ONE_MILLISECOND).toMilliseconds());
		assertEquals(999, of(ONE_SECOND, ONE_MILLISECOND.negated()).toMilliseconds());
	}

	@Test
	public void testBetween()
	{
		Date now = new Date();
		Date yesterday = new Date(now.getTime() - 86400000L);
		assertEquals(ONE_DAY, between(yesterday, now));
		assertEquals(ONE_DAY.negated(), between(now, yesterday));
	}

	@Test
	public void testSince()
	{
		Date now = new Date();
		Date yesterday = new Date(now.getTime() - 86400000L);
		Date tomorrow = new Date(now.getTime() + 86400000L);
		assertTrue(since(yesterday).toMilliseconds() >= ONE_DAY.toMilliseconds());
		assertTrue(since(tomorrow).toMilliseconds() >= -ONE_DAY.toMilliseconds());
	}

	@Test
	public void testUntil()
	{
		Date now = new Date();
		Date yesterday = new Date(now.getTime() - 86400000L);
		Date tomorrow = new Date(now.getTime() + 86400000L);
		assertTrue(until(yesterday).toMilliseconds() <= -ONE_DAY.toMilliseconds());
		assertTrue(until(tomorrow).toMilliseconds() <= ONE_DAY.toMilliseconds());
	}

	@Test
	public void testToUnit()
	{
		assertEquals(1L, ONE_SECOND.to(TimeUnit.SECONDS));
		assertEquals(1000L, ONE_SECOND.to(TimeUnit.MILLISECONDS));
		assertEquals(86400L, ONE_DAY.to(TimeUnit.SECONDS));
	}

	@Test
	public void testToMilliseconds()
	{
		assertEquals(1000L, ONE_SECOND.toMilliseconds());
		assertEquals(3600000L, ONE_HOUR.toMilliseconds());
	}

	@Test
	public void testPlus()
	{
		assertEquals(ONE_SECOND, ONE_SECOND.plus(ZERO));
		assertEquals(ZERO, ONE_SECOND.plus(ONE_SECOND.negated()));
		assertEquals(ONE_WEEK, ONE_DAY.plus(ONE_DAY).plus(ONE_DAY)
			.plus(ONE_DAY).plus(ONE_DAY).plus(ONE_DAY).plus(ONE_DAY));
	}

	@Test
	public void testMinus()
	{
		assertEquals(ONE_SECOND, ONE_SECOND.minus(ZERO));
		assertEquals(ZERO, ONE_SECOND.minus(ONE_SECOND));
	}

	@Test
	public void testMultipliedBy()
	{
		assertEquals(ZERO, ONE_MINUTE.multipliedBy(0));
		assertEquals(ONE_WEEK, ONE_WEEK.multipliedBy(1));
		assertEquals(ONE_WEEK, ONE_DAY.multipliedBy(7));
		assertEquals(ONE_DAY.negated(), ONE_DAY.multipliedBy(-1));
	}

	@Test
	public void testDividedBy()
	{
		assertEquals(ONE_WEEK, ONE_WEEK.dividedBy(1));
		assertEquals(ONE_DAY, ONE_WEEK.dividedBy(7));
		assertEquals(ONE_DAY.negated(), ONE_DAY.dividedBy(-1));
	}

	@Test
	public void testNegated()
	{
		assertEquals(-ONE_DAY.toMilliseconds(), ONE_DAY.negated().toMilliseconds());
		assertEquals(ONE_DAY.toMilliseconds(), ONE_DAY.negated().negated().toMilliseconds());
	}

	@Test
	public void testSign()
	{
		assertEquals(0, ZERO.sign());
		assertEquals(1, ONE_MILLISECOND.sign());
		assertEquals(-1, ONE_MILLISECOND.negated().sign());
	}

	@Test
	public void testAbsoluteValue()
	{
		assertEquals(ONE_DAY, ONE_DAY.negated().absoluteValue());
	}

	@Test
	public void testAfter()
	{
		Date now = new Date();
		Date yesterday = new Date(now.getTime() - 86400000L);
		Date tomorrow = new Date(now.getTime() + 86400000L);
		assertEquals(now, ONE_DAY.after(yesterday));
		assertEquals(tomorrow, ONE_DAY.after(now));
	}

	@Test
	public void testBefore()
	{
		Date now = new Date();
		Date yesterday = new Date(now.getTime() - 86400000L);
		Date tomorrow = new Date(now.getTime() + 86400000L);
		assertEquals(now, ONE_DAY.before(tomorrow));
		assertEquals(yesterday, ONE_DAY.before(now));
	}

	@Test
	public void testCompareTo()
	{
		assertEquals(0, ONE_DAY.compareTo(ONE_DAY));
		assertTrue(ONE_DAY.compareTo(ONE_HOUR) > 0);
		assertTrue(ONE_HOUR.compareTo(ONE_DAY) < 0);
		assertTrue(ONE_DAY.compareTo(valueOf("9223372036854775807 ms").negated()) > 0);
		assertTrue(valueOf("9223372036854775807 ms").negated().compareTo(ONE_DAY) < 0);
	}

	@Test
	public void testToString()
	{
		assertEquals("0 millisecond", ZERO.toString());
		assertEquals("1 millisecond", ONE_MILLISECOND.toString());
		assertEquals("10 milliseconds", ONE_MILLISECOND.multipliedBy(10).toString());
		assertEquals("1 second", ONE_SECOND.toString());
		assertEquals("-1 second", ONE_SECOND.negated().toString());
		assertEquals("-1 hour, -10 minutes", ONE_MINUTE.multipliedBy(70).negated().toString());
		assertEquals("1 hour, 1 second", ONE_HOUR.plus(ONE_SECOND).toString());
		assertEquals("7 days, 2 hours", ONE_WEEK.plus(ONE_HOUR).plus(ONE_HOUR).toString());
	}

	@Test
	public void testEqualsAndHashCode()
	{
		assertEquals(ONE_DAY, ONE_DAY);
		assertEquals(ONE_DAY.hashCode(), ONE_DAY.hashCode());
		assertFalse(ONE_HOUR.equals((Duration) null));
		assertEquals(ONE_WEEK, ONE_DAY.multipliedBy(7));
		assertFalse(ONE_DAY.equals(ONE_SECOND));
	}
}
