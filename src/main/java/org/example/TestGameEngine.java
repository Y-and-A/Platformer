package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestGameEngine {
    private TestPlayer player;

    private final int TILE_SIZE = 50;
    private int[][] map = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };

    public TestGameEngine() {
        player = new TestPlayer();
        player.x = 100;
        player.y = 100;

        player.velocityY = 1;
    }

    public void update(boolean[] keys) {
        player.update(keys);
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 1) {
                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        g.setColor(Color.red);
        player.draw(g);
    }
}
