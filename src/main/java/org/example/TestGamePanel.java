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

        //TEST
        player.x = 1300;
        player.y = 800;
        //TEST

        /*
        guide 0 =air
        11-13 top layer
        14-16 middle layer
        17-19 bottom layer

        21-13 floating blocks
        31 -34 on part grass rest dirt
         */
        map = new int[][]{
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
                {0 , 21, 22, 23, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 11, 12, 13, 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 14, 15, 16, 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 17, 18, 19, 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
                {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0},
                {11, 13, 0 , 0 , 0 , 0 , 11, 12, 13, 0 , 0 , 0 , 0 , 0},
                {14, 32, 12, 12, 12, 12, 31, 15, 32, 12, 12, 12, 12, 13},
                {14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16}


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
