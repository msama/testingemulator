/**
 *
 */
package ucl.cs.testingEmulator.sampleScript;


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
public class TimeScript implements Script {

	/* (non-Javadoc)
	 * @see cs.ucl.testingEmulator.core.Script#getName()
	 */
	public String getName() {
		return "TimeScript";
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		VirtualClock virtualClock=VirtualClock.getInstance();
		virtualClock.setStatus(VirtualClockStatus.TIME_STOPPED_AND_DELAYS_HANDLED);
		VirtualClock.setSimulatedTime(VirtualClock.getSimulatedTime()+1000);
	}

	/*
	public JPanel getFrontend() {
		return new JPanel();
	}*/

}
