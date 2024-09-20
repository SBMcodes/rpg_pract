package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentManager {
    GamePanel gp;
    Lighting lighting;
    EnvironmentState envState=EnvironmentState.LIGHTING;

    public EnvironmentManager(GamePanel gp){
        this.gp=gp;
        setup();
    }

    public void update(){
        lighting.update();
    }
    public void setup(){
        lighting= new Lighting(gp);
    }

    public void draw(Graphics2D g){
        if(envState==EnvironmentState.LIGHTING){
            lighting.draw(g);
        }
    }

    public void resetCurrentEnv(){
        if(envState==EnvironmentState.LIGHTING){
            lighting.dayCounter=0;
            lighting.currentLightingState=LightingState.DAY;
            lighting.filterAlpha=0f;
        }
    }

    public void setEnvState(EnvironmentState env){
        this.envState=env;
    }
}
