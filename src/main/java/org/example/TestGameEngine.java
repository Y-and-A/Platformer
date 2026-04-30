package org.example;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TestGameEngine {
    private TestPlayer player;
    private int[][] map;
    private int TILE_WIDTH;
    private int TILE_HEIGHT;


    public TestGameEngine(TestPlayer player, int[][] map) {
        this.player = player;
        this.map = map;

        TILE_WIDTH = TestWindow.WIDTH / map[0].length;
        TILE_HEIGHT = TestWindow.HEIGHT / map.length;
    }

    public void update(boolean[] keys) {
        this.player.update(keys);
        handleCollisions(player);
    }

    public void handleCollisions(TestEntity entity) {
        entity.x += entity.velocityX;

        if (isColliding(entity.x, entity.y, entity.width, entity.height)) {
            if (entity.velocityX > 0) {
                entity.x = ((int) (entity.x + entity.width) / TILE_WIDTH) * TILE_WIDTH - entity.width - 0.01;
            } else if (entity.velocityX < 0) {
                entity.x = ((int) entity.x / TILE_WIDTH + 1) * TILE_WIDTH;
            }
            entity.velocityX = 0;
        }

        entity.y += entity.velocityY;
        entity.onFloor = false;

        if (isColliding(entity.x, entity.y, entity.width, entity.height)) {
            if (entity.velocityY > 0) {
                entity.y = ((int) (entity.y + entity.height) / TILE_HEIGHT) * TILE_HEIGHT - entity.height - 0.01;
                entity.onFloor = true;
            } else if (entity.velocityY < 0) {
                entity.y = ((int) entity.y / TILE_HEIGHT + 1) * TILE_HEIGHT;
            }
            entity.velocityY = 0;
        }

        if (entity.x < 0) entity.x = 0;
        if (entity.x > TestWindow.WIDTH - entity.width) entity.x = TestWindow.WIDTH - entity.width;
    }

    public boolean isColliding(double x, double y, int width, int height) {
        int leftCol = (int) (x / TILE_WIDTH);
        int rightCol = (int) ((x + width - 1) / TILE_WIDTH);
        int topRow = (int) (y / TILE_HEIGHT);
        int bottomRow = (int) ((y + height - 1) / TILE_HEIGHT);

        if (leftCol < 0 || rightCol >= map[0].length || topRow < 0 || bottomRow > map.length) return false;

        for (int r = topRow; r <= bottomRow; r++) {
            for (int c = leftCol; c <= rightCol; c++) {
                if (map[r][c] == 1) return true;
            }
        }

        return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 1) {
                    g2d.fillRect(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                }
            }
        }

        this.player.draw(g);
    }
}
