package org.example;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;

public class Window {
    public static int WIDTH = 0;
    public static int HEIGHT = 0;
    public static final JFrame window = new JFrame("Platformer");

    public Window() {
        window.setResizable(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension screanSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(screanSize);
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
