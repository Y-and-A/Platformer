package org.example;

import javax.swing.*;

public class TitleScreenButton extends JButton {
    private final int buttonWidth = 120;
    private final int buttonHieght = 30;

    public TitleScreenButton(String text, int x, int y) {
        setBounds(x-buttonWidth/2,y-buttonHieght/2, buttonWidth, buttonHieght);
        setFocusable(false);
        setText(text);
        setBorder(null);
    }
}
