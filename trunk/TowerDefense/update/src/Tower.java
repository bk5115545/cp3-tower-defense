import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.Timer;

@SuppressWarnings("serial")
public class Tower extends Sprite {
    
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
    
    boolean fire = false; //sync lock for firing
    
    public Tower(BufferedImage image, BufferedImage projImage, int x, int y, int pSpeed, Environment e)
    {
        setImage(image); 
        setX(x);
        setY(y);
        env = e;
        projectileSpeed = pSpeed;
        projectileImage = projImage;
    }
    
    @Override
    public void update(long t) {
    	
    	//find target
    	
    	
    	//if its time to shoot :: shoot
    	if(fireRate.action(t)) {
    		fire = true;
    	}
    	if(fire) {
    		fire = false;
    		currentTarget = (Monster) getTarget();
    		if(currentTarget!=null)
    			env.addProjectile(new Projectile(projectileImage, getX(), getY(), currentTarget.getX(), currentTarget.getY(), 1, attackPower, env.game));
    	}
    	
    }    
    
    private Sprite getTarget() {
    	for(Sprite i: env.monsters.getSprites()) {
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

	public void upgrade(Tower selectedTower) {
		towerLevel++;
		attackSpeed/=2;
		fireRate = new Timer(attackSpeed);
		attackPower+=25;
	}
}