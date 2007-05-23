package javax.microedition.location;
/**
 * The Landmark class represents a landmark, i.e. a known location with a name. A landmark has a name by which it is known to the end user, a textual description, QualifiedCoordinates and optionally AddressInfo.
 * This class is only a container for the information. The constructor does not validate the parameters passed in but just stores the values, except the name field is never allowed to be null. The get* methods return the values passed in the constructor or if the values are later modified by calling the set* methods, the get* methods return the modified values. The QualifiedCoordinates object inside the landmark is a mutable object and the Landmark object holds only a reference to it. Therefore, it is possible to modify the QualifiedCoordinates object inside the Landmark object by calling the set* methods in the QualifiedCoordinates object. However, any such dynamic modifications affect only the Landmark object instance, but MUST not automatically update the persistent landmark information in the landmark store. The LandmarkStore.updateLandmark method is the only way to commit the modifications to the persistent landmark store.
 * When the platform implementation returns Landmark objects, it MUST ensure that it only returns objects where the parameters have values set as described for their semantics in this class.
 *
 * The implementation of this method is based on the NOKIA official release of the JSR179
 *
 */
public class Landmark{
	
	private String name;
    private String description;
    private QualifiedCoordinates coordinates;
    private AddressInfo addressInfo;
    //private int id;
    //private int storeId;
    //private long categoryBitfield;
	
	
    /**
     * Constructs a new Landmark object with the values specified.
     * name - the name of the landmarkdescription - description of the landmark. May be null if not available.coordinates - the Coordinates of the landmark. May be null if not known.addressInfo - the textual address information of the landmark. May be null if not known.
     * java.lang.NullPointerException - if the name is null
     */
    public Landmark(java.lang.String name, java.lang.String description, javax.microedition.location.QualifiedCoordinates coordinates, javax.microedition.location.AddressInfo addressInfo){
    	//this.id = -1;
        //this.storeId = -1;
        //this.categoryBitfield = 0L;
        if(name == null)
        {
            throw new NullPointerException("Landmark name must not be null");
        } else
        {
            this.name = name;
            this.description = description;
            this.coordinates = coordinates;
            this.addressInfo = addressInfo;
            //this.id = -1;
            //this.storeId = -1;
            //this.categoryBitfield = 0L;
            return;
        }

    }

    /**
     * Gets the AddressInfo of the landmark.
     */
    public javax.microedition.location.AddressInfo getAddressInfo(){
        return this.addressInfo; 
    }

    /**
     * Gets the landmark description.
     */
    public java.lang.String getDescription(){
        return this.description; 
    }

    /**
     * Gets the landmark name.
     */
    public java.lang.String getName(){
        return this.name;
    }

    /**
     * Gets the QualifiedCoordinates of the landmark.
     */
    public javax.microedition.location.QualifiedCoordinates getQualifiedCoordinates(){
        return this.coordinates;
    }

    /**
     * Sets the AddressInfo of the landmark.
     */
    public void setAddressInfo(javax.microedition.location.AddressInfo addressInfo){
        this.addressInfo=addressInfo;
    }

    /**
     * Sets the description of the landmark.
     */
    public void setDescription(java.lang.String description){
        this.description=description;
    }

    /**
     * Sets the name of the landmark.
     */
    public void setName(java.lang.String name){
    	if(name == null)
        {
            throw new NullPointerException("Landmark name must not be null");
        } else
        {
            this.name = name;
            return;
        }
    }

    /**
     * Sets the QualifiedCoordinates of the landmark.
     */
    public void setQualifiedCoordinates(javax.microedition.location.QualifiedCoordinates coordinates){
        this.coordinates=coordinates;
    }

}
