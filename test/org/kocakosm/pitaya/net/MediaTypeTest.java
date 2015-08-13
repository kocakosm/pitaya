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

package org.kocakosm.pitaya.net;

import static org.kocakosm.pitaya.charset.Charsets.*;
import static org.kocakosm.pitaya.net.MediaType.*;
import static org.junit.Assert.*;

import java.nio.charset.IllegalCharsetNameException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * {@link MediaType}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class MediaTypeTest
{
	private static final Map<String, String> PARAMS = new HashMap<String, String>() {{
		put("a", "1");
		put("b", "2");
		put("c", "3");
	}};

	@Test
	public void testParse()
	{
		MediaType m1 = parse("image/png");
		assertEquals("image", m1.type());
		assertEquals("png", m1.subtype());
		assertTrue(m1.parameters().isEmpty());

		MediaType m2 = parse("Application/atom+XML; a=1; b=2; c=3");
		assertEquals("application", m2.type());
		assertEquals("atom+xml", m2.subtype());
		assertEquals(PARAMS, m2.parameters());

		MediaType m3 = parse("application/vnd.net-fpx;a.B-c=\"?\\\\?\\\"D\"");
		assertEquals("application", m3.type());
		assertEquals("vnd.net-fpx", m3.subtype());
		assertEquals("?\\?\"D", m3.parameters().get("a.b-c"));

		MediaType m4 = parse("Text/XML;  A=\"()<>@\\\\,;: \\\"/[]?=\";B=c");
		assertEquals("text", m4.type());
		assertEquals("xml", m4.subtype());
		assertEquals("()<>@\\,;: \"/[]?=", m4.parameters().get("a"));
		assertEquals("c", m4.parameters().get("b"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseEmptyString()
	{
		parse("");
	}

	@Test
	public void testParseInvalidString()
	{
		testParseFailure("/");
		testParseFailure("text");
		testParseFailure("text/");
		testParseFailure("te<t/plain");
		testParseFailure("text/pl@in");
		testParseFailure("text/plain;");
		testParseFailure("text/plain; ");
		testParseFailure("text/plain; a");
		testParseFailure("text/plain; a=");
		testParseFailure("text/plain; a=@");
		testParseFailure("text/plain; a=\"@");
		testParseFailure("text/plain; a=1;");
		testParseFailure("text/plain; a=1; ");
		testParseFailure("text/plain; a=1; b");
		testParseFailure("text/plain; a=1; b=");
		testParseFailure("text/plain; a=\u2025");
	}

	private void testParseFailure(String input)
	{
		IllegalArgumentException expected = null;
		try {
			parse(input);
		} catch (IllegalArgumentException e) {
			expected = e;
		}
		assertNotNull(expected);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithInvalidType()
	{
		create("?", "plain");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithInvalidSubtype()
	{
		create("text", "pl@in");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithWildcardTypeDeclaredSubtype()
	{
		create("*", "text");
	}

	@Test
	public void testType()
	{
		assertEquals("text", PLAIN_TEXT.type());
	}

	@Test
	public void testSubtype()
	{
		assertEquals("xml", XML.subtype());
	}

	@Test
	public void testParameters()
	{
		assertTrue(PNG.parameters().isEmpty());
		assertEquals(PARAMS, PNG.withParameters(PARAMS).parameters());
	}

	@Test
	public void testWithoutParameters()
	{
		assertEquals(OCTET_STREAM, OCTET_STREAM.withoutParameters());
		assertEquals(GIF, parse("image/gif; foo=bar").withoutParameters());
	}

	@Test
	public void testWithParameters()
	{
		assertEquals(
			parse("image/jpeg; a=1; b=2; c=3"),
			JPEG.withParameters(PARAMS));
		assertEquals(
			parse("text/plain; a=1; b=2; c=3"),
			parse("text/plain; a=0; b=2").withParameters(PARAMS));
		assertEquals(
			parse("text/plain; a=1; b=2; c=3"),
			parse("text/plain; a=1; b=2; c=3").withParameters(PARAMS));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithParametersWithInvalidAttribute()
	{
		HTML.withParameters(new HashMap<String, String>() {{
			put("a", "1");
			put("@", "2");
			put("c", "3");
		}});
	}

	@Test
	public void testWithoutParameter()
	{
		assertEquals(CSS, CSS.withoutParameter("a"));
		assertEquals(
			parse("text/plain; a=1"),
			parse("text/plain; a=1; b=2").withoutParameter("b"));
	}

	@Test
	public void testWithParameter()
	{
		assertEquals(
			parse("text/plain; a=1"),
			parse("text/plain").withParameter("a", "1"));
		assertEquals(
			parse("text/plain; a=1"),
			parse("text/plain; a=1; a=2").withParameter("a", "1"));
		assertEquals(
			parse("text/plain; a=3"),
			parse("text/plain; a=1; a=2").withParameter("a", "3"));
		assertEquals(
			parse("text/plain; a=1; b=2; c=3"),
			parse("text/plain; a=1; b=2").withParameter("c", "3"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithParameterWithInvalidAttribute()
	{
		JSON.withParameter("@", "2");
	}

	@Test
	public void testWithCharset()
	{
		assertEquals(
			parse("text/plain; charset=utf-8"),
			parse("text/plain").withCharset(UTF_8));
		assertEquals(
			parse("text/plain; charset=utf-8"),
			parse("text/plain; charset=utf-16").withCharset(UTF_8));
	}

	@Test
	public void testCharset()
	{
		MediaType m1 = PLAIN_TEXT;
		assertNull(m1.charset());
		m1 = m1.withParameter("charset", "utf-8");
		assertEquals(UTF_8, m1.charset());
		assertEquals("UTF-8", m1.parameters().get("charset"));

		MediaType m2 = parse("text/plain; CHARSET=utf-8");
		assertEquals(UTF_8, m2.charset());
		assertEquals("UTF-8", m2.parameters().get("charset"));
	}

	@Test(expected = IllegalCharsetNameException.class)
	public void testIllegalCharset()
	{
		parse("text/plain; charset=\"???\"").charset();
	}

	@Test
	public void testHasWildcard()
	{
		assertFalse(PLAIN_TEXT.hasWildcard());
		assertFalse(JPEG.hasWildcard());
		assertTrue(ANY_TYPE.hasWildcard());
		assertTrue(ANY_APPLICATION_TYPE.hasWildcard());
		assertTrue(ANY_AUDIO_TYPE.hasWildcard());
		assertTrue(ANY_IMAGE_TYPE.hasWildcard());
		assertTrue(ANY_TEXT_TYPE.hasWildcard());
		assertTrue(ANY_VIDEO_TYPE.hasWildcard());
	}

	@Test
	public void testIs()
	{
		assertTrue(PLAIN_TEXT.is(ANY_TYPE));
		assertTrue(JPEG.is(ANY_TYPE));
		assertTrue(ANY_TEXT_TYPE.is(ANY_TYPE));
		assertTrue(PLAIN_TEXT.is(ANY_TEXT_TYPE));
		assertTrue(PLAIN_TEXT.is(ANY_TEXT_TYPE));
		assertFalse(JPEG.is(ANY_TEXT_TYPE));
		assertTrue(PLAIN_TEXT.is(PLAIN_TEXT));
		assertFalse(PLAIN_TEXT.is(HTML));
		assertFalse(PLAIN_TEXT.is(PLAIN_TEXT.withCharset(UTF_8)));
		assertFalse(PLAIN_TEXT.withCharset(UTF_16).is(PLAIN_TEXT.withCharset(UTF_8)));
	}

	@Test
	public void testEqualsAndHashCode()
	{
		MediaType m0 = null;
		MediaType m1 = create("A", "B").withParameter("C", "D");
		MediaType m2 = parse("a/B;c=D");
		MediaType m3 = parse("A/b;  C=D");
		MediaType m4 = parse("a/b;c=e");
		MediaType m5 = parse("a/z;c=e");

		assertFalse(m1.equals(m0));
		assertEquals(m1, m1);
		assertEquals(m1.hashCode(), m1.hashCode());
		assertEquals(m1, m2);
		assertEquals(m2, m1);
		assertEquals(m2, m3);
		assertEquals(m1, m3);
		assertEquals(m1.hashCode(), m2.hashCode());
		assertEquals(m2.hashCode(), m3.hashCode());
		assertNotEquals(m1, m4);
		assertNotEquals(m4, m1);
		assertNotEquals(m2, m4);
		assertNotEquals(m4, m5);
		assertNotEquals(m2, m5);
		assertNotEquals(m3, m4);
		assertNotEquals(m4, m5);
	}

	@Test
	public void testToString()
	{
		assertEquals("text/plain", create("Text", "Plain").toString());
		MediaType test = PLAIN_TEXT.withParameter("a", "!@#$%\" ^&*()");
		assertEquals("text/plain; a=\"!@#$%\\\" ^&*()\"", test.toString());
	}
}
