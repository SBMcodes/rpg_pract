package main;

import entity.Entity;

import java.awt.*;

public class EventHandler extends Entity {

    // Even Trigger Rect
    EventRect[][][] eventRect;

    public int transitionMap=0,transitionRow=0,transitionCol=0;

    public EventHandler(GamePanel gp){
        super(gp,"Event Handler");

        // We could have easily used map here
        eventRect = new EventRect[gp.maxMap][gp.maxWorldRow][gp.maxWorldCol];

        for (int k = 0; k <gp.maxMap ; k++) {
            for (int i = 0; i < gp.maxWorldRow ; i++) {
                for (int j = 0; j < gp.maxWorldCol; j++) {
                    eventRect[k][i][j] = new EventRect();
                    eventRect[k][i][j].x=23;
                    eventRect[k][i][j].y = 23;
                    eventRect[k][i][j].width=2;
                    eventRect[k][i][j].height=2;

                    eventRect[k][i][j].eventRectDefaultX = eventRect[k][i][j].x;
                    eventRect[k][i][j].eventRectDefaultY = eventRect[k][i][j].y;
                }
            }
        }

        setDialogue();

    }

    public void setDialogue(){
        dialogues[0][0]="You fall into a pit!";
        dialogues[1][0]="You drink water\nYour life has been recovered";
    }

    public void drawAllEvents(Graphics2D g){
        g.setColor(Color.pink);
        if(eventRect[0][16][27].init){
            drawEvent(g,0,16,27);
        }
        if(eventRect[0][12][23].init){
            drawEvent(g,0,12,23);
        }
    }

    private void drawEvent(Graphics2D g,int mapNum,int row,int col){
        g.fillRect((col*gp.tileSize)-(gp.player.worldX-gp.player.screenX)+eventRect[mapNum][row][col].x,(row*gp.tileSize)-(gp.player.worldY-gp.player.screenY)+eventRect[mapNum][row][col].y,eventRect[mapNum][row][col].width*2,eventRect[mapNum][row][col].height*2);
    }

    public void drawEvent(Graphics2D g){
        if(hit(0,12,23,"any",false)){
            g.setColor(Color.white);
            g.drawString("Drink Water",gp.screenWidth-240,gp.screenHeight-60);
        }
    }

    public void checkEvent(){
        // deactivate: mapEvent gets deactivated once we use
        // it & gets activated when moved a certain distance
        if(hit(0,16,27,"right",true)){
            damagePitEvent(gp.dialogueState);
        }

        if(hit(0,12,23,"any",false)){
            if(gp.keyH.pressed.get("enter")){
                gp.player.attacking=false;
                healingPoolEvent(gp.dialogueState);
            }
        }

        if(hit(0,39,10,"up",false)){
            teleportPlayer(1,13,12);
        }

        if(hit(1,13,12,"down",false)){
            teleportPlayer(0,39,10);
        }
    }



    public boolean hit(int eventMap,int eventRow,int eventCol,String reqDirection,boolean deactivate){
        boolean hit = false;

        if(eventMap==gp.currentMap){
            if(!eventRect[eventMap][eventRow][eventCol].eventDone && !eventRect[eventMap][eventRow][eventCol].eventActivated){
                gp.player.solidArea.x = gp.player.worldX+gp.player.solidArea.x;
                gp.player.solidArea.y = gp.player.worldY+gp.player.solidArea.y;
                eventRect[eventMap][eventRow][eventCol].x = eventCol*gp.tileSize + eventRect[eventMap][eventRow][eventCol].x;
                eventRect[eventMap][eventRow][eventCol].y = eventRow*gp.tileSize + eventRect[eventMap][eventRow][eventCol].y;

                if(gp.player.solidArea.intersects(this.eventRect[eventMap][eventRow][eventCol])){
                    if(reqDirection.equals("any") || gp.player.direction.equals(reqDirection)){
                        hit=true;
                        if(deactivate){
                            eventRect[eventMap][eventRow][eventCol].eventActivated=true;
                        }
                    }
                }

                gp.player.solidArea.x = gp.player.solidAreaDefaultX;
                gp.player.solidArea.y = gp.player.solidAreaDefaultY;
                eventRect[eventMap][eventRow][eventCol].x = eventRect[eventMap][eventRow][eventCol].eventRectDefaultX;
                eventRect[eventMap][eventRow][eventCol].y = eventRect[eventMap][eventRow][eventCol].eventRectDefaultY;
            } else if (!eventRect[eventMap][eventRow][eventCol].eventDone && eventRect[eventMap][eventRow][eventCol].eventActivated) {
                int dist = Math.max(Math.abs(gp.player.worldX-(eventCol*gp.tileSize)),Math.abs(gp.player.worldY-(eventRow*gp.tileSize)));
                if(dist>2*gp.tileSize){
                    eventRect[eventMap][eventRow][eventCol].eventActivated=false;
                }
            }
        }

        return hit;
    }

    private void teleportPlayer(int map,int row,int col) {
        gp.gameState=gp.transitionState;
        transitionMap=map;
        transitionRow=row;
        transitionCol=col;

        gp.playSoundEffect(12);
    }


    public void damagePitEvent(int gameState){
        gp.gameState=gameState;
//        gp.ui.currentDialogue="You fall into a pit!";
        startDialogue(this,0);
        gp.player.life-=1;
    }

    private void healingPoolEvent(int gameState) {
        gp.gameState=gameState;
//        gp.ui.currentDialogue="You drink water\nYour life has been recovered";
        startDialogue(this,1);
        gp.player.life=gp.player.maxLife;
    }

}
