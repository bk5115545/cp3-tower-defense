import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;



class MyGame extends Component {
	
	Timer gameUpdater = new Timer(5);
	volatile boolean update = false;
	
	Timer moreEnemies = new Timer(500);

	
	Player player;
	volatile BufferedImage background;
	SpriteGroup player_projectile_group, enemy_group, player_group, enemy_projectile_group, powerup_group, splash_group;
	
	//player images
	BufferedImage[] playerImages;
	
	//projectile images named by activation key
	BufferedImage Button__Space__Image;
	BufferedImage Button__A__Image;
	BufferedImage Button__S__Image;
	BufferedImage Button__D__Image;
	BufferedImage[] smallSplashImages;
	
	//enemy images
	BufferedImage[] weakEnemyImages;
	BufferedImage[] hardEnemyImages;
	
	//power-up images
	BufferedImage[] healthPowerUpImages;
	
	
	//collision groups
	PlayerEnemyCollision player_enemy__Collision;
	PlayerProjectileEnemyCollision proj_enemy_Collision;
	SplashEnemy_Collision splash_enemy_Collision;
	PowerUpPlayer_Collision player_powerup_collision;
	
	
	//fonts
	Font hull = new Font("Arial", Font.PLAIN, 24);
	Font battery = new Font("Arial", Font.PLAIN, 24);
	Font heat = new Font("Arial", Font.PLAIN, 24);
	Font barTitler = new Font("Arial", Font.ITALIC|Font.BOLD, 22);
	
	int lastPlayerBasicShot; int shotCounter;

	public void initResources() {
		
		lastPlayerBasicShot = 0; shotCounter = 0;
		background = getImage("Background1.gif");
		player_group = new SpriteGroup("Player");
		
		playerImages = new BufferedImage[2];
		playerImages[0] = getImage("PewPew Machine1 v.2.gif");
		playerImages[1] = getImage("PewPew Machine2 v.2.gif");
		player = new Player();
		player_group.add(player);
		
		//sprite groups
		player_projectile_group = new SpriteGroup("Player Projectile Group");
		
		enemy_group = new SpriteGroup("Enemy Group");
		
		powerup_group = new SpriteGroup("PowerUps");
		
		splash_group = new SpriteGroup("sprites that do radius damage");
		
		//projectile images
		Button__Space__Image = getImage("fire-bullet.gif");
		Button__A__Image = getImage("green-fire-bullet.gif");
		Button__S__Image = getImage("rocket-w-trial.gif");
		Button__D__Image = getImage("plasmaball.gif");
		//power-up images
		healthPowerUpImages = new BufferedImage[13];
		healthPowerUpImages[0] = getImage("Health power up animated-2.png");
		healthPowerUpImages[1] = getImage("Health power up animated-3.png");
		healthPowerUpImages[2] = getImage("Health power up animated-4.png");
		healthPowerUpImages[3] = getImage("Health power up animated-5.png");
		healthPowerUpImages[4] = getImage("Health power up animated-6.png");
		healthPowerUpImages[5] = getImage("Health power up animated-7.png");
		healthPowerUpImages[6] = getImage("Health power up animated-8.png");
		healthPowerUpImages[7] = getImage("Health power up animated-9.png");
		healthPowerUpImages[8] = getImage("Health power up animated-10.png");
		healthPowerUpImages[9] = getImage("Health power up animated-11.png");
		healthPowerUpImages[10] = getImage("Health power up animated-12.png");
		healthPowerUpImages[11] = getImage("Health power up animated-13.png");
		healthPowerUpImages[12] = getImage("Health power up animated-14.png");
		
		//enemy images
		weakEnemyImages = new BufferedImage[2];
		weakEnemyImages[0] = getImage("Enemy.gif");
		weakEnemyImages[1] = getImage("enemy2.gif");
		
		//hardEnemyImages = new BufferedImage[2];
		//hardEnemyImages[0] = ;
		//hardEnemyImages[1] = 
		
		
		
		//collision managers
		player_enemy__Collision = new PlayerEnemyCollision();
		player_enemy__Collision.setCollisionGroup(player_group, enemy_group);
		
		proj_enemy_Collision = new PlayerProjectileEnemyCollision();
		proj_enemy_Collision.setCollisionGroup(player_projectile_group, enemy_group);
		
		player_powerup_collision = new PowerUpPlayer_Collision();
		player_powerup_collision.setCollisionGroup(player_group, powerup_group);
		
		splash_enemy_Collision = new SplashEnemy_Collision();
		splash_enemy_Collision.setCollisionGroup(splash_group, enemy_group);
	}

	private BufferedImage getImage(String string) {
		try {
			return ImageIO.read(new File(string));
		} catch (Exception e) {return null;}
	}

	
	public void render(Graphics2D g) {
		shotCounter++;
		g.drawImage(background,0,0,null);

		g.setColor(Color.green);
		for(int i=0; i<200*player.getPercentHealth(); i++) {
			g.drawString("|", i*4, 4);
		}
		g.setColor(Color.black);
		g.drawString("Hull", 2, 8);
		g.setColor(Color.blue);
		for(int i=0; i<200*player.getPercentBattery(); i++) {
			g.drawString("|", i*4, 32);
		}
		g.setColor(Color.black);
		g.drawString("Battery", 2, 36);
		g.setColor(Color.red);
		for(int i=0; i<200*player.getPercentHeat(); i++) {
			g.drawString("|", i*4, 60);
		}
		g.setColor(Color.black);
		g.drawString("OverHeat", 2, 64);
		
		player_group.render(g);
		player_projectile_group.render(g);
		powerup_group.render(g);
		enemy_group.render(g);
		splash_group.render(g);
		
		update = true;
		player_enemy__Collision.checkCollision();
		player_powerup_collision.checkCollision();
		proj_enemy_Collision.checkCollision();
		splash_enemy_Collision.checkCollision();
	}

	
	public void update(long time) {
		if(gameUpdater.action(time) || update) {
			
			enemy_group.update(time);
			player_projectile_group.update(time);
			powerup_group.update(time);
			player_group.update(time);
			splash_group.update(time);

			if(moreEnemies.action(time)) enemy_group.add(new WeakSlideEnemy());
			
			if(bsInput.isKeyPressed(KeyEvent.VK_E)) enemy_group.add(new WeakSlideEnemy());
			//if(bsInput.isKeyPressed(KeyEvent.VK_X)) powerup_group.add(new HealthPowerUP(30, 100, 0)); 
			
			update = false;
		}

		if(bsInput.isKeyPressed(KeyEvent.VK_ESCAPE)) finish();
	}


	
	
	
	
	
	
	
	class Player extends AnimatedSprite {
		private static final long serialVersionUID = -3784097176896786948L;
		
		private double friction = 0.97;
		private double xLimit = 0.2;
		private double yLimit = 0.2;
		
		private double xSpeed;
		private double ySpeed;
		
		private int health = 100;
		public double maxHealth = 100;
		
		private double battery = 100.0;
		private double maxBattery = 100.0;
		private int rechargeLevel = 1;
		
		private int coolingLevel = 1;
		private double heat = 0.0;
		private double maxHeat = 100.0;
		
		public void setMaxHeat(double h) {
			maxHeat = h;
		}
		
		public double getMaxHeat() {
			return maxHeat;
		}
		
		public void setHeat(double h) {
			heat = h;
		}
		
		public double getHeat() {
			return heat;
		}
		
		public void setCoolingLevel(int l) {
			coolingLevel = l;
		}
		
		public int getCoolingLevel() {
			return coolingLevel;
		}
		
		public double getPercentHeat() {
			return heat/maxHeat;
		}
		
		private void heat(int h) {
			heat += h;
		}
		private void cool() {
			if(heat > 0+0.005*coolingLevel) {
				heat -= heat*(0.005*coolingLevel);
			}
		}
		public void setBattery(int b) {
			battery = b;
		}
		
		public double getPercentBattery() {
			return battery/maxBattery;
		}
		
		public void setMaxBattery(double max) {
			maxBattery = max;
		}
		
		public void setRechargeLevel(int l) {
			rechargeLevel = l;
		}
		
		public int getRechargeLevel() {
			return (int)rechargeLevel;
		}
		
		private void recharge() {
			if(battery< maxBattery+battery*(0.005*rechargeLevel)) {
				battery += battery*(0.005*rechargeLevel);
			}
		}
		
		public void discharge(double d) {
			battery -= d;
		}
		
		public double getPercentHealth() {
			return getID()/maxHealth;
		}
		public double getMaxHealth() {
			return maxHealth;
		}
		
		public int getHealth() {
			return getID();
		}
		public void setHealth(int h) {
			health = h;
			setID(h);
		}
		public void setMaxHealth(int m) {
			maxHealth = m;
		}
		
		public Player() {
			setImages(playerImages);
			setAnimationFrame(0, 1);
			setAnimationTimer(new Timer(600));
			setLocation(background.getHeight()/2, background.getWidth()/2);
			setAnimate(true);
			setLoopAnim(true);
			setID(health);
		}
		

		public void setLimits(double x, double y) {
			xLimit = x;
			yLimit = y;
		}
		
		public void setFriction(double f) {
			friction = f;
		}
		
		int laserCounter = 0;
		int missileCounter = 0;
		int basicCounter = 0;
		int ballCounter = 0;
		double x;
		double y;
		public void update(long t) {
			if(getAnimationTimer().action(t)) updateAnimation();
			xSpeed = getHorizontalSpeed();
			ySpeed = getVerticalSpeed();
			x = getX();
			y = getY();
			
			//movement
			if(bsInput.isKeyDown(KeyEvent.VK_LEFT)) { 
				xSpeed= xSpeed<xLimit && xSpeed>(-1 * xLimit)? xSpeed - 0.02 : xSpeed;  
			}
			if(bsInput.isKeyDown(KeyEvent.VK_RIGHT)) {    
				xSpeed= xSpeed<xLimit && xSpeed>(-1 * xLimit)? xSpeed + 0.02 : xSpeed;  
			}
			if(bsInput.isKeyDown(KeyEvent.VK_UP)) { 
				ySpeed= ySpeed<yLimit && ySpeed>(-1 * yLimit)? ySpeed - 0.02 : ySpeed;   
			}
			if(bsInput.isKeyDown(KeyEvent.VK_DOWN)) { 
				ySpeed= ySpeed<yLimit && ySpeed>(-1 * yLimit)? ySpeed + 0.02 : ySpeed;  
			}
			
			xSpeed *= friction;
			ySpeed *= friction;
			
			setSpeed(xSpeed, ySpeed);

			
			if(x<0 || x>800-getWidth()) { //out of x screen bounds
				setLocation( x<5? 0 : 800-getWidth(), y);
				setHorizontalSpeed(0);
			}
			if(y<0 || y>600-getHeight()) { //out of y screen bounds
				setLocation(x, y<5? 0 : 600-getHeight());
				setVerticalSpeed(0);
			}
			updateMovement();
			//end movement
			
			recharge();cool();
			//fire basic projectile
			if(bsInput.isKeyDown(KeyEvent.VK_SPACE) && basicCounter+10<shotCounter && heat*coolingLevel<90) { 
				player_projectile_group.add(new FireBulletProjectile(this));
				basicCounter = shotCounter;
				heat(6);
			}
			//fire laser
			if(bsInput.isKeyDown(KeyEvent.VK_A) && laserCounter+1<shotCounter && battery>10) {
				player_projectile_group.add(new LaserPlayerProjectile());
				laserCounter = shotCounter;
				discharge(2);
			}
			//fire missile
			if(bsInput.isKeyDown(KeyEvent.VK_D) && missileCounter+18<shotCounter && heat*coolingLevel<85) {
				splash_group.add(new Missile1());
				missileCounter = shotCounter;
				heat(15);
			}
			//plasma ball
			if(bsInput.isKeyDown(KeyEvent.VK_S) && ballCounter+30<shotCounter && battery>60) {
				player_projectile_group.add(new PlasmaBall());
				ballCounter = shotCounter;
				discharge(50);
			}
		}
	}
	
	
	
	class HealthPowerUP extends AnimatedSprite {
		private static final long serialVersionUID = 1990496082110164147L;
		
		public HealthPowerUP(int h, int x, int y) {
			setID(h);
			setLocation(x, y);
			setSpeed(0, 0.02);
			setImages(healthPowerUpImages);
			setAnimate(true);
			setAnimationFrame(0, 12);
			setAnimationTimer(new Timer(100));
		}
		
		public void update(long t) {
			if(getAnimationTimer().action(t)) updateAnimation();
			updateMovement();
		}
		
	}


	/**
	 * Standard bullet sprite.  Starts at a leader and continues onward.
	 * @author Bryan
	 *
	 */
	class FireBulletProjectile extends Sprite {
		private static final long serialVersionUID = -6687341667401564427L;
		
		//the projectile is bound to the physics of the leader
		public FireBulletProjectile(Sprite leader) { 
			setImage(Button__Space__Image); 
			setLocation(leader.getX()+leader.getWidth()/2-7, leader.getY()-leader.getHeight()/2); 
			
			setSpeed(leader.getHorizontalSpeed()/2, leader.getVerticalSpeed() - 0.4); 
			setID(5);
			}
		private double x;
		private double y;
		public void update(long t) {
			x = getX();
			y = getY();
			updateMovement();
			
			if(x<-10 || x>810 || y < -20 || y > 620) setActive(false); //if off screen then destroy
		}
	}
		

	class LaserPlayerProjectile extends Sprite {
		private static final long serialVersionUID = 4290699595573604251L;
		
		public LaserPlayerProjectile() {
			setImage(Button__A__Image);
			setSpeed(0, -2.0);
			setID(2);
			setLocation(player.getX()+player.getWidth()/2-7, player.getY()-player.getHeight());
		}
		private double x;
		private double y;
		public void update(long time) {
			updateMovement();
			
			x = getX();
			y = getY();
			if(x>820 || x<-20 || y>620 || y<-20) setActive(false);
		}
	}
	
	class Missile1 extends Sprite {
		private static final long serialVersionUID = -3761470348980977973L;
		
		int targetX;
		int targetY;
		Sprite[] targets;
		Sprite target;
		
		public Missile1() {
			setImage(Button__S__Image);
			setSpeed( 0, -0.0005);
			setID(5);
			setLocation(player.getX()+player.getWidth()/2-7, player.getY()-player.getHeight());
			if(enemy_group.getActiveSprite()!=null) {
				targets = enemy_group.getSprites();
				target = enemy_group.getActiveSprite();
			}
			for(int i=0; i<targets.length; i++) {
				if(targets[i]==null) continue;
				//get closest
				if(target!=null && Math.abs(targets[i].getX()-getX())<Math.abs(target.getX()-getX())) target = targets[i];
			}
		}
		
		private double x;
		private double y;
		public void update(long t) {
			if(enemy_group.getActiveSprite()!=null) {
				targets = enemy_group.getSprites();
				target = enemy_group.getActiveSprite();
			}
			for(int i=0; i<targets.length; i++) {
				if(targets[i]==null) continue;
				//get closest
				if(target!=null && Math.abs(targets[i].getX()-getX())<Math.abs(target.getX()-getX())) target = targets[i];
			}
			updateMovement(t);
			if(getVerticalSpeed()>=-0.4) setVerticalSpeed(getVerticalSpeed()*1.5);
			x = getX();
			y = getY();
			if(target!=null) {

				setHorizontalSpeed(
						target.getX()<x ? -0.1 : 0.1
					);
			}
			enemy_group.removeInactiveSprites();
			enemy_group.removeImmutableSprites();
			if(x>820 || x<-20 || y>620 || y<-20) setActive(false);
		}
	}
	
	
	class PlasmaBall extends Sprite {
		private static final long serialVersionUID = -5871469306803637288L;

		int lifeFrame = 0;
		double amplitude = 0.0;
		double launchSpeed = 0;
		public PlasmaBall() {
			setLocation(player.getX(), player.getY());
			amplitude = player.getHorizontalSpeed();
			setID(80);
			setImage(Button__D__Image);
			launchSpeed = player.getHorizontalSpeed();
		}
		double x;
		double y;
		double xSpeed;
		public void update(long t) {
			xSpeed = amplitude*2*Math.sin(0.25*lifeFrame) + launchSpeed/3;
			lifeFrame ++;
			setSpeed(xSpeed, -0.3);
			updateMovement(t);
			
			if(x>820 || x<-20 || y<-20 || y>620) setActive(false);
		}
	}

	
	
	
	
	/**
	 * The Stadard Enemy Sprite.  Starts at the top and comes down.
	 * @author Bryan
	 *
	 */
	class WeakSlideEnemy extends AnimatedSprite {
		private static final long serialVersionUID = -706351153332993809L;
		
		Sprite target = player_group.getActiveSprite();
		public WeakSlideEnemy() {
			setLocation(Math.random()*bsGraphics.getSize().getWidth(), -100);
			setImages(weakEnemyImages);
			setAnimate(true);
			setLoopAnim(true);
			setAnimationFrame(0, 1);
			setAnimationTimer(new Timer(750));
			setBackground(background);
			setSpeed(0,0.05);
			setID(10);
		}
		double x; double targetX;
		double y; double targetY;
		public void update(long t) {
			if(target == null) target = player_group.getActiveSprite();
			else {
				targetX = target.getX();
				targetY = target.getY();
				if(targetX<x) setHorizontalSpeed(-0.002);
				else if(targetX>x) setHorizontalSpeed(0.02);
				else setHorizontalSpeed(0);
				if(targetY<y && targetY>550) setVerticalSpeed(-0.05);
				else if(targetY>y) setVerticalSpeed(0.05);
				else setVerticalSpeed(0.05);
			}
			x = getX();
			y = getY();
			updateMovement(t);
			if(getAnimationTimer().action(t)) updateAnimation();
			if(x<0||x>800) setHorizontalSpeed(-1*getHorizontalSpeed());
			if(y<-200||y>800) setActive(false);
		}
	}
	
	
	class PlayerEnemyCollision extends BasicCollisionGroup {
		
		public PlayerEnemyCollision() {
			pixelPerfectCollision = true;
		}

		int playerHealth;
		int enemyHealth;
		@Override
		public void collided(Sprite player, Sprite enemy) {
			
			playerHealth = player.getID();
			enemyHealth = enemy.getID();
			
			playerHealth -= enemyHealth;
			enemyHealth -= player.getID();
			
			player.setID(playerHealth);
			enemy.setID(enemyHealth);
			
			if(enemyHealth <= 0) enemy.setActive(false);
			if(playerHealth <= 0) player.setActive(false);
			
		}
		
	}
	class PlayerProjectileEnemyCollision extends BasicCollisionGroup {

		public PlayerProjectileEnemyCollision() {
			pixelPerfectCollision = true;
		}
		private int projDamage;
		private int enemyHealth;
		@Override
		public void collided(Sprite proj, Sprite enemy) {
			
			
			projDamage = proj.getID();
			enemyHealth = enemy.getID();
			
			
			enemyHealth -= projDamage;
			projDamage -= enemy.getID();
			
			if(Math.random()<=0.01 && enemyHealth<=0) powerup_group.add(new HealthPowerUP(10, (int)enemy.getX(), (int)enemy.getY()));
			
			enemy.setID(enemyHealth);
			proj.setID(projDamage);
			
			if(enemyHealth<=0) enemy.setActive(false);
			if(projDamage<=0) proj.setActive(false);
		}
		
	}
	
	class SplashEnemy_Collision extends AdvanceCollisionGroup {
		
		public SplashEnemy_Collision() {
			pixelPerfectCollision = true;
		}

		int projDamage = 0;
		int enemyHealth = 0;
		@Override
		public void collided(Sprite proj, Sprite enemy) {
			
			projDamage = proj.getID();
			enemyHealth = enemy.getID();
			
			
			enemyHealth -= projDamage;
			projDamage -= enemy.getID();
			
			int x = (int)enemy.getX();
			int y = (int)enemy.getY();
			
			
			if(Math.random()<=0.01 && enemyHealth<=0) powerup_group.add(new HealthPowerUP(10, (int)enemy.getX(), (int)enemy.getY()));
			if(projDamage<=0) player_projectile_group.add(new smallSplashDamage(25, x, y));
			
			enemy.setID(enemyHealth);
			proj.setID(projDamage);
			
			
			if(enemyHealth<=0) enemy.setActive(false);
			if(projDamage<=0) proj.setActive(false);
			
		}
		
	}
	
	class smallSplashDamage extends AnimatedSprite {
		private static final long serialVersionUID = 4945879522285700194L;

		public smallSplashDamage(int damage, int x, int y) {
			setImages(smallSplashImages);
			setID(damage);
			setSpeed(0, 0);
			setAnimationTimer(new Timer(10));
			setAnimationFrame(10, 20);
			setAnimate(true);
			setLocation(x,y);
		}
		
		public void update(long t) {
			if(getAnimationTimer().action(t)) updateAnimation();
			if(getFrame()>15) setActive(false);
		}
	}
	
	
	class PowerUpPlayer_Collision extends AdvanceCollisionGroup {

		public PowerUpPlayer_Collision() {
			pixelPerfectCollision = true;
		}
		
		@Override
		public void collided(Sprite pler, Sprite up) {
			if(up instanceof HealthPowerUP) {
				if(pler.getID()+up.getID()<player.getMaxHealth()) pler.setID(pler.getID() + up.getID());
				else player.setHealth((int) player.getMaxHealth());
				up.setActive(false);
			}
		}
		
	}
	
	
	
	
	public static void main(String[] args) {
		GameLoader game = new GameLoader();
		game.setup(new MyGame(), new Dimension(800,600), false);
		game.start();
	}
	
}

