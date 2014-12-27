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

package org.kocakosm.pitaya.net;

import org.kocakosm.pitaya.charset.ASCII;
import org.kocakosm.pitaya.charset.Charsets;
import org.kocakosm.pitaya.util.BaseEncoding;
import org.kocakosm.pitaya.util.Parameters;

import java.io.Serializable;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Uniform Resource Name (URN) as specified in RFC 2141. Instances
 * of this class are immutable.
 *
 * @see <a href="https://www.ietf.org/rfc/rfc2141.txt">RFC 2141</a>
 *
 * @author Osman KOCAK
 */
public final class URN implements Serializable
{
	private static final long serialVersionUID = 6295350526747423877L;
	private static final Pattern PATTERN;
	static {
		String regex = "(^urn):([a-z0-9][a-z0-9\\-]{0,31}):"
			+ "(([a-z0-9()+,\\-.:=@;$_!*']|%[0-9a-f]{2})+$)";
		PATTERN = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	}

	/**
	 * Creates a new {@code URN} by parsing the given {@code String}. This
	 * function invokes the {@link URN#URN(java.lang.String)} constructor;
	 * any {@code URNSyntaxException} thrown by the constructor is caught
	 * and wrapped in a new {@code IllegalArgumentException}, which is then
	 * thrown. This method is provided for use in situations where it is
	 * known that the given {@code String} is a legal {@code URN}.
	 *
	 * @param urn the {@code String} to be parsed into a {@code URN}.
	 *
	 * @return the created {@code URN}.
	 *
	 * @throws NullPointerException if {@code urn} is {@code null}.
	 * @throws IllegalArgumentException if {@code urn} violates RFC 2141.
	 */
	public static URN create(String urn)
	{
		try {
			return new URN(urn);
		} catch (URNSyntaxException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	private final String scheme;
	private final String nid;
	private final String nss;

	/**
	 * Creates a new {@code URN} by parsing the given {@code String}. This
	 * constructor parses the given {@code String} exactly as specified by
	 * the grammar in RFC 2141.
	 *
	 * @param urn the {@code String} to be parsed into a {@code URN}.
	 *
	 * @throws NullPointerException if {@code urn} is {@code null}.
	 * @throws URNSyntaxException if {@code urn} violates RFC 2141.
	 */
	public URN(String urn) throws URNSyntaxException
	{
		Matcher m = PATTERN.matcher(Parameters.checkNotNull(urn));
		if (m.matches()) {
			this.scheme = m.group(1);
			this.nid = m.group(2);
			this.nss = m.group(3);
			checkNID(this.nid);
			checkNSS(this.nss);
		} else {
			throw new URNSyntaxException("Invalid URN: " + urn);
		}
	}

	/**
	 * Creates a new {@code URN}.
	 *
	 * @param nid the Namespace Identifier.
	 * @param nss the Namespace Specific String.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws URNSyntaxException if the {@code URN} constructed from the
	 *	given components violates RFC 2141.
	 */
	public URN(String nid, String nss) throws URNSyntaxException
	{
		this("urn:" + Parameters.checkNotNull(nid) + ":"
			+ Parameters.checkNotNull(nss));
	}

	private URN(String scheme, String nid, String nss)
	{
		this.scheme = scheme;
		this.nid = nid;
		this.nss = nss;
	}

	private void checkNID(String nid) throws URNSyntaxException
	{
		if ("urn".equalsIgnoreCase(nid)) {
			throw new URNSyntaxException("Invalid NID: " + nid);
		}
	}

	private void checkNSS(String nss) throws URNSyntaxException
	{
		int len = nss.length();
		char[] chars = nss.toCharArray();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			if (chars[i] == '%') {
				sb.append(chars[++i]).append(chars[++i]);
				if (i == len - 1 || chars[i + 1] != '%') {
					decodeUTF8(decodeBase16(sb.toString()));
					sb.setLength(0);
				}
			}
		}
	}

	private String decodeUTF8(byte[] utf8) throws URNSyntaxException
	{
		CharsetDecoder decoder = Charsets.UTF_8.newDecoder();
		try {
			return decoder.decode(ByteBuffer.wrap(utf8)).toString();
		} catch (CharacterCodingException ex) {
			throw new URNSyntaxException(ex);
		}
	}

	private byte[] decodeBase16(String hex) throws URNSyntaxException
	{
		try {
			return BaseEncoding.BASE_16.decode(hex);
		} catch (IllegalArgumentException e) {
			throw new URNSyntaxException(
				"Invalid NSS: Invalid hex character found", e);
		}
	}

	/**
	 * Returns the Namespace Identifier part of this {@code URN}.
	 *
	 * @return this {@code URN}'s NID.
	 */
	public String nid()
	{
		return nid;
	}

	/**
	 * Returns the Namespace Specific String of this {@code URN}.
	 *
	 * @return this {@code URN}'s NSS.
	 */
	public String nss()
	{
		return nss;
	}

	/**
	 * Returns the normalized equivalent of this {@code URN}. Normalizing a
	 * {@code URN} consists in lower-casing the scheme (the leading "urn"),
	 * NID and percent encoded parts of a {@code URN}.
	 *
	 * @return the normalized equivalent of this {@code URN}.
	 */
	public URN normalized()
	{
		return new URN("urn", ASCII.toLowerCase(nid), normalize(nss));
	}

	private String normalize(String nss)
	{
		char[] chars = nss.toCharArray();
		StringBuilder sb = new StringBuilder(chars.length);
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '%') {
				sb.append('%');
				sb.append(ASCII.toLowerCase(chars[++i]));
				sb.append(ASCII.toLowerCase(chars[++i]));
			} else {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * Returns the {@code URI} equivalent to this {@code URN}.
	 *
	 * @see URI#create(java.lang.String)
	 *
	 * @return the {@code URI} equivalent to this {@code URN}.
	 */
	public URI toURI()
	{
		return URI.create(toString());
	}

	@Override
	public String toString()
	{
		return scheme + ":" + nid + ":" + nss;
	}

	@Override
	public int hashCode()
	{
		return normalized().nss.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		if (!(o instanceof URN)) {
			return false;
		}
		URN u = normalized();
		URN t = ((URN) o).normalized();
		return u.nid.equals(t.nid) && u.nss.equals(t.nss);
	}
}
