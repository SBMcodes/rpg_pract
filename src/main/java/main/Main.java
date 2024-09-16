package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // create window using swing jframe
        JFrame window = new JFrame();


        window.setTitle("RPG Adventure");
        if(Settings.fullScreen){
            window.setUndecorated(true);
        }

        // Resizable->false
        window.setResizable(false);
        // close window on pressing the button
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        GamePanel gamePanel = new GamePanel(window);
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}
