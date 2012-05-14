import java.awt.image.BufferedImage;


public class AnimatedSprite extends Sprite {

	private BufferedImage[] images;
	private int frame_int = 0;
	private int frame_low;
	private int frame_high;
	private Timer timer;
	private boolean isAnimate = false;
	private boolean isLoopAnim = false;
	
	public void setImages(BufferedImage[] images) {
		this.images = images;
	}
	
	public void setAnimationFrame(int low, int high) {
		frame_int = low;
		frame_low = low;
		frame_high = high;
	}
	
	public void setAnimationTimer(Timer t) {
		timer = t;
	}
	
	public void setAnimate(boolean a) {
		isAnimate = a;
	}
	
	public void setLoopAnim(boolean a) {
		isLoopAnim = a;
	}
	
	public Timer getAnimationTimer() {
		return timer;
	}
	
	public void updateAnimation() {
		if(frame_int<frame_high+1) {
			frame_int += 1;
			setImage(images[frame_int]);
		}
	}
	
}
