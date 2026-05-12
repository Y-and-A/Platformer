package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private static File file;
    private Clip loopClip;
    private Sound nowPlaying = Sound.NULL;

    public void play(Sound sound) {
        if (sound.equals(Sound.INTRO) && nowPlaying.equals(Sound.INTRO)) return;

        //todo create a shouldStop(Sound sound) method in this class
        if (sound != Sound.SHOT && sound != Sound.SHROOM_JUMP) stop(); // stop songs only

        nowPlaying = sound;

        switch (sound) {
            case SONG -> file = Assets.getRandomSong();
            case INTRO -> file = Assets.soundIntro;
            case SHOT -> file = Assets.soundSilencedShot;
            case LAST_SHOT -> file = Assets.soundShot;
            case DEATH -> file = Assets.getRandomDeathSound();
            case SHROOM_JUMP -> file = Assets.shroomJump;
            case LEVEL_COMPLETE -> file = Assets.soundLevelPassed;
            case PLAYER_HURT -> file = Assets.getRandomHurtSound();
        }

        try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                loopClip = AudioSystem.getClip();
                loopClip.open(audioStream);
                loopClip.loop(sound.equals(Sound.SONG) ? -1 : 0);
                loopClip.setFramePosition(0);
                loopClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    /* for debug purposes
    public void testSound(File file) {
        if (file.exists()) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                loopClip = AudioSystem.getClip();
                loopClip.open(audioStream);
                loopClip.loop(0);
                loopClip.setFramePosition(0);
                loopClip.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
     */

    public void stop() {
        if (loopClip != null) loopClip.stop();
    }
}
