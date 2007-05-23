package javax.microedition.location;
/**
 * The LandmarkException is thrown when an error related to handling landmarks has occurred.
 */
public class LandmarkException extends java.lang.Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7138978924074264633L;

	/**
     * Constructs a LandmarkException with no detail message.
     */
    public LandmarkException(){
    }

    /**
     * Constructs a LandmarkException with the specified detail message.
     * s - the detailed message
     */
    public LandmarkException(java.lang.String s){
         super(s);
    }

}
