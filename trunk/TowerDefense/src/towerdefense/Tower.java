/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package towerdefense;


import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Timer;
import java.awt.image.BufferedImage;

/**
 *
 * @author lovinz
 */

@SuppressWarnings("serial")
public class Tower extends AnimatedSprite {
    
    int attackPower = 0;
    int attackSpeed = 0;
    int attackRange = 0;
    int projectileSpeed = 0;
    int towerLevel = 1;
    int towerCost = 0;
    int saleCost = 0;
    int numberOfKills = 0;
    int shotsFired = 0;
    int targetMode = 1;
    Monster currentTarget = null;
    Environment env = null;
    
    public Tower(BufferedImage[] images, Environment e, int pSpeed)
    {
        setImages(images);
        setAnimate(true);
        setAnimationFrame(0, images.length);
        setAnimationTimer(new Timer(500)); //update every 500ms        
        env = e;
        projectileSpeed = pSpeed;
    }

    public Tower(BufferedImage[] images, int x, int y, int pSpeed, Environment e)
    {
        setImages(images);
        setAnimate(true);
        setAnimationFrame(0, images.length);
        setAnimationTimer(new Timer(500)); //update every 500ms        
        env = e;
        setX(x);
        setY(y);
        projectileSpeed = pSpeed;
    }    
    
    @Override
    public void update(long t) {
            updateMovement(t);
            if(getAnimationTimer().action(t)) //if time to update animation
                    updateAnimation();			  //update
    }    
    
    public Monster currentTarget() {return currentTarget; }
    public int getProjectileSpeed() {return projectileSpeed; }
    
    public void setCurrentTarget(Monster m) { currentTarget = m; }
}