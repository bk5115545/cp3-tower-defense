package Levels;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.Game;
import Monsters.Monster;

public class StartingLevel extends Level {
	private static final long serialVersionUID = 1L;
	

	public StartingLevel(Game g) {
		Point[] movementInstructionSet = new Point[1];
		
		movementInstructionSet[0] = new Point(0,0);
	    
	    setInstructions(movementInstructionSet);
	    
	    
	    setSpawnRate(100);
	    
	    
	    try {
			setBackgroundImage(Game.getScaledImage(ImageIO.read(new File("map1.png")), g.getScale().x*g.getGridXCount(), g.getScale().y*g.getGridYCount()));
		} catch (IOException e) {}
	}

}
