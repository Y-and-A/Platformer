package org.example;

import java.awt.*;

public abstract class Entity extends GameObject {
    protected Image image;

    protected final double GRAVITY = 0.6;
    protected final double FRICTION = 1.5;
    protected final double DRAG = 0.2;

    protected double moveX;
    protected double velocityX = 0;
    protected double velocityY = 0;
    protected double maxVelocityX;
    protected double maxVelocityY;
    protected double jumpForce;
    protected double wallJumpForce;

    protected int lives;

    protected boolean alive = true;
    protected boolean onFloor;
    protected boolean onWall;
    protected boolean leftCollision;
    protected boolean rightCollision;
    protected boolean topCollision;
//    protected boolean topCollisionWithFloating;

    protected Entity(int x, int y, int width, int height) {
        super(x, y, width, height);
    }


    public void update() {
        velocityY += GRAVITY;

        if (velocityX > 0) {
            velocityX -= (onFloor ? FRICTION : DRAG);
            if (velocityX < 0) velocityX = 0;
        }
        else if (velocityX < 0) {
            velocityX += (onFloor ? FRICTION : DRAG);
            if (velocityX > 0) velocityX = 0;
        }

        velocityX = Math.clamp(velocityX, -maxVelocityX, maxVelocityX);
        velocityY = Math.clamp(velocityY, -maxVelocityY, maxVelocityY);
    }

}
