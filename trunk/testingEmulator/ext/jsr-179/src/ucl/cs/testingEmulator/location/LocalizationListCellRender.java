/**
 *
 */
package ucl.cs.testingEmulator.location;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author -Michele Sama- aka -RAX-
 * 
 * University College London Dept. of Computer Science Gower Street London WC1E
 * 6BT United Kingdom
 * 
 * Email: M.Sama (at) cs.ucl.ac.uk
 * 
 * Group: Software Systems Engineering
 * 
 */
public class LocalizationListCellRender implements ListCellRenderer{

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		if ((value instanceof LocalizationMethodWrapper) == false) {
			return null;
		}

		LocalizationMethodJPanel panel = new LocalizationMethodJPanel();
		panel.setLocationMethod((LocalizationMethodWrapper) value);
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
			background = Color.RED;
			foreground = Color.WHITE;

			// unselected, and not the DnD drop location
		} else {
			background = Color.WHITE;
			foreground = Color.BLACK;
		}
		;

		panel.setBackground(background);
		panel.setForeground(foreground);

		return panel;
	}

}
