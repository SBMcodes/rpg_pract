package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class Obj_Heart extends Entity {

    public Obj_Heart(GamePanel gp) {
        super(gp, "heart");
        image = setImage("/images/objects/heart_full.png");
        image2 = setImage("/images/objects/heart_half.png");
        image3 = setImage("/images/objects/heart_blank.png");
        down1 = image;
        imageMap.put("down", new BufferedImage[]{down1});

        entityType=typePickup;
        value=2;
    }

    @Override
    public void use(Entity e){
        gp.playSoundEffect(1);
        gp.ui.addMessage("Health +"+value);
        e.life=Math.min(e.life+value,e.maxLife);
    }
}
