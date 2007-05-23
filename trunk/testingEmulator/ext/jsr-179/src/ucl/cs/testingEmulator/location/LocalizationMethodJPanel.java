/**
 *
 */
package ucl.cs.testingEmulator.location;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
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
public class LocalizationMethodJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private LocalizationMethodWrapper _locationMethod=null;  //  @jve:decl-index=0:

	private JLabel jLabelName = null;

	private JLabel jLabelValue = null;

	private JTextPane jTextPaneDescription = null;

	/**
	 * This is the default constructor
	 */
	public LocalizationMethodJPanel() {
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
		gridBagConstraints11.fill = GridBagConstraints.BOTH;
		gridBagConstraints11.gridy = 1;
		gridBagConstraints11.weightx = 1.0;
		gridBagConstraints11.weighty = 1.0;
		gridBagConstraints11.gridwidth = 2;
		gridBagConstraints11.gridx = 0;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 0;
		jLabelValue = new JLabel();
		jLabelValue.setText("Value");
		jLabelValue.setPreferredSize(new Dimension(150, 16));
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		jLabelName = new JLabel();
		jLabelName.setText("Name");
		jLabelName.setPreferredSize(new Dimension(200, 16));
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.add(jLabelName, gridBagConstraints);
		this.add(jLabelValue, gridBagConstraints1);
		this.add(getJTextPaneDescription(), gridBagConstraints11);
	}

	/**
	 * This method initializes jTextPaneDescription	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPaneDescription() {
		if (jTextPaneDescription == null) {
			jTextPaneDescription = new JTextPane();
			jTextPaneDescription.setEditable(false);
			jTextPaneDescription.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			//jTextPaneDescription.setBackground(SystemColor.control);
			jTextPaneDescription.setOpaque(false);
			
		}
		return jTextPaneDescription;
	}

	/**
	 * @param _locationMethod the _locationMethod to set
	 */
	public void setLocationMethod(LocalizationMethodWrapper _locationMethod) {
		this._locationMethod = _locationMethod;
		if(this._locationMethod==null)
		{
			this.jLabelName.setText("Name");
			this.jLabelValue.setText("0");
			this.jTextPaneDescription.setText("");
		}else
		{
			this.jLabelName.setText(this._locationMethod.getName());
			this.jLabelValue.setText("Value:"+this._locationMethod.getValue());
			this.jTextPaneDescription.setText(this._locationMethod.getDescription());
		}
	}

	/**
	 * @return the _locationMethod
	 */
	public LocalizationMethodWrapper getLocationMethod() {
		return _locationMethod;
	}

}
