/**
 *
 */
package ucl.cs.testingEmulator.contexNotifierScripts;

import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.midlet.MIDlet;
import javax.swing.JPanel;

import ucl.cs.testingEmulator.connection.file.EmulatedFileSystem;
import ucl.cs.testingEmulator.core.Script;
import ucl.cs.testingEmulator.time.VirtualClock;
import ucl.cs.testingEmulator.time.VirtualClockStatus;

import java.awt.Dimension;
import javax.swing.JToggleButton;
import java.awt.GridBagConstraints;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;

import org.microemu.app.Common;
import org.microemu.app.Main;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JSlider;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
public class MobiSys2007 extends JPanel implements Script {

	public static final String JARLOCATION = "/Users/rax/APPZ/TestingEmulator/ContextNotifier/dist/ContextNotifier.jad";  //  @jve:decl-index=0:
	public static final String MIDLET_NAME= "ucl.cs.contextNotifier.ContextNotifierMIDlet";
	
	RemoteDevice _btMyComputer=new RemoteDevice("01:A2:20:00:01");  //  @jve:decl-index=0:
	RemoteDevice _btMyBossComputer=new RemoteDevice("01:0A:00:00:02");  //  @jve:decl-index=0:
	RemoteDevice _btMyBossMobile=new RemoteDevice("00:FF:00:A0:03");  //  @jve:decl-index=0:
	RemoteDevice _btMyMobile=new RemoteDevice("00:03:00:10:04");  //  @jve:decl-index=0:
	
	private Thread _timeUpdateThread;  //  @jve:decl-index=0:
	private boolean _stopUpdating=false;
	private VirtualClock _virtualClock=VirtualClock.getInstance();
	private long _initialTime=0;//System.currentTimeMillis();
	EmulatedFileSystem _meetingFileSystem=new EmulatedFileSystem("/Users/rax/Documents/demoFakeRoots/meeting","/E:",Connector.READ);  //  @jve:decl-index=0:
	
	SampleFileOracleJFrame _sampleFileOracle=null;
	
	private static final long serialVersionUID = 1L;
	private JPanel jPanelTime = null;
	private JToggleButton jToggleButtonTime_0_0 = null;
	private JToggleButton jToggleButtonTime_0_30 = null;
	private JToggleButton jToggleButtonTime_1_0 = null;
	private JToggleButton jToggleButtonTime_2_0 = null;
	private JToggleButton jToggleButtonTime_2_25 = null;
	private ButtonGroup buttonGroupTime = null;  //  @jve:decl-index=0:visual-constraint="1119,101"
	private JTextField jTextFieldCurrentTime = null;
	private JPanel jPanelBluetooth = null;
	private JToggleButton jToggleButtonBT_mycomputer = null;
	private JToggleButton jToggleButtonBT_mycellphone = null;
	private JToggleButton jToggleButtonBT_bosscomputer = null;
	private JToggleButton jToggleButtonBT_bosscellphone = null;
	private JPanel jPanelLocation = null;
	private JPanel jPanelFileSystem = null;
	private JToggleButton jToggleButtonPresentationPPT = null;
	private JPanel jPanelOracles = null;
	private JToggleButton jToggleButtonFileOracle = null;
	private JLabel jLabelLogo = null;
	private MovementJComponent movementJPanel = null;
	/**
	 * 
	 */
	public MobiSys2007() {
		// TODO Auto-generated constructor stub
		super();
		initialize();
	}

	/**
	 * @param arg0
	 */
	public MobiSys2007(LayoutManager arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param arg0
	 */
	public MobiSys2007(boolean arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MobiSys2007(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		FileSystemRegistry.getInstance().addFileSystem(_meetingFileSystem);
		VirtualClock.getInstance().setStatus(VirtualClockStatus.TIME_STOPPED_AND_DELAYS_HANDLED);
		
		GregorianCalendar calendar=new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		//calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		this._initialTime=calendar.getTimeInMillis();

		VirtualClock.getInstance().setSimulatedTime(this._initialTime);
		try {
			Common.openJadUrlSafe(new File(JARLOCATION).toURL().toString());
			Common.getInstance().startMidlet(Common.getInstance().getLauncher().getSelectedMidletEntry().getMIDletClass(), null);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		MIDlet midlet=Common.getInstance().getLauncher().getCurrentMIDlet();
		this._sampleFileOracle=new SampleFileOracleJFrame(midlet, "Presentation File", this._meetingFileSystem.getRealPath()+System.getProperty("file.separator")+"Presentation.ppt", 2000);
		*/
		
		VirtualClock.getInstance().setStatus(VirtualClockStatus.TIME_WARPED_STATUS);
		
		//GUI
		JFrame frame=new JFrame(this.getName());
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				_stopUpdating=true;
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see ucl.cs.testingEmulator.core.Script#getName()
	 */
	public String getName() {
		return "MobiSys2007";
	}

	private void initializeScriptComponents()
	{
		this._btMyComputer.setDeviceClass(new DeviceClass(1179916));
		this._btMyComputer.setFriendlyName("MyComputer");
		
		this._btMyBossComputer.setDeviceClass(new DeviceClass(1179908));
		this._btMyBossComputer.setFriendlyName("MyBossComputer");
		
		this._btMyBossMobile.setDeviceClass(new DeviceClass(7471628));
		this._btMyBossMobile.setFriendlyName("MyBossMobile");
		
		this._btMyMobile.setDeviceClass(new DeviceClass(7471628));
		this._btMyMobile.setFriendlyName("MyMobile");
	}
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.initializeScriptComponents();
		GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
		gridBagConstraints18.gridx = 0;
		gridBagConstraints18.gridwidth = 1;
		gridBagConstraints18.gridy = 0;
		jLabelLogo = new JLabel();
		//jLabelLogo.setPreferredSize(new Dimension(500, 148));
		jLabelLogo.setIcon(new ImageIcon(getClass().getResource("/ucl/cs/testingEmulator/contexNotifierScripts/ucl_logo.gif")));
		GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
		gridBagConstraints17.gridx = 0;
		gridBagConstraints17.fill = GridBagConstraints.BOTH;
		gridBagConstraints17.gridy = 5;
		GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
		gridBagConstraints15.gridx = 0;
		gridBagConstraints15.fill = GridBagConstraints.BOTH;
		gridBagConstraints15.gridy = 4;
		GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
		gridBagConstraints13.gridx = 0;
		gridBagConstraints13.fill = GridBagConstraints.BOTH;
		gridBagConstraints13.gridy = 3;
		GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBagConstraints10.gridx = 0;
		gridBagConstraints10.fill = GridBagConstraints.BOTH;
		gridBagConstraints10.gridy = 2;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.gridx = 0;
		gridBagConstraints5.fill = GridBagConstraints.BOTH;
		gridBagConstraints5.gridy = 1;
		this.setSize(525, 595);
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		this.add(getJPanelTime(), gridBagConstraints5);
		this.add(getJPanelBluetooth(), gridBagConstraints10);
		this.add(getJPanelLocation(), gridBagConstraints13);
		this.add(getJPanelFileSystem(), gridBagConstraints15);
		this.add(getJPanelOracles(), gridBagConstraints17);
		this.add(jLabelLogo, gridBagConstraints18);
	}

	/**
	 * This method initializes jPanelTime	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTime() {
		if (jPanelTime == null) {
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 4;
			gridBagConstraints19.fill = GridBagConstraints.NONE;
			gridBagConstraints19.gridy = 0;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.gridy = 1;
			gridBagConstraints21.weightx = 0.0D;
			gridBagConstraints21.gridwidth = 5;
			gridBagConstraints21.gridx = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 5;
			gridBagConstraints11.gridy = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 3;
			gridBagConstraints3.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			jPanelTime = new JPanel();
			jPanelTime.setLayout(new GridBagLayout());
			jPanelTime.setBorder(BorderFactory.createTitledBorder(null, "Time", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelTime.add(getJToggleButtonTime_0_0(), gridBagConstraints);
			jPanelTime.add(getJToggleButtonTime_0_30(), gridBagConstraints1);
			jPanelTime.add(getJToggleButtonTime_1_0(), gridBagConstraints2);
			jPanelTime.add(getJToggleButtonTime_2_0(), gridBagConstraints3);
			jPanelTime.add(getJToggleButtonTime_2_25(), gridBagConstraints19);
			jPanelTime.add(getJTextFieldCurrentTime(), gridBagConstraints21);
		}
		return jPanelTime;
	}

	/**
	 * This method initializes jToggleButtonTime_0_0	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonTime_0_0() {
		if (jToggleButtonTime_0_0 == null) {
			jToggleButtonTime_0_0 = new JToggleButton();
			jToggleButtonTime_0_0.setText("00:00");
			jToggleButtonTime_0_0.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jToggleButtonTime_0_0.isSelected())
					{
						_virtualClock.setSimulatedTime(_initialTime+0);
					}
				}
			});
			jToggleButtonTime_0_0.setSelected(true);
			this.getButtonGroupTime().add(jToggleButtonTime_0_0);
		}
		return jToggleButtonTime_0_0;
	}

	/**
	 * This method initializes jToggleButtonTime_0_30	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonTime_0_30() {
		if (jToggleButtonTime_0_30 == null) {
			jToggleButtonTime_0_30 = new JToggleButton();
			jToggleButtonTime_0_30.setText("00:30");
			jToggleButtonTime_0_30.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jToggleButtonTime_0_30.isSelected())
					{
						_virtualClock.setSimulatedTime(_initialTime+30*60*1000);
					}
				}
			});
			this.getButtonGroupTime().add(jToggleButtonTime_0_30);
		}
		return jToggleButtonTime_0_30;
	}

	/**
	 * This method initializes jToggleButtonTime_1_0	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonTime_1_0() {
		if (jToggleButtonTime_1_0 == null) {
			jToggleButtonTime_1_0 = new JToggleButton();
			jToggleButtonTime_1_0.setText("01:00");
			jToggleButtonTime_1_0.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jToggleButtonTime_1_0.isSelected())
					{
						_virtualClock.setSimulatedTime(_initialTime+60*60*1000);
					}
				}
			});
			this.getButtonGroupTime().add(jToggleButtonTime_1_0);
		}
		return jToggleButtonTime_1_0;
	}

	/**
	 * This method initializes jToggleButtonTime_2_0	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonTime_2_0() {
		if (jToggleButtonTime_2_0 == null) {
			jToggleButtonTime_2_0 = new JToggleButton();
			jToggleButtonTime_2_0.setText("02:00");
			jToggleButtonTime_2_0.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jToggleButtonTime_2_0.isSelected())
					{
						_virtualClock.setSimulatedTime(_initialTime+120*60*1000);
					}
				}
			});
			this.getButtonGroupTime().add(jToggleButtonTime_2_0);
		}
		return jToggleButtonTime_2_0;
	}

	/**
	 * This method initializes jToggleButtonTime_2_25	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonTime_2_25() {
		if (jToggleButtonTime_2_25 == null) {
			jToggleButtonTime_2_25 = new JToggleButton();
			jToggleButtonTime_2_25.setText("02:25");
			jToggleButtonTime_2_25.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jToggleButtonTime_2_25.isSelected())
					{
						_virtualClock.setSimulatedTime(_initialTime+145*60*1000);
					}
				}
			});
			this.getButtonGroupTime().add(jToggleButtonTime_2_25);
		}
		return jToggleButtonTime_2_25;
	}

	/**
	 * This method initializes buttonGroupTime	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */
	private ButtonGroup getButtonGroupTime() {
		if (buttonGroupTime == null) {
			buttonGroupTime = new ButtonGroup();
		}
		return buttonGroupTime;
	}

	/**
	 * This method initializes jTextFieldCurrentTime	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldCurrentTime() {
		if (jTextFieldCurrentTime == null) {
			jTextFieldCurrentTime = new JTextField();
			jTextFieldCurrentTime.setEditable(false);
			jTextFieldCurrentTime.setHorizontalAlignment(JTextField.CENTER);
			jTextFieldCurrentTime.setPreferredSize(new Dimension(150, 22));
			jTextFieldCurrentTime.setBackground(SystemColor.controlShadow);
			
			_timeUpdateThread=new Thread("CurrentTime field updater")
			{
				public void run()
				{
					while(_stopUpdating==false){
						long time=VirtualClock.getSimulatedTime();
						Date date=new Date(time);
						GregorianCalendar calendar=new GregorianCalendar();
						calendar.setTime(date);
						jTextFieldCurrentTime.setText(calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			_timeUpdateThread.start();
		}
		return jTextFieldCurrentTime;
	}

	/**
	 * This method initializes jPanelBluetooth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelBluetooth() {
		if (jPanelBluetooth == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = -1;
			gridBagConstraints4.gridx = -1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 3;
			gridBagConstraints9.gridy = 0;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.gridy = 0;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.gridy = 0;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 0;
			jPanelBluetooth = new JPanel();
			jPanelBluetooth.setLayout(new GridBagLayout());
			jPanelBluetooth.setBorder(BorderFactory.createTitledBorder(null, "Bluetooth", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelBluetooth.add(getJToggleButtonBT_mycomputer(), gridBagConstraints6);
			jPanelBluetooth.add(getJToggleButtonBT_mycellphone(), gridBagConstraints7);
			jPanelBluetooth.add(getJToggleButtonBT_bosscomputer(), gridBagConstraints8);
			jPanelBluetooth.add(getJToggleButtonBT_bosscellphone(), gridBagConstraints9);
		}
		return jPanelBluetooth;
	}

	/**
	 * This method initializes jToggleButtonBT_mycomputer	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonBT_mycomputer() {
		if (jToggleButtonBT_mycomputer == null) {
			jToggleButtonBT_mycomputer = new JToggleButton();
			jToggleButtonBT_mycomputer.setText("MyComputer");
			jToggleButtonBT_mycomputer.setVerticalTextPosition(SwingConstants.BOTTOM);
			jToggleButtonBT_mycomputer.setHorizontalTextPosition(SwingConstants.CENTER);
			jToggleButtonBT_mycomputer.setPreferredSize(new Dimension(85, 62));
			jToggleButtonBT_mycomputer.setIcon(new ImageIcon(getClass().getResource("/ucl/cs/testingEmulator/contexNotifierScripts/pc.jpg")));
			jToggleButtonBT_mycomputer
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if(jToggleButtonBT_mycomputer.isSelected())
							{
								DiscoveryAgent.getInstance().addRemoteDevice(_btMyComputer, _btMyComputer.getDeviceClass());
							}else
							{
								DiscoveryAgent.getInstance().removeRemoteDevice(_btMyComputer);
							}
						}
					});
		}
		return jToggleButtonBT_mycomputer;
	}

	/**
	 * This method initializes jToggleButtonBT_mycellphone	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonBT_mycellphone() {
		if (jToggleButtonBT_mycellphone == null) {
			jToggleButtonBT_mycellphone = new JToggleButton();
			jToggleButtonBT_mycellphone.setIcon(new ImageIcon(getClass().getResource("/ucl/cs/testingEmulator/contexNotifierScripts/ipod.png")));
			jToggleButtonBT_mycellphone.setVerticalTextPosition(SwingConstants.BOTTOM);
			jToggleButtonBT_mycellphone.setHorizontalTextPosition(SwingConstants.CENTER);
			jToggleButtonBT_mycellphone.setPreferredSize(new Dimension(85, 62));
			jToggleButtonBT_mycellphone.setText("MyMobile");
			jToggleButtonBT_mycellphone
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if(jToggleButtonBT_mycellphone.isSelected())
							{
								DiscoveryAgent.getInstance().addRemoteDevice(_btMyMobile, _btMyMobile.getDeviceClass());
							}else
							{
								DiscoveryAgent.getInstance().removeRemoteDevice(_btMyMobile);
							}
						}
					});
		}
		return jToggleButtonBT_mycellphone;
	}

	/**
	 * This method initializes jToggleButtonBT_bosscomputer	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonBT_bosscomputer() {
		if (jToggleButtonBT_bosscomputer == null) {
			jToggleButtonBT_bosscomputer = new JToggleButton();
			jToggleButtonBT_bosscomputer.setPreferredSize(new Dimension(85, 62));
			jToggleButtonBT_bosscomputer.setIcon(new ImageIcon(getClass().getResource("/ucl/cs/testingEmulator/contexNotifierScripts/pc.jpg")));
			jToggleButtonBT_bosscomputer.setText("BossComputer");
			jToggleButtonBT_bosscomputer.setVerticalTextPosition(SwingConstants.BOTTOM);
			jToggleButtonBT_bosscomputer.setHorizontalTextPosition(SwingConstants.CENTER);
			jToggleButtonBT_bosscomputer
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if(jToggleButtonBT_bosscomputer.isSelected())
							{
								DiscoveryAgent.getInstance().addRemoteDevice(_btMyBossComputer, _btMyBossComputer.getDeviceClass());
							}else
							{
								DiscoveryAgent.getInstance().removeRemoteDevice(_btMyBossComputer);
							}
						}
					});
		}
		return jToggleButtonBT_bosscomputer;
	}

	/**
	 * This method initializes jToggleButtonBT_bosscellphone	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonBT_bosscellphone() {
		if (jToggleButtonBT_bosscellphone == null) {
			jToggleButtonBT_bosscellphone = new JToggleButton();
			jToggleButtonBT_bosscellphone.setPreferredSize(new Dimension(85, 62));
			jToggleButtonBT_bosscellphone.setIcon(new ImageIcon(getClass().getResource("/ucl/cs/testingEmulator/contexNotifierScripts/ipod.png")));
			jToggleButtonBT_bosscellphone.setText("BossMobile");
			jToggleButtonBT_bosscellphone.setVerticalTextPosition(SwingConstants.BOTTOM);
			jToggleButtonBT_bosscellphone.setHorizontalTextPosition(SwingConstants.CENTER);
			jToggleButtonBT_bosscellphone
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if(jToggleButtonBT_bosscellphone.isSelected())
							{
								DiscoveryAgent.getInstance().addRemoteDevice(_btMyBossMobile, _btMyBossMobile.getDeviceClass());
							}else
							{
								DiscoveryAgent.getInstance().removeRemoteDevice(_btMyBossMobile);
							}
						}
					});
		}
		return jToggleButtonBT_bosscellphone;
	}

	/**
	 * This method initializes jPanelLocation	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelLocation() {
		if (jPanelLocation == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.NONE;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.weighty = 1.0;
			gridBagConstraints12.gridx = 0;
			jPanelLocation = new JPanel();
			jPanelLocation.setLayout(new GridBagLayout());
			jPanelLocation.setBorder(BorderFactory.createTitledBorder(null, "Location", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			//jPanelLocation.setPreferredSize(new Dimension(380, 200));
			jPanelLocation.add(getMovementJPanel(), gridBagConstraints12);
		}
		return jPanelLocation;
	}

	/**
	 * This method initializes jPanelFileSystem	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelFileSystem() {
		if (jPanelFileSystem == null) {
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.gridy = 0;
			jPanelFileSystem = new JPanel();
			jPanelFileSystem.setLayout(new GridBagLayout());
			jPanelFileSystem.setBorder(BorderFactory.createTitledBorder(null, "FileSystem", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelFileSystem.add(getJToggleButtonPresentationPPT(), gridBagConstraints14);
		}
		return jPanelFileSystem;
	}

	/**
	 * This method initializes jToggleButtonPresentationPPT	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonPresentationPPT() {
		if (jToggleButtonPresentationPPT == null) {
			jToggleButtonPresentationPPT = new JToggleButton();
			jToggleButtonPresentationPPT.setText("Presentation.ppt");
			jToggleButtonPresentationPPT.setVerticalTextPosition(SwingConstants.BOTTOM);
			jToggleButtonPresentationPPT.setHorizontalTextPosition(SwingConstants.CENTER);
			jToggleButtonPresentationPPT.setIcon(new ImageIcon(getClass().getResource("/ucl/cs/testingEmulator/contexNotifierScripts/ppt.jpg")));
			jToggleButtonPresentationPPT
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if(jToggleButtonPresentationPPT.isSelected()){
								createFile("Presentation.ppt");
							}else
							{
								deleteFile("Presentation.ppt");
							}
							if(_sampleFileOracle.isVisible())
							{
								_sampleFileOracle.checkStatus();
							}
						}
					});
		}
		return jToggleButtonPresentationPPT;
	}

	protected void createFile(String filename) {
		File f=new File(this._meetingFileSystem.getRealPath()+System.getProperty("file.separator")+filename);
		if(f.exists()==false)
		{
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void deleteFile(String filename){
		File f=new File(this._meetingFileSystem.getRealPath()+System.getProperty("file.separator")+filename);
		if(f.exists()==true)
		{
			f.delete();
		}
	}

	/**
	 * This method initializes jPanelOracles	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelOracles() {
		if (jPanelOracles == null) {
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.gridy = 0;
			jPanelOracles = new JPanel();
			jPanelOracles.setLayout(new GridBagLayout());
			jPanelOracles.setBorder(BorderFactory.createTitledBorder(null, "Oracles", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelOracles.add(getJToggleButtonFileOracle(), gridBagConstraints16);
		}
		return jPanelOracles;
	}

	/**
	 * This method initializes jToggleButtonFileOracle	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JToggleButton getJToggleButtonFileOracle() {
		if (jToggleButtonFileOracle == null) {
			jToggleButtonFileOracle = new JToggleButton();
			jToggleButtonFileOracle.setText("File");
			jToggleButtonFileOracle.setVerticalTextPosition(SwingConstants.BOTTOM);
			jToggleButtonFileOracle.setHorizontalTextPosition(SwingConstants.CENTER);
			jToggleButtonFileOracle.setPreferredSize(new Dimension(85, 62));
			jToggleButtonFileOracle.setIcon(new ImageIcon(getClass().getResource("/ucl/cs/testingEmulator/contexNotifierScripts/sfinge.jpg")));
			jToggleButtonFileOracle.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					_sampleFileOracle.setVisible(jToggleButtonFileOracle.isSelected());
				}
			});
		}
		return jToggleButtonFileOracle;
	}

	/**
	 * This method initializes movementJPanel	
	 * 	
	 * @return ucl.cs.testingEmulator.contexNotifierScripts.MovementJComponent	
	 */
	private MovementJComponent getMovementJPanel() {
		if (movementJPanel == null) {
			movementJPanel = new MovementJComponent();
			movementJPanel.setMinimumSize(new Dimension(360, 180));
			movementJPanel.setMaximumSize(new Dimension(360, 180));
		}
		return movementJPanel;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
