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

import static org.junit.Assert.*;

import org.pitaya.charset.ASCII;
import org.pitaya.util.Base16;

import org.junit.Test;

/**
 * {@link Digests}' unit tests.
 *
 * @author Osman KOCAK
 */
public final class DigestsTest
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
		Digest md2 = Digests.md2();
		assertThat(EMPTY_STRING).hashedWith(md2)
			.isEqualTo("8350e5a3e24c153df2275c9f80692773");
		assertThat(PANGRAM).hashedWith(md2)
			.isEqualTo("03d85a0d629d2c442e987525319fc471");
		assertEquals(16, md2.length());
		assertEquals("MD2", md2.toString());
	}

	@Test
	public void testMD4()
	{
		Digest md4 = Digests.md4();
		assertThat(EMPTY_STRING).hashedWith(md4)
			.isEqualTo("31d6cfe0d16ae931b73c59d7e0c089c0");
		assertThat(PANGRAM).hashedWith(md4)
			.isEqualTo("1bee69a46ba811185c194762abaeae90");
		assertEquals(16, md4.length());
		assertEquals("MD4", md4.toString());
	}

	@Test
	public void testMD5()
	{
		Digest md5 = Digests.md5();
		assertThat(EMPTY_STRING).hashedWith(md5)
			.isEqualTo("d41d8cd98f00b204e9800998ecf8427e");
		assertThat(PANGRAM).hashedWith(md5)
			.isEqualTo("9e107d9d372bb6826bd81d3542a419d6");
		assertEquals(16, md5.length());
		assertEquals("MD5", md5.toString());
	}

	@Test
	public void testSHA1()
	{
		Digest sha1 = Digests.sha1();
		assertThat(EMPTY_STRING).hashedWith(sha1)
			.isEqualTo("da39a3ee5e6b4b0d3255bfef95601890afd80709");
		assertThat(PANGRAM).hashedWith(sha1)
			.isEqualTo("2fd4e1c67a2d28fced849ee1bb76e7391b93eb12");
		assertEquals(20, sha1.length());
		assertEquals("SHA1", sha1.toString());
	}

	@Test
	public void testSHA256()
	{
		Digest sha256 = Digests.sha256();
		assertThat(EMPTY_STRING).hashedWith(sha256)
			.isEqualTo("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649"
				+ "b934ca495991b7852b855");
		assertThat(PANGRAM).hashedWith(sha256)
			.isEqualTo("d7a8fbb307d7809469ca9abcb0082e4f8d5651e46d3"
				+ "cdb762d02d0bf37c9e592");
		assertEquals(32, sha256.length());
		assertEquals("SHA-256", sha256.toString());
	}

	@Test
	public void testSHA512()
	{
		Digest sha512 = Digests.sha512();
		assertThat(EMPTY_STRING).hashedWith(sha512)
			.isEqualTo("cf83e1357eefb8bdf1542850d66d8007d620e4050b5"
				+ "715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d"
				+ "2877eec2f63b931bd47417a81a538327af927da3e");
		assertThat(PANGRAM).hashedWith(sha512)
			.isEqualTo("07e547d9586f6a73f73fbac0435ed76951218fb7d0c"
				+ "8d788a309d785436bbb642e93a252a954f23912547d1"
				+ "e8a3b5ed6e1bfd7097821233fa0538f3db854fee6");
		assertEquals(64, sha512.length());
		assertEquals("SHA-512", sha512.toString());
	}

	private static Input assertThat(String input)
	{
		return new Input(input);
	}

	private static final class Input
	{
		private final byte[] data;

		Input(String input)
		{
			this.data = ASCII.encode(input);
		}

		Result hashedWith(Digest digest)
		{
			return new Result(digest.digest(data));
		}
	}

	private static final class Result
	{
		private final byte[] digest;

		Result(byte[] digest)
		{
			this.digest = digest;
		}

		void isEqualTo(String expected)
		{
			assertArrayEquals(Base16.decode(expected), digest);
		}
	}
}
