package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp) {
        super(gp,"old_man");
        speed=1;
        direction="down";

        getImage();
    }

    public NPC_OldMan(GamePanel gp,int worldX,int worldY) {
        this(gp);
        this.worldX=worldX;
        this.worldY=worldY;
    }

    public void getImage(){
        try {
            up1 = setImage("/images/npc/oldman_up_1.png");
            up2 = setImage("/images/npc/oldman_up_2.png");
            down1 = setImage("/images/npc/oldman_down_1.png");
            down2 = setImage("/images/npc/oldman_down_2.png");

            left1 = setImage("/images/npc/oldman_left_1.png");
            left2 = setImage("/images/npc/oldman_left_2.png");
            right1 = setImage("/images/npc/oldman_right_1.png");
            right2 = setImage("/images/npc/oldman_right_2.png");

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

    @Override
    public void setAction(){
        Random random = new Random();
        int i =  random.nextInt(100)+1; // Range -> [1-100]

        if(i<=25){
            direction="up";
        }
        else if(i<=50){
            direction="down";
        } else if (i<=75) {
            direction="left";
        } else{
            direction="right";
        }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    @Override
    public void update(){

        actionCounter++;
        if(this.collisionOn || actionCounter>180){
            actionCounter=0;
            setAction();
        }

        collisionOn=false;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkPlayer(this);

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
            // animation
            increaseSpriteCounter();
        }


    }

}
