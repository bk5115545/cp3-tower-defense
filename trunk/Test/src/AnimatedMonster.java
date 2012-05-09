import java.awt.image.BufferedImage;

import com.golden.gamedev.object.AnimatedSprite;
import com.golden.gamedev.object.Timer;


@SuppressWarnings("serial")
public class AnimatedMonster extends AnimatedSprite {

	public AnimatedMonster(BufferedImage[] images) {
		setImages(images);
		setAnimate(true);
		setAnimationFrame(0, images.length);
		setAnimationTimer(new Timer(500)); //update every 500ms
		
		//has methods like
		/*
		setLocation(0.0, 0.0);
		setHorizontalSpeed(1);
		setVerticalSpeed(1);
		getHorizontalSpeed();
		getVerticalSpeed();
		*/
		//for updating movement
	}
	
	@Override
	public void update(long t) {
		updateMovement(t);
		if(getAnimationTimer().action(t)) //if time to update animation
			updateAnimation();			  //update
	}
	
}
