/**
 *
 */
package ucl.cs.testingEmulator.time;

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
public enum VirtualClockStatus {
	TIME_NORMAL_STATUS,
	TIME_STOPPED_STATUS,
	TIME_STOPPED_AND_DELAYS_HANDLED,
	TIME_WARPED_STATUS;
}
