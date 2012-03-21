/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012 Osman KOCAK <kocakosm@gmail.com>                        *
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

package org.pitaya.hmac;

import static org.junit.Assert.*;

import org.pitaya.charset.ASCII;
import org.pitaya.util.Base16;

import org.junit.Test;

/**
 * HMACs unit tests.
 *
 * @author Osman KOCAK
 */
public final class HMACsTest
{
	private static final String PANGRAM;
	private static final String EMPTY_STRING;
	static {
		PANGRAM = "The quick brown fox jumps over the lazy dog";
		EMPTY_STRING = "";
	}

	@Test
	public void testMD5()
	{
		HMAC hmac = HMACs.md5(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("74e6f7298a9c2d168935f58c001bad88"),
			hmac.hmac(ascii(EMPTY_STRING))
		);
		hmac = HMACs.md5(ascii("key"));
		assertArrayEquals(
			hex("80070713463e7749b90c2dc24911e275"),
			hmac.hmac(ascii(PANGRAM))
		);
	}

	@Test
	public void testSHA1()
	{
		HMAC hmac = HMACs.sha1(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("fbdb1d1b18aa6c08324b7d64b71fb76370690e1d"),
			hmac.hmac(ascii(EMPTY_STRING))
		);
		hmac = HMACs.sha1(ascii("key"));
		assertArrayEquals(
			hex("de7c9b85b8b78aa6bc8a7a36f70a90701c9db4d9"),
			hmac.hmac(ascii(PANGRAM))
		);
	}

	@Test
	public void testSHA256()
	{
		HMAC hmac = HMACs.sha256(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("b613679a0814d9ec772f95d778c35fc5ff1697c493715653c6"
				+ "c712144292c5ad"),
			hmac.hmac(ascii(EMPTY_STRING))
		);
		hmac = HMACs.sha256(ascii("key"));
		assertArrayEquals(
			hex("f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997"
				+ "479dbc2d1a3cd8"),
			hmac.hmac(ascii(PANGRAM))
		);
	}

	private byte[] hex(String hex)
	{
		return Base16.decode(hex);
	}

	private byte[] ascii(String str)
	{
		return ASCII.encode(str);
	}
}
