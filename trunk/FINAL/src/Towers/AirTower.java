package Towers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Environment;
import Main.Projectile;
import Monsters.Monster;

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

public class AirTower extends Tower {
	private static final long serialVersionUID = -2988626192880657726L;
	
	int pentalyLessTargets = 1;
	Object clearLock = new Object(); //synchronizing target clearing
	
	public AirTower(BufferedImage image, BufferedImage projImage, int pSpeed, Environment e) {
		super(image, projImage, pSpeed, e);
		
		setAttackPower(25);
		setAttackRange(150);
	}

	ArrayList<Monster> inRange = new ArrayList<Monster>();
	int size;
	@Override
	public void attack() {
		synchronized (clearLock) {
			for(int i=0; i<getEnv().getMonsters().getSize(); i++) {
				if(Math.sqrt(Math.pow(getX()-getEnv().getMonsters().getSprites()[i].getX(), 2) + Math.pow(getY()-getEnv().getMonsters().getSprites()[i].getY(), 2))<getAttackRange()) {
					inRange.add((Monster)getEnv().getMonsters().getSprites()[i]);
				}
			}
			size = inRange.size();
			for(Monster m : inRange) {
				getEnv().addProjectile(new Projectile(getProjectileImage(), getX(), getY(), m.getX(), m.getY(), 1, (getAttackPower()+getAttackPower()*pentalyLessTargets)/size, getEnv().getGame()));
			}
		}
			
		synchronized (clearLock) {
			//this might take a while so fork()
			new Thread(new Runnable() {
				@Override
				public void run() {
					inRange.clear();
				}
			}).start();
		}
	}

	@Override
	public void upgrade(Tower selectedTower) {
		if((getEnv().getGame().getMoney()-Math.pow(2, getTowerLevel())*100)+100>=0) {
			setTowerLevel(getTowerLevel() + 1);
			if(getTowerLevel()%2==0) {
				pentalyLessTargets+=2;
				setAttackRange(getAttackRange() + 25);
			}
			setAttackPower(getAttackPower() + 10);
			getEnv().getGame().setMoney((int) (getEnv().getGame().getMoney()-Math.pow(2, getTowerLevel()-1)*100)+100);
		}
	}

}
