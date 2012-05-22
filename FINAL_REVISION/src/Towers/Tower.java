package Towers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.font.SystemFont;

import Main.Environment;
import Monsters.Monster;

@SuppressWarnings("serial")
public abstract class Tower extends Sprite {

	private int attackPower = 50;
	private int attackSpeed = 500;
	private int attackRange = 200; //pixels
	private double projectileSpeed = 0.1; //time inbetween shots in ms
	private int towerLevel = 1;
	private Timer fireRate = new Timer(getAttackSpeed()); // shoot every 0.5s to start with
	private Monster currentTarget = null;
	private Environment env;
	private BufferedImage projectileImage;
	
	private SystemFont font = new SystemFont(new Font("Arial", Font.BOLD|Font.PLAIN, 12),Color.BLUE);

	private boolean fire = false; //sync lock for firing
	private boolean renderInformation = false;
	
	private int x,y; //the rendering X and Y instead of the underlying XY

	public Tower(BufferedImage image, BufferedImage projImage, int pSpeed, Environment e)
	{
		setImage(image); 
		setEnv(e);
		projectileSpeed = pSpeed;
		setProjectileImage(projImage);
	}

	public void render(Graphics2D g, int x, int y) {
		this.x = x;
		this.y = y;
		g.drawImage(getImage(), x, y, null);
		font.drawString(g, getTowerLevel()+"", x, y);
		
		if(renderInformation) {
			g.drawImage(getEnv().getGame().getInformationRenderImage(), (int)x, (int)y, null);
			
			font.drawString(g, "Damage: "+getAttackPower(), (int)x, (int)y);
			font.drawString(g, "Attack Speed: "+getAttackSpeed(), (int)x, (int)y+10);
			font.drawString(g, "Range: "+getAttackRange(), (int)x, (int)y+20);
			font.drawString(g, "Upgrade Cost: "+((int)Math.pow(2, getTowerLevel())*100+100), (int)x, (int)y+30);
			
			
		}
	}

	@Override
	public void update(long t) {
		if(getFireRate().action(t)) {
			fire = true;
		}
		if(fire && getTarget()!=null) {
			attack();
			fire = false;
		}
		if(getEnv().getGame().bsInput.getMouseX()>=x && getEnv().getGame().bsInput.getMouseX()<=x+getImage().getWidth() && getEnv().getGame().bsInput.getMouseY()>=y && getEnv().getGame().bsInput.getMouseY()<=y+getImage().getHeight()) {
				renderInformation = true;
		} else renderInformation = false;
	}   

	public abstract void attack();

	public Sprite getTarget() {
		for(Sprite i: getEnv().getMonsters().getSprites()) {
			if(i==null) continue;
			if(Math.sqrt(Math.pow(getX()-i.getX(), 2)+Math.pow(getY()-i.getY(), 2))<getAttackRange()) {
				return i;
			}
		}
		return null;
	}

	public Monster currentTarget() {return getCurrentTarget(); }
	public double getProjectileSpeed() {return projectileSpeed; }

	public void setCurrentTarget(Monster m) { currentTarget = m; }

	public abstract void upgrade(Tower selectedTower);

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public Environment getEnv() {
		return env;
	}

	public void setProjectileImage(BufferedImage projectileImage) {
		this.projectileImage = projectileImage;
	}

	public BufferedImage getProjectileImage() {
		return projectileImage;
	}

	public void setTowerLevel(int towerLevel) {
		this.towerLevel = towerLevel;
	}

	public int getTowerLevel() {
		return towerLevel;
	}

	public Monster getCurrentTarget() {
		return currentTarget;
	}

	public void setAttackSpeed(double d) {
		this.attackSpeed = (int) d;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public void setFireRate(Timer fireRate) {
		this.fireRate = fireRate;
	}

	public Timer getFireRate() {
		return fireRate;
	}
}