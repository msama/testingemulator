/**
 * 
 */
package ucl.cs.testingEmulator.core;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JFrame;

import org.microemu.app.Main;

import ucl.cs.testingEmulator.bluetooth.BluetoothJPanel;
import ucl.cs.testingEmulator.time.VirtualClockJPanel;

import javax.swing.JTabbedPane;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import ucl.cs.testingEmulator.connection.file.FileSystemRegistryJPanel;
//import java.awt.Dimension;
import ucl.cs.testingEmulator.location.LocationJTabbedPane;

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
public class TestingJFrame extends JFrame implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private BluetoothJPanel bluetoothJPanel = null;

	private JTabbedPane jTabbedPaneComponents = null;

	private DeviceConfigurationJPanel deviceConfigurationJPanel = null;

	private VirtualClockJPanel virtualClockJPanel = null;

	private ScriptLoaderJPanel scriptLoaderJPanel = null;

	private FileSystemRegistryJPanel fileSystemRegistryJPanel = null;

	private LocationJTabbedPane locationJTabbedPane = null;



	/**
	 * This method initializes jTabbedPaneComponents	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPaneComponents() {
		if (jTabbedPaneComponents == null) {
			jTabbedPaneComponents = new JTabbedPane();
			jTabbedPaneComponents.addTab("Device", null, getDeviceConfigurationJPanel(), null);
			jTabbedPaneComponents.addTab("Bluetooth", null, getBluetoothJPanel(), null);
			jTabbedPaneComponents.addTab("Time", null, getVirtualClockJPanel(), null);
			jTabbedPaneComponents.addTab("Location", null, getLocationJTabbedPane(), null);
			jTabbedPaneComponents.addTab("FileSystem", null, getFileSystemRegistryJPanel(), null);
			jTabbedPaneComponents.addTab("Script", null, getScriptLoaderJPanel(), null);
			
		}
		return jTabbedPaneComponents;
	}

	/**
	 * This method initializes deviceConfigurationJPanel	
	 * 	
	 * @return cs.ucl.testingEmulator.core.DeviceConfigurationJPanel	
	 */
	private DeviceConfigurationJPanel getDeviceConfigurationJPanel() {
		if (deviceConfigurationJPanel == null) {
			deviceConfigurationJPanel = new DeviceConfigurationJPanel();
		}
		return deviceConfigurationJPanel;
	}

	/**
	 * This method initializes virtualClockJPanel	
	 * 	
	 * @return cs.ucl.testingEmulator.time.VirtualClockJPanel	
	 */
	private VirtualClockJPanel getVirtualClockJPanel() {
		if (virtualClockJPanel == null) {
			virtualClockJPanel = new VirtualClockJPanel();
		}
		return virtualClockJPanel;
	}

	/**
	 * This method initializes scriptLoaderJPanel	
	 * 	
	 * @return cs.ucl.testingEmulator.core.ScriptLoaderJPanel	
	 */
	private ScriptLoaderJPanel getScriptLoaderJPanel() {
		if (scriptLoaderJPanel == null) {
			scriptLoaderJPanel = new ScriptLoaderJPanel();
		}
		return scriptLoaderJPanel;
	}

	/**
	 * This method initializes fileSystemRegistryJPanel	
	 * 	
	 * @return ucl.cs.testingEmulator.connection.file.FileSystemRegistryJPanel	
	 */
	private FileSystemRegistryJPanel getFileSystemRegistryJPanel() {
		if (fileSystemRegistryJPanel == null) {
			fileSystemRegistryJPanel = new FileSystemRegistryJPanel();
		}
		return fileSystemRegistryJPanel;
	}

	/**
	 * This method initializes locationJTabbedPane	
	 * 	
	 * @return ucl.cs.testingEmulator.location.LocationJTabbedPane	
	 */
	private LocationJTabbedPane getLocationJTabbedPane() {
		if (locationJTabbedPane == null) {
			locationJTabbedPane = new LocationJTabbedPane();
		}
		return locationJTabbedPane;
	}

	public static void main(String args[])
	{
		System.out.println(""+System.currentTimeMillis());
		TestingJFrame testingJFrame=new TestingJFrame();
		testingJFrame.setVisible(true);
	    Main.main(args);
	}
	
	/**
	 * This is the default constructor
	 */
	public TestingJFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		TestingEmulator.getInstance().addPropertyChangeListener(this);
		this.setSize(629, 405);
		this.setContentPane(getJContentPane());
		this.setTitle("TestingEmulator");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setName("TestingJFrame");
			jContentPane.add(getJTabbedPaneComponents(), BorderLayout.CENTER);
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes bluetoothJPanel	
	 * 	
	 * @return javax.bluetooth.BlueetoothJPanel	
	 */
	private BluetoothJPanel getBluetoothJPanel() {
		if (bluetoothJPanel == null) {
			bluetoothJPanel = new BluetoothJPanel();
		}
		return bluetoothJPanel;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String act=evt.getPropertyName();
		if(act.equals(TestingEmulator.JSR82))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJTabbedPaneComponents().setEnabledAt(this.getJTabbedPaneComponents().indexOfComponent(this.getBluetoothJPanel()), b);
			this.getBluetoothJPanel().setEnabled(b);
		}else if(act.equals(TestingEmulator.JSR75PIM))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();	
		}else if(act.equals(TestingEmulator.JSR75FC))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJTabbedPaneComponents().setEnabledAt(this.getJTabbedPaneComponents().indexOfComponent(this.getFileSystemRegistryJPanel()), b);
			this.getFileSystemRegistryJPanel().setEnabled(b);
		}
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
