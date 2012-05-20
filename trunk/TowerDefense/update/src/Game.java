import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;


public class Game extends com.golden.gamedev.Game {
	
	int gridXCount = 8;
	int gridYCount = 8;
	Point scale; //click location checking
	Point[][] gridDefinitions; //click location checking
	Tile[][] grid; //play area
	Tile[] GUIgrid;
	
	int levelNumber = 1;
	
	int money = 1000;
	
	ImageBackground bkg;
	BufferedImage blackBackgroundCover = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
	
	BufferedImage waterTowerImage;
	BufferedImage airTowerImage;
	BufferedImage fireTowerImage;
	BufferedImage earthTowerImage;
	
	BufferedImage waterProjImage;
	BufferedImage earthProjImage;
	BufferedImage fireProjImage;
	BufferedImage airProjImage;
	
	BufferedImage mobVanilla;
	
	GUI gui;
	Environment enviro;
	
	@Override
	public void initResources() {
		
		setFPS(60);
		
		initGrid();
		gui = new GUI(this);
		enviro = new Environment(this);
		
		try {
			bkg = new ImageBackground(getScaledImage(ImageIO.read(new File("map"+levelNumber+".png")),scale.x*gridXCount,scale.y*gridYCount));
		} catch (IOException e1) {}
		
		
		
		try {
		waterTowerImage = getScaledImage(ImageIO.read(new File("tiles/watertower.png")), scale.x, scale.y);
		airTowerImage = getScaledImage(ImageIO.read(new File("tiles/airtower.png")), scale.x, scale.y);
		earthTowerImage = getScaledImage(ImageIO.read(new File("tiles/earthtower.png")), scale.x, scale.y);
		fireTowerImage = getScaledImage(ImageIO.read(new File("tiles/firetower.png")), scale.x, scale.y);
		
		earthProjImage = getScaledImage(ImageIO.read(new File("sprites/rock.png")), scale.x/5, scale.y/5);
		waterProjImage = getScaledImage(ImageIO.read(new File("sprites/waterball.png")), scale.x/5, scale.y/5);
		fireProjImage = getScaledImage(ImageIO.read(new File("sprites/fireball.png")), scale.x/5, scale.y/5);
		airProjImage = getScaledImage(ImageIO.read(new File("sprites/airball.png")), scale.x/5, scale.y/5);
		
		mobVanilla = ImageIO.read(new File("sprites/mobvanilla.png"));
		} catch (IOException e) {
			System.out.println("Resources not loaded properly.");
		}
	}

	@Override
	public void render(Graphics2D g) {
		drawFPS(g, 0, 25);
		g.drawImage(blackBackgroundCover, 0,0, null);
		bkg.render(g);
		for(int i=0; i<gridXCount; i++) {
			for(Sprite k : grid[i]) {
				if(k!=null) k.render(g); //every sprite overrides render
			}
		}
		gui.render(g);
		enviro.render(g);
	}

	@Override
	public void update(long time) {
		//if(waveQueue.size()>0 && startingPoint == empty) {
		//	startingPoint.setSprite(waveQueue.get(0));
		//	waveQueue.remove(0);
		//}
		
		if(bsInput.isMouseReleased(1)) {
			Sprite s = getSpriteUnderMouse(bsInput.getMouseX(), bsInput.getMouseY());
			if(s instanceof Monster) {
				s = (Monster)s;
				//do something
				//environment.alertMonster((Monster)s);
			}
			else if(s instanceof Tile) {
				s = (Tile)s;
				//((Tile) s).addSprite(new Sprite(waterTowerImage));
				//displayInformation(s);
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
		
		gui.update(time);
		enviro.update(time);
	}

	
	
	
	protected void initGrid() {
		grid = new Tile[gridXCount][gridYCount];
		scale = new Point((bsGraphics.getSize().width-100)/gridXCount, (bsGraphics.getSize().height-25)/gridYCount);
		gridDefinitions = new Point[gridXCount][gridYCount];
		for(int i=0; i<gridXCount; i++) {
			for(int k=0; k<gridXCount; k++) {
				gridDefinitions[i][k] = new Point(i*scale.x, k*scale.y);
				grid[i][k] = new Tile(true, i*scale.x, k*scale.y);
			}
		}
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
		
	
	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) {
        int imageWidth  = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double)width/imageWidth;
        double scaleY = (double)height/imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(image, new BufferedImage(width, height, image.getType()));
	}
}
