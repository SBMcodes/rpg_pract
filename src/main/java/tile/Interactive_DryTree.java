package tile;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class Interactive_DryTree extends InteractiveTile{

    public Interactive_DryTree(GamePanel gp) {
        super(gp);
        id="Dry Tree";
        destructible=true;

        life=2;

        down1 = setImage("/images/tiles/interactive/drytree.png");
        imageMap.put("down",new BufferedImage[]{down1});

    }

    public void playSe(){
        gp.playSoundEffect(10);
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile destroyedTile = new Interactive_Trunk(gp);
        destroyedTile.worldX = this.worldX;
        destroyedTile.worldY = this.worldY;
        return destroyedTile;
    }
}
