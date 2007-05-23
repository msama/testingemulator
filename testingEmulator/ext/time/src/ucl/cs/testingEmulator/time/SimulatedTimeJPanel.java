/**
 * 
 */
package ucl.cs.testingEmulator.time;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.toedter.calendar.JCalendar;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.SystemColor;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

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
public class SimulatedTimeJPanel extends JCalendar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 193835202011193150L;
	private JPanel jPanelTime = null;
	private JTextField jTextFieldTime = null;
	private JLabel jLabelTime = null;
	private JButton jButtonApply = null;
	/**
	 * 
	 */
	public SimulatedTimeJPanel() {
		initialize();
	}
		

	/**
	 * @param date
	 */
	public SimulatedTimeJPanel(Date date) {
		super(date);
		initialize();
	}

	/**
	 * @param calendar
	 */
	public SimulatedTimeJPanel(Calendar calendar) {
		super(calendar);
		initialize();
	}

	/**
	 * @param locale
	 */
	public SimulatedTimeJPanel(Locale locale) {
		super(locale);
		initialize();
	}

	/**
	 * @param monthSpinner
	 */
	public SimulatedTimeJPanel(boolean monthSpinner) {
		super(monthSpinner);
		initialize();
	}

	/**
	 * @param date
	 * @param locale
	 */
	public SimulatedTimeJPanel(Date date, Locale locale) {
		super(date, locale);
		initialize();
	}

	/**
	 * @param date
	 * @param monthSpinner
	 */
	public SimulatedTimeJPanel(Date date, boolean monthSpinner) {
		super(date, monthSpinner);
		initialize();
	}

	/**
	 * @param locale
	 * @param monthSpinner
	 */
	public SimulatedTimeJPanel(Locale locale, boolean monthSpinner) {
		super(locale, monthSpinner);
		initialize();
	}

	/**
	 * @param date
	 * @param locale
	 * @param monthSpinner
	 * @param weekOfYearVisible
	 */
	public SimulatedTimeJPanel(Date date, Locale locale, boolean monthSpinner,
			boolean weekOfYearVisible) {
		super(date, locale, monthSpinner, weekOfYearVisible);
		initialize();
	}



	private void initialize() {
        this.setSize(new Dimension(433, 275));
        this.add(getJPanelTime(), BorderLayout.SOUTH);
	}
	/**
	 * This method initializes jPanelTime	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelTime() {
		if (jPanelTime == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 3;
			gridBagConstraints3.gridy = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridx = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			jLabelTime = new JLabel();
			jLabelTime.setText("Time");
			jLabelTime.setHorizontalTextPosition(SwingConstants.CENTER);
			jLabelTime.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelTime.setPreferredSize(new Dimension(100, 16));
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridx = 0;
			jPanelTime = new JPanel();
			jPanelTime.setLayout(new GridBagLayout());
			jPanelTime.setBackground(SystemColor.textHighlight);
			jPanelTime.setPreferredSize(new Dimension(200, 50));
			jPanelTime.add(jLabelTime, gridBagConstraints1);
			jPanelTime.add(getJTextFieldTime(), gridBagConstraints2);
			jPanelTime.add(getJButtonApply(), gridBagConstraints3);
		}
		return jPanelTime;
	}

	/**
	 * This method initializes jTextFieldTime	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldTime() {
		if (jTextFieldTime == null) {
			jTextFieldTime = new JTextField();
			jTextFieldTime.setPreferredSize(new Dimension(200, 22));
			jTextFieldTime.setHorizontalAlignment(JTextField.TRAILING);
			jTextFieldTime.setText(this.getCalendar().get(Calendar.HOUR)+":"+this.getCalendar().get(Calendar.MINUTE)+":"+this.getCalendar().get(Calendar.SECOND));
			jTextFieldTime.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String s=jTextFieldTime.getText();
					try {
						int hour=Integer.parseInt(s.substring(0,s.indexOf(':')));
						getCalendar().set(Calendar.HOUR, hour);
						int mins=Integer.parseInt(s.substring(s.indexOf(':')+1,s.lastIndexOf(':')));
						getCalendar().set(Calendar.MINUTE, mins);
						int sec=Integer.parseInt(s.substring(s.lastIndexOf(':')+1));
						getCalendar().set(Calendar.SECOND, sec);
					} catch (NumberFormatException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return jTextFieldTime;
	}


	/* (non-Javadoc)
	 * @see com.toedter.calendar.JCalendar#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String act=evt.getPropertyName();
		if(act.equals(VirtualClock.TIME_STATUS))
		{
			//boolean b=((Boolean)evt.getNewValue()).booleanValue();
		}else if(act.equals(VirtualClock.TIME_CHANGED)){
			super.propertyChange(evt);
			this.getJTextFieldTime().setText(this.getCalendar().get(Calendar.HOUR)+":"+this.getCalendar().get(Calendar.MINUTE)+":"+this.getCalendar().get(Calendar.SECOND));
		}else
		{
			super.propertyChange(evt);
		}
	}


	/**
	 * This method initializes jButtonApply	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonApply() {
		if (jButtonApply == null) {
			jButtonApply = new JButton();
			jButtonApply.setText("Apply");
			jButtonApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					VirtualClock.setSimulatedTime(getCalendar().getTimeInMillis());
					String s=jTextFieldTime.getText();
					try {
						int hour=Integer.parseInt(s.substring(0,s.indexOf(':')));
						getCalendar().set(Calendar.HOUR, hour);
						int mins=Integer.parseInt(s.substring(s.indexOf(':')+1,s.lastIndexOf(':')));
						getCalendar().set(Calendar.MINUTE, mins);
						int sec=Integer.parseInt(s.substring(s.lastIndexOf(':')+1));
						getCalendar().set(Calendar.SECOND, sec);
					} catch (NumberFormatException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return jButtonApply;
	}



}  //  @jve:decl-index=0:visual-constraint="10,10"
