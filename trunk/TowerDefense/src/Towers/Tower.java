package Towers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Main.Environment;
import Main.Projectile;
import Monsters.Monster;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.Timer;
import com.golden.gamedev.object.font.SystemFont;

@SuppressWarnings("serial")
public abstract class Tower extends Sprite {

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
	
	SystemFont font = new SystemFont(new Font("Arial", Font.BOLD|Font.PLAIN, 12),Color.BLUE);

	boolean fire = false; //sync lock for firing
	boolean renderInformation = false;

	public Tower(BufferedImage image, BufferedImage projImage, int pSpeed, Environment e)
	{
		setImage(image); 
		env = e;
		projectileSpeed = pSpeed;
		projectileImage = projImage;
	}

	public void render(Graphics2D g, int x, int y) {
		g.drawImage(getImage(), x, y, null);
		font.drawString(g, towerLevel+"", x, y);
		
		if(renderInformation) {
			g.drawImage(env.getGame().getInformationRenderImage(), (int)getX()-30, (int)getY()-30, null);
			
			font.drawString(g, "Damage: "+attackPower, (int)getX()-29, (int)getY()-9);
			font.drawString(g, "Attack Speed: "+attackSpeed, (int)getX()-29, (int)getY()-19);
			font.drawString(g, "Range: "+attackRange, (int)getX()-29, (int)getY());
			font.drawString(g, "Upgrade Cost: "+((int)Math.pow(2, towerLevel-1)*100+100), (int)getX()+1, (int)getY()-29);
			
			
		}
	}

	@Override
	public void update(long t) {
		if(fireRate.action(t)) {
			fire = true;
		}
		if(fire) {
			attack();
			fire = false;
		}
		if(env.getGame().bsInput.getMouseX()>=this.getX()-getImage().getWidth()/2.95 && env.getGame().bsInput.getMouseX()<=this.getX()+getImage().getWidth()/2.5) {
			renderInformation = true;
		} else renderInformation = false;
	}   

	public abstract void attack();

	public Sprite getTarget() {
		for(Sprite i: env.getMonsters().getSprites()) {
			if(i==null) continue;
			if(Math.sqrt(Math.pow(getX()-i.getX(), 2)+Math.pow(getY()-i.getY(), 2))<attackRange) {
				return i;
			}
		}
		return null;
	}

	public Monster currentTarget() {return currentTarget; }
	public double getProjectileSpeed() {return projectileSpeed; }

	public void setCurrentTarget(Monster m) { currentTarget = m; }

	public abstract void upgrade(Tower selectedTower);
}