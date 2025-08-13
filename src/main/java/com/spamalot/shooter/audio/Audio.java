package com.spamalot.shooter.audio;

import javax.sound.sampled.*;

public final class Audio {
    public static Clip loadWav(String path) {
        try (var is = Audio.class.getResourceAsStream(path)) {
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        } catch (Exception e) { throw new RuntimeException(e); }
    }
    private Audio() {}
}
