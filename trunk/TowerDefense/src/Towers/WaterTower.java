package Towers;

/**
 * DECLARED VARIABLES

    int attackPower = 50;
	int attackSpeed = 500;
	int attackRange = 200; //pixels
	double projectileSpeed = 0.1; //time inbetween shots in ms
	int towerLevel = 1;
	int saleCost = 0;
	int targetMode = 1;
	Timer fireRate = new Timer(attackSpeed); // shoot every 0.5s to start with
	Monster currentTarget = null;
	Environment env;
	BufferedImage projectileImage;

	SystemFont font = new SystemFont(new Font("Arial", Font.BOLD|Font.PLAIN, 32),Color.BLUE);

	boolean fire = false; //sync lock for firing
 */


import java.awt.image.BufferedImage;

import Main.Environment;
import Main.Projectile;
import Monsters.Monster;

public class WaterTower extends Tower {
	private static final long serialVersionUID = -655304973147668876L;

	int splashRadius = 20;
	int splashDamage = 10;

	public WaterTower(BufferedImage image, BufferedImage projImage, int pSpeed, Environment e) {
		super(image, projImage, pSpeed, e);
		
		attackPower = 25;
		attackRange = 150;
	}

	@Override
	public void attack() {
		currentTarget = (Monster) getTarget();
		if(currentTarget!=null){
			env.addProjectile(new Projectile(projectileImage, getX(), getY(), currentTarget.getX(), currentTarget.getY(), 1, attackPower+splashDamage, env.getGame()));
			for(int i=0; i<env.getMonsters().getSize(); i++) {
				if(Math.sqrt(Math.pow(currentTarget.getX()-env.getMonsters().getSprites()[i].getX(), 2) + Math.pow(currentTarget.getY()-env.getMonsters().getSprites()[i].getY(), 2))<splashRadius) {
					env.getProj_monsterCollision().collided(new Projectile(splashDamage),env.getMonsters().getSprites()[i]);
				}
			}
		}
	}


	@Override
	public void upgrade(Tower selectedTower) {
		if((int) (env.getGame().getMoney()-Math.pow(2, towerLevel)*100)+100>=0) {
			towerLevel++;
			env.getGame().setMoney((int) (env.getGame().getMoney()-Math.pow(2, towerLevel-1)*100)+100);
			splashRadius+=20;
			if(towerLevel%2==0) splashDamage *= 2;
		}
	}

}
