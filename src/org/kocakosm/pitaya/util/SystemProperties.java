/*----------------------------------------------------------------------------*
 * This file is part of Pitaya.                                               *
 * Copyright (C) 2012-2016 Osman KOCAK <kocakosm@gmail.com>                   *
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

package org.kocakosm.pitaya.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * System properties.
 *
 * @author Osman KOCAK
 */
public final class SystemProperties
{
	/** Operating system name. */
	public static final String OS_NAME = System.getProperty("os.name");

	/** Operating system architecture. */
	public static final String OS_ARCH = System.getProperty("os.arch");

	/** Operating system version. */
	public static final String OS_VERSION = System.getProperty("os.version");

	/** User's account name. */
	public static final String USER_NAME = System.getProperty("user.name");

	/** User's home directory. */
	public static final File USER_HOME = toFile(System.getProperty("user.home"));

	/** User's current working directory. */
	public static final File USER_DIR = toFile(System.getProperty("user.dir"));

	/** File separator ('/' on UNIX systems). */
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	/** Path separator (':' on UNIX systems). */
	public static final String PATH_SEPARATOR = System.getProperty("path.separator");

	/** Line separator ('\n' on UNIX systems). */
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/** Java Runtime Environment version. */
	public static final String JAVA_VERSION = System.getProperty("java.version");

	/** Java installation directory. */
	public static final File JAVA_HOME = toFile(System.getProperty("java.home"));

	/** Name of JIT compiler to use. */
	public static final String JAVA_COMPILER = System.getProperty("java.compiler");

	/** Java Runtime Environment vendor name. */
	public static final String JAVA_VENDOR = System.getProperty("java.vendor");

	/** Java Runtime Environment vendor URL. */
	public static final URL JAVA_VENDOR_URL = toURL(System.getProperty("java.vendor.url"));

	/** Java Virtual Machine implementation name. */
	public static final String JAVA_VM_NAME = System.getProperty("java.vm.name");

	/** Java Virtual Machine implementation version. */
	public static final String JAVA_VM_VERSION = System.getProperty("java.vm.version");

	/** Java Virtual Machine implementation vendor. */
	public static final String JAVA_VM_VENDOR = System.getProperty("java.vm.vendor");

	/** Java Virtual Machine specification name. */
	public static final String JAVA_VM_SPECIFICATION_NAME = System.getProperty("java.vm.specification.name");

	/** Java Virtual Machine specification version. */
	public static final String JAVA_VM_SPECIFICATION_VERSION = System.getProperty("java.vm.specification.version");

	/** Java Virtual Machine specification vendor. */
	public static final String JAVA_VM_SPECIFICATION_VENDOR = System.getProperty("java.vm.specification.vendor");

	/** Java Runtime Environment specification version. */
	public static final String JAVA_SPECIFICATION_NAME = System.getProperty("java.specification.name");

	/** Java Virtual Machine implementation version. */
	public static final String JAVA_SPECIFICATION_VERSION = System.getProperty("java.specification.version");

	/** Java Virtual Machine implementation vendor. */
	public static final String JAVA_SPECIFICATION_VENDOR = System.getProperty("java.specification.vendor");

	/** Java class path. */
	public static final List<File> JAVA_CLASS_PATH = toFiles(System.getProperty("java.class.path"));

	/** Java class format version number. */
	public static final String JAVA_CLASS_VERSION = System.getProperty("java.class.version");

	/** List of paths to search when loading libraries. */
	public static final List<File> JAVA_LIBRARY_PATH = toFiles(System.getProperty("java.library.path"));

	/** Default temp file path. */
	public static final File JAVA_IO_TMP_DIR = toFile(System.getProperty("java.io.tmpdir"));

	/** Path of extension directory or directories. */
	public static final List<File> JAVA_EXT_DIRS = toFiles(System.getProperty("java.ext.dirs"));

	private static URL toURL(String url)
	{
		try {
			return new URL(url);
		} catch (MalformedURLException ex) {
			throw Throwables.propagate(ex);
		}
	}

	private static File toFile(String path)
	{
		return new File(path);
	}

	private static List<File> toFiles(String paths)
	{
		String separator = System.getProperty("path.separator");
		List<File> files = new ArrayList<File>();
		for (String path : paths.split(separator)) {
			files.add(new File(path));
		}
		return Collections.unmodifiableList(files);
	}

	private SystemProperties()
	{
		/* ... */
	}
}
