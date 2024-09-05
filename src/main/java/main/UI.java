package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g;
    Font arial_40,arial_60b;

    public boolean messageOn = false;
    public String message = "";

    int frameCounter = 0;

    double curTime = 0.0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp){
        this.gp=gp;
        this.arial_40 = new Font("Arial",Font.PLAIN,30);
        this.arial_60b = new Font("Arial",Font.BOLD,60);
    }

    public void draw(Graphics2D g){
        this.g = g;



        if(this.messageOn){
            drawMessage();
        }

        if(gp.gameState==gp.playState){
            curTime+=(double)1/60;
        } else if (gp.gameState==gp.pauseState) {
            drawPauseScreen();
        }
        drawTime();
    }

    public void drawTime(){
        g.setFont(arial_40);
        g.setColor(Color.white);

        g.drawString(String.valueOf(dFormat.format(curTime)),gp.screenWidth-100,60);
    }

    public void showMessage(String message){
        this.message=message;
        messageOn=true;
        frameCounter=0;
    }

    public void drawMessage(){
        g.setFont(g.getFont().deriveFont(30f));
        g.drawString(this.message,(gp.screenWidth/2)-72,65);
        frameCounter++;
        if(frameCounter>90){
            frameCounter=0;
            this.messageOn=false;
        }
    }

    public void drawPauseScreen(){
        g.setFont(g.getFont().deriveFont(Font.BOLD,64f));
        g.setColor(Color.white);

        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g.drawString(text,x,y);
    }

    public int getXforCenteredText(String text){
        int len = (int)g.getFontMetrics().getStringBounds(text,g).getWidth();
        return gp.screenWidth/2 - len/2;
    }
}
