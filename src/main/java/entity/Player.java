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

    // Getting KeyHandler & GamePanel to update & draw entity
    public Player(GamePanel gp,KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        x=100;
        y=100;
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
                y-=speed;
            } else if (keyH.pressed.get("down")) {
                direction="down";
                y+=speed;
            }

            if(keyH.pressed.get("left")){
                direction="left";
                x-=speed;
            } else if (keyH.pressed.get("right")) {
                direction="right";
                x+=speed;
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
        g.drawImage(image,x,y,gp.tileSize,gp.tileSize,null);
    }

}
