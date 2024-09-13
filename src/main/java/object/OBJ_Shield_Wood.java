package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp,"Wooden Shield");

        down1 = setImage("/images/objects/shield_wood.png");
        imageMap.put("down", new BufferedImage[]{down1});

        description = "["+id+"]\nA Basic Shield Built\n Using Wood";

        defenseValue=1;

        entityType = typeShield;
    }
}
