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

package org.pitaya.security;

import static org.junit.Assert.assertArrayEquals;

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
	public void testMD2()
	{
		MAC hmac = HMACs.md2(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("6f6e031223b36cd2a997787a03d16bf5"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMACs.md2(ascii("key"));
		assertArrayEquals(
			hex("13758b9534bfb38d850457814613b0c1"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testMD4()
	{
		MAC hmac = HMACs.md4(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("c8d444e3153b538850e7850fa84bb247"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMACs.md4(ascii("key"));
		assertArrayEquals(
			hex("8d3366c440a9c65124ab0b5f4ca27338"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testMD5()
	{
		MAC hmac = HMACs.md5(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("74e6f7298a9c2d168935f58c001bad88"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMACs.md5(ascii("key"));
		assertArrayEquals(
			hex("80070713463e7749b90c2dc24911e275"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testSHA1()
	{
		MAC hmac = HMACs.sha1(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("fbdb1d1b18aa6c08324b7d64b71fb76370690e1d"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMACs.sha1(ascii("key"));
		assertArrayEquals(
			hex("de7c9b85b8b78aa6bc8a7a36f70a90701c9db4d9"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testSHA256()
	{
		MAC hmac = HMACs.sha256(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("b613679a0814d9ec772f95d778c35fc5ff1697c493715653c6"
				+ "c712144292c5ad"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMACs.sha256(ascii("key"));
		assertArrayEquals(
			hex("f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997"
				+ "479dbc2d1a3cd8"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testSHA512()
	{
		MAC hmac = HMACs.sha512(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("b936cee86c9f87aa5d3c6f2e84cb5a4239a5fe50480a6ec66b"
				+ "70ab5b1f4ac6730c6c515421b327ec1d69402e53dfb4"
				+ "9ad7381eb067b338fd7b0cb22247225d47"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMACs.sha512(ascii("key"));
		assertArrayEquals(
			hex("b42af09057bac1e2d41708e48a902e09b5ff7f12ab428a4fe8"
				+ "6653c73dd248fb82f948a549f7b791a5b41915ee4d1e"
				+ "c3935357e4e2317250d0372afa2ebeeb3a"),
			hmac.mac(ascii(PANGRAM))
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
