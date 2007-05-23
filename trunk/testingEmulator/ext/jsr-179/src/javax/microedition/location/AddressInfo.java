package javax.microedition.location;

import java.util.Hashtable;

/**
 * The AddressInfo class holds textual address information about a location. Typically the information is e.g. street address. The information is divided into fields (e.g. street, postal code, city, etc.). Defined field constants can be used to retrieve field data.
 * If the value of a field is not available, it is set to null.
 * The names of the fields use terms and definitions that are commonly used e.g. in the United States. Addresses for other countries should map these to the closest corresponding entities used in that country.
 * This class is only a container for the information. The getField method returns the value set for the defined field using the setField method. When the platform implementation returns AddressInfo objects, it MUST ensure that it only returns objects where the parameters have values set as described for their semantics in this class.
 * Below are some typical examples of addresses in different countries and how they map to the AddressInfo fields.
 */
public class AddressInfo{
    /**
     * Address field denoting a building floor.
     * See Also:Constant Field Values
     */
    public static final int BUILDING_FLOOR=11;

    /**
     * Address field denoting a building name.
     * See Also:Constant Field Values
     */
    public static final int BUILDING_NAME=10;

    /**
     * Address field denoting a building room.
     * See Also:Constant Field Values
     */
    public static final int BUILDING_ROOM=12;

    /**
     * Address field denoting a building zone
     * See Also:Constant Field Values
     */
    public static final int BUILDING_ZONE=13;

    /**
     * Address field denoting town or city name.
     * See Also:Constant Field Values
     */
    public static final int CITY=4;

    /**
     * Address field denoting country.
     * See Also:Constant Field Values
     */
    public static final int COUNTRY=7;

    /**
     * Address field denoting country as a two-letter ISO 3166-1 code.
     * See Also:Constant Field Values
     */
    public static final int COUNTRY_CODE=8;

    /**
     * Address field denoting a county, which is an entity between a state and a city
     * See Also:Constant Field Values
     */
    public static final int COUNTY=5;

    /**
     * Address field denoting a street in a crossing.
     * See Also:Constant Field Values
     */
    public static final int CROSSING1=14;

    /**
     * Address field denoting a street in a crossing.
     * See Also:Constant Field Values
     */
    public static final int CROSSING2=15;

    /**
     * Address field denoting a municipal district.
     * See Also:Constant Field Values
     */
    public static final int DISTRICT=9;

    /**
     * Address field denoting address extension, e.g. flat number.
     * See Also:Constant Field Values
     */
    public static final int EXTENSION=1;

    /**
     * Address field denoting a phone number for this place.
     * See Also:Constant Field Values
     */
    public static final int PHONE_NUMBER=17;

    /**
     * Address field denoting zip or postal code.
     * See Also:Constant Field Values
     */
    public static final int POSTAL_CODE=3;

    /**
     * Address field denoting state or province.
     * See Also:Constant Field Values
     */
    public static final int STATE=6;

    /**
     * Address field denoting street name and number.
     * See Also:Constant Field Values
     */
    public static final int STREET=2;

    /**
     * Address field denoting a URL for this place.
     * See Also:Constant Field Values
     */
    public static final int URL=16;

    
    private Hashtable<Integer,String> _info=new Hashtable<Integer, String>();
    
    /**
     * Constructs an AddressInfo object with all the values of the fields set to null.
     */
    public AddressInfo(){
    }

    /**
     * Returns the value of an address field. If the field is not available null is returned.
     * Example: getField(AddressInfo.STREET) might return "113 Broadway" if the location is on Broadway, New York, or null if not available.
     */
    public java.lang.String getField(int field){
        return this._info.get(new Integer(field));
    }

    /**
     * Sets the value of an address field.
     */
    public void setField(int field, java.lang.String value){
        switch(field)
        {
        	case AddressInfo.BUILDING_FLOOR:
        	case AddressInfo.BUILDING_NAME:
        	case AddressInfo.BUILDING_ROOM:
        	case AddressInfo.BUILDING_ZONE:
        	case AddressInfo.CITY:
        	case AddressInfo.COUNTRY:
        	case AddressInfo.COUNTRY_CODE:
        	case AddressInfo.COUNTY:
        	case AddressInfo.CROSSING1:
        	case AddressInfo.CROSSING2:
        	case AddressInfo.DISTRICT:
        	case AddressInfo.EXTENSION:
        	case AddressInfo.PHONE_NUMBER:
        	case AddressInfo.POSTAL_CODE:
        	case AddressInfo.STATE:
        	case AddressInfo.STREET:
        	case AddressInfo.URL:
        	{
        		this._info.put(field, value);
        	}
        }
    }

}
