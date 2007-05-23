/**
 *  MicroEmulator
 *  Copyright (C) 2001-2007 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2006-2007 Vlad Skarzhevskyy
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package javax.microedition.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.microemu.microedition.ImplFactory;

public class Connector {

	public static final int READ = 1;

	public static final int WRITE = 2;

	public static final int READ_WRITE = 3;

	public static Connection open(String name) throws IOException {
		return ImplFactory.getCGFImplementation(name).open(name);
	}

	public static Connection open(String name, int mode) throws IOException {
		return ImplFactory.getCGFImplementation(name).open(name, mode);
	}

	public static Connection open(String name, int mode, boolean timeouts) throws IOException {
		return ImplFactory.getCGFImplementation(name).open(name, mode, timeouts);
	}

	public static DataInputStream openDataInputStream(String name) throws IOException {
		return ImplFactory.getCGFImplementation(name).openDataInputStream(name);
	}

	public static DataOutputStream openDataOutputStream(String name) throws IOException {
		return ImplFactory.getCGFImplementation(name).openDataOutputStream(name);
	}

	public static InputStream openInputStream(String name) throws IOException {
		return ImplFactory.getCGFImplementation(name).openInputStream(name);
	}

	public static OutputStream openOutputStream(String name) throws IOException {
		return ImplFactory.getCGFImplementation(name).openOutputStream(name);
	}

}
