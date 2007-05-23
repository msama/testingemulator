package javax.microedition.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

/**
 * Represents the common interfaces of an item for a PIM list.
 * A PIM item represents a collection of data for a single PIM entry.
 * A PIM item is created from a particular PIM list and is associated with
 * that list for the life of the item.  PIM items can have its data
 * imported and exported using standard byte based formats.  Each implementing
 * class defines what formats can be imported and exported for that item.
 * </p>
 * <H3>Fields</H3>
 * <P>PIMItems reference its data through <i>fields</i>.  A field is a grouping
 * of data values that all have similar characteristics.  An example of a field
 * is TEL, which indicates data values for that particular field are telephone
 * numbers. Classes implementing the PIMItem interface defines the possible
 * fields that for that specific class (e.g TEL is defined in the
 * <code>Contact</code> interface as a field that a contact may support).
 * </P><P>
 * PIM implementations are not required to support all of the possible fields
 * defined in the classes implementing the PIMItem interface.  This is because
 * no native PIM databases contain all of the fields defined in this API.
 * The PIMList that a PIMItem belongs to determines what fields a PIMItem can
 * support and store (all PIMItems in a particular PIMList support the
 * same set of fields). The {@link PIMList#getSupportedFields} method from a
 * particular PIMItem's PIMList is used to find out what fields are supported
 * within this item.  Since not all possible fields are actually supported in a
 * particular PIMItem, <B>all fields should be checked for support in the item's
 * PIMList using {@link PIMList#isSupportedField} prior to being used in any
 * retrieval or storage method.</B>
 * </p><p>
 * Each field has the following pieces of information available for it:
 * <UL>
 * <LI>Zero or more <i>data values</I> associated with the Field
 * <LI><i>Attributes</I> for data values for the Field
 * <LI>Descriptive <i>label</I> for the Field
 * <LI><I>Data Type</i> of the data associated with the Field
 * </UL>
 * <h5>Data Values in a Field</h5>
 * <p>A single field can have zero or more data values associated with it at any
 * instance.  All values within a field have the same data type as dictated by
 * the field (for example, all Contact.TEL field data values must be of STRING
 * type).  The data type of a field determines the add/get/set methods to use
 * for accessing the data values (for example, if a field requires STRING data
 * types, then addString, getString, and setString methods are used to access
 * the data).
 * </p><P>
 * Data values within a field are treated as a variable-length array of values,
 * very similar to the behavior of a <code>Vector</code>.  As such, the
 * following rules apply for accessing data values for fields:<br>
 * <ul>
 * <li>Values are added using the appropriate addXXX() method.  The value is
 * appended as the last data value in the field's array, similar to
 * <code>Vector.addElement</code>.</li>
 * <li>Values are retrieved one at a time using the appropriate getXXX() method
 * with an index.  The index is an array index into the field's array of data
 * values. Values are assigned a sequential index beginning from 0 for the first
 * value in a field up to n-1, where n is the total number of values currently
 * assigned to the field. This behavior is similar to the method
 * <code>Vector.elementAt()</code>.</li>
 * <li>Values are removed from a field by using the method {@link #removeValue}.
 * All indexes in the field's array are guaranteed by the implementation to
 * contain an assigned value.  Therefore, removing fields from the middle of a
 * field's array causes compacting of the array and reindexing of the data
 * values. This is similar behavior to the method
 * <code>Vector.removeElement(Object)</code>.</li>
 * </ul>
 * <h5>Field Labels</h5>
 * <p>Each field has a human readable <i>label</i>, usually used for display
 * purposes. The label can be retrieved through {@link PIMList#getFieldLabel}.
 * </p>
 * <h5>Field Data Types</h5>
 * <p>The data values for a field has a <i>data type</i>, such as {@link #INT},
 * {@link #BINARY}, {@link #BOOLEAN}, {@link #DATE}, {@link #STRING_ARRAY} or
 * {@link #STRING}.  The data type of the field's data can be retrieved
 * through {@link PIMList#getFieldDataType}.  All data values for a particular
 * field have the same data type.
 * </p>
 * <h4>Standard and Extended Fields</h4>
 * <p>Fields can be classified into two logical divisions: standard fields and
 * extended fields.  This division of fields generally determines the
 * portability of the fields across implementations. Standard fields are
 * specifically defined within the javax.microedition.pim package and may be
 * available on almost all PIM implementations.  Extended fields are platform
 * specific fields defined by an individual implementation and are therefore
 * generally not portable across different devices.  Extended fields are
 * generally defined in vendor specific classes derived from this class.
 * </p>
 * <h5>Standard Fields</h5>
 * <P>Standard fields are fields that have IDs explicitly defined as part of the
 * PIM APIs in the javax.microedition.pim package. These fields are the common
 * fields among PIM lists and are more likely to be portable across PIM
 * implementations (but not guaranteed since not all platforms support the same
 * fields in a PIMItem).
 * </P>
 * <h5>Extended Fields</h5>
 * <p>Extended fields are fields that do not have a specific field explicitly
 * defined in the javax.microedition.pim package, but are defined in
 * vendor-specific classes in a separate vendor package.  These fields may or
 * may not be exposed publicly in vendor specific classes. Vendors are allowed
 * to extend the field set for any of the PIM items in this manner to address
 * any platform specific fields they wish to support.  Users can find out if a
 * field is an extended field by comparing its value against
 * {@link #EXTENDED_FIELD_MIN_VALUE}, find out the field's allowed data type
 * through the method {@link PIMList#getFieldDataType}, and find out the field's
 * label through the method{@link PIMList#getFieldLabel}.
 * </p>
 * <h4>Attributes</h4>
 * <p>Optional attributes can be provided to further describe individual data
 * values for a field.  Attributes are specified when adding data values to a
 * field.  These attributes are hints to the underlying implementation providing
 * more information about the data value than just a field can provide.  Since
 * they are hints, they may or may not be ignored by the implementation when
 * adding the data values.  The actual attributes used and associated with the
 * data values after adding can be retrieved by the method
 * {@link #getAttributes}.  Attributes can also have human readable labels
 * associated with them, retrieved by the method {@link PIMList#getAttributeLabel}.
 * If no attributes are to be associated with a data value, then
 * {@link #ATTR_NONE} must be used.
 * </p><p>
 * Attributes are handled in the API using a single bit to indicate a specific
 * attribute and using int values as bit arrays to indicate a set of attributes.
 * int values can be checked to see if they contain a specific attribute by
 * using bitwise AND (&) with the attribute and the int value.
 * {@link #ATTR_NONE} is a special attribute that indicates no attributes are
 * set and has a value of 0 that erases all other attributes previously set.
 * </P>
 * <h5>Extended Attributes</h5>
 * <p>Optional attributes may also be extended by vendors and their PIM API
 * implementations.  These extended attributes also may or may not be exposed
 * publicly in vendor specific classes.  The label for these attributes can
 * be retrieved through {@link PIMList#getAttributeLabel}.
 * </p>
 * <h3>Categories</h3>
 * <p>Categories are string items assigned to an item to represent the
 * item's inclusion in a logical grouping.  The category string
 * correspond to category values already existing in the PIMItem's
 * associated PIMList.  Category support per list is optional, depending
 * on the implementing PIMList class that the
 * item is associated with.  The item's list determines if categories can be
 * assigned, and how many categories can be assigned per item.
 * </p>
 *
 * @since PIM 1.0
 */

public interface PIMItem {

    /**
    * Data type indicating data is binary in a byte array.
    * Data associated with <code>BINARY</code> is
    * retrieved via {@link #getBinary} and added via {@link #addBinary}.
    */
    public static final int BINARY = 0;

    /**
    * Data type indicating data is of boolean primitive data type.
    * Data associated with <code>BOOLEAN</code> is
    * retrieved via {@link #getBoolean} and added via {@link #addBoolean}.
    */
    public static final int BOOLEAN = 1;

    /**
    * Data type indicating data is a Date in long primitive data type format
    * expressed in the same long value format as java.util.Date, which is
    * milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
    * Data associated with <code>DATE</code> is
    * retrieved via {@link #getDate} and added via {@link #addDate}.
    */
    public static final int DATE = 2;

    /**
    * Data type indicating data is of int primitive data type.
    * Data associated with <code>INT</code> is
    * retrieved via {@link #getInt} and added via {@link #addInt}.
    */
    public static final int INT = 3;

    /**
    * Data type indicating data is a String object.
    * Data associated with <code>STRING</code> is
    * retrieved via {@link #getString} and added via {@link #addString}.
    */
    public static final int STRING = 4;

    /**
    * Data type indicating data is a array of related fields returned in a
    * string array. Data associated with <code>STRING_ARRAY</code> is
    * retrieved via {@link #getStringArray} and added via {@link #addStringArray}.
    */
    public static final int STRING_ARRAY = 5;

    /**
     * Constant indicating that no additional attributes are applicable to
     * a data value for a field.
     */
    public static final int ATTR_NONE = 0;

    /**
     * Constant indicating the minimum possible value for an extended field
     * constant.
     */
    public static final int EXTENDED_FIELD_MIN_VALUE = 0x1000000;

    /**
     * Constant indicating the minimum possible value for an extended attribute
     * constant.
     */
    public static final int EXTENDED_ATTRIBUTE_MIN_VALUE = 0x1000000;


    /**
    * Returns the PIMList associated with this item.  An item
    * always have the same list associated with it for its life as
    * an object once a list is associated with the item.
    *
    * @return   the PIMList that this item belongs to.  If the item does
    *           not belong to any list, <code>null</code> is returned.
    */
    public abstract PIMList getPIMList();

    /**
    * This method persists the data in the item to its PIM list.
    * As data is changed in an item through other methods in this class,
    * the data is not immediately written to the underlying data storage for
    * the list.  This method commits that data to the underlying data storage
    * for the list.
    * <p>
    * Some devices may not allow categories in the item to be persisted
    * which are not in the list returned from {@link PIMList#getCategories}.
    * In this case the categories are dropped silently when this method
    * is invoked (this allows data imported using {@link PIM#fromSerialFormat}
    * to be persisted).
    * </p><p>
    * Also note that some field values may be altered during the commit to fit
    * platform restrictions.  A common example of this is Date fields.  If a
    * platform does not support storing its Date values with millisecond
    * granularity, the platform rounds down the given Date value to its
    * nearest value supported by the platform.
    * </p>
    *
    * @throws   PIMException if the commit encounters an error and cannot
    *           complete.  Also thrown if the item does not belong to any
    *           list, or the list is closed or inaccessible.
    * @throws   SecurityException if the application has not been granted
    *           write access to the PIM list or the list is opened READ_ONLY.
    */
    public abstract void commit() throws PIMException;

    /**
    * This method returns a boolean indicating whether any of this item's
    * fields have been modified since the item was retrieved or last
    * committed.
    *
    * @return   boolean true if any fields have been modified since the
    *           item was last retrieved or committed, false otherwise.
    */
    public abstract boolean isModified();

    /**
    * Returns all fields in the item that have data stored for them.
    * This allows quick access to all of the data stored in the item without
    * having to iterate through all supported fields and checking if data
    * is stored for the field or not.
    *
    * @return   int array of fields that have data currently stored for them.
    *           If no fields contain data, a zero length array is returned.
    */
    public abstract int[] getFields();


    /**
    * Get a binary data value for a field from the item.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field from which the data is retrieved.
    * @param    index an index to a particular value associated with the field.
    * @return   a byte array representing a value for the field.
    *           The value is an inline binary data representation
    *           in a "B" binary encoded string as defined by [IETF RFC 2047].
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract byte[] getBinary(int field, int index);

    /**
    * Adds a binary data value to a field in the item.  The value is
    * appended as the last data value in the field's array, similar to
    * <code>Vector.addElement</code>.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field to which the value belongs.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The data to add to the field. The
    *           data is an inline binary data representation and must be in
    *           a "B" binary encoded string as defined by [IETF RFC 2047].
    * @param    offset int indicating the offset into the provided byte array
    *           from where to start reading the binary data.
    * @param    length int indicating the number of bytes to read from starting
    *           from the offset in the byte array.  If the number of bytes 
	*			available from the array are less than the length, only the
	*			remaining bytes are provided and the field's resulting binary data 
	*			length is <code>value.length - offset</code>.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class, the field does not have a data type
	*			of <code>BINARY</code>, offset is negative, offset is greater
	*			than or equal to the length of the value byte array, length 
	*			is zero or negative, or the value array is length 0.
    * @throws   NullPointerException if <code>value</code> is <code>null</code>.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   FieldFullException if the field already contains the maximum
    *           number of data values it can hold.
    */
    public abstract void addBinary(int field, int attributes, byte[] value,
                                    int offset, int length);

    /**
    * Sets an existing binary data value in a field to a new value.  The value
    * located at the provided index is set, similar to
    * <code>Vector.setElementAt</code>.  This method cannot be used to add new
    * values to a field; use {@link #addBinary}.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field to which the value belongs.
    * @param    index an index to a particular value associated with the field.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The new value of the data at the field's index. The
    *           data is an inline binary data representation and must be in
    *           a "B" binary encoded string as defined by [IETF RFC 2047].
    * @param    offset int indicating the offset into the provided byte array
    *           from where to start reading the binary data.
    * @param    length int indicating the number of bytes to read starting
    *           from the offset in the byte array.  If the number of bytes 
	*			available from the array are less than the length, only the
	*			remaining bytes are provided and the field's resulting binary 
	*			data length is <code>value.length - offset</code>.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class, if <code>offset</code> is negative,
	*			<code>length</code> is less than or equal to zero, or
	*			<code>value</code> is zero length.
    * @throws   NullPointerException if <code>value</code> is <code>null</code>.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    */
    public abstract void setBinary(int field, int index, int attributes,
                                    byte[] value, int offset, int length);

    /**
    * Get a date value from a field in the item.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field from which the data is retrieved.
    * @param    index an index to a particular value associated with the field.
    * @return   a date representing a value of the field, returned in the same
    *           long format as java.util.Date, which is
    *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract long getDate(int field, int index);

    /**
    * Adds a date value to a field in the item. The value is
    * appended as the last data value in the field's array, similar to
    * <code>Vector.addElement</code>.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    * </P><P>
    * Note that the value provided may be rounded-down by an implementation due
    * to platform restrictions.  For example, should a native PIM database on
    * support date values with granularity in terms of seconds, then the
    * provided date value is rounded down to the nearest second.
    * </p>
    *
    * @param    field The field to which the value belongs.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The data to add to the field. The date must be expressed
    *           in the same long value format as java.util.Date, which is
    *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class or the field does not have a data 
				type of <code>DATE</code>.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   FieldFullException if the field already contains the maximum
    *           number of data values it can hold.
    */
    public abstract void addDate(int field, int attributes, long value);

    /**
    * Sets an existing date data value in a field to a new value.  The value
    * located at the provided index is set, similar to
    * <code>Vector.setElementAt</code>. This method cannot be used to add new
    * values to a field; use {@link #addDate}.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    * </P><P>
    * Note that the value provided may be rounded-down by an implementation due
    * to platform restrictions.  For example, should a native PIM database only
    * support date values with granularity in terms of seconds, then the
    * provided date value is rounded down to the nearest second.
    * </p>
    *
    * @param    field The field to which the value belongs.
    * @param    index an index to a particular value associated with the field.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The new value of the data at the field's index.
    *           The date must be expressed
    *           in the same long value format as java.util.Date, which is
    *           milliseconds since the epoch (00:00:00 GMT, January 1, 1970).
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class, or the field cannot be set to the
	*			value provided (e.g. the value is not valid for that field or
	*			this field's value is read-only).
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    */
    public abstract void setDate(int field, int index,
                                 int attributes, long value);

    /**
    * Get an integer value from a field in the item.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field from which the data is retrieved.
    * @param    index an index to a particular value associated with the field.
    * @return   an int representing a value of the field.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract int getInt(int field, int index);

    /**
    * Adds an integer value to a field in the item. The value is
    * appended as the last data value in the field's array, similar to
    * <code>Vector.addElement</code>.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field to which the value belongs.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The data to add to the field.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class or the field does not have a data 
				type of <code>INT</code>.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   FieldFullException if the field already contains the maximum
    *           number of data values it can hold.
    */
    public abstract void addInt(int field, int attributes, int value);

    /**
    * Sets an existing int data value in a field to a new value.  The value
    * located at the provided index is set, similar to
    * <code>Vector.setElementAt</code>.  This method cannot be used to add new
    * values to a field; use {@link #addInt}.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field to which the value belongs.
    * @param    index an index to a particular value associated with the field.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The new value of the data at the field's index.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class, or the field cannot be set to the
	*			value provided (e.g. the value is not valid for that field or
	*			this field's value is read-only).
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    */
    public abstract void setInt(int field, int index, int attributes, int value);

    /**
    * Get a String value from a field in the item.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.  The returned
    * string contains no "escaped" characters that may have been used when
    * importing the field's data.
    *
    * @param    field The field from which the data is retrieved.
    * @param    index an index to a particular value associated with the field.
    * @return   a String representing a value of the field.  A null String
    *           (e.g. String("")) is a valid data value for string.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract String getString(int field, int index);

    /**
    * Adds a String value to a field in the item. The value is
    * appended as the last data value in the field's array, similar to
    * <code>Vector.addElement</code>.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method. Values given to
    * this method should not be "escaped"; i.e. having special characters
    * marked in the string by a particular escape character.  Any characters
    * that requiring special handling such as these for importing and exporting
    * is handled transparently by toSerialFormat and fromSerialFormat.
    *
    * @param    field The field to which the value belongs.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The data to add to the field. A null String
    *           (e.g. String("")) is a valid data value for string.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class or the field does not have a data 
				type of <code>STRING</code>.
    * @throws   NullPointerException if <code>value</code> is <code>null</code>.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   FieldFullException if the field already contains the maximum
    *           number of data values it can hold.
    */
    public abstract void addString(int field, int attributes, String value);

    /**
    * Sets an existing String data value in a field to a new value.  The value
    * located at the provided index is set, similar to
    * <code>Vector.setElementAt</code>. This method cannot be used to add new
    * values to a field; use {@link #addString}.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method. Values given to
    * this method should not be "escaped"; i.e. having special characters
    * marked in the string by a particular escape character.  Any characters
    * that requiring special handling such as these for importing and exporting
    * is handled transparently by toSerialFormat and fromSerialFormat.
    *
    * @param    field The field to which the value belongs.
    * @param    index an index to a particular value associated with the field.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The new value of the data at the field's index.
    *           A null String (e.g. String("")) is a valid data value for string.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class, or the field cannot be set to the
	*			value provided (e.g. the value is not valid for that field or
	*			this field's value is read-only).
    * @throws   NullPointerException if <code>value</code> is <code>null</code>.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    */
    public abstract void setString(int field, int index,
                                   int attributes, String value);

    /**
    * Get a boolean value from a field in the item.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field from which the data is retrieved.
    * @param    index an index to a particular value associated with the field.
    * @return   a boolean representing a value of the field.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract boolean getBoolean(int field, int index);

    /**
    * Adds a boolean value to a field in the item. The value is
    * appended as the last data value in the field's array, similar to
    * <code>Vector.addElement</code>.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field to which the value belongs.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The data to add to the field.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class or the field does not have a data 
				type of <code>BOOLEAN</code>.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   FieldFullException if the field already contains the maximum
    *           number of data values it can hold.
    */
    public abstract void addBoolean(int field, int attributes, boolean value);

    /**
    * Sets an existing boolean data value in a field to a new value.  The value
    * located at the provided index is set, similar to
    * <code>Vector.setElementAt</code>. This method cannot be used to add new
    * values to a field; use {@link #addBoolean}.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field to which the value belongs.
    * @param    index an index to a particular value associated with the field.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The new value of the data at the field's index.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class, or the field cannot be set to the
	*			value provided (e.g. the value is not valid for that field or
	*			this field's value is read-only).
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    */
    public abstract void setBoolean(int field, int index,
                                    int attributes, boolean value);
    /**
    * Get an array of related values from a field in the item.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    * </P><P>
    * Not all elements in the array are required to be supported by the item.
    * If an array index is not supported by this item, <code>null</code> is
    * the value for the String at the array index.
    * </P>
    *
    * @param    field The field from which the data is retrieved.
    * @param    index an index to a particular value associated with the field.
    * @return   a String array representing a group of related values from the
    *           field.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract String[] getStringArray(int field, int index);

    /**
    * Adds an array of related string values as a single entity to a field in
    * the item. The array is appended as the last data value in the field's
    * array of values, similar to <code>Vector.addElement</code>.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    * </P><P>
    * Not all elements in the array are required to be supported by the item.
    * If a String is provided at an array index that is not supported by this
    * item, that value is silently discarded.
    * {@link PIMList#isSupportedArrayElement} should be used to verify the
    * validity of the array element prior to invoking this method.
    * </P>
    *
    * @param    field The field to which the value belongs.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The data to add to the field.  At least one index in the
    *           array must contain a String object.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class or all of the strings in the array
    *           are <code>null</code> or the field does not have a data 
				type of <code>STRING_ARRAY</code>.
    * @throws   NullPointerException if <code>value</code> is <code>null</code>.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   FieldFullException if the field already contains the maximum
    *           number of data values it can hold.
    */
    public abstract void addStringArray(int field, int attributes, String[] value);

    /**
    * Sets an existing String array data value in a field to a new value.  The
    * value located at the provided index is set, similar to
    * <code>Vector.setElementAt</code>. This method cannot be used to add new
    * values to a field; use {@link #addStringArray}.
    * {@link PIMList#isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    * </P><P>
    * Not all elements in the array are required to be supported by the item.
    * If a String is provided at an array index that is not supported by this
    * item, that value is silently discarded.
    * {@link PIMList#isSupportedArrayElement} should be used to verify the
    * validity of the array element prior to invoking this method.
    * </P>
    *
    * @param    field The field to which the value belongs.
    * @param    index an index to a particular value associated with the field.
    * @param    attributes a bit array specifying any optional attributes
    *           describing this value.  These attributes are a hint to the
    *           about the value's characteristics and some or all may be ignored
    *           by this method due to platform restrictions.  Attributes
    *           that are invalid or not applicable are also ignored.
    * @param    value The new value of the data at the field's index.
    *           At least one index in the
    *           array must contain a String object.
    * @throws   NullPointerException if <code>value</code> is <code>null</code>.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class, all of the strings in the array
    *           are <code>null</code>, or the field cannot be set to the
	*			value provided (e.g. the value is not valid for that field or
	*			this field's value is read-only).
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    * @throws   IndexOutOfBoundsException if the index is negative or greater
    *           than or equal to the number of values currently contained in the
    *           field.
    */
    public abstract void setStringArray(int field, int index,
                                        int attributes, String[] value);

    /**
     * Returns the number of data values currently set in a particular field.
     *
     * @param   field The field from which the current count of values is
     *          returned.
     * @return  int indicating the number of values currently assigned to the
     *          field in this item.
     * @throws  IllegalArgumentException if the field is not valid
     *          for the implementing class.
     * @throws  UnsupportedFieldException if the field is not supported in
     *          the implementing instance of the class.
     */
    public abstract int countValues(int field);

    /**
     * Removes the value at the given index for the indicated field in this
     * item.  Note that all indexes in the field's array are guaranteed by the
     * implementation to contain an assigned value.  Therefore, removing fields
     * from a field's array may cause compacting of the array and reindexing of
     * the data values. This is similar behavior to the method
     * <code>Vector.removeElement(Object)</code>.
     *
     * @param   field The field from which the data is removed.
     * @param   index an index to a particular value associated with the field.
     * @throws  IllegalArgumentException if the field is not valid
     *          for the implementing class.
     * @throws  IndexOutOfBoundsException if the index is negative or greater
     *          than or equal to the number of values currently contained in the
     *          field.
     * @throws  UnsupportedFieldException if the field is not supported in
     *          the implementing instance of the class.
     */
    public abstract void removeValue(int field, int index);

    /**
     * Gets the actual attributes associated with the data value at the given
     * index for the indicated field. The attributes are returned in an int
     * that represents an array of attribute bits.
     *
     * @param   field The field from which the data is removed.
     * @param   index an index to a particular value associated with the field.
     * @return  int representing the attributes assigned to the value at the
     *          given field and index.  The int contains attribute values
     *          logically OR'd together in a bit array.  Individual attributes
     *          can be checked for in this bit array by using bitwise AND (&)
     *          with the attribute and the array.
     * @throws  IllegalArgumentException if the field is not valid
     *          for the implementing class.
     * @throws  IndexOutOfBoundsException if the index is negative or greater
     *          than or equal to the number of values currently contained in the
     *          field.
     * @throws  UnsupportedFieldException if the field is not supported in
     *          the implementing instance of the class.
     */
    public abstract int getAttributes(int field, int index);

    /**
    * Adds a category to this item. It is recommended to use the category
    * names defined in {@link PIMList#getCategories} from the list associated
    * with this item.  Some devices may not allow categories to be added
    * which are not in the list returned from {@link PIMList#getCategories}.
    * In this case a <code>PIMException</code> may be thrown. If the given
    * category is already associated with this item, the category is not
    * added again and the method call is considered successful and returns.
    * <p>
    * The category names are case sensitive in this API, but not necessarily in
    * the underlying implementation. For example, "Work" and "WORK" map
    * to the same underlying category if the platform's implementation of
    * categories is case-insensitive; adding both separately would result in
    * the item being assigned to only one category in this case.
    *
    * @param    category the category to add
    * @throws   NullPointerException if <code>category</code> is
    *           <code>null</code>.
    * @throws   PIMException may be thrown if category is not in the list's
    *           category list and the list prevents that condition from
    *           occurring.  Also thrown if categories are not supported in the
    *           implementation. Also thrown if the max categories that this
    *           item can be assigned to is exceeded.
    */
    public abstract void addToCategory(String category) throws PIMException;

    /**
    * Remove a category from this item. If the given category is already
    * removed from this item, the method call is considered successful and
    * returns.
    * <p>
    * The category names are case sensitive in this API, but not necessarily in
    * the underlying implementation. For example, "Work" and "WORK" map
    * to the same underlying category if the platform's implementation of
    * categories is case-insensitive; removing both separately would result in
    * the item being removed from only one category in this case.
    *
    * @param    category the category to remove
    * @throws   NullPointerException if <code>category</code> is <code>null</code>
    */
    public abstract void removeFromCategory(String category);

    /**
    * Returns all the categories for that the item belongs to.
    * If there are no categories assigned to this item, a zero length
    * array is returned.
    *
    * @return a string array of all the categories for the item.
    **/
    public abstract String[] getCategories();

    /**
    * Returns the maximum number of categories that this item can be
    * assigned to.
    *
    * @return   int the number of categories this item can be assigned to.
    *           0 indicates no category support and -1 indicates there is
    *           no limit to the number of categories that this item can
    *           be assigned to.
    */
    public abstract int maxCategories();
}