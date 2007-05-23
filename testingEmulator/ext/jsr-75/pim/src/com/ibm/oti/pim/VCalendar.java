package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

/**
 * VCalendar 1.0
 */
public interface VCalendar {
	
	public static final String BEGIN_TAG = "BEGIN";
	public static final String END_TAG = "END";
	public static final String EXTENDED_PREFIX_TAG = "X-";
	
	public static final String ATTR_ENCODING_TAG = "ENCODING";
	public static final String ATTR_VALUE_TAG = "VALUE";
	public static final String ATTR_TYPE_TAG = "TYPE";
	public static final String ATTR_CHARSET_TAG = "CHARSET";
	public static final String ATTR_LANGUAGE_TAG = "LANGUAGE";
	
	public static final String VCALENDAR_TAG = "VCALENDAR";
	
	public static final String EVENT_TAG = "VEVENT";
	public static final String TODO_TAG = "VTODO";
	
	public static final String VEVENT_BEGIN_TAG = BEGIN_TAG + ":" + EVENT_TAG;
	public static final String VEVENT_END_TAG = END_TAG + ":" + EVENT_TAG;
	
	public static final String VTODO_BEGIN_TAG = BEGIN_TAG + ":" + TODO_TAG;
	public static final String VTODO_END_TAG = END_TAG + ":" + TODO_TAG;
	
	public static final String VCALENDAR_BEGIN_TAG = BEGIN_TAG + ":" +VCalendar.VCALENDAR_TAG;
	public static final String VCALENDAR_END_TAG = END_TAG + ":" +VCalendar.VCALENDAR_TAG;
	
	
	// RRULE
	public static final String REPEAT_RULE_TAG = "RRULE";
	public static final String DAILY_TAG = "D";
	public static final String WEEKLY_TAG = "W";
	public static final String MONTHLY_BY_DAY_TAG = "MD";
	public static final String MONTHLY_BY_POSITION_TAG = "MP";
	public static final String YEARLY_BY_DAY_TAG = "YD";
	public static final String YEARLY_BY_MONTH_TAG = "YM";
	public static final String LAST_DAY = "LD";
	public static final String COUNT_TAG = "RNUM";
	public static final String EXCEPTION_DATE_TAG = "EXDATE";
	
	
	public static final String VERSION_TAG = "VERSION";
	
	public static final String COMPLETED_TAG = "COMPLETED";
	
	public static final String CLASS_TAG = "CLASS";
	public static final String CLASS_PUBLIC_TAG = "PUBLIC";
	public static final String CLASS_PRIVATE_TAG = "PRIVATE";
	public static final String CLASS_CONFIDENTIAL_TAG = "CONFIDENTIAL";
	
	public static final String SUMMARY_TAG = "SUMMARY";
	public static final String NOTE_TAG = "DESCRIPTION";
	
	public static final String ALARM_TAG = "DALARM";
	
	public static final String END_DATE_TAG = "DTEND";
	public static final String START_TAG = "DTSTART";
	public static final String DUE_TAG = "DUE";

	public static final String REVISION_TAG = "LAST-MODIFIED";
	public static final String LOCATION_TAG = "LOCATION";
	public static final String UID_TAG = "UID";
	public static final String PRIORITY_TAG = "PRIORITY";
	
	public static final String CATEGORIES_TAG = "CATEGORIES";
	public static final String RESOURCES_TAG = "RESOURCES";
	
	public static final String[] WEEK_DAY_TAGS = {"SU", "MO", "TU", "WE", "TH", "FR", "SA"};
	
	/**
	 * Valid property names.
	 */
	public static final String[] NAMES = {"GEO",
											"PRODID",
											"TZ",
											VERSION_TAG,
											"AALARM",
											CATEGORIES_TAG,
											CLASS_TAG,
											ALARM_TAG,
											EXCEPTION_DATE_TAG,
											"MALARM",
											"PALARM",
											"RDATE",
											RESOURCES_TAG,
											"STATUS",
											"ATTACH",
											"ATTENDEE",
											"DCREATED",
											COMPLETED_TAG,
											NOTE_TAG,
											DUE_TAG,
											END_DATE_TAG,
											"EXRULE",
											REVISION_TAG,
											LOCATION_TAG,
											COUNT_TAG,
											PRIORITY_TAG,
											"RELATED-TO",
											REPEAT_RULE_TAG,
											"SEQUENCE",
											START_TAG,
											SUMMARY_TAG,
											"TRANSP",
											"URL",
											UID_TAG,
											"DAYLIGHT"};
	
	/**
	 * Property names allowing multiple values.
	 */
	public static final String[] MULTIPLE_VALUES_NAMES = {"AALARM",
															CATEGORIES_TAG,
															ALARM_TAG,
															EXCEPTION_DATE_TAG,
															"MALARM",
															"PALARM",
															"RDATE",
															RESOURCES_TAG};
	
	/**
	 * Known parameter values.
	 */
	public static final String[] PARAMS_VALUE = {ATTR_TYPE_TAG,
												   ATTR_VALUE_TAG,
												   ATTR_ENCODING_TAG,
												   ATTR_CHARSET_TAG,
												   ATTR_LANGUAGE_TAG,
												   "ROLE",
												   "STATUS",
												   EXTENDED_PREFIX_TAG};
	
	/**
	 * Frequencies for the recurrence rule.
	 */
	public static final String[] FREQUENCIES = {DAILY_TAG,
												  WEEKLY_TAG,
												  MONTHLY_BY_DAY_TAG,
												  MONTHLY_BY_POSITION_TAG,
												  YEARLY_BY_DAY_TAG,
												  YEARLY_BY_MONTH_TAG};
	
}