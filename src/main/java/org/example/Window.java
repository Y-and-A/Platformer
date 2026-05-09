package org.example;

import javax.swing.*;
import java.awt.*;

public class Window {
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 800;

    private static final String SCENE_TITLE = "TITLE_SCREEN";
    private static final String SCENE_HOW_TO = "HOW_TO";
    protected static final String SCENE_LEVEL_SELECTOR = "SELECT_LEVEL";
    private static final String LEVEL_PREFIX = "LEVEL_";

    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel mainPanel = new JPanel();

    private static GamePanel gamePanel = null;


    public Window() {
        JFrame window = new JFrame("Platformer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setLocationRelativeTo(null);

        mainPanel.setLayout(cardLayout);
        mainPanel.add(createTitleScreen(), SCENE_TITLE);
        mainPanel.add(createLevelSelectorScreen(), SCENE_LEVEL_SELECTOR);
        mainPanel.add(createHowToScreen(), SCENE_HOW_TO);

        window.add(mainPanel);
        window.setVisible(true);
    }

    public static void changeScene(String scene) {
        if (scene.startsWith(LEVEL_PREFIX)) {
            try {
                int level = Integer.parseInt(scene.substring(LEVEL_PREFIX.length()));

                if (gamePanel != null) {
                    gamePanel.stopGame();
                    mainPanel.remove(gamePanel);
                }

                gamePanel = new GamePanel(level);
                mainPanel.add(gamePanel, scene);

                gamePanel.startGame();
                cardLayout.show(mainPanel, scene);
                gamePanel.requestFocusInWindow();
            } catch (NumberFormatException e) {
                System.err.println("Critical error: Tried to load an invalid level number from string: " + scene);
            }
        }
        else {
            cardLayout.show(mainPanel, scene);
            mainPanel.requestFocusInWindow();

            if (gamePanel != null) {
                gamePanel.stopGame();
                mainPanel.remove(gamePanel);
                gamePanel = null;
            }
        }
    }

    private JPanel createTitleScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.GRAY);

        JButton selectLevelButton = new JButton("Select Level");
        selectLevelButton.setFocusable(false);
        selectLevelButton.setPreferredSize(new Dimension(120, 30));
        selectLevelButton.addActionListener(e -> changeScene(SCENE_LEVEL_SELECTOR));
        panel.add(selectLevelButton);

        JButton howToButton = new JButton("How To");
        howToButton.setFocusable(false);
        howToButton.setPreferredSize(new Dimension(120, 30));
        howToButton.addActionListener(e -> changeScene(SCENE_HOW_TO));
        panel.add(howToButton);

        return panel;
    }

    private JPanel createLevelSelectorScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 10, 10));
        panel.setBackground(Color.DARK_GRAY);

        for (int i = 0; i < 9; i++) {
            JButton levelButton = new LevelButton(i);

            final int finalI = i;
            levelButton.addActionListener(e -> changeScene(LEVEL_PREFIX + finalI));
            panel.add(levelButton);
        }

        return panel;
    }

    private JPanel createHowToScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN);

        panel.add(new JLabel("how to text"));

        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.addActionListener(e -> changeScene(SCENE_TITLE));
        panel.add(backButton);

        return panel;
    }
}
