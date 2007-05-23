/**
 * 
 */
package ucl.cs.testingEmulator.bluetooth;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import java.awt.Dimension;

/**
 * @author -Michele Sama- aka -RAX-
 * 
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
public class BluetoothJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane jTabbedPane = null;
	private LocalDeviceJPanel localDeviceJPanel = null;
	private DiscoveryAgentJPanel discoveryAgentJPanel = null;

	/**
	 * This is the default constructor
	 */
	public BluetoothJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.gridx = 0;
		this.setSize(455, 200);
		this.setLayout(new GridBagLayout());
		this.add(getJTabbedPane(), gridBagConstraints);
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("LocalDevice", null, getLocalDeviceJPanel(), null);
			jTabbedPane.addTab("DiscoveryAgent", null, getDiscoveryAgentJPanel(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes localDeviceJPanel	
	 * 	
	 * @return javax.bluetooth.LocalDeviceJPanel	
	 */
	private LocalDeviceJPanel getLocalDeviceJPanel() {
		if (localDeviceJPanel == null) {
			localDeviceJPanel = new LocalDeviceJPanel();
		}
		return localDeviceJPanel;
	}

	/**
	 * This method initializes discoveryAgentJPanel	
	 * 	
	 * @return javax.bluetooth.DiscoveryAgentJPanel	
	 */
	private DiscoveryAgentJPanel getDiscoveryAgentJPanel() {
		if (discoveryAgentJPanel == null) {
			discoveryAgentJPanel = new DiscoveryAgentJPanel();
		}
		return discoveryAgentJPanel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
