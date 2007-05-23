/**
 *
 */
package ucl.cs.testingEmulator.contexNotifierScripts;

import javax.swing.JFrame;

import ucl.cs.testingEmulator.core.Script;
import ucl.cs.testingEmulator.time.VirtualClock;
import ucl.cs.testingEmulator.time.VirtualClockStatus;

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
public class TestingAdaptiveReminder implements Script {

	/* (non-Javadoc)
	 * @see ucl.cs.testingEmulator.core.Script#getName()
	 */
	public String getName() {
		return TestingAdaptiveReminder.class.getSimpleName();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		VirtualClock.getInstance().setStatus(VirtualClockStatus.TIME_STOPPED_STATUS);
		VirtualClock.setSimulatedTime(0);
		JFrame frame=new JFrame(this.getName());
		frame.getContentPane().add(new CommandJPanel());
		frame.pack();
		frame.setVisible(true);
	}

}
