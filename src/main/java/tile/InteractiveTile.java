package tile;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    public boolean destructible=false;
    public InteractiveTile(GamePanel gp){
        super(gp,"Interactive Tile");
        maxInvincibleCount=30;
        blinkRate=1;
    }

    public boolean isCorrectItem(Entity e){
        if(e.currentWeapon.entityType==typeAxe){
            return true;
        }
        return false;
    }

    public void playSe(){
    }
    public InteractiveTile getDestroyedForm(){
        return null;
    }

    @Override
    public void update(){
        this.increaseInvincibleCount();
    }
}
