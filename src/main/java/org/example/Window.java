package org.example;

import javax.swing.*;

public class Window {
    private final String title = "Platformer";
    public static final int WINDOW_WIDTH = 1300;
    public static final int WINDOW_HEIGHT = 850;

    public Window() {
        JFrame window = new JFrame(title);

        window.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setResizable(false);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(new TitleScreen());

        window.setVisible(true);
    }
}
