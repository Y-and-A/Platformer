package org.example;

import javax.swing.*;
import java.awt.*;

public class TitleScreen extends JPanel {
    private JButton startGame;
    private JButton howTo;

    public TitleScreen() {
        setBounds(0, 0, Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        setLayout(null);

        add(new TitleScreenButtons("Start game", Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 - 15));
        add(new TitleScreenButtons("How to", Window.WINDOW_WIDTH / 2, Window.WINDOW_HEIGHT / 2 + 15));
    }
}
