package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject {
    public OBJ_Key(GamePanel gp){
        super(gp);
        name="key";
        try {
        image = ImageIO.read(getClass().getResourceAsStream("/images/objects/key.png"));
        image = UtilityTool.scaleImage(image,gp.tileSize,gp.tileSize);
        } catch (Exception e){
            System.out.println("Could not read key object image!");
        }

    }
}
