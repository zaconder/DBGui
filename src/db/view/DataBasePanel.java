package db.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import db.controller.DataBaseAppController;

public class DataBasePanel extends JPanel
{
	
	/**
	 * A custom blueish color.
	 */
	Color aColor = new Color(669900);
	/**
	 * A custom orange color.
	 */
	Color bColor = new Color(0xFF9900);
	/**
	 * Adds the DatabaseAppController
	 */
	private DataBaseAppController baseController;
	/**
	 * Adds a Button
	 */
	private JButton appButton;
	/**
	 * Adds a Button
	 */
	private JButton appButton2;
	/**
	 * Adds a Button
	 */
	private JButton appButton3;
	/**
	 * Adds a Button
	 */
	private JButton appButton4;
	/**
	 * Adds a Button
	 */
	private JButton appButton5;
	/**
	 * Adds a text area to the Display
	 */
	private JTextArea displayArea;
	/**
	 * Makes the displayArea Scrollable
	 */
	private JScrollPane displayPane;
	/**
	 * This is the layout to the application.
	 */
	private SpringLayout baseLayout;
	/**
	 * A table of information to store data into.
	 */
	private JTable tableData;
	/**
	 * A password field for the database that will actually mask the characters.
	 */
	private JPasswordField password;
	/**
	 * This sets up the Panel on the Application
	 * @param baseController Is the data from the baseController that gets used.
	 */
	
	/**
	 * The Cell Renderer that will take the information and word wrap it.
	 */
	private TableCellWrapRenderer cellRenderer;
	
	/**
	 * The main Panel for the Application.
	 * @param baseController Grabs all the information and puts it into the panel.
	 */
	public DataBasePanel(DataBaseAppController baseController)
	{
		this.baseController = baseController;
		baseLayout = new SpringLayout();
		appButton = new JButton("Describe the Query");
		appButton2 = new JButton("Test the query");
		appButton3 = new JButton("Insert into the Database");
		appButton4 = new JButton("read text from file");
		
		appButton5 = new JButton("save text to file");
		

		displayArea = new JTextArea(15, 30);
		displayPane = new JScrollPane(displayArea);
		
		password = new JPasswordField(null, 20);
		
		cellRenderer = new TableCellWrapRenderer();
	
		//setupTable();
		setupPane();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	/**
	 * Sets up the table inside the panel for use.
	 */
	private void setupTable()
	{
		tableData = new JTable(new DefaultTableModel(baseController.getDataBase().realInfo(), baseController.getDataBase().getMetaData()));
		displayPane = new JScrollPane(tableData);
		
		for(int spot = 0; spot < tableData.getColumnCount(); spot++)
		{
			tableData.getColumnModel().getColumn(spot).setCellRenderer(cellRenderer);
		}
	}
	/**
	 * This is the actual panel that gets setup for the GUI.
	 */
	private void setupPanel()
	{
		this.setBackground(bColor);
		this.setSize(1000,1000);
		this.setLayout(baseLayout);
		this.add(appButton);
		this.add(appButton2);
		this.add(appButton3);
		this.add(appButton4);
		this.add(appButton5);
		this.add(displayPane);
		this.add(password);
		password.setEchoChar('');
//		☢ ☣ ☠ ⚠ ☤ ⚕ ⚚ † ☯ ⚖ ☮ ⚘ 
		password.setForeground(Color.BLACK);
		password.setFont(new Font("Serif", Font.BOLD, 30));
	}
	/**
	 * This is were buttons and other "listeners" will go so an action can be performed with a button.
	 */
	private void setupListeners()
	{
		appButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				tableData = new JTable(new DefaultTableModel(baseController.getDataBase().tableInfo(), baseController.getDataBase().getMetaData()));
				displayPane = new JScrollPane(tableData);
//				String databaseAnswer = baseController.getDataBase().describeTable();
//				displayArea.setText(databaseAnswer);
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
				tableData.setModel(new DefaultTableModel(baseController.getDataBase().realInfo(), baseController.getDataBase().getMetaData()));
			}
		});
		
		appButton4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String savedChat = baseController.readTextFromFile();
				{
					if(savedChat.length()<1)
					{
						displayArea.setText("no text in file");
					}
					else
					{
						displayArea.setText(savedChat);
					}
				}
			}
		});
		
		appButton5.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent click)
			{
				String chat = displayArea.getText();
				baseController.saveText(chat, true, null);
			}
		});
		
	}
		
	/**
	 * The layout for everything, so things aren't overlapping.
	 */
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 40, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, displayPane, 20, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, appButton2, 160, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.WEST, appButton3, 290, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, password, 480, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, appButton4, 300, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, appButton5, 300, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, appButton5, 155, SpringLayout.WEST, this);
	}
	/**
	 * The pane for the application.
	 */
	private void setupPane()
	{
		for(int count = 0; count < baseController.getDataBase().tableInfo().length; count++)
		{
			displayArea.append(baseController.getDataBase().tableInfo()[count][0]);
		}
	}
}
