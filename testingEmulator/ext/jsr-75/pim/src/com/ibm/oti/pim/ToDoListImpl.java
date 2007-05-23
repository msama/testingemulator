package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import javax.microedition.pim.ToDo;
import javax.microedition.pim.ToDoList;


class ToDoListImpl extends PIMListImpl implements ToDoList {
	
	/**
	 * Answers the todo record handles occuring right after currentDate.
	 * All events in this array have the date.
	 * @param handle. The list handle.
	 * @param field. One of the supported DATE fields.
	 * @param currentDate. Number of milliseconds since Jan 1, 1970 GMT.
	 * @return int[]. The next records or a zero length array
	 * if no more todos occur after currentDate.
	 * @throws PIMException if the list is not accessible.
	 */
	private native int[] nextSortedToDoN(int handle, int field, long currentDate) throws PIMException;
	
	/**
	 * Constructor for ToDoListImpl.
	 */
	ToDoListImpl(String name, int mode, int handle) {
		super(name, PIM.TODO_LIST, mode, handle);
	}

	/**
	 * @see javax.microedition.pim.ToDoList#createToDo()
	 */
	public ToDo createToDo() {
		return new ToDoImpl(this, -1);
	}

	/**
	 * @see javax.microedition.pim.ToDoList#importToDo(ToDo)
	 */ 
	public ToDo importToDo(ToDo item) {
		ToDoImpl tod = (ToDoImpl)importItem(PIM.TODO_LIST, item);
		return tod;
	}

	/**
	 * @see javax.microedition.pim.ToDoList#removeToDo(ToDo)
	 */
	public void removeToDo(ToDo item) throws PIMException {
		removeItem(item);
	}

	/**
	 * @see javax.microedition.pim.ToDoList#items(int, long, long)
	 */
	public Enumeration items(final int field, final long startDate, final long endDate) throws PIMException {
		if (getFieldDataType(field) != ToDo.DATE)
			throw new IllegalArgumentException("The field data type must be DATE");
		if (startDate > endDate)
			throw new IllegalArgumentException("Invalid startDate or endDate");
		checkListClosed();
		checkListMode(PIM.WRITE_ONLY);
		
		return new Enumeration() {
			
			ItemStack todos = new ItemStack(false);
			// last millisecond of the previous day.
			long currentDate = DateHelper.setTime(startDate, 0, 0, 0, 0) - 1;
			
			/**
			 * @see java.util.Enumeration#hasMoreElements()
			 */
			public boolean hasMoreElements() {
				if (todos.empty())
					getNext();
				return (!todos.empty());
			}

			/**
			 * @see java.util.Enumeration#nextElement()
			 */
			public Object nextElement() {
				if (todos.empty())
					getNext();
				if (todos.empty())
					throw new NoSuchElementException();
				return todos.pop();
			}
			
			/**
			 * Searchs for the next item. 
			 */
			private void getNext() {
				try {
					if (currentDate > endDate)
						return;
					int[] recHandles;
					do {
						recHandles = nextSortedToDoN(handle, field, currentDate);
						for (int i = 0; i < recHandles.length; i++) {
							int recHandle = recHandles[i];
							ToDo todo = new ToDoImpl(ToDoListImpl.this, recHandle);
							currentDate = todo.getDate(field, 0);
							if (currentDate > endDate)
								return;
							checkIsInRange(todo, recHandle);
						}
						if (!todos.empty())
							return;
					}
					while (recHandles.length != 0);
				}
				catch (PIMException e) {
					// Do nothing
					// If an error occurs the method hasMoreElements will return false. 
				}
			}

			/**
			 * Checks if the given todo is in the range then stores it.
			 * @param todo
			 * @recHanlde
			 * @return boolean
			 */
			private boolean checkIsInRange(ToDo todo, int recHandle) {
				if (currentDate <= endDate && currentDate >= startDate) {
					todos.push(currentDate, todo, recHandle);
					return true;
				}
				return false;
			}
		};
	}
}