package db.controller;

import db.view.DataBaseFrame;

public class DataBaseAppController
{
	private DataBaseFrame appFrame;
	private DataBaseController database;
	
	public DataBaseAppController()
	{
		database = new DataBaseController(this);
		appFrame = new DataBaseFrame(this);
	}
	
	public void start()
	{
		
	}
	
	public DataBaseFrame getAppFrame()
	{
		return appFrame;
	}
	
	public DataBaseController getDataBase()
	{
		return database;
	}
}
