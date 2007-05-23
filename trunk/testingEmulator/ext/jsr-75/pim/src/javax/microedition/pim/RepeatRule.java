package javax.microedition.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

import com.ibm.oti.pim.DateHelper;
import com.ibm.oti.pim.PIMUtil;
import com.ibm.oti.pim.RepeatDateEnumeration;

/**
 * Represents a description for a repeating pattern for an Event item.
 * The fields are a subset of the capabilities of the RRULE field in VEVENT
 * defined by the vCalendar 1.0 specification from the Internet Mail Consortium
 * (http://www.imc.org). It is use to determine how often an associated Event
 * occurs.
 * </P><P>
 * The fields of a Repeat Rule can conceptually be grouped into two categories:
 * <ul>
 * 		<li>Frequency of a Repeat Rule</li>
 *		<li>Fields that modify or refine the Frequency of a Repeat Rule</li>
 * </ul>
 * This means that a Repeat Rule's calculation of applicable dates  start with 
 * a repeating frequency (such as weekly, daily, yearly, or monthly) and then
 * other fields refine or modify the repeat characteristics according to the
 * field (e.g. the COUNT field specifies that
 * only X repeat occurrences happen at the given frequency).  The first category
 * contains only <code>FREQUENCY</code>, while all other repeat rule fields are
 * classified in the second category.  This classification of the fields aids in
 * understanding of the relationship of the fields and allows for a method to
 * query for supported fields (see {@link EventList#getSupportedRepeatRuleFields}).
 * </P><P>
 * A repeat rule typically needs to have its frequency set first and foremost.  
 * The following table shows the valid values for the frequency fields that can 
 * be set in RepeatRule:</P>
 * <table BORDER=1>
 * <TR>
 * <th> Fields </th><th> Set Method </th><th> Valid Values </th>
 * </tr>
 * <tr><td><code>FREQUENCY</code></td>
 *     <td><code>setInt</code></td>
 *     <td><code>DAILY, WEEKLY, MONTHLY, YEARLY</td>
 * </tr>
 * </table>
 *
 * <P>
 * The following table shows the valid values for the fields that modify or refine
 * the frequency of a RepeatRule:</P>
 * <table BORDER=1>
 * <TR>
 * <th> Fields </th><th> Set Method </th><th> Valid Values </th>
 * </tr>
 * <tr><td><code>COUNT</code></td>
 *     <td><code>setInt</code></td>
 *     <td>any positive int</td>
 * </tr>
 * <tr><td><code>INTERVAL</code></td>
 *     <td><code>setInt</code></td>
 *     <td>any positive int</td>
 * </tr>
 * <tr><td><code>END</code></td>
 *     <td><code>setDate</code></td>
 *     <td>any valid Date</td>
 * </tr>
 * <tr><td><code>MONTH_IN_YEAR</code></td>
 *     <td><code>setInt</code></td>
 *     <td><code>JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST,
 *          SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER</code> (Note: that these are
 *          constants defined in the RepeatRule class and are not the same
 *          as those in the Calendar class)</td>
 * </tr>
 * <tr><td><code>DAY_IN_WEEK</code></td>
 *     <td><code>setInt</code></td>
 *     <td><CODE>SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
 *          </CODE>(Note: that these are
 *          constants defined in the RepeatRule class and are not the same
 *          as those in the Calendar class)</td>
 * </tr>
 * <tr><td><code>WEEK_IN_MONTH</code></td>
 *     <td><code>setInt</code></td>
 *     <td><CODE>FIRST, SECOND, THIRD, FOURTH, FIFTH, LAST, SECONDLAST,
 *          THIRDLAST, FOURTHLAST, FIFTHLAST</CODE></td>
 * </tr>
 * <tr><td><code>DAY_IN_MONTH</code></td>
 *     <td><code>setInt</code></td>
 *     <td>1-31</td>
 * </tr>
 * <tr><td><code>DAY_IN_YEAR</code></td>
 *     <td><code>setInt</code></td>
 *     <td>1-366</td>
 * </tr>
 * </table>
 *
 * <h3>Examples</h3>
 * <P>The following examples demonstrate some possible repeat values.</P>
 *
 * To specify the associated event occurs every day: <br>
 * <pre><code>
 *      setInt(RepeatRule.FREQUENCY, RepeatRule.DAILY);</code></pre>
 *
 * To specify the associated event occurs every day for the next five days: <br>
 * <pre><code>
 *      setInt(RepeatRule.FREQUENCY, RepeatRule.DAILY);
 *      setInt(RepeatRule.COUNT, 5);</code></pre>
 *
 * To specify this event occurs every week on Monday and Tuesday: <br>
 * <pre><code>
 *      setInt(RepeatRule.FREQUENCY, RepeatRule.WEEKLY);
 *      setInt(RepeatRule.DAY_IN_WEEK, RepeatRule.MONDAY | RepeatRule.TUESDAY);</code></pre>
 *
 * To specify the associated event occurs every third week on Friday: <br>
 * <pre><code>
 *      setInt(RepeatRule.FREQUENCY, RepeatRule.WEEKLY);
 *      setInt(RepeatRule.INTERVAL, 3);
 *      setInt(RepeatRule.DAY_IN_WEEK, RepeatRule.FRIDAY);</code></pre>
 *
 * To specify the associated event occurs every month on the Wednesday of the
 * second week until the end of the current year: <br>
 * <pre><code>
 *      setInt(RepeatRule.FREQUENCY, RepeatRule.MONTHLY);
 *      setInt(RepeatRule.WEEK_IN_MONTH, RepeatRule.SECOND);
 *      setInt(RepeatRule.DAY_IN_WEEK, RepeatRule.WEDNESDAY);
 *      java.util.Calendar cal = Calendar.getInstance();
 *      cal.set(Calendar.MONTH, Calendar.DECEMBER);
 *      cal.set(Calendar.DAY_OF_MONTH, 31);
 *      cal.set(Calendar.AM_PM, Calendar.PM);
 *      cal.set(Calendar.HOUR_OF_DAY, 23);
 *      cal.set(Calendar.MINUTE, 59);
 *      setDate(RepeatRule.END, cal.getTime().getTime());</code></pre>
 *
 * To specify the associated event occurs every year on the Sunday of the
 * second week in May: <br>
 * <pre><code>
 *      setInt(RepeatRule.FREQUENCY, RepeatRule.YEARLY);
 *      setInt(RepeatRule.MONTH_IN_YEAR, RepeatRule.MAY);
 *      setInt(RepeatRule.WEEK_IN_MONTH, RepeatRule.SECOND);
 *      setInt(RepeatRule.DAY_IN_WEEK, RepeatRule.SUNDAY);</code></pre>
 *
 * To specify the associated event occurs every year on the 4th of July: <br>
 * <pre><code>
 *      setInt(RepeatRule.FREQUENCY, RepeatRule.YEARLY);
 *      setInt(RepeatRule.MONTH_IN_YEAR, RepeatRule.JULY);
 *      setInt(RepeatRule.DAY_IN_MONTH, 4);</code></pre>
 *
 * To specify the associated event occurs every year on the first day: <br>
 * <pre><code>      
 *      setInt(RepeatRule.FREQUENCY, RepeatRule.YEARLY);
 *      setInt(RepeatRule.DAY_IN_YEAR, 1);</code></pre>
 *
 * To check if a particular Repeat Rule frquency value is supported for events 
 * for a certain event list:<br>
 * <pre><code>
 *      // Check if RepeatRule.DAILY is supported in the default event list
 * 
 *      EventList el = PIM.openPIMList(PIM.EVENT_LIST, PIM.READ_WRITE);
 *      int[] supported_fields = el.getSupportedRepeatRuleFields(RepeatRule.DAILY);
 *      if (supported_fields.length > 0) {
 *          System.out.println("RepeatRule.DAILY is supported in default event list");
 *      }
 *			</code></pre>
 *
 * To check if a particular Repeat Rule field is supported for events for a certain
 * event list:<br>
 * <pre><code>
 *      // Check if RepeatRule.INTERVAL is supported for DAILY frequency events
 * 
 *      EventList el = PIM.openPIMList(PIM.EVENT_LIST, PIM.READ_WRITE);
 *      int[] supported_fields = el.getSupportedRepeatRuleFields(RepeatRule.DAILY);
 *      int i = 0;
 *      while (i < supported_fields.length)
 *      if (supported_fields[i] & RepeatRule.INTERVAL != 0) {
 *          System.out.println("INTERVAL supported in default event list");
 *          break;
 *      }
 *			</code></pre>
 *
 * @since PIM 1.0
 */
public class RepeatRule extends Object {

    /**
     * Field specifying the frequency of the Repeat.
     * This field has a value of either <code>DAILY</code>,
     * <code>WEEKLY</code>, <code>MONTHLY</code> or <code>YEARLY</code>.
     * The default data value associated with this field in RepeatRule
     * is <code>DAILY</code>. This field can
     * be checked for support by {@link EventList#getSupportedRepeatRuleFields}.  
     */
    public static final int FREQUENCY = 0;

    /**
     * Field specifying the day of the month an Event occurs; for example, 15.
     * This value is 1 based from the first day of the month.  This field can
     * be checked for support in the bit array values returned by
     * {@link EventList#getSupportedRepeatRuleFields}.  
     * 
     */
    public static final int DAY_IN_MONTH = 1;

    /**
     * Field specifying the days of the week an Event occurs.
     * To set multiple days, OR the values together
     * (e.g. <code>MONDAY | THURSDAY</code>).  Retrieval of
     * data for this field can contain multiple days OR'd together in the
     * same manner as setting the value. This field can
     * be checked for support in the bit array values returned by
     * {@link EventList#getSupportedRepeatRuleFields}. 
     */
    public static final int DAY_IN_WEEK = 2;

    /**
     * Field specifying the day of the year an Event occurs; for example, 134.
     * This value is 1 based from the first day of the beginning of the year.
     * This field can
     * be checked for support in the bit array values returned by
     * {@link EventList#getSupportedRepeatRuleFields}. 
     */
    public static final int DAY_IN_YEAR = 4;

     /**
     * Field specifying the month in which an event occurs.
     * To set multiple months, OR the values together
     * (e.g. <code>RepeatRule.JANUARY | RepeatRule.FEBRUARY </code>).  Retrieval
     * of data for this field can contain multiple months OR'd together in the
     * same manner as setting the value. This field can
     * be checked for support in the bit array values returned by
     * {@link EventList#getSupportedRepeatRuleFields}. 
     */
    public static final int MONTH_IN_YEAR = 8;

    /**
     * Field specifying which week in a month a particular event occurs.
     * To set multiple weeks, OR the values together
     * (e.g. <code>FIRST | LAST | SECOND | SECONDLAST</code>).  Retrieval of
     * data for this field can contain multiple weeks OR'd together in the
     * same manner as setting the value. This field can
     * be checked for support in the bit array values returned by
     * {@link EventList#getSupportedRepeatRuleFields}. 
     */
    public static final int WEEK_IN_MONTH = 16;

     /**
     * Field specifying the number of times this event repeats including the
     * first time, starting from the first time the event starts (derived from
     * <code>Event.START</code>) and continuing to the last date of
     * the repeat (defined by <code>RepeatRule.END</code>).
     * <code>COUNT</code> controls the number of times the event occurs during
     * the period and is used with RepeatRule interval and the frequency to
     * calculate when the event occurs.  <code>RepeatRule.END</code>
     * overrides this data if the end is reached prior to the count finishing.
     * If <CODE>COUNT</CODE> is 0 and <CODE>END</CODE> is <code>null</code>,
     * the event repeats forever. This field can
     * be checked for support in the bit array values returned by
     * {@link EventList#getSupportedRepeatRuleFields}. 
     */
    public static final int COUNT = 32;

  /**
     * Field specifying the ending date of the repeating event.  Data for this
     * field is expressed in the same long value format as java.util.Date,
     * which is milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
     *  This field can
     * be checked for support in the bit array values returned by
     * {@link EventList#getSupportedRepeatRuleFields}. 
     */
    public static final int END = 64;

    /**
     * Field specifying the number of iterations of the frequency between
     * occurring dates, or how often the frequency repeats.
     * For example, for every other day the <code>FREQUENCY</code> is
     * <code>DAILY</code> and <code>INTERVAL</code> is 2.
     * The default value for data associated with this field is 1. 
     * This field can
     * be checked for support in the bit array values returned by
     * {@link EventList#getSupportedRepeatRuleFields}. 
     */
    public static final int INTERVAL = 128;

    /**
     * Used for frequency when the Event happens every day.
     */
    public static final int DAILY = 0x10;

    /**
     * Used for frequency when the Event happens every week.
     */
    public static final int WEEKLY = 0x11;

    /**
     * Used for frequency when the Event happens every month.
     */
    public static final int MONTHLY = 0x12;

    /**
     * Used for frequency when the Event happens every year.
     */
    public static final int YEARLY = 0x13;

    /**
     * Constant for the first week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int FIRST = 0x1;

    /**
     * Constant for the second week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int SECOND = 0x2;

    /**
     * Constant for the third week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int THIRD = 0x4;

    /**
     * Constant for the fourth week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int FOURTH = 0x8;

    /**
     * Constant for the fifth week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int FIFTH = 0x10;

    /**
     * Constant for the last week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int LAST = 0x20;

    /**
     * Constant for the second to last week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int SECONDLAST = 0x40;

    /**
     * Constant for the third to last week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int THIRDLAST = 0x80;

    /**
     * Constant for the fourth to last week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int FOURTHLAST = 0x100;

    /**
     * Constant for the fifth to last week of the month used with
     * <code>WEEK_OF_MONTH</code> field.
     */
    public static final int FIFTHLAST = 0x200;

    /**
     * Constant for the day of week Saturday used with
     * <code>DAY_IN_WEEK</code> field.
     */
    public static final int SATURDAY = 0x400;

    /**
     * Constant for the day of week Friday used with
     * <code>DAY_IN_WEEK</code> field.
     */
    public static final int FRIDAY = 0x800;

    /**
     * Constant for the day of week Thursday used with
     * <code>DAY_IN_WEEK</code> field.
     */
    public static final int THURSDAY = 0x1000;

    /**
     * Constant for the day of week Wednesday used with
     * <code>DAY_IN_WEEK</code> field.
     */
    public static final int WEDNESDAY = 0x2000;

    /**
     * Constant for the day of week Tuesday used with
     * <code>DAY_IN_WEEK</code> field.
     */
    public static final int TUESDAY = 0x4000;

    /**
     * Constant for the day of week Monday used with
     * <code>DAY_IN_WEEK</code> field.
     */
    public static final int MONDAY = 0x8000;

    /**
     * Constant for the day of week Sunday used with
     * <code>DAY_IN_WEEK</code> field.
     */
    public static final int SUNDAY = 0x10000;

     /**
     * Constant for the month of January used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int JANUARY = 0x20000;

    /**
     * Constant for the month of February used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int FEBRUARY = 0x40000;

    /**
     * Constant for the month of March used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int MARCH = 0x80000;

    /**
     * Constant for the month of April used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int APRIL = 0x100000;

    /**
     * Constant for the month of May used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int MAY = 0x200000;

    /**
     * Constant for the month of June used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int JUNE = 0x400000;

    /**
     * Constant for the month of July used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int JULY = 0x800000;

    /**
     * Constant for the month of August used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int AUGUST = 0x1000000;

    /**
     * Constant for the month of September used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int SEPTEMBER = 0x2000000;

    /**
     * Constant for the month of October used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int OCTOBER = 0x4000000;

    /**
     * Constant for the month of November used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int NOVEMBER = 0x8000000;

    /**
     * Constant for the month of December used with
     * <code>MONTH_IN_YEAR</code> field.
     */
    public static final int DECEMBER = 0x10000000;

	/**
	 * Data type INT (all fields except {@link #END}).
	 */
	private static final int INT = 0;
	
	/**
	 * Data type DATE (only {@link #END});
	 */
	private static final int DATE = 1;

	/**
	 * The exception dates.
	 */
	private Vector exceptDates;
	
	/**
	 * Repeat fields' internal representation.
	 * <p>
	 * <table border=1>
	 * <tr>
	 * <th> index </th>
	 * <th> field ID </th>
	 * </tr>
	 * <tr><td>0</td><td>{@link #COUNT}</td></tr>
	 * <tr><td>1</td><td>{@link #DAY_IN_MONTH}</td></tr>
	 * <tr><td>2</td><td>{@link #DAY_IN_WEEK}</td></tr>
	 * <tr><td>3</td><td>{@link #DAY_IN_YEAR}</td></tr>
	 * <tr><td>4</td><td>{@link #END}</td></tr>
	 * <tr><td>5</td><td>{@link #FREQUENCY}</td></tr>
	 * <tr><td>6</td><td>{@link #INTERVAL}</td></tr>
	 * <tr><td>7</td><td>{@link #MONTH_IN_YEAR}</td></tr>
	 * <tr><td>8</td><td>{@link #WEEK_IN_MONTH}</td></tr>
	 * </table>
	 */
	private long[] fields = {-1, -1, -1, -1, -1, -1, -1, -1, -1};


    /**
     * Default constructor.
     */
	public RepeatRule() {
		exceptDates = new Vector();
	}

    /**
     * Returns an Enumeration of dates on which an Event would occur.  A start
     * date is specified form which the repeating rule is applied to generate
     * dates.  Then a beginning date and a start date is also provided to
     * return only a subset of all possible occurrences of an Event within the
     * given timeframe.  The sequence of the items is by date.  Exceptional dates
     * are not included in the returned Enumeration. <BR>
     * For example, an Event may happen every Monday during a year starting on
     * January 1st.  However, one wants to know occurrences of the Event during
     * the month of June only.  The startDate parameter specifies the anchor point
     * for the Event from which it begins repeating, and the subsetBeginning and
     * subsetEnding parameters would limit the Events returned to those only in
     * June in this example.
     *
     * @param    startDate the start date for the sequence, from which the repeat
     *           rule is applied to generate possible occurrence dates.  This
     *           value must be expressed
     *           in the same long value format as java.util.Date, which is
     *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
     * @param    subsetBeginning the beginning date of the period for which events
     *           should be returned. This value must be expressed
     *           in the same long value format as java.util.Date, which is
     *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
     * @param    subsetEnding the end date of the period for which events should
     *           be returned. This value must be expressed
     *           in the same long value format as java.util.Date, which is
     *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
     * @return   an Enumeration of dates for the given parameters, with the
     *           Enumeration containing <code>java.util.Date</code> instances.
     * @throws   IllegalArgumentException if <code>beginning</code> is greater
     *           than <code>ending</code>.
     */
	public Enumeration dates(long startDate, long subsetBeginning, long subsetEnding) {
		if (subsetBeginning > subsetEnding)
			throw new IllegalArgumentException("The subset ending must occur after the subset beginning");		
		return new RepeatDateEnumeration(this, startDate, subsetBeginning, subsetEnding);
	}

    /**
     * Add a Date for which this RepeatRule should not occur. This value may 
     * be rounded off to the date only from a date time stamp if the 
     * underlying platform implementation only supports date fields with dates 
     * only and not date time stamps.
     *
     * @param    date the date to add to the list of except dates, expressed
     *           in the same long value format as java.util.Date, which is
     *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
     */
	public void addExceptDate(long date) {
		Date newDate = new Date(date);
		for (int i = 0; i < exceptDates.size(); i++) {
			// make sure the new exception date is not already contained.
			if (isSameDay((Date)exceptDates.elementAt(i), newDate))
				return;
		}
		exceptDates.addElement(newDate);
	}
	 
	/**
	 * Checks if the given dates occur the same day.
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	private boolean isSameDay(Date date1, Date date2) {
		long longDate1 = date1.getTime();
		long longDate2 = date2.getTime();
		if (Math.abs((long)(longDate1 - longDate2)) > 1000*60*60*24)
			return false;
		if (DateHelper.getDayInMonth(longDate1) != DateHelper.getDayInMonth(longDate2))
			return false;
		return true;
	}

    /**
     * Remove a Date for which this RepeatRule should not occur. If the
     * date was in the list of except dates, it is removed.
     *
     * @param    date the date to remove from the list of except dates expressed
     *           in the same long value format as java.util.Date, which is
     *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
     */
	public void removeExceptDate(long date) {
		exceptDates.removeElement(new Date(date));
	}

    /**
     * Returns the Dates for which this RepeatRule should not occur.
     *
     * @return   an Enumeration of dates for which this RepeatRule should not
     *           occur, with the
     *           Enumeration containing <code>java.util.Date</code> instances.
     */
	public Enumeration getExceptDates() {
		return new Enumeration() {
			
			private int index = 0;
			
			/**
			 * @see java.util.Enumeration#hasMoreElements()
			 */
			public boolean hasMoreElements() {
				return index < exceptDates.size();
			}

			/**
			 * @see java.util.Enumeration#nextElement()
			 */
			public Object nextElement() {
				if(index >= exceptDates.size())
					throw new NoSuchElementException();
				
				Object next = exceptDates.elementAt(index);
				index++;
				return next;
			}
		};
	}

    /**
     * Retrieves an integer field. The field values can be one of <code>COUNT,
     * DAY_IN_MONTH, FREQUENCY, INTERVAL, MONTH_IN_YEAR, WEEK_IN_MONTH,
     * DAY_IN_WEEK, DAY_IN_YEAR</code>.  {@link #getFields} should be checked
     * prior to invoking the method to ensure the field has a value associated
     * with it.
     *
     * @param    field The field to get, for example <code>COUNT</code>.
     * @return   an int representing the value of the field.
     * @throws   IllegalArgumentException if field is not one of the
     *           the valid RepeatRule fields for this method.
     * @throws   FieldEmptyException if the field does is a valid integer field
     *           but does not have any data values assigned to it.
     */
	public int getInt(int field) {
		checkForException(field, INT);
		return (int) getFieldValue(field);
	}

    /**
     * Sets an integer field. The field value can be one
     * of <code>COUNT, DAYNUMBER, FREQUENCY, INTERVAL, MONTH_IN_YEAR,
     * WEEK_IN_MONTH, DAY_IN_WEEK, DAY_IN_YEAR</code>.
     *
     * @param    field The field to set, for example <code>COUNT</code>.
     * @param    value The value to set the field to.
     * @throws   IllegalArgumentException if field is not one of the
     *           the valid RepeatRule fields for this method, or the value
     *           provided is not a valid value for the given field.
     */
	public void setInt(int field, int value) {
		checkForExceptions(field, INT, value);
		fields[PIMUtil.getRepeatFieldIndexFromID(field)] = value;
	}

    /**
     * Retrieves a Date field. The field value is currently limited to
     * <code>END</code>.  {@link #getFields} should be checked prior to
     * invoking the method to ensure the field has a value associated with it.
     *
     * @param   field The field to get.
     * @return  a Date representing the value of the field, expressed
     *           in the same long value format as java.util.Date, which is
     *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
     * @throws  IllegalArgumentException if field is not one of the
     *          the valid RepeatRule fields for this method.
     * @throws  FieldEmptyException if the field does is a valid date field
     *          but does not have any data values assigned to it.
     */
	public long getDate(int field) {
		checkForException(field, DATE);
		return getFieldValue(field);
	}

    /**
     * Sets a Date field. The field value is currently limited to
     * <code>END</code>.  This field may be rounded off to the date only
     * from a date time stamp if the underlying platform implementation
     * only supports date fields with dates only and not date time stamps.
     *
     * @param   field The field to set.
     * @param   value The value to set the field to, expressed
     *           in the same long value format as java.util.Date, which is
     *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
     * @throws  IllegalArgumentException if field is not one of the
     *          the valid RepeatRule fields for this method.
     */
	public void setDate(int field, long value) {
		checkForExceptions(field, DATE, value);
		fields[PIMUtil.getRepeatFieldIndexFromID(field)] = value;
	}

    /**
     * Returns a list of fields that currently have values assigned to it.  If
     * a field is not "set", the field is not included in the return value.
     *
     * @return  an array of fields that have values currently assigned to them.
     *          If no fields have values set, an array of zero length is
     *          returned.
     */
	public int[] getFields() {
		int length = 0;
		int current = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] != -1)
				length++;
		}
		int[] fieldsSet = new int[length];
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] != -1) {
				fieldsSet[current] = PIMUtil.getRepeatFieldIDFromIndex(i);
				current++;
			}
		}
		return fieldsSet;
	}
	
	/**
	 * Answers the value set for the field <code>field</code>.
	 * @param field
	 * @return long
	 */
	private long getFieldValue(int field) {
		long value = fields[PIMUtil.getRepeatFieldIndexFromID(field)];
		if (value == -1)
			throw new FieldEmptyException("", field);
		return value;
	}
	
	/**
	 * Answers the value set for the field <code>field</code>
	 * with the valid data type.
	 * @param field
	 * @param dataType {@link #INT} or {@link #DATE}.
	 */
	private void checkForException(int field, int dataType) {
		if (getDataType(field) != dataType)
			throw new IllegalArgumentException("Invalid data type");
	}
	
	/**
	 * Checks the field is valid and the data type 
	 * ({@link #INT} or {@link #DATE}) is correct.
	 * @param field
	 * @return int
	 */
	private int getDataType(int field) {
		switch (field) {
			case FREQUENCY:
			case COUNT:
			case INTERVAL:
			case DAY_IN_MONTH:
			case DAY_IN_WEEK:
			case DAY_IN_YEAR:
			case WEEK_IN_MONTH:
			case MONTH_IN_YEAR:
				return INT;
			case END:
				return DATE;
			default:
				StringBuffer buffer = new StringBuffer(50);
				buffer.append("The field ");
				buffer.append(field);
				buffer.append(" is not a valid RepeatRule field");
				throw new IllegalArgumentException(buffer.toString());
		}
	}
	
	/**
	 * Checks the field and its values are valid.
	 * @param field
	 * @param dataType {@link #INT} or {@link #DATE}.
	 * @param value
	 */
	private void checkForExceptions(int field, int dataType, long value) {
		// first, tests the field is valid
		checkForException(field, dataType);
		boolean throwExcept = false;
		int flag = 0;
		switch(field) {
			case FREQUENCY:
				if (value < DAILY || value > YEARLY)
					throwExcept = true;
				break;
			case COUNT:
			case INTERVAL:
				if (value <= 0)
					throwExcept = true;
				break;
			case DAY_IN_MONTH:
				if (value < 1 || value > 31)
					throwExcept = true;
				break;
			case DAY_IN_WEEK:
				flag = ~(MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY);
				if ((value & flag) != 0 || value == 0)
					throwExcept = true;
				break;
			case WEEK_IN_MONTH:
				flag = ~(FIRST|SECOND|THIRD|FOURTH|FIFTH|LAST|SECONDLAST|THIRDLAST|FOURTHLAST|FIFTHLAST);
				if ((value & flag) != 0 || value == 0)
					throwExcept = true;
				break;
			case MONTH_IN_YEAR:
				flag = ~(JANUARY|FEBRUARY|MARCH|APRIL|MAY|JUNE|JULY|AUGUST|SEPTEMBER|OCTOBER|NOVEMBER|DECEMBER);
				if ((value & flag) != 0 || value == 0)
					throwExcept = true;
				break;
			case DAY_IN_YEAR:
				if (value < 1 || value > 366)
					throwExcept = true;
				break;
		}
		if (throwExcept)
			throw new IllegalArgumentException("The value is not valid for the repeat field " + field);
	}
	
	/**
	 * Compares this RepeatRule with a given RepeatRule for content equality. 
	 * For RepeatRules, dates are considered equal if one or both of the dates 
	 * compared contains a date only with no timestamp and the date values are
	 * equal regardless of the time qualifier.  This rule accounts for platform
	 * dependent rounding off of dates from date time stamps to dates only.  
	 * For example, a date value of 3/14/03 with no time stamp is considered
	 * equal to a date value of 3/14/03 with a time stamp.  If the application
	 * requires that dates be exactly equal, comparisons should be made 
	 * explicitly outside of this method.
	 *
	 * @param	obj another RepeatRule object to compare against
	 * @return	true if the contents of the RepeatRules are equivalent, false
	 *			otherwise.
	 */
	public boolean equals(Object o) {
		if(!(o instanceof RepeatRule))
			return false;
		RepeatRule r = (RepeatRule)o;

		for(int i = 0; i<fields.length; i++)
			if(i != PIMUtil.getRepeatFieldIndexFromID(END) && fields[i] != r.fields[i])
				return false;

		long longDate1 = fields[PIMUtil.getRepeatFieldIndexFromID(END)];
		long longDate2 = r.fields[PIMUtil.getRepeatFieldIndexFromID(END)];
		
		if (longDate1 == -1 ^ longDate2 == -1)
			return false;

		if (Math.abs((long)(longDate1 - longDate2)) > 1000*60*60*24)
			return false;
			
		if (DateHelper.getDayInMonth(longDate1) != DateHelper.getDayInMonth(longDate2))
			return false;
			
		if (exceptDates.size() != r.exceptDates.size())
			return false;
			
		for(int i = 0; i<exceptDates.size(); i++) {
			boolean foundCurrMatchInSet = false;
			for(int j = 0; j<r.exceptDates.size() && !foundCurrMatchInSet; j++) {
				if(isSameDay((Date)exceptDates.elementAt(i),
							 (Date)r.exceptDates.elementAt(j)))
					foundCurrMatchInSet = true;
			}
			if(!foundCurrMatchInSet)
				return false;
		}
			
		return true;				
	}
}