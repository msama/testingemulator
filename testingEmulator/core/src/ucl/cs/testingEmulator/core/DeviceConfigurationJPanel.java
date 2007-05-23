/**
 * 
 */
package ucl.cs.testingEmulator.core;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
public class DeviceConfigurationJPanel extends JPanel implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private JCheckBox jCheckBoxJSR82 = null;
	private JCheckBox jCheckBoxJSR75PIM = null;
	private JCheckBox jCheckBoxJSR75FC = null;
	private JCheckBox jCheckBoxJSR179 = null;

	/**
	 * This is the default constructor
	 */
	public DeviceConfigurationJPanel() {
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
		GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBagConstraints11.gridx = 0;
		gridBagConstraints11.gridy = 3;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 2;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getJCheckBoxJSR82(), gridBagConstraints);
		this.add(getJCheckBoxJSR75PIM(), gridBagConstraints1);
		this.add(getJCheckBoxJSR75FC(), gridBagConstraints2);
		this.add(getJCheckBoxJSR179(), gridBagConstraints11);
	}

	/**
	 * This method initializes jCheckBoxJSR82	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxJSR82() {
		if (jCheckBoxJSR82 == null) {
			jCheckBoxJSR82 = new JCheckBox();
			jCheckBoxJSR82.setText("JSR82");
			jCheckBoxJSR82.setPreferredSize(new Dimension(100, 22));
			jCheckBoxJSR82.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TestingEmulator.getInstance().setJSR82Loaded(jCheckBoxJSR82.isSelected());
				}
			});
		}
		return jCheckBoxJSR82;
	}

	/**
	 * This method initializes jCheckBoxJSR75PIM	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxJSR75PIM() {
		if (jCheckBoxJSR75PIM == null) {
			jCheckBoxJSR75PIM = new JCheckBox();
			jCheckBoxJSR75PIM.setText("JSR75PIM");
			jCheckBoxJSR75PIM.setPreferredSize(new Dimension(100, 22));
			jCheckBoxJSR75PIM.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TestingEmulator.getInstance().setJSR75PimLoaded(jCheckBoxJSR75PIM.isSelected());
				}
			});
		}
		return jCheckBoxJSR75PIM;
	}

	/**
	 * This method initializes jCheckBoxJSR75FC	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxJSR75FC() {
		if (jCheckBoxJSR75FC == null) {
			jCheckBoxJSR75FC = new JCheckBox();
			jCheckBoxJSR75FC.setText("JSR75FC");
			jCheckBoxJSR75FC.setPreferredSize(new Dimension(100, 22));
			jCheckBoxJSR75FC.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TestingEmulator.getInstance().setJSR75FcLoaded(jCheckBoxJSR75FC.isSelected());
				}
			});
		}
		return jCheckBoxJSR75FC;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String act=evt.getPropertyName();
		if(act.equals(TestingEmulator.JSR82))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxJSR82().setSelected(b);
		}else if(act.equals(TestingEmulator.JSR75PIM))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxJSR75PIM().setSelected(b);
		}else if(act.equals(TestingEmulator.JSR75FC))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxJSR75FC().setSelected(b);
		}else if(act.equals(TestingEmulator.JSR179))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxJSR179().setSelected(b);
		}
		
	}

	/**
	 * This method initializes jCheckBoxJSR179	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxJSR179() {
		if (jCheckBoxJSR179 == null) {
			jCheckBoxJSR179 = new JCheckBox();
			jCheckBoxJSR179.setText("JSR179");
			jCheckBoxJSR179.setPreferredSize(new Dimension(100, 22));
			jCheckBoxJSR179.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TestingEmulator.getInstance().setJSR179Loaded(jCheckBoxJSR179.isSelected());
				}
			});
		}
		return jCheckBoxJSR179;
	}

}
