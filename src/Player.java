import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class that handles just about everything for the Player
 * 
 * @author Sean Takafuji
 *
 */
public class Player {

	// File Scanner
	protected Scanner fs;
	
	// Image File Arrays
	protected ArrayList<String> moveLeftFiles = new ArrayList<String>();
	protected ArrayList<String> moveRightFiles = new ArrayList<String>();
	protected ArrayList<String> jumpLeftFiles = new ArrayList<String>();
	protected ArrayList<String> jumpRightFiles = new ArrayList<String>();
	protected ArrayList<String> deadFiles = new ArrayList<String>();

	// Image Arrays
	private ArrayList<EZImage> moveImages = new ArrayList<EZImage>();
	private ArrayList<EZImage> jumpImages = new ArrayList<EZImage>();
	private ArrayList<EZImage> deadImages = new ArrayList<EZImage>();

	// Groups and Weapon for player
	protected EZGroup mainGroup, playerGroup;
	protected Weapon playerWeapon;

	// Weapon Variables
	public static final int BOW = 0;
	public static final int GUN = 1;
	public static final int ROCKET_LAUNCHER = 2;
	public static final int BUBBLE_GUN = 3;

	public static int selectedWeapon = -1;

	// Image adjuster (usually modified in a subclass)
	protected double scaleSize = 1;

	// State Variables
	private int currState = MOVE;
	private int oldState = MOVE;

	private static final int MOVE = 0;
	private static final int JUMP = 1;
	private static final int FALL = 2;
	private static final int DEAD = 3;

	// Direction Variables
	private int direction = LEFT;
	private int oldDirection = LEFT;

	private static final int LEFT = 0;
	private static final int RIGHT = 1;

	// Animation Variables
	private int currFrame = 0;
	private long startAnimationTime = 0;
	private long startJumpTime = 0;
	private long startDamageTime = 0;

	// Game Variables
	public static int health;
	private long iFrames = 1500;

	// Constructor
	public Player(int xpos, int ypos) {

		try {
			
			fs = new Scanner(new FileReader("playerOptions.txt"));
			health = fs.nextInt();
		
		} catch (Exception e) {}
		
		mainGroup = EZ.addGroup();
		playerGroup = EZ.addGroup();
		mainGroup.addElement(playerGroup);

		getImageFiles();
		setImageFiles(xpos, ypos);
		setWeapon(xpos, ypos);
	}

	// Placeholder function for subclasses
	protected void getImageFiles() {}

	private void setImageFiles(int x, int y) {

		int size = moveLeftFiles.size() + moveRightFiles.size();

		// Add the move images with left = even and right = odd
		for (int i = 0; i < size; i++) {

			if (i % 2 == 0) {

				moveImages.add(EZ.addImage(moveLeftFiles.get(0), x, y));
				moveLeftFiles.remove(0);
			}

			else {

				moveImages.add(EZ.addImage(moveRightFiles.get(0), x, y));
				moveRightFiles.remove(0);
			}

			// Scale, Hide, and add Image to the Player Group
			moveImages.get(i).scaleTo(scaleSize);
			moveImages.get(i).hide();
			playerGroup.addElement(moveImages.get(i));
		}

		size = jumpLeftFiles.size() + jumpRightFiles.size();

		// Add the jump images with left = even and right = odd
		for (int i = 0; i < size; i++) {

			// If even add left image
			if (i % 2 == 0) {

				jumpImages.add(EZ.addImage(jumpLeftFiles.get(0), x, y));
				jumpLeftFiles.remove(0);
			}

			// Else  add right image
			else {

				jumpImages.add(EZ.addImage(jumpRightFiles.get(0), x, y));
				jumpRightFiles.remove(0);
			}

			// Scale, Hide, and add Image to the Player Group
			jumpImages.get(i).scaleTo(scaleSize);
			jumpImages.get(i).hide();
			playerGroup.addElement(jumpImages.get(i));
		}

		// Add the death images
		deadImages.add(EZ.addImage(deadFiles.get(0), x, y));
		deadImages.get(0).hide();
		playerGroup.addElement(deadImages.get(0));
	}

	// Select the weapon the hunter will be using
	protected void setWeapon(int x, int y) {

		switch (selectedWeapon) {

		case BOW: playerWeapon = new Bow(x, y); break;

		case GUN: playerWeapon = new Gun(x, y); break;

		case ROCKET_LAUNCHER: playerWeapon = new RocketLauncher(x, y); break;

		case BUBBLE_GUN: playerWeapon = new BubbleGun(x, y); break;
		}
	}

	// Main function of the player that handles all the player's actions
	public void go(Map m, int x, int y) {

		// Normalize the speed per the screen size and frame rate
		float moveSpeed = getMoveSpeed(x);
		float jumpSpeed = getJumpSpeed(y);

		facingDirection(mainGroup.getWorldXCenter());
		states(m, moveSpeed, jumpSpeed);
	}

	// Function that processes the varying states of the player
	private void states(Map m, float xspeed, float yspeed) {

		extraState(m);

		// Call the proper state function based on what state the player is in
		switch(currState) {

		case MOVE: // Move State

			moveState(m, xspeed);

			break;

		case JUMP: // Jump State

			jumpState(m, xspeed, yspeed);

			break;

		case FALL: // Fall State

			fallState(m, xspeed, yspeed);

			break;

		case DEAD: // Death State

			deadState(m, yspeed);

			break;
		}
	}

	// Function while processing the move state
	private void moveState(Map m, float xspeed) {

		if (Controls.jump()) // If Jump Key is down, switch to jump state
			currState = JUMP;

		else if (Controls.moveLeft()) // If Move Left is down, animate movement to the left
			animateMove(m, -xspeed);

		else if (Controls.moveRight()) // If Move Right is down, animate movement to the right
			animateMove(m, xspeed);

		else  // Else, idle
			idle();

		// If the player is in groundContact with any of the terrain, switch to fall
		if (!groundContact(m)) 
			currState = FALL;
	}

	// Function while processing the jump state
	private void jumpState(Map m, float xspeed, float yspeed) {

		float xjspeed = -xspeed; // xspeed during jump (reversed since it is going into map)
		xjspeed = (xjspeed * 2) / 3;

		if (Controls.moveLeft()) { // If Move Left is down, move left

			if (direction == RIGHT) { // If facing opposite direction half the speed

				xjspeed /= 2;
			}

			m.move(-xjspeed, 0);
		}

		else if (Controls.moveRight()) { // If Move Right is down, move right

			if (direction == LEFT) { // If facing opposite direction half the speed

				xjspeed /= 2;
			}

			m.move(xjspeed, 0);
		}

		// If the difference between start time and current time is less than the duration of a jump
		if (System.currentTimeMillis() - startJumpTime < airTime()) 
			animateJump(yspeed);

		// When the difference exceeds the duration of a jump, switch to FALL
		else 
			currState = FALL;
	}

	// Function while processing the fall state
	private void fallState(Map m, float xspeed, float yspeed) {

		float xfspeed = -xspeed;
		xfspeed = (xfspeed * 2) / 3;

		if (Controls.moveLeft()) { // If Move Left is down, move left

			if (direction == RIGHT) { // If facing opposite direction half the speed

				xfspeed /= 2;
			}

			m.move(-xfspeed, 0);
		}

		else if (Controls.moveRight()) { // If Move Right is down, move right

			if (direction == LEFT) { // If facing opposite direction half the speed

				xfspeed /= 2;
			}

			xfspeed = (xfspeed * 2) / 3;

			m.move(xfspeed, 0);
		}

		// Animate falling while facing to the left
		animateJump((float) (-yspeed * 1.5));

		// If the player is in groundContact with any of the terrain, switch to MOVE
		if (groundContact(m)) 
			currState = MOVE;
	}

	// Function for when the player dies
	private void deadState(Map m, float yspeed) {

		// Hide all the images and show the death image
		mainGroup.hide();
		deadImages.get(0).show();

		// Make the player fall he hits the ground
		if (!groundContact(m)) {

			mainGroup.translateBy(0,(float) (yspeed * 1.5));
		}
	}

	// Extra State handling function
	private void extraState(Map m) {

		// If the state is not jump, update the jump timer
		if (currState != JUMP) {

			airChange();
		}

		// If the player is not dead, run the wepon
		if (currState != DEAD) {

			playerWeapon.go(m);
			enemyContact(m);
		}

		// If the player is dead, update the state
		if (health <= 0) {

			currState = DEAD;
		}
	}

	// Float for x-distance traveled per tick
	protected float getMoveSpeed(float x) {

		return x / 200;
	}

	// Float for y-distance traveled per tick
	protected float getJumpSpeed(float y) {

		return y / 80;
	}

	// Long for duration the player should be in the air during a jump
	protected long airTime() {

		return 350;
	}

	// Long for the duration each move frame should last
	protected long moveFrameDuration() {

		return 50;
	}

	// Long for the duration each jump frame should last
	protected long jumpFrameDuration() {

		return 50;
	}

	// For when the player's state changes
	private void stateChange() {

		// If the player's state or direction changed
		if (currState != oldState || direction != oldDirection) {

			// Update the Player State
			oldState = currState;
			oldDirection = direction;

			// Hide all images of Player first
			playerGroup.hide();
			currFrame = direction;

			// Reset the start of the images
			startAnimationTime = System.currentTimeMillis();
		}
	}

	// Updates the starting jump time so when the jump state is called it is ready
	private void airChange() {

		startJumpTime = System.currentTimeMillis();
	}

	// For when a player's facing direction changes
	private void facingDirection(double x) {

		if (EZInteraction.getXMouse() >= x) { // If the mouse is on the right side of the player
			if (direction != RIGHT) { // Updates direction only if it changed

				direction = RIGHT; // Change the facing direction to right
			}
		}

		else { // Otherwise, the mouse must be on the left side of the player
			if (direction != LEFT) { // Updates direction only if it changed

				direction = LEFT; // Changes the facing direction to left
			}
		}
	}

	// Main animation function for the varying states
	private void animate(ArrayList<EZImage> image, long dur) {

		// Smoothens animation state changes
		stateChange();
		image.get(currFrame).show();

		// Run when the frame has been shown for the correct duration
		if (System.currentTimeMillis() - startAnimationTime > dur) 
		{
			// Cycle through the frames
			image.get(currFrame).hide();
			currFrame = (currFrame + 2) % image.size();
			image.get(currFrame).show();

			// Reset the timer
			startAnimationTime = System.currentTimeMillis();
		}
	}

	// Function for the horizontal movement of the player
	private void animateMove(Map m, float speed) {

		m.move(-speed, 0);

		animate(moveImages, moveFrameDuration());
	}

	// Function for the vertical movement of the player
	private void animateJump(float speed) {

		mainGroup.translateBy(0, -speed);

		animate(jumpImages, jumpFrameDuration());
	}

	// Function for when there are no player inputs
	private void idle() {

		playerGroup.hide();
		moveImages.get(direction).show();
	}

	// Function to determine whether the player is groundContacting any of the ground elements of the map
	boolean groundContact(Map m) {

		// Set distance points from the center
		int partX = mainGroup.getWidth() / 4;
		int partY = mainGroup.getHeight() / 5;

		// Check for all hidden ground objects
		for (int i = 0; i < m.terrain.hiddenTerrain.size(); i++) {

			// Simplify the text needed to be typed
			int xpos = mainGroup.getWorldXCenter();
			int ypos = mainGroup.getWorldYCenter();

			// Check if any of the four corners of the player are touching the hidden ground
			if (m.terrain.hiddenTerrain.get(i).isPointInElement(xpos + partX, ypos + partY) || 
					m.terrain.hiddenTerrain.get(i).isPointInElement(xpos + partX, ypos - partY) ||
					m.terrain.hiddenTerrain.get(i).isPointInElement(xpos - partX, ypos + partY) ||
					m.terrain.hiddenTerrain.get(i).isPointInElement(xpos - partX, ypos - partY)) {

				// Return true if the player is in contact with the ground
				return true;
			}
		}

		// Check for all platforms
		for (int i = 0; i < m.platforms.size(); i++) {

			// Simplify the text needed to be typed
			int xpos = mainGroup.getWorldXCenter();
			int ypos = mainGroup.getWorldYCenter();

			partX = mainGroup.getWidth() / 6;
			partY = mainGroup.getHeight() / 4;

			// Check if any of the four corners of the player are touching the hidden ground
			if (m.platforms.get(i).platform.isPointInElement(xpos + partX, ypos + partY) ||
					m.platforms.get(i).platform.isPointInElement(xpos - partX, ypos + partY)) {

				// Return true if the player is in contact with the ground
				return true;
			}
		}

		// Otherwise return false
		return false;
	}

	// Function to see if player has made contact with any enemies
	private void enemyContact(Map m) {

		// Set distance points from the center
		int partX = mainGroup.getWidth() / 4;
		int partY = mainGroup.getHeight() / 5;

		// Check for all enemies
		for (int i = 0; i < m.enemies.size(); i++) {

			// Simplify the text needed to be typed
			int xpos = mainGroup.getWorldXCenter();
			int ypos = mainGroup.getWorldYCenter();

			// If the enemy is not dead
			if (m.enemies.get(i).enemyState != Enemy.DIE) {

				// If player is in the walk images
				if (m.enemies.get(i).walkImages[0].isPointInElement(xpos + partX, ypos + partY) ||
						m.enemies.get(i).walkImages[0].isPointInElement(xpos + partX, ypos - partY) ||
						m.enemies.get(i).walkImages[0].isPointInElement(xpos - partX, ypos + partY) ||
						m.enemies.get(i).walkImages[0].isPointInElement(xpos - partX, ypos - partY)) {

					// Player takes damage if they did not in the last iFrames
					if (System.currentTimeMillis() - startDamageTime > iFrames) {

						health -= m.enemies.get(i).damage;
						startDamageTime = System.currentTimeMillis();
					}
				}

			}
		}
	}
}
