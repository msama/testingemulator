/**
 *
 */
package ucl.cs.testingEmulator.vibration;

import java.awt.Point;

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
 */
public class VibrationThread extends Thread {

	private JFrame _jFrame=null;
	private int _duration=0;
	


	/**
	 * @param frame
	 * @param _duration
	 */
	public VibrationThread(JFrame frame, int _duration) {
		super();
		_jFrame = frame;
		this._duration = _duration;
	}



	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run(){
		super.run();
		long time=System.currentTimeMillis()+this._duration*1000;
		Point p;
		while(time>System.currentTimeMillis())
		{
			p=this._jFrame.getLocation();
			p.x+=5;
			this._jFrame.setLocation(p);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p=this._jFrame.getLocation();
			p.x-=5;
			this._jFrame.setLocation(p);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
