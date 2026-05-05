package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Enemy extends Entity {
    protected Enemy(int x, int y, int width, int height) {
        super(x, y, width, height);
        try {
            image = ImageIO.read(new File("src/main/resources/enemy/front.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        g.drawImage(image, (int) x, (int) y,width,height,null);
    }
}
