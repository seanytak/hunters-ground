import java.util.Random;

/**
 * A platform that can only last for so long. Different from terrain in some ways so a new class was made
 * 
 * @author Sean Takafuji
 *
 */
public class Platform {

	// Member variables
	EZImage platform;
	Random rg = new Random();

	int startTime = 0;
	int lifespan = 125;
	int fallSpeed = 10;
	
	int x, y;
	
 	String[] platformImages = {"platform_0.png", "platform_1.png", "platform_2.png"};
	
 	// Constructor for the platform
	public Platform(int xpos, int ypos) {

		x = xpos;
		y = ypos;
		
		drawPlatform(x, y);
	}

	// Create the platform
	private void drawPlatform(int x, int y) {

		platform = EZ.addImage(platformImages[rg.nextInt(3)], xRange(), yRange());
	}
	
	// Slight randomization on the x-coord
	private int xRange() {
		
		int xtemp;
		
		// Randomize until it is off the starting screen
		do {
			
			xtemp = rg.nextInt(x * 6) - x * 3;
			
		} while (x / 2 - x / 8 < xtemp && xtemp < x / 2 + x /8);
		
		return xtemp;
	}
	
	// Slight randomizaton on the y-coord
	private int yRange() {
		
		// High or low platform basically
		return (y / 3) * (rg.nextInt(2) + 1);
	}

	// Function that runs the life cycle of a platform
	public void breakPlatform(Player p) {
		
		// Set distance points from the center
		int partX = p.mainGroup.getWidth() / 4;
		int partY = p.mainGroup.getHeight() / 4;
		
		// Simplify the text needed to be typed
		int xpos = p.mainGroup.getWorldXCenter();
		int ypos = p.mainGroup.getWorldYCenter();

		// Check if any of the four corners of the player are touching the hidden ground
		if (platform.isPointInElement(xpos + partX, ypos + partY) ||
			platform.isPointInElement(xpos - partX, ypos + partY)) {

			lifespan -= 1;
			platform.translateBy(rg.nextInt(5) - 2, 0);
		}
		
		
		// If the lifespan is over drop the platform
		if (lifespan <= 0) {

			platform.translateBy(0, fallSpeed);
		}
	}
}
