package javax.microedition.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.util.Enumeration;

/**
 * Represents the common functionality of a PIM list. PIMLists contain zero
 * or more PIMItems (represented by the class {@link PIMItem}. A PIMList
 * allows retrieval of all or some of the PIMItems contained in the list.
 * <P>
 * PIMList maintain categories and fields.  Categories are logical
 * groupings for the PIMItems contained in the list.  Each PIMList can
 * support zero or more categories.  Categories are represented by unique
 * String names.
 * </P><P>
 * Each PIMList also defines what fields are included in the items that get
 * stored in the list.  While the definition of all possible fields reside
 * in PIMItem, each PIMList dictates which fields are supported for storage
 * and retrieval in the list's items at runtime.
 *
 * @since PIM 1.0
 */

public interface PIMList {

    /**
     * Constant for the {@link #itemsByCategory} method to indicate to search
     * for uncategorized items. The value <code>null</code> is assigned to
     * the string <code>UNCATEGORIZED</code>.
     */
    public static final String UNCATEGORIZED = null;

    /**
     * Provides the name of the list.  All PIMLists have a non-null
	 * name associated with it.
     *
     * @return  a String representation of the list name.
     */
    public abstract String getName();

    /**
    * Closes the list, releasing any resources for this list.
    * A <code>PIMException</code> is thrown for any subsequent method
    * invocations for the class instance.
    *
    * @throws 	PIMException If the list is no longer accessible.
    */
    public abstract void close() throws PIMException;

    /**
    * Return an Enumeration of all items in the list. The order is undefined.
    *
    * @return   an Enumeration of all items.
    * @throws 	PIMException If an error occurs or the list is no longer
    *           accessible or closed.
    * @throws	java.lang.SecurityException if the application is not given
    *           permission to read the PIM list or the list is opened
    *           WRITE_ONLY.
    */
    public abstract Enumeration items() throws PIMException;

    /**
    * Return an Enumeration of all items in the list that contain fields
    * that match all of those fields specified in the given matching item.
    * An item that matches may have more data than is specified in the
    * matching item, but must at least match all fields specified in the
    * matching item.  Only fields that have values set
    * within the matching item are used for matching comparisons (i.e. fields
    * returned by {@link PIMItem#getFields} for the matching item).
    * <P>
    * Matching is done with the following rules and may be locale dependent:
    * <ul>
    * <li>
    * When matching values for a particular field, the indexes associated with
    * a value in a field is not used and irrelevant.  A match is considered
    * positive if the searched-for value appears in a field at any index. For
    * example, a string value of "Bob" at index 0 in a PIMItem field
    * matches "Bob" at index 0, index 1, or any index in that field in another
    * PIMItem.
    * </li><li>
    * When matching values for a particular field, all of the attributes
    * specified in the search-for value must be a subset or complete set of
    * attributes in a compared value in order for the match to be considered
    * positive.  For example, if <code>ATTR_HOME</code> were specified with the
    * searched-for value, then values with attributes <code>ATTR_HOME</code>
    * or <code>ATTR_HOME|ATTR_MOBILE</code> (or any other attributes OR'd with
    * <code>ATTR_HOME</code>) would satisfy this attribute matching rule.
    * </li><li>
    * <code>PIMItem.ATTR_NONE</code> is an exception to the above rule such that
    * <code>PIMItem.ATTR_NONE</code> is an unspecified member of every attribute
    * set and therefore matches all attribute sets.  For example, if
    * <code>ATTR_NONE</code> were specified with a searched-for value of "Bob",
    * then all "Bob" values regardless of its actual assigned attributes would
    * satisfy this attribute matching rule.
    * </li><li>
    * Implementations have the option of ignoring attribute hints assigned per
    * value in a field.  The evaluation of the attribute hints are applied
    * prior to comparison by the implementation.  For example, if
    * <code>ATTR_HOME|ATTR_MOBILE</code> were specified with the
    * searched-for value but <code>ATTR_MOBILE</code> was not supported in the
    * particular list being searched, then this method would use
    * <code>ATTR_HOME</code> as the attribute to match for according to the
    * other attribute maching rules.  To avoid this situation in which the
    * attribute sets used may different than intended, it is recommended to
    * verify the attribute's validity using {@link #isSupportedAttribute} prior
    * to setting the attribute in the searched-for PIMItem.
    * </li><li>
    * For fields that store Strings as values, matching is defined as true if
	* the input String field value is contained anywhere within the field's
	* stored String value. The locale of the device MAY be used as a basis for 
	* the matching rules. Comparisons are case insensitive if case is supported
	* in the current locale.  For example, if the matching item NOTE field is set
    * to "pho", a positive match occurs in the strings "Mobile phone" and
    * "THIS IS A PHONE NOTE" as well as the string "telephone".  If an empty
    * string is provided in the matching item (i.e. String("")), the empty
    * string matches all set values for the field.
    * </li><li>
    * All the other data types besides Strings must match data values exactly
    * (i.e. equals()).
    * </li>
    * </ul>
    *
    * @param    matchingItem the item whose set fields are used for finding
    *           matching items.  This item must have been created using
    *           this same list.
    * @return	an enumeration of all items that contains fields that match
    *           all of the fields of the item parameter.
    * @throws 	PIMException If an error occurs or the list is no longer
    *           accessible or closed.
    * @throws	java.lang.IllegalArgumentException If the item provided did not
    *           originate from this list.
    * @throws   NullPointerException if <code>matching</code> is <code>null</code>.
    * @throws	java.lang.SecurityException if the application is not given
    *           permission to read the PIM list or the list is opened
    *           WRITE_ONLY.
    */
    public abstract Enumeration items(PIMItem matchingItem)
          throws PIMException;

    /**
    * Return an Enumeration of all items in the list that contain at least
    * one String field data value that matches the string value provided.
    * Only fields that have values set within the matching item are
    * used for matching comparisons (i.e. fields
    * returned by {@link PIMItem#getFields} for the matching item).
    * NOTE: this method may not be as optimized as {@link #items(PIMItem)}
    * so that method should be used instead of this one if applicable.
    * <P>
    * Matching is done with the following rules and may be locale dependent:
    * <ul>
    * <li>
    * The locale of the device MAY be used as a basis for 
	* the matching rules. Comparisons are case insensitive if case is supported
	* in the current locale. 
    * </li><li>
    * A match is positive if
	* the input String value is contained anywhere within any String field's
	* stored String value. For example, if the matching item NOTE
    * field is set to "pho", a positive match occurs in the strings
    * "Mobile phone" and "THIS IS A PHONE NOTE" as well as in the string
    * "telephone".
    * </li><li>
    * If an empty string is provided in the matching item
    * (i.e. String("")), the empty string matches all set values for the field.
    * </li></ul>
    *
    * @param    matchingValue a string value to which all set String field
    *           values are compared.
    * @return	an enumeration of all items that contain at least one field
    *           that matches the string parameter.
    * @throws 	PIMException If an error occurs or the list is no longer
    *           accessible or closed.
    * @throws   NullPointerException if <code>matching</code> is <code>null</code>.
    * @throws	java.lang.SecurityException if the application is not given
    *           permission to read the PIM list or the list is opened
    *           WRITE_ONLY.
    */
    public abstract Enumeration items(String matchingValue) throws PIMException;

    /**
     * Returns an enumeration of all items in the PIM list that match the
     * provided category.
     * </P><P>
     * Category matching rules:
     * <ul>
     * <li>A PIM list item that contains the given category is in the
     * returned enumeration.</li>
     * <li>A category string that matches none of the categories of the items
     * in the PIM list returns an empty enumeration.</li>
     * <li>Use of the category string <code>UNCATEGORIZED</code> returns an
     * enumeration of all PIM items of the PIM list that have no category
     * assignments.</li>
     * <li>A null category string is equivalent to the category string
     * <code>UNCATEGORIZED</code>.</li>
     * <li>An empty category string <code>""</code> is considered a valid string
     * parameter for comparison with existing categories, e.g. "" matches
     * only a category also named "".</li>
     * <li>The full category string is used for comparison, and must return
     * true for an <code>String.equals()</code> comparison.</li>
     * </ul>
     *
     * @param   category A string category to find matching items with.
     * @return  An enumeration of all items in the PIM list that match the
     *          category.
     * @throws  PIMException If an error occurs or the list is no longer
     *          accessible or closed.
     * @throws  SecurityException If the application is not given permission to
     *          read the PIM list or the list is opened
     *          WRITE_ONLY.
     */
    public abstract Enumeration itemsByCategory(String category)
        throws PIMException;

    /**
    * Returns the categories defined for the PIM list.  If there are no
    * categories defined for the PIM list or categories are unsupported for
    * the list, then a zero length array is returned.
    *
    * @return   A string array containing the categories defined for the
    *           PIM list.
    * @throws	PIMException If an error occurs or the list is no longer
    *			accessible or closed.
    */
    public abstract String[] getCategories() throws PIMException;

    /**
     * Returns indication of whether the given category is a valid existing
     * category for this list.
     *
     * @param   category a String representing the category to query for.
     * @return  true if the category indicated is a valid existing category for
     *          this list, false otherwise.
     * @throws  NullPointerException if <code>category</code> is
     *          <code>null</code>.
     * @throws  PIMException If an error occurs or the list is no longer
     *          accessible or closed.
     */
    public abstract boolean isCategory(String category) throws PIMException;

    /**
    * Adds the provided category to the PIM list.  If the given
    * category already exists for the list, the method does not add another
    * category and considers that this method call is successful and returns.
    * </P><P>
    * The category names are case sensitive in this API, but not necessarily in
    * the underlying implementation. For example, "Work" and "WORK" map
    * to the same underlying category if the platform's implementation of
    * categories is case-insensitive; adding both separately would result in
    * only one category being created in this case.
    * </P><P>
    * A string with no characters ("") may or may not be a valid category on a
    * particular platform.  If the string is not a valid category as defined by
	* the platform, a PIMException is thrown when trying to add it.
    *
    * @param    category a String representing a category to add.
    * @throws   PIMException If categories are unsupported, an error occurs,
    *           or the list is no longer accessible or closed. Also returned if
    *           the max number of categories for this list is exceeded, or if
    *           the category name is invalid.
    * @throws	java.lang.SecurityException if the application is not given
    *           permission to write to the PIM list or the list is opened
    *           READ_ONLY.
    * @throws   NullPointerException if <code>category</code> is
    *           <code>null</code>.
    */
    public abstract void addCategory(String category) throws PIMException;

    /**
    * Deletes the indicated category from the PIM list.  If the indicated
    * category is not in the PIM list, this method is treated as
    * successfully completing.
    * <p>
    * The category names are case sensitive in this API, but not necessarily in
    * the underlying implementation. For example, "Work" and "WORK" map
    * to the same underlying category if the platform's implementation of
    * categories is case-insensitive; removing both separately would result in
    * only one category being removed in this case.
    *
    * @param    category a String category.
    * @param    deleteUnassignedItems boolean flag where true indicates to
    *           delete items that no longer have any categories assigned to them
    *           as a result of this method, and where false indicates that no
    *           items are deleted as a result of this method.
    * @throws 	PIMException If categories are unsupported, an error occurs,
    *           or the list is no longer accessible or closed.
    * @throws	java.lang.SecurityException if the application is not given
    *           permission to write to the PIM list or the list is opened
    *           READ_ONLY.
    * @throws   NullPointerException if <code>category</code> is
    *           <code>null</code>.
    */
    public abstract void deleteCategory(String category, boolean deleteUnassignedItems)
             throws PIMException;

    /**
     * Renames a category from an old name to a new name.  All items associated
     * with the old category name are changed to reference the new category
     * name after this method is invoked.  If the new category name is already
     * an existing category, then the items associated with the old category
     * name are associated with the existing category.
     * </P><P>
     * A string with no characters ("") may or may not be a valid category on a
     * particular platform.  If the string is not a category on a platform, a
     * PIMException is thrown when trying to rename a category to it.
     *
     * @param   currentCategory String representing the current category name.
     * @param   newCategory String representing the new category name to use.
     * @throws 	PIMException If categories are unsupported, an error occurs,
     *          the list is no longer accessible or closed, or the category
     *          name is invalid.
     * @throws	java.lang.SecurityException if the application is not given
     *          permission to write to the PIM list or the list is opened
     *          READ_ONLY.
     * @throws  NullPointerException if <code>currentCategory</code> or
     *          <code>newCategory</code> is <code>null</code>.
     */
    public abstract void renameCategory(String currentCategory, String newCategory)
        throws PIMException;

    /**
    * Returns the maximum number of categories that this list can have.
    *
    * @return   int the number of categories supported by this list.
    *           0 indicates no category support and -1 indicates there is no
    *           limit the the number of categories that this list can have.
    */
    public abstract int maxCategories();

    /**
    * Indicates whether or not the given field is supported in this PIM list.
    *
    * @param    field The field to check, as defined by in the class
    *           implementing the PIMItem.
    * @return   true if supported in this list, false otherwise.  Invalid fields
    *           return false.
    */
    public abstract boolean isSupportedField(int field);

    /**
     * Gets all fields that are supported in this list.  All fields supported by
     * this list, including both standard and extended, are returned in this
     * array.
     *
     * @return  an int array containing all fields supported by this list.  The
     *          order of the fields returned is unspecified.  If there are no
     *          supported fields, a zero-length array is returned.
     */
    public abstract int[] getSupportedFields();

    /**
    * Indicates whether or not the given attribute is supported in this PIM list
    * for the indicated field.
    *
    * @param    field The field against which the attribute is checked.
    * @param    attribute The single attribute to check
    * @return   true if supported, false otherwise. Invalid fields and invalid
    *           attributes return false.
    */
    public abstract boolean isSupportedAttribute(int field, int attribute);

    /**
    * Returns an integer array containing all of the supported attributes
    * for the given field.  All attributes supported by this list, including
    * both standard and extended, are returned in this array. The attributes are
    * provided one attribute per entry in the returned integer array.
    *
    * @param    field the field to check
    * @return   an int array of the supported attributes, one
    *           attribute per entry in the array.  If there are no
     *          supported fields, a zero-length array is returned.
    * @throws   java.lang.IllegalArgumentException if field is not a valid
    *           field (i.e. not a standard field and not an extended field).
    *           IllegalArgumentException takes precedence over
    *           UnsupportedFieldException when checking the provided field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract int[] getSupportedAttributes(int field);

    /**
    * Indicates whether or not the given element in a array is supported for the
    * indicated field in this PIM list.
    *
    * @param    stringArrayField The field which has a STRING_ARRAY data type,
    *           as defined by classes implementing the PIMItem interface.
    * @param    arrayElement The element in the array to check, as defined in
    *           the classes implementing the PIMItem interface.
    * @return   true if supported in this list, false otherwise.  Invalid fields
    *           and invalid array elements return false.
    */
    public abstract boolean isSupportedArrayElement(int stringArrayField,
                                                    int arrayElement);

    /**
    * Returns an integer array containing all of the supported elements of a
    * string array for the given field.  The array elements are provided one
    * element per entry in the returned integer array.
    *
    * @param    stringArrayField the field to check
    * @return   an int array representing the supported array elements, one
    *           array element per entry in the array. If there are no
     *          supported array elements, a zero-length array is returned.
    * @throws   java.lang.IllegalArgumentException if field is not a valid
    *           StringArray field (i.e. not a standard field and not an extended
	*			field that has a data type of StringArray).
    *           IllegalArgumentException takes precedence over
    *           UnsupportedFieldException when checking the provided field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract int[] getSupportedArrayElements(int stringArrayField);

    /**
    * Returns an int representing the data type of the data associated with
    * the given field.  This method is useful for platform extended fields.
    * {@link #isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field for which the data type is being queried.
    * @return   int representing the type of the data associated with the
    *           field.
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class.
    *           IllegalArgumentException takes precedence over
    *           UnsupportedFieldException when checking the provided field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract int getFieldDataType(int field);

    /**
    * Returns a String label associated with the given field.  Field labels are
    * provided by the platform and are not changeable by the application.</P><P>
    * {@link #isSupportedField} should be used to verify the field
    * validity for this item prior to invoking this method.
    *
    * @param    field The field for which the label is being
    *           queried.
    * @return   String label for the field. The label is locale specific
    *           (see the microedition.locale system property).
    * @throws   IllegalArgumentException if the field is not valid
    *           for the implementing class.
    *           IllegalArgumentException takes precedence over
    *           UnsupportedFieldException when checking the provided field.
    * @throws   UnsupportedFieldException if the field is not supported in
    *           the implementing instance of the class.
    */
    public abstract String getFieldLabel(int field);

    /**
    * Returns a String label associated with the given attribute.  Attribute
    * labels are provided by the platform and are not changeable by the
    * application.   </P><P>
    * {@link PIMList#isSupportedAttribute} should be used to verify the
    * attribute's validity for this item prior to invoking this method.
    *
    * @param    attribute The attribute for which the label is being queried.
    * @return   String label for the attribute. The label is locale specific
    *           (see the microedition.locale system property).
    * @throws   IllegalArgumentException if the attribute is not valid
    *           for the implementing class.
    *           IllegalArgumentException takes precedence over
    *           UnsupportedFieldException when checking the provided field.
    * @throws   UnsupportedFieldException if the attribute is not supported in
    *           the implementing instance of the class.
    */
    public abstract String getAttributeLabel(int attribute);

    /**
    * Returns a String label associated with the given array element.  Array
    * element labels are provided by the platform and are not changeable by the
    * application.   </P><P>
    * {@link PIMList#isSupportedArrayElement} should be used to verify the
    * array elements's validity for this item prior to invoking this method.
    *
    * @param    stringArrayField The field which has a STRING_ARRAY data type,
    *           as defined by classes implementing the PIMItem interface.
    * @param    arrayElement The element in the array, as defined in
    *           the classes implementing the PIMItem interface.
    * @return   String label for the array element. The label is locale specific
    *           (see the microedition.locale system property).
    * @throws   IllegalArgumentException if the array element or the field is
    *           not valid for the implementing class.
    *           IllegalArgumentException takes precedence over
    *           UnsupportedFieldException when checking the provided parameters.
    * @throws   UnsupportedFieldException if the field or array element is not
    *           supported in the implementing instance of the class.
    */
    public abstract String getArrayElementLabel(int stringArrayField, int arrayElement);

    /**
     * Indicates the total number of data values that a particular field
     * supports in this list.
     *
     * @param   field The field to check for multiple value support.
     * @return  int indicating the number of values that can be stored in the
     *          field.  Additionally, -1 indicates this field
     *          supports having an unlimited number of added values in it, and
     *          0 indicates the field is not supported by this list.
     * @throws  IllegalArgumentException if the field is not valid
     *          for the implementing class.
     */
    public abstract int maxValues(int field);

	/**
	 * Returns the size of the array for the given string array field.  
	 * Used for creating arrays for the string array field.
	 * 
     * @param   stringArrayField The string array field for which its
	 *			array size is returned.
	 * @return int the size of the array of a single data instance of
	 *			a string array field.
     * @throws  IllegalArgumentException if the field is not valid
     *          for the implementing class or not a string array field.
	 */
	public abstract int stringArraySize(int stringArrayField);

}