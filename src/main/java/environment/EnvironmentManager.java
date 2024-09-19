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

    public void setup(){
        lighting= new Lighting(gp,300);
    }

    public void draw(Graphics2D g){
        if(envState==EnvironmentState.LIGHTING){
            lighting.draw(g);
        }
    }

    public void setEnvState(EnvironmentState env){
        this.envState=env;
    }
}
