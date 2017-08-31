import java.util.ArrayList;

/**
 * 
 * @author Celine Chan
 *
 */

public class TRex extends Enemy {

	static String[] tRexwalk= {"trex0.png", "trex1.png", "trex2.png", "trex3.png", };
	static String[] tRexattack={"trex4.png","trex5.png", "trex6.png", "trex7.png"};
	static String[] tRexwalkRight = {"rightTrex0.png","rightTrex1.png","rightTrex2.png","rightTrex3.png"};
	static String[] tRexattackRight= {"rightTrex4.png","rightTrex5.png","rightTrex6.png","rightTrex7.png"};
	static String[] tRexdeath= {"trex8.png","rightTrex8.png"};
	EZSound tRexroar= EZ.addSound("TRex_Roar_SFX.wav");
	boolean didRoar = false;
	boolean isDespawned = false;
	
	double scaleSize = 1.5;
	long startAttack = 0;
	
	public TRex(){
		loadWalkFiles(tRexwalk);
		loadAttackFiles(tRexattack);
		loadRightWalkFiles(tRexwalkRight);
		loadRightAttackFiles(tRexattackRight);
		loadDeathFiles(tRexdeath);

		health = 1000;
		y=(23 * 1080) / 30; //holds y position of tRex
		
		damage = 100;
	}

	void states(ArrayList<Projectile> p) {

		checkAttack();
		checkHit(p);
		checkDead();
		
		switch(enemyState){

		
		case NEUTRAL:				//stands still
			
			if(goLeft){				//if heading left, show left standing image
				hideAll();
				walkImages[0].show();
			}
			if(!goLeft){			//if heading right, show right standing image
				hideAll();
				rightWalkImages[0].show();
			}
			break;

		case WALK:				//walks
			
			speed=3f;			//holds speed for tRex walking
			hide(attack); hide(rightAttack); 
			
			if(goLeft){
				hide(rightWalk);
				show();
				x-=speed;		//tRex goes left
				go(500);	
				animateWalk();
			}
			if(!goLeft){
				hide(walk);
				show();
				x+=speed;		//tRex goes right
				go(500);
				animateRightWalk();
			}
			break;
			
		case ATTACK:			//attacks
			speed=12f;			//holds speed for tRex attacking
			hide(walk); hide(rightWalk); 
			
			if (!didRoar) {
				
				tRexroar.play();
				didRoar = !didRoar;
			}
			
			if (System.currentTimeMillis() - startAttack > 1000) {
				
				if (enemyGroup.getWorldXCenter() > 1980 / 2) {
					goLeft = true;
				}
				
				if (enemyGroup.getWorldXCenter() < 1980 / 2) {
					goLeft = false;
				}
				
				startAttack = System.currentTimeMillis();
			}
			
			if(goLeft){
				hide(rightAttack);
				show();
				x-=speed;		//tRex goes left
				go(300);
				animateAttack();
			}
			if(!goLeft){
				hide(attack);
				show();
				x+=speed;		//tRex goes right
				go(300);
				animateRightAttack();
			}
			break;

		case DIE:				//death 
			stop();
			visibility=false;
			starting=false;
			
			points+=10;
			
			if(goLeft){
				hideAll();
				deathImages[0].show();	
			}
			if(!goLeft){
				hideAll();
				deathImages[1].show();
			}
			
			if (System.currentTimeMillis() - startDeath > 2000) {
				
				translateToTrex(-100, -500);
				isDespawned = true;
			}
			
			break;

		default:				//default do nothing

			break;
		}
		
		if (enemyState != ATTACK) {
			
			didRoar = false;
		}
	}
	
	void checkHit(ArrayList<Projectile> p) {
		
		for (int i = 0; i < p.size(); i++) {

			if (walkImages[0].isPointInElement(p.get(i).projectile.getWorldXCenter(), 
					p.get(i).projectile.getWorldYCenter()) && p.get(i).isActive) {

				health -= p.get(i).damage;
				p.get(i).isActive = false;
			}
		}
	}
	
	//function to move the tRex
	boolean go(long dur){
		direction();
		duration=dur;
		if (stopped) return false;

		// If the animation is starting for the first time save the starttime
		if (starting){
			starttime = System.currentTimeMillis();
			starting = false;
		}

		// If the duration for the animation is exceeded and if looping is enabled
		// then restart the animation from the beginning.
		if ((System.currentTimeMillis() - starttime) > duration) {
			if (loopIt) {
				restart();
				return true;
			}
			// Otherwise there is no more animation to play so stop.
			return false;
		}

		//if still animating, translate enemy to x,y
		if(!stopped) translateToTrex(x,y);
		return true;
	}	



	void loadWalkFiles(String[]filename){
		// Make an EZgroup to gather up all the individual EZimages for walk
		walk=EZ.addGroup();
		numWalkFrames = filename.length;

		// Make an array to hold the EZImages
		walkImages =  new EZImage[numWalkFrames];

		// Load each image
		for(int i = 0; i < filename.length; i++){
			walkImages[i] = EZ.addImage(filename[i], 0, 0);
			walkImages[i].hide();
			walk.addElement(walkImages[i]);
		}

		walk.translateTo(x,y);		//move the group to x,y
		scaleTo(walk,scaleSize);	
		enemyGroup.addElement(walk);
	}

	void loadRightWalkFiles(String[] filename){
		// Make an EZgroup to gather up all the individual EZimages for walk
		rightWalk=EZ.addGroup();
		numRightWalkFrames = filename.length;

		// Make an array to hold the EZImages
		rightWalkImages =  new EZImage[numRightWalkFrames];

		// Load each image
		for(int i = 0; i < filename.length; i++){
			rightWalkImages[i] = EZ.addImage(filename[i], 0, 0);
			rightWalkImages[i].hide();
			rightWalk.addElement(rightWalkImages[i]);
		}

		rightWalk.translateTo(x,y);		//move the group to x,y
		scaleTo(rightWalk,scaleSize);		
		enemyGroup.addElement(rightWalk);
	}

	void loadAttackFiles(String[] filename){
		// Make an EZgroup to gather up all the individual EZimages for attack
		attack=EZ.addGroup();
		numAttackFrames = filename.length;

		// Make an array to hold the EZImages
		attackImages =  new EZImage[numAttackFrames];

		// Load each image
		for(int i = 0; i < filename.length; i++){
			attackImages[i] = EZ.addImage(filename[i], 0, 0);
			attackImages[i].hide();
			attack.addElement(attackImages[i]);
		}
		attack.translateTo(x,y);	//move the group to x,y
		scaleTo(attack,scaleSize);
		enemyGroup.addElement(attack);
	}

	void loadRightAttackFiles(String[] filename){
		// Make an EZgroup to gather up all the individual EZimages for attack
		rightAttack=EZ.addGroup();
		numRightAttackFrames = filename.length;

		// Make an array to hold the EZImages
		rightAttackImages =  new EZImage[numRightAttackFrames];

		// Load each image
		for(int i = 0; i < filename.length; i++){
			rightAttackImages[i] = EZ.addImage(filename[i], 0, 0);
			rightAttackImages[i].hide();
			rightAttack.addElement(rightAttackImages[i]);
		}
		rightAttack.translateTo(x,y);	//move the group to x,y
		scaleTo(rightAttack,scaleSize);
		enemyGroup.addElement(rightAttack);
	}
	void animateWalk(){
		// Based on the current frame and elapsed time figure out what frame to show.
		float normTime = (float) (System.currentTimeMillis() - starttime)/ (float) duration;
		int currentFrame = (int) (((float) numWalkFrames) *  normTime);
		if (currentFrame > numWalkFrames-1) currentFrame = numWalkFrames-1;

		// Hide all the frames first
		for (int i=0; i < numWalkFrames; i++) {
			if (i != currentFrame) walkImages[i].hide();
		}

		// Then show all the frame you want to see.
		if (visibility)
			walkImages[currentFrame].show();
		else 
			walkImages[currentFrame].hide();

	}

	void animateAttack(){
		// Based on the current frame and elapsed time figure out what frame to show.
		float normTime = (float) (System.currentTimeMillis() - starttime)/ (float) duration;
		int currentFrame = (int) (((float) numAttackFrames) *  normTime);
		if (currentFrame > numAttackFrames-1) currentFrame = numAttackFrames-1;

		// Hide all the frames first
		for (int i=0; i < numAttackFrames; i++) {
			if (i != currentFrame) attackImages[i].hide();
		}

		// Then show all the frame you want to see.
		if (visibility)
			attackImages[currentFrame].show();
		else 
			attackImages[currentFrame].hide();
	}

	void animateRightWalk(){
		// Based on the current frame and elapsed time figure out what frame to show.
		float normTime = (float) (System.currentTimeMillis() - starttime)/ (float) duration;
		int currentFrame = (int) (((float) numRightWalkFrames) *  normTime);
		if (currentFrame > numRightWalkFrames-1) currentFrame = numRightWalkFrames-1;

		// Hide all the frames first
		for (int i=0; i < numRightWalkFrames; i++) {
			if (i != currentFrame) rightWalkImages[i].hide();
		}

		// Then show all the frame you want to see.
		if (visibility)
			rightWalkImages[currentFrame].show();
		else 
			rightWalkImages[currentFrame].hide();

	}

	void animateRightAttack(){
		// Based on the current frame and elapsed time figure out what frame to show.
		float normTime = (float) (System.currentTimeMillis() - starttime)/ (float) duration;
		int currentFrame = (int) (((float) numRightAttackFrames) *  normTime);
		if (currentFrame > numRightAttackFrames-1) currentFrame = numRightAttackFrames-1;

		// Hide all the frames first
		for (int i=0; i < numRightAttackFrames; i++) {
			if (i != currentFrame) rightAttackImages[i].hide();
		}

		// Then show all the frame you want to see.
		if (visibility)
			rightAttackImages[currentFrame].show();
		else 
			rightAttackImages[currentFrame].hide();
	}

	int count = 0;
	
}


