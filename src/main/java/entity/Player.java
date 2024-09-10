package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    KeyHandler keyH;
    BufferedImage image=null;

    boolean invincible = false;
    int invincibleCount = 0;

    public final int screenX,screenY;

    // Getting KeyHandler & GamePanel to update & draw entity
    public Player(GamePanel gp,KeyHandler keyH){
        super(gp,"player");
        this.keyH=keyH;

        int halfWidth = gp.screenWidth/2;
        int halfHeight = gp.screenHeight/2;
        int halfTileSize = gp.tileSize/2;

        // Location of player on screen
        screenX = halfWidth - halfTileSize;
        screenY= halfHeight - halfTileSize;

        solidArea = new Rectangle(12,16,gp.tileSize-20,gp.tileSize-20);
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        this.collisionOn=false;


        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX=23*gp.tileSize;
        worldY=21*gp.tileSize;
        speed=5;
        direction="down";

        // Player status
        this.maxLife=6;
        this.life=this.maxLife;
    }

    public void getPlayerImage(){
        try {
            up1 = setImage("/images/player/boy_up_1.png");
            up2 = setImage("/images/player/boy_up_2.png");
            down1 = setImage("/images/player/boy_down_1.png");
            down2 = setImage("/images/player/boy_down_2.png");

            left1 = setImage("/images/player/boy_left_1.png");
            left2 = setImage("/images/player/boy_left_2.png");
            right1 = setImage("/images/player/boy_right_1.png");
            right2 = setImage("/images/player/boy_right_2.png");

            BufferedImage[] up = {up1,up2};
            BufferedImage[] down = {down1,down2};
            BufferedImage[] left = {left1,left2};
            BufferedImage[] right = {right1,right2};

            imageMap.put("up",up);
            imageMap.put("down",down);
            imageMap.put("left",left);
            imageMap.put("right",right);



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Fetching Image!");
        }
    }

    public void update(){


        if(keyH.pressed.get("up") || keyH.pressed.get("down") || keyH.pressed.get("left") || keyH.pressed.get("right")){
            if(keyH.pressed.get("up")){
                direction="up";
            } else if (keyH.pressed.get("down")) {
                direction="down";
            }

            if(keyH.pressed.get("left")){
                direction="left";
            } else if (keyH.pressed.get("right")) {
                direction="right";
            }

            // Check if collision exists
            collisionOn=false;

            // Tile interaction
            gp.cChecker.checkTile(this);

            // Object interaction
            int idx = gp.cChecker.checkObject(this,true);

            if(idx!=-1){
                interactObj(idx);
            }

            // NPC interaction
            idx = gp.cChecker.checkEntity(this,gp.npc);
            if(idx!=-1){
                interactNpc(idx);
            }

            // Monster interaction
            idx = gp.cChecker.checkEntity(this,gp.monster);
            if(idx!=-1 && !this.invincible){
                interactMonster();
            }

            // Event interaction
//            gp.eventHandler.checkEvent();

            // May not be necessary
//            gp.keyH.pressed.replace("enter",false);

            // If there is no collision player can move
            if(!this.collisionOn){
                switch (direction){
                    case "up":
                        worldY-=speed;
                        break;
                    case "down":
                        worldY+=speed;
                        break;
                    case "left":
                        worldX-=speed;
                        break;
                    case "right":
                        worldX+=speed;
                        break;
                }
            }



            // We could also have got the total number of sprite images
            // using map & then increase it & set it to 0 once it over bounds
            // but as total number is only 2 per direction so this is also okay
            // player animation
            increaseSpriteCounter();
        }
        else{
            spriteCounter=6;
        }

        gp.eventHandler.checkEvent();

        if(this.invincible){
            invincibleCount++;
            if(invincibleCount>120){
                invincibleCount=0;
                this.invincible=false;
            }
        }
    }

    public void interactMonster(){
        if(!this.invincible){
            this.life-=1;
            this.invincible=true;
        }

    }

    public void interactObj(int idx){

    }

    public void interactNpc(int idx){
        if(gp.npc[idx].id=="old_man"){
            if(gp.keyH.pressed.get("enter")){
                gp.npc[idx].speak();
                gp.gameState=gp.dialogueState;
            }
        }
    }

    @Override
    public void draw(Graphics2D g){
//        g.setColor(Color.pink);
//        g.fillRect(x,y,gp.tileSize,gp.tileSize);

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
        // ImageObserver: null

        if(this.invincible && (this.invincibleCount%2==0)){
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
            g.drawImage(image,screenX,screenY,null);
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
        else if(!this.invincible){
            g.drawImage(image,screenX,screenY,null);
        }
        if(gp.testing.get("isTesting") && gp.testing.get("window")){
            g.setColor(Color.RED);
            g.fillRect(screenX+solidArea.x,screenY+solidArea.x,solidArea.width,solidArea.height);
        }
    }

}
