/**
 *
 */
package ucl.cs.testingEmulator.location;

import javax.microedition.location.Coordinates;
import javax.microedition.location.ProximityListener;


/**
 * @author -Michele Sama- aka -RAX-
 * 
 * University College London
 * Dept. of Computer Science
 * Gower Street
 * London WC1E 6BT
 * United Kingdom
 *
 * Email: M.Sama (at) cs.ucl.ac.uk
 *
 * Group:
 * Software Systems Engineering
 *
 */
public class ProximityListenerWrapper {

	private ProximityListener _proximityListener=null;
	private Coordinates _coordinates=null;
	private float _distanceRadius=0;
	
	
	/**
	 * @param _listener
	 * @param _location
	 * @param _distance
	 */
	public ProximityListenerWrapper(ProximityListener _listener, Coordinates _coordinates, float _distance) {
		super();
		this._proximityListener = _listener;
		this._coordinates = _coordinates;
		this._distanceRadius = _distance;
	}


	/**
	 * @param _proximityListener the _proximityListener to set
	 */
	public void setProximityListener(ProximityListener _proximityListener) {
		this._proximityListener = _proximityListener;
	}


	/**
	 * @return the _proximityListener
	 */
	public ProximityListener getProximityListener() {
		return _proximityListener;
	}


	/**
	 * @param _coordinates the _coordinates to set
	 */
	public void setCoordinates(Coordinates _coordinates) {
		this._coordinates = _coordinates;
	}


	/**
	 * @return the _coordinates
	 */
	public Coordinates getCoordinates() {
		return _coordinates;
	}


	/**
	 * @param _distanceRadius the _distanceRadius to set
	 */
	public void setDistanceRadius(float _distanceRadius) {
		this._distanceRadius = _distanceRadius;
	}


	/**
	 * @return the _distanceRadius
	 */
	public float getDistanceRadius() {
		return _distanceRadius;
	}
	
}
