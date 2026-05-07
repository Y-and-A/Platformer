package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bullet extends Entity {
    private int bulletImgWidth = 50;
    private int bulletImgHeight = 50;

    double velocityX;
    double velocityY;
    double bulletVelocity = 8.5;

    private Image left, right,up,down;

    public Bullet(Player player) {
        // MAGIC NUMBERS IN ORDER TO ALIGN BULLET TO PLAYER HAND
        super((player.facingDirection == Direction.LEFT) ? player.x : player.x + 15, player.y + 21);

        this.width = bulletImgWidth;
        this.height = bulletImgHeight;

        loadImages();
        if (player.facingDirection == Direction.LEFT) {
            this.velocityX = -bulletVelocity;
            image = left;
        } else if (player.facingDirection == Direction.RIGHT) {
            this.velocityX = bulletVelocity;
            image = right;
        } else if (player.facingDirection == Direction.UP) {
            this.velocityY = -bulletVelocity;
            image = up;
        } else if (player.facingDirection == Direction.DOWN) {
            this.velocityY = bulletVelocity;
            image = down;
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

    public void loadImages() {
        try {
            right = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmallRight.png"));
            left = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmallLeft.png"));
            up = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmallUp.png"));
            down = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmallDown.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
    }
}
