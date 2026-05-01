package org.example;

import java.awt.*;

public abstract class TestEntity extends TestGameObject {
    protected int width;
    protected int height;
    protected Image image;

    protected double gravity = 0.6;
    protected final double friction = 1.5;
    protected final double drag = 0.2;

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
        double timeScale = deltaTime * 60;

        velocityY += gravity * timeScale;

        if (velocityX > 0) {
            velocityX -= (onFloor ? friction : drag) * timeScale;
            if (velocityX < 0) velocityX = 0;
        }
        else if (velocityX < 0) {
            velocityX += (onFloor ? friction : drag) * timeScale;
            if (velocityX > 0) velocityX = 0;
        }

        velocityX = Math.max(-maxVelocityX, Math.min(maxVelocityX, velocityX));
        velocityY = Math.max(-maxVelocityY, Math.min(maxVelocityY, velocityY));
    }

}
