package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;


public class KeyHandler implements KeyListener {

    public Map<String,Boolean> pressed = new HashMap<>();
    GamePanel gp;

    public KeyHandler(GamePanel gp){
        this.gp=gp;
        pressed.put("up",false);
        pressed.put("down",false);
        pressed.put("left",false);
        pressed.put("right",false);
        pressed.put("enter",false);
        pressed.put("space",false);

        pressed.put("menu_up",false);
        pressed.put("menu_down",false);

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(gp.gameState==gp.playState){
            playStateEvent(code);
        } else if (gp.gameState==gp.pauseState) {
            pauseStateEvent(code);
        } else if (gp.gameState==gp.dialogueState) {
            dialogueStateEvent(code);
        }
        else if (gp.gameState==gp.titleState) {
            titleStateEvent(code);
        }
        else if (gp.gameState==gp.characterState) {
            characterStateEvent(code);
        }

    }

    public void playStateEvent(int code){
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
        else if(code==KeyEvent.VK_ENTER){
            pressed.replace("enter",true);
        }
        else if(code==KeyEvent.VK_SPACE){
            pressed.replace("space",true);
        }
        else if(code==KeyEvent.VK_ESCAPE){
            gp.gameState=gp.pauseState;
        } else if (code==KeyEvent.VK_C) {
            gp.gameState=gp.characterState;
        }
    }

    public void pauseStateEvent(int code){
        if(code==KeyEvent.VK_ESCAPE){
            gp.gameState=gp.playState;
        } else if (code==KeyEvent.VK_W) {
            gp.ui.pauseNum-=1;
            if(gp.ui.pauseNum<0){
                gp.ui.pauseNum=gp.ui.maxPauseNum-1;
            }
            gp.playSoundEffect(8);
        } else if (code==KeyEvent.VK_S) {
            gp.ui.pauseNum+=1;
            gp.playSoundEffect(8);
            if(gp.ui.pauseNum==gp.ui.maxPauseNum){
                gp.ui.pauseNum=0;
            }
        } else if (code==KeyEvent.VK_ENTER) {
            gp.ui.executePauseCommand();
        }
        else if (code==KeyEvent.VK_A) {
            if(gp.ui.pauseSubState==0){
                if(gp.ui.pauseNum==1 && gp.music.volumeScale>0){
                    gp.music.volumeScale-=1;
                    gp.music.checkVolume();
                    gp.playSoundEffect(8);
                } else if(gp.ui.pauseNum==2 && gp.sound.volumeScale>0){
                    gp.sound.volumeScale-=1;
                    gp.sound.checkVolume();
                    gp.playSoundEffect(8);
                }
                Settings.saveConfig(gp);
            }
        }
        else if (code==KeyEvent.VK_D) {
            if(gp.ui.pauseSubState==0){
                if(gp.ui.pauseNum==1 && gp.music.volumeScale<gp.music.maxVolumeScale){
                    gp.music.volumeScale+=1;
                    gp.music.checkVolume();
                    gp.playSoundEffect(8);
                }
                else if(gp.ui.pauseNum==2 && gp.sound.volumeScale<gp.sound.maxVolumeScale){
                    gp.sound.volumeScale+=1;
                    gp.sound.checkVolume();
                    gp.playSoundEffect(8);
                }
                Settings.saveConfig(gp);
            }
        }
    }

    public void titleStateEvent(int code){
        if(gp.ui.titleScreenState==0){
            if(code==KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum<0){
                    gp.ui.commandNum=gp.ui.totalCommand-1;
                }
            }
            else if(code==KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum==gp.ui.totalCommand){
                    gp.ui.commandNum=0;
                }
            }
        }
        if(code==KeyEvent.VK_ENTER){
            gp.ui.executeCommand();
        }
    }

    public void dialogueStateEvent(int code){
        if(code==KeyEvent.VK_ENTER){
            gp.gameState=gp.playState;
        }
    }

    public void characterStateEvent(int code){
        if(code==KeyEvent.VK_C || code==KeyEvent.VK_ESCAPE){
            gp.gameState=gp.playState;
        }
        else if(code==KeyEvent.VK_W){
            if(gp.ui.slotRow-1>=0){
                gp.ui.slotRow--;
            }
            else{
                gp.ui.slotRow=gp.ui.maxSlotRow-1;
            }
            gp.playSoundEffect(8);
        }
        else if(code==KeyEvent.VK_S){
            if(gp.ui.slotRow+1<gp.ui.maxSlotRow){
                gp.ui.slotRow++;
            }
            else{
                gp.ui.slotRow=0;
            }
            gp.playSoundEffect(8);
        }
        else if(code==KeyEvent.VK_A){
            if(gp.ui.slotCol-1>=0){
                gp.ui.slotCol--;
            }
            else{
                gp.ui.slotCol=gp.ui.maxSlotCol-1;
            }
            gp.playSoundEffect(8);
        }
        else if(code==KeyEvent.VK_D){
            if(gp.ui.slotCol+1<gp.ui.maxSlotCol){
                gp.ui.slotCol++;
            }
            else{
                gp.ui.slotCol=0;
            }
            gp.playSoundEffect(8);
        } else if (code==KeyEvent.VK_ENTER) {
            gp.player.selectItem();
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
            pressed.replace("right",false);}
         else if (code==KeyEvent.VK_ENTER) {
            pressed.replace("enter",false);
        }
        else if (code==KeyEvent.VK_SPACE) {
            pressed.replace("space",false);
        }
    }
}
