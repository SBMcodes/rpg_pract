package main;

import entity.NPC_OldMan;
import monster.Monster_GreenSlime;
import object.OBJ_Door;

public class AssetManager {
    public GamePanel gp;

    public AssetManager(GamePanel gp){
        this.gp=gp;
    }

    public void setObject(){
//        gp.obj[0] = new OBJ_Door(gp);
//        gp.obj[0].worldX = 21*gp.tileSize;
//        gp.obj[0].worldY = 22*gp.tileSize;
//
//        gp.obj[1] = new OBJ_Door(gp);
//        gp.obj[1].worldX = 23*gp.tileSize;
//        gp.obj[1].worldY = 25*gp.tileSize;
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp,21*gp.tileSize,21*gp.tileSize);

    }

    public void setMonster(){
        gp.monster[0] = new Monster_GreenSlime(gp);
        gp.monster[0].worldX = 23*gp.tileSize;
        gp.monster[0].worldY = 36*gp.tileSize;

        gp.monster[1] = new Monster_GreenSlime(gp);
        gp.monster[1].worldX = 23*gp.tileSize;
        gp.monster[1].worldY = 37*gp.tileSize;

    }
}
