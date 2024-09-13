package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp, "Normal Sword");

        down1 = setImage("/images/objects/sword_normal.png");
        imageMap.put("down", new BufferedImage[]{down1});

        description = "["+id+"]\nAn Old Rusty Sword";

        attackValue=1;

        attackArea.width=36;
        attackArea.height=36;

        entityType = typeSword;
    }
}
