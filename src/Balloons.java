
/**
 * Adds Balloons to the screen. Currently unused but possible feature
 * 
 * @author Sean Takafuji
 */

import java.awt.Color;
import java.util.Random;

public class Balloons {

	EZCircle balloon;
	Random rand;
	
	private Color[] colorList = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};
	
	public Balloons(int x, int y, int r) {
		
		rand = new Random();
		
		balloon = EZ.addCircle(x, y, r, r, randomColor(), true);
	}
	
	public Color randomColor() {
		
		// Generate a random integer (bounded by the length of colorList) to access a random color in colorList
		return colorList[rand.nextInt(colorList.length)];
	}
	
	public void point(int x, int y) {
		
		if (balloon.isPointInElement(x, y)) {
			
			balloon.hide();
		}
	}
}
