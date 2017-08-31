
/**
 * Enemy is superclass for all enemy related objects
 * @author Celine Chan
 */
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {

	/*enemy variables*/
	EZGroup walk;		//holds the walk EZGroup
	EZGroup attack;		//holds the attack EZGroup
	EZGroup rightWalk;
	EZGroup rightAttack;
	EZGroup death;
	EZGroup fly;
	EZGroup rightFly;
	EZGroup enemyGroup = EZ.addGroup();

	EZImage[] walkImages;		//holds all the walk animation frames
	EZImage[] attackImages;		//holds all the attack animation frames
	EZImage[] rightWalkImages;
	EZImage[] rightAttackImages;
	EZImage[] deathImages;
	EZImage[] flyImages;
	EZImage[] rightFlyImages;

	Random rg=new Random();
	protected float x;
	protected float y;		//holds y position of enemy
	protected float speed;		//holds speed of enemy 


	/*animation variables*/
	protected int numWalkFrames;		// Keeps track of how many frames are in the leftWalk animation sequence
	protected int numAttackFrames;	// Keeps track of how many frames are in the attack animation sequence
	protected int numRightWalkFrames;
	protected int numRightAttackFrames;
	protected int numDeathFrames;
	protected int numFlyFrames;
	protected int numRightFlyFrames;

	protected long duration;		// duration to play the animation over (in milliseconds)
	protected long starttime;		// keep track of starting time of animation
	protected long startDeath;
	protected long startDirection = 0;

	protected boolean loopIt;		// determine whether animation should loop or not
	protected boolean starting;	// Flag to indicate that you are starting animation from frame zero
	protected boolean stopped;	// Flag to indicate if the animation has stopped.
	protected boolean visibility;	// Flag to determine if the images should be visible or not
	protected boolean goLeft;

	//EZCircle circle=new EZCircle(512,600,50,50,Color.RED,false);

	//enemy states variables
	static final int NEUTRAL= 0;
	static final int WALK=1;
	static final int FLY = 1;
	static final int ATTACK= 2;
	static final int DIE= 3;

	int enemyState=NEUTRAL;
	int newState=0;

	// Coordinate Variables
	public int leftX, rightX;	
	public int topY, botY;

	// Game Variables
	public int health;
	public int points= 0;
	public int damage;
	public boolean isPointsAdded = false;
	boolean isDespawned = false;
		
	public boolean isAttacking = false;

	
	Enemy(){
		randomizePosition();

		setLoop(true);
		starting = true;
		stopped = false;
		visibility = true;
		goLeft=true;
	}
	
	 

	//detects coordinates of the image
	void detector(){
		//x coordinates
		leftX=walk.getWorldXCenter()-walk.getWidth()/5;
		rightX=walk.getWorldXCenter()+walk.getWidth()/5;

		//y coordinates
		topY=walk.getWorldYCenter()-walk.getHeight()/5;
		botY=walk.getWorldYCenter()+walk.getHeight()/5;
	}

	//randomizes x position of enemy
	void randomizePosition(){

		do {

			x=rg.nextInt(15000) + -7500;

		} while (0 < x && x < 1980);
	}

	void randomizeState(){
		//randomly chooses a state
		if((System.currentTimeMillis()%2000==0&& enemyState!=3)){
			System.out.println("hi");
			enemyState=rg.nextInt(3);
		}
	}

	//function to determine which state enemy should be in
	void states(ArrayList<Projectile> p) {	}

	void direction(){

		if (System.currentTimeMillis() - startDirection >= 3000 ) {

			int half = rg.nextInt(21);
			
			if(half > 10){
				goLeft=false;
			}
			if(10 >= half){
				goLeft=true;
			}
			
			startDirection = System.currentTimeMillis();
		}	
	}

	//function to translate EZGroups
	void translateToTrex(float posX, float posY) {
		walk.translateTo(posX, posY);
		attack.translateTo(posX,posY);
		rightWalk.translateTo(posX, posY);
		rightAttack.translateTo(posX, posY);
		death.translateTo(posX,posY);
	}
	void translateToPtero(float posX, float posY){
		fly.translateTo(posX, posY);
		rightFly.translateTo(posX, posY);
	}

	//function to scale EZGroups
	void scaleTo(EZGroup group,double scale){
		group.scaleTo(scale);
	}

	//loops animation
	void setLoop(boolean loop){
		loopIt = loop;
	}

	//restarts animation
	void restart(){
		starting = true;
	}

	//stops animation
	void stop(){
		stopped = true;
	}

	// Hide each animation frame
	void hide(EZGroup group){
		visibility = false;
		group.hide();
	}
	void hideAll(){
		walk.hide(); rightWalk.hide();
		attack.hide(); rightAttack.hide();
	}
	void hideAllPtero() {
		
		fly.hide(); rightFly.hide();
	}
	//shows EZImages
	void show() {
		visibility = true;
	}

	boolean go(long dur){
		return true;
	}

	// check if state should be attack
	void checkAttack() {

		if (enemyState != DIE) {
			if (isAttacking) {

				enemyState=ATTACK;
			}
			else {
				enemyState = WALK;
			}
		}
	}

	// check if enemy is hit
	void checkHit(ArrayList<Projectile> p) {}

	// check if enemy is dead
	void checkDead() {

		if (health <= 0 && enemyState != DIE) {

			enemyState = DIE;
			startDeath = System.currentTimeMillis();
		}
	}

	//function to load walk images into an array an object
	void loadWalkFiles(String[] filename){}

	void loadAttackFiles(String[] filename){}

	void loadDeathFiles(String[] filename){
		// Make an EZgroup to gather up all the individual EZimages for walk
		death=EZ.addGroup();
		numDeathFrames = filename.length;

		// Make an array to hold the EZImages
		deathImages =  new EZImage[numDeathFrames];

		// Load each image
		for(int i = 0; i < filename.length; i++){
			deathImages[i] = EZ.addImage(filename[i], 0, 0);
			deathImages[i].hide();
			death.addElement(deathImages[i]);
		}

		death.translateTo(x,y);		//move the group to x,y
		scaleTo(death,1.5);
		enemyGroup.addElement(death);
	}

	//function to load walk images into an array an object
	void loadRightWalkFiles(String[] filename){}

	void loadRightAttackFiles(String[] filename){}

	void loadFlyFiles(String[] filename){}

	void loadRightFlyFiles(String[] filename){}

	//cycles through the images for walk
	void animateWalk(){}

	//cycles through the images for attack
	void animateAttack(){}

	//cycles through the images for walk
	void animateRightWalk(){}

	//cycles through the images for attack
	void animateRightAttack(){}

	void animateFly(){}
	void animateRightFly(){}
}

