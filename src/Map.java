import java.awt.Color;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Build the Map for the game
 * 
 * @author Sean Takafuji
 *
 */
public class Map extends Background {

	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	ArrayList<Platform> platforms = new ArrayList<Platform>();

	EZGroup mapGroup, terrainGroup, enemyGroup, platformGroup;
	Terrain terrain;
	TRex trex1, trex2, trex3;
	Pterodactyl ptero1, ptero2, ptero3;
	Scanner fs;

	int x, y;

	static int platformCount;
	static int trexCount;
	static int pterodactylCount;

	// Constructor to build the map
	public Map(int xpos, int ypos, int amt) {
		super(xpos, ypos, amt);

		try {

			fs = new Scanner(new FileReader("mapOptions.txt"));

			platformCount = fs.nextInt();
			trexCount = fs.nextInt();
			Timer.duration = fs.nextLong() * 1000;

		} catch (Exception e) {}
		terrain = new Terrain();

		x = xpos;
		y = ypos;

		buildGroups();
		buildMap();
	}

	// Main function for the map
	public void go(Player p) {

		// Update the trex
		for (int i = 0; i < enemies.size(); i++) {

			enemies.get(i).states(p.playerWeapon.projectile);

			if (enemies.get(i).isDespawned) {
				enemies.remove(i);
			}

		}

		// Update the platforms
		for (int i = 0; i < platforms.size(); i++) {

			platforms.get(i).breakPlatform(p);

			if (platforms.get(i).platform.getWorldYCenter() - platforms.get(i).platform.getHeight() > y) {

				platforms.remove(i);
			}
		}

		// Hide backgrounds not within the visible screen
		hideBackground();
	}

	// Function that moves the map to make it appear as if the player is moving
	public void move(float x, float y) {

		mapGroup.translateBy(x, y);
	}

	// Add all the groups to mapGroup
	private void buildGroups() {

		mapGroup = EZ.addGroup();
		enemyGroup = EZ.addGroup();
		platformGroup = EZ.addGroup();

		mapGroup.addElement(enemyGroup);
		mapGroup.addElement(terrain.vtGroup);
		mapGroup.addElement(terrain.htGroup);
		mapGroup.addElement(backgroundGroup);
		mapGroup.addElement(platformGroup);
	}

	// Build the various elements of the map
	private void buildMap() {

		setEnemies();
		setTerrain();
		setLayers();
	}

	// Build the terrain for the map
	private void setTerrain() {

		terrain.drawTerrain(x / 2, (53 * y) / 60, x * 100, y / 10);

		for (int i = 0; i < platformCount; i++) {

			platforms.add(new Platform(x, y));
			platformGroup.addElement(platforms.get(i).platform);
		}
	}

	// Arrange the layers for the map
	private void setLayers() {

		terrain.htGroup.pushToBack();
		backgroundGroup.pullToFront();
		terrain.vtGroup.pullToFront();
		platformGroup.pullToFront();
		enemyGroup.pullToFront();
	}

	// Build the enemies for the map
	private void setEnemies() {

		for (int i = 0; i < trexCount; i++) {

			enemies.add(new TRex());
			enemyGroup.addElement(enemies.get(i).enemyGroup);
		}
	}
}
