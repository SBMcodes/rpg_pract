package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    String id;
    // Entity position on world map
    public int worldX,worldY;
    // Position of entity on screen
    public int screenX,screenY;
    public int speed;
    public String direction="down";

    // stores x,y,width,height
    public Rectangle solidArea;
    public int solidAreaDefaultX,solidAreaDefaultY;
    public boolean collisionOn = false;

    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public Map<String, BufferedImage[]> imageMap = new HashMap<>();

    public int spriteNum=0,spriteCounter=0,actionCounter=0;

    public String[] dialogues = new String[20];
    int dialogueIndex = 0;

    public int maxLife;
    public int life;

    public GamePanel gp;

    // Imported from SuperObject
    public boolean collision = false;
    public BufferedImage image,image2,image3;

    public Entity(GamePanel gp,String id){
        this.id=id;
        this.gp=gp;
        solidArea=new Rectangle(0,0,gp.tileSize,gp.tileSize);
    }

    public BufferedImage setImage(String imagePath){
        BufferedImage img=null;
        try{
            img = ImageIO.read(getClass().getResourceAsStream(imagePath));
            img = UtilityTool.scaleImage(img,gp.tileSize,gp.tileSize);
        } catch (IOException e) {
            System.out.println("Error loading entity image!");
        }
        return img;
    }

    public void setAction(){
    }

    public void increaseSpriteCounter(){
        spriteCounter++;
        if(spriteCounter>11){
            spriteCounter=0;
            if(spriteNum==1){
                spriteNum=0;
            }
            else {
                spriteNum=1;
            }
        }
    }

    public void speak(){
        if(dialogues[dialogueIndex]==null){
            if(dialogues[0]!=null){
                dialogueIndex=0;
            }
            else{
                return;
            }
        }
        gp.ui.currentDialogue=dialogues[dialogueIndex];
        dialogueIndex++;

        // Change the npc direction when talking
        switch (gp.player.direction){
            case "up":
                this.direction="down";
                break;
            case "down":
                this.direction="up";
                break;
            case "left":
                this.direction="right";
                break;
            case "right":
                this.direction="left";
                break;
        }
    }

    public void draw(Graphics2D g){
        BufferedImage image = null;

        int screenX = (worldX+gp.player.screenX)-gp.player.worldX;
        int screenY = (worldY+gp.player.screenY)-gp.player.worldY;

        if(screenX>-gp.tileSize&& screenY>-gp.tileSize && screenX<gp.screenWidth && screenY<gp.screenHeight){
            switch (direction){
                case "up":
                    image=imageMap.get("up")[spriteNum];
                    break;
                case "down":
                    image=imageMap.get("down")[spriteNum];
                    break;
                case "left":
                    image=imageMap.get("left")[spriteNum];
                    break;
                case "right":
                    image=imageMap.get("right")[spriteNum];
                    break;
            }
            g.drawImage(image,screenX,screenY,null);
        }
    }

    public void drawObj(Graphics2D g){
        int screenX = (worldX+gp.player.screenX)-gp.player.worldX;
        int screenY = (worldY+gp.player.screenY)-gp.player.worldY;

        if(screenX>-gp.tileSize&& screenY>-gp.tileSize && screenX<gp.screenWidth && screenY<gp.screenHeight){
            g.drawImage(image,screenX,screenY,null);
        }
    }

    public void update(){

    }
}
