package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.MainCourse;
import model.MenuCard;


/**
 * TODO: Write a thorough description of this class and also java docs
 * for the constructor and the class' methods
 * 
 * 
 * @author Christoffer Søndergaard
 * @version 21/05/2025 - 08:54
 */	
public class ViewGuestMenuAdult extends JFrame
{
	// Added in order to suppress the warning that appears in serializable classes where no serialVersionUID is specified
	private static final long serialVersionUID = 1L;
	
	// The Jframes that will be retrieved from the guest frame theme and used for manipulating the contents
	private JPanel navigationPanel;
	private JPanel primaryContentPanel;
	private JPanel bottomPanel;
	
	// Determines whether or not this frame should have the "tilbage" button enabled in the navigational panel
	boolean isPreviousViewEnabled = true;
	
	// Determines whether or not the 'Anmod om service' button is enabled in the navigational panel
	boolean isServiceEnabled = true;
	
	
	/**
	 * Create the frame.
	 */
	public ViewGuestMenuAdult()
	{
		initGUI();
	}
	
	
	private void initGUI()
	{
		// Creates a ComponentFrameThemeGuest component
		ComponentFrameThemeGuest frameTheme = new ComponentFrameThemeGuest();

		// Changes the frame / view's theme to the universal bone's brand theme
		// - 	OBS! Since the heading and instruction text utilizes HTML for structuring
		// 		you must use the <br> tag to break up the text instead of \n 
		frameTheme.applyGeneralVisuals("VOKSEN MENUKORT", this, "Tilføj Retter", "Vælg alle de retter og drikkevare som du<br>ønsker at tilføje til din ordrebestilling.");
		
		
		
		// Retrieves the navigationPanel component from the frameTheme
		navigationPanel = frameTheme.getNavigationPanel();
		
		// Retrieves the primary content panel component from the the frameTheme
		// - This is the panel that we add our varying contents to in the different view / windows
		primaryContentPanel = frameTheme.getPrimaryContentPanel();
		
		// Retrieves the bottomContentPanel component from the frameTheme
		bottomPanel = frameTheme.getBottomContentPanel();


		
		///////////////////////////////
		// - Primary Content Panel - //
		///////////////////////////////
		//   THIS IS WHERE CONTENT   //
		//   SHOULD BE INSERTED IN   //
		///////////////////////////////
		
		MenuCard adultMenu = UtilityGuestInformation.getInstance().getAdultMenu();
		List<MainCourse> listOfMainCourses = UtilityGuestInformation.getInstance().getMainCourses(adultMenu);
		
		for (MainCourse mainCourse: listOfMainCourses)
		{
			ComponentGuestMenuItem menuItemBox = new ComponentGuestMenuItem(mainCourse);
			primaryContentPanel.add(menuItemBox);
			primaryContentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
			menuItemBox.getAddButton().addActionListener(x -> {
				UtilityGuestInformation.getInstance().enterMainCourse(menuItemBox.getMainCourse());
				openUniversalMainMenu();
			});
		}
		
		
		////////////////////////////////
		// - Navigation Back Button - //
		////////////////////////////////
		
		
		// Creates a button with the specified text
		ComponentGuestNavigationButton btnBack = new ComponentGuestNavigationButton("← Tilbage");
		
		// If the isPreviousViewEnabled variable is set to true then execute this section
		if(isPreviousViewEnabled == true)
		{
			// Moves the button to the west side of the panel it is attached to
			navigationPanel.add(btnBack, BorderLayout.WEST);
			
			// Adds an action listener for when the button is clicked
			btnBack.addActionListener(event ->
			{
				// Create and configure the new frame (you can replace this with your actual destination window)
				ViewGuestMenuOverview previouslyOpenFrameView = new ViewGuestMenuOverview();
				
				// Sets the visibility to true turning the previous view / window visible
				previouslyOpenFrameView.setVisible(true);

				// Find and closes the current view / window
				this.dispose();
			});
		}
		
		
		
		///////////////////////////////////
		// - Navigation Service Button - //
		///////////////////////////////////
		
		// If the isServiceEnabled variable is set to true then execute this section
		if(isServiceEnabled == true)
		{
			// Creates a button with the specified text
			ComponentGuestNavigationButton btnRequestService = new ComponentGuestNavigationButton("Anmod Om Service 🔔");
			
			// Moves the button to the east side of the panel it is attached to
			navigationPanel.add(btnRequestService, BorderLayout.EAST);
		}
		

		//////////////////////////////
		// - Bottom Panel Buttons - //
		//////////////////////////////
		
		
		// Creates a customized button component with the supplied information
		ComponentGuestButtonContinue btnEmptyOrder = new ComponentGuestButtonContinue("Tøm Bestilling", bottomPanel);
		
		// Adds the customized button to the panel
		bottomPanel.add(btnEmptyOrder);
		
		// Adds an action listener for when the button is clicked
		btnEmptyOrder.addActionListener(event ->
		{
			UtilityGuestInformation.getInstance().clearOrder();
		});
		
		
		// Creates a customized button component with the supplied information
		ComponentGuestButtonContinue btnCompleteOrder = new ComponentGuestButtonContinue("Færdiggør Bestilling", bottomPanel);
		
		// Adds the customized button to the panel
		bottomPanel.add(btnCompleteOrder);
		
		// Adds an action listener for when the button is clicked
		btnCompleteOrder.addActionListener(event ->
		{
			UtilityGuestInformation.getInstance().finishPersonalOrder();
			// Creates the new frame that should be opened when pressing the button
			ViewGuestTableOrder nextView = new ViewGuestTableOrder();

			// Sets the visibility to true turning the previous view / window visible
			nextView.setVisible(true);
			
			// Closes the current frame/window
			this.dispose();
		});
		
		// TODO: This is a very dirty fix for displaying the graphical user interface
        //          contents without spending too much time on adjusting them, this 
        //         would be added to the backlog and revisited in a later iteration.
        //
		
		// Gets the screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Changes the size of the to the specified screen size
		setSize(screenSize);
		
		// Changes the view / window's position to the middle of the screen
		setLocationRelativeTo(null);
	}
	
	private void openUniversalMainMenu()
	{
		// Creates the new frame that should be opened when pressing the button
					JFrame nextView = new ViewGuestUniversalMainMenu();

					// Sets the visibility to true turning the previous view / window visible
					nextView.setVisible(true);
					
					// Closes the current frame/window
					this.dispose();
	}
}