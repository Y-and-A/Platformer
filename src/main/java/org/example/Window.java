package org.example;

import javax.swing.*;
import java.awt.*;

public class Window {
    public static int WIDTH;
    public static int HEIGHT;
    public static final JFrame window = new JFrame("Platformer");

    public Window() {
        window.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        WIDTH = screenSize.width;
//        HEIGHT  =screenSize.height;
        window.setSize(screenSize);
        WIDTH = window.getWidth();
        HEIGHT = window.getHeight();

        System.out.println(WIDTH + "" + HEIGHT);

        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TitleScreen titleScreen = new TitleScreen();
        window.add(titleScreen);

        LevelSelector levelSelector = new LevelSelector();
        HowTo howTo = new HowTo();

        howTo.setOnBackButtonClicked(() -> {
            changeScene(titleScreen);
        });

        titleScreen.setOnStartGameClicked(() -> {
            changeScene(levelSelector);
        });

        titleScreen.setOnHowToClicked(() -> {
            changeScene(howTo);
        });

        window.setVisible(true);
    }

    public static void changeScene(JPanel panel) {
        window.getContentPane().removeAll();
        window.add(panel);
        window.revalidate();
        window.repaint();
    }
}