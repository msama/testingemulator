/**
 *
 */
package ucl.cs.testingEmulator.time;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JButton;

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
public class VirtualClockJPanel extends JPanel implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanelStatus = null;
	private SimulatedTimeJPanel simulatedTimeJPanel = null;
	private ButtonGroup buttonGroupVirtualClockStatus = null;  //  @jve:decl-index=0:visual-constraint="680,37"
	private JRadioButton jRadioButtonTimeNormal = null;
	private JRadioButton jRadioButtonTimeStopped = null;
	private JRadioButton jRadioButtonTimeStoppedDelaysHandled = null;
	private JRadioButton jRadioButtonTimeWarped = null;
	private JButton jButtonRestartWarpTime = null;

	/**
	 * This is the default constructor
	 */
	public VirtualClockJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		VirtualClock.getInstance().addPropertyChangeListener(this);
		this.getButtonGroupVirtualClockStatus();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.gridx = 0;
		this.setSize(519, 315);
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
			jTabbedPane.addTab("Status", null, getJPanelStatus(), null);
			jTabbedPane.addTab("Time", null, getSimulatedTimeJPanel(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanelStatus	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelStatus() {
		if (jPanelStatus == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 5;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 5;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 4;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 3;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 2;
			jPanelStatus = new JPanel();
			jPanelStatus.setLayout(new GridBagLayout());
			jPanelStatus.add(getJRadioButtonTimeNormal(), gridBagConstraints3);
			jPanelStatus.add(getJRadioButtonTimeStopped(), gridBagConstraints4);
			jPanelStatus.add(getJRadioButtonTimeStoppedDelaysHandled(), gridBagConstraints5);
			jPanelStatus.add(getJRadioButtonTimeWarped(), gridBagConstraints6);
			jPanelStatus.add(getJButtonRestartWarpTime(), gridBagConstraints1);
		}
		return jPanelStatus;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String act=evt.getPropertyName();
		if(act.equals(VirtualClock.TIME_STATUS))
		{
			VirtualClockStatus status=(VirtualClockStatus)evt.getNewValue();
			if(status==VirtualClockStatus.TIME_NORMAL_STATUS)
			{
				this.getJRadioButtonTimeNormal().setSelected(true);
			}else if(status==VirtualClockStatus.TIME_STOPPED_AND_DELAYS_HANDLED)
			{
				this.getJRadioButtonTimeStoppedDelaysHandled().setSelected(true);
			}else if(status==VirtualClockStatus.TIME_STOPPED_STATUS)
			{
				this.getJRadioButtonTimeStopped().setSelected(true);
			}else if(status==VirtualClockStatus.TIME_WARPED_STATUS)
			{
				this.getJRadioButtonTimeWarped().setSelected(true);
			}
		}else if(act.equals(VirtualClock.TIME_CHANGED)){
			
		}
	}

	/**
	 * This method initializes simulatedTimeJPanel	
	 * 	
	 * @return cs.ucl.testingEmulator.time.SimulatedTimeJPanel	
	 */
	private SimulatedTimeJPanel getSimulatedTimeJPanel() {
		if (simulatedTimeJPanel == null) {
			simulatedTimeJPanel = new SimulatedTimeJPanel();
		}
		return simulatedTimeJPanel;
	}

	/**
	 * This method initializes buttonGroupVirtualClockStatus	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */
	private ButtonGroup getButtonGroupVirtualClockStatus() {
		if (buttonGroupVirtualClockStatus == null) {
			buttonGroupVirtualClockStatus = new ButtonGroup();
			buttonGroupVirtualClockStatus.add(this.getJRadioButtonTimeNormal());
			buttonGroupVirtualClockStatus.add(this.getJRadioButtonTimeStopped());
			buttonGroupVirtualClockStatus.add(this.getJRadioButtonTimeStoppedDelaysHandled());
			buttonGroupVirtualClockStatus.add(this.getJRadioButtonTimeWarped());
		}
		return buttonGroupVirtualClockStatus;
	}

	/**
	 * This method initializes jRadioButtonTimeNormal	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonTimeNormal() {
		if (jRadioButtonTimeNormal == null) {
			jRadioButtonTimeNormal = new JRadioButton();
			jRadioButtonTimeNormal.setText("Time normal");
			jRadioButtonTimeNormal.setPreferredSize(new Dimension(250, 22));
			jRadioButtonTimeNormal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					VirtualClock.getInstance().setStatus(VirtualClockStatus.TIME_NORMAL_STATUS);
				}
			});
		}
		return jRadioButtonTimeNormal;
	}

	/**
	 * This method initializes jRadioButtonTimeStopped	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonTimeStopped() {
		if (jRadioButtonTimeStopped == null) {
			jRadioButtonTimeStopped = new JRadioButton();
			jRadioButtonTimeStopped.setText("Time stopped");
			jRadioButtonTimeStopped.setPreferredSize(new Dimension(250, 22));
			jRadioButtonTimeStopped.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					VirtualClock.getInstance().setStatus(VirtualClockStatus.TIME_STOPPED_STATUS);
				}
			});
		}
		return jRadioButtonTimeStopped;
	}

	/**
	 * This method initializes jRadioButtonTimeStoppedDelaysHandled	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonTimeStoppedDelaysHandled() {
		if (jRadioButtonTimeStoppedDelaysHandled == null) {
			jRadioButtonTimeStoppedDelaysHandled = new JRadioButton();
			jRadioButtonTimeStoppedDelaysHandled.setText("Time stopped and delays handled");
			jRadioButtonTimeStoppedDelaysHandled.setPreferredSize(new Dimension(250, 22));
			jRadioButtonTimeStoppedDelaysHandled
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							VirtualClock.getInstance().setStatus(VirtualClockStatus.TIME_STOPPED_AND_DELAYS_HANDLED);
						}
					});
		}
		return jRadioButtonTimeStoppedDelaysHandled;
	}

	/**
	 * This method initializes jRadioButtonTimeWarped	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonTimeWarped() {
		if (jRadioButtonTimeWarped == null) {
			jRadioButtonTimeWarped = new JRadioButton();
			jRadioButtonTimeWarped.setText("Time warped");
			jRadioButtonTimeWarped.setPreferredSize(new Dimension(250, 22));
			jRadioButtonTimeWarped.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					VirtualClock.getInstance().setStatus(VirtualClockStatus.TIME_WARPED_STATUS);
				}
			});
			jRadioButtonTimeWarped.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					getJButtonRestartWarpTime().setEnabled(jRadioButtonTimeWarped.isSelected());
				}
			});
			
		}
		return jRadioButtonTimeWarped;
	}

	/**
	 * This method initializes jButtonRestartWarpTime	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRestartWarpTime() {
		if (jButtonRestartWarpTime == null) {
			jButtonRestartWarpTime = new JButton();
			jButtonRestartWarpTime.setText("Reset");
			jButtonRestartWarpTime.setEnabled(false);
			jButtonRestartWarpTime.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					VirtualClock.getInstance().restartWarpingTime();
				}});
		}
		return jButtonRestartWarpTime;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
