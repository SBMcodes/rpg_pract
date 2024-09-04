package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject{
    public OBJ_Chest(GamePanel gp){
        super(gp);
        name="chest";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/chest.png"));
            image = UtilityTool.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch (Exception e){
            System.out.println("Could not read door object image!");
        }

    }
}
