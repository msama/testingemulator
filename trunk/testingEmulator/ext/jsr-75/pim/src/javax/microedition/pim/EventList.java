package javax.microedition.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */
 
import java.util.Enumeration;

/**
 * Represents an Event list containing Event items.
 * <P>
 * An Event List is responsible for determining which of the fields
 * from an Event are retained when an Event is persisted into the List.
 * An Event List does not have to retain all of the fields in an Event
 * when the Event is persisted into the List.
 * See the Event interface for a description of the fields available
 * for a specific Event.  The fields that are supported by a particular
 * Event List can be queried through the method {@link #isSupportedField}.
 * If a field ID that is not in the Event
 * interface is provided as the parameter to the {@link #isSupportedField}
 * method, a <code>java.lang.IllegalArgumentException</code> is thrown.
 * </P>
 * <h3>Inherited Method Behavior</h3>
 * <P>
 * An EventList only accepts objects implementing the Event interface as
 * a parameter to {@link #items(PIMItem)}).  A
 * <code>java.lang.IllegalArgumentException</code> is thrown by this method if
 * the input parameter does not implement the Event interface. </P>
 * <P>
 * Enumerations returned by {@link #items()}, {@link #items(PIMItem)},
 * and {@link #items(int, long, long, boolean)} contain only objects
 * implementing an Event interface.</P>
 *
 * @see Event
 * @since PIM 1.0
 */

public interface EventList extends PIMList {

    /**
     * Constant representing a search type for Events based on the event
     * occurrence's specific start date/time.
     */
    public static final int STARTING = 0;

    /**
     * Constant representing a search type for Events based on the event
     * occurrence's specific end date/time.
     */
    public static final int ENDING = 1;

    /**
     * Constant representing a search type for Events based on any occurrence
     * of an event during a time period.
     */
    public static final int OCCURRING = 2;

    /**
    * Factory method to create an Event for this event list.   The Event
    * is empty upon creation with none of its fields containing any data (i.e. a
    * call to the method <code>Event.getFields()</code> returns an array
    * of zero length). Even though it is initially empty, the Event is
    * <i>capable</i> of containing data for exactly those fields that this list
    * supports.
    * Note that creation of the Event does not add the Event to the list
    * from which the item was created; a specific call to
    * <code>PIMItem.commit()</code> must be made to commit the item and
    * its data to the list.
    *
    * @return   a new, empty Event object associated with this list.  However,
    *           the Event is still not persistent in the list until a call to
    *           <code>PIMItem.commit()</code> for the Event is made.
    */
    public abstract Event createEvent();

    /**
    * Imports the given Event into this list by making a new Event for the
    * list and filling its information with as much information as it can from
    * the provided Event.  If the input Event is already in the list,
    * a new Event is still created with information similar to the input item
    * (but not necessarily identical). </P>
    * <P>Note that not all data from the input Event may be supported in the
    * new Event due to field restrictions for the list instance.  In this
    * case, data fields not supported are not transferred to the new Event
    * object. </P>
    * <P>Also note that creation of the Event does not add the Event
    * to this list; a specific call to <code>PIMItem.commit()</code> must be
    * made to commit the item and its data to the list. </P>
    *
    * @param    item the Event to import into the list.
    * @return   a newly created Event.
    * @throws	NullPointerException If the <code>item</code> is
    *           <code>null</code>.
    */
    public abstract Event importEvent(Event item);

    /**
    * Removes a specific Event from the list.  The item must already
    * exist in the list for this method to succeed.
    *
    * @param    item	the Event to be removed from the list.
    * @throws 	PIMException If an error occurs deleting the item
    *           or the list is no longer accessible or closed.
    * @throws	NullPointerException If <code>item</code> is
    *           <code>null</code>.
    * @throws	java.lang.SecurityException if the application is not given
    *           permission to write to the Event list or the list is opened
    *           READ_ONLY.
    */
    public abstract void removeEvent(Event item) throws PIMException;

    /**
     * Return an enumeration of all the Events where at least one of the Event's
     * occurrences falls in the specified range from startDate to
     * endDate inclusive. The search type specified determines the criteria for
     * matching an event occurrence; <code>STARTING</code> searches for events
     * starting between startDate and endDate, <code>ENDING</code> searches for
     * events ending between startDate and endDate, and <code>OCCURRING</code>
     * searches for events that have an part of the event occurring during the
     * period specified by startDate and endDate.  The returned Events are
     * sorted in ascending order by the starting date-timestamp of the event's 
     * occurrence.  An Event is returned in the Enumeration only once if the 
     * event occurs more than once during the specified time interval.
     *
     * @param   searchType the criteria used to determine which Events are
     *          returned. Must be {@link #STARTING}, {@link #ENDING}, or
     *          {@link #OCCURRING}.
     * @param   startDate the inclusive start date to begin looking for event
     *          occurrences on or after this date, returned in milliseconds
     *          since the epoch (00:00:00 GMT, January 1, 1970)
     * @param   endDate the inclusive stop date to end looking for events beyond
     *          this date, returned in  milliseconds since the epoch
     *          (00:00:00 GMT, January 1, 1970)
     * @param   initialEventOnly true indicates only search based on an Event's
     *          START and END values and do not calculate repeating occurrences
     *          of the event.  False indicates repeating occurrences of
     *          an Event are included during the search.
     * @return  an enumeration of all the Events with an occurrence
     *          between startDate and endDate
     * @throws  java.lang.IllegalArgumentException if searchType is not
     *          {@link #STARTING}, {@link #ENDING}, or {@link #OCCURRING}, or
     *			<code>startDate</code> is greater than <code>endDate</code>.
     * @throws 	PIMException If the operation is unsupported, an error occurs,
     *          or the list is no longer accessible or closed.
     * @throws	java.lang.SecurityException if the application is not given
     *          permission to read the Event list or the list is opened
     *          WRITE_ONLY.
     */
    public abstract Enumeration items(int searchType, long startDate, long endDate,
                                      boolean initialEventOnly)
        throws PIMException;


	/**
	 * Returns the Repeat Rule fields that are settable by the class user and
	 * supported by this EventList for the provided Repeat Rule frequency.  
	 * Repeat Rule frequencies are {@link RepeatRule#YEARLY}, 
	 * {@link RepeatRule#MONTHLY}, {@link RepeatRule#WEEKLY}, and 
	 * {@link RepeatRule#DAILY}. The int values
	 * in the array represent a bitwise combination of Repeat Rule fields that
	 * are valid for the given frequency.  For example,
	 * if <code>MONTHLY</code> was the frequency and this event list supported
	 * specifying monthly repeat rules by either the day of the month or by
	 * a day in a specific week (e.g. Thursday of the 3rd week in the month), 
	 * then the returned int array would contain two elements with one value
	 * being <code>DAY_IN_MONTH</code> and the other being 
	 * <code>DAY_IN_WEEK | WEEK_IN_MONTH</code>.  All possible field combinations
	 * that are valid are returned in the array.  
	 * </P><P>
	 * If the given frequency
	 * is not supported by this list, a zero length array is returned.  If the
	 * frequency is supported but no fields 
	 * are supported by this list for that frequency, a one item array 
	 * containing the integer 0 is returned.  In these conditions, any field
	 * that has a value set will be ignored by this event list.
	 *
	 * @param	frequency Repeat Rule frequency for which the supported fields
	 *			are queried.
     * @return  an array of integer values corresponding to the Repeat Rule
	 *			fields that are supported by this list for the given frequency.	 
     * @throws  java.lang.IllegalArgumentException if frequncy is not
     *          {@link RepeatRule#YEARLY}, {@link RepeatRule#MONTHLY}, 
	 * 			{@link RepeatRule#WEEKLY}, or {@link RepeatRule#DAILY}.
	 * @see		RepeatRule
	 */
	public abstract int[] getSupportedRepeatRuleFields(int frequency) ;

}