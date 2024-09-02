package object;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject{
    public OBJ_Door(){
        name="door";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/images/objects/door.png"));
        } catch (Exception e){
            System.out.println("Could not read door object image!");
        }
        collision=true;
    }
}
