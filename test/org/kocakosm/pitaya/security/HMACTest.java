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

import static org.junit.Assert.assertArrayEquals;

import org.kocakosm.pitaya.charset.ASCII;
import org.kocakosm.pitaya.util.Base16;

import java.io.ByteArrayInputStream;

import org.junit.Test;

/**
 * {@link HMAC}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class HMACTest
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
		MAC hmac = HMAC.md2(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("6f6e031223b36cd2a997787a03d16bf5"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.md2(ascii("key"));
		assertArrayEquals(
			hex("13758b9534bfb38d850457814613b0c1"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testMD4()
	{
		MAC hmac = HMAC.md4(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("c8d444e3153b538850e7850fa84bb247"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.md4(ascii("key"));
		assertArrayEquals(
			hex("8d3366c440a9c65124ab0b5f4ca27338"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testMD5()
	{
		MAC hmac = HMAC.md5(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("74e6f7298a9c2d168935f58c001bad88"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.md5(ascii("key"));
		assertArrayEquals(
			hex("80070713463e7749b90c2dc24911e275"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testSHA1()
	{
		MAC hmac = HMAC.sha1(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("fbdb1d1b18aa6c08324b7d64b71fb76370690e1d"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.sha1(ascii("key"));
		assertArrayEquals(
			hex("de7c9b85b8b78aa6bc8a7a36f70a90701c9db4d9"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testSHA256()
	{
		MAC hmac = HMAC.sha256(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("b613679a0814d9ec772f95d778c35fc5ff1697c493715653c6"
				+ "c712144292c5ad"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.sha256(ascii("key"));
		assertArrayEquals(
			hex("f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997"
				+ "479dbc2d1a3cd8"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testSHA512()
	{
		MAC hmac = HMAC.sha512(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("b936cee86c9f87aa5d3c6f2e84cb5a4239a5fe50480a6ec66b"
				+ "70ab5b1f4ac6730c6c515421b327ec1d69402e53dfb4"
				+ "9ad7381eb067b338fd7b0cb22247225d47"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.sha512(ascii("key"));
		assertArrayEquals(
			hex("b42af09057bac1e2d41708e48a902e09b5ff7f12ab428a4fe8"
				+ "6653c73dd248fb82f948a549f7b791a5b41915ee4d1e"
				+ "c3935357e4e2317250d0372afa2ebeeb3a"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testKeccak224()
	{
		MAC hmac = HMAC.keccak224(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("5b71601b23a34517acd63847e99e461baf705e71884b986b4c"
				+ "f64e19"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.keccak224(ascii("key"));
		assertArrayEquals(
			hex("763e70a1ec866fbc1c6e6c398cd6e2383e2ad3aecbb3d6150f"
				+ "1e56fd"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testKeccak256()
	{
		MAC hmac = HMAC.keccak256(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("042186ec4e98680a0866091d6fb89b60871134b44327f8f467"
				+ "c14e9841d3e97b"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.keccak256(ascii("key"));
		assertArrayEquals(
			hex("74547bc8c8e1ef02aec834ca60ff24cc316d4c2244a360fe17"
				+ "448cb53410bed4"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testKeccak384()
	{
		MAC hmac = HMAC.keccak384(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("09e18efb747979fcbc89849ff5c388a763c5fed1836a2850b7"
				+ "9598eaa3a5cf014d57e337923e69c6f4a2dade032b02"
				+ "40"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.keccak384(ascii("key"));
		assertArrayEquals(
			hex("73acb07b5b1db5431758262b55e5923d362de4492229a74203"
				+ "02c80d4348ca1b11ecea06fb1c232f9b832aadca8cd2"
				+ "89"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testKeccak512()
	{
		MAC hmac = HMAC.keccak512(ascii(EMPTY_STRING));
		assertArrayEquals(
			hex("a857c9f1cd9cb25c2f24a933618abfc724d4eaebf74099dddc"
				+ "5e0a1c7ab8de3865faa1fb039419de768e962bdc81a6"
				+ "d85b9809d6ab84f8cc984f5d6b90f23c15"),
			hmac.mac(ascii(EMPTY_STRING))
		);
		hmac = HMAC.keccak512(ascii("key"));
		assertArrayEquals(
			hex("22fb03b3391bc0adfc73c18e0919d9f142390e81d6cc268971"
				+ "6ac53ab75458a718059d58cfbb23c6a416c32b8afa84"
				+ "a9a7a9d852312a743bef0a55148e5a1b8a"),
			hmac.mac(ascii(PANGRAM))
		);
	}

	@Test
	public void testMacInputStream() throws Exception
	{
		MAC hmac = HMAC.sha1(ascii("key"));
		assertArrayEquals(
			hex("de7c9b85b8b78aa6bc8a7a36f70a90701c9db4d9"),
			hmac.mac(new ByteArrayInputStream(ascii(PANGRAM))));
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
