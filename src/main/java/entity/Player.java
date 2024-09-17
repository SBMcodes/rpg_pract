package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;
import tile.InteractiveTile;
import tile.Interactive_Trunk;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{

    KeyHandler keyH;
    BufferedImage image=null;

    public boolean attacking = false;

    public final int screenX,screenY;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    // Getting KeyHandler & GamePanel to update & draw entity
    public Player(GamePanel gp,KeyHandler keyH){
        super(gp,"player");
        this.keyH=keyH;

        int halfWidth = gp.screenWidth/2;
        int halfHeight = gp.screenHeight/2;
        int halfTileSize = gp.tileSize/2;

        // Location of player on screen
        screenX = halfWidth - halfTileSize;
        screenY= halfHeight - halfTileSize;

        solidArea = new Rectangle(12,16,gp.tileSize-20,gp.tileSize-20);
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;

        attackArea = new Rectangle(0,0,36,36);


        this.collisionOn=false;

        projectile = new OBJ_Fireball(gp);


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues(){
        worldX=23*gp.tileSize;
        worldY=21*gp.tileSize;
        speed=5;
        direction="down";

        // Player status
        this.maxLife=6;
        this.life=this.maxLife;

        entityType = typePlayer;

        currentWeapon = new OBJ_Sword_Normal(gp);
        curerntShield = new OBJ_Shield_Wood(gp);

        setInventoryItems();

        getAttack();
        getDefense();
    }

    public void setInventoryItems(){
        inventory.add(currentWeapon);
        inventory.add(curerntShield);
    }

    public void getAttack(){
        // Attack area will change depending upon the weapon
        attackArea = currentWeapon.attackArea;
        attack = strength* currentWeapon.attackValue;
    }

    public void getDefense(){
        defense = dexterity * curerntShield.defenseValue;
    }


    public void getPlayerImage(){
        try {
            up1 = setImage("/images/player/boy_up_1.png");
            up2 = setImage("/images/player/boy_up_2.png");
            down1 = setImage("/images/player/boy_down_1.png");
            down2 = setImage("/images/player/boy_down_2.png");

            left1 = setImage("/images/player/boy_left_1.png");
            left2 = setImage("/images/player/boy_left_2.png");
            right1 = setImage("/images/player/boy_right_1.png");
            right2 = setImage("/images/player/boy_right_2.png");

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
            System.out.println("Error Fetching Image!");
        }
    }

    public void getPlayerAttackImage(){
        try {
            if(this.currentWeapon.entityType==this.typeSword){
                attackUp1 = setImage("/images/player/boy_attack_up_1.png",gp.tileSize,gp.tileSize*2);
                attackUp2 = setImage("/images/player/boy_attack_up_2.png",gp.tileSize,gp.tileSize*2);
                attackDown1 = setImage("/images/player/boy_attack_down_1.png",gp.tileSize,gp.tileSize*2);
                attackDown2 = setImage("/images/player/boy_attack_down_2.png",gp.tileSize,gp.tileSize*2);

                attackLeft1 = setImage("/images/player/boy_attack_left_1.png",gp.tileSize*2,gp.tileSize);
                attackLeft2 = setImage("/images/player/boy_attack_left_2.png",gp.tileSize*2,gp.tileSize);
                attackRight1 = setImage("/images/player/boy_attack_right_1.png",gp.tileSize*2,gp.tileSize);
                attackRight2 = setImage("/images/player/boy_attack_right_2.png",gp.tileSize*2,gp.tileSize);
            }
            else {
                attackUp1 = setImage("/images/player/boy_axe_up_1.png",gp.tileSize,gp.tileSize*2);
                attackUp2 = setImage("/images/player/boy_axe_up_2.png",gp.tileSize,gp.tileSize*2);
                attackDown1 = setImage("/images/player/boy_axe_down_1.png",gp.tileSize,gp.tileSize*2);
                attackDown2 = setImage("/images/player/boy_axe_down_2.png",gp.tileSize,gp.tileSize*2);

                attackLeft1 = setImage("/images/player/boy_axe_left_1.png",gp.tileSize*2,gp.tileSize);
                attackLeft2 = setImage("/images/player/boy_axe_left_2.png",gp.tileSize*2,gp.tileSize);
                attackRight1 = setImage("/images/player/boy_axe_right_1.png",gp.tileSize*2,gp.tileSize);
                attackRight2 = setImage("/images/player/boy_axe_right_2.png",gp.tileSize*2,gp.tileSize);
            }


            BufferedImage[] up = {attackUp1,attackUp2};
            BufferedImage[] down = {attackDown1,attackDown2};
            BufferedImage[] left = {attackLeft1,attackLeft2};
            BufferedImage[] right = {attackRight1,attackRight2};

            imageMap.put("attackUp",up);
            imageMap.put("attackDown",down);
            imageMap.put("attackLeft",left);
            imageMap.put("attackRight",right);



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Fetching Image!");
        }
    }

    public void update(){

        if(this.attacking){
            startAttack();
        }

        else if(keyH.pressed.get("up") || keyH.pressed.get("down") || keyH.pressed.get("left") || keyH.pressed.get("right") || keyH.pressed.get("enter")){
            if(keyH.pressed.get("up")){
                direction="up";
            } else if (keyH.pressed.get("down")) {
                direction="down";
            }

            if(keyH.pressed.get("left")){
                direction="left";
            } else if (keyH.pressed.get("right")) {
                direction="right";
            }

            // Check if collision exists
            collisionOn=false;

            // Tile interaction
            gp.cChecker.checkTile(this);

            // Object interaction
            int idx = gp.cChecker.checkObject(this,true);

            if(idx!=-1){
                interactObj(idx);
            }


            // Check Interactive tile
            gp.cChecker.checkEntity(this,gp.iTile);


            // NPC interaction
            idx = gp.cChecker.checkEntity(this,gp.npc);
            if(idx!=-1){
                interactNpc(idx);
            }
            else if(gp.keyH.pressed.get("enter")){
                this.attacking=true;
            }

            // Monster interaction
            idx = gp.cChecker.checkEntity(this,gp.monster);
            if(idx!=-1 && !this.invincible){
                interactMonster(gp.monster[idx].invincible,gp.monster[idx].attack);
            }

            // Event interaction
//            gp.eventHandler.checkEvent();

            // May not be necessary
//            gp.keyH.pressed.replace("enter",false);

            // If there is no collision player can move
            if(!this.collisionOn && !keyH.pressed.get("enter")){
                switch (direction){
                    case "up":
                        worldY-=speed;
                        break;
                    case "down":
                        worldY+=speed;
                        break;
                    case "left":
                        worldX-=speed;
                        break;
                    case "right":
                        worldX+=speed;
                        break;
                }
            }



            // We could also have got the total number of sprite images
            // using map & then increase it & set it to 0 once it over bounds
            // but as total number is only 2 per direction so this is also okay
            // player animation
            increaseSpriteCounter();
        }
        else{
            spriteCounter=6;
        }

        if(keyH.pressed.get("space") && !projectile.isProjectileAlive){
            gp.playSoundEffect(9);
            projectile.set(worldX,worldY,direction,true,this);
            gp.projectileList.add(projectile);
        }

        gp.eventHandler.checkEvent();

        increaseInvincibleCount();

        if(this.life<=0){
            gp.gameState = gp.gameOverState;
            gp.playSoundEffect(11);
        }
    }

    private void increaseSpriteAtackCounter() {
        spriteCounter++;
        if (spriteCounter <=5) {
            spriteNum=0;
        } else if (spriteCounter>5 && spriteCounter<=25) {
            spriteNum=1;

            // calculate attack area of player
            int currentWorldX = worldX,currentWorldY=worldY,solidAreaWidth=solidArea.width,solidAreaHeight=solidArea.height;
            switch (direction){
                case "up":
                    worldY-=attackArea.height;
                    break;
                case "down":
                    worldY+=attackArea.height;
                    break;
                case "left":
                    worldX-=attackArea.width;
                    break;
                case "right":
                    worldX+=attackArea.width;
                    break;
            }
            solidArea.width=attackArea.width;
            solidArea.height=attackArea.height;

            int iTileIdx = gp.cChecker.checkEntity(this,gp.iTile);
            if(iTileIdx!=-1){
                breakTile(iTileIdx);
            }

            // Check monster collision with player solid area
            // We could also have get a list of indexes instead of single index
            int monsterIdx = gp.cChecker.checkEntity(this,gp.monster);

            if(monsterIdx!=-1){
                gp.monster[monsterIdx].gotHit(this.direction,this);
            }

            worldX=currentWorldX;
            worldY=currentWorldY;
            solidArea.width=solidAreaWidth;
            solidArea.height=solidAreaHeight;
        }
        else{
            spriteNum=0;
            spriteCounter=0;
            attacking=false;
        }
    }

    private void startAttack() {
        increaseSpriteAtackCounter();
    }


    public void reducePlayerLife(int attack){
        if(!this.invincible){
            int damage = attack-this.defense;
            if(damage<0){
                damage=0;
            }
            this.life-=damage;
            this.invincible=true;
            gp.playSoundEffect(6);
        }
    }

    public void interactMonster(boolean isInvincible,int attack){
        if(!this.invincible && !isInvincible){
            reducePlayerLife(attack);
        }
    }

//    public void interactMonster(){
//        if(!this.invincible){
//            reducePlayerLife();
//        }
//    }

    public void interactObj(int idx){
        if(idx!=-1){
            if(gp.obj[idx].entityType==typePickup){
                gp.obj[idx].use(this);
                gp.obj[idx]=null;
                return;
            }
            if(inventory.size()<this.maxInventorySize){
                inventory.add(gp.obj[idx]);
                gp.ui.addMessage("Got a "+gp.obj[idx].id);
                gp.obj[idx]=null;
            }
            else{
                gp.ui.addMessage("Your inventory is full!");
            }
        }
    }

    public void interactNpc(int idx){
        if(gp.npc[idx].id=="old_man"){
            if(gp.keyH.pressed.get("enter")){
                gp.npc[idx].speak();
                gp.gameState=gp.dialogueState;
            }
        }
    }

    public void addExp(int xp){
        this.exp+=xp;
        gp.ui.addMessage("+"+xp+" XP");
        if(this.exp>=this.nextLevelExp){
            nextLevelExp=nextLevelExp*2;
            level++;
            maxLife+=2;
            strength++;
            getAttack();
            gp.ui.addMessage("Level: "+this.level);
            gp.playSoundEffect(7);
        }
    }

    @Override
    public void draw(Graphics2D g){
//        g.setColor(Color.pink);
//        g.fillRect(x,y,gp.tileSize,gp.tileSize);

        int tempScreenX = screenX,tempScreenY=screenY;

        if(this.attacking){
            switch (direction){
                case "up":
                    image=imageMap.get("attackUp")[spriteNum];
                    tempScreenY=screenY-gp.tileSize;
                    break;
                case "down":
                    image=imageMap.get("attackDown")[spriteNum];
                    break;
                case "left":
                    tempScreenX=screenX-gp.tileSize;
                    image=imageMap.get("attackLeft")[spriteNum];
                    break;
                case "right":
                    image=imageMap.get("attackRight")[spriteNum];
                    break;
            }
        }
        else{
            switch (direction){
                case "up":
                    image=imageMap.get("up")[spriteNum];
                    break;
                case "down":
                    image=imageMap.get("down")[spriteNum];
                    break;
                case "left":
                    image=imageMap.get("left")[spriteNum];
                    break;
                case "right":
                    image=imageMap.get("right")[spriteNum];
                    break;
            }
        }



        if(this.invincible && (this.invincibleCount%2==0)){
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
            g.drawImage(image,tempScreenX,tempScreenY,null);
//            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
        else if(!this.invincible){
            g.drawImage(image,tempScreenX,tempScreenY,null);
        }

        if(gp.testing.get("isTesting") && gp.testing.get("window")){
            g.setColor(Color.RED);
            g.fillRect(screenX+solidArea.x,screenY+solidArea.x,solidArea.width,solidArea.height);
        }
    }

    public void selectItem(){
        int itemIdx = gp.ui.getCurItemIndex();

        if(itemIdx<this.inventory.size()){
            Entity selectedItem = this.inventory.get(itemIdx);
            if(selectedItem.entityType==typeSword || selectedItem.entityType==typeAxe){
                currentWeapon = selectedItem;
                getAttack();
                getPlayerAttackImage();
            }
            else if(selectedItem.entityType==typeShield){
                curerntShield=selectedItem;
                getDefense();
//                getPlayerAttackImage();
            } else if (selectedItem.entityType==typeConsumable) {
                selectedItem.use(this);
                inventory.remove(itemIdx);
            }
        }
    }

    public void breakTile(int idx){
        if(gp.iTile[idx].destructible && gp.iTile[idx].isCorrectItem(this) && !gp.iTile[idx].invincible){
            gp.iTile[idx].playSe();
            gp.iTile[idx].life--;
            gp.iTile[idx].invincible=true;

            // Generate Particle
            generateParticle(gp.iTile[idx],gp.iTile[idx]);

            if(gp.iTile[idx].life==0){
                gp.iTile[idx]=gp.iTile[idx].getDestroyedForm();
            }
        }
    }

}
