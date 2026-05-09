package org.example;

import java.awt.*;

public class Enemy extends Entity {
    private enum Direction {LEFT, RIGHT, UP, DOWN}
    private Direction facingDirection = Direction.RIGHT;

    protected Enemy(int x, int y) {
        super(x, y, 50, 50);

        lives = 1;
        jumpForce = 11.0;
        maxVelocityX = 3.0; // TODO this is for test purposes only, change it back to around 3.0
        maxVelocityY = 16.0;
        movementForce = 2;

        image = Assets.enemyFront;
    }

    public void chasePlayer(Player player, short[][] map) {
        if (!alive) return;

        double distanceX = player.x - this.x;
        double distanceY = player.y - this.y;

        boolean floorOnRight = false;
        boolean floorOnLeft = false;

        if (onFloor) {
            int startRow = (int) ((this.y + this.height + 2) / Tile.HEIGHT);
            int rightCol = (int) ((this.x + this.width + 5) / Tile.WIDTH);
            int leftCol = (int) ((this.x - 5) / Tile.WIDTH);

            if (rightCol >= 0 && rightCol < map[0].length) {
                for (int r = startRow; r < map.length; r++) {
                    if (Tile.isSolid(map[r][rightCol])) {
                        floorOnRight = true;
                        break;
                    }
                }
            }

            if (leftCol >= 0 && leftCol < map[0].length) {
                for (int r = startRow; r < map.length; r++) {
                    if (Tile.isSolid(map[r][leftCol])) {
                        floorOnLeft = true;
                        break;
                    }
                }
            }
        } else {
            floorOnRight = true;
            floorOnLeft = true;
        }

        if (Math.abs(distanceX) > 2) {
            if (distanceX > 0) {
                if (onFloor && !floorOnRight) {
                    this.velocityX = 0;
                } else {
                    this.velocityX += movementForce;
                    facingDirection = Direction.RIGHT;
                    image = Assets.enemyRight;
                }
            } else {
                if (onFloor && !floorOnLeft) {
                    this.velocityX = 0;
                } else {
                    this.velocityX -= movementForce;
                    facingDirection = Direction.LEFT;
                    image = Assets.enemyLeft;
                }
            }
        } else this.velocityX = 0;

        if (onFloor) {
            boolean shouldJump = false;

            if (facingDirection == Direction.RIGHT && rightCollision) shouldJump = true;
            else if (facingDirection == Direction.LEFT && leftCollision) shouldJump = true;

            else if (Math.abs(distanceX) < 100) {
                // making sure enemies won't jump if player is too far above
                if (distanceY < -20 && distanceY >= -(Tile.HEIGHT * 3)) shouldJump = true;
            }

            if (shouldJump) this.velocityY = -jumpForce;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }
}
