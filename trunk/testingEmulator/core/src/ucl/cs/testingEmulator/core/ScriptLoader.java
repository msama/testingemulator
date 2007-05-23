/**
 *
 */
package ucl.cs.testingEmulator.core;



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
public class ScriptLoader {

	public static Script loadScriptByName(String name) throws ClassNotFoundException
	{
		try {
			Script script = (Script)Class.forName(name).newInstance();
			return script;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		throw new ClassNotFoundException(name + " not found or not an instance of Script.");
	}
	
	public static void runScript(Script script)
	{
		Thread th=new Thread(script,script.getName());
		th.setDaemon(true);
		th.start();
	}
	

}
