package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Interactive_Trunk extends InteractiveTile {
    public Interactive_Trunk(GamePanel gp) {
        super(gp);
        id="Trunk";

        down1 = setImage("/images/tiles/interactive/trunk.png");
        imageMap.put("down",new BufferedImage[]{down1});

        solidArea= new Rectangle(0,0,0,0);
    }
}
