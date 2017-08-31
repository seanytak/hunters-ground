import java.awt.event.KeyEvent;

/**
 * A class that just holds the control characters for the user. Easy to access and modify
 * 
 * @author Sean Takafuji
 */

public class Controls {
			
	// Lowcase characters
	public static char JUMP_KEY1 = 'w';
	public static char MOVE_LEFT_KEY1 = 'a';
	public static char MOVE_RIGHT_KEY1 = 'd';
	
	// Uppercase characters (in the case CAPS LOCK is down the game sitll operates normally)
	public static char JUMP_KEY2 = 'W';
	public static char MOVE_LEFT_KEY2 = 'A';
	public static char MOVE_RIGHT_KEY2 = 'D';
	
	public static boolean jump() {
		
		if (EZInteraction.isKeyDown(JUMP_KEY1) || EZInteraction.isKeyDown(JUMP_KEY2) || EZInteraction.isKeyDown(KeyEvent.VK_SPACE)) {
			
			return true;
		}
		
		return false;
	}
	
	public static boolean moveLeft() {
		
		if (EZInteraction.isKeyDown(MOVE_LEFT_KEY1) || EZInteraction.isKeyDown(MOVE_LEFT_KEY2)) {
			
			return true;
		}
		
		return false;
	}
	
	public static boolean moveRight() {
		
		if (EZInteraction.isKeyDown(MOVE_RIGHT_KEY1) || EZInteraction.isKeyDown(MOVE_RIGHT_KEY2)) {
			
			return true;
		}
		
		return false;
	}
}
