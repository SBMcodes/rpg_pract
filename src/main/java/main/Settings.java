package main;

import java.io.*;

public class Settings {
    public static boolean sound = true;
    public static boolean music = true;
    public static boolean fullScreen = false;

    public static void saveConfig(GamePanel gp){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("settings.txt"));

            if(fullScreen){
                bw.write("1");
            }
            else{
                bw.write("0");
            }
            bw.newLine();
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();
            bw.write(String.valueOf(gp.sound.volumeScale));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            System.out.println("Writing to settings error!");
        }
    }

    public static void loadConfig(GamePanel gp){
        try {
            BufferedReader br = new BufferedReader(new FileReader("settings.txt"));

            String line = br.readLine();
            if(line.equals("1")){
                fullScreen=true;
            }
            else{
                fullScreen=false;
            }

            line = br.readLine();
            gp.music.volumeScale=Integer.parseInt(line);
            gp.music.checkVolume();

            line=br.readLine();
            gp.sound.volumeScale=Integer.parseInt(line);
            gp.sound.checkVolume();
        } catch (Exception e) {
            saveConfig(gp);
            System.out.println("Reading Settings Error!");
        }
    }
}
