package db.controller;

import java.util.ArrayList;
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
	
}
