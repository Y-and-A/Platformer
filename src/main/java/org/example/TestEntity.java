package org.example;

public abstract class TestEntity extends TestGameObject {
    protected int width;
    protected int height;

    protected double velocityX = 0;
    protected double velocityY = 0;

    protected double gravity = 0.5;
    protected final double friction = 0.5;
    protected final double drag = 0.1;
    protected double maxVelocityX;
    protected double maxVelocityY;

    protected double movementForce;
    protected double jumpForce;

    protected int lives;

    protected boolean alive = true;
    protected boolean onFloor;


    public void update() {
        if (!onFloor) velocityY += gravity;

        if (velocityX > 0) {
            velocityX -= onFloor ? friction : drag;
            if (velocityX < 0) velocityX = 0;
        }
        else if (velocityX < 0) {
            velocityX += onFloor ? friction : drag;
            if (velocityX > 0) velocityX = 0;
        }

        velocityX = Math.max(-maxVelocityX, Math.min(maxVelocityX, velocityX));
        velocityY = Math.max(-maxVelocityY, Math.min(maxVelocityY, velocityY));
    }

}
