package org.example;

import java.awt.*;

public abstract class Entity extends GameObject {
    protected Image image;

    protected final double GRAVITY = 0.6;
    protected final double FRICTION = 1.5;
    protected final double DRAG = 0.2;

    protected double velocityX = 0;
    protected double velocityY = 0;
    protected double maxVelocityX;
    protected double maxVelocityY;
    protected double movementForce;
    protected double jumpForce;

    protected double hitForceX;
    protected double hitForceY;

    protected int lives = 1;

    public boolean alive = true;
    protected boolean onFloor;
    protected boolean onWall;
    protected boolean leftCollision;
    protected boolean rightCollision;
    protected boolean topCollision;

    public int canBeHitIn;
    protected Rectangle hitbox;


    protected Entity(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void updateHitbox() {
        this.hitbox.setLocation((int) x, (int) y);
    }

    public Rectangle getHitbox() {
        return this.hitbox;
    }


    public void update() {
        if (lives <= 0) alive = false;

        velocityY += GRAVITY;

        if (velocityX > 0) {
            velocityX -= (onFloor ? FRICTION : DRAG);
            if (velocityX < 0) velocityX = 0;
        } else if (velocityX < 0) {
            velocityX += (onFloor ? FRICTION : DRAG);
            if (velocityX > 0) velocityX = 0;
        }

        velocityX = Math.clamp(velocityX, -maxVelocityX, maxVelocityX);
        velocityY = Math.clamp(velocityY, -maxVelocityY, maxVelocityY);
    }

}
