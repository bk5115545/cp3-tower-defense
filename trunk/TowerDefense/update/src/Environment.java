import java.awt.Graphics2D;
import java.util.ArrayList;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;

public class Environment {
	
	private int lives = 30;
	
	
	
	SpriteGroup monsters;
	SpriteGroup towers;
	SpriteGroup projectiles;
	
	Projectile_MonsterCollision proj_monsterCollision;
	
	Game game;
	
	Timer waitTimer = new Timer(5000); //time in between waves
	Timer waveTimer; //how fast should we add monsters to the screen?
	boolean adding = false;
	
	ArrayList<Sprite> waveQueue = new ArrayList<Sprite>();
	
	public Environment(Game g) {
		waveTimer = new Timer(1000);
		
		monsters = new SpriteGroup("Monsters");
		towers = new SpriteGroup("Towers");
		projectiles = new SpriteGroup("Projectiles");
		game = g;
		
		proj_monsterCollision = new Projectile_MonsterCollision();
		proj_monsterCollision.pixelPerfectCollision = true;  //commented until we calibrate projectile aiming
		proj_monsterCollision.setCollisionGroup(projectiles, monsters);
	}
	
	
	public void update(long time) {
		
		if(waitTimer.action(time)) {
			adding  = false;
		}
		
		if(game.bsInput.isMouseReleased(3)) {
			monsters.add(new Monster(game.mobVanilla, this));
		}
		
		proj_monsterCollision.checkCollision();
		
		monsters.update(time);
		
		towers.update(time);
		projectiles.update(time);
		
		if(waveTimer.action(time)) {
			//monsters.add(new Monster(game.mobVanilla, game.getMouseX(), game.getMouseY(), this));
		}
		
	}
	
	
	public void render(Graphics2D g) {
		
		monsters.render(g);
		towers.render(g);
		projectiles.render(g);
		
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
	public void addTower(Tower t) {
		towers.add(t);
	}
	public void addMonster(Monster m) {
		monsters.add(m);
	}
	
	
	
	
	class Projectile_MonsterCollision extends AdvanceCollisionGroup {

		public Projectile_MonsterCollision() {
			
		}
		
		//don't recreate ints every frame
		private int damage = 0;
		private int health = 0;
		@Override
		public void collided(Sprite s1, Sprite s2) {
			
			damage = s1.getID();
			health = s2.getID();
			s2.setID(health-damage);
			s1.setID(damage-health);
			
			if(damage-health<1) s1.setActive(false);
			
			
			if(health-damage<1) {
				s2.setActive(false);
				game.money += 10;
			}
			
			
			getGroup1().removeInactiveSprites();
			getGroup2().removeInactiveSprites();
		}
		
	}


	public void addLife() {
		lives++;
	}
	
	public  int getLives() {
		return lives;
	}

	public void loseLife() {
		lives--;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void nextWave() {
		ArrayList<String> names = LevelLoader.loadLevel(game.levelNumber);
		
		for(String i : names) {
			try {
				//wherever out classpath is       vvv
				Class monsterClass  = Class.forName(i + ".class");
				//this will error until we decide ^^^
				Object ojb = monsterClass.newInstance();  //the monster has been created (thank you reflection)
				ojb = monsterClass.cast(ojb);            //make it its corect type
				waveQueue.add((Sprite) ojb);			//add it as a sprite
			} catch (ClassNotFoundException e) {
				System.out.println("class not found.");
				e.printStackTrace();
			} catch (InstantiationException e) {
				System.out.println("monster takes arguements.");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("Y U no have prems?!?!"); //this better not ever happen
				e.printStackTrace();
			} catch (SecurityException e) {
				System.out.println("Y U STILL no have prems?!?!"); //again.....
				e.printStackTrace();
			}
		}
	}
	
}