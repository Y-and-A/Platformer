package org.example;

import java.awt.*;

public class TestGameEngine {
    private TestPlayer player;
    private int[][] map;
    private int TILE_WIDTH;
    private int TILE_HEIGHT;


    public TestGameEngine(TestPlayer player, int[][] map) {
        this.player = new TestPlayer();
        this.map = map;
    }

    public void update(boolean[] keys) {
        this.player.update(keys);
        if (onFloor(player)) {
            player.onFloor = true;
        }
        else player.onFloor = false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        TILE_WIDTH = TestWindow.WIDTH / map[0].length;
        TILE_HEIGHT = TestWindow.HEIGHT / map.length;
        g.setColor(Color.green);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 1) {
                    g.fillRect(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                }
            }
        }

        g.setColor(Color.red);
        this.player.draw(g);
    }

    public boolean onFloor(TestEntity entity) {
        Rectangle entityRect = entity.getRect();
        Rectangle tileRect;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 1) {
                    tileRect = new Rectangle(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                    if (entityRect.intersects(tileRect)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    public boolean collidesWithTile(TestEntity entity) {
        Rectangle entityRect = entity.getRect();
        Rectangle tileRect;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 1) {
                    tileRect = new Rectangle(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                    if (entityRect.intersects(tileRect)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
