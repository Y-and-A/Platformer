package org.example;

import java.awt.*;

public class Bullet extends Entity {
    private static final int BULLET_IMG_WIDTH = 50;
    private static final int BULLET_IMG_HEIGHT = 50;

    double velocityX;
    double velocityY;
    double bulletVelocity = 8.5;

    public Bullet(Player player) {
        // MAGIC NUMBERS IN ORDER TO ALIGN BULLET TO PLAYER HAND
        super((player.facingDirection == Direction.LEFT) ? player.x : player.x + 15, player.y + 21);

        this.width = BULLET_IMG_WIDTH;
        this.height = BULLET_IMG_HEIGHT;

        if (player.facingDirection == Direction.LEFT) {
            this.velocityX = -bulletVelocity;
            image = Assets.bulletLeft;
        } else if (player.facingDirection == Direction.RIGHT) {
            this.velocityX = bulletVelocity;
            image = Assets.bulletRight;
        } else if (player.facingDirection == Direction.UP) {
            this.velocityY = -bulletVelocity;
            image = Assets.bulletUp;
        } else if (player.facingDirection == Direction.DOWN) {
            this.velocityY = bulletVelocity;
            image = Assets.bulletDown;
        }
    }

    public void update() {
        if (alive) {
            this.x += velocityX;
            this.y += velocityY;
        } else { // TODO couldn't dispawn bullet, needs to be replaced
            x = -1;
            y = -1;
            velocityX = 0;
            velocityY = 0;
            image = null;
        }
    }

    public Rectangle rectangle() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }
}
