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
	/**
	 * The spring layout for the panel.
	 */
	private SpringLayout baseLayout;

	/**
	 * The send button for the Dynamic Panel.
	 */
	private JButton sendButton;

	/**
	 * The link to the DataBaseAppController for the panel.
	 */
	private DataBaseAppController baseController;

	/**
	 * An array list that stores all the input in the field.
	 */
	private ArrayList<JTextField> inputFieldList;
	
	/**
	 * The string for the tableName.
	 */
	private String tableName;
	
	/**
	 * The main method for the Dynamic Panel.
	 * @param baseController The link to the DBAppController
	 * @param tableName The Table name for the current query.
	 */
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

	/**
	 * The method to setup the Panel
	 * @param tableName Takes the table and stores it as a String.
	 */
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

	/**
	 * The layout for the panel.
	 */
	private void setupLayout()
	{

	}
	
	/**
	 * Gets the values inside the current query.
	 * @return
	 */
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
	
	/**
	 * Gets the list of fields inside the current database.
	 * @return
	 */
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

	/**
	 * Sets up the buttons/listeners for the panel.
	 */
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
