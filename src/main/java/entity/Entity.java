package entity;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity {
    public int x,y,speed;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public Map<String, BufferedImage[]> imageMap = new HashMap<>();
    public String direction;

    public int spriteNum=0,spriteCounter=0;

}
