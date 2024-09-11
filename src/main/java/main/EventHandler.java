package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;

    // Even Trigger Rect
    EventRect[][] eventRect;

    public EventHandler(GamePanel gp){
        this.gp=gp;

        // We could have easily used map here
        eventRect = new EventRect[gp.maxWorldRow][gp.maxWorldCol];

        for (int i = 0; i < gp.maxWorldRow ; i++) {
            for (int j = 0; j < gp.maxWorldCol; j++) {
                eventRect[i][j] = new EventRect();
                eventRect[i][j].x=23;
                eventRect[i][j].y = 23;
                eventRect[i][j].width=2;
                eventRect[i][j].height=2;
                // This is dumb
                eventRect[i][j].eventRectDefaultX = eventRect[i][j].x;
                eventRect[i][j].eventRectDefaultY = eventRect[i][j].y;
            }
        }

    }

    public void drawAllEvents(Graphics2D g){
        g.setColor(Color.pink);
        if(eventRect[16][27].init){
            drawEvent(g,16,27);
        }
        if(eventRect[12][23].init){
            drawEvent(g,12,23);
        }
    }

    private void drawEvent(Graphics2D g,int row,int col){
        g.fillRect((col*gp.tileSize)-(gp.player.worldX-gp.player.screenX)+eventRect[row][col].x,(row*gp.tileSize)-(gp.player.worldY-gp.player.screenY)+eventRect[row][col].y,eventRect[row][col].width*2,eventRect[row][col].height*2);
    }

    public void checkEvent(){
        if(hit(16,27,"right",true)){
            damagePitEvent(gp.dialogueState);
        }

//
        if(gp.keyH.pressed.get("enter") && hit(12,23,"any",false)){
            gp.player.attacking=false;
            healingPoolEvent(gp.dialogueState);

        }
    }

    public boolean hit(int eventRow,int eventCol,String reqDirection,boolean deactivate){
        boolean hit = false;

        if(!eventRect[eventRow][eventCol].eventDone && !eventRect[eventRow][eventCol].eventActivated){
            gp.player.solidArea.x = gp.player.worldX+gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY+gp.player.solidArea.y;
            eventRect[eventRow][eventCol].x = eventCol*gp.tileSize + eventRect[eventRow][eventCol].x;
            eventRect[eventRow][eventCol].y = eventRow*gp.tileSize + eventRect[eventRow][eventCol].y;

            if(gp.player.solidArea.intersects(this.eventRect[eventRow][eventCol])){
                if(reqDirection.equals("any") || gp.player.direction.equals(reqDirection)){
                    hit=true;
                    if(deactivate){
                        eventRect[eventRow][eventCol].eventActivated=true;
                    }
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[eventRow][eventCol].x = eventRect[eventRow][eventCol].eventRectDefaultX;
            eventRect[eventRow][eventCol].y = eventRect[eventRow][eventCol].eventRectDefaultY;
        } else if (!eventRect[eventRow][eventCol].eventDone && eventRect[eventRow][eventCol].eventActivated) {
            int dist = Math.max(Math.abs(gp.player.worldX-(eventCol*gp.tileSize)),Math.abs(gp.player.worldY-(eventRow*gp.tileSize)));
            if(dist>2*gp.tileSize){
                eventRect[eventRow][eventCol].eventActivated=false;
            }
        }

        return hit;
    }



    public void damagePitEvent(int gameState){
        gp.gameState=gameState;
        gp.ui.currentDialogue="You fall into a pit!";
        gp.player.life-=1;
    }

    private void healingPoolEvent(int gameState) {
        gp.gameState=gameState;
        gp.ui.currentDialogue="You drink water\nYour life has been recovered";
        gp.player.life=gp.player.maxLife;
    }

}
