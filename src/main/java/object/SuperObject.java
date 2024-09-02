package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX,worldY;

    public void draw(Graphics2D g, GamePanel gp){
        int screenX = (worldX+gp.player.screenX)-gp.player.worldX;
        int screenY = (worldY+gp.player.screenY)-gp.player.worldY;

        if(screenX>-gp.tileSize&& screenY>-gp.tileSize && screenX<gp.screenWidth && screenY<gp.screenHeight){
            g.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
        }
    }
}
