package javax.microedition.location;
/**
 * The QualifiedCoordinates class represents coordinates as latitude-longitude-altitude values that are associated with an accuracy value.
 */
public class QualifiedCoordinates extends javax.microedition.location.Coordinates{
   
    private float _horizontalAccuracy;
    private float _verticalAccuracy;
	
	
	/**
     * Constructs a new QualifiedCoordinates object with the values specified. The latitude and longitude parameters are expressed in degrees using floating point values. The degrees are in decimal values (rather than minutes/seconds).
     * The coordinate values always apply to the WGS84 datum.
     * The Float.NaN value can be used for altitude to indicate that altitude is not known.
     * latitude - the latitude of the location. Valid range: [-90.0, 90.0]longitude - the longitude of the location. Valid range: [-180.0, 180.0)altitude - the altitude of the location in meters, defined as height above WGS84 ellipsoid. Float.NaN can be used to indicate that altitude is not known.horizontalAccuracy - the horizontal accuracy of this location result in meters. Float.NaN can be used to indicate that the accuracy is not known. Must be greater or equal to 0.verticalAccuracy - the vertical accuracy of this location result in meters. Float.NaN can be used to indicate that the accuracy is not known. Must be greater or equal to 0.
     * java.lang.IllegalArgumentException - if an input parameter is out of the valid range
     */
    public QualifiedCoordinates(double latitude, double longitude, float altitude, float horizontalAccuracy, float verticalAccuracy){
    	super(latitude,longitude,altitude);
    	this.setHorizontalAccuracy(horizontalAccuracy);
    	this.setVerticalAccuracy(verticalAccuracy);
    }

    /**
     * Returns the horizontal accuracy of the location in meters (1-sigma standard deviation). A value of Float.NaN means the horizontal accuracy could not be determined.
     * The horizontal accuracy is the RMS (root mean square) of east accuracy (latitudinal error in meters, 1-sigma standard deviation), north accuracy (longitudinal error in meters, 1-sigma).
     */
    public float getHorizontalAccuracy(){
        return this._horizontalAccuracy;
    }

    /**
     * Returns the accuracy of the location in meters in vertical direction (orthogonal to ellipsoid surface, 1-sigma standard deviation). A value of Float.NaN means the vertical accuracy could not be determined.
     */
    public float getVerticalAccuracy(){
        return this._verticalAccuracy;
    }

    /**
     * Sets the horizontal accuracy of the location in meters (1-sigma standard deviation). A value of Float.NaN means the horizontal accuracy could not be determined.
     * The horizontal accuracy is the RMS (root mean square) of east accuracy (latitudinal error in meters, 1-sigma standard deviation), north accuracy (longitudinal error in meters, 1-sigma).
     *
     * NOKIA implementation of this method
     */
    public void setHorizontalAccuracy(float horizontalAccuracy){
    	if(Float.isNaN(horizontalAccuracy) || !Float.isNaN(horizontalAccuracy) && horizontalAccuracy >= 0.0F)
    	{
            this._horizontalAccuracy = horizontalAccuracy;
    	}else
    	{
            throw new IllegalArgumentException("Illegal parameter for horizontal accuracy");
    	}
    }

    /**
     * Sets the accuracy of the location in meters in vertical direction (orthogonal to ellipsoid surface, 1-sigma standard deviation). A value of Float.NaN means the vertical accuracy could not be determined.
     *
     * NOKIA implementation of this method
     */
    public void setVerticalAccuracy(float verticalAccuracy){
    	if(Float.isNaN(verticalAccuracy) || !Float.isNaN(verticalAccuracy) && verticalAccuracy >= 0.0F)
    	{
            this._verticalAccuracy=verticalAccuracy;
    	}else
    	{
            throw new IllegalArgumentException("Illegal parameter for vertical accuracy");
        }
    }

}
