package object;

import entity.Projectile;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Fireball extends Projectile {
    public OBJ_Fireball(GamePanel gp) {
        super(gp);

        id = "Fireball";
        speed=6;
        maxLife=100;
        life=maxLife;
        attack=1;
        projectileUseCost=2;
        getImage();
    }

    public void getImage(){
        try {
            up1 = setImage("/images/ability/fireball_up_1.png");
            up2 = setImage("/images/ability/fireball_up_2.png");
            down1 = setImage("/images/ability/fireball_down_1.png");
            down2 = setImage("/images/ability/fireball_down_2.png");

            left1 = setImage("/images/ability/fireball_left_1.png");
            left2 = setImage("/images/ability/fireball_left_2.png");
            right1 = setImage("/images/ability/fireball_right_1.png");
            right2 = setImage("/images/ability/fireball_right_2.png");

            BufferedImage[] up = {up1,up2};
            BufferedImage[] down = {down1,down2};
            BufferedImage[] left = {left1,left2};
            BufferedImage[] right = {right1,right2};

            imageMap.put("up",up);
            imageMap.put("down",down);
            imageMap.put("left",left);
            imageMap.put("right",right);



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Fetching Fireball Image!");
        }
    }
}
