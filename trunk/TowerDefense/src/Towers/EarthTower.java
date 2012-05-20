package Towers;

/**
 * DECLARED VARIABLES
 
    int attackPower = 50;
	int attackSpeed = 500;
	int attackRange = 200; //pixels
	double projectileSpeed = 0.1; //time inbetween shots in ms
	int towerLevel = 1;
	int saleCost = 0;
	int targetMode = 1; //1 for closest, (nothing else implemented yet)
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

public class EarthTower extends Tower {
	private static final long serialVersionUID = -7970861311400699359L;

	
	public EarthTower(BufferedImage image, BufferedImage projImage, int pSpeed, Environment e) {
		super(image, projImage, pSpeed, e);
	}

	@Override
	public void attack() {
		currentTarget = (Monster) getTarget();
		if(currentTarget!=null)
			env.addProjectile(new Projectile(projectileImage, getX(), getY(), currentTarget.getX(), currentTarget.getY(), 1, attackPower, env.getGame()));
	}

	@Override
	public void upgrade(Tower selectedTower) {
		if((env.getGame().getMoney()-Math.pow(2, towerLevel)*100)+100>=0) {
			towerLevel++;
			attackPower += 50;
			env.getGame().setMoney((int) (env.getGame().getMoney()-Math.pow(2, towerLevel-1)*100)+100);
		}
	}

}
