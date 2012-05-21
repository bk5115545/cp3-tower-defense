package Main;
import Monsters.Monster;

import com.golden.gamedev.object.Sprite;
import java.awt.image.BufferedImage;


public class Projectile extends Sprite{
	private static final long serialVersionUID = 607394573486212907L;
	
    Monster currentTarget;
    int speed = 0;
    Game g;
    
    public Projectile(BufferedImage image, double x1, double y1, double x2, double y2, int speed, int damage, Game g)
    {
        this.g = g;
        this.speed = speed;
        double theta = 0;
        try {
        	theta = angle(x2-x1+image.getWidth()*3, y2-y1+image.getHeight()*3);  //weird because of calibration
        } catch (Exception e) { setActive(false); }
        
        setID(damage);
        
        setImage(image);      
        setX(x1-x1%g.scale.x/2);
        setY(y1-y1%g.scale.y/2);
        
        setSpeed(speed*Math.cos(theta), speed*Math.sin(theta));
    }
    
    //for splash checking
    public Projectile(int damage) {
    	setID(damage);
    }
    
    @Override
    public void update(long t) {
    	if((getX()>g.bsGraphics.getSize().width || getX()<0) && (getY()>g.bsGraphics.getSize().height || getY()<0)) setActive(false);
    	
        updateMovement(t);
    }     
    
    private double angle(double x, double y) {
        if( x != 0 && y != 0) return Math.atan2(y,x);
        if(x == 0) {
            if(y > 0) return Math.PI/2.0;
            if(y < 0) return 3.0*Math.PI/2.0;
        }
        if(y == 0) {
            if(x > 0) return 0;
            if(x < 0) return Math.PI;
        }
        return 0;
    }
    
}
