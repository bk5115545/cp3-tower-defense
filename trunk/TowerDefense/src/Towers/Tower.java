package Towers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Main.Environment;
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
	
	int x,y; //the rendering X and Y instead of the underlying XY

	public Tower(BufferedImage image, BufferedImage projImage, int pSpeed, Environment e)
	{
		setImage(image); 
		env = e;
		projectileSpeed = pSpeed;
		projectileImage = projImage;
	}

	public void render(Graphics2D g, int x, int y) {
		this.x = x;
		this.y = y;
		g.drawImage(getImage(), x, y, null);
		font.drawString(g, towerLevel+"", x, y);
		
		if(renderInformation) {
			g.drawImage(env.getGame().getInformationRenderImage(), (int)x, (int)y, null);
			
			font.drawString(g, "Damage: "+attackPower, (int)x, (int)y);
			font.drawString(g, "Attack Speed: "+attackSpeed, (int)x, (int)y+10);
			font.drawString(g, "Range: "+attackRange, (int)x, (int)y+20);
			font.drawString(g, "Upgrade Cost: "+((int)Math.pow(2, towerLevel)*100+100), (int)x, (int)y+30);
			
			
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
		if(env.getGame().bsInput.getMouseX()>=x && env.getGame().bsInput.getMouseX()<=x+getImage().getWidth() && env.getGame().bsInput.getMouseY()>=y && env.getGame().bsInput.getMouseY()<=y+getImage().getHeight()) {
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