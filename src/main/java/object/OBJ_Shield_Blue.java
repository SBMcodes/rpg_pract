package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp,"Blue Shield");

        down1 = setImage("/images/objects/shield_blue.png");
        imageMap.put("down", new BufferedImage[]{down1});

        description = "["+id+"]\nAn Advanced Shield";

        defenseValue=2;

        entityType = typeShield;
    }

}
