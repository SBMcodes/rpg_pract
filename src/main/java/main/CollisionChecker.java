package main;

import entity.Entity;
import object.SuperObject;

import java.util.Arrays;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp=gp;
    }

    // Check collision
    public void checkTile(Entity entity){

        // Finding position of the rectangle
        int entityLeftWorldX = entity.worldX+entity.solidArea.x;
        int entityRightWorldX = entity.worldX+entity.solidArea.x+entity.solidArea.width;
        int entityTopWorldY = entity.worldY+entity.solidArea.y;
        int entityBottomWorldY = entity.worldY+entity.solidArea.y+entity.solidArea.height;

        // Finding column of the tile
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;


        int tileNum1,tileNum2;

        // See the tile where the player will be after going up/down/left/right
        // At a time only two corner of rectangle has a possibility of collision
        switch (entity.direction){
            case "up":

                entityTopRow = (entityTopWorldY-entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileManager.mapTileNum[entityTopRow][entityRightCol];

                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn=true;
                }
                break;

            case "down":

                entityBottomRow = (entityBottomWorldY+entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileManager.mapTileNum[entityBottomRow][entityRightCol];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn=true;
                }
                break;
            case "left":

                entityLeftCol = (entityLeftWorldX-entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileManager.mapTileNum[entityBottomRow][entityLeftCol];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn=true;
                }
                break;
            case "right":

                entityRightCol = (entityRightWorldX+entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tileManager.mapTileNum[entityBottomRow][entityRightCol];
                if(gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision){
                    entity.collisionOn=true;
                }
                break;
        }
    }

    public int checkObject(Entity entity,boolean isPlayer){
        int idx=-1;

        int count =0;
        for(SuperObject o: gp.obj){
            if(o!=null){

                // A different way to do the same stuff At the we are going to change it to default
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY+entity.solidArea.y;

                o.solidArea.x = o.worldX + o.solidArea.x;
                o.solidArea.y = o.worldY + o.solidArea.y;

                switch (entity.direction){
                    case "up":
                        entity.solidArea.y-=entity.speed;
                        if(entity.solidArea.intersects(o.solidArea)){
                            if(o.collision==true){
                                entity.collisionOn=true;
                            }
                            if(isPlayer){
                                idx=count;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y+=entity.speed;
                        if(entity.solidArea.intersects(o.solidArea)){
                            if(o.collision==true){
                                entity.collisionOn=true;
                            }
                            if(isPlayer){
                                idx=count;
                            }

                        }
                        break;
                    case "left":
                        entity.solidArea.x-=entity.speed;
                        if(entity.solidArea.intersects(o.solidArea)){
                            if(o.collision==true){
                                entity.collisionOn=true;
                            }
                            if(isPlayer){
                                idx=count;
                            }

                        }
                        break;
                    case "right":
                        entity.solidArea.x+=entity.speed;
                        if(entity.solidArea.intersects(o.solidArea)){
                            if(o.collision==true){
                                entity.collisionOn=true;
                            }
                            if(isPlayer){
                                idx=count;
                            }
                        }
                        break;
                }

                o.solidArea.x = o.solidAreaDefaultX;
                o.solidArea.y = o.solidAreaDefaultY;

                entity.solidArea.x  = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
            }

            count+=1;


        }



        return idx;
    }

    public int checkEntity(Entity entity,Entity[] target){
        int idx=-1;

        int count =0;
        for(Entity o: target){
            if(o!=null){

                // A different way to do the same stuff At the end we are going to change it to default
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY+entity.solidArea.y;

                o.solidArea.x = o.worldX + o.solidArea.x;
                o.solidArea.y = o.worldY + o.solidArea.y;

                switch (entity.direction){
                    case "up":
                        idx=count;
                        entity.solidArea.y-=entity.speed;
                        if(entity.solidArea.intersects(o.solidArea)){
                            entity.collisionOn=true;
                        }
                        break;
                    case "down":
                        idx=count;
                        entity.solidArea.y+=entity.speed;
                        if(entity.solidArea.intersects(o.solidArea)){
                            entity.collisionOn=true;

                        }
                        break;
                    case "left":
                        idx=count;
                        entity.solidArea.x-=entity.speed;
                        if(entity.solidArea.intersects(o.solidArea)){
                            entity.collisionOn=true;

                        }
                        break;
                    case "right":
                        idx=count;
                        entity.solidArea.x+=entity.speed;
                        if(entity.solidArea.intersects(o.solidArea)){
                            entity.collisionOn=true;
                            }
                        break;
                        }

                o.solidArea.x = o.solidAreaDefaultX;
                o.solidArea.y = o.solidAreaDefaultY;

                entity.solidArea.x  = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                }
                count+=1;
            }

            return idx;
        }

    public void checkPlayer(Entity entity){

        Entity o = gp.player;

        if(o!=null){

            // A different way to do the same stuff At the end we are going to change it to default
            entity.solidArea.x = entity.worldX + entity.solidArea.x;
            entity.solidArea.y = entity.worldY+entity.solidArea.y;

            o.solidArea.x = o.worldX + o.solidArea.x;
            o.solidArea.y = o.worldY + o.solidArea.y;

            switch (entity.direction){
                case "up":
                    entity.solidArea.y-=entity.speed;
                    if(entity.solidArea.intersects(o.solidArea)){
                        entity.collisionOn=true;
                    }
                    break;
                case "down":
                    entity.solidArea.y+=entity.speed;
                    if(entity.solidArea.intersects(o.solidArea)){
                        entity.collisionOn=true;

                    }
                    break;
                case "left":
                    entity.solidArea.x-=entity.speed;
                    if(entity.solidArea.intersects(o.solidArea)){
                        entity.collisionOn=true;

                    }
                    break;
                case "right":
                    entity.solidArea.x+=entity.speed;
                    if(entity.solidArea.intersects(o.solidArea)){
                        entity.collisionOn=true;
                    }
                    break;
            }

            o.solidArea.x = o.solidAreaDefaultX;
            o.solidArea.y = o.solidAreaDefaultY;

            entity.solidArea.x  = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
        }
    }




}
