package Main;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Levels.Level;
import Monsters.Monster;
import Towers.Tower;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.collision.AdvanceCollisionGroup;

public class Environment {

	private int lives = 0;

	private int levelIndex = 0;
	private Level currentLevel;
	private ArrayList<Level> levels = new ArrayList<Level>();

	private SpriteGroup monsters;
	private SpriteGroup towers;
	private SpriteGroup projectiles;

	private Projectile_MonsterCollision proj_monsterCollision;

	private Game game;

	private Timer clock = new Timer(1000);


	public Environment(Game g) {
		monsters = new SpriteGroup("Monsters");
		towers = new SpriteGroup("Towers");
		projectiles = new SpriteGroup("Projectiles");
		game = g;

		proj_monsterCollision = new Projectile_MonsterCollision();

		proj_monsterCollision.setCollisionGroup(projectiles, monsters);
	}


	public void update(long time) {

		if(clock.action(time)) {
			getGame().setSeconds(getGame().getSeconds() + 1);
		}

		getProj_monsterCollision().checkCollision();

		getMonsters().update(time);

		towers.update(time);
		projectiles.update(time);
		if(game.endlessMode) {
			currentLevel = game.getEndlessLevel();
		} else {
			try {
				if(currentLevel.getWaveRemaining()<1) {
					nextLevel();
				}
			} catch (Exception e) {}
		}
		currentLevel.update(time);
	}


	public void render(Graphics2D g) {
		game.getGui().getFont().drawString(g, "Level: " + (levelIndex-1), 10,30);
		monsters.render(g);
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

	public void setProj_monsterCollision(Projectile_MonsterCollision proj_monsterCollision) {
		this.proj_monsterCollision = proj_monsterCollision;
	}

	public Projectile_MonsterCollision getProj_monsterCollision() {
		return proj_monsterCollision;
	}

	public int getMovementInstructionSetSize() {
		return currentLevel.getMovementInstructionsSize();
	}

	public void addLevel(Level l) {
		levels.add(l);
	}

	public void nextLevel() {
		try {
			currentLevel = levels.get(levelIndex);
			levelIndex++;
		} catch (Exception e) {}
	}







	public class Projectile_MonsterCollision extends AdvanceCollisionGroup {

		public Projectile_MonsterCollision() {
			pixelPerfectCollision = true;
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

			s1.setActive(false);

			if(health-damage<1) {
				s2.setActive(false);
				game.setMoney(game.getMoney() + 10);
			}


			getGroup1().removeInactiveSprites();
			getGroup2().removeInactiveSprites();
		}

	}


}