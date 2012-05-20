import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;

@SuppressWarnings("serial")
public class Monster extends Sprite {
	
	Point[] movementInstructionSet = new Point[4];
	int movementInstructionIndex = 0;
	Environment e;
	
    public Monster(BufferedImage image, Environment environment) 
    {
    	e = environment;
        
        setImage(image);   
        
        /**
         * Init instruction set in format
         * 
         * point-to-start-at next-point next-point finish-point
         */
        
        setID(100);
        
        
        movementInstructionSet[0] = new Point(0,55);
        movementInstructionSet[1] = new Point(360,55);
        movementInstructionSet[2] = new Point(360,420);
        movementInstructionSet[3] = new Point(680,420);
        
        setX(movementInstructionSet[movementInstructionIndex].x);
        setY(movementInstructionSet[movementInstructionIndex].y);
    }

    @Override
    public void update(long t) {
    	if(movementInstructionIndex==0) {
    		setSpeed(0.1, 0);
    	}
    	else if(movementInstructionIndex==1) {
    		setSpeed(0,0.1);
    	}
    	else if(movementInstructionIndex==2) {
    		setSpeed(0.1,0);
    	}
    	else if(movementInstructionIndex==3) {
    		e.loseLife();
    		setActive(false);
    	}
    	
    	
    	
    	if((getX()>e.game.bsGraphics.getSize().width || getX()<0) || (getY()>e.game.bsGraphics.getSize().height-100 || getY()<0)) setActive(false);
    	try {
    		if(getX()>movementInstructionSet[movementInstructionIndex+1].x-5 && getX()<movementInstructionSet[movementInstructionIndex+1].x+5 && getY()>movementInstructionSet[movementInstructionIndex+1].y-5 && getY()<movementInstructionSet[movementInstructionIndex+1].y+5) {
    			movementInstructionIndex++;
    		}
    	} catch (Exception e) {}
    	updateMovement(t);
    }    
    
    
    public void render(Graphics2D g) {
    	g.drawImage(getImage(), (int)getX(), (int)getY(), null);
    }

}

