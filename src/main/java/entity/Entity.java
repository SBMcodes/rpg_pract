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
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2
            , attackRight1, attackRight2;
    public Map<String, BufferedImage[]> imageMap = new HashMap<>();

    // stores x,y,width,height
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle attackArea;



    public boolean collisionOn = false;
    public int maxLife=0;
    public int life=0;

    public boolean invincible = false;
    int invincibleCount = 0;;


    // Entity position on world map
    public int worldX, worldY;
    // Position of entity on screen
    public int screenX, screenY;
    public int speed;
    public String direction = "down";
    // Imported from SuperObject
    public boolean collision = false;
    public BufferedImage image, image2, image3;


    public int spriteNum = 0, spriteCounter = 0, actionCounter = 0;

    public String[] dialogues = new String[20];
    int dialogueIndex = 0;


    public GamePanel gp;


    // useful when an entity hits another entity
    // 0 - player   1 - npc   2 - monster
    public int entityType = 1;

    public Entity(GamePanel gp, String id) {
        this.id = id;
        this.gp = gp;
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
    }

    public BufferedImage setImage(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(imagePath));
            img = UtilityTool.scaleImage(img, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.out.println("Error loading entity image!");
        }
        return img;
    }

    public BufferedImage setImage(String imagePath,int width,int height) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(imagePath));
            img = UtilityTool.scaleImage(img, width, height);
        } catch (IOException e) {
            System.out.println("Error loading entity image!");
        }
        return img;
    }

    public void setAction() {
    }

    public void gotHit(String dir){
        if(!this.invincible && this.life>=0){
            this.invincible=true;
            this.life-=1;
            gp.playSoundEffect(5);
        }
        switch (dir){
            case "up":
                this.worldY-=5;
                break;
            case "down":
                this.worldY+=5;
                break;
            case "left":
                this.worldX-=5;
                break;
            case "right":
                this.worldX+=5;
                break;
        }
    }

    public void increaseSpriteCounter() {
        spriteCounter++;
        if (spriteCounter > 11) {
            spriteCounter = 0;
            if (spriteNum == 1) {
                spriteNum = 0;
            } else {
                spriteNum = 1;
            }
        }
    }



    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            if (dialogues[0] != null) {
                dialogueIndex = 0;
            } else {
                return;
            }
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // Change the npc direction when talking
        switch (gp.player.direction) {
            case "up":
                this.direction = "down";
                break;
            case "down":
                this.direction = "up";
                break;
            case "left":
                this.direction = "right";
                break;
            case "right":
                this.direction = "left";
                break;
        }
    }

    public void draw(Graphics2D g) {
        BufferedImage image = null;

        int screenX = (worldX + gp.player.screenX) - gp.player.worldX;
        int screenY = (worldY + gp.player.screenY) - gp.player.worldY;

        if (screenX > -gp.tileSize && screenY > -gp.tileSize && screenX < gp.screenWidth && screenY < gp.screenHeight) {
            switch (direction) {
                case "up":
                    image = imageMap.get("up")[spriteNum];
                    break;
                case "down":
                    image = imageMap.get("down")[spriteNum];
                    break;
                case "left":
                    image = imageMap.get("left")[spriteNum];
                    break;
                case "right":
                    image = imageMap.get("right")[spriteNum];
                    break;
            }
            if(this.invincible && this.invincibleCount%3==0){
                g.drawImage(image, screenX, screenY, null);
            }
            else if(!this.invincible){
            g.drawImage(image, screenX, screenY, null);
            }
        }

        if(this.entityType==2){
            g.setColor(new Color(200,200,200));
            g.fillRect(screenX-2,screenY-17,gp.tileSize+4,14);
            g.setColor(new Color(255,0,30));
            g.fillRect(screenX,screenY-15,(int)(gp.tileSize*((double)this.life/this.maxLife)),10);
        }
    }

    public void drawObj(Graphics2D g) {
        int screenX = (worldX + gp.player.screenX) - gp.player.worldX;
        int screenY = (worldY + gp.player.screenY) - gp.player.worldY;

        if (screenX > -gp.tileSize && screenY > -gp.tileSize && screenX < gp.screenWidth && screenY < gp.screenHeight) {
            g.drawImage(image, screenX, screenY, null);
        }
    }

    public void increaseInvincibleCount(){
        if(this.invincible){
            invincibleCount++;
            if(invincibleCount>60){
                invincibleCount=0;
                this.invincible=false;
            }
        }
    }

    public void update() {
        actionCounter++;
        if (this.collisionOn || actionCounter > 180) {
            actionCounter = 0;
            setAction();
        }

        collisionOn = false;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this);

        if (!this.collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
            // animation
            increaseSpriteCounter();

            increaseInvincibleCount();
        }
    }
}