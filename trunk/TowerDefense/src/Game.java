import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;


public class Game extends com.golden.gamedev.Game {
	
	int gridXCount = 8;
	int gridYCount = 8;
	Point scale; //click location checking
	Point[][] gridDefinitions; //click location checking
	Tile[][] grid; //everything visible
	BufferedImage[] backgrounds; //the level background loaded as an array for auto place-ability checking
	ImageBackground bkg;         //the rendered background
	int levelNumber = 1;
	ArrayList<Sprite> waveQueue;
	
	
	@Override
	public void initResources() {
		setFPS(60);
		grid = new Tile[gridXCount][gridYCount];
		backgrounds = getImages("maps/map"+levelNumber+".png",8,8);
		
		initGrid();
		waveQueue = new ArrayList<Sprite>();
	}

	@Override
	public void render(Graphics2D g) {
		bkg.render(g);
		for(int i=0; i<gridXCount; i++) {
			for(Sprite k : grid[i]) {
				if(k!=null) k.render(g); //every sprite overrides render
			}
		}
	}

	@Override
	public void update(long time) {
		//if(waveQueue.size()>0 && startingPoint == empty) {
		//	startingPoint.setSprite(waveQueue.get(0));
		//	waveQueue.remove(0);
		//}
		
		if(bsInput.isMouseDown(1)) {
			Sprite s = getSpriteUnderMouse(bsInput.getMouseX(), bsInput.getMouseY());
			if(s instanceof Monster) {
				//do something
				//environment.alertMonster((Monster)s);
			}
			else if(s instanceof GUIElement) {
				//do something else
				//environment.alertGUI((GUIElement)s);
			}
			else { //s instance of Tile
				//anything to be done?
			}
		}
		if(bsInput.isKeyReleased(KeyEvent.VK_SPACE)) {
			//example of a hotkey -- it might do this
			//game.nextWave();
		}
		
		for(int i=0; i<gridXCount; i++) {
			for(int k=0; k<gridYCount; k++) {
				grid[i][k].update(time);
			}
		}
	}

	
	
	
	protected void initGrid() {
		scale = new Point(bsGraphics.getSize().width/gridXCount, bsGraphics.getSize().height/gridYCount);
		gridDefinitions = new Point[gridXCount][gridYCount];
		for(int i=0; i<gridXCount; i++) {
			for(int k=0; k<gridXCount; k++) {
				gridDefinitions[i][k] = new Point(i*scale.x, k*scale.y);
				boolean placeonable = isPlaceable(i,k,i*scale.x, k*scale.y, scale);
				grid[i][k] = new Tile(placeonable, i*scale.x, k*scale.y);
			}
		}
	}
	
	public boolean isPlaceable(int i, int k, int x, int y, Point scale) {
		
		x+=scale.x/2;
		y+=scale.y/2;
		Color c = new Color(backgrounds[(i==0?1:i)*(k==0?1:k)-1].getRGB(x, y));
		//brown
		if(c.getRed()>30 && c.getGreen()>20 && c.getBlue()>5 && c.getRed()<95 && c.getGreen()<70 && c.getBlue()<45) return false;
		
		return true;
	}

	public Sprite getSpriteUnderMouse(int x, int y) {
		Sprite s = null;
		x-=x%scale.x;
		y-=y%scale.y;
		for(int i=0; i<gridXCount; i++)
			for(int k=0; k<gridYCount; k++)
				if(grid[i][k]!=null && grid[i][k].getX()==x && grid[i][k].getY()==y) {
					s = grid[i][k];
					break;
				}
		return s;
	}
	
	@SuppressWarnings("unchecked")
	public void nextWave() {
		ArrayList<String> names = LevelLoader.loadLevel(levelNumber);
		
		for(String i : names) {
			try {
				//wherever out classpath is       vvv
				Class monsterClass  = Class.forName(i + ".class");
				//this will error until we decide ^^^
				Object ojb = monsterClass.newInstance();  //the monster has been created (thank you reflection)
				ojb = monsterClass.cast(ojb);            //make it its corect type
				waveQueue.add((Sprite) ojb);
			} catch (ClassNotFoundException e) {
				System.out.println("class not found.");
				e.printStackTrace();
			} catch (InstantiationException e) {
				System.out.println("monster takes arguements.");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("Y U no have prems?!?!"); //this better not ever happen
				e.printStackTrace();
			} catch (SecurityException e) {
				System.out.println("Y U STILL no have prems?!?!"); //again.....
				e.printStackTrace();
			}
		}
	}
	
	public void nextLevel() {
		levelNumber++;
		backgrounds = getImages("maps/map-"+levelNumber+".png",8,8);
		bkg = new ImageBackground(getImage("maps/map-"+levelNumber+".png"));
		initGrid();
	}
}
