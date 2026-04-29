package org.example;

public abstract class TestEntity extends TestGameObject {
    protected double velocityX = 0;
    protected double velocityY = 0;

    protected int lives;

    protected boolean alive = true;
    protected boolean onFloor;

    protected double gravity = 0.1;

    public void update(boolean[] keys) {
        if (!onFloor) {
            velocityY += gravity;
        } else velocityY = 0;

        this.y += (int) velocityY;
        this.x += (int) velocityX;
    }
}
