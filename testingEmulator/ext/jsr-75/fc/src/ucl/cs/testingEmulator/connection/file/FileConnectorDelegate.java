/**
 *
 */
package ucl.cs.testingEmulator.connection.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connection;


import org.microemu.microedition.io.ConnectorDelegate;



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
public class FileConnectorDelegate implements ConnectorDelegate {

	public static final int READ = 1;

	public static final int WRITE = 2;

	public static final int READ_WRITE = 3;
	
	
	/* (non-Javadoc)
	 * @see org.microemu.microedition.io.ConnectorDelegate#open(java.lang.String)
	 */
	public Connection open(String name) throws IOException {
		return this.open(name, READ_WRITE, false);
	}

	/* (non-Javadoc)
	 * @see org.microemu.microedition.io.ConnectorDelegate#open(java.lang.String, int)
	 */
	public Connection open(String name, int mode) throws IOException {
		return this.open(name, mode, false);
	}

	/* (non-Javadoc)
	 * @see org.microemu.microedition.io.ConnectorDelegate#open(java.lang.String, int, boolean)
	 */
	public Connection open(String name, int mode, boolean timeouts)
			throws IOException {
		ConcreteFileConnection fc=new ConcreteFileConnection();
		fc.setParameters(name, mode, timeouts);
		return fc;
	}

	/* (non-Javadoc)
	 * @see org.microemu.microedition.io.ConnectorDelegate#openDataInputStream(java.lang.String)
	 */
	public DataInputStream openDataInputStream(String name) throws IOException {
		ConcreteFileConnection fc=(ConcreteFileConnection)this.open(name);
		return fc.openDataInputStream();
	}

	/* (non-Javadoc)
	 * @see org.microemu.microedition.io.ConnectorDelegate#openDataOutputStream(java.lang.String)
	 */
	public DataOutputStream openDataOutputStream(String name)
			throws IOException {
		ConcreteFileConnection fc=(ConcreteFileConnection)this.open(name);
		return fc.openDataOutputStream();
	}

	/* (non-Javadoc)
	 * @see org.microemu.microedition.io.ConnectorDelegate#openInputStream(java.lang.String)
	 */
	public InputStream openInputStream(String name) throws IOException {
		ConcreteFileConnection fc=(ConcreteFileConnection)this.open(name);
		return fc.openInputStream();
	}

	/* (non-Javadoc)
	 * @see org.microemu.microedition.io.ConnectorDelegate#openOutputStream(java.lang.String)
	 */
	public OutputStream openOutputStream(String name) throws IOException {
		ConcreteFileConnection fc=(ConcreteFileConnection)this.open(name);
		return fc.openOutputStream();
	}

}
