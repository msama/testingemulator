/**
 *
 */
package ucl.cs.testingEmulator.contexNotifierScripts;

import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;

import ucl.cs.testingEmulator.location.TestingLocationProvider;

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
public class MovementJComponent extends Canvas implements Runnable, PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	
	protected int _cursorX=180;
	protected int _cursorY=90;
	protected int _targetCursorX=180;
	protected int _targetCursorY=90;
	
	protected Thread _updateThread;

	/**
	 * 
	 */
	public MovementJComponent() {
		super();
		initialize();
	}

	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(360, 180);
		this.setBackground(new Color(153, 153, 255));
		//this.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				startUpdating(e.getX(),e.getY());
			}
		});
	}

	private synchronized void startUpdating(int newX, int newY)
	{
		_targetCursorX=newX;
		_targetCursorY=newY;
		if(this._updateThread==null||this._updateThread.getState()==Thread.State.TERMINATED)
		{
			this._updateThread=new Thread(this,"MovementJComponent");
			this._updateThread.start();
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.gray);
		g.drawLine(this._cursorX-5, this._cursorY, this._cursorX+5, this._cursorY);
		g.drawLine(this._cursorX, this._cursorY-5, this._cursorX, this._cursorY+5);
		
		g.setColor(Color.darkGray);
		g.drawLine(this._cursorX, this._cursorY, _targetCursorX, _targetCursorY);
		g.drawLine(this._targetCursorX-5, this._targetCursorY, this._targetCursorX+5, this._targetCursorY);
		g.drawLine(this._targetCursorX, this._targetCursorY-5, this._targetCursorX, this._targetCursorY+5);
		
	}

	public void run() {
		while(Thread.currentThread()==this._updateThread&&(this._cursorX!=this._targetCursorX||this._cursorY!=this._targetCursorY))
		{
			if(this._cursorX<this._targetCursorX)
			{
				this._cursorX++;
			}else if(this._cursorX>this._targetCursorX)
			{
				this._cursorX--;
			}
			if(this._cursorY<this._targetCursorY)
			{
				this._cursorY++;
			}else if(this._cursorY>this._targetCursorY)
			{
				this._cursorY--;
			}
			this.repaint();
			TestingLocationProvider.getInstance().setLongitude(this._cursorX-180);
			TestingLocationProvider.getInstance().setLatitude(-(this._cursorY-90));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String action=evt.getPropertyName();
		if(action.equals(TestingLocationProvider.LATITUDE_UPDATED))
		{
			this._cursorY=(int)Math.round(((Double)evt.getNewValue()).doubleValue())-90;
			this.repaint();
		}else if(action.equals(TestingLocationProvider.LONGITUDE_UPDATED))
		{
			this._cursorX=(int)Math.round(((Double)evt.getNewValue()).doubleValue())-180;
			this.repaint();
		}
	}

}
