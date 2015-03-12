package db.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import db.controller.DataBaseAppController;

public class DataBasePanel extends JPanel
{
	
	Color aColor = new Color(669900);
	Color bColor = new Color(0xFF9900);
	
	private DataBaseAppController baseController;
	
	private JButton appButton;
	private JButton appButton2;
	private JButton appButton3;
	private JTextArea displayArea;
	private JScrollPane displayPane;
	private SpringLayout baseLayout;
	private JTable tableData;
	
	public DataBasePanel(DataBaseAppController baseController)
	{
		this.baseController = baseController;
		baseLayout = new SpringLayout();
		appButton = new JButton("Describe the Query");
		appButton2 = new JButton("Test the query");
		appButton3 = new JButton("Insert into the Database");

		displayArea = new JTextArea(10, 30);
		displayPane = new JScrollPane(displayArea);
			
		setupTable();
		setupPane();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	private void setupTable()
	{
		tableData = new JTable(new DefaultTableModel(baseController.getDataBase().tableInfo(), baseController.getDataBase().getMetaData()));
		displayPane = new JScrollPane(tableData);
	}
	
	
	private void setupPanel()
	{
		this.setBackground(bColor);
		this.setSize(1000,1000);
		this.setLayout(baseLayout);
		this.add(appButton);
		this.add(appButton2);
		this.add(appButton3);
		this.add(displayPane);
	}
	
	private void setupListeners()
	{
		appButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String databaseAnswer = baseController.getDataBase().describeTable();
				displayArea.setText(databaseAnswer);
			}
		});
		
		appButton2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String databaseAnswer = baseController.getDataBase().displayTables();
				displayArea.setText(databaseAnswer);
			}
		});
		
		appButton3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				int answer = baseController.getDataBase().insertSample();
//				displayArea.setText(displayArea.getText() + "\nRows Affected: " + answer);
				JOptionPane.showMessageDialog(displayArea, "\nRows Affected: " + answer);
			}
		});
		
	}
		
		
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 40, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, displayPane, 20, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, appButton2, 160, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, appButton3, 290, SpringLayout.WEST, this);
	}
	
	private void setupPane()
	{
		
	}
}
