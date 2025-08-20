package com.spamalot.shooter.audio;

import javax.sound.sampled.Clip;

/**
 * Represents a sound effect that can be played within the game.
 *
 * <p>This is a thin wrapper around a {@link Clip} loaded by the {@link Audio}
 * utility class. It exposes simple methods to play and dispose of the clip.</p>
 */
public class Sound {
  /** The underlying audio clip. */
  private final Clip clip;

  private Sound(Clip clip) {
    this.clip = clip;
  }

  /**
   * Loads a sound effect from a classpath resource.
   *
   * @param classpath location of the WAV file
   * @return a new {@code Sound} instance
   */
  public static Sound load(String classpath) {
    return new Sound(Audio.loadWav(classpath));
  }

  /**
   * Plays the sound from the beginning.
   *
   * <p>If the clip is already running it is first stopped and rewound before
   * playback begins to ensure the sound always starts at the first frame.</p>
   */
  public void play() {
    if (clip.isRunning()) {
      clip.stop();
    }
    clip.setFramePosition(0);
    clip.start();
  }

  /** @return {@code true} if the clip is currently playing. */
  public boolean isPlaying() {
    return clip.isRunning();
  }

  /** Releases resources associated with the clip. */
  public void dispose() {
    clip.close();
  }
}
