
/**
 * Main screen class
 * 
 * @author Sean Takafuji
 */

import java.awt.Color;

public class Menu {
	
	// Member variables
	private Button guideButton, optionsButton, playButton, quitButton, infoButton;
	
	private Color borderColor = Color.WHITE;
	private Color playColor = Color.GREEN;
	private Color guideColor = Color.BLUE;
	private Color infoColor = Color.BLUE;
	private Color optionsColor = Color.BLUE;
	private Color quitColor = Color.RED;
	
	private EZImage background, title;
	private Guide guide;
	private Options options;
	private WeaponSelect play;
	private Info info;
	private String backgroundFile = "hunter_background_0.png", titleFile = "hunter_title_0.png";
	
	
	// Constructor to call the "Menu Screen"
	public Menu() {
		
		// Local variables for the dimensions of the screen (used to fill in parameter calculations)
		double x = EZ.getWindowWidth();
		double y = EZ.getWindowHeight();
		
		// Set the background image
		background = EZ.addImage(backgroundFile, (int) x / 2, (int) y / 2);
		background.translateTo(x / 2, y / 2);
		
		// Set the title image
		title = EZ.addImage(titleFile, (int) x / 2, (int) ((int) y / 3.5));
		
		// Create all the buttons for the Menu Screen
		openMenu((int) x,(int) y);
		
		// Use the run function
		run();
	}
	
	// Function that runs while the Menu Screen is open
	private void run() {

		// Local Variable to determine whether the program should be in the Menu Screen
		boolean isMenu = true;
		
		// While the program should be in the Menu Screen
		while (isMenu) {
			
			// Local variables for the mouse location
			int clickX = EZInteraction.getXMouse();
			int clickY = EZInteraction.getYMouse();
			
			interaction(clickX, clickY);
			
			// If mouse left click was pressed
			if (EZInteraction.wasMouseLeftButtonPressed()) {
				
				// If the click was on top of the play button
				if (playButton.checkClick(clickX, clickY)) {
					
					// Remove the background
					EZ.removeAllEZElements();
					
					// Remove all elements from the menu screen
					closeMenu();
					
					play = new WeaponSelect();
					
					// Tell the program it is no longer in the menu screen
					isMenu = false;
				}
				
				// If the click was on top of the guide button
				if (guideButton.checkClick(clickX, clickY)) {
					
					// Remove all elements from the menu screen
					closeMenu();
					
					// Open the Rules Screen
					guide = new Guide();
					
					// Tell the program it is no longer in the menu screen
					isMenu = false;
				}
				
				// If the click was on top of the info button
				if (infoButton.checkClick(clickX, clickY)) {
					
					// Remove all elements from the menu screen
					closeMenu();
					
					// Open the Rules Screen
					info = new Info();
					
					// Tell the program it is no longer in the menu screen
					isMenu = false;
				}
				
				// If the click was on top of the options button
				if (optionsButton.checkClick(clickX, clickY)) {
					
					// Remove all elements from the menu screen
					closeMenu();
					
					// Open the Options Screen
					options = new Options();
					
					// Tell the program it is no longer in the menu screen
					isMenu = false;
					
				}
				
				// If the click was on top of the quit button
				if (quitButton.checkClick(clickX, clickY)) {
					
					// Call the close function
					exit();
				}	
			}
			
			// Update the screen
			EZ.refreshScreen();
		}
	}
	
	// Open all Menu Elements
	private void openMenu(int x, int y) {
		
		// Create the buttons for the Menu
		playButton = new Button(x / 4, 15 * y / 20, x / 2 - x / 10, y / 3, borderColor, playColor, true, "PLAY", x / 8);
		guideButton = new Button(5 * x / 8, 13 * y / 20, x / 4 - x / 20, y / 6, borderColor, infoColor, true, "GUIDE", x / 24);
		infoButton = new Button(5 * x / 8, 17 * y / 20, x / 4 - x / 20, y / 6, borderColor, infoColor, true, "INFO", x / 24);
		optionsButton = new Button(7 * x / 8, 13 * y / 20, x / 4 - x / 20, y / 6, borderColor,  optionsColor, true, "OPTIONS", x / 24);
		quitButton = new Button(7 * x / 8, 17 * y / 20, x / 4 - x / 20, y / 6, borderColor, quitColor, true, "QUIT", x / 24);
		
	}
	
	// Function to highlight buttons
	private void interaction(int x, int y) {
		
		playButton.highlightButton(x, y, playColor);
		guideButton.highlightButton(x, y, guideColor);
		infoButton.highlightButton(x, y, infoColor);
		optionsButton.highlightButton(x, y, optionsColor);
		quitButton.highlightButton(x, y, quitColor);
	}
	
	// Close all Menu Elements
	private void closeMenu() {
		
		// Close all buttons
		playButton.close();
		guideButton.close();
		infoButton.close();
		optionsButton.close();
		quitButton.close();
	}
	
	// Function to close the program
	public void exit() {
		
		// Closes the program
		System.exit(0);
	}
}
