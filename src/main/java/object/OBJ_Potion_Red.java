package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Potion_Red extends Entity {
    int value = 2;
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp, "Red Potion");
        entityType=this.typeConsumable;

        down1 = setImage("/images/objects/potion_red.png");
        imageMap.put("down", new BufferedImage[]{down1});
        description = "["+this.id+"]\nHeals your life by "+this.value;
    }

    @Override
    public void use(Entity entity){
        gp.gameState=gp.dialogueState;
        gp.ui.currentDialogue = "You drink the "+this.id+"\n Your life has been recovered by "+this.value;
        entity.life+=value;
        if(entity.life>=entity.maxLife){
            entity.life = entity.maxLife;
        }
        gp.playSoundEffect(2);
    }
}
