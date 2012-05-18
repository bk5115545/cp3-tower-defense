/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package towerdefense;

import com.golden.gamedev.object.Sprite;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author lovinz
 */
public class Environment {
    
    ArrayList<Sprite> entities = new ArrayList<Sprite>();
    ArrayList<KeyEvent> keys = new ArrayList<KeyEvent>();
    Sprite[][] grid;
    
    private int mx;
    private int my;
    private boolean md;
    private int screenX = 640;
    private int screenY = 480;
    private int gridX = 80;
    private int gridY = 80;
    private Tower tower;
    private Monster monster;
    
    public Environment(int sx, int sy)
    {
        screenX = sx;
        screenY = sy;
        
        //temporary
        BufferedImage[] image = new BufferedImage[1];
        BufferedImage[] image2 = new BufferedImage[1];

        try {
            image[0] = getScaledImage(ImageIO.read(new File("art/tiles/airtower.png")),80,80);
        } catch (IOException ex) {
            Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
        }

        Tower t = new Tower(image, 0,240, 1, this);

        try {
            image2[0] = getScaledImage(ImageIO.read(new File("art/sprites/mobair.png")),80,80);
        } catch (IOException ex) {
            Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        Monster m = new Monster(image2, 320, 240);
        
        t.setCurrentTarget(m);

        tower = t;
        
        entities.add(t);        
        entities.add(m);

    }
    
    public void update(long elapsedTime)
    {
        if(md)
        {
            
            BufferedImage image = null;

            try {
                image = getScaledImage(ImageIO.read(new File("art/sprites/airball.png")),25,25);
            } catch (IOException ex) {
                Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
            }            
            
            Projectile p = new Projectile(image, tower);
            entities.add(p);
        }
        
        
        for(int i = 0; i < entities.size(); i++)
        {
            if(entities.get(i) instanceof Projectile)
            {
                entities.get(i).update(elapsedTime);
            }
        }
        
        System.out.println(entities.size());
        
    }
    
    private void placeTower()
    {
        mx -= mx%gridX;
        my -= my%gridY;
        
        if(md)
        {
            
            BufferedImage[] image = new BufferedImage[1];
            
            try {
                image[0] = getScaledImage(ImageIO.read(new File("art/tiles/airtower.png")),80,80);
            } catch (IOException ex) {
                Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Tower t = new Tower(image, mx, my, 1, this);
            
            entities.add(t);
            
        }        
    }
    
    public void getMouseInput(int mx, int my, boolean md)
    {
        this.mx = mx;
        this.my = my;
        this.md = md;
    }
    
    public void getKeyInput(KeyEvent k)
    {
        keys.add(k);
    }
    
    public ArrayList<Sprite> getEntities() { return entities; }
    
    public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
        int imageWidth  = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double)width/imageWidth;
        double scaleY = (double)height/imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

        return bilinearScaleOp.filter(
            image,
            new BufferedImage(width, height, image.getType()));
    }
    
}