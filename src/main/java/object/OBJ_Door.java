package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Door extends Entity {
    public OBJ_Door(GamePanel gp){
        super(gp,"door");
        collision=true;
        image = setImage("/images/objects/door.png");
        down1 = image;
        imageMap.put("down", new BufferedImage[]{down1});

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width=gp.tileSize;
        solidArea.height=32;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;

    }
}
