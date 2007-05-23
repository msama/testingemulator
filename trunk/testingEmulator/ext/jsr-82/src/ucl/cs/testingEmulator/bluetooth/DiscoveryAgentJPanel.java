/**
 *
 */
package ucl.cs.testingEmulator.bluetooth;

import java.awt.GridBagLayout;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.RemoteDevice;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JList;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.ComponentOrientation;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;

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
public class DiscoveryAgentJPanel extends JPanel implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private JList jListRemoteDevices = null;
	private JPanel jPanelRemoteDeviceDetail = null;
	private JButton jButtonNew = null;
	private DefaultListModel defaultListModelRemoteDevice = null;  //  @jve:decl-index=0:visual-constraint="634,58"
	private JButton jButtonDelete = null;
	private RemoteDeviceJPanel remoteDeviceJPanel = null;
	private JScrollPane jScrollPaneDevices = null;

	/**
	 * This is the default constructor
	 */
	public DiscoveryAgentJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		DiscoveryAgent.getInstance().addPropertyChangeListener(this);
		this.setSize(563, 387);
		this.setLayout(new BorderLayout());
		this.add(getJPanelRemoteDeviceDetail(), BorderLayout.SOUTH);
		this.add(getRemoteDeviceJPanel(), BorderLayout.NORTH);
		this.add(getJScrollPaneDevices(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jListRemoteDevices	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListRemoteDevices() {
		if (jListRemoteDevices == null) {
			jListRemoteDevices = new JList();
			//jListRemoteDevices.setPreferredSize(new Dimension(1, 1));
			//jListRemoteDevices.setVisibleRowCount(-1);
			jListRemoteDevices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jListRemoteDevices.setModel(this.getDefaultListModelRemoteDevice());
			//jListRemoteDevices.setCellRenderer(new RemoteDeviceListCellRenderer());
			jListRemoteDevices
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(javax.swing.event.ListSelectionEvent e) {
							RemoteDevice rem=(RemoteDevice)getJListRemoteDevices().getSelectedValue();
							/*if(rem==null)
							{
								
								return;
							}*/
							getRemoteDeviceJPanel().setRemoteDevice(rem);
						}
					});
		}
		return jListRemoteDevices;
	}

	/**
	 * This method initializes jPanelRemoteDeviceDetail	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelRemoteDeviceDetail() {
		if (jPanelRemoteDeviceDetail == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 2;
			gridBagConstraints.gridy = 5;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 1;
			gridBagConstraints15.gridy = 5;
			jPanelRemoteDeviceDetail = new JPanel();
			jPanelRemoteDeviceDetail.setLayout(new GridBagLayout());
			jPanelRemoteDeviceDetail.add(getJButtonNew(), gridBagConstraints15);
			jPanelRemoteDeviceDetail.add(getJButtonDelete(), gridBagConstraints);
		}
		return jPanelRemoteDeviceDetail;
	}

	/**
	 * This method initializes jButtonNew	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonNew() {
		if (jButtonNew == null) {
			jButtonNew = new JButton();
			jButtonNew.setText("New");
			jButtonNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DiscoveryAgent.getInstance().addRemoteDevice(new RemoteDevice("00:00:00:00:00"), new DeviceClass((1+2+4+8+16)*256));
					//getJListRemoteDevices().repaint();
				}
			});
		}
		return jButtonNew;
	}

	/**
	 * This method initializes defaultListModelRemoteDevice1	
	 * 	
	 * @return javax.swing.DefaultListModel	
	 */
	private DefaultListModel getDefaultListModelRemoteDevice() {
		if (defaultListModelRemoteDevice == null) {
			defaultListModelRemoteDevice = new DefaultListModel();
		}
		return defaultListModelRemoteDevice;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getSource() instanceof RemoteDevice)
		{
			this.getJListRemoteDevices().repaint();
		}
		String action=evt.getPropertyName();
		if(action.equals(DiscoveryAgent.TEST_REMOTE_DEVICE_ADDED))
		{
			RemoteDevice rem=(RemoteDevice)evt.getNewValue();
			this.getDefaultListModelRemoteDevice().addElement(rem);
			rem.addPropertyChangeListener(this);
			this.getJListRemoteDevices().repaint();
		}else if(action.equals(DiscoveryAgent.TEST_REMOTE_DEVICE_REMOVED))
		{
			RemoteDevice rem=(RemoteDevice)evt.getNewValue();
			this.getDefaultListModelRemoteDevice().removeElement(rem);
			rem.removePropertyChangeListener(this);
			this.getJListRemoteDevices().repaint();
		}
	}

	/**
	 * This method initializes jButtonDelete	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonDelete() {
		if (jButtonDelete == null) {
			jButtonDelete = new JButton();
			jButtonDelete.setText("Delete");
			jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					RemoteDevice rem=(RemoteDevice)getJListRemoteDevices().getSelectedValue();
					if(rem==null)
					{
						return;
					}
					DiscoveryAgent.getInstance().removeRemoteDevice(rem);
				}
			});
		}
		return jButtonDelete;
	}

	/**
	 * This method initializes remoteDeviceJPanel	
	 * 	
	 * @return ucl.cs.testingEmulator.bluetooth.RemoteDeviceJPanel	
	 */
	private RemoteDeviceJPanel getRemoteDeviceJPanel() {
		if (remoteDeviceJPanel == null) {
			remoteDeviceJPanel = new RemoteDeviceJPanel();
			//remoteDeviceJPanel.setPreferredSize(new Dimension(874, 203));
		}
		return remoteDeviceJPanel;
	}

	/**
	 * This method initializes jScrollPaneDevices	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneDevices() {
		if (jScrollPaneDevices == null) {
			jScrollPaneDevices = new JScrollPane(getJListRemoteDevices());
			//jScrollPaneDevices.setViewportView(getJListRemoteDevices());
			jScrollPaneDevices.setPreferredSize(new Dimension(260, 300));
		}
		return jScrollPaneDevices;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
