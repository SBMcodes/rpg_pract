package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public Map<String, BufferedImage[]> imageMap = new HashMap<>();

    // stores x,y,width,height
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);


    public boolean collisionOn = false;
    public int maxLife = 0;
    public int life = 0;
    public int maxMana = 0;
    public int mana = 0;

    public boolean invincible = false;
    int invincibleCount = 0;
    ;
    public int maxInvincibleCount = 60;
    public int blinkRate = 3;


    // Entity position on world map
    public int worldX, worldY;
    // Position of entity on screen
    public int screenX, screenY;
    public String direction = "down";
    // Imported from SuperObject
    public boolean collision = false;
    public BufferedImage image, image2, image3;


    public int spriteNum = 0, spriteCounter = 0, actionCounter = 0;

    public String[] dialogues = new String[20];
    int dialogueIndex = 0;


    public GamePanel gp;

    public Entity currentLight;
    public int lightRadius;
    public boolean isGlowing=false;


    // CHARACTER ATTRIBUTES
    // 0 - player   1 - npc   2 - monster
    // useful when an entity hits another entity
    public int entityType = 1;
    public final int typePlayer = 0;
    public final int typeNpc = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6;
    public final int typePickup = 7;
    public final int typeLight = 8;


    public String id;
    public int speed;
    public int level = 1;
    public int strength = 1;
    public int dexterity = 1;
    public int attack = 0;
    public int defense = 0;
    public int exp = 0;
    public int nextLevelExp = 5;
    public int coin = 0;
    public Entity currentWeapon;
    public Entity curerntShield;
    public Projectile projectile;
    public int projectileUseCost;
    public boolean isProjectileAlive = false;

    // ITEM ATTRIBUTES
    public int value = 0;
    public int attackValue = 0;
    public int defenseValue = 0;

    public String description = "";

    // To enable Entity follow a certain destination
    public boolean onPath = false;

    public Entity(GamePanel gp, String id) {
        this.id = id;
        this.gp = gp;
        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
    }

    public BufferedImage setImage(String imagePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(imagePath));
            img = UtilityTool.scaleImage(img, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            System.out.println("Error loading entity image!");
        }
        return img;
    }

    public BufferedImage setImage(String imagePath, int width, int height) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResourceAsStream(imagePath));
            img = UtilityTool.scaleImage(img, width, height);
        } catch (IOException e) {
            System.out.println("Error loading entity image!");
        }
        return img;
    }

    public void setAction() {
    }

    public void gotHit(String dir, Entity entity) {
        if (!this.invincible && this.life >= 0) {
            int damage = entity.attack - this.defense;
            if (damage < 0) {
                return;
            }
            this.invincible = true;
            this.life -= damage;
            gp.playSoundEffect(5);
            this.onPath=true;
        }
        switch (dir) {
            case "up":
                this.worldY -= 5;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkEntity(this, gp.monster[gp.currentMap]);
                if (this.collisionOn) {
                    this.worldY += 5;
                    this.collisionOn = false;
                }
                break;
            case "down":
                this.worldY += 5;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkEntity(this, gp.monster[gp.currentMap]);
                if (this.collisionOn) {
                    this.worldY -= 5;
                    this.collisionOn = false;
                }
                break;
            case "left":
                this.worldX -= 5;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkEntity(this, gp.monster[gp.currentMap]);
                if (this.collisionOn) {
                    this.worldX += 5;
                    this.collisionOn = false;
                }
                break;
            case "right":
                this.worldX += 5;
                gp.cChecker.checkTile(this);
                gp.cChecker.checkEntity(this, gp.monster[gp.currentMap]);
                if (this.collisionOn) {
                    this.worldX -= 5;
                    this.collisionOn = false;
                }
                break;
        }
    }

    public void increaseSpriteCounter() {
        spriteCounter++;
        if (spriteCounter > 11) {
            spriteCounter = 0;
            if (spriteNum == 1) {
                spriteNum = 0;
            } else {
                spriteNum = 1;
            }
        }
    }


    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            if (dialogues[0] != null) {
                dialogueIndex = 0;
            } else {
                return;
            }
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // Change the npc direction when talking
        switch (gp.player.direction) {
            case "up":
                this.direction = "down";
                break;
            case "down":
                this.direction = "up";
                break;
            case "left":
                this.direction = "right";
                break;
            case "right":
                this.direction = "left";
                break;
        }
    }

    public void draw(Graphics2D g) {
        BufferedImage image = null;

        int screenX = (worldX + gp.player.screenX) - gp.player.worldX;
        int screenY = (worldY + gp.player.screenY) - gp.player.worldY;

        if (screenX > -gp.tileSize && screenY > -gp.tileSize && screenX < gp.screenWidth && screenY < gp.screenHeight) {
            switch (direction) {
                case "up":
                    image = imageMap.get("up")[spriteNum];
                    break;
                case "down":
                    image = imageMap.get("down")[spriteNum];
                    break;
                case "left":
                    image = imageMap.get("left")[spriteNum];
                    break;
                case "right":
                    image = imageMap.get("right")[spriteNum];
                    break;
            }
            if (this.invincible && this.invincibleCount % blinkRate == 0) {
                g.drawImage(image, screenX, screenY, null);
            } else if (!this.invincible) {
                g.drawImage(image, screenX, screenY, null);
            }
        }

        if (this.entityType == 2) {
            g.setColor(new Color(200, 200, 200));
            g.fillRect(screenX - 2, screenY - 17, gp.tileSize + 4, 14);
            g.setColor(new Color(255, 0, 30));
            g.fillRect(screenX, screenY - 15, (int) (gp.tileSize * ((double) this.life / this.maxLife)), 10);
        }
    }

    public void drawObj(Graphics2D g) {
        int screenX = (worldX + gp.player.screenX) - gp.player.worldX;
        int screenY = (worldY + gp.player.screenY) - gp.player.worldY;

        if (screenX > -gp.tileSize && screenY > -gp.tileSize && screenX < gp.screenWidth && screenY < gp.screenHeight) {
            g.drawImage(image, screenX, screenY, null);
        }
    }


    public void increaseInvincibleCount() {
        if (this.invincible) {
            invincibleCount++;
            if (invincibleCount > maxInvincibleCount) {
                invincibleCount = 0;
                this.invincible = false;
            }
        }
    }

    public void use(Entity entity) {
    }


    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.iTile[gp.currentMap]);
        gp.cChecker.checkEntity(this, gp.npc[gp.currentMap]);
        gp.cChecker.checkEntity(this, gp.monster[gp.currentMap]);
        gp.cChecker.checkPlayer(this);
    }

    public void update() {
        if (!this.onPath) {
            actionCounter++;
            if (this.collisionOn || actionCounter > 180) {
                actionCounter = 0;
                setAction();
            }
        } else {
            setAction();
        }

        checkCollision();

        if (!this.collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
            // animation
            increaseSpriteCounter();

            increaseInvincibleCount();
        }
    }

    public void checkDrop() {

    }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }


    // Particle methods blueprint
    public Color getParticleColor() {
        return null;
    }

    public int getParticleSize() {
        return 0;
    }

    public int getParticleSpeed() {
        return 0;
    }

    public int getParticleMaxLife() {
        return 0;
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed + 1, maxLife, -1, -2);
        Particle p2 = new Particle(gp, target, color, size, speed + 1, maxLife, 1, -2);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -1, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 1, 1);
        Particle p5 = new Particle(gp, target, color, size, speed + 1, maxLife, -2, 0);
        Particle p6 = new Particle(gp, target, color, size, speed + 1, maxLife, 2, 0);
        Particle p7 = new Particle(gp, target, color, size, speed, maxLife, 0, -3);


        gp.particleList.addAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7));
    }

    public boolean stopAfterReachingGoal=true;

    public void searchPath(int goalRow, int goalCol) {
        int startRow = (this.worldY + solidArea.y) / gp.tileSize;
        int startCol = (this.worldX + solidArea.x) / gp.tileSize;

        gp.pathFinder.setNodes(startRow, startCol, goalRow, goalCol);
        if (gp.pathFinder.search()) {
            int nextX = gp.pathFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pathFinder.pathList.get(0).row * gp.tileSize;


            int enLeftX = this.worldX + this.solidArea.x;
            int enRightX = this.worldX + this.solidArea.x + this.solidArea.width;
            int enTopY = this.worldY + this.solidArea.y;
            int enBottomY = this.worldY + this.solidArea.y + this.solidArea.height;



            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
//                System.out.println("HERE1");
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
//                System.out.println("HERE2");
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
//                System.out.println("HERE3");
                if (enLeftX > nextX) {
                    direction = "left";
                }
                else if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if (collisionOn) {
//                    System.out.println("HERE4-2");
                    direction = "left";
                }
            }
            else if (enTopY>nextY && enLeftX<nextX) {
//                System.out.println("HERE5");
                direction="up";
                checkCollision();
                if(collisionOn){
                    direction="right";
                }
            }
            else if (enTopY<nextY && enLeftX>nextX) {
//                System.out.println("HERE6");
                direction="down";
                checkCollision();
                if(collisionOn){
                    direction="left";
                }
            }
            else if (enTopY<nextY && enLeftX<nextX) {
//                System.out.println("HERE7");
                direction="down";
                checkCollision();
                if(collisionOn){
                    direction="right";
                }
            }

            if (this.stopAfterReachingGoal) {
                int nextRow=gp.pathFinder.pathList.get(0).row;
                int nextCol=gp.pathFinder.pathList.get(0).col;
                if(nextRow==goalRow && nextCol==goalCol){
                    onPath=false;
                }
            }



        }
//        else{
//            onPath=false;
//        }

//
    }
}