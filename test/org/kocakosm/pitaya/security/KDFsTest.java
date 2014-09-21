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

import org.junit.Test;

/**
 * {@link KDFs}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class KDFsTest
{
	@Test
	public void testPBKDF1()
	{
		KDF pbkdf1 = KDFs.pbkdf1(Algorithm.SHA1, 4096, 20);
		assertArrayEquals(
			hex("2DE33AD2137E4650EA29FB13136F967B0A4508D9"),
			pbkdf1.deriveKey(ascii("password"), ascii("salt"))
		);
	}

	@Test
	public void testPBKDF2()
	{
		KDF pbkdf2 = KDFs.pbkdf2(Algorithm.HMAC_SHA1, 4096, 20);
		assertArrayEquals(
			hex("4B007901B765489ABEAD49D926F721D065A429C1"),
			pbkdf2.deriveKey(ascii("password"), ascii("salt"))
		);
	}

	@Test
	public void testHKDF()
	{
		KDF hkdf = KDFs.hkdf(Algorithm.HMAC_SHA1, ascii("info"), 20);
		assertArrayEquals(
			hex("DB8E037B0757A7AD231AD2CEFFFAD365CAD45C4F"),
			hkdf.deriveKey(ascii("password"), ascii("salt"))
		);
	}

	@Test
	public void testSCrypt()
	{
		KDF scrypt = KDFs.scrypt(8, 512, 16, 20);
		assertArrayEquals(
			hex("567C46E015DFCC5F2A14096DC1A851E5196C06EF"),
			scrypt.deriveKey(ascii("password"), ascii("salt"))
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
