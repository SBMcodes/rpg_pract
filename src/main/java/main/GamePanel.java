package main;

import entity.Entity;
import entity.Player;
import tile.InteractiveTile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.stream.Collectors;

// Acts as a game screen
public class GamePanel extends JPanel implements Runnable{
    // Screen Settings

    // Tile size of player,npc,tiles etc.
    final int originalTitleSize=16;
    // scale current tiles to fit resolution 16*3=48px
    final int scale = 3;

    public final int tileSize = originalTitleSize*scale; // 48*48 tile

    // 4:3 ratio
    public final int maxScreenCol=20;
    public final int maxScreenRow=12;

    public final int screenWidth = maxScreenCol*tileSize; // 960px
    public final int screenHeight = maxScreenRow*tileSize; // 576px

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public final int worldWidth = maxWorldCol*tileSize;
    public final int worldHeight = maxWorldRow*tileSize;

    public final CollisionChecker cChecker = new CollisionChecker(this);


    final int FPS = 60;


    public final KeyHandler keyH = new KeyHandler(this);
    public int maxMap=10;
    public int currentMap=0;


    // refreshes game panel 60 times per second
    Thread gameThread;

    public Player player = new Player(this,keyH);
    public Entity[][] npc = new Entity[this.maxMap][10];

    TileManager tileManager = new TileManager(this);

    public Map<String,Boolean> testing = new HashMap<>();

    public Entity[][] obj = new Entity[this.maxMap][10];
    public Entity[][] monster = new Entity[this.maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<Entity>();

    public InteractiveTile[][] iTile = new InteractiveTile[this.maxMap][50];
    public AssetManager assetManager = new AssetManager(this);

    public ArrayList<Entity> entityList = new ArrayList<Entity>();
    public ArrayList<Entity> projectileList = new ArrayList<Entity>();



    public UI ui = new UI(this);

    public EventHandler eventHandler = new EventHandler(this);

    Sound sound = new Sound();
    Sound music = new Sound();

    // GameState
    public int gameState;
    public final int titleState = 0;
    public final int playState=1;
    public final int pauseState=2;
    public final int dialogueState=3;
    public final int characterState=4;
    public final int gameOverState=5;


    // For Full Screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;


    JFrame window;
    public GamePanel(JFrame window){

        this.window=window;

        // sets the dimension of the screen
        this.setPreferredSize(new Dimension(screenWidth2,screenHeight2));
        this.setBackground(Color.BLACK);
        // buffering improves rendering performance
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        // panel is now focused to receive key input
        this.setFocusable(true);

//        startGameThread();
        this.playMusic(0);

    }

    public void setupGame(){
        init();
        assetManager.setObject();
        assetManager.setNPC();
        assetManager.setMonster();
        assetManager.setInteractiveTile();
        gameState = titleState;

        // Draw to Temp Screen -> Draw Temp Screen to window
        tempScreen = new BufferedImage(screenWidth2,screenHeight2,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if(Settings.fullScreen){
            setFullScreen();
        }
    }

    public void restartGame(){
        g2.setColor(Color.black);
        g2.drawRect(0,0,screenWidth2,screenHeight2);
        ui = new UI(this);
//        assetManager.setObject();
        assetManager.setNPC();
        assetManager.setMonster();
        assetManager.setInteractiveTile();

        ArrayList<Entity> prevInventory = new ArrayList<>(this.player.inventory);
        this.player=new Player(this,keyH);
        player.inventory=prevInventory;

        this.playMusic(0);
        this.gameState=playState;
    }

    Integer fullScreenWidth=null,fullScreenHeight=null;
    public void setFullScreen(){
        if(fullScreenWidth==null){
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            gd.setFullScreenWindow(window);
            screenWidth2=window.getWidth();
            screenHeight2=window.getHeight();

            fullScreenWidth=screenWidth2;
            fullScreenHeight=screenHeight2;
        }
        else{
            window.setSize(fullScreenWidth,fullScreenHeight);
            screenWidth2=fullScreenWidth;
            screenHeight2=fullScreenHeight;
        }
        Settings.fullScreen=true;
        Settings.saveConfig(this);
    }

    public void setNormalScreen(){
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice gd = ge.getDefaultScreenDevice();
//        gd.setDisplayMode(new DisplayMode(screenWidth,screenHeight,8,60));
        window.setSize(screenWidth,screenHeight);
        screenWidth2=screenWidth;
        screenHeight2=screenHeight;
        Settings.fullScreen=false;
        Settings.saveConfig(this);
    }



    public void init(){
        testing.put("isTesting",false);
        testing.put("console",true);
        testing.put("window",true);
        testing.put("fps",false);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        long curTime = System.nanoTime();
        double drawInterval = 1_000_000_000/FPS; // 0.0166666 seconds
        double nextDrawTime = System.nanoTime()+drawInterval;

        int fpsCount=0;
        double fpsStart=System.currentTimeMillis();

        while(gameThread!=null){
            // 1. Update: update info such as character position
            update();

            // 2. Draw: draw screen with updated info

            // This is how we call paintComponent inside JPanel
//            repaint();
            drawToTempScreen(); // Draw to buffered image
            drawToScreen(); // Draw buffered image to the screen


            // Other than this there is also delta accumulator method which we
            // use to implement game loop
            // delta = 0
            // delta += (currentTime-lastTime)/drawInterval
            // if(draw>=1){ // execute loop delta--; }
            double remainingTime = nextDrawTime-System.nanoTime();
            if(remainingTime>0){
                try {
                    Thread.sleep((long)(remainingTime/1_000_000));
                }
                catch (Exception e){}
            }
            nextDrawTime+=drawInterval;

            if(testing.get("isTesting") && testing.get("fps")){
                fpsCount+=1;

                // print current fps
                if(System.currentTimeMillis()-fpsStart>=1000){
                    System.out.println(fpsCount+" fps");
                    fpsCount=0;
                    fpsStart=System.currentTimeMillis();
                }
            }


        }
    }

    public void update(){
        if(this.gameState==this.playState){
        player.update();
        for(Entity o: npc[currentMap]){
            if(o!=null){
                o.update();
            }
        }

            for(int i=0;i<monster[currentMap].length;i++){
                if(monster[currentMap][i]!=null){
                    monster[currentMap][i].update();
                    if(monster[currentMap][i].life<=0 && !monster[currentMap][i].invincible){
                        ui.addMessage("Killed a "+monster[currentMap][i].id);
                        this.player.addExp(monster[currentMap][i].exp);

                        monster[currentMap][i].checkDrop();

                        monster[currentMap][i]=null;
                    }
                }
            }

            for(int i=0;i<projectileList.size();i++){
                if(projectileList.get(i)!=null){
                    projectileList.get(i).update();
                    if(!projectileList.get(i).isProjectileAlive){
                        projectileList.set(i,null);
                    }
                }
            }

            for(int i=0;i<particleList.size();i++){
                if(particleList.get(i)!=null){
                    particleList.get(i).update();
                    if(!particleList.get(i).isProjectileAlive){
                        particleList.set(i,null);
                    }
                }
            }

            for(InteractiveTile t: iTile[currentMap]){
                if(t!=null){
                    t.update();
                }
            }

        }
        else if(this.gameState==this.pauseState){
        }
        else if (this.gameState==this.titleState) {

        }
    }

    public void startNewGame(){
        this.gameState=this.playState;
    }

    public void quitGame(){
        this.gameThread=null;
        System.exit(0);
    }


    public void drawToScreen(){
        Graphics g = getGraphics();
        if(g!=null){
            g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2,null);
            g.dispose();
        }
    }

    public void drawToTempScreen(){
        if(gameState==titleState){
            tileManager.drawTitleScreenTiles(g2);
            ui.draw(g2);
        }
        else{
            // Tiles
            tileManager.draw(g2);
            for (InteractiveTile t: iTile[currentMap]){
                if(t!=null){
                    t.draw(g2);
                }
            }

            //  Add Entities to the list
            entityList.add(player);

            for (int i = 0; i <npc[currentMap].length ; i++) {
                if(npc[currentMap][i]!=null){
                    entityList.add(npc[currentMap][i]);
                }
            }

            for (int i = 0; i <obj[currentMap].length ; i++) {
                if(obj[currentMap][i]!=null){
                    entityList.add(obj[currentMap][i]);
                }
            }

            for (int i = 0; i <monster[currentMap].length ; i++) {
                if(monster[currentMap][i]!=null){
                    entityList.add(monster[currentMap][i]);
                }
            }

            for (int i = 0; i <projectileList.size() ; i++) {
                if(projectileList.get(i)!=null){
                    entityList.add(projectileList.get(i));
                }
            }

            for (int i = 0; i <particleList.size() ; i++) {
                if(particleList.get(i)!=null){
                    entityList.add(particleList.get(i));
                }
            }

            // Sort according to worldY
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    return Integer.compare(o1.worldY,o2.worldY);
                }
            });

//            // Draw Entity List
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            // Clear entityList
            entityList.clear();

            eventHandler.drawAllEvents(g2);

            // UI
            ui.draw(g2);

        }


    }

//    // Graphics has many functions to draw object on screen
//    @Override
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        // Graphics2D extends Graphics and provides more functionality over Graphics
//        Graphics2D g2 = (Graphics2D)g;
//
//        // Releases system resources its holding after every frame
//        g2.dispose();
//    }

    public void playMusic(int idx){
        if(Settings.music){
            music.setFile(idx);
            music.play();
            music.loop();
        }

    }

    public void stopMusic(){
        music.stop();
    }

    public void playSoundEffect(int idx){
        sound.setFile(idx);
        sound.play();
    }


}
