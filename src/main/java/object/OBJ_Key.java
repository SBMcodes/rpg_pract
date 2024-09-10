package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp){
        super(gp,"key");
        image = setImage("/images/objects/key.png");
        down1 = image;
        imageMap.put("down", new BufferedImage[]{down1});

//        try {
//        image = ImageIO.read(getClass().getResourceAsStream(""));
//        image = UtilityTool.scaleImage(image,gp.tileSize,gp.tileSize);
//        } catch (Exception e){
//            System.out.println("Could not read key object image!");
//        }

    }
}
