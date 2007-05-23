/**
 *
 */
package ucl.cs.testingEmulator.location;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


import javax.microedition.location.Coordinates;
import javax.microedition.location.Location;
import javax.microedition.location.LocationProvider;

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
public class TestingLocationProvider {

	public static final String STATE_CHANGED = "State";

	public static final String LATITUDE_UPDATED = "LatitudeUpdated";

	public static final String LONGITUDE_UPDATED = "LongitudeUpdated";

	public static final String ALTITUDE_UPDATED = "AltitudeUpdated";

	public static final String VERTICAL_ACCURACY_UPDATED = "VerticalAccuracyUpdated";

	public static final String HORIZONTAL_ACCURACY_UPDATED = "HorizontalAccuracyUpdated";

	public static final String LOCATION_VALIDITY_UPDATED = "LocationValidityUpdated";

	public static final String SPEED_UPDATED = "SpeedUpdated";

	private static TestingLocationProvider INSTANCE=null;
	
	private Location _currentEmulatedLocation = new Location();

	
	
	
	
	private int _state = LocationProvider.AVAILABLE;

	private int _simulatedDelay = 5000;


	private PropertyChangeSupport _propertyChangeSupport;
	
	/**
	 * 
	 */
	public TestingLocationProvider() {
		super();
		this._propertyChangeSupport=new PropertyChangeSupport(this);
	}

	public static TestingLocationProvider getInstance()
	{
		if(TestingLocationProvider.INSTANCE==null)
		{
			TestingLocationProvider.INSTANCE=new TestingLocationProvider();
		}
		return TestingLocationProvider.INSTANCE;
	}



	public int getState() {
		return this._state;
	}

	/**
	 * Set the inner state of the Provider for testing purpose.
	 * 
	 * @param state
	 *            the new state to set.
	 */
	public void setState(int state) {
		switch (state) {
			case LocationProvider.AVAILABLE:
			case LocationProvider.OUT_OF_SERVICE:
			case LocationProvider.TEMPORARILY_UNAVAILABLE: {
				this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.STATE_CHANGED, state, this._state);
				this._state = state;
			}
		}
	}


	/**
	 * @param _simulatedDelay
	 *            the _simulatedDelay to set
	 */
	public void setSimulatedDelay(int _simulatedDelay) {
		if(_simulatedDelay!=-1 || _simulatedDelay<0)
		{
			throw new java.lang.IllegalArgumentException("Delay must be >0 or equals to -1");
		}
		this._simulatedDelay = _simulatedDelay;
	}

	/**
	 * @return the _simulatedDelay
	 */
	public int getSimulatedDelay() {
		return _simulatedDelay;
	}

	/**
	 * @param _currentEmulatedLocation the _currentEmulatedLocation to set
	 */
	public void setCurrentEmulatedLocation(Location _currentEmulatedLocation) {
		this._currentEmulatedLocation = _currentEmulatedLocation;
	}

	/**
	 * @return the _currentEmulatedLocation
	 */
	public Location getCurrentEmulatedLocation() {
		return _currentEmulatedLocation;
	}

	/**
	 * @param speed
	 * @see javax.microedition.location.Location#setSpeed(float)
	 */
	public void setSpeed(float speed) {
		this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.SPEED_UPDATED, _currentEmulatedLocation.isValid(), speed);
		_currentEmulatedLocation.setSpeed(speed);
	}

	/**
	 * @param valid
	 * @see javax.microedition.location.Location#setValid(java.lang.Boolean)
	 */
	public void setValid(boolean valid) {
		this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.LOCATION_VALIDITY_UPDATED, _currentEmulatedLocation.isValid(), valid);
		_currentEmulatedLocation.setValid(valid);
	}


	public void setHorizontalAccuracy(float horizontalAccuracy) {
		this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.HORIZONTAL_ACCURACY_UPDATED, _currentEmulatedLocation.getQualifiedCoordinates().getHorizontalAccuracy(), horizontalAccuracy);
		_currentEmulatedLocation.getQualifiedCoordinates().setHorizontalAccuracy(horizontalAccuracy);
	}

	public void setVerticalAccuracy(float verticalAccuracy) {
		this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.VERTICAL_ACCURACY_UPDATED, _currentEmulatedLocation.getQualifiedCoordinates().getVerticalAccuracy(), verticalAccuracy);
		_currentEmulatedLocation.getQualifiedCoordinates().setVerticalAccuracy(verticalAccuracy);
	}
	
	public void setAltitude(float altitude) {
		this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.ALTITUDE_UPDATED, _currentEmulatedLocation.getQualifiedCoordinates().getAltitude(), altitude);
		_currentEmulatedLocation.getQualifiedCoordinates().setAltitude(altitude);
	}

	public void setLongitude(double longitude) {
		this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.LONGITUDE_UPDATED, _currentEmulatedLocation.getQualifiedCoordinates().getLongitude(), longitude);
		_currentEmulatedLocation.getQualifiedCoordinates().setLongitude(longitude);
	}
	
	public void setLongitude(String longitude) {
		double d;
		try {
			d = Coordinates.convert(longitude);
		} catch (RuntimeException e) {
			System.err.println("TestingLocationProvider.setLatitude: "+e.getLocalizedMessage());
			return;
		}
		this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.LONGITUDE_UPDATED, _currentEmulatedLocation.getQualifiedCoordinates().getLongitude(), d);
		_currentEmulatedLocation.getQualifiedCoordinates().setLongitude(Coordinates.convert(longitude));
	}
	
	public void setLatitude(double latitude) {
		this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.LATITUDE_UPDATED, _currentEmulatedLocation.getQualifiedCoordinates().getLatitude(), latitude);
		_currentEmulatedLocation.getQualifiedCoordinates().setLatitude(latitude);
	}
	
	public void setLatitude(String latitude) {
		double d;
		try {
			d = Coordinates.convert(latitude);
		} catch (RuntimeException e) {
			System.err.println("TestingLocationProvider.setLatitude: "+e.getLocalizedMessage());
			return;
		}
		this._propertyChangeSupport.firePropertyChange(TestingLocationProvider.LATITUDE_UPDATED, _currentEmulatedLocation.getQualifiedCoordinates().getLatitude(), d);
		_currentEmulatedLocation.getQualifiedCoordinates().setLatitude(d);
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listener.propertyChange(new PropertyChangeEvent(this,TestingLocationProvider.ALTITUDE_UPDATED,null,this._currentEmulatedLocation.getQualifiedCoordinates().getAltitude()));
		listener.propertyChange(new PropertyChangeEvent(this,TestingLocationProvider.HORIZONTAL_ACCURACY_UPDATED,null,this._currentEmulatedLocation.getQualifiedCoordinates().getHorizontalAccuracy()));
		listener.propertyChange(new PropertyChangeEvent(this,TestingLocationProvider.LATITUDE_UPDATED,null,this._currentEmulatedLocation.getQualifiedCoordinates().getLatitude()));
		listener.propertyChange(new PropertyChangeEvent(this,TestingLocationProvider.LOCATION_VALIDITY_UPDATED,null,this._currentEmulatedLocation.isValid()));
		listener.propertyChange(new PropertyChangeEvent(this,TestingLocationProvider.LONGITUDE_UPDATED,null,this._currentEmulatedLocation.getQualifiedCoordinates().getLongitude()));
		listener.propertyChange(new PropertyChangeEvent(this,TestingLocationProvider.SPEED_UPDATED,null,this._currentEmulatedLocation.getSpeed()));
		listener.propertyChange(new PropertyChangeEvent(this,TestingLocationProvider.STATE_CHANGED,null,this.getState()));
		listener.propertyChange(new PropertyChangeEvent(this,TestingLocationProvider.VERTICAL_ACCURACY_UPDATED,null,this._currentEmulatedLocation.getQualifiedCoordinates().getVerticalAccuracy()));
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if(propertyName.equals(TestingLocationProvider.ALTITUDE_UPDATED))
		{
			listener.propertyChange(new PropertyChangeEvent(this,propertyName,null,this._currentEmulatedLocation.getQualifiedCoordinates().getAltitude()));
		}
		else if(propertyName.equals(TestingLocationProvider.HORIZONTAL_ACCURACY_UPDATED))
		{
			listener.propertyChange(new PropertyChangeEvent(this,propertyName,null,this._currentEmulatedLocation.getQualifiedCoordinates().getHorizontalAccuracy()));
		}
		else if(propertyName.equals(TestingLocationProvider.LATITUDE_UPDATED))
		{
			listener.propertyChange(new PropertyChangeEvent(this,propertyName,null,this._currentEmulatedLocation.getQualifiedCoordinates().getLatitude()));
		}
		else if(propertyName.equals(TestingLocationProvider.LOCATION_VALIDITY_UPDATED))
		{
			listener.propertyChange(new PropertyChangeEvent(this,propertyName,null,this._currentEmulatedLocation.isValid()));
		}
		else if(propertyName.equals(TestingLocationProvider.LONGITUDE_UPDATED))
		{
			listener.propertyChange(new PropertyChangeEvent(this,propertyName,null,this._currentEmulatedLocation.getQualifiedCoordinates().getLongitude()));
		}
		else if(propertyName.equals(TestingLocationProvider.SPEED_UPDATED))
		{
			listener.propertyChange(new PropertyChangeEvent(this,propertyName,null,this._currentEmulatedLocation.getSpeed()));
		}
		else if(propertyName.equals(TestingLocationProvider.STATE_CHANGED))
		{
			listener.propertyChange(new PropertyChangeEvent(this,propertyName,null,this.getState()));
		}
		else if(propertyName.equals(TestingLocationProvider.VERTICAL_ACCURACY_UPDATED))
		{
			listener.propertyChange(new PropertyChangeEvent(this,propertyName,null,this._currentEmulatedLocation.getQualifiedCoordinates().getVerticalAccuracy()));
		}
		_propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
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
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		_propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

}



