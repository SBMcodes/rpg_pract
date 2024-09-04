package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    public TileManager(GamePanel gp){
        this.gp=gp;
        // store n different types of tile
        tile = new Tile[50];

        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        loadMap("/maps/world01.txt");

        getTileImage();
    }

    // Read Map Text File
    public void loadMap(String path){
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row=0,col=0;

            while(row<gp.maxWorldRow){
                String line = br.readLine();
                String[] numbers = line.split(" ") ;

                while(col<gp.maxWorldCol){
                    mapTileNum[row][col] = Integer.parseInt(numbers[col]);
                    col+=1;
                }

                row+=1;
                col=0;
            }

        } catch (Exception e) {
            System.out.println("Error loading map...!");
        }
    }

    // Read Tile Images
    public void getTileImage(){
        try {


            setImage(0,"/images/tiles/grass.png",false);
            setImage(1,"/images/tiles/wall.png",true);
            setImage(2,"/images/tiles/water.png",true);
            setImage(3,"/images/tiles/earth.png",false);
            setImage(4,"/images/tiles/tree.png",true);
            setImage(5,"/images/tiles/sand.png",false);

        } catch (Exception e) {
            System.out.println("Getting Tile Image exception");
        }
    }

    public void setImage(int idx,String imagePath,boolean collision){
        try {
            tile[idx] = new Tile(collision);
            tile[idx].image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            // Optimizing images by not scaling it in loop & scaling it previously
            tile[idx].image = UtilityTool.scaleImage(tile[idx].image,gp.tileSize,gp.tileSize);

        } catch (Exception e) {
            System.out.println("Loading Tile Failed!");
        }
    }

    // Draw tiles to the screen
    public void draw(Graphics2D g){
        int worldRow=0,worldCol=0;

        while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow){

            // Place of tile in the world
            int worldX = worldCol*gp.tileSize;
            int worldY = worldRow*gp.tileSize;

            // Place of tile in the screen
            // Think it as camera/view of the player
            // Basically tells us how far each tile is from screen
            // Think it in terms of 1D instead of 2D
            // (gp.player.worldX+gp.player.screenX) gives us location of the player
            // forget gp.player.screenX & only look after gp.player.worldX
            int screenX = (worldX+gp.player.screenX)-gp.player.worldX;
            int screenY = (worldY+gp.player.screenY)-gp.player.worldY;


            if(screenX>-gp.tileSize&& screenY>-gp.tileSize && screenX<gp.screenWidth && screenY<gp.screenHeight){
//                g.drawImage(tile[mapTileNum[worldRow][worldCol]].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
                // Optimized it by removing the scaling
                g.drawImage(tile[mapTileNum[worldRow][worldCol]].image,screenX,screenY,null);
            }

            worldCol++;
            if(worldCol==gp.maxWorldCol){
                worldCol=0;
                worldRow++;
            }
        }
    }
}
