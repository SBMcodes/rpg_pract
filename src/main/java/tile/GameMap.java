package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

// We could have just pass tile manager as an object
// instead of extending it to avoid creating it two times
public class GameMap extends TileManager{

    BufferedImage[] worldMap;
    public boolean miniMapOn=false;

    public GameMap(GamePanel gp) {
        super(gp);
        initWorldMap();
    }

    public void initWorldMap(){
        worldMap = new BufferedImage[gp.maxMap];
        int worldMapWidth=gp.maxWorldCol*gp.tileSize;
        int worldMapHeight=gp.maxWorldRow*gp.tileSize;

        for (int i = 0; i < gp.maxMap; i++) {
            worldMap[i] = new BufferedImage(worldMapWidth,worldMapHeight,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = worldMap[i].createGraphics();
            for (int row = 0; row < gp.maxWorldRow; row++) {
                for (int col = 0; col < gp.maxWorldCol; col++) {
                    int tileNum = mapTileNum[i][row][col];
                    int x = gp.tileSize*col;
                    int y = gp.tileSize*row;
                    g.drawImage(tile[tileNum].image,x,y,null);
                }
            }
        }
    }

    public void drawFullMap(Graphics2D g){
        g.setColor(new Color(0,0,0,20));
        g.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int width=500,height=500;
        int x=(gp.screenWidth/2)-(width/2),y=(gp.screenHeight/2)-(height/2);
        g.drawImage(worldMap[gp.currentMap],x,y,width,height,null);

        // Player
        double scale = (double) (gp.worldWidth)/width;
        int playerX = (int) (gp.player.worldX/scale)+x;
        int playerY = (int) (gp.player.worldY/scale)+y;
        int playerSize = (int) (gp.tileSize/scale);
        g.drawImage(gp.player.down1,playerX,playerY,playerSize*2,playerSize*2,null);
    }


    public void drawMiniMap(Graphics2D g){
        if(this.miniMapOn){
            int width=200,height=200;
            int x=30,y=gp.screenHeight-height-30;

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f));
            g.drawImage(worldMap[gp.currentMap],x,y,width,height,null);

            // Player
            double scale = (double) (gp.worldWidth)/width;
            int playerX = (int) (gp.player.worldX/scale)+x;
            int playerY = (int) (gp.player.worldY/scale)+y;
            int playerSize = (int) (gp.tileSize/scale);
            g.drawImage(gp.player.down1,playerX-3,playerY-3,playerSize*3,playerSize*3,null);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

        }
    }
}
