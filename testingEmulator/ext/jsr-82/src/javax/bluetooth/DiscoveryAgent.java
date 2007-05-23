/*
 *  DiscoveryAgent.java
 *
 *  $Revision$ $Date$
 *
 *  (c) Copyright 2001, 2002 Motorola, Inc.  ALL RIGHTS RESERVED.
 */



package javax.bluetooth;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;



/**
 * The <code>DiscoveryAgent</code> class provides methods to perform
 * device and service discovery.  A local device must have only one
 * <code>DiscoveryAgent</code> object.  This object must be retrieved
 * by a call to <code>getDiscoveryAgent()</code> on the
 * <code>LocalDevice</code> object.
 *
 * <H3>Device Discovery</H3>
 *
 * There are two ways to discover devices.  First, an application may
 * use <code>startInquiry()</code> to start an inquiry to find devices
 * in proximity to the local device. Discovered devices are returned
 * via the <code>deviceDiscovered()</code> method of the interface
 * <code>DiscoveryListener</code>.  The second way to
 * discover devices is via the <code>retrieveDevices()</code> method.
 * This method will return devices that have been discovered via a
 * previous inquiry or devices that are classified as pre-known.
 * (Pre-known devices are those devices that are defined in the
 * Bluetooth Control Center as devices this device frequently contacts.)
 * The <code>retrieveDevices()</code> method does not perform an
 * inquiry, but provides a quick way to get a list of devices that may
 * be in the area.
 *
 * <H3>Service Discovery</H3>
 * The <code>DiscoveryAgent</code> class also encapsulates the
 * functionality provided by the service discovery application profile.
 * The class provides an interface for an application to search and
 * retrieve attributes for a particular service.  There are two ways to
 * search for services.  To search for a service on a single device,
 * the <code>searchServices()</code> method should be used.  On the
 * other hand, if you don't care which device a service is on, the
 * <code>selectService()</code> method does a service search on a
 * set of remote devices.
 *
 *
 * @version 1.0 February 11, 2002
 *
 */
 public class DiscoveryAgent implements Runnable{
	 
	 private PropertyChangeSupport _propertyChangeSupport=null;
	 
	 /**Specify a delay between the startInquire and the inquireCompleted*/
	 private long _simulatedInquireDelay=10000;
	 public static final String SIMULATED_INQUIRE_DELAY="SimulatedInquireDelay";
	 
	 private long _simulatedFriendlyNameDelay=2000;
	 
	 private static DiscoveryAgent INSTANCE=new DiscoveryAgent();

	 private Vector<RemoteDevice> _remoteDevice_preknown=new Vector<RemoteDevice>();
	 private Vector<RemoteDevice> _remoteDevice_cached=new Vector<RemoteDevice>();
	 
	 private Vector<DiscoveryListener> _discoveryListener_liac=new Vector<DiscoveryListener>();
	 private Vector<DiscoveryListener> _discoveryListener_giac=new Vector<DiscoveryListener>();
	 private DiscoveryListener _discoveryStarter=null;

   /**
    * Takes the device out of discoverable mode.
    * <P>
    * The value of <code>NOT_DISCOVERABLE</code> is 0x00 (0).
    */
    public static final int NOT_DISCOVERABLE = 0;



   /**
    * The inquiry access code for General/Unlimited Inquiry Access Code
    * (GIAC). This is used to specify the type of inquiry to complete or
    * respond to.
    * <P>
    * The value of <code>GIAC</code> is 0x9E8B33 (10390323). This value
    * is defined in the Bluetooth Assigned Numbers document.
    */
    public static final int GIAC = 0x9E8B33;



   /**
    * The inquiry access code for Limited Dedicated Inquiry Access Code
    * (LIAC). This is used to specify the type of inquiry to complete or
    * respond to.
    * <P>
    * The value of <code>LIAC</code> is 0x9E8B00 (10390272). This value
    * is defined in the Bluetooth Assigned Numbers document.
    */
    public static final int LIAC = 0x9E8B00;



   /**
    * Used with the <code>retrieveDevices()</code> method to return
    * those devices that were found via a previous inquiry.  If no
    * inquiries have been started, this will cause the method to return
    * <code>null</code>.
    * <P>
    * The value of <code>CACHED</code> is 0x00 (0).
    *
    * @see #retrieveDevices
    */
    public static final int CACHED = 0x00;



   /**
    * Used with the <code>retrieveDevices()</code> method to return
    * those devices that are defined to be pre-known devices.  Pre-known
    * devices are specified in the BCC.  These are devices that are
    * specified by the user as devices with which the local device will
    * frequently communicate.
    * <P>
    * The value of <code>PREKNOWN</code> is 0x01 (1).
    *
    * @see #retrieveDevices
    */
    public static final int PREKNOWN = 0x01;

    public static final String TEST_REMOTE_DEVICE_ADDED = "Test_RemoteDeviceAdded";
    public static final String TEST_REMOTE_DEVICE_REMOVED = "Test_RemoteDeviceRemoved";

   /**
    * Creates a <code>DiscoveryAgent</code> object.
    */
    private DiscoveryAgent() {
    	this._propertyChangeSupport=new PropertyChangeSupport(this);
    }   /*  End of the constructor method   */


    
    /**
     * Factory method for testing purpose only
     * @return DiscoveryAgent.INSTANCE
     * */
    public static DiscoveryAgent getInstance()
    {
    	if(DiscoveryAgent.INSTANCE==null)
    	{
    		DiscoveryAgent.INSTANCE=new DiscoveryAgent();
    	}
    	return DiscoveryAgent.INSTANCE;
    }




   /**
    * Returns an array of Bluetooth devices that have either been found
    * by the local device during previous inquiry requests or been
    * specified as a pre-known device depending on the argument. The list
    * of previously found devices is maintained by the implementation of
    * this API. (In other words, maintenance of the list of previously
    * found devices is an implementation detail.) A device can be set as
    * a pre-known device in the Bluetooth Control Center.
    *
    * @param option <code>CACHED</code> if previously found devices
    * should be returned; <code>PREKNOWN</code> if pre-known devices
    * should be returned
    *
    * @return an array containing the Bluetooth devices that were
    * previously found if <code>option</code> is <code>CACHED</code>;
    * an array of devices that are pre-known devices if
    * <code>option</code> is <code>PREKNOWN</code>; <code>null</code>
    * if no devices meet the criteria
    *
    * @exception IllegalArgumentException if <code>option</code> is
    * not <code>CACHED</code> or <code>PREKNOWN</code>
    */
    public RemoteDevice[] retrieveDevices(int option) {
    	switch(option)
    	{
	    	case DiscoveryAgent.PREKNOWN: 
	    	{
	    		return this._remoteDevice_preknown.toArray(new RemoteDevice[this._remoteDevice_preknown.size()]);
	    	}
	    	case DiscoveryAgent.CACHED:
	    	{
	    		return this._remoteDevice_cached.toArray(new RemoteDevice[this._remoteDevice_cached.size()]);
	    	}
	    	default: 
	    	{
	    		throw new IllegalArgumentException("Param \"option\" must be either DiscoveryAgent.PREKNOWN or DiscoveryAgent.CACHED.");
	    	}
    	}
    }   /*  End of the method retrieveDevices   */

   /**
    * Places the device into inquiry mode.  The length of the inquiry is
    * implementation dependent. This method will search for devices with the
    * specified inquiry access code. Devices that responded to the inquiry
    * are returned to the application via the method
    * <code>deviceDiscovered()</code> of the interface
    * <code>DiscoveryListener</code>. The <code>cancelInquiry()</code>
    * method is called to stop the inquiry.
    *
    * @see #cancelInquiry
    * @see #GIAC
    * @see #LIAC
    *
    * @param accessCode  the type of inquiry to complete
    *
    * @param listener the event listener that will receive device
    * discovery events
    *
    * @return <code>true</code> if the inquiry was started;
    * <code>false</code> if the inquiry was not started because the
    * <code>accessCode</code> is not supported
    *
    * @exception IllegalArgumentException if the access code provided
    * is not <code>LIAC</code>, <code>GIAC</code>, or in the range
    * 0x9E8B00 to 0x9E8B3F
    *
    * @exception NullPointerException if <code>listener</code> is
    * <code>null</code>
    *
    * @exception BluetoothStateException if the Bluetooth device does
    * not allow an inquiry to be started due to other operations that are being
    * performed by the device
    */
    public boolean startInquiry(int accessCode, DiscoveryListener listener)
                                throws BluetoothStateException {

    	if(listener==null)
    	{
    		throw new NullPointerException("Param \"listener\" cannot be null.");
    	}
    	switch(accessCode)
    	{
	    	case DiscoveryAgent.GIAC:
	    	{
	    		this._discoveryListener_giac.addElement(listener);
	    		break;
	    	} 
	    	case DiscoveryAgent.LIAC:
	    	{
	    		this._discoveryListener_liac.addElement(listener);
	    		break;
	    	}
	    	default: 
	    	{
	    		throw new IllegalArgumentException("Param \"accessCode\" must be either DiscoveryAgent.GIAC or DiscoveryAgent.LIAC.");
	    	}
    	}
    	
    	
    	
    	LocalDevice localDevice=LocalDevice.getLocalDevice();
    	if(localDevice.isInquiring()==false)
    	{
    		localDevice.setInquiring(true);
    		this._discoveryStarter=listener;
    		Thread th=new Thread(this,"Blueetooth discovery delay");
    		th.setDaemon(true);
    		th.start();
    	}
    	
    
    	
    	return true;
    	
    }   /*  End of the method  startInquiry     */







   /**
    * Removes the device from inquiry mode.
    * <P>
    * An <code>inquiryCompleted()</code> event will occur with a type of
    * <code>INQUIRY_TERMINATED</code> as a result of calling this
    * method.  After receiving this
    * event, no further <code>deviceDiscovered()</code> events will occur
    * as a result of this inquiry.
    *
    * <P>
    *
    * This method will only cancel the inquiry if the
    * <code>listener</code> provided is the listener that started
    * the inquiry.
    *
    * @param listener the listener that is receiving inquiry events
    *
    * @return <code>true</code> if the inquiry was canceled; otherwise
    * <code>false</code> if the inquiry was not canceled or if the inquiry
    * was not started using <code>listener</code>
    *
    * @exception NullPointerException if <code>listener</code> is
    * <code>null</code>
    */
    public boolean cancelInquiry(DiscoveryListener listener) {
    	if(listener==null)
    	{
    		throw new NullPointerException("Param \"listener\" cannot be null.");
    	}
    	
    	LocalDevice localDevice=null;
		try {
			localDevice = LocalDevice.getLocalDevice();
		} catch (BluetoothStateException e) {
			e.printStackTrace();
			return false;
		}
		
    	if(localDevice.isInquiring()==false)
    	{
    		return false;
    	}
    	
    	this._discoveryListener_giac.remove(listener);
    	this._discoveryListener_liac.removeElement(listener);
    	
    	if(this._discoveryStarter==listener)
    	{
    		localDevice.setInquiring(false);
    		for(DiscoveryListener list:this._discoveryListener_giac)
    		{
    			list.inquiryCompleted(DiscoveryListener.INQUIRY_TERMINATED);
    		}
    		for(DiscoveryListener list:this._discoveryListener_liac)
    		{
    			list.inquiryCompleted(DiscoveryListener.INQUIRY_TERMINATED);
    		}
    		return true;
    	}else
    	{
    		return false;
    	}
    }   /*  End of the method cancelInquiry */










    /**
     * Searches for services on a remote Bluetooth device that have all the
     * UUIDs specified in <code>uuidSet</code>.  Once the service is found,
     * the attributes specified in <code>attrSet</code> and the default
     * attributes are retrieved.  The default attributes are
     * ServiceRecordHandle (0x0000), ServiceClassIDList
     * (0x0001), ServiceRecordState (0x0002), ServiceID (0x0003), and
     * ProtocolDescriptorList (0x0004).If <code>attrSet</code> is
     * <code>null</code> then only the default attributes will be retrieved.
     * <code>attrSet</code> does not have to be sorted in increasing order,
     * but must only contain values in the range [0 - (2<sup>16</sup>-1)].
     *
     * @see DiscoveryListener
     *
     * @param attrSet indicates the attributes whose values will be
     * retrieved on services which have the UUIDs specified in
     * <code>uuidSet</code>
     *
     * @param uuidSet the set of UUIDs that are being searched for;  all
     * services returned will contain all the UUIDs specified here
     *
     * @param btDev the remote Bluetooth device to search for services on
     *
     * @param discListener the object that will receive events when
     * services are discovered
     *
     * @return the transaction ID of the service search; this number
     * must be positive
     *
     * @exception BluetoothStateException if the number of concurrent
     * service search transactions exceeds the limit specified by the
     * <code>bluetooth.sd.trans.max</code> property obtained from the
     * class <code>LocalDevice</code> or the system is unable to start
     * one due to current conditions
     *
     * @exception IllegalArgumentException if <code>attrSet</code> has
     * an illegal service attribute ID or exceeds the property
     * <code>bluetooth.sd.attr.retrievable.max</code>
     * defined in the class <code>LocalDevice</code>; if
     * <code>attrSet</code>
     * or <code>uuidSet</code> is of length 0; if <code>attrSet</code>
     * or <code>uuidSet</code> contains duplicates
     *
     * @exception NullPointerException if <code>uuidSet</code>,
     * <code>btDev</code>, or <code>discListener</code> is
     * <code>null</code>; if an element in  <code>uuidSet</code> array is
     * <code>null</code>
     *
     */
     public int searchServices(int[] attrSet,
                               UUID[] uuidSet,
                               RemoteDevice btDev,
                               DiscoveryListener discListener)
                               throws BluetoothStateException {
throw new RuntimeException("Not Implemented! Used to compile Code");
     }   /*  End of the method searchServices    */






    /**
     * Cancels the service search transaction that has the specified
     * transaction ID. The ID was assigned to the transaction by the
     * method <code>searchServices()</code>. A
     * <code>serviceSearchCompleted()</code> event with a discovery type
     * of <code>SERVICE_SEARCH_TERMINATED</code> will occur when
     * this method is called. After receiving this event, no further
     * <code>servicesDiscovered()</code> events will occur as a result
     * of this search.
     *
     * @param transID the ID of the service search transaction to
     * cancel; returned by <code>searchServices()</code>
     *
     * @return <code>true</code> if the service search transaction is
     * terminated, else <code>false</code>  if the <code>transID</code>
     * does not represent an active service search transaction
     */
     public boolean cancelServiceSearch(int transID) {
throw new RuntimeException("Not Implemented! Used to compile Code");
     }   /*  End of the method cancelServiceSearch   */




    /**
     * Attempts to locate a service that contains <code>uuid</code> in
     * the ServiceClassIDList of its service record.  This
     * method will return a string that may be used in
     * <code>Connector.open()</code> to establish a connection to the
     * service.  How the service is selected if there are multiple services
     * with <code>uuid</code> and which devices to
     * search is implementation dependent.
     *
     * @see ServiceRecord#NOAUTHENTICATE_NOENCRYPT
     * @see ServiceRecord#AUTHENTICATE_NOENCRYPT
     * @see ServiceRecord#AUTHENTICATE_ENCRYPT
     *
     * @param uuid the UUID to search for in the ServiceClassIDList
     *
     * @param security specifies the security requirements for a connection
     * to this service; must be one of
     * <code>ServiceRecord.NOAUTHENTICATE_NOENCRYPT</code>,
     * <code>ServiceRecord.AUTHENTICATE_NOENCRYPT</code>, or
     * <code>ServiceRecord.AUTHENTICATE_ENCRYPT</code>
     *
     * @param master determines if this client must be the master of the
     * connection; <code>true</code> if the client must be the master;
     * <code>false</code> if the client can be the master or the slave
     *
     * @return the connection string used to connect to the service
     * with a UUID of <code>uuid</code>; or <code>null</code> if no
     * service could be found with a UUID of <code>uuid</code> in the
     * ServiceClassIDList
     *
     * @exception BluetoothStateException if the Bluetooth system cannot
     * start the request due to the current state of the Bluetooth system
     *
     * @exception NullPointerException if <code>uuid</code> is
     * <code>null</code>
     *
     * @exception IllegalArgumentException if <code>security</code> is
     * not <code>ServiceRecord.NOAUTHENTICATE_NOENCRYPT</code>,
     * <code>ServiceRecord.AUTHENTICATE_NOENCRYPT</code>, or
     * <code>ServiceRecord.AUTHENTICATE_ENCRYPT</code>
     */
    public String selectService(UUID uuid, int security, boolean master)
        throws BluetoothStateException {

    	//BluetoothStateException is not raised by this implementation
    	
    	if(uuid==null)
    	{
    		throw new NullPointerException("Param \"uuid\" cannot be null");
    	}
    	
    	if(security!=ServiceRecord.NOAUTHENTICATE_NOENCRYPT||security!=ServiceRecord.AUTHENTICATE_NOENCRYPT||security!=ServiceRecord.AUTHENTICATE_ENCRYPT)
    	{
    		throw new IllegalArgumentException("Param \"security\" is not ServiceRecord.NOAUTHENTICATE_NOENCRYPT, ServiceRecord.AUTHENTICATE_NOENCRYPT, or ServiceRecord.AUTHENTICATE_ENCRYPT.");
    	}
    	
    	
    	//TODO return a string
    	throw new java.lang.RuntimeException("Not yet implemented");
    }

    
    
    
    /**
     * This method is only for testing purposes.
     * It takes elements from the vector and push it into the listeners.
     * 
     * */
    /*private void inquireCompleted()
    {
    	LocalDevice localDevice=null;
		try {
			localDevice = LocalDevice.getLocalDevice();
		} catch (BluetoothStateException e) {
			e.printStackTrace();
			return;
		}
		if(localDevice.isInquiring()==false){return;}
		
		localDevice.setInquiring(false);
		
		//notify all the listeners	
		for(DiscoveryListener listener:this._discoveryListener_giac)
		{
			for(RemoteDevice device:this._remoteDevice_cached)
			{
				DeviceClass deviceClass=device.getDeviceClass();
				//TODO giac -> if(deviceClass.)
				listener.deviceDiscovered(device, deviceClass);
			}
			for(RemoteDevice device:this._remoteDevice_preknown)
			{
				DeviceClass deviceClass=device.getDeviceClass();
				//TODO giac -> if(deviceClass.)
				listener.deviceDiscovered(device, deviceClass);
			}
			listener.inquiryCompleted(DiscoveryListener.INQUIRY_COMPLETED);
		}
		
		for(DiscoveryListener listener:this._discoveryListener_liac)
		{
			for(RemoteDevice device:this._remoteDevice_cached)
			{
				DeviceClass deviceClass=device.getDeviceClass();
				//TODO liac -> if(deviceClass.)
				listener.deviceDiscovered(device, deviceClass);
			}
			for(RemoteDevice device:this._remoteDevice_preknown)
			{
				DeviceClass deviceClass=device.getDeviceClass();
				//TODO liac -> if(deviceClass.)
				listener.deviceDiscovered(device, deviceClass);
			}
			listener.inquiryCompleted(DiscoveryListener.INQUIRY_COMPLETED);
		}
		
		
		//clear all the listener
		this._discoveryListener_giac.clear();
		this._discoveryListener_liac.clear();
    }*/


    /**this method is for testing purpose only and run inquireCompleted in order to notify listener of the discovered devices*/
	public void run() {
		/*try {
			Thread.sleep(this._simulatedInquireDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.inquireCompleted();*/
		//**********************************
		
		Vector<RemoteDevice> preknown=(Vector<RemoteDevice>) this._remoteDevice_preknown.clone();
		Vector<RemoteDevice> cached=(Vector<RemoteDevice>) this._remoteDevice_cached.clone();
		Vector<DiscoveryListener> liac=(Vector<DiscoveryListener>) this._discoveryListener_liac.clone();
		Vector<DiscoveryListener> giac=(Vector<DiscoveryListener>) this._discoveryListener_giac.clone();
		
		LocalDevice localDevice=null;
		try {
			localDevice = LocalDevice.getLocalDevice();
		} catch (BluetoothStateException e) {
			e.printStackTrace();
			return;
		}
		if(localDevice.isInquiring()==false){return;}
		localDevice.setInquiring(false);
		
		int size=preknown.size()+cached.size();
		if(size==0)
		{
			try {
				Thread.sleep(this._simulatedInquireDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else{
			long delay=this._simulatedInquireDelay/size;
			
			for(RemoteDevice device:this._remoteDevice_preknown)
			{
				DeviceClass deviceClass=device.getDeviceClass();
				for(DiscoveryListener listener:liac)
				{
					listener.deviceDiscovered(device, deviceClass);
				}
				for(DiscoveryListener listener:giac)
				{
					listener.deviceDiscovered(device, deviceClass);
				}
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for(RemoteDevice device:this._remoteDevice_cached)
			{
				
				DeviceClass deviceClass=device.getDeviceClass();
				for(DiscoveryListener listener:liac)
				{
					listener.deviceDiscovered(device, deviceClass);
				}
				for(DiscoveryListener listener:giac)
				{
					listener.deviceDiscovered(device, deviceClass);
				}
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		for(DiscoveryListener listener:liac)
		{
			listener.inquiryCompleted(DiscoveryListener.INQUIRY_COMPLETED);
		}
		for(DiscoveryListener listener:giac)
		{
			listener.inquiryCompleted(DiscoveryListener.INQUIRY_COMPLETED);
		}
		
		//		clear all the listener
		this._discoveryListener_giac.clear();
		this._discoveryListener_liac.clear();
		
	}



	/**
	 * Method for testing purpose only.
	 * Specify the simulated delay to any inquiry
	 * @param simulatedInquireDelay the simulatedInquireDelay to set
	 */
	public void setSimulatedInquireDelay(long _simulatedInquireDelay) {
		this._propertyChangeSupport.firePropertyChange(DiscoveryAgent.SIMULATED_INQUIRE_DELAY, this._simulatedInquireDelay, _simulatedInquireDelay);
		this._simulatedInquireDelay = _simulatedInquireDelay;
	}



	/**
	 * Method for testing purpose only.
	 * Get the simulated delay to any inquiry
	 * @return the simulatedInquireDelay
	 */
	public long getSimulatedInquireDelay() {
		return _simulatedInquireDelay;
	}
	
	



	/**
	 * Add a new RemoteDevice. this method is for testing purpose only
	 * @param arg0
	 * @see java.util.Vector#addElement(java.lang.Object)
	 */
	public void addRemoteDevice(RemoteDevice arg0, DeviceClass dClass) {
		arg0.setDeviceClass(dClass);
		_remoteDevice_cached.addElement(arg0);
		this._propertyChangeSupport.firePropertyChange(DiscoveryAgent.TEST_REMOTE_DEVICE_ADDED, null, arg0);
	}



	/**
	 * @param arg0
	 * @return
	 * @see java.util.Vector#elementAt(int)
	 */
	public RemoteDevice remoteDeviceAt(int arg0) {
		return _remoteDevice_cached.elementAt(arg0);
	}



	/**
	 * @param arg0
	 * @return
	 * @see java.util.Vector#removeElement(java.lang.Object)
	 */
	public boolean removeRemoteDevice(Object arg0) {
		boolean flag=_remoteDevice_cached.removeElement(arg0);
		if(flag==true)
		{
			this._propertyChangeSupport.firePropertyChange(DiscoveryAgent.TEST_REMOTE_DEVICE_REMOVED, null, arg0);
		}
		return flag;
	}



	/**
	 * @param arg0
	 * @see java.util.Vector#removeElementAt(int)
	 */
	public void removeRemoteDeviceAt(int arg0) {
		RemoteDevice rem=this._remoteDevice_cached.elementAt(arg0);
		this._propertyChangeSupport.firePropertyChange(DiscoveryAgent.TEST_REMOTE_DEVICE_REMOVED, null, rem);
		_remoteDevice_cached.removeElementAt(arg0);
	}



	/**
	 * @return
	 * @see java.util.Vector#size()
	 */
	public int remoteDevicesCount() {
		return _remoteDevice_cached.size();//+this._remoteDevice_preknown.size();
	}



	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {

		listener.propertyChange(new PropertyChangeEvent(this, DiscoveryAgent.SIMULATED_INQUIRE_DELAY,null,this._simulatedInquireDelay));

		for(int i=0;i<this.remoteDevicesCount();i++)
		{
			listener.propertyChange(new PropertyChangeEvent(this, DiscoveryAgent.TEST_REMOTE_DEVICE_ADDED,null,this.remoteDeviceAt(i)));
		}
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}



	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if(propertyName.equals(DiscoveryAgent.SIMULATED_INQUIRE_DELAY))
		{
			listener.propertyChange(new PropertyChangeEvent(this, DiscoveryAgent.SIMULATED_INQUIRE_DELAY,null,this._simulatedInquireDelay));
		}else if(propertyName.equals(DiscoveryAgent.TEST_REMOTE_DEVICE_ADDED))
		{
			for(int i=0;i<this.remoteDevicesCount();i++)
			{
				listener.propertyChange(new PropertyChangeEvent(this, DiscoveryAgent.TEST_REMOTE_DEVICE_ADDED,null,this.remoteDeviceAt(i)));
			}
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
	 * @param _simulatedFriendlyNameDelay the _simulatedFriendlyNameDelay to set
	 */
	public void setSimulatedFriendlyNameDelay(long _simulatedFriendlyNameDelay) {
		this._simulatedFriendlyNameDelay = _simulatedFriendlyNameDelay;
	}



	/**
	 * @return the _simulatedFriendlyNameDelay
	 */
	public long getSimulatedFriendlyNameDelay() {
		return _simulatedFriendlyNameDelay;
	}

    
    
    
}   /*  End of the definition of class DiscoveryAgent */
