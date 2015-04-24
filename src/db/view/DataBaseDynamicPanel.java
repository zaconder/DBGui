package db.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	private ArrayList<JTextField> inputFieldList;
	
	private String tableName;

	public DataBaseDynamicPanel(DataBaseAppController baseController, String tableName)
	{
		this.baseController = baseController;
		this.tableName = tableName;
		baseLayout = new SpringLayout();
		sendButton = new JButton();
		inputFieldList = new ArrayList<JTextField>();
		
		setupPanel(tableName);
		setupLayout();
		setupListeners();
	}

	private void setupPanel(String tableName)
	{
		this.setLayout(baseLayout);
		this.add(sendButton);
		int startOffset = 20;
		String[] columns = baseController.getDataBase().getDataBaseColumnNames(tableName);
		
		for(int fieldCount = 0; fieldCount < columns.length; fieldCount ++)
		{
			//baseController.getDataBase().getMetaData()
			
			if(!columns[fieldCount].equalsIgnoreCase("id"))
			{
				
			
			
				JTextField dynamicField = new JTextField(20);
				JLabel dynamicLabel = new JLabel(columns[fieldCount] + " entry field:");
			
				this.add(dynamicLabel);
				this.add(dynamicField);
			
				dynamicLabel.setName(columns[fieldCount] + "Label");
				dynamicField.setName(columns[fieldCount] + "Field");
			
				inputFieldList.add(dynamicField);
			
			
				baseLayout.putConstraint(SpringLayout.NORTH, dynamicLabel, startOffset, SpringLayout.NORTH, this);
			
				baseLayout.putConstraint(SpringLayout.NORTH, dynamicField, startOffset, SpringLayout.NORTH, this);
			
				startOffset += 50;
			}
		}
	}

	private void setupLayout()
	{

	}
	
	private String getValueList()
	{
		String values = "(";
		
		for(int spot = 0; spot < inputFieldList.size(); spot++)
		{
			String temp = inputFieldList.get(spot).getText();
			
			if(spot == inputFieldList.size()-1)
			{
				values += "'" + temp + "')";
			}
			else
			{
				values += "'" + temp + "', ";
			}
			
		}
		return values;
	}
	
	private String getFieldList()
	{
		String fields = "(";
		//Needs the format (`field`, `field`, `field` ...)
		
		for(int spot = 0; spot < inputFieldList.size(); spot++)
		{
			String temp = inputFieldList.get(spot).getName();
			int cutoff = temp.indexOf("Field");
			temp = temp.substring(0, cutoff);
			
			if(spot == inputFieldList.size()-1)
			{
				fields += "`" + temp + "`)";
			}
			else
			{
				fields += "`" + temp + "`, ";
			}
			
		}
		return fields;
	}

	private void setupListeners()
	{

		sendButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String myQuery = "INSERT INTO " + tableName + " " + getFieldList() + " VALUES " + getValueList() + ";";
				baseController.getDataBase().submitQuery(myQuery);
			}
			
			
		});
					
//		 JTextField [] myFields;
//		ArrayList<JTextField> myTextFields = new ArrayList<JTextField>();
//		int fieldCount = 0;
//		for (Component current : this.getComponents())
//		{
//			if (current instanceof JTextField)
//			{
//				// fieldCount++;
//				myTextFields.add((JTextField) current);
//			}
//		}
//		 myFields = new JTextField[fieldCount];
//		 for(Component current : this.getComponents())
//		 {
//		 if(current instanceof JTextField)
//		 {
//		 myFields[fieldCount-1] = (JTextField) current;
//		 fieldCount--;
//		 }
//		 }
	}

}
