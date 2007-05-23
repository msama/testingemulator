package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.microedition.pim.Contact;
import javax.microedition.pim.Event;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import javax.microedition.pim.PIMItem;
import javax.microedition.pim.PIMList;
import javax.microedition.pim.ToDo;
import javax.microedition.pim.UnsupportedFieldException;


class PIMListImpl implements PIMList {

	/**
	 * Handle of the list.
	 */
	protected int handle;
		
	/**
	 * PIM list type : {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * or {@link PIM#TODO_LIST}.
	 */
	protected int listType;
	
	/**
	 * Access mode of the list: {@link PIM#READ_ONLY}, {@link PIM#WRITE_ONLY} 
	 * or {@link PIM#READ_WRITE}.
	 */
	protected int mode;
	
	/**
	 * The name of the list
	 */
	private String listName;
	
	/**
	 * Specifies if the list has been closed.
	 */
	private boolean listClosed = false;
	
	/**
	 *	The identifier of the list (<code>listType</code> + <code>listName</code>).
	 */
	private String key;
	
	/**
	 * Caches the supported array elements of STRING_ARRAY fields.
	 */
	protected Hashtable supportedArrayElements = new Hashtable();
	
	/**
	 * Holds the extended fields supported by the 
	 * underlying implementation.
	 */
	protected int[] extendedFields = null;
	
	/**
	 * Constructor for PIMListImpl.
	 */
	PIMListImpl(String name, int type, int mode, int handle) {
		super();
		listName = name;
		listType = type;
		key = listType + listName;
		this.handle = handle;
		this.mode = mode;
	}
	
	/**
	 * Closes the given list.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @throws PIMException if the list is not accessible.
	 */
	private native void closePIMListN(int listType, int handle) throws PIMException;
	
	/**
	 * Adds a new category to the given list.
	 * The implementor can assume the new category does not yet exist.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param name The new category.
	 * @throws PIMException if the list is not accessible.
	 */
	private native void addCategoryN(int listType, int handle, String name) throws PIMException;	
	
	/**
	 * Deletes the given category.
	 * The implementor can assume the category exists.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param name The category name.
	 * @param deleteUnassignedItems boolean flag where true
	 * 		indicates to delete items that no longer have any categories
	 * 		assigned to them as a result of this method, and where false 
	 * 		indicates that no items are deleted as a result of this method.
	 * @throws PIMException if the list is not accessible or an error occurs.
	 */
	private native void deleteCategoryN(int listType, int handle, String name, boolean deleteUnassignedItems) throws PIMException;
	
	/**
	 * Renames the given category.
	 * All items associated with the old category name will 
	 * be changed to reference the new category name after this method is invoked
	 * If the new category name is already an existing category, then the items
	 * associated with the old category name will be associated with the existing category. 
	 * The implementor can assume the old categeroy exists.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param name The category name to be renamed.
	 * @param newName The new category name.
	 * @throws PIMException if the list is not accessible.
	 */
	private native void renameCategoryN(int listType, int handle, String name, String newName) throws PIMException;
	
	/**
	 * Answers an array of category names for the given PIMList.
	 * The implementor can assume this list supports categories. 
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @return String[] An array of category names.
	 * @throws PIMException if the list is not accessible. 
	 */
	private native String[] getCategoriesN(int listType, int handle);
	
	/**
	 * Answers if the given name is a category.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param categoryName The category name.
	 * @return boolean true if the given name is a category.
	 * @throws PIMException if the list is not accessible.
	 */
	private native boolean isCategoryN(int listType, int handle, String categoryName);
	
	/**
	 * Answers the maximum number of categories that this list can have.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @return int
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native int maxCategoriesN(int listType, int handle);
	
	/**
	 * Answers the extended fields supported by this list.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @return int[] The order of the fields returned is unspecified.
	 *		If there are no extended fields, a zero-length array is returned.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native int[] getExtendedFieldsN(int listType, int handle);
	
	/**
	 * Answers all the fields supported by this list, including
	 * both standard and extended fields. 
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @return int[] The order of the fields returned is unspecified.
	 * 		If there are no supported fields, a zero-length array is returned.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native int[] getSupportedFieldsN(int listType, int handle);
	
	/**
	 * Answers an integer array containing all of the supported attributes 
	 * for the given field. All attributes supported by this list, including
	 * both standard and extended, are returned in this array. 
	 * The attributes are provided one attribute per entry in the returned 
	 * integer array.
	 * The implementor can assume the field is supported.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param field One of the supported fields.
	 * @return int[] An int array of the supported attributes, one attribute
	 * 		per entry in the array. If there are no supported fields, a zero-length
	 * 		array is returned.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native int[] getSupportedAttributesN(int listType, int handle, int field);
	
	/**
	 * Answers an integer array containing all of the supported attributes 
	 * for all fields supported by this list. The array includes
	 * both standard and extended attributes. 
	 * The attributes are provided one attribute per entry in the returned 
	 * integer array.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @return int[] An int array of the supported attributes, one attribute
	 * 		per entry in the array. If there are no supported fields, a zero-length
	 * 		array is returned.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native int[] getAllSupportedAttributesN(int listType, int handle);
	
	/**
	 * Answers an integer array containing all of the supported elements of
	 * a string array for the given field. The array elements are provided
	 * one element per entry in the returned integer array.
	 * The implementor can assume the field is a supported STRING_ARRAY field. 
	 * @param listType  {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}. 
	 * @param handle The list handle.
	 * @return int[] An int array representing the supported array elements,
	 * 		one array element per entry in the array. If there are no supported 
	 * 		array elements, a zero-length array is returned.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native int[] getSupportedArrayElementsN(int listType, int handle, int field);
	
	/**
	 * Answers Returns an int representing the data type of the data associated 
	 * with the given extended field.
	 * The implementor can assume the field is a valid extended field.
	 * @param listType  {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param field One of the extended fields.
	 * @return int Representing the type of the data associated
	 * 		with the field. 
	 * @throws RuntimeException if the list is not accessible.
	 */
	private static native int getExtendedFieldDataTypeN(int listType, int field);
	
	/**
	 * Answers a String label associated with the given field. 
	 * The implementor can assume the field is supported.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle
	 * @param field One of the supported fields.
	 * @return String The String label for this field.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native String getFieldLabelN(int listType, int handle, int field);
	
	/**
	 * Answers a String label associated with the given attribute.
	 * The implementor can assume the attribute is valid for this field
	 * and the field is one of the supported fields, including both standart
	 * and extended.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param attribute One of the supported attributes for this field.
	 * @return String The String label for the attribute.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native String getAttributeLabelN(int listType, int handle, int attribute);
	
	/**
	 * Answers a String label associated with the given array element.
	 * The implementor can assume the field is a supported STRING_ARRAY field
	 * and the array element is valid for this field. 
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle. The list handle
	 * @param field One of the supported STRING_ARRAY fields.
	 * @param arrayElement The element in the array.
	 * @return String The String label for the array element.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native String getArrayElementLabelN(int listType, int handle, int field, int arrayElement);
	
	/**
	 * Indicates the total number of data values that a particular field 
	 * supports in this list.
	 * The implementor can assume the field is valid.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle
	 * @param field One of the supported fields.
	 * @return int A int indicating the number of values that can be stored
	 * 		in the field. Additionally, -1 indicates this field supports having 
	 * 		an unlimited number of added values in it.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private native int maxValuesN(int listType, int handle, int field);
	
	/**
	 * Indicates the size of the given extended STRING_ARRAY field.
	 * The implementor can assume the field is a valid extended STRING_ARRAY field. 
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param field One of the extended STRING_ARRAY fields.
	 * @return int The array length of the field.
	 * @throws RuntimeException if the list is not accessible.
	 */
	private static native int extendedStringArraySizeN(int listType, int field);
	
	/**
	 * Answers the next available (not deleted) record.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param rechandle The record handle.
	 * @return int The new record handle or -1.
	 * @throws PIMException if the list is not accessible.
	 */
	private native int nextRecordN(int listType, int handle, int rechandle) throws PIMException;
	
	/**
	 * Answers the next available record in the given category.
	 * The implementor can assume the category exists.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param rechandle The record handle.
	 * @param category The category name.
	 * @return int The new record handle or -1.
	 * @throws PIMException if the list is not accessible.
	 */
	private native int nextRecordInCategoryN(int listType, int handle, int rechandle, String category) throws PIMException;
	
	/**
	 * Answers the next record where one of the STRING field match the given String.
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param rechandle The record handle.
	 * @param matching
	 * @return int The new record handle or -1.
	 * @throws PIMException if the list is not accessible.
	 */
	private native int nextRecordWithMatchStrN(int listType, int handle, int rechandle, String matching) throws PIMException;

	/**
	 * Removes the given record. 
	 * @param listType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param handle The list handle.
	 * @param rechandle The record handle.
	 * @throws PIMException if the list is not accessible or if the record 
	 * 		has been deleted or an error occurs.
	 */
	private native void removeItemN(int listType, int handle, int rechandle) throws PIMException;
	
	/**
	 * @see javax.microedition.pim.PIMList#getName()
	 */
	public String getName() {
		return listName;
	}

	/**
	 * @see javax.microedition.pim.PIMList#close()
	 */
	public void close() throws PIMException {
		synchronized(key) {
			checkListClosed("List already closed.");
			closePIMListN(listType, handle);
			PIMImpl.removePIMList(key);
			listClosed = true;
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#items()
	 */
	public Enumeration items() throws PIMException {
		synchronized(key) {
			checkListClosed();	
			checkListMode(PIM.WRITE_ONLY);
			
			return new Enumeration() {
				
				private int currentRechandle = -1;
				private Object current = null;
				
				public boolean hasMoreElements() {
					if(current == null)
						current = getNext();
					return (current!=null);
				}
				
				public Object nextElement() {
					Object next;
					if(current != null) {
						next = current;
						current = null;
					}
					else
						next = getNext();
					
					if (next == null)
						throw new NoSuchElementException();
					return next;
				}
				
				private Object getNext() {
					try {
						checkListClosed();
						int nextRechandle = nextRecordN(listType, handle, currentRechandle);
						PIMItem item = null;
						if (nextRechandle != -1) {
							currentRechandle = nextRechandle;
							switch (PIMListImpl.this.listType) {
								case PIM.CONTACT_LIST:
									item = new ContactImpl(PIMListImpl.this, nextRechandle);
									break;
								case PIM.EVENT_LIST:
									item = new EventImpl(PIMListImpl.this, nextRechandle);
									break;
								case PIM.TODO_LIST:
									item = new ToDoImpl(PIMListImpl.this, nextRechandle);
									break;
							}
						}
						return item;
					}
					catch (PIMException e) {
						return null;
					}
				}
			};
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#items(PIMItem)
	 */
	public Enumeration items(final PIMItem matchingItem) throws PIMException {
		synchronized(key) {
			if (matchingItem == null)
				throw new NullPointerException();
			if (!this.equals(matchingItem.getPIMList()))
				throw new IllegalArgumentException("The matching item does not belong to this list");
			checkListClosed();
			checkListMode(PIM.WRITE_ONLY);
			
			return new Enumeration() {
				
				private int currentRechandle = -1;
				private Object current = null;
				Enumeration items = items();
				int[] fields = matchingItem.getFields();
				
				public boolean hasMoreElements() {
					if(current == null)
						current = getNext();
					return (current != null);
				}
				
				public Object nextElement() {
					Object next;
					if(current != null) {
						next = current;
						current = null;
					}
					else
						next = getNext();	
					
					if (next == null)
						throw new NoSuchElementException();	
					return next;
				}
				
				private Object getNext() {
					boolean matched = false;
					
					while (items.hasMoreElements() && !matched) {
						PIMItem nextItem = (PIMItem)items.nextElement();
						int[] otherfields = nextItem.getFields();

						for (int i = 0; i < fields.length; i++) {
							if (!PIMUtil.contains(otherfields, fields[i]))
								break;
							else {
								boolean comp = compareField(fields[i], matchingItem, nextItem);
								if (comp == false)
									break;
							}
							// check if we made it through all the fields
							if (i == fields.length - 1)
								matched = true;
						}
						
						if (matched)
							return nextItem;
					}
					return null;
				}
			};
		}
	}

	/**
	 * Compares the values set in <code>nextItem</code> with 
	 * <code>matchingItem</code> for the given field .
	 * @param field
	 * @param matchingItem
	 * @param nextItem
	 * @return boolean
	 */
	private boolean compareField(int field, PIMItem matchingItem, PIMItem nextItem) {
		boolean result = true;
		int type = getFieldDataType(field);
		int numvalues1 = matchingItem.countValues(field);
		int numvalues2 = nextItem.countValues(field);
		
		// Compare the values of the field and attributes
		// indexes are irrelevant
		switch (type) {
			case Contact.INT: 	
				for (int j = 0; j < numvalues1; j++) {
					int matchingValue = matchingItem.getInt(field, j);
					
					// indexes are irrelevant
					boolean match = false;
					for (int i = 0; i < numvalues2 && !match; i++) {
						
						int value = nextItem.getInt(field, i);
						if (matchingValue == value) {
							int attr1 = matchingItem.getAttributes(field, j);
							int attr2 = nextItem.getAttributes(field, i);
							// attr1 must be a subset or equal to attr2
							if ((attr1 & attr2) == attr1)
								match = true;
						}
					}
					if (!match) 
						result = false;
				}
				break;
				
			case Contact.DATE: 
				for (int j = 0; j < numvalues1; j++) {
					long matchingValue = matchingItem.getDate(field, j);
					
					// indexes are irrelevant
					boolean match = false;
					for (int i = 0; i < numvalues2 && !match; i++) {
						
						long value = nextItem.getDate(field, i);
						if (matchingValue == value)
						{
							int attr1 = matchingItem.getAttributes(field, j);
							int attr2 = nextItem.getAttributes(field, i);
							// attr1 must be a subset or equal to attr2
							if ((attr1 & attr2) == attr1)
								match = true;
						}
					}
					if (!match) 
						result = false;
				}
				break;
				
			case Contact.BINARY: 
				for (int j = 0; j < numvalues1; j++) {
					byte[] matchingValue = matchingItem.getBinary(field, j);
					
					// indexes are irrelevant
					boolean match = false;
					for (int i = 0; i < numvalues2 && !match; i++) {
						
						byte[] value = nextItem.getBinary(field, i);
						if (matchingValue.equals(value))
						{
							int attr1 = matchingItem.getAttributes(field, j);
							int attr2 = nextItem.getAttributes(field, i);
							// attr1 must be a subset or equal to attr2
							if ((attr1 & attr2) == attr1)
								match = true;
						}
					}
					if (!match) 
						result = false;
				}
				break;
				
			case Contact.BOOLEAN: 
				for (int j = 0; j < numvalues1; j++) {
					boolean matchingValue = matchingItem.getBoolean(field, j);
					
					// indexes are irrelevant
					boolean match = false;
					for (int i = 0; i < numvalues2 && !match; i++) {
						
						boolean value = nextItem.getBoolean(field, i);
						if (matchingValue == value) {
							int attr1 = matchingItem.getAttributes(field, j);
							int attr2 = nextItem.getAttributes(field, i);
							// attr1 must be a subset or equal to attr2
							if ((attr1 & attr2) == attr1)
								match = true;
						}
					}
					if (!match) 
						result = false;
				}
				break;
				
			case Contact.STRING: 	
				for (int j = 0; j < numvalues1; j++) {
					String matchingValue = matchingItem.getString(field, j);
					// indexes are irrelevant
					boolean match = false;
					for (int i = 0; i < numvalues2 && !match; i++) {
						String value = nextItem.getString(field, i);
						if (compareString(matchingValue, value) || matchingValue.equals("")) {
							int attr1 = matchingItem.getAttributes(field, j);
							int attr2 = nextItem.getAttributes(field, i);
							// attr1 must be a subset or equal to attr2
							if ((attr1 & attr2) == attr1)
								match = true;
						}
					}
					if (!match)
						result = false;
				}
				break;
			case Contact.STRING_ARRAY: 
				for (int j = 0; j < numvalues1; j++) {
					String[] matchingValue = matchingItem.getStringArray(field, j);
					
					// indexes are irrelevant
					boolean match = false;
					for (int i = 0; i < numvalues2 && !match; i++) {
						
						String[] value = nextItem.getStringArray(field, i);
						if (compareStringArray(matchingValue, value))
						{
							int attr1 = matchingItem.getAttributes(field, j);
							int attr2 = nextItem.getAttributes(field, i);
							// attr1 must be a subset or equal to attr2
							if ((attr1 & attr2) == attr1)
								match = true;
						}
					}
					if (!match) 
						result = false;
				}
				break;
		}
		return result;
	}

	/**
	 * Compares <code>value</code> with <code>matchingValue.
	 * @param matchingValue
	 * @param value
	 * @return boolean
	 */
	private boolean compareStringArray(String[] matchingValue, String[] value) {
		for (int i = 0; i < matchingValue.length; i++) {
			if (matchingValue[i] != null && value[i] != null) {
				if (!compareString(matchingValue[i], value[i])) {
					return false;
				}
			}
			else if (matchingValue[i] != null && value == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Compares <code>value</code> with <code>matchingValue..
	 * @param matchingValue
	 * @param value
	 * @return boolean
	 */
	private boolean compareString(String matchingValue, String value) {
		return value.toLowerCase().indexOf(matchingValue.toLowerCase()) != -1;
	}

	/**
	 * @see javax.microedition.pim.PIMList#items(String)
	 */
	public Enumeration items(final String matchingValue) throws PIMException {
		synchronized(key) {
			if (matchingValue == null)
				throw new NullPointerException();
			checkListClosed();
			checkListMode(PIM.WRITE_ONLY);
				
			return new Enumeration() {
				private int currentRechandle = -1;
				private Object current = null;

				public boolean hasMoreElements() {
					if(current == null)
						current = getNext();
					return (current != null);
				}
				
				public Object nextElement() {
					Object next;
					if(current != null) {
						next = current;
						current = null;
					}
					else
						next = getNext();	
					
					if (next == null)
						throw new NoSuchElementException();	
					return next;
				}
				
				private Object getNext() {
					try {
						checkListClosed();
						int nextRechandle = nextRecordWithMatchStrN(listType, handle, currentRechandle, matchingValue);
						PIMItem item = null;
						if (nextRechandle != -1) {
							currentRechandle = nextRechandle;
							switch (PIMListImpl.this.listType) {
								case PIM.CONTACT_LIST:
									item = new ContactImpl(PIMListImpl.this, nextRechandle);
									break;
								case PIM.EVENT_LIST:
									item = new EventImpl(PIMListImpl.this, nextRechandle);
									break;
								case PIM.TODO_LIST:
									item = new ToDoImpl(PIMListImpl.this, nextRechandle);
									break;
							}
						}
						return item;
					}
					catch (PIMException e) {
						return null;
					}
				}
			};
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#itemsByCategory(String)
	 */
	public Enumeration itemsByCategory(final String category) throws PIMException {
		synchronized(key) {
			// category = null means return items uncategorized 
			checkListClosed();
			checkListMode(PIM.WRITE_ONLY);	
				
			return new Enumeration() {
				
				private int currentRechandle = -1;
				private Object current = null;
				
				public boolean hasMoreElements() {
					if(current == null)
						current = getNext();
					return (current != null);
				}
				
				public Object nextElement() {
					Object next;
					if(current != null) {
						next = current;
						current = null;
					}
					else
						next = getNext();	
					
					if (next == null)
						throw new NoSuchElementException();	
					return next;
				}
				
				private Object getNext() {
					try {
						checkListClosed();

						int nextRechandle = nextRecordInCategoryN(listType, handle, currentRechandle, category);
						
						PIMItem item = null;
						if (nextRechandle != -1) {
							currentRechandle = nextRechandle;
							switch (PIMListImpl.this.listType) {
								case PIM.CONTACT_LIST:
									item = new ContactImpl(PIMListImpl.this, nextRechandle);
									break;
								case PIM.EVENT_LIST:
									item = new EventImpl(PIMListImpl.this, nextRechandle);
									break;
								case PIM.TODO_LIST:
									item = new ToDoImpl(PIMListImpl.this, nextRechandle);
									break;
							}
						}
						return item;
					}
					catch (PIMException e) {
						return null;
					}
				}
			};
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#getCategories()
	 */
	public String[] getCategories() throws PIMException {			
		synchronized(key) {
			if (maxCategories() == 0) // not supported
				return new String[0];
			checkListClosed();
			String[] categories = getCategoriesN(listType, handle);
			return categories;
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#isCategory(String)
	 */
	public boolean isCategory(String category) throws PIMException {
		synchronized(key) {
			if (category == null)
				throw new NullPointerException();
			if (maxCategories() == 0) // not supported
				return false;
				
			checkListClosed();
			return isCategoryN(listType, handle, category);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#addCategory(String)
	 */
	public void addCategory(String category) throws PIMException {
		synchronized(key) {
			checkListClosed();
			checkListMode(PIM.READ_ONLY);
			if (category == null)
				throw new NullPointerException("Category is null");
			int maxCategories = maxCategories();
			if (maxCategories == 0)	
				throw new PIMException("Categories are not supported", PIMException.FEATURE_NOT_SUPPORTED);
			if (getCategories().length >= maxCategories && maxCategories != -1)
				throw new PIMException("Max number of categories exceeded");
			
			addCategoryN(listType, handle, category);
		}
		
	}

	/**
	 * @see javax.microedition.pim.PIMList#deleteCategory(String, boolean)
	 */
	public void deleteCategory(String category, boolean deleteUnassignedItems) throws PIMException {
		synchronized(key) {
			checkListClosed();
			checkListMode(PIM.READ_ONLY);
			if (category == null)
				throw new NullPointerException("Category is null");
			if (maxCategories() == 0)	
				throw new PIMException("Categories are not supported", PIMException.FEATURE_NOT_SUPPORTED);
			
			deleteCategoryN(listType, handle, category, deleteUnassignedItems);
		}
	}
		
	/**
	 * @see javax.microedition.pim.PIMList#renameCategory(String, String)
	 */
	public void renameCategory(String currentCategory, String newCategory) throws PIMException {
		synchronized(key) {
			checkListClosed();
			checkListMode(PIM.READ_ONLY);
			if (currentCategory == null || newCategory == null)
				throw new NullPointerException("Category is null");
			if (maxCategories() == 0)	
				throw new PIMException("Categories are not supported.", PIMException.FEATURE_NOT_SUPPORTED);
			if (!isCategory(currentCategory))
				throw new PIMException("Current category does not exist.");
			if (currentCategory.equals(newCategory))
				return; // nothing to do
				
			renameCategoryN(listType, handle, currentCategory, newCategory);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#maxCategories()
	 */
	public int maxCategories() {
		synchronized(key) {
			return maxCategoriesN(listType, handle);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#isSupportedField(int)
	 */
	public boolean isSupportedField(int field) {
		synchronized(key) {
			int[] suppfields = getSupportedFields();
			return PIMUtil.contains(suppfields, field);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#getSupportedFields()
	 */
	public int[] getSupportedFields() {
		synchronized(key) {
			return getSupportedFieldsN(listType, handle);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#isSupportedAttribute(int, int)
	 */
	public boolean isSupportedAttribute(int field, int attribute) {
		synchronized(key) {
			int[] attr = getSupportedAttributesN(listType, handle, field);
			return PIMUtil.contains(attr, attribute);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#getSupportedAttributes(int)
	 */
	public int[] getSupportedAttributes(int field) {
		synchronized(key) {
			checkForFieldException(field);
			return getSupportedAttributesN(listType, handle, field);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#isSupportedArrayElement(int, int)
	 */
	public boolean isSupportedArrayElement(int stringArrayField, int arrayElement) {
		synchronized(key) {
			int[] arrayElements = getSupportedArrayElementsN(listType, handle, stringArrayField);
			return PIMUtil.contains(arrayElements, arrayElement);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#getSupportedArrayElements(int)
	 */
	public int[] getSupportedArrayElements(int stringArrayField) {
		synchronized(key) {
			checkForFieldException(stringArrayField, PIMItem.STRING_ARRAY);
			Integer field = new Integer(stringArrayField);
			int[] arrayElements = (int[])supportedArrayElements.get(field);
			if (arrayElements == null) {
				arrayElements = getSupportedArrayElementsN(listType, handle, stringArrayField);
				supportedArrayElements.put(field, arrayElements);
			}
			return arrayElements;
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#getFieldDataType(int)
	 */
	public int getFieldDataType(int field) {
		synchronized(key) {
			checkForFieldException(field);
			return getFieldDataTypeS(listType, field);
		}
	}
	
	/**
	 * Answers the field data type for the standart fields.
	 * @param PIMListType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param field One of the standart field.
	 * @return int The field data type or -1.
	 */
	protected static int getFieldDataTypeS(int PIMListType, int field) {
		switch (PIMListType) {
			case PIM.CONTACT_LIST:
				switch (field) {
					case Contact.NAME:
					case Contact.ADDR:	
						return PIMItem.STRING_ARRAY;
					case Contact.EMAIL:
					case Contact.FORMATTED_NAME:
					case Contact.FORMATTED_ADDR:
					case Contact.NICKNAME:
					case Contact.NOTE:
					case Contact.ORG:
					case Contact.TEL:
					case Contact.TITLE:
					case Contact.UID:
					case Contact.URL:
					case Contact.PHOTO_URL:
					case Contact.PUBLIC_KEY_STRING:
						return PIMItem.STRING;
					case Contact.BIRTHDAY:
					case Contact.REVISION:
						return PIMItem.DATE;
					case Contact.PHOTO:
					case Contact.PUBLIC_KEY:
						return PIMItem.BINARY;
					case Contact.CLASS:
						return PIMItem.INT;
					default :
						break;
				}
			case PIM.EVENT_LIST:
				switch (field) {
					case Event.LOCATION:
					case Event.SUMMARY:
					case Event.UID:
					case Event.NOTE:
						return PIMItem.STRING;
					case Event.END:
					case Event.REVISION:
					case Event.START:
						return PIMItem.DATE;
					case Event.ALARM:
					case Event.CLASS:
						return PIMItem.INT;
					default :
						break;
				}
			case PIM.TODO_LIST:
				switch (field) {
					case ToDo.SUMMARY:
					case ToDo.UID:
					case ToDo.NOTE:
						return PIMItem.STRING;
					case ToDo.DUE:
					case ToDo.REVISION:
					case ToDo.COMPLETION_DATE:
						return PIMItem.DATE;
					case ToDo.PRIORITY:
					case ToDo.CLASS:
						return PIMItem.INT;
					case ToDo.COMPLETED:
						return PIMItem.BOOLEAN;
					default :
						break;
				}
		}		
		return getExtendedFieldDataTypeN(PIMListType, field);
	}

	/**
	 * @see javax.microedition.pim.PIMList#getFieldLabel(int)
	 */
	public String getFieldLabel(int field) {
		synchronized(key) {
			checkForFieldException(field);
			return getFieldLabelN(listType, handle, field);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#getAttributeLabel(int)
	 */
	public String getAttributeLabel(int attribute) {
		synchronized(key) {
			checkForAttributeException(attribute);
			return getAttributeLabelN(listType, handle, attribute);
		}
	}
	
	/**
	 * @see javax.microedition.pim.PIMList#getArrayElementLabel(int, int)
	 */
	public String getArrayElementLabel(int stringArrayField, int arrayElement) {
		synchronized(key) {
			checkForFieldException(stringArrayField, PIMItem.STRING_ARRAY);
			int length = stringArraySize(stringArrayField);
			if (arrayElement < 0 || arrayElement > length)
				throw new IllegalArgumentException("Invalid array element");
			if (!isSupportedArrayElement(stringArrayField, arrayElement))
			// ?? really a UnsupportedFieldEx...
				throw new UnsupportedFieldException("Array element not supported");
			return getArrayElementLabelN(listType, handle, stringArrayField, arrayElement);
		}
	}

	/**
	 * @see javax.microedition.pim.PIMList#maxValues(int)
	 */
	public int maxValues(int field) {
		synchronized(key) {
			try {
				checkForFieldException(field);
			} catch (UnsupportedFieldException e) {
				return 0;
			}
			return maxValuesN(listType, handle, field);
		}
	}
	
	/**
	 * @see javax.microedition.pim.PIMList#stringArraySize(int)
	 */
	public int stringArraySize(int stringArrayField) {
		synchronized(key) {
			try {
				checkForFieldException(stringArrayField, PIMItem.STRING_ARRAY);
			}
			// this method do not throw UnsupportedFieldException.
			catch (UnsupportedFieldException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
			return getStringArraySizeS(listType, stringArrayField);
		}
	}
	
	/**
	 * Answers the size of the given STRING_ARRAY field.
	 * @param PIMListType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param stringArrayField One of the standart STRING_ARRAY field.
	 * @return int The array size or -1; 
	 */
	protected static int getStringArraySizeS(int PIMListType, int stringArrayField) {
		if (stringArrayField == Contact.NAME)
			return ContactImpl.NAMESIZE;
		if (stringArrayField == Contact.ADDR)
			return ContactImpl.ADDRSIZE;
		return extendedStringArraySizeN(PIMListType, stringArrayField);
	}	
	
	/**
	 * Imports a PIMItem.
	 * @param PIMListType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param item The item to import.
	 * @return PIMItem A new PIMItem with data from <code>item</code>.
	 */
	protected PIMItem importItem(int PIMListType, PIMItem item) {
		if (item == null)
			throw new NullPointerException();
			
		PIMItemImpl newItem = null;
		
		switch (PIMListType) {
			case PIM.CONTACT_LIST:
				newItem = new ContactImpl(this, -1);
				break;
			case PIM.EVENT_LIST:
				newItem = new EventImpl(this, -1);
				break;
			case PIM.TODO_LIST:
				newItem = new ToDoImpl(this, -1);
		}
		
		// add categories
		String[] categories = item.getCategories();
		int length = categories.length;
		int maxCat = Math.min(length, newItem.maxCategories());
		for (int i = 0; i < maxCat; i++) {
			try {
				String category = categories[i];
				addCategory(category);
				newItem.addToCategory(category);
			}
			catch (PIMException e) {
				// nothing to do
			}
		}
		
		// add fields
		int[] fields = item.getFields();
		for (int i = 0; i < fields.length; i++) {
			if (isSupportedField(fields[i])) {
				int type = getFieldDataType(fields[i]);
				int numvalues = item.countValues(fields[i]);
				try {
					switch (type) {
						case Contact.INT: 	
							for (int j = 0; j < numvalues; j++) {
								newItem.addInt(fields[i], item.getAttributes(fields[i], j), item.getInt(fields[i], j));						
							}
							break;
						case Contact.DATE: 
							for (int j = 0; j < numvalues; j++) {
								newItem.addDate(fields[i], item.getAttributes(fields[i], j), item.getDate(fields[i], j));						
							}
							break;
						case Contact.BINARY: 
							for (int j = 0; j < numvalues; j++) {
								byte[] bin = item.getBinary(fields[i], j);
								newItem.addBinary(fields[i], item.getAttributes(fields[i], j), bin, 0, bin.length);
														
							}
							break;
						case Contact.BOOLEAN: 
							for (int j = 0; j < numvalues; j++) {
								newItem.addBoolean(fields[i], item.getAttributes(fields[i], j), item.getBoolean(fields[i], j));						
							}
							break;
						case Contact.STRING: 	
							for (int j = 0; j < numvalues; j++) {
								newItem.addString(fields[i], item.getAttributes(fields[i], j), item.getString(fields[i], j));						
							}
							break;
						case Contact.STRING_ARRAY: 
							for (int j = 0; j < numvalues; j++) {
								newItem.addStringArray(fields[i], item.getAttributes(fields[i], j), item.getStringArray(fields[i], j));						
							}
							break;
					}
				}
				catch (Exception e) {
					// nothing to do
				}
			}
		}
		importExtendedFields(PIMListType, item, newItem);
		// updates the revision date.
		newItem.updateRevisionDate();
		return newItem;
	}
	
	/**
	 * Try to import extended fields if <code>item</code> comes from a stream.
	 * @param PIMListType {@link PIM#CONTACT_LIST}, {@link PIM#EVENT_LIST} 
	 * 		or {@link PIM#TODO_LIST}.
	 * @param item
	 * @param newItem
	 */
	private void importExtendedFields(int PIMListType, PIMItem item, PIMItemImpl newItem) {
		Vector properties = ((PIMItemImpl)item).getVExtendedFields();
		if (properties == null)
			return;
		// gets the extended fields for the given PIMList. 
		int[] extFields = getExtendedFieldsN(listType, handle);
		String[] labels = new String[extFields.length];
 		for (int i = 0; i < labels.length; i++) {
			labels[i] = getFieldLabel(extFields[i]);
		}
		for (int i = 0; i < properties.size(); i++) {
			try {
				Property property = (Property) properties.elementAt(i);
				String name = property.getName().substring(2);
				String[] values = property.getValues();
				String[] params = property.getParameters();
				if (values.length == 0)
					continue;
				int j = 0;
				for (j = 0; j < labels.length; j++) {
					if (PIMUtil.equalsIgnoreCase(labels[j], name))
						break;
				}
				if (j == labels.length)
					continue;
				int fieldID = extFields[j];
				
				int attribID = 0;
				for (int k = 0; k < params.length; k++) {
					int result = PIMUtil.getAttributeID(PIMListType, params[k]);
					if (result != -1)
						attribID |= result;
				}
				
				int dataType = getFieldDataType(fieldID);
				int maxValues = maxValues(fieldID);
				if(maxValues == -1) maxValues = Integer.MAX_VALUE;
				switch (dataType) {
					case PIMItem.INT:
						for (int k = 0; k < Math.min(values.length, maxValues); k++) {
							newItem.addInt(fieldID, attribID, Integer.parseInt(values[k]));
						}
						break;
					case PIMItem.DATE:
						for (int k = 0; k < Math.min(values.length, maxValues); k++) {
							newItem.addDate(fieldID, attribID, Long.parseLong(values[k]));
						}
						break;
					case PIMItem.STRING:
						for (int k = 0; k < Math.min(values.length, maxValues); k++) {
							newItem.addString(fieldID, attribID, values[k]);
						}
						break;
					case PIMItem.BINARY:
						for (int k = 0; k < Math.min(values.length, maxValues); k++) {
							byte[] binaryValue = values[k].getBytes();
							newItem.addBinary(fieldID, attribID, binaryValue, 0, binaryValue.length);
						}
						break;
					case PIMItem.BOOLEAN:
						for (int k = 0; k < Math.min(values.length, maxValues); k++) {
							if (PIMUtil.equalsIgnoreCase(values[k], "true"))
								newItem.addBoolean(fieldID, attribID == -1 ? 0 : attribID, true);
							else if (PIMUtil.equalsIgnoreCase(values[k], "false"))
								newItem.addBoolean(fieldID, attribID == -1 ? 0 : attribID, false);
						}
						break;
					case PIMItem.STRING_ARRAY:
						int size = stringArraySize(fieldID);
						String[] array = new String[size];
						for (int k = 0; k < Math.min(size, values.length); k++) {
							array[k] = values[k];
						}
						newItem.addStringArray(fieldID, attribID == -1 ? 0 : attribID, array);
						break;
				}
			}
			catch (Exception e) {
				// do nothing
			}
		}
	}
	
	/**
	 * Deletes the given item from this list.
	 * @param item The item to remove.
	 * @throws PIMException if the list is closed, not accessible or if the item
	 * 		does not belong to this PIMList.
	 * @throws SecurityException if the application is not given 
	 * 		permission to write to the Contact list or the list is opened READ_ONLY.
	 */
	protected void removeItem(PIMItem item) throws PIMException {
		if (item == null)
			throw new NullPointerException();
		if (!this.equals(((PIMItemImpl)item).owner))
			throw new PIMException("The item does not belong to this list.");
		int rechandle = ((PIMItemImpl)item).rechandle;
		if (rechandle == -1)
			throw new PIMException("The item has not been commited.");
		else {
			checkListMode(PIM.READ_ONLY);
			checkListClosed();
			removeItemN(listType, handle, rechandle);
			((PIMItemImpl)item).delete();
		}
	}
	
	/**
	 * Checks if the given field is valid and supported.
	 * If not valid throws a IllegalArgumentException and
	 * id not supported throws an UnsupportedFieldException.
	 * @param field
	 */
	protected void checkForFieldException(int field) {
		if (!isExtendedField(field)) {
			if (listType == PIM.CONTACT_LIST && (field < Contact.ADDR || field > Contact.URL))
				throw new IllegalArgumentException("Invalid field");
			else if (listType == PIM.EVENT_LIST && (field < Event.ALARM || field > Event.UID))
				throw new IllegalArgumentException("Invalid field");
			else if (listType == PIM.TODO_LIST && (field < ToDo.CLASS || field > ToDo.UID))
				throw new IllegalArgumentException("Invalid field");
			// Unsupported field
			if (!isSupportedField(field))
				throw new UnsupportedFieldException("Field not supported", field);
		}
	}
	
	/**
	 * Checks if the given field is valid, supported and 
	 * its data type match the given data type.
	 * @param field The field to check.
	 * @param dataType The expected data type. {@link PIMItem#BINARY}, {@link PIMItem#BOOLEAN}, {@link PIMItem#DATE},
	 * 		{@link PIMItem#INT}, {@link PIMItem#STRING} or {@link PIMItem#STRING_ARRAY}.
	 */
	protected void checkForFieldException(int field, int dataType) {
		checkForFieldException(field);
		if (getFieldDataType(field) != dataType)
			throw new IllegalArgumentException();
	}

	/**
	 * Indicates if the given field is an extended field.
	 * @param field
	 * @return boolean
	 */
	private boolean isExtendedField(int field) {
		if (extendedFields == null)
			extendedFields = getExtendedFieldsN(listType, handle);
		return PIMUtil.contains(extendedFields, field);
	}

	/**
	 * Checks if the given attribute is valid and supported
	 * If not valid throws a IllegalArgumentException and if not supported
	 * throws an UnsupportedFieldException. 
	 * @param attribute The attribute to check.
	 */
	protected void checkForAttributeException(int attribute) {
		if (attribute != PIMItem.ATTR_NONE) {
			int [] attrs = getAllSupportedAttributesN(listType, handle);
			if (!PIMUtil.contains(attrs, attribute)) {
				if(attribute != Contact.ATTR_ASST &&
				   attribute != Contact.ATTR_AUTO &&
				   attribute != Contact.ATTR_FAX &&
				   attribute != Contact.ATTR_HOME &&
				   attribute != Contact.ATTR_MOBILE &&
				   attribute != Contact.ATTR_OTHER &&
				   attribute != Contact.ATTR_PAGER && 
				   attribute != Contact.ATTR_PREFERRED &&
				   attribute != Contact.ATTR_SMS &&
				   attribute != Contact.ATTR_WORK)
					throw new IllegalArgumentException();
				
				throw new UnsupportedFieldException("Attribute not supported");
			}
		}
	}
	
	/**
	 * Checks if <code>m</code> is the list mode.
	 * If not throws a SecurityException.
	 * @param m {@link PIM#READ_ONLY}, {@link PIM#WRITE_ONLY} 
	 * 		or {@link PIM#READ_WRITE}.
	 */
	protected void checkListMode(int m) {
		if (m != mode)
			return;
		if (m == PIM.WRITE_ONLY)
			throw new SecurityException("Unable to perform this operation in WRITE_ONLY mode.");
		if (m == PIM.READ_ONLY)
			throw new SecurityException("Unable to perform this operation in READ_ONLY mode.");
	}
	
	/**
	 * Checks if the list is closed then throws a PIMException.
	 * @param message The exception message.
	 * @throws PIMException
	 */
	protected void checkListClosed(String message) throws PIMException {
		if (listClosed)
			throw new PIMException(message, PIMException.LIST_CLOSED);
	}
	
	/**
	 * Checks if the list is closed then throws a PIMException.
	 * @throws PIMException
	 */
	protected void checkListClosed() throws PIMException {
		checkListClosed("The list is closed");
	}
	
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof PIMListImpl))
			return false;
		return this.key.equals(((PIMListImpl)o).key);		
	}
}