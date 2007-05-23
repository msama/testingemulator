package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.pim.Contact;
import javax.microedition.pim.Event;
import javax.microedition.pim.FieldEmptyException;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import javax.microedition.pim.PIMItem;
import javax.microedition.pim.PIMList;
import javax.microedition.pim.RepeatRule;
import javax.microedition.pim.ToDo;


public class PIMImpl extends PIM {

	/**
	 * List of open PIMLists.
	 */
	private static Hashtable lists = new Hashtable();
	
	/**
	 * Name of the property specifying the access rights.
	 */
	private final static String PROPERTY_ACCESS_MODE = "javax.microedition.pim.access_mode";
	
	/**
	 * Value of the access mode property when the mode readonly.
	 */
	private final static String STRING_READ_ONLY = "READ_ONLY";
	
	/**
	 * Value of the access mode property when the mode writeonly.
	 */
	private final static String STRING_WRITE_ONLY = "WRITE_ONLY";
	
	/**
	 * Value of the access mode property when the mode readwrite.
	 */
	private final static String STRING_READ_WRITE = "READ_WRITE";
	
	private boolean eventDatesExported = false;
	
	/**
	 * Constructor for PIMImpl.
	 */
	public PIMImpl() {
		super();
	}

	/**
	 * Opens a PIMList and returns its handle.
	 * @param pimListType. CONTACT_LIST, EVENT_LIST or TODO_LIST.
	 * @param mode. READY_ONLY, WRITE_ONLY or READ_WRITE.
	 * @param name. The list name.
	 * @return int. The handle for this list.
	 * @throws PIMException if the operation is unsupported or an error occurs. 
	 */
	private native int openPIMListN(int pimListType, int mode, String name) throws PIMException;
	
	/**
	 * Answers a list of all PIMLists available.
	 * @param pimListType. CONTACT_LIST, EVENT_LIST or TODO_LIST.
	 * @return String[]. The list of PIMLists or a zero length array 
	 * if no lists are available or an error occurs.
	 */
	private native String[] listPIMListsN(int pimListType) ;
	
	/**
	 * @see javax.microedition.pim.PIM#openPIMList(int, int)
	 */
	public PIMList openPIMList(int pimListType, int mode) throws PIMException {
		if (pimListType != PIM.CONTACT_LIST && pimListType != PIM.TODO_LIST && pimListType != PIM.EVENT_LIST)
			throw new IllegalArgumentException("Invalid list type.");
		String[] listnames = listPIMListsN(pimListType);
		if (listnames.length == 0)
			throw new PIMException("No list available");					
		return openPIMList(pimListType, mode, listnames[0]);
	}

	/**
	 * @see javax.microedition.pim.PIM#openPIMList(int, int, String)
	 */
	public PIMList openPIMList(int pimListType, int mode, String name) throws PIMException {
		if (name == null)
			throw new NullPointerException();
		if (mode != PIM.READ_ONLY && mode != PIM.WRITE_ONLY && mode != PIM.READ_WRITE)
			throw new IllegalArgumentException("Invalid mode.");
		if (pimListType != PIM.CONTACT_LIST && pimListType != PIM.EVENT_LIST && pimListType != PIM.TODO_LIST)
			throw new IllegalArgumentException("Invalid list type.");
		
		//check the mode with the property
		String propertyMode = System.getProperty(PROPERTY_ACCESS_MODE);
		if (propertyMode != null) {
			if ((propertyMode.equals(STRING_READ_ONLY) && mode != READ_ONLY) ||
				(propertyMode.equals(STRING_WRITE_ONLY) && mode != WRITE_ONLY))
				throw new SecurityException("The application is not given permission that matches the requested mode");
		}
		
		PIMListImpl list = null;
		
		// check if the list is already open
		String key = pimListType + name;
		list = (PIMListImpl)lists.get(key);
		if (list == null) {
			// check string name
			if (isValidListName(pimListType, name)) {
				int handle = openPIMListN(pimListType, mode, name);
				switch (pimListType) {
					case PIM.CONTACT_LIST:
						list = new ContactListImpl(name, mode, handle);
						break;
					case PIM.EVENT_LIST:
						list = new EventListImpl(name, mode, handle);
						break;
					case PIM.TODO_LIST:
						list = new ToDoListImpl(name, mode, handle);
						break;
				}
				lists.put(key, list);				
			}		
			else
				throw new PIMException("List name not valid: " + name);
		}
		else {
			// if list already open but not in the same mode
			if (list.mode != mode)
				throw new PIMException("List already open in a different mode", PIMException.LIST_NOT_ACCESSIBLE);
		}
		return list;
	}

	/**
	 * Answers true if a PIMlist with this name exists.
	 * @param listType. PIM.CONTACT_LIST, PIM.EVENT_LIST or PIM.TODO_LIST.
	 * @param name
	 * @return boolean
	 */
	private boolean isValidListName(int listType, String name) {
		return PIMUtil.contains(listPIMListsN(listType), name);
	}

	/**
	 * @see javax.microedition.pim.PIM#listPIMLists(int)
	 */
	public String[] listPIMLists(int pimListType) {
		if (pimListType != PIM.CONTACT_LIST && pimListType != PIM.TODO_LIST && pimListType != PIM.EVENT_LIST)
			throw new IllegalArgumentException("Invalid list type.");
		//check the mode with the property
		String propertyMode = System.getProperty(PROPERTY_ACCESS_MODE);
		if (propertyMode != null) {
			if (propertyMode.equals(STRING_WRITE_ONLY))
				throw new SecurityException("The application is not given permission that matches the requested mode");
		}
		return listPIMListsN(pimListType);
	}

	/**
	 * @see javax.microedition.pim.PIM#fromSerialFormat(InputStream, String)
	 */
	public PIMItem[] fromSerialFormat(InputStream is, String enc) throws PIMException, UnsupportedEncodingException {
		if (is == null)
			throw new NullPointerException();
		if (enc == null)
			enc = PIMUtil.UTF8_ENCODING; // default encoding	
		if (!enc.equals(PIMUtil.UTF8_ENCODING))
			throw new UnsupportedEncodingException();
		
		UTF8InputStreamReader in = new UTF8InputStreamReader(is);
		VParser parser = new VParser(in);
		Vector entries = null;
		try {
			entries = parser.parse(null, -1);
		}
		catch (IOException e) {
			throw new PIMException("Error parsing the stream: " + e.getMessage());
		}
		if (entries == null)
			throw new PIMException("Invalid format");

		PIMItem[] items = new PIMItem[entries.size()];
		
		for (int i = 0; i < entries.size(); i++) {
			Vector entry = (Vector) entries.elementAt(i);
			if (entry.size() == 0)
				throw new PIMException("The stream does not contain enougth information");
			int type = ((Property)entry.elementAt(0)).getType();
			PIMItemImpl item;
			switch (type) {
				case Property.CONTACT:
					item = new ContactImpl(null, -1);
					populateItem(PIM.CONTACT_LIST, item, entry);
					break;
				case Property.EVENT:
					item = new EventImpl(null, -1);
					populateItem(PIM.EVENT_LIST, item, entry);
					break;
				case Property.TODO:
					item = new ToDoImpl(null, -1);
					populateItem(PIM.TODO_LIST, item, entry);
					break;
				default :
					throw new PIMException("Invalid format");
			}	
			items[i] = item;
		}
		return items;
	}

	/**
	 * Populates the given item with data contained into <code>properties</code>.
	 * @param PIMListType
	 * @param item
	 * @param properties
	 * @throws PIMException
	 * @throws UnsupportedEncodingException
	 */
	private void populateItem(int PIMListType, PIMItemImpl item, Vector properties) throws PIMException, UnsupportedEncodingException {
		long alarmDate = -1;
		for (int i = 0; i < properties.size(); i++) {
			Property property = (Property) properties.elementAt(i);
			String name = property.getName();
			String[] values = property.getValues();
			String[] params = property.getParameters();
			
			if (values.length == 0)
				throw new PIMException("Incorrect data.");
			
			// check the version
			if (checkIsValidVersion(property, PIMListType))
				continue;
			
			// categories
			if (PIMUtil.equalsIgnoreCase(name, VCard.CATEGORIES_TAG) ||
				PIMUtil.equalsIgnoreCase(name, VCalendar.CATEGORIES_TAG))
			{
				for (int j = 0; j < values.length; j++)
					item.addToCategory(values[j]);
				continue;
			}
			
			// check for repeat rule
			if (PIMListType == PIM.EVENT_LIST)
				populateRepeatRule(property, (Event)item);

			// fill the item
			int fieldID = PIMUtil.getFieldID(PIMListType, name);
			int attribID = 0;
			for (int j = 0; j < params.length; j++) {
				int result = PIMUtil.getAttributeID(PIMListType, params[j]);
				if (result != -1)
					attribID |= result;
			}
			
			// check if extended field			
			if (fieldID == -1) {
				if (name.startsWith(VCard.EXTENDED_PREFIX_TAG))
					// store extended property.
					item.addVExtendedField(property);
			}
			else {
			
				// check if field is PHOTO_URL
				if (PIMListType == PIM.CONTACT_LIST && fieldID == Contact.PHOTO) {
					if (PIMUtil.containsIgnoreCase(params, VCard.ATTR_VALUE_TAG + '=' +VCard.URL_TAG, true))
						fieldID = Contact.PHOTO_URL;
				}
				
				// check if field is PUBLIC_KEY_STRING
				else if (PIMListType == PIM.CONTACT_LIST && fieldID == Contact.PUBLIC_KEY) {
					// if not encoded in BASE64 then string
					if (property.getEncoding() != Property.BASE64)
						fieldID = Contact.PUBLIC_KEY_STRING;
				}
				
				if (PIMListType == PIM.EVENT_LIST && fieldID == Event.ALARM) {
					// start date needed
					alarmDate = DateHelper.fromVDate(values[0]);
					continue;
				}
				
				
				if (PIMListType == PIM.EVENT_LIST && fieldID == Event.CLASS ||
					      PIMListType == PIM.CONTACT_LIST && fieldID == Contact.CLASS ||
					      PIMListType == PIM.TODO_LIST && fieldID == ToDo.CLASS) 
				{
					String stringValue = values[0];
					int intValues = -1;
					if (PIMUtil.equalsIgnoreCase(stringValue, VCalendar.CLASS_CONFIDENTIAL_TAG))
						intValues = Contact.CLASS_CONFIDENTIAL;
					else if (PIMUtil.equalsIgnoreCase(stringValue, VCalendar.CLASS_PRIVATE_TAG))
						intValues = Contact.CLASS_PRIVATE;
					else if (PIMUtil.equalsIgnoreCase(stringValue, VCalendar.CLASS_PUBLIC_TAG))
						intValues = Contact.CLASS_PUBLIC;
					else
						throw new PIMException("Incorrect data.");
						
					if (PIMListType == PIM.EVENT_LIST)
						item.addInt(Event.CLASS, Event.ATTR_NONE, intValues);
					else if (PIMListType == PIM.CONTACT_LIST)
						item.addInt(Contact.CLASS, Contact.ATTR_NONE, intValues);
					else
						item.addInt(ToDo.CLASS, ToDo.ATTR_NONE, intValues);
					
					continue;
				}
				
				// add the values
				int dataType = PIMListImpl.getFieldDataTypeS(PIMListType, fieldID);
				if (dataType == PIMItemImpl.STRING_ARRAY) {
					int length = PIMListImpl.getStringArraySizeS(PIMListType, fieldID);
					if (length != -1) {
						String[] array = new String[length];
						System.arraycopy(values, 0, array, 0, values.length);
						item.addStringArray(fieldID, PIMItemImpl.ATTR_NONE, array);
					}
				}
				else {
					for (int j = 0; j < values.length; j++) {
						String value = values[j];
						
						switch (dataType) {
							case PIMItem.STRING:
								item.addString(fieldID, attribID, value);
								break;
							case PIMItem.INT:
								try {
									int intValue = Integer.parseInt(value);
									item.addInt(fieldID, attribID, intValue);
								}
								catch (NumberFormatException e) {
									throw new PIMException("Incorrect data.");
								}
								break;
							case PIMItem.BOOLEAN:
								if (PIMUtil.equalsIgnoreCase(value, "true"))
									item.addBoolean(fieldID,attribID, true);
								else if (PIMUtil.equalsIgnoreCase(value, "false"))
									item.addBoolean(fieldID, attribID, false);
								break;
							case PIMItem.BINARY:
								byte[] byteValue = value.getBytes();
								item.addBinary(fieldID, attribID, byteValue, 0, byteValue.length);
								break;
							case PIMItem.DATE:
								long date = DateHelper.fromVDate(value);
								if (hasDateToBeAdded(PIMListType, item, fieldID, date)) {
									item.addDate(fieldID, attribID, date);
									if (PIMListType == PIM.TODO_LIST && fieldID == ToDo.COMPLETION_DATE)
										// presence of COMPLETION_DATE indicates completed is true
										item.addBoolean(ToDo.COMPLETED, ToDo.ATTR_NONE, true);
								}
								break;
						}
					}
				}
			}
		}
		// set the alarm using the start date
		if (alarmDate != -1) {
			try {
				long startDate = item.getDate(Event.START, 0);
				item.addInt(Event.ALARM, Event.ATTR_NONE,(int) (startDate - alarmDate)/1000);
			}
			catch (Exception e) {
				// no start date
				throw new PIMException("Incorrect data.");
			}
		}
	}
	
	/**
	 * Answers if the date has to be be added.
	 * When the event has no duration, add only 
	 * the start or end date (first found on stream).
	 * @param listType
	 * @param item
	 * @param field
	 * @param value
	 * @return boolean
	 */
	private boolean hasDateToBeAdded(int listType, PIMItem item, int field, long value) {
		if (!(listType == PIM.EVENT_LIST && (field == Event.START || field == Event.END)))
			return true;
		
		if (field == Event.START) {
			if (item.countValues(Event.END) != 0) {
				long end = item.getDate(Event.END, 0);
				if (end == value)
					return false;
			}	
		}
		else {
			if (item.countValues(Event.START) != 0) {
				long start = item.getDate(Event.START, 0);
				if (start == value)
					return false;
			}
		}
		return true;		
	}
	
	/**
	 * Checks if the input stream contains a supported version of VCard or VCalendar.
	 * @param property
	 * @param PIMListType
	 * @return boolean
	 * @throws PIMException
	 * @throws UnsupportedEncodingException
	 */
	private boolean checkIsValidVersion(Property property, int PIMListType) throws PIMException, UnsupportedEncodingException {
		String name = property.getName();
		String[] values = property.getValues();
		if (PIMUtil.equalsIgnoreCase(name, VCard.VERSION_TAG)) {
			if (values.length != 1)
				throw new PIMException("Invalid format");
			StringBuffer format = new StringBuffer(13);
			if (PIMListType == PIM.CONTACT_LIST)
				format.append(VCard.VCARD_TAG);
			else
				format.append(VCalendar.VCALENDAR_TAG);
			format.append("/");
			format.append(values[0]);
			
			if (!(PIMUtil.containsIgnoreCase(supportedSerialFormats(PIMListType), format.toString(), true)))
				throw new PIMException("Unsupported serial format");
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Populates the repeat rule if necessary.
	 * @param property
	 * @param item
	 * @throws PIMException
	 */
	private void populateRepeatRule(Property property, Event item) throws PIMException {
		String name = property.getName();
		String[] values = property.getValues();
		if (PIMUtil.equalsIgnoreCase(name, VCalendar.REPEAT_RULE_TAG)) {
			RepeatRule repeat = item.getRepeat();
			if (repeat == null) {
				repeat = new RepeatRule();
				item.setRepeat(repeat);
			}
			RecurrenceRuleParser.parse(values[0], repeat);
		}
		// exception dates
		else if (PIMUtil.equalsIgnoreCase(name, VCalendar.EXCEPTION_DATE_TAG)) {
			RepeatRule repeat = item.getRepeat();
			if (repeat == null) {
				repeat = new RepeatRule();
				item.setRepeat(repeat);
			}
			for (int j = 0; j < values.length; j++) {
				repeat.addExceptDate(DateHelper.fromVDate(values[j]));
			}
		}
		else if (PIMUtil.equalsIgnoreCase(name, VCalendar.COUNT_TAG)) {
			RepeatRule repeat = item.getRepeat();
			if (repeat == null) {
				repeat = new RepeatRule();
				item.setRepeat(repeat);
			}
			try {
				int count = Integer.parseInt(values[0]);
				repeat.setInt(RepeatRule.COUNT, count);
			}
			catch (NumberFormatException e) {
				throw new PIMException("Incorrect data.");
			}
		}
	}

	/**
	 * @see javax.microedition.pim.PIM#toSerialFormat(PIMItem, OutputStream, String, String)
	 */
	public void toSerialFormat(PIMItem item, OutputStream os, String enc, String dataFormat)
		throws PIMException, UnsupportedEncodingException 
	{
		if (enc == null)
			enc = PIMUtil.UTF8_ENCODING; // defaukt encoding
			
		if (!enc.equals(PIMUtil.UTF8_ENCODING))
			throw new UnsupportedEncodingException();			
			
		if (item == null || os == null || dataFormat == null)
			throw new NullPointerException();
		
		int listType = -1;
		// set list type
		if(item instanceof Contact)
			listType = PIM.CONTACT_LIST;
		else if(item instanceof Event)
			listType = PIM.EVENT_LIST;
		else if(item instanceof ToDo)
			listType = PIM.TODO_LIST;
			
		if (listType == -1)
			// cannot happen
			throw new PIMException("Item is not a Contact, ToDo or Event.", PIMException.GENERAL_ERROR);
		
		// check if the format is supported
		String[] supportedFormats = supportedSerialFormats(listType);
		if (!PIMUtil.containsIgnoreCase(supportedFormats, dataFormat, true))
			throw new IllegalArgumentException("Unsupported data format.");
		// Start exporting
		try {
			UTF8OutputStreamWriter osUTF8 = new UTF8OutputStreamWriter(os);
			StringBuffer buffer = new StringBuffer(50);
	
			// write start tag
			if (listType == PIM.CONTACT_LIST) {
				buffer.append(VCard.VCARD_BEGIN_TAG);
				buffer.append(PIMUtil.CRLF);
				buffer.append(VCard.VERSION_TAG);
				buffer.append(":2.1");
				buffer.append(PIMUtil.CRLF);
			}
			else {
				buffer.append(VCalendar.VCALENDAR_BEGIN_TAG);
				buffer.append(PIMUtil.CRLF);
				buffer.append(VCalendar.VERSION_TAG);
				buffer.append(":1.0");
				buffer.append(PIMUtil.CRLF);
				if (listType == PIM.TODO_LIST)
					buffer.append(VCalendar.VTODO_BEGIN_TAG);
				else
					buffer.append(VCalendar.VEVENT_BEGIN_TAG);
				buffer.append(PIMUtil.CRLF);
			}
			osUTF8.write(buffer.toString(), 0, buffer.length());
		
			// write categories
			String[] categories = item.getCategories();
			int length = categories.length;
			if (length > 0) {
				buffer = new StringBuffer(length * 9 + 15);
				if (listType == PIM.CONTACT_LIST)
					buffer.append(VCard.CATEGORIES_TAG);
				else
					buffer.append(VCalendar.CATEGORIES_TAG);
				buffer.append(":");
				for (int i = 0; i < length; i++) {
					buffer.append(categories[i]);
					if (i < (length - 1))
						buffer.append(";");
				}
				buffer.append(PIMUtil.CRLF);
				osUTF8.write(buffer.toString(), 0, buffer.length());
			}
			// write the data stored to the stream.
			writeDataToStream(item, listType, osUTF8);
			// if an event, write the repeat rule
			writeRepeat(item, listType, osUTF8);

			// write ending tag
			if (listType == PIM.CONTACT_LIST) {
				buffer = new StringBuffer(10);
				buffer.append(VCard.VCARD_END_TAG);
			}
			else {
				buffer = new StringBuffer(25);
				if (listType == PIM.TODO_LIST) {
					buffer.append(VCalendar.VTODO_END_TAG);
				}
				else
					buffer.append(VCalendar.VEVENT_END_TAG);
				buffer.append(PIMUtil.CRLF);
				buffer.append(VCalendar.VCALENDAR_END_TAG);
			}
			osUTF8.write(buffer.toString(), 0, buffer.length());
		}
		catch (IOException e) {
			// ???
			throw new PIMException("Error writing to the stream");
		}
		finally {
			eventDatesExported = false;
		}
	}

	/**
	 * Writes the data stored in the item to the stream.
	 * @param item
	 * @param listType. PIM.CONTACT_LIST, PIM.EVENT_LIST or PIM.TODO_LIST
	 * @param osUTF8
	 * @throws IOException
	 */
	private void writeDataToStream(PIMItem item, int listType, UTF8OutputStreamWriter osUTF8) throws IOException {
		// write data
		int[] fields = item.getFields();
		for (int i = 0; i < fields.length; i++) {
			int field = fields[i];
			int datatype =  PIMListImpl.getFieldDataTypeS(listType, field);
			if (datatype == -1)
				continue;
			String tag = PIMUtil.getVTag(listType, item, field);
			for (int j = 0; j < item.countValues(field); j++) {
				StringBuffer buffer = new StringBuffer(80); // size ???
				buffer.append(tag);
				if (listType == PIM.TODO_LIST && field == ToDo.COMPLETED) {
					// check if we don't have a completion date
					if (!PIMUtil.contains(fields, ToDo.COMPLETION_DATE)) {
						boolean b = item.getBoolean(field, j);
						if (b) {
							buffer.append(':');
							Date today = new Date();
							today.setTime(today.getTime());
							buffer.append(DateHelper.toVDate(today, false));
							buffer.append(PIMUtil.CRLF);
						}
					}
					continue;
				}
				int attr = item.getAttributes(field, j);
				if (attr != PIMItem.ATTR_NONE) {
					buffer.append(';');
					boolean wroteAttribute = false;
					for (int k = 0; k < 10; k++) {
						int attrvalue = 1 << k;	
						if ((attr & attrvalue) != 0) {
							if (wroteAttribute)
								buffer.append(';');
							buffer.append(PIMUtil.getVTag(PIM.CONTACT_LIST, item, attrvalue));
							wroteAttribute = true;
						}
					}
				}
				
				// BASE64 encoding tag
				if (datatype == Contact.BINARY) {
					if (attr != PIMItem.ATTR_NONE)
						buffer.append(',');
					else
						buffer.append(';');
					buffer.append(VCard.ATTR_ENCODING_TAG);
					buffer.append('=');
					buffer.append(PIMUtil.BASE64_ENCODING);
				}
				buffer.append(':');
				
				if (datatype == Contact.STRING) {
					String s = item.getString(field, j);
					if (EncodingHelper.hasToBeQuotedPrintableEncoded(s)) {
						int position = buffer.length() - 1;
						buffer.insert(position, PIMUtil.QUOTED_PRINTABLE_ENCODING);
						buffer.insert(position, '=');
						buffer.insert(position, VCard.ATTR_ENCODING_TAG);
						buffer.insert(position, ';');
						s = EncodingHelper.encodeQuotedPrintable(s);
					}
					buffer.append(s);
				}
				else if (datatype == Contact.STRING_ARRAY) {
					int position = buffer.length() - 1;
					boolean encoded = false;
					String[] array = item.getStringArray(field, j);
					for (int k = 0; k < array.length; k++) {
						if (array[k] != null) {
							if (EncodingHelper.hasToBeQuotedPrintableEncoded(array[k])) {
								encoded = true;
								array[k] = EncodingHelper.encodeQuotedPrintable(array[k]);	
							}
							buffer.append(array[k]);
						}
						if (k != (array.length-1))
							buffer.append(';');
					}
					if (encoded) {
						buffer.insert(position, PIMUtil.QUOTED_PRINTABLE_ENCODING);
						buffer.insert(position, '=');
						buffer.insert(position, VCard.ATTR_ENCODING_TAG);
						buffer.insert(position, ';');
					}
				}
				else if (datatype == Contact.INT) {
					int x = item.getInt(field, j);
					if (field == Event.ALARM && listType == PIM.EVENT_LIST) {
						long al = x*1000;
						long start = item.getDate(Event.START, j);
						String ddate = DateHelper.toVDate(new Date(start-al+DateHelper.getTimeOffset(start-al)), true);
						buffer.append(ddate);
					}
					else if (isClassField(listType, field))
						buffer.append(PIMUtil.getCLASSValueTag(x));
					else
						buffer.append(x);
				}
				else if (datatype == Contact.DATE) {
					if (listType == PIM.EVENT_LIST && (field == Event.START || field == Event.END)) {
						writeEventDates(item, buffer, field, osUTF8);
						continue;
					}
					else {
						long d = item.getDate(field, j);
						String sdate = DateHelper.toVDate(new Date(d - DateHelper.getTimeOffset(d)), true);
						buffer.append(sdate);
					}
				}
				else if (datatype == Contact.BOOLEAN) {
					// the only field of type boolean is ToDo.COMPLETED
					// this field's value has been taken care of previously
				}
				else if (datatype == Contact.BINARY) {
					byte[] bin = item.getBinary(field, j);
					String sbin = new String(EncodingHelper.encodeBASE64(bin));
					buffer.append(sbin);
				}
				buffer.append(PIMUtil.CRLF);
				osUTF8.write(buffer.toString(), 0, buffer.length());
			}
		}
	}

	/**
	 * Writes to the stream the start and end dates for the given event.
	 * @param item
	 * @param buffer
	 * @param field
	 * @param os
	 * @throws IOException
	 */
	private void writeEventDates(PIMItem item, StringBuffer buffer, int field, UTF8OutputStreamWriter os) throws IOException {
		// already done.
		if (eventDatesExported)
			return;
		
		long start = -1;
		if (item.countValues(Event.START) != 0)
			start = item.getDate(Event.START, 0);
		long end = -1;
		if (item.countValues(Event.END) != 0)
			end = item.getDate(Event.END, 0);	
		
		if (start == end) { // all day event
			end += 24*60*60*1000; // 24 hours
		}
		if (start == -1)
			start = end;
		if (end == -1)
			end = start;
		
		// write on the stream
		if (field == Event.START) {
			buffer.append(DateHelper.toVDate(new Date(start - DateHelper.getTimeOffset(start)), true));
			buffer.append(PIMUtil.CRLF);
			buffer.append(VCalendar.END_DATE_TAG);
			buffer.append(':');
			buffer.append(DateHelper.toVDate(new Date(end - DateHelper.getTimeOffset(end)), true));	
		}
		
		else {
			buffer.append(DateHelper.toVDate(new Date(end - DateHelper.getTimeOffset(end)), true));
			buffer.insert(0, PIMUtil.CRLF);
			buffer.insert(0, DateHelper.toVDate(new Date(start - DateHelper.getTimeOffset(start)), true));
			buffer.insert(0, ':');
			buffer.append(VCalendar.START_TAG);
		}
		buffer.append(PIMUtil.CRLF);
		os.write(buffer.toString(), 0, buffer.length());
		eventDatesExported = true;		
	}


	/**
	 * Writes the repeat rule into the given stream.
	 * @param item
	 * @param listtype
	 * @param osUTF8
	 * @throws PIMException
	 * @throws IOException
	 */
	private void writeRepeat(PIMItem item, int listtype, UTF8OutputStreamWriter osUTF8) throws PIMException, IOException {
		if (listtype == PIM.EVENT_LIST) {
			RepeatRule repeat = ((Event)item).getRepeat();
			if (repeat != null) {
				// repeat tag
				StringBuffer buffer = new StringBuffer(15); // 15 ??
				buffer.append(VCalendar.REPEAT_RULE_TAG);
				buffer.append(":");
				try {
					int[] repeatFields = repeat.getFields();
					int interval = repeat.getInt(repeat.INTERVAL);
					switch(repeat.getInt(repeat.FREQUENCY)) {
						case RepeatRule.DAILY:
							buffer.append(VCalendar.DAILY_TAG);
							buffer.append(interval);
							break;
						case RepeatRule.WEEKLY:
							buffer.append(VCalendar.WEEKLY_TAG);
							buffer.append(interval);
							int dayInWeek = repeat.getInt(repeat.DAY_IN_WEEK);
							for (int i = 0; i < 7; i++) {
								if ((dayInWeek & (1 << (10+i))) != 0) {
									buffer.append(' ');
									buffer.append(VCalendar.WEEK_DAY_TAGS[6-i]);
								}
							}
							break;
						case RepeatRule.MONTHLY:
							if (PIMUtil.contains(repeatFields, repeat.DAY_IN_MONTH)) {
								buffer.append(VCalendar.MONTHLY_BY_DAY_TAG);
								buffer.append(interval);
								int mday = repeat.getInt(repeat.DAY_IN_MONTH);
								buffer.append(" ");
								buffer.append(mday);
							}
							else {
								buffer.append(VCalendar.MONTHLY_BY_POSITION_TAG);
								buffer.append(interval);
								int weekInMonth = repeat.getInt(repeat.WEEK_IN_MONTH);
								for (int i = 0;i < 10;i++) {
									if ((weekInMonth & (1 << i)) != 0) {
										buffer.append(' ');
										if (i < 5) {
											buffer.append(i + 1);
											buffer.append('+');
										}
										else {
											buffer.append(i - 1);
											buffer.append('-');
										}
									}
								}
								
								dayInWeek = repeat.getInt(repeat.DAY_IN_WEEK);
								for (int i = 0; i < 7; i++) {
									if ((dayInWeek & (1 << (10+i))) != 0) {
										buffer.append(' ');
										buffer.append(VCalendar.WEEK_DAY_TAGS[6-i]);
									
									}
								}
							}
							break;
						case RepeatRule.YEARLY:
							if (PIMUtil.contains(repeatFields, repeat.MONTH_IN_YEAR)) {
								buffer.append(VCalendar.YEARLY_BY_MONTH_TAG);
								buffer.append(interval);
								int monthInYear = repeat.getInt(repeat.MONTH_IN_YEAR);
								for (int i = 0; i < 12; i++) {
									if ((monthInYear & (1 << (17+i))) != 0) {
										buffer.append(' ');
										buffer.append(i + 1);
									}
								}
							}
							else {
								buffer.append(VCalendar.YEARLY_BY_DAY_TAG);
								buffer.append(interval);
								int dayInYear = repeat.getInt(repeat.DAY_IN_YEAR);
								buffer.append(' ');
								buffer.append(dayInYear);							
							}
							break;	
					}
					
					if (PIMUtil.contains(repeatFields, repeat.COUNT)) {
						int count = repeat.getInt(repeat.COUNT);
						buffer.append(" #");
						buffer.append(count);
					}
					else if (PIMUtil.contains(repeatFields, repeat.END)) {
						long end = repeat.getDate(repeat.END);
						String date = DateHelper.toVDate(new Date(end+DateHelper.getTimeOffset(end)), true);
						buffer.append(' ');
						buffer.append(date);
					}
					buffer.append(PIMUtil.CRLF);
					osUTF8.write(buffer.toString(), 0, buffer.length());
					// Exception dates
					Enumeration exceptionDates = repeat.getExceptDates();
					buffer = new StringBuffer(); // size???
					boolean hasExecptionDate = false;
					while(exceptionDates.hasMoreElements()) {
						hasExecptionDate = true;
						Date dd = (Date) exceptionDates.nextElement();
						dd.setTime(dd.getTime() + DateHelper.getTimeOffset(dd.getTime()));
						buffer.append(DateHelper.toVDate(dd, false));
						if (exceptionDates.hasMoreElements())
							buffer.append(',');
					}
					if (hasExecptionDate) {
						buffer.insert(0, ':');
						buffer.insert(0, VCalendar.EXCEPTION_DATE_TAG);
						buffer.append(PIMUtil.CRLF);
					}
					osUTF8.write(buffer.toString(), 0, buffer.length());
					
				}
				catch (FieldEmptyException fie) {
					throw new PIMException("Invalid RepeatRule.", PIMException.GENERAL_ERROR);
				}
			}
		}
	}
	
	/**
	 * Answers if the given field is {@link Contact#CLASS}, {@link Event#CLASS}
	 * or {@link ToDo#CLASS}.
	 * @param listType
	 * @param field
	 * @return boolean
	 */
	private boolean isClassField(int listType, int field) {
		return ((field == Contact.CLASS && listType == PIM.CONTACT_LIST) ||
				 (field == ToDo.CLASS && listType == PIM.TODO_LIST) ||
				 (field == Event.CLASS && listType == PIM.EVENT_LIST));
	}


	/**
	 * @see javax.microedition.pim.PIM#supportedSerialFormats(int)
	 */
	public String[] supportedSerialFormats(int pimListType) {
		if (pimListType != PIM.CONTACT_LIST &&
			pimListType != PIM.TODO_LIST &&
			pimListType != PIM.EVENT_LIST )
			
			throw new IllegalArgumentException();
		
		String[] result = null;
		if (pimListType == PIM.CONTACT_LIST)
			result = new String[] {"VCARD/2.1"};
		else if (pimListType == PIM.TODO_LIST || pimListType == PIM.EVENT_LIST)
			result = new String[] {"VCALENDAR/1.0"};
		return result;
	}

	/**
	 * Removes the given PIMList from the list of open PIMLists.
	 * @param string. A PIMList identifier.
	 */
	static void removePIMList(String key) {
		lists.remove(key);
	}
	
	/**
	 * Answers the list of open PIMLists.
	 * The Hashtable will contain the lists not closed
	 * when the Java process is exited.
	 * The vendor may have to manage the resource cleaning.
	 * @return Hashtable
	 */
	public static Hashtable getOpenPIMLists() {
		return lists;
	}
}