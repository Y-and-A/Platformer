package org.example;

import javax.swing.*;
import java.awt.*;

public class HowTo extends JPanel {
    private Runnable onBackButtonClicked;

    public HowTo() {
        setBackground(Color.BLACK);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            if (onBackButtonClicked != null) onBackButtonClicked.run();
        });
        add(backButton);
    }

    public void setOnBackButtonClicked(Runnable onBackButtonClicked) {
        this.onBackButtonClicked = onBackButtonClicked;
    }
}
