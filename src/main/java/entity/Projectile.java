package entity;

import main.GamePanel;

import java.awt.*;

public class Projectile extends Entity{
    Entity user;

    public Projectile(GamePanel gp) {
        super(gp, "Projectile");
    }

    public void set(int worldX,int worldY,String direction,boolean alive,Entity user){
        this.worldX=worldX;
        this.worldY=worldY;
        this.direction=direction;
        this.isProjectileAlive=alive;
        this.user = user;
        // Reset to max life everytime
        this.life = this.maxLife;
        this.collisionOn=false;
    }

    public void update(){
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

        if(user==gp.player){
            int idx = gp.cChecker.checkEntity(this,gp.monster);
            if(idx!=-1){
                gp.monster[idx].gotHit(this.direction,this);
            }
        }
        else{
            gp.cChecker.checkPlayer(this);
            if(this.collisionOn){
                gp.player.reducePlayerLife(attack);
            }
        }


        life--;
        if(life<=0 || this.collisionOn){
            isProjectileAlive=false;
        }

        increaseSpriteCounter();
    }
}
