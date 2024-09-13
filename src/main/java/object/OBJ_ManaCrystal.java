package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_ManaCrystal extends Entity {
    public OBJ_ManaCrystal(GamePanel gp){
        super(gp,"Mana Crystal");
        image = setImage("/images/objects/manacrystal_blank.png");
        image2 = setImage("/images/objects/manacrystal_full.png");
        down1 = image;
        down2 = image2;
        imageMap.put("down", new BufferedImage[]{down1,down2});
    }
}
