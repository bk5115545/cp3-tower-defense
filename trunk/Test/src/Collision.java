import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.CollisionGroup;


public class Collision extends CollisionGroup {

	@Override
	public void collided(Sprite s1, Sprite s2) {
		s1.setActive(false);
		s2.setActive(false);
	}

}
