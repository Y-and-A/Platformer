package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private final Map<Sound, Clip> soundCache = new HashMap<>();
    private final Map<String, Clip> dynamicCache = new HashMap<>();

    private Clip backgroundMusicClip;
    private Sound currentMusicType = Sound.NULL;

    public SoundManager() {
        preloadStandardSounds();
    }

    private void preloadStandardSounds() {
        loadToCach(Sound.SHOT, Assets.soundSilencedShot);
        loadToCach(Sound.LAST_SHOT, Assets.soundShot);
        loadToCach(Sound.SHROOM_JUMP, Assets.shroomJump);
        loadToCach(Sound.LEVEL_COMPLETE, Assets.soundLevelPassed);
    }

    private void loadToCach(Sound sound, File file) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            soundCache.put(sound, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Failed to load sound: " + sound);
        }
    }

    public void play(Sound sound) {
        if (sound == Sound.SONG || sound == Sound.INTRO) {
            handleMusic(sound);
            return;
        }

        if (sound == Sound.PLAYER_HURT || sound == Sound.DEATH) {
            playDynamicSound(sound);
            return;
        }

        Clip clip = soundCache.get(sound);
        if (clip != null) {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    private void handleMusic(Sound sound) {
        if (currentMusicType == sound && backgroundMusicClip != null && backgroundMusicClip.isRunning()) return;

        stopMusic();

        File musicFile = (sound == Sound.INTRO) ? Assets.soundIntro : Assets.getRandomSong();
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(stream);

            // loop
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusicClip.start();
            currentMusicType = sound;
        } catch (Exception e) {
            System.err.println("Failed to play music: " + sound);
        }
    }

    private void playDynamicSound(Sound sound) {
        File file = (sound == Sound.DEATH) ? Assets.getRandomDeathSound() : Assets.getRandomHurtSound();
        String path = file.getAbsolutePath();

        if (!dynamicCache.containsKey(path)) {
            try {
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                dynamicCache.put(path, clip);
            } catch (Exception e) {
                System.err.println("Failed to play dynamic sound: " + sound);
                return;
            }
        }

        Clip clip = dynamicCache.get(path);
        clip.setFramePosition(0);
        clip.start();
    }

    public void stopMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
        }
        currentMusicType = Sound.NULL;
    }
}
