package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp, "sword_normal");
        down1 = setImage("/images/objects/sword_normal.png");
        attackValue=1;
    }
}
