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

import org.kocakosm.pitaya.util.BaseEncoding;
import org.kocakosm.pitaya.util.ByteBuffer;
import org.kocakosm.pitaya.util.Parameters;
import org.kocakosm.pitaya.util.XArrays;
import org.kocakosm.pitaya.util.XObjects;

/**
 * HMAC-based Key Derivation Function (RFC 5869). Instances of this class are
 * immutable.
 *
 * @author Osman KOCAK
 */
final class HKDF implements KDF
{
	private final int dkLen;
	private final byte[] info;
	private final Algorithm<MAC> algorithm;

	/**
	 * Creates a new {@code HKDF} instance.
	 *
	 * @param algorithm the MAC algorithm to use.
	 * @param info optional context and application specific information,
	 *	may be {@code null} or empty.
	 * @param dkLen the desired length for derived keys, in bytes.
	 *
	 * @throws NullPointerException if {@code algorithm} is {@code null}.
	 * @throws IllegalArgumentException if {@code dkLen} is negative, or if
	 *	the MAC algorithm is unknown, or if {@code dkLen} is greater
	 *	than 255 * MAC algorithm's output length.
	 */
	HKDF(Algorithm<MAC> algorithm, byte[] info, int dkLen)
	{
		Parameters.checkCondition(dkLen > 0);
		MAC mac = Factory.newMAC(algorithm, new byte[0]);
		Parameters.checkCondition(dkLen <= 255 * mac.length());
		this.algorithm = algorithm;
		this.dkLen = dkLen;
		this.info = info == null ? new byte[0] : XArrays.copyOf(info);
	}

	@Override
	public byte[] deriveKey(byte[] secret, byte[] salt)
	{
		return expand(extract(secret, salt));
	}

	private byte[] extract(byte[] key, byte[] salt)
	{
		return Factory.newMAC(algorithm, salt).digest(key);
	}

	private byte[] expand(byte[] key)
	{
		MAC mac = Factory.newMAC(algorithm, key);
		ByteBuffer t = new ByteBuffer(dkLen + mac.length());
		int n = (int) Math.ceil((double) dkLen / mac.length());
		byte[] u = new byte[0];
		for (int i = 1; i <= n; i++) {
			u = mac.update(u).update(info).digest((byte) i);
			t.append(u);
		}
		return t.toByteArray(0, dkLen);
	}

	@Override
	public String toString()
	{
		return XObjects.toStringBuilder("HKDF").append("MAC", algorithm)
			.append("info", "0x" + BaseEncoding.BASE_16.encode(info))
			.append("dkLen", dkLen).toString();
	}
}
