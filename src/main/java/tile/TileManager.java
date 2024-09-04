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
        loadMap("/maps/worldV2.txt");

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


            // We are using 0-9 as placeholder as we are not to use single digit number in our map
            setImage(0,"grass00",false);
            setImage(1,"grass00",false);
            setImage(2,"grass00",false);
            setImage(3,"grass00",false);
            setImage(4,"grass00",false);
            setImage(5,"grass00",false);
            setImage(6,"grass00",false);
            setImage(7,"grass00",false);
            setImage(8,"grass00",false);
            setImage(9,"grass00",false);

            setImage(10,"grass00",false);
            setImage(11,"grass01",false);
            setImage(12,"water00",true);
            setImage(13,"water01",true);
            setImage(14,"water02",true);
            setImage(15,"water03",true);
            setImage(16,"water04",true);
            setImage(17,"water05",true);
            setImage(18,"water06",true);
            setImage(19,"water07",true);
            setImage(20,"water08",true);
            setImage(21,"water09",true);
            setImage(22,"water10",true);
            setImage(23,"water11",true);
            setImage(24,"water12",true);
            setImage(25,"water13",true);
            setImage(26,"road00",false);
            setImage(27,"road01",false);
            setImage(28,"road02",false);
            setImage(29,"road03",false);
            setImage(30,"road04",false);
            setImage(31,"road05",false);
            setImage(32,"road06",false);
            setImage(33,"road07",false);
            setImage(34,"road08",false);
            setImage(35,"road09",false);
            setImage(36,"road10",false);
            setImage(37,"road11",false);
            setImage(38,"road12",false);
            setImage(39,"earth",false);
            setImage(40,"wall",true);
            setImage(41,"tree",true);










        } catch (Exception e) {
            System.out.println("Getting Tile Image exception");
        }
    }

    public void setImage(int idx,String imagePath,boolean collision){
        try {
            tile[idx] = new Tile(collision);
            tile[idx].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/"+imagePath+".png"));
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
