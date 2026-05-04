package org.example;

import java.awt.*;
import java.util.Arrays;

public class Enemy extends Entity {
    protected Enemy(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void findPath(short[][] map, int targetX, int targetY) {
        pathFinderHelper(convertMap(map), (int) this.x, (int) this.y, targetX, targetY, new boolean[map.length][map[0].length]);
    }
    public boolean pathFinderHelper(boolean[][] map, int cX, int cY, int tX, int tY, boolean[][] visited) {
        if (cX == tX && cY == tY) return true;


        return true;
    }

    public boolean[][] convertMap(short[][] map) {
        boolean[][] result = new boolean[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                result[i][j] = map[i][j] != 0;
            }
        }

        return result;
    }

    @Override
    public void draw(Graphics g) {

    }
}
