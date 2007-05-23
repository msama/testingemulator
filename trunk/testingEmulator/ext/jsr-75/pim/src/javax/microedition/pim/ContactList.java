package javax.microedition.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

/**
 * Represents a Contact list containing Contact items.
 * <P>
 * A Contact List is responsible for determining which of the fields
 * from a Contact are retained when a Contact is persisted into the List.
 * A Contact List does not have to retain all of the fields in a Contact
 * when the Contact is persisted into the List.
 * See the Contact interface for a description of the fields
 * in a Contact.  The fields that are supported by a particular
 * Contact List are queried through the methods {@link #isSupportedField} and
 * {@link #getSupportedAttributes}.  If a field ID that is not in the Contact
 * interface is provided as the parameter to the {@link #isSupportedField}
 * method, a <code>java.lang.IllegalArgumentException</code> is thrown.
 * The {@link #isSupportedField} method for a Contact List also accepts field
 * type values (e.g. <code>TYPE_*</code> constants) as valid parameters for
 * checking for support.
 * </P>
 * <h3>Inherited Method Behavior</h3>
 * <P>
 * A ContactList only accepts objects implementing the Contact interface as
 * a parameter to {@link #items(PIMItem)}).  A
 * <code>java.lang.IllegalArgumentException</code> is thrown by this method if
 * the input parameter does not implement the Contact interface. </P>
 * <P>
 * Enumerations returned by {@link #items()} and
 * {@link #items(PIMItem)} in a Contact List contain only objects
 * implementing a Contact interface.</P>
 *
 * @see Contact
 * @since PIM 1.0
 */
public interface ContactList extends PIMList {

    /**
    * Factory method to create a Contact for this contact list.  The Contact
    * is empty upon creation with none of its fields containing any data (i.e. a
    * call to the method <code>Contact.getFields()</code> returns an array
    * of zero length).  Even though it is initially empty, the Contact is
    * <i>capable</i> of containing data for exactly those fields that this list
    * supports.
    * Note that creation of the Contact does not add the Contact to the list
    * from which the item was created; a specific call to
    * <code>PIMItem.commit()</code> must be made to commit the item and
    * its data to the list.
    *
    * @return   a new, empty Contact object associated with this list.  However,
    *           the Contact is still not persistent in the list until a call to
    *           <code>PIMItem.commit()</code> for the Contact is made.
    */
    public abstract Contact createContact();

    /**
    * Imports the given Contact into this contact list by making a new Contact
    * for the list and filling its information with as much information as it
    * can from the provided Contact.  If the input Contact is already in the
    * list, a new Contact is still created with information similar to the input
    * item (but not necessarily identical). </P>
    * <P>Note that not all data from the input Contact may be supported in the
    * new Contact due to field restrictions for the list instance.  In this
    * case, data fields not supported are not transferred to the new Contact
    * object. </P>
    * <P>Also note that creation of the Contact does not add the Contact
    * to this list; a specific call to <code>PIMItem.commit()</code> must be
    * made to commit the item and its data to the list. </P>
    *
    * @param    contact the Contact to import into the list.
    * @return   a newly created Contact.
    * @throws	NullPointerException If <code>contact</code> is
    *           <code>null</code>.
    */
    public abstract Contact importContact(Contact contact);

    /**
    * Removes a specific Contact from the list.  The item must already
    * exist in the list for this method to succeed.
    *
    * @param    contact the Contact to be removed from the list.
    * @throws 	PIMException If an error occurs deleting the item
    *           or the list is no longer accessible or closed.
    * @throws	NullPointerException If <code>contact</code> is
    *           <code>null</code>.
    * @throws	java.lang.SecurityException if the application is not given
    *           permission to write to the Contact list or the list is opened
    *           READ_ONLY.
    */
    public abstract void removeContact(Contact contact) throws PIMException;

}