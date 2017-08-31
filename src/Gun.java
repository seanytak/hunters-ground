
/**
 * This is a subclass of weapon for a gun. Currently needs to be updated and is no different from gun
 * 
 * @author Sean Takafuji
 */

import java.util.ArrayList;

public class Gun extends Weapon {

	private static final double R = 150;
	private static final double VELOCITY = 100; // Set speed the bullet fires
	private static final double SIZE = 1;

	private long startTime = 0;
	private long fireDuration = 50;
	


	public Gun(int x, int y){

		super(x, y, SIZE);
		
		sound = EZ.addSound("Gun_SFX.wav");
	}

	public void prepFiles() {

		weaponLeftFiles.add("hunter_gun_left_0.png");
		weaponLeftFiles.add("hunter_gun_left_1.png");

		weaponRightFiles.add("hunter_gun_right_0.png");
		weaponRightFiles.add("hunter_gun_right_1.png");
	}

	// Main function that is called in other classes
	public void go(Map m) {

		// Set the x and y position to the gun's current position
		x = weaponGroup.getWorldXCenter();
		y = weaponGroup.getWorldYCenter();

		// Call the facing direction function
		facingDirection();

		// Shooting the Gun
		if (EZInteraction.isMouseLeftButtonDown() && System.currentTimeMillis() - startTime > fireDuration) {

			// Hide the weapon and shoot the bullet
			shootBullet();

			if (EZInteraction.getXMouse() >= x) {

				// Show the gun shooting to the right
				weaponLeft.get(0).hide();
				weaponLeft.get(1).hide();
				weaponRight.get(0).hide();
				weaponRight.get(1).show();
			}

			else {

				// Show the gun shooting to the left
				weaponRight.get(0).hide();
				weaponRight.get(1).hide();
				weaponLeft.get(0).hide();
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

					// Show default gun facing right
					weaponRight.get(0).show();
				}

				// Else it must be left
				else {

					// Show default gun facing left
					weaponLeft.get(0).show();
				}
				
				sound.stop();
			}
		}
		projectileTravel(m);
	}

	// Since the gun extends past the center of the character, the x is adjusted
	private double adjustX() {

		// Adjacent = Hypotenuse * cos(angle)
		return R * (Math.cos(Math.toRadians(projectileAngle)));
	}

	// Since the gun extends past the center of the character, the y is adjusted
	private double adjustY() {

		// Opposite = Hypotenuse * sin(angle)
		return R * (Math.sin(Math.toRadians(projectileAngle)));
	}

	// Function for shooting the bullet from the gun
	private void shootBullet() {

		// If the angle is in the first quadrant
		if (projectileAngle <= 90) {

			// Create a bullet to be shot
			projectile.add(new Bullet("bullet_right.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, RIGHT));

		}

		// If the angle is in the second quadrant
		else if (projectileAngle <= 180 && 90 < projectileAngle) {

			// Create a bullet to be shot
			projectile.add(new Bullet("bullet_left.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, LEFT));
		}

		// If the angle is in the third quadrant
		else if (projectileAngle <= 270 && 180 < projectileAngle) {

			// Create a bullet to be shot
			projectile.add(new Bullet("bullet_left.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, LEFT));
		}

		// If the angle is in the fourth quadrant
		else if (projectileAngle <= 360 && 270 < projectileAngle) {

			// Create a bullet to be shot
			projectile.add(new Bullet("bullet_right.png", 
					(int) (x + adjustX()), (int) (y - adjustY()), VELOCITY, projectileAngle, 1, RIGHT));
		}
		
		sound.loop();
	}
}

