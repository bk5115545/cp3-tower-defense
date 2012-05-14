import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;

public class SpriteGroup {

	private String name;
	private ArrayList<Sprite> children = new ArrayList<Sprite>();
	public SpriteGroup(String string) {
		name = string;
	}
	public void add(Sprite s) {
		children.add(s);
	}
	public void render(Graphics2D g) {
		
		
		
	}
	public void update(long time) {
		
		
		
	}
	public Sprite getActiveSprite() {
		try {
			return children.get(0);
		} catch (Exception e) {return null;}
	}
	public Sprite[] getSprites() {
		return Arrays.;
	}

}
