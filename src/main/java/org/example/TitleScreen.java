package org.example;

import javax.swing.*;

public class TitleScreen extends JPanel {
    private JButton startGameButton;
    private JButton howToButton;

    private Runnable onStartGameClicked;

    public TitleScreen() {
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);
        setLayout(null);

        startGameButton = new TitleScreenButton("Start Game", Window.WIDTH / 2, Window.HEIGHT / 2 - 15);
        startGameButton.addActionListener(e -> {
            if (onStartGameClicked != null) onStartGameClicked.run();
        });
        add(startGameButton);
//        startGameButton.setAction();

        howToButton = new TitleScreenButton("How To", Window.WIDTH / 2, Window.HEIGHT / 2 + 15);
        howToButton.addActionListener(e -> {

        });
        add(howToButton);
    }

    public void setOnStartGameClicked(Runnable onStartGameClicked) {
        this.onStartGameClicked = onStartGameClicked;
    }
}
