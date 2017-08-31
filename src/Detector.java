import java.awt.Color;
import java.util.ArrayList;

/**
 * Class to allow the enemies to switch to attack mode. Just a rectangle that stays with the player. Only other part
 * not part of the player that doesnt move with the map
 * 
 * @author Sean Takafuji
 *
 */
public class Detector {

	private int x, y;
	private EZRectangle detector;
	
	public Detector(int x, int y) {
		
		detector = EZ.addRectangle(x / 2, y / 2, 3 * x / 5, y, Color.BLACK, true);
		detector.pushToBack();
	}
	
	int count = 0;
	
	public void detect(ArrayList<Enemy> e) {
		
		for (int i = 0; i < e.size(); i++) {
			
			if (detector.isPointInElement(e.get(i).enemyGroup.getWorldXCenter(), e.get(i).enemyGroup.getWorldYCenter())) {
				
				e.get(i).isAttacking = true;
			}
			
			else {
				
				e.get(i).isAttacking = false;
			}
		}
	}
}
