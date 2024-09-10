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

//        try {
//            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/heart_full.png"));
//            image = UtilityTool.scaleImage(image,gp.tileSize,gp.tileSize);
//
//            image2 = ImageIO.read(getClass().getResourceAsStream("/images/objects/heart_half.png"));
//            image2 = UtilityTool.scaleImage(image2,gp.tileSize,gp.tileSize);
//
//            image3 = ImageIO.read(getClass().getResourceAsStream("/images/objects/heart_blank.png"));
//            image3 = UtilityTool.scaleImage(image3,gp.tileSize,gp.tileSize);
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
    }
}
