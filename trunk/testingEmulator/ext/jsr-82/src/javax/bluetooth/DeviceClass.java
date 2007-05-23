/*
 *  DeviceClass.java
 *
 *  $Revision$ $Date$ 
 * 
 *  (c) Copyright 2001, 2002 Motorola, Inc.  ALL RIGHTS RESERVED.
 */
package javax.bluetooth;


/**
 * The <code>DeviceClass</code> class represents the class of device (CoD)
 * record as defined by the Bluetooth specification.  This record is defined in
 * the Bluetooth Assigned Numbers document
 * and contains information on the type of the device and the type of services
 * available on the device.
 * <P>
 * The Bluetooth Assigned Numbers document
 * (<A HREF="http://www.bluetooth.org/assigned-numbers/baseband.htm">
 * http://www.bluetooth.org/assigned-numbers/baseband.htm</A>)
 * defines the service class, major device class, and minor device class.  The
 * table below provides some examples of possible return values and their
 * meaning:
 * <TABLE>
 * <TR><TH>Method</TH><TH>Return Value</TH><TH>Class of Device</TH></TR>
 * <TR><TD><code>getServiceClasses()</code></TD>
 * <TD>0x22000</TD>
 * <TD>Networking and Limited Discoverable Major Service Classes</TD></TR>
 * <TR><TD><code>getServiceClasses()</code></TD>
 * <TD>0x100000</TD>
 * <TD>Object Transfer Major Service Class</TD></TR>
 * <TR><TD><code>getMajorDeviceClass()</code></TD>
 * <TD>0x00</TD>
 * <TD>Miscellaneous Major Device Class</TD></TR>
 * <TR><TD><code>getMajorDeviceClass()</code></TD>
 * <TD>0x200</TD>
 * <TD>Phone Major Device Class</TD></TR>
 * <TR><TD><code>getMinorDeviceClass()</code></TD>
 * <TD>0x0C</TD><TD>With a Computer Major Device Class,
 *   Laptop Minor Device Class</TD></TR>
 * <TR><TD><code>getMinorDeviceClass()</code></TD>
 * <TD>0x04</TD><TD>With a Phone Major Device Class,
 *   Cellular Minor Device Class</TD></TR>
 * </TABLE>
 *
 * @version 1.0 February 11, 2002
 */
public class DeviceClass {

	private int _record;
	
	/**
	 * Bitmask for the Forma Type field.
	 * refers to http://www.bluetooth.org/assigned-numbers/baseband.htm
	 * 0000 0000 0000 0000 0000 0011
	 * */
	private static int BITMASK_FORMAT_TYPE=Integer.parseInt("000000000000000000000011", 2);
	
	/**
	 * Bitmask for the Minor Device Class field.
	 * refers to http://www.bluetooth.org/assigned-numbers/baseband.htm
	 * 0000 0000 0000 0000 1111 1100
	 * */
	private static int BITMASK_MINOR_DEVICE=Integer.parseInt("000000000000000011111100", 2);
	
	/**
	 * Bitmask for the Major Device Class field.
	 * refers to http://www.bluetooth.org/assigned-numbers/baseband.htm
	 * 0000 0000 0001 1111 0000 0000
	 * */
	private static int BITMASK_MAJOR_DEVICE=Integer.parseInt("000000000001111100000000", 2);
	
	/**
	 * Bitmask for the Reserved Service Class field.
	 * refers to http://www.bluetooth.org/assigned-numbers/baseband.htm
	 * 1111 1111 1110 0000 0000 0000
	 * */
	private static int BITMASK_RESERVED_SERVICE=Integer.parseInt("111111111110000000000000", 2);
	
    /**
     * Creates a <code>DeviceClass</code> from the class of device record
     * provided.  <code>record</code> must follow the format of the
     * class of device record in the Bluetooth specification.
     *
     * @param record describes the classes of a device
     *
     * @exception IllegalArgumentException if <code>record</code> has any bits
     * between 24 and 31 set
     */
    public DeviceClass(int record) {
    	this._record=record;
    }

    /**
     * Retrieves the major service classes.  A device may have multiple major
     * service classes.  When this occurs, the major service classes are
     * bitwise OR'ed together.
     *
     * @return the major service classes
     */
    public int getServiceClasses() {
    	return (DeviceClass.BITMASK_RESERVED_SERVICE & this._record);
    }

    /**
     * Retrieves the major device class.  A device may have only a single major
     * device class.
     *
     * @return the major device class
     */
    public int getMajorDeviceClass() {
    	return (DeviceClass.BITMASK_MAJOR_DEVICE & this._record);
    }

    /**
     * Retrieves the minor device class.
     *
     * @return the minor device class
     */
    public int getMinorDeviceClass() {
    	return (DeviceClass.BITMASK_MINOR_DEVICE & this._record);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DeviceClass)
		{
			DeviceClass dev=(DeviceClass)obj;
			return this._record==dev._record;
		}else
		{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ""+this._record;
	}
}
