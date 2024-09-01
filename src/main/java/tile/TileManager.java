package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;
    public TileManager(GamePanel gp){
        this.gp=gp;
        // store n different types of tile
        tile = new Tile[50];

        mapTileNum = new int[gp.maxScreenRow][gp.maxScreenCol];
        loadMap("/maps/test_map.txt");

        getTileImage();
    }

    // Read Map Text File
    public void loadMap(String path){
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row=0,col=0;

            while(row<gp.maxScreenRow){
                String line = br.readLine();
                String[] numbers = line.split(" ") ;

                for(String num: numbers){
                    mapTileNum[row][col] = Integer.parseInt(num);
                    col+=1;
                }
                row+=1;
                col=0;
            }

        } catch (Exception e) {
            System.out.println();
        }
    }

    // Read Tile Images
    public void getTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/grass01.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/water01.png"));

        } catch (Exception e) {
            System.out.println("Getting Tile Image exception");
        }
    }

    // Draw tiles to the screen
    public void draw(Graphics2D g){
        int row=0,col=0;
        int x=0,y=0;

        while(col<gp.maxScreenCol && row<gp.maxScreenRow){
            g.drawImage(tile[mapTileNum[row][col]].image,x,y,gp.tileSize,gp.tileSize,null);
            col++;
            x+=gp.tileSize;
            if(col==gp.maxScreenCol){
                col=0;
                row++;
                x=0;
                y+=gp.tileSize;
            }
        }
    }
}
