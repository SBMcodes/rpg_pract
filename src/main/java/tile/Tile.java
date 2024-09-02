package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    public Tile(){}

    public Tile(boolean isCollision){
        this.collision=isCollision;
    }
}
