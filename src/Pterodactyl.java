import java.util.ArrayList;

/**
 * Pterodactyl class as one of the enemies
 * 
 * @author Celine Chan
 *
 */
public class Pterodactyl extends Enemy{

	static String[] Pterofly= {"pterodactyl0.png","pterodactyl1.png","pterodactyl2.png"};
	static String[] Pteroflyright= {"rightPtero0.png","rightPtero1.png","rightPtero2.png"};

	public Pterodactyl (){
		randomizePosition();
		loadFlyFiles(Pterofly);
		loadRightFlyFiles(Pteroflyright);
		
		y=200;				//holds y position of pterodactyl
		health = 500;
		damage = 50;
	}

	void states(ArrayList<Projectile> p) {

		checkAttack();
		checkHit(p);
		checkDead();
		
		enemyGroup.show();
		
		switch(enemyState){
		
		case FLY:				//default fly
			speed=0.1f;			//holds speed pterodactyl flying
			
			if(goLeft){			//if heading left, animate left flying images
				hide(rightFly);
				show();
				x-=speed;		//pterodactyl goes left
				go(500);
				animateFly();
			}
			if(!goLeft){		//if heading right, animate right flying images
				hide(fly);
				show();
				x+=speed;		//pterodactyl goes right
				go(500);
				animateRightFly();
			}
			
			System.out.println(x + " " + y);
			break;
			
		case ATTACK:
				speed=0.2f;
				
				if(enemyGroup.getWorldXCenter()==1980 / 2 && goLeft){
					//hide(rightFly); //hide(fly);
					flyImages[0].show();
					flyImages[0].rotateBy(90);
					y+=speed;
				}
				if(enemyGroup.getWorldXCenter()==1980 / 2 && !goLeft){
					//hide(rightFly); //hide(fly);
					rightFlyImages[0].show();
					rightFlyImages[0].rotateBy(90);
					y+=speed;
				}
			break;
		
		case DIE:
				
			stop();
			visibility=false;
			starting=false;
			
			points+=5;
			
			if(goLeft){
				hideAllPtero();
				deathImages[0].show();	
			}
			if(!goLeft){
				hideAllPtero();
				deathImages[1].show();
			}
			
			if (System.currentTimeMillis() - startDeath > 2000) {
				
				translateToPtero(-100, -100);
				isDespawned = true;
			}
			
			break;
		}

		System.out.println(enemyState);
	}
	
	void checkHit(ArrayList<Projectile> p) {
		
		for (int i = 0; i < p.size(); i++) {

			if (flyImages[0].isPointInElement(p.get(i).projectile.getWorldXCenter(), 
					p.get(i).projectile.getWorldYCenter()) && p.get(i).isActive) {

				health -= p.get(i).damage;
				p.get(i).isActive = false;
			}
		}
	}
	
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
		if(!stopped)translateToPtero(x,y);
		return true;
	}	
	
	
	void loadFlyFiles(String[] filename){
		// Make an EZgroup to gather up all the individual EZimages for attack
		fly=EZ.addGroup();
		numFlyFrames = filename.length;

		// Make an array to hold the EZImages
		flyImages =  new EZImage[numFlyFrames];

		// Load each image
		for(int i = 0; i < filename.length; i++){
			flyImages[i] = EZ.addImage(filename[i], 0, 0);
			flyImages[i].hide();
			fly.addElement(flyImages[i]);
		}
		fly.translateTo(x,y);	//move the group to x,y
		scaleTo(fly,1.5);
		enemyGroup.addElement(fly);
	}

	void loadRightFlyFiles(String[] filename){
		// Make an EZgroup to gather up all the individual EZimages for attack
		rightFly=EZ.addGroup();
		numRightFlyFrames = filename.length;

		// Make an array to hold the EZImages
		rightFlyImages =  new EZImage[numRightFlyFrames];

		// Load each image
		for(int i = 0; i < filename.length; i++){
			rightFlyImages[i] = EZ.addImage(filename[i], 0, 0);
			rightFlyImages[i].hide();
			rightFly.addElement(rightFlyImages[i]);
		}
		rightFly.translateTo(x,y);	//move the group to x,y
		scaleTo(rightFly,1.5);
		enemyGroup.addElement(rightFly);
	}

	void animateFly(){
		// Based on the current frame and elapsed time figure out what frame to show.
		float normTime = (float) (System.currentTimeMillis() - starttime)/ (float) duration;
		int currentFrame = (int) (((float) numFlyFrames) *  normTime);
		if (currentFrame > numFlyFrames-1) currentFrame = numFlyFrames-1;

		// Hide all the frames first
		for (int i=0; i < numFlyFrames; i++) {
			if (i != currentFrame) flyImages[i].hide();
		}

		// Then show all the frame you want to see.
		if (visibility)
			flyImages[currentFrame].show();
		else 
			flyImages[currentFrame].hide();

	}

	void animateRightFly(){
		// Based on the current frame and elapsed time figure out what frame to show.
		float normTime = (float) (System.currentTimeMillis() - starttime)/ (float) duration;
		int currentFrame = (int) (((float) numRightFlyFrames) *  normTime);
		if (currentFrame > numRightFlyFrames-1) currentFrame = numRightFlyFrames-1;

		// Hide all the frames first
		for (int i=0; i < numRightFlyFrames; i++) {
			if (i != currentFrame) rightFlyImages[i].hide();
		}

		// Then show all the frame you want to see.
		if (visibility)
			rightFlyImages[currentFrame].show();
		else 
			rightFlyImages[currentFrame].hide();
	}
}
