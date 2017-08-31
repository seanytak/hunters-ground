import java.util.ArrayList;

/**
 * Projectile for the rocket launcher
 * 
 * @author Sean Takafuji
 *
 */
public class Rocket extends Projectile {

	public Rocket(String filename, int x, int y, double v, double ang, double scale, int fd) {
		super(filename, x, y, v, ang, scale, fd);
		
		damage = 500;
		projectileType = ROCKET;
	}

	// Actual motion of the rocket
	public void motionPlus(Map m) {
		
		motion(m);
	}
	
	public void endMotionPlus() {
		
		explode();
		endMotion();
	}
	
	private void explode() {
		
		//explosion = EZ.addImage("explosion.png", (int) currX, (int) currY);
	}
}
