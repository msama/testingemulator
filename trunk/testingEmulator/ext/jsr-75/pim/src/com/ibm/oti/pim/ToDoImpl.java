package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import javax.microedition.pim.ToDo;

import com.ibm.oti.pim.*;


class ToDoImpl extends PIMItemImpl implements ToDo {

	/**
	 * Constructor for ToDoImpl.
	 */
	ToDoImpl(PIMListImpl owner, int rechandle) {
		super(owner, rechandle);
	}	
}