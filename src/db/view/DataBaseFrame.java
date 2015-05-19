package db.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import db.controller.DataBaseAppController;

/**
 * The frame for the Database
 * @author zcon5199
 *
 */
public class DataBaseFrame extends JFrame
{
	/**
	 * The panel for the Application.
	 */
	private DataBasePanel appPanel;
	
	/**
	 * The link between the DBAppController.
	 */
	private DataBaseAppController baseController;
	
	/**
	 * The method for the DB frame that holds the panel.
	 * @param baseController Takes the information from the baseController.
	 */
	public DataBaseFrame(DataBaseAppController baseController)
	{
		this.baseController = baseController;
		appPanel = new DataBasePanel(baseController);
		setupFrame();
		setupListeners();
		
		
	}
	
	/**
	 * Sets up the Frame with a 1000x1000 frame for the informtaion to be displayed.
	 */
	private void setupFrame()
	{
		this.setSize(1000,1000);
		this.setContentPane(appPanel);
		this.setVisible(true);
	}
	
	
	/**
	 * Sets up some listeners for the application.
	 */
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
