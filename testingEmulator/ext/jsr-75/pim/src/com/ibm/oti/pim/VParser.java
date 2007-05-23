package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.io.IOException;
import java.util.Vector;

import javax.microedition.pim.PIMException;

/**
 * An instance of this class is a VCard 2.1 / VCalendar 1.0 parser.
 */
class VParser {

	UTF8InputStreamReader in;
	
	StringBuffer groupsAndName, params, values;
	StringBuffer buffer;
	
	Vector entry = new Vector();
	Vector entity = new Vector();
	
	int type;
	
	private int flag;
	private static final int CHECK_NEW_ENTRY = 0x01;
	private static final int CHECK_EOE = 0x02;	
	
	private boolean parsing = false;
	private boolean endOfEntry = false;
	private boolean readingProperty = false;
	
	private final static String VCARD_TAG_CRLF =  VCard.VCARD_TAG + PIMUtil.CRLF;
	private final static String VCALENDAR_TAG_CRLF = VCalendar.VCALENDAR_TAG + PIMUtil.CRLF;
	private final static String VEVENT_TAG_CRLF = VCalendar.EVENT_TAG + PIMUtil.CRLF;
	private final static String VTODO_TAG_CRLF = VCalendar.TODO_TAG + PIMUtil.CRLF;
	
	private Vector propertyGroups = new Vector();
	
	/*	0 - no encoding
	 *  1 - BASE64 
	 *  2 - QuotedPrintable
	 */
	private int encoding = 0;

	Property property;
	
	int previousState = 0;
	int state = 0;
	
							/*	':'  ','  ';'  '.'  '='  '\r' '\n' other */
	final int[][] states = { { -1,  -1,  -1,  -1,   1,   1,   1,   1 },
							  {  4,  -1,   2,   0,   1,   1,   1,   1 },
							  { -1,  -1,  -1,  -1,  -1,  -1,  -1,   3 },
							  { -3,  -1,   2,  -1,   2,   3,   3,   3 },
							  {  4,   4,   4,   4,   4,   5,   4,   4 }, // no encoding
							  {  4,   4,   4,   4,   4,   5,  -2,   4 },
							  {  6,   6,   6,   6,   6,   7,   6,   6 }, // base 64
							  {  6,   6,   6,   6,   6,   7,   8,   6 },
							  {  6,   6,   6,   6,   6,   9,   6,   6 },
							  {  6,   6,   6,   6,   6,   7,  -2,   6 },
							  { 10,  10,  10,  10,  11,  13,  10,  10 }, // quoted-printable
							  { 10,  10,  10,  10,  11,  12,  10,  10 },
							  { 10,  10,  10,  10,  11,  13,  10,  10 },
							  { 10,  10,  10,  10,  11,  13,  -2,  10 }};


	VParser(UTF8InputStreamReader in) {
		this.in = in;
	}


	/**
	 * Parses the input stream.
	 * @param properties. A list of properties for an entry.
	 * @param type. The type of entry: VCARD, VEVENT, VTODO or VCALENDAR.
	 * @return Vector. a vector of all properties for one entry. 
	 * @throws IOException
	 * @throws PIMException
	 */
	Vector parse(Vector properties, int type) throws IOException, PIMException {
		this.type = type;
		if (properties != null) {
			for (int i = 0; i < properties.size(); i++) {
				// update the type
				Property property = (Property) properties.elementAt(i);
				property.setType(type);
				// add into the current element
				entity.addElement(property);
			}
		}

		init();
		boolean readNext = true;
		while (readNext) {
			if (!readingProperty) {
				storeBuffer(values);
				if (property.hasToBeAdded())
					entity.addElement(property);
				init();
			}
			if (type != -1)
				parsing = true;
			char currentChar = in.readChar();
			readNext = 	readNext(currentChar);
		}
		parsing = false;
		
		if (entry.size() == 0)
			entry.addElement(entity);

		return entry;
	}

	/*
	 * Stores the buffer value into the current property
	 */
	private void storeBuffer(StringBuffer buff) throws PIMException {		
		if (buff == groupsAndName) {
			
			buff.deleteCharAt(buff.length() - 1);
			trim(buff);
			
			String name = buff.toString();
			int index = name.lastIndexOf('.');
			if (index == -1)
				property.setName(name);
			else {
				String groups =  name.substring(0, index);
				if (propertyGroups.size() == 0 || !propertyGroups.contains(groups)) {
					propertyGroups.addElement(name.substring(0, index));
				}
				else // grouping not supported
					property.setAddToEntry(false);
				name = name.substring(index + 1);
					property.setName(name.trim());
			}
		}
		else if (buff == values) {
			String data = null;
			try {
				if(encoding == Property.BASE64) {
					data = new String(EncodingHelper.decodeBASE64(buff.toString()));
					property.setEncoding(Property.BASE64);
				}
				else if (encoding == Property.QUOTED_PRINTABLE) {
					data = EncodingHelper.decodeQuotedPrintable(values.toString());
					property.setEncoding(Property.QUOTED_PRINTABLE);
				}
				else
					data = values.toString();
			}
			catch (Exception e) {
				throw new PIMException("Incorrect data.");
			}
			
			data = data.trim();
			
			int index = 0, previousIndex = 0;
			
			// the separator is ',' for exception dates 
			char separator = PIMUtil.equalsIgnoreCase(property.getName(), VCalendar.EXCEPTION_DATE_TAG) ? ',' : ';';
			
			while ((index = data.indexOf(separator, previousIndex)) != -1) {
				property.addValue(data.substring(previousIndex, index).trim());
				previousIndex = index + 1;
			}
			index = data.lastIndexOf(separator);
			property.addValue(data.substring(index == -1 ? 0 : index + 1, data.length()).trim());

		}
		else if (buff == params) {
			buff.deleteCharAt(params.length() - 1);
			trim(buff);
			String paramaters = buff.toString();
			
			int index = 0, previousIndex = 0;
			while ((index = paramaters.indexOf(';', previousIndex)) != -1) {
				property.addParameter(paramaters.substring(previousIndex, index).trim());
				previousIndex = index + 1;
			}
			index = paramaters.lastIndexOf(';');
			property.addParameter(paramaters.substring(index == -1 ? 0 : index + 1, paramaters.length()).trim());
		}
	}
	
	/*
	 * Consumes the whitespaces and CRLF characters 
	 * at the beginning and the end of the buffer.
	 */
	private void trim(StringBuffer buffer) {
		if (buffer.length() == 0)
			return;
		while (true) {
			char c = buffer.charAt(0);
			if (c == ' ' || c == '\r' || c == '\n')
				buffer.deleteCharAt(0);
			else break;
		}
		int index = buffer.length() - 1;
		while (true) {
			char c = buffer.charAt(index);
			if (c == ' ' || c == '\r' || c == '\n') {
				buffer.deleteCharAt(index);
				index = buffer.length() - 1;
				if (index == -1)
					break;
			} else
				break;
		}
			
	}

	/*
	 * Initialization
	 */
	private void init() {	
		flag = CHECK_NEW_ENTRY | CHECK_EOE;	
		property = new Property(type);
		groupsAndName = new StringBuffer();
		params = new StringBuffer();
		values = new StringBuffer();
		
		buffer = groupsAndName;
		
		previousState = 0;
		state = 0;
		
		encoding = 0;
		
		endOfEntry = false;
		readingProperty = true;
	}
	
	/*
	 * Answers if the there is more character to read.
	 */
	private boolean readNext(char oneChar) throws IOException, PIMException {
		
		if (endOfEntry)
			return false;
			
		buffer.append(oneChar);
		
		setState(oneChar);
		
		if (state == -1)
			throw new PIMException("Invalid format.");
		
		if (state == -2)
			readingProperty = false; // end of property;
		else
			readingProperty = true;
		
				
		if (state == 2 && buffer.equals(groupsAndName)) {
			storeBuffer(groupsAndName);
			buffer = params;
		}
		
		if (state > 3 && (buffer.equals(params) || buffer.equals(groupsAndName))) {
			storeBuffer(buffer.equals(params) ? params : groupsAndName);
			buffer = values;
		}
		
		if (state > 3 || state == -2) {
			checkNewEntry();
			return !isEndOfEntryReached();
		}					
		return true;
	}
	
	/*
	 * Set the new state.
	 */
	private void setState(char oneChar) throws PIMException {
		int column = getColumnIndex(oneChar);
		previousState = state;
		if (state < 0)
			throw new PIMException("Invalid format.");
		state = states[state][column];
		if (state == -3) {
			storeBuffer(params);
			buffer = values;
			state = checkEncoding();
		}
	}
	
	/*
	 * Checks if the property values are encoded. 
	 */
	private int checkEncoding() throws PIMException {
		String[] parameters = property.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			String param = parameters[i];
			if (PIMUtil.startsWithIgnoreCase(param, VCalendar.ATTR_ENCODING_TAG)) {
				StringBuffer buf = new StringBuffer(param);
				buf.delete(0, VCalendar.ATTR_ENCODING_TAG.length() + 1);
				String encoding = buf.toString();
				if (PIMUtil.equalsIgnoreCase(encoding, PIMUtil.BASE64_ENCODING)) {
					this.encoding = Property.BASE64;
					return 6;
				}
				if (PIMUtil.equalsIgnoreCase(encoding, PIMUtil.QUOTED_PRINTABLE_ENCODING)) {
					this.encoding = Property.QUOTED_PRINTABLE;
					return 10;
				}
				else
					throw new PIMException("Unsupported encoding");
			}
		}
		return 4;
	}
	
	/*
	 * Checks if a new entry occurs.
	 */
	private void checkNewEntry() throws IOException, PIMException {
		
		if ((flag&CHECK_NEW_ENTRY) == 0)
			return;
		
		int entryType = -1;
		
		if (PIMUtil.equalsIgnoreCase(this.property.getName(), VCard.BEGIN_TAG)) {
			String valueStr = values.toString();
			if (PIMUtil.equalsIgnoreCase(valueStr, VCARD_TAG_CRLF))
				entryType = Property.VCARD;
			else if (PIMUtil.equalsIgnoreCase(valueStr, VCALENDAR_TAG_CRLF))
				entryType = Property.VCALENDAR;
			else if (PIMUtil.equalsIgnoreCase(valueStr, VEVENT_TAG_CRLF))
				entryType = Property.VEVENT;
			else if (PIMUtil.equalsIgnoreCase(valueStr, VTODO_TAG_CRLF))
				entryType = Property.VTODO;
		}
		else
			flag &= ~CHECK_NEW_ENTRY; // do not need to test until next property

		if (entryType != -1) {
			Vector elements = null;
			
			this.property.setAddToEntry(false);
			
			if (parsing) {
				elements = new VParser(in).parse(entity, entryType);
			}
			else {
				parsing = true;
				type = entryType;
			}
			
			if (elements != null) {
				for (int i = 0; i < elements.size(); i++)
					entry.addElement(elements.elementAt(i));
			}
		}
	}
	
	/*
	 * Answers an index depending on the char.
	 */
	private int getColumnIndex(char oneChar) {
		switch (oneChar) {
			case ':':
				return 0;
			case ',':
				return 1;
			case ';':
				return 2;
			case '.':
				return 3;
			case '=':
				return 4;
			case '\r':
				return 5;
			case '\n':
				return 6;
			default:
				return 7;
		}
	}
	
	/*
	 * Answers if the end of the entry is reached.
	 */
	private boolean isEndOfEntryReached() {
		if ((flag & CHECK_EOE) == 0)
			return false;
		
		if (PIMUtil.equalsIgnoreCase(groupsAndName.toString(), VCard.END_TAG)) {
			String valuesStr = values.toString();
			if (PIMUtil.equalsIgnoreCase(valuesStr, VCard.VCARD_TAG) ||
			    PIMUtil.equalsIgnoreCase(valuesStr, VCalendar.VCALENDAR_TAG) ||
			    PIMUtil.equalsIgnoreCase(valuesStr, VCalendar.EVENT_TAG) ||
		    	PIMUtil.equalsIgnoreCase(valuesStr, VCalendar.TODO_TAG)) 
			{
				endOfEntry = true;
				return true;
			}
		}
		else
			flag &= ~CHECK_EOE; // do not need to test until next property
		return false;
	}
}