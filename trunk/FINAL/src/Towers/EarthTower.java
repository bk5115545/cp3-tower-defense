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
		
		setAttackPower(25);
		setAttackRange(150);
	}

	@Override
	public void attack() {
		setCurrentTarget((Monster) getTarget());
		if(getCurrentTarget()!=null)
			getEnv().addProjectile(new Projectile(getProjectileImage(), getX(), getY(), getCurrentTarget().getX(), getCurrentTarget().getY(), 1, getAttackPower(), getEnv().getGame()));
	}

	@Override
	public void upgrade(Tower selectedTower) {
		if((getEnv().getGame().getMoney()-Math.pow(2, getTowerLevel())*100)+100>=0) {
			setTowerLevel(getTowerLevel() + 1);
			setAttackPower(getAttackPower() + 50);
			getEnv().getGame().setMoney((int) (getEnv().getGame().getMoney()-Math.pow(2, getTowerLevel()-1)*100)+100);
		}
	}

}
