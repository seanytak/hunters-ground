
/**
 * Controls all floors and walls in the game
 * 
 * @author Sean Takafuji
 */


import java.awt.Color;
import java.util.ArrayList;

public class Terrain {

	// Arrays for the terrain
	ArrayList<EZImage> visibleTerrain = new ArrayList<EZImage>();
	ArrayList<EZRectangle> hiddenTerrain = new ArrayList<EZRectangle>();

	EZGroup vtGroup, htGroup; // Visible Terrain Group and Hidden Terrain Group

	// Terrain Constructor
	public Terrain() {

		vtGroup = EZ.addGroup();
		htGroup = EZ.addGroup();
	}

	// Draw terrain with an image
	public void drawTerrain(String filename, double x, double y, double w, double h) {

		// Draw the terrain
		visibleTerrain.add(EZ.addImage(filename, (int) x, (int) y));
		hiddenTerrain.add(EZ.addRectangle((int) x, (int) y, (int) w, (int) h, Color.BLACK, true));
		
		// Add the terrain to their respective groups
		vtGroup.addElement(visibleTerrain.get(visibleTerrain.size() - 1));
		htGroup.addElement(hiddenTerrain.get(hiddenTerrain.size() - 1));
	}
	
	// Draw terrain without an image
	public void drawTerrain(double x, double y, double w, double h) {

		// Draw the terrain
		hiddenTerrain.add(EZ.addRectangle((int) x, (int) y, (int) w, (int) h, Color.BLACK, true));
		
		// Add the terrain to their respective groups
		htGroup.addElement(hiddenTerrain.get(hiddenTerrain.size() - 1));
	}
	
	// If anyone interacts with the terrain (currently unused)
	public boolean contact(int x, int y) {

		for (int i = 1; i < hiddenTerrain.size(); i++) {

			if (hiddenTerrain.get(i).isPointInElement(x, y)) {

				return true;
			}
		}

		return false;
	}
}
