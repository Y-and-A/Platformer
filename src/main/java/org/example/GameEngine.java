package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameEngine {
    private Player player;

    private final ArrayList<Tile> tiles = new ArrayList<>();
    public final ArrayList<Enemy> enemies = new ArrayList<>();

    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ConcurrentLinkedQueue<Bullet> pendingBullets = new ConcurrentLinkedQueue<>();

    private final short[][] map;

    public GameEngine(short[][] map) {
        this.map = map;

        Image image;
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                boolean floatingPlatform = Tile.isPlatform(map[r][c]);
                image = Assets.getTileImage(map[r][c]);
                if (image != null) tiles.add(new Tile(c * Tile.WIDTH, r * Tile.HEIGHT, floatingPlatform, image));
                else if (map[r][c] == Tile.ENEMY_ID) enemies.add(new Enemy(c * Tile.WIDTH, r * Tile.HEIGHT));
                else if (map[r][c] == Tile.PLAYER_ID) this.player = new Player(c * Tile.WIDTH, r * Tile.HEIGHT);
            }
        }
    }

    public void update(boolean[] keys, boolean[] prevKeys) {
        player.update(keys, prevKeys);
        move(player);
        checkEnemyDamage(player);

        separateEnemies();
        for (Enemy enemy : enemies) {
            if (enemy.alive) {
                if (enemy.x + enemy.width < 0 || enemy.x > Window.WIDTH || enemy.y < 0 || enemy.y > Window.HEIGHT) {
                    enemy.alive = false;
                    continue;
                }

                enemy.chasePlayer(player, map);
                enemy.update();
//                move(enemy);
            }
        }

        synchronized (bullets) {
            while (!pendingBullets.isEmpty()) {
                bullets.add(pendingBullets.poll());
            }
        }

        for (Bullet bullet : bullets) {
            bullet.update();
            if (!bullet.alive) continue;

            if (bullet.x < 0 || bullet.x > Window.WIDTH || bullet.y < 0 || bullet.y > Window.HEIGHT) {
                bullet.alive = false;
                continue;
            }

            //todo change it to match the bullet size exactly or resize the image and Bullet.width/height
            // the hitbox is the correct size should use it instead, but does not mater since we using bullet y
            int bCol = (int) (bullet.x / Tile.WIDTH);
            int bRow = (int) (bullet.y / Tile.HEIGHT);

            if (bRow >= 0 && bRow < map.length && bCol >= 0 && bCol < map[0].length) {
                short tileId = map[bRow][bCol];
                if (Tile.isSolid(tileId)) {
                    if (Tile.isPlatform(tileId)) {
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

            for (Enemy enemy : enemies) {
                if (!enemy.alive) continue;

                if (enemy.hitbox.intersects(bullet.getHitbox())) {
                    enemy.lives--;
                    if (enemy.lives <= 0) enemy.alive = false;
                    bullet.alive = false;
                    break;
                }
            }
        }

        synchronized (bullets) { bullets.removeIf(bullet -> !bullet.alive); }
        synchronized (enemies) { enemies.removeIf(enemy -> !enemy.alive); }

        if (!player.alive || enemies.isEmpty()) Window.changeScene("Title screen");
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

        entity.updateHitbox();
    }

    private void checkXCollision(Entity entity) {
        entity.leftCollision = false;
        entity.rightCollision = false;
        entity.onWall = false;

        if (entity.velocityX == 0) return;

        double topY = entity.y;
        double bottomY = entity.y + entity.height - 0.1;

        int startRow = Math.max(0, (int) (topY / Tile.HEIGHT));
        // in (bottomY - 5) I mean to raise the bottom row a bit to make sure it won't consider the floor as a wall
        int checkBottomRow = Math.min(map.length - 1, (int) ((bottomY - 5) / Tile.HEIGHT));

        double targetX = (entity.velocityX > 0) ? (entity.x + entity.width - 0.1) : entity.x;
        int targetCol = (int) (targetX / Tile.WIDTH);

        if (targetCol >= 0 && targetCol < map[0].length) {
            for (int r = startRow; r <= checkBottomRow; r++) {
                short tileId = map[r][targetCol];
                if (Tile.isSolid(tileId)) {
                    double tileTop = r * Tile.HEIGHT;
                    double tileBottom = tileTop + (Tile.isPlatform(tileId) ? Tile.FLOATING_HEIGHT : Tile.HEIGHT);

                    if (topY < tileBottom && bottomY > tileTop) {
                        if (entity.velocityX > 0) entity.rightCollision = true;
                        else entity.leftCollision = true;

                        entity.onWall = true;
                        break;
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
                        if (Tile.isPlatform(tileId)) tileBottom = tileTop + Tile.FLOATING_HEIGHT;
                        else tileBottom = tileTop + Tile.HEIGHT;

                        if (bottomY >= tileTop && bottomY <= tileBottom) {
                            if (Tile.isMushroom(tileId)) {
                                //warning, it can't be higher than 16 or -16 because of the maxVelocityY of Enemy
                                entity.velocityY = -16;
                                entity.velocityX = 0;
                                break;
                            }
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
                        if (Tile.isPlatform(tileId)) tileBottom = tileTop + Tile.FLOATING_HEIGHT;
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
        player.canBeHitIn = Math.max(0, player.canBeHitIn - 1);
        if (player.canBeHitIn > 0) return;

        for (Enemy enemy : enemies) {
            if (!enemy.alive) continue;

            if (player.hitbox.intersects(enemy.hitbox)) {
                player.lives--;
                player.canBeHitIn = 40;

                double playerCenterX = player.x + player.width / 2.0;
                double enemyCenterX = enemy.x + enemy.width / 2.0;

                if (playerCenterX < enemyCenterX) player.velocityX = -player.hitForceX;
                else player.velocityX = player.hitForceX;

                player.velocityY = -player.hitForceY;
                break;
            }
        }
    }

    private void separateEnemies() {
        //todo O(n^2) :) should be optimized or something
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e1 = enemies.get(i);
            if (!e1.alive) continue;

            for (int j = i + 1; j < enemies.size(); j++) {
                Enemy e2 = enemies.get(j);
                if (!e2.alive) continue;

                if (e1.getHitbox().intersects(e2.getHitbox())) {
                    double center1 = e1.x + e1.width / 2.0;
                    double center2 = e2.x + e2.width / 2.0;

                    if (center1 == center2) center2 += 0.1; // should  not happen, edge case

                    double pushAmount = 1.0;
                    e1.x += (center1 < center2) ? -pushAmount : pushAmount;
                    e2.x += (center1 < center2) ? pushAmount : -pushAmount;

                    e1.updateHitbox();
                    e2.updateHitbox();
                }
            }
        }
    }

    public int getPlayerLives() {
        return player.lives;
    }

    public void shotBullet() {
        pendingBullets.add(new Bullet(player));
    }

    public void draw(Graphics g) {
        for (Tile tile : tiles) tile.draw(g);

        synchronized (enemies) { for (Enemy enemy : enemies) enemy.draw(g); }
        synchronized (bullets) { for (Bullet bullet : bullets) bullet.draw(g); }

        this.player.draw(g);
    }
}
