package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_CoinBronze extends Entity {

    public OBJ_CoinBronze(GamePanel gp) {
        super(gp, "Bronze Coin");
        down1 = setImage("/images/objects/coin_bronze.png");
        imageMap.put("down",new BufferedImage[]{down1});

        entityType = typePickup;
        value=1;
    }

    @Override
    public void use(Entity e){
        if(e!=gp.player){
            return;
        }
        gp.playSoundEffect(1);
        gp.player.coin+=value;
        gp.ui.addMessage("+"+value+" Coin");
    }
}
