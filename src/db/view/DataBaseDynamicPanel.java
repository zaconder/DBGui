package db.view;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import db.controller.DataBaseAppController;

public class DataBaseDynamicPanel extends JPanel
{
	private SpringLayout baseLayout;
	
	private JButton sendButton;

	private DataBaseAppController baseController;
	
	public DataBaseDynamicPanel(DataBaseAppController baseController)
	{
		baseLayout = new SpringLayout();
		this.baseController = baseController;
		setupPanel();
		setupListeners();
	}
	
	private void setupPanel()
	{
		this.setLayout(baseLayout);
		int startOffset = 20;
		for(int count = 0; count < baseController.getDataBase().getMetaData().length; count ++)
		{
			JLabel test = new JLabel("" + count + " label");
			JTextField textField = new JTextField(10);
			this.add(test);
			this.add(textField);
			
			baseLayout.putConstraint(SpringLayout.NORTH, test, startOffset, SpringLayout.NORTH, this);
			startOffset += 20;
			baseLayout.putConstraint(SpringLayout.NORTH, textField, startOffset, SpringLayout.NORTH, this);
			
			startOffset += 50;
		}
	}
	
	private void setupLayout()
	{
		
	}
	
	private void setupListeners()
	{
//		JTextField [] myFields;
		ArrayList<JTextField> myTextFields = new ArrayList<JTextField>();
//		int fieldCount = 0;
		for(Component current : this.getComponents())
		{
			if(current instanceof JTextField)
			{
//				fieldCount++;
				myTextFields.add((JTextField)current);
			}
		}
//		myFields = new JTextField[fieldCount];
//		for(Component current : this.getComponents())
//		{
//			if(current instanceof JTextField)
//			{
//				myFields[fieldCount-1] = (JTextField) current;
//				fieldCount--;
//			}
//		}
	}
	
}
