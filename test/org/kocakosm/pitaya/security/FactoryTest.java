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
		assertEquals(MD2, Factory.getDigest(MD2));
		assertEquals(MD4, Factory.getDigest(MD4));
		assertEquals(MD5, Factory.getDigest(MD5));
		assertEquals(SHA1, Factory.getDigest(SHA1));
		assertEquals(SHA256, Factory.getDigest(SHA256));
		assertEquals(SHA512, Factory.getDigest(SHA512));
		assertEquals(KECCAK224, Factory.getDigest(KECCAK224));
		assertEquals(KECCAK256, Factory.getDigest(KECCAK256));
		assertEquals(KECCAK384, Factory.getDigest(KECCAK384));
		assertEquals(KECCAK512, Factory.getDigest(KECCAK512));
	}

	@Test
	public void testMACs()
	{
		byte[] key = new byte[0];
		assertEquals(HMAC_MD2, Factory.getMAC(HMAC_MD2, key));
		assertEquals(HMAC_MD4, Factory.getMAC(HMAC_MD4, key));
		assertEquals(HMAC_MD5, Factory.getMAC(HMAC_MD5, key));
		assertEquals(HMAC_SHA1, Factory.getMAC(HMAC_SHA1, key));
		assertEquals(HMAC_SHA256, Factory.getMAC(HMAC_SHA256, key));
		assertEquals(HMAC_SHA512, Factory.getMAC(HMAC_SHA512, key));
		assertEquals(HMAC_KECCAK224, Factory.getMAC(HMAC_KECCAK224, key));
		assertEquals(HMAC_KECCAK256, Factory.getMAC(HMAC_KECCAK256, key));
		assertEquals(HMAC_KECCAK384, Factory.getMAC(HMAC_KECCAK384, key));
		assertEquals(HMAC_KECCAK512, Factory.getMAC(HMAC_KECCAK512, key));
	}

	private void assertEquals(Algorithm algo, Object o)
	{
		Assert.assertEquals(algo.toString(), o.toString());
	}
}
