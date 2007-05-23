package javax.microedition.location;
/**
 * This interface represents a listener to events associated with detecting proximity to some registered coordinates. Applications implement this interface and register it with a static method in LocationProvider to obtain notfications when proximity to registered coordinates is detected.
 * This listener is called when the terminal enters the proximity of the registered coordinates. The proximity is defined as the proximity radius around the coordinates combined with the horizontal accuracy of the current sampled location.
 * The listener is called only once when the terminal enters the proximity of the registered coordinates. The registration with these coordinates is cancelled when the listener is called. If the application wants to be notified again about these coordinates, it must re-register the coordinates and the listener.
 */
public interface ProximityListener{
    /**
     * Called to notify that the state of the proximity monitoring has changed.
     * These state changes are delivered to the application as soon as possible after the state of the monitoring changes.
     * Regardless of the state, the ProximityListener remains registered until the application explicitly removes it with LocationProvider.removeProximityListener or the application exits.
     * These state changes may be related to state changes of some location providers, but this is implementation dependent as implementations can freely choose the method used to implement this proximity monitoring.
     */
    public abstract void monitoringStateChanged(boolean isMonitoringActive);

    /**
     * After registering this listener with the LocationProvider, this method will be called by the platform when the implementation detects that the current location of the terminal is within the defined proximity radius of the registered coordinates.
     */
    public abstract void proximityEvent(javax.microedition.location.Coordinates coordinates, javax.microedition.location.Location location);

}
