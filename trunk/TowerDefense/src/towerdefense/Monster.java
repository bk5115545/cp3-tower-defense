/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package towerdefense;

/**
 *
 * @author lovinz
 */
import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Timer;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Monster extends AnimatedSprite {
	
    public Monster(BufferedImage[] images, int x, int y) 
    {
        setX(x);
        setY(y);
        setImages(images);
        setAnimate(true);
        setAnimationFrame(0, images.length);
        setAnimationTimer(new Timer(500)); //update every 500ms            
    }

    @Override
    public void update(long t) {
            updateMovement(t);
            if(getAnimationTimer().action(t)) //if time to update animation
                    updateAnimation();			  //update
    }    
    
    public void render(long time) {}

}

