/**
 *  MicroEmulator
 *  Copyright (C) 2006-2007 Bartek Teodorczyk <barteo@barteo.net>
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
 *
 *  @version $Id: ClassPreprocessor.java 1140 2007-03-30 00:10:36Z vlads $
 */
package org.microemu.app.classloader;

import java.io.IOException;
import java.io.InputStream;

import org.microemu.log.Logger;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import ucl.cs.testingEmulator.core.MultiClassAdapter;
import ucl.cs.testingEmulator.time.TimeClassAdapter;

/**
 * @author -Michele Sama- aka -RAX-
 * 
 * University College London Dept. of Computer Science Gower Street London WC1E
 * 6BT United Kingdom
 * 
 * Email: M.Sama (at) cs.ucl.ac.uk
 * 
 * Group: Software Systems Engineering
 * 
 * 
 * This class instruments all loaded class with a MultiClassAdapter
 */
public class ClassPreprocessor {


	
	public static byte[] instrument(final InputStream classInputStream, InstrumentationConfig config) {
		try {
			ClassReader cr = new ClassReader(classInputStream);
			//ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			ClassWriter cw = new ClassWriter(0);
			//ClassAdapter ca = ClassPreprocessor.generateClassAdapter(cw, config);
			//cr.accept(ca, 0);
			
			//Adds Microemulator Threads and console trap
			//cr.accept(new ChangeCallsClassVisitor(cw, config), 0);
			
			//Time support
			//cr.accept(new TimeClassAdapter(cw), 0);
			
			cr.accept(cw, 0);
			byte[] b=cw.toByteArray();
			
			//Adds Microemulator Threads and console trap
			cr = new ClassReader(b,0,b.length);
			//cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			cw = new ClassWriter(0);
			cr.accept(new ChangeCallsClassVisitor(cw, config), 0);
			b = cw.toByteArray();
			
			
			//Time
			cr = new ClassReader(b,0,b.length);
			//cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			cw = new ClassWriter(0);
			cr.accept(new TimeClassAdapter(cw), 0);
			b = cw.toByteArray();
			
			return b;
		} catch (IOException e) {
			Logger.error("Error loading MIDlet class", e);
			return null;
		} 
    }
	
	
	protected static byte[] instrument(byte[] b, InstrumentationConfig config, ClassVisitor cv)
	{
		ClassReader cr = new ClassReader(b,0,b.length);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cr.accept(cv, 0);
		return cw.toByteArray();
	}
	
	/*
	protected static ClassAdapter generateClassAdapter(ClassWriter cw, InstrumentationConfig config)
	{
		MultiClassAdapter multiClassAdapter=new MultiClassAdapter(cw);
		
		//Adds Microemulator Threads and console trap
		multiClassAdapter.addElement(new ChangeCallsClassVisitor(cw, config));
		
		//Time support
		//multiClassAdapter.addElement(new TimeClassAdapter(cw));
		
		return multiClassAdapter;
	}*/
	
}
