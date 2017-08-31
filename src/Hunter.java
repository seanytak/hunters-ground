
/**
 * A subclass of player for the Hunter character
 * All images for the hunter created by
 * 
 * @author Sean Takafuji
 *
 */

public class Hunter extends Player {
	
	// Hunter Constructor
	public Hunter(int x, int y) {
		super(x, y);
		
		mainGroup.addElement(playerWeapon.weaponGroup);
	}
	
	// Get all the filenames and add them to their respective arrays
	protected void getImageFiles() {
		
		moveLeftFiles.add("hunter_move_left_0.png");
		moveLeftFiles.add("hunter_move_left_1.png");
		moveLeftFiles.add("hunter_move_left_2.png");
		moveLeftFiles.add("hunter_move_left_3.png");
		
		moveRightFiles.add("hunter_move_right_0.png");
		moveRightFiles.add("hunter_move_right_1.png");
		moveRightFiles.add("hunter_move_right_2.png");
		moveRightFiles.add("hunter_move_right_3.png");
		
		jumpLeftFiles.add("hunter_move_left_3.png");
		
		jumpRightFiles.add("hunter_move_right_3.png");
		
		deadFiles.add("hunter_dead.png");
	}
}
