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

package org.kocakosm.pitaya.security;

import static org.kocakosm.pitaya.security.Algorithm.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link Factory}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class FactoryTest
{
	@Test
	public void testDigests()
	{
		assertEquals(MD2, Factory.newDigest(MD2));
		assertEquals(MD4, Factory.newDigest(MD4));
		assertEquals(MD5, Factory.newDigest(MD5));
		assertEquals(SHA1, Factory.newDigest(SHA1));
		assertEquals(SHA256, Factory.newDigest(SHA256));
		assertEquals(SHA512, Factory.newDigest(SHA512));
		assertEquals(KECCAK224, Factory.newDigest(KECCAK224));
		assertEquals(KECCAK256, Factory.newDigest(KECCAK256));
		assertEquals(KECCAK384, Factory.newDigest(KECCAK384));
		assertEquals(KECCAK512, Factory.newDigest(KECCAK512));
	}

	@Test
	public void testMACs()
	{
		byte[] key = new byte[0];
		assertEquals(HMAC_MD2, Factory.newMAC(HMAC_MD2, key));
		assertEquals(HMAC_MD4, Factory.newMAC(HMAC_MD4, key));
		assertEquals(HMAC_MD5, Factory.newMAC(HMAC_MD5, key));
		assertEquals(HMAC_SHA1, Factory.newMAC(HMAC_SHA1, key));
		assertEquals(HMAC_SHA256, Factory.newMAC(HMAC_SHA256, key));
		assertEquals(HMAC_SHA512, Factory.newMAC(HMAC_SHA512, key));
		assertEquals(HMAC_KECCAK224, Factory.newMAC(HMAC_KECCAK224, key));
		assertEquals(HMAC_KECCAK256, Factory.newMAC(HMAC_KECCAK256, key));
		assertEquals(HMAC_KECCAK384, Factory.newMAC(HMAC_KECCAK384, key));
		assertEquals(HMAC_KECCAK512, Factory.newMAC(HMAC_KECCAK512, key));
	}

	private void assertEquals(Algorithm algo, Object o)
	{
		Assert.assertEquals(algo.toString(), o.toString());
	}
}
