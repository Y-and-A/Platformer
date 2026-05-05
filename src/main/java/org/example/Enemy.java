package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Enemy extends Entity {
    private enum Directions {LEFT, RIGHT}
    private Directions direction;

    protected Enemy(int x, int y, int width, int height) {
        super(x, y, width, height);
        try {
            image = ImageIO.read(new File("src/main/resources/enemy/front.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void findPath(short[][] map, int targetX, int targetY) {
        pathFinderHelper(convertMap(map), (int) this.x, (int) this.y, targetX, targetY);
    }

    public boolean pathFinderHelper(boolean[][] map, int cX, int cY, int tX, int tY) {
        if (cX == tX && cY == tY) return true; // check win

        // try to go left
        if (map[0].length > cX-1 && map[cY][cX-1]) {
            for (int i = cX-1; i < map.length; i++) {
                // find the lowest ground in the left column and consider the tile above as the next tile
                if (!map[i][cX-1]) {
                    // send the next tile to find the solution and return its answer if it's positive
                    boolean hasSolution = pathFinderHelper(map, cX-1, i+1, tX, tY);
                    this.direction = Directions.LEFT;
                    if (hasSolution) return true;
                }
            }
        }

        // try to go right
        if (map.length > cX+1 && map[cY][cX+1]) {
            for (int i = cX+1; i < map.length; i++) {
                if (!map[i][cX+1]) {
                    boolean hasSolution = pathFinderHelper(map, cX+1, i+1, tX, tY);
                    this.direction = Directions.RIGHT;
                    if (hasSolution) return true;
                }
            }
        }

        return false;
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
        g.drawImage(image, (int) x, (int) y,width,height,null);
    }
}
