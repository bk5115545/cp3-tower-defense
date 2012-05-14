import java.awt.image.BufferedImage;


public class Sprite {
	
	private double x = 0;
	private double y = 0;
	private double vx = 0;
	private double vy = 0;
	private boolean active = true;
	
	private int id = 0;
	
	private BufferedImage image;
	
	public int getID() {
		return id;
	}
	public void setID(int i) {
		id = i;
	}
	
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setImage(BufferedImage b) {
		image = b;
	}
	
	public double getHorizontalSpeed() {
		return vx;
	}
	
	public double getVerticalSpeed() {
		return vy;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setSpeed(double x, double y) {
		vx = x;
		vy = y;
	}
	
	public void setHorizontalSpeed(double s) {
		vx = s;
	}
	
	public void setVerticalSpeed(double s) {
		vy = s;
	}
	
	public void updateMovement() {
		x += vx;
		y += vy;
	}
	
	public void setActive(boolean b) {
		active = b;
	}
	
	public boolean getActive() {
		return active;
	}

}
