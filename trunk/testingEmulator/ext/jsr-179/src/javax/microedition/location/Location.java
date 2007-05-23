package javax.microedition.location;

/**
 * The Location class represents the standard set of basic location information.
 * This includes the timestamped coordinates, accuracy, speed, course, and
 * information about the positioning method used for the location, plus an
 * optional textual address. The location method is indicated using a bit field.
 * The individual bits are defined using constants in this class. This bit field
 * is a bitwise combination of the location method technology bits (MTE_*),
 * method type (MTY_*) and method assistance information (MTA_*). All other bits
 * in the 32 bit integer than those that have defined constants in this class
 * are reserved and MUST not be set by implementations (i.e. these bits must be
 * 0). A Location object may be either 'valid' or 'invalid'. The validity can be
 * queried using the isValid method. A valid Location object represents a
 * location with valid coordinates and the getQualifiedCoordinates method must
 * return there coordinates. An invalid Location object doesn't have valid
 * coordinates, but the extra info that is obtained from the getExtraInfo method
 * can provide information about the reason why it was not possible to provide a
 * valid Location. For an invalid Location object, the getQualifiedCoordinates
 * method may return either null or some coordinates where the information is
 * not necessarily fully correct. The periodic location updates to the
 * LocationListener may return invalid Location objects if it isn't possible to
 * determine the location. This class is only a container for the information.
 * When the platform implementation returns Location objects, it MUST ensure
 * that it only returns objects where the parameters have values set as
 * described for their semantics in this class.
 */
public class Location {
	/**
	 * Location method is assisted by the other party (Terminal assisted for
	 * Network based, Network assisted for terminal based). MTA_ASSISTED =
	 * 0x00040000 See Also:Constant Field Values
	 */
	public static final int MTA_ASSISTED = 262144;

	/**
	 * Location method is unassisted. This bit and MTA_ASSISTED bit MUST NOT
	 * both be set. Only one of these bits may be set or neither to indicate
	 * that the assistance information is not known. MTA_UNASSISTED = 0x00080000
	 * See Also:Constant Field Values
	 */
	public static final int MTA_UNASSISTED = 524288;

	/**
	 * Location method Angle of Arrival for cellular / terrestrial RF system.
	 * MTE_ANGLEOFARRIVAL = 0x00000020 See Also:Constant Field Values
	 */
	public static final int MTE_ANGLEOFARRIVAL = 32;

	/**
	 * Location method Cell-ID for cellular (in GSM, this is the same as CGI,
	 * Cell Global Identity). MTE_CELLID = 0x00000008 See Also:Constant Field
	 * Values
	 */
	public static final int MTE_CELLID = 8;

	/**
	 * Location method using satellites (for example, Global Positioning System
	 * (GPS)). MTE_SATELLITE = 0x00000001 See Also:Constant Field Values
	 */
	public static final int MTE_SATELLITE = 1;

	/**
	 * Location method Short-range positioning system (for example, Bluetooth
	 * LP). MTE_SHORTRANGE = 0x00000010 See Also:Constant Field Values
	 */
	public static final int MTE_SHORTRANGE = 16;

	/**
	 * Location method Time Difference for cellular / terrestrial RF system (for
	 * example, Enhanced Observed Time Difference (E-OTD) for GSM).
	 * MTE_TIMEDIFFERENCE = 0x00000002 See Also:Constant Field Values
	 */
	public static final int MTE_TIMEDIFFERENCE = 2;

	/**
	 * Location method Time of Arrival (TOA) for cellular / terrestrial RF
	 * system. MTE_TIMEOFARRIVAL = 0x00000004 See Also:Constant Field Values
	 */
	public static final int MTE_TIMEOFARRIVAL = 4;

	/**
	 * Location method is of type network based. This means that the final
	 * location result is calculated in the network. This bit and
	 * MTY_TERMINALBASED bit MUST NOT both be set. Only one of these bits may be
	 * set or neither to indicate that it is not known where the result is
	 * calculated. MTY_NETWORKBASED = 0x00020000 See Also:Constant Field Values
	 */
	public static final int MTY_NETWORKBASED = 131072;

	/**
	 * Location method is of type terminal based. This means that the final
	 * location result is calculated in the terminal. MTY_TERMINALBASED =
	 * 0x00010000 See Also:Constant Field Values
	 */
	public static final int MTY_TERMINALBASED = 65536;

	
	private int _locationMethod=Location.MTE_SATELLITE;
	
	private long _timestamp=System.currentTimeMillis();
	
	private boolean _valid=false;
	
	private float _speed=0;
	
	private QualifiedCoordinates _coordinates=null;
	
	
	/**
	 * A protected constructor for the Location to allow implementations of
	 * LocationProviders to construct the Location instances. This method is not
	 * intended to be used by applications. This constructor sets the fields to
	 * implementation specific default values. Location providers are expected
	 * to set the fields to the correct values after constructing the object
	 * instance.
	 */
	public Location() {
		this.setValid(false);
		this.setLocationMethod(Location.MTE_SATELLITE);
		this.setQualifiedCoordinates(new QualifiedCoordinates(0,0,0,0,0));
	}
	
	public Location(int method,QualifiedCoordinates coordinates)
	{
		this.setLocationMethod(method);
		this.setValid(true);
		this.setQualifiedCoordinates(coordinates);
	}

	/**
	 * Returns the AddressInfo associated with this Location object. If no
	 * address is available, null is returned.
	 */
	public javax.microedition.location.AddressInfo getAddressInfo() {
		throw new java.lang.RuntimeException("Not yet implemented");
		//return null; // TODO codavaj!!
	}

	/**
	 * Returns the terminal's course made good in degrees relative to true
	 * north. The value is always in the range [0.0,360.0) degrees.
	 */
	public float getCourse() {
		throw new java.lang.RuntimeException("Not yet implemented");
		//return 0.0f; // TODO codavaj!!
	}

	/**
	 * Returns extra information about the location. This method is intended to
	 * provide location method specific extra information that applications that
	 * are aware of the used location method and information format are able to
	 * use. A MIME type is used to identify the type of the extra information
	 * when requesting it. If the implementation supports this type, it returns
	 * the extra information as a String encoded according to format identified
	 * by the MIME type. If the implementation does not support this type, the
	 * method returns null. This specification does not require implementations
	 * to support any extra information type. The following MIME types are
	 * defined here together with their definitions in order to ensure
	 * interoperability of implementations wishing to use these types. The
	 * definition of these types here is not an indication that these formats
	 * are preferred over any other format not defined here. When the MIME type
	 * is "application/X-jsr179-location-nmea", the returned string shall be a
	 * valid sequence of NMEA sentences formatted according to the syntax
	 * specified in the NMEA 0183 v3.1 specification. These sentences shall
	 * represent the set of NMEA sentences that are related to this location at
	 * the time this location was created. When the MIME type is
	 * "application/X-jsr179-location-lif", the returned string shall contain an
	 * XML formatted document containing the "pd" element defined in the LIF
	 * Mobile Location Protocol TS 101 v3.0.0 as the root element of the
	 * document. When the MIME type is "text/plain", the returned string shall
	 * contain textual extra information that can be displayed to the end user.
	 */
	public java.lang.String getExtraInfo(java.lang.String mimetype) {
		throw new java.lang.RuntimeException("Not yet implemented");
		//return null; // TODO codavaj!!
	}

	/**
	 * Returns information about the location method used. The returned value is
	 * a bitwise combination (OR) of the method technology, method type and
	 * assistance information. The method technology values are defined as
	 * constant values named MTE_* in this class, the method type values are
	 * named MTY_* and assistance information values are named MTA_*. For
	 * example, if the location method used is terminal based, network assisted
	 * E-OTD, the value 0x00050002 ( = MTY_TERMINALBASED | MTA_ASSISTED |
	 * MTE_TIMEDIFFERENCE) would be returned. If the location is determined by
	 * combining several location technologies, the returned value may have
	 * several MTE_* bits set. If the used location method is unknown, the
	 * returned value must have all the bits set to zero. Only bits that have
	 * defined constants within this class are allowed to be used. Other bits
	 * are reserved and must be set to 0.
	 */
	public int getLocationMethod() {
		return this._locationMethod;
	}
	
	public void setLocationMethod(int method)
	{
		this._locationMethod=method;
	}

	/**
	 * Returns the coordinates of this location and their accuracy.
	 */
	public javax.microedition.location.QualifiedCoordinates getQualifiedCoordinates() {
		return this._coordinates;
	}
	
	public void setQualifiedCoordinates(QualifiedCoordinates coordinates)
	{
		this._coordinates=coordinates;
	}

	/**
	 * Returns the terminal's current ground speed in meters per second (m/s) at
	 * the time of measurement. The speed is always a non-negative value. Note
	 * that unlike the coordinates, speed does not have an associated accuracy
	 * because the methods used to determine the speed typically are not able to
	 * indicate the accuracy.
	 */
	public float getSpeed() {
		return this._speed;
	}
	
	public void setSpeed(float speed)
	{
		if(speed<0)
		{
			throw new java.lang.IllegalArgumentException("Parameter speed must be >0.");
		}
		this._speed=speed;
	}

	/**
	 * Returns the time stamp at which the data was collected. This timestamp
	 * should represent the point in time when the measurements were made.
	 * Implementations make best effort to set the timestamp as close to this
	 * point in time as possible. The time returned is the time of the local
	 * clock in the terminal in milliseconds using the same clock and same time
	 * representation as System.currentTimeMillis().
	 */
	public long getTimestamp() {
		return this._timestamp;
	}

	/**
	 * Returns whether this Location instance represents a valid location with
	 * coordinates or an invalid one where all the data, especially the latitude
	 * and longitude coordinates, may not be present. A valid Location object
	 * contains valid coordinates whereas an invalid Location object may not
	 * contain valid coordinates but may contain other information via the
	 * getExtraInfo() method to provide information on why it was not possible
	 * to provide a valid Location object.
	 */
	public boolean isValid() {
		return this._valid;
	}

	public void setValid(Boolean valid)
	{
		this._valid=valid;
	}
}
