package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
    private static TitleScreen titleScreenPanel;
    private static final SoundManager soundManager = new SoundManager();

    public Window() {
        JFrame window = new JFrame("Platformer");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIDTH, HEIGHT);
        window.setLocationRelativeTo(null);

        mainPanel.setLayout(cardLayout);
        titleScreenPanel = new TitleScreen(
                () -> changeScene(SCENE_LEVEL_SELECTOR),
                () -> changeScene(SCENE_HOW_TO)
        );

        mainPanel.add(titleScreenPanel, SCENE_TITLE);
        mainPanel.add(createLevelSelectorScreen(), SCENE_LEVEL_SELECTOR);
        mainPanel.add(createHowToScreen(), SCENE_HOW_TO);


        window.add(mainPanel);

        titleScreenPanel.resetAnimation();
        soundManager.play(Sound.INTRO);

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
                soundManager.play(Sound.SONG);

                cardLayout.show(mainPanel, scene);
                gamePanel.requestFocusInWindow();
            } catch (NumberFormatException e) {
                System.err.println("Critical error: Tried to load an invalid level number from string: " + scene);
            }
        } else {
            soundManager.play(Sound.INTRO);
            cardLayout.show(mainPanel, scene);
            mainPanel.requestFocusInWindow();

            if (scene.equals(SCENE_TITLE)) {
                titleScreenPanel.resetAnimation();
            }

            if (gamePanel != null) {
                gamePanel.stopGame();
                mainPanel.remove(gamePanel);
                gamePanel = null;
            }
        }
    }

    private JPanel createLevelSelectorScreen() {
        JPanel wrapperPanel = new JPanel() {
            @Override
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };

        wrapperPanel.setLayout(new OverlayLayout(wrapperPanel));

        JPanel buttonLayer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonLayer.setOpaque(false);

        WesternButton backButton = new WesternButton("Back");
        backButton.setPreferredSize(new Dimension(140, 50));
        backButton.addActionListener(e -> changeScene(SCENE_TITLE));
        buttonLayer.add(backButton);

        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        gridPanel.setBackground(new Color(210, 180, 140));

        gridPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));

        for (int i = 0; i < 9; i++) {
            JButton levelButton = new LevelButton(i);
            final int finalI = i;
            levelButton.addActionListener(e -> changeScene(LEVEL_PREFIX + finalI));
            gridPanel.add(levelButton);
        }

        wrapperPanel.add(buttonLayer);
        wrapperPanel.add(gridPanel);

        return wrapperPanel;
    }

    private JPanel createHowToScreen() {
        JPanel panel = new JPanel(new BorderLayout()) {
            private final BufferedImage image = (BufferedImage) Assets.screenHowTo;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                g2d.setColor(new Color(139, 204, 232));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                double scaleX = (double) getWidth() / image.getWidth();
                double scaleY = (double) getHeight() / image.getHeight();
                double scale = Math.min(scaleX, scaleY);

                int scaledWidth = (int) (image.getWidth() * scale);
                int scaledHeight = (int) (image.getHeight() * scale);
                int offsetX = (getWidth() - scaledWidth) / 2;
                int offsetY = (getHeight() - scaledHeight) / 2;

                g2d.drawImage(image, offsetX, offsetY, scaledWidth, scaledHeight, this);
            }
        };

        JPanel topContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        topContainer.setOpaque(false); // Let the background paint correctly behind it

        WesternButton backButton = new WesternButton("Back");
        backButton.setPreferredSize(new Dimension(140, 50));
        backButton.addActionListener(e -> changeScene(SCENE_TITLE));

        topContainer.add(backButton);
        panel.add(topContainer, BorderLayout.NORTH);

        return panel;
    }
}
