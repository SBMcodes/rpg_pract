package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    BufferedImage image=null;

    int totalKey = 0;

    public final int screenX,screenY;

    // Getting KeyHandler & GamePanel to update & draw entity
    public Player(GamePanel gp,KeyHandler keyH){
        this.gp=gp;
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
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_right_2.png"));

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
            gp.cChecker.checkTile(this);
            int idx = gp.cChecker.checkObject(this,true);

            if(idx!=-1){
                interactObj(idx);
            }

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
        else{
            spriteCounter=6;
        }
    }

    public void interactObj(int idx){
        if(gp.obj[idx].name=="key"){
            gp.obj[idx] = null;
            totalKey+=1;
        }
        else if(gp.obj[idx].name=="door"){
            if(totalKey>0){
                totalKey-=1;
                gp.obj[idx]=null;
            }
        }
    }

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
        g.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
        if(gp.testing.get("isTesting") && gp.testing.get("window")){
            g.setColor(Color.RED);
            g.fillRect(screenX+solidArea.x,screenY+solidArea.x,solidArea.width,solidArea.height);
        }
    }

}
