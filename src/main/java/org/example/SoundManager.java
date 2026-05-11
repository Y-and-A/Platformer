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

        if (sound != Sound.SHOT) stop();

        nowPlaying = sound;

        switch (sound) {
            case SONG -> file = Assets.getRandomSong();
            case INTRO -> file = Assets.soundIntro;
            case SHOT -> file = Assets.soundSilencedShot;
            case LAST_SHOT -> file = Assets.soundShot;
            case DEATH -> file = Assets.getRandomDeath();
            case SHROOM_JUMP -> file = Assets.shroomJump;
            case LEVEL_COMPLETE -> file = Assets.getRandomLevelPassed();
            case PLAYER_HURT -> file = Assets.getRandomHurt();
        }

        try {
            if (file.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                loopClip = AudioSystem.getClip();
                loopClip.open(audioStream);
                loopClip.loop(sound.equals(Sound.SONG) ? -1 : 0);
                loopClip.setFramePosition(0);
                loopClip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        if (loopClip != null) loopClip.stop();
    }
}
