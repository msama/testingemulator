/**
 *
 */
package ucl.cs.testingEmulator.location;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.microedition.location.Orientation;

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
public class OrientationProvider {

	public static final String AZIMUT_CHANGED="Azimut";
	public static final String MAGNETIC_CHANGED="Magnetic";
	public static final String PITCH_CHANGED="Pitch";
	public static final String ROLL_CHANGED="Roll";
	
	private static OrientationProvider INSTANCE=null;
	protected PropertyChangeSupport _propertyChangeSupport=null;
	
	private float _azimuth;
    private float _pitch;
    private float _roll;
    private boolean _magnetic;
    
    protected OrientationProvider()
    {
    	this._propertyChangeSupport=new PropertyChangeSupport(this);
    }
    
    public static OrientationProvider getInstance()
    {
    	if(OrientationProvider.INSTANCE==null)
    	{
    		OrientationProvider.INSTANCE=new OrientationProvider();
    	}
    	return OrientationProvider.INSTANCE;
    }
	
	public static Orientation getOrientation()
	{
		OrientationProvider provider=OrientationProvider.getInstance();
		return new Orientation(provider.getAzimuth(),provider.isMagnetic(),provider.getPitch(),provider.getRoll());
	}

	/**
	 * @param _azimuth the _azimuth to set
	 */
	public void setAzimuth(float _azimuth) {
		if(_azimuth<0.0||_azimuth>360.0)
		{
			throw new java.lang.IllegalArgumentException("CompassAzimuth must be in the range [0.0 - 360.0].");
		}
		this._propertyChangeSupport.firePropertyChange(OrientationProvider.AZIMUT_CHANGED, this._azimuth, _azimuth);
		this._azimuth = _azimuth;
	}

	/**
	 * @return the _azimuth
	 */
	public float getAzimuth() {
		return _azimuth;
	}

	/**
	 * @param _pitch the _pitch to set
	 */
	public void setPitch(float _pitch) {
		if(_pitch<-90.0||_pitch>90.0)
		{
			throw new java.lang.IllegalArgumentException("Pitch must be in the range [-90.0 - +90.0].");
		}
		this._propertyChangeSupport.firePropertyChange(OrientationProvider.PITCH_CHANGED, this._pitch, _pitch);
		this._pitch = _pitch;
	}

	/**
	 * @return the _pitch
	 */
	public float getPitch() {
		return _pitch;
	}

	/**
	 * @param _roll the _roll to set
	 */
	public void setRoll(float _roll) {
		if(_roll<-180.0||_roll>180.0)
		{
			throw new java.lang.IllegalArgumentException("Roll must be in the range [-180.0 - +180.0].");
		}
		this._propertyChangeSupport.firePropertyChange(OrientationProvider.ROLL_CHANGED, this._roll, _roll);
		this._roll = _roll;
	}

	/**
	 * @return the _roll
	 */
	public float getRoll() {
		return _roll;
	}

	/**
	 * @param _magnetic the _magnetic to set
	 */
	public void setMagnetic(boolean _magnetic) {
		this._magnetic = _magnetic;
	}

	/**
	 * @return the _magnetic
	 */
	public boolean isMagnetic() {
		return _magnetic;
	}

	/**
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this._propertyChangeSupport.firePropertyChange(OrientationProvider.AZIMUT_CHANGED, null, this._azimuth);
		this._propertyChangeSupport.firePropertyChange(OrientationProvider.MAGNETIC_CHANGED, null, this._magnetic);
		this._propertyChangeSupport.firePropertyChange(OrientationProvider.PITCH_CHANGED, null, this._pitch);
		this._propertyChangeSupport.firePropertyChange(OrientationProvider.ROLL_CHANGED, null, this._roll);
		_propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * @param propertyName
	 * @param listener
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if(propertyName.equals(OrientationProvider.AZIMUT_CHANGED))
		{
			this._propertyChangeSupport.firePropertyChange(OrientationProvider.AZIMUT_CHANGED, null, this._azimuth);
		}else if(propertyName.equals(OrientationProvider.MAGNETIC_CHANGED))
		{
			this._propertyChangeSupport.firePropertyChange(OrientationProvider.MAGNETIC_CHANGED, null, this._magnetic);
		}else if(propertyName.equals(OrientationProvider.PITCH_CHANGED))
		{
			this._propertyChangeSupport.firePropertyChange(OrientationProvider.PITCH_CHANGED, null, this._pitch);
		}else if(propertyName.equals(OrientationProvider.ROLL_CHANGED))
		{
			this._propertyChangeSupport.firePropertyChange(OrientationProvider.ROLL_CHANGED, null, this._roll);
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
