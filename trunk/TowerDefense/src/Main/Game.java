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

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.font.SystemFont;


public class Game extends com.golden.gamedev.Game {


	ImageBackground titleScreen;
	Sprite startButton;
	Sprite[] difficultyButtons = new Sprite[3];
	BufferedImage difficultyIdentifier;
	boolean showMenu = true;
	boolean paused = false;

	private double difficulty = 2;
	boolean endlessMode = true;

	public SystemFont font = new SystemFont(new Font("Arial", Font.BOLD|Font.PLAIN, 32),Color.BLUE);
	public SystemFont healthBar = new SystemFont(new Font("Arial", Font.BOLD|Font.PLAIN, 8),Color.GREEN);

	int gridXCount = 8;
	int gridYCount = 8;
	Point scale; //click location checking
	Point[][] gridDefinitions; //click location checking
	Tile[][] grid; //play area
	Tile[] GUIgrid;

	int levelNumber = 1;
	int seconds = 0;

	private int money = 1000;

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

	private BufferedImage informationRenderImage;
	GUI gui;
	Environment enviro;

	{
		distribute = true;
	}

	@Override
	public void initResources() {

		setFPS(60);
		showCursor();

		initGrid();
		gui = new GUI(this);
		enviro = new Environment(this);

		try {
			bkg = new ImageBackground(getScaledImage(ImageIO.read(new File("map"+levelNumber+".png")),scale.x*gridXCount,scale.y*gridYCount));
		} catch (IOException e1) {}



		try {
			titleScreen = new ImageBackground(getScaledImage(ImageIO.read(new File("menuBackground.png")),800,600));
			
			informationRenderImage = getScaledImage(ImageIO.read(new File("InformationImage.gif")),scale.x,scale.y);

			difficultyButtons[0] = new Sprite(getScaledImage(ImageIO.read(new File("sprites/difficultyButtonEasy.png")),200,150));
			difficultyButtons[1] = new Sprite(getScaledImage(ImageIO.read(new File("sprites/difficultyButtonNormal.png")),200,150));
			difficultyButtons[2] = new Sprite(getScaledImage(ImageIO.read(new File("sprites/difficultyButtonExtreme.png")),200,150));

			waterTowerImage = getScaledImage(ImageIO.read(new File("tiles/watertower.png")), scale.x, scale.y);
			airTowerImage = getScaledImage(ImageIO.read(new File("tiles/airtower.png")), scale.x, scale.y);
			earthTowerImage = getScaledImage(ImageIO.read(new File("tiles/earthtower.png")), scale.x, scale.y);
			fireTowerImage = getScaledImage(ImageIO.read(new File("tiles/firetower.png")), scale.x, scale.y);

			earthProjImage = getScaledImage(ImageIO.read(new File("sprites/rock.png")), scale.x/5, scale.y/5);
			waterProjImage = getScaledImage(ImageIO.read(new File("sprites/waterball.png")), scale.x/5, scale.y/5);
			fireProjImage = getScaledImage(ImageIO.read(new File("sprites/fireball.png")), scale.x/5, scale.y/5);
			airProjImage = getScaledImage(ImageIO.read(new File("sprites/airball.png")), scale.x/5, scale.y/5);

			mobVanilla = getScaledImage(ImageIO.read(new File("sprites/mobvanilla.png")),48,48);
		} catch (IOException e) {
			System.out.println("Resources not loaded properly.");
			System.exit(1);
		}
	}

	@Override
	public void render(Graphics2D g) {

		if(!showMenu && !paused && enviro.getLives() > 0) {
			g.drawImage(blackBackgroundCover, 0,0, null);
			bkg.render(g);
			
			for(int i=0; i<gridXCount; i++) {
				for(Sprite k : grid[i]) {
					if(k!=null) k.render(g); //every sprite overrides render
				}
			}
			
			gui.render(g);
			enviro.render(g);
		} else if(showMenu){ //showing menu

			titleScreen.render(g);
			font.drawString(g, "Elemental Tower Defense", 50, 85);
			difficultyButtons[0].render(g, 50, 200);
			difficultyButtons[1].render(g, 50, 400);
			difficultyButtons[2].render(g, 325, 400);
		}

		else if(paused) {
			g.drawImage(blackBackgroundCover, 0,0, null);
			bkg.render(g);
			for(int i=0; i<gridXCount; i++) {
				for(Sprite k : grid[i]) {
					if(k!=null) k.render(g); //every sprite overrides render
				}
			}
			gui.render(g);
			enviro.render(g);
			font.drawString(g, "Paused", 300, 200);
			font.drawString(g, "Space to Continue or Escape to Exit", 100, 350);
		}
		else if(enviro.getLives()<1) {
			showMenu = true;
		}
	}

	@Override
	public void update(long time) {

		if(!showMenu && !paused) {
			if(bsInput.isKeyReleased(KeyEvent.VK_SPACE)) {
				paused = true;
			}

			for(int i=0; i<gridXCount; i++) {
				for(int k=0; k<gridYCount; k++) {
					grid[i][k].update(time);
				}
			}

			gui.update(time);
			enviro.update(time);
		}

		else if(showMenu) { //showing menu

			if(bsInput.isMouseDown(1)) {
				int mouseX = bsInput.getMouseX();
				int mouseY = bsInput.getMouseY();

				if(mouseX>50 && mouseX<250 && mouseY>200 && mouseY<350) {
					setDifficulty(4);
					showMenu = false;
				}
				if(mouseX>50 && mouseX<250 && mouseY>400 && mouseY<550) {
					setDifficulty(6);
					showMenu = false;
				}
				if(mouseX>325 && mouseX<525 && mouseY>400 && mouseY<550) {
					setDifficulty(12);
					showMenu = false;
				}
				if(bsInput.isKeyReleased(KeyEvent.VK_ESCAPE)) {
					finish();
				}
			}
		}
		else if(paused) {
			gui.update(time);
			if(bsInput.isKeyReleased(KeyEvent.VK_SPACE)) {
				paused = false;
			}
			if(bsInput.isKeyReleased(KeyEvent.VK_ESCAPE)) {
				finish();
			}
		}
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
}
