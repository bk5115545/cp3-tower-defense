public class Tile {

	Sprite onTop;
	Effect effect;
	BufferedImage background;

	public Tile(BufferedImage image) {
		background = image;
	}

	public void set(Sprite monster) {
		onTop = monster;
	}

	public void clear() {
		onTop = null;
	}

	public boolean isClear() {
		return onTop==null;
	}

	public void applyEffect() {
		if(onTop!=null) {
			onTop.applyEffect(effect);
		}
	}

	public void setEffect(Effect e) {
		effect = e;
	}

	public void clearEffect() {
		effect = null;
	}

}
