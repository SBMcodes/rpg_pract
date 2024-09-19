package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;
    LightingState currentLightingState=LightingState.DAY;
//    LightingState[] lightingStates = {LightingState.DAY,LightingState.DUSK,LightingState.NIGHT,LightingState.DAWN};
    int dayCounter=0;
    float filterAlpha=0f;

    public Lighting(GamePanel gp){
        this.gp=gp;
        setLightSource();
    }

    public void setLightSource(){

        darknessFilter=new BufferedImage(gp.screenWidth,gp.screenHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) darknessFilter.getGraphics();

        if(gp.player.currentLight==null){
            g.setColor(new Color(0,0,0,0.9f));
        }
        else{
            //        Area screenArea = new Area(new Rectangle2D.Double(0,0,gp.screenWidth,gp.screenHeight));
//
            int centerX = gp.player.screenX+gp.tileSize/2;
            int centerY = gp.player.screenY+gp.tileSize/2;
//
//        // top left x and y of the light circle
//        int x=centerX-(circleSize/2);
//        int y=centerY-(circleSize/2);
//
//        Shape circleShape = new Ellipse2D.Double(x,y,circleSize,circleSize);
//
//        Area lightArea = new Area(circleShape);
//
//        screenArea.subtract(lightArea);

            // Gradation effect
            Color[] colors = new Color[5];
            float[] fraction = {0f,0.25f,0.5f,0.75f,1f};

            colors[0] = new Color(0,0,0,0f);
            colors[1] = new Color(0,0,0,0.25f);
            colors[2] = new Color(0,0,0,0.50f);
            colors[3] = new Color(0,0,0,0.75f);
            colors[4] = new Color(0,0,0,0.9f);

            RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY,((float) gp.player.currentLight.lightRadius),fraction,colors);

            g.setPaint(gPaint);
    //        g.fill(lightArea);


    //        g.setColor(new Color(0,0,0,0.9f));

    //        g.fill(screenArea);
        }

        g.fillRect(0,0,gp.screenWidth,gp.screenHeight);



        g.dispose();
    }

    public void update(){
        if(gp.player.lightUpdated){
            setLightSource();
            gp.player.lightUpdated=false;
        }
        if(currentLightingState==LightingState.DAY){
            dayCounter++;
            if(dayCounter>600){
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
            if(dayCounter>600){
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
