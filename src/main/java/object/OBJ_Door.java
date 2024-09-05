package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject{
    public OBJ_Door(GamePanel gp){
        super(gp,"door");
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/door.png"));
            image = UtilityTool.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch (Exception e){
            System.out.println("Could not read door object image!");
        }
        collision=true;
    }
}
