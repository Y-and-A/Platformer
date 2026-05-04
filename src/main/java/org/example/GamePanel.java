package org.example;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final GameEngine gameEngine;
    private Thread gameThread;

    private Player player;
    private short[][] map;

    private final boolean[] keys = new boolean[256];
    private final boolean[] prevKeys = new boolean[256];

    public GamePanel(int levelNum) {
        map = Level.getLevel(levelNum);
        int playerX =Level.getPlayerPos(levelNum)[0];
        int playerY =Level.getPlayerPos(levelNum)[1];
        player = new Player(playerX,playerY);

        gameEngine = new GameEngine(player, map);
        setFocusable(true);

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
        gameEngine.draw(g);
    }
}
