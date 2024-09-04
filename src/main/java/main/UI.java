package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial_40,arial_60b;
    BufferedImage image;

    public boolean messageOn = false;
    public String message = "";

    int frameCounter = 0;

    double curTime = 0.0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp){
        this.gp=gp;
        this.arial_40 = new Font("Arial",Font.PLAIN,30);
        this.arial_60b = new Font("Arial",Font.BOLD,60);
        image = new OBJ_Key(gp).image;
    }

    public void draw(Graphics2D g){
        g.setFont(arial_40);
        g.setColor(Color.white);

        g.drawImage(image,(gp.tileSize/2),gp.tileSize/2,gp.tileSize,gp.tileSize,null);
        g.drawString("X "+gp.player.totalKey,74,65);

        g.drawString(String.valueOf(dFormat.format(curTime)),gp.screenWidth-100,60);

        if(this.messageOn){
            g.setFont(g.getFont().deriveFont(30f));
            g.drawString(this.message,(gp.screenWidth/2)-72,65);
            frameCounter++;
            if(frameCounter>90){
                frameCounter=0;
                this.messageOn=false;
            }
        }

        if(gp.isGameOver){
//            String text = "You WON!";
//            int textWidth = (int)g.getFontMetrics().getStringBounds(text,g).getWidth();
//            int textHeight = (int)g.getFontMetrics().getStringBounds(text,g).getHeight();

            g.setFont(arial_60b);
            g.setColor(Color.white);
            g.drawString("You WON!",(gp.screenWidth/2)-160,120);

            gp.gameThread=null;
        }
        else{
            curTime+=(double)1/60;
        }
    }

    public void showMessage(String message){
        this.message=message;
        messageOn=true;
        frameCounter=0;
    }
}
