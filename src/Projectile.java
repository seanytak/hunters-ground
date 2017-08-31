
import java.util.ArrayList;

/**
 * Projectile is the superclass for all projectile related objects. The program uses real projectile motion equations
 * for accurate real-time motion. 
 * 
 * @author Sean Takafuji
 *
 */

public class Projectile {

	// Projectile Variables
	public EZImage projectile; // Image of the projectile

	// Projectile Equation Variables
	public double velocity; // The initial velocity of the projectile
	public double degreeAngle; // The angle of the projectile in degrees
	public double radianAngle; // The angle of the projectile in radians

	// Location Variables
	public double x, y; // Original location of projectile
	public double adjustX, adjustY; // Current location of projectile within the projectile motion equation
	public double currX, currY; // Actual current location of projectile
	public double peakX, peakY; // The calculated halfway point in the projectile motion equation
	public double nextX, nextY; // The "next" point in the projectile motion equation used to calculate the projectile's trajectory if necessary

	// Time Variables
	public double normTime = 0; // The interpolated time
	public double nextInterval = 0.001; // The time difference between curr and next variables in their location in the projectile motion equation
	public double duration; // Duration of projectile before going lower than the initial launch height
	public double startTime; // Holds the starting time of the projectile's launch
	public double animationSpeed = 100; // 1000 is normal speed, less is faster

	// Image Variables
	public double scaleSize = 1; // Adjust the size of the image
	public double imageAdjustment = -20 * scaleSize; // Adjusts the projectile's image since scaling doesn't do it properly

	public int facingDirection; // Left = 0, Right = 1

	public boolean interfered; // Holds whether or not the projectile has interacted with something that would interfere it
	public boolean addedToMap = false;
	
	// Trajectory Variables
	public double angleX, angleY;
	public double currRadianAngle, currDegreeAngle;

	// Constants
	public final static double GRAVITY = 9.8; // Acceleration of gravity at 9.8 m/s^2
	public final static double SCALE_METERS = 5; // The pixel to meter ratio. 1 meter = whatever pixels it's set to
	public final static long LIFE_SPAN = 10000; // How long a projectile exists in ms
	
	// Projectile Types
	public int projectileType;
	public final static int ARROW = 0;
	public final static int BULLET = 1;
	public final static int ROCKET = 2;
	public final static int BUBBLE = 3;
	
	// Game Variables
	public boolean isActive = true;
	public int damage;

	// Constructor for the projectile
	public Projectile(String filename, int x, int y, double v, double ang, double scale, int fd) {

		// Set up the image
		projectile = EZ.addImage(filename, x, y);
		scaleSize = scale;
		facingDirection = fd;
		projectile.scaleBy(scale);

		// Take the parameters and input it into the member variables
		this.x = x;
		this.y = y;

		// Projectile motion parameters inputted into member variables
		velocity = v;
		degreeAngle = ang;
		radianAngle = Math.toRadians(ang);

		setProjectile(v, ang);
	}

	// Get the x-position of the projectile (simpler to type)
	public int getX() {

		return projectile.getWorldXCenter();
	}

	// Get the y-position of the projectile (simpler to type)
	public int getY() {

		return projectile.getWorldYCenter();
	}

	// Get the x-velocity
	public double velocityX(double v, double ang) {

		return (v * Math.cos(ang));
	}

	// Get the initial y-velocity
	public double initialVelocityY(double v, double ang) {
		
		return (v * Math.sin(ang));
	}

	// Get the final y-velcoity
	public double finalVelocityY(double v) {

		return (v * -1);
	}

	// Calcuate the time of the projectile in it's regular flight
	public double time(double v, double ang) {

		return ((finalVelocityY(initialVelocityY(v, ang)) - initialVelocityY(v, ang)) / (-1 * GRAVITY));
	}

	// Calculate the x-position of the projectile with respect to time
	public double projX(double v, double ang, double t) {

		return (SCALE_METERS * (velocityX(v, ang) * t));
	}

	// Calculate the y-position of the projectile with respect to time
	public double projY(double v, double ang, double t) {

		return (SCALE_METERS * (initialVelocityY(v, ang) * t + 0.5 * (-1 * GRAVITY) * t * t));
	}

	// Prepare the projectile's flight course
	public void setProjectile(double v, double ang) {

		// Time of projectile course
		startTime = System.currentTimeMillis();
		
		duration = time(velocity, radianAngle);

		// Calculate the X for the peak of its course
		peakX = x + projX(velocity, radianAngle, duration / 2);
		peakY = y - projY(velocity, radianAngle, duration / 2);

		interfered = false;
	}

	// Execute the projectile's motion using all the calculations from above
	public void projectileCourse(Map m) {
		
		// Runs while the projectile is not interfered (by an object or terrain)
		if (!interfered) {

			// Calculate the current time of the projectile's course
			normTime = (System.currentTimeMillis() - startTime) / animationSpeed;

			// Calculate the position of the projectile on its course at the current time
			adjustX = projX(velocity, radianAngle, normTime);
			adjustY = projY(velocity, radianAngle, normTime);

			// Calcuate the future position of the projectile on its course to create a triangular approximation for trajectory
			nextX = x + projX(velocity, radianAngle, normTime + nextInterval);
			nextY = y - projY(velocity, radianAngle, normTime + nextInterval);

			// Get the current position of the projectile
			currX = x + adjustX;
			currY = y - adjustY;

			// Moving the projectile
			projectile.translateTo(currX, currY);
		}
	}
	
	// Determines whether the projectile is interfered by something
	public boolean isInterfered(ArrayList<EZRectangle> ground) {

		// Temporary variables to create rectangular positions
		int partX = projectile.getWidth() / 4;
		int partY = projectile.getHeight() / 5;

		// Check if the projectile is interacting with any objects
		for (int i = 0; i < ground.size(); i++) {
			
			/*if (ground.get(i).isPointInElement(getX() + partX, getY() + partY) || 
				ground.get(i).isPointInElement(getX() + partX, getY() - partY) ||
				ground.get(i).isPointInElement(getX() - partX, getY() + partY) ||
				ground.get(i).isPointInElement(getX() - partX, getY() - partY)) {

				// Return true if the projectile is interfered
				return true;
			}*/
			
			if (ground.get(i).isPointInElement(getX(), getY() - partY)) {
				
				return true;
			}

		}
		
		// Return false if the projectile is not interfered
		return false;
	}
	
	// Remove the projectile after a certain period of time (clear the space on the screen)
	public void projectileLife() {
		
		// While the projectile should still be shown
		if (LIFE_SPAN < System.currentTimeMillis() - startTime) {
			
			// Hide the projectile
			projectile.hide();
		}
	}
	
	// Calculate the angle of the arrow in the projectile motion
	private void trajectory() {

		// If the current x-position is less than the position of the peak x-position
		if (currX < peakX) {

			// If the "next" x-positiion is less than the position of the peak x-position
			if (nextX < peakX) {
				
				// Get the absolute value of the triangle position that will be formed
				angleX = Math.abs(nextX - currX);
				angleY = Math.abs(currY - nextY);
			}
			
			// Calculate the angle of the arrow's path in both radians and degrees
			currRadianAngle = Math.atan2(angleY, angleX);
			currDegreeAngle = Math.toDegrees(currRadianAngle);
			
			// Rotate the arrow
			projectile.rotateTo(-currDegreeAngle);
		}

		// If the current x-position is greater than the position of the peak x-position
		if (currX > peakX) {

			// If the "next" x-positiion is greater than the position of the peak x-position
			if (nextX > peakX) {
				
				// Get the absolute value of the triangle position that will be formed
				angleX = Math.abs(currX - nextX);
				angleY = Math.abs(currY - nextY);
			}

			// Calculate the angle of the arrow's path in both radians and degrees
			currRadianAngle = Math.atan2(angleY, angleX);
			currDegreeAngle = Math.toDegrees(currRadianAngle);
			
			// Rotate the arrow
			projectile.rotateTo(currDegreeAngle);
		}
	}

	// Standard projectile motion execution
	public void motion(Map m) {

		projectileCourse(m);
		trajectory();
	}
	
	// For Polymorphism purposes
	public void motionPlus(Map m) {}
	
	// For Polymorphism purposes
	public void endMotion() {
		
		isActive = false;
		projectile.hide();
	}
	
	public void endMotionPlus() {}
}
