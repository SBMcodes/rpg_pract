package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Rock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Monster_GreenSlime extends Entity {

    public Monster_GreenSlime(GamePanel gp) {
        super(gp, "Green Slime");
        speed=2;
        maxLife=3;
        life=maxLife;
        attack=2;
        exp=2;
        collision=true;

        entityType=typeMonster;

        solidArea = new Rectangle();
        solidArea.x=3;
        solidArea.y=18;
        solidArea.width=gp.tileSize-6;
        solidArea.height=30;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;

        projectile = new OBJ_Rock(gp);

        getImage();
    }

    public void getImage(){
        try {
            up1 = setImage("/images/monster/greenslime_down_1.png");
            up2 = setImage("/images/monster/greenslime_down_2.png");
            down1 = setImage("/images/monster/greenslime_down_1.png");
            down2 = setImage("/images/monster/greenslime_down_2.png");

            left1 = setImage("/images/monster/greenslime_down_1.png");
            left2 = setImage("/images/monster/greenslime_down_2.png");
            right1 = setImage("/images/monster/greenslime_down_1.png");
            right2 = setImage("/images/monster/greenslime_down_2.png");

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

        i =  random.nextInt(100)+1;
        if(i>60 && !projectile.isProjectileAlive){
            projectile.set(worldX,worldY,direction,true,this);
            gp.projectileList.add(this.projectile);
        }
    }

    @Override
    public void update() {
        super.update();
    }
    }