package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import javax.microedition.pim.PIMException;
import javax.microedition.pim.RepeatRule;

/**
 * This parser allows to parse recurrence rules from VEVENT.
 */
class RecurrenceRuleParser {
	
	private static final int DAILY = 0;	
	private static final int WEEKLY = 1;
	private static final int MONTHLY_BY_DAY = 2;
	private static final int MONTHLY_BY_POSTION = 3;
	private static final int YEARLY_BY_DAY = 4;
	private static final int YEARLY_BY_MONTH = 5;
	
	private static int frequency = -1;
	private static RepeatRule repeat;
	
	private static final PIMException exception = new PIMException("Invalid format.");
	
	
	/**
	 * Parses the recurrence rule and fills the given repeat rule.
	 * @param recurrenceRule. The recurrence rule.
	 * @param repeatRule.
	 * @throws PIMException
	 */
	static void parse(String recurrenceRule, RepeatRule repeatRule) throws PIMException {
		repeat = repeatRule;
		int dayInWeek = 0;
		int weekInMonth = 0;
		int monthInYear = 0;
		
		StringBuffer buffer = new StringBuffer(recurrenceRule.trim());
		
		// set the frequency
		consumeFrequency(buffer);
		// set the interval
		consumeInterval(buffer);
	
		while(buffer.length() != 0) {
			char oneChar = buffer.charAt(0);
			if (oneChar == '#')
				// set the duration (count)
				consumeCount(buffer);
			else if (PIMUtil.isAlpha(oneChar)) {
				if (frequency == MONTHLY_BY_POSTION || frequency == WEEKLY) 
					dayInWeek |= consumeDayInWeek(buffer);
				else if (frequency == MONTHLY_BY_DAY)
					consumeDayInMonth(buffer);
				else
					throw exception;
			}
			else if (Character.isDigit(oneChar)) {
				if (buffer.toString().indexOf(' ') == -1 && buffer.length() >= 8) // 8: a date without time
					consumeEndDate(buffer);		
				else if (frequency == MONTHLY_BY_POSTION)
					weekInMonth |= consumeWeekInMonth(buffer);
				else if (frequency == MONTHLY_BY_DAY)
					consumeDayInMonth(buffer);
				else if (frequency == YEARLY_BY_DAY)
					consumeDayInYear(buffer);
				else if (frequency == YEARLY_BY_MONTH)
					monthInYear |= consumeMonthInYear(buffer);
				else
					throw exception;
			}
		}
		
		if (dayInWeek != 0)
			repeat.setInt(RepeatRule.DAY_IN_WEEK, dayInWeek);
		
		if (weekInMonth != 0)
			repeat.setInt(RepeatRule.WEEK_IN_MONTH, weekInMonth);
		
		if (monthInYear != 0)
			repeat.setInt(RepeatRule.MONTH_IN_YEAR, monthInYear);
	}

	/*
	 * Consumes the white spaces at the beginning.
	 */
	private static void consumeWhiteSpace(StringBuffer buffer) {
		if (buffer.length() == 0)
			return;
		while (buffer.charAt(0) == ' ')
			buffer.deleteCharAt(0);
	}
	
	/*
	 * Consumes the frequency and sets the repeat rule.
	 */
	private static void consumeFrequency(StringBuffer buffer) throws PIMException {
		String current = buffer.toString();
		int intFreq = -1;
		if (PIMUtil.startsWithIgnoreCase(current, VCalendar.DAILY_TAG)) {
			intFreq = RepeatRule.DAILY;
			frequency = DAILY;
		}
		else if (PIMUtil.startsWithIgnoreCase(current, VCalendar.WEEKLY_TAG)) {
			intFreq = RepeatRule.WEEKLY;
			frequency = WEEKLY;
		}
		else if (PIMUtil.startsWithIgnoreCase(current, VCalendar.MONTHLY_BY_DAY_TAG)) {
			intFreq = RepeatRule.MONTHLY;
			frequency = MONTHLY_BY_DAY;
		}
		else if (PIMUtil.startsWithIgnoreCase(current, VCalendar.MONTHLY_BY_POSITION_TAG)) {
			intFreq = RepeatRule.MONTHLY;
			frequency = MONTHLY_BY_POSTION;
		}
		else if (PIMUtil.startsWithIgnoreCase(current, VCalendar.YEARLY_BY_DAY_TAG)) {
			intFreq = RepeatRule.YEARLY;
			frequency = YEARLY_BY_DAY;
		}
		else if (PIMUtil.startsWithIgnoreCase(current, VCalendar.YEARLY_BY_MONTH_TAG)) {
			intFreq = RepeatRule.YEARLY;
			frequency = YEARLY_BY_MONTH;
		}
		else	
			throw exception;
		
		repeat.setInt(RepeatRule.FREQUENCY, intFreq);
		buffer.delete(0, frequency > 1 ? 2 : 1);
		consumeWhiteSpace(buffer);
	}
	
	/*
	 * Consumes the interval and sets the repeat rule.
	 */
	private static void consumeInterval(StringBuffer buffer) throws PIMException {
		String current = buffer.toString();
		int index = 0;
		while((buffer.length() > index)) {
			if (Character.isDigit(buffer.charAt(index)))
				index++;
			else
				break;	
		}
		try {
			int interval = Integer.parseInt(current.substring(0, index));
			repeat.setInt(RepeatRule.INTERVAL, interval);
		}
		catch (NumberFormatException e) {
			throw exception;
		}
		buffer.delete(0, index + 1);		
		consumeWhiteSpace(buffer);		
	}
	
	/*
	 * Consumes the count and sets the repeat rule.
	 */
	private static void consumeCount(StringBuffer buffer) throws PIMException {
		buffer.deleteCharAt(0);
		int index = 0;
		while((buffer.length() > index)) {
			if (buffer.charAt(index) != ' ')
				index++;
			else
				break;	
		}
		String current = buffer.toString();
		try {
			int count = Integer.parseInt(current.substring(0, index));
			if (count > 0)
				repeat.setInt(RepeatRule.COUNT, count);
		}
		catch (NumberFormatException e) {
			throw exception;
		}
		buffer.delete(0, index + 1);		
		consumeWhiteSpace(buffer);
	}
	
	/*
	 * Consumes a day in week.
	 */
	private static int consumeDayInWeek(StringBuffer buffer) {
		String day = buffer.toString().substring(0, 2);
		int result = -1;
		String[] days = VCalendar.WEEK_DAY_TAGS;
		for (int i = 0; i < days.length; i++) {
			if (PIMUtil.equalsIgnoreCase(day, days[i])) {
				result = toAPIFormat(i);
				break;
			}
		}
		buffer.delete(0, 2);
		consumeWhiteSpace(buffer);
		return result;
	}
	
	/*
	 * Consumes a week in month.
	 */
	private static int consumeWeekInMonth(StringBuffer buffer) throws PIMException{
		String current = buffer.toString();
		int weekInMonth = -1;
		try {
			int value = Integer.parseInt(current.substring(0, 1));
			if (current.charAt(1) == '+')
				weekInMonth = 1 << (value - 1);
			else if (current.charAt(1) == '-')
				weekInMonth = 1 << (value + 4);	
			else
				throw exception;		
		}
		catch (NumberFormatException e) {
			throw exception;
		}
		buffer.delete(0, 2);
		consumeWhiteSpace(buffer);
		return weekInMonth;
	}
	
	/*
	 * Consumes a day in month and sets the repeat rule.
	 */
	private static void consumeDayInMonth(StringBuffer buffer) throws PIMException {
		String current = buffer.toString();
		if (Character.isDigit(current.charAt(0))) {
			int dayIndex = -1;
			try {
				int index = 0;
				if (Character.isDigit(current.charAt(1))) {
					index = 2;
					dayIndex = Integer.parseInt(current.substring(0, 2));
				}	
				else {
					index = 1;
					dayIndex = Integer.parseInt(current.substring(0, 1));
				}
				if (buffer.length() > index) {
					if (current.charAt(index) == '+')
						dayIndex++;
					else if (current.charAt(index) == '-')
						dayIndex--;
					
					else if (current.charAt(index) != ' ')
						throw exception;
				}
				// The underlying native implementation will make coherent if
				// the last day of the start month is not 31.
				repeat.setInt(RepeatRule.DAY_IN_MONTH, dayIndex % 31);
				
				buffer.delete(0, index + 1);
			}
			catch (NumberFormatException e) {
				throw exception;
			}
		}
		else {
			if (PIMUtil.equalsIgnoreCase(VCalendar.LAST_DAY, current.substring(0, 2))) {
				buffer.delete(0, 2);
				// sets the day in month field to 31. 
				// The underlying native implementation will make coherent if
				// the last day of the start month is not 31.
				repeat.setInt(RepeatRule.DAY_IN_MONTH, 31);
			}
		}
		consumeWhiteSpace(buffer);
	}
	
	/*
	 * Consumes a day in year and sets it.
	 * The PIM representation does not support multiple values for this field.
	 * If multiple, the last one sets will be used.  
	 */
	private static void consumeDayInYear(StringBuffer buffer) throws PIMException {
		String current = buffer.toString();
		int index = 0;
		for (index = 0; index < Math.min(3, buffer.length()); index++) {
			if (!Character.isDigit(current.charAt(index)))
				break;
		}
		if (index == 0)
			throw exception;

		try {
			int dayInYear = Integer.parseInt(current.substring(0, index));
			repeat.setInt(RepeatRule.DAY_IN_YEAR, dayInYear);
		}
		catch (NumberFormatException e) {
			throw exception;
		}
		buffer.delete(0, index);
		consumeWhiteSpace(buffer);
	}
	
	/*
	 * Consumes a month in year.
	 */
	private static int consumeMonthInYear(StringBuffer buffer) throws PIMException {
		String current = buffer.toString();
		int index = 0;
		for (index = 0; index < Math.min(2, buffer.length()); index++) {
			if (!Character.isDigit(current.charAt(index)))
				break;
		}
		if (index == 0)
			throw exception;
		
		int monthInYear = 0;
		try {
			int value = Integer.parseInt(current.substring(0, index));
			monthInYear = 1 << (value + 16);
		}
		catch (NumberFormatException e) {
			throw exception;
		}
		buffer.delete(0, index);
		consumeWhiteSpace(buffer);
		return monthInYear;
	}
	
	/*
	 * Consumes the end date and sets the repeat rule.
	 */
	private static void consumeEndDate(StringBuffer buffer) throws PIMException {
		String endDate = buffer.toString();
		repeat.setDate(RepeatRule.END, DateHelper.fromVDate(endDate));
		buffer.delete(0, buffer.length()); 
	}
	
	/*
	 * Convert a Vcalendar day of week to a PIM day in week. 
	 */
	private static int toAPIFormat(int dayIndex) {
		switch (dayIndex) {
			case 0:
				return RepeatRule.SUNDAY;
			case 1:
				return RepeatRule.MONDAY;
			case 2:
				return RepeatRule.TUESDAY;
			case 3:
				return RepeatRule.WEDNESDAY;
			case 4:
				return RepeatRule.THURSDAY;
			case 5:
				return RepeatRule.FRIDAY;
			case 6:
				return RepeatRule.SATURDAY;
			case 7:
				return RepeatRule.SUNDAY;
			default:
				return -1;
		}
	}
}