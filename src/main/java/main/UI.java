package main;

import entity.Entity;
import object.Obj_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g;
    Font purisa_b,monica;

    public boolean messageOn = false;
    public String message = "";

    int frameCounter = 0;

    double curTime = 0.0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public String currentDialogue = "";

    public int commandNum;
    public final int totalCommand=2;
    public int startCommand = 0;
    public int quitCommand = 1;

    public int titleScreenState = 0; // Title Screen Sub State

    BufferedImage heartFull,heartHalf,heartBlank;

    public UI(GamePanel gp){
        this.gp=gp;
        commandNum=0;

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Purisa Bold.ttf");
            purisa_b = Font.createFont(Font.TRUETYPE_FONT,is);
            is = getClass().getResourceAsStream("/fonts/Monica.ttf");
            monica = Font.createFont(Font.TRUETYPE_FONT,is);

        } catch (Exception e) {
            System.out.println("Loading Font Error!");
        }

        Entity heart = new Obj_Heart(gp);
        heartFull = heart.image;
        heartHalf = heart.image2;
        heartBlank = heart.image3;
    }

    public void draw(Graphics2D g){
        this.g = g;

        g.setFont(purisa_b);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        if(this.messageOn){
            drawMessage();
        }

        if(gp.gameState==gp.playState){
            curTime+=(double)1/60;
        } else if (gp.gameState==gp.pauseState) {
            drawPauseScreen();
        } else if (gp.gameState==gp.dialogueState) {
            drawDialogueScreen();
        } else if (gp.gameState==gp.titleState) {
            drawTitleScreen();
        }
        if(gp.gameState==gp.playState || gp.gameState==gp.pauseState){
            g.setFont(purisa_b);
            drawTime();
            drawPlayerLife();
        }
        if(gp.gameState==gp.characterState){
            drawCharacterScreen();
        }
    }

    private void drawCharacterScreen(){
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(20f));

        final int lineHeight = 34;
        int textX = frameX+20;
        int textY = frameY+lineHeight*2;

        // Stat Title
        textY = drawCharacterStatTitle("Level",textX,textY,lineHeight);
        textY = drawCharacterStatTitle("Life",textX,textY,lineHeight);
        textY = drawCharacterStatTitle("Strength",textX,textY,lineHeight);
        textY = drawCharacterStatTitle("Dexterity",textX,textY,lineHeight);
        textY = drawCharacterStatTitle("Attack",textX,textY,lineHeight);
        textY = drawCharacterStatTitle("Defense",textX,textY,lineHeight);
        textY = drawCharacterStatTitle("Exp",textX,textY,lineHeight);
        textY = drawCharacterStatTitle("Next Level",textX,textY,lineHeight);
        textY = drawCharacterStatTitle("Coin",textX,textY,lineHeight);
        textY+=15;
        textY = drawCharacterStatTitle("Weapon",textX,textY,lineHeight);
        textY+=15;
        textY = drawCharacterStatTitle("Shield",textX,textY,lineHeight);

        // Stat Values
        textY = frameY+lineHeight*2;
        int tailX = (frameX+frameWidth) - 30;

        g.setFont(g.getFont().deriveFont(26f));
        textY=drawCharacterStatValue(String.valueOf(gp.player.level),tailX,textY,lineHeight);
        textY=drawCharacterStatValue(String.valueOf(gp.player.life)+"/"+String.valueOf(gp.player.maxLife),tailX,textY,lineHeight);
        textY=drawCharacterStatValue(String.valueOf(gp.player.strength),tailX,textY,lineHeight);
        textY=drawCharacterStatValue(String.valueOf(gp.player.dexterity),tailX,textY,lineHeight);
        textY=drawCharacterStatValue(String.valueOf(gp.player.attack),tailX,textY,lineHeight);
        textY=drawCharacterStatValue(String.valueOf(gp.player.defense),tailX,textY,lineHeight);
        textY=drawCharacterStatValue(String.valueOf(gp.player.exp),tailX,textY,lineHeight);
        textY=drawCharacterStatValue(String.valueOf(gp.player.nextLevelExp),tailX,textY,lineHeight);
        textY=drawCharacterStatValue(String.valueOf(gp.player.coin),tailX,textY,lineHeight);

        g.drawImage(gp.player.currentWeapon.down1,tailX-gp.tileSize,textY-15,null);
        textY+=(lineHeight);
        g.drawImage(gp.player.curerntShield.down1,tailX-gp.tileSize,textY,null);


        

    }

    public int drawCharacterStatTitle(String text,int textX,int textY,int lineHeight){
        g.drawString(text,textX,textY);
        return textY+lineHeight;
    }

    public int drawCharacterStatValue(String text,int tailX,int textY,int lineHeight){
        int textX = getXforAlignRightText(text,tailX);
        g.drawString(text,textX,textY);

        return textY+lineHeight;
    }

    private void drawPlayerLife() {
        // Blank Heart
        int x = gp.tileSize/2;
        int y=gp.tileSize/2;

        for (int i = 0; i <gp.player.maxLife/2 ; i++) {
            g.drawImage(heartBlank,x,y,null);
            x+=60;
        }

        // Full Heart
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        int drawLife = 0;

        while(drawLife+2<=gp.player.life) {
            drawLife+=2;
            g.drawImage(heartFull,x,y,null);
            x+=60;
        }

        // halfHeart
        while(drawLife+1<=gp.player.life) {
            drawLife+=1;
            g.drawImage(heartHalf,x,y,null);
            x+=60;
        }
    }

    private void drawTitleScreen() {

        if(titleScreenState==0){
            g.setFont(g.getFont().deriveFont(Font.BOLD,48f));


            String text = "Adventure Boy";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;

            g.setColor(new Color(40,40,40));
            g.drawString(text,x+4,y+4);

            g.setColor(new Color(0,0,0));
            g.drawString(text,x+2,y+2);

            g.setColor(new Color(240,240,240,200));
            g.drawString(text,x-2,y-2);

            g.setColor(new Color(255,255,255,200));
            g.drawString(text,x,y);

            // Image
            int imageWidth = gp.tileSize*3;
            int imageHeight = gp.tileSize*3;
            x = gp.screenWidth/2 - (imageWidth/2);
            y+=60;
            g.drawImage(gp.player.down1,x,y,imageWidth,imageHeight,null);

            // Menu Items
            g.setFont(monica);
            g.setFont(g.getFont().deriveFont(Font.BOLD,48f));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y+=220;
            g.drawString(text,x,y);
            if(commandNum==0){
                g.drawString(">",x-40,y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y+=60;
            g.drawString(text,x,y);
            if(commandNum==1){
                g.drawString(">",x-40,y);
            }
        } else if (titleScreenState==1) {
            g.setColor(new Color(220,220,220));
            g.setFont(g.getFont().deriveFont(Font.BOLD,48f));
            String text = "Here is the story...\n There is no story";
            int x = getXforCenteredText(text);
            int y=120;
            for(String line:text.split("\n")){
                x = getXforCenteredText(line);
                g.drawString(line,x,y);
                y+=120;
            }
        }


    }

    public void drawTime(){
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(Font.BOLD,28f));

        g.drawString(String.valueOf(dFormat.format(curTime)),gp.screenWidth-90,60);
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
        g.setFont(monica);
        g.setFont(g.getFont().deriveFont(Font.BOLD,80f));
        g.setColor(new Color(220,220,220));

        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g.drawString(text,x,y);
    }

    public int getXforCenteredText(String text){
        int len = (int)g.getFontMetrics().getStringBounds(text,g).getWidth();
        return gp.screenWidth/2 - len/2;
    }

    public int getXforAlignRightText(String text,int tailX){
        int len = (int)g.getFontMetrics().getStringBounds(text,g).getWidth();
        return tailX-len;
    }

    public void drawDialogueScreen(){
        // Dialogue Window
        int x = gp.tileSize*2,y=gp.tileSize/2;
        int width = gp.screenWidth-(gp.tileSize*4),height=gp.tileSize*4;

        drawSubWindow(x,y,width,height);

        x+=gp.tileSize;
        y+=gp.tileSize;
        g.setFont(g.getFont().deriveFont(Font.PLAIN,24f));
        for(String line:currentDialogue.split("\n")){
            g.drawString(line,x,y);
            y+=40;
        }
    }

    private void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0,0,0,180);
        g.setColor(c);
        g.fillRoundRect(x,y,width,height,35,35);

        c=new Color(255,255,255,200);
        g.setColor(c);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }


    public void executeCommand() {
        if(this.titleScreenState==0){
            if(this.commandNum==this.startCommand){
                this.titleScreenState++;
            } else if (this.commandNum==this.quitCommand) {
                gp.quitGame();
            }
        } else if (this.titleScreenState==1) {
            gp.startNewGame();
        }

    }
}
