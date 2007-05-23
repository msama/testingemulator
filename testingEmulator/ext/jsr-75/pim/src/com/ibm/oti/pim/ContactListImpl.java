package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;


class ContactListImpl extends PIMListImpl implements ContactList {

	/**
	 * Constructor for ContactListImpl.
	 */
	ContactListImpl(String name, int mode, int handle) {
		super(name, PIM.CONTACT_LIST, mode, handle);
	}

	/**
	 * @see javax.microedition.pim.ContactList#createContact()
	 */
	public Contact createContact() {
		return new ContactImpl(this, -1);
	}

	/**
	 * @see javax.microedition.pim.ContactList#importContact(Contact)
	 */
	public Contact importContact(Contact contact) {
		return (ContactImpl)importItem(PIM.CONTACT_LIST, contact);
	}

	/**
	 * @see javax.microedition.pim.ContactList#removeContact(Contact)
	 */
	public void removeContact(Contact contact) throws PIMException {
		removeItem(contact);
	}
}