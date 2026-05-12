package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WesternButton extends JButton {

    private boolean isHovered = false;

    public WesternButton(String text) {
        super(text);

        // A classic Serif font gives a "Wanted Poster" or Saloon vibe
        setFont(new Font("Serif", Font.BOLD, 28));
        setForeground(new Color(255, 245, 225)); // Off-white text

        // Remove default Swing button styling
        setFocusPainted(false);
        setContentAreaFilled(false);
        setFocusable(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Create a thick, dark border to look like a carved wooden sign
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 30, 10), 4),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Hover effect listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint(); // Force the button to redraw
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // Turn on antialiasing for smoother text and edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the wooden background. Lighten the color if the mouse is hovering.
        if (isHovered) {
            g2d.setColor(new Color(160, 90, 40)); // Lighter Wood
        } else {
            g2d.setColor(new Color(120, 60, 20)); // Dark Wood
        }
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();

        // Call super to draw the text and borders on top of our custom background
        super.paintComponent(g);
    }
}