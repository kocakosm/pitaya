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

import static org.kocakosm.pitaya.math.Numbers.*;
import static java.util.concurrent.TimeUnit.*;

import org.kocakosm.pitaya.util.Parameters;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A duration. Instances of this class are immutable. Implementation note: the
 * duration is stored as milliseconds in a {@code long} value, thus some methods
 * on this class are subject to overflow.
 *
 * @author Osman KOCAK
 */
public final class Duration implements Comparable<Duration>, Serializable
{
	private static final long serialVersionUID = 2629881563004939761L;

	/** Zero millisecond. */
	public static final Duration ZERO = new Duration(0L);

	/** One millisecond. */
	public static final Duration ONE_MILLISECOND = new Duration(1L);

	/** One second. */
	public static final Duration ONE_SECOND = new Duration(1000L);

	/** One minute. */
	public static final Duration ONE_MINUTE = new Duration(60000L);

	/** One hour. */
	public static final Duration ONE_HOUR = new Duration(3600000L);

	/** One day. */
	public static final Duration ONE_DAY = new Duration(86400000L);

	/** One week. */
	public static final Duration ONE_WEEK = new Duration(604800000L);

	/**
	 * Parses the given duration's {@code String} representation into a
	 * {@code Duration} instance. The given {@code String} must be a list
	 * of value/unit pairs separated by ' ', '+', '-', '\n', '\t', '\r',
	 * {@code '&'} or ',' characters or by the "and" {@code String}. Values
	 * must be integer (either positive or negative) values. Valid units are:
	 * "milliseconds", "millisecond", "millis" and "ms" for milliseconds,
	 * "seconds", "second", "sec", "secs" and "s" for seconds, "minutes",
	 * "minute", "min", "mins" and "m" for minutes, "hours", "hour" and "h"
	 * for hours, "days", "day" and "d" for days. Values must be separated
	 * from their unit by a whitespace character. Parsing is not case
	 * sensitive.
	 *
	 * @param duration the {@code String} to parse into a {@code Duration}.
	 *
	 * @return the parsed {@code Duration}.
	 *
	 * @throws NullPointerException if {@code duration} is {@code null}.
	 * @throws IllegalArgumentException if {@code duration} can't be parsed.
	 * @throws ArithmeticException if the total duration value overflows the
	 *	storage capacity of this class.
	 */
	public static Duration valueOf(String duration)
	{
		String[] tokens = duration.toLowerCase().replace("and", "")
			.replaceAll("[,&\\+]", " ").replaceAll("-\\s+", "-")
			.replace("--", "").replaceAll("[\n\t\r]", " ").trim()
			.replaceAll("\\s+", " ").split("\\s");
		Parameters.checkCondition(tokens.length % 2 == 0);
		long ms = 0L;
		for (int i = 0; i < tokens.length; i += 2) {
			long v = Long.parseLong(tokens[i]);
			TimeUnit u = parseTimeUnit(tokens[i + 1]);
			ms = safeAdd(ms, MILLISECONDS.convert(v, u));
		}
		return new Duration(ms);
	}

	private static TimeUnit parseTimeUnit(String unit)
	{
		for (Unit u : Unit.values()) {
			for (String name : u.names) {
				if (unit.equals(name)) {
					return u.unit;
				}
			}
		}
		throw new IllegalArgumentException("Unknown unit: " + unit);
	}

	private enum Unit
	{
		MS(MILLISECONDS, "milliseconds", "millisecond", "millis", "ms"),
		S(SECONDS, "seconds", "second", "sec", "secs", "s"),
		M(MINUTES, "minutes", "minute", "min", "mins", "m"),
		H(HOURS, "hours", "hour", "h"),
		D(DAYS, "days", "day", "d");

		private final TimeUnit unit;
		private final String[] names;

		private Unit(TimeUnit unit, String... names)
		{
			this.unit = unit;
			this.names = names;
		}
	}

	/**
	 * Creates a new {@code Duration} representing the given amount in the
	 * given unit.
	 *
	 * @param amount the amount.
	 * @param unit the unit in which the amout is given.
	 *
	 * @return the created {@code Duration} instance.
	 *
	 * @throws NullPointerException if {@code unit} is {@code null}.
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public static Duration of(long amount, TimeUnit unit)
	{
		long duration = MILLISECONDS.convert(amount, unit);
		if (unit.convert(duration, MILLISECONDS) != amount) {
			throw new ArithmeticException(
				"Too large duration: " + amount + " " + unit);
		}
		return new Duration(duration);
	}

	/**
	 * Creates a new {@code Duration} instance by summing all the given
	 * {@code Duration}s' values.
	 *
	 * @param durations the {@code Duration}s to sum.
	 *
	 * @return the created {@code Duration}.
	 *
	 * @throws NullPointerException if {@code durations} is {@code null} or
	 *	if it contains {@code null} reference.
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public static Duration of(Duration... durations)
	{
		if (durations.length == 0) {
			return new Duration(0L);
		}
		long ms = durations[0].milliseconds;
		for (int i = 1; i < durations.length; i++) {
			ms = safeAdd(ms, durations[i].milliseconds);
		}
		return new Duration(ms);
	}

	/**
	 * Creates a new {@code Duration} instance representing the amount of
	 * time elapsed between the given dates.
	 *
	 * @param start the start date.
	 * @param end the end date.
	 *
	 * @return the duration between {@code start} and {@code end}.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 */
	public static Duration between(Date start, Date end)
	{
		return new Duration(end.getTime() - start.getTime());
	}

	/**
	 * Creates a new {@code Duration} instance representing the amount of
	 * time elapsed since the given date.
	 *
	 * @param d the start date.
	 *
	 * @return the duration since {@code d}.
	 *
	 * @throws NullPointerException if {@code d} is {@code null}.
	 */
	public static Duration since(Date d)
	{
		return new Duration(now() - d.getTime());
	}

	/**
	 * Creates a new {@code Duration} instance representing the amount of
	 * time that will elapse until the given date.
	 *
	 * @param d the end date.
	 *
	 * @return the duration until {@code d}.
	 *
	 * @throws NullPointerException if {@code d} is {@code null}.
	 */
	public static Duration until(Date d)
	{
		return new Duration(d.getTime() - now());
	}

	private static long now()
	{
		return System.currentTimeMillis();
	}

	private final long milliseconds;

	private Duration(long milliseconds)
	{
		this.milliseconds = milliseconds;
	}

	/**
	 * Returns the result of adding the given duration to this duration.
	 *
	 * @param d the duration to add.
	 *
	 * @return the sum of this duration and the given one.
	 *
	 * @throws NullPointerException if {@code d} is {@code null}.
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public Duration plus(Duration d)
	{
		return new Duration(safeAdd(milliseconds, d.milliseconds));
	}

	/**
	 * Returns the result of subtracting the given duration from this one.
	 *
	 * @param d the duration to subtract.
	 *
	 * @return the difference between this duration and the given one.
	 *
	 * @throws NullPointerException if {@code d} is {@code null}.
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public Duration minus(Duration d)
	{
		return new Duration(safeSubtract(milliseconds, d.milliseconds));
	}

	/**
	 * Returns the result of multiplying this duration by the given factor.
	 *
	 * @param factor the multiplication factor.
	 *
	 * @return the product of this duration by the given factor.
	 *
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public Duration multipliedBy(int factor)
	{
		return new Duration(safeMultiply(milliseconds, factor));
	}

	/**
	 * Returns the result of dividing this duration by the given divisor.
	 *
	 * @param divisor the divisor.
	 *
	 * @return the resulting duration.
	 *
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public Duration dividedBy(int divisor)
	{
		return new Duration(safeDivide(milliseconds, divisor));
	}

	/**
	 * Returns the sign of this duration, namely it returns {@code 1} if
	 * this duration is positive, {@code 0} if it is equal to {@code 0}, and
	 * {@code -1} if it is negative.
	 *
	 * @return the sign of this duration.
	 */
	public int sign()
	{
		return milliseconds > 0L ? 1 : milliseconds < 0L ? -1 : 0;
	}

	/**
	 * Returns the result of negating this duration.
	 *
	 * @return the negated duration.
	 *
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public Duration negated()
	{
		return new Duration(safeNegate(milliseconds));
	}

	/**
	 * Returns a copy of this duration with a positive length.
	 *
	 * @return a copy of this duration with a positive length.
	 *
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public Duration absoluteValue()
	{
		return new Duration(safeAbs(milliseconds));
	}

	/**
	 * Returns the date obtained by adding this duration to the given date.
	 *
	 * @param d the origin date.
	 *
	 * @return the date obtained by adding this duration to the given date.
	 *
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public Date after(Date d)
	{
		return new Date(safeAdd(d.getTime(), milliseconds));
	}

	/**
	 * Returns the date obtained by subtracting this duration from the given
	 * date.
	 *
	 * @param d the origin date.
	 *
	 * @return the date obtained by subtracting this duration from the given
	 * date.
	 *
	 * @throws ArithmeticException if the resulting duration is too large.
	 */
	public Date before(Date d)
	{
		return new Date(safeSubtract(d.getTime(), milliseconds));
	}

	/**
	 * Converts this duration into the given unit. Beware of possible
	 * precision loss.
	 *
	 * @param unit the target unit.
	 *
	 * @return the value of the duration in the given unit.
	 *
	 * @throws NullPointerException if {@code unit} is {@code null}.
	 */
	public long to(TimeUnit unit)
	{
		return unit.convert(milliseconds, MILLISECONDS);
	}

	/**
	 * Returns the number of milliseconds in this duration.
	 *
	 * @return the number of milliseconds in this duration.
	 */
	public long toMilliseconds()
	{
		return milliseconds;
	}

	@Override
	public int compareTo(Duration d)
	{
		try {
			long c = safeSubtract(milliseconds, d.milliseconds);
			return c > 0L ? 1 : c < 0L ? -1 : 0;
		} catch (ArithmeticException e) {
			return sign();
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof Duration)) {
			return false;
		}
		final Duration d = (Duration) o;
		return milliseconds == d.milliseconds;
	}

	@Override
	public int hashCode()
	{
		return (int) (milliseconds ^ (milliseconds >>> 32));
	}

	@Override
	public String toString()
	{
		if (milliseconds == 0L) {
			return "0 millisecond";
		}
		StringBuilder sb = new StringBuilder();
		long time = safeAbs(milliseconds);
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
		if (milliseconds < 0L) {
			sb.insert(0, "-(");
			sb.append(")");
		}
		return sb.toString();
	}

	private void append(StringBuilder sb, long value, String unit)
	{
		if (value > 0L) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(value).append(' ').append(unit);
			if (value > 1L) {
				sb.append('s');
			}
		}
	}
}
