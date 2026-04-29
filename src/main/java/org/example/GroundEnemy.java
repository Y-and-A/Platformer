package org.example;

public class GroundEnemy extends Enemy {
    private final int width = 20;
    private final int height = 50;

    public GroundEnemy(int x, int y, int lives) {
        super(x, y, 20, 50, lives);
        canMove = true;
    }
}
