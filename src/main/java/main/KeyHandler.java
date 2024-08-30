package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;


public class KeyHandler implements KeyListener {

    public Map<String,Boolean> pressed = new HashMap<>();

    public KeyHandler(){
        pressed.put("up",false);
        pressed.put("down",false);
        pressed.put("left",false);
        pressed.put("right",false);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code==KeyEvent.VK_W){
            pressed.replace("up",true);
        }
        else if(code==KeyEvent.VK_S){
            pressed.replace("down",true);
        }
        else if(code==KeyEvent.VK_A){
            pressed.replace("left",true);
        }
        else if(code==KeyEvent.VK_D){
            pressed.replace("right",true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code==KeyEvent.VK_W){
            pressed.replace("up",false);
        }
        else if(code==KeyEvent.VK_S){
            pressed.replace("down",false);
        }
        else if(code==KeyEvent.VK_A){
            pressed.replace("left",false);
        }
        else if(code==KeyEvent.VK_D){
            pressed.replace("right",false);
        }
    }
}
