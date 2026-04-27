package org.example;

public class Player extends Entity {
    public Player(int x, int y) {
        this.x = x;
        this.y = y;

        this.lives = 3;
        this.canMove = true;
    }
}
