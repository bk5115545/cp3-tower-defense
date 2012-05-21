package Monsters;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import Levels.Level;
import Levels.Level.DoublePoint;
import Main.Environment;

import com.golden.gamedev.object.Sprite;

@SuppressWarnings("serial")
public class Monster extends Sprite {
	
	Point[] movementInstructionSet;
	int movementInstructionIndex = 0;
	Level l;
	Environment e;
	
	int maxHealth;
	
    public Monster(BufferedImage image, Level l, Environment e) 
    {
        this.l = l; 
        this.e = e;
        setImage(image);   
        
        movementInstructionSet = l.getMovementInstructions();
        
        /**
         * Init instruction set in format
         * 
         * point-to-start-at next-point next-point finish-point
         */
        
        setID((int)(100*e.getGame().getDifficulty()));
        maxHealth = (int) (100*e.getGame().getDifficulty());
        
        setX(movementInstructionSet[movementInstructionIndex].x);
        setY(movementInstructionSet[movementInstructionIndex].y);
    }

    DoublePoint currentDirection;
    @Override
    public void update(long t) {
    	
    	currentDirection = l.getDirection(movementInstructionIndex);
    	if(currentDirection==null) {
    		e.loseLife();
    		setActive(false);
    	} else setSpeed(currentDirection.x, currentDirection.y);
    	
    	if((getX()>e.getGame().bsGraphics.getSize().width || getX()<0) || (getY()>e.getGame().bsGraphics.getSize().height-100 || getY()<0)) setActive(false);
    	try {
    		if(getX()>movementInstructionSet[movementInstructionIndex+1].x-5*movementInstructionIndex-5 && getX()<movementInstructionSet[movementInstructionIndex+1].x+5*movementInstructionIndex+5 && getY()>movementInstructionSet[movementInstructionIndex+1].y-5*movementInstructionIndex-5 && getY()<movementInstructionSet[movementInstructionIndex+1].y+5*movementInstructionIndex+5) {
    			movementInstructionIndex++;
    		}
    	} catch (Exception e) {}
    	updateMovement(t);
    }    
    
    
    public void render(Graphics2D g) {
    	g.drawImage(getImage(), (int)getX(), (int)getY(), null);
    	for(double i=0; i<getID()/(maxHealth+0.0)*48; i+=0.1)
    		e.getGame().healthBar.drawString(g, "|", (int)(getX()+i), (int)getY()-10);
    }
}
