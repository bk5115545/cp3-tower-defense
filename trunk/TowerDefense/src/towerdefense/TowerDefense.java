/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package towerdefense;

import com.golden.gamedev.Game;
import java.awt.Graphics2D;
import java.awt.Dimension;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.engine.BaseInput;
import com.golden.gamedev.object.Sprite;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author lovinz
 */
public class TowerDefense extends Game {
    
    private static int SX = 640;
    private static int SY = 480;
    Environment e = new Environment(SX,SY);
    int[] keys = new int[256];

    public void initResources() 
    {
        setFPS(60);
    }

    public void update(long elapsedTime)
    {
        boolean lMouseDown = bsInput.isMousePressed(MouseEvent.BUTTON1);
        e.getMouseInput(bsInput.getMouseX(),bsInput.getMouseY(),lMouseDown);
        
        e.update(elapsedTime);
    }

    public BaseInput getInput()
    {
        return bsInput;
    }
    
    public void render(Graphics2D g) 
    {
        ArrayList<Sprite> entities = e.getEntities();
        for(int i = 0; i < entities.size(); i++)
        {
            entities.get(i).render(g);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        GameLoader game = new GameLoader();
        game.setup(new TowerDefense(), new Dimension(SX,SY), false);
        game.start();
        
    }
    
    private void initKeys()
    {
        keys[0] = KeyEvent.VK_0; 
        keys[1] = KeyEvent.VK_1;
        keys[2] = KeyEvent.VK_2;
        keys[3] = KeyEvent.VK_3;
        keys[4] = KeyEvent.VK_4;
        keys[5] = KeyEvent.VK_5;
        keys[6] = KeyEvent.VK_6;
        keys[7] = KeyEvent.VK_7;
        keys[8] = KeyEvent.VK_8;
        keys[9] = KeyEvent.VK_9;       
        keys[10] = KeyEvent.VK_A;
        keys[11] = KeyEvent.VK_B;
        keys[12] = KeyEvent.VK_C;
        keys[13] = KeyEvent.VK_D;
        keys[14] = KeyEvent.VK_E;
        keys[15] = KeyEvent.VK_F;
        keys[16] = KeyEvent.VK_G;
        keys[17] = KeyEvent.VK_H;
        keys[18] = KeyEvent.VK_I;
        keys[19] = KeyEvent.VK_J;
        keys[20] = KeyEvent.VK_K;
        keys[21] = KeyEvent.VK_L;
        keys[22] = KeyEvent.VK_M;
        keys[23] = KeyEvent.VK_N;
        keys[24] = KeyEvent.VK_O;
        keys[25] = KeyEvent.VK_P;
        keys[26] = KeyEvent.VK_Q;
        keys[27] = KeyEvent.VK_R;
        keys[28] = KeyEvent.VK_S;
        keys[29] = KeyEvent.VK_T;
        keys[30] = KeyEvent.VK_U;
        keys[31] = KeyEvent.VK_V;
        keys[32] = KeyEvent.VK_W;
        keys[33] = KeyEvent.VK_X;
        keys[34] = KeyEvent.VK_Y;
        keys[35] = KeyEvent.VK_Z;
        keys[36] = KeyEvent.VK_DOWN;
        keys[37] = KeyEvent.VK_LEFT;
        keys[38] = KeyEvent.VK_UP;
        keys[39] = KeyEvent.VK_RIGHT;
        keys[40] = KeyEvent.VK_NUMPAD0;
        keys[41] = KeyEvent.VK_NUMPAD1;
        keys[42] = KeyEvent.VK_NUMPAD2;
        keys[43] = KeyEvent.VK_NUMPAD3;
        keys[44] = KeyEvent.VK_NUMPAD4;
        keys[45] = KeyEvent.VK_NUMPAD5;
        keys[46] = KeyEvent.VK_NUMPAD6;
        keys[47] = KeyEvent.VK_NUMPAD7;
        keys[48] = KeyEvent.VK_NUMPAD8;
        keys[49] = KeyEvent.VK_NUMPAD9;        
    }
    
    private ArrayList<KeyEvent> getKeys()
    {
        ArrayList<KeyEvent> keysPressed = new ArrayList<KeyEvent>();
        
        return keysPressed;
    }
}
