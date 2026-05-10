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

        this.hitbox.setSize(16, 16);//actual bullet size
//        new SoundManager(); //fixme bla

        this.width = BULLET_IMG_WIDTH;
        this.height = BULLET_IMG_HEIGHT;

        if (player.facingDirection == Direction.LEFT) {
            this.velocityX = -bulletVelocity;
            image = Assets.bulletLeft;
        } else if (player.facingDirection == Direction.RIGHT) {
            this.velocityX = bulletVelocity;
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
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }
}
