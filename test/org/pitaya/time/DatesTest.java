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

package org.pitaya.time;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * {@link Dates}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class DatesTest
{
	@Test
	public void testCopy()
	{
		assertEquals(null, Dates.copy(null));

		Date now = new Date();
		assertEquals(now, Dates.copy(now));
		assertNotSame(now, Dates.copy(now));
	}

	@Test
	public void testNow()
	{
		assertTrue(new Date().getTime() - Dates.now().getTime() <= 20);
	}

	@Test
	public void testIsSameInstant()
	{
		Date now = new Date();
		Date yesterday = new Date(now.getTime() - 86400000L);
		assertTrue(Dates.isSameInstant(now, now));
		assertTrue(Dates.isSameInstant(toCalendar(now), toCalendar(now)));
		assertFalse(Dates.isSameInstant(yesterday, now));
		assertFalse(Dates.isSameInstant(toCalendar(yesterday), toCalendar(now)));
	}

	@Test
	public void testIsSameLocalTime() throws Exception
	{
		Date d1 = toDate("2010-08-10 08:38:51 -0700", "yyyy-MM-dd HH:mm:ss Z");
		Date d2 = toDate("2010-08-10 15:38:51 -0000", "yyyy-MM-dd HH:mm:ss Z");
		Date d3 = toDate("2010-08-10 08:38:51 -0000", "yyyy-MM-dd HH:mm:ss Z");

		assertTrue(Dates.isSameLocalTime(d1, d2));
		assertTrue(Dates.isSameLocalTime(toCalendar(d1), toCalendar(d2)));
		assertFalse(Dates.isSameLocalTime(d1, d3));
		assertFalse(Dates.isSameLocalTime(toCalendar(d1), toCalendar(d3)));
	}

	@Test
	public void testIsSameDay() throws Exception
	{
		Date d1 = toDate("2012-10-20 08:38:51", "yyyy-MM-dd HH:mm:ss");
		Date d2 = toDate("2012-10-20 15:08:11", "yyyy-MM-dd HH:mm:ss");
		Date d3 = toDate("2012-10-21 15:08:11", "yyyy-MM-dd HH:mm:ss");

		assertTrue(Dates.isSameDay(d1, d2));
		assertTrue(Dates.isSameDay(toCalendar(d1), toCalendar(d2)));
		assertFalse(Dates.isSameDay(d1, d3));
		assertFalse(Dates.isSameDay(toCalendar(d1), toCalendar(d3)));
	}

	@Test
	public void testIsSameWeek() throws Exception
	{
		Date d1 = toDate("2014-08-05 08:38:51", "yyyy-MM-dd HH:mm:ss");
		Date d2 = toDate("2014-08-09 15:08:11", "yyyy-MM-dd HH:mm:ss");
		Date d3 = toDate("2014-08-12 15:08:11", "yyyy-MM-dd HH:mm:ss");

		assertTrue(Dates.isSameWeek(d1, d2));
		assertTrue(Dates.isSameWeek(toCalendar(d1), toCalendar(d2)));
		assertFalse(Dates.isSameWeek(d1, d3));
		assertFalse(Dates.isSameWeek(toCalendar(d1), toCalendar(d3)));
	}

	@Test
	public void testIsSameMonth() throws Exception
	{
		Date d1 = toDate("2014-08-05 08:38:51", "yyyy-MM-dd HH:mm:ss");
		Date d2 = toDate("2014-08-15 15:08:11", "yyyy-MM-dd HH:mm:ss");
		Date d3 = toDate("2014-09-15 15:08:11", "yyyy-MM-dd HH:mm:ss");

		assertTrue(Dates.isSameMonth(d1, d2));
		assertTrue(Dates.isSameMonth(toCalendar(d1), toCalendar(d2)));
		assertFalse(Dates.isSameMonth(d1, d3));
		assertFalse(Dates.isSameMonth(toCalendar(d1), toCalendar(d3)));
	}

	@Test
	public void testIsSameYear() throws Exception
	{
		Date d1 = toDate("2014-08-05 08:38:51", "yyyy-MM-dd HH:mm:ss");
		Date d2 = toDate("2014-02-15 15:08:11", "yyyy-MM-dd HH:mm:ss");
		Date d3 = toDate("2015-02-15 15:08:11", "yyyy-MM-dd HH:mm:ss");

		assertTrue(Dates.isSameYear(d1, d2));
		assertTrue(Dates.isSameYear(toCalendar(d1), toCalendar(d2)));
		assertFalse(Dates.isSameYear(d1, d3));
		assertFalse(Dates.isSameYear(toCalendar(d1), toCalendar(d3)));
	}

	@Test
	public void testParse() throws Exception
	{
		Date d = Dates.parse("2014-08-05 08:38:51", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss");
		Date expected = toDate("2014-08-05 08:38:51", "yyyy-MM-dd HH:mm:ss");
		assertEquals(expected, d);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseError()
	{
		Dates.parse("2014-08-05 08:38:51", "yyyy-MM-dd");
	}

	@Test
	public void testFormat() throws Exception
	{
		Date d = toDate("2014-08-05", "yyyy-MM-dd");
		assertEquals("2014-08-05", Dates.format(d, "yyyy-MM-dd"));
	}

	@Test
	public void testToCalendar()
	{
		Date d = new Date();
		assertEquals(d, Dates.toCalendar(d).getTime());
	}

	private Calendar toCalendar(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private Date toDate(String date, String format) throws Exception
	{
		return new SimpleDateFormat(format).parse(date);
	}
}
