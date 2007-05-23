package javax.microedition.location;
/**
 * The criteria used for the selection of the location provider is defined by the values in this class. It is up to the implementation to provide a LocationProvider that can obtain locations constrained by these values.
 * Instances of Criteria are used by the application to indicate criteria for choosing the location provider in the LocationProvider.getInstance method call. The implementation considers the different criteria fields to choose the location provider that best fits the defined criteria. The different criteria fields do not have any defined priority order but the implementation uses some implementation specific logic to choose the location provider that can typically best meet the defined criteria.
 * However, the cost criteria field is treated differently from others. If the application has set the cost field to indicate that the returned location provider is not allowed to incur financial cost to the end user, the implementation MUST guarantee that the returned location provider does not incur cost.
 * If there is no available location provider that is able to meet all the specified criteria, the implementation is allowed to make its own best effort selection of a location provider that is closest to the defined criteria (provided that the cost criteria is met). However, an implementation is not required to return a location provider if it does not have any available provider that is able to meet these criteria or be sufficiently close to meeting them, where the judgement of sufficiently close is an implementation dependent best effort choice. It is left up to the implementation to consider what is close enough to the specified requirements that it is worth providing the location provider to the application.
 * The default values for the criteria fields are specified below in the table. The default values are always the least restrictive option that will match all location providers. Default values: Criteria field Default value Horizontal accuracy NO_REQUIREMENT Vertical accuracy NO_REQUIREMENT Preferred response time NO_REQUIREMENT Power consumption NO_REQUIREMENT Cost allowed true (allowed to cost) Speed and course required false (not required) Altitude required false (not required) Address info required false (not required)
 * The implementation of this class only retains the values that are passed in using the set* methods. It does not try to validate the values of the parameters in any way. Applications may set any values it likes, even negative values, but the consequence may be that no matching LocationProvider can be created.
 *
 * The implementation of this method is based on the NOKIA official release of the JSR179
 *
 */
public class Criteria{
    /**
     * Constant indicating no requirements for the parameter.
     * See Also:Constant Field Values
     */
    public static final int NO_REQUIREMENT=0;

    /**
     * Level indicating high power consumption allowed.
     * See Also:Constant Field Values
     */
    public static final int POWER_USAGE_HIGH=3;

    /**
     * Level indicating only low power consumption allowed.
     * See Also:Constant Field Values
     */
    public static final int POWER_USAGE_LOW=1;

    /**
     * Level indicating average power consumption allowed.
     * See Also:Constant Field Values
     */
    public static final int POWER_USAGE_MEDIUM=2;

    
    
    private int horizontalAccuracy;
    private int verticalAccuracy;
    private int maxResponseTime;
    private int powerConsumption;
    private boolean costAllowed;
    private boolean speedRequired;
    private boolean altitudeRequired;
    private boolean addressInfoRequired;
    
    
    
    /**
     * Constructs a Criteria object. All the fields are set to the default values that are specified below in the specification of the set* methods for the parameters.
     */
    public Criteria(){
    	horizontalAccuracy = 0;
        verticalAccuracy = 0;
        maxResponseTime = 0;
        powerConsumption = 0;
        costAllowed = true;
        speedRequired = false;
        altitudeRequired = false;
        addressInfoRequired = false;
    }

    /**
     * Returns the horizontal accuracy value set in this Criteria.
     */
    public int getHorizontalAccuracy(){
        return this.horizontalAccuracy;
    }

    /**
     * Returns the preferred power consumption.
     */
    public int getPreferredPowerConsumption(){
    	 return this.powerConsumption;
    }

    /**
     * Returns the preferred maximum response time.
     */
    public int getPreferredResponseTime(){
        return this.maxResponseTime; 
    }

    /**
     * Returns the vertical accuracy value set in this Criteria.
     */
    public int getVerticalAccuracy(){
        return this.verticalAccuracy; 
    }

    /**
     * Returns whether the location provider should be able to determine textual address information.
     */
    public boolean isAddressInfoRequired(){
        return this.addressInfoRequired;
    }

    /**
     * Returns the preferred cost setting.
     */
    public boolean isAllowedToCost(){
        return this.costAllowed;
    }

    /**
     * Returns whether the location provider should be able to determine altitude.
     */
    public boolean isAltitudeRequired(){
        return this.altitudeRequired; 
    }

    /**
     * Returns whether the location provider should be able to determine speed and course.
     */
    public boolean isSpeedAndCourseRequired(){
        return this.speedRequired; 
    }

    /**
     * Sets whether the location provider should be able to determine textual address information. Setting this criteria to true implies that a location provider should be selected that is capable of providing the textual address information. This does not mean that every returned location instance necessarily will have all the address information filled in, though.
     * Default is false.
     */
    public void setAddressInfoRequired(boolean addressInfoRequired){
        this.addressInfoRequired=addressInfoRequired;
    }

    /**
     * Sets whether the location provider should be able to determine altitude. Default is false.
     */
    public void setAltitudeRequired(boolean altitudeRequired){
        this.altitudeRequired=altitudeRequired;
    }

    /**
     * Sets the preferred cost setting.
     * Sets whether the requests for location determination is allowed to incur any financial cost to the user of the terminal.
     * The default is true, i.e. the method is allowed to cost.
     * Note that the platform implementation may not always be able to know if a location method implies cost to the end user or not. If the implementation doesn't know, it MUST assume that it may cost. When this criteria is set to false, the implementation may only return a LocationProvider of which it is certain that using it for determining the location does not cause a per usage cost to the end user.
     */
    public void setCostAllowed(boolean costAllowed){
        this.costAllowed=costAllowed;
    }

    /**
     * Sets the desired horizontal accuracy preference. Accuracy is measured in meters. The preference indicates maximum allowed typical 1-sigma standard deviation for the location method. Default is NO_REQUIREMENT, meaning no preference on horizontal accuracy.
     */
    public void setHorizontalAccuracy(int accuracy){
        this.horizontalAccuracy=accuracy;
    }

    /**
     * Sets the preferred maximum level of power consumption.
     * These levels are inherently indeterminable and depend on many factors. It is the judgement of the implementation that defines a positioning method as consuming low power or high power. Default is NO_REQUIREMENT, meaning power consumption is not a quality parameter.
     */
    public void setPreferredPowerConsumption(int level){
        this.powerConsumption=level;
    }

    /**
     * Sets the desired maximum response time preference. This value is typically used by the implementation to determine a location method that typically is able to produce the location information within the defined time. Default is NO_REQUIREMENT, meaning no response time constraint.
     */
    public void setPreferredResponseTime(int time){
        this.maxResponseTime=time;
    }

    /**
     * Sets whether the location provider should be able to determine speed and course. Default is false.
     */
    public void setSpeedAndCourseRequired(boolean speedAndCourseRequired){
        this.speedRequired=speedAndCourseRequired;
    }

    /**
     * Sets the desired vertical accuracy preference. Accuracy is measured in meters. The preference indicates maximum allowed typical 1-sigma standard deviation for the location method. Default is NO_REQUIREMENT, meaning no preference on vertical accuracy.
     */
    public void setVerticalAccuracy(int accuracy){
        this.verticalAccuracy=accuracy;
    }

}
