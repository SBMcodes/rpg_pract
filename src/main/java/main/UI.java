package main;

import entity.Entity;
import object.OBJ_ManaCrystal;
import object.Obj_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

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

    BufferedImage heartFull,heartHalf,heartBlank,manaBlank,manaFull;

    ArrayList<String> messages = new ArrayList<>();
    ArrayList<Integer> messagesCounter = new ArrayList<>();

    // Inventory Cursor
    public int maxSlotRow=4,maxSlotCol=5;
    public int slotRow=0,slotCol = 0;

    public int pauseSubState=0;
    public int pauseNum = 0,maxPauseNum=4;

    public int gameOverNum=-1,maxGameOverNum=2;


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

        Entity mana = new OBJ_ManaCrystal(gp);
        manaBlank = mana.image;
        manaFull = mana.image2;
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
            drawMessage();
        } else if (gp.gameState==gp.pauseState) {
            drawPauseScreen();
        } else if (gp.gameState==gp.dialogueState) {
            drawDialogueScreen();
        } else if (gp.gameState==gp.titleState) {
            drawTitleScreen();
        } else if (gp.gameState==gp.gameOverState) {
            this.drawGameOverScreen();
        }
        if(gp.gameState==gp.playState || gp.gameState==gp.pauseState){
            g.setFont(purisa_b);
            drawTime();
            drawPlayerLife();
        }
        if(gp.gameState==gp.characterState){
            drawCharacterScreen();
            drawInventory();
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

    private void drawInventory(){
        // FRAME
        int frameX = 12*gp.tileSize,frameY=gp.tileSize;
        int frameWidth=6*gp.tileSize,frameHeight=5*gp.tileSize;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        // SLOT
        final int slotXstart = frameX+20;
        final int slotYstart = frameY+20;

        int slotX = slotXstart,slotY=slotYstart;
        int slotSize = gp.tileSize+3;

        // PLAYER ITEMS
        for (int i = 0; i < gp.player.inventory.size(); i++) {

            if(gp.player.inventory.get(i)==gp.player.currentWeapon || gp.player.inventory.get(i)==gp.player.curerntShield){
                g.setColor(new Color(144, 129, 199));
                g.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }

            g.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
            slotX+=slotSize;

            if((i+1)%maxSlotCol==0){
                slotX=slotXstart;
                slotY+=slotSize;
            }
        }


        // CURSOR
        int cursorX=slotXstart+(slotCol*slotSize),cursorY=slotYstart+(slotRow*slotSize);
        int cursorWidth=gp.tileSize+2,cursorHeight=gp.tileSize+2;

        // Draw Cursor
        g.setColor(new Color(220,220,220));
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);



        int curItemIdx = getCurItemIndex();
        if(curItemIdx<gp.player.inventory.size()){
            // DESCRIPTION Window
            int dFrameX=frameX,dFrameY=frameY+frameHeight+20,dFrameWidth=frameWidth,dFrameHeight=5*gp.tileSize;
            drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);

            int dTextX = dFrameX+20,dTextY=dFrameY+gp.tileSize;
            g.setFont(g.getFont().deriveFont(18f));


            String dText = gp.player.inventory.get(curItemIdx).description;
            for (String line: dText.split("\n")){
                g.drawString(line,dTextX,dTextY);
                dTextY+=34;
            }
        }
    }

    public int getCurItemIndex(){
        int total = (slotRow*maxSlotCol)+slotCol;
        return total;
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

        // MANA
        x = gp.tileSize/2;
        y+=gp.tileSize;
        if(gp.player.projectile.isProjectileAlive){
            g.drawImage(manaBlank,x,y,null);
        }
        else{
            g.drawImage(manaFull,x,y,null);
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



    public void addMessage(String message){

        messages.add(message);
        messagesCounter.add(0);

//        this.message=message;
//        messageOn=true;
//        frameCounter=0;
    }

    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;

        g.setFont(g.getFont().deriveFont(Font.BOLD,22f));

        for (int i = 0; i < this.messages.size() ; i++) {
            if(messages.get(i)!=null){

                g.setColor(new Color(30,30,30));
                g.drawString(messages.get(i),messageX+2,messageY+2);
                g.setColor(Color.white);
                g.drawString(messages.get(i),messageX,messageY);
                messageY+=32;

                messagesCounter.set(i,messagesCounter.get(i)+1);
                if(messagesCounter.get(i)>180){
                    messages.set(i,null);
                }
            }
        }

//        g.setFont(g.getFont().deriveFont(30f));
//        g.drawString(this.message,(gp.screenWidth/2)-72,65);
//        frameCounter++;
//        if(frameCounter>90){
//            frameCounter=0;
//            this.messageOn=false;
//        }
    }


    public void drawGameOverScreen(){
        g.setFont(monica);
        g.setColor(new Color(0,0,0,150));
        g.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g.setFont(g.getFont().deriveFont(Font.BOLD,110f));

        String text="GAME OVER";
        int x=getXforCenteredText(text),y=4*gp.tileSize;


        g.setColor(Color.black);
        g.drawString(text,x,y);
        g.setColor(Color.white);
        g.drawString(text,x-4,y-4);

        g.setFont(g.getFont().deriveFont(Font.BOLD,50f));

        text="RETRY";
        x=getXforCenteredText(text);
        y+=gp.tileSize*4;
        if(gameOverNum==0){
            g.drawString(">",x-35,y);
        }
        g.drawString(text,x,y);

        text="QUIT";
        x=getXforCenteredText(text);
        y+=(int)(gp.tileSize*1.5);
        if(gameOverNum==1){
            g.drawString(">",x-35,y);
        }
        g.drawString(text,x,y);
    }

    public void executeGameOverCommand(){
        if(gameOverNum==0){
            gp.restartGame();
        } else if (gameOverNum==1) {
            System.exit(0);
        }
    }

    public void drawPauseScreen(){
        g.setFont(monica);
        g.setFont(g.getFont().deriveFont(Font.BOLD,24f));
        g.setColor(new Color(220,220,220));

        int frameX = 6*gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = 8*gp.tileSize;
        int frameHeight = 10*gp.tileSize;

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch(pauseSubState){
            case 0:
                options_top(frameX,frameY);
                break;
            case 1:
                break;
        }
    }

    public void options_top(int frameX,int frameY){
        int textX,textY=frameY;

        g.setFont(g.getFont().deriveFont(30f));

        // Title
        String text="OPTIONS";
        textX = getXforCenteredText(text);
        textY+=gp.tileSize;
        g.drawString(text,textX,textY);

        // Full Screen Option
        textX=frameX+gp.tileSize;
        textY+=(2*gp.tileSize);
        g.drawString("Full Screen",textX,textY);
        if(pauseNum==0){
            g.drawString(">",textX-25,textY);
        }

        // Music
        textY+=(gp.tileSize);
        g.drawString("Music",textX,textY);
        if(pauseNum==1){
            g.drawString(">",textX-25,textY);
        }

        // Sound
        textY+=(gp.tileSize);
        g.drawString("Sound",textX,textY);
        if(pauseNum==2){
            g.drawString(">",textX-25,textY);
        }

        // Control
//        textY+=(gp.tileSize);
//        g.drawString("Control",textX,textY);
//        if(pauseNum==3){
//            g.drawString(">",textX-25,textY);
//        }

        // Quit Game
        textY+=(gp.tileSize);
        g.drawString("Quit",textX,textY);
        if(pauseNum==3){
            g.drawString(">",textX-25,textY);
        }

        // Full Screen Check Box
        textX = frameX + (int)(4.5*gp.tileSize);
        textY = frameY+(2*gp.tileSize)+25;
        g.setStroke(new BasicStroke(3));
        g.drawRoundRect(textX,textY,gp.tileSize/2,gp.tileSize/2,5,5);
        if(Settings.fullScreen){
            g.fillRoundRect(textX+4,textY+4,(gp.tileSize/2)-8,(gp.tileSize/2)-8,5,5);
        }

        // Music Volume
        textY+=gp.tileSize;
        g.drawRoundRect(textX,textY,120,24,5,5);
        g.fillRoundRect(textX+4,textY+4,(int)((120-8)*((float)gp.music.volumeScale/(float)gp.music.maxVolumeScale)),24-8,5,5);
//        g.drawRoundRect(textX,textY,gp.tileSize/2,gp.tileSize/2,5,5);
//        if(Settings.music){
//            g.fillRoundRect(textX+4,textY+4,(gp.tileSize/2)-8,(gp.tileSize/2)-8,5,5);
//        }

        // Sound Volume
        textY+=gp.tileSize;
        g.drawRoundRect(textX,textY,120,24,5,5);
        g.fillRoundRect(textX+4,textY+4,(int)((120-8)*((float)gp.sound.volumeScale/(float)gp.sound.maxVolumeScale)),24-8,5,5);

//        g.drawRoundRect(textX,textY,gp.tileSize/2,gp.tileSize/2,5,5);
//        if(Settings.sound){
//            g.fillRoundRect(textX+4,textY+4,(gp.tileSize/2)-8,(gp.tileSize/2)-8,5,5);
//        }
    }

    public void executePauseCommand(){
        if(pauseNum==0){
            Settings.fullScreen=!Settings.fullScreen;
            if(Settings.fullScreen){
                gp.setFullScreen();
            }
            else{
                gp.setNormalScreen();
            }
        }
        else if(pauseNum==1){
//            Settings.music=!Settings.music;
//            if(Settings.music){
//                gp.playMusic(0);
//            }
//            else{
//                gp.stopMusic();
//            }
        }
        else if(pauseNum==2){
//            Settings.sound=!Settings.sound;
        }
        else if(pauseNum==3){
            System.exit(0);
        }
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
        g.setFont(g.getFont().deriveFont(Font.PLAIN,20f));
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
