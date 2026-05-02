package org.example;

import java.awt.*;

public abstract class TestEntity extends TestGameObject {
    protected int width;
    protected int height;
    protected Image image;

    protected final double TARGET_FPS = 60.0;
    protected final double VELOCITY_MULTIPLIER = TARGET_FPS;
    protected final double ACCELERATION_MULTIPLIER = TARGET_FPS * TARGET_FPS;

    protected final double GRAVITY = 0.6 * ACCELERATION_MULTIPLIER;
    protected final double FRICTION = 1.5 * ACCELERATION_MULTIPLIER;
    protected final double DRAG = 0.2 * ACCELERATION_MULTIPLIER;

    protected double velocityX = 0;
    protected double velocityY = 0;
    protected double maxVelocityX;
    protected double maxVelocityY;
    protected double movementForce;
    protected double jumpForce;

    protected int lives;

    protected boolean alive = true;
    protected boolean onFloor;


    public void update(double deltaTime) {
        velocityY += GRAVITY * deltaTime;

        if (velocityX > 0) {
            velocityX -= (onFloor ? FRICTION : DRAG) * deltaTime;
            if (velocityX < 0) velocityX = 0;
        }
        else if (velocityX < 0) {
            velocityX += (onFloor ? FRICTION : DRAG) * deltaTime;
            if (velocityX > 0) velocityX = 0;
        }

        velocityX = Math.max(-maxVelocityX, Math.min(maxVelocityX, velocityX));
        velocityY = Math.max(-maxVelocityY, Math.min(maxVelocityY, velocityY));
    }

}
