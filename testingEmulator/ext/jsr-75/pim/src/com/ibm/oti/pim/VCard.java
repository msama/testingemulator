package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

/**
 * VCard 2.1
 */
public interface VCard {
	
	public static final String BEGIN_TAG = "BEGIN";
	public static final String END_TAG = "END";
	public static final String EXTENDED_PREFIX_TAG = "X-";
	
	public static final String ATTR_ENCODING_TAG = "ENCODING";
	public static final String ATTR_VALUE_TAG = "VALUE";
	public static final String ATTR_TYPE_TAG = "TYPE";
	public static final String ATTR_CHARSET_TAG = "CHARSET";
	public static final String ATTR_LANGUAGE_TAG = "LANGUAGE";
	
	public static final String VCARD_TAG = "VCARD";
		
	public static final String VCARD_BEGIN_TAG = BEGIN_TAG + ":" +VCard.VCARD_TAG;
	public static final String VCARD_END_TAG = END_TAG + ":" +VCard.VCARD_TAG;
	
	public static final String VERSION_TAG = "VERSION";
	
	public static final String NAME_TAG = "N";
	public static final String FORMATTED_NAME_TAG = "FN";
	public static final String TITLE_TAG = "TITLE";
	public static final String ORG_TAG = "ORG";
	public static final String ADDR_TAG = "ADR";
	public static final String FORMATTED_ADDR_TAG = "LABEL";
	public static final String TEL_TAG = "TEL";
	public static final String EMAIL_TAG = "EMAIL";
	public static final String BIRTHDAY_TAG = "BDAY";
	public static final String NOTE_TAG = "NOTE";
	public static final String PHOTO_TAG = "PHOTO";
	public static final String PUBLIC_KEY_TAG = "KEY";
	public static final String REVISION_TAG = "REV";
	public static final String URL_TAG = "URL";
	public static final String UID_TAG = "UID";
	
	public static final String AGENT_TAG = "AGENT";
	
	// not supported in VCard 2.1
	public static final String CLASS_TAG = EXTENDED_PREFIX_TAG + "CLASS";
	public static final String CLASS_PUBLIC_TAG = "PUBLIC";
	public static final String CLASS_PRIVATE_TAG = "PRIVATE";
	public static final String CLASS_CONFIDENTIAL_TAG = "CONFIDENTIAL";
	public static final String NICKNAME_TAG = EXTENDED_PREFIX_TAG + "NICKNAME";
	public static final String CATEGORIES_TAG = EXTENDED_PREFIX_TAG + "CATEGORIES";
	
	// Attributes	
	public static final String ATTR_INTERNET_TAG = "INTERNET";
	
	public static final String ATTR_PREF_TAG = "PREF";
	public static final String ATTR_WORK_TAG = "WORK";
	public static final String ATTR_HOME_TAG = "HOME";
	public static final String ATTR_FAX_TAG = "FAX";
	public static final String ATTR_SMS_TAG = "MSG";
	public static final String ATTR_MOBILE_TAG = "CELL";
	public static final String ATTR_PAGER_TAG = "PAGER";
	public static final String ATTR_AUTO_TAG = "CAR";
	// default TEL attribute
	public static final String ATTR_VOICE_TAG = "VOICE";
	
	// not supported in VCard 2.1
	public static final String ATTR_ASST_TAG = EXTENDED_PREFIX_TAG + "ASST";
	public static final String ATTR_OTHER_TAG = EXTENDED_PREFIX_TAG + "OTHER";
	
	public static final String ATTR_TYPE_X501 = "X501";
	public static final String ATTR_TYPE_PGP = "PGP";
	
	/**
	 * Valid property names.
	 */
	public static final String[] NAMES = {"LOGO",
											AGENT_TAG,
											ORG_TAG,
											PHOTO_TAG,
											NAME_TAG,
											ADDR_TAG,
											NICKNAME_TAG,
											FORMATTED_ADDR_TAG,
											FORMATTED_NAME_TAG,
											TITLE_TAG,
											"SOUND",
											VERSION_TAG,
											TEL_TAG,
											EMAIL_TAG,
											"TZ",
											"GEO",
											NOTE_TAG,
											URL_TAG,
											BIRTHDAY_TAG,
											"ROLE",
											REVISION_TAG,
											UID_TAG,
											PUBLIC_KEY_TAG,
											"MAILER"};
	
	/**
	 * Property names allowing multiple values.
	 */
	public static final String[] MULTIPLE_VALUES_NAMES = {ADDR_TAG,
														    ORG_TAG,
														    NAME_TAG,
														    AGENT_TAG,
														    CATEGORIES_TAG};
	
	/**
	 * Known parameter values.
	 */
	public static final String[] PARAMS_VALUE = {ATTR_TYPE_TAG,
												   ATTR_VALUE_TAG,
												   ATTR_ENCODING_TAG,
												   ATTR_CHARSET_TAG,
												   ATTR_LANGUAGE_TAG,
												   EXTENDED_PREFIX_TAG};
													   
	/**
	 * Known types.
	 */
	public static final String[] KNOWN_TYPES = {"DOM",
												  "INTL",
												  "POSTAL",
												  "PARCEL",
												  ATTR_HOME_TAG,
												  ATTR_WORK_TAG,
												  ATTR_PREF_TAG,
												  ATTR_VOICE_TAG,
												  ATTR_FAX_TAG,
												  ATTR_SMS_TAG,
												  ATTR_MOBILE_TAG,
												  ATTR_PAGER_TAG,
												  "BBS",
												  "MODEM",
												  ATTR_AUTO_TAG,
												  "ISDN",
												  "VIDEO",
												  "AOL",
												  "APPLELINK",
												  "ATTMAIL",
												  "CIS",
												  "EWORLD",
												  ATTR_INTERNET_TAG,
												  "IBMMAIL",
												  "MCIMAIL",
												  "POWERSHARE",
												  "PRODIGY",
												  "TLX",
												  "X400",
												  "GIF",
												  "CGM",
												  "WMF",
												  "BMP",
												  "MET",
												  "PMB",
												  "DIB",
												  "PICT",
												  "TIFF",
												  "PDF",
												  "PS",
												  "JPEG",
												  "QTIME",
												  "MPEG",
												  "MPEG2",
												  "AVI",
												  "WAVE",
												  "AIFF",
												  "PCM",
												  ATTR_TYPE_X501,
												  ATTR_TYPE_PGP};
		
}