package db.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * The TableCellWrapRenderer class will take the information from the cells and wrap it so it looks more clean.
 * @author zcon5199
 *
 */
public class TableCellWrapRenderer extends JTextArea implements TableCellRenderer
{


	/**
	 * This is the actual method that takes the cells and wraps it once it hits a certain point. And adds an offset color.
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		this.setText(value.toString());
		this.setWrapStyleWord(true);
		this.setLineWrap(true);
		int fontHeight = this.getFontMetrics(this.getFont()).getHeight();
		int textPixelLength = this.getFontMetrics(this.getFont()).stringWidth(this.getText());
		TableColumn columnSelected = table.getColumnModel().getColumn(column);
		int lines = (textPixelLength / (columnSelected.getWidth())) + 1;
		int height = fontHeight * (lines + 3);
		table.setRowHeight(row, height);
		if(row % 2 == 0)
		{
			this.setBackground(Color.LIGHT_GRAY);
		}
		else
		{
			this.setBackground(Color.WHITE);
		}
		return this;
	}

}
