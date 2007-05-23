package javax.microedition.location;

import ucl.cs.testingEmulator.location.LandmarkStoreManager;

/**
 * The LandmarkStore class provides methods to store, delete and retrieve
 * landmarks from a persistent landmark store. There is one default landmark
 * store and there may be multiple other named landmark stores. The
 * implementation may support creating and deleting landmark stores by the
 * application. All landmark stores MUST be shared between all J2ME applications
 * and MAY be shared with native applications in the terminal. Named landmark
 * stores have unique names in this API. If the underlying implementation allows
 * multiple landmark stores with the same name, it must present them with unique
 * names in the API e.g. by adding some postfix to those names that have
 * multiple instances in order to differentiate them. Because native landmark
 * stores may be stored as files in a file system and file systems have
 * sometimes limitations for the allowed characters in file names, the
 * implementations MUST support all other Unicode characters in landmark store
 * names except the following list: 0x0000...0x001F control characters 0x005C
 * '\' 0x002F '/' 0x003A ':' 0x002A '*' 0x003F '?' 0x0022 '"' 0x003C '<' 0x003E
 * '>' 0x007C '|' 0x007F...0x009F control characters 0xFEFF Byte-order-mark
 * 0xFFF0...0xFFFF Support for the listed characters is not required and
 * therefore applications are strongly encouraged not to use the characters
 * listed above in landmark store names in order to ensure interoperability of
 * the application on different platform implementations. The Landmarks have a
 * name and may be placed in a category or several categories. The category is
 * intended to group landmarks that are of similar type to the end user, e.g.
 * restaurants, museums, etc. The landmark names are strings that identify the
 * landmark to the end user. The category names describe the category to the end
 * user. The language used in the names may be any and depends on the
 * preferences of the end user. The names of the categories are unique within a
 * LandmarkStore. However, the names of the landmarks are not guaranteed to be
 * unique. Landmarks with the same name can appear in multiple categories or
 * even several Landmarks with the same name in the same category. The Landmark
 * objects returned from the getLandmarks methods in this class shall guarantee
 * that the application can read a consistent set of the landmark data valid at
 * the time of obtaining the object instance, even if the landmark information
 * in the store is modified subsequently by this or some other application. The
 * Landmark object instances can be in two states: initially constructed by an
 * application belongs to a LandmarkStore A Landmark object belongs to a
 * LandmarkStore if it has been obtained from the LandmarkStore using
 * getLandmarks or if it has been added to the LandmarkStore using addLandmark.
 * A Landmark object is initially constructed by an application when it has been
 * constructed using the constructor but has not been added to a LandmarkStore
 * using addLandmark. Note that the term "belongs to a LandmarkStore" is defined
 * above in a way that "belong to a LandmarkStore" has an different meaning than
 * the landmark "being currently stored in a LandmarkStore". According to the
 * above definition, a Landmark object instance may be in a state where it is
 * considered to "belong to a LandmarkStore" even when it is not stored in that
 * LandmarkStore (e.g. if the landmark is deleted from the LandmarkStore using
 * deleteLandmark method, the Landmark object instance still is considered to
 * "belong to this LandmarkStore"). The landmark stores created by an
 * application and landmarks added in landmark stores persist even if the
 * application itself is deleted from the terminal. Accessing the landmark store
 * may cause a SecurityException, if the calling application does not have the
 * required permissions. The permissions to read and write (including add and
 * delete) landmarks are distinct. An application having e.g. a permission to
 * read landmarks wouldn't necessarily have the permission to delete them. The
 * permissions (names etc.) for the MIDP 2.0 security framework are defined
 * elsewhere in this specification.
 */
public class LandmarkStore {
	
 
	
	
	
	/**
	 * Adds a category to this LandmarkStore. All implementations must support
	 * names that have length up to and including 32 characters. If the provided
	 * name is longer it may be truncated by the implementation if necessary.
	 */
	public void addCategory(java.lang.String categoryName)
			throws javax.microedition.location.LandmarkException,
			java.io.IOException {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return; // TODO codavaj!!
	}

	/**
	 * Adds a landmark to the specified group in the landmark store. If some
	 * textual String field inside the landmark object is set to a value that is
	 * too long to be stored, the implementation is allowed to automatically
	 * truncate fields that are too long. However, the name field MUST NOT be
	 * truncated. Every implementation shall be able to support name fields that
	 * are 32 characters or shorter. Implementations may support longer names
	 * but are not required to. If an application tries to add a Landmark with a
	 * longer name field than the implementation can support,
	 * IllegalArgumentException is thrown. When the landmark store is empty,
	 * every implementation is required to be able to store a landmark where
	 * each String field is set to a 30 character long string. If the Landmark
	 * object that is passed as a parameter is an instance that belongs to this
	 * LandmarkStore, the same landmark instance will be added to the specified
	 * category in addition to the category/categories which it already belongs
	 * to. If the landmark already belongs to the specified category, this
	 * method returns with no effect. If the landmark has been deleted after
	 * obtaining it from getLandmarks, it will be added back when this method is
	 * called. If the Landmark object that is passed as a parameter is an
	 * instance initially constructed by the application using the constructor
	 * or an instance that belongs to a different LandmarkStore, a new landmark
	 * will be created in this LandmarkStore and it will belong initially to
	 * only the category specified in the category parameter. After this method
	 * call, the Landmark object that is passed as a parameter belongs to this
	 * LandmarkStore.
	 */
	public void addLandmark(javax.microedition.location.Landmark landmark,
			java.lang.String category) throws java.io.IOException {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return; // TODO codavaj!!
	}

	/**
	 * Creates a new landmark store with a specified name. All LandmarkStores
	 * are shared between all J2ME applications and may be shared with native
	 * applications. Implementations may support creating landmark stores on a
	 * removable media. However, the Java application is not able to directly
	 * choose where the landmark store is stored, if the implementation supports
	 * several storage media. The implementation of this method may e.g. prompt
	 * the end user to make the choice if the implementation supports several
	 * storage media. If the landmark store is stored on a removable media, this
	 * media might be removed by the user possibly at any time causing it to
	 * become unavailable. A newly created landmark store does not contain any
	 * landmarks. Note that the landmark store name MAY be modified by the
	 * implementation when the store is created, e.g. by adding an
	 * implementation specific post-fix to differentiate stores on different
	 * storage drives as described in the class overview. Therefore, the
	 * application needs to use the listLandmarkStores method to discover the
	 * form the name was stored as. However, when creating stores to the default
	 * storage location, it is recommended that the implementation does not
	 * modify the store name but preserves it in the form it was passed to this
	 * method. It is strongly recommended that this method is implemented as
	 * character case preserving for the store name.
	 */
	public static void createLandmarkStore(java.lang.String storeName)
			throws java.io.IOException,
			javax.microedition.location.LandmarkException {
		LandmarkStoreManager.getInstance().createLandmarkStore(storeName);
	}

	/**
	 * Removes a category from this LandmarkStore. The category will be removed
	 * from all landmarks that are in that category. However, this method will
	 * not remove any of the landmarks, only the associated category information
	 * from the landmarks. If a category with the supplied name does not exist
	 * in this LandmarkStore, the method returns silently with no error.
	 */
	public void deleteCategory(java.lang.String categoryName)
			throws javax.microedition.location.LandmarkException,
			java.io.IOException {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return; // TODO codavaj!!
	}

	/**
	 * Deletes a landmark from this LandmarkStore. This method removes the
	 * specified landmark from all categories and deletes the information from
	 * this LandmarkStore. The Landmark instance passed in as the parameter must
	 * be an instance that belongs to this LandmarkStore. If the Landmark
	 * belongs to this LandmarkStore but has already been deleted from this
	 * LandmarkStore, then the request is silently ignored and the method call
	 * returns with no error. Note that LandmarkException is thrown if the
	 * Landmark instance does not belong to this LandmarkStore, and this is
	 * different from not being stored currently in this LandmarkStore.
	 */
	public void deleteLandmark(javax.microedition.location.Landmark lm)
			throws java.io.IOException,
			javax.microedition.location.LandmarkException {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return; // TODO codavaj!!
	}

	/**
	 * Delete a landmark store with a specified name. All the landmarks and
	 * categories defined in the named landmark store are irrevocably removed.
	 * If a landmark store with the specified name does not exist, this method
	 * returns silently without any error. Note that the landmark store names
	 * MAY be handled as either case-sensitive or case-insensitive (e.g. Unicode
	 * collation algorithm level 2). Therefore, the implementation MUST accept
	 * the names in the form returned by listLandmarkStores and MAY accept the
	 * name in other variations of character case.
	 */
	public static void deleteLandmarkStore(java.lang.String storeName)
			throws java.io.IOException,
			javax.microedition.location.LandmarkException {
		LandmarkStoreManager.getInstance().deleteLandmarkStore(storeName);
	}

	/**
	 * Returns the category names that are defined in this LandmarkStore. The
	 * language and locale used for these names depends on the implementation
	 * and end user settings. The names shall be such that they can be displayed
	 * to the end user and have a meaning to the end user.
	 */
	public java.util.Enumeration getCategories() {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return null; // TODO codavaj!!
	}

	/**
	 * Gets a LandmarkStore instance for storing, deleting and retrieving
	 * landmarks. There must be one default landmark store and there may be
	 * other landmark stores that can be accessed by name. Note that the
	 * landmark store names MAY be handled as either case-sensitive or
	 * case-insensitive (e.g. Unicode collation algorithm level 2). Therefore,
	 * the implementation MUST accept the names in the form returned by
	 * listLandmarkStores and MAY accept the name in other variations of
	 * character case.
	 */
	public static javax.microedition.location.LandmarkStore getInstance(
			java.lang.String storeName) {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return null; // TODO codavaj!!
	}

	/**
	 * Lists all landmarks stored in the store.
	 */
	public java.util.Enumeration getLandmarks() throws java.io.IOException {
		return null; // TODO codavaj!!
	}

	/**
	 * Lists all the landmarks that are within an area defined by bounding
	 * minimum and maximum latitude and longitude and belong to the defined
	 * category, if specified. The bounds are considered to belong to the area.
	 * If minLongitude <= maxLongitude, this area covers the longitude range
	 * [minLongitude, maxLongitude]. If minLongitude > maxLongitude, this area
	 * covers the longitude range [-180.0, maxLongitude] and [minLongitude,
	 * 180.0). For latitude, the area covers the latitude range [minLatitude,
	 * maxLatitude].
	 */
	public java.util.Enumeration getLandmarks(java.lang.String category,
			double minLatitude, double maxLatitude, double minLongitude,
			double maxLongitude) throws java.io.IOException {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return null; // TODO codavaj!!
	}

	/**
	 * Gets the Landmarks from the storage where the category and/or name
	 * matches the given parameters.
	 */
	public java.util.Enumeration getLandmarks(java.lang.String category,
			java.lang.String name) throws java.io.IOException {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return null; // TODO codavaj!!
	}

	/**
	 * Lists the names of all the available landmark stores. The default
	 * landmark store is obtained from getInstance by passing null as the
	 * parameter. The null name for the default landmark store is not included
	 * in the list returned by this method. If there are no named landmark
	 * stores, other than the default landmark store, this method returns null.
	 * The store names must be returned in a form that is directly usable as
	 * input to getInstance and deleteLandmarkStore.
	 */
	public static java.lang.String[] listLandmarkStores()
			throws java.io.IOException {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return new java.lang.String[0]; // TODO codavaj!!
	}

	/**
	 * Removes the named landmark from the specified category. The Landmark
	 * instance passed in as the parameter must be an instance that belongs to
	 * this LandmarkStore. If the Landmark is not found in this LandmarkStore in
	 * the specified category or if the parameter is a Landmark instance that
	 * does not belong to this LandmarkStore, then the request is silently
	 * ignored and the method call returns with no error. The request is also
	 * silently ignored if the specified category does not exist in this
	 * LandmarkStore. The landmark is only removed from the specified category
	 * but the landmark information is retained in the store. If the landmark no
	 * longer belongs to any category, it can still be obtained from the store
	 * by passing null as the category to getLandmarks.
	 */
	public void removeLandmarkFromCategory(
			javax.microedition.location.Landmark lm, java.lang.String category)
			throws java.io.IOException {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return; // TODO codavaj!!
	}

	/**
	 * Updates the information about a landmark. This method only updates the
	 * information about a landmark and does not modify the categories the
	 * landmark belongs to. The Landmark instance passed in as the parameter
	 * must be an instance that belongs to this LandmarkStore. This method can't
	 * be used to add a new landmark to the store.
	 */
	public void updateLandmark(javax.microedition.location.Landmark lm)
			throws java.io.IOException,
			javax.microedition.location.LandmarkException {
		throw new java.lang.RuntimeException("Not yet implemented!");
		//return; // TODO codavaj!!
	}

}
