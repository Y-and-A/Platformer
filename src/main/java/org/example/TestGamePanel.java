package org.example;

import org.example.levels.Level00;

import javax.swing.*;
import java.awt.*;

public class TestGamePanel extends JPanel {
    private final TestGameEngine gameEngine;
    private final Timer gameLoop;
    private TestPlayer player;
    private int[][] map;

    private final boolean[] keys = new boolean[256];

    public TestGamePanel() {
        player = new TestPlayer();
        map = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,}


        };
        gameEngine = new TestGameEngine(player, map);
        setFocusable(true);

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
//                System.out.println("Key pressed");
                if (e.getKeyCode() < 256) keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
//                System.out.println("Key released");
                if (e.getKeyCode() < 256) keys[e.getKeyCode()] = false;
            }
        });

        gameLoop = new Timer(16, e -> {
            gameEngine.update(keys);
            repaint();
        });
    }

    public void startGame() {
        gameLoop.start();
    }

    public void stopGame() {
        gameLoop.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        gameEngine.draw(g);
    }
}
