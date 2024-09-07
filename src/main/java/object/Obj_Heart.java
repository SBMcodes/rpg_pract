package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;

public class Obj_Heart extends SuperObject{

    public Obj_Heart(GamePanel gp) {
        super(gp, "heart");
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/heart_full.png"));
            image = UtilityTool.scaleImage(image,gp.tileSize,gp.tileSize);

            image2 = ImageIO.read(getClass().getResourceAsStream("/images/objects/heart_half.png"));
            image2 = UtilityTool.scaleImage(image2,gp.tileSize,gp.tileSize);

            image3 = ImageIO.read(getClass().getResourceAsStream("/images/objects/heart_blank.png"));
            image3 = UtilityTool.scaleImage(image3,gp.tileSize,gp.tileSize);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
