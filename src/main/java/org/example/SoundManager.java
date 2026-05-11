package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    public enum Sound {NULL, SHOT,LAST_SHOT, INTRO, SONG, SHROOM_JUMP, DEATH, KILL, LEVEL_COMPLETE,PLAYER_HURT}

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
            case DEATH -> {
                file = Assets.getRandomDeath();
                nowPlaying = Sound.DEATH;
            }
            case SHROOM_JUMP -> {
                file = Assets.shroomJump;
                nowPlaying = Sound.SHROOM_JUMP;
            }
            case LEVEL_COMPLETE -> {
                file = Assets.getRandomLevelPassed();
                nowPlaying = Sound.LEVEL_COMPLETE;
            }
            case PLAYER_HURT -> {
                file = Assets.getRandomHurt();
                nowPlaying = Sound.PLAYER_HURT;
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
