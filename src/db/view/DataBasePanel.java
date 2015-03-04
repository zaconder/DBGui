package db.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import db.controller.DataBaseAppController;

public class DataBasePanel extends JPanel
{
	private DataBaseAppController baseController;
	
	private JButton appButton;
	private JButton appButton2;
	private JTextArea displayArea;
	private JScrollPane displayPane;
	private SpringLayout baseLayout;
	
	public DataBasePanel(DataBaseAppController baseController)
	{
		this.baseController = baseController;
		appButton = new JButton("Describe the Query");
		appButton2 = new JButton("Test the query");
		displayArea = new JTextArea(10, 30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		
		
		setupPane();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	private void setupPanel()
	{
		this.setBackground(Color.MAGENTA);
		this.setLayout(baseLayout);
		this.add(appButton);
		this.add(appButton2);
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
		
	}
		
		
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 40, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, displayPane, 20, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, appButton2, 160, SpringLayout.WEST, this);
	}
	
	private void setupPane()
	{

	}
}
