/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2013 Osman KOCAK <kocakosm@gmail.com>                   *
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

package org.pitaya.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * {@link Algorithm}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class AlgorithmTest
{
	@Test
	public void testToString()
	{
		assertEquals("MD2", Algorithm.MD2.toString());
		assertEquals("MD4", Algorithm.MD4.toString());
		assertEquals("MD5", Algorithm.MD5.toString());
		assertEquals("SHA1", Algorithm.SHA1.toString());
		assertEquals("SHA-256", Algorithm.SHA256.toString());
		assertEquals("SHA-512", Algorithm.SHA512.toString());
		assertEquals("Keccak-224", Algorithm.KECCAK224.toString());
		assertEquals("Keccak-256", Algorithm.KECCAK256.toString());
		assertEquals("Keccak-384", Algorithm.KECCAK384.toString());
		assertEquals("Keccak-512", Algorithm.KECCAK512.toString());
		assertEquals("HMAC-MD2", Algorithm.HMAC_MD2.toString());
		assertEquals("HMAC-MD4", Algorithm.HMAC_MD4.toString());
		assertEquals("HMAC-MD5", Algorithm.HMAC_MD5.toString());
		assertEquals("HMAC-SHA1", Algorithm.HMAC_SHA1.toString());
		assertEquals("HMAC-SHA-256", Algorithm.HMAC_SHA256.toString());
		assertEquals("HMAC-SHA-512", Algorithm.HMAC_SHA512.toString());
		assertEquals("HMAC-Keccak-224", Algorithm.HMAC_KECCAK224.toString());
		assertEquals("HMAC-Keccak-256", Algorithm.HMAC_KECCAK256.toString());
		assertEquals("HMAC-Keccak-384", Algorithm.HMAC_KECCAK384.toString());
		assertEquals("HMAC-Keccak-512", Algorithm.HMAC_KECCAK512.toString());
	}
}
