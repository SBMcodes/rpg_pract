package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Potion_Red extends Entity {
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp, "Red Potion");

        entityType=this.typeConsumable;
        value=2;

        down1 = setImage("/images/objects/potion_red.png");
        imageMap.put("down", new BufferedImage[]{down1});
        description = "["+this.id+"]\nHeals your life by "+this.value;

        setDialogue();
    }

    public void setDialogue(){
        this.dialogues[0][0]="You drink the "+this.id+"\n Your life has been recovered by "+this.value;
    }

    @Override
    public void use(Entity entity){
        startDialogue(this,0);
        entity.life+=value;
        if(entity.life>=entity.maxLife){
            entity.life = entity.maxLife;
        }
        gp.playSoundEffect(2);
    }
}
