package main;

import entity.NPC_OldMan;

public class AssetManager {
    public GamePanel gp;

    public AssetManager(GamePanel gp){
        this.gp=gp;
    }

    public void setObject(){

    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp,21*gp.tileSize,21*gp.tileSize);
    }
}
