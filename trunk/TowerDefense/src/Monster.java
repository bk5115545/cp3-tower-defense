import com.golden.gamedev.object.Sprite;


@SuppressWarnings("serial")
public abstract class Monster extends Sprite {
	
	public Monster() {}
	
	public abstract void update(long time);
	public abstract void render(long time);

}
