package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundUrl[] = new URL[30];

    public Sound(){
        soundUrl[0] = getClass().getResource("/sounds/BlueBoyAdventure.wav");
        soundUrl[1] = getClass().getResource("/sounds/coin.wav");
        soundUrl[2] = getClass().getResource("/sounds/powerup.wav");
        soundUrl[3] = getClass().getResource("/sounds/unlock.wav");
        soundUrl[4] = getClass().getResource("/sounds/fanfare.wav");
    }

    public void setFile(int idx){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[idx]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10f);
            if(!Settings.sound){
                gainControl.setValue(-50f);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Setting Audio Error");
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(-1);
    }

    public void stop(){
        clip.stop();
    }

}
