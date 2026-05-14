package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    private final GameEngine gameEngine;
    private Thread gameThread;
    private JPanel buttonContainer;
    private final int currentLevel;

    short[][] map;

    private final boolean[] keys = new boolean[256];
    private final boolean[] prevKeys = new boolean[256];

    private final BufferedImage backBuffer;

    public GamePanel(int levelNum) {
        currentLevel = levelNum;
        map = Level.getLevel(currentLevel);
        gameEngine = new GameEngine(map);

        setLayout(new BorderLayout());
        setFocusable(true);

        backBuffer = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_RGB);
        setBackground(Color.BLACK);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() < 256) keys[e.getKeyCode()] = true;
                if (keys[KeyEvent.VK_P]) {
                    if (gameEngine.paused) {
                        resumeGame();
                    } else {
                        pauseGame();
                    }
                }
            }


            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() < 256) keys[e.getKeyCode()] = false;
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                gameEngine.shotBullet();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame() {
        gameThread = null;
    }

    @Override
    public void run() {
        // High-resolution timer for extreme accuracy
        long lastTime = System.nanoTime();

        // Target 60 Updates (Ticks) Per Second
        final double TICKS_PER_SECOND = 60.0;
        final double TIME_PER_TICK_NS = 1000000000.0 / TICKS_PER_SECOND;

        double deltaAccumulator = 0;

        boolean shouldRender;
        while (gameThread != null) {
            long now = System.nanoTime();
            // Calculate how much real time passed since the last loop iteration
            // and add it to the accumulator as a fraction of a "tick".
            deltaAccumulator += (now - lastTime) / TIME_PER_TICK_NS;
            lastTime = now;

            shouldRender = false;

            // If a full tick (or multiple) has accumulated, update the game logic!
            while (deltaAccumulator >= 1) {
                if (gameThread == null) break;

                gameEngine.update(keys, prevKeys);
                System.arraycopy(keys, 0, prevKeys, 0, keys.length);
                deltaAccumulator--; // Consume one logical tick
                shouldRender = true; // render only if the game actualy updated
            }

            if (shouldRender) repaint();
            else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void pauseGame() {
        gameEngine.paused = true;

        buttonContainer = new JPanel(new GridBagLayout());
        buttonContainer.setOpaque(false);

        WesternButton resume = new WesternButton("resume");
        resume.addActionListener(e -> resumeGame());
        resume.setPreferredSize(new Dimension(300, 50));

        WesternButton restart = new WesternButton("restart");
        restart.addActionListener(e -> Window.changeScene(Window.LEVEL_PREFIX + currentLevel));
        restart.setPreferredSize(new Dimension(300, 50));

        WesternButton returnToSelector = new WesternButton("return");
        returnToSelector.addActionListener(e -> Window.changeScene(Window.SCENE_LEVEL_SELECTOR));
        returnToSelector.setPreferredSize(new Dimension(300, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonContainer.add(resume, gbc);
        gbc.gridy = 1;
        buttonContainer.add(restart, gbc);
        gbc.gridy = 2;
        buttonContainer.add(returnToSelector, gbc);

        add(buttonContainer, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void resumeGame() {
        gameEngine.paused =false;
        this.remove(buttonContainer);
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D bG = backBuffer.createGraphics();

        bG.setColor(Color.decode("#87CEEB")); //blue
        bG.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

        gameEngine.draw(bG);//draws the image on the backBuffer

        bG.setColor(Color.red);
        bG.setFont(new Font("David", Font.BOLD, 30));
        bG.drawString(gameEngine.getPlayerLives() + "", Window.WIDTH / 2, 30);
        bG.dispose();

        Graphics2D g2d = (Graphics2D) g;
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        double scaleX = (double) windowWidth / Window.WIDTH;
        double scaleY = (double) windowHeight / Window.HEIGHT;
        double scale = Math.min(scaleX, scaleY);

        int scaledWidth = (int) (Window.WIDTH * scale);
        int scaledHeight = (int) (Window.HEIGHT * scale);
        int offsetX = (windowWidth - scaledWidth) / 2;
        int offsetY = (windowHeight - scaledHeight) / 2;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(backBuffer, offsetX, offsetY, scaledWidth, scaledHeight, null);
    }
}