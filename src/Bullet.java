
/**
 * Projectile for the gun
 * 
 * @author Sean Takafuji
 *
 */

public class Bullet extends Projectile {

	public Bullet(String filename, int x, int y, double v, double ang, double scale, int fd) {
		super(filename, x, y, v, ang, scale, fd);
		
		damage = 50;
		projectileType = BULLET;
	}

	// Actual motion of the bullet
	public void motionPlus(Map m) {
		
		motion(m);
	}
	
	public void endMotionPlus() {
		
		endMotion();
	}
}
