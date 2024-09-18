package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundUrl[] = new URL[30];

    // Range: -80f to 6f
    // Realistically: -30f to 6f
    FloatControl fc;
    int volumeScale = 3,maxVolumeScale=5;
    float volume;

    public Sound(){
        soundUrl[0] = getClass().getResource("/sounds/BlueBoyAdventure.wav");
        soundUrl[1] = getClass().getResource("/sounds/coin.wav");
        soundUrl[2] = getClass().getResource("/sounds/powerup.wav");
        soundUrl[3] = getClass().getResource("/sounds/unlock.wav");
        soundUrl[4] = getClass().getResource("/sounds/fanfare.wav");

        soundUrl[5] = getClass().getResource("/sounds/hitmonster.wav");
        soundUrl[6] = getClass().getResource("/sounds/receivedamage.wav");
        soundUrl[7] = getClass().getResource("/sounds/levelup.wav");
        soundUrl[8] = getClass().getResource("/sounds/cursor.wav");

        soundUrl[9] = getClass().getResource("/sounds/burning.wav");
        soundUrl[10] = getClass().getResource("/sounds/cuttree.wav");

        soundUrl[11] = getClass().getResource("/sounds/gameover.wav");

        soundUrl[12] = getClass().getResource("/sounds/stairs.wav");


        // Its done to initialize the sound system
        setFile(0);
    }

    // Can be optimized by pre fetching the sounds during start
    public void setFile(int idx){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[idx]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            this.checkVolume();

//            gainControl.setValue(-10f);
//            if(!Settings.sound){
//                gainControl.setValue(-50f);
//            }
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

    public void checkVolume(){
        switch (volumeScale){
            case 0:
                volume=-80f;
                break;
            case 1:
                volume=-22f;
                break;
            case 2:
                volume=-12f;
                break;
            case 3:
                volume=-4f;
                break;
            case 4:
                volume=0f;
                break;
            case 5:
                volume=6f;
                break;
        }
        fc.setValue(volume);
    }

}
