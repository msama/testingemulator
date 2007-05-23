/**
 *
 */
package ucl.cs.testingEmulator.location;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
public class OrientationProviderJPanel extends JPanel implements PropertyChangeListener {

	
	private OrientationProvider _orientationProvider=null;  //  @jve:decl-index=0:
	
	private static final long serialVersionUID = 1L;
	private JLabel jLabelAzimuth = null;
	private JSlider jSliderAzimuth = null;
	private JLabel jLabelRoll = null;
	private JSlider jSliderRoll = null;
	private JLabel jLabelPitch = null;
	private JSlider jSliderPitch = null;
	private JCheckBox jCheckBoxIsMagnetic = null;

	/**
	 * This is the default constructor
	 */
	public OrientationProviderJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this._orientationProvider=OrientationProvider.getInstance();
		this._orientationProvider.addPropertyChangeListener(this);
		GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBagConstraints6.gridx = 0;
		gridBagConstraints6.gridy = 3;
		GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
		gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints5.gridy = 2;
		gridBagConstraints5.weightx = 1.0;
		gridBagConstraints5.gridx = 1;
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.gridy = 2;
		jLabelPitch = new JLabel();
		jLabelPitch.setText("Pitch");
		jLabelPitch.setPreferredSize(new Dimension(100, 16));
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints3.gridy = 1;
		gridBagConstraints3.weightx = 1.0;
		gridBagConstraints3.gridx = 1;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.gridy = 1;
		jLabelRoll = new JLabel();
		jLabelRoll.setText("Roll");
		jLabelRoll.setPreferredSize(new Dimension(100, 16));
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.gridx = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		jLabelAzimuth = new JLabel();
		jLabelAzimuth.setText("Azimuth");
		jLabelAzimuth.setPreferredSize(new Dimension(100, 16));
		this.setSize(448, 200);
		this.setLayout(new GridBagLayout());
		this.add(jLabelAzimuth, gridBagConstraints);
		this.add(getJSliderAzimuth(), gridBagConstraints1);
		this.add(jLabelRoll, gridBagConstraints2);
		this.add(getJSliderRoll(), gridBagConstraints3);
		this.add(jLabelPitch, gridBagConstraints4);
		this.add(getJSliderPitch(), gridBagConstraints5);
		this.add(getJCheckBoxIsMagnetic(), gridBagConstraints6);
	}

	/**
	 * This method initializes jSliderAzimuth	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSliderAzimuth() {
		if (jSliderAzimuth == null) {
			jSliderAzimuth = new JSlider();
			jSliderAzimuth.setMaximum(360);
			jSliderAzimuth.setMinorTickSpacing(10);
			jSliderAzimuth.setPaintLabels(true);
			jSliderAzimuth.setPaintTicks(true);
			jSliderAzimuth.setPreferredSize(new Dimension(250, 54));
			jSliderAzimuth.setValue(0);
			jSliderAzimuth.setMajorTickSpacing(60);
			jSliderAzimuth.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					_orientationProvider.setAzimuth(jSliderAzimuth.getValue());
				}
			});
		}
		return jSliderAzimuth;
	}

	/**
	 * This method initializes jSliderRoll	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSliderRoll() {
		if (jSliderRoll == null) {
			jSliderRoll = new JSlider();
			jSliderRoll.setMaximum(180);
			jSliderRoll.setMajorTickSpacing(60);
			jSliderRoll.setMinorTickSpacing(10);
			jSliderRoll.setPaintLabels(true);
			jSliderRoll.setPaintTicks(true);
			jSliderRoll.setPreferredSize(new Dimension(250, 54));
			jSliderRoll.setValue(0);
			jSliderRoll.setMinimum(-180);
			jSliderRoll.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					_orientationProvider.setRoll(jSliderRoll.getValue());
				}
			});
		}
		return jSliderRoll;
	}

	/**
	 * This method initializes jSliderPitch	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSliderPitch() {
		if (jSliderPitch == null) {
			jSliderPitch = new JSlider();
			jSliderPitch.setMinimum(-90);
			jSliderPitch.setMajorTickSpacing(30);
			jSliderPitch.setMinorTickSpacing(10);
			jSliderPitch.setPaintLabels(true);
			jSliderPitch.setPaintTicks(true);
			jSliderPitch.setPreferredSize(new Dimension(250, 54));
			jSliderPitch.setValue(0);
			jSliderPitch.setMaximum(90);
			jSliderPitch.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					_orientationProvider.setPitch(jSliderPitch.getValue());
				}
			});
		}
		return jSliderPitch;
	}

	/**
	 * This method initializes jCheckBoxIsMagnetic	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxIsMagnetic() {
		if (jCheckBoxIsMagnetic == null) {
			jCheckBoxIsMagnetic = new JCheckBox();
			jCheckBoxIsMagnetic.setText("IsMagnetic");
			jCheckBoxIsMagnetic.setPreferredSize(new Dimension(100, 16));
			jCheckBoxIsMagnetic.setHorizontalTextPosition(SwingConstants.LEFT);
			jCheckBoxIsMagnetic.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					_orientationProvider.setMagnetic(jCheckBoxIsMagnetic.isSelected());
				}
			});
		}
		return jCheckBoxIsMagnetic;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String action=evt.getPropertyName();
		if(action.equals(OrientationProvider.AZIMUT_CHANGED))
		{
			float k=((Float)evt.getNewValue()).floatValue();
			this.getJSliderAzimuth().setValue((int)Math.floor(k));
		}else if(action.equals(OrientationProvider.PITCH_CHANGED))
		{
			float k=((Float)evt.getNewValue()).floatValue();
			this.getJSliderPitch().setValue((int)Math.floor(k));
		}else if(action.equals(OrientationProvider.ROLL_CHANGED))
		{
			float k=((Float)evt.getNewValue()).floatValue();
			this.getJSliderRoll().setValue((int)Math.floor(k));
		}else if(action.equals(OrientationProvider.AZIMUT_CHANGED))
		{
			boolean k=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxIsMagnetic().setSelected(k);
		}
		
	}

}  //  @jve:decl-index=0:visual-constraint="79,10"
