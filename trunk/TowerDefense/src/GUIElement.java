import java.awt.Graphics2D;
import com.golden.gamedev.object.Sprite;


public abstract class GUIElement extends Sprite {
	private static final long serialVersionUID = -1436043842502044578L;
	
	public GUIElement() {}
	
	public abstract void update(long time);
	public abstract void render(Graphics2D g);

}
