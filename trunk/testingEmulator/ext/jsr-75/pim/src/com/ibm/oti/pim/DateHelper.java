package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.microedition.pim.PIMException;
import javax.microedition.pim.RepeatRule;


public class DateHelper {

	private static Calendar calendar = Calendar.getInstance();
	private static final int changeYear = 1582;
	private static final int[] DAYS_IN_YEAR = new int[] {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
	private static final int[] DAYS_IN_MONTH = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

	/**
	 * Answers the day of the month from the date time.
	 * @param time Number of milliseconds since Jan 1, 1970 GMT
	 * @return int A value between 1 and 31.
	 */
	public static int getDayInMonth(long time) {
		return toCalendarDate(new Date(time))[2];
	}
	
	/**
	 * Answers the day in the week from the date time.
	 * @param time Number of milliseconds since Jan 1, 1970 GMT
	 * @return int see constants defined in RepeatRule.java.
	 */
	public static int getDayInWeek(long time) {
		calendar.setTime(new Date(time));
		int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return 1 << (17 - dayInWeek);
	}
	
	/**
	 * Answers the day the year of the given date.
	 * @param time Number of milliseconds since Jan 1, 1970 GMT
	 * @return int A value between 1 and 366.
	 */
	public static int getDayInYear(long time) {
		int[] date = toCalendarDateAndTime(time);
		return DAYS_IN_YEAR[date[1]] + date[2] + (date[1] > 1 && isLeapYear(date[0]) ? 1 : 0);
	}
	
	/**
	 * Answers the week indexes from the date time.
	 * @param time Number of milliseconds since Jan 1, 1970 GMT
	 * @return int[] see constants defined in RepeatRule.java.
	 */	
	public static int[] getWeekInMonth(long time) {
		int[] date = toCalendarDate(new Date(time));
		int dayInMonth = date[2];
		int weekIndex = dayInMonth / 7;
		int numOfDay = DAYS_IN_MONTH[date[1]] + ((date[1] >= 1 && isLeapYear(date[0])) ? 1 : 0);
		int weekIndexFromLast = (numOfDay - dayInMonth + 1) / 7;
		if ((numOfDay - dayInMonth + 1) % 7 !=0)
			weekIndexFromLast++;
		if (dayInMonth % 7 != 0)
			weekIndex++;
		return new int[] {1 << (weekIndex - 1), 1 << 4 + weekIndexFromLast};
	}
	
	/**
	 * Answers the month in year of the date time.
	 * @param time Number of milliseconds since Jan 1, 1970 GMT
	 * @return int see constants defined in RepeatRule.java.
	 */
	public static int getMonthInYear(long time) {
		int monthInYear  = toCalendarDate(new Date(time))[1] -1;
		return 1 << (17 + monthInYear);
	}
	
	/**
	 * Set the time set with given values.
	 * @param date Number of milliseconds since Jan 1, 1970 GMT.
	 * @param hour between 0 and 23.
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return long. Number of milliseconds since Jan 1, 1970 GMT.
	 */
	public static long setTime(long date, int hour, int minute, int second, int millisecond) {
		int[] arrayDate = toCalendarDateAndTime(date);
		arrayDate[3] = hour;
		arrayDate[4] = minute;
		arrayDate[5] = second;
		arrayDate[6] = millisecond;
		set(arrayDate);
		return calendar.getTime().getTime();
	}
	
	/**
	 * Computes the repeatRule end date.
	 * @param repeatFields The repeat fields: [{@link RepeatRule#COUNT}, {@link RepeatRule#DAY_IN_MONTH}, 
	 * 		{@link RepeatRule#DAY_IN_WEEK}, {@link RepeatRule#DAY_IN_YEAR}, {@link RepeatRule#END}, 
	 * 		{@link RepeatRule#FREQUENCY}, {@link RepeatRule#INTERVAL}, {@link RepeatRule#MONTH_IN_YEAR}, 
	 * 		{@link RepeatRule#WEEK_IN_MONTH}]
	 * @param exceptionDates The exception dates of this repeat rule.
	 * @param start Number of milliseconds since Jan 1, 1970 GMT
	 * @return long Number of milliseconds since Jan 1, 1970 GMT
	 */
	public static long computeEndDate(long[] repeatFields, long[] exceptionDates, long start) {
		int count = (int)repeatFields[PIMUtil.COUNT];
		int[] dayInWeek;
		int[] weekInMonth;
		int[] monthInYear;
		int interval = (int)repeatFields[PIMUtil.INTERVAL];
		int[] startDate = toCalendarDateAndTime(start);
		int[] date = startDate;
		switch((int)repeatFields[PIMUtil.FREQUENCY]) {
			case RepeatRule.DAILY:
				for (int i = 0; i < count - 1; i++) {
					date = getNextDailyDate(interval, date);
					if (isAnExceptionDate(exceptionDates, date))
						i--;
				}
				break;
			case RepeatRule.WEEKLY:
				if (repeatFields[PIMUtil.DAY_IN_WEEK] != -1) {
					dayInWeek = daysToCalendarFormat((int)repeatFields[PIMUtil.DAY_IN_WEEK]);
					for (int i = 0; i < count - 1; i++) {
						date = getNextWeeklyDate(interval, date, dayInWeek);
						if (isAnExceptionDate(exceptionDates, date))
							i--;
					}
				}
				break;
			case RepeatRule.MONTHLY:
				if (repeatFields[PIMUtil.DAY_IN_WEEK] != -1 && repeatFields[PIMUtil.WEEK_IN_MONTH] != -1) {
					dayInWeek = daysToCalendarFormat((int)repeatFields[PIMUtil.DAY_IN_WEEK]);
					weekInMonth = toIndexFormat((int)repeatFields[PIMUtil.WEEK_IN_MONTH]);
					for (int i = 0; i < count - 1; i++) {
						date = getNextMonthlyByDayDate(interval, date, weekInMonth, dayInWeek);
						if (isAnExceptionDate(exceptionDates, date))
							i--;
					}
				}
				else {
					for (int i = 0; i < count - 1; i++) {
						date = getNextMonthlyByDateDate(interval, date, (int)repeatFields[PIMUtil.DAY_IN_MONTH]);
						if (isAnExceptionDate(exceptionDates, date))
							i--;
					}
				}
				break;
			case RepeatRule.YEARLY:
				if (repeatFields[PIMUtil.DAY_IN_YEAR] != -1) {
					for (int i = 0; i < count - 1; i++) {
						date = getNextYearlyByDayIndexDate(interval, date, (int)repeatFields[PIMUtil.DAY_IN_YEAR]);
						if (isAnExceptionDate(exceptionDates, date))
							i--;
					}
					break;
				}
				if (repeatFields[PIMUtil.DAY_IN_MONTH] != -1) {
					monthInYear = monthToCalendarFormat((int)repeatFields[PIMUtil.MONTH_IN_YEAR]);
					for (int i = 0; i < count - 1; i++) {
						date = getNextYearlyByDateDate(interval, date, (int)repeatFields[PIMUtil.DAY_IN_MONTH], monthInYear);
						if (isAnExceptionDate(exceptionDates, date))
							i--;
					}
					break;
				}
				if (repeatFields[PIMUtil.DAY_IN_WEEK] != -1 && repeatFields[PIMUtil.WEEK_IN_MONTH] != -1 && repeatFields[PIMUtil.MONTH_IN_YEAR] != 1) {
					dayInWeek = daysToCalendarFormat((int)repeatFields[PIMUtil.DAY_IN_WEEK]);
					weekInMonth = toIndexFormat((int)repeatFields[PIMUtil.WEEK_IN_MONTH]);
					monthInYear = monthToCalendarFormat((int)repeatFields[PIMUtil.MONTH_IN_YEAR]);
					for (int i = 0; i < count - 1; i++) {
						date = getNextYearlyByDayDate(interval, date, monthInYear, weekInMonth, dayInWeek);
						if (isAnExceptionDate(exceptionDates, date))
							i--;
					}
					break;
				}
		}
		set(date);
		return calendar.getTime().getTime();
	}
	
	/**
	 * Computes the number of times an event repeats.
	 * @param repeatFields The repeat fields: [{@link RepeatRule#COUNT}, {@link RepeatRule#DAY_IN_MONTH}, 
	 * 		{@link RepeatRule#DAY_IN_WEEK}, {@link RepeatRule#DAY_IN_YEAR}, {@link RepeatRule#END}, 
	 * 		{@link RepeatRule#FREQUENCY}, {@link RepeatRule#INTERVAL}, {@link RepeatRule#MONTH_IN_YEAR}, 
	 * 		{@link RepeatRule#WEEK_IN_MONTH}]
	 * @param exceptionDates The exception dates for the repeat rule.
	 * @param start The event start date. Number of milliseconds since Jan 1, 1970 GMT
	 * @return int
	 */
	public static int computeCount(long[] repeatFields, long[] exceptionDates, long start) {
		int count = 0;
		int[] dayInWeek;
		int[] weekInMonth;
		int[] monthInYear;
		int interval = (int)repeatFields[PIMUtil.INTERVAL];
		int[] startDate = toCalendarDateAndTime(start);
		int[] endDate = toCalendarDateAndTime(repeatFields[PIMUtil.END]);
		int[] date = startDate;
		
		switch((int)repeatFields[PIMUtil.FREQUENCY]) {
			case RepeatRule.DAILY:
				do {
					if  (!isAnExceptionDate(exceptionDates, date))
							count++;
					date = getNextDailyDate(interval, date);
				}
				while(isBeforeOrEqual(date, endDate));
				break;
			case RepeatRule.WEEKLY:
				dayInWeek = daysToCalendarFormat((int)repeatFields[PIMUtil.DAY_IN_WEEK]);
				do {
					if  (!isAnExceptionDate(exceptionDates, date))
							count++;
					date = getNextWeeklyDate(interval, date, dayInWeek);
				}
				while(isBeforeOrEqual(date, endDate));
				break;
			case RepeatRule.MONTHLY:
				if (repeatFields[PIMUtil.DAY_IN_WEEK] != -1 && repeatFields[PIMUtil.WEEK_IN_MONTH] != -1) {
					dayInWeek = daysToCalendarFormat((int)repeatFields[PIMUtil.DAY_IN_WEEK]);
					weekInMonth = toIndexFormat((int)repeatFields[PIMUtil.WEEK_IN_MONTH]);
					do {
						if  (!isAnExceptionDate(exceptionDates, date))
							count++;
						date = getNextMonthlyByDayDate(interval, date, weekInMonth, dayInWeek);
					}
					while(isBeforeOrEqual(date, endDate));	
				}
				else {
					do {
						if  (!isAnExceptionDate(exceptionDates, date))
							count++;
						date = getNextMonthlyByDateDate(interval, date, (int)repeatFields[PIMUtil.DAY_IN_MONTH]);
					}
					while(isBeforeOrEqual(date, endDate));
				}
				break;
			case RepeatRule.YEARLY:
				if (repeatFields[PIMUtil.DAY_IN_YEAR] != -1) {
					do {
						if  (!isAnExceptionDate(exceptionDates, date))
							count++;
						date = getNextYearlyByDayIndexDate(interval, date, (int)repeatFields[PIMUtil.DAY_IN_YEAR]);
					}
					while(isBeforeOrEqual(date, endDate));
					break;
				}
				if (repeatFields[PIMUtil.DAY_IN_MONTH] != -1) {
					monthInYear = monthToCalendarFormat((int)repeatFields[PIMUtil.MONTH_IN_YEAR]);
					do {
						if  (!isAnExceptionDate(exceptionDates, date))
							count++;
						date = getNextYearlyByDateDate(interval, date, (int)repeatFields[PIMUtil.DAY_IN_MONTH], monthInYear);
					}
					while(isBeforeOrEqual(date, endDate));
					break;
				}
				if (repeatFields[PIMUtil.DAY_IN_WEEK] != -1 && repeatFields[PIMUtil.WEEK_IN_MONTH] != -1 && repeatFields[PIMUtil.MONTH_IN_YEAR] != 1) {
					dayInWeek = daysToCalendarFormat((int)repeatFields[PIMUtil.DAY_IN_WEEK]);
					weekInMonth = toIndexFormat((int)repeatFields[PIMUtil.WEEK_IN_MONTH]);
					monthInYear = monthToCalendarFormat((int)repeatFields[PIMUtil.MONTH_IN_YEAR]);
					do {
						if  (!isAnExceptionDate(exceptionDates, date))
							count++;
						date = getNextYearlyByDayDate(interval, date, monthInYear, weekInMonth, dayInWeek);
					}
					while(isBeforeOrEqual(date, endDate));
					break;
				}
		}
		return count;
	}
	
	/**
	 * Answers the next date for the given repeat rule.
	 * @param repeatFields The repeat fields: [{@link RepeatRule#COUNT}, {@link RepeatRule#DAY_IN_MONTH}, 
	 * 		{@link RepeatRule#DAY_IN_WEEK}, {@link RepeatRule#DAY_IN_YEAR}, {@link RepeatRule#END}, 
	 * 		{@link RepeatRule#FREQUENCY}, {@link RepeatRule#INTERVAL}, {@link RepeatRule#MONTH_IN_YEAR}, 
	 * 		{@link RepeatRule#WEEK_IN_MONTH}]
	 * @param exceptionDates The exception dates for the repeat rule.
	 * @param start Number of milliseconds since Jan 1, 1970 GMT
	 * @return long Number of milliseconds since Jan 1, 1970 GMT
	 */
	public static long getNextDate(long[] repeatFields, long[] exceptionDates, long start) {
		int[] dayInWeek;
		int[] weekInMonth;
		int[] monthInYear;
		int interval = (int)repeatFields[PIMUtil.INTERVAL];
		int[] startDate = toCalendarDateAndTime(start);
		int[] date = startDate;
		switch((int)repeatFields[PIMUtil.FREQUENCY]) {
			case RepeatRule.DAILY:
				do {
					date = getDate(startDate, interval);
				}
				while (isAnExceptionDate(exceptionDates, date));
				break;
			case RepeatRule.WEEKLY:
				if (repeatFields[PIMUtil.DAY_IN_WEEK] != -1) {
					dayInWeek = daysToCalendarFormat((int)repeatFields[PIMUtil.DAY_IN_WEEK]);
					do {
						date = getNextWeeklyDate(interval, date, dayInWeek);
					}
					while (isAnExceptionDate(exceptionDates, date));
				}
				break;
			case RepeatRule.MONTHLY:
				if (repeatFields[PIMUtil.DAY_IN_WEEK] != -1 && repeatFields[PIMUtil.WEEK_IN_MONTH] != -1) {
					dayInWeek = daysToCalendarFormat((int)repeatFields[PIMUtil.DAY_IN_WEEK]);
					weekInMonth = toIndexFormat((int)repeatFields[PIMUtil.WEEK_IN_MONTH]);
					do {
						date = getNextMonthlyByDayDate(interval, date, weekInMonth, dayInWeek);
					}
					while (isAnExceptionDate(exceptionDates, date));
				}
				else {
					do {
						date = getNextMonthlyByDateDate(interval, date, (int)repeatFields[PIMUtil.DAY_IN_MONTH]);
					}
					while (isAnExceptionDate(exceptionDates, date));
				}
				break;
			case RepeatRule.YEARLY:
				if (repeatFields[PIMUtil.DAY_IN_YEAR] != -1) {
					do {
						date = getNextYearlyByDayIndexDate(interval, date, (int)repeatFields[PIMUtil.DAY_IN_YEAR]);
					}
					while (isAnExceptionDate(exceptionDates, date));
					break;
				}
				if (repeatFields[PIMUtil.DAY_IN_MONTH] != -1) {
					monthInYear = monthToCalendarFormat((int)repeatFields[PIMUtil.MONTH_IN_YEAR]);
					do {
						date = getNextYearlyByDateDate(interval, date, (int)repeatFields[PIMUtil.DAY_IN_MONTH], monthInYear);
					}
					while (isAnExceptionDate(exceptionDates, date));
					break;
				}
				if (repeatFields[PIMUtil.DAY_IN_WEEK] != -1 && repeatFields[PIMUtil.WEEK_IN_MONTH] != -1 && repeatFields[PIMUtil.MONTH_IN_YEAR] != 1) {
					dayInWeek = daysToCalendarFormat((int)repeatFields[PIMUtil.DAY_IN_WEEK]);
					weekInMonth = toIndexFormat((int)repeatFields[PIMUtil.WEEK_IN_MONTH]);
					monthInYear = monthToCalendarFormat((int)repeatFields[PIMUtil.MONTH_IN_YEAR]);
					do {
						date = getNextYearlyByDayDate(interval, date, monthInYear, weekInMonth, dayInWeek);
					}
					while (isAnExceptionDate(exceptionDates, date));
					break;
				}
		}
		set(date);
		return calendar.getTime().getTime();
	}
	
	/**
	 * Answers if the given date is a valid occurance date for this repeatRule.
	 * @param repeatFields The repeat fields: [{@link RepeatRule#COUNT}, {@link RepeatRule#DAY_IN_MONTH}, 
	 * 		{@link RepeatRule#DAY_IN_WEEK}, {@link RepeatRule#DAY_IN_YEAR}, {@link RepeatRule#END}, 
	 * 		{@link RepeatRule#FREQUENCY}, {@link RepeatRule#INTERVAL}, {@link RepeatRule#MONTH_IN_YEAR}, 
	 * 		{@link RepeatRule#WEEK_IN_MONTH}]
	 * @param exceptionDates The exception dates for the repeat rule.
	 * @param start Number of milliseconds since Jan 1, 1970 GMT
	 * @param date Number of milliseconds since Jan 1, 1970 GMT
	 * @return boolean
	 */
	public static boolean isValidDate(long[] repeatFields, long[] exceptionDates, long start, long date) {
		long nextDate = start;
		while (nextDate < date)
			nextDate = getNextDate(repeatFields, exceptionDates, nextDate);
		return (date == nextDate);
	}
	
	/**
	 * Answers if the given date is a valid exception date.
	 * @param repeatFields The repeat fields: [{@link RepeatRule#COUNT}, {@link RepeatRule#DAY_IN_MONTH}, 
	 * 		{@link RepeatRule#DAY_IN_WEEK}, {@link RepeatRule#DAY_IN_YEAR}, {@link RepeatRule#END}, 
	 * 		{@link RepeatRule#FREQUENCY}, {@link RepeatRule#INTERVAL}, {@link RepeatRule#MONTH_IN_YEAR}, 
	 * 		{@link RepeatRule#WEEK_IN_MONTH}]
	 * @param start Number of milliseconds since Jan 1, 1970 GMT
	 * @param exceptionDates The exception dates for the repeat rule.
	 * @return boolean
	 */
	public static boolean isValidExceptionDate(long[] repeatFields, long start, long exceptionDate) {
		// no time for exception dates.
		start = setTime(start, 0, 0, 0, 0);
		exceptionDate = setTime(exceptionDate, 0, 0, 0, 0);
		return isValidDate(repeatFields, new long[0], start, exceptionDate);
	}
	
	/**
	 * Converts PIM days into Calendar days.
	 * @param days
	 * @return int[]
	 */
	private static int[] daysToCalendarFormat(int days) {
		int[] result = {0, 0, 0, 0, 0, 0, 0};
		int size = 0;
		if ((days & RepeatRule.SUNDAY) != 0) {
			result[0] = 1;
			size++;
		}
		if ((days & RepeatRule.MONDAY) != 0) {
			result[1] = 2;
			size++;
		}
		if ((days & RepeatRule.TUESDAY) != 0) {
			result[2] = 3;
			size++;
		}
		if ((days & RepeatRule.WEDNESDAY) != 0) {
			result[3] = 4;
			size++;
		}
		if ((days & RepeatRule.THURSDAY) != 0) {
			result[4] = 5;
			size++;
		}
		if ((days & RepeatRule.FRIDAY) != 0) {
			result[5] = 6;
			size++;
		}
		if ((days & RepeatRule.SATURDAY) != 0) {
			result[6] = 7;
			size++;
		}
		int[] calendarDays = new int[size];
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			if (result[i] != 0) {
				calendarDays[index] = result[i];
				index++;
			}
		}
		return calendarDays;
	}
	
	/**
	 * Converts PIM months into Calendar months.
	 * @param months
	 * @return int[]
	 */
	private static int[] monthToCalendarFormat(int months) {
		int[] result = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int size = 0;
		if ((months & RepeatRule.JANUARY) != 0) {
			result[0] = 1;
			size++;
		}
		if ((months & RepeatRule.FEBRUARY) != 0) {
			result[1] = 1;
			size++;
		}
		if ((months & RepeatRule.MARCH) != 0) {
			result[2] = 1;
			size++;
		}
		if ((months & RepeatRule.APRIL) != 0) {
			result[3] = 1;
			size++;
		}
		if ((months & RepeatRule.MAY) != 0) {
			result[4] = 1;
			size++;
		}
		if ((months & RepeatRule.JUNE) != 0) {
			result[5] = 1;
			size++;
		}
		if ((months & RepeatRule.JULY) != 0) {
			result[6] = 1;
			size++;
		}
		if ((months & RepeatRule.AUGUST) != 0) {
			result[7] = 1;
			size++;
		}
		if ((months & RepeatRule.SEPTEMBER) != 0) {
			result[8] = 1;
			size++;
		}
		if ((months & RepeatRule.OCTOBER) != 0) {
			result[9] = 1;
			size++;
		}
		if ((months & RepeatRule.NOVEMBER) != 0) {
			result[10] = 1;
			size++;
		}
		if ((months & RepeatRule.DECEMBER) != 0) {
			result[11] = 1;
			size++;
		}
		int[] calendarMonth = new int[size];
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			if (result[i] != 0) {
				calendarMonth[index] = i;
				index++;
			}
		}
		return calendarMonth;
	}
	
	/**
	 * Answers an index representing the week in month.
	 * @param weekInMonth
	 * @return int[]
	 */
	private static int[] toIndexFormat(int weekInMonth) {
		int[] result = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		int size = 0;
		if ((weekInMonth & RepeatRule.FIRST) != 0) {
				result[0] = 1;
				size++;
		}
		if ((weekInMonth & RepeatRule.SECOND) != 0) {
				result[1] = 2;
				size++;
		}
		if ((weekInMonth & RepeatRule.THIRD) != 0) {
				result[2] = 3;
				size++;
		}
		if ((weekInMonth & RepeatRule.FOURTH) != 0) {
				result[3] = 4;
				size++;
		}
		if ((weekInMonth & RepeatRule.FIFTH) != 0) {
				result[4] = 5;
				size++;
		}
		if ((weekInMonth & RepeatRule.LAST) != 0) {
				result[5] = 6;
				size++;
		}
		if ((weekInMonth & RepeatRule.SECONDLAST) != 0) {
				result[6] = 7;
				size++;
		}
		if ((weekInMonth & RepeatRule.THIRDLAST) != 0) {
				result[7] = 8;
				size++;
		}
		if ((weekInMonth & RepeatRule.FOURTHLAST) != 0) {
				result[8] = 9;
				size++;
		}
		if ((weekInMonth & RepeatRule.FIFTHLAST) != 0) {
				result[9] = 10;
				size++;
		}
		int[] weekIndex = new int[size];
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			if (result[i] != 0) {
				weekIndex[index] = result[i];
				index++;
			}
		}
		return weekIndex;
	}
	
	/**
	 * Answers the next date for a daily event.
	 * @param interval
	 * @param date The current date.
	 */
	private static int[] getNextDailyDate(int interval, int[] date) {
		return getDate(date, interval);
	}
	
	/**
	 * Answers the next date for a weekly event.
	 * @param interval
	 * @param date The current date
	 * @param dayInWeek The days of the week in Calendar format.
	 */
	private static int[] getNextWeeklyDate(int interval, int[] date, int[] dayInWeek) {
	 	set(date);
	 	int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
	 	int newDay = getNextDayInWeek(date, dayInWeek);
		int increment = newDay - currentDay;
		increment = increment <= 0 ? interval*7 + increment : increment;
		return getDate(date, increment);
	}
	
	/**
	 * Answers the next date for a monthly event.
	 * @param interval
	 * @param date The current date.
	 * @param weekInMonth An array of week indexes into a month.
	 * @param dayInWeek An array of days in week.
	 */
	private static int[] getNextMonthlyByDayDate(int interval, int[] date, int[] weekInMonth, int[] dayInWeek) {
		int[] nextDate = date;
	 	for (int i = 0; i < weekInMonth.length; i++) {
	 		int[] newDate = PIMUtil.arraycopy(date);
			newDate = getNextMonthlyByDayDate(interval, newDate, weekInMonth[i], dayInWeek);
			if (i == 0)
				nextDate = newDate;
			if (isBetween(newDate, date, nextDate))
				nextDate = newDate;
		}
		return nextDate;
	}
	
	/**
	 * Answers the next date for a monthly event.
	 * @param interval
	 * @param date The current date.
	 * @param weekInMonth An index of the week in months.
	 * @param dayInWeek An array of days in week.
	 */
	private static int[] getNextMonthlyByDayDate(int interval, int[] date, int weekInMonth, int[] dayInWeek) {
	 	int[] nextDate = date;
	 	for (int i = 0; i < dayInWeek.length; i++) {
	 		int[] newDate = PIMUtil.arraycopy(date);
			newDate = getNextMonthlyByDayDate(interval, newDate, weekInMonth, dayInWeek[i]);
			if (isBeforeOrEqual(newDate, date)) {
				newDate[0] += (newDate[1] + interval)/12;
				newDate[1] = (newDate[1] + interval)%12;
				newDate = getNextMonthlyByDayDate(interval, newDate, weekInMonth, dayInWeek[i]);
			}
			if (i == 0)
				nextDate = newDate;
			if (isBetween(newDate, date, nextDate))
				nextDate = newDate;
		}
		return nextDate;
	}
	
	/**
	 * Answers the next date for a monthly event.
	 * @param interval
	 * @param date The current date.
	 * @param weekInMonth An index of the week in months.
	 * @param dayInWeek An particular day in week.
	 */
	private static int[] getNextMonthlyByDayDate(int interval, int[] date ,int weekInMonth, int dayInWeek) {
		int newDate = 0;
		if (weekInMonth <= 5) {
			set(date[0], date[1]+1, 1);
			int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
			int offset = dayInWeek - firstDay;
			newDate = weekInMonth*7 + 1 + (offset < 0 ? offset : offset - 7);
			if (newDate > (date[1] == 1 && isLeapYear(date[0]) ? DAYS_IN_MONTH[date[1]]+1 : DAYS_IN_MONTH[date[1]])) {
				date[0] += (date[1] + interval)/12;
			date[1] = (date[1] + interval)%12;
				return getNextMonthlyByDayDate(interval, date, weekInMonth, dayInWeek);
			}
		}
		
		else {
			int lastDate = date[1] == 1 && isLeapYear(date[0]) ? DAYS_IN_MONTH[date[1]]+1 : DAYS_IN_MONTH[date[1]];
			set(date[0], date[1]+1, lastDate);
			int lastDay = calendar.get(Calendar.DAY_OF_WEEK);
			int offset = dayInWeek - lastDay;
			newDate = lastDate - (weekInMonth - 5)*7 + (offset <= 0 ? 7 + offset : offset);
			if (newDate <= 0) {
				date[0] += (date[1] + interval)/12;
				date[1] = (date[1] + interval)%12;
				return getNextMonthlyByDayDate(interval, date, weekInMonth, dayInWeek);
			}
		}
		date[2] = newDate;
		return date;
	}
	
	/**
	 * Answers the next date for a monthly event.
	 * @param interval
	 * @param date The current date.
	 */
	private static int[] getNextMonthlyByDateDate(int interval, int[] date, int dayInMonth) {
		if (date[2] >= dayInMonth) {
			date[0] += (date[1] + interval)/12;
			date[1] = (date[1] + interval)%12;
		}
		date[2] = dayInMonth;
		if ((date[2] == 29 && date[1] == 1 && !isLeapYear(date[0])) || DAYS_IN_MONTH[date[1]] < date[2])
			return getNextMonthlyByDateDate(interval, date, dayInMonth);
		return date;
	}
	
	/**
	 * Answers the next date for a yearly event which occurs at the same date every n year.
	 * @param interval
	 * @param date The current date.
	 * @param monthInYear. An array of month indexes in year.
	 */
	private static int[] getNextYearlyByDateDate(int interval, int[] date, int dayInMonth, int[] monthInYear) {
		if (date[2] >= dayInMonth) {
			int nextMonth = getNextMonthInYear(date, monthInYear);
			int increment = nextMonth - date[1];
			increment = increment <= 0 ? interval*12 + increment : increment;
			date[0] += increment / 12;
			date[1] = nextMonth;	
		}
		date[2] = dayInMonth;
		if ((date[2] == 29 && date[1] == 1 && !isLeapYear(date[0])) || date[2] > DAYS_IN_MONTH[date[1]]) {
			return getNextYearlyByDateDate(interval, date, dayInMonth, monthInYear);
		}
		return date;
	}
	
	/**
	 * Answers the next date for a yearly event which occurs the same day every n year.
	 * @param interval
	 * @param date The current date.
	 */
	private static int[] getNextYearlyByDayIndexDate(int interval, int[] date, int dayInYear) {
		int dayIndex = DAYS_IN_YEAR[date[1]] + date[2] + (date[1] > 1 && isLeapYear(date[0]) ? 1 : 0);		
		// sets the date to the first day of the new year.
		if (dayIndex >= dayInYear)
			date[0] += interval;
		date[1] = 0;
		date[2] = 1;
		return getDate(date, dayInYear - 1);
	}
	
	/**
	 * Answers the next date for a yearly event.
	 * @param interval
	 * @param date The current date.
	 * @param monthInYear An array of month indexes in year.
	 * @param weekInMonth An array of week indexes in month.
	 * @param dayInWeek An array of days in week.
	 */
	private static int[] getNextYearlyByDayDate(int interval, int[] date, int[] monthInYear, int[] weekInMonth, int[] dayInWeek) {
		int[] newDate = PIMUtil.arraycopy(date);
		newDate = getNextMonthlyByDayDate(interval, newDate, weekInMonth, dayInWeek);
		while (!PIMUtil.contains(monthInYear, newDate[1]) || !isMultiple(newDate[0] - date[0], interval))
			newDate = getNextMonthlyByDayDate(1, newDate, weekInMonth, dayInWeek);
		return newDate;
	}
	
	
	/**
	 * Answers the new date of the current day + 'increment' days.
	 * @param date The current date.
	 * @param increment The number of days to increment.
	 */
	private static int[] getDate(int[] date, int increment) {
		int numberOfDays = date[2];
		for (int i = 0; i < date[1]; i++) {
			numberOfDays += DAYS_IN_MONTH[i];
		}
		if (date[1] > 1 && isLeapYear(date[0]))
			numberOfDays++;
		numberOfDays += increment;
		int yearInc = numberOfDays / 365;
		for (int i = 0; i < yearInc; i++) {
			numberOfDays -=  365 + 	(isLeapYear(date[0]+i) ? 1 : 0);
		}
		date[0] += yearInc;
		int offset = 0;
		for (int i=0; i<13; i++) {
			if (numberOfDays > offset)
				offset+= DAYS_IN_MONTH[i] + ((i == 1 && isLeapYear(date[0])) ? 1 : 0);
			else {
				date[1] = Math.max(0,i - 1);
				date[2] = numberOfDays - offset + DAYS_IN_MONTH[date[1]] + ((date[1] == 1 && isLeapYear(date[0])) ? 1 : 0);
				break;
			}		
		}
		return date;
	}
	
	/**
	 * Answers if 'date1' occurs before or equal to 'date2'.
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	private static boolean isBeforeOrEqual(int[] date1, int[] date2) {
		for (int i = 0; i < date1.length; i++) {
			if (date1[i] < date2[i])
				return true;
			if (date1[i] > date2[i])
				return false;
		}
		return true; // equal
	}
	
	/**
	 * Answers if 'date1' occurs before 'date2'.
	 * @param date1
	 * @param date2
	 * @return boolean
	 */
	private static boolean isBefore(int[] date1, int[] date2) {
		for (int i = 0; i < date1.length; i++) {
			if (date1[i] < date2[i])
				return true;
			if (date1[i] > date2[i])
				return false;
		}
		return false; // equal
	}
	
	/**
	 * Answers the next day of the week after the current date.
	 * @param date
	 * @param dayInWeek
	 * @return int
	 */
	private static int getNextDayInWeek(int date[], int[] dayInWeek) {
		set(date);
	 	int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
	 	int newDay = currentDay;
		
		if (dayInWeek.length != 1) {
			newDay = 8;
			for (int i = 0; i < dayInWeek.length; i++) {
				if (dayInWeek[i] - currentDay > 0) {
					if (newDay - currentDay > dayInWeek[i] - currentDay)
						newDay = dayInWeek[i];
				}
			}
			
			if (newDay == 8) {
				newDay = currentDay;
				for (int i = 0; i < dayInWeek.length; i++) {
					if (currentDay - dayInWeek[i] > 0) {
						if(currentDay - newDay < currentDay - dayInWeek[i])
							newDay = dayInWeek[i];
					}
				}
			}
		}
		return newDay;
	}
	
	/**
	 * Answers the next month after the current date.
	 * @param date
	 * @param monthInYear
	 * @return int
	 */
	private static int getNextMonthInYear(int date[], int[] monthInYear) {
		int nextMonth = date[1];
		if (monthInYear.length != -1) {
			nextMonth = 12;
			for (int i = 0; i < monthInYear.length; i++) {
				if (monthInYear[i] - date[1] > 0) {
					if (nextMonth - date[1] > monthInYear[i] - date[1])
						nextMonth = monthInYear[i];
				}
			}
			if (nextMonth == 12) {
				nextMonth = date[1];
				for (int i = 0; i < monthInYear.length; i++) {
					if (date[1] - monthInYear[i] > 0) {
						if (date[1] - nextMonth < date[1] - monthInYear[i])
							nextMonth = monthInYear[i];
					}
				}
			}
		}
		return nextMonth;
	}
	
	/**
	 * Returns true if date is between start and end.
	 * @param date
	 * @param start
	 * @param end
	 */
	private static boolean isBetween(int[] date, int[] start, int[] end) {
		if (isBeforeOrEqual(date, start))
			return false;
		for (int i = 0; i < start.length; i++) {
			if (date[i] - start[i] > end[i] - start[i])
				return false;
			if (date[i] - start[i] < end[i] - start[i])
				return true;
		}
		return true;	
	}
	
	/**
	 * Checks if 'date' is an exception date.
	 * @param exceptionDates
	 * @param date
	 * @return boolean
	 */
	private static boolean isAnExceptionDate(long[] exceptionDates, int[] date) {
		// no time for exception dates.
		set(date[0], date[1] + 1, date[2], 0, 0, 0, 0);
		long longDate = calendar.getTime().getTime(); 
		for (int i = 0; i < exceptionDates.length; i++) {
			if (longDate == exceptionDates[i])
				return true;
		}
		return false;
	}
	
	/**
	 * Answers true if 'value' is a multiple of 'multiple'.
	 * @param value
	 * @param multiple
	 * @return boolean
	 */
	private static boolean isMultiple(int value, int multiple) {
		if ((value % multiple) == 0)
			return true;
		return false;
	}
	
	/*
	 * Month between 0 and 11.
	 * hour: 0 - 23
	 */
	private static void set(int[] date) {
		set(date[0], date[1]+1, date[2],  date[3], date[4], date[5], date[6]);
	}
	
	/*
	 * month between 0 and 11.
	 * hour: 0 - 23
	 */
	private static int[] toCalendarDateAndTime(long date) {
		calendar.setTime(new Date(date));
		
		int [] calDate = {calendar.get(Calendar.YEAR), 
						  calendar.get(Calendar.MONTH), 
						  calendar.get(Calendar.DATE), 
					      calendar.get(Calendar.HOUR_OF_DAY), 
						  calendar.get(Calendar.MINUTE),
						  calendar.get(Calendar.SECOND),
						  calendar.get(Calendar.MILLISECOND)};
		
		return calDate;
	}
	
	/**
	 * Converts the given date.
	 * @param inMillis Number of milliseconds since Jan 1, 1970 GMT.
	 * @return int[] [year, month, date].
	 */
	public static int[] toCalendarDate(Date inMillis) {
		calendar.setTime(inMillis);
		int [] calDate = {calendar.get(Calendar.YEAR), 
						  calendar.get(Calendar.MONTH) + 1, 
						  calendar.get(Calendar.DATE)};
		
		return calDate;
	}
	
	/**
	 * Converts the given date.
	 * @param inMillis Number of milliseconds since Jan 1, 1970 GMT.
	 * @return int[] [year, month, date, hour, minute, decond, millisecond].
	 */
	public static int[] toCalendarDateAndTime(Date inMillis) {
		calendar.setTime(inMillis);
		int [] calDate = 
			{calendar.get(Calendar.YEAR), 
			 calendar.get(Calendar.MONTH) + 1, 
			 calendar.get(Calendar.DATE), 
		     calendar.get(Calendar.HOUR_OF_DAY), 
			 calendar.get(Calendar.MINUTE),
			 calendar.get(Calendar.SECOND),
			 calendar.get(Calendar.MILLISECOND)};
		
		return calDate;
	}
	
	/**
	 * Converts a long date to a long sixteen bit date format.
	 * @param inMillis Number of milliseconds since Jan 1, 1970 GMT.
	 * @return long
	 */
	public static long toSixteenBitDate(long inMillis) {
		int[] theDate = toCalendarDateAndTime(new Date(inMillis));
		int sixteenBitDate = (((theDate[0]-1904)&0x7F)<<9) |
								     (((theDate[1])&0x0F)<<5) |
								     (theDate[2]&0x1F);				     	
		return (long)sixteenBitDate;
	}
	
	/**
	 * Converts a long date to a sixteen bit date and time and returns the time.
	 * @param inMillis Number of milliseconds since Jan 1, 1970 GMT.
	 * @return int[] [date, hour, minute]
	 */
	public static int[] toSixteenBitDateAndTime(long inMillis) {
		int[] theDate = toCalendarDateAndTime(new Date(inMillis));
		int sixteenBitDate = (((theDate[0]-1904)&0x7F)<<9) |
								     (((theDate[1])&0x0F)<<5) |
								     (theDate[2]&0x1F);
					
		// DATE, HOUR, MINUTE	????		     
		return new int[] {sixteenBitDate, theDate[3], theDate[4]};
	}
	
	/**
	 * Answers the long value representing the given date.
	 * @param year
	 * @param month
	 * @param day
	 * @return long Number of milliseconds since Jan 1, 1970 GMT.
	 */
	public static long fromCalendarDate(int year, int month, int day) {
		set(year, month, day);
		return calendar.getTime().getTime();
	}
	
	/**
	 * Answers the long value representing the given date.
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minutes
	 * @param second
	 * @param millisecond
	 * @return long Number of milliseconds since Jan 1, 1970 GMT.
	 */
	public static long fromCalendarDateAndTime(int year, int month, int day, int hour, int minutes, int second, int millisecond) {
		set(year, month, day, hour, minutes, second, millisecond);
		return calendar.getTime().getTime();
	}
	
	/*
	 * Sets the calendar with the given data.
	 */
	private static void set(int year, int month, int day, int hour, int minutes, int second, int millisecond) {
		//first set date to 1 to avoid problems if day was previously outside the range
		//of days of certain months. For example, if the calendar was initially set to the 7 of
		//Feb and you were to set the date to 31, the calendar would return March 3rd.
		calendar.set(Calendar.DATE, 1);
		
		calendar.set(Calendar.YEAR, year);
		//Month are accepted as 1-base to be consistent with year and day.
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
	}
	
	/*
	 * Sets the calendar with the given date and a time equals to 0. 
	 */
	private static void set(int year, int month, int day) {
		set(year, month, day, 0, 0, 0, 0);
	}
	
	/**
	 * Answers the offset in milliseconds from GMT.
	 * @param date milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
	 * @return int in milliseconds
	 */
	public static int getTimeOffset(long date) {
		TimeZone tz = TimeZone.getDefault();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(date));
		
		int time = calendar.get(Calendar.MILLISECOND) +
				   calendar.get(Calendar.SECOND)*1000 +
				   calendar.get(Calendar.MINUTE)*60*1000 +
				   calendar.get(Calendar.HOUR)*60*60*1000;
		
		return tz.getOffset(1,
							 calendar.get(Calendar.YEAR),
							 calendar.get(Calendar.MONTH),
							 calendar.get(Calendar.DATE),
							 calendar.get(Calendar.DAY_OF_WEEK),
							 time);
	}
	
	/**
	 * Answers if the specified year is a leap year.
	 * @param		year	the year
	 * @return		true if the specified year is a leap year, false otherwise
	 */
	public static boolean isLeapYear(int year) {
		if (year > changeYear)
			return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
		else
			return year % 4 == 0;
	}
	
	/**
	 * Answers the long value of the given date.
	 * @param VDate see ISO 8601
	 * @return long milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
	 * @throws PIMException if the format is not valid.
	 */
	public static long fromVDate(String VDate) throws PIMException {	
		StringBuffer buffer = new StringBuffer(VDate);
		String exceptionMessage = "Invalid format.";
		// at least full date 
		if (buffer.length() < 8)
			throw new PIMException(exceptionMessage);
		
		int i = 0;
		//consume '-' on
		while(i != -1) {
			if (buffer.charAt(i) == '-')
				buffer.delete(i, i+1);
			else
				i++;
			i = (i >= Math.min(buffer.length(), 8) ? -1 : i);
		}
		//consume ':'
		i = 7;
		while(i != -1) {
			if (buffer.charAt(i) == ':')
				buffer.delete(i, i+1);
			else
				i++;
			i = (i >= buffer.length() ? -1 : i);
		}
		
		// add time if necessary
		if (buffer.length() == 8)
			buffer.append("T000000");
		
		if (buffer.length() < 15)
			throw new PIMException(exceptionMessage);
		
		try {
			// date	
			String date = buffer.toString();
			int year = Integer.parseInt(date.substring(0, 4));
			buffer.delete(0, 4);
			
			date = buffer.toString();
			int month = Integer.parseInt(date.substring(0, 2));
			buffer.delete(0, 2);
			
			date = buffer.toString();
			int day = Integer.parseInt(date.substring(0, 2));
			buffer.delete(0, 2);
			
			
			if (day > 31 || month > 12)
				throw new PIMException(exceptionMessage);

			if (buffer.length() == 0)
				return DateHelper.fromCalendarDate(year, month, day);		

			// time
			if (buffer.charAt(0) != 'T')
				throw new PIMException(exceptionMessage);
			
			buffer.delete(0, 1);
			
			
			date = buffer.toString();
			int hour = Integer.parseInt(date.substring(0, 2));
			buffer.delete(0, 2);
			
			date = buffer.toString();
			int minute = Integer.parseInt(date.substring(0, 2));
			buffer.delete(0, 2);
			
			date = buffer.toString();
			int second = Integer.parseInt(date.substring(0, 2));
			buffer.delete(0, 2);
			
			// fraction of second
			if (buffer.length() != 0) {
				if (buffer.charAt(0) == ',' || buffer.charAt(0) == '.') {
					buffer.delete(0, 1);
					int count = 0;
					for (count = 0; count < buffer.length(); count++) {
						if (!Character.isDigit(buffer.charAt(count)))
							break;
					}
					buffer.delete(0, count);
				}
			}
			
			if (hour > 24 || minute > 60 || second > 60)
				throw new PIMException(exceptionMessage);
			
			long result = DateHelper.fromCalendarDateAndTime(year, month, day, hour, minute, second, 0);
			
			if (buffer.length() == 0)
				return result;		
			
			if (buffer.charAt(0) == '+' || buffer.charAt(0) == '-') {
				boolean plus = buffer.charAt(0) == '+';
				buffer.deleteCharAt(0);
				date = buffer.toString();
				int hourOffset = Integer.parseInt(date.substring(0, 2));
				buffer.delete(0, 2);
				int minuteOffset = 0;
				if (buffer.length() > 1) {
					date = buffer.toString();
					minuteOffset = Integer.parseInt(date.substring(0, 2));
					buffer.delete(0, 2);
				}
				
				if (plus) {
					hour -= hourOffset;
					minute -= minuteOffset;
				}
				else {
					hour += hourOffset;
					minute += minuteOffset;
				}
				
				result = DateHelper.fromCalendarDateAndTime(year, month, day, hour, minute, second, 0);
				
				return result + DateHelper.getTimeOffset(result);
			}
			
			// when GMT time
			if (buffer.charAt(0) == 'Z')
				return result + DateHelper.getTimeOffset(result);
			else
				return result;
		}
		catch (Exception e) {
			throw new PIMException(exceptionMessage);
		}
	}
	
	/**
	 * Converts the given date to a 'VDate'.
	 * @param date The date to convert.
	 * @param includeTime
	 * @return String see ISO 8601.
	 */
	public static String toVDate(Date date, boolean includeTime) {
		char[] VDate=null;
		
		if (includeTime)
			VDate = new char[] {0,0,0,0,0,0,0,0,'T',0,0,0,0,'0','0','Z'};
		else
			VDate = new char[] {0,0,0,0,0,0,0,0};
			
		int [] theDate = DateHelper.toCalendarDateAndTime(date);
		
		VDate[0] = (char)(theDate[0]/1000 + '0');
		theDate[0] %= 1000;
		VDate[1] = (char)(theDate[0]/100 + '0');
		theDate[0] %= 100;
		VDate[2] = (char)(theDate[0]/10 + '0');
		theDate[0] %= 10;
		VDate[3] = (char)(theDate[0] + '0');
		
		VDate[4] = (char)(theDate[1]/10 + '0');
		theDate[1] %= 10;
		VDate[5] = (char)(theDate[1] + '0');
		
		VDate[6] = (char)(theDate[2]/10 + '0');
		theDate[2] %= 10;
		VDate[7] = (char)(theDate[2] + '0');
		
		if (includeTime) {
			VDate[9] = (char)(theDate[3]/10 + '0');
			theDate[3] %= 10;
			VDate[10] = (char)(theDate[3] + '0');
			
			VDate[11] = (char)(theDate[4]/10 + '0');
			theDate[4] %= 10;
			VDate[12] = (char)(theDate[4] + '0');
		}
		
		return new String(VDate);
	}
}