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
	
	public ArrayList<QueryInfo> getTimingInfoList()
	{
		return timingInfoList;
	}
	
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
