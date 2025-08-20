package com.spamalot.shooter.render;

/**
 * Represents a simple bitmap font atlas used for text rendering.
 *
 * <p>The font texture contains 16x8 grid of characters starting at ASCII 32. The
 * {@link Renderer2D} queries this class for texture coordinates of each glyph
 * when drawing text.</p>
 */
public class BitmapFont {
  private Texture fontTexture;
  private static final int CHAR_WIDTH = 8;
  private static final int CHAR_HEIGHT = 16;
  private static final int CHARS_PER_ROW = 16;

  /**
   * Loads the font atlas from disk.
   *
   * @param fontTexturePath path to the bitmap font image
   */
  public BitmapFont(String fontTexturePath) {
    this.fontTexture = Texture.load(fontTexturePath);
  }

  /** @return the texture containing all font glyphs. */
  public Texture getTexture() {
    return fontTexture;
  }

  /**
   * Calculates the U texture coordinate for the given character.
   */
  public float getCharU(char c) {
    int charIndex = c - 32;
    return (charIndex % CHARS_PER_ROW) / (float) CHARS_PER_ROW;
  }

  /**
   * Calculates the V texture coordinate for the given character.
   */
  public float getCharV(char c) {
    int charIndex = c - 32;
    return (charIndex / CHARS_PER_ROW) / ((float) fontTexture.height() / CHAR_HEIGHT);
  }

  /** Releases the underlying texture resources. */
  public void dispose() {
    fontTexture.dispose();
  }
}
