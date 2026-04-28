package org.example;

import javax.swing.*;

public class Window {
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 850;
    public static final JFrame window = new JFrame("Platformer");

    public Window() {
        window.setSize(WIDTH, HEIGHT);
        window.setResizable(false);
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
