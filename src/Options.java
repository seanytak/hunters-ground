
/**
 * Screen that holds all the options and customizable settings
 * 
 * @author Sean Takafuji
 */

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Options {

	// Member variables
	private int[] optionsList = new int[4];
	private String[] optionsName = {"Player Health", "Platform Count", "TRex Count", "Game Duration"};
	private ArrayList<EZText> optionsText = new ArrayList<EZText>();
	private ArrayList<Button> optionsButton = new ArrayList<Button>();
	private Button menuButton, textBox;
	private EZImage background;
	private FileWriter fw;
	private Scanner fs;
	private String backgroundFile = "hunter_background_0.png";

	// Constructor that generates the Options Screen
	public Options() {

		// Local variables for the dimensions of the screen (used to fill in parameter calculations)
		int x = EZ.getWindowWidth();
		int y = EZ.getWindowHeight();

		// Set the background image
		background = EZ.addImage(backgroundFile, x / 2, y / 2);

		// Set the textbox
		textBox = new Button(x / 2, 3 *  y / 7, 3 * x / 5, 15 * y / 20, Color.BLACK, Color.WHITE, true);

		// Create the buttons for the Options Screen
		menuButton = new Button(x / 2, 9 * y / 10, x / 3, y / 10, Color.WHITE, Color.GREEN, true, "RETURN TO MENU", 20);

		readOptions();

		printOptions(x, y);

		// Use the run function
		run();
	}

	// Function that runs while the Options Screen is open
	private void run() {

		// Local Variable to determine whether the program should be in the Options Screen
		boolean isOptions = true;

		// While the program should be in the Options Screen
		while (isOptions) {

			// Local variables for the mouse location
			int clickX = EZInteraction.getXMouse();
			int clickY = EZInteraction.getYMouse();

			interaction(clickX, clickY);
			updateOptions();

			// If mouse left click was pressed
			if (EZInteraction.wasMouseLeftButtonReleased()) {

				// If the click was on top of the menu button
				if (menuButton.checkClick(clickX, clickY)) {

					// Remove all elements from the options screen
					closeOptions();

					// Open the menu screen
					Menu menu = new Menu();

					// Tell the program it is no longer in the options screen
					isOptions = false;
				}
				
				for (int i = 0; i < optionsButton.size(); i++) {
					if (optionsButton.get(i).checkClick(clickX, clickY)) {
						switch (i) {
						
						case 0: optionsList[0] += 10; break;
						case 1: optionsList[0] -= 10; break;
						case 2: optionsList[1] += 1; break;
						case 3: optionsList[1] -= 1; break;
						case 4: optionsList[2] += 1; break;
						case 5: optionsList[2] -= 1; break;
						case 6: optionsList[3] += 1; break;
						case 7: optionsList[3] -= 1; break;
						}
					}
				}
			}

			// Update the screen
			EZ.refreshScreen();
		}
	}

	private void readOptions() {

		// Try to make the file reading scanner
		try {

			// Make the file reading scanner
			fs = new Scanner(new FileReader("playerOptions.txt"));

		} catch (FileNotFoundException e) { // If the file can't be found

			// Print out that the file can't be found
			System.out.println("playerOptions.txt" + " not found!");
		}

		optionsList[0] = fs.nextInt();

		fs.close();

		// Try to make the file reading scanner
		try {

			// Make the file reading scanner
			fs = new Scanner(new FileReader("mapOptions.txt"));

		} catch (FileNotFoundException e) { // If the file can't be found

			// Print out that the file can't be found
			System.out.println("playerOptions.txt" + " not found!");
		}

		optionsList[1] = fs.nextInt();
		optionsList[2] = fs.nextInt();
		optionsList[3] = fs.nextInt();

		fs.close();
	}

	// Print the options out
	private void printOptions(int x, int y) {

		int j = 1;
		
		// Loop that runs depending on the size of optionsList
		for (int i = 0; i < optionsList.length; i++) {

			// Print the text for each line in optionsList
			optionsText.add(EZ.addText("8-BIT WONDER.TTF", x / 2, (y / 12) * (i + j), 
					optionsName[i], Color.BLACK, x / 50));
			
			j+=1;
			
			optionsText.add(EZ.addText("8-BIT WONDER.TTF", x / 2, (y / 12) * (i + j), 
					Integer.toString(optionsList[i]), Color.BLACK, x / 50));
			
			optionsButton.add(new Button("PLUS", x / 2 - x / 8, (y / 12) * (i + j), x / 16, y / 16, Color.BLACK, Color.GREEN, true));
			optionsButton.add(new Button("MINUS", x / 2 + x / 8, (y / 12) * (i + j), x / 16, y / 16, Color.BLACK, Color.RED, true));
		}
	}
	
	// Update the options
	private void updateOptions() {
		
		optionsText.get(1).setMsg(Integer.toString(optionsList[0]));
		optionsText.get(3).setMsg(Integer.toString(optionsList[1]));
		optionsText.get(5).setMsg(Integer.toString(optionsList[2]));
		optionsText.get(7).setMsg(Integer.toString(optionsList[3]));
	}

	// Check if button is being interacted with
	private void interaction(int x, int y) {

		menuButton.highlightButton(x, y, Color.GREEN);
		
		for (int i = 0; i < optionsButton.size(); i++) {
			
			if (i % 2 == 0) {
				
				optionsButton.get(i).highlightButton(x, y, Color.GREEN);
			}
			
			else {
				
				optionsButton.get(i).highlightButton(x, y, Color.RED);
			}

		}
	}

	// Function to close elements in the Options Screen
	private void closeOptions() {

		// Close the background
		EZ.removeEZElement(background);

		// Close all buttons
		menuButton.close();
		
		for (int i = 0; i < optionsButton.size(); i++) {
			optionsButton.get(i).close();
		}
		
		// Write to the files
		try {
			
			fw = new FileWriter("playerOptions.txt");
			fw.write(Integer.toString(optionsList[0]));
			
			fw.close();
			fw = new FileWriter("mapOptions.txt");
			fw.write(Integer.toString(optionsList[1]) + "\n");
			fw.write(Integer.toString(optionsList[2]) + "\n");
			fw.write(Integer.toString(optionsList[3]) + "\n");
			fw.close();
			
		} catch (Exception e) {}
	}

	private void optionsList() {

	}
}
