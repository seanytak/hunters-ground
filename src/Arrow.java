
/**
 * Arrow is a subclass of Projectile and utilizes an arrow projectile. Differences from the main projectile class are
 * the functions that align the arrow's trajectory to match the parabolic motion of projectiles.
 * 
 * 
 * @author Sean Takafuji
 *
 */

import java.awt.Color;

public class Arrow extends Projectile {

	public Arrow(String filename, int x, int y, double v, double ang, double scale, int fd) {
		
		super(filename, x, y, v, ang, scale, fd);
		
		damage = 300;
		projectileType = ARROW;
	}
	
	// Actual motion of the arrow
	public void motionPlus(Map m) {
		
		motion(m);
	}
	
	public void endMotionPlus() {
		
		endMotion();
	}
}
