/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package towerdefense;

import com.golden.gamedev.object.Sprite;
import java.awt.image.BufferedImage;

/**
 *
 * @author lovinz
 */
public class Projectile extends Sprite{
    
    Tower shooter = null;
    double theta = 0;
    
    public Projectile(BufferedImage image, Tower t)
    {
        shooter = t;
        theta = Math.atan2(shooter.currentTarget().getX()-getX(), shooter.currentTarget().getY()-getY());
        
        setImage(image);      
        setX(shooter.getX());
        setY(shooter.getY());
        
        setSpeed(shooter.getProjectileSpeed()*Math.cos(theta), shooter.getProjectileSpeed()*Math.sin(theta));
    }
    
    @Override
    public void update(long t) {
        theta = angle(shooter.currentTarget().getX()-getX(), shooter.currentTarget().getY()-getY());
        //theta = Math.atan2(shooter.currentTarget().getX()-getX(), shooter.currentTarget().getY()-getY());
        setSpeed(shooter.getProjectileSpeed()*Math.cos(theta), shooter.getProjectileSpeed()*Math.sin(theta));
        System.out.println("Theta: " + theta + " X Speed: " + getHorizontalSpeed() + " Y Speed: " + getVerticalSpeed());
        System.out.println("Calculated x: " + shooter.getProjectileSpeed()*Math.cos(theta) + " Calculated y: " + shooter.getProjectileSpeed()*Math.sin(theta));
        System.out.println("Theta test: " + Math.atan2(100,0));
        updateMovement(t);
    }    
    
    private double angle(double x, double y)
    {
        
        if( x != 0 && y != 0) return Math.atan2(x,y);
        
        if(x == 0)
        {
            if(y > 0) return Math.PI/2.0;
            else if(y < 0) return 3.0*Math.PI/2.0;
        }
        if(y == 0)
        {
            if(x > 0) return 0;
            else if(x < 0) return Math.PI;
        }
        
        return 0;
    }
    
}
