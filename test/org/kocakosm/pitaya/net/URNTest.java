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

package org.kocakosm.pitaya.net;

import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Test;

/**
 * {@link URN}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class URNTest
{
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithInvalidNID()
	{
		URN.create("urn:urn:nss");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithInvalidHexCharacter()
	{
		URN.create("urn:pitaya:%0s");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithInvalidUTF8Encoding()
	{
		URN.create("urn:pitaya:%15%d2");
	}

	@Test
	public void testComponentsConstructor() throws URNSyntaxException
	{
		URN urn = new URN("nid", "nss");
		assertEquals("nid", urn.nid());
		assertEquals("nss", urn.nss());
	}

	@Test
	public void testNID()
	{
		URN urn = URN.create("uRn:nid:nss");
		assertEquals("nid", urn.nid());
	}

	@Test
	public void testNSS()
	{
		URN urn = URN.create("uRn:nid:nss");
		assertEquals("nss", urn.nss());
	}

	@Test
	public void testNormalization()
	{
		URN urn = URN.create("uRn:nID:NSS%C3%A7").normalized();
		assertEquals("nid", urn.nid());
		assertEquals("NSS%c3%a7", urn.nss());
	}

	@Test
	public void testToURI()
	{
		URI uri = URN.create("urn:nid:nss").toURI();
		assertEquals("urn", uri.getScheme());
	}

	@Test
	public void testToString()
	{
		URN urn = URN.create("uRn:nID:NSS%C3%A7");
		assertEquals("uRn:nID:NSS%C3%A7", urn.toString());
		assertEquals("urn:nid:NSS%c3%a7", urn.normalized().toString());
	}

	@Test
	public void testEqualsAndHashCode()
	{
		URN urn0 = null;
		URN urn1 = URN.create("URN:foo:a123,456");
		URN urn2 = URN.create("urn:foo:a123,456");
		URN urn3 = URN.create("urn:FOO:a123,456");
		URN urn4 = URN.create("urn:foo:A123,456");
		URN urn5 = URN.create("urn:foo:a123%2C456");
		URN urn6 = URN.create("URN:FOO:a123%2c456");

		assertFalse(urn1.equals(urn0));
		assertEquals(urn1, urn1);
		assertEquals(urn1.hashCode(), urn1.hashCode());
		assertEquals(urn1, urn2);
		assertEquals(urn2, urn3);
		assertEquals(urn1, urn3);
		assertEquals(urn1.hashCode(), urn2.hashCode());
		assertEquals(urn2.hashCode(), urn3.hashCode());
		assertNotEquals(urn1, urn5);
		assertNotEquals(urn2, urn6);
		assertNotEquals(urn4, urn1);
		assertNotEquals(urn4, urn2);
		assertNotEquals(urn4, urn3);
		assertNotEquals(urn4, urn5);
		assertNotEquals(urn4, urn6);
		assertEquals(urn5, urn6);
		assertEquals(urn6, urn5);
		assertEquals(urn5.hashCode(), urn6.hashCode());
		assertNotEquals(urn5, urn2);
		assertNotEquals(urn6, urn1);
	}
}
