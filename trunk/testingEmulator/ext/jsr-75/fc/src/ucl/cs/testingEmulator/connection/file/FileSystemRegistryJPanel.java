/**
 *
 */
package ucl.cs.testingEmulator.connection.file;

import java.awt.GridBagLayout;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileSystemListener;
import javax.microedition.io.file.FileSystemRegistry;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JList;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

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
public class FileSystemRegistryJPanel extends JPanel implements FileSystemListener{

	private static final long serialVersionUID = 1L;
	private JList jListRoots = null;
	private JPanel jPanelCenter = null;
	private JButton jButtonAdd = null;
	private JButton jButtonRemove = null;
	private JLabel jLabelRealPath = null;
	private JTextField jTextFieldRealPath = null;
	private JButton jButtonSearchPath = null;
	private JLabel jLabelLogicalPath = null;
	private JTextField jTextFieldLogicalPath = null;
	private DefaultListModel defaultListModelRoots = null;  //  @jve:decl-index=0:visual-constraint="680,101"
	private JButton jButtonSave = null;
	private JFileChooser jFileChooserRealPath = null;  //  @jve:decl-index=0:visual-constraint="936,101"

	/**
	 * This is the default constructor
	 */
	public FileSystemRegistryJPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		FileSystemRegistry.addFileSystemListener(this);
		this.setSize(452, 200);
		this.setLayout(new BorderLayout());
		this.add(getJListRoots(), BorderLayout.CENTER);
		this.add(getJPanelCenter(), BorderLayout.NORTH);
	}

	/**
	 * This method initializes jListRoots	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListRoots() {
		if (jListRoots == null) {
			jListRoots = new JList();
			//jListRoots.setPreferredSize(new Dimension(100, 20));
			jListRoots.setModel(this.getDefaultListModelRoots());
			jListRoots
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(javax.swing.event.ListSelectionEvent e) {
							EmulatedFileSystem sys=(EmulatedFileSystem)getJListRoots().getSelectedValue();
							if(sys!=null)
							{
								getJTextFieldLogicalPath().setText(sys.getLogicalPath());
								getJTextFieldRealPath().setText(sys.getRealPath());
							}
						}
					});
		}
		return jListRoots;
	}

	/**
	 * This method initializes jPanelCenter	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelCenter() {
		if (jPanelCenter == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 3;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints6.gridy = 2;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.gridx = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 2;
			jLabelLogicalPath = new JLabel();
			jLabelLogicalPath.setText("LogicalPath");
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			jLabelRealPath = new JLabel();
			jLabelRealPath.setText("RealPath");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.gridy = 3;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 2;
			gridBagConstraints.gridy = 4;
			jPanelCenter = new JPanel();
			jPanelCenter.setLayout(new GridBagLayout());
			jPanelCenter.add(getJButtonAdd(), gridBagConstraints);
			jPanelCenter.add(getJButtonRemove(), gridBagConstraints1);
			jPanelCenter.add(jLabelRealPath, gridBagConstraints2);
			jPanelCenter.add(getJTextFieldRealPath(), gridBagConstraints3);
			jPanelCenter.add(getJButtonSearchPath(), gridBagConstraints4);
			jPanelCenter.add(jLabelLogicalPath, gridBagConstraints5);
			jPanelCenter.add(getJTextFieldLogicalPath(), gridBagConstraints6);
			jPanelCenter.add(getJButtonSave(), gridBagConstraints11);
		}
		return jPanelCenter;
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
			jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int k=0;
					String logicalP=null;
					EmulatedFileSystem sys=null;
					do
					{
						logicalP=FileSystemRegistry.FILE_SEPARATOR+"NewRoot"+k;
						k++;
						sys=FileSystemRegistry.getInstance().getEmulatedFileSystem(logicalP);
					}while(sys!=null);
					sys=new EmulatedFileSystem(System.getProperty("user.home"),logicalP,Connector.READ_WRITE);
					FileSystemRegistry.getInstance().addFileSystem(sys);
				}
			});
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
			jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmulatedFileSystem sys=(EmulatedFileSystem)getJListRoots().getSelectedValue();
					if(sys!=null)
					{
						FileSystemRegistry.getInstance().removeFileSystem(sys);
					}
				}
			});
		}
		return jButtonRemove;
	}

	/**
	 * This method initializes jTextFieldRealPath	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldRealPath() {
		if (jTextFieldRealPath == null) {
			jTextFieldRealPath = new JTextField();
			jTextFieldRealPath.setPreferredSize(new Dimension(200, 22));
		}
		return jTextFieldRealPath;
	}

	/**
	 * This method initializes jButtonSearchPath	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonSearchPath() {
		if (jButtonSearchPath == null) {
			jButtonSearchPath = new JButton();
			jButtonSearchPath.setText("...");
			jButtonSearchPath.setPreferredSize(new Dimension(75, 22));
			jButtonSearchPath.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int k=getJFileChooserRealPath().showOpenDialog(null);
					if(k==JFileChooser.APPROVE_OPTION)
					{
						String s=getJFileChooserRealPath().getSelectedFile().getAbsolutePath();
						getJTextFieldRealPath().setText(s);
					}
				}
			});
		}
		return jButtonSearchPath;
	}

	/**
	 * This method initializes jTextFieldLogicalPath	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldLogicalPath() {
		if (jTextFieldLogicalPath == null) {
			jTextFieldLogicalPath = new JTextField();
			jTextFieldLogicalPath.setPreferredSize(new Dimension(200, 22));
		}
		return jTextFieldLogicalPath;
	}

	public void rootChanged(int state, String rootName) {
		if(state==FileSystemListener.ROOT_ADDED)
		{
			EmulatedFileSystem sys=FileSystemRegistry.getInstance().getEmulatedFileSystem(rootName);
			if(sys!=null)
			{
				this.getDefaultListModelRoots().addElement(sys);
				this.getJListRoots().repaint();
			}
		}else if(state==FileSystemListener.ROOT_REMOVED)
		{
			for(int i=0;i<this.getDefaultListModelRoots().size();i++)
			{
				EmulatedFileSystem sys=(EmulatedFileSystem)this.getDefaultListModelRoots().elementAt(i);
				if(sys.getLogicalPath().equals(rootName))
				{
					this.getDefaultListModelRoots().removeElement(sys);
				}
			}
			this.getJListRoots().repaint();
		}
		
	}

	/**
	 * This method initializes defaultListModelRoots	
	 * 	
	 * @return javax.swing.DefaultListModel	
	 */
	private DefaultListModel getDefaultListModelRoots() {
		if (defaultListModelRoots == null) {
			defaultListModelRoots = new DefaultListModel();
		}
		return defaultListModelRoots;
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
			jButtonSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					EmulatedFileSystem sys=(EmulatedFileSystem)getJListRoots().getSelectedValue();
					if(sys!=null)
					{
						sys.setLogicalPath(getJTextFieldLogicalPath().getText());
						sys.setRealPath(getJTextFieldRealPath().getText());
						getJListRoots().repaint();
					}
				}
			});
		}
		return jButtonSave;
	}

	/**
	 * This method initializes jFileChooserRealPath	
	 * 	
	 * @return javax.swing.JFileChooser	
	 */
	private JFileChooser getJFileChooserRealPath() {
		if (jFileChooserRealPath == null) {
			jFileChooserRealPath = new JFileChooser();
			jFileChooserRealPath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jFileChooserRealPath.addChoosableFileFilter(new FileFilter()
			{

				@Override
				public boolean accept(File f) {
					return f.isDirectory();
				}

				@Override
				public String getDescription() {
					return "Directory";
				}
				
			});
		}
		return jFileChooserRealPath;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
