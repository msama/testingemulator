package ucl.cs.testingEmulator.location;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;

public class LocationProviderClient extends LocationProvider implements
		PropertyChangeListener, Runnable {

	protected TestingLocationProvider _testingLocationProvider = TestingLocationProvider
			.getInstance();

	private Criteria _criteria = null;

	// handling the locationlinstener
	private static final int defaultLocationListenerInterval = 8000;

	private static final int defaultLocationListenerTimeout = 2000;

	private LocationListener _locationListener = null;

	private int _loacationListenerTimeout = 0;

	private int _locationListenerInterval = 0;

	private int _locationListenerMaxAge = 0;

	private Thread _locationListenerThread = null;

	/**
	 * @param _criteria
	 */
	public LocationProviderClient(Criteria _criteria) {
		super();
		this._criteria = _criteria;
	}

	/**
	 * @param _criteria
	 *            the _criteria to set
	 */
	public void setCriteria(Criteria _criteria) {
		this._criteria = _criteria;
	}

	/**
	 * @return the _criteria
	 */
	public Criteria getCriteria() {
		return _criteria;
	}

	/**
	 * Adds a LocationListener for updates at the defined interval. The listener
	 * will be called with updated location at the defined interval. The
	 * listener also gets updates when the availablilty state of the
	 * LocationProvider changes.
	 * 
	 * Passing in -1 as the interval selects the default interval which is
	 * dependent on the used location method. Passing in 0 as the interval
	 * registers the listener to only receive provider status updates and not
	 * location updates at all.
	 * 
	 * Only one listener can be registered with each LocationProvider instance.
	 * Setting the listener replaces any possibly previously set listener.
	 * Setting the listener to null cancels the registration of any previously
	 * set listener.
	 * 
	 * The implementation shall initiate obtaining the first location result
	 * immediately when the listener is registered and provide the location to
	 * the listener as soon as it is available. Subsequent location updates will
	 * happen at the defined interval after the first one. If the specified
	 * update interval is smaller than the time it takes to obtain the first
	 * result, the listener shall receive location updates with invalid
	 * Locations at the defined interval until the first location result is
	 * available. Note that prior to getting the first valid location result,
	 * the timeout parameter has no effect. When the first valid location result
	 * is obtained, the implementation may return it to the application
	 * immediately, i.e. before the next interval is due. This implies that in
	 * the beginning when starting to obtain location results, the listener may
	 * first get updates with invalid location results at the defined interval
	 * and when the first valid location result is obtained, it may be returned
	 * to the listener as soon as it is available before the next interval is
	 * due. After the first valid location result is delivered to the
	 * application the timeout parameter is used and the next update is
	 * delivered between the time defined by the interval and time
	 * interval+timeout after the previous update.
	 * 
	 * The timeout parameter determines a timeout that is used if it's not
	 * possible to obtain a new location result when the update is scheduled to
	 * be provided. This timeout value indicates how many seconds the update is
	 * allowed to be provided late compared to the defined interval. If it's not
	 * possible to get a new location result (interval + timeout) seconds after
	 * the previous update, the update will be made and an invalid Location
	 * instance is returned. This is also done if the reason for the inability
	 * to obtain a new location result is due to the provider being temporarily
	 * unavailable or out of service. For example, if the interval is 60 seconds
	 * and the timeout is 10 seconds, the update must be delivered at most 70
	 * seconds after the previous update and if no new location result is
	 * available by that time the update will be made with an invalid Location
	 * instance.
	 * 
	 * The maxAge parameter defines how old the location result is allowed to be
	 * provided when the update is made. This allows the implementation to reuse
	 * location results if it has a recent location result when the update is
	 * due to be delivered. This parameter can only be used to indicate a larger
	 * value than the normal time of obtaining a location result by a location
	 * method. The normal time of obtaining the location result means the time
	 * it takes normally to obtain the result when a request is made. If the
	 * application specifies a time value that is less than what can be realized
	 * with the used location method, the implementation shall provide as recent
	 * location results as are possible with the used location method. For
	 * example, if the interval is 60 seconds, the maxAge is 20 seconds and
	 * normal time to obtain the result is 10 seconds, the implementation would
	 * normally start obtaining the result 50 seconds after the previous update.
	 * If there is a location result otherwise available that is more recent
	 * than 40 seconds after the previous update, then the maxAge setting to 20
	 * seconds allows to return this result and not start obtaining a new one.
	 * 
	 * The requirements for the intervals hold while the application is
	 * executing. If the application environment or the device is suspended so
	 * that the application will not execute at all and then the environment is
	 * later resumed, the periodic updates MUST continue at the defined interval
	 * but there may be a shift after the suspension period compared to the
	 * original interval schedule.
	 * 
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.location.LocationProvider#setLocationListener(javax.microedition.location.LocationListener,
	 *      int, int, int)
	 */
	@Override
	public void setLocationListener(LocationListener listener, int interval,
			int timeout, int maxAge) {

		if (interval < -1
				|| (interval != -1 && (timeout > interval || maxAge > interval
						|| (timeout < 1 && timeout != -1) || (maxAge < 1 && maxAge != -1)))) {
			throw new IllegalArgumentException("Interval must be -1, 0 or >0.");
		}

		if (listener == null) {
			this._locationListener = null;
			this._locationListenerThread = null;
			return;
		} else {
			// kill previous thread
			// this._locationListenerThread. Thread kill itself
		}
		if (interval == -1) {
			// set the default interval.....
			interval = LocationProviderClient.defaultLocationListenerInterval;
			timeout = LocationProviderClient.defaultLocationListenerTimeout;
		}
		/*if(timeout==-1)
		{
			timeout=LocationProviderClient.defaultLocationListenerTimeout;
		}
		if(maxAge==-1)
		{
			maxAge=LocationProviderClient.defaultLocationListenerInterval-LocationProviderClient.defaultLocationListenerTimeout;
		}*/

		this._locationListener = listener;
		this._locationListenerInterval = interval;
		this._loacationListenerTimeout = timeout;
		this._locationListenerMaxAge = maxAge;

		if (interval != 0) {
			this._locationListenerThread = new Thread(this, "LocationProviderClient");
			this._locationListenerThread.setDaemon(true);
			this._locationListenerThread.start();
		}

	}

	/**
	 * 
	 */
	@Override
	public void reset() {
		super.reset();
		this._locationListener = null;
		// this._locationListenerThread. Thread kill itself
	}

	public void run() {
		Location location = null;
		long lastUpdateTime = 0;
		LocationListener lastListener = this._locationListener;
		try {
			while (this._locationListener != null
					&& this._locationListener == lastListener) {

				if (this._locationListenerMaxAge != -1
						&& (System.currentTimeMillis() - this._lastKonwnLocationTime) < this._locationListenerMaxAge) {
					this._locationListener.locationUpdated(this,
							this._lastKnownLocation);
					continue;
				}

				if ((System.currentTimeMillis() - lastUpdateTime) > (this._locationListenerInterval + this._loacationListenerTimeout)) {
					location = new Location();
					this._locationListener.locationUpdated(this, location);
					lastUpdateTime = System.currentTimeMillis();
					continue;
				}

				try {
					location = this
							.getLocation((int) ((this._locationListenerInterval + this._loacationListenerTimeout) - (System
									.currentTimeMillis() - lastUpdateTime)));
				} catch (LocationException e) {
					location = new Location();
					e.printStackTrace();
				} catch (InterruptedException e) {
					location = new Location();
					e.printStackTrace();
				}
				this._locationListener.locationUpdated(this, location);
				lastUpdateTime = System.currentTimeMillis();

			}
		} catch (RuntimeException e) {
			// listener becomes null or just change
			e.printStackTrace();
		}
	}

	public Location getLocation(int timeout) throws LocationException,
			InterruptedException {

		// TODO InterruptedException

		if (timeout > 0 == false) {
			throw new IllegalArgumentException(
					"LocationProviderClient.getLocation(timeout): timeout must be >0");
		}

		if (this.getState() == LocationProvider.AVAILABLE) {
			if (this._testingLocationProvider.getSimulatedDelay() <= timeout) {
				Thread.sleep(this._testingLocationProvider.getSimulatedDelay());
				// update of the last known location and of the lookup time.
				this._lastKnownLocation = this._testingLocationProvider
						.getCurrentEmulatedLocation();
				this._lastKonwnLocationTime = System.currentTimeMillis();
				return this._lastKnownLocation;
			} else {
				Thread.sleep(timeout);
				throw new LocationException(
						"LocationProviderClient.getLocation(timeout): simulatedDelay>timeout");
			}
		} else if (this.getState() == LocationProvider.OUT_OF_SERVICE) {
			throw new LocationException(
					"LocationProviderClient.getLocation(timeout): inner state = OUT_OF_SERVICE");
		} else if (this.getState() == LocationProvider.TEMPORARILY_UNAVAILABLE) {
			throw new LocationException(
					"LocationProviderClient.getLocation(timeout): inner state = TEMPORARILY_UNAVAILABLE");
		}

		throw new LocationException(
				"TestingLocationProvider.getLocation(timeout): inner state != AVAILABLE");
	}

	@Override
	public int getState() {
		return this._testingLocationProvider.getState();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String action = evt.getPropertyName();
		if (action.equals(TestingLocationProvider.STATE_CHANGED)) {
			if (this._locationListener != null) {
				this._locationListener.providerStateChanged(this,
						this._testingLocationProvider.getState());
			}
		}

	}
}
