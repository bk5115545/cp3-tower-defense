import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.background.ImageBackground;
import com.golden.gamedev.object.font.SystemFont;


/**
 * The files outputed are in the correct format and directory to be used by the LevelLoader.
 * Requires a "monsters" directory
 * Requires a "levels" directory
 * 
 * 
 * Middle click to write level.
 * Left Click to add monster to level.
 * Left Arrow to go back one level.
 * Right Arrow to go forward one level.
 * del Key to remove last added monster from the current level.
 * esc -- exit
 * 
 * 
 * @author kochb
 * @version 0.5
 * @since 5-9-2012
 */

public class Main extends Game {

	File[] allFilesInDirectory;
	File monsterFileLocation = new File("monsters/");
	String outputFilePrefix = "Level-";
	int levelNumber = 1;
	String outputFilePath = "levels/";
	String outputFilePostfix = ".level";
	ArrayList<File> levelStack = new ArrayList<File>();
	Tile[] clickableTiles;

	int gridXCount;
	int gridYCount;

	Point[][] gridDefinitions;
	Point gridScale;

	SystemFont font;
	ImageBackground background;

	@Override
	public void initResources() {
		font = new SystemFont(new Font("Arial", Font.PLAIN, 8), Color.WHITE);
		background = new ImageBackground(new BufferedImage(bsGraphics.getSize().width, bsGraphics.getSize().height, BufferedImage.TYPE_INT_RGB));
		setFPS(45);
		initPlayGrid();
		
		allFilesInDirectory = monsterFileLocation.listFiles(new FilenameFilter() { //list every .java file -- every tower file
			@Override
			public boolean accept(File dir, String name) {
				if(name.contains(".java")) return true;
				return false;
			}
		}); 
		
		
		int nTimesGreaterThanMaxX = 0;
		clickableTiles = new Tile[allFilesInDirectory.length];
		for(int i=0; i<clickableTiles.length; i++) {
			clickableTiles[i] = new Tile(allFilesInDirectory[i].getName(), i+nTimesGreaterThanMaxX*gridXCount);
			if(i>gridXCount) nTimesGreaterThanMaxX++;
		}

		
	}

	@Override
	public void render(Graphics2D g) {
		background.render(g);
		renderTiles(g);

		renderStackInfo(g);
	}

	private void renderStackInfo(Graphics2D g) {
		font.drawString(g, "Stack Size: "+levelStack.size(), 10, 10);
		font.drawString(g, "Level Number: "+levelNumber, 10, 30);

		font.drawString(g, "STACK", 10, 70);
		int i=10;
		for(File f : levelStack) {
			font.drawString(g, f.getName(), 10, 10*i);
			i++;
		}
	}


	@Override
	public void update(long t) {
		if(bsInput.isMouseReleased(1)) {
			updateGrid(bsInput.getMouseX(), bsInput.getMouseY());
		}
		if(bsInput.isMouseDown(2)&& levelStack.size()>0) {
			writeStack();
			System.out.println("stack written: " + levelStack.size());
			levelStack = new ArrayList<File>();
		}
		if(bsInput.isKeyReleased(KeyEvent.VK_LEFT)) {
			levelNumber -= 1;
		}
		if(bsInput.isKeyReleased(KeyEvent.VK_RIGHT)) {
			levelNumber += 1;
		}
		if(bsInput.isKeyReleased(KeyEvent.VK_DELETE)) {
			try {
				levelStack.remove(levelStack.size()-1);
			} catch (Exception e) {}
		}
	}

	private void initPlayGrid() {
		gridXCount = 5;
		gridYCount = 1+1;

		gridScale = new Point(bsGraphics.getSize().width/gridXCount, bsGraphics.getSize().height/gridYCount);
		gridDefinitions = new Point[gridXCount+1][gridYCount+1];
		//define grid click areas
		for(int i=1; i<=gridXCount; i++) {
			for(int k=1; k<=gridYCount; k++) {
				gridDefinitions[i][k] = new Point(gridScale.x*i, gridScale.y*k);
			}
		}
	}
	

	private void renderTiles(Graphics2D g) {
		for(int i=1; i<=gridXCount; i++) {
			for(int k=1; k<=gridYCount; k++) {
				if(k*i<allFilesInDirectory.length) {
					font.drawString(g, allFilesInDirectory[i*k].getName(), gridDefinitions[i][k].x-20, gridDefinitions[i][k].y+10);
				} else break;
			}
		}
	}

	private Point comparePoint;
	private void updateGrid(int mouseX, int mouseY) {
		mouseX -= mouseX%gridScale.x;
		mouseY -= mouseY%gridScale.y;

		int gx = -1;
		int gy = -1;

		comparePoint = new Point(mouseX, mouseY);
		for(int i=1; i<gridXCount; i++) {
			for(int k=1; k<gridYCount; k++) {
				if(gridDefinitions[i][k].equals(comparePoint)) {
					gx = i;
					gy = k;
				}
			}
		}
		if(gx==-1 || gy==-1) return; //clicked on something else

		System.out.println(gx*gy);
		if(gx*gy>=allFilesInDirectory.length || gx*gy<1 || allFilesInDirectory[gx*gy]==null) return;
		levelStack.add(allFilesInDirectory[gx*gy]);
	}

	private void writeStack() {
		File output = new File(outputFilePath+outputFilePrefix+levelNumber+outputFilePostfix);
		try {
			FileWriter fout = new FileWriter(output);
			for(int i=0; i<levelStack.size(); i++) {
				fout.write(levelStack.get(i).getName()+"\n");
			}
			fout.close();
			levelNumber += 1;
		} catch (IOException e) {
			System.out.println("write failed");
		}
	}

	@SuppressWarnings("serial")
	class Tile extends Sprite {

		String text;
		int index = -1;

		public Tile(String text, int index) {
			this.text = text;
			this.index = index;
		}
	}

	public static void main(String[] args) {
		GameLoader gl = new GameLoader();
		gl.setup(new Main(), new Dimension(800,600), false);
		gl.start();
	}

}
