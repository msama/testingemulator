/**
 *
 */
package ucl.cs.testingEmulator.sampleScript;


import javax.microedition.io.Connector;
import javax.microedition.io.file.FileSystemRegistry;


import ucl.cs.testingEmulator.connection.file.EmulatedFileSystem;
import ucl.cs.testingEmulator.core.Script;

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
public class AddFakeRoots implements Script{


	/**
	 * This method initializes 
	 * 
	 */
	public AddFakeRoots() {
		super();
	}

	public String getName() {
		return "AddFakeRoots";
	}

	public void run() {
		//add fakeroot
		EmulatedFileSystem sys=new EmulatedFileSystem("/Users/rax/j2meFakeRoot","/NewRoot0",Connector.READ_WRITE);
		FileSystemRegistry.getInstance().addFileSystem(sys);
		
		
	}

}  
