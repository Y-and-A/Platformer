package org.example;

import javax.swing.*;

public class Window {
    private final String title = "Platformer";
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public Window() {
        JFrame frame = new JFrame(title);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);



        frame.setLocationRelativeTo(null);
    }
}
