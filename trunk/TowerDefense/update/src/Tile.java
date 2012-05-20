import java.awt.Graphics2D;

import com.golden.gamedev.object.Sprite;


public class Tile extends Sprite {
	private static final long serialVersionUID = 1207070338475312736L;
	
	Sprite sprite;
	
	boolean canPlaceOn = false;
	
	public Tile(boolean placeable, int x, int y) {
		canPlaceOn = placeable;
		setX(x);
		setY(y);
	}
	
	
	@Override
	public void update(long time) {
		if(sprite != null) sprite.update(time);
	}
	
	@Override
	public void render(Graphics2D g) {
		if(sprite != null) sprite.render(g,(int)getX(),(int)getY());
	}
	
	public boolean canBuild() {
		return canPlaceOn && sprite==null;
	}
	
	public void addSprite(Sprite s) {
		sprite = s;
	}
}
