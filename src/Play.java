
/**
 * Main File for the actual game
 * 
 * @author Sean Takafuji
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Play {

	// Coordinate variables
	private int x = 2000;
	private int y = 500;

	// Member Variables
	private Button menuButton;
	private EZSound bgm;
	private EZText gameOverText, finishText, menuText;
	private Detector detector;
	private Map map;
	private Menu menu;
	private Hunter player;
	private Points points;
	private Timer timer;

	private String bgmname = "Starfox_Adventure_BG.wav";

	private boolean isPlay;

	// Game runs here
	public Play() {

		isPlay = true;

		prepPlay();
		playGame();
		gameOver();
	}

	// Function to prepare the game
	public void prepPlay() {

		// Set the Variables
		x = EZ.getWindowWidth();
		y = EZ.getWindowHeight();

		// Create all the objects
		map = new Map(x, y, 20);
		detector = new Detector(x, y);
		player = new Hunter(x / 2, y / 4);
		points = new Points(x, y);
		timer = new Timer(x, y);
		bgm = EZ.addSound(bgmname);
		bgm.loop();

		EZ.setFrameRate(150);
	}

	// Function run during game
	public void playGame() {

		// While player is alive or time is not up
		while (Player.health > 0 && timer.time > 0) {

			player.go(map, x, y);
			map.go(player);
			detector.detect(map.enemies);
			points.modifyStats(map.enemies, Player.health);
			timer.modifyTime();

			EZ.refreshScreen();
		}
	}

	// Function run after the game is done
	public void gameOver() {

		EZ.setFrameRate(1000);

		if (player.health <= 0) {

			gameOverText = EZ.addText("8-BIT WONDER.TTF", x / 2, y / 2, "GAME OVER", Color.RED, 60);
			finishText = EZ.addText(x / 2, y / 2 + y / 16, "YOU DIED", Color.RED, 60);

			while (!player.groundContact(map)) {

				player.go(map, x, y);
			}
		}

		else if (timer.time <= 0) {

			gameOverText = EZ.addText(x / 2, y / 2, "GAME OVER", Color.GREEN, 60);
			finishText = EZ.addText(x / 2, y / 2 + y / 16, "YOU SURVIVED", Color.GREEN, 60);
		}
		
		menuButton = new Button(x / 2, y / 5, x / 3, y / 10, Color.WHITE, Color.GREEN, true);
		menuText = EZ.addText(x / 2, y / 5, "RETURN TO MENU", Color.BLACK, 25);

		while (isPlay) {

			int clickX = EZInteraction.getXMouse();
			int clickY = EZInteraction.getYMouse();

			menuButton.highlightButton(clickX, clickY, Color.GREEN);
			player.playerWeapon.sound.stop();

			if (EZInteraction.wasMouseLeftButtonPressed()){
				if (menuButton.checkClick(clickX, clickY)) {
					EZ.removeAllEZElements();
					bgm.stop();

					menu = new Menu();
					isPlay = false;
				}
			}
		}
	}
}
