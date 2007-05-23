package ucl.cs.testingEmulator.bluetooth;

import java.awt.GridBagLayout;

import javax.bluetooth.RemoteDevice;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import java.awt.Color;

public class RemoteDeviceCellJPanel extends JPanel implements PropertyChangeListener {

	
	private RemoteDevice _remoteDevice=null;  //  @jve:decl-index=0:
	
	private static final long serialVersionUID = 1L;
	private JLabel jLabelMacAddress = null;
	private JTextField jTextFieldAddress = null;
	private JLabel jLabelName = null;
	private JTextField jTextFieldName = null;
	private JCheckBox jCheckBoxTrusted = null;
	private JCheckBox jCheckBoxAutenticathed = null;
	private JCheckBox jCheckBoxEncripted = null;

	private JPanel jPanelSouth = null;
	/**
	 * This is the default constructor
	 */
	public RemoteDeviceCellJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.gridwidth = 2;
		gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints11.gridy = 2;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.BOTH;
		gridBagConstraints3.gridy = 1;
		gridBagConstraints3.weightx = 1.0;
		gridBagConstraints3.gridwidth = 1;
		gridBagConstraints3.gridx = 1;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		jLabelName = new JLabel();
		jLabelName.setText("Name");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridwidth = 1;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		jLabelMacAddress = new JLabel();
		jLabelMacAddress.setText("MacAddress");
		//this.setPreferredSize(new Dimension(400, 90));
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		this.add(jLabelMacAddress, gridBagConstraints);
		this.add(getJTextFieldAddress(), gridBagConstraints1);
		this.add(jLabelName, gridBagConstraints2);
		this.add(getJTextFieldName(), gridBagConstraints3);
		this.add(getJPanelSouth(), gridBagConstraints11);
	}

	/**
	 * This method initializes jTextFieldAddress	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldAddress() {
		if (jTextFieldAddress == null) {
			jTextFieldAddress = new JTextField();
			jTextFieldAddress.setPreferredSize(new Dimension(50, 27));
			jTextFieldAddress.setOpaque(false);

		}
		return jTextFieldAddress;
	}

	/**
	 * This method initializes jTextFieldName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldName() {
		if (jTextFieldName == null) {
			jTextFieldName = new JTextField();
			jTextFieldName.setPreferredSize(new Dimension(50, 27));
			jTextFieldName.setOpaque(false);

		}
		return jTextFieldName;
	}

	/**
	 * This method initializes jCheckBoxTrusted	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxTrusted() {
		if (jCheckBoxTrusted == null) {
			jCheckBoxTrusted = new JCheckBox();
			jCheckBoxTrusted.setText("Trusted");
			jCheckBoxTrusted.setPreferredSize(new Dimension(150, 22));
			jCheckBoxTrusted.setOpaque(false);
		}
		return jCheckBoxTrusted;
	}

	/**
	 * This method initializes jCheckBoxAutenticathed	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxAutenticathed() {
		if (jCheckBoxAutenticathed == null) {
			jCheckBoxAutenticathed = new JCheckBox();
			jCheckBoxAutenticathed.setText("Autenticathed");
			jCheckBoxAutenticathed.setPreferredSize(new Dimension(150, 22));
			jCheckBoxAutenticathed.setOpaque(false);
		}
		return jCheckBoxAutenticathed;
	}

	/**
	 * This method initializes jCheckBoxEncripted	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxEncripted() {
		if (jCheckBoxEncripted == null) {
			jCheckBoxEncripted = new JCheckBox();
			jCheckBoxEncripted.setText("Encripted");
			jCheckBoxEncripted.setPreferredSize(new Dimension(150, 22));
			jCheckBoxEncripted.setOpaque(false);
		}
		return jCheckBoxEncripted;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String name=evt.getPropertyName();
		if(name.equals(RemoteDevice.ADDRESS_CHANGED))
		{
			this.getJTextFieldAddress().setText(this._remoteDevice.getBluetoothAddress());
		}else if(name.equals(RemoteDevice.NAME_CHANGED))
		{
			try {
				this.getJTextFieldName().setText(this._remoteDevice.getFriendlyName(true));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(name.equals(RemoteDevice.AUTHENTICATE_STATUS_CHANGED))
		{
			this.getJCheckBoxAutenticathed().setSelected(this._remoteDevice.isAuthenticated());
		}else if(name.equals(RemoteDevice.ENCRYPTED_STATUS_CHANGED))
		{
			this.getJCheckBoxEncripted().setSelected(this._remoteDevice.isEncrypted());
		}else if(name.equals(RemoteDevice.TRUSTED_STATUS_CHANGED))
		{
			this.getJCheckBoxTrusted().setSelected(this._remoteDevice.isTrustedDevice());
		}
		
	}

	/**
	 * @param _remoteDevice the _remoteDevice to set
	 */
	public void setRemoteDevice(RemoteDevice _remoteDevice) {
		if(this._remoteDevice!=null)
		{
			this._remoteDevice.removePropertyChangeListener(this);
		}
		this._remoteDevice = _remoteDevice;
		if(this._remoteDevice!=null)
		{
			this._remoteDevice.addPropertyChangeListener(this);
		}
	}

	/**
	 * @return the _remoteDevice
	 */
	public RemoteDevice getRemoteDevice() {
		return _remoteDevice;
	}

	/**
	 * This method initializes jPanelSouth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSouth() {
		if (jPanelSouth == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.gridy = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			jPanelSouth = new JPanel();
			jPanelSouth.setLayout(new GridBagLayout());
			jPanelSouth.add(getJCheckBoxTrusted(), gridBagConstraints4);
			jPanelSouth.add(getJCheckBoxAutenticathed(), gridBagConstraints5);
			jPanelSouth.add(getJCheckBoxEncripted(), gridBagConstraints6);
			jPanelSouth.setOpaque(false);
		}
		return jPanelSouth;
	}



}  //  @jve:decl-index=0:visual-constraint="10,86"
