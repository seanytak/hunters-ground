
import java.awt.Color;

public class MainProgram {
	
	// Constants (Used to make EZ a debug screen)
	public static final int X = 1920;
	public static final int Y = 1080;
	
	public static void main(String[] args) {
		
		// Set up the screen
		EZ.initialize(X, Y);
		EZ.setBackgroundColor(new Color(100, 200, 200));
		EZ.setFrameRate(1000);
		
		// Create the Menu Screen (The program will operate from class to class from here)
		Menu menu = new Menu();
	}
}
