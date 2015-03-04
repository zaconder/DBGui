package db.view;

import java.awt.Color;

import javax.swing.JFrame;

import db.controller.DataBaseAppController;

public class DataBaseFrame extends JFrame
{
	private DataBasePanel appPanel;
	
	public DataBaseFrame(DataBaseAppController baseController)
	{
		appPanel = new DataBasePanel(baseController);
		setupFrame();
	}
	
	private void setupFrame()
	{
		this.setSize(1000,1000);
		this.setContentPane(appPanel);
		this.setVisible(true);
	}
}
