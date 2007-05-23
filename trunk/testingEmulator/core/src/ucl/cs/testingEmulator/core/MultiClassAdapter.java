/**
 *
 */
package ucl.cs.testingEmulator.core;

import java.util.Vector;


import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;



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
public class MultiClassAdapter extends ClassAdapter {

	public MultiClassAdapter(ClassVisitor arg0) {
		super(arg0);
	}

	protected Vector<ClassVisitor> _classVisitors = new Vector<ClassVisitor>();
	
	//protected Vector<MethodVisitor> _methodVisitors = new Vector<MethodVisitor>();
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.objectweb.asm.ClassVisitor#visit(int, int, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String[])
	 */
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		for (ClassVisitor cv : this._classVisitors) {
			cv.visit(version, access, name, signature, superName, interfaces);
		}
	}
	
	public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
		for (ClassVisitor cv : this._classVisitors) {
			cv.visitMethod(access, name, desc, signature, exceptions).visitCode();
		}
		/*for (MethodVisitor mv : this._methodVisitors) {
			mv.visitCode();
		}*/
		//return this._classVisitors.elementAt(1).visitMethod(access, name, desc, signature, exceptions);
		return null;
	}



	/**
	 * @param arg0
	 * @see java.util.Vector#addElement(java.lang.Object)
	 */
	public void addElement(ClassVisitor arg0) {
		_classVisitors.addElement(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.util.Vector#elementAt(int)
	 */
	public ClassVisitor elementAt(int arg0) {
		return _classVisitors.elementAt(arg0);
	}

	/**
	 * 
	 * @see java.util.Vector#removeAllElements()
	 */
	public void removeAllElements() {
		_classVisitors.removeAllElements();
	}

	/**
	 * @param arg0
	 * @see java.util.Vector#removeElementAt(int)
	 */
	public void removeElementAt(int arg0) {
		_classVisitors.removeElementAt(arg0);
	}

	/**
	 * @return
	 * @see java.util.Vector#size()
	 */
	public int size() {
		return _classVisitors.size();
	}

}
