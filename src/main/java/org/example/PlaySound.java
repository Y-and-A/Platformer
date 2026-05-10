package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PlaySound {
    private static String path;
    public static boolean playingShot = false;
    public static boolean playingSong = false;
    private static Thread playing;


    public PlaySound(String fileSource) {
        Random random = new Random();//maybe last shot should be nonSilent
        int rand;
        if (fileSource == "song") {
            if (playingSong) return;
            rand = random.nextInt(4) + 1;
            switch (rand) {
                case 1 -> path = "src/main/resources/sound/song1.wav";
                case 2 -> path = "src/main/resources/sound/song2.wav";
                case 3 -> path = "src/main/resources/sound/song3.wav";
                case 4 -> path = "src/main/resources/sound/song4.wav";
            }
        }
        else if (fileSource == "shot") {
            if (!playingShot) {
                playingShot = true;
                rand = random.nextInt(2) + 1;
                switch (rand) {
                    case 1 -> path = "src/main/resources/sound/silencedShot.wav";
                    case 2 -> path = "src/main/resources/sound/nonSilencedShot.wav";
                }
            } else path = "";

        }
        if (path.isEmpty())
            return;
        playing = new Thread(() -> {
            try {
                File file = new File(path);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip loopClip = AudioSystem.getClip();
                loopClip.setFramePosition(0);
                loopClip.open(audioStream);
                loopClip.loop(fileSource=="song"?-1:0);
                loopClip.start();
                playingShot = false;
                playingSong = false;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });
        playing.start();
    }
}