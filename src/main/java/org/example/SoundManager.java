package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private static File file;

    public enum Sound {SHOT, INTRO, SONG, JUMP, DEATH, KILL, LEVEL_COMPLETE;}
    private Sound currentSound =Sound.INTRO;
    Clip loopClip;


    public void play(Sound sound) {
        if (currentSound!=sound)stop();
        currentSound = sound;

        switch (sound) {
            case SONG -> file = Assets.getRandomSong();
            case INTRO -> file = Assets.soundIntro;
            case SHOT -> file = Assets.soundSilencedShot;
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
