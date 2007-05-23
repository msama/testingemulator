package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.util.EmptyStackException;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.pim.PIMItem;

/*
 * Comments copy from java.util.Stack
 */
/**
 * <code>ItemStack</code> is a Last-In/First-Out(LIFO) data structure which represents
 * a stack of PIMItems.
 * It enables users to pop and push onto the stack.
 * There is no limit to the size of the stack
 */
class ItemStack {
	
	private Hashtable dates;
	private Hashtable items;
	private Vector handles;
	
	private int currentIndex = 0;
	private boolean sort;
	
	/**
	 * Constructs a new ItemStack.
	 * @param sort. If true sorts the data.
	 */
	ItemStack(boolean sort) {
		items = new Hashtable();
		dates = new Hashtable();
		handles = new Vector();
		this.sort = sort;
	}
	
	/*
	 * Clears the data indexes.
	 */
	private void clearHandles() {
		handles = new Vector();
		currentIndex = 0;
	}
	
	/**
	 * Pushes the object from the parameter onto the top of the stack.
	 * @param date. The date using to sort the items.
	 * @param item. The item to store.
	 * @param recordHandle. The record handle.
	 * @see #peek
	 * @see #pop
	 */
	synchronized void push(long date, PIMItem item, int recordHandle) {
		if (empty())
			clearHandles();
		Integer handle = new Integer(recordHandle);
		Long longDate = new Long(date);
		if (items.containsKey(handle))
			return;
		else {
			items.put(handle, item);
			dates.put(handle, longDate);
			if (sort)
				handles.insertElementAt(handle, getIndex(date));
			else
				handles.addElement(handle);
		}	
	}
	
	/**
	 * Returns the element at the top of the stack and removes it.
	 * @return Object. The element at the top of the stack.
	 * @exception	EmptyStackException when empty() is true.
	 * @see #peek
	 * @see #push
	 */
	synchronized Object pop() {
		if (empty())
			throw new EmptyStackException();
		Object handle = handles.elementAt(currentIndex);
		Object item = items.get(handle);
		dates.remove(handle);
		items.remove(handle);
		currentIndex++;
		return item;
	}
	
	/**
	 * Returns the element at the top of the stack without removing it.
	 * @return Object. The element at the top of the Stack.
	 * @exception	EmptyStackException when empty() is true.
	 * @see #pop
	 */
	synchronized Object peek() {
		if (empty())
			throw new EmptyStackException();
		Object handle = handles.elementAt(currentIndex);
		return items.get(handle);
	}
	
	/**
	 * Determines if the stack is empty or not.
	 * @return boolean. true if the stack is empty, false otherwise
	 */
	boolean empty() {
		return items.isEmpty();
	}

	/*
	 * Answers the index to store the data onto the stack.
	 * Could be improved.
	 */
	private int getIndex(long date) {
		for (int i = 0; i < handles.size(); i++) {
			Long longDate = (Long) dates.get(handles.elementAt(i));
			if (date < longDate.longValue())
				return i;
		}
		return handles.size();
	}
}