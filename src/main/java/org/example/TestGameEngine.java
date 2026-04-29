package org.example;

import java.awt.*;

public class TestGameEngine {
    private TestPlayer player;
    private int[][] map;
    private int TILE_WIDTH;
    private int TILE_HEIGHT;


    public TestGameEngine(TestPlayer player, int[][] map) {
        this.player = new TestPlayer();
        this.map = map;
    }

    public void update(boolean[] keys) {
        collidesWithTile(player);
        this.player.update(keys);
    }

    public void draw(Graphics g) {
        g.setColor(Color.green);
        TILE_WIDTH = TestWindow.WIDTH / map[0].length;
        TILE_HEIGHT = TestWindow.HEIGHT / map.length;
        g.setColor(Color.green);
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 1) {
                    g.fillRect(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                }
            }
        }

        g.setColor(Color.red);
        this.player.draw(g);
    }

    public void collidesWithTile(TestEntity entity) {
        Rectangle entityRect = entity.getRect();
        Rectangle tileRect;
        entity.onFloor = false;
        entity.rightCollision = false;
        entity.leftCollision  =false;
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 1) {
                    tileRect = new Rectangle(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
                    entityRect = new Rectangle(entity.x+10,entity.y+entity.height,entity.width-20,1);
                    if (entityRect.intersects(tileRect)) {
                        entity.y = row * TILE_HEIGHT- entity.height;
//                        System.out.println("clipped floor");
                        entity.onFloor = true ;
                    }


                    entityRect = new Rectangle(entity.x,entity.y,1,entity.height-2);
                    if (entityRect.intersects(tileRect)) {
                        entity.x = (col+1) * TILE_WIDTH;
                        System.out.println("clipped left");
                        entity.leftCollision=true;
                    }
                    entityRect = new Rectangle(entity.x+entity.width,entity.y,1,entity.height-2);
                    if (entityRect.intersects(tileRect)) {
                        entity.x = col * TILE_WIDTH-entity.width;
                        System.out.println("clipped right");
                        entity.rightCollision = true;
                    }
                }
            }
        }
    }
    public void fixClipping(TestEntity entity){

    }

}
