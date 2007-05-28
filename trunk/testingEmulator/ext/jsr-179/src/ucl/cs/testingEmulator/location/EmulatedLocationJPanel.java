/**
 *
 */
package ucl.cs.testingEmulator.location;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JSlider;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

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
public class EmulatedLocationJPanel extends JPanel implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	private JPanel jPanelCoordinates = null;
	private JPanel QualifiedCoordinates = null;
	private JPanel jPanelQualifiedCoordinatesNoth = null;
	private JLabel jLabelAltitude = null;
	private JTextField jTextFieldAltitude = null;
	private JLabel jLabelLatitude = null;
	private JSlider jSliderLatitude = null;
	private JLabel jLabelLongitude = null;
	private JSlider jSliderLongitude = null;
	private JLabel jLabelHorizontalAccuracy = null;
	private JTextField jTextFieldHorizontalAccuracy = null;
	private JLabel jLabelVerticalAccuracy = null;
	private JTextField jTextFieldVerticalAccuracy = null;
	private JPanel jPanelLocationNorth = null;
	private JLabel jLabelSpeed = null;
	private JCheckBox jCheckBoxValid = null;
	private JLabel jLabelTimeStamp = null;
	private JTextField jTextFieldTimeStamp = null;
	private JTextField jTextFieldSpeed = null;

	private TestingLocationProvider _testingLocationProvider=TestingLocationProvider.getInstance();  //  @jve:decl-index=0:
	
	/**
	 * This is the default constructor
	 */
	public EmulatedLocationJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this._testingLocationProvider.addPropertyChangeListener(this);
		this.setSize(591, 456);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(null, "Location", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		this.add(getJPanelLocationNorth(), BorderLayout.NORTH);
		this.add(getQualifiedCoordinates(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes jPanelCoordinates	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelCoordinates() {
		if (jPanelCoordinates == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			jLabelLongitude = new JLabel();
			jLabelLongitude.setText("Longitude");
			jLabelLongitude.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelLongitude.setPreferredSize(new Dimension(130, 16));
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			jLabelLatitude = new JLabel();
			jLabelLatitude.setText("Latitude");
			jLabelLatitude.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelLatitude.setPreferredSize(new Dimension(130, 16));
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			jLabelAltitude = new JLabel();
			jLabelAltitude.setText("Altitude");
			jLabelAltitude.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelAltitude.setPreferredSize(new Dimension(130, 16));
			jPanelCoordinates = new JPanel();
			jPanelCoordinates.setLayout(new GridBagLayout());
			jPanelCoordinates.setBorder(BorderFactory.createTitledBorder(null, "Coordinates", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jPanelCoordinates.add(jLabelAltitude, gridBagConstraints);
			jPanelCoordinates.add(getJTextFieldAltitude(), gridBagConstraints1);
			jPanelCoordinates.add(jLabelLatitude, gridBagConstraints2);
			jPanelCoordinates.add(getJSliderLatitude(), gridBagConstraints3);
			jPanelCoordinates.add(jLabelLongitude, gridBagConstraints4);
			jPanelCoordinates.add(getJSliderLongitude(), gridBagConstraints5);
		}
		return jPanelCoordinates;
	}

	/**
	 * This method initializes QualifiedCoordinates	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getQualifiedCoordinates() {
		if (QualifiedCoordinates == null) {
			QualifiedCoordinates = new JPanel();
			QualifiedCoordinates.setLayout(new BorderLayout());
			QualifiedCoordinates.setBorder(BorderFactory.createTitledBorder(null, "QualifiedCoordinates", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			QualifiedCoordinates.add(getJPanelQualifiedCoordinatesNoth(), BorderLayout.NORTH);
			QualifiedCoordinates.add(getJPanelCoordinates(), BorderLayout.CENTER);
		}
		return QualifiedCoordinates;
	}

	/**
	 * This method initializes jPanelQualifiedCoordinatesNoth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelQualifiedCoordinatesNoth() {
		if (jPanelQualifiedCoordinatesNoth == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.gridx = 1;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 1;
			jLabelVerticalAccuracy = new JLabel();
			jLabelVerticalAccuracy.setText("VerticalAccuracy");
			jLabelVerticalAccuracy.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelVerticalAccuracy.setPreferredSize(new Dimension(130, 16));
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.weightx = 1.0;
			gridBagConstraints7.gridx = 1;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 0;
			jLabelHorizontalAccuracy = new JLabel();
			jLabelHorizontalAccuracy.setText("HorizontalAccuracy");
			jLabelHorizontalAccuracy.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelHorizontalAccuracy.setPreferredSize(new Dimension(130, 16));
			jPanelQualifiedCoordinatesNoth = new JPanel();
			jPanelQualifiedCoordinatesNoth.setLayout(new GridBagLayout());
			jPanelQualifiedCoordinatesNoth.setPreferredSize(new Dimension(173, 80));
			jPanelQualifiedCoordinatesNoth.add(jLabelHorizontalAccuracy, gridBagConstraints6);
			jPanelQualifiedCoordinatesNoth.add(getJTextFieldHorizontalAccuracy(), gridBagConstraints7);
			jPanelQualifiedCoordinatesNoth.add(jLabelVerticalAccuracy, gridBagConstraints8);
			jPanelQualifiedCoordinatesNoth.add(getJTextFieldVerticalAccuracy(), gridBagConstraints9);
		}
		return jPanelQualifiedCoordinatesNoth;
	}

	/**
	 * This method initializes jTextFieldAltitude	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldAltitude() {
		if (jTextFieldAltitude == null) {
			jTextFieldAltitude = new JTextField();
			jTextFieldAltitude.setPreferredSize(new Dimension(200, 22));
			jTextFieldAltitude.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						float f=Float.parseFloat(jTextFieldAltitude.getText());
						_testingLocationProvider.getCurrentEmulatedLocation().getQualifiedCoordinates().setAltitude(f);
					} catch (NumberFormatException e1) {
						return;
					}
				}
			});
		}
		return jTextFieldAltitude;
	}

	/**
	 * This method initializes jSliderLatitude	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSliderLatitude() {
		if (jSliderLatitude == null) {
			jSliderLatitude = new JSlider();
			jSliderLatitude.setPaintLabels(true);
			jSliderLatitude.setMaximum(90);
			jSliderLatitude.setMinimum(-90);
			jSliderLatitude.setValue(0);
			jSliderLatitude.setMinorTickSpacing(10);
			jSliderLatitude.setMajorTickSpacing(30);
			jSliderLatitude.setPreferredSize(new Dimension(250, 54));
			jSliderLatitude.setPaintTicks(true);
			jSliderLatitude.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					_testingLocationProvider.getCurrentEmulatedLocation().getQualifiedCoordinates().setLatitude(jSliderLatitude.getValue());
				}
			});
		}
		return jSliderLatitude;
	}

	/**
	 * This method initializes jSliderLongitude	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSliderLongitude() {
		if (jSliderLongitude == null) {
			jSliderLongitude = new JSlider();
			jSliderLongitude.setMaximum(180);
			jSliderLongitude.setMinorTickSpacing(20);
			jSliderLongitude.setMajorTickSpacing(60);
			jSliderLongitude.setPaintLabels(true);
			jSliderLongitude.setPaintTicks(true);
			jSliderLongitude.setValue(0);
			jSliderLongitude.setPreferredSize(new Dimension(250, 54));
			jSliderLongitude.setMinimum(-180);
			jSliderLongitude.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					_testingLocationProvider.getCurrentEmulatedLocation().getQualifiedCoordinates().setLongitude(jSliderLongitude.getValue());
				}
			});
		}
		return jSliderLongitude;
	}

	/**
	 * This method initializes jTextFieldHorizontalAccuracy	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldHorizontalAccuracy() {
		if (jTextFieldHorizontalAccuracy == null) {
			jTextFieldHorizontalAccuracy = new JTextField();
			jTextFieldHorizontalAccuracy.setPreferredSize(new Dimension(200, 22));
			jTextFieldHorizontalAccuracy
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								float f=Float.parseFloat(jTextFieldHorizontalAccuracy.getText());
								_testingLocationProvider.getCurrentEmulatedLocation().getQualifiedCoordinates().setHorizontalAccuracy(f);
							} catch (NumberFormatException e1) {
								return;
							}
						}
					});
		}
		return jTextFieldHorizontalAccuracy;
	}

	/**
	 * This method initializes jTextFieldVerticalAccuracy	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldVerticalAccuracy() {
		if (jTextFieldVerticalAccuracy == null) {
			jTextFieldVerticalAccuracy = new JTextField();
			jTextFieldVerticalAccuracy.setPreferredSize(new Dimension(200, 22));
			jTextFieldVerticalAccuracy
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							try {
								float f=Float.parseFloat(jTextFieldVerticalAccuracy.getText());
								_testingLocationProvider.getCurrentEmulatedLocation().getQualifiedCoordinates().setVerticalAccuracy(f);
							} catch (NumberFormatException e1) {
								return;
							}
						}
					});
		}
		return jTextFieldVerticalAccuracy;
	}

	/**
	 * This method initializes jPanelLocationNorth	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelLocationNorth() {
		if (jPanelLocationNorth == null) {
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints14.gridy = 1;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.gridx = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints13.gridy = 2;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.gridx = 1;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 2;
			jLabelTimeStamp = new JLabel();
			jLabelTimeStamp.setText("TimeStamp");
			jLabelTimeStamp.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelTimeStamp.setPreferredSize(new Dimension(130, 16));
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 0;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 1;
			jLabelSpeed = new JLabel();
			jLabelSpeed.setText("Speed");
			jLabelSpeed.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabelSpeed.setPreferredSize(new Dimension(130, 16));
			jPanelLocationNorth = new JPanel();
			jPanelLocationNorth.setLayout(new GridBagLayout());
			jPanelLocationNorth.setPreferredSize(new Dimension(300, 100));
			jPanelLocationNorth.add(jLabelSpeed, gridBagConstraints10);
			jPanelLocationNorth.add(getJCheckBoxValid(), gridBagConstraints11);
			jPanelLocationNorth.add(jLabelTimeStamp, gridBagConstraints12);
			jPanelLocationNorth.add(getJTextFieldTimeStamp(), gridBagConstraints13);
			jPanelLocationNorth.add(getJTextFieldSpeed(), gridBagConstraints14);
		}
		return jPanelLocationNorth;
	}

	/**
	 * This method initializes jCheckBoxValid	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBoxValid() {
		if (jCheckBoxValid == null) {
			jCheckBoxValid = new JCheckBox();
			jCheckBoxValid.setText("isValid");
			jCheckBoxValid.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					_testingLocationProvider.getCurrentEmulatedLocation().setValid(jCheckBoxValid.isSelected());
				}
			});
		}
		return jCheckBoxValid;
	}

	/**
	 * This method initializes jTextFieldTimeStamp	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldTimeStamp() {
		if (jTextFieldTimeStamp == null) {
			jTextFieldTimeStamp = new JTextField();
			jTextFieldTimeStamp.setPreferredSize(new Dimension(200, 22));
			jTextFieldTimeStamp.setEnabled(false);
		}
		return jTextFieldTimeStamp;
	}

	/**
	 * This method initializes jTextFieldSpeed	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldSpeed() {
		if (jTextFieldSpeed == null) {
			jTextFieldSpeed = new JTextField();
			jTextFieldSpeed.setPreferredSize(new Dimension(200, 22));
			jTextFieldSpeed.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						float f=Float.parseFloat(jTextFieldSpeed.getText());
						_testingLocationProvider.getCurrentEmulatedLocation().setSpeed(f);
					} catch (NumberFormatException e1) {
						return;
					}
				}
			});
		}
		return jTextFieldSpeed;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName=evt.getPropertyName();
		if(propertyName.equals(TestingLocationProvider.ALTITUDE_UPDATED))
		{
			float f=((Float)evt.getNewValue()).floatValue();
			this.getJTextFieldAltitude().setText(""+f);
		}
		else if(propertyName.equals(TestingLocationProvider.HORIZONTAL_ACCURACY_UPDATED))
		{
			float f=((Float)evt.getNewValue()).floatValue();
			this.getJTextFieldHorizontalAccuracy().setText(""+f);
		}
		else if(propertyName.equals(TestingLocationProvider.LATITUDE_UPDATED))
		{
			double d=((Double)evt.getNewValue()).doubleValue();
			this.getJSliderLatitude().setValue((int)Math.round(d));
		}
		else if(propertyName.equals(TestingLocationProvider.LOCATION_VALIDITY_UPDATED))
		{
			boolean b=((Boolean)evt.getNewValue()).booleanValue();
			this.getJCheckBoxValid().setSelected(b);
		}
		else if(propertyName.equals(TestingLocationProvider.LONGITUDE_UPDATED))
		{
			double d=((Double)evt.getNewValue()).doubleValue();
			this.getJSliderLongitude().setValue((int)(int)Math.round(d));
		}
		else if(propertyName.equals(TestingLocationProvider.SPEED_UPDATED))
		{
			float f=((Float)evt.getNewValue()).floatValue();
			this.getJTextFieldSpeed().setText(""+f);
		}
		else if(propertyName.equals(TestingLocationProvider.STATE_CHANGED))
		{
			
		}
		else if(propertyName.equals(TestingLocationProvider.VERTICAL_ACCURACY_UPDATED))
		{
			float f=((Float)evt.getNewValue()).floatValue();
			this.getJTextFieldVerticalAccuracy().setText(""+f);
		}
		
	}

}  //  @jve:decl-index=0:visual-constraint="18,7"
