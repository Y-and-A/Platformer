package org.example;

public class Main {
    public static void main(String[] args) {
        Assets.loadAll();
        new Window();
        //for debuging sound
//        SoundManager soundManager = new SoundManager();
//    soundManager.testSound(Assets.levelPassed[0]);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        soundManager.testSound(Assets.levelPassed[1]);
    }
}
