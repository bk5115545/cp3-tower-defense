package Main;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Monsters.Monster;
import Towers.Tower;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;

public class Environment {
	
	private int lives = 30;
	
	
	
	private SpriteGroup monsters;
	SpriteGroup towers;
	SpriteGroup projectiles;
	
	Projectile_MonsterCollision proj_monsterCollision;
	
	private Game game;
	
	Timer waitTimer = new Timer(5000); //time in between waves
	Timer waveTimer; //how fast should we add monsters to the screen?
	Timer clock = new Timer(1000);
	boolean adding = false;
	
	ArrayList<Sprite> waveQueue = new ArrayList<Sprite>();
	
	Timer infinite = new Timer(1000);
	Timer infiniteIncrease = new Timer(5000);
	
	public Environment(Game g) {
		waveTimer = new Timer(1000);
		
		setMonsters(new SpriteGroup("Monsters"));
		towers = new SpriteGroup("Towers");
		projectiles = new SpriteGroup("Projectiles");
		setGame(g);
		
		proj_monsterCollision = new Projectile_MonsterCollision();
		proj_monsterCollision.pixelPerfectCollision = true;  //commented until we calibrate projectile aiming
		proj_monsterCollision.setCollisionGroup(projectiles, getMonsters());
	}
	
	
	public void update(long time) {
		
		if(waitTimer.action(time)) {
			adding  = false;
		}
		
		if(clock.action(time)) {
			getGame().seconds++;
		}
		
		
		if(infinite.action(time) && getGame().endlessMode==true) {
			getMonsters().add(new Monster(getGame().mobVanilla, this));
			
		}
		
		if(infiniteIncrease.action(time) && getGame().endlessMode==true) {
			infinite = new Timer((int)(infinite.getDelay()*0.99));
			getGame().setDifficulty(getGame().getDifficulty() * 1.01);
		}
		
		
		if(getGame().bsInput.isMouseReleased(3)) {
			getMonsters().add(new Monster(getGame().mobVanilla, this));
		}
		
		proj_monsterCollision.checkCollision();
		
		getMonsters().update(time);
		
		towers.update(time);
		projectiles.update(time);
		
		if(waveTimer.action(time)) {
			//monsters.add(new Monster(game.mobVanilla, game.getMouseX(), game.getMouseY(), this));
		}
		
	}
	
	
	public void render(Graphics2D g) {
		
		getMonsters().render(g);
		projectiles.render(g);
		
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
	public void addTower(Tower t) {
		towers.add(t);
	}
	public void addMonster(Monster m) {
		getMonsters().add(m);
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
			
			//if(damage-health<1) //bullets go through monsters until they have no damage left
				s1.setActive(false); //or it is one hit per 
			
			
			if(health-damage<1) {
				s2.setActive(false);
				getGame().setMoney(getGame().getMoney() + 10);
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


	public void setMonsters(SpriteGroup monsters) {
		this.monsters = monsters;
	}


	public SpriteGroup getMonsters() {
		return monsters;
	}


	public void setGame(Game game) {
		this.game = game;
	}


	public Game getGame() {
		return game;
	}
}