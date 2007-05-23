/**
 *
 */
package ucl.cs.testingEmulator.vibration;

import javax.swing.JFrame;

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
 *
 * This class rapresent a Manager for the vibration functionality. 
 * A static method would be enough for that functionality 
 * but using a singleton grants the possibility to expand that object by having information regarding the status of the device.
 */
public class VibrationManager{

	private static VibrationManager INSTANCE=null;
	
	public VibrationManager(){}
	
	public static VibrationManager getInstance()
	{
		if(VibrationManager.INSTANCE==null)
		{
			VibrationManager.INSTANCE=new VibrationManager();
		}
		return VibrationManager.INSTANCE;
	}
	
	public void vibrate(JFrame frame,int duration)
	{
		if(frame!=null)
		{
			Thread th=new VibrationThread(frame,duration);
			th.setName("VibrationManager.vibrate");
			th.start();
		}
	}


}
