package org.example;

public class Main {
    public static void main(String[] args) {
        Assets.loadAll();
        new Window();
        SoundManager soundManager = new SoundManager();
//    soundManager.play(Sound.LAST_SHOT);
    }
}
