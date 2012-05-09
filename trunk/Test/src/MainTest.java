import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.font.SystemFont;


public class MainTest extends Game {
	
	ImageBackground background;
	
	ImageBackground nullImage;
	Monster nullCompare;
	ImageBackground spriteTestImage;
	
	Sprite[][] grid;
	int gridXCount;
	int gridYCount;
	Dimension screenSize;
	Dimension towerGridSize;
	Point[][] gridDefinitions;
	Point gridScale;
	
	int lastClickedID = 0;
	
	SystemFont informer = new SystemFont(new Font("Times New Roman",Font.BOLD,32),Color.red);

	@Override
	public void initResources() {
		setFPS(45);
		screenSize = bsGraphics.getSize();
		towerGridSize = new Dimension(screenSize.width-100,screenSize.height);
		
		initPlayGrid();
		
		nullImage = new ImageBackground(getImage("null.png"));
		nullCompare = new Monster(nullImage.getImage());
		for(int i=0; i<gridXCount; i++) {
			for(int k=0; k<gridYCount; k++) {
				grid[i][k] = nullCompare;
			}
		}
		
		spriteTestImage = new ImageBackground(getImage("orange.png"));
		background = new ImageBackground(getImage("background.png"));
	}

	/**
	 * Call this method to reinitialize the grid with the current gridXCount and gridYCount.
	 * This can be used in between games to clear or resize the board.
	 * @Warning Clears everything except the background.
	 */
	private void initPlayGrid() {
		gridXCount = 5;
		gridYCount = 5;
		
		gridScale = new Point(towerGridSize.width/gridXCount, towerGridSize.height/gridYCount);
		grid = new Sprite[gridXCount][gridYCount];
		gridDefinitions = new Point[gridXCount][gridYCount];
		//define grid click areas
		for(int i=0; i<gridXCount; i++) {
			for(int k=0; k<gridYCount; k++) {
				gridDefinitions[i][k] = new Point(gridScale.x*i, gridScale.y*k);
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		background.render(g);
		for(int i=0; i<gridXCount; i++) {
			for(int k=0; k<gridYCount; k++) {
				grid[i][k].render(g, gridDefinitions[i][k].x, gridDefinitions[i][k].y);
			}
		}
		
	}

	@Override
	public void update(long arg0) {
		if(bsInput.getMousePressed()==1) {
			updateGrid(bsInput.getMouseX(), bsInput.getMouseY());
		}
		
		if(bsInput.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			finish();
		}
	}
	
	
	private void updateGrid(int mouseX, int mouseY) {
		mouseX -= mouseX%gridScale.x;
		mouseY -= mouseY%gridScale.y;
		
		int gx = -1;
		int gy = -1;
		
		for(int i=0; i<gridXCount; i++) {
			for(int k=0; k<gridYCount; k++) {
				if(gridDefinitions[i][k].equals(new Point(mouseX, mouseY))) {
					gx = i;
					gy = k;
				}
			}
		}
		if(gx==-1 || gy==-1) return; //clicked on something else
		
		if(grid[gx][gy].equals(nullCompare)) {
			grid[gx][gy] = new Monster(spriteTestImage.getImage());
			System.out.println("Object created");
		}
	}
	
	public Sprite[][] getGrid() {
		return grid;
	}

	public static void main(String[] args) {
		GameLoader gameLoader = new GameLoader();
		gameLoader.setup(new MainTest(), new Dimension(800,600), false);
		gameLoader.start();
	}

}
