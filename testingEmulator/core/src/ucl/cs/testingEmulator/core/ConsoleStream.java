/**
 *
 */
package ucl.cs.testingEmulator.core;

import java.io.FileNotFoundException;
import java.io.PrintStream;

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
public class ConsoleStream {
	
	public static final String HTML="html";
	public static final String HEAD="head";
	public static final String BODY="body";
	public static final String OUT="";
	public static final String ERR="";
	
	public static final String FOLDER=".microemulator";
	public static final String OUT_FILENAME="out.txt";
	public static final String ERR_FILENAME="err.txt";

	
	public static PrintStream out;
	
	static{
		try {
			ConsoleStream.out=new PrintStream(System.getProperty("user.home")+System.getProperty("file.separator")+ConsoleStream.FOLDER+System.getProperty("file.separator")+ConsoleStream.OUT_FILENAME);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
