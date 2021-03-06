package db.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import db.model.QueryInfo;
import db.view.DataBaseFrame;

public class DataBaseAppController
{
	/**
	 * this is the appFrame for the application
	 */
	private DataBaseFrame appFrame;
	/**
	 * this is the database information for the application.
	 */
	private DataBaseController database;
	
	private ArrayList<QueryInfo> timingInfoList;
	
	/**
	 * Where the appFrame and the database get instantiated.
	 */
	public DataBaseAppController()
	{
		timingInfoList = new ArrayList<QueryInfo>();
		database = new DataBaseController(this);
		appFrame = new DataBaseFrame(this);
	}
	
	/**
	 * The start method where things get called
	 */
	public void start()
	{	
		readTextFromFile();
//		database.setupConnection();
	}
	/**
	 * The getter for the Frame.
	 * @return returns the information in the Frame.
	 */
	public DataBaseFrame getAppFrame()
	{
		return appFrame;
	}
	
	/**
	 * The getter for the Database.
	 * @return Returns the information in the Database.
	 */
	public DataBaseController getDataBase()
	{
		return database;
	}
	
	/**
	 * Gets the timing info.
	 * @return the list for the timing info.
	 */
	public ArrayList<QueryInfo> getTimingInfoList()
	{
		return timingInfoList;
	}
	
	/**
	 * This method will save text into a text file.
	 * @param conversation This is the actual information that is presented on the screen in the application.
	 * @param appendToEnd This will make sure that all the data will get appended to the end of the file.
	 * @param timeInfo This is the timeing info for the database, and how long it took to do each query.
	 */
	public void saveText(String conversation, boolean appendToEnd, ArrayList<QueryInfo> timeInfo)
	{
		String fileName = "/Users/zcon5199/Documents/saved database text.txt";
		PrintWriter outputWriter;
		ArrayList<QueryInfo> outputStream = getTimingInfoList();
//		FileWriter writer; // = new FileWriter(fileName);
		
		if(appendToEnd)
		{
			try
			{
				outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
				 
				for(QueryInfo currentInfo: outputStream) 
				{
				
				  outputWriter.write(currentInfo.getQuery() + " ");
				  
				  outputWriter.write(currentInfo.getQueryTime()+ "\n");
				}
				outputWriter.close();
			}
			catch(FileNotFoundException noExistingFile)
			{
				JOptionPane.showMessageDialog(appFrame, "there is no file there :(");
				JOptionPane.showMessageDialog(appFrame, noExistingFile.getMessage());
			}
			catch(IOException inputOutputError)
			{
				JOptionPane.showMessageDialog(appFrame, "there is no file there :(");
				JOptionPane.showMessageDialog(appFrame, inputOutputError.getMessage());
			}
		}
		else
		{
			try
			{
				outputWriter = new PrintWriter(fileName);
				outputWriter.println((CharSequence) timeInfo);
				
				outputWriter.close();
			}
			catch(FileNotFoundException noFileIsThere)
			{
				JOptionPane.showMessageDialog(appFrame, "there is no file there :(");
			}
		}
		
	}
	
	/**
	 * This is where it will take the timing information and save it to a file.
	 */
	public void saveTimeingInformation()
	{
		try
		{
			File saveFile = new File("asldkjfasd.save");
			PrintWriter writer = new PrintWriter(saveFile);
			if(saveFile.exists())
			{
				for(QueryInfo current : timingInfoList)
				{
					writer.println(current.getQuery());
					writer.println(current.getQueryTime());
				}
				writer.close();
				JOptionPane.showMessageDialog(getAppFrame(), timingInfoList.size() + " QueryInfo objects were saved");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects saved");
			}
		}
		catch(IOException current)
		{
			this.getDataBase().displayErrors(current);
		}
	}
	
	/**
	 * This method will allow you to read file from the saved text backup file.
	 * @return The text from the file.
	 */
	public String readTextFromFile()
	{
		String fileText = "";
		String filePath = "/Users/zcon5199/Documents/";
		String fileName = filePath + "saved database text.txt";
		File inputFile = new File(fileName);
		
		try
		{
			Scanner fileScanner = new Scanner(inputFile);
			while(fileScanner.hasNext())
			{
				fileText += fileScanner.nextLine() + "\n";
			}
			
			fileScanner.close();
			
		}
		catch(FileNotFoundException fileException)
		{
			JOptionPane.showMessageDialog(appFrame, "the file is not here :/");
		}
		
		return fileText;
	}
	
	/**
	 * loads the information from the text file.
	 */
	private void loadTimingInfo()
	{
		File saveFile = new File("save.txt");
		try
		{
			Scanner readFileScanner;
			if(saveFile.exists())
			{
				timingInfoList.clear();
				readFileScanner = new Scanner(saveFile);
				while(readFileScanner.hasNext())
				{
					String tempQuery = readFileScanner.nextLine();
					readFileScanner.next();
					long tempTime = readFileScanner.nextLong();
					timingInfoList.add(new QueryInfo(tempQuery, tempTime));
				}
				
				readFileScanner.close();
			}
		}
		catch(IOException current)
		{
			this.getDataBase().displayErrors(current);
		}
	}
	
}
