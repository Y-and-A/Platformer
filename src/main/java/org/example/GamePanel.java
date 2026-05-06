package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private final GameEngine gameEngine;
    private Thread gameThread;
    public static ArrayList<Long> paintingDelay = new ArrayList<>();

    private final boolean[] keys = new boolean[256];
    private final boolean[] prevKeys = new boolean[256];

    private final BufferedImage backBuffer;
    private static int windowWidth;
    private static int windowHeight;
    private static double scaleX;
    private static double scaleY;
    private static double scale;
    private static int scaledWidth;
    private static int scaledHeight;
    private static int offsetX;
    private static int offsetY;

    public GamePanel(int levelNum) {
        short[][] map = Level.getLevel(levelNum);
        gameEngine = new GameEngine(map);
        setFocusable(true);

        backBuffer = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_RGB);
        setBackground(Color.BLACK);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() < 256) keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() < 256) keys[e.getKeyCode()] = false;
            }
        });
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame() {
        gameThread = null;
    }

    @Override
    public void run() {
        while (gameThread != null) {
            gameEngine.update(keys, prevKeys);
            System.arraycopy(keys, 0, prevKeys, 0, keys.length);
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D bG = backBuffer.createGraphics();

        bG.setColor(Color.decode("#87CEEB")); //blue
        bG.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
        long before = System.nanoTime();
        gameEngine.draw(bG);//draws the image on the backBuffer
        long after = System.nanoTime() -before;
        paintingDelay.add(after);
        long longest = 0;
        for (int i = 0; i < paintingDelay.size(); i++) {
            if (paintingDelay.get(i)>longest)longest = paintingDelay.get(i);
        }
        System.out.println("longest: "+longest/100000);
        bG.setColor(Color.red);
        bG.setFont(new Font("David",Font.BOLD,30));
        bG.drawString(gameEngine.getPlayerLives()+"",Window.WIDTH-30,30);
        bG.dispose();

        Graphics2D g2d = (Graphics2D) g;
        windowWidth = getWidth();
        windowHeight = getHeight();

        scaleX = (double) windowWidth / Window.WIDTH;
        scaleY = (double) windowHeight / Window.HEIGHT;
        scale = Math.min(scaleX, scaleY);

        scaledWidth = (int) (Window.WIDTH * scale);
        scaledHeight = (int) (Window.HEIGHT * scale);
        offsetX = (windowWidth - scaledWidth) / 2;
        offsetY = (windowHeight - scaledHeight) / 2;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(backBuffer, offsetX, offsetY, scaledWidth, scaledHeight, null);
    }
}