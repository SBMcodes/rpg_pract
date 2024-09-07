package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;

    // Even Trigger Rect
    Rectangle eventRect;
    int eventRectDefaultX,eventRectDefaultY;

    public EventHandler(GamePanel gp){
        this.gp=gp;

        eventRect = new Rectangle();
        eventRect.x=23;
        eventRect.y = 23;
        eventRect.width=2;
        eventRect.height=2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY=eventRect.y;
    }

    public void drawAllEvents(Graphics2D g){
        g.setColor(Color.pink);
        g.fillRect((27*gp.tileSize)-(gp.player.worldX-gp.player.screenX)+eventRect.x,(16*gp.tileSize)-(gp.player.worldY-gp.player.screenY)+eventRect.y,eventRect.width*2,eventRect.height*2);
        g.fillRect((23*gp.tileSize)-(gp.player.worldX-gp.player.screenX)+eventRect.x,(12*gp.tileSize)-(gp.player.worldY-gp.player.screenY)+eventRect.y,eventRect.width*2,eventRect.height*2);
    }

    public void checkEvent(){
        if(hit(16,27,"right")){
            damagePitEvent(gp.dialogueState);
        }
        else if(hit(12,23,"up") && gp.keyH.pressed.get("enter")){
            healingPoolEvent(gp.dialogueState);
        }
    }

    private void healingPoolEvent(int gameState) {
        gp.gameState=gameState;
        gp.ui.currentDialogue="You drink water\nYour life has been recovered";
        gp.player.life=gp.player.maxLife;
    }

    public boolean hit(int eventRow,int eventCol,String reqDirection){
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX+gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY+gp.player.solidArea.y;
        eventRect.x = eventCol*gp.tileSize + eventRect.x;
        eventRect.y = eventRow*gp.tileSize + eventRect.y;

        if(gp.player.solidArea.intersects(this.eventRect)){
            if(reqDirection.equals("any") || gp.player.direction.equals(reqDirection)){
                hit=true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    public void damagePitEvent(int gameState){
        gp.gameState=gameState;
        gp.ui.currentDialogue="You fall into a pit!";
        gp.player.life-=1;
    }
}
