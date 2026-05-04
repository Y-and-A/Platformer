package org.example;

import javax.swing.*;
import java.awt.*;

public class Window {
    public final JFrame window = new JFrame("Platformer");

    public static final int WIDTH = 1300;
    public static final int HEIGHT = 800;

    public final CardLayout cardLayout = new CardLayout();
    public final JPanel mainPanel = new JPanel();

    private GamePanel gamePanel = null;

    private final String titleScreenName = "Title screen";
    private final String levelSelectorName = "SELECTOR";
    private final String levelName = "LEVEL";

    public Window() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setLocationRelativeTo(null);

        mainPanel.setLayout(cardLayout);
        mainPanel.add(createTitleScreen(), titleScreenName);
        mainPanel.add(createLevelSelectorScreen(), levelSelectorName);

        window.add(mainPanel);
        window.setVisible(true);
    }

    public void changeScene(String scene) {
        if (scene.contains(levelName)) {
            int level = Integer.parseInt(scene.substring(levelName.length()));

            if (gamePanel == null) {
                gamePanel = new GamePanel(level);
                mainPanel.add(gamePanel, levelName); // add gamePanel to mainPanel for the first time
            } else {
                gamePanel = new GamePanel(level);
            }
            gamePanel.startGame();
            cardLayout.show(mainPanel, levelName);
            gamePanel.requestFocusInWindow();
        }

        else {
            cardLayout.show(mainPanel, scene);
            mainPanel.requestFocusInWindow();

            if (gamePanel != null) gamePanel.stopGame();
        }
    }

    public JPanel createTitleScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(null);
        JButton selectLevelButton = new JButton("Select Level");
        selectLevelButton.setBounds(window.getWidth()/2-50,window.getHeight()/2,100,30);
        selectLevelButton.setFocusable(false);
        selectLevelButton.addActionListener(e -> changeScene(levelSelectorName));
        panel.add(selectLevelButton);

        return panel;
    }

    public JPanel createLevelSelectorScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 10, 10));
        panel.setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            JButton levelButton = new LevelButton("Level " + i);
            int finalI = i;
            levelButton.addActionListener(e -> changeScene(levelName + finalI));
            panel.add(levelButton);
        }

        return panel;
    }
}
