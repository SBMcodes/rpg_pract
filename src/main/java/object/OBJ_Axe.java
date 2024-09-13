package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Axe extends Entity {
    public OBJ_Axe(GamePanel gp) {
        super(gp, "Axe");

        down1 = setImage("/images/objects/axe.png");
        imageMap.put("down", new BufferedImage[]{down1});

        description = "["+id+"]\nGood for cutting trees";

        attackValue = 2;

        attackArea.width = 30;
        attackArea.height = 30;

        entityType = typeAxe;

    }
}
