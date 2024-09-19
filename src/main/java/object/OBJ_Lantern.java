package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Lantern extends Entity {

    public OBJ_Lantern(GamePanel gp) {
        super(gp, "Lantern");
        entityType=typeLight;
        down1 = setImage("/images/objects/lantern.png");
        imageMap.put("down",new BufferedImage[]{down1});
        lightRadius=200;
        description="[Lantern]\nIlluminates your\nsurroundings";
    }
}
