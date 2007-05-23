/**
 *
 */
package ucl.cs.testingEmulator.contexNotifierScripts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.File;

import javax.microedition.midlet.MIDlet;
import javax.swing.JPanel;
import javax.swing.JFrame;

import ucl.cs.contextNotifier.ContextNotifierMIDlet;
import ucl.cs.contextNotifier.DecisionEngine;
import ucl.cs.contextNotifier.Rule;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

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
public class SampleFileOracleJFrame extends JFrame implements Runnable{
	
	private String fileName;
	private Rule rule;
	private long maxUpdateDelay;
	private long _deadline=0;
	private boolean _innerValue=false;
	private boolean _ruleValue=false;
	
	private Thread _innerThread=null;

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JLabel jLabelDescription = null;
	private JLabel jLabelFilesystem = null;
	private JLabel jLabelFileSystemStatus = null;
	private JLabel jLabelRule = null;
	private JLabel jLabelRuleStatus = null;
	private JLabel jLabelStatus = null;

	public SampleFileOracleJFrame(MIDlet midlet, String ruleName, String fileName, long maxUpdateDelay)
	{
		if(midlet instanceof ContextNotifierMIDlet == false)
		{
			throw new java.lang.IllegalArgumentException("MIDlet is not an instance of ContextNotifierMIDlet");
		}
		ContextNotifierMIDlet contextNotifier=(ContextNotifierMIDlet)midlet;
		
		DecisionEngine decisionEng=DecisionEngine.getInstance();
		Rule r=decisionEng.getRuleByName(ruleName);
		this.fileName = fileName;
		this.maxUpdateDelay = maxUpdateDelay;
		this.setTitle("Sample Oracle");
		initialize();
	}
	
	/**
	 * @param fileName
	 * @param rule
	 * @param maxUpdateDelay
	 * @throws HeadlessException
	 */
	public SampleFileOracleJFrame(String fileName, Rule rule, long maxUpdateDelay){
		super();
		this.fileName = fileName;
		this.rule = rule;
		this.maxUpdateDelay = maxUpdateDelay;
		this.setTitle("Sample Oracle");
		initialize();
	}



	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	public synchronized void checkStatus()
	{
		if(this._innerThread==null||this._innerThread.getState()==Thread.State.TERMINATED)
		{
			this._innerThread=new Thread(this,this.getClass().getSimpleName());
			this._innerThread.start();
		}else
		{
			this._deadline=0;
		}
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridwidth = 2;
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.gridy = 3;
			jLabelStatus = new JLabel();
			jLabelStatus.setText("Correct");
			jLabelStatus.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelStatus.setFont(new Font("Lucida Grande", Font.BOLD, 24));
			jLabelStatus.setHorizontalTextPosition(SwingConstants.CENTER);
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 2;
			jLabelRuleStatus = new JLabel();
			jLabelRuleStatus.setText("false");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 2;
			jLabelRule = new JLabel();
			jLabelRule.setText("Rule");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 1;
			jLabelFileSystemStatus = new JLabel();
			jLabelFileSystemStatus.setText("false");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			jLabelFilesystem = new JLabel();
			jLabelFilesystem.setText("FileSystem:");
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			jLabelDescription = new JLabel();
			jLabelDescription.setText("Sample Oracle for the \"presentation\" rule");
			jLabelDescription.setIcon(new ImageIcon(getClass().getResource("/ucl/cs/testingEmulator/contexNotifierScripts/sfinge.jpg")));
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(jLabelDescription, gridBagConstraints);
			jContentPane.add(jLabelFilesystem, gridBagConstraints1);
			jContentPane.add(jLabelFileSystemStatus, gridBagConstraints2);
			jContentPane.add(jLabelRule, gridBagConstraints3);
			jContentPane.add(jLabelRuleStatus, gridBagConstraints4);
			jContentPane.add(jLabelStatus, gridBagConstraints5);
		}
		return jContentPane;
	}

	public void run() {
		File f=new File(this.fileName);
		this._innerValue=f.exists()==false;
		this.jLabelFilesystem.setText(""+this._innerValue);
		this.jLabelStatus.setForeground(Color.black);
		this.jLabelStatus.setText("Checking.....");
		this._deadline=0;
		do{
			this._ruleValue=this.rule.isActualValue().isTrue();
			if(this._ruleValue!=this._innerValue)
			{
				this._deadline+=this.maxUpdateDelay/10;
				try {
					Thread.sleep(this.maxUpdateDelay/10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else
			{
				break;
			}
		}while(this._deadline<this.maxUpdateDelay);
			
		if(this._innerValue==this._ruleValue)
		{
			this.jLabelStatus.setForeground(Color.green);
			this.jLabelStatus.setText("CORRECT");
		}else
		{
			this.jLabelStatus.setForeground(Color.red);
			this.jLabelStatus.setText("INCORRECT");
		}
	}

}
