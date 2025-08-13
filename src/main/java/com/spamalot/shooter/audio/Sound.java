package com.spamalot.shooter.audio;

import javax.sound.sampled.Clip;

public class Sound {
    private final Clip clip;

    private Sound(Clip clip) { this.clip = clip; }

    public static Sound load(String classpath) {
        return new Sound(Audio.loadWav(classpath));
    }

    public void play() {
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public boolean isPlaying() { return clip.isRunning(); }
    public void dispose() { clip.close(); }
}
