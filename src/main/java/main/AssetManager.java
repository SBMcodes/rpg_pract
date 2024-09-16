package main;

import entity.NPC_OldMan;
import monster.Monster_GreenSlime;
import object.*;
import tile.InteractiveTile;
import tile.Interactive_DryTree;

public class AssetManager {
    public GamePanel gp;

    public AssetManager(GamePanel gp){
        this.gp=gp;
    }

    public void setObject(){

        gp.obj[0] = new OBJ_Potion_Red(gp);
        gp.obj[0].worldX = 26*gp.tileSize;
        gp.obj[0].worldY = 21*gp.tileSize;

        gp.obj[1] = new OBJ_Axe(gp);
        gp.obj[1].worldX = 33*gp.tileSize;
        gp.obj[1].worldY = 21*gp.tileSize;

        gp.obj[2] = new OBJ_CoinBronze(gp);
        gp.obj[2].worldX = 36*gp.tileSize;
        gp.obj[2].worldY = 22*gp.tileSize;

        gp.obj[3] = new Obj_Heart(gp);
        gp.obj[3].worldX = 22*gp.tileSize;
        gp.obj[3].worldY = 29*gp.tileSize;
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

        gp.monster[2] = new Monster_GreenSlime(gp);
        gp.monster[2].worldX = 34*gp.tileSize;
        gp.monster[2].worldY = 42*gp.tileSize;

        gp.monster[3] = new Monster_GreenSlime(gp);
        gp.monster[3].worldX = 38*gp.tileSize;
        gp.monster[3].worldY = 42*gp.tileSize;

    }

    public void setInteractiveTile(){
        int i=0;
        gp.iTile[i]=new Interactive_DryTree(gp);
        gp.iTile[i].worldX=28*gp.tileSize;
        gp.iTile[i].worldY=20*gp.tileSize;
        i++;

        gp.iTile[i]=new Interactive_DryTree(gp);
        gp.iTile[i].worldX=28*gp.tileSize;
        gp.iTile[i].worldY=21*gp.tileSize;
        i++;

        gp.iTile[i]=new Interactive_DryTree(gp);
        gp.iTile[i].worldX=28*gp.tileSize;
        gp.iTile[i].worldY=22*gp.tileSize;
        i++;

        gp.iTile[i]=new Interactive_DryTree(gp);
        gp.iTile[i].worldX=30*gp.tileSize;
        gp.iTile[i].worldY=12*gp.tileSize;
        i++;

        gp.iTile[i]=new Interactive_DryTree(gp);
        gp.iTile[i].worldX=31*gp.tileSize;
        gp.iTile[i].worldY=12*gp.tileSize;
        i++;

        gp.iTile[i]=new Interactive_DryTree(gp);
        gp.iTile[i].worldX=32*gp.tileSize;
        gp.iTile[i].worldY=12*gp.tileSize;
    }
}
