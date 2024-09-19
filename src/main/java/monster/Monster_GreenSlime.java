package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_CoinBronze;
import object.OBJ_Rock;
import object.Obj_Heart;

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

        this.stopAfterReachingGoal=false;

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

        // If the slime is angry
        if(onPath){

            int goalRow=(gp.player.worldY+gp.player.solidArea.y)/gp.tileSize;
            int goalCol=(gp.player.worldX+gp.player.solidArea.x)/gp.tileSize;

            searchPath(goalRow,goalCol);

            int i =  random.nextInt(100)+1;
            if(i>75 && !projectile.isProjectileAlive){
                projectile.set(worldX,worldY,direction,true,this);
                gp.projectileList.add(this.projectile);
            }
        }
        else{
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

//            i =  random.nextInt(100)+1;
//            if(i>60 && !projectile.isProjectileAlive){
//                projectile.set(worldX,worldY,direction,true,this);
//                gp.projectileList.add(this.projectile);
//            }
        }

    }

    @Override
    public void update() {
        super.update();

        // Get angry when player is near
        int xDist = Math.abs(gp.player.worldX-this.worldX);
        int yDist = Math.abs(gp.player.worldY-this.worldY);
        int tileDist = (xDist+yDist)/gp.tileSize;

//        if(!onPath && tileDist<5){
//            onPath=true;
//        }

        if(onPath && tileDist>12){
            onPath=false;
        }

    }

    @Override
    public void checkDrop(){
        int randI = new Random().nextInt(100)+1;
        if(randI<50){
            dropItem(new OBJ_CoinBronze(gp));
        } else if (randI<75) {
            dropItem(new Obj_Heart(gp));
        }
    }

}