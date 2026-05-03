package org.example;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final GameEngine gameEngine;
    private Thread gameThread;

    private Player player;
    private int[][] map;

    private final boolean[] keys = new boolean[256];
    private final boolean[] prevKeys = new boolean[256];

    public int[][] maps(int level){
        /*
        guide
         0 =air
        11-13 top layer
        14-16 middle layer
        17-19 bottom layer

        21-13 floating blocks
        31 -34 one part grass rest dirt
         */
        int[][] level1 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 21, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 21, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 21, 22, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 23, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 13, 0},
                {0, 0, 0, 0, 0, 0, 11, 12, 13, 0, 0, 0, 0, 0, 0, 0, 14, 16, 0},
                {0, 0, 0, 0, 0, 14, 15, 15, 16, 0, 0, 0, 0, 0, 0, 0, 14, 16, 0},
                {0, 0, 0, 0, 0, 0, 17, 18, 19, 0, 0, 0, 0, 0, 0, 0, 14, 16, 0},
                {0, 0, 0, 21, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 16, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 17, 19, 0},
                {11, 13, 0, 0, 0, 0, 11, 12, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {14, 32, 12, 12, 12, 12, 31, 15, 32, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16},
                {14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16},
                {14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16},
                {14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16}
        };

        int[][] level2 = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 24, 0, 0, 0, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 24, 0, 0, 0, 0, 0, 0, 11, 13, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 16, 0, 0, 0, 0, 0, 0, 40, 0},
                {24, 0, 0, 0, 0, 0, 0, 0, 0, 14, 32, 12, 12, 12, 12, 12, 12, 61, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 15, 15, 15, 15, 15, 15, 15, 16, 0},
                {0, 0, 24, 0, 0, 0, 0, 0, 0, 17, 18, 18, 18, 18, 18, 18, 18, 19, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 11, 12, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {12, 12, 12, 31, 15, 32, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
        };
        int[][] level3 = new int[][]{

                {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
                {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
                {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
                {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
                {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
                {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
                {0,	0,	0,	0,	0,	0,	0,	21,	22,	23,	0,	11,	12,	12,	12,	12,	12,	13,	0},
                {0,	0,	0,	0,	0,	0,	24,	0,	0,	0,	0,	17,	18,	18,	18,	18,	18,	19,	0},
                {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
                {0,	0,	0,	0,	0,	24,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
                {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	11,	12,	12,	12,	12,	12,	13},
                {0,	0,	0,	0,	24,	0,	0,	0,	0,	0,	0,	0,	17,	18,	18,	18,	18,	18,	19},
                {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
                {21,22,	22,	23,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0}


        };

        if (level == 1)
            return level1;
        else if (level == 2) {
            return level2;
        }
            else if (level == 3) {
                return level3;

            }
            return level1;
        }

    public GamePanel() {
            player = new Player();

            player.x = 0;
            player.y = 0;

            map = maps(2);
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

        public void startGame () {
            gameThread = new Thread(this);
            gameThread.start();
        }

        public void stopGame () {
            gameThread = null;
        }

        @Override
        public void run () {

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
        protected void paintComponent (Graphics g){
            super.paintComponent(g);

            gameEngine.draw(g);
        }
    }
