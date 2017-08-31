

/**
 * A class for the background of the game
 * 
 * @author Sean Takafuji
 * 
 * 
 * 
 * All backgrounds created by 
 * 
 * @author Celine Chan
 */

import java.util.ArrayList;
import java.util.Random;

public class Background {

	// Arrays to hold the backgrounds
	ArrayList<String> backgroundFiles = new ArrayList<String>();
	ArrayList<EZImage> background = new ArrayList<EZImage>();
	
	// Background group
	EZGroup backgroundGroup;
	
	Random rg = new Random();
	
	int x, y; // x and y lengths of the screen
	int xc, yc; // x and y center of the screen
	int w; // width of the background
	
	public Background(int xpos, int ypos, int n) {
		
		backgroundGroup = EZ.addGroup();
		
		x = xpos;
		y = ypos;
		
		xc = xpos / 2;
		yc = ypos / 2;
		
		getBackgroundFiles();
		prepBackgroundFiles();
		setBackgroundFiles(n);
	}
	
	public void move(double x, double y) {
		
		for (int i = 0; i < background.size(); i++) {
			
			background.get(i).translateBy(x, y);
		}
	}
	
	// Add the background image file names to an array
	private void getBackgroundFiles() {
		
		backgroundFiles.add("hunter_background_3.png");
		//backgroundFiles.add("hunter_background_2.png");
		backgroundFiles.add("hunter_background_4.png");
	}
	
	// Put the first background image and use it as a reference
	private void prepBackgroundFiles() {
		
		background.add(EZ.addImage("hunter_background_4.png", xc, yc));
		w = background.get(0).getWidth() - (background.get(0).getWidth() / 50);
	}
	
	// Add background images for the length of the game
	private void setBackgroundFiles(int n) {
		
		// Add n-amount of background images to the left and right of the first background image
		for (int i = 1; i <= n; i++) {
			
			background.add(EZ.addImage(backgroundFiles.get(i % 2), xc + (w * i), yc));
			background.add(EZ.addImage(backgroundFiles.get(i % 2), xc - (w * i), yc));
		}
		
		// Add the background images to the screen group
		for (int i = 0; i < background.size(); i++) {
			
			backgroundGroup.addElement(background.get(i));
		}
	}
	
	// Function to hide background images that aren't near the player
	public void hideBackground() {
		
		// For all background images
		for (int i = 0; i < background.size(); i++) {
			
			// If the background image is within 2 background image lengths from the player
			if (background.get(i).getWorldXCenter() > xc - w && 
				background.get(i).getWorldXCenter() < xc + w) {
				
				background.get(i).show();
			}
			
			else {
				
				background.get(i).hide();
			}
		}
	}
}
