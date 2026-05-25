package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

public class TitleScreen extends JPanel {
    private final Timer animTimer;
    private final int PLAYER_WALKS=0,TUMBLEWEED_ROLLS=1,SHOOTING=2,EXPLOSION=3;
    private int animState = 0; // 0: Player walks, 1: Tumbleweed rolls, 2: Shooting, 3: Explosion

    // Animation variables
    private double tumbleweedX, tumbleweedY, tumbleweedAngle;
    private double playerX, playerY;
    private double bulletX;

    // UI Elements
    private final JPanel buttonContainer;
    private final ArrayList<Particle> particles = new ArrayList<>();

    public TitleScreen(Runnable onSelectLevel, Runnable onHowTo) {
        setLayout(new BorderLayout());

        buttonContainer = new JPanel(new GridBagLayout());
        buttonContainer.setOpaque(false);

        WesternButton selectLevelBtn = new WesternButton("Select Level");
        selectLevelBtn.setPreferredSize(new Dimension(250, 60));
        selectLevelBtn.addActionListener(e -> onSelectLevel.run());

        WesternButton howToBtn = new WesternButton("How To Play");
        howToBtn.setPreferredSize(new Dimension(250, 60));
        howToBtn.addActionListener(e -> onHowTo.run());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10, 0, 10, 0);
        buttonContainer.add(selectLevelBtn, gbc);

        gbc.gridy = 1;
        buttonContainer.add(howToBtn, gbc);

        add(buttonContainer, BorderLayout.CENTER);

        // Timer still runs at ~60 FPS
        animTimer = new Timer(16, e -> updateAnimation());
    }

    public void resetAnimation() {
        buttonContainer.setVisible(false);
        // Calculate exact Y positions so they sit on the sand.
        // Sand starts at Window.HEIGHT - 150.
        playerY = Window.HEIGHT - 150 - 70;      // 70 is player height
        tumbleweedY = Window.HEIGHT - 150 - 60;  // 60 is tumbleweed height

        // Player starts off-screen left
        playerX = -100;

        // Tumbleweed starts way off-screen right
        tumbleweedX = Window.WIDTH + 200;
        tumbleweedAngle = 0;

        // Hide bullet initially
        bulletX = -100;

        animState = PLAYER_WALKS;
        particles.clear();

        animTimer.start();
        repaint();
    }

    private void updateAnimation() {
        if (animState == PLAYER_WALKS) {
            // Player walks in from the left
            playerX += 6;
            if (playerX >= 200) { // Stops at x = 200
                animState = TUMBLEWEED_ROLLS;
            }
        }
        else if (animState == TUMBLEWEED_ROLLS) {
            buttonContainer.setVisible(true);
            // Tumbleweed suddenly rolls in from the right
            tumbleweedX -= 15;
            tumbleweedAngle -= 0.4;

            if (tumbleweedX <= Window.WIDTH / 2.0 + 100) {
                // Tumbleweed reaches the target zone, player shoots!
                bulletX = playerX + 45; // Start bullet at the gun barrel
                animState = SHOOTING;
            }
        }
        else if (animState == SHOOTING) {
            // Bullet moves extremely fast
            bulletX += 45;

            // Tumbleweed slows down a bit but keeps rolling for momentum
            tumbleweedX -= 8;
            tumbleweedAngle -= 0.2;

            if (bulletX >= tumbleweedX) {
                // Pass the center of the tumbleweed to the explosion generator
                createExplosion(tumbleweedX + 30, tumbleweedY + 30);
                animState = EXPLOSION;
            }
        }
        else if (animState == EXPLOSION) {
            // Handle particle physics
            Iterator<Particle> it = particles.iterator();
            while (it.hasNext()) {
                Particle p = it.next();
                p.x += p.vx;
                p.y += p.vy;
                p.vy += 0.8; // Gravity pulls particles down
                p.life--;
                if (p.life <= 0) it.remove();
            }

            // Stop timer to save CPU once dust settles
            if (particles.isEmpty()) {
                animTimer.stop();
            }
        }
        repaint();
    }

    private void createExplosion(double x, double y) {
        for (int i = 0; i < 25; i++) {
            double vx = (Math.random() - 0.5) * 25;
            double vy = (Math.random() - 1) * 20;
            particles.add(new Particle(x, y, vx, vy, (int)(Math.random() * 20 + 20)));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // 1. Draw Desert Background
        g2d.setColor(new Color(135, 206, 235));
        g2d.fillRect(0, 0, getWidth(), getHeight() - 150);

        g2d.setColor(new Color(210, 180, 140));
        g2d.fillRect(0, getHeight() - 150, getWidth(), 150);

        // 2. Draw Player (always drawn once animation starts)
        g2d.drawImage(Assets.playerRight, (int) playerX, (int) playerY, 49, 70, null);

        // 3. Draw Tumbleweed (states 1 and 2)
        if (animState ==TUMBLEWEED_ROLLS || animState ==SHOOTING) {
            AffineTransform old = g2d.getTransform();
            // Translate to the center of the tumbleweed (x + 30, y + 30) for perfect rotation
            g2d.translate(tumbleweedX + 30, tumbleweedY + 30);
            g2d.rotate(tumbleweedAngle);

            // Draw image relative to the new translated center
            g2d.drawImage(Assets.tumbleweed, -30, -30, 60, 60, null);

            g2d.setTransform(old);
        }

        // 4. Draw Bullet (state 2 only)
        if (animState == TUMBLEWEED_ROLLS) {
            g2d.setColor(Color.BLACK);
            // Adjusted bullet Y position to match gun height better
            g2d.fillOval((int)bulletX, (int)playerY + 28, 12, 12);
        }

        // 5. Draw Particles (state 3)
        if (animState == EXPLOSION) {
            g2d.setColor(new Color(139, 115, 85));
            for (Particle p : particles) {
                g2d.fillRect((int)p.x, (int)p.y, 8, 8);
            }
        }
    }

    private static class Particle {
        double x, y, vx, vy;
        int life;
        Particle(double x, double y, double vx, double vy, int life) {
            this.x = x; this.y = y; this.vx = vx; this.vy = vy; this.life = life;
        }
    }
}