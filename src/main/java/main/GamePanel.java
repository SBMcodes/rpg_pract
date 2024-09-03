package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// Acts as a game screen
public class GamePanel extends JPanel implements Runnable{
    // Screen Settings

    // Tile size of player,npc,tiles etc.
    final int originalTitleSize=16;
    // scale current tiles to fit resolution 16*3=48px
    final int scale = 3;

    public final int tileSize = originalTitleSize*scale; // 48*48 tile

    // 4:3 ratio
    public final int maxScreenCol=16;
    public final int maxScreenRow=12;

    public final int screenWidth = maxScreenCol*tileSize; // 768px
    public final int screenHeight = maxScreenRow*tileSize; // 576px

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public final int worldWidth = maxWorldCol*tileSize; // 768px
    public final int worldHeight = maxWorldRow*tileSize;

    public final CollisionChecker cChecker = new CollisionChecker(this);


    final int FPS = 60;

    public boolean isGameOver = false;


    final KeyHandler keyH = new KeyHandler();


    // refreshes game panel 60 times per second
    Thread gameThread;

    public Player player = new Player(this,keyH);

    TileManager tileManager = new TileManager(this);

    public Map<String,Boolean> testing = new HashMap<>();

    public SuperObject obj[] = new SuperObject[10];
    public AssetManager assetManager = new AssetManager(this);

    public UI ui = new UI(this);

    Sound sound = new Sound();
    Sound music = new Sound();


    public GamePanel(){

        init();
        setupGame();

        // sets the dimension of the screen
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        // buffering improves rendering performance
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        // panel is now focused to receive key input
        this.setFocusable(true);

        startGameThread();
        playMusic(0);
    }

    public void setupGame(){
        assetManager.setObject();
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
            repaint();


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
        player.update();
    }


    // Graphics has many functions to draw object on screen
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // Graphics2D extends Graphics and provides more functionality over Graphics
        Graphics2D g2 = (Graphics2D)g;


        // Tiles
        tileManager.draw(g2);

        // Objects
        for (SuperObject o : obj){
            if(o!=null){
                o.draw(g2,this);
            }
        }

        // Player
        player.draw(g2);

        ui.draw(g2);

        // Releases system resources its holding after every frame
        g2.dispose();
    }

    public void playMusic(int idx){
        music.setFile(idx);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSoundEffect(int idx){
        sound.setFile(idx);
        sound.play();
    }


}
