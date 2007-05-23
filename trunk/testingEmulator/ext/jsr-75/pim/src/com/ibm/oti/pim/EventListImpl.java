package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.microedition.pim.Event;
import javax.microedition.pim.EventList;
import javax.microedition.pim.FieldEmptyException;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMException;
import javax.microedition.pim.RepeatRule;


class EventListImpl extends PIMListImpl implements EventList {

	/**
	 * Answers the event record handles occuring right after currentDate.
	 * All events in this array have the same start date.
	 * @param handle. The list handle.
	 * @param currentDate. Number of milliseconds since Jan 1, 1970 GMT.
	 * @return int[]. The next records or a zero length array
	 * if no more events occur after currentDate.
	 * @throws PIMException if the list is not accessible.
	 */
	private native int[] nextSortedEventN(int handle, long currentDate) throws PIMException;

	/**
	 * Returns the native Repeat Rule frequencies that are supported by this EventList
	 * in an int array with each entry in the array being one of the RepeatRule
	 * frequencies.  Repeat Rule frequencies are {@link RepeatRule#YEARLY},
	 * {@link RepeatRule#MONTHLY}, {@link RepeatRule#WEEKLY}, and
	 * {@link RepeatRule#DAILY}.  If no frequencies are supported by this list, 
	 * a zero-length array is returned.
	 *
     * @return  an array of integer values corresponding to the Repeat Rule
	 *			frequencies that are supported by this list.	 
	 */
	static native int[] getSupportedRepeatRuleFrequenciesN();


	/**
	 * Returns the native Repeat Rule fields that are supported by this EventList for
	 * the provided Repeat Rule frequency.  Repeat Rule frequencies are 
	 * {@link RepeatRule#YEARLY}, {@link RepeatRule#MONTHLY}, 
	 * {@link RepeatRule#WEEKLY}, and {@link RepeatRule#DAILY}.  If no fields 
	 * are supported by this list for the given frequency, a zero-length array 
	 * is returned.  The int values in the array represent a bitwise combination
	 * of Repeat Rule fields that are valid for the given frequency.  For example,
	 * if <code>MONTHLY</code> was the frequency and this event list supported
	 * specifying monthly repeat rules by either the day of the month or by
	 * a day in a specific week (e.g. Thursday of the 3rd week in the month), 
	 * then the returned int array would contain two elements with one value
	 * being <code>DAY_IN_MONTH</code> and the other being 
	 * <code>DAY_IN_WEEK | WEEK_IN_MONTH</code>.  All valid field combinations
	 * are returned in the array.
	 *
	 * @param	frequency Repeat Rule frequency for which the supported fields
	 *			are queried.
     * @return  an array of integer values corresponding to the Repeat Rule
	 *			fiels that are supported by this list for the given frequency.	 
     */
	static native int[] getSupportedRepeatRuleFieldsN(int frequency);
	
	/**
	 * Constructor for EventListImpl.
	 */
	EventListImpl(String name, int mode, int handle) {
		super(name, PIM.EVENT_LIST, mode, handle);
	}

	/**
	 * @see javax.microedition.pim.EventList#createEvent()
	 */
	public Event createEvent() {
		return new EventImpl(this, -1);
	}

	/**
	 * @see javax.microedition.pim.EventList#importEvent(Event)
	 */
	public Event importEvent(Event item) {
		EventImpl event = (EventImpl)importItem(PIM.EVENT_LIST, item);
		// imports the repeat rule.
		RepeatRule rule = item.getRepeat();
		if (rule != null) {
			RepeatRule repeat = new RepeatRule();
			int frequency = -1;
			try {
				frequency = rule.getInt(rule.FREQUENCY);	
			}
			catch (FieldEmptyException e) {
				// nothing to do
			}
			int[] fields = rule.getFields();
			int[] supportedFields = getSupportedRepeatRuleFields(frequency);
			for (int i = 0; i < fields.length; i++) {
				int field = fields[i];
				if (PIMUtil.contains(supportedFields, field) ||
					field == RepeatRule.COUNT || field == RepeatRule.END ||
					field == RepeatRule.FREQUENCY || field == RepeatRule.INTERVAL)
				{
					if (field == RepeatRule.END)
						repeat.setDate(field, rule.getDate(field));
					else
						repeat.setInt(field, rule.getInt(field));
				}
				Enumeration exceptDates = rule.getExceptDates();
				while (exceptDates.hasMoreElements())
					repeat.addExceptDate(((Date)exceptDates.nextElement()).getTime());
				event.setRepeat(repeat);					
			}
		}
		return event;
	}

	/**
	 * @see javax.microedition.pim.EventList#removeEvent(Event)
	 */
	public void removeEvent(Event item) throws PIMException {
		removeItem(item);
	}

	/**
	 * @see javax.microedition.pim.EventList#items(int, long, long, boolean)
	 */
	public Enumeration items(final int searchType, final long startDate, final long endDate, final boolean initialEventOnly) throws PIMException {
		if (searchType != EventList.ENDING && searchType != EventList.OCCURRING && searchType != EventList.STARTING)
			throw new IllegalArgumentException("Invalid search type");
		if (startDate > endDate)
			throw new IllegalArgumentException("Invalid startDate or endDate");
		checkListClosed();		
		checkListMode(PIM.WRITE_ONLY);
		
		return new Enumeration() {
			
			private ItemStack events = new ItemStack(!initialEventOnly);
			private long currentDate = -1;
			
			/**
			 * @see java.util.Enumeration#hasMoreElements()
			 */
			public boolean hasMoreElements() {
				if (events.empty())
					getNext();
				return (!events.empty());
			}

			/**
			 * @see java.util.Enumeration#nextElement()
			 */
			public Object nextElement() {
				if (events.empty())
					getNext();
				if (events.empty())
					throw new NoSuchElementException();
				return events.pop();
			}
			
			/*
			 * Answers the next element.
			 */
			private void getNext() {
				try {
					if (initialEventOnly)
						currentDate = Math.max(DateHelper.setTime(startDate, 0, 0, 0, 0)-1, currentDate);
					if (currentDate > endDate)
						return;
					int[] recHandles;
					do {
						recHandles = nextSortedEventN(handle, currentDate);
						for (int i = 0; i < recHandles.length; i++) {
							int recHandle = recHandles[i];
							Event event = new EventImpl(EventListImpl.this, recHandle);
							// at least start or end date
							if (event.countValues(Event.START) != 0)
								currentDate = event.getDate(Event.START, 0);
							else
								currentDate = event.getDate(Event.END, 0);
							if (currentDate > endDate)
								return;
							checkIsInRange(event, recHandle);
						}
						if (!events.empty() && initialEventOnly)
							return;
					}
					while (recHandles.length != 0);
				}
				catch (PIMException e) {
					// Do nothing
					// If an error occurs the method hasMoreElements will return false. 
				}
			}
			
			/*
			 * Checks if the given event is between startDate and endDate.
			 */
			private boolean checkIsInRange(Event event, int recHandle) {
				long start = -1;
				long end = -1;
				// at least start or end date.
				if (event.countValues(Event.START) != 0)
					start = event.getDate(Event.START, 0);
				else {
					start = event.getDate(Event.END, 0);
					end = start + 1; // duration: 1ms
				}
				if (event.countValues(Event.END) != 0 && end == -1)
					end = event.getDate(Event.END, 0);
				else
					end = start + 1;
								
				if (initialEventOnly)
					return checkIsInRange(event, start, end, start, recHandle);
				RepeatRule rule = event.getRepeat();
				if (rule == null)
					return checkIsInRange(event, start, end, start, recHandle);
				Enumeration dates = rule.dates(currentDate, currentDate, endDate);
				while (dates.hasMoreElements()) {
					long date = ((Date) dates.nextElement()).getTime();
					if (date > endDate)
						return false;
					if (checkIsInRange(event, start, end, date, recHandle))
						return true;
				}
				return false;
			}
			
			/*
			 * Checks if date is between startDate and endDate.
			 */
			private boolean checkIsInRange(Event event, long start, long end, long date, int recHandle) {
				if (start == end) { // All day events
					start = DateHelper.setTime(start, 0, 0, 0, 0); // no time (set to zero).
					end += (1000*60*60*24 - 1); // last milliseconds of the day.
				}
				switch (searchType) {
					case EventList.STARTING:
						if (date <= endDate && date >= startDate) {
							events.push(date, event, recHandle);
							return true;
						}
						break;
					case EventList.ENDING:
						long longDate = date + end - start;
						if (longDate <= endDate && longDate >= startDate) {
							events.push(date, event, recHandle);
							return true;
						}
						break;
					case EventList.OCCURRING: 
						if (date < endDate && (date + end - start) > startDate) {
							events.push(date, event, recHandle);
							return true;
						}
						break;
				}
				return false;
			}
		};
	}
	
	/**
	 * @see javax.microedition.pim.EventList#getSupportedRepeatRuleFields(int)
	 */
	public int[] getSupportedRepeatRuleFields(int frequency) {
		if(frequency != RepeatRule.DAILY && frequency != RepeatRule.WEEKLY &&
		   frequency != RepeatRule.MONTHLY && frequency != RepeatRule.YEARLY)
		    throw new IllegalArgumentException("Invalid frequency specified");  
		return getSupportedRepeatRuleFieldsN(frequency);
	}
}