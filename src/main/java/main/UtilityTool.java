package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTool {
    public static BufferedImage scaleImage(BufferedImage image,int width, int height){
        // This is like a Empty Canvas with Width , Height , ImageType properties
        BufferedImage scaledImage = new BufferedImage(width,height,image.getType());
        // Whatever g will draw will store in scaledImage
        Graphics2D g = scaledImage.createGraphics();
        g.drawImage(image,0,0,width,height,null);
        g.dispose();

        return scaledImage;
    }
}
