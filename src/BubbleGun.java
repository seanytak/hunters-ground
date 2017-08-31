
/**
 * Bubble Gun Weapon. Most fun weapon
 * 
 * @author Sean Takafuji
 *
 */

public class BubbleGun extends Weapon {

	// Member Variables
	private static final int R = 120; // Length from center of weapon model to the tip of the bubble gun

	private static final double SIZE = 1;
	private static final double VELOCITY = 50; // Speed the bubble is traveling

	private long startTime = 0; // Variable for animations
	private long fireDuration = 50; // Variable for the duration the bubble gun firing animartion should last

	public BubbleGun(int x, int y) {
		super(x, y, SIZE);

		sound = EZ.addSound("Bubble_SFX.WAV");
	}

	// Prepare the image files
	public void prepFiles() {

		// Add the left files
		weaponLeftFiles.add("hunter_bubble_gun_left_0.png");

		// Add the right files
		weaponRightFiles.add("hunter_bubble_gun_right_0.png");

	}

	// Main function that is called in other classes
	public void go(Map m) {

		// Set the x and y position to the bubble gun's current position
		x = weaponGroup.getWorldXCenter();
		y = weaponGroup.getWorldYCenter();

		// Call the facing direction function
		facingDirection();

		// Shooting the bubble gun
		if (EZInteraction.isMouseLeftButtonDown() && System.currentTimeMillis() - startTime > fireDuration) {

			// Hide the weapon and shoot the bubble
			shootbubble();

			// The first projectile is abnormal so we hide it
			projectile.get(0).projectile.hide();
			
			// Start the animation time
			startTime = System.currentTimeMillis();
		}
		
		else {
			
			sound.stop();
		}
		
		// If facing right
		if (EZInteraction.getXMouse() >= x) {

			// Show default bubble gun facing right
			weaponLeft.get(0).hide();
			weaponRight.get(0).show();
		}

		// Else it must be left
		else {

			// Show default bubble gun facing left
			weaponRight.get(0).hide();
			weaponLeft.get(0).show();
		}

		// Allow the projectiles to travel
		projectileTravel(m);
	}

	// Since the bubble gun extends past the center of the character, the x is adjusted
	private double adjustX() {

		// Adjacent = Hypotenuse * cos(angle)
		return R * (Math.cos(Math.toRadians(projectileAngle)));
	}

	// Since the bubble gun extends past the center of the character, the y is adjusted
	private double adjustY() {

		// Opposite = Hypotenuse * sin(angle)
		return R * (Math.sin(Math.toRadians(projectileAngle)));
	}

	// Function for shooting the bubble from the bubble gun
	private void shootbubble() {

		// If the angle is in the first quadrant
		if (projectileAngle <= 90) {

			// Create a bubble to be shot
			projectile.add(new Bubble("bubble.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, RIGHT));

		}

		// If the angle is in the second quadrant
		else if (projectileAngle <= 180 && 90 < projectileAngle) {

			// Create a bubble to be shot
			projectile.add(new Bubble("bubble.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, LEFT));
		}
		
		// If the angle is in the third quadrant
		else if (projectileAngle <= 270 && 180 < projectileAngle) {

			// Create a bubble to be shot
			projectile.add(new Bubble("bubble.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, LEFT));
		}

		// If the angle is in the fourth quadrant
		else if (projectileAngle <= 360 && 270 < projectileAngle) {

			// Create a bubble to be shot
			projectile.add(new Bubble("bubble.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, RIGHT));
		}
		
		sound.play();
	}
}
