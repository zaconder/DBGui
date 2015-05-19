package db.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import db.controller.DataBaseAppController;

public class DataBaseFrame extends JFrame
{
	private DataBasePanel appPanel;
	private DataBaseAppController baseController;
	
	public DataBaseFrame(DataBaseAppController baseController)
	{
		this.baseController = baseController;
		appPanel = new DataBasePanel(baseController);
		setupFrame();
		setupListeners();
		
		
	}
	
	private void setupFrame()
	{
		this.setSize(1000,1000);
		this.setContentPane(appPanel);
		this.setVisible(true);
	}
	
	private void setupListeners()
	{
//		this.addWindowListener(new WindowListener()
//		{
//
//			@Override
//			public void windowOpened(WindowEvent e)
//			{
//				
//				
//			}
//
//			@Override
//			public void windowClosing(WindowEvent e)
//			{
//				baseController.saveText(conversation, appendToEnd, timeingInfoList);
//				
//			}
//
//			@Override
//			public void windowClosed(WindowEvent e)
//			{
//				
//				
//			}
//
//			@Override
//			public void windowIconified(WindowEvent e)
//			{
//				
//				
//			}
//
//			@Override
//			public void windowDeiconified(WindowEvent e)
//			{
//				
//				
//			}
//
//			@Override
//			public void windowActivated(WindowEvent e)
//			{
//				
//				
//			}
//
//			@Override
//			public void windowDeactivated(WindowEvent e)
//			{
//				
//				
//			}
//			
//		});
	}
}
