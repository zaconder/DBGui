package db.controller;

import java.sql.*;
import javax.swing.*;

/**
 * The controller that manages the connection to the database and what the
 * connection will do.
 * 
 * @author zcon5199
 * @version 1.4 3/4/2015 Converted 1d data into 2d data.
 */
public class DataBaseController
{
	private String connectionString;
	private Connection databaseConnection;
	private DataBaseAppController baseController;

	/**
	 * This connects to the Database as user "root" and it accesses the database
	 * of "motorcycle"
	 * 
	 * @param dataBaseAppController
	 */
	public DataBaseController(DataBaseAppController dataBaseAppController)
	{
		connectionString = "jdbc:mysql://localhost/motorcycle?user=root";
		this.baseController = dataBaseAppController;
		checkDriver();
		setupConnection();

	}

	/**
	 * This method will check the driver being used and if it's not the correct
	 * one, it will display an error.
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception currentException)
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
		} catch (SQLException error)
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
		} catch (SQLException currentException)
		{
			displayErrors(currentException);
		}
	}

	/**
	 * displayErrors is where the error codes are stored. It will grab the
	 * current exception and display it to the user.
	 * 
	 * @param currentException
	 *            The error code.
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());

		if (currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException));
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException));
		}
	}

	/**
	 * Display tables will grab data (tables) from the Database and present it
	 * to the user inside the panel.
	 * 
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
			while (answer.next())
			{
				results += answer.getString(1) + "\n";
			}
			answer.close();
			firstStatement.close();
		} catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}

		return results;
	}

	/**
	 * This will grab data from a specific table and describe the data inside of
	 * it.
	 * 
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
			while (answer.next())
			{
				results += answer.getString(1) + "\t" + answer.getString(2) + "\t" + answer.getString(3) + "\t" + answer.getString(4) + "\n";
			}
			answer.close();
			firstStatement.close();
		} catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}

		return results;

	}

	/**
	 * This is where the SQL code gets used and actually inserts data into the DataBase.
	 * @return Will return how many rows have been affected by this.
	 */
	public int insertSample()
	{
		int rowsAffected = 0;
		String insertQuery = "INSERT INTO `motorcycle`.`motorcycles`"
		+ " (`id`, `color`, `type_motorcycle`, `top_speed`, `engine_size_cc`, `manufacturer`, `year_made`, `name`)"
		+ " VALUES (NULL, 'Green', 'Sport', '165', '300', 'Kawasaki', '2014', 'Kawasaki Ninja');";

		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(insertQuery);
			insertStatement.close();
		} catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}

		return rowsAffected;
	}

	
	/**
	 * Will convert 1d data into 2d data
	 * @return will display the information that has been converted
	 */
	public String[][] tableInfo()
	{
		
	String[][] results;
	String query = "SHOW TABLES";
	
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String [rowCount][1];
			
			while(answer.next())
			{
				results[answer.getRow() - 1][0] = answer.getString(1);
			}
			
			answer.close();
			firstStatement.close();
		}
		catch(SQLException currentSQLError)
		{
			results = new String [][] {{"you get, NOTHING!!!! :D"}};
			displayErrors(currentSQLError);
		}
		
		return results;
	}

	/**
	 * Will get the metaData that is in the query
	 * @return returns the column info of the table
	 */
	public String[] getMetaData()
	{
		String[] colInfo;
		String query = "SHOW TABLES";

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			ResultSetMetaData myMeta = answer.getMetaData();

			colInfo = new String[myMeta.getColumnCount()];
			for (int spot = 0; spot < myMeta.getColumnCount(); spot++)
			{
				colInfo[spot] = myMeta.getColumnName(spot + 1);
			}

			answer.close();
			firstStatement.close();
		} catch (SQLException currentSQLError)
		{
			colInfo = new String[] { "NOTHING!!" };
			displayErrors(currentSQLError);
		}

		return colInfo;
	}

}
