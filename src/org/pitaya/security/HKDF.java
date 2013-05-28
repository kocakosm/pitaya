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

import org.pitaya.util.ByteBuffer;
import org.pitaya.util.Parameters;

/**
 * HMAC-based Key Derivation Function (RFC 5869). Thread-safe.
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
	 * @param dkLen the desired length for derived keys.
	 * 
	 * @throws NullPointerException if {@code algorithm} is {@code null}.
	 * @throws IllegalArgumentException if {@code dkLen} is negative, or if
	 *	the MAC algorithm is unknown, or if {@code dkLen} is greater 
	 *	than 255 * MAC algorithm's output length.
	 */
	HKDF(Algorithm<MAC> algorithm, byte[] info, int dkLen)
	{
		Parameters.checkCondition(dkLen > 0);
		MAC mac = Factory.getMAC(algorithm, new byte[0]);
		Parameters.checkCondition(dkLen <= 255 * mac.length());
		this.algorithm = algorithm;
		this.dkLen = dkLen;
		this.info = info == null ? new byte[0] : info;
	}

	@Override
	public byte[] deriveKey(byte[] secret, byte[] salt)
	{
		return expand(extract(secret, salt));
	}

	private byte[] extract(byte[] key, byte[] salt)
	{
		return Factory.getMAC(algorithm, salt).mac(key);
	}

	private byte[] expand(byte[] key)
	{
		MAC mac = Factory.getMAC(algorithm, key);
		ByteBuffer t = new ByteBuffer(dkLen + mac.length());
		int n = (int) Math.ceil((double) dkLen / mac.length());
		byte[] u = new byte[0];
		for (int i = 1; i <= n; i++) {
			mac.update(u);
			mac.update(info);
			mac.update((byte) i);
			u = mac.mac();
			t.append(u);
		}
		return t.toByteArray(0, dkLen);
	}
}
