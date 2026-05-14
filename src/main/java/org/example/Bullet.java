package org.example;

import java.awt.*;

public class Bullet extends Entity {
    private static final int BULLET_IMG_WIDTH = 50;
    private static final int BULLET_IMG_HEIGHT = 50;
    private static final int HITBOX_WIDTH = 16;
    private static final int HITBOX_HEIGHT = 16;

    public Bullet(Player player) {
        // MAGIC NUMBERS IN ORDER TO ALIGN BULLET TO PLAYER HAND
        super(
                (int) ((player.shootDirection == Direction.LEFT) ? player.x : player.x + 15),
                (int) (player.y + 32),
                HITBOX_WIDTH,
                HITBOX_HEIGHT
        );

        movementForce = 8.5;

        if (player.shootDirection == Direction.LEFT) {
            this.velocityX = -movementForce;
            image = Assets.bulletLeft;
        } else if (player.shootDirection == Direction.RIGHT) {
            this.velocityX = movementForce;
            image = Assets.bulletRight;
        }
    }

    public void update() {
        if (alive) {
            this.x += velocityX;
            this.y += velocityY;
            updateHitbox();
        }
    }

    @Override
    public void draw(Graphics g) {
        int offsetX = (BULLET_IMG_WIDTH - HITBOX_WIDTH) / 2;
        int offsetY = (BULLET_IMG_HEIGHT - HITBOX_HEIGHT) / 2;

        int xPos = (int) x - offsetX;
        int yPos = (int) y - offsetY;

        g.drawImage(image, xPos, yPos, BULLET_IMG_WIDTH, BULLET_IMG_HEIGHT, null);
    }
}
