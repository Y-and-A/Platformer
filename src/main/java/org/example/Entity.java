package org.example;

import java.awt.*;

import static org.example.UiScaling.scale;

public abstract class Entity extends GameObject {
    protected int width;
    protected int height;
    protected Image image;

    protected final double GRAVITY = 0.6*scale;
    protected final double FRICTION = 1.5*scale;
    protected final double DRAG = 0.2*scale;

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
