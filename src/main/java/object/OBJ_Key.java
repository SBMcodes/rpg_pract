package object;

import javax.imageio.ImageIO;

public class OBJ_Key extends SuperObject {
    public OBJ_Key(){
        name="key";
        try {
        image = ImageIO.read(getClass().getResourceAsStream("/images/objects/key.png"));
        } catch (Exception e){
            System.out.println("Could not read key object image!");
        }

    }
}
