/**
 *
 */
package ucl.cs.testingEmulator.location;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.Dimension;

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
public class LocalizationMethodsJPanel extends JPanel {

	TestingLocationProvider _testingLocationProvider=TestingLocationProvider.getInstance();
	
	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPaneLocalizationMethod = null;
	private JList jListLocalizationMethod = null;
	
	
	
	
	/**
	 * This is the default constructor
	 */
	public LocalizationMethodsJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setSize(300, 200);

		this.add(getJScrollPaneLocalizationMethod(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jScrollPaneLocalizationMethod	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPaneLocalizationMethod() {
		if (jScrollPaneLocalizationMethod == null) {
			jScrollPaneLocalizationMethod = new JScrollPane();
			jScrollPaneLocalizationMethod.setPreferredSize(new Dimension(0, 0));
			jScrollPaneLocalizationMethod.setViewportView(getJListLocalizationMethod());
		}
		return jScrollPaneLocalizationMethod;
	}

	/**
	 * This method initializes jListLocalizationMethod	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListLocalizationMethod() {
		if (jListLocalizationMethod == null) {
			jListLocalizationMethod = new JList(LocalizationMethodWrapper.getAllKnownLocalizationMethod());
			jListLocalizationMethod
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(javax.swing.event.ListSelectionEvent e) {
							LocalizationMethodWrapper method= (LocalizationMethodWrapper)jListLocalizationMethod.getSelectedValue();
							if(method!=null){
								TestingLocationProvider.getInstance().getCurrentEmulatedLocation().setLocationMethod(method.getValue());
							}
						}
					});
			jListLocalizationMethod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jListLocalizationMethod.setCellRenderer(new LocalizationListCellRender());
		}
		return jListLocalizationMethod;
	}

}  //  @jve:decl-index=0:visual-constraint="200,111"
