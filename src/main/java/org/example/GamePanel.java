package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    private final GameEngine gameEngine;
    private Thread gameThread;

    private Player player;
    private short[][] map;

    private final boolean[] keys = new boolean[256];
    private final boolean[] prevKeys = new boolean[256];

    private BufferedImage backBuffer;

    public GamePanel(int levelNum) {
        map = Level.getLevel(levelNum);
        int playerX = Level.getPlayerPos(levelNum)[0];
        int playerY = Level.getPlayerPos(levelNum)[1];
        player = new Player(playerX, playerY);

        gameEngine = new GameEngine(player, map);
        setFocusable(true);

        backBuffer = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_RGB);
        setBackground(Color.BLACK);

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() < 256) keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() < 256) keys[e.getKeyCode()] = false;
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
        while (gameThread != null) {
            gameEngine.update(keys, prevKeys);
            System.arraycopy(keys, 0, prevKeys, 0, keys.length);
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D bG = backBuffer.createGraphics();

        bG.setColor(Color.decode("#87CEEB")); //blue
        bG.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

        gameEngine.draw(bG);//draws the image on the backBuffer
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