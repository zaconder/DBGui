package db.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * The controller that manages the connection to the database and what the connection will do.
 * @author zcon5199
 * @version 1.3 3/4/2015
 */
public class DataBaseController
{
	private String connectionString;
	private Connection databaseConnection;
	private DataBaseAppController baseController;
	
	/**
	 * This connects to the Database as user "root" and it accesses the database of "motorcycle"
	 * @param dataBaseAppController
	 */
	public DataBaseController(DataBaseAppController dataBaseAppController)
	{
		connectionString = "jdbc:mysql://127.0.0.1/motorcycle?user=root";
		this.baseController = dataBaseAppController;
		checkDriver();
		setupConnection();
		
	}
	
	/**
	 * This method will check the driver being used and if it's not the correct one, it will display an error.
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}
	
	/**
	 * Will close the Connection when the user closes the window.
	 */
	public void closeConnection()
	{
		try
		{
			databaseConnection.close();
		}
		catch (SQLException error)
		{
			displayErrors(error);
		}
	}
	
	/**
	 * This will grab the connection and from the database and establish it.
	 */
	private void setupConnection()
	{
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	/**
	 * displayErrors is where the error codes are stored. It will grab the current exception and display it to the user.
	 * @param currentException The error code.
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());
		
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException));
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException));
		}
	}
	
	/**
	 * Display tables will grab data (tables) from the Database and present it to the user inside the panel.
	 * @return Will return the results of what the database has.
	 */
	public String displayTables()
	{
		String results = "";
		String query = "SHOW TABLES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			while(answer.next())
			{
				results += answer.getString(1) + "\n";
			}
			answer.close();
			firstStatement.close();
		}
		catch(SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return results;
	}
	
	/**
	 * This will grab data from a specific table and describe the data inside of it.
	 * @return Returns the data inside "glove" 
	 */
	public String describeTable()
	{
		String results = "";
		String query = "DESCRIBE glove";
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			while(answer.next())
			{
				results += answer.getString(1) + "\t" + answer.getString(2) + "\t" + answer.getString(3) + "\t" + answer.getString(4) + "\n" ;
			}
			answer.close();
			firstStatement.close();
		}
		catch(SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return results;
		
	}
}
