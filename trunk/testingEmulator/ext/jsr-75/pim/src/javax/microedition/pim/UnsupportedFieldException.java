package javax.microedition.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

/**
 * Represents an exception thrown when a field is referenced that is not
 * supported in the particular PIM list that an element belongs to.
 *
 * @since PIM 1.0
 */
public class UnsupportedFieldException extends RuntimeException {
	
	/**
	 * The offending field
	 */
	private int field = -1;

    /**
     * Constructs a new instance of this class with its stack trace filled in.
     *
     */
	public UnsupportedFieldException() {
		super();
	}

    /**
     * Constructs a new instance of this class with its stack trace and message
     * filled in.
     *
     * @param  detailMessage String
     *         The detail message for the exception.
     */
	public UnsupportedFieldException(String detailMessage) {
		super(detailMessage);
	}

    /**
     * Constructs a new instance of this class with its stack trace, message, and
     * offending field filled in.
     *
     * @param  detailMessage String The detail message for the exception.
     * @param  field int the offending field for the exception.
     */
	public UnsupportedFieldException(String detailMessage, int field) {
		super(detailMessage);
		this.field = field;
	}

    /**
     * Method to access the field for which this exception is thrown.
     *
     * @return int representing the offending field.
     */
	public int getField() {
		return field;
	}
}