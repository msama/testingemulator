/**
 *
 */
package ucl.cs.testingEmulator.location;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JList;
import java.awt.Insets;
import javax.swing.JTree;

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
public class LandmarkStoreJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox jCheckBoxAllowMultipleLandmarkStore = null;
	private JPanel jPanelDefaultLandmarkStore = null;
	private JRadioButton jRadioButtonUseDefault = null;
	private JRadioButton jRadioButtonCustom = null;
	private JComboBox jComboBoxCustomStore = null;
	private JButton jButtonSelectDefaultLandmarkStore = null;
	private ButtonGroup buttonGroupDefaultStore = null;  //  @jve:decl-index=0:visual-constraint="524,57"
	private JPanel jPanelLandmarkStores = null;
	private JList jListLandmarkStore = null;
	private JButton jButtonAdd = null;
	private JButton jButtonRemove = null;
	private JPanel jPanelLandmarks = null;
	private JTree jTreeLandmarks = null;
	private JButton jButtonAddLandmark = null;
	private JButton jButtonRemoveLandmark = null;
	private JButton jButtonAddCathegory = null;
	private JButton jButtonRemoveCathegory = null;
	/**
	 * This is the default constructor
	 */
	public LandmarkStoreJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.getButtonGroupDefaultStore();
		GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
		gridBagConstraints15.gridx = 0;
		gridBagConstraints15.fill = GridBagConstraints.BOTH;
		gridBagConstraints15.gridy = 3;
		GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBagConstraints9.gridx = 0;
		gridBagConstraints9.fill = GridBagConstraints.BOTH;
		gridBagConstraints9.gridy = 2;
		GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
		gridBagConstraints8.gridx = 0;
		gridBagConstraints8.fill = GridBagConstraints.BOTH;
		gridBagConstraints8.gridy = 1;
		GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBagConstraints7.gridx = 0;
		gridBagConstraints7.fill = GridBagConstraints.BOTH;
		gridBagConstraints7.gridy = 0;
		this.setLayout(new GridBagLayout());
		this.setSize(422, 362);
		this.add(getJCheckBoxAllowMultipleLandmarkStore(), gridBagConstraints7);
		this.add(getJPanelDefaultLandmarkStore(), gridBagConstraints8);
		this.add(getJPanelLandmarkStores(), gridBagConstraints9);
		this.add(getJPanelLandmarks(), gridBagConstraints15);
	}

	/**
	 * This method initializes jCheckBoxAllowMultipleLandmarkStore	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxAllowMultipleLandmarkStore() {
		if (jCheckBoxAllowMultipleLandmarkStore == null) {
			jCheckBoxAllowMultipleLandmarkStore = new JCheckBox();
			jCheckBoxAllowMultipleLandmarkStore.setText("allow multiple LandmarkStore");
		}
		return jCheckBoxAllowMultipleLandmarkStore;
	}

	/**
	 * This method initializes jPanelDefaultLandmarkStore	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelDefaultLandmarkStore() {
		if (jPanelDefaultLandmarkStore == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.weighty = 0.0D;
			gridBagConstraints.weightx = 0.0D;
			gridBagConstraints.gridwidth = 3;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			jPanelDefaultLandmarkStore = new JPanel();
			jPanelDefaultLandmarkStore.setLayout(new GridBagLayout());
			jPanelDefaultLandmarkStore.setBorder(BorderFactory.createTitledBorder(null, "Default LandmarkStore", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelDefaultLandmarkStore.add(getJRadioButtonUseDefault(), gridBagConstraints);
			jPanelDefaultLandmarkStore.add(getJRadioButtonCustom(), gridBagConstraints1);
			jPanelDefaultLandmarkStore.add(getJComboBoxCustomStore(), gridBagConstraints2);
			jPanelDefaultLandmarkStore.add(getJButtonSelectDefaultLandmarkStore(), gridBagConstraints3);
		}
		return jPanelDefaultLandmarkStore;
	}

	/**
	 * This method initializes jRadioButtonUseDefault	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonUseDefault() {
		if (jRadioButtonUseDefault == null) {
			jRadioButtonUseDefault = new JRadioButton();
			jRadioButtonUseDefault.setName("");
			jRadioButtonUseDefault.setPreferredSize(new Dimension(200, 16));
			jRadioButtonUseDefault.setText("EmulatorDefaultStore");
		}
		return jRadioButtonUseDefault;
	}

	/**
	 * This method initializes jRadioButtonCustom	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRadioButtonCustom() {
		if (jRadioButtonCustom == null) {
			jRadioButtonCustom = new JRadioButton();
			jRadioButtonCustom.setText("Custom");
		}
		return jRadioButtonCustom;
	}

	/**
	 * This method initializes jComboBoxCustomStore	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxCustomStore() {
		if (jComboBoxCustomStore == null) {
			jComboBoxCustomStore = new JComboBox();
			jComboBoxCustomStore.setPreferredSize(new Dimension(200, 27));
			jComboBoxCustomStore.setEditable(true);
		}
		return jComboBoxCustomStore;
	}

	/**
	 * This method initializes jButtonSelectDefaultLandmarkStore	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSelectDefaultLandmarkStore() {
		if (jButtonSelectDefaultLandmarkStore == null) {
			jButtonSelectDefaultLandmarkStore = new JButton();
			jButtonSelectDefaultLandmarkStore.setText("...");
		}
		return jButtonSelectDefaultLandmarkStore;
	}

	/**
	 * This method initializes buttonGroupDefaultStore	
	 * 	
	 * @return javax.swing.ButtonGroup	
	 */
	private ButtonGroup getButtonGroupDefaultStore() {
		if (buttonGroupDefaultStore == null) {
			buttonGroupDefaultStore = new ButtonGroup();
			buttonGroupDefaultStore.add(this.getJRadioButtonCustom());
			buttonGroupDefaultStore.add(this.getJRadioButtonUseDefault());
		}
		return buttonGroupDefaultStore;
	}

	/**
	 * This method initializes jPanelLandmarkStores	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelLandmarkStores() {
		if (jPanelLandmarkStores == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.anchor = GridBagConstraints.NORTH;
			gridBagConstraints6.gridy = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.BOTH;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.weighty = 1.0;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridwidth = 1;
			gridBagConstraints4.gridheight = 2;
			gridBagConstraints4.gridx = 0;
			jPanelLandmarkStores = new JPanel();
			jPanelLandmarkStores.setLayout(new GridBagLayout());
			jPanelLandmarkStores.setBorder(BorderFactory.createTitledBorder(null, "LandmarkStore", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelLandmarkStores.add(getJListLandmarkStore(), gridBagConstraints4);
			jPanelLandmarkStores.add(getJButtonAdd(), gridBagConstraints5);
			jPanelLandmarkStores.add(getJButtonRemove(), gridBagConstraints6);
		}
		return jPanelLandmarkStores;
	}

	/**
	 * This method initializes jListLandmarkStore	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListLandmarkStore() {
		if (jListLandmarkStore == null) {
			jListLandmarkStore = new JList();
			jListLandmarkStore.setPreferredSize(new Dimension(100, 50));
		}
		return jListLandmarkStore;
	}

	/**
	 * This method initializes jButtonAdd	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton();
			jButtonAdd.setText("Add");
		}
		return jButtonAdd;
	}

	/**
	 * This method initializes jButtonRemove	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRemove() {
		if (jButtonRemove == null) {
			jButtonRemove = new JButton();
			jButtonRemove.setText("Remove");
		}
		return jButtonRemove;
	}

	/**
	 * This method initializes jPanelLandmarks	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelLandmarks() {
		if (jPanelLandmarks == null) {
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.gridy = 3;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.gridy = 2;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 0;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.BOTH;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.weighty = 1.0;
			gridBagConstraints10.gridheight = 4;
			gridBagConstraints10.gridx = 0;
			jPanelLandmarks = new JPanel();
			jPanelLandmarks.setLayout(new GridBagLayout());
			jPanelLandmarks.setBorder(BorderFactory.createTitledBorder(null, "Landmark", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelLandmarks.add(getJTreeLandmarks(), gridBagConstraints10);
			jPanelLandmarks.add(getJButtonAddLandmark(), gridBagConstraints11);
			jPanelLandmarks.add(getJButtonRemoveLandmark(), gridBagConstraints12);
			jPanelLandmarks.add(getJButtonAddCathegory(), gridBagConstraints13);
			jPanelLandmarks.add(getJButtonRemoveCathegory(), gridBagConstraints14);
		}
		return jPanelLandmarks;
	}

	/**
	 * This method initializes jTreeLandmarks	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getJTreeLandmarks() {
		if (jTreeLandmarks == null) {
			jTreeLandmarks = new JTree();
		}
		return jTreeLandmarks;
	}

	/**
	 * This method initializes jButtonAddLandmark	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAddLandmark() {
		if (jButtonAddLandmark == null) {
			jButtonAddLandmark = new JButton();
			jButtonAddLandmark.setText("AddLandmark");
		}
		return jButtonAddLandmark;
	}

	/**
	 * This method initializes jButtonRemoveLandmark	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRemoveLandmark() {
		if (jButtonRemoveLandmark == null) {
			jButtonRemoveLandmark = new JButton();
			jButtonRemoveLandmark.setText("RemoveLandmark");
		}
		return jButtonRemoveLandmark;
	}

	/**
	 * This method initializes jButtonAddCathegory	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAddCathegory() {
		if (jButtonAddCathegory == null) {
			jButtonAddCathegory = new JButton();
			jButtonAddCathegory.setText("AddCathegory");
		}
		return jButtonAddCathegory;
	}

	/**
	 * This method initializes jButtonRemoveCathegory	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRemoveCathegory() {
		if (jButtonRemoveCathegory == null) {
			jButtonRemoveCathegory = new JButton();
			jButtonRemoveCathegory.setText("RemoveCathegory");
		}
		return jButtonRemoveCathegory;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
