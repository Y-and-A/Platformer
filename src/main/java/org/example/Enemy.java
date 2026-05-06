package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Enemy extends Entity {
    Image left, right, front, back;

    private enum Direction {LEFT, RIGHT, UP, DOWN}
    private Direction facingDirection = Direction.RIGHT;

    protected Enemy(int x, int y) {
        super(x, y);

        width = 50;
        height = 50;

        lives = 1;
        jumpForce = 11.0;
        maxVelocityX = 3.5;
        maxVelocityY = 16.0;
        moveX = 2;

        loadImages();
        image = front;
    }

    public void chasePlayer(Player player) {
        if (!alive) return;

        double distanceX = player.x - this.x;
        double distanceY = player.y - this.y;

        if (Math.abs(distanceX) > 2) {
            if (distanceX > 0) {
                this.velocityX += moveX;
                facingDirection = Direction.RIGHT;
                image = right;
            } else {
                this.velocityX -= moveX;
                facingDirection = Direction.LEFT;
                image = left;
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
    }

    //TODO this method should be provided by other class
    // something like boolean canWalkThrough(Entity entity, int tileNum)
    private boolean canWalkThrough(int tileType) {
        return tileType == 0 || tileType > 90;
    }

    public boolean[][] convertMap(short[][] map) {
        boolean[][] result = new boolean[map.length][map[0].length];

        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                result[r][c] = canWalkThrough(map[r][c]);
            }
        }

        return result;
    }

    private void loadImages() {
        try {
            front = ImageIO.read(new File("src/main/resources/enemy/front.png"));
            back = ImageIO.read(new File("src/main/resources/enemy/back.png"));
            left = ImageIO.read(new File("src/main/resources/enemy/left.png"));
            right = ImageIO.read(new File("src/main/resources/enemy/right.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
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
