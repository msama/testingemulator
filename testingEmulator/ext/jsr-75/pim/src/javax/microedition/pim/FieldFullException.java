package javax.microedition.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

/**
 * Represents an exception thrown when an attempt is made to add data to a field
 * but the field already has all available slots for data values assigned.
 *
 * @since PIM 1.0
 */

public class FieldFullException extends java.lang.RuntimeException {

    private int offending_field = -1;

    /**
    * Constructs a new instance of this class with its stack trace filled in.
    *
    */
    public FieldFullException() {
        super();
    }

    /**
    * Constructs a new instance of this class with its stack trace and message
    * filled in.
    *
    * @param  detailMessage String
    *         The detail message for the exception.
    */
    public FieldFullException(String detailMessage) {
        super(detailMessage);
    }

    /**
    * Constructs a new instance of this class with its stack trace, message, and
    * offending field filled in.
    *
    * @param  detailMessage String The detail message for the exception.
    * @param  field int the offending field for the exception.
    */
    public FieldFullException(String detailMessage, int field) {
        super(detailMessage);
        offending_field = field;
    }

    /**
     * Method to access the field for which this exception is thrown.
     *
     * @return int representing the offending field.
     */
    public int getField() {
        return offending_field;
    }
}