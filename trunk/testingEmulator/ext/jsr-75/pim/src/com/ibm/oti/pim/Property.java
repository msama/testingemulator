package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.util.Vector;

import javax.microedition.pim.PIMException;


/**
 * A instance of this class represents a VCard2.1 or VCalendar 1.0 property.
 */
class Property {

	private String name = null;
	private Vector params = null;
	private Vector values = null;
	
	private int type = -1;
	
	public static final int VCARD = 0;
	public static final int VEVENT = 1;
	public static final int VTODO = 2;
	public static final int VCALENDAR = 3;
	
	// pim types
	public static final int CONTACT = VCARD; 
	public static final int EVENT = VEVENT;
	public static final int TODO = VTODO;
	
	private int encoding = -1;
	public static final int BASE64 = 1;
	public static final int QUOTED_PRINTABLE = 2;
	
	private boolean addToEntry = true; 
	
	Property(int type) {
		this.type = type;
		params = new Vector();
		values = new Vector();
	}
	
	/**
	 * Sets the name of this property.
	 * @param name
	 * @throws PIMException
	 */
	void setName(String name) throws PIMException {
		if (!isValidName(name))
			throw new PIMException("Unknown property: " + name);
		this.name = name;
	}
	
	/*
	 * Checks if the name is valid accordingly to the VCard2.1/VCalendar1.0 specification.
	 */
	private boolean isValidName(String name) {
		if (type == CONTACT) {
			if(PIMUtil.containsIgnoreCase(VCard.NAMES, name, true) || 
			   PIMUtil.startsWithIgnoreCase(name, VCard.EXTENDED_PREFIX_TAG) ||
			   PIMUtil.equalsIgnoreCase(name, VCard.BEGIN_TAG) ||
			   PIMUtil.equalsIgnoreCase(name, VCard.END_TAG))
				return true;
			else
				return false;
		}
		else {
			if(PIMUtil.containsIgnoreCase(VCalendar.NAMES, name, true) || 
			   PIMUtil.startsWithIgnoreCase(name, VCalendar.EXTENDED_PREFIX_TAG) ||
			   PIMUtil.equalsIgnoreCase(name, VCalendar.BEGIN_TAG) ||
			   PIMUtil.equalsIgnoreCase(name, VCalendar.END_TAG))
				return true;
			else
				return false;
		}
	}
	
	/**
	 * Adds a new parameter.
	 * @param param
	 * @throws PIMException
	 */
	void addParameter(String param) throws PIMException {
		if (!isValidParameter(param)) {
			StringBuffer message = new StringBuffer(80);
			message.append("Error in property ");
			message.append(getName());
			message.append(": invalid parameter ");
			message.append(param);
			throw new PIMException(message.toString());
		}
		params.addElement(param);
	}
	
	/*
	 * Checks if the parameter is valid accordingly to the VCard2.1/VCalendar1.0 specification.
	 */
	private boolean isValidParameter(String param) {
		if (type == CONTACT) {
			if(PIMUtil.containsIgnoreCase(VCard.KNOWN_TYPES, param, true) || 
			   PIMUtil.containsIgnoreCase(VCard.PARAMS_VALUE, param, false))
				return true;
			else
				return false;
		}
		else {
			if(PIMUtil.containsIgnoreCase(VCard.PARAMS_VALUE, param, false))
				return true;
			else
				return false;
		}
	}
	
	/**
	 * Adds a new value.
	 * @param value
	 * @throws PIMException
	 */
	void addValue(String value) throws PIMException {
		if (values.size() != 0) {
			if (type == VCARD && !PIMUtil.containsIgnoreCase(VCard.MULTIPLE_VALUES_NAMES, name, true))
				return;
			else if (type != VCARD && !PIMUtil.containsIgnoreCase(VCalendar.MULTIPLE_VALUES_NAMES, name, true))
				return;
		}
		values.addElement(value);
	}

	/**
	 * Answers the property's values.
	 * @return String[]
	 */
	String[] getValues() {
		String[] result = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			result[i] = (String)values.elementAt(i);
		}
		return result;
	}

	/**
	 * Returns the property's parameters.
	 * @return String[]
	 */
	String[] getParameters() {
		String[] result = new String[params.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (String) params.elementAt(i);
		}
		return result;
	}

	/**
	 * Returns the type.
	 * @return int
	 */
	int getType() {
		return type;
	}
	
	/**
	 * Sets the type of this property: VCARD, VCALENDAR, VTODO or VEVENT.
	 * @param type
	 */
	void setType(int type) {
		this.type = type; 
	}

	/**
	 * Returns the name of the property.
	 * @return String
	 */
	String getName() {
		return name;
	}

	/**
	 * Returns the encoding.
	 * @return int
	 */
	int getEncoding() {
		return encoding;
	}

	/**
	 * Sets the encoding.
	 * @param encoding The encoding to set
	 */
	void setEncoding(int encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * Sets if this property will be added or not.
	 * @param value
	 */
	void setAddToEntry(boolean value) {
		addToEntry = value;
	}
	
	/**
	 * Answers true if this property has to be added.
	 * @return boolean
	 */
	boolean hasToBeAdded() {
		return addToEntry;
	}
}