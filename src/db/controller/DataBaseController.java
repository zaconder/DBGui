package db.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import db.model.QueryInfo;

/**
 * The controller that manages the connection to the database and what the
 * connection will do.
 * 
 * @author zcon5199
 * @version 1.4 3/18/2015 Added new methods to access the database.
 */
public class DataBaseController
{
	private String connectionString;
	private Connection databaseConnection;
	private DataBaseAppController baseController;
	private String currentQuery;

	/**
	 * This connects to the Database as user "root" and it accesses the database
	 * of "motorcycle"
	 * 
	 * @param dataBaseAppController
	 */
	public DataBaseController(DataBaseAppController dataBaseAppController)
	{
		String databaseName = "motorcycle";
		String pathToDBServer = "localhost";
		String userName = "root";
		String password = "";
//		connectionString = "jdbc:mysql://localhost/motorcycle?user=root";
		this.baseController = dataBaseAppController;
		checkDriver();
		connectionStringBuilder(pathToDBServer, databaseName, userName, password);
		setupConnection();
		

	}
	
	/**
	 * This method will prompt the user to input the information for the DataBase
	 * ? means to interrupt the code to start sending information
	 * & means to continue the interruption
	 * @param pathToDBServer This is the Path to the Database Server
	 * @param databaseName This is the path to the Database inside the database
	 * @param userName The user name for the database
	 * @param password The Password for the database
	 */
	public void connectionStringBuilder(String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" + userName;
		connectionString += "&password=" + password;
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
	public void setupConnection()
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
		long startTime, endTime;
		startTime = System.currentTimeMillis();

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
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
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
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();
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
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
		return results;

	}

	/**
	 * This is where the SQL code gets used and actually inserts data into the
	 * DataBase.
	 * 
	 * @return Will return how many rows have been affected by this.
	 */
	public int insertSample()
	{
		int rowsAffected = 0;
		String insertQuery = "INSERT INTO `motorcycle`.`motorcycles`"
							+ " (`id`, `color`, `type_motorcycle`, `top_speed`, `engine_size_cc`, `manufacturer`, `year_made`, `name`)"
							+ " VALUES (NULL, 'Blue', 'Street', '130', '300', 'honda', '206', 'shadow');";

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
	 * 
	 * @return will display the information that has been converted
	 */
	public String[][] tableInfo()
	{

		String[][] results;
		String query = "SHOW TABLES";
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount][1];

			while (answer.next())
			{
				results[answer.getRow() - 1][0] = answer.getString(1);
			}

			answer.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			results = new String[][] { { "you get, NOTHING!!!! :D" } };
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
		return results;
	}

	/**
	 * Will get the metaData that is in the query
	 * 
	 * @return returns the column info of the table
	 */
	public String[] getMetaData()
	{
		String[] colInfo;
		String query = "SELECT * FROM `motorcycles`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();

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
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			colInfo = new String[] { "NOTHING!!" };
			displayErrors(currentSQLError);
		}
		
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
		return colInfo;
	}

	/**
	 * Grabs the info from a database
	 * @return The results of the database info
	 */
	public String[][] realInfo()
	{
		String[][] results;
		String query = "SELECT * FROM `motorcycles`";
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int columnCount = answer.getMetaData().getColumnCount();
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount][columnCount];

			while (answer.next())
			{
				for (int col = 0; col < columnCount; col++)
				{
					results[answer.getRow() - 1][col] = answer.getString(col + 1);
				}
			}

			answer.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			results = new String[][] { { "error processing" } };
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
		return results;
	}

	/**
	 * Will check for any Violations in the code inserted into the database.
	 * @return
	 */
	private boolean checkForDataViolation()
	{
		if (currentQuery.toUpperCase().contains(" DROP ")
				|| currentQuery.toUpperCase().contains(" TRUNCATE ")
				|| currentQuery.toUpperCase().contains(" SET ")
				|| currentQuery.toUpperCase().contains(" ALTER "))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * Generic select based query for the DatabaseController. Checks that the
	 * query will not destroy data by calling the checkForDataViolation method in
	 * the try/catch.
	 * @param query The query to be executed on the database. 
	 * It will be set as the currentQuery for the controller         
	 * @return the 2d array of results from the query to be displayed in the JTable.
	 */
	public String[][] selectedQueryResults(String query)
	{
		this.currentQuery = query;
		String[][] results;
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();

		try
		{
			if (checkForDataViolation())
			{
				throw new SQLException("Attempted illigal modification of data", 
						":( Dont tried to mess up da data state :(",
						Integer.MIN_VALUE);
			}

			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int columnCount = answer.getMetaData().getColumnCount();

			answer.last();
			int rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount][columnCount];

			while (answer.next())
			{
				for (int col = 0; col < columnCount; col++)
				{
					results[answer.getRow() - 1][col] = answer.getString(col + 1);
				}
			}

			answer.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		} catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			results = new String[][] {
										{ "error processing" },
										{"try sending a better query"},
										{currentSQLError.getMessage()}
									};
			displayErrors(currentSQLError);
		}
		
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
		return results;
	}

	{
	}

}
