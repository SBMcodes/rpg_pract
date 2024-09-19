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

        int mapNum=0;

        gp.obj[mapNum][0] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][0].worldX = 26*gp.tileSize;
        gp.obj[mapNum][0].worldY = 21*gp.tileSize;

//        gp.obj[mapNum][1] = new OBJ_Axe(gp);
//        gp.obj[mapNum][1].worldX = 33*gp.tileSize;
//        gp.obj[mapNum][1].worldY = 21*gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_CoinBronze(gp);
        gp.obj[mapNum][2].worldX = 36*gp.tileSize;
        gp.obj[mapNum][2].worldY = 22*gp.tileSize;

        gp.obj[mapNum][3] = new Obj_Heart(gp);
        gp.obj[mapNum][3].worldX = 22*gp.tileSize;
        gp.obj[mapNum][3].worldY = 29*gp.tileSize;

        gp.obj[mapNum][3] = new OBJ_Lantern(gp);
        gp.obj[mapNum][3].worldX = 18*gp.tileSize;
        gp.obj[mapNum][3].worldY = 20*gp.tileSize;

        mapNum++;
        gp.obj[mapNum][1] = new OBJ_Axe(gp);
        gp.obj[mapNum][1].worldX = 10*gp.tileSize;
        gp.obj[mapNum][1].worldY = 10*gp.tileSize;
    }

    public void setNPC(){
        int mapNum=0;
        gp.npc[mapNum][0] = new NPC_OldMan(gp,21*gp.tileSize,21*gp.tileSize);

    }

    public void setMonster(){
        int mapNum=0;
        gp.monster[mapNum][0] = new Monster_GreenSlime(gp);
        gp.monster[mapNum][0].worldX = 23*gp.tileSize;
        gp.monster[mapNum][0].worldY = 36*gp.tileSize;

        gp.monster[mapNum][1] = new Monster_GreenSlime(gp);
        gp.monster[mapNum][1].worldX = 23*gp.tileSize;
        gp.monster[mapNum][1].worldY = 37*gp.tileSize;

        gp.monster[mapNum][2] = new Monster_GreenSlime(gp);
        gp.monster[mapNum][2].worldX = 34*gp.tileSize;
        gp.monster[mapNum][2].worldY = 42*gp.tileSize;

        gp.monster[mapNum][3] = new Monster_GreenSlime(gp);
        gp.monster[mapNum][3].worldX = 38*gp.tileSize;
        gp.monster[mapNum][3].worldY = 42*gp.tileSize;

        mapNum++;
        gp.monster[mapNum][0] = new Monster_GreenSlime(gp);
        gp.monster[mapNum][0].worldX = 12*gp.tileSize;
        gp.monster[mapNum][0].worldY = 7*gp.tileSize;


    }

    public void setInteractiveTile(){
        int i=0;
        int mapNum=0;
        gp.iTile[mapNum][i]=new Interactive_DryTree(gp);
        gp.iTile[mapNum][i].worldX=28*gp.tileSize;
        gp.iTile[mapNum][i].worldY=20*gp.tileSize;
        i++;

        gp.iTile[mapNum][i]=new Interactive_DryTree(gp);
        gp.iTile[mapNum][i].worldX=28*gp.tileSize;
        gp.iTile[mapNum][i].worldY=21*gp.tileSize;
        i++;

        gp.iTile[mapNum][i]=new Interactive_DryTree(gp);
        gp.iTile[mapNum][i].worldX=28*gp.tileSize;
        gp.iTile[mapNum][i].worldY=22*gp.tileSize;
        i++;

        gp.iTile[mapNum][i]=new Interactive_DryTree(gp);
        gp.iTile[mapNum][i].worldX=30*gp.tileSize;
        gp.iTile[mapNum][i].worldY=12*gp.tileSize;
        i++;

        gp.iTile[mapNum][i]=new Interactive_DryTree(gp);
        gp.iTile[mapNum][i].worldX=31*gp.tileSize;
        gp.iTile[mapNum][i].worldY=12*gp.tileSize;
        i++;

        gp.iTile[mapNum][i]=new Interactive_DryTree(gp);
        gp.iTile[mapNum][i].worldX=32*gp.tileSize;
        gp.iTile[mapNum][i].worldY=12*gp.tileSize;
    }
}
