import java.awt.Color;

/**
 * Screen where the player can select their weapon of choice
 * 
 * @author Sean Takafuji
 *
 */
public class WeaponSelect {

	// Member Variables
	private Button bowButton, gunButton, rocketLauncherButton, bubbleGunButton;
	private Color borderColor = Color.BLACK;
	private Color weaponButtonColor = Color.GRAY;

	private EZImage background;
	private String backgroundFile = "hunter_background_0.png";
	
	private Play play;

	public WeaponSelect() {

		// Local variables for the dimensions of the screen (used to fill in parameter calculations)
		double x = EZ.getWindowWidth();
		double y = EZ.getWindowHeight();

		// Set the background image
		background = EZ.addImage(backgroundFile, (int) x / 2, (int) y / 2);
		background.translateTo(x / 2, y / 2);

		// Create all the buttons for the Menu Screen
		openWeaponSelect((int) x,(int) y);

		// Use the run function
		run();
	}

	// Main function for the weapon select screen
	private void run() {

		// Local Variable to determine whether the program should be in the Menu Screen
		boolean isWeaponSelect = true;

		// While the program should be in the Menu Screen
		while (isWeaponSelect) {

			// Local variables for the mouse location
			int clickX = EZInteraction.getXMouse();
			int clickY = EZInteraction.getYMouse();

			interaction(clickX, clickY);

			// If mouse left click was pressed
			if (EZInteraction.wasMouseLeftButtonPressed()) {

				// If the click was on top of the bow button
				if (bowButton.checkClick(clickX, clickY)) {

					// Remove the current screen to setup for the game
					EZ.removeAllEZElements();

					// Set the weapon to the bow
					Hunter.selectedWeapon = Hunter.BOW;
					
					// Start the game
					play = new Play();
					
					// Tell the program it is no longer in the menu screen
					isWeaponSelect = false;
				}

				// If the click was on top of the gun button
				if (gunButton.checkClick(clickX, clickY)) {

					// Remove the current screen to setup for the game
					EZ.removeAllEZElements();
					
					// Set the weapon to the gun
					Hunter.selectedWeapon = Hunter.GUN;

					// Start the game
					play = new Play();
					
					// Tell the program it is no longer in the menu screen
					isWeaponSelect = false;
				}

				// If the click was on top of the rocket launcher button
				if (rocketLauncherButton.checkClick(clickX, clickY)) {

					// Remove the current screen to setup for the game
					EZ.removeAllEZElements();
					
					// Set the weapon to the rocket launcher
					Hunter.selectedWeapon = Hunter.ROCKET_LAUNCHER;

					// Start the game
					play = new Play();
					
					// Tell the program it is no longer in the menu screen
					isWeaponSelect = false;
				}

				// If the click was on top of the bubble gun button
				if (bubbleGunButton.checkClick(clickX, clickY)) {

					// Remove the current screen to setup for the game
					EZ.removeAllEZElements();
					
					// Set the weapon to the bubble gun
					Hunter.selectedWeapon = Hunter.BUBBLE_GUN;

					// Start the game
					play = new Play();
					
					// Tell the program it is no longer in the menu screen
					isWeaponSelect = false;

				}
			}

			// Update the screen
			EZ.refreshScreen();
		}
	}

	// Function to select weapon for game
	private void openWeaponSelect(int x, int y) {

		// Create the buttons for the Weapon Select Screen
		// With the corresponding image inside

		EZ.addText("8-BIT WONDER.TTF", x / 2, y / 2, "SELECT YOUR WEAPON", Color.BLACK, 60);

		// Bow Button
		bowButton = new Button(x / 4, y / 4, x / 3, y / 3, 
				borderColor, weaponButtonColor, true);

		EZ.addText("8-BIT WONDER.TTF", x / 4, y / 4 - y / 8, "BOW", Color.BLACK, 40);
		EZ.addImage("hunter_move_left_0.png", x / 4, y / 4 + y / 24);
		EZ.addImage("hunter_bow_left_0.png", x / 4, y / 4 + y / 24);

		// Gun Button
		gunButton = new Button(x / 4, 3 * y / 4, x / 3, y / 3, 
				borderColor, weaponButtonColor, true);

		EZ.addText("8-BIT WONDER.TTF", x / 4, 3 * y / 4 - y / 8, "MACHINE GUN", Color.BLACK, 40);
		EZ.addImage("hunter_move_left_0.png", x / 4, 3 * y / 4 + y / 24);
		EZ.addImage( "hunter_gun_left_0.png", x / 4, 3 * y / 4 + y / 24);


		// Rocket Launcher Button
		rocketLauncherButton = new Button(3 * x / 4, y / 4, x / 3, y / 3, 
				borderColor, weaponButtonColor, true);

		EZ.addText("8-BIT WONDER.TTF", 3 * x / 4, y / 4 - y / 8, "ROCKET LAUNCHER", Color.BLACK, 40);
		EZ.addImage("hunter_move_left_0.png", 3 * x / 4, y / 4 + y / 24);
		EZ.addImage("hunter_rocket_launcher_left_0.png", 3 * x / 4, y / 4 + y / 24);

		// Bubble Gun Button
		bubbleGunButton = new Button(3 * x / 4, 3 * y / 4, x / 3, y / 3, 
				borderColor,  weaponButtonColor, true);

		EZ.addText("8-BIT WONDER.TTF", 3 * x / 4, 3 * y / 4 - y / 8, "BUBBLE GUN", Color.BLACK, 40);
		EZ.addImage("hunter_move_left_0.png", 3 * x / 4, 3 * y / 4 + y / 24);
		EZ.addImage("hunter_bubble_gun_left_0.png", 3 * x / 4, 3 * y / 4 + y / 24);
	}
	
	// Function to highlight buttons
	private void interaction(int x, int y) {
		
		bowButton.highlightButton(x, y, weaponButtonColor);
		gunButton.highlightButton(x, y, weaponButtonColor);
		rocketLauncherButton.highlightButton(x, y, weaponButtonColor);
		bubbleGunButton.highlightButton(x, y, weaponButtonColor);
	}
}
