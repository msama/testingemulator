/**
 *
 */
package ucl.cs.testingEmulator.time;

import org.microemu.app.util.MIDletThread;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author -Michele Sama- aka -RAX-
 * 
 * University College London
 * Dept. of Computer Science
 * Gower Street
 * London WC1E 6BT
 * United Kingdom
 *
 * Email: M.Sama (at) cs.ucl.ac.uk
 *
 * Group:
 * Software Systems Engineering
 *
 */
public class TimeClassAdapter extends ClassAdapter {

	/**
	 * @param arg0
	 */
	public TimeClassAdapter(ClassVisitor cv) {
		super(cv);
	}
	
	public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
		return new TimeMethodAdapter(super.visitMethod(access, name, desc, signature, exceptions));
	}

}
