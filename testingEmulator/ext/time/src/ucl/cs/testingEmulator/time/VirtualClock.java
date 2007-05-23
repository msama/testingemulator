/**
 * 
 */
package ucl.cs.testingEmulator.time;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

/**
 * @author -Michele Sama- aka -RAX-
 * 
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
public class VirtualClock {

	public static final String TIME_CHANGED = "TimeChanged";
	
	public static final String TIME_STATUS = "TimeStatus";
	
	/*
	public static final int TIME_NORMAL_STATUS = 0;//"TimeNormalStatus";
	
	public static final int TIME_STOPPED_STATUS = 1;//"TimeFixedStatus";
	
	public static final int TIME_STOPPED_AND_DELAYS_HANDLED = 2;
	
	public static final int TIME_WARPED_STATUS = 3;//"TimeWarpedStatus";
	 */
	
	//public static final String HANDLE_DELAYS_STATUS_CHANGED = "HandleDelaysStatus";

	/**Singleton*/
	private static VirtualClock INSTANCE=null;
	
	private long _simulatedTime=0;
	
	private VirtualClockStatus _status=VirtualClockStatus.TIME_NORMAL_STATUS;
	
	/*
	private boolean _timeStopped=false;

	private boolean _timeWarped=false;
	
	private boolean _handleDelays=false;
	*/
	
	private long _warpingRealTime=0;
	
	private PropertyChangeSupport _propertyChangeSupport=null;
	
	/**
	 * Singleton default protected constructor
	 * */
	protected VirtualClock() {
		this._propertyChangeSupport=new PropertyChangeSupport(this);
	}
	
	/**
	 * Singleton accessor for the instance.
	 * @return {@link VirtualClock}.INSTANCE
	 * */
	public static VirtualClock getInstance()
	{
		if(VirtualClock.INSTANCE==null)
		{
			VirtualClock.INSTANCE=new VirtualClock();
		}
		return VirtualClock.INSTANCE;
	}

	/**
	 * @param _simulatedTime the _simulatedTime to set
	 */
	public static  void setSimulatedTime(long _simulatedTime) {
		VirtualClock virtualClock=VirtualClock.getInstance();
		virtualClock._propertyChangeSupport.firePropertyChange(TIME_CHANGED,new Date(VirtualClock.getInstance()._simulatedTime),new Date(_simulatedTime));
		virtualClock._simulatedTime = _simulatedTime;
		if(virtualClock._status==VirtualClockStatus.TIME_WARPED_STATUS)
		{
			virtualClock.restartWarpingTime();
		}
		
		//if(virtualClock.isTimeStoppedAndDelaysHandled()==true){
			virtualClock.wakeUpInvokers();
		//}
	}
	
	public synchronized void wakeUpInvokers()
	{
		this.notifyAll();
	}

	/**
	 * @return the _simulatedTime
	 */
	public static long getSimulatedTime() {
		VirtualClock virtualClock=VirtualClock.getInstance();
		if(virtualClock.isTimeStopped()||virtualClock.isTimeStoppedAndDelaysHandled())
		{
			return virtualClock._simulatedTime;
		}else if(VirtualClock.getInstance().isTimeWarped()){
			return virtualClock._simulatedTime + (System.currentTimeMillis()-virtualClock._warpingRealTime);
		}else
		{
			return System.currentTimeMillis();
		}
		//return VirtualClock.getInstance()._simulatedTime;
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		/*
		listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.TIME_STOPPED_STATUS,null,this._timeStopped));
		listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.TIME_WARPED_STATUS,null,this._timeWarped));
		listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.HANDLE_DELAYS_STATUS_CHANGED,null,this._handleDelays));
		*/
		listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.TIME_STATUS,null,this._status));
		listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.TIME_CHANGED,null,VirtualClock.getSimulatedTime()));
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		/*
		if(propertyName.equals(VirtualClock.HANDLE_DELAYS_STATUS_CHANGED)){
			listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.HANDLE_DELAYS_STATUS_CHANGED,null,this._handleDelays));
		}else if(propertyName.equals(VirtualClock.TIME_STOPPED_STATUS)){
			listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.TIME_STOPPED_STATUS,null,this._timeStopped));
		}else if(propertyName.equals(VirtualClock.TIME_WARPED_STATUS)){
			listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.TIME_WARPED_STATUS,null,this._timeWarped));
		}*/
		if(propertyName.equals(VirtualClock.TIME_STATUS))
		{
		listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.TIME_STATUS,null,this._status));
		}else if(propertyName.equals(VirtualClock.TIME_CHANGED))
		{
			listener.propertyChange(new PropertyChangeEvent(this,VirtualClock.TIME_CHANGED,null,VirtualClock.getSimulatedTime()));
		}
		_propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		_propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		_propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * @param _timeStopped the _timeStopped to set
	 */
	/*
	public void setTimeStopped(boolean _timeStopped) {
		this._propertyChangeSupport.firePropertyChange(VirtualClock.TIME_STOPPED_STATUS,this._timeStopped,_timeStopped);
		this._timeStopped = _timeStopped;
		if(this._timeStopped==false)
		{
			this.setHandleDelays(false);
		}
	}*/

	/**
	 * @return the _simulating
	 */
	public boolean isTimeStopped() {
		return this._status==VirtualClockStatus.TIME_STOPPED_STATUS;//_timeStopped;
	}
	
	public boolean isTimeNormal()
	{
		return this._status==VirtualClockStatus.TIME_NORMAL_STATUS;
	}
	
	/**	
     * Causes the currently executing thread to sleep (temporarily cease 
     * execution) for the specified number of milliseconds. The thread 
     * does not lose ownership of any monitors.
     *
     * @param      millis   the length of time to sleep in milliseconds.
     * @exception  InterruptedException if another thread has interrupted
     *             the current thread.  The <i>interrupted status</i> of the
     *             current thread is cleared when this exception is thrown.
     * @see        java.lang.Object#notify()
     */
    public static/*native*/ void sleep(long millisec) throws InterruptedException
    {/*
    	VirtualClock virtualClock=VirtualClock.getInstance();
    	if(virtualClock.isTimeStoppedAndDelaysHandled()==true)
    	{
    		virtualClock.suspendInvoker(VirtualClock.getSimulatedTime()+millis);
    	}else
    	{	
    		long wakeupTime = VirtualClock.getSimulatedTime()+millis;
    		Thread.sleep(millis);
    		virtualClock.suspendInvoker(wakeupTime);
    	}
    	*/
    	VirtualClock virtualClock=VirtualClock.getInstance();
    	virtualClock.suspendInvoker(millisec);
    }
    
    protected synchronized void suspendInvoker(long millisec)
    {
    	/*
		while(this.isTimeStoppedAndDelaysHandled()==true && wakeupTime>VirtualClock.getSimulatedTime())
		{
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
    	VirtualClock virtualClock=VirtualClock.getInstance();
    	long wakeupTime = VirtualClock.getSimulatedTime()+millisec;
    	while(wakeupTime>VirtualClock.getSimulatedTime())
    	{
    		try {
				virtualClock.wait(wakeupTime-VirtualClock.getSimulatedTime());
			} catch (InterruptedException e) {
				System.err.println("VirtualClock.sleep "+e.getLocalizedMessage());
			}
    	}
    }

	/**
	 * @param _handleDelays the _handleDelays to set
	 */
    /*
	public void setHandleDelays(boolean _handleDelays) {
		this._propertyChangeSupport.firePropertyChange(VirtualClock.HANDLE_DELAYS_STATUS_CHANGED,this._handleDelays,_handleDelays);
		this._handleDelays = _handleDelays;
		if(this._handleDelays==false)
		{
			this.wakeUpInvokers();
		}
	}
	*/

	/**
	 * @return the _handleDelays
	 */
	public boolean isTimeStoppedAndDelaysHandled() {
		return this._status==VirtualClockStatus.TIME_STOPPED_AND_DELAYS_HANDLED;//_handleDelays;
	}

	/**
	 * @param _timeWarped the _timeWarped to set
	 */
	/*
	public void setTimeWarped(boolean _timeWarped) {
		boolean old=this._timeWarped;
		this._timeWarped = _timeWarped;
		this._propertyChangeSupport.firePropertyChange(VirtualClock.TIME_WARPED_STATUS,old,_timeWarped);
		if(old==false&&this._timeWarped==true)
		{
			this._warpingRealTime=System.currentTimeMillis();
		}
	}
	*/

	/**
	 * @return the _timeWarped
	 */
	public boolean isTimeWarped() {
		return this._status.equals(VirtualClockStatus.TIME_WARPED_STATUS);//_timeWarped;
	}
	
	
	public synchronized void setStatus(VirtualClockStatus _status)
	{
		//if this._status does not change there is nothing to do.
		if(_status==this._status)
		{
			return;
		}
		
		//*********update if the internal time	
		//If clock was time warping and whe are stopping time must be setted as the warp 
		//so we will be able to start and stop
		if(this._status==VirtualClockStatus.TIME_WARPED_STATUS&&_status!=VirtualClockStatus.TIME_NORMAL_STATUS)
		{
			this._simulatedTime=VirtualClock.getSimulatedTime();
		}else 
		//if time was normal then internal time must be updated.	
		if(this._status==VirtualClockStatus.TIME_NORMAL_STATUS)
		{
			this._simulatedTime=VirtualClock.getSimulatedTime();
		}
		
		
		//***********update of the warping time
		if(_status==VirtualClockStatus.TIME_WARPED_STATUS)
		{
			this.restartWarpingTime();
		}//else
		
		//********wake up threads
		//If we were handling delays and we had to wake up all the stopped threads.
		if(this._status==VirtualClockStatus.TIME_STOPPED_AND_DELAYS_HANDLED)
		{
			this.wakeUpInvokers();
		}
			
		VirtualClockStatus oldStatus=this._status;
		this._status=_status;
		this._propertyChangeSupport.firePropertyChange(VirtualClock.TIME_STATUS,oldStatus,this._status);	
	}
	
	public void restartWarpingTime()
	{
		this._warpingRealTime=System.currentTimeMillis();
	}
}
