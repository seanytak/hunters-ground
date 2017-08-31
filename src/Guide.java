
/**
 * A screen for a "How to play" manual
 * 
 * @author Sean Takafuji
 */

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class Guide {
	
	// Member variables
	private ArrayList<EZText> guideText = new ArrayList<EZText>();
	private ArrayList<String> guideList = new ArrayList<String>();
	private Button menuButton, textBox;
	private EZImage background;
	private Scanner fs;
	private String backgroundFile = "hunter_background_0.png";
	private String textBoxFile = "point_box.png";

	// Constructor that generates the Rules Screen
	public Guide() {
		
		// Local variables for the dimensions of the screen (used to fill in parameter calculations)
		int x = EZ.getWindowWidth();
		int y = EZ.getWindowHeight();

		// Set the background image
		background = EZ.addImage(backgroundFile, x / 2, y / 2);
		
		// Set the textbox
		textBox = new Button(x / 2, 3 *  y / 7, 3 * x / 5, 7 * y / 10, Color.BLACK, Color.WHITE, true);
		

		// Create the buttons for the Rules screen
		menuButton = new Button(x / 2, 9 * y / 10, x / 3, y / 10, Color.WHITE, Color.GREEN, true, "RETURN TO MENU", 20);
		
		readGuide("guide.txt");
		
		printGuide(x, y);
		
		// Use the run function
		run();
	}

	// Function that runs while the Rules Screen is open
	private void run() {

		// Local Variable to determine whether the program should be in the Rules Screen
		boolean isRules = true;

		// While the program should be in the Rules Screen
		while (isRules) {

			// Local variables for the mouse location
			int clickX = EZInteraction.getXMouse();
			int clickY = EZInteraction.getYMouse();

			interaction(clickX, clickY);
			
			// If mouse left click was pressed
			if (EZInteraction.wasMouseLeftButtonPressed()) {
				
				// If the click was on top of the menu button
				if (menuButton.checkClick(clickX, clickY)) {
					
					// Remove all elements from the options screen
					closeGuide();
					
					// Open the Menu Screen
					Menu menu = new Menu();
					
					// Tell the program it should no longer be in the rules screen
					isRules = false;
				}

			}

			// Update the screen
			EZ.refreshScreen();
		}
	}
	
	// Function to read the rules file
	private void readGuide(String fileName) {
		
		// Try to make the file reading scanner
		try {
			
			// Make the file reading scanner
			fs = new Scanner(new FileReader(fileName));
			
		} catch (FileNotFoundException e) { // If the file can't be found
			
			// Print out that the file can't be found
			System.out.println(fileName + " not found!");
		}
		
		// While the file has rules
		while (fs.hasNextLine()) {
			
			// Add the line to the array that stores the rules
			guideList.add(fs.nextLine().trim()); // Trailing white space is also trimmed
		}
		
		fs.close();
	}
	
	private void printGuide(int x, int y) {
		
		// Loop that runs depending on the size of guideList
		for (int i = 0; i < guideList.size(); i++) {
			
			// Print the text for each line in guideList
			guideText.add(EZ.addText("8-BIT WONDER.TTF", x / 2, (y / 9) * (i + 1), guideList.get(i), Color.BLACK, x / 50));
		}
		
		// Clears the rules list so the elements inside don't repeat themselves
		guideList.clear();
	}
	
	private void interaction(int x, int y) {
		
		menuButton.highlightButton(x, y, Color.GREEN);
	}
	
	// Close all rules
	private void closeGuide() {
		
		// Close all buttons
		menuButton.close();
		
		// Close the background
		EZ.removeEZElement(background);
		
		for (int i = 0; i < guideText.size(); i++) {
			
			EZ.removeEZElement(guideText.get(i));
		}
	}
}
