package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
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
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/grass.png"));

            tile[1] = new Tile(true);
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/wall.png"));

            tile[2] = new Tile(true);
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/water.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/earth.png"));

            tile[4] = new Tile(true);
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/tree.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/sand.png"));

        } catch (Exception e) {
            System.out.println("Getting Tile Image exception");
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
                g.drawImage(tile[mapTileNum[worldRow][worldCol]].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            }

            worldCol++;
            if(worldCol==gp.maxWorldCol){
                worldCol=0;
                worldRow++;
            }
        }
    }
}
