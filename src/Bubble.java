
/**
 * Bubble Projectile
 * 
 * @author Sean Takafuji
 *
 */
public class Bubble extends Projectile {

	private static final double SIZE = 1;
	
	public Bubble(String filename, int x, int y, double v, double ang, double scale, int fd) {
		super(filename, x, y, v, ang, SIZE, fd);

		damage = 100;
		projectileType = BUBBLE;
	}

	// Actual motion of the bubble
	public void motionPlus(Map m) {
		
		motion(m);
	}
	
	// When the projectile is no longer moving
	public void endMotionPlus() {
		
		endMotion();
	}
}
