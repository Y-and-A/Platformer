package org.example;

import java.awt.*;

import static org.example.UiScaling.scale;

public class Tile extends GameObject{
    Image image;
    boolean floating;
    public Tile(int x,int y,boolean floating,Image image){
        super(x, y);
        width= (int) (50*scale);
        if (floating){
            height = (int) (20*scale);
            floating = true;
        }
        else
            height = (int) (50*scale);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y,width, (int) (50*scale),null);
    }
    public Rectangle rectangle (){
        return new Rectangle((int) x, (int) y,width,height);
    }
}
