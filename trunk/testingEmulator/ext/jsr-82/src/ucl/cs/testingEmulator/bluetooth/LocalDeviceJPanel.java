/**
 * 
 */
package ucl.cs.testingEmulator.bluetooth;

import java.awt.GridBagLayout;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JSlider;



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
public class LocalDeviceJPanel extends JPanel implements PropertyChangeListener{

	
	private static final long serialVersionUID = 1L;
	
	private JLabel jLabelBluetoothAddress = null;
	private JTextField jTextFieldBluetoothAddress = null;
	private JLabel jLabelFriendlyName = null;
	private JTextField jTextFieldFriendlyName = null;
	private JLabel jLabelDiscoverableMode = null;
	private JComboBox jComboBox = null;
	private JLabel jLabelInquiringState = null;
	private JCheckBox jCheckBoxInquiringState = null;

	
	private LocalDevice _localDevice=null;

	private JLabel jLabelInquiringDelay = null;

	private JSlider jSliderDelay = null;

	/**
	 * This is the default constructor
	 */
	public LocalDeviceJPanel() {
		super();
		try {
			this._localDevice=LocalDevice.getLocalDevice();
			this._localDevice.addPropertyChangeListener(this);
			this._localDevice.getDiscoveryAgent().addPropertyChangeListener(DiscoveryAgent.SIMULATED_INQUIRE_DELAY, this);
		} catch (BluetoothStateException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints21.gridy = 4;
		gridBagConstraints21.weightx = 1.0;
		gridBagConstraints21.gridx = 1;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.gridy = 4;
		jLabelInquiringDelay = new JLabel();
		jLabelInquiringDelay.setText("InquiringDelay");
		jLabelInquiringDelay.setPreferredSize(new Dimension(120, 16));
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 1;
		gridBagConstraints7.gridy = 3;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints5.gridy = 2;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.gridx = 1;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints3.gridy = 1;
		gridBagConstraints3.weightx = 1.0;
		gridBagConstraints3.gridx = 1;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.gridy = 3;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.gridy = 2;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		jLabelInquiringState = new JLabel();
		jLabelInquiringState.setText("InquiringState");
		jLabelInquiringState.setPreferredSize(new Dimension(120, 16));
		jLabelDiscoverableMode = new JLabel();
		jLabelDiscoverableMode.setText("DiscoverableMode");
		jLabelDiscoverableMode.setPreferredSize(new Dimension(120, 16));
		jLabelFriendlyName = new JLabel();
		jLabelFriendlyName.setText("FriendlyName");
		jLabelFriendlyName.setPreferredSize(new Dimension(120, 16));
		jLabelBluetoothAddress = new JLabel();
		jLabelBluetoothAddress.setText("BluetoothAddress");
		jLabelBluetoothAddress.setPreferredSize(new Dimension(120, 16));
		this.setSize(435, 342);
		this.setLayout(new GridBagLayout());
		this.add(jLabelBluetoothAddress, gridBagConstraints);
		this.add(jLabelFriendlyName, gridBagConstraints2);
		this.add(jLabelDiscoverableMode, gridBagConstraints4);
		this.add(jLabelInquiringState, gridBagConstraints6);
		this.add(getJTextFieldBluetoothAddress(), gridBagConstraints1);
		this.add(getJTextFieldFriendlyName(), gridBagConstraints3);
		this.add(getJComboBox(), gridBagConstraints5);
		this.add(getJCheckBoxInquiringState(), gridBagConstraints7);
		this.add(jLabelInquiringDelay, gridBagConstraints11);
		this.add(getJSliderDelay(), gridBagConstraints21);
	}

	/**
	 * This method initializes jTextFieldBluetoothAddress	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldBluetoothAddress() {
		if (jTextFieldBluetoothAddress == null) {
			jTextFieldBluetoothAddress = new JTextField();
			jTextFieldBluetoothAddress.setPreferredSize(new Dimension(250, 27));
			jTextFieldBluetoothAddress
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							_localDevice.setBluetoothAddress(jTextFieldBluetoothAddress.getText());
						}
					});
		}
		return jTextFieldBluetoothAddress;
	}

	/**
	 * This method initializes jTextFieldFriendlyName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldFriendlyName() {
		if (jTextFieldFriendlyName == null) {
			jTextFieldFriendlyName = new JTextField();
			jTextFieldFriendlyName.setPreferredSize(new Dimension(250, 27));
			jTextFieldFriendlyName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					_localDevice.setFriendlyName(jTextFieldFriendlyName.getText());
				}
			});
		}
		return jTextFieldFriendlyName;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox(new String[]{"GIAC","LIAC","NOT_DISCOVERABLE"});
			jComboBox.setPreferredSize(new Dimension(250, 27));
			jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String str=jComboBox.getSelectedItem().toString();
					int k=-1;
					boolean flag=false;
					if(str.equals("GIAC"))
					{
						k=DiscoveryAgent.GIAC;
						flag=true;
					}else if(str.equals("LIAC"))
					{
						k=DiscoveryAgent.LIAC;
						flag=true;
					} else if(str.equals("NOT_DISCOVERABLE"))
					{
						k=DiscoveryAgent.NOT_DISCOVERABLE;
						flag=true;
					}
					if(flag==true)
					{
						try {
							_localDevice.setDiscoverable(k);
						} catch (BluetoothStateException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
		return jComboBox;
	}

	/**
	 * This method initializes jCheckBoxInquiringState	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxInquiringState() {
		if (jCheckBoxInquiringState == null) {
			jCheckBoxInquiringState = new JCheckBox();
			jCheckBoxInquiringState.setText("isInquiring");
		}
		return jCheckBoxInquiringState;
	}


	public void propertyChange(PropertyChangeEvent evt) {
		String action=evt.getPropertyName();
		if(action.equals(LocalDevice.BLUETOOTH_ADDRESS_CHANGE))
		{
			this.getJTextFieldBluetoothAddress().setText(evt.getNewValue().toString());
		}else if(action.equals(LocalDevice.FRIENDLY_NAME_CHANGE))
		{
			this.getJTextFieldFriendlyName().setText(evt.getNewValue().toString());
		}else if(action.equals(LocalDevice.DISCOVERABLE_MODE_CHANGE))
		{
			int k=((Integer)evt.getNewValue()).intValue();
			switch(k)
			{
				case DiscoveryAgent.GIAC:
				{
					this.getJComboBox().setSelectedItem("GIAC");
					break;
				}
				case DiscoveryAgent.LIAC:
				{
					this.getJComboBox().setSelectedItem("LIAC");
					break;
				}
				case DiscoveryAgent.NOT_DISCOVERABLE:
				{
					this.getJComboBox().setSelectedItem("NOT_DISCOVERABLE");
					break;
				}
			}
			
		}else if(action.equals(LocalDevice.INQUIRING_STATE_CHANGE))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxInquiringState().setSelected(b);
		}else if(action.equals(DiscoveryAgent.SIMULATED_INQUIRE_DELAY))
		{
			long delay=this._localDevice.getDiscoveryAgent().getSimulatedInquireDelay();
			this.getJSliderDelay().setValue((int)delay);
		}
	}

	/**
	 * This method initializes jSliderDelay	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSliderDelay() {
		if (jSliderDelay == null) {
			jSliderDelay = new JSlider();
			jSliderDelay.setMaximum(20000);
			jSliderDelay.setMajorTickSpacing(5000);
			jSliderDelay.setPaintTicks(true);
			jSliderDelay.setPaintLabels(true);
			jSliderDelay.setPreferredSize(new Dimension(250, 54));
			jSliderDelay.setMinorTickSpacing(1000);
			jSliderDelay.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					_localDevice.getDiscoveryAgent().setSimulatedInquireDelay(jSliderDelay.getValue());
				}
			});
		}
		return jSliderDelay;
	}

}  //  @jve:decl-index=0:visual-constraint="21,8"
