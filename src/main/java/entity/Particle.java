package entity;

import main.GamePanel;

import java.awt.*;

public class Particle extends Entity{
    Entity generator;
    Color color;
    int size,xd,yd;

    public Particle(GamePanel gp, Entity generator, Color color,int size,int speed,int maxLife,int xd,int yd) {
        super(gp, "Particle");
        this.generator=generator;
        this.color=color;
        this.size=size;
        this.speed=speed;
        this.maxLife=maxLife;
        this.xd=xd;
        this.yd=yd;

        int offset = (gp.tileSize/2)-(this.size/2);

        life=maxLife;
        worldX=generator.worldX+offset;
        worldY=generator.worldY+offset;

        isProjectileAlive=true;
    }

    @Override
    public void update(){

        if(life<(maxLife/2)+15){
            yd=Math.min(yd+1,3);
        }

        worldX+=((xd*speed)/2);
        worldY+=((yd*speed)/2);

        this.life-=1;
        if(this.life==0){
            this.isProjectileAlive=false;
        }
        if(this.life%10==0){
            this.size-=1;
            if(this.size<=0){
                this.size=0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g){
        int screeX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g.setColor(color);
        g.fillOval(screeX,screenY,this.size,this.size);

    }
}
