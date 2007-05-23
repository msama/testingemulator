/**
 *
 */
package ucl.cs.testingEmulator.time;

import org.microemu.app.util.MIDletThread;
import org.microemu.app.util.MIDletTimer;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
 */
public class TimeMethodAdapter extends MethodAdapter {

	public TimeMethodAdapter(MethodVisitor mv) {
		super(mv);
	}

	public static String codeName(Class klass) {
		return klass.getName().replace('.', '/');
	}

	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		//super.visitMethodInsn(opcode, owner, name, desc);
		
		switch (opcode) {
		case Opcodes.INVOKESTATIC: {
			//System.out.println("visitMethodInsn "+owner+"."+name+" desc "+desc);
			if ((name.equals("currentTimeMillis"))
					&& (owner.equals(codeName(System.class)))) {
				mv.visitMethodInsn(opcode, codeName(VirtualClock.class),
						"getSimulatedTime", desc);
				return;
			}
			if ((name.equals("sleep"))
					&& ((owner.equals(codeName(Thread.class)) || (owner
							.equals(codeName(MIDletThread.class)))))) {
				mv.visitMethodInsn(opcode, codeName(VirtualClock.class),
						"sleep", desc);
				return;
			}
			break;
		}
		case Opcodes.INVOKEVIRTUAL: {
			break;
		}
		case Opcodes.INVOKESPECIAL: {
			break;
		}
		}

		mv.visitMethodInsn(opcode, owner, name, desc);
	}
}
