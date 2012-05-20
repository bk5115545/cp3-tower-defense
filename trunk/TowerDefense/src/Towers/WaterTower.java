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
import java.util.ArrayList;

import Main.Environment;
import Main.Projectile;
import Monsters.Monster;

public class WaterTower extends Tower {
	private static final long serialVersionUID = -655304973147668876L;

	int splashRadius = 10;

	public WaterTower(BufferedImage image, BufferedImage projImage, int pSpeed, Environment e) {
		super(image, projImage, pSpeed, e);
	}

	
	ArrayList<Monster> splashHits = new ArrayList<Monster>();
	@Override
	public void attack() {
		currentTarget = (Monster) getTarget();
		if(currentTarget!=null){
			//for(Monster m : env.getMonsters()) {
				
			//}
			env.addProjectile(new Projectile(projectileImage, getX(), getY(), currentTarget.getX(), currentTarget.getY(), 1, attackPower, env.getGame()));
		}
	}


	@Override
	public void upgrade(Tower selectedTower) {
		if((int) (env.getGame().getMoney()-Math.pow(2, towerLevel)*100)+100>=0) {
			towerLevel++;
			env.getGame().setMoney((int) (env.getGame().getMoney()-Math.pow(2, towerLevel-1)*100)+100);
			splashRadius+=10;
		}
	}

}
