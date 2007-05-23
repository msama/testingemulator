package javax.microedition.io.file;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2003  All Rights Reserved
 */
 
/**
 * Represents an exception thrown when a method is invoked requiring a
 * particular security mode (e.g. READ or WRITE), but the connection opened 
 * is not in the mode required.  The application does pass all security
 * checks, but the connection object is in the wrong mode.
 *
 * @since FileConnection 1.0
 */

public class IllegalModeException extends java.lang.RuntimeException {

    /**
    * Constructs a new instance of this class with its stack trace filled in.
    *
    */
    public IllegalModeException() {
        super();
    }

    /**
    * Constructs a new instance of this class with its stack trace and message
    * filled in.
    *
    * @param  detailMessage String
    *         The detail message for the exception.
    */
    public IllegalModeException(String detailMessage) {
        super(detailMessage);
    }
}