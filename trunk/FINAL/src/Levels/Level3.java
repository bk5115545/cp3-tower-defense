package Levels;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.Game;
import Monsters.Monster;

public class Level3 extends Level {
	private static final long serialVersionUID = 1L;
	

	public Level3(Game g) {
		setGame(g);
		Point[] movementInstructionSet = new Point[4];
		
		movementInstructionSet[0] = new Point(0,55);
	    movementInstructionSet[1] = new Point(360,55);
	    movementInstructionSet[2] = new Point(360,420);
	    movementInstructionSet[3] = new Point(680,420);
	    
	    setInstructions(movementInstructionSet);
	    
	    
	    ArrayList<Monster> queue = new ArrayList<Monster>();
	    
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    queue.add(new Monster(g.mobWater, this, g.getEnviro()));
	    
	    setWaveQueue(queue);
	    setSpawnRate(450);
	    
	    
	    
	    DoublePoint[] speeds = new DoublePoint[4];
    	speeds[0] = new DoublePoint(0.1, 0);
    	speeds[1] = new DoublePoint(0,0.1);
    	speeds[2] = new DoublePoint(0.1,0);
    	speeds[3] = null;
    	
    	setDirectionAndSpeeds(speeds);
	    
	    
	    try {
			setBackgroundImage(Game.getScaledImage(ImageIO.read(new File("map1.png")), g.getScale().x*g.getGridXCount(), g.getScale().y*g.getGridYCount()));
		} catch (IOException e) {}
	}
}
