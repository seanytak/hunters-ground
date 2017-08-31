
/**
 * This is a subclass of Weapon and utilizes a bow as the weapon. Differences mainly include the drawing 
 * of the bow to shoot. 
 * 
 * @author Sean Takafuji
 */

import java.util.ArrayList;
import java.util.Random;

public class Bow extends Weapon {

	private long startDraw; // Holds the time for the last drawSpeed update
	private long drawSpeed = 1; // Holds the value for the drawSpeed
	private long drawUpdate = 200; // Holds the duration for each draw update
	private long maxDraw = 4; // Holds the max draw updates allowed

	private static final double SIZE = 1;

	public Bow(int x, int y){

		super(x, y, SIZE);
		sound = EZ.addSound("Arrow_SFX.wav");
	}

	// Prepare the bow image files
	public void prepFiles() {

		weaponLeftFiles.add("hunter_bow_left_0.png");
		weaponLeftFiles.add("hunter_bow_left_1.png");
		weaponLeftFiles.add("hunter_bow_left_2.png");
		weaponLeftFiles.add("hunter_bow_left_3.png");

		weaponRightFiles.add("hunter_bow_right_0.png");
		weaponRightFiles.add("hunter_bow_right_1.png");
		weaponRightFiles.add("hunter_bow_right_2.png");
		weaponRightFiles.add("hunter_bow_right_3.png");
	}

	// Main function that is called in other classes
	public void go(Map m) {

		// Set the x and y position to the bow's current position
		x = weaponGroup.getWorldXCenter();
		y = weaponGroup.getWorldYCenter();

		// Call the facing direction function
		facingDirection();

		// Shooting the Bow
		if (EZInteraction.isMouseLeftButtonDown()) { // While drawing

			drawBow(); // Call the draw function

			if (EZInteraction.getXMouse() >= x) {

				for (int i = 0; i < weaponLeft.size(); i++) {

					weaponLeft.get(i).hide();
				}

				animateWeapon(weaponRight, drawUpdate);
			}

			else {

				for (int i = 0; i < weaponRight.size(); i++) {

					weaponRight.get(i).hide();
				}

				animateWeapon(weaponLeft, drawUpdate);
			}
		}

		else if (EZInteraction.wasMouseLeftButtonReleased()) { // Releasing the bow

			shootArrow(m); // Call the shoot arrow function
			drawSpeed = 1; // Reset the drawSpeed to 1
		}

		else { // No bow activity

			weaponGroup.hide();

			startDraw = 0;
			currFrame = 0;

			if (EZInteraction.getXMouse() >= x) {

				weaponRight.get(0).show();
			}

			else {

				weaponLeft.get(0).show();
			}
		}

		projectileTravel(m);

		// As a result of clicking the play button, EZ registers one released click generating an arrow without any velocity
		// Since this arrow behaves abnormally, we hide it
		projectile.get(0).projectile.hide();


	}

	// Function for drawing the bow (pulling the bow sting)
	private void drawBow() {

		// Run every 200 ms from the last draw update
		if (System.currentTimeMillis() - startDraw >= drawUpdate) {

			// Reset the start draw time
			startDraw = System.currentTimeMillis();

			// Increment the draw speed by 1
			drawSpeed++;

			// If the draw speed exceeds 4 decrement it
			if (drawSpeed > maxDraw) {

				// Decrement the draw speed by 1
				drawSpeed--;
			}
		}
	}

	// Function for shooting the arrow from the bow
	private void shootArrow(Map m) {

		// If the angle is for the right side of the bow
		if (projectileAngle <= 90 || projectileAngle > 270) {

			// Create an arrow to be shot to the right
			projectile.add(new Arrow("arrow_right.png", x, y, drawSpeed * 15, projectileAngle, 1, 1));

		} 

		// If the angle is for the left side of the bow
		else {

			// Create an arrow to be shot to the left
			projectile.add(new Arrow("arrow_left.png", x, y, drawSpeed * 15, projectileAngle, 1, 0));
		}
		
		sound.play();
	}
}
