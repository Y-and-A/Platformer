package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.example.UiScaling.scale;

public class GameEngine {
    private Player player;
    private short[][] map;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private final int TILE_WIDTH=(int) (50 * scale);
    private final int TILE_HEIGHT=(int) (50 * scale);

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
    private Image floatingSingle;
    private Image only1;
    private Image only2;
    private Image only3;
    private Image only4;
    private Image special1;
    private Image special2;
    private Image fullGrassUp;
    private Image fullGrassLeft;


    public GameEngine(Player player, short[][] map) {
        this.player = player;
        this.map = map;
        loadImages();
        Image image = special1;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                int multiplier = (int) (50 *scale);
                boolean floating = (map[r][c]>20&&map[r][c]<25);//floating tiles id 21,22,23,24
                image = switch (map[r][c]) {
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
                    case 24 -> floatingSingle;
                    case 31 -> only1;
                    case 32 -> only2;
                    case 33 -> only3;
                    case 34 -> only4;
                    case 40 -> fullGrassUp;
                    case 41 -> fullGrassLeft;
                    case 61 -> special1;
                    case 62 -> special2;
                    default -> null;
                };
                if (image!=null){
                    tiles.add(new Tile(c*multiplier,r*multiplier,floating,image));
                }
            }
        }
    }

    public void update(boolean[] keys, boolean[] prevKeys) {
        this.player.update(keys, prevKeys);
        handleCollisions(player);
    }

    public void handleCollisions(Entity entity) {
        entity.onWall = false;
        entity.onFloor = false;

        entity.x += entity.velocityX;

        if (isColliding(entity)) {
            if (entity.velocityX > 0) {
                entity.x = ((int) (entity.x + entity.width) / TILE_WIDTH) * TILE_WIDTH - entity.width - 0.01;
                entity.onWall = true;
            } else if (entity.velocityX < 0) {
                entity.x = ((int) entity.x / TILE_WIDTH + 1) * TILE_WIDTH;
                entity.onWall = true;
            }
            entity.velocityX = 0;
        }

        if (entity.x < 0) {
            entity.x = 0;
            entity.onWall = true;
        }
        if (entity.x + entity.width> Window.WIDTH) {
            entity.x = Window.WIDTH - entity.width;
            entity.onWall = true;
        }

        entity.y += entity.velocityY;
        if (isColliding(entity)) {
            if (entity.velocityY > 0) {
                entity.y = ((int) (entity.y + entity.height) / TILE_HEIGHT) * TILE_HEIGHT - entity.height - 0.01;
                entity.onFloor = true;
            } else if (entity.velocityY < 0) {
                entity.y = ((int) entity.y / TILE_HEIGHT + 1) * TILE_HEIGHT;
            }
            entity.velocityY = 0;
        }
    }

    public boolean isColliding(Entity entity) {
        int leftColumn =  Math.max(0, (int) (entity.x / TILE_WIDTH));//math max/min to insure bounds
        int rightColumn = Math.min(map[0].length - 1, (int) ((entity.x + entity.width - 0.01) / TILE_WIDTH));
        int topRow = Math.max(0, (int) (entity.y / TILE_HEIGHT));
        int bottomRow = Math.min(map.length - 1, (int) ((entity.y + entity.height - 0.01) / TILE_HEIGHT));

        for (int r = topRow; r <= bottomRow; r++) {
            for (int c = leftColumn; c <= rightColumn; c++) {
               if (map[r][c]!=0)return true;
            }
        }

        return false;
    }

    public void draw(Graphics g) {
        drawTiles(g);
        this.player.draw(g);
    }
    private void drawTiles(Graphics g){
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).draw(g);
        }
    }
    private void loadImages(){
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
            floatingSingle = ImageIO.read(new File("src/main/resources/tiles/floatingSingle.png"));
            only1 = ImageIO.read(new File("src/main/resources/tiles/only1.png"));
            only2 = ImageIO.read(new File("src/main/resources/tiles/only2.png"));
            only3 = ImageIO.read(new File("src/main/resources/tiles/only3.png"));
            only4 = ImageIO.read(new File("src/main/resources/tiles/only4.png"));
            special1 = ImageIO.read(new File("src/main/resources/tiles/special1.png"));
            special2 = ImageIO.read(new File("src/main/resources/tiles/special2.png"));
            fullGrassUp = ImageIO.read(new File("src/main/resources/tiles/fullGrassUp.png"));
            fullGrassLeft = ImageIO.read(new File("src/main/resources/tiles/fullGrassLeft.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
