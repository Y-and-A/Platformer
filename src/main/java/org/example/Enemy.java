package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Enemy extends Entity {
    Image left, right, front, back;

    private enum Direction {LEFT, RIGHT, UP, DOWN}
    private Direction nextMove;

    protected Enemy(int x, int y) {
        super(x, y);

        width = 50;
        height = 50;

        loadImages();
        image = front;
    }

    public void chasePlayer(short[][] map, int playerRow, int playerCol) {
        boolean[][] bMap = convertMap(map);
        boolean[][] visited = new boolean[map.length][map[0].length];
        boolean hasSolution = findPath(bMap, (int) x / 50, (int) y / 50, playerRow, playerCol, visited);
        if (hasSolution) {
//            System.out.println("Solution found");
            //TODO call this.update here, which should use nextMove to perform action (move left, right, or jump)
        }
    }

    public void update() {
        System.out.println("Enemy update called");
        super.update();
    }

    private boolean findPath(boolean[][] map, int cR, int cC, int tR, int tC, boolean[][] visited) {
        if (cR == tR && cC == tC) {
            visited[cR][cC] = true; // has no effect
            return true;
        }
        if (cR < 0 || cR >= map.length || cC < 0 || cC >= map[0].length) return false;

        visited[cR][cC] = true;

        // LEFT
        if (cC - 1 >= 0 && map[cR][cC - 1] && !visited[cR][cC + 1]) {
            boolean hasSolution = findPath(map, cR, cC - 1, tR, tC, visited);
            if (hasSolution) {
                nextMove = Direction.LEFT;
                return true;
            }
        }

        // RIGHT
        if (cC + 1 < map[0].length && map[cR][cC + 1] && !visited[cR][cC + 1]) {
            boolean hasSolution = findPath(map, cR, cC + 1, tR, tC, visited);
            if (hasSolution) {
                nextMove = Direction.RIGHT;
                return true;
            }
        }

        // UP
        if (cR - 1 >= 0 && map[cR - 1][cC] && !visited[cR - 1][cC]) {
            boolean hasSolution = findPath(map, cR - 1, cC, tR, tC, visited);
            if (hasSolution) {
                nextMove = Direction.UP;
                return true;
            }
        }

        // DOWN
        if (cR + 1 < map.length && map[cR + 1][cC] && !visited[cR + 1][cC]) {
            boolean hasSolution = findPath(map, cR + 1, cC, tR, tC, visited);
            if (hasSolution) {
                nextMove = Direction.DOWN;
                return true;
            }
        }

        visited[cR][cC] = false;
        return false;
    }

    //TODO this method should be provided by other class
    // something like boolean canWalkThrough(Entity entity, int tileNum)
    private boolean canWalkThrough(int tileType) {
        return tileType == 0 || tileType > 90;
    }

    public boolean[][] convertMap(short[][] map) {
        boolean[][] result = new boolean[map.length][map[0].length];

        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                result[r][c] = canWalkThrough(map[r][c]);
            }
        }

        return result;
    }

    private void loadImages() {
        try {
            front = ImageIO.read(new File("src/main/resources/enemy/front.png"));
            back = ImageIO.read(new File("src/main/resources/enemy/back.png"));
            left = ImageIO.read(new File("src/main/resources/enemy/left.png"));
            right = ImageIO.read(new File("src/main/resources/enemy/right.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Rectangle rectangle() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }
}
