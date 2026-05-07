package org.example;

import java.awt.*;

public class Enemy extends Entity {
    private enum Direction {LEFT, RIGHT, UP, DOWN}
    private Direction facingDirection = Direction.RIGHT;

    protected Enemy(int x, int y) {
        super(x, y);

        width = 50;
        height = 50;

        lives = 1;
        jumpForce = 11.0;
        maxVelocityX = 3.0;
        maxVelocityY = 16.0;
        movementForce = 2;

        image = Assets.enemyFront;
    }

    public void chasePlayer(Player player) {
        if (!alive) return;

        double distanceX = player.x - this.x;
        double distanceY = player.y - this.y;

        if (Math.abs(distanceX) > 2) {
            if (distanceX > 0) {
                this.velocityX += movementForce;
                facingDirection = Direction.RIGHT;
                image = Assets.enemyRight;
            } else {
                this.velocityX -= movementForce;
                facingDirection = Direction.LEFT;
                image = Assets.enemyLeft;
            }
        } else this.velocityX = 0;

        if (onFloor) {
            boolean shouldJump = false;

            if (facingDirection == Direction.RIGHT && rightCollision) shouldJump = true;
            else if (facingDirection == Direction.LEFT && leftCollision) shouldJump = true;

            else if (distanceY < -60 && Math.abs(distanceX) < 100) shouldJump = true;

            if (shouldJump) this.velocityY = -jumpForce;
        }
    }

    public void update() {
        super.update();
        if (lives <= 0) alive = false;
    }

    public Rectangle rectangle() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }
}
