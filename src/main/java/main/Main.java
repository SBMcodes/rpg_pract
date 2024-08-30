package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // create window using swing jframe
        JFrame window = new JFrame();
        GamePanel gamePanel = new GamePanel();

        window.setTitle("RPG Adventure");

        // Resizable->false
        window.setResizable(false);
        // close window on pressing the button
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);


    }
}
