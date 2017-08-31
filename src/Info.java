
/**
 * The screen for the "Info" about the game. Slightly different from the how to play
 * 
 * @author Sean Takafuji
 */

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Info {
	
	// Member variables
	private ArrayList<EZText> infoText = new ArrayList<EZText>();
	private ArrayList<String> infoList = new ArrayList<String>();
	private Button menuButton, textBox;
	private EZImage background;
	private Scanner fs;
	private String backgroundFile = "hunter_background_0.png";

	// Constructor that generates the Info Screen
	public Info() {
		
		// Local variables for the dimensions of the screen (used to fill in parameter calculations)
		int x = EZ.getWindowWidth();
		int y = EZ.getWindowHeight();

		// Set the background image
		background = EZ.addImage(backgroundFile, x / 2, y / 2);
		
		// Set the textbox
		textBox = new Button(x / 2, 3 *  y / 7, 3 * x / 5, 15 * y / 20, Color.BLACK, Color.WHITE, true);

		// Create the buttons for the Info screen
		menuButton = new Button(x / 2, 9 * y / 10, x / 3, y / 10, Color.WHITE, Color.GREEN, true, "RETURN TO MENU", 20);
		
		readInfo("info.txt");
		
		printInfo(x, y);
		
		// Use the run function
		run();
	}

	// Function that runs while the Info Screen is open
	private void run() {

		// Local Variable to determine whether the program should be in the Info Screen
		boolean isInfo = true;

		// While the program should be in the Info Screen
		while (isInfo) {

			// Local variables for the mouse location
			int clickX = EZInteraction.getXMouse();
			int clickY = EZInteraction.getYMouse();

			interaction(clickX, clickY);
			
			// If mouse left click was pressed
			if (EZInteraction.wasMouseLeftButtonPressed()) {
				
				// If the click was on top of the menu button
				if (menuButton.checkClick(clickX, clickY)) {
					
					// Remove all elements from the options screen
					closeInfo();
					
					// Open the Menu Screen
					Menu menu = new Menu();
					
					// Tell the program it should no longer be in the info screen
					isInfo = false;
				}

			}

			// Update the screen
			EZ.refreshScreen();
		}
	}
	
	// Function to read the info file
	private void readInfo(String fileName) {
		
		// Try to make the file reading scanner
		try {
			
			// Make the file reading scanner
			fs = new Scanner(new FileReader(fileName));
			
		} catch (FileNotFoundException e) { // If the file can't be found
			
			// Print out that the file can't be found
			System.out.println(fileName + " not found!");
		}
		
		// While the file has info
		while (fs.hasNextLine()) {
			
			// Add the line to the array that stores the info
			infoList.add(fs.nextLine().trim()); // Trailing white space is also trimmed
		}
		
		fs.close();
	}
	
	// Function to print out the information in the info file
	private void printInfo(int x, int y) {
		
		// Loop that runs depending on the size of infoList
		for (int i = 0; i < infoList.size(); i++) {
			
			// Print the text for each line in infoList
			infoText.add(EZ.addText("8-BIT WONDER.TTF", x / 2, (y / 9) * (i + 1), infoList.get(i), Color.BLACK, x / 40));
		}
		
		// Clears the info list so the elements inside don't repeat themselves
		infoList.clear();
	}
	
	private void interaction(int x, int y) {
		
		menuButton.highlightButton(x, y, Color.GREEN);
	}
	
	// Close all info
	private void closeInfo() {
		
		// Close all buttons
		menuButton.close();
		
		// Close the background
		EZ.removeEZElement(background);
		
		for (int i = 0; i < infoText.size(); i++) {
			
			EZ.removeEZElement(infoText.get(i));
		}
	}
}
