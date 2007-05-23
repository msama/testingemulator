/**
 *
 */
package ucl.cs.testingEmulator.core;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JTabbedPane;

import ucl.cs.testingEmulator.contexNotifierScripts.Demo26March2007;
import ucl.cs.testingEmulator.contexNotifierScripts.MobiSys2007;
import ucl.cs.testingEmulator.contexNotifierScripts.TestingAdaptiveReminder;
import ucl.cs.testingEmulator.sampleScript.AddFakeRoots;
import ucl.cs.testingEmulator.sampleScript.TimeScript;



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
public class ScriptLoaderJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox jComboBoxClassName = null;
	private JButton jButtonRunClass = null;
	/**
	 * This is the default constructor
	 */
	public ScriptLoaderJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 2;
		gridBagConstraints1.gridy = 0;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		this.setSize(402, 200);
		this.setLayout(new GridBagLayout());
		this.add(getJComboBoxClassName(), gridBagConstraints);
		this.add(getJButtonRunClass(), gridBagConstraints1);
	}

	/**
	 * This method initializes jComboBoxClassName	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxClassName() {
		if (jComboBoxClassName == null) {
			jComboBoxClassName = new JComboBox();
			jComboBoxClassName.setEditable(true);
			jComboBoxClassName.setPreferredSize(new Dimension(350, 27));
			String script="Script";
			int k=0;
			String classname=null;
			//TODO this is not complete and it is not working yet!!!!!!!!!
			do{
				classname=System.getProperty(script+k);
				System.out.println("ScriptLoaderJPanel loading "+script+k+": "+classname);
				k++;
				if(classname!=null)
				{
					jComboBoxClassName.addItem(classname);
				}
			}while(classname!=null);
			jComboBoxClassName.addItem(MobiSys2007.class.getCanonicalName());
			jComboBoxClassName.addItem(Demo26March2007.class.getCanonicalName());
			jComboBoxClassName.addItem(TimeScript.class.getCanonicalName());
			jComboBoxClassName.addItem(AddFakeRoots.class.getCanonicalName());
			jComboBoxClassName.addItem(TestingAdaptiveReminder.class.getCanonicalName());
		}
		return jComboBoxClassName;
	}

	/**
	 * This method initializes jButtonRunClass	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRunClass() {
		if (jButtonRunClass == null) {
			jButtonRunClass = new JButton();
			jButtonRunClass.setText("Run");
			jButtonRunClass.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String file=(String)getJComboBoxClassName().getSelectedItem();
					if(file==null){return;}
					if(getJComboBoxClassName().getSelectedIndex()==-1)
					{
						getJComboBoxClassName().addItem(file);
					}
					try {
						Script script=ScriptLoader.loadScriptByName(file);
						ScriptLoader.runScript(script);
					} catch (ClassNotFoundException ex) {
						ex.printStackTrace();
					} 
				}
			});
		}
		return jButtonRunClass;
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
