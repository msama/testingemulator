package javax.microedition.location;
/**
 * The LocationException is thrown when a location API specific error has occurred. The detailed conditions when this exception is thrown are documented in the methods that throw this exception.
 */
public class LocationException extends java.lang.Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6446593056960983840L;

	/**
     * Constructs a LocationException with no detail message.
     */
    public LocationException(){
         //TODO codavaj!!
    }

    /**
     * Constructs a LocationException with the specified detail message.
     * s - the detail message
     */
    public LocationException(java.lang.String s){
         super(s);
    }

}
