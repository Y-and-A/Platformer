package org.example;

import javax.swing.*;
import java.awt.*;

public class Window {
    public final JFrame window = new JFrame("Platformer");
    public static int WIDTH;
    public static int HEIGHT;

    public final CardLayout cardLayout = new CardLayout();
    public final JPanel mainPanel = new JPanel();

    private final GamePanel gamePanel;

    private final String titleScreenName = "Title screen";
    private final String levelSelectorName = "LEVEL SELECTOR";
    private final String levelName = "LEVEL";
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Window() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(screenSize);
        WIDTH = window.getWidth();
        HEIGHT = window.getHeight();
        window.setResizable(false);
        System.out.println(WIDTH + "," + HEIGHT);

        gamePanel = new GamePanel();

        mainPanel.setLayout(cardLayout);

        mainPanel.add(createTitleScreen(), titleScreenName);
        mainPanel.add(createLevelSelectorScreen(), levelSelectorName);
        mainPanel.add(gamePanel, levelName);

        window.add(mainPanel);
        window.setVisible(true);
    }

    public void changeScene(String scene) {
        cardLayout.show(mainPanel, scene);
        mainPanel.requestFocusInWindow();

        if (scene.equals(levelName)) {
            gamePanel.startGame();
            gamePanel.requestFocusInWindow();
        } else gamePanel.stopGame();
    }

    public JPanel createTitleScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(null);
        JButton selectLevelButton = new JButton("Select Level");
        selectLevelButton.setBounds(screenSize.width/2,screenSize.height/2,100,30);
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
            JButton levelButton = new LevelButton("Level " + (i + 1));
            levelButton.addActionListener(e -> changeScene(levelName));
            panel.add(levelButton);
        }

        return panel;
    }
}
