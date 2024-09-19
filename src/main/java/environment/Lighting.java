package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp,int circleSize){
        this.gp=gp;
        darknessFilter=new BufferedImage(gp.screenWidth,gp.screenHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) darknessFilter.getGraphics();

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

        RadialGradientPaint gPaint = new RadialGradientPaint(centerX,centerY,((float) circleSize /2),fraction,colors);

        g.setPaint(gPaint);

        g.fillRect(0,0,gp.screenWidth,gp.screenHeight);
//        g.fill(lightArea);


//        g.setColor(new Color(0,0,0,0.9f));

//        g.fill(screenArea);

        g.dispose();
    }

    public void draw(Graphics2D g){
        g.drawImage(darknessFilter,0,0,null);
    }
}
