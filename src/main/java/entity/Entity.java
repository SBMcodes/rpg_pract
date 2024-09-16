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
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2
            , attackRight1, attackRight2;
    public Map<String, BufferedImage[]> imageMap = new HashMap<>();

    // stores x,y,width,height
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle attackArea = new Rectangle(0,0,0,0);



    public boolean collisionOn = false;
    public int maxLife=0;
    public int life=0;
    public int maxMana=0;
    public int mana=0;

    public boolean invincible = false;
    int invincibleCount = 0;;
    public int maxInvincibleCount=60;
    public int blinkRate = 3;


    // Entity position on world map
    public int worldX, worldY;
    // Position of entity on screen
    public int screenX, screenY;
    public String direction = "down";
    // Imported from SuperObject
    public boolean collision = false;
    public BufferedImage image, image2, image3;


    public int spriteNum = 0, spriteCounter = 0, actionCounter = 0;

    public String[] dialogues = new String[20];
    int dialogueIndex = 0;


    public GamePanel gp;



    // CHARACTER ATTRIBUTES
    // 0 - player   1 - npc   2 - monster
    // useful when an entity hits another entity
    public int entityType = 1;
    public final int typePlayer = 0;
    public final int typeNpc = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6;
    public final int typePickup = 7;


    public String id;
    public int speed;
    public int level=1;
    public int strength=1;
    public int dexterity=1;
    public int attack=0;
    public int defense=0;
    public int exp=0;
    public int nextLevelExp=5;
    public int coin=0;
    public Entity currentWeapon;
    public Entity curerntShield;
    public Projectile projectile;
    public int projectileUseCost;
    public boolean isProjectileAlive=false;

    // ITEM ATTRIBUTES
    public int value =0;
    public int attackValue=0;
    public int defenseValue=0;

    public String description="";


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

    public void gotHit(String dir,Entity entity){
        if(!this.invincible && this.life>=0){
            int damage = entity.attack-this.defense;
            if(damage<0){
                return;
            }
            this.invincible=true;
            this.life-=damage;
            gp.playSoundEffect(5);
        }
        switch (dir){
            case "up":
                this.worldY-=5;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkEntity(this,gp.monster);
                if(this.collisionOn){
                    this.worldY+=5;
                    this.collisionOn=false;
                }
                break;
            case "down":
                this.worldY+=5;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkEntity(this,gp.monster);
                if(this.collisionOn){
                    this.worldY-=5;
                    this.collisionOn=false;
                }
                break;
            case "left":
                this.worldX-=5;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkEntity(this,gp.monster);
                if(this.collisionOn){
                    this.worldX+=5;
                    this.collisionOn=false;
                }
                break;
            case "right":
                this.worldX+=5;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkEntity(this,gp.monster);
                if(this.collisionOn){
                    this.worldX-=5;
                    this.collisionOn=false;
                }
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
            if(this.invincible && this.invincibleCount%blinkRate==0){
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
            if(invincibleCount>maxInvincibleCount){
                invincibleCount=0;
                this.invincible=false;
            }
        }
    }

    public void use(Entity entity){}

    public void update() {
        actionCounter++;
        if (this.collisionOn || actionCounter > 180) {
            actionCounter = 0;
            setAction();
        }

        collisionOn = false;


        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.iTile);
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

    public void checkDrop(){

    }

    public void dropItem(Entity droppedItem){
        for (int i = 0; i < gp.obj.length ; i++) {
            if(gp.obj[i]==null){
                gp.obj[i]=droppedItem;
                gp.obj[i].worldX=worldX;
                gp.obj[i].worldY=worldY;
                break;
            }
        }
    }
}