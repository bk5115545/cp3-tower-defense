import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.golden.gamedev.object.font.SystemFont;


public class GUI {
	private static final long serialVersionUID = 6013907881027208449L;


	BufferedImage selectionImage;
	Tower selectedTower;
	boolean hasSelectedTower = false;

	private int m_tempX;
	private int m_tempY;

	SystemFont font;
	int waveBarX = 0;
	int waveBarY = 566;
	
	
	BufferedImage guiBackground; 

	Game game; //singleton reference to the implementing game
	public GUI(Game g) {
		game = g;
		
		font = new SystemFont(new Font("Arial", Font.BOLD|Font.PLAIN, 28), Color.BLUE);
	}


	private boolean firstClick = true; //lock so that only one tower is created per mouse drag
	public void update(long time) {
		if(game.bsInput.isMouseDown(1)) {
			m_tempX = game.bsInput.getMouseX();
			m_tempY = game.bsInput.getMouseY();
			if((checkClick(m_tempX, m_tempY)!=null || hasSelectedTower) && firstClick) {
				firstClick = false;
				hasSelectedTower = true; //we are dragging a tower to a tile
				selectedTower = checkClick(m_tempX, m_tempY);
			}
		} else if(game.bsInput.isMouseReleased(1)) {
			//try to place the tower
			try {
				if(((Tile) game.getSpriteUnderMouse(game.getMouseX(), game.getMouseY())).canBuild() && ((Tile) game.getSpriteUnderMouse(game.getMouseX(), game.getMouseY())).sprite==null) {
					//success
					game.money-=100;
					selectedTower.setX(game.bsInput.getMouseX());
					selectedTower.setY(game.bsInput.getMouseY());
					((Tile) game.getSpriteUnderMouse(game.getMouseX(), game.getMouseY())).addSprite(selectedTower);
				}
				else { //Tile already has a sprite so make sure that we can build on it
					if(selectedTower!=null && ((Tile) game.getSpriteUnderMouse(game.getMouseX(), game.getMouseY())).sprite instanceof Tower) {
						selectedTower.setX(game.bsInput.getMouseX());
						selectedTower.setY(game.bsInput.getMouseY());
						((Tower) ((Tile) game.getSpriteUnderMouse(game.getMouseX(), game.getMouseY())).sprite).upgrade((Tower)selectedTower);
					}
				}
				hasSelectedTower = false;
				firstClick = true;
				selectedTower = null;
			} catch (Exception e) {
				//the tower was dropped off of the playing grid
				//nothing needs to be done besides a reset
				hasSelectedTower = false;
				firstClick = true;
				selectedTower = null;
			}
			//placing was successful
		}
	}


	public void render(Graphics2D g) {
		g.drawImage(game.waterTowerImage, 700, 0, null);
		g.drawImage(game.earthTowerImage, 700, 100, null);
		g.drawImage(game.fireTowerImage, 700, 200, null);
		g.drawImage(game.airTowerImage, 700, 300, null);

		if(selectedTower!=null && game.bsInput.isMouseDown(1) && !firstClick) {
			selectedTower.render(g, m_tempX-game.scale.x/2, m_tempY-game.scale.y/2);
		}
		
		font.drawString(g, "Lives: "+game.enviro.getLives(), 650, waveBarY);
		font.drawString(g, "Points: "+game.money, waveBarX, waveBarY);
	}



	private Tower checkClick(int x, int y) {
		if(x>700 && y>0 && y<75) {
			return (Tower)(new Tower(game.waterTowerImage, game.waterProjImage, x-x%game.scale.x, y-y%game.scale.y, 1, game.enviro));
		}
		if(x>700 && y>100 && y<173) {
			return (Tower)(new Tower(game.earthTowerImage, game.earthProjImage, x-x%game.scale.x, y-y%game.scale.y, 1, game.enviro));
		}
		if(x>700 && y>200 && y<273) {
			return (Tower)(new Tower(game.fireTowerImage, game.fireProjImage, x-x%game.scale.x, y-y%game.scale.y, 1, game.enviro));
		}
		if(x>700 && y>300 && y<373) {
			return (Tower)(new Tower(game.airTowerImage, game.airProjImage, x-x%game.scale.x, y-y%game.scale.y, 1, game.enviro));
		}
		else return null;
	}

}
