package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import javax.microedition.pim.Contact;
import javax.microedition.pim.Event;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMItem;
import javax.microedition.pim.RepeatRule;
import javax.microedition.pim.ToDo;


public class PIMUtil {
	
	/**
	 * CRLF characters.
	 */
	public static final String CRLF = "\r\n";
	
	/**
	 * Formal String declaration of BASE64 encoding.
	 */
	public static final String BASE64_ENCODING = "BASE64";
	
	/**
	 * Formal String declaration of Quoted-Printable encoding.
	 */
	public static final String QUOTED_PRINTABLE_ENCODING = "QUOTED-PRINTABLE";
	
	/**
	 * Formal String declaration of UTF8 encoding.
	 */
	public static final String UTF8_ENCODING = "UTF-8";

	/**
	 * Internal index of the repeat field COUNT.
	 */
	public static final int COUNT = 0;
	
	/**
	 * Internal index of the repeat field DAY_IN_MONTH.
	 */
	public static final int DAY_IN_MONTH = 1;
	
	/**
	 * Internal index of the repeat field DAY_IN_WEEK.
	 */
	public static final int DAY_IN_WEEK = 2;
	
	/**
	 * Internal index of the repeat field DAY_IN_YEAR.
	 */
	public static final int DAY_IN_YEAR = 3;
	
	/**
	 * Internal index of the repeat field END.
	 */
	public static final int END = 4;
	
	/**
	 * Internal index of the repeat field FREQUENCY.
	 */
	public static final int FREQUENCY = 5;
	
	/**
	 * Internal index of the repeat field INTERVAL.
	 */
	public static final int INTERVAL = 6;
	
	/**
	 * Internal index of the repeat field MONTH_IN_YEAR.
	 */
	public static final int MONTH_IN_YEAR = 7;
	
	/**
	 * Internal index of the repeat field WEEK_IN_MONTH.
	 */
	public static final int WEEK_IN_MONTH = 8;
	
	/**
	 * Answers if the given strings are equal ignoring the case of the characters.
	 * @param string1
	 * @param string2
	 * @return boolean
	 */
	public static boolean equalsIgnoreCase(String string1, String string2) {
		if (string1.length() != string2.length()) return false;
		string1 = string1.toLowerCase();
		string2 = string2.toLowerCase();
		return string1.equals(string2);
	}
	
	/**
	 * Answers if <code>string1</code> starts with <code>string2</code> 
	 * ignoring the case of the characters.
	 * @param string1
	 * @param string2
	 * @return boolean
	 */
	public static boolean startsWithIgnoreCase(String string1, String string2) {
		if (string1.length() < string2.length()) return false;
		string1 = string1.toLowerCase();
		string2 = string2.toLowerCase();
		return string1.startsWith(string2);
	}
	
	/**
	 * Answers if <code>string1</code> ends with <code>string2</code> 
	 * ignoring the case of the characters.
	 * @param string1
	 * @param string2
	 * @return boolean
	 */
	public static boolean endsWithIgnoreCase(String string1, String string2) {
		if (string1.length() < string2.length()) return false;
		string1 = string1.toLowerCase();
		string2 = string2.toLowerCase();
		return string1.endsWith(string2);
	}
	
	/**
	 * Answers if <code>value</code> is contained into <code>array</code>.
	 * The case is ignored.
	 * @param array
	 * @param value
	 * @param wholeWord. Indicates if the method checks for the entire word. 
	 * @return boolean
	 */
	public static boolean containsIgnoreCase(String[] array, String value, boolean wholeWord) {
		int length = value.length();
		for (int i = 0; i < array.length; i++) {
			if (wholeWord) {
				if (equalsIgnoreCase(array[i], value))
					return true;
			}
			else {
				if (length >= array[i].length()) {
					String string = array[i].toLowerCase();
					if (value.toLowerCase().startsWith(string))
						return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Answers if <code>value</code> is contained into <code>array</code>.
	 * @param array
	 * @param value
	 * @return boolean
	 */
	public static boolean contains(String[] array, String value) {
		for (int i = 0; i < array.length; i++) {
			if (value.equals(array[i]))
				return true;
		}
		return false;
	}
	
	/**
	 * Answers if <code>array</code> contains <code>value</code>.
	 * @param array An int array.
	 * @param value
	 * @return boolean
	 */
	public static boolean contains(int[] array, int value) {
		for (int i = 0; i < array.length; i++) {
			if (value == array[i])
				return true;
		}
		return false;
	}
	
	/**
	 * Indicates if the given char is an aplha character.
	 * @param oneChar
	 * @return boolean
	 */
	public static boolean isAlpha(char oneChar) {
		return ((oneChar >= 65 && oneChar <= 90) || (oneChar >= 97 && oneChar <= 122));
	}
	
	/**
	 * Makes a copy of the given array.
	 * @param array
	 * @return int[]
	 */
	public static int[] arraycopy(int[] array) {
		int[] copy = new int[array.length]; 
		System.arraycopy(array, 0, copy, 0, array.length);
		return copy;
	}
	
	/**
	 * Answers the property name (VTag) of the given field.
	 * @param field
	 * @return String
	 */
	public static String getVTag(int listType, PIMItem item, int field) {
		String result = null;
		PIMListImpl list = ((PIMItemImpl)item).owner;
		if (field >= PIMItem.EXTENDED_FIELD_MIN_VALUE) {	
			if (list != null) {
				StringBuffer propertyName = new StringBuffer();
				propertyName.append(VCard.EXTENDED_PREFIX_TAG);
				propertyName.append(list.getFieldLabel(field));
				result = propertyName.toString();
			}
			else {
				return null;
			}
		}
		else {
			if (listType == PIM.CONTACT_LIST) {
				switch(field) {
					case Contact.ADDR: 
						result = VCard.ADDR_TAG;
						break;
					case Contact.BIRTHDAY: 
						result = VCard.BIRTHDAY_TAG;
						break;
					case Contact.EMAIL: 
						result = VCard.EMAIL_TAG;
						break;
					case Contact.FORMATTED_ADDR: 
						result = VCard.FORMATTED_ADDR_TAG;
						break;
					case Contact.FORMATTED_NAME: 
						result = VCard.FORMATTED_NAME_TAG;
						break;
					case Contact.NAME: 
						result = VCard.NAME_TAG;
						break;
					case Contact.NICKNAME: 
						result = VCard.NICKNAME_TAG;
						break;
					case Contact.NOTE: 
						result = VCard.NOTE_TAG;
						break;
					case Contact.ORG: 
						result = VCard.ORG_TAG;
						break;
					case Contact.PHOTO: 
						result = VCard.PHOTO_TAG;
						break;
					case Contact.PHOTO_URL: 
						result = VCard.PHOTO_TAG + ";" + VCard.ATTR_VALUE_TAG + "=" + VCard.URL_TAG;
						break;
					case Contact.PUBLIC_KEY: 
						result = VCard.PUBLIC_KEY_TAG;
						break;
					case Contact.PUBLIC_KEY_STRING: 
						result = VCard.PUBLIC_KEY_TAG;
						break;
					case Contact.REVISION: 
						result = VCard.REVISION_TAG;
						break;
					case Contact.TEL: 
						result = VCard.TEL_TAG;
						break;
					case Contact.TITLE: 
						result = VCard.TITLE_TAG;
						break;
					case Contact.UID: 
						result = VCard.UID_TAG;
						break;	
					case Contact.URL: 
						result = VCard.URL_TAG;
						break;		
					case Contact.ATTR_ASST: 
						result = VCard.ATTR_ASST_TAG;
						break;
					case Contact.ATTR_AUTO: 
						result = VCard.ATTR_AUTO_TAG;
						break;
					case Contact.ATTR_FAX: 
						result = VCard.ATTR_FAX_TAG;
						break;
					case Contact.ATTR_HOME: 
						result = VCard.ATTR_HOME_TAG;
						break;
					case Contact.ATTR_MOBILE: 
						result = VCard.ATTR_MOBILE_TAG;
						break;
					case Contact.ATTR_OTHER: 
						result = VCard.ATTR_OTHER_TAG;
						break;
					case Contact.ATTR_PAGER: 
						result = VCard.ATTR_PAGER_TAG;
						break;
					case Contact.ATTR_PREFERRED: 
						result = VCard.ATTR_PREF_TAG;
						break;
					case Contact.ATTR_SMS: 
						result = VCard.ATTR_SMS_TAG;
						break;		
					case Contact.ATTR_WORK: 
						result = VCard.ATTR_WORK_TAG;
						break;	
					case Contact.CLASS:
						result = VCard.CLASS_TAG;
						break;						
				}
			}
			else if (listType == PIM.TODO_LIST) {
				switch(field) {
					case ToDo.CLASS: 
						result = VCalendar.CLASS_TAG;
						break;
					case ToDo.COMPLETION_DATE: 
						result = VCalendar.COMPLETED_TAG;
						break;
					case ToDo.COMPLETED: 
						result = VCalendar.COMPLETED_TAG;
						break;
					case ToDo.DUE: 
						result = VCalendar.DUE_TAG;
						break;
					case ToDo.NOTE: 
						result = VCalendar.NOTE_TAG;
						break;
					case ToDo.PRIORITY: 
						result = VCalendar.PRIORITY_TAG;
						break;
					case ToDo.REVISION: 
						result = VCalendar.REVISION_TAG;
						break;
					case ToDo.SUMMARY: 
						result = VCalendar.SUMMARY_TAG;
						break;
					case ToDo.UID: 
						result = VCalendar.UID_TAG;
						break;
				}
			}
			else if (listType == PIM.EVENT_LIST) {
				switch(field) {
					case Event.ALARM: 
						result = VCalendar.ALARM_TAG;
						break;
					case Event.CLASS: 
						result = VCalendar.CLASS_TAG;
						break;
					case Event.END: 
						result = VCalendar.END_DATE_TAG;
						break;
					case Event.LOCATION: 
						result = VCalendar.LOCATION_TAG;
						break;
					case Event.NOTE: 
						result = VCalendar.NOTE_TAG;
						break;
					case Event.REVISION: 
						result = VCalendar.REVISION_TAG;
						break;
					case Event.START: 
						result = VCalendar.START_TAG;
						break;
					case Event.SUMMARY: 
						result = VCalendar.SUMMARY_TAG;
						break;
					case Event.UID: 
						result = VCalendar.UID_TAG;
						break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Maps the given property name to a PIM field.
	 * @param PIMListType The type of list.
	 * @param VTag The property name.
	 * @return int The field ID mapped or -1.
	 */
	public static int getFieldID(int PIMListType, String VTag) {	
		switch(PIMListType) {
			case PIM.CONTACT_LIST:
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.NAME_TAG))
					return Contact.NAME;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.TEL_TAG))
					return Contact.TEL;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.FORMATTED_NAME_TAG))
					return Contact.FORMATTED_NAME;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.NICKNAME_TAG))
					return Contact.NICKNAME;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.ADDR_TAG))
					return Contact.ADDR;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.BIRTHDAY_TAG))
					return Contact.BIRTHDAY;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.FORMATTED_ADDR_TAG))
					return Contact.FORMATTED_ADDR;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.EMAIL_TAG))
					return Contact.EMAIL;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.NOTE_TAG))
					return Contact.NOTE;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.ORG_TAG))
					return Contact.ORG;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.PHOTO_TAG))
					return Contact.PHOTO;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.PUBLIC_KEY_TAG))
					return Contact.PUBLIC_KEY;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.REVISION_TAG))
					return Contact.REVISION;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.TITLE_TAG))
					return Contact.TITLE;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.URL_TAG))
					return Contact.URL;
				if (PIMUtil.equalsIgnoreCase(VTag, VCard.CLASS_TAG))
					return Contact.CLASS;
				break;
			case PIM.EVENT_LIST:
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.CLASS_TAG))
					return Event.CLASS;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.START_TAG))
					return Event.START;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.END_DATE_TAG))
					return Event.END;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.LOCATION_TAG))
					return Event.LOCATION;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.NOTE_TAG))
					return Event.NOTE;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.SUMMARY_TAG))
					return Event.SUMMARY;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.UID_TAG))
					return Event.UID;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.REVISION_TAG))
					return Event.REVISION;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.ALARM_TAG))
					return Event.ALARM;
				
				// Repeat Rule
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.COUNT_TAG))
					return RepeatRule.COUNT;

				break;
			case PIM.TODO_LIST:
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.CLASS_TAG))
					return ToDo.CLASS;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.PRIORITY_TAG))
					return ToDo.PRIORITY;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.NOTE_TAG))
					return ToDo.NOTE;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.SUMMARY_TAG))
					return ToDo.SUMMARY;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.UID_TAG))
					return ToDo.UID;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.DUE_TAG))
					return ToDo.DUE;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.REVISION_TAG))
					return ToDo.REVISION;
				if (PIMUtil.equalsIgnoreCase(VTag, VCalendar.COMPLETED_TAG))
					return ToDo.COMPLETION_DATE;
				break;
		}
		return -1;
	}	
	
	/**
	 * Maps the given property parameter to a PIM attribute
	 * @param PIMListType The type of list
	 * @param VTag The property parameter.
	 * @return int The attribute ID or -1.
	 */
	public static int getAttributeID(int PIMListType, String VTag) {
				
		if (PIMListType != PIM.CONTACT_LIST)
			return -1;
		
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_ASST_TAG))
			return Contact.ATTR_ASST;
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_AUTO_TAG))
			return Contact.ATTR_AUTO;
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_FAX_TAG))
			return Contact.ATTR_FAX;
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_HOME_TAG))
			return Contact.ATTR_HOME;
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_MOBILE_TAG))
			return Contact.ATTR_MOBILE;
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_OTHER_TAG))
			return Contact.ATTR_OTHER;
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_PAGER_TAG))
			return Contact.ATTR_PAGER;
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_PREF_TAG))
			return Contact.ATTR_PREFERRED;
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_SMS_TAG))
			return Contact.ATTR_SMS;
		if (PIMUtil.equalsIgnoreCase(VTag, VCard.ATTR_WORK_TAG))
			return Contact.ATTR_WORK;	
		
		return -1;
	}
	
	/**
	 * Answers the string representing the CLASS value.
	 * @param value
	 * @return String
	 */
	public static String getCLASSValueTag(int value) {
		switch (value) {
			case Contact.CLASS_CONFIDENTIAL:
				return VCalendar.CLASS_CONFIDENTIAL_TAG;
			case Contact.CLASS_PRIVATE:
				return VCalendar.CLASS_PRIVATE_TAG;
			case Contact.CLASS_PUBLIC:
				return VCalendar.CLASS_PUBLIC_TAG;
			default :
				return null;
		}
	}

	/**
	 * Answers the index of the repeat field.
	 * @param fieldID
	 * @return int
	 */
	public static int getRepeatFieldIndexFromID(int fieldID) {
		switch(fieldID) {
			case RepeatRule.COUNT:
				return COUNT;
			case RepeatRule.DAY_IN_MONTH:
				return DAY_IN_MONTH;
			case RepeatRule.DAY_IN_WEEK:
				return DAY_IN_WEEK;
			case RepeatRule.DAY_IN_YEAR:
				return DAY_IN_YEAR;
			case RepeatRule.END:
				return END;
			case RepeatRule.FREQUENCY:
				return FREQUENCY;
			case RepeatRule.INTERVAL:
				return INTERVAL;
			case RepeatRule.MONTH_IN_YEAR:
				return MONTH_IN_YEAR;
			case RepeatRule.WEEK_IN_MONTH:
				return WEEK_IN_MONTH;	
		}
		return -1;
	}
	
	/**
	 * Answers the field ID of the repeat field using the given index.
	 * @param fieldIndex
	 * @return int
	 */
	public static int getRepeatFieldIDFromIndex(int fieldIndex) {
		switch(fieldIndex) {
			case COUNT:
				return RepeatRule.COUNT;
			case DAY_IN_MONTH:
				return RepeatRule.DAY_IN_MONTH;
			case DAY_IN_WEEK:
				return RepeatRule.DAY_IN_WEEK;
			case DAY_IN_YEAR:
				return RepeatRule.DAY_IN_YEAR;
			case END:
				return RepeatRule.END;
			case FREQUENCY:
				return RepeatRule.FREQUENCY;
			case INTERVAL:
				return RepeatRule.INTERVAL;
			case MONTH_IN_YEAR:
				return RepeatRule.MONTH_IN_YEAR;
			case WEEK_IN_MONTH:
				return RepeatRule.WEEK_IN_MONTH;
		}
		return -1;
	}
}