package org.example;

import javax.swing.*;
import java.awt.*;

public class TitleScreen extends JPanel {
    private JButton startGameButton;
    private JButton howToButton;

    private Runnable onStartGameClicked;
    private Runnable onHowToClicked;

    public TitleScreen() {
        setLayout(null);
        setPreferredSize(new Dimension(Window.WIDTH,Window.HEIGHT));
        setBackground(Color.BLUE);

        startGameButton = new TitleScreenButton("Start Game", Window.WIDTH/2, Window.HEIGHT/2 - 15);
        startGameButton.addActionListener(e -> {
            if (onStartGameClicked != null) onStartGameClicked.run();
        });
        add(startGameButton);

        howToButton = new TitleScreenButton("How To", Window.WIDTH/2, Window.HEIGHT / 2 + 15);
        howToButton.addActionListener(e -> {
            if (onHowToClicked != null) onHowToClicked.run();
        });
        add(howToButton);
    }

    public void setOnStartGameClicked(Runnable onStartGameClicked) {
        this.onStartGameClicked = onStartGameClicked;
    }
    public void setOnHowToClicked(Runnable onHowToClicked) {
        this.onHowToClicked = onHowToClicked;
    }
}
