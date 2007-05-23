package javax.microedition.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.ibm.oti.pim.PIMImpl;

/**
 * Class for accessing PIM lists on a device and performing PIM wide
 * functions.  This class is a collection of static methods for getting the
 * names of the existing PIM lists, opening the lists, and converting raw
 * data streams to and from PIM items for importing and exporting into those
 * lists.
 *
 * <h3>Examples</h3>
 * <h4>Importing a Contact from a Stream</h4>
 * This example assumes that the InputStream contains a valid vCard 3.0 entry
 * with the following characteristics:
 * <pre>
 *     Content-Type: text/directory; charset="UTF-8"; profile="vCard"
 *     Content-ID: <id3@host.com>
 *     Content-Transfer-Encoding: Quoted-Printable
 * </pre>
 * The content of the vCard looks like the following:
 * <pre>
 *     begin:VCARD
 *     source:ldap://cn=3Dbjorn%20Jensen, o=3Duniversity%20of%20Michigan, c=3DUS
 *     name:Bjorn Jensen
 *     fn:Bj=C3=B8rn Jensen
 *     n:Jensen;Bj=F8rn
 *     email;type=3Dinternet:bjorn@umich.edu
 *     tel;type=3Dwork,voice,msg:+1 313 747-4454
 *     key;type=3Dx509;encoding=3DB:dGhpcyBjb3VsZCBiZSAKbXkgY2VydGlmaWNhdGUK
 *     end:VCARD
 * </pre>
 * The following method assumes that the input stream is positioned at the first
 * character of the vCard (i.e. the start of the "begin:" tag).  For
 * simplicity in this example, the method also hardcodes the transfer encoding
 * of Quoted Printable and the data in the character set UTF-8.
 * <pre>
 * void importVCard(InputStream is) {
 *      // Using application defined class QuotedPrintableInputStream ...
 *      QuotedPrintableInputStream qpis = new QuotedPrintableInputStream(is);
 *      try {
 *          PIMItem[] items = PIM.getInstance().fromSerialFormat(qpis, "UTF-8");
 *          Contact c = (Contact) (items[0]);
 *          ContactList cl = (ContactList) PIM.getInstance().openPIMList(PIM.CONTACT_LIST, PIM.READ_WRITE);
 *          cl.importContact(c);
 *      }
 *      catch(PIMException pe) {
 *      }
 *      catch(IOException ioe) {
 *      }
 * }
 * </pre>
 *
 * <h4>Exporting a Contact to a Stream</h4>
 * This example exports a Contact to the provided output stream.  The data
 * format used is the first data format returned from
 * {@link #supportedSerialFormats}.
 * Note that any transfer encoding, such as applying Quoted Printable, is the
 * responsibility of the application outside of this method.
 * <pre>
 * void exportVCard(Contact c, OutputStream os) {
 *      String[] data_formats = PIM.getInstance().supportedSerialFormats(PIM.CONTACT_LIST);
 *      try {
 *          PIM.getInstance().toSerialFormat(c, os, "UTF-8", data_formats[0]);
 *      }
 *      catch (PIMException pe){
 *      }
 *      catch (IOException ioe){
 *      }
 * }
 * </pre>
 *
 * @since PIM 1.0
 */
public abstract class PIM extends Object {

    /**
     * Constant representing a Contact List.
     */
	public static final int CONTACT_LIST = 1;

    /**
     * Constant representing an Event List.
     */
	public static final int EVENT_LIST = 2;

    /**
     * Constant representing a ToDo List.
     */
	public static final int TODO_LIST = 3;

    /**
     * Constant representing opening a list in read only mode.
     */
	public static final int READ_ONLY = 1;

    /**
     * Constant representing opening a list in write only mode.
     */
	public static final int WRITE_ONLY = 2;

    /**
     * Constant representing opening a list in read/write mode.
     */
	public static final int READ_WRITE = 3;
	
	private static PIMImpl instance = null;
	
    /**
     * Constructor for subclasses. PIM class objects are not instantiated
     * directly.  To access a PIM object, {@link #getInstance} should be used.
     */
	protected PIM() {
	}

    /**
     * Factory method to get an instance of the PIM class.
     *
     * @return an instance of the PIM class.
     */
	public static PIM getInstance() {	
		if (instance == null) {
			System.loadLibrary("pimdll");
			instance = new PIMImpl();
		}
		return instance;
	}

    /**
     * Open the default list for the indicated PIM list type.
     *
     * @param    pimListType int representing the PIM list type to open, either
     *           {@link #CONTACT_LIST}, {@link #EVENT_LIST}, or {@link #TODO_LIST}.
     * @param    mode in which the List is opened, either
     *           {@link #READ_ONLY}, {@link #READ_WRITE}, or {@link #WRITE_ONLY}.
     * @return   the default PIM list for the indicated PIM list type.
     * @throws	PIMException If the operation is unsupported or an error occurs.
     * @throws	java.lang.SecurityException if the application is not given
     *           permission that matches the requested mode.
     * @throws   IllegalArgumentException If an invalid mode is provided as a
     *           parameter or if pimListType is not a valid
     *           PIM list type.
     */
	public abstract PIMList openPIMList(int pimListType, int mode) throws PIMException;

    /**
     * Open a named PIM List. Only string names returned by
     * {@link #listPIMLists} can be used to open a list.
     *
     * @param    pimListType int representing the PIM list type to open, either
     *           {@link #CONTACT_LIST}, {@link #EVENT_LIST}, or {@link #TODO_LIST}.
     * @param    mode in which the List is opened, either
     *           {@link #READ_ONLY}, {@link #READ_WRITE}, or {@link #WRITE_ONLY}.
     * @param    name the named List to open
     * @return   the named List.
     * @throws	PIMException If the list is not available, the operation is
     *           unsupported or an error occurs.
     * @throws	java.lang.SecurityException if the application is not given
     *           permission that matches the requested mode.
     * @throws   IllegalArgumentException If an invalid mode is provided as a
     *           parameter or if pimListType is not a valid
     *           PIM list type.
     * @throws   NullPointerException If <code>name</code> is <code>null</code>.
     */
	public abstract PIMList openPIMList(int pimListType, int mode, String name) throws PIMException;

    /**
     * Returns a list of all PIM List names for the given PIM list type.  If
     * there are no Lists for that list type, a zero length String array is
     * returned.  The first name in the list is the name of the default List for
     * a type.
     *
     * @param    pimListType int representing the PIM list type to list, either
     *           {@link #CONTACT_LIST}, {@link #EVENT_LIST}, or {@link #TODO_LIST}.
     * @return   a list of named Lists
     * @throws	java.lang.SecurityException if the application is not given
     *           permission to read PIM lists.
     * @throws   java.lang.IllegalArgumentException if pimListType is not a valid
     *           PIM list type.
     */
	public abstract String[] listPIMLists(int pimListType);

   /**
     * Creates and fills one or more PIM items from data provided in the given
     * InputStream object where the data is expressed in a valid
     * data format supported by this platform. The character
     * stream starting from the InputStream's current character position must
     * respresent a complete entry as specified by the supported data format or
     * else a {@link PIMException} is thrown.  Data representing unsupported
	 * data entries and/or data fields is ignored as long as a valid entry of 
	 * a supported entry type is contained in the stream.
	 * <P> 
     * This method reads only one complete data entry from the
     * InputStream, leaving the InputStream object open and its character
     * position on the character after the end of the entry, or at a position
	 * in the stream where an error is detected if the data stream is in an 
	 * invalid or incomplete format.  A single valid data entry in an 
	 * InputStream results in the creation of one or more PIMItems (in some 
	 * data formats more than one PIMItem may be in a single entry).
     * </P><P>
     * The format of the data in the InputStream determines what type of PIM
     * items are created (see {@link #supportedSerialFormats} to see what
     * data formats are supported by this implementation).
     * </P><P>
     * It is the responsibility of the application to perform
     * any transfer character decoding (such as <i>Content-Transfer-Encoding</i>
     * for MIME or <i>Transfer-Encoding</i> for HTTP) prior to providing the
     * InputStream object with the entry data to this method.
     * </P><P>
     * In creating PIM items, data from the input stream is taken and mapped to
     * appropriate corresponding PIM item fields. If there is no appropriate
     * corresponding PIM item field for a particular piece of data in the input
     * stream, that data may be either silently discarded by this method or
     * retained in the PIM item as an extended field value (the action taken is
     * implementation specific).  If the data from the input stream does not
     * contain values for all of the fields required by the item, the VM may
     * provide default values so that the information can still be received
     * and PIMItems can still be created.
     * </P><P>
     * Also note that creation of the PIM items does not add the item to
     * any list; the import method for a list capable of containing the specific
     * PIM item must be used to add the item to a list.  For
     * example, should a <code>Contact</code> item be created, then the method
     * <code>ContactList.importContact</code> should be invoked from an existing
     * list to import that contact into the target list.</P>
     *
     * @param    is an input stream containing PIM information in a well-known
     *           data format supported by this method.
     * @param    enc the character encoding of the characters in the input
     *           stream.  If <code>null</code>, the default character encoding
     *           of UTF-8 is used.
     * @return   an array of newly created PIM items created from the
     *           OutputStream.  In some cases, more than one PIM item may be
     *           created from a complete entry (for example, a single vCalendar
     *           entry may produce two PIM items, both an Event and a ToDo).
     * @throws   PIMException If the data is incorrect, does not contain
     *           enough information to form a complete entry, or a stream
   	 *			error occurs.
     * @throws   NullPointerException if <code>is</code> is
     *           <code>null</code>.
     * @throws   UnsupportedEncodingException if <code>enc</code> is not a
     *           encoding method supported on this platform.
     * @see      #supportedSerialFormats
     */
	public abstract PIMItem[] fromSerialFormat(InputStream is, String enc) throws PIMException, UnsupportedEncodingException;

    /**
     * Writes the data from the given item to the given OutputStream object as
     * Unicode characters in a format indicated by the String parameter.  The
     * character encoding for those characters written to the OutputStream is in
     * the provided character encoding.
     * The data format specified must be supported by the PIM implementation
     * (see {@link #supportedSerialFormats}) or else an
     * <code>IllegalArgumentException</code> is thrown. After this method is
     * invoked, the OutputStream contains the contact as a data stream in
     * the given data format.  The output produced is a single complete entry
     * and suitable for exchange with other programs supporting that data format.
     * The OutputStream is left open and any writes to that stream occurs
     * at the position immediately after the entry just written.
     * </P><P>
     * All data fields contained in the provided PIMItem are written out to the
     * output stream in the specified format.  Fields that do not have specific
     * corresponding fields in the data format specified are written as
     * extended fields if the data format supports extended fields.
     * </P><P>
     * It is the responsibility of the application to perform any further
     * character encoding needed for transportation of the vCard entry (such as
     * <i>Content-Transfer-Encoding</i> for MIME or <i>Transfer-Encoding</i>
     * for HTTP).
     *
     * @param   item the item to export to the OutputStream object
     * @param   os the OutputStream object that this item is written to as
     *          a character stream.
     * @param   enc the character encoding used to write the PIMItem to the
     *          OutputStream.  If <code>null</code>, the default character
     *          encoding of UTF-8 is used.
     * @param   dataFormat the requested PIM data format to use (i.e. the
     *          well-known structure of the data prior to character encoding)
     * @throws  PIMException if an error occurs when coverting the item's
     *          data or if a stream error occurs, preventing the data from 
	 *			being written in a complete and valid form.
     *          There is no guarantee that the OutputStream is unmodified
     *          or contain incomplete information if this exception is thrown.
     * @throws  NullPointerException if <code>os</code> is <code>null</code>,
     *          <code>dataFormat</code> is <code>null</code>, or
     *          <code>item</code> is <code>null</code>.
     * @throws  UnsupportedEncodingException if <code>enc</code> is not a
     *          encoding method supported on this platform.
	 * @throws  IllegalArgumentException if the <code>dataFormat</code> is not
	 *			a valid format listed in <code>supportedSerialFormats</code>.
     * @see     #supportedSerialFormats
     */
	public abstract void toSerialFormat(PIMItem item, OutputStream os, String enc, String dataFormat) throws PIMException, UnsupportedEncodingException;

    /**
     * This method returns the supported data formats for items
     * used when converting a PIMItem's data to and from data streams.
     * The return value is suitable for use as the data format parameter in
     * {@link #toSerialFormat}. The naming convention for the strings returned
     * is the common name of the data format in all capital letters, followed
     * by a slash ("/"), and then followed by the version number of the data
     * format. For example "VCARD/2.1" or "VCALENDAR/1.0".
     *
     * @param    pimListType int representing the PIM list type, either
     *           {@link #CONTACT_LIST}, {@link #EVENT_LIST}, or {@link #TODO_LIST}.
     * @return   String array of data formats supported for converting
     *           items to and from data streams.
     * @throws   java.lang.IllegalArgumentException if pimListType is not a valid
     *           PIM list type.
     */
	public abstract String[] supportedSerialFormats(int pimListType);
}