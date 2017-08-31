
/**
 * This is the superclass for all types of weapons the player will use
 * 
 * @author Sean Takafuji
 */

import java.awt.Color;
import java.util.ArrayList;

public class Weapon {

	public ArrayList<String> weaponLeftFiles = new ArrayList<String>();
	public ArrayList<String> weaponRightFiles = new ArrayList<String>();

	public ArrayList<EZImage> weaponLeft = new ArrayList<EZImage>();
	public ArrayList<EZImage> weaponRight = new ArrayList<EZImage>();

	public ArrayList<Long> projectileLife = new ArrayList<Long>();
	public ArrayList<Projectile> projectile = new ArrayList<Projectile>(); // Contains all projectiles shot from the weapon
	
	public ArrayList<AfterEffects> ae = new ArrayList<AfterEffects>();

	public EZGroup weaponGroup; // Group for the weapon

	public int triangleX; // Holds the x-length of the triangle formed for angle assistance
	public int triangleY; // Holds the y-length of the triangle formed for angle assistance

	public static final int LEFT = 0;
	public static final int RIGHT = 1;

	public int weaponState = 1; // Keeps track of the player's current state
	public int oldWeaponState = 0; // Assists in checking when the player's state changes

	public int currFrame = 0; // Keeps track of the current frame the image is on
	public long startAnimationTime; // Keeps track of the starting time of each frame

	public double projectileAngle; // Holds the angle the arrow is being fired at
	public double weaponAngle; // Holds to angle the bow should be at

	public int x, y; // Holds the x and y position of the weapon
	
	public EZSound sound;

	public Weapon(int xpos, int ypos, double size) {

		x = xpos;
		y = ypos;

		prepFiles();
		prepImages(x, y, size);
	}

	public void prepFiles() {}

	// Function to add the images to the group
	public void prepImages(int x, int y, double size) {

		weaponGroup = EZ.addGroup();

		// Add the images for the weapon when it is facing left
		for (int i = 0; i < weaponLeftFiles.size(); i++) {
			weaponLeft.add(EZ.addImage(weaponLeftFiles.get(i), x, y));
			weaponLeft.get(i).scaleTo(size);
			weaponLeft.get(i).hide();
			weaponGroup.addElement(weaponLeft.get(i));
		}

		// Add the images for the weapon when it is facing right
		for (int i = 0; i < weaponRightFiles.size(); i++) {
			weaponRight.add(EZ.addImage(weaponRightFiles.get(i), x, y));
			weaponRight.get(i).scaleTo(size);
			weaponRight.get(i).hide();
			weaponGroup.addElement(weaponRight.get(i));
		}
	}

	public void go(Map m) {}

	// Function for running the projectile shot from the weapon
	public void projectileTravel(Map m) {

		// For all existing arrows (except the 1st element)
		for (int i = 1; i < projectile.size(); i++) {

			// For all existing walls / platforms
			for (int j = 0; j < m.terrain.hiddenTerrain.size(); j++) {

				// Create an integer that holds the amount of walls / platforms there are in the game
				int numTerrain = 0;

				// If the arrow is not interacting with a particular terrain
				if (!projectile.get(i).isInterfered(m.terrain.hiddenTerrain)) {

					// Increment the terrain by 1
					numTerrain++;
				}

				// If the arrow is not interacting with all the terrain
				if (numTerrain == m.terrain.hiddenTerrain.size() && projectile.get(i).isActive) {
					// Call the arrow's motion function
					projectile.get(i).motionPlus(m);
				}

				else if (!projectile.get(i).addedToMap || !projectile.get(i).isActive){

					m.mapGroup.addElement(projectile.get(i).projectile);
					projectile.get(i).addedToMap = true;
					projectile.get(i).endMotionPlus();
					ae.add(new AfterEffects(projectile.get(i)));
					
					projectile.get(i).getClass();
					projectile.remove(i);
				}
			}
		}
		
		for (int i = 0; i < ae.size(); i++) {
			
			m.mapGroup.addElement(ae.get(i).effect);
			ae.get(i).destroyEffect();
		}
	}

	// For when the weapon's state changes
	public void stateChange() {

		// If the oldWeaponState is different from the current weaponState
		if (weaponState != oldWeaponState) {

			// Update the Weapon State
			oldWeaponState = weaponState;

			// Hide all images of Weapon first
			weaponGroup.hide();
			currFrame = 0;

			// Reset the start of the images
			startAnimationTime = System.currentTimeMillis();
		}
	}

	// Function for the horizontal movement of the player
	public void animateWeapon(ArrayList<EZImage> image, long dur) {

		// If the animation is starting for the first time this will run
		stateChange();
		image.get(currFrame).show();

		// Run when the frame has been shown for the correct duration
		if (System.currentTimeMillis() - startAnimationTime > dur) {

			// Cycle through the frame
			image.get(currFrame).hide();

			if (currFrame < image.size() - 1)
				currFrame++;

			image.get(currFrame).show();

			// Reset the timer
			startAnimationTime = System.currentTimeMillis();
		}
	}

	// Rotates the bow to the correct position
	public void facingDirection() {

		// Returns the x and y distance between the cursor and bow position to form a triangle for a rough angle calculation
		triangleX = EZInteraction.getXMouse() - x;
		triangleY = EZInteraction.getYMouse() - y;

		// Quadrants are taken in respect to where the positive x and y values are

		if (triangleX >= 1 && triangleY <= 1) { // Quadrant 4, Top Right

			// Calculations for the arrow angle and bow angle
			projectileAngle = Math.toDegrees(Math.atan2(triangleY, triangleX));
			weaponAngle = projectileAngle;
			projectileAngle = -projectileAngle;
		}

		else if (triangleX < 1 && triangleY < 1) { // Quadrant 3, Top Left

			// Calculations for the arrow angle and bow angle
			projectileAngle = Math.toDegrees(Math.atan2(triangleY, triangleX));
			weaponAngle = projectileAngle;
			projectileAngle = -projectileAngle;

		}

		else if (triangleX <= 1 && triangleY >= 1) { // Quadrant 2, Bottom Left

			// Calculations for the arrow angle and bow angle
			projectileAngle = Math.toDegrees(Math.atan2(triangleY, triangleX));
			weaponAngle = projectileAngle;
			projectileAngle = 180 - projectileAngle;
			projectileAngle += 180;
		}

		else if (triangleX > 1 && triangleY > 1) { // Quadrant 1, Bottom Right

			// Calculations for the arrow angle and bow angle
			projectileAngle = Math.toDegrees(Math.atan2(triangleY, triangleX));
			weaponAngle = projectileAngle;
			projectileAngle = 90 - projectileAngle;
			projectileAngle += 270;
		}

		for (int i = 0; i < weaponLeft.size(); i++) {

			weaponLeft.get(i).rotateTo(weaponAngle + 180);
			weaponRight.get(i).rotateTo(weaponAngle);
		}
	}
}
