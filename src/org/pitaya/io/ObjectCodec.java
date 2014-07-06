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

package org.pitaya.io;

import org.pitaya.util.Parameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Serialization utilities.
 *
 * @author Osman KOCAK
 */
public final class ObjectCodec
{
	/**
	 * Serializes the given {@code Serializable} object into a {@code byte}
	 * array.
	 *
	 * @param object the object to encode.
	 *
	 * @return the given object's encoding.
	 *
	 * @throws NullPointerException if {@code object} is {@code null}.
	 * @throws EncodingException if the given object cannot be serialized.
	 */
	public static byte[] encode(Serializable object)
	{
		Parameters.checkNotNull(object);
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(out);
			oos.writeObject(object);
			oos.flush();
			return out.toByteArray();
		} catch (IOException ex) {
			throw new EncodingException(ex);
		} finally {
			IO.close(oos);
		}
	}

	/**
	 * Deserializes the given {@code byte} array into an object.
	 *
	 * @param object the object's encoding.
	 *
	 * @return the deserialized object.
	 *
	 * @throws NullPointerException if {@code object} is {@code null}.
	 * @throws DecodingException if the given object cannot be deserialized.
	 */
	public static Serializable decode(byte[] object)
	{
		return decode(object, Serializable.class);
	}

	/**
	 * Deserializes the given {@code byte} array into an object.
	 *
	 * @param <T> the object's expected type.
	 * @param object the object's encoding.
	 * @param t the object's expected type's {@code Class}.
	 *
	 * @return the deserialized object.
	 *
	 * @throws NullPointerException if one of the arguments is {@code null}.
	 * @throws DecodingException if the given object cannot be deserialized.
	 * @throws ClassCastException if the deserialized object is not an
	 *	instance of the expected class.
	 */
	public static <T extends Serializable> T decode(byte[] object, Class<T> t)
	{
		Parameters.checkNotNull(t);
		Parameters.checkNotNull(object);
		ObjectInputStream ois = null;
		ByteArrayInputStream in = new ByteArrayInputStream(object);
		try {
			ois = new ObjectInputStream(in);
			return Parameters.checkType(ois.readObject(), t);
		} catch (IOException ex) {
			throw new DecodingException(ex);
		} catch (ClassNotFoundException ex) {
			throw new DecodingException(ex);
		} finally {
			IO.close(ois);
		}
	}

	/** Meant to be thrown on serialization error. */
	public static final class EncodingException extends RuntimeException
	{
		private static final long serialVersionUID = 27396917846938764L;

		EncodingException(Throwable cause)
		{
			super(cause);
		}
	}

	/** Meant to be thrown on deserialization error. */
	public static final class DecodingException extends RuntimeException
	{
		private static final long serialVersionUID = 39875873692107851L;

		DecodingException(Throwable cause)
		{
			super(cause);
		}
	}

	private ObjectCodec()
	{
		/* ... */
	}
}
