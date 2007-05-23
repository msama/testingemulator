/**
 *
 */
package ucl.cs.testingEmulator.location;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.location.LandmarkException;

/**
 * @author -Michele Sama- aka -RAX-
 * 
 * University College London Dept. of Computer Science Gower Street London WC1E
 * 6BT United Kingdom
 * 
 * Email: M.Sama (at) cs.ucl.ac.uk
 * 
 * Group: Software Systems Engineering
 * 
 */
public class LandmarkStoreManager {

	private static LandmarkStoreManager INSTANCE = null;

	private PropertyChangeSupport _propertyChangeSupport = null;

	public static final String IS_SUPPORTING_MULIPLE_LANDMARKSTORE = "MultipleLandmarkStore";
	
	public static final String LANDMARKSTORE_ADDED = "LandmarkStoreAdded";
	
	public static final String LANDMARKSTORE_REMOVED = "LandmarkStoreRemoved";
	
	public Vector<String> _landmarkStores=new Vector<String>();

	private boolean _multipleLandmarkStoreSupported = true;

	private static final String DEFAULT_LANDMARKSTORE = "DefaultLandmarkStore.xml";

	private static final String TEMPLATE_LANDMARKSTORE = "LandmarkStoreTemplate.xml";

	public static LandmarkStoreManager getInstance() {
		if (LandmarkStoreManager.INSTANCE == null) {
			LandmarkStoreManager.INSTANCE = new LandmarkStoreManager();
			LandmarkStoreManager.INSTANCE._landmarkStores.addElement(LandmarkStoreManager.DEFAULT_LANDMARKSTORE);
		}
		return LandmarkStoreManager.INSTANCE;
	}

	/**
	 * 
	 */
	protected LandmarkStoreManager() {
		this._propertyChangeSupport = new PropertyChangeSupport(this);
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
	 * become unavailable.
	 * 
	 * A newly created landmark store does not contain any landmarks.
	 * 
	 * Note that the landmark store name MAY be modified by the implementation
	 * when the store is created, e.g. by adding an implementation specific
	 * post-fix to differentiate stores on different storage drives as described
	 * in the class overview. Therefore, the application needs to use the
	 * listLandmarkStores method to discover the form the name was stored as.
	 * However, when creating stores to the default storage location, it is
	 * recommended that the implementation does not modify the store name but
	 * preserves it in the form it was passed to this method. It is strongly
	 * recommended that this method is implemented as character case preserving
	 * for the store name.
	 * 
	 * @param storeName -
	 *            the name of the landmark store to create
	 * @throws java.lang.NullPointerException -
	 *             if the parameter is null
	 * @throws java.lang.IllegalArgumentException -
	 *             if the name is too long or if a landmark store with the
	 *             specified name already exists
	 * 
	 * @throws java.io.IOException -
	 *             if the landmark store couldn't be created due to an I/O error
	 * @throws java.lang.SecurityException -
	 *             if the application does not have permissions to create a new
	 *             landmark store LandmarkException - if the implementation does
	 *             not support creating new landmark stores
	 * 
	 * 
	 */
	public void createLandmarkStore(java.lang.String storeName)
			throws java.io.IOException,
			javax.microedition.location.LandmarkException {

		if (storeName == null) {
			throw new java.lang.NullPointerException(
					"Parameter storeName cannot be null.");
		}

		 /* check if it already exists */
		if (this._landmarkStores.contains(storeName)==true) {
			throw new java.lang.IllegalArgumentException(
					"A LandmarkStore named: " + storeName + " already exists.");
		}

		if (this._multipleLandmarkStoreSupported == false) {
			throw new java.lang.SecurityException(
					"The actual configuration of the LandmarkStore is simulating an implementation that does not support multiple LandmarkStore.");
		}

		try {
			File newfile=FileSystemRegistry.getInstance().openFile(storeName);
			PrintWriter pw = new PrintWriter(newfile);
			BufferedReader br = new BufferedReader(new InputStreamReader(this
					.getClass().getResourceAsStream(
							LandmarkStoreManager.TEMPLATE_LANDMARKSTORE)));
			String line = null;
			while ((line = br.readLine()) != null) {
				pw.write(line);
			}
			pw.flush();
			pw.close();
			br.close();
		} catch (java.io.IOException e) {
			throw new java.io.IOException(
					"I/O error in LandmarkStore creation.");
		}
		this._landmarkStores.addElement(storeName);
		this._propertyChangeSupport.firePropertyChange(LandmarkStoreManager.LANDMARKSTORE_ADDED, null, storeName);
	}

	/**
	 * Delete a landmark store with a specified name. All the landmarks and
	 * categories defined in the named landmark store are irrevocably removed.
	 * 
	 * If a landmark store with the specified name does not exist, this method
	 * returns silently without any error.
	 * 
	 * Note that the landmark store names MAY be handled as either
	 * case-sensitive or case-insensitive (e.g. Unicode collation algorithm
	 * level 2). Therefore, the implementation MUST accept the names in the form
	 * returned by listLandmarkStores and MAY accept the name in other
	 * variations of character case.
	 * 
	 * @param storeName -
	 *            the name of the landmark store to delete
	 * @throws java.lang.NullPointerException -
	 *             if the parameter is null (the default landmark store can't be
	 *             deleted)
	 * @throws java.io.IOException -
	 *             if the landmark store couldn't be deleted due to an I/O error
	 * @throws java.lang.SecurityException -
	 *             if the appliction does not have permissions to delete a
	 *             landmark store
	 * @throws LandmarkException -
	 *             if the implementation does not support deleting landmark
	 *             stores
	 * 
	 */
	public void deleteLandmarkStore(java.lang.String storeName)
			throws java.io.IOException,
			javax.microedition.location.LandmarkException {
		
		if (storeName == null || storeName.equals(LandmarkStoreManager.DEFAULT_LANDMARKSTORE)) {
			throw new java.lang.NullPointerException(
					"Parameter storeName cannot be null.");
		}
		
		if (storeName.equals(LandmarkStoreManager.DEFAULT_LANDMARKSTORE)) {
			throw new java.lang.SecurityException(
					"The default landmark store can't be deleted.");
		}

		if (this._multipleLandmarkStoreSupported == false) {
			throw new LandmarkException(
					"The actual configuration of the LandmarkStore is simulating an implementation that does not support multiple LandmarkStore.");
		}
		
		if(this._landmarkStores.contains(storeName)==false)
		{
			return;
		}
		
		try {
			File oldfile=FileSystemRegistry.getInstance().openFile(storeName);
			//TODO check if it is a LandmarkStore;
			oldfile.delete();
		} catch (java.io.IOException e) {
			throw new java.io.IOException(
					"I/O error while removing LandmarkStore named: "+storeName+" .");
		}
		this._landmarkStores.remove(storeName);
		this._propertyChangeSupport.firePropertyChange(LandmarkStoreManager.LANDMARKSTORE_REMOVED, null, storeName);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		listener.propertyChange(new PropertyChangeEvent(this,
				LandmarkStoreManager.IS_SUPPORTING_MULIPLE_LANDMARKSTORE, null,
				this._multipleLandmarkStoreSupported));
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (propertyName
				.equals(LandmarkStoreManager.IS_SUPPORTING_MULIPLE_LANDMARKSTORE)) {
			listener.propertyChange(new PropertyChangeEvent(this,
					LandmarkStoreManager.IS_SUPPORTING_MULIPLE_LANDMARKSTORE,
					null, this._multipleLandmarkStoreSupported));
		}
		_propertyChangeSupport
				.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		_propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		_propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	/**
	 * @param _multipleLandmarkStoreSupported
	 *            the _multipleLandmarkStoreSupported to set
	 */
	public void setMultipleLandmarkStoreSupported(
			boolean _multipleLandmarkStoreSupported) {
		this._propertyChangeSupport.firePropertyChange(
				LandmarkStoreManager.IS_SUPPORTING_MULIPLE_LANDMARKSTORE,
				_multipleLandmarkStoreSupported,
				this._multipleLandmarkStoreSupported);
		this._multipleLandmarkStoreSupported = _multipleLandmarkStoreSupported;
	}

	/**
	 * @return the _multipleLandmarkStoreSupported
	 */
	public boolean isMultipleLandmarkStoreSupported() {
		return _multipleLandmarkStoreSupported;
	}

}
