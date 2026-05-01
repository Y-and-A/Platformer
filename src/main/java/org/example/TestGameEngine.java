package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class TestGameEngine {
    private TestPlayer player;
    private int[][] map;
    private int TILE_WIDTH;
    private int TILE_HEIGHT;
    private Image rightTop;
    private Image middleTop;
    private Image leftTop;
    private Image leftMiddle;
    private Image middleMiddle;
    private Image rightMiddle;
    private Image leftBottom;
    private Image middleBottom;
    private Image rightBottom;
    private Image floatingLeft;
    private Image floatingMiddle;
    private Image floatingRight;
    private Image only1;
    private Image only2;
    private Image only3;
    private Image only4;


    public TestGameEngine(TestPlayer player, int[][] map) {
        this.player = player;
        this.map = map;
        startImages();


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
                if (map[r][c] != 0) return true;
            }
        }

        return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);

        Image tile = null;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                tile = switch (map[row][col]) {
                    case 0 -> null;
                    case 11 -> leftTop;
                    case 12 -> middleTop;
                    case 13 -> rightTop;
                    case 14 -> leftMiddle;
                    case 15 -> middleMiddle;
                    case 16 -> rightMiddle;
                    case 17 -> leftBottom;
                    case 18 -> middleBottom;
                    case 19 -> rightBottom;
                    case 21 -> floatingLeft;
                    case 22 -> floatingMiddle;
                    case 23 -> floatingRight;
                    case 31 -> only1;
                    case 32 -> only2;
                    case 33 -> only3;
                    case 34 -> only4;
                    default -> middleTop;
                };
                g2d.drawImage(tile,col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT,null);
            }
        }
        this.player.draw(g);
    }
    private void startImages(){
        try {
            rightTop = ImageIO.read(new File("src/main/resources/tiles/rightTop.png"));
            middleTop = ImageIO.read(new File("src/main/resources/tiles/middleTop.png"));
            leftTop = ImageIO.read(new File("src/main/resources/tiles/leftTop.png"));
            leftMiddle = ImageIO.read(new File("src/main/resources/tiles/leftMiddle.png"));
            middleMiddle = ImageIO.read(new File("src/main/resources/tiles/middleMiddle.png"));
            rightMiddle = ImageIO.read(new File("src/main/resources/tiles/rightMiddle.png"));
            leftBottom = ImageIO.read(new File("src/main/resources/tiles/leftBottom.png"));
            middleBottom = ImageIO.read(new File("src/main/resources/tiles/middleBottom.png"));
            rightBottom = ImageIO.read(new File("src/main/resources/tiles/rightBottom.png"));
            floatingLeft = ImageIO.read(new File("src/main/resources/tiles/floatingLeft.png"));
            floatingMiddle = ImageIO.read(new File("src/main/resources/tiles/floatingMiddle.png"));
            floatingRight = ImageIO.read(new File("src/main/resources/tiles/floatingRight.png"));
            only1 = ImageIO.read(new File("src/main/resources/tiles/only1.png"));
            only2 = ImageIO.read(new File("src/main/resources/tiles/only2.png"));
            only3 = ImageIO.read(new File("src/main/resources/tiles/only3.png"));
            only4 = ImageIO.read(new File("src/main/resources/tiles/only4.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
