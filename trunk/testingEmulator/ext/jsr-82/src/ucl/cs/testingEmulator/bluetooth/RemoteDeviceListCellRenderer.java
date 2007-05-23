/**
 *
 */
package ucl.cs.testingEmulator.bluetooth;

import java.awt.Color;
import java.awt.Component;

import javax.bluetooth.RemoteDevice;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
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
public class RemoteDeviceListCellRenderer implements ListCellRenderer {

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		if ((value instanceof RemoteDevice) == false) {
			return null;
		}
		

		RemoteDeviceCellJPanel panel = new RemoteDeviceCellJPanel();
		panel.setRemoteDevice((RemoteDevice) value);
		panel.setEnabled(true);
		panel.setSize(new Dimension(752, 90));
		panel.setFocusable(true);
		panel.setOpaque(true);

		Color background;
		Color foreground;

		// check if this cell represents the current DnD drop location
		/*
		 * JList.DropLocation dropLocation = list.getDropLocation(); if
		 * (dropLocation != null && !dropLocation.isInsert() &&
		 * dropLocation.getIndex() == index) {
		 * 
		 * background = Color.BLUE; foreground = Color.WHITE;
		 *  // check if this cell is selected } else
		 */if (isSelected) {
			background = Color.DARK_GRAY;
			foreground = Color.WHITE;

			// unselected, and not the DnD drop location
		} else {
			background = Color.WHITE;
			foreground = Color.BLACK;
		}
		

		panel.setBackground(background);
		panel.setForeground(foreground);

		return panel;
	}
}
