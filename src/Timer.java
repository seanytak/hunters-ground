import java.awt.Color;

/**
 * Class to keep track of the Timer
 * 
 * @author Sean Takafuji
 *
 */
public class Timer {

	// Member Variables
	EZImage timerBox;
	EZText timeText;
	
	static long duration = 120000; // Two Minutes
	long time = duration;
	long startTime;
	
	// Create the timer
	public Timer(int x, int y) {
		
		timerBox = EZ.addImage("point_box.png", x / 8, y / 7);
		timerBox.scaleTo(0.5);
		timeText = EZ.addText("8-BIT WONDER.TTF", x / 8,  y / 6, "TIME:    " + time, Color.BLACK, 25);
		
		startTime = System.currentTimeMillis();
	}
	
	// Update the time
	public void modifyTime() {
		
		time = (duration - (System.currentTimeMillis() - startTime)) / 1000;
		timeText.setMsg("TIME    " + time);
	}
}
