package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX,worldY;

    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public GamePanel gp;

    public SuperObject(GamePanel gp){
        this.gp=gp;
    }


    public void draw(Graphics2D g){
        int screenX = (worldX+gp.player.screenX)-gp.player.worldX;
        int screenY = (worldY+gp.player.screenY)-gp.player.worldY;

        if(screenX>-gp.tileSize&& screenY>-gp.tileSize && screenX<gp.screenWidth && screenY<gp.screenHeight){
            g.drawImage(image,screenX,screenY,null);
        }
    }
}
