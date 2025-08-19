package com.spamalot.shooter.render;

public class BitmapFont {
  private Texture fontTexture;
  private static final int CHAR_WIDTH = 8;
  private static final int CHAR_HEIGHT = 16;
  private static final int CHARS_PER_ROW = 16;

  public BitmapFont(String fontTexturePath) {
    this.fontTexture = Texture.load(fontTexturePath);
  }

  public Texture getTexture() {
    return fontTexture;
  }

  public float getCharU(char c) {
    int charIndex = c - 32;
    return (charIndex % CHARS_PER_ROW) / (float) CHARS_PER_ROW;
  }

  public float getCharV(char c) {
    int charIndex = c - 32;
    return (charIndex / CHARS_PER_ROW) / ((float) fontTexture.height() / CHAR_HEIGHT);
  }

  public void dispose() {
    fontTexture.dispose();
  }
}