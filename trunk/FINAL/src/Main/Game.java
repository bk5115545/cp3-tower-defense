package Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Levels.Endless;
import Levels.Level1;
import Levels.Level2;
import Levels.Level3;
import Levels.Level4;
import Levels.Level5;
import Levels.Level6;
import Levels.Level7;
import Levels.Level8;
import Levels.StartingLevel;

import com.golden.gamedev.engine.BaseAudio;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.font.SystemFont;


public class Game extends com.golden.gamedev.Game {


	private ImageBackground titleScreen;
	private Sprite[] difficultyButtons = new Sprite[4];
	boolean showMenu = true;
	boolean paused = false;
	boolean lost = false;

	private double difficulty = 2;
	public boolean endlessMode = false;
	private Endless endlessLevel;

	public SystemFont font = new SystemFont(new Font("Arial", Font.BOLD|Font.PLAIN, 32),Color.BLUE);
	public SystemFont healthBar = new SystemFont(new Font("Arial", Font.BOLD|Font.PLAIN, 8),Color.GREEN);

	private int gridXCount = 8;
	private int gridYCount = 8;
	private Point scale; //click location checking
	private Point[][] gridDefinitions; //click location checking
	private Tile[][] grid; //play area

	private int levelNumber = 1;
	private int seconds = 0;

	private int money = 1000;

	private BufferedImage bkg;
	private BufferedImage blackBackgroundCover = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);

	BufferedImage waterTowerImage;
	BufferedImage airTowerImage;
	BufferedImage fireTowerImage;
	BufferedImage earthTowerImage;

	BufferedImage waterProjImage;
	BufferedImage earthProjImage;
	BufferedImage fireProjImage;
	BufferedImage airProjImage;

	public BufferedImage mobVanilla;
	public BufferedImage mobWater;
	public BufferedImage mobFire;
	public BufferedImage mobEarth;
	public BufferedImage mobAir;

	private BufferedImage informationRenderImage;
	private BufferedImage yesImage;
	private BufferedImage noImage;
	private BufferedImage loseImage;
	private GUI gui;
	private Environment enviro;

	private int soundPlayerIndex = 1;

	{
		//distribute = true;
	}

	@Override
	public void initResources() {

		showMenu = true;
		lost = false;
		endlessMode = false;
		levelNumber = 1;

		setFPS(60);
		showCursor();
		initGrid();

		setGui(new GUI(this));
		enviro = new Environment(this);

		try {
			bkg = getScaledImage(ImageIO.read(new File("gui/map1.png")),scale.x*getGridXCount(),scale.y*gridYCount);
		} catch (IOException e1) {}

		try {
			titleScreen = new ImageBackground(getScaledImage(ImageIO.read(new File("gui/titlebg.png")),800,600));

			informationRenderImage = getScaledImage(ImageIO.read(new File("tooltiptower.png")),(int) (getScale().x*1.5),getScale().y);

			loseImage = getScaledImage(ImageIO.read(new File("layoverlosescreen.png")),(int) 800,600); //overlay
			yesImage = getScaledImage(ImageIO.read(new File("buttonyes.png")),(int) (getScale().x*3),getScale().y*2);
			noImage = getScaledImage(ImageIO.read(new File("buttonno.png")),(int) (getScale().x*3),getScale().y*2);

			difficultyButtons[0] = new Sprite(getScaledImage(ImageIO.read(new File("gui/buttoneasy.png")),(int)(200*0.75),(int)(150*0.75)));
			difficultyButtons[1] = new Sprite(getScaledImage(ImageIO.read(new File("gui/buttonnormal.png")),(int)(200*0.75),(int)(150*0.75)));
			difficultyButtons[2] = new Sprite(getScaledImage(ImageIO.read(new File("gui/buttonhard.png")),(int)(200*0.75),(int)(150*0.75)));
			difficultyButtons[3] = new Sprite(getScaledImage(ImageIO.read(new File("gui/buttoninsane.png")),(int)(200*0.75),(int)(150*0.75)));

			waterTowerImage = getScaledImage(ImageIO.read(new File("tiles/watertower.png")), scale.x, scale.y);
			airTowerImage = getScaledImage(ImageIO.read(new File("tiles/airtower.png")), scale.x, scale.y);
			earthTowerImage = getScaledImage(ImageIO.read(new File("tiles/earthtower.png")), scale.x, scale.y);
			fireTowerImage = getScaledImage(ImageIO.read(new File("tiles/firetower.png")), scale.x, scale.y);

			earthProjImage = getScaledImage(ImageIO.read(new File("sprites/rock.png")), scale.x/5, scale.y/5);
			waterProjImage = getScaledImage(ImageIO.read(new File("sprites/waterball.png")), scale.x/5, scale.y/5);
			fireProjImage = getScaledImage(ImageIO.read(new File("sprites/fireball.png")), scale.x/5, scale.y/5);
			airProjImage = getScaledImage(ImageIO.read(new File("sprites/airball.png")), scale.x/5, scale.y/5);

			mobVanilla = getScaledImage(ImageIO.read(new File("sprites/mobvanilla.png")),48,48);
			mobAir = getScaledImage(ImageIO.read(new File("sprites/mobair.png")),48,48);
			mobFire = getScaledImage(ImageIO.read(new File("sprites/mobfire.png")),48,48);
			mobEarth = getScaledImage(ImageIO.read(new File("sprites/mobearth.png")),48,48);
			mobWater = getScaledImage(ImageIO.read(new File("sprites/mobwater.png")),48,48);
		} catch (IOException e) {
			System.out.println("Resources not loaded properly.");
			System.exit(1);
		}

		StartingLevel startingLevel = new StartingLevel(this);

		Level1 level1 = new Level1(this);
		Level2 level2 = new Level2(this);
		Level3 level3 = new Level3(this);
		Level4 level4 = new Level4(this);
		Level5 level5 = new Level5(this);
		Level6 level6 = new Level6(this);
		Level7 level7 = new Level7(this);
		Level8 level8 = new Level8(this);
		setEndlessLevel(new Endless(this));
		
		endlessMode = false;

		enviro.addLevel(startingLevel);
		enviro.addLevel(level1);
		enviro.addLevel(level2);
		enviro.addLevel(level3);
		enviro.addLevel(level4);
		enviro.addLevel(level5);
		enviro.addLevel(level6);
		enviro.addLevel(level7);
		enviro.addLevel(level8);
		enviro.addLevel(getEndlessLevel());

		enviro.nextLevel();
	}

	@Override
	public void render(Graphics2D g) {

		if(!showMenu && !paused && getEnviro().getLives() > 0) {
			g.drawImage(blackBackgroundCover, 0,0, null);
			g.drawImage(bkg,0,0,null);

			for(int i=0; i<getGridXCount(); i++) {
				for(Sprite k : grid[i]) {
					if(k!=null) k.render(g); //every sprite overrides render
				}
			}

			getGui().render(g);
			getEnviro().render(g);

		} else if(showMenu && !lost){ //showing menu
			titleScreen.render(g);
			difficultyButtons[0].render(g, 325, 200);
			difficultyButtons[1].render(g, 325, 275);
			difficultyButtons[2].render(g, 325, 350);
			difficultyButtons[3].render(g, 325, 425);
		}

		else if(paused && !lost) {
			g.drawImage(blackBackgroundCover, 0,0, null);
			g.drawImage(bkg,0,0,null);
			for(int i=0; i<getGridXCount(); i++) {
				for(Sprite k : grid[i]) {
					if(k!=null) k.render(g); //every sprite overrides render
				}
			}
			getGui().render(g);
			getEnviro().render(g);
			font.drawString(g, "Paused", 300, 200);
			font.drawString(g, "Space to Continue or Escape to Exit", 100, 350);
		}
		else {
			lost = true;
			g.drawImage(blackBackgroundCover, 0,0, null);
			g.drawImage(bkg,0,0,null);
			getGui().render(g);
			getEnviro().render(g);
			g.drawImage(loseImage, 0,0, null);
			g.drawImage(yesImage, 50,300, null);
			g.drawImage(noImage, 400,300,null);
		}

		//font.drawString(g, "X: " + getMouseX() + "\tY: " + getMouseY(),getMouseX(), getMouseY());
	}

	@Override
	public void update(long time) {

		if(!showMenu && !paused && !lost) {
			if(!endlessMode && bsInput.isKeyReleased(KeyEvent.VK_N)){ 
				enviro.nextLevel();
			}
			if(bsInput.isKeyReleased(KeyEvent.VK_SPACE)) {
				paused = true;
			}
			for(int i=0; i<getGridXCount(); i++) {
				for(int k=0; k<gridYCount; k++) {
					grid[i][k].update(time);
				}
			}
			getGui().update(time);
			getEnviro().update(time);
		}

		else if(showMenu && !lost) { //showing menu

			if(bsInput.isMouseDown(1)) {
				int mouseX = bsInput.getMouseX();
				int mouseY = bsInput.getMouseY();

				if(mouseX>325 && mouseX<475 && mouseY>225 && mouseY<289) {
					setDifficulty(4);
					money = 1000;
					endlessMode = false;
					initResources();
					for(int i=0; i<30; i++) enviro.addLife();
					showMenu = false;
				}
				if(mouseX>325 && mouseX<475 && mouseY>300 && mouseY<363) {
					setDifficulty(6);
					money = 1000;
					initResources();
					endlessMode = false;
					for(int i=0; i<30; i++) enviro.addLife();
					showMenu = false;
				}
				if(mouseX>325 && mouseX<475 && mouseY>375 && mouseY<440) {
					setDifficulty(12);
					money = 1000;
					initResources();
					endlessMode = false;
					for(int i=0; i<30; i++) enviro.addLife();
					showMenu = false;
				}
				if(mouseX>325 && mouseX<475 && mouseY>450 && mouseY<513) {
					setDifficulty(20);
					money = 500;
					initResources();
					endlessMode = true;
					for(int i=0; i<30; i++) enviro.addLife();
					showMenu = false;
				}
				if(bsInput.isKeyReleased(KeyEvent.VK_ESCAPE)) {
					finish();
				}
			}
		}
		else if(paused && !lost) {
			gui.update(time);
			if(bsInput.isKeyReleased(KeyEvent.VK_SPACE)) {
				paused = false;
			}
			if(bsInput.isKeyReleased(KeyEvent.VK_ESCAPE)) {
				finish();
			}
		}
		else {
			if(bsInput.isMouseReleased(1))
				if(getMouseX()>50 && getMouseX()<364 && getMouseY()>331 && getMouseY()<413) {
					initResources();
					lost = false;
				} else if(getMouseX()>400 && getMouseX()<457 && getMouseY()>330 && getMouseY()<413){
					finish();
				}
		}
	}




	protected void initGrid() {
		grid = new Tile[getGridXCount()][gridYCount];
		setScale(new Point((bsGraphics.getSize().width-100)/getGridXCount(), (bsGraphics.getSize().height-25)/gridYCount));
		gridDefinitions = new Point[getGridXCount()][gridYCount];
		for(int i=0; i<getGridXCount(); i++) {
			for(int k=0; k<getGridXCount(); k++) {
				gridDefinitions[i][k] = new Point(i*getScale().x, k*getScale().y);
				grid[i][k] = new Tile(true, i*getScale().x, k*getScale().y);
			}
		}
	}

	public Sprite getSpriteUnderMouse(int x, int y) {
		Sprite s = null;
		x-=x%getScale().x;
		y-=y%getScale().y;
		for(int i=0; i<getGridXCount(); i++)
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

	public void setMoney(int money) {
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}

	public double getDifficulty() {
		return difficulty;
	}

	public void setInformationRenderImage(BufferedImage informationRenderImage) {
		this.informationRenderImage = informationRenderImage;
	}

	public BufferedImage getInformationRenderImage() {
		return informationRenderImage;
	}

	public void setScale(Point scale) {
		this.scale = scale;
	}

	public Point getScale() {
		return scale;
	}

	public void setGridXCount(int gridXCount) {
		this.gridXCount = gridXCount;
	}

	public int getGridXCount() {
		return gridXCount;
	}

	public int getGridYCount() {
		return gridYCount;
	}

	public void setEnviro(Environment enviro) {
		this.enviro = enviro;
	}

	public Environment getEnviro() {
		return enviro;
	}

	public int getSoundIndex() {
		return soundPlayerIndex++;
	}

	public BaseAudio getSound() {
		return bsSound;
	}

	public void setEndlessLevel(Endless endlessLevel) {
		this.endlessLevel = endlessLevel;
	}

	public Endless getEndlessLevel() {
		return endlessLevel;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}

	public GUI getGui() {
		return gui;
	}
}
