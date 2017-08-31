import java.awt.Color;
import java.util.ArrayList;

/**
 * Class to keep track of the point box
 * 
 * @author Sean Takafuji
 *
 */
public class Points {

	// Member Variables
	EZRectangle rect;
	EZImage pointBox;
	EZText healthText, pointText;
	
	int health, points;
	
	private long colorDuration = 300;
	private long startColor = 0;
	
	// Create the Point Box
	public Points(int x, int y) {
		
		pointBox = EZ.addImage("point_box.png", 7 * x / 8, y / 7);
		pointBox.scaleTo(0.5);
		healthText = EZ.addText("8-BIT WONDER.TTF", 7 * x / 8, y / 6, "Health    " + health, Color.RED, 25);
		pointText = EZ.addText("8-BIT WONDER.TTF", 7 * x / 8, y / 6 + y / 12, "Points    " + points, new Color(51, 102, 0), 25);
	}
	
	// Update the points
	public void modifyStats(ArrayList<Enemy> e, int h) {
		
		// For all enemies, check if dead and if the points have not been added yet
		for (int i = 0; i < e.size(); i++) {
			if (!e.get(i).isPointsAdded && e.get(i).enemyState == Enemy.DIE) {
				
				// Add the points and update that they have been added
				points += e.get(i).points;
				e.get(i).isPointsAdded = true;
			}
		}
		
		health = h;
		
		// Reset the message
		healthText.setMsg("Health     " + health);
		pointText.setMsg("Points     " + points);
	}
}
