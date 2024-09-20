package environment;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;
    LightingState currentLightingState=LightingState.DAY;
//    LightingState[] lightingStates = {LightingState.DAY,LightingState.DUSK,LightingState.NIGHT,LightingState.DAWN};
    int dayCounter=0;
    int dayLimit = 600;
    float filterAlpha=0f;

    public Lighting(GamePanel gp){
        this.gp=gp;
        setLightSource();
    }

    public BufferedImage getLightImage(){

        int centerX = gp.tileSize;
        int centerY = gp.tileSize;

        BufferedImage lightImage = new BufferedImage(gp.tileSize*2,gp.tileSize*2,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) lightImage.getGraphics();

        g.setColor(new Color(0,0,0,1f));

        Color[] colors = new Color[5];
        float[] fraction = {0f,0.25f,0.5f,0.75f,1f};

        colors[0] = new Color(0,0,0,0f);
        colors[1] = new Color(0,0,0,0.25f);
        colors[2] = new Color(0,0,0,0.50f);
        colors[3] = new Color(0,0,0,0.75f);
        colors[4] = new Color(0,0,0,1f);

        RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY,gp.tileSize-4,fraction,colors);


        g.setPaint(gPaint);

        g.fillRect(0,0,gp.tileSize*2,gp.tileSize*2);

        return lightImage;
    }

    public void lightObj(Graphics2D g, Entity obj){

        int centerX = (obj.worldX + gp.player.screenX) - gp.player.worldX;
        int centerY = (obj.worldY + gp.player.screenY) - gp.player.worldY;


        g.drawImage(getLightImage(),centerX-20,centerY-22,null);

    }

    public void setLightSource(){

        darknessFilter=new BufferedImage(gp.screenWidth,gp.screenHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) darknessFilter.getGraphics();
        Composite oldComposite = g.getComposite();




        g.setColor(new Color(0,0,0,0.9f));

        if(gp.player.currentLight!=null){

            int centerX = gp.player.screenX+gp.tileSize/2;
            int centerY = gp.player.screenY+gp.tileSize/2;

            int radius = gp.player.currentLight.lightRadius;



            // Gradation effect
            Color[] colors = new Color[5];
            float[] fraction = {0f,0.25f,0.5f,0.75f,1f};

            colors[0] = new Color(0,0,0,0f);
            colors[1] = new Color(0,0,0,0.25f);
            colors[2] = new Color(0,0,0,0.50f);
            colors[3] = new Color(0,0,0,0.75f);
            colors[4] = new Color(0,0,0,0.9f);

            RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY,radius,fraction,colors);
            g.setPaint(gPaint);

        }

        g.fillRect(0,0,gp.screenWidth,gp.screenHeight);


        g.setComposite(AlphaComposite.SrcIn);

        for(Entity p: gp.projectileList){
            if(p!=null){
                if(p.isGlowing){
                    lightObj(g,p);
                }
            }
        }

        g.setComposite(oldComposite);


        g.dispose();
    }

    public void update(){
        setLightSource();
        if(currentLightingState==LightingState.DAY){
            dayCounter++;
            if(dayCounter>dayLimit){
                dayCounter=0;
                currentLightingState=LightingState.DUSK;
            }
        }

        else if(currentLightingState==LightingState.DUSK){
            filterAlpha+=0.001f;
            if(filterAlpha>1f){
                filterAlpha=1f;
                currentLightingState=LightingState.NIGHT;
            }
        } else if (currentLightingState==LightingState.NIGHT) {
            dayCounter++;
            if(dayCounter>dayLimit){
                dayCounter=0;
                currentLightingState=LightingState.DAWN;
            }
        } else if (currentLightingState==LightingState.DAWN) {
            filterAlpha-=0.001f;
            if(filterAlpha<=0){
                filterAlpha=0;
                currentLightingState=LightingState.DAY;
            }
        }
    }

    public void draw(Graphics2D g){
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,filterAlpha));
        g.drawImage(darknessFilter,0,0,null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
}
