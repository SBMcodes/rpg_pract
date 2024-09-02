package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity {
    // Entity position on world map
    public int worldX,worldY;
    // Position of entity on screen
    public int screenX,screenY;
    public int speed;
    public String direction;

    // stores x,y,width,height
    public Rectangle solidArea;
    public boolean collisionOn = false;

    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public Map<String, BufferedImage[]> imageMap = new HashMap<>();

    public int spriteNum=0,spriteCounter=0;

}
