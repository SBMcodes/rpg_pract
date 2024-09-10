package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class OBJ_Boots extends Entity {
    public OBJ_Boots(GamePanel gp){
        super(gp,"boots");
        image = setImage("/images/objects/boots.png");
        down1 = image;
        imageMap.put("down", new BufferedImage[]{down1});

//        try {
//            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/boots.png"));
//            image = UtilityTool.scaleImage(image,gp.tileSize,gp.tileSize);
//        } catch (Exception e){
//            System.out.println("Could not read door object image!");
//        }
    }
}
