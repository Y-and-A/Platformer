package org.example;

import java.awt.*;
import java.util.ArrayList;

import static org.example.Window.levelSelectorName;

public class GameEngine {
    private Player player;

    private final ArrayList<Tile> tiles = new ArrayList<>();
    public ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();

    private final short[][] map;

    public GameEngine(short[][] map) {
        this.map = map;

        Image image;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                boolean floating = Tile.isFloatingTile(map[r][c]);
                image = Assets.getTileImage(map[r][c]);
                if (image != null) tiles.add(new Tile(c * Tile.WIDTH, r * Tile.HEIGHT, floating, image));
                else if (map[r][c] == Tile.ENEMY_ID) enemies.add(new Enemy(c * Tile.WIDTH, r * Tile.HEIGHT));
                else if (map[r][c] == Tile.PLAYER_ID) this.player = new Player(c * Tile.WIDTH, r * Tile.HEIGHT);
            }
        }
    }

    public void update(boolean[] keys, boolean[] prevKeys) {
        player.update(keys, prevKeys);
        move(player);
        checkEnemyDamage(player);

        for (Enemy enemy : enemies) {
            if (enemy.alive) {
                enemy.chasePlayer(player);
                enemy.update();
                move(enemy);
            }
        }

        for (Bullet bullet : bullets) {
            bullet.update();
            if (!bullet.alive) continue;

            if (bullet.x < 0 || bullet.x > Window.WIDTH) {
                bullet.alive = false;
                continue;
            }

            int bCol = (int) (bullet.x / Tile.WIDTH);
            int bRow = (int) (bullet.y / Tile.HEIGHT);

            if (bRow >= 0 && bRow < map.length && bCol >= 0 && bCol < map[0].length) {
                short tileId = map[bRow][bCol];
                if (Tile.isSolid(tileId)) {
                    if (Tile.isFloatingTile(tileId)) {
                        if (bullet.y <= bRow * Tile.HEIGHT + Tile.FLOATING_HEIGHT) {
                            bullet.alive = false;
                            continue;
                        }
                    } else {
                        bullet.alive = false;
                        continue;
                    }
                }
            }

            Rectangle bulletRect = bullet.rectangle();
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

    public void move(Entity entity) {
        entity.x += entity.velocityX;
        checkXCollision(entity);

        if (entity.rightCollision) {
            entity.x = Math.floor((entity.x + entity.width) / Tile.WIDTH) * Tile.WIDTH - entity.width - 0.01;
            entity.velocityX = 0;
        } else if (entity.leftCollision) {
            entity.x = (Math.floor(entity.x / Tile.WIDTH) + 1) * Tile.WIDTH;
            entity.velocityX = 0;
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
        checkYCollision(entity);

        if (entity.onFloor) {
            entity.velocityY = 0;
        } else if (entity.topCollision) {
            entity.velocityY = 0;
        }
    }

    private void checkXCollision(Entity entity) {
        entity.leftCollision = false;
        entity.rightCollision = false;
        entity.onWall = false;

        double leftX = entity.x;
        double rightX = entity.x + entity.width - 0.1;
        double topY = entity.y;
        double bottomY = entity.y + entity.height - 0.1;

        int startRow = Math.max(0, (int) (topY / Tile.HEIGHT));
        // in (bottomY - 5) I mean to raise the bottom row a bit to make sure it won't consider the floor as a wall
        int checkBottomRow = Math.min(map.length - 1, (int) ((bottomY - 5) / Tile.HEIGHT));

        if (entity.velocityX > 0) {
            int rightCol = (int) (rightX / Tile.WIDTH);
            if (rightCol >= 0 && rightCol < map[0].length) {
                for (int r = startRow; r <= checkBottomRow; r++) {
                    short tileId = map[r][rightCol];
                    if (Tile.isSolid(tileId)) {
                        double tileTop = r * Tile.HEIGHT;

                        double tileBottom;
                        if (Tile.isFloatingTile(tileId)) tileBottom = tileTop + Tile.FLOATING_HEIGHT;
                        else tileBottom = tileTop + Tile.HEIGHT;

                        if (topY < tileBottom && bottomY > tileTop) {
                            entity.rightCollision = true;
                            entity.onWall = true;
                            break;
                        }
                    }
                }
            }
        } else if (entity.velocityX < 0) {
            int leftCol = (int) (leftX / Tile.WIDTH);
            if (leftCol >= 0 && leftCol < map[0].length) {
                for (int r = startRow; r <= checkBottomRow; r++) {
                    short tileId = map[r][leftCol];
                    if (Tile.isSolid(tileId)) {
                        double tileTop = r * Tile.HEIGHT;

                        double tileBottom;
                        if (Tile.isFloatingTile(tileId)) tileBottom = tileTop + Tile.FLOATING_HEIGHT;
                        else tileBottom = tileTop + Tile.HEIGHT;

                        if (topY < tileBottom && bottomY > tileTop) {
                            entity.leftCollision = true;
                            entity.onWall = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void checkYCollision(Entity entity) {
        entity.onFloor = false;
        entity.topCollision = false;

        double leftX = entity.x;
        double rightX = entity.x + entity.width - 0.1;
        double topY = entity.y;
        double bottomY = entity.y + entity.height;

        int startCol = Math.max(0, (int) (leftX / Tile.WIDTH));
        int endCol = Math.min(map[0].length - 1, (int) (rightX / Tile.WIDTH));

        if (entity.velocityY >= 0) {
            int floorRow = (int) (bottomY / Tile.HEIGHT);
            if (floorRow >= 0 && floorRow < map.length) {
                for (int c = startCol; c <= endCol; c++) {
                    short tileId = map[floorRow][c];
                    if (Tile.isSolid(tileId)) {
                        double tileTop = floorRow * Tile.HEIGHT;

                        double tileBottom;
                        if (Tile.isFloatingTile(tileId)) tileBottom = tileTop + Tile.FLOATING_HEIGHT;
                        else tileBottom = tileTop + Tile.HEIGHT;

                        if (bottomY >= tileTop && bottomY <= tileBottom) {
                            entity.onFloor = true;
                            entity.y = tileTop - entity.height - 0.01;
                            break;
                        }
                    }
                }
            }
        } else if (entity.velocityY < 0) {
            int ceilRow = (int) (topY / Tile.HEIGHT);
            if (ceilRow >= 0 && ceilRow < map.length) {
                for (int c = startCol; c <= endCol; c++) {
                    short tileId = map[ceilRow][c];
                    if (Tile.isSolid(tileId)) {
                        double tileTop = ceilRow * Tile.HEIGHT;

                        double tileBottom;
                        if (Tile.isFloatingTile(tileId)) tileBottom = tileTop + Tile.FLOATING_HEIGHT;
                        else tileBottom = tileTop + Tile.HEIGHT;

                        if (topY <= tileBottom && topY >= tileTop) {
                            entity.topCollision = true;
                            entity.y = tileBottom + 0.01;
                            break;
                        }
                    }
                }
            }
        }
    }

    private void checkEnemyDamage(Player player) {
        player.canBeHitIn -= 100;
        if (player.canBeHitIn > 0) return;

        Rectangle pRect = new Rectangle((int) player.x, (int) player.y, player.width, player.height);

        for (Enemy enemy : enemies) {
            if (!enemy.alive) continue;
            Rectangle eRect = enemy.rectangle();

            if (pRect.intersects(eRect)) {
                player.lives--;
                player.canBeHitIn = 1500;

                double playerCenterX = player.x + player.width / 2.0;
                double enemyCenterX = enemy.x + enemy.width / 2.0;

                if (playerCenterX < enemyCenterX) player.velocityX = -player.hitForceX;
                else player.velocityX = player.hitForceX;

                player.velocityY = -player.hitForceY;
                break;
            }
        }
    }

    public int getPlayerLives() {
        return player.lives;
    }

    public void shotBullet() {
        bullets.add(new Bullet(player));
    }

    public void draw(Graphics g) {
        for (Tile tile : tiles) tile.draw(g);
        for (Enemy enemy : enemies) enemy.draw(g);
        for (Bullet bullet : bullets) bullet.draw(g);
        this.player.draw(g);
    }
}
