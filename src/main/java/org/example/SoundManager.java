package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    public enum Sound {NULL, SHOT,LAST_SHOT, INTRO, SONG, JUMP, DEATH, KILL, LEVEL_COMPLETE}

    private static File file;
    private Clip loopClip;
    private Sound nowPlaying = Sound.NULL;

    public void play(Sound sound) {
        if (sound.equals(Sound.INTRO) &&  nowPlaying.equals(Sound.INTRO)) return;

        if (sound != Sound.SHOT) stop();

        switch (sound) {
            case SONG -> {
                file = Assets.getRandomSong();
                nowPlaying = Sound.SONG;
            }
            case INTRO -> {
                file = Assets.soundIntro;
                nowPlaying = Sound.INTRO;
            }
            case SHOT -> {
                file = Assets.soundSilencedShot;
                nowPlaying = Sound.SHOT;
            }
            case LAST_SHOT -> {
                file = Assets.soundShot;
                nowPlaying = Sound.LAST_SHOT;

            }
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

    public void stop() {
        if (loopClip == null) return;
        loopClip.stop();
    }
}
