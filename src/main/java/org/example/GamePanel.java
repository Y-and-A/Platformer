package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    private final GameEngine gameEngine;
    private Thread gameThread;

    private final boolean[] keys = new boolean[256];
    private final boolean[] prevKeys = new boolean[256];

    private final BufferedImage backBuffer;
    long longest=0;

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
        // High-resolution timer for extreme accuracy
        long lastTime = System.nanoTime();

        // Target 60 Updates (Ticks) Per Second
        final double TICKS_PER_SECOND = 60.0;
        final double TIME_PER_TICK_NS = 1000000000.0 / TICKS_PER_SECOND;

        double deltaAccumulator = 0;

        while (gameThread != null) {
            long now = System.nanoTime();
            // Calculate how much real time passed since the last loop iteration
            // and add it to the accumulator as a fraction of a "tick".
            deltaAccumulator += (now - lastTime) / TIME_PER_TICK_NS;
            lastTime = now;

            // If a full tick (or multiple) has accumulated, update the game logic!
            while (deltaAccumulator >= 1) {
                gameEngine.update(keys, prevKeys);
                System.arraycopy(keys, 0, prevKeys, 0, keys.length);
                deltaAccumulator--; // Consume one logical tick
            }

            // Render the screen as fast as possible
            repaint();

            // Optional: Give the CPU a tiny break to prevent 100% core utilization.
            // Even if this sleep overshoots on Windows, the math above guarantees
            // our game logic catches up flawlessly on the next iteration.
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D bG = backBuffer.createGraphics();

        bG.setColor(Color.decode("#87CEEB")); //blue
        bG.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
        gameEngine.draw(bG);//draws the image on the backBuffer
        bG.setColor(Color.red);
        bG.setFont(new Font("David",Font.BOLD,30));
        bG.drawString(gameEngine.getPlayerLives()+"",Window.WIDTH-30,30);
        bG.dispose();

        Graphics2D g2d = (Graphics2D) g;
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        double scaleX = (double) windowWidth / Window.WIDTH;
        double scaleY = (double) windowHeight / Window.HEIGHT;
        double scale = Math.min(scaleX, scaleY);

        int scaledWidth = (int) (Window.WIDTH * scale);
        int scaledHeight = (int) (Window.HEIGHT * scale);
        int offsetX = (windowWidth - scaledWidth) / 2;
        int offsetY = (windowHeight - scaledHeight) / 2;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(backBuffer, offsetX, offsetY, scaledWidth, scaledHeight, null);
        long after = System.nanoTime() - before;
        if (after>longest){
            longest = after;
            System.out.println("longest: "+longest/100000);
        }
    }
}