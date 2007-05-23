package ucl.cs.testingEmulator.bluetooth;

import java.awt.GridBagLayout;

import javax.bluetooth.DeviceClass;
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
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;

public class RemoteDeviceJPanel extends JPanel implements PropertyChangeListener {

	
	private RemoteDevice _remoteDevice=null;  //  @jve:decl-index=0:
	
	private static final long serialVersionUID = 1L;
	private JLabel jLabelMacAddress = null;
	private JTextField jTextFieldAddress = null;
	private JLabel jLabelName = null;
	private JTextField jTextFieldName = null;
	private JCheckBox jCheckBoxTrusted = null;
	private JCheckBox jCheckBoxAutenticathed = null;
	private JCheckBox jCheckBoxEncripted = null;
	private JLabel jLabelClass = null;
	private JComboBox jComboBoxClass = null;

	private ButtonGroup buttonGroupMajorDeviceGroup = null;  //  @jve:decl-index=0:visual-constraint="764,36"

	private JPanel jPanelDeviceClass = null;

	private JComboBox jComboBoxMajorDeviceClass = null;

	private JLabel jLabelMajorDeviceClass = null;

	private JLabel jLabelMinorDeviceClass = null;

	private JComboBox jComboBoxMinorDeviceClass = null;

	private JLabel jLabelFormat = null;

	private JPanel jPanelService = null;

	private JRadioButton jRadioButtonLDM = null;

	private JRadioButton jRadioButtonPositioning = null;

	private JRadioButton jRadioButtonNetworking = null;

	private JRadioButton jRadioButtonRendering = null;

	private JRadioButton jRadioButtonCapturing = null;

	private JRadioButton jRadioButtonObjectTransfer = null;

	private JRadioButton jRadioButtonAudio = null;

	private JRadioButton jRadioButtonTelephony = null;

	private JRadioButton jRadioButtonInformation = null;
	private JComboBox jComboBoxFormat = null;

	private JPanel jPanelCheckBoxes = null;

	/**
	 * This is the default constructor
	 */
	public RemoteDeviceJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.getButtonGroupMajorDeviceGroup();
		GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
		gridBagConstraints16.gridx = 2;
		gridBagConstraints16.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints16.gridy = 3;
		GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
		gridBagConstraints14.gridx = 0;
		gridBagConstraints14.gridwidth = 3;
		gridBagConstraints14.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints14.gridheight = 2;
		gridBagConstraints14.gridy = 4;
		GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
		gridBagConstraints21.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints21.gridy = 2;
		gridBagConstraints21.weightx = 1.0;
		gridBagConstraints21.gridwidth = 2;
		gridBagConstraints21.gridx = 1;
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.gridy = 2;
		jLabelClass = new JLabel();
		jLabelClass.setText("Class");
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints3.gridy = 1;
		gridBagConstraints3.weightx = 1.0;
		gridBagConstraints3.gridwidth = 2;
		gridBagConstraints3.gridx = 1;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		jLabelName = new JLabel();
		jLabelName.setText("Name");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridwidth = 2;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		jLabelMacAddress = new JLabel();
		jLabelMacAddress.setText("MacAddress");
		this.setSize(638, 275);
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(638, 275));
		this.add(jLabelMacAddress, gridBagConstraints);
		this.add(getJTextFieldAddress(), gridBagConstraints1);
		this.add(jLabelName, gridBagConstraints2);
		this.add(getJTextFieldName(), gridBagConstraints3);
		this.add(jLabelClass, gridBagConstraints11);
		this.add(getJComboBoxClass(), gridBagConstraints21);
		this.add(getJPanelDeviceClass(), gridBagConstraints14);
		this.add(getJPanelCheckBoxes(), gridBagConstraints16);
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
			jTextFieldAddress.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					_remoteDevice.setBluetoothAddress(jTextFieldAddress.getText());
				}
			});
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
			jTextFieldName.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(_remoteDevice!=null)
					{
						_remoteDevice.setFriendlyName(jTextFieldName.getText());
					}
				}
			});
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
			jCheckBoxTrusted.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(_remoteDevice!=null)
					{
						_remoteDevice.setTrustedDevice(jCheckBoxTrusted.isSelected());
					}
				}
			});
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
			jCheckBoxAutenticathed.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(_remoteDevice!=null)
					{
						_remoteDevice.setAuthenticated(jCheckBoxAutenticathed.isSelected());
					}
				}
			});
			
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
			jCheckBoxEncripted.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(_remoteDevice!=null)
					{
						_remoteDevice.setEncrypted(jCheckBoxEncripted.isSelected());
					}
				}
			});
		}
		return jCheckBoxEncripted;
	}

	/**
	 * This method initializes jComboBoxClass	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxClass() {
		if (jComboBoxClass == null) {
			jComboBoxClass = new JComboBox();
			jComboBoxClass.setPreferredSize(new Dimension(50, 27));
			jComboBoxClass.setOpaque(false);
		}
		return jComboBoxClass;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String name=evt.getPropertyName();
		if(name.equals(RemoteDevice.ADDRESS_CHANGED))
		{
			String s=(String)evt.getNewValue();
			this.getJTextFieldAddress().setText(s);
		}else if(name.equals(RemoteDevice.NAME_CHANGED))
		{
			String s=(String)evt.getNewValue();
			this.getJTextFieldName().setText(s);
		}else if(name.equals(RemoteDevice.AUTHENTICATE_STATUS_CHANGED))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxAutenticathed().setSelected(b);
		}else if(name.equals(RemoteDevice.ENCRYPTED_STATUS_CHANGED))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxEncripted().setSelected(b);
		}else if(name.equals(RemoteDevice.TRUSTED_STATUS_CHANGED))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxTrusted().setSelected(b);
		}else if(name.equals(RemoteDevice.DEVICECLASS_CHANGED))
		{
			DeviceClass dClass=(DeviceClass)evt.getNewValue();
			
			int service=dClass.getServiceClasses();
			this.getJRadioButtonLDM().setSelected((service&(int)Math.pow(2, 13))!=0);
			this.getJRadioButtonPositioning().setSelected((service&(int)Math.pow(2, 16))!=0);
			this.getJRadioButtonNetworking().setSelected((service&(int)Math.pow(2, 17))!=0);
			this.getJRadioButtonRendering().setSelected((service&(int)Math.pow(2, 18))!=0);
			this.getJRadioButtonCapturing().setSelected((service&(int)Math.pow(2, 19))!=0);
			this.getJRadioButtonObjectTransfer().setSelected((service&(int)Math.pow(2, 20))!=0);
			this.getJRadioButtonAudio().setSelected((service&(int)Math.pow(2, 21))!=0);
			this.getJRadioButtonTelephony().setSelected((service&(int)Math.pow(2, 22))!=0);
			this.getJRadioButtonInformation().setSelected((service&(int)Math.pow(2, 23))!=0);
			
			//major
			int major=dClass.getMajorDeviceClass();
			String majorName=null;
			for(Entry<String,Integer> entry:DeviceClassWrapper.majorClass.entrySet())
			{
				if(entry.getValue().intValue()==major)
				{
					majorName=entry.getKey();
					break;
				}
			}
			if(majorName!=null)
			{
				this.getJComboBoxMajorDeviceClass().setSelectedItem(majorName);
				Hashtable<String, Integer> minorTable=DeviceClassWrapper.classes.get(majorName);
				int minor=dClass.getMinorDeviceClass();
				String minorName=null;
				for(Entry<String,Integer> entry:minorTable.entrySet())
				{
					if(entry.getValue().intValue()==minor)
					{
						minorName=entry.getKey();
						break;
					}
				}
				if(minorName!=null)
				{
					this.getJComboBoxMinorDeviceClass().setSelectedItem(minorName);
				}
			}
			
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
		}else
		{
			this.getJCheckBoxAutenticathed().setSelected(false);
			this.getJCheckBoxEncripted().setSelected(false);
			this.getJCheckBoxTrusted().setSelected(false);
			this.getJTextFieldAddress().setText("");
			this.getJTextFieldName().setText("");
		}
	}

	/**
	 * @return the _remoteDevice
	 */
	public RemoteDevice getRemoteDevice() {
		return _remoteDevice;
	}

	/**
	 * This method initializes buttonGroupMajorDeviceGroup	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */
	private ButtonGroup getButtonGroupMajorDeviceGroup() {
		if (buttonGroupMajorDeviceGroup == null) {
			buttonGroupMajorDeviceGroup = new ButtonGroup();
		}
		return buttonGroupMajorDeviceGroup;
	}

	/**
	 * This method initializes jPanelDeviceClass	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelDeviceClass() {
		if (jPanelDeviceClass == null) {
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.gridy = 4;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.gridx = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridwidth = 2;
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridheight = 2;
			gridBagConstraints7.weighty = 0.0D;
			gridBagConstraints7.gridy = 0;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridy = 4;
			jLabelFormat = new JLabel();
			jLabelFormat.setText("Format");
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.BOTH;
			gridBagConstraints13.gridy = 3;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 3;
			jLabelMinorDeviceClass = new JLabel();
			jLabelMinorDeviceClass.setText("MinorDeviceClass");
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 2;
			jLabelMajorDeviceClass = new JLabel();
			jLabelMajorDeviceClass.setText("MajorDeviceClass");
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.BOTH;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.gridx = 1;
			jPanelDeviceClass = new JPanel();
			jPanelDeviceClass.setLayout(new GridBagLayout());
			jPanelDeviceClass.setBorder(BorderFactory.createTitledBorder(null, "DeviceClass", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelDeviceClass.setPreferredSize(new Dimension(874, 300));
			jPanelDeviceClass.add(getJComboBoxMajorDeviceClass(), gridBagConstraints9);
			jPanelDeviceClass.add(jLabelMajorDeviceClass, gridBagConstraints10);
			jPanelDeviceClass.add(jLabelMinorDeviceClass, gridBagConstraints12);
			jPanelDeviceClass.add(getJComboBoxMinorDeviceClass(), gridBagConstraints13);
			jPanelDeviceClass.add(jLabelFormat, gridBagConstraints15);
			jPanelDeviceClass.add(getJPanelService(), gridBagConstraints7);
			jPanelDeviceClass.add(getJComboBoxFormat(), gridBagConstraints8);
		}
		return jPanelDeviceClass;
	}

	/**
	 * This method initializes jComboBoxMajorDeviceClass	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxMajorDeviceClass() {
		if (jComboBoxMajorDeviceClass == null) {
			jComboBoxMajorDeviceClass = new JComboBox();
			jComboBoxMajorDeviceClass.setPreferredSize(new Dimension(150, 27));

			jComboBoxMajorDeviceClass
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Hashtable<String, Integer> minorC=DeviceClassWrapper.classes.get(jComboBoxMajorDeviceClass.getSelectedItem());
							if(minorC!=null)
							{
								getJComboBoxMinorDeviceClass().removeAllItems();
								Enumeration<String> labels=minorC.keys();
								while(labels.hasMoreElements())
								{
									getJComboBoxMinorDeviceClass().addItem(labels.nextElement());
								}
							}
							setDeviceClass();
						}
					});
			Enumeration<String> labels=DeviceClassWrapper.majorClass.keys();
			while(labels.hasMoreElements())
				jComboBoxMajorDeviceClass.addItem(labels.nextElement());
			}
		return jComboBoxMajorDeviceClass;
	}

	/**
	 * This method initializes jComboBoxMinorDeviceClass	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxMinorDeviceClass() {
		if (jComboBoxMinorDeviceClass == null) {
			jComboBoxMinorDeviceClass = new JComboBox();
			jComboBoxMinorDeviceClass.setPreferredSize(new Dimension(150, 27));
			jComboBoxMinorDeviceClass
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setDeviceClass();
						}
					});
		}
		return jComboBoxMinorDeviceClass;
	}

	protected void setDeviceClass()
	{
		if(this._remoteDevice==null)
		{
			return;
		}
		int service=0;
			//service=0;//Integer.parseInt(this.getJTextFieldService().getText());
			if(this.getJRadioButtonLDM().isSelected())
			{
				service+=(int)Math.pow(2, 13);
			}
			if(this.getJRadioButtonPositioning().isSelected())
			{
				service+=(int)Math.pow(2, 16);
			}
			if(this.getJRadioButtonNetworking().isSelected())
			{
				service+=(int)Math.pow(2, 17);
			}
			if(this.getJRadioButtonRendering().isSelected())
			{
				service+=(int)Math.pow(2, 18);
			}
			if(this.getJRadioButtonCapturing().isSelected())
			{
				service+=(int)Math.pow(2, 19);
			}
			if(this.getJRadioButtonObjectTransfer().isSelected())
			{
				service+=(int)Math.pow(2, 20);
			}
			if(this.getJRadioButtonAudio().isSelected())
			{
				service+=(int)Math.pow(2, 21);
			}
			if(this.getJRadioButtonTelephony().isSelected())
			{
				service+=(int)Math.pow(2, 22);
			}
			if(this.getJRadioButtonInformation().isSelected())
			{
				service+=(int)Math.pow(2, 23);
			}

		int format=0;
		try {
			format=Integer.parseInt(this.getJComboBoxFormat().getSelectedItem().toString());
		} catch (NumberFormatException e) {
			System.err.println("setDeviceClass format: "+e.getMessage());
		}
		int major=0;
		try {
			major=DeviceClassWrapper.majorClass.get(this.getJComboBoxMajorDeviceClass().getSelectedItem()).intValue();
		} catch (RuntimeException e) {
			System.err.println("setDeviceClass MajorClass: "+e.getMessage());
		}
		int minor=0;
		try {
			minor=DeviceClassWrapper.classes.get(this.getJComboBoxMajorDeviceClass().getSelectedItem()).get(this.getJComboBoxMinorDeviceClass().getSelectedItem()).intValue();
		} catch (RuntimeException e) {
			System.err.println("setDeviceClass MinorClass: "+e.getMessage());
		}
		DeviceClass dev=new DeviceClass(service+major+minor+format);
		this._remoteDevice.setDeviceClass(dev);
	}

	/**
	 * This method initializes jPanelService	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelService() {
		if (jPanelService == null) {
			jPanelService = new JPanel();
			jPanelService.setLayout(new FlowLayout());
			jPanelService.setBorder(BorderFactory.createTitledBorder(null, "Service", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelService.setPreferredSize(new Dimension(862, 400));
			jPanelService.add(getJRadioButtonLDM(), null);
			jPanelService.add(getJRadioButtonPositioning(), null);
			jPanelService.add(getJRadioButtonNetworking(), null);
			jPanelService.add(getJRadioButtonRendering(), null);
			jPanelService.add(getJRadioButtonCapturing(), null);
			jPanelService.add(getJRadioButtonObjectTransfer(), null);
			jPanelService.add(getJRadioButtonAudio(), null);
			jPanelService.add(getJRadioButtonTelephony(), null);
			jPanelService.add(getJRadioButtonInformation(), null);
		}
		return jPanelService;
	}

	/**
	 * This method initializes jRadioButtonLDM	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonLDM() {
		if (jRadioButtonLDM == null) {
			jRadioButtonLDM = new JRadioButton();
			jRadioButtonLDM.setText("LDM");
			jRadioButtonLDM.setToolTipText("Limited Discoverable mode");
			jRadioButtonLDM.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDeviceClass();
				}
			});
		}
		return jRadioButtonLDM;
	}

	/**
	 * This method initializes jRadioButtonPositioning	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonPositioning() {
		if (jRadioButtonPositioning == null) {
			jRadioButtonPositioning = new JRadioButton();
			jRadioButtonPositioning.setText("Positioning");
			jRadioButtonPositioning.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDeviceClass();
				}
			});
		}
		return jRadioButtonPositioning;
	}

	/**
	 * This method initializes jRadioButtonNetworking	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonNetworking() {
		if (jRadioButtonNetworking == null) {
			jRadioButtonNetworking = new JRadioButton();
			jRadioButtonNetworking.setText("Networking");
			jRadioButtonNetworking.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDeviceClass();
				}
			});
		}
		return jRadioButtonNetworking;
	}

	/**
	 * This method initializes jRadioButtonRendering	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonRendering() {
		if (jRadioButtonRendering == null) {
			jRadioButtonRendering = new JRadioButton();
			jRadioButtonRendering.setText("Rendering");
			jRadioButtonRendering.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDeviceClass();
				}
			});
		}
		return jRadioButtonRendering;
	}

	/**
	 * This method initializes jRadioButtonCapturing	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonCapturing() {
		if (jRadioButtonCapturing == null) {
			jRadioButtonCapturing = new JRadioButton();
			jRadioButtonCapturing.setText("Capturing");
			jRadioButtonCapturing.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDeviceClass();
				}
			});
		}
		return jRadioButtonCapturing;
	}

	/**
	 * This method initializes jRadioButtonObjectTransfer	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonObjectTransfer() {
		if (jRadioButtonObjectTransfer == null) {
			jRadioButtonObjectTransfer = new JRadioButton();
			jRadioButtonObjectTransfer.setText("ObjectTransfer");
			jRadioButtonObjectTransfer
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setDeviceClass();
						}
					});
		}
		return jRadioButtonObjectTransfer;
	}

	/**
	 * This method initializes jRadioButtonAudio	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonAudio() {
		if (jRadioButtonAudio == null) {
			jRadioButtonAudio = new JRadioButton();
			jRadioButtonAudio.setText("Audio");
			jRadioButtonAudio.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDeviceClass();
				}
			});
		}
		return jRadioButtonAudio;
	}

	/**
	 * This method initializes jRadioButtonTelephony	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonTelephony() {
		if (jRadioButtonTelephony == null) {
			jRadioButtonTelephony = new JRadioButton();
			jRadioButtonTelephony.setText("Telephony");
			jRadioButtonTelephony.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDeviceClass();
				}
			});
		}
		return jRadioButtonTelephony;
	}

	/**
	 * This method initializes jRadioButtonInformation	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonInformation() {
		if (jRadioButtonInformation == null) {
			jRadioButtonInformation = new JRadioButton();
			jRadioButtonInformation.setText("Information");
			jRadioButtonInformation.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDeviceClass();
				}
			});
		}
		return jRadioButtonInformation;
	}

	/**
	 * This method initializes jComboBoxFormat	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxFormat() {
		if (jComboBoxFormat == null) {
			jComboBoxFormat = new JComboBox(new String[]{"0","1","2","3"});
			jComboBoxFormat.setPreferredSize(new Dimension(150, 27));
			jComboBoxFormat.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jComboBoxFormat.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setDeviceClass();
				}
			});
		}
		return jComboBoxFormat;
	}

	/**
	 * This method initializes jPanelCheckBoxes	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelCheckBoxes() {
		if (jPanelCheckBoxes == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.gridy = 0;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			jPanelCheckBoxes = new JPanel();
			jPanelCheckBoxes.setLayout(new GridBagLayout());
			jPanelCheckBoxes.add(getJCheckBoxTrusted(), gridBagConstraints4);
			jPanelCheckBoxes.add(getJCheckBoxAutenticathed(), gridBagConstraints5);
			jPanelCheckBoxes.add(getJCheckBoxEncripted(), gridBagConstraints6);
		}
		return jPanelCheckBoxes;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
