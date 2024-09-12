package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp,"Wooden Shield");
        down1 = setImage("/images/objects/shield_wood.png");
        defenseValue=1;

        description = "["+id+"]\nA Basic Shield Built\n Using Wood";
    }
}
