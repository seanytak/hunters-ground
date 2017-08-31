
/**
 * This is the main button class that all menu type classes utilize.
 * 
 *  @author Sean Takafuji
 */

import java.awt.Color;
import java.awt.Font;


public class Button {

	// Member variables
	EZRectangle button, border;
	EZText text;
	
	// Constructor that builds a button with text
	public Button(int x, int y, int w, int h, Color c1, Color c2,  boolean f, String msg, int fs) {
		
		// Set the member variables equal to EZ objects
		border = EZ.addRectangle(x, y, w + (w / 25), h + (w / 25), c1, true);
		button = EZ.addRectangle(x, y, w, h, c2, f);	
		text = EZ.addText(Font.DIALOG, x, y, msg, Color.BLACK, fs);
	}
	
	// Constructor that builds a button without text
	public Button(int x, int y, int w, int h, Color c1, Color c2, boolean f) {
		
		// Set the member variables equal to EZ objects
		border = EZ.addRectangle(x, y, w + (w / 25), h + (w / 25), c1, true);
		button = EZ.addRectangle(x, y, w, h, c2, f);
	}
	
	public Button(String type, int x, int y, int w, int h, Color c1, Color c2, boolean f) {
		
		// Set the member variables equal to EZ objects
		border = EZ.addRectangle(x, y, w + (w / 25), h + (w / 25), c1, true);
		button = EZ.addRectangle(x, y, w, h, c2, f);
		
		if (type == "PLUS") {
			
			EZ.addLine(x  - x / 24, y , x  + x / 24, y , c1, w / 15);
			EZ.addLine(x , y  - 25, x, y  + 25, c1, w / 15);
		}
		
		if (type == "MINUS") {
			
			EZ.addLine(x  - x / 24, y , x  + x / 24, y , c1, w / 15);
		}
	}
	 
	// Function to highlight the button when the mouse is over it
	public void highlightButton(int x, int y, Color original) {
		
		// If the mouse (the x and y parameters) are in the button
		if (button.isPointInElement(x, y)) {
			
			// Set the button color to cyan
			button.setColor(Color.CYAN);
			
		} else { // If the mouse is not inside the button
			
			// Return the button to the original color
			button.setColor(original);
		}
	}
	
	// Boolean that checks if the button is being clicked
	public boolean checkClick(int x, int y) {
		
		// Check if point (mouse) is in the button
		if (button.isPointInElement(x, y)) {
			
			// Return that the button was pressed
			return true;
		}
		
		// Return that the button was not pressed
		return false;
	}
	
	// Close the elements of the button
	public void close() {
		
		// Remove the three elements that make up the button
		EZ.removeEZElement(border);
		EZ.removeEZElement(button);
		EZ.removeEZElement(text);
	}
}
