package javax.microedition.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

/**
 * Represents exceptions thrown by the PIM classes.
 * This class has a reason code optionally associated with it
 * to provide more information about the PIM exception that occurred.
 *
 * @since PIM 1.0
 */
public class PIMException extends Exception {

    /**
     * Indicates a PIM exception where the functionality
     * is not supported in this implementation.
     */
	public static final int FEATURE_NOT_SUPPORTED = 0;
	
   /**
    * Indicates a general PIM exception error.  This is the
    * default value for the reason code.
    */
	public static final int GENERAL_ERROR = 1;
	
	/**
     * Indicates a PIM exception where a list is closed
     * and access is attempted.
     */
	public static final int LIST_CLOSED = 2;
	
	/**
     * Indicates a PIM exception where a list is no
     * longer accessible by the application, such as if
     * the underlying PIM database is deleted.
     */
	public static final int LIST_NOT_ACCESSIBLE = 3;
	
	/**
     * Indicates the max number of categories is exceeded.
     */
	public static final int MAX_CATEGORIES_EXCEEDED = 4;
	
	
	/**
     * Indicates the data is in an unsupported PIM version.
     */
	public static final int UNSUPPORTED_VERSION = 5;
	
	/**
     * Indicates a PIM exception where the update could not continue.
     */
	public static final int UPDATE_ERROR = 6;
	
	/**
	 * Default reason: GENERAL_ERROR
	 */
	private int reason = GENERAL_ERROR;

	
   /**
    * Constructs a new instance of this class with its stack trace filled in.
    *
    */
	public PIMException() {
		super();
	}

	/**
     * Constructs a new instance of this class with its
     * stacktrace and message filled in.
     *
     * @param		detailMessage String
     *				The detail message for the exception.
     */
	public PIMException(String detailMessage) {
		super(detailMessage);
	}

    /**
     * Constructs a new instance of this class with its
     * stacktrace, message, and reason filled in.
     *
     * @param	detailMessage String
     *			The detail message for the exception.
     * @param    reason int
     *           Integer representing the reason for the exception.
     */
	public PIMException(String detailMessage, int reason) {
		super(detailMessage);
		this.reason = reason;
	}

    /**
     * Returns the reason for the PIM Exception.  The int returned
     * is one of the values <code>PIMException.PIM_*</code> defined
     * in this class.
     *
     * @return   int reason for the exception.
     */
	public int getReason() {
		return reason;
	}
}