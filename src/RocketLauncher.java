import java.awt.Color;

/**
 * Class for the rocket launcher
 * 
 * 
 * @author Sean Takafuji
 *
 */
public class RocketLauncher extends Weapon {

	// Member Variables
	private static final int R = 100; // Length from center of weapon model to the tip of the rocket launcher

	private static final double SIZE = 1;
	private static final double VELOCITY = 50; // Speed the rocket is traveling

	private long startTime = 0; // Variable for animations
	private long fireDuration = 500; // Variable for the duration the rocket launcher firing animartion should last
	
	// Constructor
	public RocketLauncher(int x, int y) {
		super(x, y, SIZE);
		sound = EZ.addSound("Rocket_SFX.wav");
	}

	// Prepare the image files
	public void prepFiles() {

		// Add the left files
		weaponLeftFiles.add("hunter_rocket_launcher_left_0.png");
		weaponLeftFiles.add("hunter_rocket_launcher_left_1.png");

		// Add the right files
		weaponRightFiles.add("hunter_rocket_launcher_right_0.png");
		weaponRightFiles.add("hunter_rocket_launcher_right_1.png");
	}

	// Main function that is called in other classes
	public void go(Map m) {

		// Set the x and y position to the rocket launcher's current position
		x = weaponGroup.getWorldXCenter();
		y = weaponGroup.getWorldYCenter();

		// Call the facing direction function
		facingDirection();

		// Shooting the Rocket Launcher
		if (EZInteraction.wasMouseLeftButtonPressed() && System.currentTimeMillis() - startTime > fireDuration) {

			// Hide the weapon and shoot the rocket
			weaponGroup.hide();
			shootRocket();

			if (EZInteraction.getXMouse() >= x) {

				// Show the rocket launcher shooting to the right
				weaponRight.get(1).show();
			}

			else {

				// Show the rocket launcher shooting to the left
				weaponLeft.get(1).show();
			}

			// The first projectile is abnormal so we hide it
			projectile.get(0).projectile.hide();
			
			// Start the animation time
			startTime = System.currentTimeMillis();
		}

		else { // No weapon activity

			// Once the animation is done
			if (System.currentTimeMillis() - startTime > fireDuration) {
				
				// Hide the weapon
				weaponGroup.hide();
				
				// If facing right
				if (EZInteraction.getXMouse() >= x) {

					// Show default rocket launcher facing right
					weaponRight.get(0).show();
				}

				// Else it must be left
				else {

					// Show default rocket launcher facing left
					weaponLeft.get(0).show();
				}
			}
		}

		// Allow the projectiles to travel
		projectileTravel(m);
	}

	// Since the rocket launcher extends past the center of the character, the x is adjusted
	private double adjustX() {

		// Adjacent = Hypotenuse * cos(angle)
		return R * (Math.cos(Math.toRadians(projectileAngle)));
	}

	// Since the rocket launcher extends past the center of the character, the y is adjusted
	private double adjustY() {

		// Opposite = Hypotenuse * sin(angle)
		return R * (Math.sin(Math.toRadians(projectileAngle)));
	}

	// Function for shooting the rocket from the rocket launcher
	private void shootRocket() {

		// If the angle is in the first quadrant
		if (projectileAngle <= 90) {

			// Create a rocket to be shot
			projectile.add(new Rocket("rocket_right.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, RIGHT));

		}

		// If the angle is in the second quadrant
		else if (projectileAngle <= 180 && 90 < projectileAngle) {

			// Create a rocket to be shot
			projectile.add(new Rocket("rocket_left.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, LEFT));
		}
		
		// If the angle is in the third quadrant
		else if (projectileAngle <= 270 && 180 < projectileAngle) {

			// Create a rocket to be shot
			projectile.add(new Rocket("rocket_left.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, LEFT));
		}

		// If the angle is in the fourth quadrant
		else if (projectileAngle <= 360 && 270 < projectileAngle) {

			// Create a rocket to be shot
			projectile.add(new Rocket("rocket_right.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, RIGHT));
		}
		
		sound.play();
	}
}
