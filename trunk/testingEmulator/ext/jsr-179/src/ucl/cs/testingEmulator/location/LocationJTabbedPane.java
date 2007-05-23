/**
 *
 */
package ucl.cs.testingEmulator.location;

import javax.swing.JTabbedPane;
import java.awt.Dimension;

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
public class LocationJTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	private OrientationProviderJPanel orientationProviderJPanel = null;
	private LandmarkStoreJPanel landmarkStoreJPanel = null;
	private LocalizationMethodsJPanel localizationMethodsJPanel = null;
	private EmulatedLocationJPanel emulatedLocationJPanel = null;

	/**
	 * This is the default constructor
	 */
	public LocationJTabbedPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(512, 463);

		this.addTab("Orientation", null, getOrientationProviderJPanel(), null);
		this.addTab("Landmark", null, getLandmarkStoreJPanel(), null);
		this.addTab("Methods", null, getLocalizationMethodsJPanel(), null);
		this.addTab("Location", null, getEmulatedLocationJPanel(), null);
	}

	/**
	 * This method initializes orientationProviderJPanel	
	 * 	
	 * @return ucl.cs.testingEmulator.location.OrientationProviderJPanel	
	 */
	private OrientationProviderJPanel getOrientationProviderJPanel() {
		if (orientationProviderJPanel == null) {
			orientationProviderJPanel = new OrientationProviderJPanel();
		}
		return orientationProviderJPanel;
	}

	/**
	 * This method initializes landmarkStoreJPanel	
	 * 	
	 * @return ucl.cs.testingEmulator.location.LandmarkStoreJPanel	
	 */
	private LandmarkStoreJPanel getLandmarkStoreJPanel() {
		if (landmarkStoreJPanel == null) {
			landmarkStoreJPanel = new LandmarkStoreJPanel();
			landmarkStoreJPanel.setEnabled(false);
		}
		return landmarkStoreJPanel;
	}

	/**
	 * This method initializes localizationMethodsJPanel	
	 * 	
	 * @return ucl.cs.testingEmulator.location.LocalizationMethodsJPanel	
	 */
	private LocalizationMethodsJPanel getLocalizationMethodsJPanel() {
		if (localizationMethodsJPanel == null) {
			localizationMethodsJPanel = new LocalizationMethodsJPanel();
		}
		return localizationMethodsJPanel;
	}

	/**
	 * This method initializes emulatedLocationJPanel	
	 * 	
	 * @return ucl.cs.testingEmulator.location.EmulatedLocationJPanel	
	 */
	private EmulatedLocationJPanel getEmulatedLocationJPanel() {
		if (emulatedLocationJPanel == null) {
			emulatedLocationJPanel = new EmulatedLocationJPanel();
		}
		return emulatedLocationJPanel;
	}



}  //  @jve:decl-index=0:visual-constraint="10,10"
