package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

public class TitleScreen extends JPanel {
    private final Timer animTimer;
    private int animState = 0; // 0: Tumbleweed rolling, 1: Player jumps, 2: Shooting, 3: Menu

    // Animation variables
    private double tumbleweedX, tumbleweedAngle;
    private double playerX, playerY, playerVy;
    private double bulletX;

    // UI Elements
    private final JPanel buttonContainer;

    // Particle System for the tumbleweed explosion (Extra trick!)
    private final ArrayList<Particle> particles = new ArrayList<>();

    public TitleScreen(Runnable onSelectLevel, Runnable onHowTo) {
        setLayout(new BorderLayout());

        // Create the button container (invisible until state 3)
        buttonContainer = new JPanel(new GridBagLayout());
        buttonContainer.setOpaque(false); // Transparent so we see the background

        // Use our new WesternButtons
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

        // Runs at ~60 FPS (16ms)
        animTimer = new Timer(16, e -> updateAnimation());
    }

    public void resetAnimation() {
        // Reset all positions
        tumbleweedX = Window.WIDTH + 100;
        tumbleweedAngle = 0;

        playerX = Window.WIDTH / 4.0;
        playerY = Window.HEIGHT + 100; // Start below screen
        playerVy = -20; // Upward jump force

        bulletX = playerX + 40;
        animState = 0;
        particles.clear();

        buttonContainer.setVisible(false);
        animTimer.start();
        repaint();
    }

    private void updateAnimation() {
        if (animState == 0) {
            // Tumbleweed rolling left
            tumbleweedX -= 6;
            tumbleweedAngle -= 0.15;

            if (tumbleweedX < Window.WIDTH / 1.5) {
                animState = 1; // Trigger player jump
            }
        }
        else if (animState == 1) {
            tumbleweedX -= 6;
            tumbleweedAngle -= 0.15;

            // Player physics
            playerY += playerVy;
            playerVy += 0.8; // Gravity

            int groundLevel = Window.HEIGHT - 200;
            if (playerY >= groundLevel) {
                playerY = groundLevel;
                animState = 2; // Trigger shooting
            }
        }
        else if (animState == 2) {
            // Bullet moves fast to the right
            bulletX += 25;
            if (bulletX >= tumbleweedX) {
                // Collision! Create explosion particles
                createExplosion(tumbleweedX, Window.HEIGHT - 170);
                animState = 3; // Finished
            }
        }
        else if (animState == 3) {
            // Update particles
            Iterator<Particle> it = particles.iterator();
            while (it.hasNext()) {
                Particle p = it.next();
                p.x += p.vx;
                p.y += p.vy;
                p.vy += 0.5; // gravity for particles
                p.life--;
                if (p.life <= 0) it.remove();
            }

            if (!buttonContainer.isVisible()) {
                buttonContainer.setVisible(true);
            }

            // Stop timer once particles are gone to save CPU
            if (particles.isEmpty()) {
                animTimer.stop();
            }
        }
        repaint(); // Tell Swing to redraw the panel
    }

    private void createExplosion(double x, double y) {
        for (int i = 0; i < 15; i++) {
            double vx = (Math.random() - 0.5) * 15;
            double vy = (Math.random() - 1) * 15;
            particles.add(new Particle(x, y, vx, vy, (int)(Math.random() * 20 + 20)));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // 1. Draw Desert Background
        g2d.setColor(new Color(135, 206, 235)); // Sky blue
        g2d.fillRect(0, 0, getWidth(), getHeight() - 150);

        g2d.setColor(new Color(210, 180, 140)); // Sand/Ground
        g2d.fillRect(0, getHeight() - 150, getWidth(), 150);

        // 2. Draw Tumbleweed
        if (animState < 3) {
            // We use AffineTransform to rotate around the center of the tumbleweed
            AffineTransform old = g2d.getTransform();
            g2d.translate(tumbleweedX, getHeight() - 170);
            g2d.rotate(tumbleweedAngle);

            // NOTE: Replace this with g2d.drawImage(Assets.tumbleweed, -30, -30, 60, 60, null);
            g2d.setColor(new Color(139, 115, 85));
            g2d.fillOval(-30, -30, 60, 60);

            g2d.setTransform(old);
        }

        // 3. Draw Player
        if (animState >= 1) {
            // NOTE: Replace with g2d.drawImage(Assets.playerShoot, (int)playerX, (int)playerY, 49, 70, null);
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect((int)playerX, (int)playerY, 49, 70);
        }

        // 4. Draw Bullet
        if (animState == 2) {
            g2d.setColor(Color.BLACK);
            g2d.fillOval((int)bulletX, (int)playerY + 25, 12, 12);
        }

        // 5. Draw Particles
        if (animState == 3) {
            g2d.setColor(new Color(139, 115, 85)); // Tumbleweed color
            for (Particle p : particles) {
                g2d.fillRect((int)p.x, (int)p.y, 8, 8);
            }
        }
    }

    // Inner class for particle physics
    private static class Particle {
        double x, y, vx, vy;
        int life;
        Particle(double x, double y, double vx, double vy, int life) {
            this.x = x; this.y = y; this.vx = vx; this.vy = vy; this.life = life;
        }
    }
}