package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import javax.microedition.pim.Contact;


class ContactImpl extends PIMItemImpl implements Contact {
	
	/**
	 * The array size of ADDR.
	 */
	static final int ADDRSIZE = 7;
	
	/**
	 * The array size of NAME.
	 */
	static final int NAMESIZE = 5;  
	
	/**
	 * Constructor for ContactImpl.
	 */
	ContactImpl(PIMListImpl owner, int rechandle) {
		super(owner, rechandle);
	}
	
	/**
	 * Answers the preferred index for the given field or -1 if no preferred index.
	 * @param handle. The list handle.
	 * @param rechandle. The record handle.
	 * @param field. One of the supported fields.
	 * @return int.
	 * @throws RuntimeException if the given record has been deleted or if the list
	 * is not accessible.
	 */
	private native int getPreferredIndexN(int handle, int rechandle, int field);

	/**
	 * @see javax.microedition.pim.Contact#getPreferredIndex(int)
	 */
	public int getPreferredIndex(int field) {
		checkItemDeleted();
		checkValidField(field);
		if (!valuesFromOS)
			return getPreferredIndexFromStruct(field);
		else {
			checkListClosed();
			return getPreferredIndexN(owner.handle, rechandle, field);
		}
	}

	/**
	 * Reads the preferred index from the cached data.
	 * @param field
	 * @return int
	 */
	private int getPreferredIndexFromStruct(int field) {
		int result = -1;
		
		int i=1, count=0;
		// get the field's data type
		int type = owner.getFieldDataType(field);
		
		synchronized (i2) {
			switch(type) {
				case Contact.STRING:
				case Contact.STRING_ARRAY:
					// check if we can find the field in stringids
					while(count < stringids[0]) {
						if (field == stringids[i] && (stringids[i+2] & Contact.ATTR_PREFERRED) != 0) {
							result = stringids[i+1];
							break;
						}
						i += 3;
						count++;
					}
					break;
				case Contact.BINARY:
					// check if we can find the field in byteids
					while(count < byteids[0]) {
						if (field == byteids[i] && (byteids[i+2] & Contact.ATTR_PREFERRED) != 0) {
							result = byteids[i+1];
							break;
						}
						i += 4;
						count++;
					}
					break;
				case Contact.DATE:
				case Contact.INT:
				case Contact.BOOLEAN:
					// check if we can find the field in longvalues
					while(count < longvalues[0]) {
						if (field == longvalues[i] && (longvalues[i+2] & Contact.ATTR_PREFERRED) != 0) {
							result = (int)longvalues[i+1];
							break;
						}
						i += 4;
						count++;
					}
					break;
			}
		}
		return result;
	}
}