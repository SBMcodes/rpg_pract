package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject{
    public OBJ_Boots(GamePanel gp){
        super(gp,"boots");
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/boots.png"));
            image = UtilityTool.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch (Exception e){
            System.out.println("Could not read door object image!");
        }
    }
}
