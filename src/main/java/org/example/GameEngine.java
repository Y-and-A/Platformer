package org.example;

import java.awt.*;
import java.util.ArrayList;

import static org.example.Window.levelSelectorName;

//TODO
// IMPLEMENT A 3D MAP (GameObject[][][]), WHERE EACH LAYER HOLDS ON TYPE OF GAME_OBJECT
// WHEN CHECKING COLLISIONS, GO OVER FIRST LAYER AND COMPARE IT TO THE OTHER ONES AT THE SAME I,J INDEXES
// ACHIEVING A FULL UPDATE AT ONLY ONE RUN-THROUGH OF THE FIRST LAYER

public class GameEngine {
    private Player player;
    private final ArrayList<Tile> tiles = new ArrayList<>();
    public ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();

    public GameEngine(short[][] map) {
        initialize(map);
    }

    public void initialize(short[][] map) {
        Image image;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                boolean floating = Tile.isFloatingTile(map[r][c]);
                image = Assets.getTileImage(map[r][c]);
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

        for (Bullet bullet : bullets) {
            bullet.update();

            if (!bullet.alive) continue;

            if (bullet.x < 0 || bullet.x > 1300) { //TODO change 1300 to actual window width, consider window resizes
                bullet.alive = false;
                break;
            }

            Rectangle bulletRect = bullet.rectangle();

            for (Tile tile : tiles) {
                if (tile.rect.intersects(bulletRect)) {
                    bullet.alive = false;
                    break;
                }
            }

            if (!bullet.alive) continue;

            for (Enemy enemy : enemies) {
                if (!enemy.alive) continue;

                if (enemy.rectangle().intersects(bulletRect)) {
                    enemy.lives--;
                    if (enemy.lives <= 0) enemy.alive = false;
                    bullet.alive = false;
                    break;
                }
            }
        }

        bullets.removeIf(bullet -> !bullet.alive);
        enemies.removeIf(enemy -> !enemy.alive);

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
        drawBullets(g);
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

    private void drawBullets(Graphics g) {
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    public int getPlayerLives() {
        return player.lives;
    }

    public void shotBullet() {
        bullets.add(new Bullet(player));
    }
}
