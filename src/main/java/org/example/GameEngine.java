package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.example.Window.levelSelectorName;

public class GameEngine {
    private Player player;
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private final ArrayList<Enemy> enemies = new ArrayList<>();

    private Image rightTop, middleTop, leftTop;
    private Image leftMiddle, middleMiddle, rightMiddle;
    private Image leftBottom, middleBottom, rightBottom;
    private Image floatingLeft, floatingMiddle, floatingRight, floatingSingle;
    private Image only1, only2, only3, only4;
    private Image special1, special2, fullGrassUp, fullGrassLeft;


    public GameEngine(short[][] map) {
        loadImages();
        Image image;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                boolean floating = Tile.isFloatingTile(map[r][c]);
                image = switch (map[r][c]) {
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
                if (image != null) {
                    tiles.add(new Tile(c * 50, r * 50, floating, image));
                }
                if (map[r][c] == 90) {
                    enemies.add(new Enemy(c * Tile.WIDTH, r * Tile.HEIGHT));
                }
                if (map[r][c] == 99)
                    this.player = new Player(c * Tile.WIDTH, r * Tile.HEIGHT);
            }
        }
    }

    public void update(boolean[] keys, boolean[] prevKeys) {
        player.update(keys, prevKeys);
        move(player);

        for (Enemy enemy : enemies) {
            enemy.chasePlayer(player);
            enemy.update();
            move(enemy);
        }

        if (!player.alive) Window.changeScene(levelSelectorName);
    }

    public void updateCollision(Entity entity) {
        Rectangle entityLeft = new Rectangle((int) entity.x, (int) entity.y, 1, entity.height - 14);
        Rectangle entityRight = new Rectangle(((int) entity.x + entity.width), (int) entity.y, 1, entity.height - 14);
        Rectangle entityUp = new Rectangle((int) entity.x, (int) entity.y, entity.width - 2, 1);
        Rectangle entityDown = new Rectangle((int) entity.x, (int) entity.y + entity.height, entity.width - 2, 1);

        Rectangle tileRect;
        Rectangle enemyRect;

        for (Tile tile : tiles) {
            tileRect = tile.rect;

            if (entityLeft.intersects(tileRect)) {
                entity.leftCollision = true;
                entity.onWall = true;
            }
            if (entityRight.intersects(tileRect)) {
                entity.rightCollision = true;
                entity.onWall = true;
            }
            if (entityUp.intersects(tileRect)) {
                if (tile.floating) {
                    entity.y = tileRect.y + tileRect.height;
                    entity.velocityY = 0;
                } else {
                    entity.topCollision = true;
                }
            }
            if (entityDown.intersects(tileRect))
                entity.onFloor = true;
        }

        entity.canBeHitIn -= 100;
        if (entity instanceof Player) {
            for (Enemy enemy : enemies) {
                if (entity.canBeHitIn <= 0) {
                    enemyRect = enemy.rectangle();

                    if (entityLeft.intersects(enemyRect)) {
                        entity.lives--;
                        entity.velocityX = 17;
                        entity.velocityY = -2;
                        entity.canBeHitIn = 1500;
                        System.out.println("left");
                        System.out.println(entity.lives);
                        continue;
                    }
                    if (entityRight.intersects(enemyRect)) {
                        entity.lives--;
                        entity.velocityX = -17;
                        entity.velocityY = -2;
                        entity.canBeHitIn = 1500;
                        System.out.println("right");
                        System.out.println(entity.lives);
                        continue;
                    }
                    if (entityUp.intersects(enemyRect)) {
                        entity.lives--;
                        entity.velocityY = -10;
                        entity.canBeHitIn = 1500;
                        System.out.println("top");
                        System.out.println(entity.lives);
                        continue;
                    }
                    if (entityDown.intersects(enemyRect)) {
                        entity.lives--;
                        entity.velocityY = -10;
                        entity.canBeHitIn = 1500;
                        System.out.println("bottom");
                        System.out.println(entity.lives);
                        // continue;
                    }


                }
            }
        }
        if (entity.lives <= 0) {
            entity.alive = false;
        }
    }

    public void move(Entity entity) {
        entity.onFloor = false;
        entity.topCollision = false;
        entity.leftCollision = false;
        entity.rightCollision = false;
        entity.onWall = false;

        entity.x += entity.velocityX;
        updateCollision(entity);

        if (entity.rightCollision) {
            entity.x = ((int) (entity.x + entity.width) / Tile.WIDTH) * Tile.WIDTH - entity.width - 0.01;
            entity.velocityX = 0;
            entity.onWall = true;
        } else if (entity.leftCollision) {
            entity.x = ((int) entity.x / Tile.WIDTH + 1) * Tile.WIDTH;
            entity.velocityX = 0;
            entity.onWall = true;
        }

        if (entity.x < 0) {
            entity.x = 0;
            entity.onWall = true;
        }
        if (entity.x + entity.width > Window.WIDTH) {
            entity.x = Window.WIDTH - entity.width;
            entity.onWall = true;
        }

        entity.y += entity.velocityY;
        updateCollision(entity);
        if (entity.onFloor) {
            entity.y = ((int) (entity.y + entity.height) / Tile.HEIGHT) * Tile.HEIGHT - entity.height - 0.01;
            entity.velocityY = 0;
        } else if (entity.topCollision) {
            entity.y = ((int) entity.y / Tile.HEIGHT + 1) * Tile.HEIGHT;
            entity.velocityY = 0;
        }
    }

    public void draw(Graphics g) {
        drawTiles(g);
        drawEnemies(g);
        this.player.draw(g);
    }

    private void drawTiles(Graphics g) {
        for (Tile tile : tiles) {
            tile.draw(g);
        }
    }

    private void drawEnemies(Graphics g) {
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
    }

    private void loadImages() {
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

    public int getPlayerLives() {
        return player.lives;
    }
}
