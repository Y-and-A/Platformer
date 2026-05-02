package org.example;

import java.awt.*;

public abstract class GameObject {
    protected double x;
    protected double y;

    abstract public void draw(Graphics g);
}
