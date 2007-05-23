/**
 *
 */
package ucl.cs.testingEmulator.core;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;

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
public class ConsoleJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanelSouth = null;
	private JCheckBox jCheckBoxUseColors = null;
	private JCheckBox jCheckBoxPrintInfo = null;
	private JButton jButtonSave = null;
	private JButton jButtonClear = null;
	private JScrollPane jScrollPane = null;
	private JEditorPane jEditorPane = null;

	/**
	 * This is the default constructor
	 */
	public ConsoleJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(459, 200);
		this.setLayout(new BorderLayout());
		this.add(getJPanelSouth(), BorderLayout.SOUTH);
		this.add(getJScrollPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jPanelSouth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelSouth() {
		if (jPanelSouth == null) {
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
			jPanelSouth = new JPanel();
			jPanelSouth.setLayout(new GridBagLayout());
			jPanelSouth.add(getJCheckBoxUseColors(), gridBagConstraints);
			jPanelSouth.add(getJCheckBoxPrintInfo(), gridBagConstraints1);
			jPanelSouth.add(getJButtonSave(), gridBagConstraints2);
			jPanelSouth.add(getJButtonClear(), gridBagConstraints3);
		}
		return jPanelSouth;
	}

	/**
	 * This method initializes jCheckBoxUseColors	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxUseColors() {
		if (jCheckBoxUseColors == null) {
			jCheckBoxUseColors = new JCheckBox();
			jCheckBoxUseColors.setText("UseColors");
		}
		return jCheckBoxUseColors;
	}

	/**
	 * This method initializes jCheckBoxPrintInfo	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxPrintInfo() {
		if (jCheckBoxPrintInfo == null) {
			jCheckBoxPrintInfo = new JCheckBox();
			jCheckBoxPrintInfo.setText("PrintInfo");
		}
		return jCheckBoxPrintInfo;
	}

	/**
	 * This method initializes jButtonSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSave() {
		if (jButtonSave == null) {
			jButtonSave = new JButton();
			jButtonSave.setText("Save");
		}
		return jButtonSave;
	}

	/**
	 * This method initializes jButtonClear	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonClear() {
		if (jButtonClear == null) {
			jButtonClear = new JButton();
			jButtonClear.setText("Clear");
		}
		return jButtonClear;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJEditorPane());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
		}
		return jEditorPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
