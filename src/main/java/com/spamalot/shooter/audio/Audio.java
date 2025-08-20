package com.spamalot.shooter.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Lightweight wrapper around the Java sound API.
 *
 * <p>This class provides utility methods for loading audio clips that can be
 * played back within the game. It does not depend on LWJGL; instead it uses the
 * standard {@code javax.sound.sampled} package.</p>
 */
public final class Audio {
  /**
   * Loads a WAV file from the classpath into a {@link Clip}.
   *
   * <p>The clip is opened and ready for playback. Any errors encountered during
   * loading are wrapped in a {@link RuntimeException} for simplicity.</p>
   *
   * @param path classpath location of the WAV file
   * @return an initialized audio {@link Clip}
   */
  public static Clip loadWav(String path) {
    try (var is = Audio.class.getResourceAsStream(path)) {
      AudioInputStream ais = AudioSystem.getAudioInputStream(is);
      Clip clip = AudioSystem.getClip();
      clip.open(ais);
      return clip;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /** Utility class - prevent instantiation. */
  private Audio() {
  }
}
