/**
 *
 */
package ucl.cs.testingEmulator.contexNotifierScripts;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileSystemRegistry;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ucl.cs.testingEmulator.connection.file.EmulatedFileSystem;
import ucl.cs.testingEmulator.core.Script;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import java.io.File;
import java.io.IOException;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

import org.microemu.app.Common;

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
public class Demo26March2007 extends JPanel implements Script {

	EmulatedFileSystem _lastConnected=null;
	EmulatedFileSystem _notificationFileSystem=new EmulatedFileSystem("/Users/rax/Documents/demoFakeRoots/notification","/E:",Connector.READ);  //  @jve:decl-index=0:
	EmulatedFileSystem _gcFileSystem=new EmulatedFileSystem("/Users/rax/Documents/demoFakeRoots/gc","/E:",Connector.READ);  //  @jve:decl-index=0:
	EmulatedFileSystem _meetingFileSystem=new EmulatedFileSystem("/Users/rax/Documents/demoFakeRoots/meeting","/E:",Connector.READ);  //  @jve:decl-index=0:
	
	RemoteDevice _btMyComputer=new RemoteDevice("00:00:00:00:01");  //  @jve:decl-index=0:
	RemoteDevice _btMyBossComputer=new RemoteDevice("00:00:00:00:02");  //  @jve:decl-index=0:
	RemoteDevice _btMyBossMobile=new RemoteDevice("00:00:00:00:03");  //  @jve:decl-index=0:
	RemoteDevice _btBlackhole=new RemoteDevice("00:00:00:00:04");  //  @jve:decl-index=0:

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8917166797291773754L;
	public static  String JARLOCATION = "/Users/rax/APPZ/TestingEmulator/ContextNotifier/dist/ContextNotifier.jad";  //  @jve:decl-index=0:
	private JPanel jPanelFileSystem = null;
	private JRadioButton jRadioButtonNotification = null;
	private JRadioButton jRadioButtonGarbageCollection = null;
	private JRadioButton jRadioButtonMeeting = null;
	private ButtonGroup buttonGroupFileSystem = null;  //  @jve:decl-index=0:visual-constraint="694,18"
	private JPanel jPanelBlueTooth = null;
	private JCheckBox jCheckBoxBTBlackhole = null;
	private JCheckBox jCheckBoxMyOffice = null;
	private JCheckBox jCheckBoxBTBossOffice = null;
	private JCheckBox jCheckBoxBTBossDevice = null;
	private JRadioButton jRadioButtonNone = null;
	private JButton jButtonRestart = null;
	/**
	 * This method initializes 
	 * 
	 */
	public Demo26March2007() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this._btMyComputer.setFriendlyName("MyComputer");
		this._btMyBossComputer.setFriendlyName("MyBossComputer");
		this._btMyBossMobile.setFriendlyName("MyBossMobile"); 
		this._btBlackhole.setFriendlyName("Blackhole");
		this.getButtonGroupFileSystem();
        this.setSize(new Dimension(527, 233));
        this.add(getJPanelFileSystem(), null);
        this.add(getJPanelBlueTooth(), null);
        this.add(getJButtonRestart(), null);
			
	}

	/* (non-Javadoc)
	 * @see ucl.cs.testingEmulator.core.Script#getName()
	 */
	public String getName() {
		return "Demo26March2007";
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		JFrame frame=new JFrame(this.getName());
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * This method initializes jPanelFileSystem	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelFileSystem() {
		if (jPanelFileSystem == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 3;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 1;
			jPanelFileSystem = new JPanel();
			jPanelFileSystem.setLayout(new GridBagLayout());
			jPanelFileSystem.setBorder(BorderFactory.createTitledBorder(null, "FileSystem", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelFileSystem.add(getJRadioButtonNotification(), gridBagConstraints);
			jPanelFileSystem.add(getJRadioButtonGarbageCollection(), gridBagConstraints1);
			jPanelFileSystem.add(getJRadioButtonMeeting(), gridBagConstraints2);
			jPanelFileSystem.add(getJRadioButtonNone(), gridBagConstraints11);
		}
		return jPanelFileSystem;
	}

	/**
	 * This method initializes jRadioButtonNotification	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonNotification() {
		if (jRadioButtonNotification == null) {
			jRadioButtonNotification = new JRadioButton();
			jRadioButtonNotification.setText("Notification");
			jRadioButtonNotification.setPreferredSize(new Dimension(150, 22));
			jRadioButtonNotification.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jRadioButtonNotification.isSelected())
					{
						FileSystemRegistry.getInstance().removeFileSystem(_lastConnected);
						FileSystemRegistry.getInstance().addFileSystem(_notificationFileSystem);
						_lastConnected=_notificationFileSystem;
					}else
					{
						//FileSystemRegistry.getInstance().removeFileSystem(_notificationFileSystem);
					}
				}
			});
		}
		return jRadioButtonNotification;
	}

	/**
	 * This method initializes jRadioButtonGarbageCollection	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonGarbageCollection() {
		if (jRadioButtonGarbageCollection == null) {
			jRadioButtonGarbageCollection = new JRadioButton();
			jRadioButtonGarbageCollection.setText("GarbageCollection");
			jRadioButtonGarbageCollection.setPreferredSize(new Dimension(150, 22));
			jRadioButtonGarbageCollection
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if(jRadioButtonGarbageCollection.isSelected())
							{
								FileSystemRegistry.getInstance().removeFileSystem(_lastConnected);
								FileSystemRegistry.getInstance().addFileSystem(_gcFileSystem);
								_lastConnected=_gcFileSystem;
							}else
							{
								//FileSystemRegistry.getInstance().removeFileSystem(_gcFileSystem);
							}
						}
					});
		}
		return jRadioButtonGarbageCollection;
	}

	/**
	 * This method initializes jRadioButtonMeeting	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonMeeting() {
		if (jRadioButtonMeeting == null) {
			jRadioButtonMeeting = new JRadioButton();
			jRadioButtonMeeting.setText("Meeting");
			jRadioButtonMeeting.setPreferredSize(new Dimension(150, 22));
			jRadioButtonMeeting.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jRadioButtonMeeting.isSelected())
					{
						FileSystemRegistry.getInstance().removeFileSystem(_lastConnected);
						FileSystemRegistry.getInstance().addFileSystem(_meetingFileSystem);
						_lastConnected=_meetingFileSystem;
					}else
					{
						//FileSystemRegistry.getInstance().removeFileSystem(_meetingFileSystem);
					}
				}
			});
		}
		return jRadioButtonMeeting;
	}

	/**
	 * This method initializes buttonGroupFileSystem	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */
	private ButtonGroup getButtonGroupFileSystem() {
		if (buttonGroupFileSystem == null) {
			buttonGroupFileSystem = new ButtonGroup();
			buttonGroupFileSystem.add(this.getJRadioButtonNone());
			buttonGroupFileSystem.add(this.getJRadioButtonGarbageCollection());
			buttonGroupFileSystem.add(this.getJRadioButtonNotification());
			buttonGroupFileSystem.add(this.getJRadioButtonMeeting());
		}
		return buttonGroupFileSystem;
	}

	/**
	 * This method initializes jPanelBlueTooth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelBlueTooth() {
		if (jPanelBlueTooth == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 3;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 0;
			jPanelBlueTooth = new JPanel();
			jPanelBlueTooth.setLayout(new GridBagLayout());
			jPanelBlueTooth.setBorder(BorderFactory.createTitledBorder(null, "BlueTooth", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelBlueTooth.add(getJCheckBoxBTBlackhole(), gridBagConstraints3);
			jPanelBlueTooth.add(getJCheckBoxMyOffice(), gridBagConstraints4);
			jPanelBlueTooth.add(getJCheckBoxBTBossOffice(), gridBagConstraints5);
			jPanelBlueTooth.add(getJCheckBoxBTBossDevice(), gridBagConstraints6);
		}
		return jPanelBlueTooth;
	}

	/**
	 * This method initializes jCheckBoxBTBlackhole	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxBTBlackhole() {
		if (jCheckBoxBTBlackhole == null) {
			jCheckBoxBTBlackhole = new JCheckBox();
			jCheckBoxBTBlackhole.setText("Blackhole");
			jCheckBoxBTBlackhole.setPreferredSize(new Dimension(100, 22));
			jCheckBoxBTBlackhole.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jCheckBoxBTBlackhole.isSelected())
					{
						DiscoveryAgent.getInstance().addRemoteDevice(_btBlackhole,new DeviceClass(8*256));
					}else
					{
						DiscoveryAgent.getInstance().removeRemoteDevice(_btBlackhole);
					}
				}
			});
		}
		return jCheckBoxBTBlackhole;
	}

	/**
	 * This method initializes jCheckBoxMyOffice	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxMyOffice() {
		if (jCheckBoxMyOffice == null) {
			jCheckBoxMyOffice = new JCheckBox();
			jCheckBoxMyOffice.setText("MyComputer");
			jCheckBoxMyOffice.setPreferredSize(new Dimension(100, 22));
			jCheckBoxMyOffice.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jCheckBoxMyOffice.isSelected())
					{
						DiscoveryAgent.getInstance().addRemoteDevice(_btMyComputer,new DeviceClass(1*256));
					}else
					{
						DiscoveryAgent.getInstance().removeRemoteDevice(_btMyComputer);
					}
				}
			});
		}
		return jCheckBoxMyOffice;
	}

	/**
	 * This method initializes jCheckBoxBTBossOffice	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxBTBossOffice() {
		if (jCheckBoxBTBossOffice == null) {
			jCheckBoxBTBossOffice = new JCheckBox();
			jCheckBoxBTBossOffice.setText("MyBossComputer");
			jCheckBoxBTBossOffice.setPreferredSize(new Dimension(100, 22));
			jCheckBoxBTBossOffice.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jCheckBoxBTBossOffice.isSelected())
					{
						DiscoveryAgent.getInstance().addRemoteDevice(_btMyBossComputer,new DeviceClass(1*256));
					}else
					{
						DiscoveryAgent.getInstance().removeRemoteDevice(_btMyBossComputer);
					}
				}
			});
		}
		return jCheckBoxBTBossOffice;
	}

	/**
	 * This method initializes jCheckBoxBTBossDevice	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxBTBossDevice() {
		if (jCheckBoxBTBossDevice == null) {
			jCheckBoxBTBossDevice = new JCheckBox();
			jCheckBoxBTBossDevice.setText("MyBossDevice");
			jCheckBoxBTBossDevice.setPreferredSize(new Dimension(100, 22));
			jCheckBoxBTBossDevice.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jCheckBoxBTBossDevice.isSelected())
					{
						DiscoveryAgent.getInstance().addRemoteDevice(_btMyBossMobile,new DeviceClass(2*256));
					}else
					{
						DiscoveryAgent.getInstance().removeRemoteDevice(_btMyBossMobile);
					}
				}
			});
		}
		return jCheckBoxBTBossDevice;
	}

	/**
	 * This method initializes jRadioButtonNone	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonNone() {
		if (jRadioButtonNone == null) {
			jRadioButtonNone = new JRadioButton();
			jRadioButtonNone.setPreferredSize(new Dimension(150, 22));
			jRadioButtonNone.setSelected(true);
			jRadioButtonNone.setText("None");
			jRadioButtonNone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jRadioButtonNone.isSelected()==true){
						FileSystemRegistry.getInstance().removeFileSystem(_lastConnected);
						_lastConnected=null;
					}
				}
			});
		}
		return jRadioButtonNone;
	}

	/**
	 * This method initializes jButtonRestart	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRestart() {
		if (jButtonRestart == null) {
			jButtonRestart = new JButton();
			jButtonRestart.setText("Restart");
			jButtonRestart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						Common.openJadUrlSafe(new File(JARLOCATION).toURL().toString());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return jButtonRestart;
	}

	
	
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
