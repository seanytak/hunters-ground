
/**
 * Class to create the after effects of the various projectiles. We dispnese the projecctile after contact so
 * creating a second class was simpler
 * 
 * @author Sean Takafuji
 *
 */

public class AfterEffects {

	// Member Variables
	EZImage effect;

	private long startEffect;
	private long duration = 150;
	
	// Projectile (unusued but needed)
	public AfterEffects(Projectile p) {
		
		switch (p.projectileType) {
		
		case Projectile.ARROW: createEffect(p, "arrow_hit.png"); break;
		
		case Projectile.BULLET: createEffect(p, "hit.png"); break;
		
		case Projectile.ROCKET: createEffect(p, "explosion.png"); break;
		
		case Projectile.BUBBLE: createEffect(p, "bubble_hit.png"); break;
		}
	}

	// Projectile Effect
	private void createEffect(Projectile p, String filename) {

		effect = EZ.addImage(filename, (int) p.currX, (int) p.currY);
		startEffect = System.currentTimeMillis();
	}

	// Destroy the effect after duration
	public void destroyEffect() {

		if (System.currentTimeMillis() - startEffect > duration) {

			effect.hide();
		}
	}
}
