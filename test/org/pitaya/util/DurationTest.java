
package org.pitaya.util;

import static org.pitaya.util.Duration.*;
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
		assertEquals(10000, valueOf(" -- 10 sec").toMilliseconds());
		assertEquals(-10000, valueOf(" - - - 10 sec").toMilliseconds());
		assertEquals(60000, valueOf("1 min").toMilliseconds());
		assertEquals(60000, valueOf("1 minute").toMilliseconds());
		assertEquals(3600000, valueOf("1 h").toMilliseconds());
		assertEquals(3600000, valueOf("1 hour").toMilliseconds());
		assertEquals(86400000, valueOf("1 d").toMilliseconds());
		assertEquals(86400000, valueOf("1 day").toMilliseconds());
		assertEquals(93784005, valueOf(", 1 Day , \n, 2 hours,  \t3 MIN, 4  S AnD 5  mS and ").toMilliseconds());
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
		assertTrue(until(yesterday).toMilliseconds() >= -ONE_DAY.toMilliseconds());
		assertTrue(until(tomorrow).toMilliseconds() >= ONE_DAY.toMilliseconds());
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
		assertEquals(ZERO.toString(), "0 millisecond");
		assertEquals(ONE_MILLISECOND.toString(), "1 millisecond");
		assertEquals(ONE_MILLISECOND.multipliedBy(10).toString(), "10 milliseconds");
		assertEquals(ONE_SECOND.toString(), "1 second");
		assertEquals(ONE_SECOND.negated().toString(), "-(1 second)");
		assertEquals(ONE_HOUR.plus(ONE_SECOND).toString(), "1 hour, 1 second");
		assertEquals(ONE_WEEK.plus(ONE_HOUR).plus(ONE_HOUR).toString(), "7 days, 2 hours");
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