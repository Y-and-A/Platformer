package org.example;

import javax.swing.*;

public class Window {
    private final String title = "Platformer";
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 850;

    public Window() {
        JFrame window = new JFrame(title);

        window.setSize(WIDTH, HEIGHT);
        window.setResizable(false);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TitleScreen ts = new TitleScreen();
        window.add(ts);

        LevelSelector levelSelector = new LevelSelector();

        ts.setOnStartGameClicked(() -> {
            window.remove(ts);
            window.add(levelSelector);
            window.revalidate();
            window.repaint();
        });

        window.setVisible(true);
    }
}
